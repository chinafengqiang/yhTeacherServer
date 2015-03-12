package com.study.dao;

import com.study.model.CourseChapter;

/**
 * 课程章节数据操作接口
 */
public interface CourseChapterDAO {
	
	void insert(CourseChapter instance);

	void update(CourseChapter instance);

	void delete(CourseChapter instance);
	
	void delete(Long id);

	/**
	 * 删除课程的所有章节记录
	 * @param courseId 课程编号
	 */
	void deleteListByCourse(Long courseId);
}
