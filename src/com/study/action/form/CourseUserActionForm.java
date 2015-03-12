package com.study.action.form;

import com.study.model.Course;
import com.study.model.CourseCategory;



/**
 * 课程 ActionForm
 */
public class CourseUserActionForm extends BaseActionForm {
	
	/**
	 * 课程编号
	 */
	private Long courseId;
	
	/**
	 * 课程类型
	 */
	private Integer courseType;
	
	/**
	 * 状态 
	 */
	private Integer status;
	
	/**
	 * 课程名称 
	 */
	private String name;
	
	/**
	 * 课程分类数据
	 */
	private CourseCategory courseCategory;
	
	/**
	 * 课程
	 */
	private Course course;
	
	/**
	 * 课程分类编号
	 */
	private Long courseCategoryId;
	
	/**
	 * 课程学员编号
	 */
	private Long courseUserId;
	
	/**
	 * 所属单位
	 */
	private String actualOrgan;
	
	/**
	 * 姓名
	 */
	private String actualName;
	
	/**
	 * 学员范围
	 */
	private String userJoinedData;

	public CourseCategory getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Long getCourseCategoryId() {
		return courseCategoryId;
	}

	public void setCourseCategoryId(Long courseCategoryId) {
		this.courseCategoryId = courseCategoryId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}

	public Integer getCourseType() {
		return courseType;
	}

	public void setCourseUserId(Long courseUserId) {
		this.courseUserId = courseUserId;
	}

	public Long getCourseUserId() {
		return courseUserId;
	}

	public void setActualOrgan(String actualOrgan) {
		this.actualOrgan = actualOrgan;
	}

	public String getActualOrgan() {
		return actualOrgan;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getActualName() {
		return actualName;
	}

	public void setUserJoinedData(String userJoinedData) {
		this.userJoinedData = userJoinedData;
	}

	public String getUserJoinedData() {
		return userJoinedData;
	}
	
	
}
