package com.study.service;

import java.io.File;
import java.util.List;

import com.examserver.model.EsExamUser;
import com.study.enums.ExamServerStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.model.Exam;
import com.study.model.ExamServer;
import com.study.model.ExamUser;
import com.study.model.User;
import com.study.model.part.UserTestPaperBrowseData;

/**
 * 考生业务接口
 */
public interface ExamUserService {

	/**
	 * 批量创建考试的考生
	 * @param examId 考试编号
	 * @param examServerList 考试服务器列表
	 * @param managerId 管理员编号
	 * @throws Exception
	 */
	void createExamUserList(Long examId, List<ExamServer> examServerList, Long managerId) throws Exception;

	/**
	 * 创建考生
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	void createExamUser(Long examId, Long userId) throws Exception;
	
	/**
	 * 校验设置重考权限
	 * @param userId 学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void validateOrganAction(Long userId, Long curOrganId) throws Exception;
	/**
	 * 校验是否有操作此考生的权限
	 * @param examUserId 考生编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void validateOrganActionByExamUser(Long examUserId, Long curOrganId) throws Exception;
	
	
	/**
	 * 设置重考
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	void resetExamUser(Long examId, Long userId) throws Exception;

	/**
	 * 获取考试服务器的考生数据列表
	 * @param exam 考试数据
	 * @param examServer 考试服务器
	 * @throws Exception
	 */
	List<EsExamUser> getEsExamUserListByExamServer(Exam exam, ExamServer examServer) throws Exception;

	/**
	 * 删除考生
	 * @param examUserId 考生编号
	 * @throws Exception
	 */
	void removeExamUser(Long examUserId) throws Exception;

	/**
	 * 汇总成绩列表
	 * @param exam 考试
	 * @param esExamUserList 考生成绩列表
	 * @throws Exception 
	 */
	void gatherExamUserList(Exam exam, List<EsExamUser> esExamUserList) throws Exception;
	
	/**
	 * 汇总考生成绩
	 * @param esExamKey ES考试标示
	 * @param esExamUserData 考生成绩
	 * @throws Exception 
	 */
	void gatherExamUser(String esExamKey, String esExamUserData) throws Exception;

	/**
	 * 导入考生答卷数据包
	 * @param examId 考试编号
	 * @param file 文件
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	void importExamUserData(Long examId, File file, Long curOrganId) throws Exception;

	/**
	 * 获取考试直通车链接入口
	 * @param examUserId 考生编号
	 * @return 考试直通车链接
	 * @throws Exception
	 */
	String getExamLink(Long examUserId) throws Exception;
	
	/**
	 * 获取答卷卷预览数据
	 * @param examUserId 考生编号
	 * @param curOrganId 当前单位编号
	 * @return
	 * @throws Exception
	 */
	UserTestPaperBrowseData getUserTestPaperBrowseData(Long examUserId, Long curOrganId) throws Exception;
}
