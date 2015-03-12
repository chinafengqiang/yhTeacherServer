package com.study.dao;

import com.study.model.TestPaper;

/**
 * 试卷数据操作接口
 */
public interface TestPaperDAO {
	
	void insert(TestPaper instance);

	void update(TestPaper instance);

	void delete(TestPaper instance);
	
	void delete(Long id);

}
