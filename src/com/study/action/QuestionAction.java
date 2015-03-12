package com.study.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.QuestionActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.QuestionTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Question;
import com.study.model.QuestionCategory;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 题目业务操作类
 */
public class QuestionAction extends BaseActionSupport implements ModelDriven<QuestionActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(QuestionAction.class);
	
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
	private QuestionActionForm actionForm = new QuestionActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取题目分类数据
	 */
	public String getQuestionCategory() {
		
		try {
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategory().getId()).noNull();
			
			QuestionCategory questionCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById( this.actionForm.getQuestionCategory().getId());
			
			this.setJsonResult_ActionResult(questionCategory);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取题目分类列表
	 */
	public String getQuestionCategoryListByTree() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			List<QuestionCategory> list = this.serviceFacade.getQuestionService().getQuestionCategoryListByTree();
			
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取题目数据
	 */
	public String getQuestion() {
		
		try {
			ActionValidator.create("题目编号", this.actionForm.getQuestion().getId()).noNull();
			
			Question question = this.modelFactoryFacade.getQuestionFactory().findById( this.actionForm.getQuestion().getId());
			
			this.setJsonResult_ActionResult(question);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取题目分页列表
	 */
	public String getQuestionListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getQuestionFactory().findListBySearch(
					this.actionForm.getQuestionCategoryId(),
					QuestionTypeEnum.valueOf(this.actionForm.getQuestionType()),
					this.actionForm.getDifficulty(),
					this.actionForm.getKen(),
					this.actionForm.getName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建题目分类记录
	 */
	public String createCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			ActionValidator.create("题目分类数据", this.actionForm.getQuestionCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getQuestionCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getQuestionCategory().getSortFlag()).noNull();
			
			this.serviceFacade.getQuestionService().createCategory(
					this.actionForm.getQuestionCategoryId(),
					this.actionForm.getQuestionCategory().getName(),
					this.actionForm.getQuestionCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功新建题目分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建题目子分类记录
	 */
	public String createChildCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			ActionValidator.create("题目分类数据", this.actionForm.getQuestionCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getQuestionCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getQuestionCategory().getSortFlag()).noNull();
			
			this.serviceFacade.getQuestionService().createChildCategory(
					this.actionForm.getQuestionCategoryId(),
					this.actionForm.getQuestionCategory().getName(),
					this.actionForm.getQuestionCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功新建题目子分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改题目分类记录
	 */
	public String modifyCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类数据", this.actionForm.getQuestionCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getQuestionCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getQuestionCategory().getSortFlag()).noNull();
			 
			this.serviceFacade.getQuestionService().modifyCategory(
					this.actionForm.getQuestionCategory().getId(),
					this.actionForm.getQuestionCategory().getName(),
					this.actionForm.getQuestionCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功修改题目分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除题目分类记录
	 */
	public String removeCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类数据", this.actionForm.getQuestionCategory()).noNull();
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategory().getId()).noNull();
			 
			this.serviceFacade.getQuestionService().removeCategory(
					this.actionForm.getQuestionCategory().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除题目分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建题目记录
	 */
	public String createQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			ActionValidator.create("题目数据", this.actionForm.getQuestion()).noNull();
			ActionValidator.create("题目内容", this.actionForm.getQuestion().getName()).noNull();
			ActionValidator.create("答案", this.actionForm.getQuestion().getAnswer()).noNull();
			ActionValidator.create("分数", this.actionForm.getQuestion().getScore()).noNull();
			ActionValidator.create("难度", this.actionForm.getQuestion().getDifficulty()).noNull();
			 
			this.serviceFacade.getQuestionService().createQuestion(
					this.actionForm.getQuestionCategoryId(),
					QuestionTypeEnum.valueOf(this.actionForm.getQuestion().getQuestionType()),
					this.actionForm.getQuestion().getName(),
					this.actionForm.getQuestion().getAnswer(),
					this.actionForm.getQuestion().getNote(),
					this.actionForm.getQuestion().getOptions(),
					this.actionForm.getQuestion().getScore(),
					this.actionForm.getQuestion().getDifficulty(),
					this.actionForm.getQuestion().getKen());
			
			this.setJsonResult_ActionResult(true, "您已成功新建题目记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改题目记录
	 */
	public String modifyQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目数据", this.actionForm.getQuestion()).noNull();
			ActionValidator.create("题目编号", this.actionForm.getQuestion().getId()).noNull();
			ActionValidator.create("题目数据", this.actionForm.getQuestion()).noNull();
			ActionValidator.create("题目内容", this.actionForm.getQuestion().getName()).noNull();
			ActionValidator.create("答案", this.actionForm.getQuestion().getAnswer()).noNull();
			ActionValidator.create("分数", this.actionForm.getQuestion().getScore()).noNull();
			ActionValidator.create("难度", this.actionForm.getQuestion().getDifficulty()).noNull();
			 
			this.serviceFacade.getQuestionService().modifyQuestion(
					this.actionForm.getQuestion().getId(),
					this.actionForm.getQuestion().getName(),
					this.actionForm.getQuestion().getAnswer(),
					this.actionForm.getQuestion().getNote(),
					this.actionForm.getQuestion().getOptions(),
					this.actionForm.getQuestion().getScore(),
					this.actionForm.getQuestion().getDifficulty(),
					this.actionForm.getQuestion().getKen());
			
			this.setJsonResult_ActionResult(true, "您已成功修改题目记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除题目记录
	 */
	public String removeQuestion() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目数据", this.actionForm.getQuestion()).noNull();
			ActionValidator.create("题目编号", this.actionForm.getQuestion().getId()).noNull();
			 
			this.serviceFacade.getQuestionService().removeQuestion(
					this.actionForm.getQuestion().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除题目记录！");
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
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			
			this.serviceFacade.getQuestionService().importQuestionList(this.actionForm.getQuestionCategoryId(), this.actionForm.getFile());

			this.setJsonResult_ActionResult(true, "您已成功导入题目集！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出题目列表
	 */
	public String exportQuestionList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			ActionValidator.create("题目分类编号", this.actionForm.getQuestionCategoryId()).noNull();
			
			QuestionCategory questionCategory = this.modelFactoryFacade.getQuestionCategoryFactory().findById(this.actionForm.getQuestionCategoryId());
			String fileName = "题目集【" + questionCategory.getName() + "】.xls";
			
			InputStream is = this.serviceFacade.getQuestionService().exportQuestionList(this.actionForm.getQuestionCategoryId());

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 导出题目模板
	 */
	public String exportQuestionExcelTemplet() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
			
			String fileName = "导入题目的模板.xls";
			
			InputStream is = this.serviceFacade.getQuestionService().exportQuestionExcelTemplet();

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 上传图片
	 */
	public String uploadQuestionImage() {
		
//		FileOutputStream fos = null;
//        FileInputStream fis = null;
//		try {
//			this.validateLoginType(SessionLoginTypeEnum.Manager);
//			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Question);
//			
//			 String realpath = ServletActionContext.getServletContext().getRealPath("/upload/testpaper");
//			 String realName = "";
//			 System.out.println("realpath: "+realpath);
//			 
//			 
//			 fos = new FileOutputStream(realpath + "\\" + actionForm.getImageFileName());
//			 fis = new FileInputStream(this.actionForm.getImage());
//			 
////			 String fileId = this.serviceFacade.getQuestionService().save(fis, "common");
////			 System.out.println("fileId=="+fileId);
////			 InputStream fism = this.serviceFacade.getQuestionService().getFileById(fileId, "common");
//			 
//			 byte[] buffer = new byte[1024];
//	         int len = 0;
//             while ((len = fis.read(buffer)) > 0) {
//                fos.write(buffer, 0, len);
//             }
//             HttpServletRequest request = ServletActionContext.getRequest();
//             String address = request.getLocalAddr();
//             InetAddress inet = InetAddress.getLocalHost();
//             int port = request.getLocalPort();
//             String url = "http://" + inet.getHostAddress() + ":" + port + "/upload/testpaper/" + actionForm.getImageFileName();
//             
////			 String url = ServerInfo.SERVER_ADDRESS + ServerInfo.SERVER_PORT + "/Question/getImageStream.action?fileId="+fileId;
////			 String url = "";
////			 System.out.println(url);
//
//			this.setJsonResult_ActionResult(url);
//		} catch (Exception ex) {
//			this.setJsonResult_ActionResult(false, ex.getMessage());
//		}
//		
//		this.responseWriteJsonResult();
		return NONE;
	}
	
	
	/**
	 * 上传图片
	 */
	public String getImageStream() {
		
//		HttpServletResponse response= ServletActionContext.getResponse ();
//		
//		try {
//			OutputStream os = response.getOutputStream();
//			//response.setContentType("image/jpg");
//			response.setContentType("image/jpeg"); 
//			String fileId = actionForm.getFileId();
//			InputStream fism = this.serviceFacade.getQuestionService().getFileById(fileId, "common");
//			byte[] buffer = new byte[1024];
//	        int len = 0;
//	        while((len = fism.read(buffer)) != -1) {
//	         os.write(buffer, 0, len);
//	        }
//	        os.flush();
//	        os.close();
//	        fism.close();
//			 
//			// String temp = new sun.misc.BASE64Encoder().encodeBuffer(output.toByteArray());
//			 
//	//		this.setJsonResult_ActionResult(temp);
//		} catch (Exception ex) {
//			this.setJsonResult_ActionResult(false, ex.getMessage());
//		}
//		
//	//	this.responseWriteJsonResult();
	return NONE;
	}
	
	@Override
	public QuestionActionForm getModel() {
		return actionForm;
	}

}
