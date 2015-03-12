package com.study.action.form;

import com.study.model.Exam;

/**
 * 考试 ActionForm
 */
public class ExamActionForm extends BaseActionForm {
	
	/**
	 * 考试
	 */
	private Exam exam;
	
	/**
	 * 考试编号
	 */
	private Long examId;
	
	/**
	 * 考生编号
	 */
	private Long examUserId;
	
	/**
	 * 考生参与范围
	 */
	private String userJoinedData;
	
	/**
	 * 分类
	 */
	private String category;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 所属单位
	 */
	private String actualOrgan;
	
	/**
	 * 所属名称
	 */
	private String actualName;

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getExamId() {
		return examId;
	}

	public void setUserJoinedData(String userJoinedData) {
		this.userJoinedData = userJoinedData;
	}

	public String getUserJoinedData() {
		return userJoinedData;
	}

	public void setExamUserId(Long examUserId) {
		this.examUserId = examUserId;
	}

	public Long getExamUserId() {
		return examUserId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
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
