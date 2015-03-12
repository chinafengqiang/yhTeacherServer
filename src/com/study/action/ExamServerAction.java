package com.study.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.examserver.model.EsExamSummary;
import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ExamServerActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ExamServerStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.ExamServer;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 考试服务器业务操作类
 */
public class ExamServerAction extends BaseActionSupport implements ModelDriven<ExamServerActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ExamServerAction.class);
	
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
	private ExamServerActionForm actionForm = new ExamServerActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取考试服务器数据
	 */
	public String getExamServer() {
		
		try {
			ActionValidator.create("考试服务器编号", this.actionForm.getExamServer().getId()).noNull();
			
			ExamServer examServer = this.modelFactoryFacade.getExamServerFactory().findById(this.actionForm.getExamServer().getId());
			
			this.setJsonResult_ActionResult(examServer);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取考试服务器分页列表
	 */
	public String getExamServerListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getExamServerFactory().findListBySearch(
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取考试在各个服务器上的摘要
	 */
	public String getExamSummary() {
		
		try {
			ActionValidator.create("考试编号", this.actionForm.getExamId()).noNull();
			
			List<EsExamSummary> list = this.serviceFacade.getExamServerService().getExamSummary( this.actionForm.getExamId());
			
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取考试服务器上的所有考试摘要
	 */
	public String getExamServerSummary() {
		
		try {
			ActionValidator.create("考试服务器编号", this.actionForm.getExamServer().getId()).noNull();
			
			List<EsExamSummary> list = this.serviceFacade.getExamServerService().getExamServerSummary(this.actionForm.getExamServer().getId());
			
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}

	/**
	 * 新建考试服务器记录
	 */
	public String createExamServer() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ExamServer);
			
			ActionValidator.create("考试服务器数据", this.actionForm.getExamServer()).noNull();
			ActionValidator.create("名称", this.actionForm.getExamServer().getName()).noNull();
			ActionValidator.create("服务器地址", this.actionForm.getExamServer().getUrl()).noNull();
			ActionValidator.create("服务器内网地址", this.actionForm.getExamServer().getDirectUrl()).noNull();
			 
			this.serviceFacade.getExamServerService().createExamServer(
					this.actionForm.getExamServer().getName(),
					this.actionForm.getExamServer().getUrl(),
					this.actionForm.getExamServer().getDirectUrl(),
					this.actionForm.getExamServer().getNote(),
					ExamServerStatusEnum.valueOf(this.actionForm.getExamServer().getStatus()));
			
			this.setJsonResult_ActionResult(true, "您已成功新建考试服务器记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改考试服务器记录
	 */
	public String modifyExamServer() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ExamServer);
			
			ActionValidator.create("考试服务器数据", this.actionForm.getExamServer()).noNull();
			ActionValidator.create("考试服务器编号", this.actionForm.getExamServer().getId()).noNull();
			ActionValidator.create("名称", this.actionForm.getExamServer().getName()).noNull();
			ActionValidator.create("服务器地址", this.actionForm.getExamServer().getUrl()).noNull();
			ActionValidator.create("服务器内网地址", this.actionForm.getExamServer().getDirectUrl()).noNull();
			
			this.serviceFacade.getExamServerService().modifyExamServer(
					this.actionForm.getExamServer().getId(),
					this.actionForm.getExamServer().getName(),
					this.actionForm.getExamServer().getUrl(),
					this.actionForm.getExamServer().getDirectUrl(),
					this.actionForm.getExamServer().getNote(),
					ExamServerStatusEnum.valueOf(this.actionForm.getExamServer().getStatus()));
			
			this.setJsonResult_ActionResult(true, "您已成功修改考试服务器记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除考试服务器记录
	 */
	public String removeExamServer() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ExamServer);
			
			ActionValidator.create("考试服务器数据", this.actionForm.getExamServer()).noNull();
			ActionValidator.create("考试服务器编号", this.actionForm.getExamServer().getId()).noNull();
			 
			this.serviceFacade.getExamServerService().removeExamServer(
					this.actionForm.getExamServer().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考试服务器记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public ExamServerActionForm getModel() {
		return actionForm;
	}

}
