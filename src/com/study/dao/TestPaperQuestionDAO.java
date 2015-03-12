package com.study.dao;

import com.study.model.TestPaperQuestion;

/**
 * 试卷题目数据操作接口
 */
public interface TestPaperQuestionDAO {
	
	void insert(TestPaperQuestion instance);

	void update(TestPaperQuestion instance);

	void delete(TestPaperQuestion instance);
	
	void delete(Long id);
	
	/**
	 * 删除试卷的所有题目记录
	 * @param testPaperId 试卷编号
	 */
	void deleteListByTestPaper(Long testPaperId);

}
