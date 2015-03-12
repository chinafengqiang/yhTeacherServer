package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.QuestionCategoryDAO;
import com.study.model.QuestionCategory;

/**
 * 题目分类数据操作类
 */
@Repository
public class QuestionCategoryDAOImpl implements QuestionCategoryDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(QuestionCategory instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(QuestionCategory instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(QuestionCategory instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(QuestionCategory.class, id));
	}

}
