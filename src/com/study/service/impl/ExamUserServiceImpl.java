package com.study.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.examserver.model.EsExamUser;
import com.examserver.model.EsExamUserData;
import com.study.dao.DAOFacade;
import com.study.enums.ExamModeEnum;
import com.study.enums.ExamServerStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.enums.ExamUserStatusEnum;
import com.study.enums.QuestionFetchTypeEnum;
import com.study.enums.TimerModeEnum;
import com.study.model.Exam;
import com.study.model.ExamServer;
import com.study.model.ExamUser;
import com.study.model.ExamUserData;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.TestPaperBrowseData;
import com.study.model.part.UserTestPaperBrowseData;
import com.study.service.ExamServerService;
import com.study.service.ExamUserService;
import com.study.service.ManagerService;
import com.study.service.SystemService;
import com.study.service.UserService;
import com.study.utility.ActionPostUtility;
import com.study.utility.DateUtility;
import com.study.utility.Encrypt;
import com.study.utility.FileUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.MD5Utility;
import com.study.utility.SystemUtility;

/**
 * 考生业务接口实现类
 */
@Service
public class ExamUserServiceImpl implements ExamUserService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ExamUserServiceImpl.class);
	
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
	 * 考试服务器业务接口 
	 */
	@Resource
	private ExamServerService examServerService;
	
	/**
	 * 学员业务接口 
	 */
	@Resource
	private UserService userService;
	
	/**
	 * 批量创建考试的考生
	 * @param examId 考试编号
	 * @param examServerList 考试服务器列表
	 * @param managerId 管理员编号
	 * @throws Exception
	 */
	public void createExamUserList(Long examId, List<ExamServer> examServerList, Long managerId) throws Exception {
		
		//检查是否存在已经启动的考试服务器
		if (examServerList.size() <= 0) {
			throw new Exception("没有发现已经启动的考试服务器！");
		}
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examId);
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("此考试已不存在!");
		}
		
		//校验考试状态，判断是否可以修改
		if (exam.getStatus() != ExamStatusEnum.Created.toValue() && exam.getStatus() != ExamStatusEnum.Destroyed.toValue()) {
			throw new Exception("此考试已部署或已归档，不可再次创建考生记录！");
		}
		
		//按考试的筛选条件获取考生列表
		List<User> userList = this.userService.extractUserList(exam.getCanAllowAllUser(), exam.getUserJoinedData(), exam.getCanMatchDutyRank(), exam.getDutyRank(), exam.getCanMatchTrade(), exam.getTrade(), exam.getCanCourseStudyLimit());
		
		//定义要插入的考生数据列表
		List<ExamUser> examUserList = new ArrayList<ExamUser>();
		
		//循环创建考生记录
		for (User user : userList) {
			ExamUser examUser = this.genarateExamUser(exam, user, examServerList);
			examUserList.add(examUser);
		}
		
		//插入考生列表
		this.daoFacade.getExamUserDAO().insertList(examUserList);
	}
	
	/**
	 * 生成考生记录
	 * @param exam 考试
	 * @param user 学员
	 * @param examServerList 考试服务器
	 * @return 考生记录
	 */
	private ExamUser genarateExamUser(Exam exam, User user, List<ExamServer> examServerList) {
	
		//初始化考生记录
		ExamUser examUser = new ExamUser();
		
		//默认套数为1
		Integer series = 1;
		
		//判断是否是随机试卷，若是，则分配套数
		if (exam.getQuestionFetchType().equals(QuestionFetchTypeEnum.Random.toValue())) {
			
			TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(exam.getTestPaperId());
			if (testPaper.getTotalSeries() > 1) {
				series = SystemUtility.createRandomValue(1, testPaper.getTotalSeries());
			}
		}
		
		//默认为第一台考试服务器
		String examServerData = this.examServerService.getExamServerData(examServerList.get(0));
		String backupExamServerData = this.examServerService.getExamServerData(examServerList.get(0));
		
		//判断考试服务器是否有多台，若是则分配考试服务器
		if (examServerList.size() > 1) {
			
			Integer index = SystemUtility.createRandomValue(0, examServerList.size() - 1);
			
			//备用考试服务器默认为下一台
			Integer backupIndex = index + 1;
			if (backupIndex >= examServerList.size()) {
				backupIndex = 0;
			}
			
			examServerData = this.examServerService.getExamServerData(examServerList.get(index));
			backupExamServerData = this.examServerService.getExamServerData(examServerList.get(backupIndex));;
		}

		//设置考生基本数据
		examUser.setExamId(exam.getId());
		examUser.setUserId(user.getId());
		examUser.setUserKey(user.getUserKey());
		examUser.setExamServerData(examServerData);
		examUser.setBackupExamServerData(backupExamServerData);
		examUser.setActualName(user.getActualName());
		examUser.setActualOrgan(user.getActualOrgan());
		examUser.setOrganId(user.getOrganId());
		examUser.setTestPaperId(exam.getTestPaperId());
		examUser.setSeries(series);
		examUser.setCanLimitValidTime(exam.getCanLimitValidTime());
		examUser.setValidFirstTime(exam.getValidFirstTime());
		examUser.setValidLastTime(exam.getValidLastTime());
		examUser.setScore(0.0);
		examUser.setCanPass(false);
		examUser.setCredit(0);
		
		//判断是否考试计时
		if (exam.getTimerMode().equals(TimerModeEnum.Limit.toValue())) {
			examUser.setTotalTime(exam.getTimerLimit() * 60);
		} else {
			examUser.setTotalTime(0);
		}
		
		examUser.setLeaveTime(examUser.getTotalTime());
		examUser.setStatus(ExamUserStatusEnum.None.toValue());
		
		return examUser;
	}
	
	/**
	 * 生成考试服务器考生记录
	 * @param exam 考试数据
	 * @param examUser 考生数据
	 * @return ES考生记录
	 */
	private EsExamUser genarateEsExamUser(Exam exam, ExamUser examUser) {
		
		//获取随机的开考延时和提交试卷延时
		Integer beginExamDelayedTime = SystemUtility.createRandomValue(1, this.systemService.getBeginExamDelayedTime());
		Integer commitExamDelayedTime = SystemUtility.createRandomValue(1, this.systemService.getCommitExamDelayedTime());
		
		//生成试卷数据文件名
		String testPaperSeriesJson = MD5Utility.getMd5ByHex(exam.getEsExamKey() + "series" + examUser.getSeries());
		
		//获取学员数据
		User user = this.modelFactoryFacade.getUserFactory().findById(examUser.getUserId());
		
		//创建ES考生数据
		EsExamUser esExamUser = new EsExamUser();
		
		esExamUser.setBeginExamDelayedTime(beginExamDelayedTime);
		esExamUser.setCommitExamDelayedTime(commitExamDelayedTime);
		esExamUser.setTestPaperSeriesJson(testPaperSeriesJson);
		
		esExamUser.setEsExamKey(exam.getEsExamKey());
		esExamUser.setUserKey(user.getUserKey());
		esExamUser.setUserName(user.getName());
		esExamUser.setExamServerData(examUser.getExamServerData().replace("{", "$%"));
		esExamUser.setBackupExamServerData(examUser.getBackupExamServerData().replace("{", "$%"));
		esExamUser.setActualOrgan(user.getActualOrgan());
		esExamUser.setActualName(user.getActualName());
		esExamUser.setPassword(this.systemService.decryptPassword(user.getPassword()));
		esExamUser.setCanLimitValidTime(examUser.getCanLimitValidTime());
		esExamUser.setValidFirstTime(examUser.getValidFirstTime());
		esExamUser.setValidLastTime(examUser.getValidLastTime());
		esExamUser.setSeries(examUser.getSeries());
		esExamUser.setTotalTime(examUser.getTotalTime());
		esExamUser.setLeaveTime(examUser.getLeaveTime());
		esExamUser.setBeginTime(examUser.getBeginTime());
		esExamUser.setEndTime(examUser.getEndTime());
		esExamUser.setScore(examUser.getScore());
		esExamUser.setStatus(examUser.getStatus());

		return esExamUser;
	}
	
	/**
	 * 创建考生
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	public void createExamUser(Long examId, Long userId) throws Exception {
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examId);
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("此考试已不存在!");
		}
		
		//若考试未处于部署状态，不可添加考生
		if (exam.getStatus() != ExamStatusEnum.Deployed.toValue()) {
			throw new Exception("此考试未部署或早已启动，不可添加考生！");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断学员记录是否存在
		if (user == null) {
			throw new Exception("此学员帐号已不存在了！");
		}
		
		//判断是否已经存在此考生记录了
		if (this.modelFactoryFacade.getExamUserFactory().findByExamId_UserId(examId, userId) != null) {
			throw new Exception("此考生记录已存在了，不可重复添加！");
		}
		
		//获取已启动的考试服务器列表
		List<ExamServer> examServerList = this.modelFactoryFacade.getExamServerFactory().findListByStatus(ExamServerStatusEnum.Opened);
		
		//检查是否有已启动的服务器
		if (examServerList.size() == 0) {
			throw new Exception("未发现已启动的考试服务器");
		}
		
		//检查考试服务器是否正常
		this.examServerService.testExamServerList(examServerList);
		
		//创建考生记录
		ExamUser examUser = this.genarateExamUser(exam, user, examServerList);
		this.daoFacade.getExamUserDAO().insert(examUser);
		
		//部署单个学生
		this.desployExamUser(exam, examUser);
	}
	
	/**
	 * 校验是否有操作此学员的权限
	 * @param userId 学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void validateOrganAction(Long userId, Long curOrganId) throws Exception {
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断学员记录是否存在
		if (user == null) {
			throw new Exception("此学员帐号已不存在了！");
		}
		
		//判断是否自己单位的
		if (user != null) {
			if (!user.getOrganId().equals(curOrganId)) {
				throw new Exception("您无权添加其他单位的考生！");
			}
		}
	}
	
	/**
	 * 校验是否有操作此考生的权限
	 * @param examUserId 考生编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void validateOrganActionByExamUser(Long examUserId, Long curOrganId) throws Exception {
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findById(examUserId);
		
		//判断考生是否存在
		if (examUser == null) {
			throw new Exception("此考生帐号已不存在了！");	
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(examUser.getUserId());
		
		//判断学员记录是否存在
		if (user == null) {
			throw new Exception("此学员帐号已不存在了！");
		}
		
		//判断是否自己单位的
		if (user != null) {
			if (!user.getOrganId().equals(curOrganId)) {
				throw new Exception("您无权添加其他单位的考生！");
			}
		}
	}	
	
	/**
	 * 设置重考
	 * @param examId 考试编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	public void resetExamUser(Long examId, Long userId) throws Exception {
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examId);
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("此考试已不存在!");
		}
		
		//若考试未处于开考状态，不可重置考生
		if (exam.getStatus() != ExamStatusEnum.Opened.toValue()) {
			throw new Exception("此考试未启动，不可设置重考！");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断学员记录是否存在
		if (user == null) {
			throw new Exception("此学员帐号已不存在了！");
		}
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findByExamId_UserId(examId, userId);
		
		//判断是否已经存在此考生记录了
		if (examUser == null) {
			throw new Exception("此考生记录已不存在了，不可重考！");
		}
		
		//获取考试服务器
		HashMap<String, Object> examServerMap = JSONConvertor.json2Map(examUser.getExamServerData());

		//发送请求
		try {
			String url = examServerMap.get("examServerUrl") + "/EsExamUser/resetExamUser.action";
			ActionPostUtility.post(url)
				.setParameter("esExamKey", exam.getEsExamKey())
				.setParameter("userName", user.getName())
				.exec();
		} catch (Exception ex) {
			throw new Exception("设置考生重考，部署到【" + examServerMap.get("examServerName") + "】考试服务器时不正常！");
		}
		
		//判断是否需要给备用考试服务器发送请求
		if (!examUser.getExamServerData().equals(examUser.getBackupExamServerData())) {
			
			//获取备用考试服务器
			Map backupExamServerMap = JSONConvertor.json2Map(examUser.getBackupExamServerData());
			
			//发送请求
			try {
				String url = backupExamServerMap.get("examServerUrl") + "/EsExamUser/resetExamUser.action";
				ActionPostUtility.post(url)
					.setParameter("esExamKey", exam.getEsExamKey())
					.setParameter("userName", user.getName())
					.exec();
			} catch (Exception ex) {
				throw new Exception("设置考生重考，部署【" + backupExamServerMap.get("examServerName") + "】考试服务器不正常！");
			}
		}
		
		//判断是否是分散考试，若是则修改本地记录
		if (exam.getExamMode().equals(ExamModeEnum.Freedom.toValue())) {
			
			examUser.setBeginTime(null);
			examUser.setEndTime(null);
			examUser.setScore(0.0);
			examUser.setStatus(ExamUserStatusEnum.None.toValue());		
			
			//修改数据库中的考生记录，并删除掉答卷记录
			this.daoFacade.getExamUserDAO().update(examUser);
			this.daoFacade.getExamUserDataDAO().deleteByExamUserId(examUser.getId());	
		}
	}
	
	/**
	 * 生成考生答卷记录
	 * @param examUserId 考生编号
	 * @param esExamUserData 考生答卷记录
	 * @return
	 */
	private ExamUserData genarateExamUserData(Long examUserId, EsExamUserData esExamUserData) {
		
		ExamUserData examUserData = new ExamUserData();
		
		examUserData.setData(esExamUserData.getData());
		examUserData.setExamUserId(examUserId);
		examUserData.setScore(esExamUserData.getScore());
		this.daoFacade.getExamUserDataDAO().insert(examUserData);
		
		return examUserData;
	}
	
	/**
	 * 获取答卷数据
	 * @param examUserId 考生编号
	 * @throws Exception
	 */
	private ExamUserData getExamUserData(Long examUserId) throws Exception {
		
		//获取考生答卷记录
		ExamUserData examUserData = this.modelFactoryFacade.getExamUserDataFactory().findByExamUser(examUserId);
		
		if (examUserData != null) {
			return examUserData;
		}
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findById(examUserId);
		
		//若不存在，则继续
		if (examUser == null) {
			throw new Exception("奇怪，这场考生的记录不见了！");
		}
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examUser.getExamId());
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("奇怪，这场考试记录不见了！");
		}
		
		//获取考试服务器
		HashMap<String, Object> examServerMap = JSONConvertor.json2Map(examUser.getExamServerData());
		
		//发送请求
		try {
			String url = examServerMap.get("examServerUrl") + "/EsExamUser/getExamUserData.action";
			Object data = ActionPostUtility.post(url)
						.setParameter("esExamKey", exam.getEsExamKey())
						.setParameter("userKey", examUser.getUserKey())
						.data(EsExamUserData.class);
			
			EsExamUserData esExamUserData = (EsExamUserData)data;
			examUserData = genarateExamUserData(examUserId, esExamUserData);
			this.daoFacade.getExamUserDataDAO().insert(examUserData);
			return examUserData;
		} catch (Exception ex) {
			
		}
		
		//获取备用考试服务器
		Map backupExamServerMap = JSONConvertor.json2Map(examUser.getBackupExamServerData());
		
		//发送请求
		try {
			String url = backupExamServerMap.get("examServerUrl") + "/EsExamUser/getExamUserData.action";
			Object data = ActionPostUtility.post(url)
						.setParameter("esExamKey", exam.getEsExamKey())
						.setParameter("userKey", examUser.getUserKey())
						.data(EsExamUserData.class);
			
			EsExamUserData esExamUserData = (EsExamUserData)data;
			examUserData = genarateExamUserData(examUserId, esExamUserData);
			this.daoFacade.getExamUserDataDAO().insert(examUserData);
			return examUserData;
		} catch (Exception ex) {
			
		}
		
		throw new Exception("无法获取此考生的答卷记录！");
	}
	
	/**
	 * 获取考试服务器的考生数据列表
	 * @param exam 考试数据
	 * @param examServer 考试服务器
	 * @throws Exception
	 */
	public List<EsExamUser> getEsExamUserListByExamServer(Exam exam, ExamServer examServer) throws Exception {
		
		//获取此考试服务器上的所有考生列表
		List<ExamUser> examUserList = this.modelFactoryFacade.getExamUserFactory().findListByExamServerData(exam.getId(), this.examServerService.getExamServerData(examServer));
	
		//初始化ES考生列表
		List<EsExamUser> esExmaUserList = new ArrayList<EsExamUser>();
		
		//生成Es考生，并添加到列表中
		for (ExamUser examUser : examUserList) {
			EsExamUser esExamUser = this.genarateEsExamUser(exam, examUser);
			esExmaUserList.add(esExamUser);
		}
		
		return esExmaUserList;
	}
	
	/**
	 * 汇总成绩列表
	 * @param exam 考试
	 * @param esExamUserListData 考生成绩列表
	 * @throws Exception 
	 */
	public void gatherExamUserList(Exam exam, List<EsExamUser> esExamUserList) throws Exception {
	
		//获取这场考试的试卷
		TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(exam.getTestPaperId());
		
		//判断此试卷是否存在
		if (testPaper == null) {
			throw new Exception("奇怪，这场考试的试卷记录不见了！");
		}
		
		//定义要更新的考生数据列表
		List<ExamUser> updateExamUserList = new ArrayList<ExamUser>();
		
		//循环处理考生数据
		for (EsExamUser esExamUser : esExamUserList) {
			
			//TODO 这里存在性能问题、不能一条条的取
			
			//获取考生记录
			ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findByExamId_UserKey(exam.getId(), esExamUser.getUserKey());
			
			//若不存在，则继续
			if (examUser == null) {
				continue;
			}
			
			//记录考试服务器返回的数据，前提条件是有分数，且考过，且分数比现在的高，防止脏数据覆盖
			if (esExamUser.getScore() != null && !esExamUser.getStatus().equals(ExamUserStatusEnum.None.toValue()) && esExamUser.getScore().compareTo(examUser.getScore()) >=0) {
			
				examUser.setBeginTime(esExamUser.getBeginTime());
				examUser.setEndTime(esExamUser.getEndTime());
				examUser.setScore(esExamUser.getScore());
				examUser.setCanPass(esExamUser.getScore().compareTo(testPaper.getPassScore().doubleValue()) >=0);
				examUser.setStatus(esExamUser.getStatus());
				
				updateExamUserList.add(examUser);
			}
		}
		
		//更新考试列表
		this.daoFacade.getExamUserDAO().updateList(updateExamUserList);

	}
	
	/**
	 * 汇总考生成绩
	 * @param esExamKey ES考试标示
	 * @param esExamUserData 考生成绩
	 * @throws Exception 
	 */
	public void gatherExamUser(String esExamKey, String esExamUserData) throws Exception {
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findByEsExamKey(esExamKey);
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("奇怪，这场考试记录不见了！");
		}
		
		//获取这场考试的试卷
		TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(exam.getTestPaperId());
		
		//判断此试卷是否存在
		if (testPaper == null) {
			throw new Exception("奇怪，这场考试的试卷记录不见了！");
		}
		
		EsExamUser esExamUser = (EsExamUser)JSONConvertor.json2Bean(esExamUserData, EsExamUser.class);
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findByExamId_UserKey(exam.getId(), esExamUser.getUserKey());
		
		//若不存在，则继续
		if (examUser == null) {
			throw new Exception("奇怪，这场考生的记录不见了！");
		}
		
		//记录考试服务器返回的数据
		examUser.setBeginTime(esExamUser.getBeginTime());
		examUser.setEndTime(esExamUser.getEndTime());
		examUser.setScore(esExamUser.getScore());
		if (esExamUser.getScore() != null) {
			examUser.setCanPass(esExamUser.getScore().compareTo(testPaper.getPassScore().doubleValue()) >=0);
		}
		examUser.setStatus(esExamUser.getStatus());
		
		this.daoFacade.getExamUserDAO().update(examUser);
	}
	
	/**
	 * 部署考生
	 * @param exam 考试数据
	 * @param examUser 考生数据
	 * @throws Exception
	 */
	private void desployExamUser(Exam exam, ExamUser examUser) throws Exception {
		
		//将考生记录转换考试服务器考生记录，分配延时时间和设置试卷链接值
		EsExamUser esExamUser = this.genarateEsExamUser(exam, examUser);
		String esExamUserData = JSONConvertor.bean2Json(esExamUser);

		//获取考试服务器
		HashMap<String, Object> examServerMap = JSONConvertor.json2Map(examUser.getExamServerData());

		//发送请求
		try {
			String url = examServerMap.get("examServerUrl") + "/EsExamUser/deployExamUser.action";
			ActionPostUtility.post(url)
				.setParameter("esExamKey", exam.getEsExamKey())
				.setParameter("esExamUserData", esExamUserData)
				.exec();
		} catch (Exception ex) {
			throw new Exception("将考生部署到【" + examServerMap.get("examServerName") + "】考试服务器时不正常！");
		}
		
		//判断是否需要给备用考试服务器发送请求
		if (examUser.getExamServerData().equals(examUser.getBackupExamServerData())) {
			return;
		}
		
		//获取考试服务器
		Map backupExamServerMap = JSONConvertor.json2Map(examUser.getBackupExamServerData());
		
		//发送请求
		try {
			String url = backupExamServerMap.get("examServerUrl") + "/EsExamUser/deployExamUser.action";
			ActionPostUtility.post(url)
				.setParameter("esExamKey", exam.getEsExamKey())
				.setParameter("esExamUserData", esExamUserData)
				.exec();
		} catch (Exception ex) {
			throw new Exception("部署【" + backupExamServerMap.get("examServerName") + "】考试服务器不正常！");
		}
	}
	
	/**
	 * 清除考生
	 * @param esExamKey 考试项目标示
	 * @param userName 学员名称
	 * @throws Exception
	 */
	private void destroyExamUser(String esExamKey, ExamUser examUser, User user) throws Exception {
		
		//获取考试服务器
		HashMap<String, Object> examServerMap = JSONConvertor.json2Map(examUser.getExamServerData());

		//发送请求
		try {
			String url = examServerMap.get("examServerUrl") + "/EsExamUser/destroyExamUser.action";
			ActionPostUtility.post(url)
				.setParameter("esExamKey", esExamKey)
				.setParameter("userName", user.getName())
				.exec();
		} catch (Exception ex) {
			throw new Exception("删除考生时，同步到【" + examServerMap.get("examServerName") + "】考试服务器时不正常！");
		}
		
		//判断是否需要给备用考试服务器发送请求
		if (examUser.getExamServerData().equals(examUser.getBackupExamServerData())) {
			return;
		}
		
		//获取备用考试服务器
		Map backupExamServerMap = JSONConvertor.json2Map(examUser.getBackupExamServerData());
		
		//发送请求
		try {
			String url = backupExamServerMap.get("ExamServerUrl") + "/EsExamUser/destroyExamUser.action";
			ActionPostUtility.post(url)
				.setParameter("esExamKey", esExamKey)
				.setParameter("userName", user.getName())
				.exec();
		} catch (Exception ex) {
			throw new Exception("删除考生时，同步到【" + backupExamServerMap.get("examServerName") + "】考试服务器时不正常！");
		}
		
	}	

	/**
	 * 删除考生
	 * @param examUserId 考生编号
	 * @throws Exception
	 */
	public void removeExamUser(Long examUserId) throws Exception {
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findById(examUserId);
		
		//判断是否存在
		if (examUser == null) {
			return;
		}
		
		//获取考试
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examUser.getExamId());
		
		//判断此考试是否存在
		if (exam == null) {
			throw new Exception("此考试已不存在!");
		}
		
		//判断是否可以删除
		if (exam.getStatus().equals(ExamStatusEnum.Opened.toValue())) {
			throw new Exception("此考试已启动，不可删除考生记录！!");
		}

		//判断是否可以删除
		if (exam.getStatus().equals(ExamStatusEnum.Closed.toValue()) && exam.getExamMode().equals(ExamModeEnum.Together.toValue())) {
			throw new Exception("此考试已停止但未汇总成绩，不可删除考生记录！!");
		}
			
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(examUser.getUserId());
		
		//删除记录
		this.daoFacade.getExamUserDAO().delete(examUserId);
		
		//若考试已部署了，需要同步到考试服务器
		if (exam.getStatus() != ExamStatusEnum.Created.toValue()) {
			this.destroyExamUser(exam.getEsExamKey(), examUser, user);	
		}
	}
	
	/**
	 * 导入考生答卷数据包
	 * @param examId 考试编号
	 * @param file 文件
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void importExamUserData(Long examId, File file, Long curOrganId) throws Exception {
		
		//获取文件内容
		String data = FileUtility.readTXT(file, "UTF-8");
		
		//判断文件内容是否为空
		if (StringUtils.isBlank(data)) {
			throw new Exception("答卷数据包文件内容是空的！");
		}
		
		//进行解压缩、解密处理
		String json = Encrypt.strDecode(data, "28", "37", "1568");
		
		//转换成考生答卷数据对象
		EsExamUserData esExamUserData = (EsExamUserData)JSONConvertor.json2Bean(json, EsExamUserData.class);
		
		//判断是否解密
		if (esExamUserData == null) {
			throw new Exception("答卷数据包文件格式有误！");
		}
		
		//获取学员数据
		User user = this.modelFactoryFacade.getUserFactory().findByUserKey(esExamUserData.getUserKey());
		
		//判断是否找到学员
		if (user == null) {
			throw new Exception("不存在此学员记录！");
		}
		
		//判断是否属于本单位
		if (!user.getOrganId().equals(curOrganId)) {
			throw new Exception("此学员不属于本单位，不可操作！");
		}
	
		//获取考试记录
		Exam exam = this.modelFactoryFacade.getExamFactory().findByEsExamKey(esExamUserData.getEsExamKey());
		
		//判断是否存在此考试记录
		if (exam == null) {
			throw new Exception("此考试记录不存在！");
		}
		
		//判断是否属于此考试
		if (!examId.equals(exam.getId())) {
			throw new Exception("此答卷考试数据包不属于此考试！");
		}
				
		//获取这场考试的试卷
		TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(exam.getTestPaperId());
		
		//判断此试卷是否存在
		if (testPaper == null) {
			throw new Exception("奇怪，这场考试的试卷记录不见了！");
		}
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findByExamId_UserId(exam.getId(), user.getId());
		
		//判断是否存在此考生记录
		if (examUser == null) {
			throw new Exception("此考生记录并不存在！");
		}
		
		//比较分数
		if (examUser.getScore().doubleValue() >=  esExamUserData.getScore().doubleValue()) {
			throw new Exception("此考生的分数已存在，而且不低于答卷数据包中的成绩！");
		}
		
		//修过考生记录
		examUser.setBeginTime(DateUtility.getCurDate());
		examUser.setEndTime(DateUtility.getCurDate());
		examUser.setScore(esExamUserData.getScore());
		if (examUser.getScore() != null) {
			examUser.setCanPass(examUser.getScore().compareTo(testPaper.getPassScore().doubleValue()) >=0);
		}
		examUser.setStatus(ExamUserStatusEnum.Done.toValue());
		
		this.daoFacade.getExamUserDAO().update(examUser);
		
		//添加考生答卷记录
		ExamUserData examUserData = this.modelFactoryFacade.getExamUserDataFactory().findByExamUser(examUser.getId());
		
		//先删除旧的
		if (examUserData != null) {
			this.daoFacade.getExamUserDataDAO().delete(examUserData.getId());
		}
		
		examUserData = new ExamUserData();
		examUserData.setExamUserId(examUser.getId());
		examUserData.setData(esExamUserData.getData());
		examUserData.setScore(esExamUserData.getScore());
		
		this.daoFacade.getExamUserDataDAO().insert(examUserData);
	}
	
	/**
	 * 获取考试直通车链接入口
	 * @param examUserId 考生编号
	 * @return 考试直通车链接
	 * @throws Exception
	 */
	public String getExamLink(Long examUserId) throws Exception {
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findById(examUserId);
		
		//判断是否存在此考生记录
		if (examUser == null) {
			throw new Exception("此考生记录并不存在！");
		}
		
		//获取考试记录
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examUser.getExamId());
		
		//判断是否存在此考试记录
		if (exam == null) {
			throw new Exception("此考试记录不存在！");
		}
		
		//判断是否已部署或启动
		if (!(exam.getStatus().equals(ExamStatusEnum.Opened.toValue()) || 
			exam.getStatus().equals(ExamStatusEnum.Deployed.toValue()))) {
			throw new Exception("此考试未部署或启动，可能已经停止了！");
		}
		
		Map<String, Object> map = (HashMap<String, Object>)JSONConvertor.json2Map(examUser.getExamServerData());
		String url = (String)map.get("examServerUrl");
		
		url = url + "/exam/" + exam.getEsExamKey() + ".html";
		
		return url;
	}
	
	/**
	 * 获取答卷卷预览数据
	 * @param examUserId 考生编号
	 * @param curOrganId 当前单位编号
	 * @return
	 * @throws Exception
	 */
	public UserTestPaperBrowseData getUserTestPaperBrowseData(Long examUserId, Long curOrganId) throws Exception {
		
		//获取考生记录
		ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findById(examUserId);
		
		//判断是否存在此考生记录
		if (examUser == null) {
			throw new Exception("此考生记录并不存在！");
		}

		//获取考试记录
		Exam exam = this.modelFactoryFacade.getExamFactory().findById(examUser.getExamId());
		
		//判断是否存在此考试记录
		if (exam == null) {
			throw new Exception("此考试记录不存在！");
		}
		
		//判断是否允许查看答卷
		if (!exam.getCanQueryAnswer() && curOrganId != null) {
			throw new Exception("此考试不允许查看答卷！");
		}
		
		//判断此考生是否已经考完了
		if (!examUser.getStatus().equals(ExamUserStatusEnum.Done.toValue())) {
			throw new Exception("此考生并未考完并汇总成绩！");
		}
		
		//获取答卷数据
		ExamUserData examUserData = this.modelFactoryFacade.getExamUserDataFactory().findByExamUser(examUserId);
		
		//若没有答卷数据，则去考试服务器去取
		if (examUserData == null) {
			
			if (!exam.getStatus().equals(ExamStatusEnum.Gathered.toValue())) {
				throw new Exception("此考试已卸载，无法获取考生答卷信息！");
			}
			
			//从考试服务器上获取答卷数据
			examUserData = getExamUserData(examUserId);
		
			//判断是否获取成功
			if (examUserData == null) {
				throw new Exception("无法从服务器上获取考生答卷信息！");
			}
		}
		
		Long testPaperId = exam.getTestPaperId();
		
		//获取试卷记录
		TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(testPaperId);	
		
		//判断此试卷是否存在
		if (testPaper == null) {
			throw new Exception("此试卷已不存在!");
		}
		
		List<TestPaperQuestion> testPaperQuestionList = this.modelFactoryFacade.getTestPaperQuestionFactory().findListByTestPaperId_Series(testPaperId, examUser.getSeries());

		UserTestPaperBrowseData data = new UserTestPaperBrowseData();
	
		data.setExamName(exam.getName());
		data.setExamUser(examUser);
		data.setTestPaper(testPaper);
		data.setSeries(examUser.getSeries());
		data.setQuestionList(testPaperQuestionList);
		data.setData(examUserData.getData());
		
		return data;
	}	
}
