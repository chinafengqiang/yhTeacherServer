package com.study.model.factory;

import java.util.List;

import com.study.enums.OrganTypeEnum;
import com.study.enums.ReportExamResultTypeEnum;
import com.study.model.ReportExamResult;

/**
 * 考试报表结果数据工厂接口
 */
public interface ReportExamResultFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ReportExamResult findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ReportExamResult> findListByAll();
	
	/**
	 * 按单位编号和考试报表项目编号统计
	 * @param organId 单位
	 * @param reportExamId 考试报表编号
	 * @return 
	 */
	ReportExamResult statByOrganId_ReportExamId(Long organId, Long reportExamId);
	
	/**
	 * 按单位编号和考试报表项目编号汇总本地级下级单位数据
	 * @param organId 单位
	 * @param reportExamId 考试报表编号
	 * @return 
	 */
	ReportExamResult statSumByOrganId_ReportExamId(Long organId, Long reportExamId);

	/**
	 * 按考试报表编号、单位编号、统计结果类型获取考试报表记录
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param resultTypeEnum 统计结果类型
	 * @return 考试报表结果记录
	 */
	ReportExamResult findByReportExamId_OrganId_ResultType(Long reportExamId, Long organId, ReportExamResultTypeEnum resultTypeEnum);
	
	/**
	 * 按考试报表编号、单位编号、统计结果类型获取下级单位的考试报表结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param resultTypeEnum 统计结果类型
	 * @param organTypeEnum 单位类型
	 * @return 考试报表结果记录
	 */
	List<ReportExamResult> findListByReportExamId_OrganId_OrganType_ResultType_Child(Long reportExamId, Long organId, ReportExamResultTypeEnum resultTypeEnum, OrganTypeEnum organTypeEnum);
}
