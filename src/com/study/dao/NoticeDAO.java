package com.study.dao;

import com.study.model.Notice;

/**
 * 公告数据操作接口
 */
public interface NoticeDAO {
	
	void insert(Notice instance);

	void update(Notice instance);

	void delete(Notice instance);
	
	void delete(Long id);

}
