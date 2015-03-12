package com.study.action.menu;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.BaseActionSupport;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.menu.form.OSActionForm;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ServiceFacade;

/**
 * 资讯业务操作类
 */
public class OSAction extends BaseActionSupport implements ModelDriven<OSActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(OSAction.class);
	
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
	private OSActionForm actionForm = new OSActionForm();

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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 单位
	 */
	public String organ() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	/**
	 * 学员
	 */
	public String user() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
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
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	
	@Override
	public OSActionForm getModel() {
		return actionForm;
	}

}
