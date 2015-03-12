package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.SysParamDAO;
import com.study.model.SysParam;

/**
 * 系统参数数据操作类
 */
@Repository
public class SysParamDAOImpl implements SysParamDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(SysParam instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(SysParam instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(SysParam instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(SysParam.class, id));
	}

}
