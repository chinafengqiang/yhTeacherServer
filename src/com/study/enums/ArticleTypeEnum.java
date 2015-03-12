package com.study.enums;

import java.util.TreeMap;

/**
 * 文章类型枚举
 */
public enum ArticleTypeEnum {
	
	/**
	 * 新闻
	 */
	News,
	/**
	 * 案例
	 */
	Case;
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(News)) { return "新闻"; }
		if (this.equals(Case)) { return "案例"; }

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
	public static ArticleTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return ArticleTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< ArticleTypeEnum.values().length; i++ ) {
			map.put(ArticleTypeEnum.values()[i].ordinal(), ArticleTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
