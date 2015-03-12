package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;

/**
 * 课程数据类
 */
@Entity
@Table(name="course")
public class Course implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7694624840734004080L;

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
	 * 分类
	 */
	@Column(name="course_category_id")
	private Long courseCategoryId;
	
	/**
	 * 课程本地标示
	 */
	@Column(name="course_key")
	private String courseKey;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 封面图片链接
	 */
	@Column(name="cover_image_link")
	private String coverImageLink;
	
	/**
	 * 学时
	 */
	@Column(name="class_hour")
	private Integer classHour;
	
	/**
	 * 学分
	 */
	private Integer credit;
	
	/**
	 * 通过所需的最低学分
	 */
	@Column(name="pass_credit_limit")
	private Integer passCreditLimit;
	
	/**
	 * 学习次数
	 */
	@Column(name="study_num")
	private Integer studyNum;
	
	/**
	 * 课程类型
	 */
	@Column(name="course_type")
	private Integer courseType;
	
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
	 * 是否允许所有学员学习
	 */
	@Column(name="can_allow_all_user")
	private Boolean canAllowAllUser;
	
	/**
	 * 学员参与范围 
	 */
	@Column(name="user_joined_data")
	private String userJoinedData;
	
	/**
	 * 参加课程的单位列表：,1,2,3,4, 
	 */
	@Column(name="joined_organs")
	private String joinedOrgans;
	
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
	 * 课程类型名称
	 */
	@Transient
	public String getCourseTypeName() {
		
		return CourseTypeEnum.valueOf(this.courseType).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return CourseStatusEnum.valueOf(this.status).toName();
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

	public Long getCourseCategoryId() {
		return courseCategoryId;
	}

	public void setCourseCategoryId(Long courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}

	public String getCourseKey() {
		return courseKey;
	}

	public void setCourseKey(String courseKey) {
		this.courseKey = courseKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverImageLink() {
		return coverImageLink;
	}

	public void setCoverImageLink(String coverImageLink) {
		this.coverImageLink = coverImageLink;
	}

	public Integer getClassHour() {
		return classHour;
	}

	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getPassCreditLimit() {
		return passCreditLimit;
	}

	public void setPassCreditLimit(Integer passCreditLimit) {
		this.passCreditLimit = passCreditLimit;
	}

	public Integer getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(Integer studyNum) {
		this.studyNum = studyNum;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
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

	public void setCanAllowAllUser(Boolean canAllowAllUser) {
		this.canAllowAllUser = canAllowAllUser;
	}

	public Boolean getCanAllowAllUser() {
		return canAllowAllUser;
	}

	public void setJoinedOrgans(String joinedOrgans) {
		this.joinedOrgans = joinedOrgans;
	}

	public String getJoinedOrgans() {
		return joinedOrgans;
	}
	
}
