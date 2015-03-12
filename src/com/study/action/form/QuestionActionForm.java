package com.study.action.form;

import com.study.model.Question;
import com.study.model.QuestionCategory;



/**
 * 题目 ActionForm
 */
public class QuestionActionForm extends BaseActionForm {
	
	/**
	 * 难度
	 */
	private Integer difficulty;
	
	/**
	 * 知识点
	 */
	private String ken;
	
	/**
	 * 题目
	 */
	private String name;
	
	/**
	 * 题型
	 */
	private Integer questionType;
	
	/**
	 * 题目分类数据
	 */
	private QuestionCategory questionCategory;
	
	/**
	 * 题目
	 */
	private Question question;
	
	/**
	 * 题目分类编号
	 */
	private Long questionCategoryId;

	public void setQuestionCategoryId(Long questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}

	public Long getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategory(QuestionCategory questionCategory) {
		this.questionCategory = questionCategory;
	}

	public QuestionCategory getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setKen(String ken) {
		this.ken = ken;
	}

	public String getKen() {
		return ken;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getDifficulty() {
		return difficulty;
	}
}
