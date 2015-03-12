package com.study.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.study.service.OrganService;
import com.study.service.ReportService;
import com.study.service.SchedulerService;
import com.study.service.UserSystemService;

/**
 * 任务调度接口
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(SchedulerServiceImpl.class);
	
	/**
	 * 单位业务接口
	 */
	@Resource
	private OrganService organService;
	
	/**
	 * 报表业务接口
	 */
	@Resource
	private ReportService reportService;
	
	/**
	 * 学员平台业务层接口
	 */
	@Resource
	private UserSystemService userSystemService;
	
	/**
	 * 自动校验单位服务期限
	 */
	public void autoValidateOrganServiceLimit() {
		
		try {
			this.organService.autoValidateOrganServiceLimit();
		} catch (Exception ex) {
			logger.error("autoValidateOrganServiceLimit:" + ex.getMessage()); 
		}
	}
	
	/**
	 * 自动统计考试报表
	 */
	public void autoStatReportExam() {
		
		try {
			this.reportService.autoStatReportExam();
		} catch (Exception ex) {
			logger.error("autoStatReportExam:" + ex.getMessage()); 
		}
	}
	
	/**
	 * 自动生成学员平台页面数据
	 */
	public void autoGenarateHtmls() {
		
		try {
			this.userSystemService.genarateHtmls();	
		} catch (Exception ex) {
			logger.error("autoGenarateHtmls:" + ex.getMessage()); 
		}
	}
}
