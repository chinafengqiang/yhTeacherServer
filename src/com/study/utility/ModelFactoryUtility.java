package com.study.utility;

import org.springframework.orm.hibernate3.HibernateTemplate;
/**
 * 
 * 
 *创建sql或者hql的工具类
 */
public class ModelFactoryUtility {
	
	/**
	 * 创建HQL工具类
	 * @param hibernateTemplate
	 * @return ModelFactoryHQLUtility
	 */
	public static ModelFactoryHQLUtility createHQL(HibernateTemplate hibernateTemplate) {
		
		return ModelFactoryHQLUtility.create(hibernateTemplate);
	}
	
	/**
	 * 创建SQL工具类
	 * @param hibernateTemplate
	 * @return ModelFactorySQLUtility
	 */
	public static ModelFactorySQLUtility createSQL(HibernateTemplate hibernateTemplate) {
		
		return ModelFactorySQLUtility.create(hibernateTemplate);
	}
}
