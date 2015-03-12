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
 * 数据工厂原生Sql的工具类
 *
 */
public class ModelFactorySQLUtility {
	
	/**
	 * hibernate模板
	 */
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * sql语句
	 */
	private String sql;
	
	/**
	 * 被封装的实体类
	 */
	private Class entityClass;
	
	/**
	  * HQL的参数
	  */
	private Object[] params;
	
	/**
	 * 创建对象
	 * @param hibernateTemplate
	 * @return ModelFactorySQLUtility
	 */
	public static ModelFactorySQLUtility create(HibernateTemplate hibernateTemplate) {
		
		ModelFactorySQLUtility utility = new ModelFactorySQLUtility();
		utility.hibernateTemplate = hibernateTemplate;
		
		return utility;
	}
	
	/**
	 * 设置sql语句
	 * @param sql
	 * @return ModelFactorySQLUtility
	 */
	public ModelFactorySQLUtility setSql(String sql) {
		
		this.sql = sql;
		return this;
	}
	
	/**
	 * 设置封装实体类
	 * @param entityClass
	 * @return ModelFactorySQLUtility
	 */
	public ModelFactorySQLUtility setEntityCalss(Class entityClass) {
		
		this.entityClass = entityClass;
		return this;
	}
	
	/**
	 * 设置HQL参数
	 * @param params
	 * @return ModelFactoryHQLUtility
	 */
	public ModelFactorySQLUtility setParams(Object ... params) {
		
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
	
	private SQLQuery createSQLQuery(Session session, String sql) {
		
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		
		if (params != null) {
			for(int i=0; i<params.length; i++) {
				sqlQuery.setParameter(i, params[i]);
			}
		}
		
		return sqlQuery;
	}
	
	/**
	 * 得到分页的对象，在获得此对象之前必须设置sql和paginateCountHql和entityClass
	 * @param start
	 * @param limit
	 * @return PaginateResult
	 */
	public PaginateResult paginateResult(final PaginateParamters paginateParamters) {
		
		return (PaginateResult) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
				
				PaginateResult result = new PaginateResult();
							
				result.setList(createSQLQuery(session)
							 .addEntity(entityClass)
							 .setFirstResult(paginateParamters.getStart())
							 .setMaxResults(paginateParamters.getLimit())							 
							 .list());
				
				result.setCount((Integer)createSQLQuery(session, PaginateResult.getTotalCountSql(sql))
				 			.addScalar(PaginateResult.getTotalCountFieldName(),Hibernate.INTEGER)
				 			.uniqueResult());
				
				result.setPageNo(paginateParamters.getPageNo());
				result.setPerPageNumber(paginateParamters.getPerPageNumber());
				
				return result;
			}});
	}
	
	/**
	 * 得到对象的LIST，必须设置sql和entityClass
	 * @return list
	 */
	public List list() {
		
		return (List) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
					
				return createSQLQuery(session).addEntity(entityClass).list();
			}});
	}
	
	/**
	 * 得到对象的LIST，必须设置sql和entityClass
	 * @return list
	 */
	public List list(final Integer limit) {
		
		return (List) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
							
				return createSQLQuery(session)
					.addEntity(entityClass)
					.setFirstResult(0)
					.setMaxResults(limit)
					.list();
			}});
	}
	
	/**
	 * 得到对象，必须设置sql和entityClass
	 * @return Object
	 */
	public Object object() {
		
		return (Object) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
				
				List list = createSQLQuery(session).addEntity(entityClass).list();
				
				if (list.size() > 0) {
					return list.get(0);
				} else {
					return null;
				}
				
			}});
	}
	
	/**
	 * 得到Long唯一值
	 * @param fieldName 字段名
	 */
	public Long uniqueResultLong(final String fieldName) {
		
		return (Long) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
			
				Long result = (Long)createSQLQuery(session).addScalar(fieldName, Hibernate.LONG).uniqueResult();
				if (result == null) {
					return 0l;
				} else {
					return result;
				}
			}});
	}
	
	/**
	 * 得到Integer唯一值
	 * @param fieldName 字段名
	 */
	public Integer uniqueResultInteger(final String fieldName) {
		
		return (Integer) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
			
				Integer result = (Integer)createSQLQuery(session).addScalar(fieldName, Hibernate.INTEGER).uniqueResult();
				if (result == null) {
					return 0;
				} else {
					return result;
				}
			}});
	}
	
	/**
	 * 得到Double唯一值
	 * @param fieldName 字段名
	 */
	public Double uniqueResultDouble(final String fieldName) {
		
		return (Double) this.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {	
			
				Double result = (Double)createSQLQuery(session).addScalar(fieldName, Hibernate.DOUBLE).uniqueResult();
				if (result == null) {
					return 0.0;
				} else {
					return result;
				}
			}});
	}
	
}
