package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 考生考卷数据类
 */
@Entity
@Table(name="exam_user_data")
public class ExamUserData implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1475116329163081234L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 考生编号
	 */
	@Column(name="exam_user_id")
	private Long examUserId;
	
	/**
	 * 数据包
	 */
	private String data;
	
	/**
	 * 分数
	 */
	private Double score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExamUserId() {
		return examUserId;
	}

	public void setExamUserId(Long examUserId) {
		this.examUserId = examUserId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getScore() {
		return score;
	}
	
}
