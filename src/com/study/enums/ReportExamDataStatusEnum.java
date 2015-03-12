package com.study.enums;

import java.util.TreeMap;

/**
 * 考试结果状态枚举
 */
public enum ReportExamDataStatusEnum {
	
	/**
	 * 待考
	 */
	None, 
	/**
	 * 缺考
	 */
	Leave,
	/**
	 * 参考
	 */
	Examed,
	/**
	 * 通过 
	 */
	Pass,
	/**
	 * 未通过
	 */
	NoPass;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(None)) { return "待考"; }
		if (this.equals(Leave)) { return "缺考"; }
		if (this.equals(Examed)) { return "已参加考试"; }
		if (this.equals(Pass)) { return "考试通过 "; }
		if (this.equals(NoPass)) { return "考试未通过 "; }

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
	public static ReportExamDataStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ReportExamDataStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ReportExamDataStatusEnum.values().length; i++ ) {
			map.put(ReportExamDataStatusEnum.values()[i].ordinal(), ReportExamDataStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
