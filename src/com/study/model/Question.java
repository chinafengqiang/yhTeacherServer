package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.QuestionTypeEnum;

/**
 * 题目数据类
 */
@Entity
@Table(name="question")
public class Question implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 542525437223603985L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 分类编号
	 */
	@Column(name="question_category_id")
	private Long questionCategoryId;
	
	/**
	 * 题目类型 
	 */
	@Column(name="question_type")
	private Integer questionType;
	
	/**
	 * 选项
	 */
	private String options;
	
	/**
	 * 答案
	 */
	private String answer;
	
	/**
	 * 知识点
	 */
	private String ken;
	
	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 分数
	 */
	private Double score;
	
	/**
	 * 备注
	 */
	private String note;

	//==================扩展字段 Begin ==================
	
	/**
	 * 题目类型名称
	 */
	@Transient
	public String getQuestionTypeName() {
		
		return QuestionTypeEnum.valueOf(this.questionType).toName();
	}
	
	//==================扩展字段 End ==================
	
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

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getKen() {
		return ken;
	}

	public void setKen(String ken) {
		this.ken = ken;
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

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	
}
