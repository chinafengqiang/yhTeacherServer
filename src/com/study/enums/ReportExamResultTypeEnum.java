package com.study.enums;

import java.util.TreeMap;

/**
 * 考试报表类型状态枚举
 */
public enum ReportExamResultTypeEnum {
	
	/**
	 * 本单位汇总数据
	 */
	Self, 
	/**
	 * 本单位及所有下级单位汇总数据
	 */
	All;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Self)) { return "本单位汇总数据"; }
		if (this.equals(All)) { return "本单位及所有下级单位汇总数据"; }

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
	public static ReportExamResultTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ReportExamResultTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ReportExamResultTypeEnum.values().length; i++ ) {
			map.put(ReportExamResultTypeEnum.values()[i].ordinal(), ReportExamResultTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
