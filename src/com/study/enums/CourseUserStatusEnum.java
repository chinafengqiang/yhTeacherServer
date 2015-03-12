package com.study.enums;

import java.util.TreeMap;

/**
 * 课程学员状态枚举
 */
public enum CourseUserStatusEnum {
	
	/**
	 * 未学
	 */
	None,
	/**
	 * 在学
	 */
	Doing,
	/**
	 * 已学
	 */
	Done;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(None)) { return "未学"; }
		if (this.equals(Doing)) { return "在学"; }
		if (this.equals(Done)) { return "已学"; }

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
	public static CourseUserStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return CourseUserStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< CourseUserStatusEnum.values().length; i++ ) {
			map.put(CourseUserStatusEnum.values()[i].ordinal(), CourseUserStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
