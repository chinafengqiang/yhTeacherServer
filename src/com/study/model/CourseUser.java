package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.CourseTypeEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.utility.DateUtility;

/**
 * 课程学员数据类
 */
@Entity
@Table(name="course_user")
public class CourseUser implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1556230426974502825L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
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
	 * 单位编号
	 */
	@Column(name="organ_id")
	private Long organId;
	
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
	 * 课程编号
	 */
	@Column(name="course_id")
	private Long courseId;
	
	/**
	 * 本次开始时间
	 */
	@Column(name="begin_time")
	private Date beginTime;
	
	/**
	 * 本次结束时间
	 */
	@Column(name="end_time")
	private Date endTime;
	
	/**
	 * 学习方式
	 */
	@Column(name="study_type")
	private Integer studyType;

	/**
	 * 学习次数
	 */
	@Column(name="study_num")
	private Integer studyNum;
	
	/**
	 * 学分
	 */
	private Integer credit;
	
	/**
	 * 已学时长（分）
	 */
	@Column(name="total_time")
	private Integer totalTime;
		
	/**
	 * 开始学习时间
	 */
	@Column(name="joined_time")
	private Date joinedTime;
	
	/**
	 * 结束课程时间
	 */
	@Column(name="finished_time")
	private Date finishedTime;
	
	/**
	 * 状态 
	 */
	private Integer status;
	
	//==================扩展字段 Begin ==================
	
	/**
	 * 课程类型名称
	 */
	@Transient
	public String getCourseTypeName() {
		
		return CourseTypeEnum.valueOf(this.studyType).toName();
	}
	
	/**
	 * 开始学习时间名称
	 */
	@Transient
	public String getJoinedTimeName() {
		
		if (this.joinedTime != null) {
			return DateUtility.dateToChineseString(this.joinedTime, false);
		} else {
			return "";
		}
	}
	
	/**
	 * 结束学习时间名称
	 */
	@Transient
	public String getFilishedTimeName() {
		
		if (this.finishedTime != null) {
			return DateUtility.dateToChineseString(this.finishedTime, false);
		} else {
			return "";
		}
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return CourseUserStatusEnum.valueOf(this.status).toName();
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public Integer getStudyType() {
		return studyType;
	}

	public void setStudyType(Integer studyType) {
		this.studyType = studyType;
	}

	public Integer getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(Integer studyNum) {
		this.studyNum = studyNum;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setJoinedTime(Date joinedTime) {
		this.joinedTime = joinedTime;
	}

	public Date getJoinedTime() {
		return joinedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
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

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getOrganId() {
		return organId;
	}
}
