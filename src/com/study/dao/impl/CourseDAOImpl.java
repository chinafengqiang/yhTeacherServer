package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.CourseDAO;
import com.study.model.Course;

/**
 * 课程数据操作类
 */
@Repository
public class CourseDAOImpl implements CourseDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Course instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Course instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Course instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Course.class, id));
	}

}
