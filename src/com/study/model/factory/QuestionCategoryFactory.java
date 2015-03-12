package com.study.model.factory;

import java.util.List;

import com.study.model.QuestionCategory;
import com.study.utility.ModelFactoryUtility;

/**
 * 题目分类数据工厂接口
 */
public interface QuestionCategoryFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	QuestionCategory findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<QuestionCategory> findListByAll();
	
	/**
	 * 获取某分类节点的子单位数量
	 * @param parantId 父单位编号
	 * @return 子分类节点数量
	 */
	Integer findCountByParant(Long parantId);
	
	/**
	 * 获取分类树
	 * @return 分类树
	 */
	List<QuestionCategory> findListByTree();
	
	/**
	 * 获取顶级记录
	 * @return 记录
	 */
	QuestionCategory findTop();
	
	/**
	 * 获取分类节点的子节点数量
	 * @param parantId 父节点编号
	 * @return 子节点列表
	 */
	List<QuestionCategory> findListByParentId(Long parentId);	

}
