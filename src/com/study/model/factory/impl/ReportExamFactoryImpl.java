package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ReportExamStatusEnum;
import com.study.model.ReportExam;
import com.study.model.factory.ReportExamFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试报表记录数据工厂实现类
 */
@Repository
public class ReportExamFactoryImpl implements ReportExamFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ReportExam findById(Long id) {
		
		return (ReportExam)this.hibernateTemplate.get(ReportExam.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ReportExam> findListByAll() {
		
		String sql = "select a.* from report_exam a";
		return (List<ReportExam>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExam.class).list();		
	}
	
	/**
	 * 获取所有启动考试报表记录
	 */
	public List<ReportExam> findListByOpened() {
		
		String sql = "select a.* from report_exam a where a.status=" + ReportExamStatusEnum.Opened.toValue();
		return (List<ReportExam>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExam.class).list();		
	}
	
	/**
	 * 按考试代号获取考试报表记录
	 * @param examCode 考试代号
	 * @return 考试报表记录
	 */
	public ReportExam findByExamCode(String examCode) {
		
		String sql = "select a.* from report_exam a where a.exam_code='" + examCode + "'";
		return (ReportExam)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExam.class).object();
	}
	
	/**
	 * 按搜索条件获取考试报表分页列表
	 * @param name 名称
	 * @param examCode 考试代号
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 考试报表分页列表
	 */
	public PaginateResult findListBySearch(String name, String examCode, ReportExamStatusEnum statusEnum, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from report_exam a where 1=1";
		
		if (statusEnum != null) {
			sql = sql + " and a.status=" + statusEnum.toValue();
		}
		
		if (!StringUtils.isBlank(examCode)) {
			sql = sql + " and a.exam_code like '%" + examCode + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExam.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取单位平台中的考试报表分页列表
	 * @param name 名称
	 * @param examCode 考试代号
	 * @param paginateParamters 分页参数
	 * @return 考试报表分页列表
	 */
	public PaginateResult findListByOrgan(String name, String examCode, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from report_exam a where a.status=" + ReportExamStatusEnum.Opened.toValue();
		
		if (!StringUtils.isBlank(examCode)) {
			sql = sql + " and a.exam_code like '%" + examCode + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExam.class).paginateResult(paginateParamters);		
	}
}
