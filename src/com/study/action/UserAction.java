package com.study.action;

import java.io.InputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.SessionLoginResult;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.form.UserActionForm;
import com.study.enums.UserStatusEnum;
import com.study.model.Organ;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.us.USUserSummary;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 学员业务操作类
 */
public class UserAction extends BaseActionSupport implements ModelDriven<UserActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(UserAction.class);
	
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
	private UserActionForm actionForm = new UserActionForm();
	
	/**
	 * 获取学员数据
	 */
	public String getUser() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("学员编号", this.actionForm.getUser().getId()).noNull();
			
			User user = this.modelFactoryFacade.getUserFactory().findById(this.actionForm.getUser().getId());
			user.setPassword(this.serviceFacade.getSystemService().decryptPassword(user.getPassword()));
			
			this.setJsonResult_ActionResult(user);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取单位平台的学员分页列表
	 */
	public String getUserListBySearch() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getUserFactory().findListBySearch(
					this.actionForm.getActualOgan(),
					this.actionForm.getName(),
					this.actionForm.getActualName(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取单位平台的可选择的学员分页列表
	 */
	public String getUserListByOrganSelect() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getUserFactory().findListByOrganSelect(
					this.actionForm.getActualOgan(),
					this.actionForm.getActualName(),
					this.actionForm.getDutyRank(),
					this.actionForm.getTrade(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取教师平台的可选择的学员分页列表
	 */
	public String getUserListByManagerSelect() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getUserFactory().findListByManagerSelect(
					this.actionForm.getActualOgan(),
					this.actionForm.getActualName(),
					this.actionForm.getDutyRank(),
					this.actionForm.getTrade(),
					this.actionForm.getOrganId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建学员记录
	 */
	public String createUser() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("学员数据", this.actionForm.getUser()).noNull();
			ActionValidator.create("帐号", this.actionForm.getUser().getName()).noNull();
			ActionValidator.create("姓名", this.actionForm.getUser().getActualName()).noNull();
			ActionValidator.create("职务级别", this.actionForm.getUser().getDutyRank()).noNull();
			ActionValidator.create("状态", this.actionForm.getUser().getStatus()).noNull();
			 
			this.serviceFacade.getUserService().createUser(
					this.actionForm.getUser().getName(),
					this.actionForm.getUser().getActualName(),
					this.actionForm.getUser().getActualOrgan(),
					this.actionForm.getUser().getDutyRank(),
					this.actionForm.getUser().getTrade(),
					this.actionForm.getUser().getMobile(),
					this.actionForm.getUser().getPassword(),
					UserStatusEnum.valueOf(this.actionForm.getUser().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建学员记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改学员记录
	 */
	public String modifyUser() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("学员数据", this.actionForm.getUser()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUser().getId()).noNull();
			ActionValidator.create("姓名", this.actionForm.getUser().getActualName()).noNull();
			 
			this.serviceFacade.getUserService().modifyUser(
					this.actionForm.getUser().getId(),
					this.actionForm.getUser().getActualName(),
					this.actionForm.getUser().getDutyRank(),
					this.actionForm.getUser().getTrade(),
					this.actionForm.getUser().getMobile(),
					this.actionForm.getUser().getPassword(),
					UserStatusEnum.valueOf(this.actionForm.getUser().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改学员记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除学员记录
	 */
	public String removeUser() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("学员数据", this.actionForm.getUser()).noNull();
			ActionValidator.create("学员编号", this.actionForm.getUser().getId()).noNull();
			 
			this.serviceFacade.getUserService().removeUser(
					this.actionForm.getUser().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除学员记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改学员密码
	 */
	public String modifyPassword() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("旧密码", this.actionForm.getOldPassword()).noNull();
			ActionValidator.create("新密码", this.actionForm.getNewPassword()).noNull();
			 
			this.serviceFacade.getUserService().modifyPassword(
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
	 * 学员登录
	 */
	public String login() {
		
		try {
			ActionValidator.create("帐号", this.actionForm.getName()).noNull();
			ActionValidator.create("密码", this.actionForm.getPassword()).noNull();
			 
			User user = this.serviceFacade.getUserService().login(
					this.actionForm.getName(),
					this.actionForm.getPassword());
			
			SessionLoginResult sessionLoginResult = SessionLoginResult.createUserLoginResult(user.getId(), user.getName(), user.getActualName());
			this.setSessionLoginResult(sessionLoginResult);
			
			this.setJsonResult_ActionResult(true, "您已成功登录学员平台！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入学员
	 */
	public String importUserList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			this.serviceFacade.getUserService().importUserList(this.getSessionLoginResult().getId(), this.actionForm.getFile());

			this.setJsonResult_ActionResult(true, "您已成功导入学员列表！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出学员列表
	 */
	public String exportUserList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);

			Organ organ = this.modelFactoryFacade.getOrganFactory().findById(this.getSessionLoginResult().getId());
			String fileName = "学员列表【" + organ.getActualName() + "】.xls";
			
			InputStream is = this.serviceFacade.getUserService().exportUserList(
					this.getSessionLoginResult().getId());

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 导出学员模板
	 */
	public String exportUserExcelTemplet() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			String fileName = "导入学员的模板.xls";
			
			InputStream is = this.serviceFacade.getUserService().exportUserExcelTemplet();

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 获取学员平台用户摘要
	 * @return
	 */
	public String getUSUserSummary() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			USUserSummary usUserSummary = this.serviceFacade.getUserService().getUSUserSummary(this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(usUserSummary);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public UserActionForm getModel() {
		return actionForm;
	}

}
