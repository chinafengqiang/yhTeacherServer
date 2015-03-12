package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.UserStatusEnum;

/**
 * 学员数据类
 */
@Entity
@Table(name="user")
public class User implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3293695815056813908L;

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
	 * 职务级别
	 */
	@Column(name="duty_rank")
	private String dutyRank;
	
	/**
	 * 行业
	 */
	private String trade;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 总学分
	 */
	@Column(name="total_credit")
	private Integer totalCredit;
	
	/**
	 * 课程学分 
	 */
	@Column(name="course_credit")
	private Integer courseCredit;
	
	/**
	 * 考试学分
	 */
	@Column(name="exam_credit")
	private Integer examCredit;
	
	/**
	 * 状态
	 */
	private Integer status;

	//==================扩展字段 Begin ==================
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return UserStatusEnum.valueOf(this.status).toName();
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

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getDutyRank() {
		return dutyRank;
	}

	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTotalCredit(Integer totalCredit) {
		this.totalCredit = totalCredit;
	}

	public Integer getTotalCredit() {
		return totalCredit;
	}

	public void setCourseCredit(Integer courseCredit) {
		this.courseCredit = courseCredit;
	}

	public Integer getCourseCredit() {
		return courseCredit;
	}

	public void setExamCredit(Integer examCredit) {
		this.examCredit = examCredit;
	}

	public Integer getExamCredit() {
		return examCredit;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getTrade() {
		return trade;
	}


	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

}
