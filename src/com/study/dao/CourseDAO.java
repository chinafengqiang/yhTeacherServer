package com.study.dao;

import com.study.model.Course;

/**
 * 课程数据操作接口
 */
public interface CourseDAO {
	
	void insert(Course instance);

	void update(Course instance);

	void delete(Course instance);
	
	void delete(Long id);

}
