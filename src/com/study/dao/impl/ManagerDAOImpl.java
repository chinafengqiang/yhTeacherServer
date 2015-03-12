package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ManagerDAO;
import com.study.model.Manager;

/**
 * 管理员数据操作类
 */
@Repository
public class ManagerDAOImpl implements ManagerDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Manager instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Manager instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Manager instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Manager.class, id));
	}

}
