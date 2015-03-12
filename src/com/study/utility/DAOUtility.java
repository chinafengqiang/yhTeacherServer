package com.study.utility;

import org.springframework.orm.hibernate3.HibernateTemplate;
/**
 * 
 * 
 *创建sql或者hql的工具类
 */
public class DAOUtility {
	
	/**
	 * 创建HQL工具类
	 * @param hibernateTemplate
	 * @return DAOHQLUtility
	 */
	public static DAOHQLUtility createHQL(HibernateTemplate hibernateTemplate) {
		
		return DAOHQLUtility.create(hibernateTemplate);
	}
	
	/**
	 * 创建SQL工具类
	 * @param hibernateTemplate
	 * @return DAOSQLUtility
	 */
	public static DAOSQLUtility createSQL(HibernateTemplate hibernateTemplate) {
		
		return DAOSQLUtility.create(hibernateTemplate);
	}
}
