package com.study.enums;

import java.util.TreeMap;

/**
 * 考试计时方式
 */
public enum TimerModeEnum {

	/**
	 * 考试不计时 
	 */
	None, 
	/**
	 * 考试计时 
	 */
	Limit;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(None)) { return "考试不计时"; }
		if (this.equals(Limit)) { return "考试计时"; }
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
	public static TimerModeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return TimerModeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< TimerModeEnum.values().length; i++ ) {
			map.put(TimerModeEnum.values()[i].ordinal(), TimerModeEnum.values()[i].toName());
		}
		return map;
	}		
}
