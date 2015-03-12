package com.study.enums;

import java.util.TreeMap;

/**
 * 公告级别类型枚举
 */
public enum NoticeGradeEnum {
	
	/**
	 * 单位
	 */
	Organ,
	/**
	 * 学员
	 */
	User;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Organ)) { return "单位公告"; }
		if (this.equals(User)) { return "学员公告"; }

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
	public static NoticeGradeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return NoticeGradeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< NoticeGradeEnum.values().length; i++ ) {
			map.put(NoticeGradeEnum.values()[i].ordinal(), NoticeGradeEnum.values()[i].toName());
		}
		return map;
	}		
}
