package com.study.utility;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ModelFactoryHQLUtility {
	
	 /**
	  * hibernate模板
	  */
	private HibernateTemplate hibernateTemplate;
	
	/**
	  * HQL语句
	  */
	private String hql;
	
	/**
	  * HQL的参数
	  */
	private Object[] params;
	
	/**
	 * 创建对象
	 * @param hibernateTemplate
	 * @return ModelFactoryHQLUtility
	 */
	public static ModelFactoryHQLUtility create(HibernateTemplate hibernateTemplate) {
		
		ModelFactoryHQLUtility utility = new ModelFactoryHQLUtility();
		utility.hibernateTemplate = hibernateTemplate;
		
		return utility;
	}
	
	/**
	 * 设置HQL语句
	 * @param hql
	 * @return ModelFactoryHQLUtility
	 */
	public ModelFactoryHQLUtility setHql(String hql) {
		
		this.hql = hql;
		return this;
	}
	
	/**
	 * 设置HQL参数
	 * @param params
	 * @return ModelFactoryHQLUtility
	 */
	public ModelFactoryHQLUtility setParams(Object ... params) {
		
		this.params = params;
		return this;
	}
	
	/**
	 * 得到对象的LIST，必须设置hql
	 * @return list
	 */
	public List list() {
		
		return this.hibernateTemplate.find(this.hql, this.params);
	}
	
	/**
	 * 得到对象，必须设置hql
	 * @return Object
	 */
	public Object object() {
		
		List list = this.hibernateTemplate.find(this.hql, this.params);
		
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
