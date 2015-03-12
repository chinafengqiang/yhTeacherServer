package com.study.model.factory;

import java.util.List;

import com.study.enums.QuestionTypeEnum;
import com.study.model.Question;
import com.study.model.TestPaperQuestion;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 试卷题目数据工厂接口
 */
public interface TestPaperQuestionFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	TestPaperQuestion findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<TestPaperQuestion> findListByAll();
	
	/**
	 * 获取试卷的重复题目的数量
	 * @param testPaperId 试卷编号编号
	 * @return 题目数量
	 */
	Integer findCountByRepeatQuestion(Long testPaperId, Integer series, QuestionTypeEnum questionTypeEnum, String name, String options);
	
	/**
	 * 获取某题型的试卷题目的数量
	 * @param testPaperId 试卷编号编号
	 * @return 题目数量
	 */
	Integer findCountByQuestionType(Long testPaperId, Integer series, QuestionTypeEnum questionTypeEnum);

	/**
	 * 获取试卷的所有记录
	 * @param testPaperId 试卷编号编号
	 * @return 题目数量
	 */
	List<TestPaperQuestion> findListByTestPaper(Long testPaperId);
	
	/**
	 * 获取试卷的所有记录
	 * @param testPaperId 试卷编号编号
	 * @param series 套数
	 * @return 题目列表
	 */
	List<TestPaperQuestion> findListByTestPaperId_Series(Long testPaperId, Integer series);
	
	/**
	 * 获取试卷套数的所有题目记录
	 * @param testPaperId 试卷编号编号
	 * @param series 套数
	 * @return 题目
	 */
	List<Question> findListByTestPaper_Series(Long testPaperId, Integer series);
	
	/**
	 * 获取试卷的题目的总分数
	 * @param testPaperId 试卷编号编号
	 * @return 题目的分数
	 */
	Double findSumScoreBySeries(Long testPaperId, Integer series);
	
	/**
	 * 按搜索条件获取试卷题目分页列表
	 * @param testPaperId 试卷编号
	 * @param series 题目套数
	 * @param name 名称
	 * @param questionTypeEnum 题型
	 * @param difficulty 难度
	 * @param ken 知识点
	 * @param paginateParamters 分页参数
	 * @return 试卷题目分页列表
	 */
	PaginateResult findListBySearch(Long testPaperId, Integer series, String name, QuestionTypeEnum questionTypeEnum, Integer difficulty, String ken, PaginateParamters paginateParamters);

}
