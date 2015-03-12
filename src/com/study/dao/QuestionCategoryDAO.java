package com.study.dao;

import com.study.model.QuestionCategory;

/**
 * 题目分类数据操作接口
 */
public interface QuestionCategoryDAO {
	
	void insert(QuestionCategory instance);

	void update(QuestionCategory instance);

	void delete(QuestionCategory instance);
	
	void delete(Long id);

}
