package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ReportExamDAO;
import com.study.model.ReportExam;

/**
 * 考试报表数据操作类
 */
@Repository
public class ReportExamDAOImpl implements ReportExamDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(ReportExam instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ReportExam instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ReportExam instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ReportExam.class, id));
	}

}
