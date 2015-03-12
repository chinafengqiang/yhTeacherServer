package com.study.model.part;

import java.util.Date;

/**
 * ExamUserResult entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ExamUserResult implements java.io.Serializable {

	// Fields

	private Long id;
	private String examKey;
	private String userKey;
	private Date examTime;
	private String certificateNo;
	private String dutyRank;

	private String actualName;
	private String sex;
	private Integer age;
	private String organ;
	private String department;
	private String post;
	private String email;
	private Double score;
	private Integer canOffline;
	
	private String parentOran;
	private Integer status;
	private String statusName;
	
	//2012-03-27添加考试结果的单位级别
	private Integer organDegree;
	
	
	
	public String getCertificateNo() {
		return certificateNo;
	}
	
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getDutyRank() {
		return dutyRank;
	}
	
	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		ExamUserResult e = (ExamUserResult)obj;
		return organ.equals(e.organ);
	}
	
	@Override
	public int hashCode() {
		return organ.hashCode();
	}

	// Constructors

	public Integer getOrganDegree() {
		return organDegree;
	}

	public void setOrganDegree(Integer organDegree) {
		this.organDegree = organDegree;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return ExamResultStatusEnum.valueOf(this.status).toString();
	}

	public String getParentOran() {
		return parentOran;
	}

	public void setParentOran(String parentOran) {
		this.parentOran = parentOran;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCanOffline() {
		return canOffline;
	}

	public void setCanOffline(Integer canOffline) {
		this.canOffline = canOffline;
	}

	/** default constructor */
	public ExamUserResult() {
	}

	/** minimal constructor */
	public ExamUserResult(String examKey, String userKey, Date examTime,
			String actualName, String sex, Integer age, String organ,
			String department, Double score) {
		this.examKey = examKey;
		this.userKey = userKey;
		this.examTime = examTime;
		this.actualName = actualName;
		this.sex = sex;
		this.age = age;
		this.organ = organ;
		this.department = department;
		this.score = score;
	}

	/** full constructor */
	public ExamUserResult(String examKey, String userKey, Date examTime,
			String actualName, String sex, Integer age, String organ,
			String department, String post, String email, Double score) {
		this.examKey = examKey;
		this.userKey = userKey;
		this.examTime = examTime;
		this.actualName = actualName;
		this.sex = sex;
		this.age = age;
		this.organ = organ;
		this.department = department;
		this.post = post;
		this.email = email;
		this.score = score;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamKey() {
		return this.examKey;
	}

	public void setExamKey(String examKey) {
		this.examKey = examKey;
	}

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public Date getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	public String getActualName() {
		return this.actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getOrgan() {
		return this.organ;
	}

	public void setOrgan(String organ) {
		this.organ = organ;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public int compareTo(Object o) {
		return 0;
	}

}