package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ReportExamStatusEnum;
import com.study.utility.DateUtility;

/**
 * 考试报表数据类
 */
@Entity
@Table(name="report_exam")
public class ReportExam implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -1401880684821357967L;

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
	 * 考试代号
	 */
	@Column(name="exam_code")
	private String examCode;
	
	/**
	 * 统计时间
	 */
	@Column(name="stated_time")
	private Date statedTime;

	/**
	 * 是否自动汇总成绩
	 */
	@Column(name="can_auto_gather")
	private Boolean canAutoGather;

	/**
	 * 状态：启动、结束
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
	 * 统计时间名称
	 */
	@Transient
	public String getStatedTimeName() {
		
		if (this.statedTime != null) {
			return DateUtility.dateToLongString(this.statedTime);
		} else {
			return "";
		}
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return ReportExamStatusEnum.valueOf(this.status).toName();
	}
	
	/**
	 * 是否自动汇总成绩名称
	 */
	@Transient
	public String getCanAutoGatherName() {
		
		if (this.canAutoGather) {
			return "是";
		} else {
			return "";
		}
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

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
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

	public void setStatedTime(Date statedTime) {
		this.statedTime = statedTime;
	}

	public Date getStatedTime() {
		return statedTime;
	}

	public void setCanAutoGather(Boolean canAutoGather) {
		this.canAutoGather = canAutoGather;
	}

	public Boolean getCanAutoGather() {
		return canAutoGather;
	}
}
