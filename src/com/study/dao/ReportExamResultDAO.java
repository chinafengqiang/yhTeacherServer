package com.study.dao;

import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamResult;

/**
 * 考试报表结果数据操作接口
 */
public interface ReportExamResultDAO {
	
	void insert(ReportExamResult instance);

	void update(ReportExamResult instance);

	void delete(ReportExamResult instance);
	
	void delete(Long id);

	/**
	 * 删除考试报表的所有报表记录
	 * @param reportExamId 考试报表编号
	 */
	void deleteListByReportExamId(Long reportExamId);

}
