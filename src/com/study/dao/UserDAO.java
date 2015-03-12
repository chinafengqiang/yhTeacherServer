package com.study.dao;

import com.study.model.User;

/**
 * 学员数据操作接口
 */
public interface UserDAO {
	
	void insert(User instance);

	void update(User instance);

	void delete(User instance);
	
	void delete(Long id);

}
