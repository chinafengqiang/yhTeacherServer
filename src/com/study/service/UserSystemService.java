package com.study.service;

import java.util.List;

import com.study.model.Course;
import com.study.model.Exam;
import com.study.model.us.USCourseSummary;
import com.study.model.us.USExamSummary;



/**
 * 学员平台业务接口
 */
public interface UserSystemService {
	
	/**
	 * 生成学员平台Html文件
	 * @throws Exception 
	 * @throws Exception
	 */
	void genarateHtmls() throws Exception;
	
	/**
	 * 获取学员平台用户课程学员摘要列表
	 * @param userId 学员编号
	 * @param courseList 课程列表
	 * @return
	 */
	List<USCourseSummary> getUSCourseSummary(Long userId, List<Course> courseList) throws Exception;
	
	/**
	 * 获取学员平台用户考试摘要列表
	 * @param userId 学员编号
	 * @param examList 考试列表
	 * @return
	 */
	List<USExamSummary> getUSExamSummary(Long userId, List<Exam> examList) throws Exception;
}
