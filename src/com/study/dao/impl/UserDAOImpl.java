package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.UserDAO;
import com.study.model.User;

/**
 * 学员数据操作类
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(User instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(User instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(User instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(User.class, id));
	}

}
