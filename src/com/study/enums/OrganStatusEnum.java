package com.study.enums;

import java.util.TreeMap;

/**
 * 单位状态枚举
 */
public enum OrganStatusEnum {

	/**
	 * 启用
	 */
	Opened, 
	/**
	 * 停用
	 */
	Closed;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(Opened)) { return "已启用"; }
		if (this.equals(Closed)) { return "已停用"; }
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
	public static OrganStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return OrganStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< OrganStatusEnum.values().length; i++ ) {
			map.put(OrganStatusEnum.values()[i].ordinal(), OrganStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
