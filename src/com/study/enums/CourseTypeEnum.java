package com.study.enums;

import java.util.TreeMap;

/**
 * 课程类型枚举
 */
public enum CourseTypeEnum {
	
	/**
	 * 必修课
	 */
	Required,
	/**
	 * 选修课
	 */
	Elective;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Required)) { return "必修课"; }
		if (this.equals(Elective)) { return "选修课"; }

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
	public static CourseTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return CourseTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< CourseTypeEnum.values().length; i++ ) {
			map.put(CourseTypeEnum.values()[i].ordinal(), CourseTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
