package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ExamUserStatusEnum;
import com.study.utility.DateUtility;

/**
 * 考生数据类
 */
@Entity
@Table(name="exam_user")
public class ExamUser implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8012974260267213324L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 考试编号
	 */
	@Column(name="exam_id")
	private Long examId;
	
	/**
	 * 学员编号
	 */
	@Column(name="user_id")
	private Long userId;
	
	/**
	 * 学员本地化标示
	 */
	@Column(name="user_key")
	private String userKey;
	
	/**
	 * 考试服务器数据
	 */
	@Column(name="exam_server_data")
	private String examServerData;
	
	/**
	 * 备用考试服务器数据
	 */
	@Column(name="backup_exam_server_data")
	private String backupExamServerData;
	
	/**
	 * 姓名
	 */
	@Column(name="actual_name")
	private String actualName;
	
	/**
	 * 所属单位
	 */
	@Column(name="actual_organ")
	private String actualOrgan;
	
	/**
	 * 学员所属单位编号
	 */
	@Column(name="organ_id")
	private Long organId;
	
	/**
	 * 试卷编号
	 */
	@Column(name="test_paper_id")
	private Long testPaperId;
	
	/**
	 * 套数
	 */
	private Integer series;
	
	/**
	 * 是否规定时间段
	 */
	@Column(name="can_limit_valid_time")
	private Boolean canLimitValidTime;

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
	 * 实际开考时间 
	 */
	@Column(name="begin_time")
	private Date beginTime;
	
	/**
	 * 实际结束时间 
	 */
	@Column(name="end_time")
	private Date endTime;
	
	/**
	 * 分数
	 */
	private Double score;
	
	/**
	 * 学分
	 */
	private Integer credit;
	
	/**
	 * 是否通过
	 */
	@Column(name="can_pass")
	private Boolean canPass;
	
	/**
	 * 考试时长（秒）
	 */
	@Column(name="total_time")
	private Integer totalTime;
	
	/**
	 * 剩余时长（秒）
	 */
	@Column(name="leave_time")
	private Integer leaveTime;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	//==================扩展字段 Begin ==================

	/**
	 * 实际开考时间名称
	 */
	@Transient
	public String getBeginTimeName() {
		
		if (this.beginTime != null) {
			return DateUtility.dateToChineseString(this.beginTime, false);
		} else {
			return "";
		}
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return ExamUserStatusEnum.valueOf(this.status).toName();
	}
	
	//==================扩展字段 End ==================
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public Boolean getCanLimitValidTime() {
		return canLimitValidTime;
	}

	public void setCanLimitValidTime(Boolean canLimitValidTime) {
		this.canLimitValidTime = canLimitValidTime;
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Boolean getCanPass() {
		return canPass;
	}

	public void setCanPass(Boolean canPass) {
		this.canPass = canPass;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setExamServerData(String examServerData) {
		this.examServerData = examServerData;
	}

	public String getExamServerData() {
		return examServerData;
	}

	public void setBackupExamServerData(String backupExamServerData) {
		this.backupExamServerData = backupExamServerData;
	}

	public String getBackupExamServerData() {
		return backupExamServerData;
	}
}
