package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.model.CourseCategory;
import com.study.model.factory.CourseCategoryFactory;
import com.study.utility.ModelFactoryUtility;

/**
 * 课程分类记录数据工厂实现类
 */
@Repository
public class CourseCategoryFactoryImpl implements CourseCategoryFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public CourseCategory findById(Long id) {
		
		return (CourseCategory)this.hibernateTemplate.get(CourseCategory.class, id);
	}
	
	/**
	 * 获取顶级记录
	 * @return 记录
	 */
	public CourseCategory findTop() {
		
		String sql = "select a.* from course_category a where parent_id=0";
		return (CourseCategory)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseCategory.class).object();		
	}
	
	/**
	 * 获取所有记录
	 */
	public List<CourseCategory> findListByAll() {
		
		String sql = "select a.* from course_category a";
		return (List<CourseCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseCategory.class).list();		
	}
	
	/**
	 * 获取某分类节点的子节点数量
	 * @param parantId 父节点编号
	 * @return 子分类节点数量
	 */
	public Integer findCountByParant(Long parantId) {
		
		String sql = "select count(a.id) as countValue from course_category a where parent_id=" + parantId;
		return (Integer)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).uniqueResultInteger("countValue");
	}
	
	/**
	 * 获取分类树
	 * @return 分类树
	 */
	public List<CourseCategory> findListByTree() {
		
		String sql = "select a.* from course_category a order by a.parent_id asc, a.sort_flag desc";
		return (List<CourseCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseCategory.class).list();		
	}
	
	/**
	 * 获取分类节点的子节点数量
	 * @param parantId 父节点编号
	 * @return 子节点列表
	 */
	public List<CourseCategory> findListByParentId(Long parentId) {
		
		String sql = "select a.* from course_category a where parent_id=" + parentId + " order by a.parent_id asc, a.sort_flag desc";
		return (List<CourseCategory>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseCategory.class).list();		
	}
}
