package com.study.model.part;


/**
 * 试卷配置
 */
public class TestPaperOption{

	/**
	 * 题目类型 
	 */
	private Integer questionType;
	
	/**
	 * 题目数量 
	 */
	private Integer number;
	
	/**
	 * 题目分数 
	 */
	private Double score;
	
	/**
	 * 显示顺序
	 */
	private Integer sortFlag;
	
	/**
	 * 备注 
	 */
	private String note;
	
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getSortFlag() {
		return sortFlag;
	}
	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}