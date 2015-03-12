package com.study.dao;

import com.study.model.Organ;

/**
 * 单位数据操作接口
 */
public interface OrganDAO {
	
	void insert(Organ instance);

	void update(Organ instance);

	void delete(Organ instance);
	
	void delete(Long id);

}
