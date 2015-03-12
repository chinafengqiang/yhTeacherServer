package com.study.model.part;


/**
 * 学员参与数据类
 */
public class UserJoinedItem implements java.io.Serializable {
	
	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
