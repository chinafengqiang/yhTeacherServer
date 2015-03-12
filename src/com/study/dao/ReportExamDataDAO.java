package com.study.dao;

import java.util.List;

import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamData;

/**
 * 考试报表基础数据操作接口
 */
public interface ReportExamDataDAO {
	
	void insert(ReportExamData instance);

	void update(ReportExamData instance);

	void delete(ReportExamData instance);
	
	void delete(Long id);

	/**
	 * 删除考试报表的所有学员记录
	 * @param reportExamId 考试报表编号
	 */
	void deleteListByReportExamId(Long reportExamId);
	
	/**
	 * 删除考试报表某单位的所有学员记录
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 */
	void deleteListByReportExamId_OrganId(Long reportExamId, Long organId);
	
	/**
	 * 插入列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	void insertList(List<ReportExamData> list) throws Exception;
	
	/**
	 * 插入列表数据
	 * @param list 列表
	 * @throws Exception 
	 */
	void updateList(List<ReportExamData> list) throws Exception;
	
	
	/**
	 * 删除考试报表的所有报表记录
	 * @param reportExamId 考试报表编号
	 * @param sourceEnum 来源枚举
	 */
	void deleteListByReportExamId_Source(Long reportExamId, ReportExamDataSourceEnum sourceEnum);
}
