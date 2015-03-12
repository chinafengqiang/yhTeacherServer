package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.TestPaperDAO;
import com.study.model.TestPaper;

/**
 * 试卷数据操作类
 */
@Repository
public class TestPaperDAOImpl implements TestPaperDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(TestPaper instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(TestPaper instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(TestPaper instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(TestPaper.class, id));
	}

}
