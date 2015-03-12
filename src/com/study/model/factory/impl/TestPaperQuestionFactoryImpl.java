package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.model.TestPaperQuestion;
import com.study.model.factory.TestPaperQuestionFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 试卷题目记录数据工厂实现类
 */
@Repository
public class TestPaperQuestionFactoryImpl implements TestPaperQuestionFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public TestPaperQuestion findById(Long id) {
		
		return (TestPaperQuestion)this.hibernateTemplate.get(TestPaperQuestion.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<TestPaperQuestion> findListByAll() {
		
		String sql = "select a.* from test_paper_question a";
		return (List<TestPaperQuestion>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaperQuestion.class).list();		
	}
	
	/**
	 * 获取试卷的重复题目的数量
	 * @param testPaperId 试卷编号编号
	 * @return 题目数量
	 */
	public Integer findCountByRepeatQuestion(Long testPaperId, Integer series, QuestionTypeEnum questionTypeEnum, String name, String options) {
		
		String sql = "select count(a.id) as countValue from test_paper_question a where a.test_paper_id=" + testPaperId + " and series=" + series;
		
		sql = sql + " and a.question_type=" + questionTypeEnum.toValue();
		sql = sql + " and a.name='" + name + "'";
		sql = sql + " and a.options='" + options + "'";
		
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 获取某题型的试卷题目的数量
	 * @param testPaperId 试卷编号编号
	 * @return 题目数量
	 */
	public Integer findCountByQuestionType(Long testPaperId, Integer series, QuestionTypeEnum questionTypeEnum) {
		
		String sql = "select count(a.id) as countValue from test_paper_question a where a.test_paper_id=" + testPaperId + " and series=" + series;
		sql = sql + " and a.question_type=" + questionTypeEnum.toValue();
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 获取试卷的题目的总分数
	 * @param testPaperId 试卷编号编号
	 * @return 题目的分数
	 */
	public Double findSumScoreBySeries(Long testPaperId, Integer series) {
		
		String sql = "select ifnull(sum(a.score), 0) as sumValue from test_paper_question a where a.test_paper_id=" + testPaperId + " and series=" + series;
		return (Double)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultDouble("sumValue");
	}
	
	/**
	 * 获取试卷的所有记录
	 * @param testPaperId 试卷编号编号
	 * @return 题目列表
	 */
	public List<TestPaperQuestion> findListByTestPaper(Long testPaperId) {
		
		String sql = "select a.* from test_paper_question a where test_paper_id=" + testPaperId;
		sql = sql + " order by a.series, a.question_type, a.sort_flag";
		return (List<TestPaperQuestion>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaperQuestion.class).list();		
	}
	
	/**
	 * 获取试卷的所有记录
	 * @param testPaperId 试卷编号编号
	 * @param series 套数
	 * @return 题目列表
	 */
	public List<TestPaperQuestion> findListByTestPaperId_Series(Long testPaperId, Integer series) {
		
		String sql = "select a.* from test_paper_question a where test_paper_id=" + testPaperId + " and series=" + series;
		sql = sql + " order by a.name ";
		return (List<TestPaperQuestion>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaperQuestion.class).list();		
	}
	
	/**
	 * 获取试卷套数的所有题目记录
	 * @param testPaperId 试卷编号编号
	 * @param series 套数
	 * @return 题目
	 */
	public List<Question> findListByTestPaper_Series(Long testPaperId, Integer series) {
		
		String sql = "select a.id, a.name, 0 as question_category_id, a.question_type, a.options, a.answer, a.ken, a.difficulty, a.score, a.note from test_paper_question a where test_paper_id=" + testPaperId + " and series=" + series;
		return (List<Question>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Question.class).list();		
	}
	
	/**
	 * 按搜索条件获取试卷题目分页列表
	 * @param testPaperId 试卷编号
	 * @param series 题目套数
	 * @param name 名称
	 * @param questionTypeEnum 题型
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @param paginateParamters 分页参数
	 * @return 试卷题目分页列表
	 */
	public PaginateResult findListBySearch(Long testPaperId, Integer series, String name, QuestionTypeEnum questionTypeEnum, Integer difficulty, String ken, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from test_paper_question a where a.test_paper_id=" + testPaperId + " and a.series=" + series;
				
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		if (!StringUtils.isBlank(ken)) {
			sql = sql + " and a.ken like '%" + ken + "%'";
		}
		
		if (difficulty != null) {
			sql = sql + " and a.difficulty=" + difficulty;
		}

		if (questionTypeEnum != null) {
			sql = sql + " and a.question_type=" + questionTypeEnum.toValue();
		}
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(TestPaperQuestion.class).paginateResult(paginateParamters);		
	}
}
