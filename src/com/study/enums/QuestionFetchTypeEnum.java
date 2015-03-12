package com.study.enums;

import java.util.TreeMap;

/**
 * 题目提取方式类型枚举
 */
public enum QuestionFetchTypeEnum {
	
	/**
	 * 固定套数
	 */
	Fixed,
	/**
	 * 随机套数
	 */
	Random;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Fixed)) { return "固定套数"; }
		if (this.equals(Random)) { return "随机套数 "; }

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
	public static QuestionFetchTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return QuestionFetchTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< QuestionFetchTypeEnum.values().length; i++ ) {
			map.put(QuestionFetchTypeEnum.values()[i].ordinal(), QuestionFetchTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
