package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamData;
import com.study.model.factory.ReportExamDataFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试报表基础数据记录数据工厂实现类
 */
@Repository
public class ReportExamDataFactoryImpl implements ReportExamDataFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ReportExamData findById(Long id) {
		
		return (ReportExamData)this.hibernateTemplate.get(ReportExamData.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ReportExamData> findListByAll() {
		
		String sql = "select a.* from report_exam_data a";
		return (List<ReportExamData>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamData.class).list();		
	}
	
	/**
	 * 按搜索条件获取教师平台的考生成绩分页列表
	 * @param reportExamId 考试报表编号
	 * @param actualOrgan 单位名称
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考生成绩分页列表
	 */
	public PaginateResult findListBySearch(Long reportExamId, String actualOrgan, String actualName, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from report_exam_data a where a.report_exam_id=" + reportExamId;
			
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and a.actual_organ like '%" + actualOrgan + "%'";
		}
		
		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_organ, a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamData.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 按搜索条件获取单位平台的考生成绩分页列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位名称
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考生成绩分页列表
	 */
	public PaginateResult findListByOrgan(Long reportExamId, Long organId, String actualOrgan, String actualName, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from report_exam_data a where a.report_exam_id=" + reportExamId;
			
		sql = sql + " and FIND_IN_SET(organ_id, getOrganChildIdList(" + organId + "))";
		
		if (!StringUtils.isBlank(actualOrgan)) {
			sql = sql + " and a.actual_organ like '%" + actualOrgan + "%'";
		}
		
		if (!StringUtils.isBlank(actualName)) {
			sql = sql + " and a.actual_name like '%" + actualName + "%'";
		}
		
		sql = sql + " order by a.actual_organ, a.actual_name";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamData.class).paginateResult(paginateParamters);		
	}
	
	/**
	 * 获取考试报表某单位的具体来源的考生记录数量
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param sourceEnum 来源
	 * @return 考生记录数量
	 */
	public Integer findCountByOrgan_Source(Long reportExamId, Long organId, ReportExamDataSourceEnum sourceEnum) {
		
		String sql = "select count(a.id) as countValue from report_exam_data a where report_exam_id=" + reportExamId + " and organ_id=" + organId + " and source=" + sourceEnum.toValue();
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
}
