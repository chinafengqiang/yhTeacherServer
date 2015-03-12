package com.study.model.part;

import java.util.List;

import com.study.model.ExamUser;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;

/**
 * 答卷浏览数据
 */
public class UserTestPaperBrowseData {

	/**
	 * 考试名称
	 */
	private String examName;
	
	/**
	 * 考生数据
	 */
	private ExamUser examUser;
	
	/**
	 * 试卷
	 */
	private TestPaper testPaper;

	/**
	 * 套数
	 */
	private Integer series;
	
	/**
	 * 题目 
	 */
	private List<TestPaperQuestion> questionList;
	
	/**
	 * 答题数据包
	 */
	private String data;

	public TestPaper getTestPaper() {
		return testPaper;
	}

	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public void setQuestionList(List<TestPaperQuestion> questionList) {
		this.questionList = questionList;
	}

	public List<TestPaperQuestion> getQuestionList() {
		return questionList;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setExamUser(ExamUser examUser) {
		this.examUser = examUser;
	}

	public ExamUser getExamUser() {
		return examUser;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamName() {
		return examName;
	}
	
}
