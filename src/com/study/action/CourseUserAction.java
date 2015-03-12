package com.study.action;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.CourseUserActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.model.CourseUser;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 课程学员业务操作类
 */
public class CourseUserAction extends BaseActionSupport implements ModelDriven<CourseUserActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(CourseUserAction.class);
	
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
	private CourseUserActionForm actionForm = new CourseUserActionForm();
	
	/**
	 * 获取教师平台课程控制台的所有课程学员分页列表
	 */
	public String getCourseUserListByManager() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseUserFactory().findListByCourse_Manager(
					this.actionForm.getCourseId(),
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
	 * 获取单位平台上的课程控制台的所有课程学员分页列表
	 */
	public String getCourseUserListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseUserFactory().findListByCourse_Organ(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getActualOrgan(),
					this.actionForm.getActualName(),
					CourseUserStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的学员学习课程的摘要
	 */
	public String getCourseUserByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			
			CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(
					this.actionForm.getCourseId(), 
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(courseUser);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 校验课程学习
	 */
	public String confirmStudy() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			 
			this.serviceFacade.getCourseUserService().confirmStudy(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您可以开始学习课程了！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 学员开始学习课程
	 */
	public String beginStudy() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			 
			this.serviceFacade.getCourseUserService().beginStudy(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已开始学习课程！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 学员结束学习课程
	 */
	public String endStudy() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			 
			this.serviceFacade.getCourseUserService().endStudy(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已结束学习课程！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 学员结束学习课程
	 */
	public String finishStudy() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			 
			this.serviceFacade.getCourseUserService().endStudy(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId());
			
			this.serviceFacade.getCourseUserService().finishStudy(
					this.actionForm.getCourseId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "恭喜，您已完成课程的学习！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 在教师平台删除课程学员
	 */
	public String removeCourseUserByManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("课程学员编号", this.actionForm.getCourseUserId()).noNull();
			 
			this.serviceFacade.getCourseUserService().removeCourseUserByManager(
					this.actionForm.getCourseUserId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除课程学员！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位管理员删除课程学员
	 */
	public String removeCourseUserByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("课程学员编号", this.actionForm.getCourseUserId()).noNull();
			 
			this.serviceFacade.getCourseUserService().removeCourseUserByOrgan(
					this.actionForm.getCourseUserId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除课程学员！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public CourseUserActionForm getModel() {
		return actionForm;
	}

}
