package com.study.enums;

import java.util.TreeMap;

/**
 * 文章状态枚举
 */
public enum ArticleStatusEnum {
	
	/**
	 * 已发布
	 */
	Opened,
	/**
	 * 已下架
	 */
	Closed;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Opened)) { return "已发布"; }
		if (this.equals(Closed)) { return "已下架"; }

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
	public static ArticleStatusEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ArticleStatusEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ArticleStatusEnum.values().length; i++ ) {
			map.put(ArticleStatusEnum.values()[i].ordinal(), ArticleStatusEnum.values()[i].toName());
		}
		return map;
	}		
}
