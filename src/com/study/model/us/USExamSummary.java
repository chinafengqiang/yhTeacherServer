package com.study.model.us;



public class USExamSummary {

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
	 * 考试时间限制
	 */
	private String examTimeLimit;
	
	/**
	 * 是否需要完成必修课
	 */
	private String courseStudyLimit;
	
	/**
	 * 总分
	 */
	private Integer totalScore;
	
	/**
	 * 通过分数
	 */
	private Integer passScore;
	
	/**
	 * 考试时长 
	 */
	private Integer timerLimit;
	
	/**
	 * 职务级别
	 */
	private String dutyRank;
	
	/**
	 * 行业
	 */
	private String trade;
	
	/**
	 * 考试状态名称
	 */
	private String examStatusName;
	
	/**
	 * 学员考试状态名称
	 */
	private String userStatusName;
	
	/**
	 * 分数
	 */
	private String scoreName;
	
	/**
	 * 考试服务器链接
	 */
	private String urlExamServer;
	
	/**
	 * 是否显示进入直通车的链接
	 */
	private String canShowExamServer;

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

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}

	public Integer getTimerLimit() {
		return timerLimit;
	}

	public void setTimerLimit(Integer timerLimit) {
		this.timerLimit = timerLimit;
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

	public String getScoreName() {
		return scoreName;
	}

	public void setScoreName(String scoreName) {
		this.scoreName = scoreName;
	}

	public String getUrlExamServer() {
		return urlExamServer;
	}

	public void setUrlExamServer(String urlExamServer) {
		this.urlExamServer = urlExamServer;
	}

	public void setExamStatusName(String examStatusName) {
		this.examStatusName = examStatusName;
	}

	public String getExamStatusName() {
		return examStatusName;
	}

	public void setExamTimeLimit(String examTimeLimit) {
		this.examTimeLimit = examTimeLimit;
	}

	public String getExamTimeLimit() {
		return examTimeLimit;
	}

	public void setCourseStudyLimit(String courseStudyLimit) {
		this.courseStudyLimit = courseStudyLimit;
	}

	public String getCourseStudyLimit() {
		return courseStudyLimit;
	}

	public void setCanShowExamServer(String canShowExamServer) {
		this.canShowExamServer = canShowExamServer;
	}

	public String getCanShowExamServer() {
		return canShowExamServer;
	}

}
