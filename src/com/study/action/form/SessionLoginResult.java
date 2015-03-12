package com.study.action.form;

/**
 * 登录结果
 */
public class SessionLoginResult {

	/**
	 * 登录类型 
	 */
	private SessionLoginTypeEnum loginType;
	
	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 创建管理员登录结果
	 * @param id 管理员编号
	 * @param name 管理员名称
	 * @param actualName 姓名
	 * @return 登录结果
	 */
	public static SessionLoginResult createManagerLoginResult(Long id, String name, String actualName) {
		
		SessionLoginResult result = new SessionLoginResult();
		
		result.setLoginType(SessionLoginTypeEnum.Manager);
		result.setId(id);
		result.setName(name);
		result.setActualName(actualName);
		
		return result;
	}
	
	/**
	 * 创建单位登录结果
	 * @param id 单位编号
	 * @param name 单位名称
	 * @return 登录结果
	 */
	public static SessionLoginResult createOrganLoginResult(Long id, String name, String actualName) {
		
		SessionLoginResult result = new SessionLoginResult();
		
		result.setLoginType(SessionLoginTypeEnum.Organ);
		result.setId(id);
		result.setName(name);
		result.setActualName(actualName);
		
		return result;
	}
	
	/**
	 * 创建学员登录结果
	 * @param id 学员编号
	 * @param name 学员名称
	 * @param actualName 姓名
	 * @return 登录结果
	 */
	public static SessionLoginResult createUserLoginResult(Long id, String name, String actualName) {
		
		SessionLoginResult result = new SessionLoginResult();
		
		result.setLoginType(SessionLoginTypeEnum.User);
		result.setId(id);
		result.setName(name);
		result.setActualName(actualName);
		
		return result;
	}
	
	public void setLoginType(SessionLoginTypeEnum loginType) {
		this.loginType = loginType;
	}

	public SessionLoginTypeEnum getLoginType() {
		return loginType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}
	
}
