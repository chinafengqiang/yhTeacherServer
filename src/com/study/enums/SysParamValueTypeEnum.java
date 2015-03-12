package com.study.enums;

import java.util.TreeMap;

/**
 * 系统参数值类型枚举
 */
public enum SysParamValueTypeEnum {
	
	/**
	 * 字符串
	 */
	StringValue,
	/**
	 * 字符串
	 */
	StringList,
	/**
	 * 数值
	 */
	IntegerValue,
	/**
	 * 小数
	 */
	DoubleValue,
	/**
	 * 日期
	 */
	DateValue;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(StringValue)) { return "字符串"; }
		if (this.equals(StringList)) { return "字符串列表"; }
		if (this.equals(IntegerValue)) { return "数值"; }
		if (this.equals(DoubleValue)) { return "小数"; }
		if (this.equals(DateValue)) { return "日期"; }

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
	public static SysParamValueTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return SysParamValueTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< SysParamValueTypeEnum.values().length; i++ ) {
			map.put(SysParamValueTypeEnum.values()[i].ordinal(), SysParamValueTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
