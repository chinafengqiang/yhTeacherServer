package com.study.action;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ManagerActionForm;
import com.study.action.form.SessionLoginResult;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ManagerGradeEnum;
import com.study.enums.ManagerStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Manager;
import com.study.model.Organ;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 管理员业务操作类
 */
public class ManagerAction extends BaseActionSupport implements ModelDriven<ManagerActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ManagerAction.class);
	
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
	private ManagerActionForm actionForm = new ManagerActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取教师数据
	 */
	public String getManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("教师编号", this.actionForm.getManager().getId()).noNull();
			
			Manager manager = this.modelFactoryFacade.getManagerFactory().findById( this.actionForm.getManager().getId());
			
			this.setJsonResult_ActionResult(manager);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取当前教师数据
	 */
	public String getCurManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			Manager manager = this.modelFactoryFacade.getManagerFactory().findById(this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(manager);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取教师分页列表
	 */
	public String getManagerListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getManagerFactory().findListBySearch(
					ManagerGradeEnum.valueOf(this.actionForm.getGrade()),
					this.actionForm.getName(),
					this.actionForm.getActualName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建教师记录
	 */
	public String createManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Manager);
			
			ActionValidator.create("教师数据", this.actionForm.getManager()).noNull();
			ActionValidator.create("帐号", this.actionForm.getManager().getName()).noNull();
			ActionValidator.create("姓名", this.actionForm.getManager().getActualName()).noNull();
			ActionValidator.create("级别", this.actionForm.getManager().getGrade()).noNull();
			ActionValidator.create("状态", this.actionForm.getManager().getStatus()).noNull();
			 
			this.serviceFacade.getManagerService().createManager(
					this.actionForm.getManager().getName(),
					this.actionForm.getManager().getActualName(),
					this.actionForm.getManager().getMobile(),
					this.actionForm.getManager().getEmail(),
					this.actionForm.getManager().getSysAccess(),
					ManagerGradeEnum.valueOf(this.actionForm.getManager().getGrade()),
					ManagerStatusEnum.valueOf(this.actionForm.getManager().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建教师记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改教师记录
	 */
	public String modifyManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Manager);
			
			ActionValidator.create("教师数据", this.actionForm.getManager()).noNull();
			ActionValidator.create("教师编号", this.actionForm.getManager().getId()).noNull();
			ActionValidator.create("姓名", this.actionForm.getManager().getActualName()).noNull();
			 
			this.serviceFacade.getManagerService().modifyManager(
					this.actionForm.getManager().getId(),
					this.actionForm.getManager().getActualName(),
					this.actionForm.getManager().getMobile(),
					this.actionForm.getManager().getEmail(),
					this.actionForm.getManager().getSysAccess(),
					ManagerGradeEnum.valueOf(this.actionForm.getManager().getGrade()),
					ManagerStatusEnum.valueOf(this.actionForm.getManager().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改教师记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除教师记录
	 */
	public String removeManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Manager);
			
			ActionValidator.create("教师数据", this.actionForm.getManager()).noNull();
			ActionValidator.create("教师编号", this.actionForm.getManager().getId()).noNull();
			 
			this.serviceFacade.getManagerService().removeManager(
					this.actionForm.getManager().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除教师记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改教师密码
	 */
	public String modifyPassword() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("旧密码", this.actionForm.getOldPassword()).noNull();
			ActionValidator.create("新密码", this.actionForm.getNewPassword()).noNull();
			 
			this.serviceFacade.getManagerService().modifyPassword(
					this.getSessionLoginResult().getId(),
					this.actionForm.getOldPassword(),
					this.actionForm.getNewPassword());
			
			this.setJsonResult_ActionResult(true, "您已成功修改登录密码！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 教师登录
	 */
	public String login() {
		
		try {
			this.validateRandomCodeKey(this.actionForm.getRandomCodeKey());
			ActionValidator.create("帐号", this.actionForm.getName()).noNull();
			ActionValidator.create("密码", this.actionForm.getPassword()).noNull();
			 
			Manager manager = this.serviceFacade.getManagerService().login(
					this.actionForm.getName(),
					this.actionForm.getPassword());
			
			SessionLoginResult sessionLoginResult = SessionLoginResult.createManagerLoginResult(manager.getId(), manager.getName(), manager.getActualName());
			this.setSessionLoginResult(sessionLoginResult);
			
			this.setJsonResult_ActionResult(manager);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 校验权限
	 */
	public String validateSysAccess() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);

			ActionValidator.create("权限类型", this.actionForm.getSysAccessType()).noNull();
			 
			this.serviceFacade.getManagerService().validateSysAccess(
					this.getSessionLoginResult().getId(),
					SysAccessTypeEnum.valueOf(this.actionForm.getSysAccessType()));
			
			this.setJsonResult_ActionResult(true, "已成功校验权限！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public ManagerActionForm getModel() {
		return actionForm;
	}

}
