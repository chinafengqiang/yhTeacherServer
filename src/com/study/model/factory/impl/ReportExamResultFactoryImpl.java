package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.enums.OrganTypeEnum;
import com.study.enums.ReportExamDataStatusEnum;
import com.study.enums.ReportExamResultTypeEnum;
import com.study.model.ReportExamResult;
import com.study.model.factory.ReportExamResultFactory;
import com.study.utility.ModelFactoryUtility;

/**
 * 考试报表结果记录数据工厂实现类
 */
@Repository
public class ReportExamResultFactoryImpl implements ReportExamResultFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ReportExamResult findById(Long id) {
		
		return (ReportExamResult)this.hibernateTemplate.get(ReportExamResult.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ReportExamResult> findListByAll() {
		
		String sql = "select a.* from report_exam_result a";
		return (List<ReportExamResult>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamResult.class).list();		
	}
	
	/**
	 * 按考试报表编号、单位编号、统计结果类型获取考试报表记录
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param resultTypeEnum 统计结果类型
	 * @return 考试报表结果记录
	 */
	public ReportExamResult findByReportExamId_OrganId_ResultType(Long reportExamId, Long organId, ReportExamResultTypeEnum resultTypeEnum) {
		
		String sql = "select a.* from report_exam_result a where a.report_exam_id=" + reportExamId + " and organ_id=" + organId + " and result_type=" + resultTypeEnum.toValue();
		return (ReportExamResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamResult.class).object();
	}
	
	/**
	 * 按考试报表编号、单位编号、统计结果类型获取下级单位的考试报表结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param resultTypeEnum 统计结果类型
	 * @param organTypeEnum 单位类型
	 * @return 考试报表结果记录
	 */
	public List<ReportExamResult> findListByReportExamId_OrganId_OrganType_ResultType_Child(Long reportExamId, Long organId, ReportExamResultTypeEnum resultTypeEnum, OrganTypeEnum organTypeEnum) {
		
		String sql = "select a.* from report_exam_result a where a.report_exam_id=" + reportExamId + " and parent_organ_id=" + organId + " and result_type=" + resultTypeEnum.toValue() + " and organ_type=" + organTypeEnum.toValue();
		return (List<ReportExamResult>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamResult.class).list();
	}
	
	/**
	 * 按单位编号和考试报表项目编号统计
	 * @param organId 单位
	 * @param reportExamId 考试报表编号
	 * @return 
	 */
	public ReportExamResult statByOrganId_ReportExamId(Long organId, Long reportExamId) {
		
		String sql = "select 1 as id, " + reportExamId + " as report_exam_id, a.parent_id as parent_organ_id, a.id as organ_id, a.actual_name as organ_name, a.organ_type, " + ReportExamResultTypeEnum.Self.toValue() + " as result_type," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id) as total_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and status<>" + ReportExamDataStatusEnum.None.toValue() + " and status<>" + ReportExamDataStatusEnum.Leave.toValue() + ") as joined_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and status=" + ReportExamDataStatusEnum.Pass.toValue() + ") as passed_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and score<=59) as score_59_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and score>=60 and score<70) as score_60to69_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and score>=70 and score<80) as score_70to79_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and score>=80 and score<90) as score_80to89_num," +
				" (select count(id) from report_exam_data where report_exam_id=" + reportExamId + " and organ_id=a.id and score>=90) as score_90_num " +
				" from organ a where a.id=" + organId;
		
		ReportExamResult result = (ReportExamResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamResult.class).object();
		
		if (result != null) {
			result.setId(null);
		}
		
		return result; 
	}
	
	/**
	 * 按单位编号和考试报表项目编号汇总本地级下级单位数据
	 * @param organId 单位
	 * @param reportExamId 考试报表编号
	 * @return 
	 */
	public ReportExamResult statSumByOrganId_ReportExamId(Long organId, Long reportExamId) {
				
		String sql = "select 1 as id, " + reportExamId + " as report_exam_id, a.parent_id as parent_organ_id, a.id as organ_id, a.actual_name as organ_name, a.organ_type, " + ReportExamResultTypeEnum.All.toValue() + " as result_type," +
				" (select ifnull(sum(total_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as total_num," +
				" (select ifnull(sum(joined_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as joined_num," +
				" (select ifnull(sum(passed_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as passed_num, " +
				" (select ifnull(sum(score_59_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as score_59_num, " +
				" (select ifnull(sum(score_60to69_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as score_60to69_num," +
				" (select ifnull(sum(score_70to79_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as score_70to79_num, " +
				" (select ifnull(sum(score_80to89_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as score_80to89_num," +
				" (select ifnull(sum(score_90_num),0) from report_exam_result where report_exam_id=" + reportExamId + " and result_type=" + ReportExamResultTypeEnum.Self.toValue() + " and organ_id in (select id from organ where FIND_IN_SET(id, getOrganChildIdList(a.id)))) as score_90_num" +
				" from organ a where a.id=" + organId;
		
		ReportExamResult result = (ReportExamResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ReportExamResult.class).object();
		
		if (result != null) {
			result.setId(null);
		}
		
		return result; 
	}
}
