package com.study.enums;

import java.util.TreeMap;

/**
 * 考生考卷数据状态枚举
 */
public enum ExamUserQuestionStatusEnum {
	
	/**
	 * 答题正确
	 */
	Right,
	/**
	 * 答题错误
	 */
	Error;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Right)) { return "答题正确"; }
		if (this.equals(Error)) { return "答题错误"; }

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
	public static ExamUserQuestionStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ExamUserQuestionStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ExamUserQuestionStatusEnum.values().length; i++ ) {
			map.put(ExamUserQuestionStatusEnum.values()[i].ordinal(), ExamUserQuestionStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
