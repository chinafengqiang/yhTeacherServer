package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.QuestionDAO;
import com.study.model.Question;

/**
 * 题目数据操作类
 */
@Repository
public class QuestionDAOImpl implements QuestionDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Question instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Question instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Question instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Question.class, id));
	}

}
