package com.study.model.part;


/**
 * 单位激活码
 */
public class ActivateCode {

	/**
	 * 帐号
	 */
	private String site;
	
	/**
	 * 帐号
	 */
	private String name;
	
	/**
	 * 名称
	 */
	private String actualName;
	
	/**
	 * 学员人数限制
	 */
	private Integer userNumberLimit;

	/**
	 * 服务期限
	 */
	private String serviceTimeLimit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public Integer getUserNumberLimit() {
		return userNumberLimit;
	}

	public void setUserNumberLimit(Integer userNumberLimit) {
		this.userNumberLimit = userNumberLimit;
	}

	public String getServiceTimeLimit() {
		return serviceTimeLimit;
	}

	public void setServiceTimeLimit(String serviceTimeLimit) {
		this.serviceTimeLimit = serviceTimeLimit;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSite() {
		return site;
	}
	
	
}
