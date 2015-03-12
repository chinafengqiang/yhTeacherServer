package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ExamServerDAO;
import com.study.model.ExamServer;

/**
 * 考试服务器数据操作类
 */
@Repository
public class ExamServerDAOImpl implements ExamServerDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(ExamServer instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ExamServer instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ExamServer instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ExamServer.class, id));
	}

}
