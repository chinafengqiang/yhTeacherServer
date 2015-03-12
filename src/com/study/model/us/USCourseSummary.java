package com.study.model.us;


public class USCourseSummary {

	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 封面图片链接
	 */
	private String coverImageLink;
	
	/**
	 * 学时
	 */
	private Integer classHour;
	
	/**
	 * 学分
	 */
	private Integer credit;
	
	/**
	 * 通过所需的最低学分
	 */
	private Integer passCreditLimit;
	
	/**
	 * 学习次数
	 */
	private Integer studyNum;
	
	/**
	 * 课程类型
	 */
	private String courseTypeName;
	
	/**
	 * 职务级别
	 */
	private String dutyRank;
	
	/**
	 * 行业
	 */
	private String trade;
	
	/**
	 * 学员学习状态名称
	 */
	private String userStatusName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverImageLink() {
		return coverImageLink;
	}

	public void setCoverImageLink(String coverImageLink) {
		this.coverImageLink = coverImageLink;
	}

	public Integer getClassHour() {
		return classHour;
	}

	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getPassCreditLimit() {
		return passCreditLimit;
	}

	public void setPassCreditLimit(Integer passCreditLimit) {
		this.passCreditLimit = passCreditLimit;
	}

	public Integer getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(Integer studyNum) {
		this.studyNum = studyNum;
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

	public String getUserStatusName() {
		return userStatusName;
	}

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public String getCourseTypeName() {
		return courseTypeName;
	}
	
	
}
