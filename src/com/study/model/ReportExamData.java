package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ReportExamDataStatusEnum;

/**
 * 考试报表基础数据数据类
 */
@Entity
@Table(name="report_exam_data")
public class ReportExamData implements java.io.Serializable {

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
	 * 考试报表编号
	 */
	@Column(name="report_exam_id")
	private Long reportExamId;
	
	/**
	 * 单位编号
	 */
	@Column(name="organ_id")
	private Long organId;
	
	/**
	 * 考试本地标示
	 */
	@Column(name="exam_key")
	private String examKey;
	
	/**
	 * 学员本地标示
	 */
	@Column(name="user_key")
	private String userKey;
	
	/**
	 * 所属单位
	 */
	@Column(name="actual_organ")
	private String actualOrgan;

	/**
	 * 学员姓名
	 */
	@Column(name="actual_name")
	private String actualName;
	
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
	 * 考试分数
	 */
	private Double score;
	
	/**
	 * 数据来源
	 */
	private Integer source;
	
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
		
		return ReportExamDataStatusEnum.valueOf(this.status).toName();
	}
	
	//==================扩展字段 End ==================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReportExamId() {
		return reportExamId;
	}

	public void setReportExamId(Long reportExamId) {
		this.reportExamId = reportExamId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getExamKey() {
		return examKey;
	}

	public void setExamKey(String examKey) {
		this.examKey = examKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDutyRank() {
		return dutyRank;
	}

	public void setDutyRank(String dutyRank) {
		this.dutyRank = dutyRank;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSource() {
		return source;
	}

	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}
	
	

}
