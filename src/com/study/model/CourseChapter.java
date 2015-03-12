package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 课程章节数据类
 */
@Entity
@Table(name="course_chapter")
public class CourseChapter implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -6448560023168182496L;
	
	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 标题
	 */
	private String name;
	
	/**
	 * 课程编号
	 */
	@Column(name="course_id")
	private Long courseId;
	
	/**
	 * 章节内容
	 */
	private String content;
	
	/**
	 * 排序
	 */
	@Column(name="sort_flag")
	private Integer sortFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}
	
}
