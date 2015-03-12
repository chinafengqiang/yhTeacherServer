package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.NoticeDAO;
import com.study.model.Notice;

/**
 * 公告数据操作类
 */
@Repository
public class NoticeDAOImpl implements NoticeDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(Notice instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(Notice instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(Notice instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(Notice.class, id));
	}

}
