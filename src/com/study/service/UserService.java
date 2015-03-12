package com.study.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.study.enums.UserStatusEnum;
import com.study.model.User;
import com.study.model.us.USUserSummary;


/**
 * 学员业务接口
 */
public interface UserService {

	/**
	 * 创建学员
	 * @param name 帐号
	 * @param actualName 项目
	 * @param actualOrgan 单位
	 * @param dutyRank 职务级别
	 * @param trade 行业
	 * @param mobile 手机号
	 * @param password 密码
	 * @param statusEnum 状态
	 * @param curOrganId 当前单位编号
	 * @return 学员
	 * @throws Exception
	 */
	User createUser(String name, String actualName, String actualOrgan, String dutyRank, 
			String trade, String mobile, String password, UserStatusEnum statusEnum, Long curOrganId) throws Exception;
	
	/**
	 * 修改学员
	 * @param id 编号
	 * @param actualName 项目
	 * @param dutyRank 职务级别
	 * @param trade 行业
	 * @param mobile 手机号
	 * @param password 密码
	 * @param statusEnum 状态
	 * @param curOrganId 当前单位编号
	 * @return 学员
	 * @throws Exception
	 */
	User modifyUser(Long id, String actualName, String dutyRank, 
			String trade, String mobile, String password, UserStatusEnum statusEnum, Long curOrganId) throws Exception;

	/**
	 * 删除学员
	 * @param id 学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void removeUser(Long id, Long curOrganId) throws Exception;
	
	/**
	 * 修改学员密码
	 * @param id 学员编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception;
	
	/**
	 * 学员登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 学员记录
	 * @throws Exception
	 */
	User login(String name, String password) throws Exception;
	
	/**
	 * 校验此学员是否在学员参与范围中
	 * @param userId 学员编号
	 * @param userJoinedData 参与范围
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return
	 */
	Boolean verifyUserJoinedData(Long userId, String userJoinedData, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade);

	
	/**
	 * 导入学员集
	 * @param curOrganId 当前单位编号
	 * @param file Excel文件
	 * @throws Exception 
	 */
	void importUserList(Long curOrganId, File file) throws Exception;
	
	/**
	 * 导出学员分类的所有学员到Excel
	 * @param curOganId 当前单位编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportUserList(Long curOganId) throws IOException;
	
	/**
	 * 导出学员模板
	 * @return
	 * @throws IOException
	 */
	InputStream exportUserExcelTemplet() throws IOException;
	
	/**
	 * 按职务级别、行业、参与范围获取已启用的学员列表
	 * @param canAllowAllUser 是否允许所有学员参加
	 * @param userJoinedData 参与范围
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return 学员列表
	 */
	List<User> extractUserList(Boolean canAllowAllUser, String userJoinedData, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade, Boolean canCourseStudyLimit);

	/**
	 * 获取学员平台的学员摘要
	 * @param userId 学员编号
	 * @return 学员摘要
	 * @throws Exception
	 */
	USUserSummary getUSUserSummary(Long userId) throws Exception;
	
	/**
	 * 更新学员学分
	 * @param userId 学员编号
	 */
	void updateUserCredit(Long userId);
}
