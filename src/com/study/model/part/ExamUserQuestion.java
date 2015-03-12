package com.study.model.part;


/**
 * 考生考卷数据类
 */
public class ExamUserQuestion implements java.io.Serializable {
	
	/**
	 * 考卷题目
	 */
	private Long testPaperQuestionId;
	
	/**
	 * 考生答案
	 */
	private String userAnswer;
	
	/**
	 * 分数
	 */
	private Double score;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 题目实际排位
	 */
	private String questionSortFlag;
	
	/**
	 * 题目选项实际排位
	 */
	private String questionOptionsSortFlag;

	public Long getTestPaperQuestionId() {
		return testPaperQuestionId;
	}

	public void setTestPaperQuestionId(Long testPaperQuestionId) {
		this.testPaperQuestionId = testPaperQuestionId;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
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

	public String getQuestionSortFlag() {
		return questionSortFlag;
	}

	public void setQuestionSortFlag(String questionSortFlag) {
		this.questionSortFlag = questionSortFlag;
	}

	public String getQuestionOptionsSortFlag() {
		return questionOptionsSortFlag;
	}

	public void setQuestionOptionsSortFlag(String questionOptionsSortFlag) {
		this.questionOptionsSortFlag = questionOptionsSortFlag;
	}
	
	
}
