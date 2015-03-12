package com.study.utility;


/**
 * 版本信息
 * @author
 *
 */
public class ServerInfo {
	
	static Configuration rc = null;
	
	static{
			rc = new Configuration("config/server_info.properties");
	}
	
	/**服务地址*/
	public static final String SERVER_ADDRESS = rc.getValue("SERVER_ADDRESS");
	/**服务端口号*/
	public static final String SERVER_PORT = rc.getValue("SERVER_PORT");

}

