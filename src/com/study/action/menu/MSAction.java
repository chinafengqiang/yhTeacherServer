package com.study.action.menu;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.BaseActionSupport;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.menu.form.MSActionForm;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.service.SystemService;

/**
 * 资讯业务操作类
 */
public class MSAction extends BaseActionSupport implements ModelDriven<MSActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(MSAction.class);
	
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
	private MSActionForm actionForm = new MSActionForm();

	/**
	 * 教师业务接口 
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 登录
	 */
	public String login() {
		
		return SUCCESS;
	}
	
	/**
	 * 公告
	 */
	public String notice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Notice);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 资讯
	 */
	public String article() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Article);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 课程
	 */
	public String course() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Course);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 修改密码
	 */
	public String modifyPassword() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 题目
	 */
	public String question() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Question);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 试卷
	 */
	public String testPaper() {
		
		return SUCCESS;
		
//		try {
//			//this.validateLoginType(SessionLoginTypeEnum.Manager);
//			
////			this.managerService.validateSysAccess(
////					this.getSessionLoginResult().getId(), 
////					SysAccessTypeEnum.TestPaper);
//
//			return SUCCESS;
//		} catch (Exception e) {
//			return ERROR;
//		}
	}
	
	/**
	 * 预览试卷
	 */
	public String testPaperBrowse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.TestPaper);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 考试
	 */
	public String exam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Exam);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 查看答卷
	 */
	public String userTestPaperBrowse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Exam);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}

	/**
	 * 考试服务器
	 */
	public String examServer() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.ExamServer);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 考试报表
	 */
	public String reportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.ReportExam);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 教师管理
	 */
	public String manager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.Manager);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 系统设置
	 */
	public String sysParam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			this.managerService.validateSysAccess(
					this.getSessionLoginResult().getId(), 
					SysAccessTypeEnum.SysParam);

			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	@Override
	public MSActionForm getModel() {
		return actionForm;
	}

}
