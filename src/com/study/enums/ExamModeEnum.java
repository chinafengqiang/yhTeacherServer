package com.study.enums;

import java.util.TreeMap;

/**
 * 考试状态
 */
public enum ExamModeEnum {

	/**
	 * 集中考试
	 */
	Together, 
	/**
	 * 分散考试
	 */
	Freedom;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(Together)) { return "集中考试"; }
		if (this.equals(Freedom)) { return "分散考试"; }
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
	public static ExamModeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return ExamModeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ExamModeEnum.values().length; i++ ) {
			map.put(ExamModeEnum.values()[i].ordinal(), ExamModeEnum.values()[i].toName());
		}
		return map;
	}		
}
