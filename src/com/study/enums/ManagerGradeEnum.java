package com.study.enums;

import java.util.TreeMap;

/**
 * 教师级别类型枚举
 */
public enum ManagerGradeEnum {
	
	/**
	 * 高级教师
	 */
	High,
	/**
	 * 普通教师
	 */
	Normal;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(High)) { return "高级教师"; }
		if (this.equals(Normal)) { return "普通教师"; }

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
	public static ManagerGradeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ManagerGradeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ManagerGradeEnum.values().length; i++ ) {
			map.put(ManagerGradeEnum.values()[i].ordinal(), ManagerGradeEnum.values()[i].toName());
		}
		return map;
	}		
}
