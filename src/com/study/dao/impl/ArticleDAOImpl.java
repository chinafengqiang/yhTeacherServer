package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ArticleDAO;
import com.study.model.Article;

/**
 * 文章数据操作类
 */
@Repository
public class ArticleDAOImpl implements ArticleDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Article instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Article instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Article instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Article.class, id));
	}

}
