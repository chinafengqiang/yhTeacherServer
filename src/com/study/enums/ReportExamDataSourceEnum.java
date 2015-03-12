package com.study.enums;

import java.util.TreeMap;

/**
 * 考试结果来源枚举
 */
public enum ReportExamDataSourceEnum {
	
	/**
	 * 网站
	 */
	Site, 
	/**
	 * 软件
	 */
	Software;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Site)) { return "网站"; }
		if (this.equals(Software)) { return "软件"; }

		return "";
	}
	
	/**
	 * 获取枚举类型数值
	 */
	public Integer toValue() {
	
		return this.ordinal();
	}
	
	/**
	 * 按数值获取对应的枚举类型
	 * @param value 数值
	 * @return 枚举类型
	 */
	public static ReportExamDataSourceEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ReportExamDataSourceEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ReportExamDataSourceEnum.values().length; i++ ) {
			map.put(ReportExamDataSourceEnum.values()[i].ordinal(), ReportExamDataSourceEnum.values()[i].toName());
		}
		return map;
	}		
}
