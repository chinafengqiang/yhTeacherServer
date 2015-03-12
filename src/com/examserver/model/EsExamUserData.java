package com.examserver.model;

import java.io.Serializable;

/**
 * ES考生考试结果类
 */
public class EsExamUserData implements Serializable {

	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * ES考试标示
	 */
	private String esExamKey;
	
	/**
	 * 学员标示
	 */
	private String userKey;
	
	/**
	 * 考试结果数据包 
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

	public String getEsExamKey() {
		return esExamKey;
	}

	public void setEsExamKey(String esExamKey) {
		this.esExamKey = esExamKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	
}
