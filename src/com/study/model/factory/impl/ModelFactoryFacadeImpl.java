package com.study.model.factory.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.study.model.factory.ArticleFactory;
import com.study.model.factory.CourseCategoryFactory;
import com.study.model.factory.CourseChapterFactory;
import com.study.model.factory.CourseFactory;
import com.study.model.factory.CourseUserFactory;
import com.study.model.factory.ExamFactory;
import com.study.model.factory.ExamServerFactory;
import com.study.model.factory.ExamUserDataFactory;
import com.study.model.factory.ExamUserFactory;
import com.study.model.factory.ManagerFactory;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.factory.NoticeFactory;
import com.study.model.factory.OrganFactory;
import com.study.model.factory.QuestionCategoryFactory;
import com.study.model.factory.QuestionFactory;
import com.study.model.factory.ReportExamDataFactory;
import com.study.model.factory.ReportExamFactory;
import com.study.model.factory.ReportExamResultFactory;
import com.study.model.factory.SysParamFactory;
import com.study.model.factory.TestPaperFactory;
import com.study.model.factory.TestPaperQuestionFactory;
import com.study.model.factory.UserFactory;

/**
 * 数据工厂门面实现类
 */
@Repository
public class ModelFactoryFacadeImpl implements ModelFactoryFacade {

	@Resource
	private ArticleFactory articleFactory;
	
	@Resource
	private CourseCategoryFactory courseCategoryFactory;

	@Resource
	private CourseChapterFactory courseChapterFactory;
	
	@Resource
	private CourseFactory courseFactory;
	
	@Resource
	private CourseUserFactory courseUserFactory;
	
	@Resource
	private ExamFactory examFactory;
	
	@Resource
	private ExamServerFactory examServerFactory;
	
	@Resource
	private ExamUserFactory examUserFactory;
	
	@Resource
	private ExamUserDataFactory examUserDataFactory;
	
	@Resource
	private ManagerFactory managerFactory;
	
	@Resource
	private NoticeFactory noticeFactory;
	
	@Resource
	private OrganFactory organFactory;
	
	@Resource
	private QuestionCategoryFactory questionCategoryFactory;
	
	@Resource
	private QuestionFactory questionFactory;
	
	@Resource
	private ReportExamFactory reportExamFactory;
	
	@Resource
	private ReportExamDataFactory reportExamDataFactory;
	
	@Resource
	private ReportExamResultFactory reportExamResultFactory;
	
	@Resource
	private SysParamFactory sysParamFactory;
	
	@Resource
	private TestPaperFactory testPaperFactory;

	@Resource
	private TestPaperQuestionFactory testPaperQuestionFactory;

	@Resource
	private UserFactory userFactory;

	public ArticleFactory getArticleFactory() {
		return articleFactory;
	}

	public void setArticleFactory(ArticleFactory articleFactory) {
		this.articleFactory = articleFactory;
	}

	public CourseCategoryFactory getCourseCategoryFactory() {
		return courseCategoryFactory;
	}

	public void setCourseCategoryFactory(CourseCategoryFactory courseCategoryFactory) {
		this.courseCategoryFactory = courseCategoryFactory;
	}

	public CourseChapterFactory getCourseChapterFactory() {
		return courseChapterFactory;
	}

	public void setCourseChapterFactory(CourseChapterFactory courseChapterFactory) {
		this.courseChapterFactory = courseChapterFactory;
	}

	public CourseFactory getCourseFactory() {
		return courseFactory;
	}

	public void setCourseFactory(CourseFactory courseFactory) {
		this.courseFactory = courseFactory;
	}

	public CourseUserFactory getCourseUserFactory() {
		return courseUserFactory;
	}

	public void setCourseUserFactory(CourseUserFactory courseUserFactory) {
		this.courseUserFactory = courseUserFactory;
	}

	public ExamFactory getExamFactory() {
		return examFactory;
	}

	public void setExamFactory(ExamFactory examFactory) {
		this.examFactory = examFactory;
	}

	public ExamServerFactory getExamServerFactory() {
		return examServerFactory;
	}

	public void setExamServerFactory(ExamServerFactory examServerFactory) {
		this.examServerFactory = examServerFactory;
	}

	public ExamUserFactory getExamUserFactory() {
		return examUserFactory;
	}

	public void setExamUserFactory(ExamUserFactory examUserFactory) {
		this.examUserFactory = examUserFactory;
	}

	public ExamUserDataFactory getExamUserDataFactory() {
		return examUserDataFactory;
	}

	public void setExamUserDataFactory(ExamUserDataFactory examUserDataFactory) {
		this.examUserDataFactory = examUserDataFactory;
	}

	public ManagerFactory getManagerFactory() {
		return managerFactory;
	}

	public void setManagerFactory(ManagerFactory managerFactory) {
		this.managerFactory = managerFactory;
	}

	public NoticeFactory getNoticeFactory() {
		return noticeFactory;
	}

	public void setNoticeFactory(NoticeFactory noticeFactory) {
		this.noticeFactory = noticeFactory;
	}

	public OrganFactory getOrganFactory() {
		return organFactory;
	}

	public void setOrganFactory(OrganFactory organFactory) {
		this.organFactory = organFactory;
	}

	public QuestionCategoryFactory getQuestionCategoryFactory() {
		return questionCategoryFactory;
	}

	public void setQuestionCategoryFactory(QuestionCategoryFactory questionCategoryFactory) {
		this.questionCategoryFactory = questionCategoryFactory;
	}

	public QuestionFactory getQuestionFactory() {
		return questionFactory;
	}

	public void setQuestionFactory(QuestionFactory questionFactory) {
		this.questionFactory = questionFactory;
	}

	public ReportExamFactory getReportExamFactory() {
		return reportExamFactory;
	}

	public void setReportExamFactory(ReportExamFactory reportExamFactory) {
		this.reportExamFactory = reportExamFactory;
	}

	public ReportExamDataFactory getReportExamDataFactory() {
		return reportExamDataFactory;
	}

	public void setReportExamDataFactory(ReportExamDataFactory reportExamDataFactory) {
		this.reportExamDataFactory = reportExamDataFactory;
	}

	public ReportExamResultFactory getReportExamResultFactory() {
		return reportExamResultFactory;
	}

	public void setReportExamResultFactory(ReportExamResultFactory reportExamResultFactory) {
		this.reportExamResultFactory = reportExamResultFactory;
	}

	public SysParamFactory getSysParamFactory() {
		return sysParamFactory;
	}

	public void setSysParamFactory(SysParamFactory sysParamFactory) {
		this.sysParamFactory = sysParamFactory;
	}

	public TestPaperFactory getTestPaperFactory() {
		return testPaperFactory;
	}

	public void setTestPaperFactory(TestPaperFactory testPaperFactory) {
		this.testPaperFactory = testPaperFactory;
	}

	public TestPaperQuestionFactory getTestPaperQuestionFactory() {
		return testPaperQuestionFactory;
	}

	public void setTestPaperQuestionFactory(TestPaperQuestionFactory testPaperQuestionFactory) {
		this.testPaperQuestionFactory = testPaperQuestionFactory;
	}

	public UserFactory getUserFactory() {
		return userFactory;
	}

	public void setUserFactory(UserFactory userFactory) {
		this.userFactory = userFactory;
	}
}
