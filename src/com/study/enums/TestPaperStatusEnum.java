package com.study.enums;

import java.util.TreeMap;

/**
 * 试卷状态
 * @author 沈志辉
 */
public enum TestPaperStatusEnum {

	/**
	 * 已启用
	 */
	Opened, 
	/**
	 * 已停用
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
	public static TestPaperStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return TestPaperStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< TestPaperStatusEnum.values().length; i++ ) {
			map.put(TestPaperStatusEnum.values()[i].ordinal(), TestPaperStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
