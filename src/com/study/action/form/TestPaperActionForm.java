package com.study.action.form;

import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;

/**
 * 试卷 ActionForm
 */
public class TestPaperActionForm extends BaseActionForm {
	
	/**
	 * 试卷编号
	 */
	private Long testPaperId;
	
	/**
	 * 套数
	 */
	private Integer series;
	
	/**
	 * 题目编号
	 */
	private Long questionId;
	
	/**
	 * 题目分类编号
	 */
	private Long questionCategoryId;

	/**
	 * 题型
	 */
	private Integer questionType;
	
	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 分数
	 */
	private Double score;
	
	/**
	 * 知识点
	 */
	private String ken;
	
	/**
	 * 数量
	 */
	private Integer number;
	
	/**
	 * 试卷题目编号 
	 */
	private Long testPaperQuestionId;
	
	/**
	 * 排序
	 */
	private Integer sortFlag;
	
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 名称
	 */
	private String category;
	
	/**
	 * 试卷题目
	 */
	private TestPaperQuestion testPaperQuestion;
	
	private String beginTime;
	
	private String endTime;
	
	public Integer getStatus() {
		return status;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getTestPaperQuestionId() {
		return testPaperQuestionId;
	}

	public void setTestPaperQuestionId(Long testPaperQuestionId) {
		this.testPaperQuestionId = testPaperQuestionId;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public Long getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(Long questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * 试卷
	 */
	private TestPaper testPaper;

	public Long getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(Long testPaperId) {
		this.testPaperId = testPaperId;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}

	public TestPaper getTestPaper() {
		return testPaper;
	}

	public void setKen(String ken) {
		this.ken = ken;
	}

	public String getKen() {
		return ken;
	}

	public void setTestPaperQuestion(TestPaperQuestion testPaperQuestion) {
		this.testPaperQuestion = testPaperQuestion;
	}

	public TestPaperQuestion getTestPaperQuestion() {
		return testPaperQuestion;
	}
	
}
