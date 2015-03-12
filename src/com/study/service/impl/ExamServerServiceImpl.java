package com.study.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.examserver.model.EsExamSummary;
import com.study.dao.DAOFacade;
import com.study.enums.ExamServerStatusEnum;
import com.study.model.Exam;
import com.study.model.ExamServer;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ExamServerService;
import com.study.service.ManagerService;
import com.study.service.SystemService;
import com.study.utility.ActionPostUtility;
import com.study.utility.JSONConvertor;

/**
 * 考试服务器业务接口实现类
 */
@Service
public class ExamServerServiceImpl implements ExamServerService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ExamServerServiceImpl.class);
	
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
	 * 教师业务接口 
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 获取考试服务器数据
	 * @param examServer 考试服务器
	 * @return
	 */
	public String getExamServerData(ExamServer examServer) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("examServerName", examServer.getName());
		map.put("examServerUrl", examServer.getUrl());
		
		return JSONConvertor.map2Json(map);
	}
	
	/**
	 * 获取考试在各个服务器上的摘要
	 * @param examId 考试编号
	 * @return 考试摘要列表
	 * @throws Exception
	 */
	public List<EsExamSummary> getExamSummary(Long examId) throws Exception {
	
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examId);
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("此考试已不存在!");
		}
		
		//获取已启动的考试服务器列表
		List<ExamServer> examServerList = this.modelFactoryFacade.getExamServerFactory().findListByStatus(ExamServerStatusEnum.Opened);
		
		//检查是否有已启动的服务器
		if (examServerList.size() == 0) {
			throw new Exception("未发现已启动的考试服务器");
		}
		
		//检查考试服务器是否正常
		this.testExamServerList(examServerList);
		
		//初始化返回列表
		List<EsExamSummary> result = new ArrayList<EsExamSummary>();

		//给考试服务器发送启动请求
		for (ExamServer examServer : examServerList) {
			
			//发送请求
			try {
				String url = examServer.getDirectUrl() + "/EsSystem/getExamSummary.action";
				Object data = ActionPostUtility.post(url)
						.setParameter("esExamKey", exam.getEsExamKey())
						.data(EsExamSummary.class);
				
				EsExamSummary esExamSummary = (EsExamSummary)data;
				result.add(esExamSummary);
			} catch (Exception ex) {
			}
		}

		return result;
	}
	
	/**
	 * 获取考试服务器上的所有考试摘要
	 * @param examServerId 考试服务器编号
	 * @return 考试摘要列表
	 * @throws Exception
	 */
	public List<EsExamSummary> getExamServerSummary(Long examServerId) throws Exception {
		
		//获取考试服务器
		ExamServer examServer = this.modelFactoryFacade.getExamServerFactory().findById(examServerId);
		
		//判断是否存在
		if (examServer == null) {
			throw new Exception("此考试服务器已不存在！");
		}
		
		//发送请求
		try {
			String url = examServer.getDirectUrl() + "/EsSystem/getExamServerSummary.action";
			return (List<EsExamSummary>)ActionPostUtility.post(url).dataList(EsExamSummary.class);
		} catch (Exception ex) {
			throw new Exception("无法正常获取此考试服务器的考试摘要数据！");
		}
	}
	
	/**
	 * 测试考试服务器是否正常运行
	 * @param examServerList 考试服务器列表
	 * @throws Exception 
	 */
	public void testExamServerList(List<ExamServer> examServerList) throws Exception {
		
		for (ExamServer examServer: examServerList) {
	
			try {
				String url = examServer.getDirectUrl() + "/EsSystem/test.action";
				ActionPostUtility.post(url).exec();
			} catch (Exception ex) {
				throw new Exception("【" + examServer.getName() + "】考试服务器不正常！");
			}
		}
	}
	
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
	public ExamServer createExamServer(String name, String url, String directUrl, String note,
			ExamServerStatusEnum statusEnum) throws Exception {
	
		//检测名称是否重复
		if (this.modelFactoryFacade.getExamServerFactory().findByName(name.trim()) != null) {
			throw new Exception("考试服务器名称已重复！");
		}
		
		//检查地址是否重复
		if (this.modelFactoryFacade.getExamServerFactory().findByUrl(url.trim()) != null) {
			throw new Exception("考试服务器地址已重复！");
		}
		
		//检查内网地址是否重复
		if (this.modelFactoryFacade.getExamServerFactory().findByDirectUrl(directUrl.trim()) != null) {
			throw new Exception("考试服务器的内网地址已重复！");
		}

		//创建考试服务器
		ExamServer examServer = new ExamServer();
		
		examServer.setName(name.trim());
		examServer.setDirectUrl(directUrl);
		examServer.setUrl(url.trim());
		examServer.setNote(note);
		examServer.setStatus(statusEnum.toValue());
		
		this.daoFacade.getExamServerDAO().insert(examServer);
		
		return examServer;
	}
	
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
	public ExamServer modifyExamServer(Long id, String name, String url, String directUrl, String note,
			ExamServerStatusEnum statusEnum) throws Exception {
	
		//获取考试服务器
		ExamServer examServer = this.modelFactoryFacade.getExamServerFactory().findById(id);
		
		//判断是否存在
		if (examServer == null) {
			throw new Exception("此考试服务器已不存在！");
		}
		
		ExamServer tempName = this.modelFactoryFacade.getExamServerFactory().findByName(name.trim());
		ExamServer tempUrl = this.modelFactoryFacade.getExamServerFactory().findByUrl(url.trim());
		ExamServer tempDirectUrl = this.modelFactoryFacade.getExamServerFactory().findByDirectUrl(directUrl.trim());
		
		//检测名称是否重复
		if (tempName != null && !tempName.getId().equals(id)) {
			throw new Exception("考试服务器名称已重复！");
		}
		
		//检查地址是否重复
		if (tempUrl != null && !tempUrl.getId().equals(id)) {
			throw new Exception("考试服务器地址已重复！");
		}
		
		//检查内网地址是否重复
		if (tempDirectUrl != null && !tempDirectUrl.getId().equals(id)) {
			throw new Exception("考试服务器内网地址已重复！");
		}
		
		//修改考试服务器
		examServer.setName(name);
		examServer.setUrl(url);
		examServer.setNote(note);
		examServer.setStatus(statusEnum.toValue());
		
		this.daoFacade.getExamServerDAO().update(examServer);
		
		return examServer;
	}
	
	/**
	 * 删除考试服务器
	 * @param id 编号
	 * @throws Exception
	 */
	public void removeExamServer(Long id) throws Exception {
		
		//获取考试服务器
		ExamServer examServer = this.modelFactoryFacade.getExamServerFactory().findById(id);
		
		//判断是否存在
		if (examServer == null) {
			throw new Exception("此考试服务器已不存在！");
		}
		
		//删除记录
		this.daoFacade.getExamServerDAO().delete(id);
	}

}
