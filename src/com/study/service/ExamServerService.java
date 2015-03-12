package com.study.service;

import java.util.List;

import com.examserver.model.EsExamSummary;
import com.study.enums.ExamServerStatusEnum;
import com.study.model.ExamServer;


/**
 * 考试服务器业务接口
 */
public interface ExamServerService {

	/**
	 * 获取考试服务器数据
	 * @param examServer 考试服务器
	 * @return
	 */
	String getExamServerData(ExamServer examServer);
		
	/**
	 * 测试考试服务器是否正常运行
	 * @param examServerList 考试服务器列表
	 * @throws Exception 
	 */
	void testExamServerList(List<ExamServer> examServerList) throws Exception;
		
	/**
	 * 创建考试服务器
	 * @param name 标题
	 * @param url 外网链接
	 * @param directUrl 内网链接
	 * @param note 备注
	 * @param statusEnum 状态
	 * @return 考试服务器
	 * @throws Exception
	 */
	ExamServer createExamServer(String name, String url, String directUrl, String note,
			ExamServerStatusEnum statusEnum) throws Exception;
	
	/**
	 * 修改考试服务器
	 * @param id 编号
	 * @param name 标题
	 * @param url 外网链接
	 * @param directUrl 内网链接
	 * @param note 备注
	 * @param statusEnum 状态
	 * @return
	 * @throws Exception
	 */
	ExamServer modifyExamServer(Long id, String name, String url, String directUrl, String note,
			ExamServerStatusEnum statusEnum) throws Exception;
	
	/**
	 * 删除考试服务器
	 * @param id 编号
	 * @throws Exception
	 */
	void removeExamServer(Long id) throws Exception;
	
	/**
	 * 获取考试在各个服务器上的摘要
	 * @param examId 考试编号
	 * @return 考试摘要列表
	 * @throws Exception
	 */
	List<EsExamSummary> getExamSummary(Long examId) throws Exception;
	
	/**
	 * 获取考试服务器上的所有考试摘要
	 * @param examServerId 考试服务器编号
	 * @return 考试摘要列表
	 * @throws Exception
	 */
	List<EsExamSummary> getExamServerSummary(Long examServerId) throws Exception;
}
