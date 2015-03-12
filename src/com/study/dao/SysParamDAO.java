package com.study.dao;

import com.study.model.SysParam;

/**
 * 系统参数数据操作接口
 */
public interface SysParamDAO {
	
	void insert(SysParam instance);

	void update(SysParam instance);

	void delete(SysParam instance);
	
	void delete(Long id);

}
