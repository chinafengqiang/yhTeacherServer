package com.study.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.study.dao.ArticleDAO;
import com.study.dao.CourseCategoryDAO;
import com.study.dao.CourseChapterDAO;
import com.study.dao.CourseDAO;
import com.study.dao.CourseUserDAO;
import com.study.dao.DAOFacade;
import com.study.dao.ExamDAO;
import com.study.dao.ExamServerDAO;
import com.study.dao.ExamUserDAO;
import com.study.dao.ExamUserDataDAO;
import com.study.dao.ManagerDAO;
import com.study.dao.NoticeDAO;
import com.study.dao.OrganDAO;
import com.study.dao.QuestionCategoryDAO;
import com.study.dao.QuestionDAO;
import com.study.dao.ReportExamDAO;
import com.study.dao.ReportExamDataDAO;
import com.study.dao.ReportExamResultDAO;
import com.study.dao.SysParamDAO;
import com.study.dao.TestPaperDAO;
import com.study.dao.TestPaperQuestionDAO;
import com.study.dao.UserDAO;

/**
 * 数据操作门面实现类
 */
@Repository
public class DAOFacadeImpl implements DAOFacade {

	@Resource
	private ArticleDAO articleDAO;
	
	@Resource
	private CourseCategoryDAO courseCategoryDAO;

	@Resource
	private CourseChapterDAO courseChapterDAO;
	
	@Resource
	private CourseDAO courseDAO;
	
	@Resource
	private CourseUserDAO courseUserDAO;
	
	@Resource
	private ExamDAO examDAO;
	
	@Resource
	private ExamServerDAO examServerDAO;
	
	@Resource
	private ExamUserDAO examUserDAO;
	
	@Resource
	private ExamUserDataDAO examUserDataDAO;
	
	@Resource
	private ManagerDAO managerDAO;
	
	@Resource
	private NoticeDAO noticeDAO;
	
	@Resource
	private OrganDAO organDAO;
	
	@Resource
	private QuestionCategoryDAO questionCategoryDAO;
	
	@Resource
	private QuestionDAO questionDAO;
	
	@Resource
	private ReportExamDAO reportExamDAO;
	
	@Resource
	private ReportExamDataDAO reportExamDataDAO;
	
	@Resource
	private ReportExamResultDAO reportExamResultDAO;
	
	@Resource
	private SysParamDAO sysParamDAO;
	
	@Resource
	private TestPaperDAO testPaperDAO;

	@Resource
	private TestPaperQuestionDAO testPaperQuestionDAO;

	@Resource
	private UserDAO userDAO;

	public ArticleDAO getArticleDAO() {
		return articleDAO;
	}

	public void setArticleDAO(ArticleDAO articleDAO) {
		this.articleDAO = articleDAO;
	}

	public CourseCategoryDAO getCourseCategoryDAO() {
		return courseCategoryDAO;
	}

	public void setCourseCategoryDAO(CourseCategoryDAO courseCategoryDAO) {
		this.courseCategoryDAO = courseCategoryDAO;
	}

	public CourseChapterDAO getCourseChapterDAO() {
		return courseChapterDAO;
	}

	public void setCourseChapterDAO(CourseChapterDAO courseChapterDAO) {
		this.courseChapterDAO = courseChapterDAO;
	}

	public CourseDAO getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	public CourseUserDAO getCourseUserDAO() {
		return courseUserDAO;
	}

	public void setCourseUserDAO(CourseUserDAO courseUserDAO) {
		this.courseUserDAO = courseUserDAO;
	}

	public ExamDAO getExamDAO() {
		return examDAO;
	}

	public void setExamDAO(ExamDAO examDAO) {
		this.examDAO = examDAO;
	}

	public ExamServerDAO getExamServerDAO() {
		return examServerDAO;
	}

	public void setExamServerDAO(ExamServerDAO examServerDAO) {
		this.examServerDAO = examServerDAO;
	}

	public ExamUserDAO getExamUserDAO() {
		return examUserDAO;
	}

	public void setExamUserDAO(ExamUserDAO examUserDAO) {
		this.examUserDAO = examUserDAO;
	}

	public ExamUserDataDAO getExamUserDataDAO() {
		return examUserDataDAO;
	}

	public void setExamUserDataDAO(ExamUserDataDAO examUserDataDAO) {
		this.examUserDataDAO = examUserDataDAO;
	}

	public ManagerDAO getManagerDAO() {
		return managerDAO;
	}

	public void setManagerDAO(ManagerDAO managerDAO) {
		this.managerDAO = managerDAO;
	}

	public NoticeDAO getNoticeDAO() {
		return noticeDAO;
	}

	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public OrganDAO getOrganDAO() {
		return organDAO;
	}

	public void setOrganDAO(OrganDAO organDAO) {
		this.organDAO = organDAO;
	}

	public QuestionCategoryDAO getQuestionCategoryDAO() {
		return questionCategoryDAO;
	}

	public void setQuestionCategoryDAO(QuestionCategoryDAO questionCategoryDAO) {
		this.questionCategoryDAO = questionCategoryDAO;
	}

	public QuestionDAO getQuestionDAO() {
		return questionDAO;
	}

	public void setQuestionDAO(QuestionDAO questionDAO) {
		this.questionDAO = questionDAO;
	}

	public ReportExamDAO getReportExamDAO() {
		return reportExamDAO;
	}

	public void setReportExamDAO(ReportExamDAO reportExamDAO) {
		this.reportExamDAO = reportExamDAO;
	}

	public ReportExamDataDAO getReportExamDataDAO() {
		return reportExamDataDAO;
	}

	public void setReportExamDataDAO(ReportExamDataDAO reportExamDataDAO) {
		this.reportExamDataDAO = reportExamDataDAO;
	}

	public ReportExamResultDAO getReportExamResultDAO() {
		return reportExamResultDAO;
	}

	public void setReportExamResultDAO(ReportExamResultDAO reportExamResultDAO) {
		this.reportExamResultDAO = reportExamResultDAO;
	}

	public SysParamDAO getSysParamDAO() {
		return sysParamDAO;
	}

	public void setSysParamDAO(SysParamDAO sysParamDAO) {
		this.sysParamDAO = sysParamDAO;
	}

	public TestPaperDAO getTestPaperDAO() {
		return testPaperDAO;
	}

	public void setTestPaperDAO(TestPaperDAO testPaperDAO) {
		this.testPaperDAO = testPaperDAO;
	}

	public TestPaperQuestionDAO getTestPaperQuestionDAO() {
		return testPaperQuestionDAO;
	}

	public void setTestPaperQuestionDAO(TestPaperQuestionDAO testPaperQuestionDAO) {
		this.testPaperQuestionDAO = testPaperQuestionDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
}
