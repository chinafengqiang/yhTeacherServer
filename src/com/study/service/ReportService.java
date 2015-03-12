package com.study.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.study.enums.ReportExamDataSourceEnum;
import com.study.enums.ReportExamDataStatusEnum;
import com.study.enums.ReportExamStatusEnum;
import com.study.model.ReportExam;
import com.study.model.ReportExamData;
import com.study.model.ReportExamResult;
import com.study.model.part.ReportExamResultOrgan;
import com.study.model.part.ReportExamResultScore;
import com.study.utility.DateUtility;


/**
 * 报表业务接口
 */
public interface ReportService {

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
	ReportExam createReportExam(String name, String examCode, Boolean canAutoGather, ReportExamStatusEnum statusEnum, Long curManagerId) throws Exception;
	
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
	ReportExam modifyReportExam(Long id, String name, Boolean canAutoGather, ReportExamStatusEnum statusEnum, Long curManagerId) throws Exception;
	
	/**
	 * 删除考试报表项目
	 * @param id 考试报表编号
	 * @param curManagerId 当前教师编号
	 * @throws Exception
	 */
	void removeReportExam(Long id, Long curManagerId) throws Exception;
	
	/**
	 * 自动统计考试报表
	 * @throws Exception
	 */
	void autoStatReportExam() throws Exception;
	
	/**
	 * 按单位获取考试报表结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 */
	List<ReportExamResultOrgan> getReportExamResultOrganList(Long reportExamId, Long organId);
	
	/**
	 * 按单位获取分数段统计结果列表
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 */
	List<ReportExamResultScore> getReportExamResultScoreList(Long reportExamId, Long organId);
	
	/**
	 * 将单位考试统计列表导出到Excel
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportReportExamResultOrganToExcel(Long reportExamId, Long organId) throws IOException;
	
	/**
	 * 将单位考试分数段统计列表导出到Excel
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @return
	 * @throws IOException
	 */
	InputStream exportReportExamResultScoreToExcel(Long reportExamId, Long organId) throws IOException;
	
	/**
	 * 获取本站点顶级单位的考试统计报表数据
	 * @param examCode 考试代号
	 * @return
	 * @throws Exception
	 */
	ReportExamResult getReportExamResultByTopOrgan(String examCode) throws Exception;
	
	/**
	 * 删除考试报表的考生成绩
	 * @param reportExamDataId 考生成绩编号
	 * @throws Exception
	 */
	void removeReportExamData(Long reportExamDataId) throws Exception;
	
	/**
	 * 修改考试报表的考生成绩
	 * @param reportExamDataId 考生成绩编号
	 * @param score 分数
	 * @param statusEnum 状态
	 * @throws Exception
	 */
	void modifyReportExamData(Long reportExamDataId, Double score, ReportExamDataStatusEnum statusEnum) throws Exception;
	
	/**
	 * 导入软件生成的考生成绩包
	 * @param reportExamId 考试报表编号
	 * @param organId 单位编号
	 * @param passScore 通过分数
	 * @param file 文件
	 * @throws Exception 
	 * @throws Exception
	 */
	void importSoftwareReportExamData(Long reportExamId, Long organId, Integer passScore, File file) throws Exception;

}
