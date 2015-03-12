package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.study.enums.ExamServerStatusEnum;

/**
 * 考试服务器数据类
 */
@Entity
@Table(name="exam_server")
public class ExamServer implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4872881905399447435L;

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
	 * Url
	 */
	private String url;

	/**
	 * 内网Url
	 */
	@Column(name="direct_url")
	private String directUrl;
	
	/**
	 * 说明
	 */
	private String note;
	
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
		
		return ExamServerStatusEnum.valueOf(this.status).toName();
	}
	
	//==================扩展字段 End ==================

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}

	public String getDirectUrl() {
		return directUrl;
	}

}
