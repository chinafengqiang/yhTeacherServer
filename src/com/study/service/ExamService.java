package com.study.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.examserver.model.EsExamUser;
import com.study.enums.ExamModeEnum;
import com.study.enums.ExamServerStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.enums.QuestionFetchTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.enums.TimerModeEnum;
import com.study.model.Exam;
import com.study.model.ExamServer;
import com.study.model.TestPaper;
import com.study.model.TestPaperQuestion;
import com.study.utility.ActionPostUtility;
import com.study.utility.JSONConvertor;


/**
 * 考试业务接口
 */
public interface ExamService {

	/**
	 * 创建考试
	 * @param name 名称
	 * @param description 描述 
	 * @param examCode 代号
	 * @param examModeEnum 考试模式
	 * @param category 分类
	 * @param testPaperId 试卷编号
	 * @param questionFetchTypeEnum 分发方式
	 * @param validFirstTime 起始时间
	 * @param validLastTime 结束时间
	 * @param timerModeEnum 计时模式
	 * @param timerLimit 时间限制
	 * @param canAllowAllUser 是否允许所有学员
	 * @param canAllowMultiJoin 是否允许学员在考试时间内多次参加考试
	 * @param canCourseStudyLimit 是否必须完成必修课
	 * @param canKeepSecretScore 是否分数保密
	 * @param canQueryAnswer 是否可以查询答卷
	 * @param canLimitValidTime 是否限制考场试卷
	 * @param canLimitCommitTime 是否要求统一交卷
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param curManagerId 当前管理员编号
	 * @return 考试
	 * @throws Exception
	 */
	Exam createExam(String name, String description, String examCode, ExamModeEnum examModeEnum, String category, 
			Long testPaperId, QuestionFetchTypeEnum questionFetchTypeEnum, Date validFirstTime, Date validLastTime, 
			TimerModeEnum timerModeEnum, Integer timerLimit, Boolean canAllowAllUser, Boolean canAllowMultiJoin, Boolean canCourseStudyLimit, 
			Boolean canKeepSecretScore, Boolean canQueryAnswer, Boolean canLimitValidTime,
			Boolean canLimitCommitTime, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, 
			String trade, Long curManagerId) throws Exception;

	/**
	 * @param id
	 * @param name 名称
	 * @param description 描述 
	 * @param category 分类
	 * @param testPaperId 试卷编号
	 * @param questionFetchTypeEnum 分发方式
	 * @param validFirstTime 起始时间
	 * @param validLastTime 结束时间
	 * @param timerModeEnum 计时模式
	 * @param timerLimit 时间限制
	 * @param canAllowAllUser 是否允许所有学员
	 * @param canAllowMultiJoin 是否允许学员在考试时间内多次参加考试
	 * @param canCourseStudyLimit 是否必须完成必修课
	 * @param canKeepSecretScore 是否分数保密
	 * @param canQueryAnswer 是否可以查询答卷
	 * @param canLimitValidTime 是否限制考场试卷
	 * @param canLimitCommitTime 是否要求统一交卷
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	Exam modifyExam(Long id, String name, String description, String category, 
			Long testPaperId, QuestionFetchTypeEnum questionFetchTypeEnum, Date validFirstTime, Date validLastTime, 
			TimerModeEnum timerModeEnum, Integer timerLimit, Boolean canAllowAllUser, Boolean canAllowMultiJoin, Boolean canCourseStudyLimit, 
			Boolean canKeepSecretScore, Boolean canQueryAnswer, Boolean canLimitValidTime,
			Boolean canLimitCommitTime, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, 
			String trade, Long curManagerId) throws Exception;
	
	/**
	 * 修改考试学员范围
	 * @param id 编号
	 * @param userJoinedData 修改考试学员范围
	 * @param curManagerId 当前教师编号
	 * @return
	 * @throws Exception
	 */
	Exam modifyExamUserJoinedData(Long id, String userJoinedData, Long curManagerId) throws Exception;
	
	/**
	 * 删除考试
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void removeExam(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 导入考试
	 * @param file 文件
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	void importExam(File file, Long curManagerId) throws Exception;
	
	/**
	 * 导出考试
	 * @param examId 考试编号
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	String exportExam(Long id, Long curManagerId) throws Exception;
	/**
	 * 部署考试
	 * @param id 考试编号
	 * @param mainServerUrl 主服务器链接
	 * @param managerId 教师编号
	 * @throws Exception
	 */
	void deployExam(Long id, String mainServerUrl, Long managerId) throws Exception;
	
	/**
	 * 启动考试
	 * @param id 考试编号
	 * @param managerId 教师编号
	 * @throws Exception
	 */
	void openExam(Long id, Long managerId) throws Exception;
	
	/**
	 * 停止考试
	 * @param id 考试编号
	 * @param managerId 教师编号
	 * @throws Exception
	 */
	void closeExam(Long id, Long managerId) throws Exception;
	
	/**
	 * 汇总考试成绩
	 * @param id 考试编号
	 * @param managerId 教师编号
	 * @throws Exception
	 */
	void gatherExam(Long id, Long managerId) throws Exception;
	
	/**
	 * 清除考试
	 * @param id 考试编号
	 * @param managerId 教师编号
	 * @throws Exception
	 */
	void destroyExam(Long id, Long managerId) throws Exception;
}
