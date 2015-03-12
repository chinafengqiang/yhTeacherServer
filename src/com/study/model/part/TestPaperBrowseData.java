package com.study.model.part;

import java.util.List;

import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;

/**
 * 试卷浏览数据
 */
public class TestPaperBrowseData {

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
	
}
