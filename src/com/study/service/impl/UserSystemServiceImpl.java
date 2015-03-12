package com.study.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.study.dao.DAOFacade;
import com.study.enums.ArticleTypeEnum;
import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.enums.ExamStatusEnum;
import com.study.enums.ExamUserStatusEnum;
import com.study.enums.NoticeGradeEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.enums.TimerModeEnum;
import com.study.model.Article;
import com.study.model.Course;
import com.study.model.CourseChapter;
import com.study.model.CourseUser;
import com.study.model.Exam;
import com.study.model.ExamUser;
import com.study.model.Notice;
import com.study.model.TestPaper;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.us.USChannel;
import com.study.model.us.USCourseSummary;
import com.study.model.us.USExamSummary;
import com.study.model.us.USLink;
import com.study.service.SystemService;
import com.study.service.UserSystemService;
import com.study.utility.DateUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.PaginateParamters;
import com.study.utility.PaginateResult;
import com.study.utility.StringUtility;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 学员平台业务接口实现类
 */
@Service
public class UserSystemServiceImpl implements UserSystemService {

	/**
	 * 模板存储路径 
	 */
	private static String FtlPath = "system/US/ftl/";
	
	/**
	 * html存储路径
	 */
	private static String HtmlPath = "system/US/html/";
	
	/**
	 * 每页记录数量
	 */
	private static Integer PerPageNumber = 20;
	
	/**
	 * 数据操作门面
	 */
	@Resource
	private DAOFacade daoFacade;
	
	/**
	 * 数据工厂门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 系统工厂门面 
	 */
	@Resource
	private SystemService systemService;
	
	
	/**
	 * 生成学员平台Html文件
	 * @throws Exception 
	 * @throws Exception
	 */
	public void genarateHtmls() throws Exception {
		
		System.out.println("=========GenarateHtmls, Begin " + DateUtility.dateToLongString(DateUtility.getCurDate()));
		
		try {
			this.genarateIndexHtml();
			this.genarateNoticeHtmls();
			this.genarateNewsHtmls();
			this.genarateCaseHtmls();
			this.genarateCourseListHtml();
			this.genarateCourseContentHtmls();
			this.genarateExamListHtml();
		} catch (Exception e) {
			System.out.println("！！！！！！生成学员平台Html Error： + " + e.getMessage());
			throw new Exception("生成学员平台页面文件出错：" + e.getMessage());
		}
		
		System.out.println("=========GenarateHtmls, End   " + DateUtility.dateToLongString(DateUtility.getCurDate()));
	}
	
	/**
	 * 获取本站点名称
	 * @return
	 */
	private String getLocalSiteName() {
		
		return this.systemService.getServerName() + "站点";
	}
	
	/**
	 * 获取子站点列表
	 * @return
	 */
	private List<USLink> getSubSiteLinkList() {
		
		List<USLink> list = new ArrayList<USLink>();
		
		if (!this.systemService.getSysParamValueByString(SysParamTypeEnum.HaveSubSite).equals("是")) {
			return list;
		}
		
		String siteNames = this.systemService.getSysParamValueByString(SysParamTypeEnum.SubSiteName);
		String siteUrls = this.systemService.getSysParamValueByString(SysParamTypeEnum.SubSiteUrl);
		
		String[] siteNameList = siteNames.split(";");
		String[] siteUrlList = siteUrls.split(";");
		
		for (int i=0; i<siteNameList.length; i++) {
			
			USLink link = new USLink();
			link.setName(siteNameList[i]);
			link.setUrl(siteUrlList[i]);
			list.add(link);
		}
		
		return list;
	}
	
	/**
	 * 获取友情链接列表
	 */
	private List<USLink> getFriendSiteLinkList() {
		
		List<USLink> list = new ArrayList<USLink>();
		
		String siteNames = this.systemService.getSysParamValueByString(SysParamTypeEnum.FriendSiteName);
		String siteUrls = this.systemService.getSysParamValueByString(SysParamTypeEnum.FriendSiteUrl);
		
		String[] siteNameList = siteNames.split(";");
		String[] siteUrlList = siteUrls.split(";");
		
		for (int i=0; i<siteNameList.length; i++) {
			
			USLink link = new USLink();
			link.setName(siteNameList[i]);
			link.setUrl(siteUrlList[i]);
			list.add(link);
		}
		
		return list;
	}
	
