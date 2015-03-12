package com.study.utility;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class DAOHQLUtility {
	
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
	public static DAOHQLUtility create(HibernateTemplate hibernateTemplate) {
		
		DAOHQLUtility utility = new DAOHQLUtility();
		utility.hibernateTemplate = hibernateTemplate;
		
		return utility;
	}
	
	/**
	 * 设置HQL语句
	 * @param hql
	 * @return ModelFactoryHQLUtility
	 */
	public DAOHQLUtility setHql(String hql) {
		
		this.hql = hql;
		return this;
	}
	
	/**
	 * 设置HQL参数
	 * @param params
	 * @return ModelFactoryHQLUtility
	 */
	public DAOHQLUtility setParams(Object ... params) {
		
		this.params = params;
		return this;
	}
	
	private Query createQuery(Session session) {
		
		Query query = session.createQuery(this.hql);
		
		if (this.params != null) {
			for(int i=0; i < this.params.length; i++) {
				query.setParameter(i, this.params[i]);
			}
		}
		
		return query;
	}
	
	/**
	 * 执行sql语句
	 */
	public void execute() {
		
		this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
					
				return createQuery(session).executeUpdate();
			}});
	}
}
