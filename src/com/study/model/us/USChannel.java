package com.study.model.us;

import java.util.List;

/**
 * 学员平台频道
 */
public class USChannel {
	
	/**
	 * 显示区域
	 */
	private String area;

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 列表链接
	 */
	private String urlList;
	
	/**
	 * 链接列表 
	 */
	private List<USLink> linkList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlList() {
		return urlList;
	}

	public void setUrlList(String urlList) {
		this.urlList = urlList;
	}

	public List<USLink> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<USLink> linkList) {
		this.linkList = linkList;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}
	
}
