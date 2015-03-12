package com.study.dao;

import com.study.model.Article;

/**
 * 文章数据操作接口
 */
public interface ArticleDAO {
	
	void insert(Article instance);

	void update(Article instance);

	void delete(Article instance);
	
	void delete(Long id);

}
