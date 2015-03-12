package com.study.action.form;

import com.study.model.Organ;

/**
 * 单位 ActionForm
 */
public class OrganActionForm extends BaseActionForm {
	
	/**
	 * 单位类型
	 */
	private Integer organType;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 上级编号
	 */
	private Long parentId;
	
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
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 单位数据
	 */
	private Organ organ;
	
	/**
	 * 单位激活码数据
	 */
	private String activateCodeData;
	
	/**
	 * 操作验证码
	 */
	private String actionPassword;

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

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrganType(Integer organType) {
		this.organType = organType;
	}

	public Integer getOrganType() {
		return organType;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setActivateCodeData(String activateCodeData) {
		this.activateCodeData = activateCodeData;
	}

	public String getActivateCodeData() {
		return activateCodeData;
	}

	public void setActionPassword(String actionPassword) {
		this.actionPassword = actionPassword;
	}

	public String getActionPassword() {
		return actionPassword;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}
	
}
