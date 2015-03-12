package com.study.dao;


/**
 * 数据操作门面接口
 */
public interface DAOFacade {
	
	ArticleDAO getArticleDAO();
	
	CourseCategoryDAO getCourseCategoryDAO();

	CourseChapterDAO getCourseChapterDAO();
	
	CourseDAO getCourseDAO();
	
	CourseUserDAO getCourseUserDAO();
	
	ExamDAO getExamDAO();
	
	ExamServerDAO getExamServerDAO();
	
	ExamUserDAO getExamUserDAO();
	
	ExamUserDataDAO getExamUserDataDAO();
	
	ManagerDAO getManagerDAO();
	
	NoticeDAO getNoticeDAO();
	
	OrganDAO getOrganDAO();
	
	QuestionCategoryDAO getQuestionCategoryDAO();
	
	QuestionDAO getQuestionDAO();
	
	ReportExamDAO getReportExamDAO();
	
	ReportExamDataDAO getReportExamDataDAO();
	
	ReportExamResultDAO getReportExamResultDAO();
	
	SysParamDAO getSysParamDAO();
	
	TestPaperDAO getTestPaperDAO();

	TestPaperQuestionDAO getTestPaperQuestionDAO();

	UserDAO getUserDAO();
}
