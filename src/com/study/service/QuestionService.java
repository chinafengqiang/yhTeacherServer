package com.study.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.model.QuestionCategory;


/**
 * 题目业务接口
 */
public interface QuestionService {

	/**
	 * 创建分类同级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	QuestionCategory createCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 创建分类下级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	QuestionCategory createChildCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 修改分类节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	QuestionCategory modifyCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception;
	
	/**
	 * 删除节点
	 * @param curCategoryId 当前节点编号
	 * @throws Exception
	 */
	void removeCategory(Long curCategoryId) throws Exception;
	
	/**
	 * 创建题目
	 * @param curCategoryId 分类节点
	 * @param questionTypeEnum 题目类型
	 * @param name 题干
	 * @param answer 答案
	 * @param note 备注
	 * @param options 选项
	 * @param score 分数
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @return
	 * @throws Exception
	 */
	Question createQuestion(Long curCategoryId, QuestionTypeEnum questionTypeEnum, 
			String name, String answer, String note, String options, Double score, 
			Integer difficulty, String ken) throws Exception;
	
	/**
	 * 修改题目
	 * @param id 题目编号
	 * @param name 题干
	 * @param answer 答案
	 * @param note 备注
	 * @param options 选项
	 * @param score 分数
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @return 题目
	 * @throws Exception
	 */
	Question modifyQuestion(Long id, String name, String answer, String note, String options, Double score, 
			Integer difficulty, String ken) throws Exception;
	
	/**
	 * 删除题目
	 * @param id 题目编号
	 */
	void removeQuestion(Long id);
		
	/**
	 * 导入题目集
	 * @param questionCategoryId 题目分类编号
	 * @param file Excel文件
	 * @throws Exception 
	 */
	void importQuestionList(Long questionCategoryId, File file) throws Exception;
	
	/**
	 * 导出题目分类的所有题目到Excel
	 * @param questionCategoryId 题目分类编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportQuestionList(Long questionCategoryId) throws IOException;
	
	/**
	 * 导出题目模板
	 * @return
	 * @throws IOException
	 */
	InputStream exportQuestionExcelTemplet() throws IOException;
	
	/**
	 * 获取题库分类树列表
	 * @return
	 * @throws Exception
	 */
	List<QuestionCategory> getQuestionCategoryListByTree() throws Exception;
	
	//文件转换处理
	public String save(InputStream is,String userId);
	
	public InputStream getFileById(String fileId,String userId);
	
	public boolean deleteFile(String fileId,String userId);
}
