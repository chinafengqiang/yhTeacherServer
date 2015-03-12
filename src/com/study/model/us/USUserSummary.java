package com.study.model.us;

public class USUserSummary {

	/**
	 * 学员帐号
	 */
	private String name;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 单位
	 */
	private String actualOrgan;
	
	/**
	 * 职务级别
	 */
	private String dutyRank;
	
	/**
	 * 行业 
	 */
	private String trade;
	
	/**
	 * 在学课程数量
	 */
	private Integer courseDoingNumber;
	
	/**
	 * 已学课程数量
	 */
	private Integer courseDoneNumber;
	
	/**
	 * 课程总学分
	 */
	private Integer courseCredit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getDutyRank() {
		return dutyRank;
	}

	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public void setCourseDoingNumber(Integer courseDoingNumber) {
		this.courseDoingNumber = courseDoingNumber;
	}

	public Integer getCourseDoingNumber() {
		return courseDoingNumber;
	}

	public void setCourseDoneNumber(Integer courseDoneNumber) {
		this.courseDoneNumber = courseDoneNumber;
	}

	public Integer getCourseDoneNumber() {
		return courseDoneNumber;
	}

	public void setCourseCredit(Integer courseCredit) {
		this.courseCredit = courseCredit;
	}

	public Integer getCourseCredit() {
		return courseCredit;
	}

}
