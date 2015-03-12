package com.study.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.study.dao.DAOFacade;
import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.model.Course;
import com.study.model.CourseCategory;
import com.study.model.CourseChapter;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.CourseService;
import com.study.service.ManagerService;
import com.study.service.OrganService;
import com.study.service.SystemService;
import com.study.service.UserService;
import com.study.utility.DateUtility;
import com.study.utility.FileUtility;
import com.study.utility.HexStrUtility;
import com.study.utility.JSONConvertor;
import com.study.utility.SystemUtility;

/**
 * 课程业务接口实现类
 */
@Service
public class CourseServiceImpl implements CourseService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(CourseServiceImpl.class);
	
	/**
	 * 课程树列表缓存Key
	 */
	private static String CourseCategoryListByTreeMemKey = "CourseCategoryListByTreeMemKey";
	
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
	 * 教师业务接口 
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 学员业务接口 
	 */
	@Resource
	private UserService userService;
	
	/**
	 * 单位业务接口 
	 */
	@Resource
	private OrganService organService;
	
	/**
	 * Memcached客户端
	 */
//	@Resource
//	private MemcachedClient memcachedClient;
	
	/**
	 * 获取课程章节内容
	 * @param id 章节编号
	 * @return
	 * @throws Exception
	 */
	public CourseChapter getCourseChapter(Long id) throws Exception {
		
		//定义课程章节内容在内存中的关键字
//		String courseChapterMemKey = "courseChapter" + id;
//		
//		//首先从内存中获取课程内
//		CourseChapter courseChapter = this.memcachedClient.get(courseChapterMemKey, this.systemService.getMemGetTimeOut());
//
//		if (courseChapter == null) {
//			
//			courseChapter = this.modelFactoryFacade.getCourseChapterFactory().findById(id);
//
//			if (courseChapter == null) {
//				throw new Exception("无法获取课程内容！");
//			}
//			
//			this.memcachedClient.set(courseChapterMemKey, 3600*24, courseChapter);
//		}
		
//		return courseChapter;
		return null;
	}

	/**
	 * 创建分类同级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public CourseCategory createCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
		
		//获取当前节点
		CourseCategory curCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//判断是否顶级节点
		if (curCategory.getParentId().equals(0l)) {
			throw new Exception("不可创建同级的顶级节点!");
		}
		
		//创建同级节点
		CourseCategory category = new CourseCategory();
		
		category.setName(name);
		category.setSortFlag(sortFlag);
		category.setParentId(curCategory.getParentId());
		category.setLevel(curCategory.getLevel());
		
		this.daoFacade.getCourseCategoryDAO().insert(category);
		
 		return category;
	}
	
	/**
	 * 创建分类下级节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public CourseCategory createChildCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
	
		//获取当前节点
		CourseCategory curCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//创建下级节点
		CourseCategory category = new CourseCategory();
		
		category.setName(name);
		category.setSortFlag(sortFlag);
		category.setParentId(curCategoryId);
		category.setLevel(curCategory.getLevel() + 1);
		
		this.daoFacade.getCourseCategoryDAO().insert(category);
		
		//清除分类树缓存
	//	this.clearCourseCategoryListByTreeCache();
		
 		return category;
	}
	
	/**
	 * 修改分类节点
	 * @param curCategoryId 当前节点编号
	 * @param name 名称
	 * @param sortFlag 排序
	 * @return 分类节点
	 * @throws Exception
	 */
	public CourseCategory modifyCategory(Long curCategoryId, String name, Integer sortFlag) throws Exception {
	
		//获取当前节点
		CourseCategory curCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		curCategory.setName(name);
		curCategory.setSortFlag(sortFlag);
		
		this.daoFacade.getCourseCategoryDAO().update(curCategory);
		
		//清除分类树缓存
//		this.clearCourseCategoryListByTreeCache();

 		return curCategory;
	}
	
	/**
	 * 删除节点
	 * @param curCategoryId 当前节点编号
	 * @throws Exception
	 */
	public void removeCategory(Long curCategoryId) throws Exception {
		
		//获取当前节点
		CourseCategory curCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			return;
		}
		
		//判断是否顶级节点
		if (curCategory.getParentId().equals(0l)) {
			throw new Exception("不可删除顶级节点!");
		}
		
		//判断是否存在下级
		if (this.modelFactoryFacade.getCourseCategoryFactory().findCountByParant(curCategoryId) > 0) {
			throw new Exception("存在下级分类节点，不可删除!");
		}
		
		//判断是否存在课程
		if (this.modelFactoryFacade.getCourseFactory().findCountByCourseCategory(curCategoryId) > 0) {
			throw new Exception("此分类节点下存在课程，不可删除!");
		}
		
		//删除课程分类
		this.daoFacade.getCourseCategoryDAO().delete(curCategoryId);
		
		//清除分类树缓存
