package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.OrganDAO;
import com.study.model.Organ;

/**
 * 单位数据操作类
 */
@Repository
public class OrganDAOImpl implements OrganDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Organ instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Organ instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Organ instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Organ.class, id));
	}

}
