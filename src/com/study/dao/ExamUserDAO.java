package com.study.dao;

import java.util.List;

import com.study.model.ExamUser;

/**
 * 考生操作接口
 */
public interface ExamUserDAO {
	
	void insert(ExamUser instance);

	void update(ExamUser instance);

	void delete(ExamUser instance);
	
	void delete(Long id);
	
	/**
	 * 删除考试的所有学员记录
	 * @param examId 考试编号
	 */
	void deleteListByExam(Long examId);
	
	/**
	 * 删除学员的所有考试记录
	 * @param userId 学员编号
	 */
	void deleteListByUser(Long userId);
	
	/**
	 * 插入列表数据
	 * @param list 列表
	 */
	void insertList(List<ExamUser> list) throws Exception;
	
	/**
	 * 更新列表数据
	 * @param list 列表
	 */
	void updateList(List<ExamUser> list) throws Exception;

}
