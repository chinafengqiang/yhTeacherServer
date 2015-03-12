package com.study.model.factory;

import java.util.List;

import com.study.enums.SysParamTypeEnum;
import com.study.model.SysParam;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 系统参数数据工厂接口
 */
public interface SysParamFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	SysParam findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<SysParam> findListByAll();

	/**
	 * 按参数类型获取系统参数
	 * @param paramTypeEnum 系统参数类型
	 * @return 系统参数
	 */
	SysParam findByParamType(SysParamTypeEnum sysParamTypeEnum);
	
	/**
	 * 获取系统参数分页列表
	 * @param paginateParamters 分页参数
	 * @return 系统参数分页列表
	 */
	PaginateResult findList(PaginateParamters paginateParamters);
}
