package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.enums.SysParamTypeEnum;
import com.study.model.SysParam;
import com.study.model.factory.SysParamFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 系统参数记录数据工厂实现类
 */
@Repository
public class SysParamFactoryImpl implements SysParamFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public SysParam findById(Long id) {
		
		return (SysParam)this.hibernateTemplate.get(SysParam.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<SysParam> findListByAll() {
		
		String sql = "select a.* from sys_param a";
		return (List<SysParam>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(SysParam.class).list();		
	}
	
	/**
	 * 按参数类型获取系统参数
	 * @param paramTypeEnum 系统参数类型
	 * @return 系统参数
	 */
	public SysParam findByParamType(SysParamTypeEnum sysParamTypeEnum) {
		
		String sql = "select a.* from sys_param a where sys_param_type=" + sysParamTypeEnum.toValue();
		return (SysParam)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(SysParam.class).object();
	}
	
	/**
	 * 获取系统参数分页列表
	 * @param paginateParamters 分页参数
	 * @return 系统参数分页列表
	 */
	public PaginateResult findList(PaginateParamters paginateParamters) {
		
		String sql = "select a.* from sys_param a where 1=1 ";
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(SysParam.class).paginateResult(paginateParamters);		
	}
}
