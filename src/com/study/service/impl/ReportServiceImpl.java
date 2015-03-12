package com.study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.dao.DAOFacade;
import com.study.enums.ExamUserStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.enums.ReportExamDataSourceEnum;
import com.study.enums.ReportExamDataStatusEnum;
import com.study.enums.ReportExamResultTypeEnum;
import com.study.enums.ReportExamStatusEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.model.Exam;
import com.study.model.ExamUser;
import com.study.model.Organ;
import com.study.model.ReportExam;
import com.study.model.ReportExamData;
import com.study.model.ReportExamResult;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.ExamResultStatusEnum;
import com.study.model.part.ExamUserResult;
import com.study.model.part.ReportExamResultOrgan;
import com.study.model.part.ReportExamResultScore;
import com.study.service.ReportService;
import com.study.service.SystemService;
import com.study.utility.ActionPostUtility;
import com.study.utility.DateUtility;
import com.study.utility.FileUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.ZipUtil;

/**
 * 资讯业务接口实现类
 */
@Service
public class ReportServiceImpl implements ReportService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ReportServiceImpl.class);
	
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
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 创建考试报表项目
	 * @param name 名称
	 * @param examCode 考试代号
	 * @param canAutoGather 是否自动汇总成绩
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师编号
	 * @return 考试报表项目
	 * @throws Exception 
	 */
	public ReportExam createReportExam(String name, String examCode, Boolean canAutoGather, ReportExamStatusEnum statusEnum, Long curManagerId) throws Exception {

		//判断考试代号是否重复
		if (this.modelFactoryFacade.getReportExamFactory().findByExamCode(examCode.trim()) != null) {
			throw new Exception("已存在相同考试代号的考试报表项目！");
		}
		
		//判断是否存在此考试代号
		if (this.modelFactoryFacade.getExamFactory().findListByExamCode(examCode).size() == 0) {
			throw new Exception("不存在此考试代号的考试！");
		}

		//创建考试报表项目
		ReportExam reportExam = new ReportExam();
		
		reportExam.setName(name);
		reportExam.setExamCode(examCode.trim());
		reportExam.setCanAutoGather(canAutoGather);
		reportExam.setStatus(statusEnum.toValue());
		reportExam.setCreatedTime(DateUtility.getCurDate());
		reportExam.setCreator(curManagerId);

		this.daoFacade.getReportExamDAO().insert(reportExam);
		
		return reportExam;
	}
	
	/**
	 * 修改考试报表
	 * @param id 编号
	 * @param name 名称
	 * @param canAutoGather 是否自动汇总成绩
	 * @param statusEnum 状态
	 * @param curManagerId 教师编号
	 * @return 考试报表
	 * @throws Exception
	 */
	public ReportExam modifyReportExam(Long id, String name, Boolean canAutoGather, ReportExamStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//获取考试报表项目
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(id);
		
		//判断考试项目是否存在
		if (reportExam == null) {
			throw new Exception("此考试报表项目记录已不存在！");
		}
		
		//修改记录
		reportExam.setName(name);
		reportExam.setCanAutoGather(canAutoGather);
		reportExam.setStatus(statusEnum.toValue());
		
		this.daoFacade.getReportExamDAO().update(reportExam);
		
		return reportExam;
	}
	
	/**
	 * 删除考试报表项目
	 * @param id 考试报表编号
	 * @param curManagerId 当前教师编号
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeReportExam(Long id, Long curManagerId) throws Exception {
		
		//获取考试报表项目
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(id);
		
		//判断考试项目是否存在
		if (reportExam == null) {
			return;
		}
		
		//删除考生记录
		this.daoFacade.getReportExamDataDAO().deleteListByReportExamId(id);
		
		//删除报表记录
		this.daoFacade.getReportExamResultDAO().deleteListByReportExamId(id);
		
		//删除报表记录
		this.daoFacade.getReportExamDAO().delete(id);
	}
	
	/**
	 * 汇总成绩
	 * @param id 考试报表项目
	 * @throws Exception
	 */
	private void gatherReportExamData(Long id) throws Exception {
		
		//获取考试报表项目
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(id);
		
		//判断考试项目是否存在
		if (reportExam == null) {
			return;
		}
		
		//删除来自网站的考生记录
		this.daoFacade.getReportExamDataDAO().deleteListByReportExamId_Source(id, ReportExamDataSourceEnum.Site);
		
		//获取所有考生
		List<ExamUser> examUserList = this.modelFactoryFacade.getExamUserFactory().findListByExamCode(reportExam.getExamCode());
		
		//获取所有学员
		List<User> userList = this.modelFactoryFacade.getUserFactory().findListByAll();
		
		//获取所有考试
		List<Exam> examList = this.modelFactoryFacade.getExamFactory().findListByAll();
		
		//初始化报表考生数据列表
		List<ReportExamData> reportExamDataList = new ArrayList<ReportExamData>();
		
		//循环处理考生列表
		for (ExamUser examUser : examUserList) {
			
			//获取已存在的考生记录索引
			Integer index = this.findIndexByReportExamDataList(examUser.getUserKey(), reportExamDataList);
			
			//若已存在，则判断分数是否比原先的高，若高，则删除旧的
			if (index.intValue() >= 0) {
				if (examUser.getScore().compareTo(reportExamDataList.get(index).getScore()) > 0) {
					reportExamDataList.remove(index);
				} else {
					continue;
				}
			}
			
			//获取用户索引
			Integer userIndex = this.findIndexByUserList(examUser.getUserKey(), userList);
			
			//若用户索引不存在，则继续
			if (userIndex < 0) {
				continue;
			}
			
			//获取考试索引
			Integer examIndex = this.findIndexByExamList(examUser.getExamId(), examList);
			
			//若考试索引不存在，则继续
			if (examIndex < 0) {
				continue;
			}
			
			//创建考生记录
			ReportExamData reportExamData = new ReportExamData();
			
			reportExamData.setActualName(examUser.getActualName());
			reportExamData.setActualOrgan(examUser.getActualOrgan());
			reportExamData.setDutyRank(userList.get(userIndex).getDutyRank());
			reportExamData.setExamKey(examList.get(examIndex).getExamKey());
			reportExamData.setOrganId(examUser.getOrganId());
			reportExamData.setReportExamId(id);
			reportExamData.setSource(ReportExamDataSourceEnum.Site.toValue());
			reportExamData.setScore(examUser.getScore());
			reportExamData.setTrade(userList.get(userIndex).getTrade());
			reportExamData.setUserKey(examUser.getUserKey());
			
			//若考生状态为未考
			if (examUser.getStatus().equals(ExamUserStatusEnum.None.toValue())) {
				
				//判断是否限时，若限时则设置为待考状态
				if (!examUser.getCanLimitValidTime()) {
					reportExamData.setStatus(ReportExamDataStatusEnum.None.toValue());
				} else {
					
					//比较是否已经超过了开考时间，若超过了，则设置为缺考
					if (examUser.getValidFirstTime().compareTo(DateUtility.getCurDate()) > 0) {
						reportExamData.setStatus(ReportExamDataStatusEnum.None.toValue());
					} else {
						reportExamData.setStatus(ReportExamDataStatusEnum.Leave.toValue());
					}
				}
			} 
			
			//若考试状态为在考，则设置为已参考
			if (examUser.getStatus().equals(ExamUserStatusEnum.Doing.toValue())) {
				reportExamData.setStatus(ReportExamDataStatusEnum.Examed.toValue());
			}
			
			//若考试状态为已考
			if (examUser.getStatus().equals(ExamUserStatusEnum.Done.toValue())) {
				
				//判断是否已过了
				if (examUser.getCanPass()) {
					reportExamData.setStatus(ReportExamDataStatusEnum.Pass.toValue());
				} else {
					reportExamData.setStatus(ReportExamDataStatusEnum.NoPass.toValue());
				}
			}
			
			//添加考生记录	
			reportExamDataList.add(reportExamData);
		}
		
		//批量插入记录
		this.daoFacade.getReportExamDataDAO().insertList(reportExamDataList);
		
	}
	
	/**
	 * 返回考生记录索引
	 * @param userKey 考生唯一标示
	 * @param reportExamDataList 考生记录列表
	 * @return
	 */
	private Integer findIndexByReportExamDataList(String userKey, List<ReportExamData> reportExamDataList) {
		
		Integer index = -1;
		
		for (ReportExamData reportExamData : reportExamDataList) {
			
			index = index + 1;
			if (reportExamData.getUserKey().equals(userKey)) {
				return index;
			}
		}
		
		return -1;
	}
	
	/**
	 * 返回学员列表
	 * @param userKey 学员标示
	 * @param userList 学员列表
	 * @return
	 */
	private Integer findIndexByUserList(String userKey, List<User> userList) {
		
		Integer index = -1;
		
		for (User user : userList) {
			
			index = index + 1;
			if (user.getUserKey().equals(userKey)) {
				return index;
			}
		}
		
		return -1;
	}
	
	/**
	 * 返回考试列表索引
	 * @param examId 考试编号
	 * @param examList 考试列表
	 * @return
	 */
	private Integer findIndexByExamList(Long examId, List<Exam> examList) {
		
		Integer index = -1;
		
		for (Exam exam : examList) {
			
			index = index + 1;
			if (exam.getId().equals(examId)) {
				return index;
			}
		}
		
		return -1;
	}
	
	/**
	 * 统计考试报表数据
	 * @param id 考试报表编号
	 * @throws Exception
	 */
	private void statReportExam(Long id) throws Exception {
		
		//获取考试报表
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(id);
		
		//判断是否存在考试报表
		if (reportExam == null) {
			throw new Exception("已不存在考试报表记录！");
		}
		
		//删除数据
		this.daoFacade.getReportExamResultDAO().deleteListByReportExamId(id);
		
		//获取有考生数据的单位
		List<Organ> organList = this.modelFactoryFacade.getOrganFactory().findListByReportExam(id);
		
		//统计出单位本身的统计数据
		for (Organ organ : organList) {
			ReportExamResult result = this.modelFactoryFacade.getReportExamResultFactory().statByOrganId_ReportExamId(organ.getId(), id);
			this.daoFacade.getReportExamResultDAO().insert(result);
		}
		
		//获取所有法宣单位
		List<Organ> theOrganList = this.modelFactoryFacade.getOrganFactory().findListByOrganType(OrganTypeEnum.Master);
		
		//统计出本地包含下级单位的数据求和
		for (Organ organ : theOrganList) {
			ReportExamResult result = this.modelFactoryFacade.getReportExamResultFactory().statSumByOrganId_ReportExamId(organ.getId(), id);
			
			//若存在考生，则保存记录
			if (result.getTotalNum().intValue() > 0) {
				this.daoFacade.getReportExamResultDAO().insert(result);
			}
		}
		
		//记录最新统计时间
		reportExam.setStatedTime(DateUtility.getCurDate());
		this.daoFacade.getReportExamDAO().update(reportExam);
	}
	
	/**
	 * 自动统计考试报表
	 * @throws Exception
	 */
	public void autoStatReportExam() throws Exception {
		
		//获取所有启动的考试报表项目
		List<ReportExam> list = this.modelFactoryFacade.getReportExamFactory().findListByOpened();
		
		//获取考生数据并统计
		for (ReportExam reportExam : list) {
			
			//判断是否可以自动收集成绩
			if (reportExam.getCanAutoGather()) {
				
				//收集考生数据
				this.gatherReportExamData(reportExam.getId());
			}
			
			//统计数据
			this.statReportExam(reportExam.getId());
			
			//统计子站点单位数据
			this.statSubSiteReportExam(reportExam.getId(), reportExam.getExamCode());
		}
	}
	
	/**
	 * 统计子站点单位数据
	 * @param reportExamId 考试报表编号
	 * @param examCode 考试代号
	 */
	private void statSubSiteReportExam(Long reportExamId, String examCode) {
		
		//获取顶级单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findByTop();
		
		//判断是否存在子站点
		if (!this.systemService.getSysParamValueByString(SysParamTypeEnum.HaveSubSite).equals("是")) {
			return;
		}
		
		//获取地址列表和单位编号列表
		String[] urlList = this.systemService.getSysParamValueByString(SysParamTypeEnum.SubSiteUrl).split(";");
		String[] organIdList = this.systemService.getSysParamValueByString(SysParamTypeEnum.SubSiteOrganId).split(";");
		
		//判断是否存在地址
		if (urlList.length == 0) {
			return;
		}
		
		//判断地址列表和单位编号列表是否一致
		if (urlList.length != organIdList.length) {
			return;
		}
		
		//从子站点提取考试代号的统计结果
		for (int i=0; i<urlList.length; i++) {
			
			try {
				String url = urlList[i] + "/Report/getReportExamResultByTopOrgan.action";
				Object data = ActionPostUtility.post(url)
							.setParameter("examCode", examCode)
							.data(ReportExamResult.class);
				
				ReportExamResult reportExamResult = (ReportExamResult)data;
				
				//更换考试报表编号、单位编号、上级单位编号
				reportExamResult.setReportExamId(reportExamId);
				reportExamResult.setOrganId(Long.parseLong(organIdList[i]));
				reportExamResult.setParentOrganId(organ.getId());
				
				//插入数据
				this.daoFacade.getReportExamResultDAO().insert(reportExamResult);
				
			} catch(Exception ex) {
				
			}
		}
	}
	
	/**
	 * 按单位获取考试报表结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 */
	public List<ReportExamResultOrgan> getReportExamResultOrganList(Long reportExamId, Long organId) {
		
		//初始化列表
		List<ReportExamResult> list = new ArrayList<ReportExamResult>();
		
		//先获取本单位的值
		ReportExamResult selfResult = this.modelFactoryFacade.getReportExamResultFactory().findByReportExamId_OrganId_ResultType(reportExamId, organId, ReportExamResultTypeEnum.Self);
		if (selfResult != null) {
			list.add(selfResult);	
		}
		
		//再获取下级法宣单位全集数据
		List<ReportExamResult> childResultList = this.modelFactoryFacade.getReportExamResultFactory().findListByReportExamId_OrganId_OrganType_ResultType_Child(reportExamId, organId, ReportExamResultTypeEnum.All, OrganTypeEnum.Master);
		list.addAll(childResultList);
		
		//先获取本单位的总值
		ReportExamResult allResult = this.modelFactoryFacade.getReportExamResultFactory().findByReportExamId_OrganId_ResultType(reportExamId, organId, ReportExamResultTypeEnum.All);
		if (allResult != null) {
			allResult.setOrganName("总  计");
			list.add(allResult);
		}
		
		return this.genarateReportExamResultOrganList(list);
	}
	
	/**
	 * 按单位获取分数段统计结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 */
	public List<ReportExamResultScore> getReportExamResultScoreList(Long reportExamId, Long organId) {
	
		ReportExamResult result = this.modelFactoryFacade.getReportExamResultFactory().findByReportExamId_OrganId_ResultType(reportExamId, organId, ReportExamResultTypeEnum.All);
		return this.genarateReportExamResultScoreList(result);
	}
	
	/**
	 * 将统计结果转换成可显示的数据列表
	 * @param reportExamResultList
	 * @return
	 */
	private List<ReportExamResultOrgan> genarateReportExamResultOrganList(List<ReportExamResult> reportExamResultList) {
		
		List<ReportExamResultOrgan> list = new ArrayList<ReportExamResultOrgan>();
		
		for (ReportExamResult result : reportExamResultList) {
			
			ReportExamResultOrgan resultOrgan = new ReportExamResultOrgan();
			resultOrgan.setName(result.getOrganName());
			resultOrgan.setTotalNum(result.getTotalNum());
			resultOrgan.setJoinedNum(result.getJoinedNum());
			resultOrgan.setPassedNum(result.getPassedNum());
			
			resultOrgan.setJoinedRate(this.getRate(result.getTotalNum(), result.getJoinedNum()));
			resultOrgan.setPassedRate(this.getRate(result.getTotalNum(), result.getPassedNum()));
			
			list.add(resultOrgan);
		}
		
		return list;
	}
	
	/**
	 * 获取比率字符串
	 * @param totalValue 总值
	 * @param partValue 分值
	 * @return
	 */
	private String getRate(Integer totalValue, Integer partValue) {
		
		if (totalValue.intValue() == 0) {
			return "";
		} 

		DecimalFormat df = new DecimalFormat("#.##"); 
		Double temp = partValue.doubleValue() * 100 / totalValue;
		
		return df.format(temp) + "%";
	}
	
	/**
	 * 将统计结果转换成分数段数据列表
	 * @param reportExamResult
	 * @return
	 */
	private List<ReportExamResultScore> genarateReportExamResultScoreList(ReportExamResult reportExamResult) {
		
		List<ReportExamResultScore> list = new ArrayList<ReportExamResultScore>();
		
		//判断是否存在统计结果
		if (reportExamResult == null) {
			return list;
		}
		
		ReportExamResultScore score90 = new ReportExamResultScore();
		score90.setName("90分以上");
		score90.setPassedNum(reportExamResult.getScore90Num());
		score90.setPassedRate(this.getRate(reportExamResult.getTotalNum(), reportExamResult.getScore90Num()));
		list.add(score90);
		
		ReportExamResultScore score80 = new ReportExamResultScore();
		score80.setName("80分-89分");
		score80.setPassedNum(reportExamResult.getScore80TO89Num());
		score80.setPassedRate(this.getRate(reportExamResult.getTotalNum(), reportExamResult.getScore80TO89Num()));
		list.add(score80);
		
		ReportExamResultScore score70 = new ReportExamResultScore();
		score70.setName("70分-79分");
		score70.setPassedNum(reportExamResult.getScore70TO79Num());
		score70.setPassedRate(this.getRate(reportExamResult.getTotalNum(), reportExamResult.getScore70TO79Num()));
		list.add(score70);	
		
		ReportExamResultScore score60 = new ReportExamResultScore();
		score60.setName("60分-69分");
		score60.setPassedNum(reportExamResult.getScore60TO69Num());
		score60.setPassedRate(this.getRate(reportExamResult.getTotalNum(), reportExamResult.getScore60TO69Num()));
		list.add(score60);
		
		ReportExamResultScore score59 = new ReportExamResultScore();
		score59.setName("60分以下");
		score59.setPassedNum(reportExamResult.getScore59Num());
		score59.setPassedRate(this.getRate(reportExamResult.getTotalNum(), reportExamResult.getScore59Num()));
		list.add(score59);
		
		return list;
	}
	
	/**
	 * 将单位考试统计列表导出到Excel
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 * @throws IOException
	 */
	public InputStream exportReportExamResultOrganToExcel(Long reportExamId, Long organId) throws IOException {
		
		//获取当前单位的考试报表列表
		List<ReportExamResultOrgan> organList = this.getReportExamResultOrganList(reportExamId, organId);
		
		//将单位考试报表列表转换成工作簿
		HSSFWorkbook workbook = ReportExamResultOrganExcelTools.getWorkbook(organList);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 将单位考试分数段统计列表导出到Excel
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 * @throws IOException
	 */
	public InputStream exportReportExamResultScoreToExcel(Long reportExamId, Long organId) throws IOException {
		
		//获取当前单位的考试报表列表
		List<ReportExamResultScore> scoreList = this.getReportExamResultScoreList(reportExamId, organId);
		
		//将单位考试报表列表转换成工作簿
		HSSFWorkbook workbook = ReportExamResultScoreExcelTools.getWorkbook(scoreList);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 获取本站点顶级单位的考试统计报表数据
	 * @param examCode 考试代号
	 * @return
	 * @throws Exception
	 */
	public ReportExamResult getReportExamResultByTopOrgan(String examCode) throws Exception {
		
		//获取考试报表记录
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findByExamCode(examCode);
		
		//判断是否存在考试报表记录
		if (reportExam == null) {
			throw new Exception("不存在此考试代号的考试报表");
		}
		
		//获取单位记录
		Organ organ = this.modelFactoryFacade.getOrganFactory().findByTop();
		
		//判断单位记录是否存在
		if (organ == null) {
			throw new Exception("不存在顶级单位！");
		}
		
		//获取本单位的总值
		ReportExamResult result = this.modelFactoryFacade.getReportExamResultFactory().findByReportExamId_OrganId_ResultType(reportExam.getId(), organ.getId(), ReportExamResultTypeEnum.All);
		
		//判断是否存在总值记录
		if (result == null) {
			throw new Exception("不存在统计结果数据！");
		}
		
		return result;
	}
	
	/**
	 * 删除考试报表的考生成绩
	 * @param reportExamDataId 考生成绩编号
	 * @throws Exception
	 */
	public void removeReportExamData(Long reportExamDataId) throws Exception {
		
		//获取考生成绩记录
		ReportExamData data = this.modelFactoryFacade.getReportExamDataFactory().findById(reportExamDataId);
		
		//判断成绩记录是否存在
		if (data == null) {
			return;
		}

		//获取考试报表
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(data.getReportExamId());
		
		//判断是否存在考试报表
		if (reportExam == null) {
			return;
		}
		
		//是否已经关闭了
		if (reportExam.getStatus().equals(ReportExamStatusEnum.Closed.toValue())) {
			throw new Exception("考试报表项目已关闭，不可删除考生成绩！");
		}
		
		//判断是否网站出的成绩
		if (data.getSource().equals(ReportExamDataSourceEnum.Site)) {
			
			//是否还在自动汇总成绩
			if (reportExam.getCanAutoGather()) {
				throw new Exception("考试报表每天会自动汇总成绩，会重新提取考生成绩，请过几天再删除！");
			}
		}
		
		//删除考生成绩
		this.daoFacade.getReportExamDataDAO().delete(reportExamDataId);
	}
	
	/**
	 * 修改考试报表的考生成绩
	 * @param reportExamDataId 考生成绩编号
	 * @param score 分数
	 * @param statusEnum 状态
	 * @throws Exception
	 */
	public void modifyReportExamData(Long reportExamDataId, Double score, ReportExamDataStatusEnum statusEnum) throws Exception {
		
		//判断成绩记录是否小于零
		if (score < 0) {
			throw new Exception("考生成绩不能小于零！");
		}
			
		//获取考生成绩记录
		ReportExamData data = this.modelFactoryFacade.getReportExamDataFactory().findById(reportExamDataId);
		
		//判断成绩记录是否存在
		if (data == null) {
			throw new Exception("考生成绩已不存在！");
		}
		
		//获取考试报表
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(data.getReportExamId());
		
		//判断是否存在考试报表
		if (reportExam == null) {
			return;
		}
		
		//是否已经关闭了
		if (reportExam.getStatus().equals(ReportExamStatusEnum.Closed.toValue())) {
			throw new Exception("考试报表项目已关闭，不可修改考生成绩！");
		}
		
		//判断是否网站出的成绩
		if (data.getSource().equals(ReportExamDataSourceEnum.Site)) {
			
			//是否还在自动汇总成绩
			if (reportExam.getCanAutoGather()) {
				throw new Exception("考试报表每天会自动汇总成绩，会重新提取考生成绩，请过几天再修改！");
			}
		}
		
		//修改考生成绩
		data.setScore(score);
		data.setStatus(statusEnum.toValue());
		this.daoFacade.getReportExamDataDAO().update(data);
	}
	
	/**
	 * 导入软件生成的考生成绩包
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param passScore 通过分数
	 * @param file 文件
	 * @throws Exception 
	 */
	public void importSoftwareReportExamData(Long reportExamId, Long organId, Integer passScore, File file) throws Exception {
		
		List<ExamUserResult> list;
		
		try {
			//获取文件内容
			String data = FileUtility.readTXT(file, "UTF-8");
			
			//判断是否存在文件内容
			if (StringUtils.isBlank(data)) {
				throw new Exception("无法读取考生成绩包文件");
			}
			
			//解压缩文件
			String json = ZipUtil.uncompress(data);
			
			//获取考生记录列表
			list = JSONConvertor.json2List(json, ExamUserResult.class);
		} catch (Exception e) {
			throw new Exception("无法读取考生成绩包文件");
		}
	
		//获取考试报表
		ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById(reportExamId);
		
		//判断是否存在考试报表
		if (reportExam == null) {
			throw new Exception("考试报表项目已不存在！");
		}
		
		//是否已经关闭了
		if (reportExam.getStatus().equals(ReportExamStatusEnum.Closed.toValue())) {
			throw new Exception("考试报表项目已关闭，不可导入考生成绩数据包！");
		}
		
		//校验单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(organId);
		
		//判断单位是否存在
		if (organ == null) {
			throw new Exception("此单位记录不存在！");
		}
		
		//单位是否存在网站考试成绩
		if (this.modelFactoryFacade.getReportExamDataFactory().findCountByOrgan_Source(reportExamId, organId, ReportExamDataSourceEnum.Site).intValue() > 0) {
			throw new Exception("此单位已存在网站记录，请检查后再导入！");
		}
		
		//首先清除垃圾数据
		this.daoFacade.getReportExamDataDAO().deleteListByReportExamId_OrganId(reportExamId, organId);
		
		//生成考生成绩数据
		List<ReportExamData> reportExamDataList = new ArrayList<ReportExamData>();
		for (ExamUserResult examUserResult : list) {
			
			ReportExamData reportExamData = new ReportExamData();
			
			reportExamData.setSource(ReportExamDataSourceEnum.Software.toValue());
			reportExamData.setActualName(examUserResult.getActualName());
			reportExamData.setActualOrgan(examUserResult.getOrgan());
			reportExamData.setDutyRank(examUserResult.getDutyRank());
			reportExamData.setExamKey(examUserResult.getExamKey());
			reportExamData.setOrganId(organId);
			reportExamData.setReportExamId(reportExamId);
			reportExamData.setScore(examUserResult.getScore());
			reportExamData.setUserKey(examUserResult.getUserKey());
			
			//判断状态
			if (examUserResult.getStatus().equals(ExamResultStatusEnum.WAIT_TEST.toValue())) {
				reportExamData.setStatus(ReportExamDataStatusEnum.None.toValue());
			}
			if (examUserResult.getStatus().equals(ExamResultStatusEnum.LEAVE_EXAM.toValue())) {
				reportExamData.setStatus(ReportExamDataStatusEnum.Leave.toValue());
			}
			if (examUserResult.getStatus().equals(ExamResultStatusEnum.EXAMED.toValue())) {
			
				//判断分数是否通过
				if (reportExamData.getScore().compareTo(passScore.doubleValue()) >= 0) {
					reportExamData.setStatus(ReportExamDataStatusEnum.Pass.toValue());
				} else {
					reportExamData.setStatus(ReportExamDataStatusEnum.NoPass.toValue());
				}
			}
			
			reportExamDataList.add(reportExamData);
		}
		
		//批量插入数据
		this.daoFacade.getReportExamDataDAO().insertList(reportExamDataList);
	}

}
