package com.examserver.model;

import java.util.List;

import com.study.model.Exam;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;

public class EsExamData {

	/**
	 * 考试数据
	 */
	private Exam exam;
	
	/**
	 * 试卷数据
	 */
	private TestPaper testPaper;
	
	/**
	 * 试题列表
	 */
	private List<TestPaperQuestion> questionList;

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public TestPaper getTestPaper() {
		return testPaper;
	}

	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}

	public List<TestPaperQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TestPaperQuestion> questionList) {
		this.questionList = questionList;
	}	
	
}
