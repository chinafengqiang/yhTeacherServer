package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ExamStatusEnum;
import com.study.model.Exam;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考试数据工厂接口
 */
public interface ExamFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Exam findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Exam> findListByAll();
	
	/**
	 * 获取相同考试代号的考试列表
	 */
	List<Exam> findListByExamCode(String examCode);
	
	/**
	 * 获取引用此试卷的考试数量
	 * @param testPaperId 试卷编号
	 * @return 考试数量
	 */
	Integer findCountByTestPaper(Long testPaperId);
	
	/**
	 * 按本地标示获取考试记录
	 * @param examKey 考试本地标示
	 * @return 考试
	 */
	Exam findByExamKey(String examKey);
	
	/**
	 * 按考试服务器考试标示获取考试记录
	 * @param esExamKey 考试服务器考试标示
	 * @return 考试
	 */
	Exam findByEsExamKey(String esExamKey);
	
	/**
	 * 按搜索条件获取考试分页列表
	 * @param category 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	PaginateResult findListBySearch(String category, ExamStatusEnum statusEnum, String name, PaginateParamters paginateParamters);
	
	/**
	 * 按搜索条件获取考试分页列表
	 * @param category 类型
	 * @param statusEnum 状态
	 * @param name 名称
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	PaginateResult findListByOrgan(String category, ExamStatusEnum statusEnum, String name, Long organId, PaginateParamters paginateParamters);
	
	/**
	 * 获取学员平台的考试分页列表
	 * @param userId 学员编号
	 * @param paginateParamters 分页参数
	 * @return 考试分页列表
	 */
	PaginateResult findListByUser(Long userId, PaginateParamters paginateParamters);

}
