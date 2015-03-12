package com.study.action.form;

import com.study.model.Manager;

/**
 * 管理员 ActionForm
 */
public class ManagerActionForm extends BaseActionForm {
	
	/**
	 * 帐号
	 */
	private String name;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 旧密码
	 */
	private String oldPassword;
	
	/**
	 * 新密码
	 */
	private String newPassword;
	
	/**
	 * 教师数据
	 */
	private Manager manager;
	
	/**
	 * 系统权限类型
	 */
	private Integer sysAccessType;
	
	/**
	 * 级别
	 */
	private Integer grade;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Manager getManager() {
		return manager;
	}

	public void setSysAccessType(Integer sysAccessType) {
		this.sysAccessType = sysAccessType;
	}

	public Integer getSysAccessType() {
		return sysAccessType;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}
}
