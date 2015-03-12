package com.study.enums;

import java.util.TreeMap;

/**
 * 试卷题目显示类型
 */
public enum QuestionShowTypeEnum {

	/**
	 * 显示所有题目 
	 */
	All,
	/**
	 * 每屏显示一种题型 
	 */
	OneType,
	/**
	 * 每屏显示一道题 
	 */
	One; 

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(All)) { return "显示所有题目"; }
		if (this.equals(OneType)) { return "每屏显示一种题型"; }
		if (this.equals(One)) { return "每屏显示一道题"; }
		return "";
	}
	
	/**
	 * 获取枚举名
	 * @return
	 */
	public String toString() {
		
		if (this.equals(One)) { return "One"; }
		if (this.equals(OneType)) { return "OneType"; }
		if (this.equals(All)) { return "All"; }
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
	public static QuestionShowTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		return QuestionShowTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< QuestionShowTypeEnum.values().length; i++ ) {
			map.put(QuestionShowTypeEnum.values()[i].ordinal(), QuestionShowTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
