package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.TestPaperQuestionDAO;
import com.study.model.TestPaperQuestion;
import com.study.utility.DAOUtility;

/**
 * 试卷题目数据操作类
 */
@Repository
public class TestPaperQuestionDAOImpl implements TestPaperQuestionDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(TestPaperQuestion instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(TestPaperQuestion instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(TestPaperQuestion instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(TestPaperQuestion.class, id));
	}

	/**
	 * 删除试卷的所有题目记录
	 * @param testPaperId 试卷编号
	 */
	public void deleteListByTestPaper(Long testPaperId) {

		Object[] params = {testPaperId};
		String sql = "delete from test_paper_question where test_paper_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
}
