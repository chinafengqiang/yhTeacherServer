package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.model.factory.QuestionFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 题目记录数据工厂实现类
 */
@Repository
public class QuestionFactoryImpl implements QuestionFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public Question findById(Long id) {
		
		return (Question)this.hibernateTemplate.get(Question.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<Question> findListByAll() {
		
		String sql = "select a.* from question a";
		return (List<Question>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Question.class).list();		
	}
	
	/**
	 * 获取题目分类的所有题目
	 * @param questionCategoryId 题目分类编号
	 * @return 题目分类的所有题目
	 */
	public List<Question> findListByQuestionCategory(Long questionCategoryId) {
		
		String sql = "select a.* from question a where a.question_category_id=" + questionCategoryId;
		return (List<Question>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Question.class).list();		
	}
	
	/**
	 * 自动挑题
	 * @param questionCategoryId 题目分类编号
	 * @param questionTypeEnum 题型
	 * @param difficulty 难度
	 * @param score 分数
	 * @param ken 知识点
	 * @param number 数量
	 * @return 题目列表
	 */
	public List<Question> findListByAutoSelect(Long questionCategoryId, QuestionTypeEnum questionTypeEnum, 
			Integer difficulty, Double score, String ken, Integer number) {
		
		String sql = "select a.* from question a where a.question_category_id=" + questionCategoryId + " and question_type=" + questionTypeEnum.toValue();
		
		if (difficulty != null) {
			sql = sql + " and a.difficulty=" + difficulty;
		}
		
		if (score != null) {
			sql = sql + " and a.score=" + score;
		}
		
		if (!StringUtils.isBlank(ken)) {
			sql = sql + " and a.ken like '%" + ken + "%'";
		}
		
		sql = sql + " order by rand()";
		sql = sql + " limit " + number;
		
		return (List<Question>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Question.class).list();		
	}
	
	/**
	 * 获取某分类节点的题目数量
	 * @param questionCategoryId 分类节点编号
	 * @return 题目数量
	 */
	public Integer findCountByQuestionCategory(Long questionCategoryId) {
		
		String sql = "select count(a.id) as countValue from question a where a.question_category_id=" + questionCategoryId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 获取某分类节点的重复题目的数量
	 * @param questionCategoryId 分类节点编号
	 * @return 题目数量
	 */
	public Integer findCountByRepeatQuestion(Long questionCategoryId, Long questionId, QuestionTypeEnum questionTypeEnum, String name, String options) {
		
		String sql = "select count(a.id) as countValue from question a where a.question_category_id=" + questionCategoryId;
		
		sql = sql + " and a.question_type=" + questionTypeEnum.toValue();
		sql = sql + " and a.name='" + name + "'";
		sql = sql + " and a.options='" + options + "'";
		
		if (questionId != null) {
			sql = sql + " and a.id<>" + questionId;
		}
		
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 按搜索条件获取题目分页列表
	 * @param questionCategoryId 题目分类编号
	 * @param questionTypeEnum 类型
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @param name 题目
	 * @param paginateParamters 分页参数
	 * @return 题目分页列表
	 */
	public PaginateResult findListBySearch(Long questionCategoryId, QuestionTypeEnum questionTypeEnum, Integer difficulty, String ken, String name, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from question a where question_category_id=" + questionCategoryId;
		
		if (questionTypeEnum != null) {
			sql = sql + " and a.question_type=" + questionTypeEnum.toValue();
		}
		
		if (!StringUtils.isBlank(ken)) {
			sql = sql + " and a.ken like '%" + ken + "%'";
		}
		
		if (!StringUtils.isBlank(name)) {
			sql = sql + " and a.name like '%" + name + "%'";
		}
		
		if (difficulty != null) {
			sql = sql + " and a.difficulty=" + difficulty;
		}
		
		sql = sql + " order by a.id desc";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(Question.class).paginateResult(paginateParamters);		
	}
	
}