	/**
	 * 获取首页公告链接列表
	 */
	private List<USLink> getIndexNoticeLinkList() {
		
		List<Notice> noticeList = this.modelFactoryFacade.getNoticeFactory().findListByGrade_Limit(NoticeGradeEnum.User, 6);
		
		List<USLink> list = new ArrayList<USLink>();
		
		for (Notice notice : noticeList) {
			
			USLink link = new USLink();
			link.setName(StringUtility.subStringCN(notice.getTitle(), 40));
			link.setUrl(this.getHtmlFileName_US() + this.getHtmlFileName_BrowseNotice(notice.getId()));
			link.setDateName(notice.getCreatedTimeName());
			list.add(link);
		}
		
		return list;
	}
	
	/**
	 * 创建首页文件
	 * @throws Exception
	 */
	private void genarateIndexHtml() throws Exception {
		
		String ftlFileName = "index.ftl";
		String htmlFileName = "index.html";

        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        dataMap.put("localSiteName", this.getLocalSiteName());
        dataMap.put("subSiteLinkList", this.getSubSiteLinkList());
        dataMap.put("friendSiteLinkList", this.getFriendSiteLinkList());
        dataMap.put("noticeLinkList", this.getIndexNoticeLinkList());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建课程列表文件
	 * @throws Exception
	 */
	private void genarateCourseListHtml() throws Exception {
		
		String ftlFileName = "courseList.ftl";
		String htmlFileName = "courseList.html";

        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	private void genarateCourseContentHtmls() throws Exception {
		
		List<Course> courseList = this.modelFactoryFacade.getCourseFactory().findListByAll();
		
		for (Course course : courseList) {
			
			if (course.getStatus().equals(CourseStatusEnum.Closed.toValue())) {
				continue;
			}

			List<CourseChapter> courseChapterList = this.modelFactoryFacade.getCourseChapterFactory().findListByCourse(course.getId());
			
			String ftlFileName = "courseContent.ftl";
			String htmlFileName = "courseContent-" + course.getId() + ".html";

	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        dataMap.put("courseId", course.getId().toString());
	        dataMap.put("courseName", course.getName());
	        dataMap.put("chapterList", courseChapterList);
	        
	        if (courseChapterList.size() > 0) {
	        	dataMap.put("chapterContent", courseChapterList.get(0).getContent());
	        } else {
	        	dataMap.put("chapterContent", "此课程暂无内容！");
	        }
	        
	        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
		}
	}
	
	/**
	 * 创建课程列表文件
	 * @throws Exception
	 */
	private void genarateExamListHtml() throws Exception {
		
		String ftlFileName = "examList.ftl";
		String htmlFileName = "examList.html";

        Map<String, Object> dataMap = new HashMap<String, Object>();
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建公告列表页面文件
	 * @throws Exception
	 */
	private void genarateNoticeHtmls() throws Exception { 
		
		PaginateParamters paginateParamters = new PaginateParamters();
		
		paginateParamters.setPageNo(1);
		paginateParamters.setPerPageNumber(PerPageNumber);
		
		PaginateResult paginateResult = this.modelFactoryFacade.getNoticeFactory().findListByUser(paginateParamters);
		
		Integer pageCount = paginateResult.getPageCount();
		if (pageCount.equals(0)) {
			pageCount = 1;
		}
		
		for (int i=1; i<=pageCount; i++) {
			
			String ftlFileName = "noticeList.ftl";
			String htmlFileName = "noticeList-" + i + ".html";
			
			paginateParamters.setPageNo(i);
			PaginateResult curPageResult = this.modelFactoryFacade.getNoticeFactory().findListByUser(paginateParamters);
			
			for (Notice notice : (List<Notice>)curPageResult.getList()) {			
				notice.setTitle(StringUtility.subStringCN(notice.getTitle(), 80));
			}
			
			Map<String, Object> dataMap = new HashMap<String, Object>();	        
	        dataMap.put("paginateResult", curPageResult);
	        
	        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
	        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
	        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
	        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
	        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
	        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

	        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	        
	        for (int j=0; j<curPageResult.getList().size(); j++) {
	        	this.genarateNoticeHtml((Notice)curPageResult.getList().get(j));
	        }
		}
	}
	
	/**
	 * 创建公告页面文件
	 * @throws Exception
	 */
	private void genarateNoticeHtml(Notice notice) throws Exception { 
		
			
		String ftlFileName = "notice.ftl";
		String htmlFileName = "notice-" + notice.getId() + ".html";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();	        
        dataMap.put("content", notice.getContent());
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建新闻页面文件
	 * @throws Exception
	 */
	private void genarateNewsHtmls() throws Exception { 
		
		String ftlFileName = "newsHome.ftl";
		String htmlFileName = "newsHome.html";
		
		String channelsValue = this.systemService.getSysParamValueByString(SysParamTypeEnum.NewsChannel);
		
		String[] channels = channelsValue.split(";");
		
		List<USChannel> channelList = new ArrayList<USChannel>();
		
		for (int i=0; i<channels.length; i++) {
			
			USChannel channel = new USChannel();
			channel.setName(channels[i]);
			channel.setUrlList("US-newsChannel" + (i + 1) + "List-1");
			
			PaginateParamters paginateParamters = new PaginateParamters();
			paginateParamters.setPageNo(1);
			paginateParamters.setPerPageNumber(5);
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.News, channels[i], null, paginateParamters);
			List<Article> articleList = paginateResult.getList();
			List<USLink> linkList = new ArrayList<USLink>();
			
			for (Article article : articleList) {
				
				USLink link = new USLink();
				link.setName(StringUtility.subStringCN(article.getTitle(),80));
				link.setUrl("US-news-" + article.getId());
				link.setDateName(article.getCreatedTimeName());
				
				linkList.add(link);
			}
			
			channel.setLinkList(linkList);
			channelList.add(channel);
			
			this.genarateNewsChannelListHtml(channels[i], i + 1);
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();	        
        dataMap.put("channelList", channelList);
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建公告列表页面文件
	 * @throws Exception
	 */
	private void genarateNewsChannelListHtml(String channel, Integer index) throws Exception { 
		
		PaginateParamters paginateParamters = new PaginateParamters();
		
		paginateParamters.setPageNo(1);
		paginateParamters.setPerPageNumber(PerPageNumber);
		
		PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.News, channel, null, paginateParamters);
		
		Integer pageCount = paginateResult.getPageCount();
		if (pageCount.equals(0)) {
			pageCount = 1;
		}
		
		for (int i=1; i<=pageCount; i++) {
			
			String ftlFileName = "newsList.ftl";
			String htmlFileName = "newsChannel" + index + "List-" + i + ".html";
			
			paginateParamters.setPageNo(i);
			PaginateResult curPageResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.News, channel, null, paginateParamters);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();	        
	        dataMap.put("paginateResult", curPageResult);
	        dataMap.put("channelName", channel);
	        dataMap.put("channelListUrlPrefix", "US-newsChannel" + index + "List-");
	        
	        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
	        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
	        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
	        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
	        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
	        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

	        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	        
	        for (int j=0; j<curPageResult.getList().size(); j++) {
	        	this.genarateNewsHtml((Article)curPageResult.getList().get(j));
	        }
		}
	}
	
	/**
	 * 创建新闻页面文件
	 * @throws Exception
	 */
	private void genarateNewsHtml(Article article) throws Exception { 
		
			
		String ftlFileName = "news.ftl";
		String htmlFileName = "news-" + article.getId() + ".html";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();	        
        dataMap.put("content", article.getContent());
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建案例页面文件
	 * @throws Exception
	 */
	private void genarateCaseHtmls() throws Exception { 
		
		String ftlFileName = "caseHome.ftl";
		String htmlFileName = "caseHome.html";
		
		String channelsValue = this.systemService.getSysParamValueByString(SysParamTypeEnum.CaseChannel);
		
		String[] channels = channelsValue.split(";");
		
		List<USChannel> channelList = new ArrayList<USChannel>();
		
		for (int i=0; i<channels.length; i++) {
			
			USChannel channel = new USChannel();
			channel.setName(channels[i]);
			channel.setUrlList("US-caseChannel" + (i + 1) + "List-1");
			
			PaginateParamters paginateParamters = new PaginateParamters();
			paginateParamters.setPageNo(1);
			paginateParamters.setPerPageNumber(5);
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.Case, channels[i], null, paginateParamters);
			List<Article> articleList = paginateResult.getList();
			List<USLink> linkList = new ArrayList<USLink>();
			
			for (Article article : articleList) {
				
				USLink link = new USLink();
				link.setName(StringUtility.subStringCN(article.getTitle(),40));
				link.setUrl("US-case-" + article.getId());
				link.setDateName(article.getCreatedTimeName());
				
				linkList.add(link);
			}
			
			channel.setLinkList(linkList);
			
			if (i % 2 == 0) {
				channel.setArea("Left");
			} else {
				channel.setArea("Right");
			}
			channelList.add(channel);
			
			this.genarateCaseChannelListHtml(channels[i], i + 1);
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();	        
        dataMap.put("channelList", channelList);
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}
	
	/**
	 * 创建公告列表页面文件
	 * @throws Exception
	 */
	private void genarateCaseChannelListHtml(String channel, Integer index) throws Exception { 
		
		PaginateParamters paginateParamters = new PaginateParamters();
		
		paginateParamters.setPageNo(1);
		paginateParamters.setPerPageNumber(PerPageNumber);
		
		PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.Case, channel, null, paginateParamters);
		
		Integer pageCount = paginateResult.getPageCount();
		if (pageCount.equals(0)) {
			pageCount = 1;
		}
		
		for (int i=1; i<=pageCount; i++) {
			
			String ftlFileName = "caseList.ftl";
			String htmlFileName = "caseChannel" + index + "List-" + i + ".html";
			
			paginateParamters.setPageNo(i);
			PaginateResult curPageResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(ArticleTypeEnum.Case, channel, null, paginateParamters);
			
			for (Article article : (List<Article>)curPageResult.getList()) {			
				article.setTitle(StringUtility.subStringCN(article.getTitle(), 80));
			}
			
			Map<String, Object> dataMap = new HashMap<String, Object>();	        
	        dataMap.put("paginateResult", curPageResult);
	        dataMap.put("channelName", channel);
	        dataMap.put("channelListUrlPrefix", "US-caseChannel" + index + "List-");
	        
	        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
	        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
	        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
	        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
	        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
	        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

	        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	        
	        for (int j=0; j<curPageResult.getList().size(); j++) {
	        	this.genarateCaseHtml((Article)curPageResult.getList().get(j));
	        }
		}
	}
	
	/**
	 * 创建案例页面文件
	 * @throws Exception
	 */
	private void genarateCaseHtml(Article article) throws Exception { 
		
			
		String ftlFileName = "case.ftl";
		String htmlFileName = "case-" + article.getId() + ".html";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();	        
        dataMap.put("content", article.getContent());
        
        dataMap.put("indexUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Index());
        dataMap.put("noticeUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Notice());
        dataMap.put("newsUrl", this.getHtmlFileName_US() + this.getHtmlFileName_News());
        dataMap.put("caseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Case());
        dataMap.put("courseUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Course());
        dataMap.put("examUrl", this.getHtmlFileName_US() + this.getHtmlFileName_Exam());

        this.genarateHtml(ftlFileName, htmlFileName, HtmlPath, dataMap);
	}	
	
	/**
	 * 获取学员平台用户课程学员摘要列表
	 * @param userId 学员编号
	 * @param courseList 课程列表
	 * @return
	 */
	public List<USCourseSummary> getUSCourseSummary(Long userId, List<Course> courseList) throws Exception {
		
		List<USCourseSummary> summaryList = new ArrayList<USCourseSummary>();
		
		for (Course course : courseList) {
			
			USCourseSummary summary =  new USCourseSummary();
			summary.setId(course.getId());
			summary.setClassHour(course.getClassHour());
			if (course.getCourseType().equals(CourseTypeEnum.Required.toValue())) {
				summary.setCourseTypeName("【" + CourseTypeEnum.valueOf(course.getCourseType()).toName() + "】");
			} else {
				summary.setCourseTypeName("");
			}
			summary.setCoverImageLink(course.getCoverImageLink());
			summary.setCredit(course.getCredit());
			summary.setDescription(course.getDescription());
			
			if (course.getCanMatchDutyRank()) {
				summary.setDutyRank(course.getDutyRank());
			} else {
				summary.setDutyRank("");
			}
			
			if (course.getCanMatchTrade()) {
				summary.setTrade(course.getTrade());
			} else {
				summary.setTrade("");
			}
			
			summary.setName(course.getName());
			summary.setPassCreditLimit(course.getPassCreditLimit());
			summary.setStudyNum(course.getStudyNum());
				
			CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(course.getId(), userId);
			
			if (courseUser == null) {
				summary.setUserStatusName("待学");
			} else {
				if (courseUser.getStatus().equals(CourseUserStatusEnum.None.toValue())) {
					summary.setUserStatusName("待学");
				}
				if (courseUser.getStatus().equals(CourseUserStatusEnum.Doing.toValue())) {
					summary.setUserStatusName("在学");
				}
				if (courseUser.getStatus().equals(CourseUserStatusEnum.Done.toValue())) {
					summary.setUserStatusName("已学完");
				}
			}
			
			summaryList.add(summary);
		}
		
		return summaryList;
	}
	
	/**
	 * 获取学员平台用户考试摘要列表
	 * @param userId 学员编号
	 * @param examList 考试列表
	 * @return
	 */
	public List<USExamSummary> getUSExamSummary(Long userId, List<Exam> examList) throws Exception {
		
		List<USExamSummary> summaryList = new ArrayList<USExamSummary>();
		
		for (Exam exam : examList) {
			
			TestPaper testPaper = this.modelFactoryFacade.getTestPaperFactory().findById(exam.getTestPaperId());
			
			USExamSummary summary =  new USExamSummary();
			
			summary.setId(exam.getId());
			summary.setName(exam.getName());
			summary.setDescription(exam.getDescription());
			summary.setExamStatusName(ExamStatusEnum.valueOf(exam.getStatus()).toName());
			summary.setPassScore(testPaper.getPassScore());
			summary.setTotalScore(testPaper.getTotalScore());
			summary.setExamTimeLimit(getExamSummary(exam));
			
			if (exam.getCanMatchDutyRank()) {
				summary.setDutyRank(exam.getDutyRank());
			} else {
				summary.setDutyRank("");
			}
			
			if (exam.getCanMatchTrade()) {
				summary.setTrade(exam.getTrade());
			} else {
				summary.setTrade("");
			}
			
			if (exam.getCanCourseStudyLimit()) {
				summary.setCourseStudyLimit("需学完必修课程");
			} else {
				summary.setCourseStudyLimit("");
			}
			
			ExamUser examUser = this.modelFactoryFacade.getExamUserFactory().findByExamId_UserId(exam.getId(), userId);
			
			summary.setScoreName("");
			summary.setUrlExamServer("");
			
			if (exam.getTimerMode().equals(TimerModeEnum.Limit.toValue())) {
				summary.setTimerLimit(exam.getTimerLimit());
			} else {
				summary.setTimerLimit(0);
			}
			
			
			if (examUser == null) {
				summary.setUserStatusName("待考");
			} else {
				summary.setUserStatusName(ExamUserStatusEnum.valueOf(examUser.getStatus()).toName());
				if (examUser.getStatus().equals(ExamUserStatusEnum.Done.toValue())) {
					
					//判断是否保密分数
					if (!exam.getCanKeepSecretScore()) {
						summary.setScoreName(examUser.getScore() + "分");
					}
				}
				
				if (exam.getStatus().equals(ExamStatusEnum.Deployed.toValue()) || exam.getStatus().equals(ExamStatusEnum.Opened.toValue())) {
					
					Map<String, Object> map = (HashMap<String, Object>)JSONConvertor.json2Map(examUser.getExamServerData());
					String url = (String)map.get("examServerUrl");
					url = url + "/exam/" + exam.getEsExamKey() + ".html";
					
					summary.setUrlExamServer(url);
				}
			}
			
			if (exam.getStatus().equals(ExamStatusEnum.Deployed.toValue()) || exam.getStatus().equals(ExamStatusEnum.Opened.toValue())) {
				summary.setCanShowExamServer("是");
				
				if (examUser != null && examUser.getStatus().equals(ExamUserStatusEnum.Done.toValue())) {
					summary.setCanShowExamServer("否");
				}
			} else {
				summary.setCanShowExamServer("否");
			}

			summaryList.add(summary);
		}
		
		return summaryList;
	}
	
	/**
	 * 获取考试摘要
	 * @param exam 考试摘要
	 * @return
	 */
	private String getExamSummary(Exam exam) {
		
		String rt = "";
		
		//限制进场时间，但不是统一交卷
		if (exam.getCanLimitValidTime() && !exam.getCanLimitCommitTime()) {

			rt = "时间：" + DateUtility.dateToChineseString(exam.getValidFirstTime(), true) + " " +
				DateUtility.dateToTimeString(exam.getValidFirstTime(), true) + " -- " + 
				DateUtility.dateToTimeString(exam.getValidLastTime(), true);
		}
		
		//限制进场时间，是统一交卷
		if (exam.getCanLimitValidTime() && exam.getCanLimitCommitTime()) {
			
			rt = "时间：" + DateUtility.dateToChineseString(exam.getValidFirstTime(), true) + " " +
			DateUtility.dateToTimeString(exam.getValidFirstTime(), true) + "　统一交卷  ";
		}
		
		//计时模式
		if (exam.getTimerMode().equals(TimerModeEnum.Limit.toValue())) {
			
			rt = rt + "　　时长：" + exam.getTimerLimit() + "分";
		}
		
		return rt;
	}
	
	/**
	 * 生成Html文件
	 * @param ftlFileName 模板文件名称
	 * @param htmlFileName Html文件名称
	 * @param dataMap 数据集
	 * @throws Exception
	 */
	private void genarateHtml(String ftlFileName, String htmlFileName, String htmlPath, Map<String, Object> dataMap) throws Exception {
		
//		Configuration freemarkerCfg = new Configuration();  
//        freemarkerCfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/" + FtlPath);  
//        freemarkerCfg.setEncoding(Locale.getDefault(), "utf-8");  
//        Template template = freemarkerCfg.getTemplate(ftlFileName);  
//        template.setEncoding("utf-8");  
//        String path = ServletActionContext.getServletContext().getRealPath("/") + htmlPath;  
//        File htmlFile = new File(path + htmlFileName);  
//        
//        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "utf-8"));  
//        template.process(dataMap, out);  
//        out.flush();  
//        out.close(); 
	}
	
	/**
	 * 获取学员平台Url前缀
	 * @return 
	 */
	private String getHtmlFileName_US() {
		
		return "US-";
	}

	/**
	 * 获取浏览公告页面文件名称
 	 * @param id 公告编号
	 * @return 浏览公告页面文件名称
	 */
	private String getHtmlFileName_BrowseNotice(Long id) {
		
		return "notice-" + id;
	}
	
	/**
	 * 获取首页模块页面文件名称
	 * @return 首页模块页面文件名称
	 */
	private String getHtmlFileName_Index() {
		
		return "index";
	}
	
	/**
	 * 获取公告模块页面文件名称
	 * @return 公告模块页面文件名称
	 */
	private String getHtmlFileName_Notice() {
		
		return "noticeList-1";
	}
	
	/**
	 * 获取新闻模块页面文件名称
	 * @return 新闻模块页面文件名称
	 */
	private String getHtmlFileName_News() {
		
		return "newsHome";
	}
	
	/**
	 * 获取案例模块页面文件名称
	 * @return 案例模块页面文件名称
	 */
	private String getHtmlFileName_Case() {
		
		return "caseHome";
	}
	
	/**
	 * 获取课程模块页面文件名称
	 * @return 课程模块页面文件名称
	 */
	private String getHtmlFileName_Course() {
		
		return "courseList";
	}
	
	/**
	 * 获取课程模块页面文件名称
	 * @return 课程模块页面文件名称
	 */
	private String getHtmlFileName_Exam() {
		
		return "examList";
	}
 
}
