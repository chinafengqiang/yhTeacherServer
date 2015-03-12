package com.study.dao;

import com.study.model.Manager;

/**
 * 管理员数据操作接口
 */
public interface ManagerDAO {
	
	void insert(Manager instance);

	void update(Manager instance);

	void delete(Manager instance);
	
	void delete(Long id);

}