//		this.clearCourseCategoryListByTreeCache();
	}
	
	/**
	 * 校验课程记录
	 * @param course 课程
	 * @throws Exception 
	 */
	private void validateCourse(Course course) throws Exception {
		
		//判断是否填写了课程
		if (StringUtils.isBlank(course.getName())) {
			throw new Exception("需要填写课程名称！");
		}
		
		//判断是否选择了封面图片
		if (StringUtils.isBlank(course.getCoverImageLink())) {
			throw new Exception("需要选择课程封面图片！");
		}
		
		//判断匹配职务级别
		if (course.getCanMatchDutyRank()) {
			if (StringUtils.isBlank(course.getDutyRank())) {
				throw new Exception("需要选择职务级别！");
			}
		}
		
		//判断匹配行业
		if (course.getCanMatchTrade()) {
			if (StringUtils.isBlank(course.getTrade())) {
				throw new Exception("需要选择行业！");
			}
		}
		
		//判断学时是否大于10分钟
		if(course.getClassHour() <=0 ){
			throw new Exception("学时必须大于10分钟");
		}
		
		//判断学习次数是否大于0
		if(course.getStudyNum() <=0 ){
			throw new Exception("学习次数必须大于0");
		}
		
		//判断必修学分是否大于总学分
		if(course.getPassCreditLimit() > course.getCredit()){
			throw new Exception("必修学分不可大于总学分");
		}
		
		//判断必修学分是否大于等于总学分的60%
		double d = course.getCredit() * 0.6;
		
		if(course.getPassCreditLimit() < d){
			throw new Exception("必修学分需大于等于总学分的60%");
		}
	}
	
	/**
	 * 创建课程
	 * @param curCategoryId 分类编号
	 * @param courseTypeEnum 课程类型
	 * @param name 名称
	 * @param description 描述
	 * @param coverImageLink 封面链接
	 * @param classHour 学时
	 * @param credit 学分
	 * @param passCreditLimit 必修学分
	 * @param studyNum 学习次数
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param canAllowAllUser 是否允许所有学员学习
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师编号
	 * @return 课程
	 * @throws Exception
	 */
	public Course createCourse(Long curCategoryId, CourseTypeEnum courseTypeEnum, 
			String name, String description, String coverImageLink, Integer classHour, 
			Integer credit,	Integer passCreditLimit, Integer studyNum, Boolean canMatchDutyRank,
			String dutyRank, Boolean canMatchTrade, String trade, Boolean canAllowAllUser,
			CourseStatusEnum statusEnum, Long curManagerId) throws Exception {

		//获取当前节点
		CourseCategory curCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById(curCategoryId);
		
		//判断当前节点是否存在
		if (curCategory == null) {
			throw new Exception("当前分类节点已不存在!");
		}
		
		//创建课程
		Course course = new Course();
		
		course.setCourseCategoryId(curCategoryId);
		course.setName(name);
		course.setCourseType(courseTypeEnum.toValue());
		course.setName(name);
		course.setDescription(description);
		course.setCoverImageLink(coverImageLink);
		course.setClassHour(classHour);
		course.setUserJoinedData("");
		course.setJoinedOrgans("");
		course.setCredit(credit);
		course.setPassCreditLimit(passCreditLimit);
		course.setStudyNum(studyNum);
		course.setCanMatchDutyRank(canMatchDutyRank);
		course.setDutyRank(dutyRank);
		course.setCanMatchTrade(canMatchTrade);
		course.setTrade(trade);
		course.setCanAllowAllUser(canAllowAllUser);
		course.setStatus(statusEnum.toValue());
		course.setCourseKey(SystemUtility.createUUID());
		course.setCreatedTime(DateUtility.getCurDate());
		course.setCreator(curManagerId);
		
		if (!canMatchDutyRank) {
			course.setDutyRank("");
		}
		if (!canMatchTrade) {
			course.setTrade("");
		}
		
		if (canMatchDutyRank && StringUtils.isBlank(dutyRank)) {
			throw new Exception("请选择职务级别");
		}
		if (canMatchTrade && StringUtils.isBlank(trade)) {
			throw new Exception("请选择行业");
		}
		
		//校验课程
		this.validateCourse(course);
		
		this.daoFacade.getCourseDAO().insert(course);
		
		return course;
	}
	
	/**
	 * 修改课程
	 * @param id 课程编号
	 * @param courseTypeEnum 课程类型
	 * @param name 名称
	 * @param description 描述
	 * @param coverImageLink 封面链接
	 * @param classHour 学时
	 * @param credit 学分
	 * @param passCreditLimit 必修学分
	 * @param studyNum 学习次数
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @param canAllowAllUser 是否允许所有学员学习
	 * @param statusEnum 状态
	 * @param curManagerId 当前教师编号
	 * @return 课程
	 * @throws Exception
	 */
	public Course modifyCourse(Long id,	CourseTypeEnum courseTypeEnum, String name, String description, 
			String coverImageLink, Integer classHour, Integer credit,	Integer passCreditLimit, 
			Integer studyNum, Boolean canMatchDutyRank,	String dutyRank, Boolean canMatchTrade, 
			String trade, Boolean canAllowAllUser, CourseStatusEnum statusEnum, Long curManagerId) throws Exception {
	
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(id);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);
		
		//修改课程
		course.setName(name);
		course.setCourseType(courseTypeEnum.toValue());
		course.setDescription(description);
		course.setCoverImageLink(coverImageLink);
		course.setClassHour(classHour);
		course.setCredit(credit);
		course.setPassCreditLimit(passCreditLimit);
		course.setStudyNum(studyNum);
		course.setCanMatchDutyRank(canMatchDutyRank);
		course.setDutyRank(dutyRank);
		course.setCanMatchTrade(canMatchTrade);
		course.setTrade(trade);
		course.setCanAllowAllUser(canAllowAllUser);
		course.setStatus(statusEnum.toValue());
		
		if (!canMatchDutyRank) {
			course.setDutyRank("");
		}
		if (!canMatchTrade) {
			course.setTrade("");
		}
		
		if (canMatchDutyRank && StringUtils.isBlank(dutyRank)) {
			throw new Exception("请选择职务级别");
		}
		if (canMatchTrade && StringUtils.isBlank(trade)) {
			throw new Exception("请选择行业");
		}
		
		//校验课程
		this.validateCourse(course);
		
		this.daoFacade.getCourseDAO().update(course);
		
		return course;
	}
	
	/**
	 * 修改课程学员范围
	 * @param id 编号
	 * @param userJoinedData 修改课程学员范围
	 * @param curManagerId 当前教师编号
	 * @return
	 * @throws Exception
	 */
	public Course modifyCourseUserJoinedData(Long id, String userJoinedData, Long curManagerId) throws Exception {
	
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(id);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//判断此课程是否允许所有学员学习
		if (course.getCanAllowAllUser()) {
			throw new Exception("此课程已经允许所有学员学习了!");
		}
		
		//判断此课程已启用
		if (course.getStatus().equals(CourseStatusEnum.Opened.toValue())) {
			throw new Exception("此课程已启动，不可重新安排单位了!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);
	
		//析出参与的单位编号列表
		String joinedOrgans = "";
		if (!StringUtils.isBlank(userJoinedData)) {
			joinedOrgans = this.organService.extractJoinedOrgans(userJoinedData);
		}
		
		//修改课程学员范围
		course.setUserJoinedData(userJoinedData);
		course.setJoinedOrgans(joinedOrgans);
		
		this.daoFacade.getCourseDAO().update(course);
		
		return course;
	}
	
	/**
	 * 删除课程
	 * @param id 课程编号
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void removeCourse(Long id, Long curManagerId) throws Exception {
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(id);
		
		//判断此课程是否存在
		if (course == null) {
			return;
		}
		
		//判断此课程是否已上架
		if (course.getStatus().equals(CourseStatusEnum.Opened.toValue())) {
			throw new Exception("此课程已上架，不可删除!");
		}
		
		//判断此课程是否存在学员学习记录
		if (this.modelFactoryFacade.getCourseUserFactory().findCountByCourse(id) > 0) {
			throw new Exception("此课程已有学员学习，请在控制台先删除学员!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);
		
		//删除课程学员
		this.daoFacade.getCourseUserDAO().deleteListByCourse(id);
		
		//删除课程章节
		this.daoFacade.getCourseChapterDAO().deleteListByCourse(id);
		
		//删除课程
		this.daoFacade.getCourseDAO().delete(id);
	}
	
	/**
	 * 导入课程
	 * @param file Excel文件
	 * @param curManagerId 当前教师编号
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void importCourse(File file, Long curManagerId) throws Exception {
		
		//获取文件内容
		String data = FileUtility.readTXT(file, "UTF-8");
		
		//进行解压缩、解密处理
		String json = this.systemService.decryptData(data);
		
		//获取课程对象
		Map<String, Object> dataMap = JSONConvertor.json2Map(json);
		Course course = (Course)JSONConvertor.json2Bean(HexStrUtility.decode((String)dataMap.get("Course")), Course.class);
		List<CourseChapter> list = (List<CourseChapter>)JSONConvertor.json2List(HexStrUtility.decode((String)dataMap.get("CourseChapterList")), CourseChapter.class);
		
		//判断是否已存在此课程，通过CourseKey判断
		if (this.modelFactoryFacade.getCourseFactory().findByCourseKey(course.getCourseKey()) != null ) {
			throw new Exception("已存在相同标示的课程!");
		}
		
		//创建课程记录
		course.setId(null);
		course.setUserJoinedData("");
		course.setCreatedTime(DateUtility.getCurDate());
		course.setCreator(curManagerId);
		
		this.daoFacade.getCourseDAO().insert(course);
		
		//创建课程章节记录
		for (CourseChapter courseChapter : list) {
			
			courseChapter.setId(null);
			courseChapter.setCourseId(course.getId());
			
			this.daoFacade.getCourseChapterDAO().insert(courseChapter);
		}
	}
	
	/**
	 * 导出课程
	 * @param courseId 课程编号
	 * @return
	 * @throws Exception 
	 */
	public String exportCourse(Long id) throws Exception {
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(id);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//获取课程章节列表
		List<CourseChapter> list = this.modelFactoryFacade.getCourseChapterFactory().findListByCourse(id);
		
		//创建文件内容
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("Course", HexStrUtility.encode(JSONConvertor.bean2Json(course)));
		dataMap.put("CourseChapterList", HexStrUtility.encode(JSONConvertor.list2Json(list)));
		
		String json = JSONConvertor.bean2Json(dataMap);
		
		//加密压缩
		json = this.systemService.encryptData(json);
		
		return json;
	}
	
	/**
	 * 创建课程章节
	 * @param courseId 课程编号
	 * @param name 名称
	 * @param content 内容
	 * @param sortFlag 排序
	 * @param curManagerId 当前管理员编号
	 * @return 课程章节
	 * @throws Exception
	 */
	public CourseChapter createCourseChapter(Long courseId, String name, String content, Integer sortFlag,
			Long curManagerId) throws Exception {
	
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseId);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//判断此课程是否已上架
		if (course.getStatus().equals(CourseStatusEnum.Opened.toValue())) {
			throw new Exception("此课程已上架，不可修改课程内容!");
		}
		
		//创建课程章节
		CourseChapter courseChapter = new CourseChapter();
		
		courseChapter.setCourseId(courseId);
		courseChapter.setName(name);
		courseChapter.setContent(content);
		courseChapter.setSortFlag(sortFlag);
		
		this.daoFacade.getCourseChapterDAO().insert(courseChapter);
		
		return courseChapter;
	}
	
	/**
	 * 修改课程章节
	 * @param id 编号
	 * @param title 标题
	 * @param content 内容
	 * @param courseChapterGradeEnum 课程章节级别
	 * @param statusEnum 状态
	 * @param curManagerId 当前管理员编号
	 * @return
	 * @throws Exception
	 */
	public CourseChapter modifyCourseChapter(Long id, String name, String content, Integer sortFlag,
			Long curManagerId) throws Exception {
	
		//获取课程章节
		CourseChapter courseChapter = this.modelFactoryFacade.getCourseChapterFactory().findById(id);
		
		//判断是否存在
		if (courseChapter == null) {
			throw new Exception("此课程章节已不存在！");
		}
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseChapter.getCourseId());
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//判断此课程是否已上架
		if (course.getStatus().equals(CourseStatusEnum.Opened.toValue())) {
			throw new Exception("此课程已上架，不可修改课程内容!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);
		
		//修改课程章节
		courseChapter.setName(name);
		courseChapter.setContent(content);
		courseChapter.setSortFlag(sortFlag);
		
		this.daoFacade.getCourseChapterDAO().update(courseChapter);
		
		return courseChapter;
	}
	
	/**
	 * 删除课程章节
	 * @param id 编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception
	 */
	public void removeCourseChapter(Long id, Long curManagerId) throws Exception {
		
		//获取课程章节
		CourseChapter courseChapter = this.modelFactoryFacade.getCourseChapterFactory().findById(id);
		
		//判断是否存在
		if (courseChapter == null) {
			return;
		}
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseChapter.getCourseId());
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//判断此课程是否已上架
		if (course.getStatus().equals(CourseStatusEnum.Opened.toValue())) {
			throw new Exception("此课程已上架，不可修改课程内容!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);
		
		//删除记录
		this.daoFacade.getCourseChapterDAO().delete(id);
	}
	
	/**
	 * 上传课程图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	public String uploadCourseImage(File file) throws Exception {
		
		//获取OSS存储空间配置
        String accessKeyId = this.systemService.getOSSAccessKeyId();
        String accessKeySecret = this.systemService.getOSSAccessKeySecret();
        String bucketName = this.systemService.getOSSBucketName();
        String bucketUrl = this.systemService.getOSSBucketUrl();
        
        //生成文件名
        String key = "course/" + SystemUtility.createUUID() + ".jpg";
        
        //初始化客户端
        OSSClient client = new OSSClient(accessKeyId, accessKeySecret);

        //配置元数据
	    ObjectMetadata meta = new ObjectMetadata();
	    meta.setContentType("image/jpeg");
	    meta.setContentLength(file.length());

	    //上传文件
	    InputStream content = new FileInputStream(file);
	    client.putObject(bucketName, key, content, meta);
	    
	    //返回图片调用网址
	    return bucketUrl + key;
	}
	
	/**
	 * 上传课程章节图片
	 * @param file 文件
	 * @return 文件网址
	 * @throws Exception
	 */
	public String uploadCourseChapterImage(File file) throws Exception {
		
		//获取OSS存储空间配置
        String accessKeyId = this.systemService.getOSSAccessKeyId();
        String accessKeySecret = this.systemService.getOSSAccessKeySecret();
        String bucketName = this.systemService.getOSSBucketName();
        String bucketUrl = this.systemService.getOSSBucketUrl();
        
        //生成文件名
        String key = "courseChapter/" + SystemUtility.createUUID() + ".jpg";
        
        //初始化客户端
        OSSClient client = new OSSClient(accessKeyId, accessKeySecret);

        //配置元数据
	    ObjectMetadata meta = new ObjectMetadata();
	    meta.setContentType("image/jpeg");
	    meta.setContentLength(file.length());

	    //上传文件
	    InputStream content = new FileInputStream(file);
	    client.putObject(bucketName, key, content, meta);
	    
	    //返回图片调用网址
	    return bucketUrl + key;
	}
	
	/**
	 * 获取课程分类树列表
	 * @return
	 * @throws Exception
	 */
//	public List<CourseCategory> getCourseCategoryListByTree() throws Exception {
//	
//		//从缓存中获取数据
////		List<CourseCategory> list = loadCourseCategoryListByTreeCache();
//		
//		//若数据存在则返回
//		if (list != null && list.size() > 0) {
//			return list;
//		}
//		
//		//重新生成数据并存储到缓冲中
//		list = genarateCourseCategoryListByTree();
////		this.saveCourseCategoryListByTreeCache(list);
//		
//		return list;
//	}
	
	/**
	 * 从缓存中获取分类树数据
	 * @return
	 * @throws Exception
	 */
//	private List<CourseCategory> loadCourseCategoryListByTreeCache() throws Exception {
//		
//		//return this.memcachedClient.get(CourseCategoryListByTreeMemKey, this.systemService.getMemGetTimeOut());
//	}
	
	/**
	 * 保存分类树数据到缓存中
	 * @return
	 * @throws Exception
	 */
//	private void saveCourseCategoryListByTreeCache(List<CourseCategory> list) throws Exception {
//		
//		this.memcachedClient.set(CourseCategoryListByTreeMemKey, 3600*24, list);
//	}
	
	/**
	 * 清除缓存中的分类树数据
	 * @return
	 * @throws Exception
	 */
//	private void clearCourseCategoryListByTreeCache() throws Exception {
//		
//		this.memcachedClient.delete(CourseCategoryListByTreeMemKey);
//	}

	/**
	 * 生成分类树数据
	 * @return
	 */
	private List<CourseCategory> genarateCourseCategoryListByTree() {
	
		List<CourseCategory> list = new ArrayList<CourseCategory>();
		
		CourseCategory top = this.modelFactoryFacade.getCourseCategoryFactory().findTop();
		doGenarateCourseCategoryListByTree(top, list);
		
		return list;
	}
	
	/**
	 * 深度递归生成子分类树数据
	 * @param courseCategory 分类节点
	 * @param list
	 */
	private void doGenarateCourseCategoryListByTree(CourseCategory courseCategory, List<CourseCategory> list) {
		
		list.add(courseCategory);
		
		List<CourseCategory> childList = this.modelFactoryFacade.getCourseCategoryFactory().findListByParentId(courseCategory.getId());
		
		for (CourseCategory child : childList) {
			doGenarateCourseCategoryListByTree(child, list);
		}
	}

	@Override
	public List<CourseCategory> getCourseCategoryListByTree() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
