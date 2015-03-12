package com.study.model.factory;

import java.util.List;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 题目数据工厂接口
 */
public interface QuestionFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Question findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Question> findListByAll();
	
	/**
	 * 获取题目分类的所有题目
	 * @param questionCategoryId 题目分类编号
	 * @return 题目分类的所有题目
	 */
	List<Question> findListByQuestionCategory(Long questionCategoryId);

	/**
	 * 获取某分类节点的题目数量
	 * @param questionCategoryId 分类节点编号
	 * @return 题目数量
	 */
	Integer findCountByQuestionCategory(Long questionCategoryId);
	
	/**
	 * 获取某分类节点的重复题目的数量
	 * @param questionCategoryId 分类节点编号
	 * @return 题目数量
	 */
	Integer findCountByRepeatQuestion(Long questionCategoryId, Long questionId, QuestionTypeEnum questionTypeEnum, String name, String options);
	
	/**
	 * 按搜索条件获取题目分页列表
	 * @param questionCategoryId 题目分类编号
	 * @param questionTypeEnum 类型
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @param name 题目
	 * @param paginateParamters 分页参数
	 * @return 题目分页列表
	 */
	PaginateResult findListBySearch(Long questionCategoryId, QuestionTypeEnum questionTypeEnum, Integer difficulty, String ken, String name, PaginateParamters paginateParamters);
	
	/**
	 * 自动挑题
	 * @param questionCategoryId 题目分类编号
	 * @param questionTypeEnum 题型
	 * @param difficulty 难度
	 * @param score 分数
	 * @param ken 知识点
	 * @param number 数量
	 * @return 题目列表
	 */
	List<Question> findListByAutoSelect(Long questionCategoryId, QuestionTypeEnum questionTypeEnum, 
			Integer difficulty, Double score, String ken, Integer number);

}
