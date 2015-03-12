package com.study.dao;

import com.study.model.ExamUserData;

/**
 * 考生考卷操作接口
 */
public interface ExamUserDataDAO {
	
	void insert(ExamUserData instance);

	void update(ExamUserData instance);

	void delete(ExamUserData instance);
	
	void delete(Long id);

	/**
	 * 删除考试的所有考卷记录
	 * @param examId 考试编号
	 */
	void deleteListByExam(Long examId);
	
	/**
	 * 删除考生的考卷记录
	 * @param examUserId 考试编号
	 */
	void deleteByExamUserId(Long examUserId);

}
