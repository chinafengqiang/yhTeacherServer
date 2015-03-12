package com.study.action.form;

import java.util.TreeMap;

/**
 * 菜谱状态枚举
 */
public enum SessionLoginTypeEnum {
	
	/**
	 * 教师
	 */
	Manager,
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

		if (this.equals(Manager)) { return "教师"; }
		if (this.equals(Organ)) { return "单位"; }
		if (this.equals(User)) { return "学员"; }

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
	public static SessionLoginTypeEnum valueOf(Integer value) {
		
		return SessionLoginTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< SessionLoginTypeEnum.values().length; i++ ) {
			map.put(SessionLoginTypeEnum.values()[i].ordinal(), SessionLoginTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
