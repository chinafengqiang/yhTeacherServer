package com.study.dao;

import com.study.model.ExamServer;

/**
 * 考试服务器操作接口
 */
public interface ExamServerDAO {
	
	void insert(ExamServer instance);

	void update(ExamServer instance);

	void delete(ExamServer instance);
	
	void delete(Long id);

}
