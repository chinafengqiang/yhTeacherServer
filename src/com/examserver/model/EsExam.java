package com.examserver.model;

import java.io.Serializable;

/**
 * ES考试数据类
 */
public class EsExam implements Serializable{

	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 考试在考试服务器中的唯一标示 
	 */
	private String esExamKey;
	
	/**
	 * 主服务器数据
	 */
	private String mainServerData;
	
	/**
	 * 考试名称
	 */
	private String examName;
	
	/**
	 * 考试数据
	 */
	private String examData;
	
	/**
	 * 试卷数据
	 */
	private String testPaperData;
	
	/**
	 * 题目数据
	 */
	private String testPaperQuestionListData;
	
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

	public String getExamData() {
		return examData;
	}

	public void setExamData(String examData) {
		this.examData = examData;
	}

	public String getTestPaperData() {
		return testPaperData;
	}

	public void setTestPaperData(String testPaperData) {
		this.testPaperData = testPaperData;
	}

	public String getTestPaperQuestionListData() {
		return testPaperQuestionListData;
	}

	public void setTestPaperQuestionListData(String testPaperQuestionListData) {
		this.testPaperQuestionListData = testPaperQuestionListData;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMainServerData(String mainServerData) {
		this.mainServerData = mainServerData;
	}

	public String getMainServerData() {
		return mainServerData;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamName() {
		return examName;
	}
}
