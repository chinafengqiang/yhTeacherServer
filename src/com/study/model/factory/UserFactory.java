package com.study.model.factory;

import java.util.List;

import com.study.model.User;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 学员数据工厂接口
 */
public interface UserFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	User findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<User> findListByAll();
	
	/**
	 * 获取单位的学员数量
	 * @param organId 单位编号
	 * @return 子单位数量
	 */
	Integer findCountByOrgan(Long organId);

	/**
	 * 按登录帐号获取学员记录
	 * @param name 登录帐号
	 * @return 学员
	 */
	User findByName(String name);
	
	/**
	 * 按真实姓名和单位获取学员记录
	 * @param organId 单位帐号
	 * @param actualName 姓名
	 * @return 学员
	 */
	User findByOrganId_ActualName(Long organId, String actualName);
	
	/**
	 * 按学员标示获取学员记录
	 * @param userKey 学员标示
	 * @return 学员
	 */
	User findByUserKey(String userKey);
	
	/**
	 * 按搜索条件获取学员分页列表
	 * @param actualOrgan 所属单位
	 * @param name 帐号
	 * @param actualName 姓名
	 * @param organId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	PaginateResult findListBySearch(String actualOrgan, String name, String actualName, Long organId, PaginateParamters paginateParamters);
	
	/**
	 * 获取单位的所有学员
	 * @param organId 单位编号
	 * @return 单位的所有学员
	 */
	List<User> findListByOrgan(Long organId);
	
	/**
	 * 获取符合条件的单位的所有已启用的学员
	 * @param organIdList 单位编号列表
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param requiredCourseNumber 必修课数量
	 * @return 单位的所有学员
	 */
	List<User> findListByExtract(String organIdList, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade, Integer requiredCourseNumber);
	
	/**
	 * 校验学员是否符合条件
	 * @param userId 学员编号
	 * @param organIdList 单位编号列表
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return 单位的所有学员
	 */
	 User findByVerify(Long userId, String organIdList, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade);

	/**
	 * 按搜索条件获取学员分页列表
	 * @param actualOrgan 所属单位
	 * @param actualName 姓名
	 * @param dutyRank 职务级别
	 * @param trade 行业 
	 * @param organId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	PaginateResult findListByOrganSelect(String actualOrgan, String actualName, String dutyRank, String trade, Long organId, PaginateParamters paginateParamters);
	
	/**
	 * 按搜索条件获取教师平台的可选择的学员分页列表
	 * @param actualOrgan 所属单位
	 * @param actualName 姓名
	 * @param dutyRank 职务级别
	 * @param trade 行业 
	 * @param organId 单位编号
	 * @param paginateParamters 分页参数
	 * @return 学员分页列表
	 */
	PaginateResult findListByManagerSelect(String actualOrgan, String actualName, String dutyRank, String trade, Long organId, PaginateParamters paginateParamters);

	 
}
