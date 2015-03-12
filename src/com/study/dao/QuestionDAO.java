package com.study.dao;

import com.study.model.Question;

/**
 * 题目数据操作接口
 */
public interface QuestionDAO {
	
	void insert(Question instance);

	void update(Question instance);

	void delete(Question instance);
	
	void delete(Long id);

}
