package com.study.action;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.form.UserSystemActionForm;
import com.study.enums.UserStatusEnum;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 学员平台业务操作类
 */
public class UserSystemAction extends BaseActionSupport implements ModelDriven<UserSystemActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(UserSystemAction.class);
	
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
	private UserSystemActionForm actionForm = new UserSystemActionForm();
	
	/**
	 * 创建学员平台的静态页面
	 */
	public String genarateHtmls() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.serviceFacade.getUserSystemService().genarateHtmls();
			this.setJsonResult_ActionResult(true, "您已成功生成学员平台页面文件！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的课程分页列表
	 */
	public String getCourseListByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseFactory().findListByUser(
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			paginateResult.setList(this.serviceFacade.getUserSystemService().getUSCourseSummary(this.getSessionLoginResult().getId(), paginateResult.getList()));
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的课程分页列表
	 */
	public String getExamListByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamFactory().findListByUser(
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			paginateResult.setList(this.serviceFacade.getUserSystemService().getUSExamSummary(this.getSessionLoginResult().getId(), paginateResult.getList()));
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public UserSystemActionForm getModel() {
		return actionForm;
	}

}
