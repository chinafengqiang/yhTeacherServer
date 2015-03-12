package com.study.model.factory;



/**
 * 数据工厂门面接口
 */
public interface ModelFactoryFacade {

	ArticleFactory getArticleFactory();
	
	CourseCategoryFactory getCourseCategoryFactory();

	CourseChapterFactory getCourseChapterFactory();
	
	CourseFactory getCourseFactory();
	
	CourseUserFactory getCourseUserFactory();
	
	ExamFactory getExamFactory();
	
	ExamServerFactory getExamServerFactory();
	
	ExamUserFactory getExamUserFactory();
	
	ExamUserDataFactory getExamUserDataFactory();
	
	ManagerFactory getManagerFactory();
	
	NoticeFactory getNoticeFactory();
	
	OrganFactory getOrganFactory();
	
	QuestionCategoryFactory getQuestionCategoryFactory();
	
	QuestionFactory getQuestionFactory();
	
	ReportExamFactory getReportExamFactory();
	
	ReportExamDataFactory getReportExamDataFactory();
	
	ReportExamResultFactory getReportExamResultFactory();
	
	SysParamFactory getSysParamFactory();
	
	TestPaperFactory getTestPaperFactory();

	TestPaperQuestionFactory getTestPaperQuestionFactory();

	UserFactory getUserFactory();
}
