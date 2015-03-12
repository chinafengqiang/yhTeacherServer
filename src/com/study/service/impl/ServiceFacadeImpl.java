package com.study.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.study.service.ArticleService;
import com.study.service.CourseService;
import com.study.service.CourseUserService;
import com.study.service.ExamServerService;
import com.study.service.ExamService;
import com.study.service.ExamUserService;
import com.study.service.ManagerService;
import com.study.service.NoticeService;
import com.study.service.OrganService;
import com.study.service.QuestionService;
import com.study.service.ReportService;
import com.study.service.ServiceFacade;
import com.study.service.SystemService;
import com.study.service.TestPaperService;
import com.study.service.UserService;
import com.study.service.UserSystemService;

/**
 * 业务层门面实现类
 */
@Service
public class ServiceFacadeImpl implements ServiceFacade {

	@Resource
	private ArticleService articleService;

	@Resource
	private CourseService courseService;
	
	@Resource
	private CourseUserService courseUserService;

	@Resource
	private ExamServerService examServerService;

	@Resource
	private ExamService examService;
	
	@Resource
	private ExamUserService examUserService;

	@Resource
	private ManagerService managerService;

	@Resource
	private NoticeService noticeService;

	@Resource
	private OrganService organService;

	@Resource
	private QuestionService questionService;

	@Resource
	private ReportService reportService;

	@Resource
	private SystemService systemService;

	@Resource
	private TestPaperService testPaperService;

	@Resource
	private UserService userService;
	
	@Resource
	private UserSystemService userSystemService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public ExamServerService getExamServerService() {
		return examServerService;
	}

	public void setExamServerService(ExamServerService examServerService) {
		this.examServerService = examServerService;
	}

	public ExamService getExamService() {
		return examService;
	}

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public ManagerService getManagerService() {
		return managerService;
	}

	public void setManagerService(ManagerService managerService) {
		this.managerService = managerService;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public OrganService getOrganService() {
		return organService;
	}

	public void setOrganService(OrganService organService) {
		this.organService = organService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public TestPaperService getTestPaperService() {
		return testPaperService;
	}

	public void setTestPaperService(TestPaperService testPaperService) {
		this.testPaperService = testPaperService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setExamUserService(ExamUserService examUserService) {
		this.examUserService = examUserService;
	}

	public ExamUserService getExamUserService() {
		return examUserService;
	}

	public void setCourseUserService(CourseUserService courseUserService) {
		this.courseUserService = courseUserService;
	}

	public CourseUserService getCourseUserService() {
		return courseUserService;
	}

	public void setUserSystemService(UserSystemService userSystemService) {
		this.userSystemService = userSystemService;
	}

	public UserSystemService getUserSystemService() {
		return userSystemService;
	}
	

}
