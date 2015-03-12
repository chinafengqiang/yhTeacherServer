package com.study.enums;

import java.util.TreeMap;

/**
 * 题目选项排序方式
 */
public enum QuestionOptionsSortTypeEnum {

	/**
	 * 固定顺序
	 */
	Fixed,
	/**
	 * 随机顺序 
	 */
	Random;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(Fixed)) { return "固定顺序"; }
		if (this.equals(Random)) { return "随机顺序"; }
		return "";
	}

	/**
	 * 获取枚举类型数值
	 */
	public Integer toValue() {
	
		return this.ordinal();
	}
	
	/**
	 * 获取枚举类型变量名称
	 */
	public String toString() {

		if (this.equals(Fixed)) { return "Fixed"; }
		if (this.equals(Random)) { return "Random"; }
		return "";
	}
	
	/**
	 * 按数值获取对应的枚举类型
	 * @param value 数值
	 * @return 枚举类型
	 */
	public static QuestionOptionsSortTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return QuestionOptionsSortTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< QuestionOptionsSortTypeEnum.values().length; i++ ) {
			map.put(QuestionOptionsSortTypeEnum.values()[i].ordinal(), QuestionOptionsSortTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
