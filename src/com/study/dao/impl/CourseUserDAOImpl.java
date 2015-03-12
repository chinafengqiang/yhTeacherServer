package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.study.dao.CourseUserDAO;
import com.study.model.CourseUser;
import com.study.utility.DAOUtility;

/**
 * 课程学员数据操作类
 */
@Repository
public class CourseUserDAOImpl implements CourseUserDAO {

	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public void insert(CourseUser instance) {

		this.hibernateTemplate.save(instance);		
	}

	public void update(CourseUser instance) {
		
		this.hibernateTemplate.update(instance);	
	}

	public void delete(CourseUser instance) {

		this.hibernateTemplate.delete(instance);
	}
	
	public void delete(Long id) {

		this.hibernateTemplate.delete(this.hibernateTemplate.get(CourseUser.class, id));
	}
	
	/**
	 * 删除课程的所有学员记录
	 * @param courseId 课程编号
	 */
	public void deleteListByCourse(Long courseId) {

		Object[] params = {courseId};
		String sql = "delete from course_user where course_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}
	
	/**
	 * 删除学员的所有课程学习记录
	 * @param userId 学员编号
	 */
	public void deleteListByUser(Long userId) {

		Object[] params = {userId};
		String sql = "delete from course_user where user_id=?";
		DAOUtility.createSQL(hibernateTemplate).setSql(sql).setParams(params).execute();
	}

}
