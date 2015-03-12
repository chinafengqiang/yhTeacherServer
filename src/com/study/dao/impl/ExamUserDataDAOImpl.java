package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.ExamUserDataDAO;
import com.study.model.ExamUserData;
import com.study.utility.DAOUtility;

/**
 * 考生考卷数据操作类
 */
@Repository
public class ExamUserDataDAOImpl implements ExamUserDataDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(ExamUserData instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(ExamUserData instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(ExamUserData instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(ExamUserData.class, id));
	}
	
	/**
	 * 删除考试的所有考卷记录
	 * @param examId 考试编号
	 */
	public void deleteListByExam(Long examId) {

		Object[] params = {examId};
		String sql = "delete from exam_user_data where exam_user_id in (select id from exam_user where exam_id=?)";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
	
	/**
	 * 删除考生的考卷记录
	 * @param examUserId 考试编号
	 */
	public void deleteByExamUserId(Long examUserId) {

		Object[] params = {examUserId};
		String sql = "delete from exam_user_data where exam_user_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}

}
