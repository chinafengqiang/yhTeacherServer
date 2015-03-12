package com.study.action;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ExamActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ExamModeEnum;
import com.study.enums.ExamStatusEnum;
import com.study.enums.QuestionFetchTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.enums.TimerModeEnum;
import com.study.model.Exam;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 考试业务操作类
 */
public class ExamAction extends BaseActionSupport implements ModelDriven<ExamActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ExamAction.class);
	
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
	private ExamActionForm actionForm = new ExamActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取考试数据
	 */
	public String getExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			
			Exam exam = this.modelFactoryFacade.getExamFactory().findById( this.actionForm.getExam().getId());
			
			this.setJsonResult_ActionResult(exam);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取教师平台的考试分页列表
	 */
	public String getExamListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamFactory().findListBySearch(
					this.actionForm.getCategory(),
					ExamStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取单位平台的考试分页列表
	 */
	public String getExamListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamFactory().findListByOrgan(
					this.actionForm.getCategory(),
					ExamStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getName(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的考试分页列表
	 */
	public String getExamListByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamFactory().findListByUser(
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建考试记录
	 */
	public String createExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试名称", this.actionForm.getExam().getName()).noNull();
			ActionValidator.create("考试代号", this.actionForm.getExam().getExamCode()).noNull();
			ActionValidator.create("考试模式", this.actionForm.getExam().getExamMode()).noNull();
			ActionValidator.create("计时模式", this.actionForm.getExam().getTimerMode()).noNull();
			ActionValidator.create("试卷", this.actionForm.getExam().getTestPaperId()).noNull();

			this.serviceFacade.getExamService().createExam(
					this.actionForm.getExam().getName(),
					this.actionForm.getExam().getDescription(),
					this.actionForm.getExam().getExamCode(),
					ExamModeEnum.valueOf(this.actionForm.getExam().getExamMode()),
					this.actionForm.getExam().getCategory(),
					this.actionForm.getExam().getTestPaperId(),
					QuestionFetchTypeEnum.valueOf(this.actionForm.getExam().getQuestionFetchType()),
					this.actionForm.getExam().getValidFirstTime(),
					this.actionForm.getExam().getValidLastTime(),
					TimerModeEnum.valueOf(this.actionForm.getExam().getTimerMode()),
					this.actionForm.getExam().getTimerLimit(),
					this.actionForm.getExam().getCanAllowAllUser(),
					this.actionForm.getExam().getCanAllowMultiJoin(),
					this.actionForm.getExam().getCanCourseStudyLimit(),
					this.actionForm.getExam().getCanKeepSecretScore(),
					this.actionForm.getExam().getCanQueryAnswer(),
					this.actionForm.getExam().getCanLimitValidTime(),
					this.actionForm.getExam().getCanLimitCommitTime(),
					this.actionForm.getExam().getCanMatchDutyRank(),
					this.actionForm.getExam().getDutyRank(),
					this.actionForm.getExam().getCanMatchTrade(),
					this.actionForm.getExam().getTrade(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建考试记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改考试记录
	 */
	public String modifyExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			ActionValidator.create("考试名称", this.actionForm.getExam().getName()).noNull();
			ActionValidator.create("试卷", this.actionForm.getExam().getTestPaperId()).noNull();
			 
			this.serviceFacade.getExamService().modifyExam(
					this.actionForm.getExam().getId(),
					this.actionForm.getExam().getName(),
					this.actionForm.getExam().getDescription(),
					this.actionForm.getExam().getCategory(),
					this.actionForm.getExam().getTestPaperId(),
					QuestionFetchTypeEnum.valueOf(this.actionForm.getExam().getQuestionFetchType()),
					this.actionForm.getExam().getValidFirstTime(),
					this.actionForm.getExam().getValidLastTime(),
					TimerModeEnum.valueOf(this.actionForm.getExam().getTimerMode()),
					this.actionForm.getExam().getTimerLimit(),
					this.actionForm.getExam().getCanAllowAllUser(),
					this.actionForm.getExam().getCanAllowMultiJoin(),
					this.actionForm.getExam().getCanCourseStudyLimit(),
					this.actionForm.getExam().getCanKeepSecretScore(),
					this.actionForm.getExam().getCanQueryAnswer(),
					this.actionForm.getExam().getCanLimitValidTime(),
					this.actionForm.getExam().getCanLimitCommitTime(),
					this.actionForm.getExam().getCanMatchDutyRank(),
					this.actionForm.getExam().getDutyRank(),
					this.actionForm.getExam().getCanMatchTrade(),
					this.actionForm.getExam().getTrade(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改考试记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 设置考生范围
	 */
	public String modifyUserJoinedData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().modifyExamUserJoinedData(
					this.actionForm.getExam().getId(),
					this.actionForm.getUserJoinedData(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功安排单位！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 删除考试记录
	 */
	public String removeExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().removeExam(
					this.actionForm.getExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考试记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 导入考试
	 */
	public String importExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			this.serviceFacade.getExamService().importExam(
					this.actionForm.getFile(),
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入考试！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出文章
	 */
	public String exportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			
			Exam exam = this.modelFactoryFacade.getExamFactory().findById(this.actionForm.getExamId());
			String fileName = "考试【" + exam.getName() + "】.txt";
			
			String data = this.serviceFacade.getExamService().exportExam(
					this.actionForm.getExamId(),
					this.getSessionLoginResult().getId());

			this.actionForm.setFileFileContentType("application/vnd.ms-xml");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(new ByteArrayInputStream(data.getBytes("UTF-8")));

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 部署考试
	 */
	public String deployExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().deployExam(
					this.actionForm.getExam().getId(),
					this.getServerPath(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功部署考试！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 启动考试
	 */
	public String openExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().openExam(
					this.actionForm.getExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功启动考试！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 停止考试
	 */
	public String closeExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().closeExam(
					this.actionForm.getExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功停止考试！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 汇总考试成绩
	 */
	public String gatherExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().gatherExam(
					this.actionForm.getExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功汇总考试成绩！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 清除考试
	 */
	public String destroyExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试数据", this.actionForm.getExam()).noNull();
			ActionValidator.create("考试编号", this.actionForm.getExam().getId()).noNull();
			 
			this.serviceFacade.getExamService().destroyExam(
					this.actionForm.getExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功卸载考试！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public ExamActionForm getModel() {
		return actionForm;
	}

}
