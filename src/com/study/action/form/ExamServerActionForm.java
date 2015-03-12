package com.study.action.form;

import com.study.model.ExamServer;

/**
 * 考试服务器 ActionForm
 */
public class ExamServerActionForm extends BaseActionForm {

	/**
	 * 考试服务器
	 */
	private ExamServer examServer;
	
	/**
	 * 考试编号
	 */
	private Long examId;

	public void setExamServer(ExamServer examServer) {
		this.examServer = examServer;
	}

	public ExamServer getExamServer() {
		return examServer;
	}

	public void setExamId(Long examId) {
		this.examId = examId;
	}

	public Long getExamId() {
		return examId;
	}
}
