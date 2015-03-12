package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.model.CourseCategory;
import com.study.model.QuestionCategory;
import com.study.model.factory.QuestionCategoryFactory;
import com.study.utility.ModelFactoryUtility;

/**
 * 题目分类记录数据工厂实现类
 */
@Repository
public class QuestionCategoryFactoryImpl implements QuestionCategoryFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public QuestionCategory findById(Long id) {
		
		return (QuestionCategory)this.hibernateTemplate.get(QuestionCategory.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<QuestionCategory> findListByAll() {
		
		String sql = "select a.* from question_category a";
		return (List<QuestionCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(QuestionCategory.class).list();		
	}
	
	/**
	 * 获取某分类节点的子单位数量
	 * @param parantId 父单位编号
	 * @return 子分类节点数量
	 */
	public Integer findCountByParant(Long parantId) {
		
		String sql = "select count(a.id) as countValue from question_category a where parent_id=" + parantId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 获取分类树
	 * @return 分类树
	 */
	public List<QuestionCategory> findListByTree() {
		
		String sql = "select a.* from question_category a order by a.parent_id asc, a.sort_flag desc";
		return (List<QuestionCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(QuestionCategory.class).list();		
	}
	
	/**
	 * 获取顶级记录
	 * @return 记录
	 */
	public QuestionCategory findTop() {
		
		String sql = "select a.* from question_category a where parent_id=0";
		return (QuestionCategory)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(QuestionCategory.class).object();		
	}
	
	/**
	 * 获取分类节点的子节点数量
	 * @param parantId 父节点编号
	 * @return 子节点列表
	 */
	public List<QuestionCategory> findListByParentId(Long parentId) {
		
		String sql = "select a.* from question_category a where parent_id=" + parentId + " order by a.parent_id asc, a.sort_flag desc";
		return (List<QuestionCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(QuestionCategory.class).list();		
	}
}
