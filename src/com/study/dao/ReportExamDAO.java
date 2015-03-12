package com.study.dao;

import com.study.model.ReportExam;

/**
 * 考试报表数据操作接口
 */
public interface ReportExamDAO {
	
	void insert(ReportExam instance);

	void update(ReportExam instance);

	void delete(ReportExam instance);
	
	void delete(Long id);

}
