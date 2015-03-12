package com.study.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.study.enums.QuestionOptionsSortTypeEnum;
import com.study.enums.QuestionShowTypeEnum;
import com.study.enums.QuestionSortTypeEnum;
import com.study.enums.QuestionTypeEnum;
import com.study.enums.TestPaperStatusEnum;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.model.part.TestPaperBrowseData;


/**
 * 试卷业务接口
 */
public interface TestPaperService {


	/**
	 * 创建试卷
	 * @param name 名称
	 * @param description 描述
	 * @param category 分类
	 * @param testPagerOptions 配置
	 * @param questionSortTypeEnum 题目排序方式
	 * @param questionOptionsSortTypeEnum 题目选项排序方式
	 * @param credit 学分
	 * @param totalScore 总分
	 * @param totalSeries 题目套数
	 * @param passScore 通过分数
	 * @param canIgnoreQuestionScore 是否忽略题目原有的分数
	 * @param statusEnum 状态 
	 * @param curManagerId 当前管理员编号
	 * @return 试卷
	 * @throws Exception
	 */
	TestPaper createTestPaper(String name, String description, String category,
			String testPagerOptions, QuestionSortTypeEnum questionSortTypeEnum,
			QuestionOptionsSortTypeEnum questionOptionsSortTypeEnum, Integer credit, Integer totalScore, Integer totalSeries, 
			Integer passScore, Boolean canIgnoreQuestionScore, TestPaperStatusEnum statusEnum, Long curManagerId, Boolean canQueryAnswer) throws Exception;
	
	/**
	 * 修改试卷
	 * @param id 编号
	 * @param name 名称
	 * @param description 描述
	 * @param category 分类
	 * @param testPagerOptions 配置
	 * @param questionSortTypeEnum 题目排序方式
	 * @param questionOptionsSortTypeEnum 题目选项排序方式
	 * @param credit 学分
	 * @param totalScore 总分
	 * @param totalSeries 题目套数
	 * @param passScore 通过分数
	 * @param canIgnoreQuestionScore 是否忽略题目原有的分数
	 * @param statusEnum 状态 
	 * @param curManagerId 当前管理员编号
	 * @return 试卷
	 * @throws Exception
	 */
	TestPaper modifyTestPaper(Long id, String name, String description, String category,
			String testPagerOptions, QuestionSortTypeEnum questionSortTypeEnum,
			QuestionOptionsSortTypeEnum questionOptionsSortTypeEnum, Integer credit, Integer totalScore, Integer totalSeries, 
			Integer passScore, Boolean canIgnoreQuestionScore, TestPaperStatusEnum statusEnum, Long curManagerId, Boolean canQueryAnswer) throws Exception;
	
	/**
	 * 删除试卷
	 * @param id 试卷编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeTestPaper(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 选题
	 * @param testPaperId 试卷编号
	 * @param series 套数
	 * @param questionId 题目编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void selectQuestion(Long testPaperId, Integer series, Long questionId, Long curManagerId) throws Exception;
	
	/**
	 * 自动抽题
	 * @param testPaperId 试卷编号
	 * @param questionCategoryId 题目分类编号
	 * @param questionTypeEnum 题型
	 * @param difficulty 难度
	 * @param score 分数
	 * @param ken 知识点
	 * @param number 数量
	 * @param series 套数
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void autoSelectQuestionList(Long testPaperId, Long questionCategoryId, QuestionTypeEnum questionTypeEnum, 
			Integer difficulty, Double score, String ken, Integer number, Integer series, Long curManagerId) throws Exception;
	
	/**
	 * 导入题目
	 * @param testPaperId 试卷编号
	 * @param series 套数
	 * @param file 文件
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void importQuestionList(Long testPaperId, Integer series, File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出试卷套数的所有题目到Excel
	 * @param testPaperId 试卷编号
	 * @param series 试题套数
	 * @param curManagerId 当前教师编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportQuestionList(Long testPaperId, Integer series, Long curManagerId) throws IOException;

	/**
	 * 获取试卷摘要
	 * @param testPaperId 试卷编号
	 * @param series 套数
	 * @param curManagerId 当前管理员编号
	 * @return 试卷摘要
	 * @throws Exception
	 */
	Map<String, String> getTestPaperSummary(Long testPaperId, Integer series, Long curManagerId) throws Exception;
	
	/**
	 * 调整题目
	 * @param testPaperQuestionId 试卷题目编号
	 * @param score 分数
	 * @param sortFlag 排序
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void adjustQuestion(Long testPaperQuestionId, Double score, Integer sortFlag, Long curManagerId) throws Exception;
	
	/**
	 * 删除题目
	 * @param testPaperQuestionId 试卷题目编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeQuestion(Long testPaperQuestionId, Long curManagerId) throws Exception;
	
	/**
	 * 校验试卷
	 * @param testPaperId 试卷编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	TestPaper validateTestPaper(Long testPaperId, Long curManagerId) throws Exception;

	/**
	 * 导入试卷
	 * @param file 文件
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void importTestPaper(File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出试卷
	 * @param testPaperId 试卷编号
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	String exportTestPaper(Long testPaperId, Long curManagerId) throws Exception;
	
	/**
	 * 加密试题答案
	 * @param testPaperQuestion 试题
	 */
	void encryptAnswer(TestPaperQuestion testPaperQuestion);
	
	/**
	 * 获取导出用的word文件名称
	 * @param testPaperId 试卷编号
	 * @param series 套数
	 * @return
	 * @throws Exception
	 */
	String getWordFileNameByExport(Long testPaperId, Integer series) throws Exception;

	/**
	 * 导出试卷题目导出到Word中
	 * @param testPaperId 试卷编号
	 * @param series 试卷编号
	 * @param curManagerId 教师编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportTestPaperToWord(Long testPaperId, Integer series, Long curManagerId) throws Exception;
	
	/**
	 * 获取试卷预览数据
	 * @param testPaperId 试卷编号
	 * @param series 套数
	 * @return
	 * @throws Exception
	 */
	TestPaperBrowseData getTestPaperBrowseData(Long testPaperId, Integer series) throws Exception;
}
