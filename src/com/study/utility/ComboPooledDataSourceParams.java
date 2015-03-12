package com.study.utility;

/**
 * 数据源参数集
 */
public class ComboPooledDataSourceParams {

	/**
	 * 驱动类 
	 */
	private String driverClass;
	
	/**
	 * 数据库链接
	 */
	private String jdbcUrl;
	
	/**
	 * 数据库用户 
	 */
	private String user;
	
	/**
	 * 数据库密码
	 */
	private String password;

	private void init() {

		this.driverClass = "org.gjt.mm.mysql.Driver";
		this.jdbcUrl = "jdbc:mysql://127.0.0.1:3306/study?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
		this.user = "root";
		this.password = "ok";
	}
	
	public ComboPooledDataSourceParams() {
	
		init();
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
