package com.study.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ExamUserActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ExamUserStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.UserTestPaperBrowseData;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 考试业务操作类
 */
public class ExamUserAction extends BaseActionSupport implements ModelDriven<ExamUserActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ExamUserAction.class);
	
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
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * Action表单
	 */
	private ExamUserActionForm actionForm = new ExamUserActionForm();
	
	/**
	 * 获取教师平台考试控制台的所有考生分页列表
	 */
	public String getExamUserListByManager() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamUserFactory().findListByExam_Manager(
					this.actionForm.getExamId(),
					this.actionForm.getActualOrgan(),
					this.actionForm.getActualName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取单位平台上的考试控制台的所有考生分页列表
	 */
	public String getExamUserListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamUserFactory().findListByExam_Organ(
					this.actionForm.getExamId(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getActualOrgan(),
					this.actionForm.getActualName(),
					ExamUserStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位管理员添加考生
	 */
	public String createExamUserByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUserId()).noNull();
			 
			this.serviceFacade.getExamUserService().validateOrganAction(
					this.actionForm.getUserId(), 
					this.getSessionLoginResult().getId());
			
			this.serviceFacade.getExamUserService().createExamUser(
					this.actionForm.getExamId(),
					this.actionForm.getUserId());
			
			this.setJsonResult_ActionResult(true, "您已成功添加考生！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 教师平台添加考生
	 */
	public String createExamUserByManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUserId()).noNull();
			 
			this.serviceFacade.getExamUserService().createExamUser(
					this.actionForm.getExamId(),
					this.actionForm.getUserId());
			
			this.setJsonResult_ActionResult(true, "您已成功添加考生！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位管理员设置重考
	 */
	public String resetExamUserByManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUserId()).noNull();
			 
			this.serviceFacade.getExamUserService().resetExamUser(
					this.actionForm.getExamId(),
					this.actionForm.getUserId());
			
			this.setJsonResult_ActionResult(true, "您已成功设置重考！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位管理员设置重考
	 */
	public String resetExamUserByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUserId()).noNull();
			
			this.serviceFacade.getExamUserService().validateOrganAction(
					this.actionForm.getUserId(), 
					this.getSessionLoginResult().getId());
			
			this.serviceFacade.getExamUserService().resetExamUser(
					this.actionForm.getExamId(),
					this.actionForm.getUserId()
					);
			
			this.setJsonResult_ActionResult(true, "您已成功设置重考！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 汇总考生成绩
	 */
	public String gatherExamUser() {
		
		try {
			ActionValidator.create("考试标示", this.actionForm.getEsExamKey()).noNull();
			ActionValidator.create("考生答卷", this.actionForm.getEsExamUserData()).noNull();
			 
			this.serviceFacade.getExamUserService().gatherExamUser(
					this.actionForm.getEsExamKey(),
					this.actionForm.getEsExamUserData());
			
			this.setJsonResult_ActionResult(true, "您已成功汇总考生成绩！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位管理员删除考生
	 */
	public String removeExamUserByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考生编号", this.actionForm.getExamUserId()).noNull();
			 
			this.serviceFacade.getExamUserService().validateOrganActionByExamUser(
					this.actionForm.getExamUserId(), 
					this.getSessionLoginResult().getId());
			
			this.serviceFacade.getExamUserService().removeExamUser(
					this.actionForm.getExamUserId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考生！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 教师平台删除考生
	 */
	public String removeExamUserByManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考生编号", this.actionForm.getExamUserId()).noNull();
			 			
			this.serviceFacade.getExamUserService().removeExamUser(
					this.actionForm.getExamUserId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考生！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入考生答卷数据包
	 */
	public String importExamUserData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);

			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();

			this.serviceFacade.getExamUserService().importExamUserData(
					this.actionForm.getExamId(),
					this.actionForm.getFile(),
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入考生答卷数据包！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 获取考试直通车链接入口
	 */
	public String getExamLink() {
		
		try {
			ActionValidator.create("考生编号", this.actionForm.getExamUserId()).noNull();
			 
			String data = this.serviceFacade.getExamUserService().getExamLink(
					this.actionForm.getExamUserId());
			
			this.setJsonResult_ActionResult(data);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 教师平台预览答卷
	 */
	public String browseUserTestPaperByMananger() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			ActionValidator.create("考生编号", this.actionForm.getExamUserId()).noNull();
			
			this.serviceFacade.getExamUserService().getUserTestPaperBrowseData(this.actionForm.getExamUserId(), null);
			
			Map session = ActionContext.getContext().getSession();
			session.put("examUserId", this.actionForm.getExamUserId());
			
			this.setJsonResult_ActionResult(true, "您可以查看答卷了！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 教师平台获取答卷浏览的数据
	 */
	public String getUserTestPaperBrowseDataByManager() {
		
		try {
			
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Exam);
			
			Map session = ActionContext.getContext().getSession();
			Long examUserId = (Long)session.get("examUserId");
			
			UserTestPaperBrowseData data = this.serviceFacade.getExamUserService().getUserTestPaperBrowseData(examUserId, null);
			
			this.setJsonResult_ActionResult(data);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位平台预览答卷
	 */
	public String browseUserTestPaperByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考生编号", this.actionForm.getExamUserId()).noNull();
			
			this.serviceFacade.getExamUserService().getUserTestPaperBrowseData(this.actionForm.getExamUserId(), this.getSessionLoginResult().getId());
			
			Map session = ActionContext.getContext().getSession();
			session.put("examUserId", this.actionForm.getExamUserId());
			
			this.setJsonResult_ActionResult(true, "您可以预览答卷了！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 单位平台获取答卷浏览的数据
	 */
	public String getUserTestPaperBrowseDataByOrgan() {
		
		try {
			
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			Map session = ActionContext.getContext().getSession();
			Long examUserId = (Long)session.get("examUserId");
			
			UserTestPaperBrowseData data = this.serviceFacade.getExamUserService().getUserTestPaperBrowseData(examUserId, this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(data);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	@Override
	public ExamUserActionForm getModel() {
		return actionForm;
	}

}
