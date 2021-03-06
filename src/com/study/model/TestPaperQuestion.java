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
 * 试卷题目数据类
 */
@Entity
@Table(name="test_paper_question")
public class TestPaperQuestion implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 250451815967592822L;

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
	 * 试卷编号
	 */
	@Column(name="test_paper_id")
	private Long testPaperId;
	
	/**
	 * 题目编号
	 */
	@Column(name="question_id")
	private Long questionId;
	
	/**
	 * 套数
	 */
	private Integer series;

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
	
	/**
	 * 排序
	 */
	@Column(name="sort_flag")
	private Integer sortFlag;

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

	public Long getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(Long testPaperId) {
		this.testPaperId = testPaperId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
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

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}
	
}
