package com.study.action.form;

import com.study.model.User;



/**
 * 学员 ActionForm
 */
public class UserActionForm extends BaseActionForm {

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
	 * 学员数据
	 */
	private User user;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 所属单位
	 */
	private String actualOgan;
	
	/**
	 * 职务级别
	 */
	private String dutyRank;
	
	/**
	 * 行业
	 */
	private String trade;
	
	/**
	 * 单位编号
	 */
	private Long organId;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public void setActualOgan(String actualOgan) {
		this.actualOgan = actualOgan;
	}

	public String getActualOgan() {
		return actualOgan;
	}

	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}

	public String getDutyRank() {
		return dutyRank;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getTrade() {
		return trade;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getOrganId() {
		return organId;
	}
	
}
