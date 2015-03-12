package com.study.model.factory;

import java.util.List;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.model.Course;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程数据工厂接口
 */
public interface CourseFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Course findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Course> findListByAll();

	/**
	 * 获取某分类节点的课程数量
	 * @param courseCategoryId 分类节点编号
	 * @return 课程数量
	 */
	Integer findCountByCourseCategory(Long courseCategoryId);
	
	/**
	 * 按本地标示获取课程记录
	 * @param courseKey 课程本地标示
	 * @return 课程
	 */
	Course findByCourseKey(String courseKey);
	
	/**
	 * 按搜索条件获取课程分页列表
	 * @param courseCategoryId 分类编号
	 * @param courseTypeEnum 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	PaginateResult findListBySearch(Long courseCategoryId, CourseTypeEnum courseTypeEnum, CourseStatusEnum statusEnum, String name, PaginateParamters paginateParamters);

	/**
	 * 按搜索条件获取课程分页列表
	 * @param courseCategoryId 分类编号
	 * @param courseTypeEnum 类型
	 * @param name 名称
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	PaginateResult findListByOrgan(Long courseCategoryId, CourseTypeEnum courseTypeEnum, String name, Long organId, PaginateParamters paginateParamters);
		
	/**
	 * 获取学员平台的课程分页列表
	 * @param userId 学员编号
	 * @param paginateParamters 分页参数
	 * @return 课程分页列表
	 */
	PaginateResult findListByUser(Long userId, PaginateParamters paginateParamters);
	
	/**
	 * 按课程类型和状态获取课程数量
	 * @param courseTypeEnum 课程类型
	 * @param statusEnum 状态
	 * @return 课程数量
	 */
	Integer findCountByCourseType_Status(CourseTypeEnum courseTypeEnum, CourseStatusEnum statusEnum);
}
