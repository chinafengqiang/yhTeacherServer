package com.study.model.factory;

import java.util.List;

import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.model.Organ;
import com.study.utility.ModelFactoryUtility;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 单位数据工厂接口
 */
public interface OrganFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Organ findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Organ> findListByAll();
	
	/**
	 * 获取所有停用的单位记录列表
	 */
	List<Organ> findListByClosed();	
	
	/**
	 * 获取顶级单位记录
	 * @return 单位
	 */
	Organ findByTop();
	
	/**
	 * 获取所有下级记录
	 */
	List<Organ> findListByChild(Long id);

	/**
	 * 按帐号获取单位记录
	 * @param name 帐号
	 * @return 单位
	 */
	Organ findByName(String name);
	
	/**
	 * 按名称获取单位记录
	 * @param actualName名称
	 * @return 单位
	 */
	Organ findByActualName(String actualName);
	
	/**
	 * 获取某单位的子单位数量
	 * @param parantId 父单位编号
	 * @return 子单位数量
	 */
	Integer findCountByParant(Long parantId);
	
	/**
	 * 按搜索条件获取本单位及下级单位分页列表
	 * @param organTypeEnum 级别枚举
	 * @param statusEnum 类型枚举
	 * @param actualName 名称
	 * @param curOrganId 当前单位编号
	 * @param paginateParamters 分页参数
	 * @return 单位分页列表
	 */
	PaginateResult findListBySearch(OrganTypeEnum organTypeEnum, OrganStatusEnum statusEnum, String actualName, Long curOrganId, PaginateParamters paginateParamters);
	
	/**
	 * 递归获取自身及下级单位的所有ID列表
	 * @param organIdList 单位ID列表
	 * @return 单位ID列表
	 */
	String findIdListBySelf_Child_Opened(String organIdList);

	/**
	 * 获取拥有考试报表数据的所有单位记录
	 * @param reportExamId 考试报表编号
	 */
	List<Organ> findListByReportExam(Long reportExamId);
	
	/**
	 * 获取拥有考试报表数据的所有单位记录
	 * @param reportExamId 考试报表编号
	 */
	List<Organ> findListByOrganType(OrganTypeEnum organTypeEnum);
	
	/**
	 * 获取本级及下级所有启动的单位
	 * @param id 本单位编号
	 */
	List<Organ> findListBySelf_Child_Opened(Long organId);
	
	/**
	 * 获取供选择的单位记录
	 * @param organTypeEnum 单位类型
	 * @param actualName 单位名称
	 * @param parentId 最高单位编号
	 * @param statusEnum 状态
	 * @param paginateParamters 分页参数
	 * @return
	 */
	PaginateResult findListBySelect(OrganTypeEnum organTypeEnum, String actualName, Long parentId, OrganStatusEnum statusEnum, PaginateParamters paginateParamters);

}
