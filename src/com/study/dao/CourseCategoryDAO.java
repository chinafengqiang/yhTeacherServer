package com.study.dao;

import com.study.model.CourseCategory;

/**
 * 课程分类数据操作接口
 */
public interface CourseCategoryDAO {
	
	void insert(CourseCategory instance);

	void update(CourseCategory instance);

	void delete(CourseCategory instance);
	
	void delete(Long id);

}
