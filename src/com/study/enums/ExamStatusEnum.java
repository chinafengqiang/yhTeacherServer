package com.study.enums;

import java.util.TreeMap;

/**
 * 考试状态
 */
public enum ExamStatusEnum {

	/**
	 * 已创建
	 */
	Created, 
	/**
	 * 已部署
	 */
	Deployed, 
	/**
	 * 已启动
	 */
	Opened, 
	/**
	 * 已停用
	 */
	Closed,
	/**
	 * 已汇总成绩
	 */
	Gathered,
	/**
	 * 已卸载部署
	 */
	Destroyed;

	/**
	 * 获取枚举类型名称
	 */
	public String toName() {
		
		if (this.equals(Created)) { return "已创建"; }
		if (this.equals(Deployed)) { return "已部署"; }
		if (this.equals(Opened)) { return "已启动"; }
		if (this.equals(Closed)) { return "已停止"; }
		if (this.equals(Gathered)) { return "已汇总成绩"; }
		if (this.equals(Destroyed)) { return "已卸载"; }
		
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
	public static ExamStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return ExamStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ExamStatusEnum.values().length; i++ ) {
			map.put(ExamStatusEnum.values()[i].ordinal(), ExamStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
