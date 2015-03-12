package com.study.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.study.dao.DAOFacade;
import com.study.enums.ManagerGradeEnum;
import com.study.enums.ManagerStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.model.Manager;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.SystemService;

/**
 * 教师业务接口实现类
 */
@Service
public class ManagerServiceImpl implements ManagerService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ManagerServiceImpl.class);
	
	/**
	 * 数据操作门面
	 */
	@Resource
	private DAOFacade daoFacade;
	
	/**
	 * 数据工厂门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 系统服务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 验证教师记录级权限
	 * @param managerId 教师编号
	 * @throws Exception 
	 */
	public void validateManagerDataAccess(Long dataCreatorId, Long curManagerId) throws Exception {
		
		//判断此记录本身的创建者是否就是当前管理员
		if (dataCreatorId.equals(curManagerId)) {
			return;
		}
		
		//获取当前教师帐号
		Manager curManager = this.modelFactoryFacade.getManagerFactory().findById(curManagerId);
		
		//判断当前教师是否存在
		if (curManager == null) {
			throw new Exception("当前教师帐号已不存在！");
		}
		
		//判断当前教师是否普通教师
		if (!curManager.getGrade().equals(ManagerGradeEnum.Normal.toValue())) {
			return;
		}
		
		//判断当前教师是否普通教师
		throw new Exception("抱歉，您没有权限做此操作！");
	}

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
	public Manager createManager(String name, String actualName, String mobile, String email, 
			String sysAccess, ManagerGradeEnum gradeEnum, ManagerStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//获取当前教师帐号
		Manager curManager = this.modelFactoryFacade.getManagerFactory().findById(curManagerId);
		
		//判断当前教师是否存在
		if (curManager == null) {
			throw new Exception("当前教师帐号已不存在！");
		}
		
		//判断当前教师是否是高级教师
		if (curManager.getGrade().equals(ManagerGradeEnum.Normal.toValue())) {
			throw new Exception("普通教师不可创建新的教师帐号！");
		}
		
		//判断登录名是否重复
		if (this.modelFactoryFacade.getManagerFactory().findByName(name.trim()) != null) {
			throw new Exception("教师帐号已被占用！");
		}
		
		//判断姓名是否重复
		if (this.modelFactoryFacade.getManagerFactory().findByActualName(actualName.trim()) != null) {
			throw new Exception("教师姓名已被占用！");
		}
		
		//获取默认教师密码
		String password = this.systemService.getSysParamValueByString(SysParamTypeEnum.DefaultManagerPassword);
		
		//要校验普通教师的有限权限
		if (gradeEnum.toValue().equals(ManagerGradeEnum.Normal.toValue())) {
			this.verifyNormalManagerSysAccess(sysAccess);
		}
		
		//创建记录
		Manager manager = new Manager();

		manager.setName(name.trim());
		manager.setActualName(actualName.trim());
		manager.setMobile(mobile);
		manager.setEmail(email);
		manager.setPassword(this.systemService.encryptPassword(password));
		manager.setSysAccess(sysAccess);
		manager.setGrade(gradeEnum.toValue());
		manager.setStatus(statusEnum.toValue());
		
		this.daoFacade.getManagerDAO().insert(manager);

		return manager;
	}
	
	/**
	 * 校验普通老师可以拥有的权限
	 * @param sysAccess 权限集
	 * @throws Exception 
	 */
	private void verifyNormalManagerSysAccess(String sysAccess) throws Exception {
		
		//判断权限集是否为空
		if (sysAccess == null) {
			return;
		}
		
		//获取权限集，并遍历判断是否存在指定的权限
		String[] accessList = sysAccess.split(";");
		for (String access : accessList) {
			
			if (access.equals(SysAccessTypeEnum.Notice.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.Notice.toName() + "】权限！");
			}
			if (access.equals(SysAccessTypeEnum.Article.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.Article.toName() + "】权限！");
			}
			if (access.equals(SysAccessTypeEnum.Question.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.Question.toName() + "】权限！");
			}
			if (access.equals(SysAccessTypeEnum.ReportExam.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.ReportExam.toName() + "】权限！");
			}
			if (access.equals(SysAccessTypeEnum.ExamServer.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.ExamServer.toName() + "】权限！");
			}
			if (access.equals(SysAccessTypeEnum.SysParam.toValue().toString())) {
				throw new Exception("普通教师不可拥有【" + SysAccessTypeEnum.SysParam.toName() + "】权限！");
			}
 		}
	}
	
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
	public Manager modifyManager(Long id, String actualName, String mobile, String email, 
			String sysAccess, ManagerGradeEnum gradeEnum, ManagerStatusEnum statusEnum, Long curManagerId) throws Exception {
		
		//获取当前教师帐号
		Manager curManager = this.modelFactoryFacade.getManagerFactory().findById(curManagerId);
		
		//判断当前教师是否存在
		if (curManager == null) {
			throw new Exception("当前教师帐号已不存在！");
		}
		
		//判断当前教师是否是高级教师
		if (curManager.getGrade().equals(ManagerGradeEnum.Normal.toValue())) {
			throw new Exception("普通教师不可修改教师记录！");
		}
		
		//判断是否更改教师自己的账户信息
		if (id.equals(curManagerId)) {
			throw new Exception("不可修改自己的帐号信息！");
		}
		
		//获取教师记录
		Manager manager = this.modelFactoryFacade.getManagerFactory().findById(id);
		
		//判断是否存在
		if (manager == null) {
			throw new Exception("此教师的记录已不存在！");
		}
		
		//判断是否修改最高级别的教师
		Manager top = this.modelFactoryFacade.getManagerFactory().findByTop();
		if (top != null && top.getId().equals(id)) {
			throw new Exception("不可修改最高级别的教师帐号！");
		}
		
		//要校验普通教师的有限权限
		if (gradeEnum.toValue().equals(ManagerGradeEnum.Normal.toValue())) {
			this.verifyNormalManagerSysAccess(sysAccess);
		}
		
		//判断教师姓名是否重复
		Manager temp = this.modelFactoryFacade.getManagerFactory().findByActualName(actualName.trim());
		if (temp != null && !temp.getId().equals(id)) {
			throw new Exception("教师姓名已被占用！");
		}
		
		//修改记录
		manager.setActualName(actualName.trim());
		manager.setMobile(mobile);
		manager.setEmail(email);
		manager.setSysAccess(sysAccess);
		manager.setGrade(gradeEnum.toValue());
		manager.setStatus(statusEnum.toValue());
		
		this.daoFacade.getManagerDAO().update(manager);
		
		return manager;
	}
	
	/**
	 * 删除教师
	 * @param id 要删除的教师编号
	 * @param curManagerId 当前教师编号
	 * @throws Exception
	 */
	public void removeManager(Long id, Long curManagerId) throws Exception {
		
		//判断是否更改教师自己的账户信息
		if (id.equals(curManagerId)) {
			throw new Exception("不可删除自己的帐号信息！");
		} 
		
		//获取当前教师帐号
		Manager curManager = this.modelFactoryFacade.getManagerFactory().findById(curManagerId);
		
		//判断当前教师是否存在
		if (curManager == null) {
			throw new Exception("当前教师帐号已不存在！");
		}
		
		//判断是否删除最高级别的教师
		Manager top = this.modelFactoryFacade.getManagerFactory().findByTop();
		if (top != null && top.getId().equals(id)) {
			throw new Exception("不可删除最高级别的教师帐号！");
		}
		
		//判断当前教师是否是高级教师
		if (curManager.getGrade().equals(ManagerGradeEnum.Normal.toValue())) {
			throw new Exception("普通教师不可删除教师记录！");
		}
		
		//判断是否删除教师自己的账户信息
		if (id.equals(curManagerId)) {
			throw new Exception("不可删除自己的帐号信息！");
		}
		
		//删除记录
		this.daoFacade.getManagerDAO().delete(id);
	}
	
	/**
	 * 修改教师密码
	 * @param id 教师编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	public void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception {
		
		//获取教师记录
		Manager manager = this.modelFactoryFacade.getManagerFactory().findById(id);
		
		//判断是否存在
		if (manager == null) {
			throw new Exception("您的教师记录已不存在！");
		}
		
		//判断旧密码是否正确
		if (!this.systemService.decryptPassword(manager.getPassword()).toLowerCase().equals(oldPassword.toLowerCase())) {
			throw new Exception("旧密码输入有误！");
		}
		
		//修改记录
		manager.setPassword(this.systemService.encryptPassword(newPassword));

		this.daoFacade.getManagerDAO().update(manager);
	}
	
	/**
	 * 教师登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 教师记录
	 * @throws Exception
	 */
	public Manager login(String name, String password) throws Exception {
		
		//获取教师记录
		Manager manager = this.modelFactoryFacade.getManagerFactory().findByName(name);
		
		//判断是否存在
		if (manager == null) {
			throw new Exception("您的教师记录已不存在！");
		}
		
		//判断密码是否正确
		if (!this.systemService.decryptPassword(manager.getPassword()).toLowerCase().equals(password.toLowerCase())) {
			throw new Exception("密码输入有误！");
		}
		
		return manager;
	}
	
	/**
	 * 校验教师权限
	 * @param managerId 教师编号
	 * @param sysAccessTypeEnum 权限
	 * @throws Exception
	 */
	public void validateSysAccess(Long managerId, SysAccessTypeEnum sysAccessTypeEnum) throws Exception {
		
		//判断要判断的权限是否为空
		if (sysAccessTypeEnum == null) {
			throw new Exception("权限为空！");
		}
		
		//获取教师记录
		Manager manager = this.modelFactoryFacade.getManagerFactory().findById(managerId);
		
		//判断是否存在
		if (manager == null) {
			throw new Exception("您的教师记录已不存在！");
		}
		
		//判断已配置了教师权限
		if (!this.checkSysAccess(manager.getSysAccess(), sysAccessTypeEnum)) {
			throw new Exception("抱歉，您没有权限操作！");
		}
	}
	
	/**
	 * 判断教师权限
	 * @param sysAccess 权限集
	 * @param sysAccessTypeEnum 权限
	 * @return 是否拥有权限
	 */
	private Boolean checkSysAccess(String sysAccess, SysAccessTypeEnum sysAccessTypeEnum) {
		
		//判断权限集是否为空
		if (sysAccess == null) {
			return false;
		}
		
		//获取权限集，并遍历判断是否存在指定的权限
		String[] accessList = sysAccess.split(";");
		for (String access : accessList) {
			if (access.equals(sysAccessTypeEnum.toValue().toString())) {
				return true;
			}
 		}
		
		return false;
	}
	
}
