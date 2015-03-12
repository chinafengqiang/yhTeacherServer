package com.study.enums;

import java.util.TreeMap;

/**
 * 考试状态
 */
public enum ExamUserStatusEnum {

	/**
	 * 未考
	 */
	None, 
	/**
	 * 在考
	 */
	Doing, 
	/**
	 * 已考
	 */
	Done;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(None)) { return "未考"; }
		if (this.equals(Doing)) { return "在考"; }
		if (this.equals(Done)) { return "已考"; }
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
	public static ExamUserStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return ExamUserStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ExamUserStatusEnum.values().length; i++ ) {
			map.put(ExamUserStatusEnum.values()[i].ordinal(), ExamUserStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
