package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ReportExamDataSourceEnum;
import com.study.model.ReportExamData;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试报表基础数据工厂接口
 */
public interface ReportExamDataFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ReportExamData findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ReportExamData> findListByAll();
	
	/**
	 * 按搜索条件获取教师平台的考生成绩分页列表
	 * @param reportExamId 考试报表编号
	 * @param actualOrgan 单位名称
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考生成绩分页列表
	 */
	PaginateResult findListBySearch(Long reportExamId, String actualOrgan, String actualName, PaginateParamters paginateParamters);
	
	/**
	 * 按搜索条件获取单位平台的考生成绩分页列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位名称
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考生成绩分页列表
	 */
	PaginateResult findListByOrgan(Long reportExamId, Long organId, String actualOrgan, String actualName, PaginateParamters paginateParamters);
	
	/**
	 * 获取考试报表某单位的具体来源的考生记录数量
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param sourceEnum 来源
	 * @return 考生记录数量
	 */
	Integer findCountByOrgan_Source(Long reportExamId, Long organId, ReportExamDataSourceEnum sourceEnum);

}
