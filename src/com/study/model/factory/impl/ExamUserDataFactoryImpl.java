package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.model.ExamUserData;
import com.study.model.factory.ExamUserDataFactory;
import com.study.utility.ModelFactoryUtility;

/**
 * 考生考卷记录数据工厂实现类
 */
@Repository
public class ExamUserDataFactoryImpl implements ExamUserDataFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public ExamUserData findById(Long id) {
		
		return (ExamUserData)this.hibernateTemplate.get(ExamUserData.class, id);
	}
	
	/**
	 * 按考生编号获取考试答卷
	 * @param examUserId 考生编号
	 * @return 考试答卷
	 */
	public ExamUserData findByExamUser(Long examUserId) {
		
		String sql = "select a.* from exam_user_data a where a.exam_user_id=" + examUserId + "";
		return (ExamUserData)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUserData.class).object();
	}
	
	/**
	 * 获取所有记录
	 */
	public List<ExamUserData> findListByAll() {
		
		String sql = "select a.* from exam_user_data a";
		return (List<ExamUserData>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(ExamUserData.class).list();		
	}
}
