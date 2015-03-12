package com.study.service;

/**
 * 业务层门面接口
 */
public interface ServiceFacade {

	ArticleService getArticleService();

	CourseService getCourseService();
	
	CourseUserService getCourseUserService();

	ExamServerService getExamServerService();

	ExamService getExamService();
	
	ExamUserService getExamUserService();

	ManagerService getManagerService();

	NoticeService getNoticeService();

	OrganService getOrganService();

	QuestionService getQuestionService();

	ReportService getReportService();

	SystemService getSystemService();

	TestPaperService getTestPaperService();

	UserService getUserService();
	
	UserSystemService getUserSystemService();
}
