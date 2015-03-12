package com.study.service;

/**
 * 任务调度业务接口
 */
public interface SchedulerService {

	/**
	 * 自动校验单位服务期限
	 */
	void autoValidateOrganServiceLimit();
	
	/**
	 * 自动统计考试报表
	 */
	void autoStatReportExam();
}
