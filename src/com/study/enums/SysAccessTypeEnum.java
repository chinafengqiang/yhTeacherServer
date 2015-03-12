package com.study.enums;

import java.util.TreeMap;

/**
 * 系统权限类型枚举
 */
public enum SysAccessTypeEnum {
	
	/**
	 * 公告管理
	 */
	Notice,
	/**
	 * 资讯管理
	 */
	Article,
	/**
	 * 课程管理
	 */
	Course,
	/**
	 * 题库管理
	 */
	Question,
	/**
	 * 试卷管理
	 */
	TestPaper,
	/**
	 * 考试管理
	 */
	Exam,
	/**
	 * 考试报表
	 */
	ReportExam,
	/**
	 * 考试服务器
	 */
	ExamServer,
	/**
	 * 教师管理
	 */
	Manager,	
	/**
	 * 系统设置
	 */
	SysParam,
	/**
	 * 考试监控
	 */
	ExamMonitor;	
		
	/**
	 * 获取枚举类型名称
	 */
	public String toName() {

		if (this.equals(Notice)) { return "公告管理"; }
		if (this.equals(Article)) { return "资讯管理"; }
		if (this.equals(Course)) { return "课程管理"; }
		if (this.equals(Question)) { return "题库管理"; }
		if (this.equals(TestPaper)) { return "试卷管理"; }
		if (this.equals(Exam)) { return "考试管理"; }
		if (this.equals(ReportExam)) { return "考试报表"; }
		if (this.equals(ExamServer)) { return "考试服务器"; }
		if (this.equals(Manager)) { return "教师管理"; }
		if (this.equals(SysParam)) { return "系统设置"; }
		if (this.equals(ExamMonitor)) { return "考试监控"; }

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
	public static SysAccessTypeEnum valueOf(Integer value) {
		
		if (value == null) {
			return null;
		}

		return SysAccessTypeEnum.values()[value];
	}
	
	/**
	 * 获取枚举类型的所有<值,名称>对
	 * @return
	 */
	public static TreeMap<Integer, String> toMap(){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		for (int i=0; i< SysAccessTypeEnum.values().length; i++ ) {
			map.put(SysAccessTypeEnum.values()[i].ordinal(), SysAccessTypeEnum.values()[i].toName());
		}
		return map;
	}
	
	/**
	 * 获取值列表(用分号隔开)
	 * @return
	 */
	public static String getValueList() {
		
		String rt = "";
		for (int i=0; i< SysAccessTypeEnum.values().length; i++ ) {
			rt = rt + i + ";";
		}
		
		return rt;
	}
}
