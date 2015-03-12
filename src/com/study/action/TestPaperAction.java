package com.study.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.form.TestPaperActionForm;
import com.study.enums.QuestionOptionsSortTypeEnum;
import com.study.enums.QuestionSortTypeEnum;
import com.study.enums.QuestionTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.enums.TestPaperStatusEnum;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.TestPaperBrowseData;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.DateUtility;
import com.study.utility.PaginateResult;

/**
 * 试卷业务操作类
 */
public class TestPaperAction extends BaseActionSupport implements ModelDriven<TestPaperActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(TestPaperAction.class);
	
	/**
	 * 数据工厂层门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 业务层门面 
	 */
	@Resource
	private ServiceFacade serviceFacade;
	
	/**
	 * Action表单
	 */
	private TestPaperActionForm actionForm = new TestPaperActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取试卷数据
	 */
	public String getTestPaper() {
		
		try {
			ActionValidator.create("试卷编号", this.actionForm.getTestPaper().getId()).noNull();
			
			TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById( this.actionForm.getTestPaper().getId());
			
			this.setJsonResult_ActionResult(testPaper);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取试卷题目数据
	 */
	public String getTestPaperQuestion() {
		
		try {
			ActionValidator.create("试卷题目编号", this.actionForm.getTestPaperQuestion().getId()).noNull();
			
			TestPaperQuestion testPaperQuestion = this.modelFactoryFacade.getTestPaperQuestionFactory().findById(this.actionForm.getTestPaperQuestion().getId());
			
			this.setJsonResult_ActionResult(testPaperQuestion);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取试卷分页列表
	 */
	public String getTestPaperListBySearch() {
		
		try {		
			//this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getTestPaperFactory().findListBySearch(
					this.actionForm.getName(),
					this.actionForm.getCategory(),
					TestPaperStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取考试可选用的试卷分页列表
	 */
	public String getTestPaperListByExamSelect() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getTestPaperFactory().findListBySearch(
					this.actionForm.getName(),
					this.actionForm.getCategory(),
					TestPaperStatusEnum.Opened,
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取试卷题目分页列表
	 */
	public String getTestPaperQuestionListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("题目套数", this.actionForm.getSeries()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getTestPaperQuestionFactory().findListBySearch(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries(),
					this.actionForm.getName(),
					QuestionTypeEnum.valueOf(this.actionForm.getQuestionType()),
					this.actionForm.getDifficulty(),
					this.actionForm.getKen(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建试卷记录
	 */
	public String createTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷数据", this.actionForm.getTestPaper()).noNull();
			ActionValidator.create("名称", this.actionForm.getTestPaper().getName()).noNull();
			 
			this.serviceFacade.getTestPaperService().createTestPaper(
					this.actionForm.getTestPaper().getName(),
					this.actionForm.getTestPaper().getDescription(),
					this.actionForm.getTestPaper().getCategory(),
					this.actionForm.getTestPaper().getTestPaperOptions(),
					QuestionSortTypeEnum.valueOf(this.actionForm.getTestPaper().getQuestionSortType()),
					QuestionOptionsSortTypeEnum.valueOf(this.actionForm.getTestPaper().getQuestionOptionsSortType()),
					this.actionForm.getTestPaper().getCredit(),
					this.actionForm.getTestPaper().getTotalScore(),
					this.actionForm.getTestPaper().getTotalSeries(),
					this.actionForm.getTestPaper().getPassScore(),
					this.actionForm.getTestPaper().getCanIgnoreQuestionScore(),
					TestPaperStatusEnum.valueOf(this.actionForm.getTestPaper().getStatus()),
					this.getSessionLoginResult().getId(), this.actionForm.getTestPaper().getCanQueryAnswer());
			
			this.setJsonResult_ActionResult(true, "您已成功新建试卷记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改试卷记录
	 */
	public String modifyTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷数据", this.actionForm.getTestPaper()).noNull();
			ActionValidator.create("试卷编号", this.actionForm.getTestPaper().getId()).noNull();
			ActionValidator.create("标题", this.actionForm.getTestPaper().getName()).noNull();
			 
			this.serviceFacade.getTestPaperService().modifyTestPaper(
					this.actionForm.getTestPaper().getId(),
					this.actionForm.getTestPaper().getName(),
					this.actionForm.getTestPaper().getDescription(),
					this.actionForm.getTestPaper().getCategory(),
					this.actionForm.getTestPaper().getTestPaperOptions(),
					QuestionSortTypeEnum.valueOf(this.actionForm.getTestPaper().getQuestionSortType()),
					QuestionOptionsSortTypeEnum.valueOf(this.actionForm.getTestPaper().getQuestionOptionsSortType()),
					this.actionForm.getTestPaper().getCredit(),
					this.actionForm.getTestPaper().getTotalScore(),
					this.actionForm.getTestPaper().getTotalSeries(),
					this.actionForm.getTestPaper().getPassScore(),
					this.actionForm.getTestPaper().getCanIgnoreQuestionScore(),
					TestPaperStatusEnum.valueOf(this.actionForm.getTestPaper().getStatus()),
					this.getSessionLoginResult().getId(),this.actionForm.getTestPaper().getCanQueryAnswer());			
			
			this.setJsonResult_ActionResult(true, "您已成功修改试卷记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除试卷记录
	 */
	public String removeTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷数据", this.actionForm.getTestPaper()).noNull();
			ActionValidator.create("试卷编号", this.actionForm.getTestPaper().getId()).noNull();
			 
			this.serviceFacade.getTestPaperService().removeTestPaper(
					this.actionForm.getTestPaper().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除试卷记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 预览试卷
	 */
	public String browseTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("试卷套数", this.actionForm.getSeries()).noNull();
			
			Map session = ActionContext.getContext().getSession();
			session.put("testPaperId", this.actionForm.getTestPaperId());
			session.put("series", this.actionForm.getSeries());
			
			this.setJsonResult_ActionResult(true, "您可以预览试卷了！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取试卷浏览的数据
	 */
	public String getTestPaperData() {
		
		try {
			
		//	this.validateLoginType(SessionLoginTypeEnum.Manager);
		//	this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
//			Map session = ActionContext.getContext().getSession();
//			Long testPaperId = (Long)session.get("testPaperId");
//			Integer series = (Integer)session.get("series");
			
			Long testPaperId = this.actionForm.getTestPaperId();
			
			TestPaperBrowseData data = this.serviceFacade.getTestPaperService().getTestPaperBrowseData(testPaperId, 1);
			
			this.setJsonResult_ActionResult(data);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取试卷浏览的数据
	 */
	public String getTestPaperBrowseData() {
		
		try {
			
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			Map session = ActionContext.getContext().getSession();
			Long testPaperId = (Long)session.get("testPaperId");
			Integer series = (Integer)session.get("series");
			
		//	Long testPaperId = this.actionForm.getTestPaperId();
			
			TestPaperBrowseData data = this.serviceFacade.getTestPaperService().getTestPaperBrowseData(testPaperId, 1);
			
			this.setJsonResult_ActionResult(data);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 选题
	 */
	public String selectQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("题目套数", this.actionForm.getSeries()).noNull();
			ActionValidator.create("题目编号", this.actionForm.getQuestionId()).noNull();
			 
			this.serviceFacade.getTestPaperService().selectQuestion(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries(),
					this.actionForm.getQuestionId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功选题！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 自动抽题
	 */
	public String autoSelectQuestionList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("题目分类", this.actionForm.getQuestionCategoryId()).noNull();
			ActionValidator.create("题型", this.actionForm.getQuestionType()).noNull();
			ActionValidator.create("数量", this.actionForm.getNumber()).noNull();
			
			this.serviceFacade.getTestPaperService().autoSelectQuestionList(
					this.actionForm.getTestPaperId(),
					this.actionForm.getQuestionCategoryId(),
					QuestionTypeEnum.valueOf(this.actionForm.getQuestionType()),
					this.actionForm.getDifficulty(),
					this.actionForm.getScore(),
					this.actionForm.getKen(),
					this.actionForm.getNumber(),
					this.actionForm.getSeries(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已完成自动选题操作！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入题目
	 */
	public String importQuestionList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("题目套数", this.actionForm.getSeries()).noNull();
			
			this.serviceFacade.getTestPaperService().importQuestionList(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries(),
					this.actionForm.getFile(),
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入题目集！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出试卷套数的题目列表
	 */
	public String exportQuestionList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("试题套数", this.actionForm.getSeries()).noNull();
			
			TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(this.actionForm.getTestPaperId());
			String fileName = "试卷题目【" + testPaper.getName() + "】第" + this.actionForm.getSeries() + "套.xls";
			
			InputStream is = this.serviceFacade.getTestPaperService().exportQuestionList(this.actionForm.getTestPaperId(), this.actionForm.getSeries(), this.getSessionLoginResult().getId());

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 获取试卷摘要
	 */
	public String getTestPaperSummary() {
		
		try {
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			ActionValidator.create("题目套数", this.actionForm.getSeries()).noNull();
			
			Map<String, String> map = this.serviceFacade.getTestPaperService().getTestPaperSummary(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(map);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 调整题目
	 */
	public String adjustQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷题目编号", this.actionForm.getTestPaperQuestionId()).noNull();
			ActionValidator.create("分数", this.actionForm.getScore()).noNull();
			ActionValidator.create("排序", this.actionForm.getSortFlag()).noNull();
			 
			this.serviceFacade.getTestPaperService().adjustQuestion(
					this.actionForm.getTestPaperQuestionId(),
					this.actionForm.getScore(),
					this.actionForm.getSortFlag(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功调整题目！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除题目
	 */
	public String removeQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷题目编号", this.actionForm.getTestPaperQuestionId()).noNull();
			 
			this.serviceFacade.getTestPaperService().removeQuestion(
					this.actionForm.getTestPaperQuestionId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除题目！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 校验试卷
	 */
	public String validateTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaperId()).noNull();
			 
			this.serviceFacade.getTestPaperService().validateTestPaper(
					this.actionForm.getTestPaperId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功校验试卷！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}

	/**
	 * 导入试卷
	 */
	public String importTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			this.serviceFacade.getTestPaperService().importTestPaper(
					this.actionForm.getFile(), 
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入试卷记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出试卷
	 */
	public String exportTestPaper() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			ActionValidator.create("试卷编号", this.actionForm.getTestPaper().getId()).noNull();
			
			String data = this.serviceFacade.getTestPaperService().exportTestPaper(
					this.actionForm.getTestPaper().getId(),
					this.getSessionLoginResult().getId());

			TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(this.actionForm.getTestPaper().getId());
			String fileName = "试卷【" + testPaper.getName() + "】.txt";

			this.actionForm.setFileFileContentType("application/vnd.ms-xml");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(new ByteArrayInputStream(data.getBytes("UTF-8")));

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 导出试卷
	 */
	public String exportTestPaperToWord() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.TestPaper);
			
			String fileName = this.serviceFacade.getTestPaperService().getWordFileNameByExport(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries());
			
			InputStream is = this.serviceFacade.getTestPaperService().exportTestPaperToWord(
					this.actionForm.getTestPaperId(),
					this.actionForm.getSeries(),
					this.getSessionLoginResult().getId());

			this.actionForm.setFileFileContentType("application/vnd.ms-word");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	
	/**
	 * 跳转到消息页面
	 * 
	 * @return
	 */
	public String editSenderTestPaper() {
		
		TestPaper testPaper = null;
		try {
			testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(this.actionForm.getTestPaper().getId());
			String firstTime = DateUtility.dateToString(new Date(), false);
			this.setJsonResult_ActionResult(testPaper);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	

//	public String sendTestPaper() {
//		
//		HttpServletRequest request = ServletActionContext.getRequest();
//		
//		TestPaper testPaper = null;
//		try {
//			testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(this.actionForm.getTestPaper().getId());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//		String targetDir = "testPaper"+testPaper.getId();
//		String fileSqlName = "testPaper" + testPaper.getId() +".sql";
//		String fileName = SystemUtility.createUUID();
//		String destDirName = "d:/push_profile";
//		String targetDirs = destDirName + "/" + targetDir;
//		String path = destDirName + "/" + fileName +".ini";
//		FileOperation.createDir(targetDirs);
//		FileOperation.createDir(destDirName);
//		String insertPath = destDirName + "/" + targetDir +"/"+ fileSqlName;
//		
//		String insertContent = " insert into test_paper (name, description, category, test_paper_key, test_paper_options, total_series"
//				+ ", total_score, pass_score, question_show_type, question_options_sort_type, question_sort_type, can_ignore_question_score," +
//						"credit, status, created_time, creator)"
//				+ " values ('"+testPaper.getName() +"', '"+testPaper.getDescription()+"', '"+testPaper.getCategory()+"',"
//						+ "'"+testPaper.getTestPaperKey()+"', '"+testPaper.getTestPaperOptions()+"', "+testPaper.getTotalSeries()+", "
//								+ ""+testPaper.getTotalScore()+", "+testPaper.getPassScore()+" , "+testPaper.getQuestionShowType()+" , "
//								+ ""+testPaper.getQuestionOptionsSortType()+", "+testPaper.getQuestionSortType()+" , "+testPaper.getCanIgnoreQuestionScore()+" , "
//								+ ""+testPaper.getCreator()+", "+testPaper.getStatus()+" , "
//										+ "'"+DateUtility.dateToString(testPaper.getCreatedTime(), false)+"' , "+testPaper.getCreator()+" ); "
//												+ " commit; ";
////		FileOperation.contentToTxt(insertPath, insertContent);
////		FileOperation.write(insertContent, insertPath, "UTF-8");
//		List<TestPaperQuestion> lists = this.modelFactoryFacade.getTestPaperQuestionFactory().findListByTestPaper(testPaper.getId());
//		String questionInsertContent = "";
//		String uploadDir = "";
//		String insertTPath = "";
//		System.out.println("lists.size()====="+lists.size());
//		for(TestPaperQuestion tpq : lists){
//			
//			
//			String targetTDir = targetDir + "/" + "testPaperQuestion"+tpq.getId();
//			String targetPicDir = targetDir + "/" + "testPaperQuestion"+tpq.getId();
//		//	String fileTSqlName = "testPaperQuestion" + tpq.getId() +".sql";
//			String fileTSqlName = "testPaper" + testPaper.getId() +".sql";
//			String desttDirName = "d:/push_profile";
//			insertTPath = desttDirName + "/" + targetDir +"/"+ fileTSqlName;
//			
//			questionInsertContent += " insert into test_paper_question (test_paper_id, series, question_id, question_type, name, options"
//				+ ", answer, ken, difficulty, score, sort_flag, note) select id, " + tpq.getSeries() + ","+ tpq.getQuestionId() +","
//				+ tpq.getQuestionType() + ",'" + tpq.getName()+"', '"+tpq.getOptions()+"', '"+tpq.getAnswer()+"', '"+tpq.getKen()+"', "
//				+ "'"+tpq.getDifficulty()+"', '"+tpq.getScore()+"', "
//				+ "'"+tpq.getSortFlag()+"' , '"+tpq.getNote()+"'"
//				+	" from test_paper where test_paper_key ='"+testPaper.getTestPaperKey()+"';  commit;";
//			
//		//	FileOperation.contentToTxt(insertTPath, questionInsertContent);
//			uploadDir = request.getSession().getServletContext().getRealPath(tpq.getNote());
//			String uploadDirName = uploadDir.substring(uploadDir.lastIndexOf("\\") + 1, uploadDir.length());
//			String targetTDirs = desttDirName + "/" + targetDir;
//			
//			System.out.println("targetTDirs=="+targetTDirs);
//			
//			try {
////				FileOperation.copyFile(new File(uploadPic), new File(targetDirs + "/" + uploadPicName));
//				FileOperation.copyFile(new File(uploadDir), new File(targetTDirs + "/" + uploadDirName));
//			} catch (IOException e1) { 
//				e1.printStackTrace();
//			}
//			
//			
//		}
//		
//		FileOperation.write(insertContent + questionInsertContent, insertTPath, "UTF-8");
////		FileOperation.contentToTxt(insertTPath, questionInsertContent + "好");
//	//	questionInsertContent += "commit;";
//		
//		
//	//	String uploadDir = request.getSession().getServletContext().getRealPath(course.getUrl());
//	//	String uploadPic = request.getSession().getServletContext().getRealPath(course.getPic());
////		String uploadDirName = uploadDir.substring(uploadDir.lastIndexOf("\\") + 1, uploadDir.length());
////		String uploadPicName = uploadPic.substring(uploadPic.lastIndexOf("\\") + 1, uploadPic.length());
//		System.out.println("uploadDir=="+uploadDir);
//		
//	
//	//	String insertPath = destDirName + "/" + targetDir +"/"+ fileSqlName;
//		
//		String firstTime = actionForm.getBeginTime();
//		String endTime = actionForm.getEndTime();
//
////		try {
//////			FileOperation.copyFile(new File(uploadPic), new File(targetDirs + "/" + uploadPicName));
////			FileOperation.copyFile(new File(uploadDir), new File(targetDirs + "/" + uploadDirName));
////		} catch (IOException e1) { 
////			e1.printStackTrace();
////		}
//		
//		try{
//		//	FileOperation.contentToTxt(insertPath, insertContent);
//	        
//			String content = "[option] \r\n"
//					+ "Dir="+targetDir+" \r\n"
//					+ "channelID=11 \r\n"
//					+ "priority=1 \r\n"
//					+ "bandwidth=1024 \r\n"
//					+ "PackFile=0 \r\n"
//					+ "sendMode=2 \r\n"
//					+ "sendTime=00:00:00 \r\n"
//					+ "repeatcount=1 \r\n"
//					+ "validRate=100.0 \r\n"
//					+ "startValidDate="+firstTime+" \r\n"
//					+ "endValidDate="+endTime+" \r\n"
//					+ "Completed=0 \r\n";
//			FileOperation.contentToTxt(path, content);
//	//		this.courseService.modifyCourseStatus(id, CourseStatusEnum.SENDED);
//
//			this.setJsonResult_ActionResult(true, "您发送成功！");
//		} catch (Exception e){
//			this.setJsonResult_ActionResult(false, e.getMessage());
//		}
//		
//		this.responseWriteJsonResult();
//		return NONE;
//	}
	
	
	@Override
	public TestPaperActionForm getModel() {
		return actionForm;
	}

}
