package com.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 课程分类数据类
 */
@Entity
@Table(name="course_category")
public class CourseCategory implements java.io.Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2455066575287581937L;

	/**
	 * 编号
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)		
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 上级编号 
	 */
	@Column(name="parent_id")
	private Long parentId;
	
	/**
	 * 排序
	 */
	@Column(name="sort_flag")
	private Integer sortFlag;
	
	/**
	 * 层次
	 */
	private Integer level;

	//==================扩展字段 Begin ==================
	
	/**
	 * 带中文空格的分类名称
	 */
	@Transient
	public String getLevelName() {
		
		String ch = "";
		for (int i=2; i<=level; i++) {
			ch = ch + "　　";
		}
		return ch + this.name;
	}
	
	//==================扩展字段 End ==================
	
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Integer sortFlag) {
		this.sortFlag = sortFlag;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return level;
	}

}
