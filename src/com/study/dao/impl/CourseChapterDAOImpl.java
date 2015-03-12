package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.CourseChapterDAO;
import com.study.model.CourseChapter;
import com.study.utility.DAOUtility;

/**
 * 课程章节数据操作类
 */
@Repository
public class CourseChapterDAOImpl implements CourseChapterDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(CourseChapter instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(CourseChapter instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(CourseChapter instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(CourseChapter.class, id));
	}
	
	/**
	 * 删除课程的所有章节记录
	 * @param courseId 课程编号
	 */
	public void deleteListByCourse(Long courseId) {

		Object[] params = {courseId};
		String sql = "delete from course_chapter where course_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}

}
