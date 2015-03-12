package com.examserver.model;

/**
 * 考试摘要
 */
public class EsExamSummary {

	/**
	 * 考试标示
	 */
	private String esExamKey;
	
	/**
	 * 考试名称
	 */
	private String examName;
	
	/**
	 * 考试服务器名称
	 */
	private String examServerName;
	
	/**
	 * 状态名称
	 */
	private String statusName;
	
	/**
	 * 考试人数
	 */
	private Integer totalNumber;
	
	/**
	 * 开考人数
	 */
	private Integer beginExamNumber;

	/**
	 * 考完人数
	 */
	private Integer endExamNumber;

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamServerName() {
		return examServerName;
	}

	public void setExamServerName(String examServerName) {
		this.examServerName = examServerName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getEndExamNumber() {
		return endExamNumber;
	}

	public void setEndExamNumber(Integer endExamNumber) {
		this.endExamNumber = endExamNumber;
	}

	public void setEsExamKey(String esExamKey) {
		this.esExamKey = esExamKey;
	}

	public String getEsExamKey() {
		return esExamKey;
	}

	public void setBeginExamNumber(Integer beginExamNumber) {
		this.beginExamNumber = beginExamNumber;
	}

	public Integer getBeginExamNumber() {
		return beginExamNumber;
	}
}
