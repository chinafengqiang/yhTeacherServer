package com.study.model.factory;

import java.util.List;

import com.study.model.CourseChapter;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程章节数据工厂接口
 */
public interface CourseChapterFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	CourseChapter findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<CourseChapter> findListByAll();
	
	/**
	 * 获取课程的所有记录
	 */
	List<CourseChapter> findListByCourse(Long courseId);

	/**
	 * 获取教师平台的课程章节分页列表
	 * @param courseId 课程编号
	 * @param paginateParamters 分页参数
	 * @return 课程章节分页列表
	 */
	PaginateResult findListByCourse_Manager(Long courseId, PaginateParamters paginateParamters);

}
