package com.study.model.part;

import java.util.TreeMap;

/**
 * 考生试卷状态
 * @author 沈志辉
 */
public enum ExamResultStatusEnum {

	/**
	 * 待考 0
	 */
	WAIT_TEST, 
	/**
	 * 缺考 1
	 */
	LEAVE_EXAM,
	/**
	 * 已考 2
	 */
	EXAMED;

	/**
	 * 获取枚举类型名称
	 */
	public String toString() {
		
		if (this.equals(WAIT_TEST)) { return "待考"; }
		if (this.equals(LEAVE_EXAM)) { return "缺考"; }
		if (this.equals(EXAMED)) { return "已考"; }
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
	public static ExamResultStatusEnum valueOf(Integer value) {
		
		return ExamResultStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ExamResultStatusEnum.values().length; i++ ) {
			map.put(ExamResultStatusEnum.values()[i].ordinal(), ExamResultStatusEnum.values()[i].toString());
		}
		return map;
	}		
}
