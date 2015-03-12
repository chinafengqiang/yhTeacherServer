package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.TestPaperStatusEnum;

/**
 * 试卷数据类
 */
@Entity
@Table(name="test_paper")
public class TestPaper implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7538843186760941545L;

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
	 * 描述
	 */
	private String description;
	
	/**
	 * 试卷本地化标示
	 */
	@Column(name="test_paper_key")
	private String testPaperKey;
	
	/**
	 * 分类
	 */
	private String category;
	
	/**
	 * 试卷配置
	 */
	@Column(name="test_paper_options")
	private String testPaperOptions;
	
	/**
	 * 试题显示方式
	 */
	@Column(name="question_show_type")
	private Integer questionShowType;
	
	/**
	 * 试题排序方式
	 */
	@Column(name="question_sort_type")
	private Integer questionSortType;
	
	/**
	 * 试题选项排序方式
	 */
	@Column(name="question_options_sort_type")
	private Integer questionOptionsSortType;
	
	/**
	 * 学分
	 */
	private Integer credit;
	
	/**
	 * 总分
	 */
	@Column(name="total_score")
	private Integer totalScore;
	
	/**
	 * 总套数
	 */
	@Column(name="total_series")
	private Integer totalSeries;
	
	/**
	 * 通过考试的分数
	 */
	@Column(name="pass_score")
	private Integer passScore;
	
	/**
	 * 是否可忽略题库题目分数
	 */
	@Column(name="can_ignore_question_score")
	private Boolean canIgnoreQuestionScore;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建时间
	 */
	@Column(name="created_time")
	private Date createdTime;
	
	/**
	 * 是否可考后查看答案
	 */
	@Column(name="can_query_answer")
	private Boolean canQueryAnswer;
	
	public Boolean getCanQueryAnswer() {
		return canQueryAnswer;
	}

	public void setCanQueryAnswer(Boolean canQueryAnswer) {
		this.canQueryAnswer = canQueryAnswer;
	}

	/**
	 * 创建人
	 */
	private Long creator;
	
	//==================扩展字段 Begin ==================
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return TestPaperStatusEnum.valueOf(this.status).toName();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getQuestionShowType() {
		return questionShowType;
	}

	public void setQuestionShowType(Integer questionShowType) {
		this.questionShowType = questionShowType;
	}

	public Integer getQuestionSortType() {
		return questionSortType;
	}

	public void setQuestionSortType(Integer questionSortType) {
		this.questionSortType = questionSortType;
	}

	public Integer getQuestionOptionsSortType() {
		return questionOptionsSortType;
	}

	public void setQuestionOptionsSortType(Integer questionOptionsSortType) {
		this.questionOptionsSortType = questionOptionsSortType;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalSeries() {
		return totalSeries;
	}

	public void setTotalSeries(Integer totalSeries) {
		this.totalSeries = totalSeries;
	}

	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}

	public Boolean getCanIgnoreQuestionScore() {
		return canIgnoreQuestionScore;
	}

	public void setCanIgnoreQuestionScore(Boolean canIgnoreQuestionScore) {
		this.canIgnoreQuestionScore = canIgnoreQuestionScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTestPaperKey(String testPaperKey) {
		this.testPaperKey = testPaperKey;
	}

	public String getTestPaperKey() {
		return testPaperKey;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setTestPaperOptions(String testPaperOptions) {
		this.testPaperOptions = testPaperOptions;
	}

	public String getTestPaperOptions() {
		return testPaperOptions;
	}
}
