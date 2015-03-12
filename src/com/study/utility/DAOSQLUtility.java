package com.study.utility;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * 数据操作原生Sql的工具类
 *
 */
public class DAOSQLUtility {
	
	/**
	 * hibernate模板
	 */
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * sql语句
	 */
	private String sql;
	
	/**
	  * 参数集合
	  */
	private Object[] params;
	
	/**
	 * 创建对象
	 * @param hibernateTemplate
	 * @return ModelFactorySQLUtility
	 */
	public static DAOSQLUtility create(HibernateTemplate hibernateTemplate) {
		
		DAOSQLUtility utility = new DAOSQLUtility();
		utility.hibernateTemplate = hibernateTemplate;
		
		return utility;
	}
	
	/**
	 * 设置sql语句
	 * @param sql
	 * @return ModelFactorySQLUtility
	 */
	public DAOSQLUtility setSql(String sql) {
		
		this.sql = sql;
		return this;
	}
	
	/**
	 * 设置参数
	 * @param params
	 * @return ModelFactoryHQLUtility
	 */
	public DAOSQLUtility setParams(Object ... params) {
		
		this.params = params;
		return this;
	}
	
	private SQLQuery createSQLQuery(Session session) {
		
		SQLQuery sqlQuery = session.createSQLQuery(this.sql);
		
		if (this.params != null) {
			for(int i=0; i < this.params.length; i++) {
				sqlQuery.setParameter(i, this.params[i]);
			}
		}
		
		return sqlQuery;
	}
	
	/**
	 * 执行sql语句
	 */
	public void execute() {
		
		this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
					
				return createSQLQuery(session).executeUpdate();
			}});
	}
}
