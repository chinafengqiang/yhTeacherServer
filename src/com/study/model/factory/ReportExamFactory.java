package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ReportExamStatusEnum;
import com.study.model.ReportExam;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试报表数据工厂接口
 */
public interface ReportExamFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ReportExam findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ReportExam> findListByAll();
	
	/**
	 * 获取所有启动考试报表记录
	 */
	List<ReportExam> findListByOpened();

	/**
	 * 按考试代号获取考试报表记录
	 * @param examCode 考试代号
	 * @return 考试报表记录
	 */
	ReportExam findByExamCode(String examCode);
	
	/**
	 * 按搜索条件获取考试报表分页列表
	 * @param name 名称
	 * @param examCode 考试代号
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 考试报表分页列表
	 */
	PaginateResult findListBySearch(String name, String examCode, ReportExamStatusEnum statusEnum, PaginateParamters paginateParamters);
	
	/**
	 * 按搜索条件获取单位平台中的考试报表分页列表
	 * @param name 名称
	 * @param examCode 考试代号
	 * @param paginateParamters 分页参数
	 * @return 考试报表分页列表
	 */
	PaginateResult findListByOrgan(String name, String examCode, PaginateParamters paginateParamters);

}
