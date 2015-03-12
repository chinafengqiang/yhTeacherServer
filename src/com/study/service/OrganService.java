package com.study.service;

import java.io.IOException;
import java.io.InputStream;

import com.study.enums.OrganTypeEnum;
import com.study.model.Organ;


/**
 * 单位业务接口
 */
public interface OrganService {

	/**
	 * 创建单位记录
	 * @param name 帐号
 	 * @param actualName 名称
	 * @param shortName 简称
	 * @param organTypeEnum 单位类型
	 * @param linkman 联系人
	 * @param tel 电话
	 * @param mobile 手机
	 * @param area 地区
	 * @param address 地址
	 * @param curOrganId 当前单位
	 * @return 单位记录
	 * @throws Exception
	 */
	Organ createOrgan(String name, String actualName, String shortName, 
			OrganTypeEnum organTypeEnum, String linkman, String tel, String mobile, String area, String address,
			Long curOrganId) throws Exception;
	
	/**
	 * 修改单位信息
	 * @param id 单位编号
	 * @param shortName 简称
	 * @param linkman 联系人
	 * @param tel 电话
	 * @param mobile 手机
	 * @param area 地区
	 * @param address 地址
	 * @param curOrganId 当前单位
	 * @return 单位记录
	 * @throws Exception
	 */
	Organ modifyOrgan(Long id, String shortName, 
			String linkman, String tel, String mobile, String area, String address,
			Long curOrganId) throws Exception;
	
	/**
	 * 删除单位
	 * @param id 要删除的单位编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void removeOrgan(Long id, Long curOrganId) throws Exception;
	
	/**
	 * 修改单位密码
	 * @param id 单位编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception;
	
	/**
	 * 单位登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 单位记录
	 * @throws Exception
	 */
	Organ login(String name, String password) throws Exception;
	
	/**
	 * 析出参加的单位编号列表
	 * @param userJoinedData 学员参与范围
	 * @return 单位编号列表
	 */
	String extractJoinedOrgans(String userJoinedData);
	
	/**
	 * 自动校验单位服务期限
	 */
	void autoValidateOrganServiceLimit();
	
	/**
	 * 激活单位
	 * @param name 单位帐号
	 * @param password 密码
	 * @param activateCodeData 激活码数据
	 */
	void activateOrgan(String name, String password, String activateCodeData) throws Exception;
	
	/**
	 * 导出当前单位的所有下级单位到Excel
	 * @param curOganId 当前单位编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportChildOrganList(Long curOganId) throws Exception;
	
	/**
	 * 导出所有单位到Excel
	 * @param actionPassword 操作验证码
	 * @return
	 * @throws IOException
	 */
	InputStream exportAllOrganList(String actionPassword) throws Exception;
}
