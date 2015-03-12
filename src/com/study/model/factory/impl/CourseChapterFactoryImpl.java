package com.study.model.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.model.CourseChapter;
import com.study.model.factory.CourseChapterFactory;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程章节记录数据工厂实现类
 */
@Repository
public class CourseChapterFactoryImpl implements CourseChapterFactory {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	public CourseChapter findById(Long id) {
		
		return (CourseChapter)this.hibernateTemplate.get(CourseChapter.class, id);
	}
	
	/**
	 * 获取所有记录
	 */
	public List<CourseChapter> findListByAll() {
		
		String sql = "select a.* from course_chapter a";
		return (List<CourseChapter>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseChapter.class).list();		
	}
	
	/**
	 * 获取课程的所有记录
	 */
	public List<CourseChapter> findListByCourse(Long courseId) {
		
		String sql = "select a.* from course_chapter a where course_id=" + courseId;
		sql = sql + " order by a.sort_flag, a.id";
		return (List<CourseChapter>)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseChapter.class).list();		
	}
	
	/**
	 * 获取教师平台的课程章节分页列表
	 * @param courseId 课程编号
	 * @param paginateParamters 分页参数
	 * @return 课程章节分页列表
	 */
	public PaginateResult findListByCourse_Manager(Long courseId, PaginateParamters paginateParamters) {
		
		String sql = "select a.* from course_chapter a where course_id=" + courseId;
		sql = sql + " order by a.sort_flag, a.id";
		
		return (PaginateResult)ModelFactoryUtility.createSQL(hibernateTemplate).setSql(sql).setEntityCalss(CourseChapter.class).paginateResult(paginateParamters);		
	}
}
