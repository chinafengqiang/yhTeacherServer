package com.study.model.factory;

import java.util.List;

import com.study.model.ExamUserData;

/**
 * 考生考卷数据工厂接口
 */
public interface ExamUserDataFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ExamUserData findById(Long id);
	
	/**
	 * 按考生编号获取考试答卷
	 * @param examUserId 考生编号
	 * @return 考试答卷
	 */
	ExamUserData findByExamUser(Long examUserId);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ExamUserData> findListByAll();

}
