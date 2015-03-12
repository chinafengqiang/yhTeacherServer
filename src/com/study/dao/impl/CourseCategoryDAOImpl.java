package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.CourseCategoryDAO;
import com.study.model.CourseCategory;

/**
 * 课程分类数据操作类
 */
@Repository
public class CourseCategoryDAOImpl implements CourseCategoryDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(CourseCategory instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(CourseCategory instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(CourseCategory instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(CourseCategory.class, id));
	}

}
