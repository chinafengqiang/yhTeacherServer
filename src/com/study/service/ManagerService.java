package com.study.service;

import com.study.enums.ManagerGradeEnum;
import com.study.enums.ManagerStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Manager;


/**
 * 管理员业务接口
 */
public interface ManagerService {

	/**
	 * 验证教师记录级权限
	 * @param managerId 教师编号
	 * @throws Exception 
	 */
	void validateManagerDataAccess(Long dataCreatorId, Long curManagerId) throws Exception;

	/**
	 * 创建教师
	 * @param name 帐号
	 * @param actualName 姓名
	 * @param mobile 手机
	 * @param email 电子邮件
	 * @param sysAccess 权限集
	 * @param gradeEnum 级别
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师
	 * @return
	 * @throws Exception
	 */
	Manager createManager(String name, String actualName, String mobile, String email, 
			String sysAccess, ManagerGradeEnum gradeEnum, ManagerStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 修改教师信息
	 * @param id 教师编号
	 * @param actualName 姓名
	 * @param mobile 手机
	 * @param email 电子邮件
	 * @param sysAccess 权限集
	 * @param gradeEnum 级别
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师
	 * @return 教师信息
	 * @throws Exception
	 */
	Manager modifyManager(Long id, String actualName, String mobile, String email, 
			String sysAccess, ManagerGradeEnum gradeEnum, ManagerStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 删除教师
	 * @param id 要删除的教师编号
	 * @param curManagerId 当前教师编号
	 * @throws Exception
	 */
	void removeManager(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 修改教师密码
	 * @param id 教师编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception;
	
	/**
	 * 教师登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 教师记录
	 * @throws Exception
	 */
	Manager login(String name, String password) throws Exception;
	
	/**
	 * 校验教师权限
	 * @param managerId 教师编号
	 * @param sysAccessTypeEnum 权限
	 * @throws Exception
	 */
	void validateSysAccess(Long managerId, SysAccessTypeEnum sysAccessTypeEnum) throws Exception;

}
