package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.CourseUserStatusEnum;
import com.study.model.CourseUser;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 课程学员数据工厂接口
 */
public interface CourseUserFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	CourseUser findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<CourseUser> findListByAll();
	
	/**
	 * 获取课程的学员数量
	 * @param courseId 课程编号
	 * @return 课程学员数量
	 */
	Integer findCountByCourse(Long courseId);
	
	/**
	 * 按状态获取学员的课程数量
	 * @param userId 学员编号
	 * @param statusEnum 状态
	 * @return 课程数量
	 */
	Integer findCountByUser_Status(Long userId, CourseUserStatusEnum statusEnum);
	
	/**
	 * 按课程编号和学员编号获取课程学员记录
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @return 课程学员记录
	 */
	CourseUser findByCourseId_UserId(Long courseId, Long userId);
	
	/**
	 * 获取教师平台的课程学员分页列表
	 * @param courseId 课程编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 课程学员分页列表
	 */
	PaginateResult findListByCourse_Manager(Long courseId, String actualOrgan, String actualName, PaginateParamters paginateParamters);
	
	/**
	 * 获取单位平台的课程学员分页列表
	 * @param courseId 课程编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 课程学员分页列表
	 */
	PaginateResult findListByCourse_Organ(Long courseId, Long organId, String actualOrgan, String actualName, CourseUserStatusEnum statusEnum, PaginateParamters paginateParamters);
	
	/**
	 * 获取学员的所有课程的学分总和
	 * @param testPaperId 试卷编号编号
	 * @return 学分总和
	 */
	Integer findSumCreditByUserId(Long userId);
	
	/**
	 * 获取学员的课程学习记录
	 * @param userId 学员编号
	 * @return 课程学习记录列表
	 */
	List<CourseUser> findListByUserId(Long userId);

}
