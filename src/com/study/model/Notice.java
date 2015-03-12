package com.study.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.utility.DateUtility;

/**
 * 公告数据类
 */
@Entity
@Table(name="notice")
public class Notice implements java.io.Serializable {

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
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容 
	 */
	private String content;

	/**
	 * 级别 
	 */
	private Integer grade;
	
	/**
	 * 排序
	 */
	@Column(name="sort_flag")
	private Integer sortFlag;
	
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
	 * 公告级别名称
	 */
	@Transient
	public String getGradeName() {
		
		return NoticeGradeEnum.valueOf(this.grade).toName();
	}
	
	/**
	 * 状态名称
	 */
	@Transient
	public String getStatusName() {
		
		return NoticeStatusEnum.valueOf(this.status).toName();
	}
	
	/**
	 * 日期名称
	 */
	@Transient
	public String getCreatedTimeName() {
		
		if (this.createdTime != null) {
			return DateUtility.dateToChineseString(this.createdTime, true);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

}
