package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ExamStatusEnum;
import com.study.enums.QuestionFetchTypeEnum;
import com.study.enums.TimerModeEnum;
import com.study.utility.DateUtility;

/**
 * 考试数据类
 */
@Entity
@Table(name="exam")
public class Exam implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 8285240810716965845L;

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
	 * 考试本地化标示
	 */
	@Column(name="exam_key")
	private String examKey;
	
	/**
	 * 考试代号
	 */
	@Column(name="exam_code")
	private String examCode;
	
	/**
	 * 考试模式
	 */
	@Column(name="exam_mode")
	private Integer examMode;
	
	/**
	 * 考试项目标示
	 */
	@Column(name="es_exam_key")
	private String esExamKey;
	
	/**
	 * 分类 
	 */
	private String category;
	
	/**
	 * 学员参与范围 
	 */
	@Column(name="user_joined_data")
	private String userJoinedData;
	
	/**
	 * 参加课程的单位列表：-1-2-3-4- 
	 */
	@Column(name="joined_organs")
	private String joinedOrgans;
	
	/**
	 * 试卷 
	 */
	@Column(name="test_paper_id")
	private Long testPaperId;
	
	/**
	 * 题目提取方式（是否随机）
	 */
	@Column(name="question_fetch_type")
	private Integer questionFetchType;
	
	/**
	 * 进场开始时间 
	 */
	@Column(name="valid_first_time")
	private Date validFirstTime;
	
	/**
	 * 进场结束时间
	 */
	@Column(name="valid_last_time")
	private Date validLastTime;
	
	/**
	 * 计时模式
	 */
	@Column(name="timer_mode")
	private Integer timerMode;
	
	/**
	 * 考试时长 
	 */
	@Column(name="timer_limit")
	private Integer timerLimit;
	
	/**
	 * 是否允许学员在考试时间内多次参加考试
	 */
	@Column(name="can_allow_multi_join")
	private Boolean canAllowMultiJoin;
	
	/**
	 * 是否允许所有学员考试
	 */
	@Column(name="can_allow_all_user")
	private Boolean canAllowAllUser;

	/**
	 * 学员完成必修课程后才能考试（考试条件）
	 */
	@Column(name="can_course_study_limit")
	private Boolean canCourseStudyLimit;
	
	/**
	 * 是否保密考试分数
	 */
	@Column(name="can_keep_secret_score")
	private Boolean canKeepSecretScore;
	
	/**
	 * 是否可考后查看答案
	 */
	@Column(name="can_query_answer")
	private Boolean canQueryAnswer;
	
	/**
	 * 是否规定统一考试时间段
	 */
	@Column(name="can_limit_valid_time")
	private Boolean canLimitValidTime;
	
	/**
	 * 是否统一交卷
	 */
	@Column(name="can_limit_commit_time")
	private Boolean canLimitCommitTime;
	
	/**
	 * 是否匹配职务级别
	 */
	@Column(name="can_match_duty_rank")
	private Boolean canMatchDutyRank;
	
	/**
	 * 职务级别
	 */
	@Column(name="duty_rank")
	private String dutyRank;
	
	/**
	 * 是否匹配行业
	 */
	@Column(name="can_match_trade")
	private Boolean canMatchTrade;
	
	/**
	 * 行业
	 */
	private String trade;
	
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
	 * 创建人
	 */
	private Long creator;

	//==================扩展字段 Begin ==================
	
	/**
	 * 题目提取方式
	 */
	@Transient
	public String getQuestionFetchTypeName() {
		
		return QuestionFetchTypeEnum.valueOf(this.questionFetchType).toName();
	}
	
	/**
	 * 题目提取方式
	 */
	@Transient
	public String getTimerModeName() {
		
		return TimerModeEnum.valueOf(this.timerMode).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return ExamStatusEnum.valueOf(this.status).toName();
	}
	
	/**
	 * 摘要
	 */
	@Transient
	public String getExamSummary() {
		
		String rt = "";
		
		//限制进场时间，但不是统一交卷
		if (this.canLimitValidTime && !this.canLimitCommitTime) {

			rt = "时间：" + DateUtility.dateToChineseString(this.validFirstTime, true) + " " +
				DateUtility.dateToTimeString(this.validFirstTime, true) + " -- " + 
				DateUtility.dateToTimeString(this.validLastTime, true);
		}
		
		//限制进场时间，是统一交卷
		if (this.canLimitValidTime && this.canLimitCommitTime) {
			
			rt = "时间：" + DateUtility.dateToChineseString(this.validFirstTime, true) + " " +
			DateUtility.dateToTimeString(this.validFirstTime, true) + "　统一交卷 ";
		}
		
		//计时模式
		if (this.timerMode.equals(TimerModeEnum.Limit.toValue())) {
			
			rt = rt + " 时长" + this.timerLimit + "分钟";
		}
		
		return rt;
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

	public String getExamKey() {
		return examKey;
	}

	public void setExamKey(String examKey) {
		this.examKey = examKey;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(Long testPaperId) {
		this.testPaperId = testPaperId;
	}

	public Integer getQuestionFetchType() {
		return questionFetchType;
	}

	public void setQuestionFetchType(Integer questionFetchType) {
		this.questionFetchType = questionFetchType;
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

	public Integer getTimerMode() {
		return timerMode;
	}

	public void setTimerMode(Integer timerMode) {
		this.timerMode = timerMode;
	}

	public Integer getTimerLimit() {
		return timerLimit;
	}

	public void setTimerLimit(Integer timerLimit) {
		this.timerLimit = timerLimit;
	}

	public Boolean getCanAllowAllUser() {
		return canAllowAllUser;
	}

	public void setCanAllowAllUser(Boolean canAllowAllUser) {
		this.canAllowAllUser = canAllowAllUser;
	}

	public Boolean getCanCourseStudyLimit() {
		return canCourseStudyLimit;
	}

	public void setCanCourseStudyLimit(Boolean canCourseStudyLimit) {
		this.canCourseStudyLimit = canCourseStudyLimit;
	}

	public Boolean getCanKeepSecretScore() {
		return canKeepSecretScore;
	}

	public void setCanKeepSecretScore(Boolean canKeepSecretScore) {
		this.canKeepSecretScore = canKeepSecretScore;
	}

	public Boolean getCanQueryAnswer() {
		return canQueryAnswer;
	}

	public void setCanQueryAnswer(Boolean canQueryAnswer) {
		this.canQueryAnswer = canQueryAnswer;
	}

	public Boolean getCanLimitValidTime() {
		return canLimitValidTime;
	}

	public void setCanLimitValidTime(Boolean canLimitValidTime) {
		this.canLimitValidTime = canLimitValidTime;
	}

	public Boolean getCanLimitCommitTime() {
		return canLimitCommitTime;
	}

	public void setCanLimitCommitTime(Boolean canLimitCommitTime) {
		this.canLimitCommitTime = canLimitCommitTime;
	}

	public Boolean getCanMatchDutyRank() {
		return canMatchDutyRank;
	}

	public void setCanMatchDutyRank(Boolean canMatchDutyRank) {
		this.canMatchDutyRank = canMatchDutyRank;
	}

	public String getDutyRank() {
		return dutyRank;
	}

	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
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

	public void setCanMatchTrade(Boolean canMatchTrade) {
		this.canMatchTrade = canMatchTrade;
	}

	public Boolean getCanMatchTrade() {
		return canMatchTrade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getTrade() {
		return trade;
	}

	public void setUserJoinedData(String userJoinedData) {
		this.userJoinedData = userJoinedData;
	}

	public String getUserJoinedData() {
		return userJoinedData;
	}

	public void setEsExamKey(String esExamKey) {
		this.esExamKey = esExamKey;
	}

	public String getEsExamKey() {
		return esExamKey;
	}

	public void setExamMode(Integer examMode) {
		this.examMode = examMode;
	}

	public Integer getExamMode() {
		return examMode;
	}

	public void setCanAllowMultiJoin(Boolean canAllowMultiJoin) {
		this.canAllowMultiJoin = canAllowMultiJoin;
	}

	public Boolean getCanAllowMultiJoin() {
		return canAllowMultiJoin;
	}

	public void setJoinedOrgans(String joinedOrgans) {
		this.joinedOrgans = joinedOrgans;
	}

	public String getJoinedOrgans() {
		return joinedOrgans;
	}

}
