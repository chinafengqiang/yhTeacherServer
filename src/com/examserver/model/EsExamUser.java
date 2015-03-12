package com.examserver.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ES考生数据类
 */
public class EsExamUser implements Serializable {

	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * ES考试唯一标示
	 */
	private String esExamKey;
	
	/**
	 * 学员唯一标示
	 */
	private String userKey;
	
	/**
	 * 学员帐号 
	 */
	private String userName;
	
	/**
	 * 考试服务器数据
	 */
	private String examServerData;
	
	/**
	 * 备用考试服务器数据
	 */
	private String backupExamServerData;
	
	/**
	 * 考卷套数Json链接
	 */
	private String testPaperSeriesJson;
	
	/**
	 * 所属单位名称
	 */
	private String actualOrgan;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 密码 
	 */
	private String password;
	
	/**
	 * 是否限制时间
	 */
	private Boolean canLimitValidTime;
	
	/**
	 * 进场时间
	 */
	private Date validFirstTime;
	
	/**
	 * 关闭考场时间
	 */
	private Date validLastTime;
	
	/**
	 * 开考延时 
	 */
	private Integer beginExamDelayedTime;
	
	/**
	 * 提交试卷延时 
	 */
	private Integer commitExamDelayedTime;
	
	/**
	 * 试卷套数
	 */
	private Integer series;
	
	/**
	 * 考试时长（秒数）
	 */
	private Integer totalTime;
	
	/**
	 * 考试剩余时间（秒数）
	 */
	private Integer leaveTime;

	/**
	 * 实际开考时间
	 */
	private Date beginTime;
	
	/**
	 * 实际结束时间
	 */
	private Date endTime;
	
	/**
	 * 分数
	 */
	private Double score;
	
	/**
	 * 状态
	 */
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEsExamKey() {
		return esExamKey;
	}

	public void setEsExamKey(String esExamKey) {
		this.esExamKey = esExamKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getCanLimitValidTime() {
		return canLimitValidTime;
	}

	public void setCanLimitValidTime(Boolean canLimitValidTime) {
		this.canLimitValidTime = canLimitValidTime;
	}

	public Date getValidFirstTime() {
		return validFirstTime;
	}

	public void setValidFirstTime(Date validFirstTime) {
		this.validFirstTime = validFirstTime;
	}

	public Date getValidLastTime() {
		return validLastTime;
	}

	public void setValidLastTime(Date validLastTime) {
		this.validLastTime = validLastTime;
	}

	public Integer getBeginExamDelayedTime() {
		return beginExamDelayedTime;
	}

	public void setBeginExamDelayedTime(Integer beginExamDelayedTime) {
		this.beginExamDelayedTime = beginExamDelayedTime;
	}

	public Integer getCommitExamDelayedTime() {
		return commitExamDelayedTime;
	}

	public void setCommitExamDelayedTime(Integer commitExamDelayedTime) {
		this.commitExamDelayedTime = commitExamDelayedTime;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setExamServerData(String examServerData) {
		this.examServerData = examServerData;
	}

	public String getExamServerData() {
		return examServerData;
	}

	public void setBackupExamServerData(String backupExamServerData) {
		this.backupExamServerData = backupExamServerData;
	}

	public String getBackupExamServerData() {
		return backupExamServerData;
	}

	public void setTestPaperSeriesJson(String testPaperSeriesJson) {
		this.testPaperSeriesJson = testPaperSeriesJson;
	}

	public String getTestPaperSeriesJson() {
		return testPaperSeriesJson;
	}

}
