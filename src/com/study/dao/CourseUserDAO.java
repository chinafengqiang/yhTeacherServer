package com.study.dao;

import com.study.model.CourseUser;

/**
 * 课程学员数据操作接口
 */
public interface CourseUserDAO {
	
	void insert(CourseUser instance);

	void update(CourseUser instance);

	void delete(CourseUser instance);
	
	void delete(Long id);

	/**
	 * 删除课程的所有学员记录
	 * @param courseId 课程编号
	 */
	void deleteListByCourse(Long courseId);
	
	/**
	 * 删除学员的所有课程学习记录
	 * @param userId 学员编号
	 */
	void deleteListByUser(Long userId);
}