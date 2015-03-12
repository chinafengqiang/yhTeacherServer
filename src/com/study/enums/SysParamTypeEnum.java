package com.study.enums;

import java.util.TreeMap;

/**
 * 系统参数类型枚举
 */
public enum SysParamTypeEnum {
	
	/**
	 * 站点名称
	 */
	SiteName,
	/**
	 * 考试分类
	 */
	ExamCategory,
	/**
	 * 试卷分类
	 */
	TestPaperCategory,
	/**
	 * 职务级别
	 */
	DutyRank,
	/**
	 * 行业
	 */
	Trade,
	/**
	 * 新闻频道
	 */
	NewsChannel,
	/**
	 * 案例频道
	 */
	CaseChannel,
	/**
	 * 默认教师帐号密码
	 */
	DefaultManagerPassword,
	/**
	 * 默认单位帐号密码
	 */
	DefaultOrganPassword,
	/**
	 * 默认学员帐号密码
	 */
	DefaultUserPassword,
	/**
	 * 是否拥有子站点
	 */
	HaveSubSite,
	/**
	 * 子站点名称
	 */
	SubSiteName,
	/**
	 * 子站点地址
	 */
	SubSiteUrl,
	/**
	 * 子站点单位编号
	 */
	SubSiteOrganId,
	/**
	 * 友情站点名称
	 */
	FriendSiteName,
	/**
	 * 友情站点地址
	 */
	FriendSiteUrl,
	/**
	 * 单位所属地区
	 */
	OrganArea;	
	
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(SiteName)) { return "站点"; }
		if (this.equals(ExamCategory)) { return "考试分类"; }
		if (this.equals(TestPaperCategory)) { return "试卷分类"; }
		if (this.equals(DutyRank)) { return "职务级别"; }
		if (this.equals(Trade)) { return "行业"; }
		if (this.equals(NewsChannel)) { return "新闻频道"; }
		if (this.equals(CaseChannel)) { return "案例频道"; }
		if (this.equals(DefaultManagerPassword)) { return "默认教师帐号密码"; }
		if (this.equals(DefaultOrganPassword)) { return "默认单位帐号密码"; }
		if (this.equals(DefaultUserPassword)) { return "默认学员帐号密码"; }
		if (this.equals(HaveSubSite)) { return "是否拥有子站点"; }
		if (this.equals(SubSiteName)) { return "子站点名称"; }
		if (this.equals(SubSiteUrl)) { return "子站点地址"; }
		if (this.equals(SubSiteOrganId)) { return "子站点单位编号"; }		
		if (this.equals(FriendSiteName)) { return "友情站点名称"; }
		if (this.equals(FriendSiteUrl)) { return "友情站点地址"; }
		if (this.equals(OrganArea)) { return "单位所属地区"; }

		return "";
	}
	
	/**
	 * 获取枚举类型数值
	 */
	public Integer toValue() {
	
		return this.ordinal();
	}
	
	/**
	 * 按枚举内名称获取枚举
	 * @param name 名称 
	 * @return 枚举
	 */
	public static SysParamTypeEnum nameOf(String name) {
		
		for (SysParamTypeEnum e : SysParamTypeEnum.values()) {
			if (e.toString().equals(name)) {
				return e;
			}
		}
		
		return null;
	}
	
	/**
	 * 按数值获取对应的枚举类型
	 * @param value 数值
	 * @return 枚举类型
	 */
	public static SysParamTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}
		
		return SysParamTypeEnum.values()[value];
	}
	 
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< SysParamTypeEnum.values().length; i++ ) {
			map.put(SysParamTypeEnum.values()[i].ordinal(), SysParamTypeEnum.values()[i].toName());
		}
		return map;
	}		
}
