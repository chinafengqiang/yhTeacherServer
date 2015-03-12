package com.study.model.factory;

import java.util.List;

import com.study.enums.ManagerGradeEnum;
import com.study.model.Manager;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;

/**
 * 管理员数据工厂接口
 */
public interface ManagerFactory {

	/**
	 * 按编号获取记录
	 * @param id 编号
	 * @return 记录
	 */
	Manager findById(Long id);

	/**
	 * 获取所有记录列表
	 * @return 所有记录列表
	 */
	List<Manager> findListByAll();

	/**
	 * 按登录帐号获取管理员记录
	 * @param name 登录帐号
	 * @return 管理员
	 */
	Manager findByName(String name);
	
	/**
	 * 获取最高管理员记录
	 * @return 管理员
	 */
	Manager findByTop();
	
	/**
	 * 按真实姓名获取管理员记录
	 * @param actualName 真实姓名
	 * @return 管理员
	 */
	Manager findByActualName(String actualName);
	
	/**
	 * 按搜索条件获取教师分页列表
	 * @param gradeEnum 级别枚举
	 * @param name 帐号
	 * @param actualName 姓名
	 * @param paginateParamters 分页参数
	 * @return 教师分页列表
	 */
	PaginateResult findListBySearch(ManagerGradeEnum gradeEnum, String name, String actualName, PaginateParamters paginateParamters);
}
