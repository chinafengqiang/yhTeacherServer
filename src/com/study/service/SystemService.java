package com.study.service;

import java.util.Map;

import com.study.enums.SysParamTypeEnum;
import com.study.model.SysParam;
import com.study.utility.AESUtility;
import com.study.utility.HexStrUtility;


/**
 * 系统业务接口
 */
public interface SystemService {

	/**
	 * 获取AES加密Key
	 */
	String getAESKey();
	
	/**
	 * 获取服务器名称
	 */
	String getServerName();
	
	/**
	 * 内存取操作超时最大值
	 */
	Integer getMemGetTimeOut();
	
	/**
	 * 获取系统设置的开考延时时间
	 */
	Integer getBeginExamDelayedTime();
	
	/**
	 * 获取系统设置的提交试卷延时时间
	 */
	Integer getCommitExamDelayedTime();
	
	/**
	 * 获取OSS权限Key
	 */
	String getOSSAccessKeyId();
	
	/**
	 * 获取OSS权限值
	 */
	String getOSSAccessKeySecret();
	
	/**
	 * 获取OSS存储单元
	 */
	String getOSSBucketName();
	
	/**
	 * 获取OSS存储单元链接
	 */
	String getOSSBucketUrl();
	
	/**
	 * 获取子站点列表
	 * @return
	 */
	Map getSubSiteMap();

	/**
	 * 获取系统参数值（字符串）
	 * @param sysParamTypeEnum 参数类型
	 * @return 参数值
	 */
	String getSysParamValueByString(SysParamTypeEnum sysParamTypeEnum);
	
	/**
	 * 获取系统参数值（数值）
	 * @param sysParamTypeEnum 参数类型
	 * @return 参数值
	 */
	Integer getSysParamValueByInteger(SysParamTypeEnum sysParamTypeEnum);
	
	/**
	 * 修改系统参数
	 * @param sysParamTypeEnum 参数类型
	 * @param value 参数值
	 * @param curManagerId 当前教师编号
	 * @return 系统参数
	 * @throws Exception
	 */
	SysParam modifySysParam(SysParamTypeEnum sysParamTypeEnum, String value, Long curManagerId) throws Exception;
	
	/**
	 * 校验值是否符合系统参数中字符串列表的约定
	 * @param value 值
	 * @param sysParamTypeEnum 参数类型
	 * @throws Exception
	 */
	void verifySysParamByStringList(String value, SysParamTypeEnum sysParamTypeEnum) throws Exception;
		
	/**
	 * 获取枚举数据
	 * @param enumName 枚举名称
	 * @return 枚举数据
	 * @throws Exception 
	 */
	Map getEnumMap(String enumName) throws Exception;
	
	/**
	 * 按参数名获取系统参数值
	 * @param sysParamName 参数名
	 * @return 系统参数
	 * @throws Exception
	 */
	String getSysParamValue(String sysParamName) throws Exception;
	
	/**
	 * 加密数据
	 * @param data
	 * @return
	 */
	String encryptData(String data);
	
	/**
	 * 解密数据
	 * @param data
	 * @return
	 */
	String decryptData(String data);
	
	/**
	 * 加密数据
	 * @param password
	 * @return
	 */
	String encryptPassword(String password);
	
	/**
	 * 解密数据
	 * @param password
	 * @return
	 */
	String decryptPassword(String password);	
}
