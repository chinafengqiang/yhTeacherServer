package com.study.dao;

import com.study.model.Exam;

/**
 * 考试数据操作接口
 */
public interface ExamDAO {
	
	void insert(Exam instance);

	void update(Exam instance);

	void delete(Exam instance);
	
	void delete(Long id);

}
