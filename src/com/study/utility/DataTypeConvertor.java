package com.study.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据类型转换器
 * @author 沈志辉
 */
public class DataTypeConvertor {

	/**
	 * boolean转换成数值
	 * @param value
	 * @return
	 */
	public static Integer booleanToInteger(Boolean value) {
		
		if (value==true) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 数值转换成boolean
	 * @param value
	 * @return
	 */
	public static boolean IntegerToBoolean(Integer value) {
		
		if (value.intValue()==1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 将字符串转换为日期
	 * @param date 日期
	 * @param formatPattern 日期格式类型字符串
	 * @return 日期
	 */
	public static Date stringToDate(String date, String formatPattern){   
		SimpleDateFormat sdf = new SimpleDateFormat();   
		try{   
			if((formatPattern==null)||formatPattern.equals("")){   
				formatPattern = "yyyy-MM-dd HH:mm:ss";   
			}   
			sdf.applyPattern(formatPattern);   
				return   sdf.parse(date);   
		}catch(Exception e){   
			e.printStackTrace();   
			return   null;   
		}   
	}
	
	/**
	 * 将日期转换为字符串
	 * @param date 日期
	 * @param shortType 是否短格式
	 * @return 日期字符串
	 */
	public static String dateToString(Date date, boolean shortType) {
		if(date!=null){
			if (shortType)
				return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
			else
				return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(date);
		}else{
			return "";
		}
	}
	
}
