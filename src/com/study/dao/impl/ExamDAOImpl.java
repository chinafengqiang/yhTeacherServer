package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ExamDAO;
import com.study.model.Exam;

/**
 * 考试数据操作类
 */
@Repository
public class ExamDAOImpl implements ExamDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Exam instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Exam instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Exam instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Exam.class, id));
	}

}
