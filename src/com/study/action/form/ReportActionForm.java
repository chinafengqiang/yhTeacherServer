package com.study.action.form;

import com.study.model.ReportExam;

/**
 * 报表 ActionForm
 */
public class ReportActionForm extends BaseActionForm {
	
	/**
	 * 考试报表
	 */
	private ReportExam reportExam;
	
	/**
	 * 考试报表编号
	 */
	private Long reportExamId;
	
	/**
	 * 单位编号
	 */
	private Long organId;
	
	/**
	 * 考试代号
	 */
	private String examCode;
	
	/**
	 * 名称 
	 */
	private String name;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 单位名称
	 */
	private String actualOrgan;
	
	/**
	 * 考生姓名
	 */
	private String actualName; 
	
	/**
	 * 成绩
	 */
	private Double score;
	
	/**
	 * 通过成绩
	 */
	private Integer passScore;
	
	/**
	 * 考生成绩编号
	 */
	private Long reportExamDataId; 

	public void setReportExam(ReportExam reportExam) {
		this.reportExam = reportExam;
	}

	public ReportExam getReportExam() {
		return reportExam;
	}

	public void setReportExamId(Long reportExamId) {
		this.reportExamId = reportExamId;
	}

	public Long getReportExamId() {
		return reportExamId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getExamCode() {
		return examCode;
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

	public void setReportExamDataId(Long reportExamDataId) {
		this.reportExamDataId = reportExamDataId;
	}

	public Long getReportExamDataId() {
		return reportExamDataId;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getScore() {
		return score;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}

	public Integer getPassScore() {
		return passScore;
	}
}
