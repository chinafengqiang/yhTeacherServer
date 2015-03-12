package com.study.model.factory;

import java.util.List;

import com.study.enums.TestPaperStatusEnum;
import com.study.model.TestPaper;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 试卷数据工厂接口
 */
public interface TestPaperFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	TestPaper findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<TestPaper> findListByAll();
	
	/**
	 * 按本地标示获取试卷记录
	 * @param testPaperKey 试卷本地标示
	 * @return 试卷
	 */
	TestPaper findByTestPaperKey(String testPaperKey);
	
	/**
	 * 按搜索条件获取试卷分页列表
	 * @param name 名称
	 * @param category 分类
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 试卷分页列表
	 */
	PaginateResult findListBySearch(String name, String category, TestPaperStatusEnum statusEnum, PaginateParamters paginateParamters);
}
