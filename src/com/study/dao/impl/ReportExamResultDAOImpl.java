package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ReportExamResultDAO;
import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamResult;
import com.study.utility.DAOUtility;

/**
 * 考试报表基础数据操作类
 */
@Repository
public class ReportExamResultDAOImpl implements ReportExamResultDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(ReportExamResult instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ReportExamResult instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ReportExamResult instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ReportExamResult.class, id));
	}
	
	/**
	 * 删除考试报表的所有报表记录
	 * @param reportExamId 考试报表编号
	 */
	public void deleteListByReportExamId(Long reportExamId) {

		Object[] params = {reportExamId};
		String sql = "delete from report_exam_result where report_exam_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}

}
