package com.study.utility;

import java.util.ResourceBundle;

public class ReadConfig {

	private static ResourceBundle res=null;
	
	public static String getString(String key){
		if(null==res){
			res=ResourceBundle.getBundle("config");
		}
		return res.getString(key);
	}
	
}
