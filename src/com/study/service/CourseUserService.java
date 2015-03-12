package com.study.service;

import java.io.File;

import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.model.Course;
import com.study.model.CourseCategory;
import com.study.model.CourseChapter;
import com.study.model.CourseUser;
import com.study.model.User;


/**
 * 课程业务接口
 */
public interface CourseUserService {

	/**
	 * 创建课程学员
	 * @param course 课程
	 * @param user 学员
	 * @return 课程学员
	 */
	CourseUser createCourseUser(Course course, User user);
		
	/**
	 * 校验学习
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	void confirmStudy(Long courseId, Long userId) throws Exception;
		
	/**
	 * 学习课程
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	void beginStudy(Long courseId, Long userId) throws Exception;
	
	/**
	 * 结束本次学习
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	void endStudy(Long courseId, Long userId) throws Exception;
	
	/**
	 * 完成课程
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception 
	 */
	void finishStudy(Long courseId, Long userId) throws Exception;
	
	/**
	 * 在教师平台删除课程学员
	 * @param courseUserId 课程学员编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception 
	 */
	void removeCourseUserByManager(Long courseUserId, Long curManagerId) throws Exception;

	/**
	 * 单位管理员删除课程学员
	 * @param courseUserId 课程学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void removeCourseUserByOrgan(Long courseUserId, Long curOrganId) throws Exception;

}
