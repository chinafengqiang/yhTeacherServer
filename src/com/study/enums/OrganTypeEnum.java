package com.study.enums;

import java.util.TreeMap;

/**
 * 单位类型枚举
 */
public enum OrganTypeEnum {
	
	/**
	 * 法宣单位
	 */
	Master,
	/**
	 * 对口单位
	 */
	Client;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Master)) { return "法宣单位"; }
		if (this.equals(Client)) { return "对口单位"; }

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
	public static OrganTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return OrganTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< OrganTypeEnum.values().length; i++ ) {
			map.put(OrganTypeEnum.values()[i].ordinal(), OrganTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
