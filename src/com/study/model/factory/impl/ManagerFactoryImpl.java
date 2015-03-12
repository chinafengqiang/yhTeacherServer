package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ManagerGradeEnum;
import com.study.model.Manager;
import com.study.model.factory.ManagerFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 管理员记录数据工厂实现类
 */
@Repository
public class ManagerFactoryImpl implements ManagerFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Manager findById(Long id) {
		
		return (Manager)this.hibernateTemplate.get(Manager.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Manager> findListByAll() {
		
		String sql = "select a.* from manager a";
		return (List<Manager>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Manager.class).list();		
	}
	
	/**
	 * 按登录帐号获取管理员记录
	 * @param name 登录帐号
	 * @return 管理员
	 */
	public Manager findByName(String name) {
		
		String sql = "select a.* from manager a where a.name='" + name + "'";
		return (Manager)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Manager.class).object();
	}
	
	/**
	 * 获取最高管理员记录
	 * @return 管理员
	 */
	public Manager findByTop() {
		
		String sql = "select * from manager where id=(select min(id) from manager)";
		return (Manager)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Manager.class).object();
	}
	
	
	
	/**
	 * 按真实姓名获取管理员记录
	 * @param actualName 真实姓名
	 * @return 管理员
	 */
	public Manager findByActualName(String actualName) {
		
		String sql = "select a.* from manager a where a.actual_name='" + actualName + "'";
		return (Manager)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Manager.class).object();
	}
	
	/**
	 * 按搜索条件获取教师分页列表
	 * @param gradeEnum 级别枚举
	 * @param name 帐号
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 教师分页列表
	 */
	public PaginateResult findListBySearch(ManagerGradeEnum gradeEnum, String name, String actualName, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from manager a where 1=1 ";
		
		if (gradeEnum != null) {
			sql = sql + " and a.grade=" + gradeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}

		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Manager.class).paginateResult(paginateParamters);		
	}
}
