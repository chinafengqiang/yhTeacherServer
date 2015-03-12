package com.study.model.factory;

import java.util.List;

import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.enums.ExamUserStatusEnum;
import com.study.model.ExamUser;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 考生数据工厂接口
 */
public interface ExamUserFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	ExamUser findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<ExamUser> findListByAll();
	
	/**
	 * 按考试编号和学员编号获取考生记录
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @return 考生记录
	 */
	ExamUser findByExamId_UserId(Long examId, Long userId);
	
	/**
	 * 按考试编号和学员标示获取考生记录
	 * @param examId 考试编号
	 * @param userKey 学员标示
	 * @return 考生记录
	 */
	ExamUser findByExamId_UserKey(Long examId, String userKey);
	
	/**
	 * 获取考试服务器的考试记录
	 * @param examId 考试编号
	 * @param examServerData 考试服务器数据
	 * @return 考生列表
	 */
	List<ExamUser> findListByExamServerData(Long examId, String examServerData);
	
	/**
	 * 获取相同考试代号的考试记录
	 * @param examCode 考试代号
	 * @return 考生列表
	 */
	List<ExamUser> findListByExamCode(String examCode);
	
	/**
	 * 获取教师平台的考试学员分页列表
	 * @param examId 考试编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 考试学员分页列表
	 */
	PaginateResult findListByExam_Manager(Long examId, String actualOrgan, String actualName, PaginateParamters paginateParamters);
	
	/**
	 * 获取单位平台的考试学员分页列表
	 * @param examId 考试编号
	 * @param organId 单位编号
	 * @param actualOrgan 单位
	 * @param actualName 姓名
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return 考试学员分页列表
	 */
	PaginateResult findListByExam_Organ(Long examId, Long organId, String actualOrgan, String actualName, ExamUserStatusEnum statusEnum, PaginateParamters paginateParamters);
	
	/**
	 * 获取学员的记录
	 * @param userId 学员编号
	 * @return 考生列表
	 */
	List<ExamUser> findListByUserId(Long userId);

}
