package com.study.service.impl;

import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.dao.DAOFacade;
import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.enums.UserStatusEnum;
import com.study.model.Course;
import com.study.model.CourseUser;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.CourseUserService;
import com.study.service.ManagerService;
import com.study.service.OrganService;
import com.study.service.SystemService;
import com.study.service.UserService;
import com.study.utility.DateUtility;

/**
 * 课程学员业务接口实现类
 */
@Service
public class CourseUserServiceImpl implements CourseUserService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(CourseUserServiceImpl.class);
	
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
	 * 创建课程学员
	 * @param course 课程
	 * @param user 学员
	 * @return 课程学员
	 */
	public CourseUser createCourseUser(Course course, User user) {
	
		CourseUser courseUser = new CourseUser();
		
		courseUser.setCourseId(course.getId());
		courseUser.setOrganId(user.getOrganId());
		courseUser.setUserId(user.getId());
		courseUser.setUserKey(user.getUserKey());
		courseUser.setActualName(user.getActualName());
		courseUser.setActualOrgan(user.getActualOrgan());
		courseUser.setJoinedTime(DateUtility.getCurDate());
		courseUser.setBeginTime(null);
		courseUser.setEndTime(null);
		courseUser.setFinishedTime(null);
		courseUser.setTotalTime(0);
		courseUser.setCredit(0);
		courseUser.setStudyNum(0);
		courseUser.setStudyType(course.getCourseType());
		courseUser.setStatus(CourseUserStatusEnum.None.toValue());
		
		this.daoFacade.getCourseUserDAO().insert(courseUser);
		
		return courseUser;
	}
	
	/**
	 * 校验学习
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	public void confirmStudy(Long courseId, Long userId) throws Exception {
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseId);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断此学是否存在
		if (user == null) {
			throw new Exception("此学员已不存在!");
		}

		//判断此课程是否启用
		if (course.getStatus().equals(CourseStatusEnum.Closed.toValue())) {
			throw new Exception("此课程已下架!");
		}
		
		//判断此学员是否可用
		if (user.getStatus().equals(UserStatusEnum.Closed.toValue())) {
			throw new Exception("您的学员帐号已停用!");
		}
		
		//获取课程学员记录
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(courseId, userId);
		
		//若未学习，则判断此学员可否学习此课程
		if (courseUser == null) {
			
			//判断此学员是否在学员参与范围
			if (!this.userService.verifyUserJoinedData(userId, course.getUserJoinedData(), course.getCanMatchDutyRank(), course.getDutyRank(), course.getCanMatchTrade(), course.getTrade())) {
				throw new Exception("您不用参与此课程的学习!");
			}
		}
	}
	
	/**
	 * 学习课程
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void beginStudy(Long courseId, Long userId) throws Exception {
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseId);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断此学是否存在
		if (user == null) {
			throw new Exception("此学员已不存在!");
		}

		//判断此课程是否启用
		if (course.getStatus().equals(CourseStatusEnum.Closed.toValue())) {
			throw new Exception("此课程已下架!");
		}
		
		//判断此学员是否可用
		if (user.getStatus().equals(UserStatusEnum.Closed.toValue())) {
			throw new Exception("您的学员帐号已停用!");
		}
		
		//获取课程学员记录
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(courseId, userId);
		
		//若未学习，则判断此学员可否学习此课程
		if (courseUser == null) {
			
			//判断此学员是否在学员参与范围
			if (!this.userService.verifyUserJoinedData(userId, course.getUserJoinedData(), course.getCanMatchDutyRank(), course.getDutyRank(), course.getCanMatchTrade(), course.getTrade())) {
				throw new Exception("您不用参与此课程的学习!");
			}
			
			//创建课程学员记录
			courseUser = this.createCourseUser(course, user);
		}
		
		//记录学习情况
		courseUser.setBeginTime(DateUtility.getCurDate());
		
		//判断是否未学，可转为在学
		if (courseUser.getStatus().equals(CourseUserStatusEnum.None.toValue())) {
			courseUser.setStatus(CourseUserStatusEnum.Doing.toValue());
		}
		
		this.daoFacade.getCourseUserDAO().update(courseUser);
	}
	
	/**
	 * 结束本次学习
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void endStudy(Long courseId, Long userId) throws Exception {
	
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseId);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断此学是否存在
		if (user == null) {
			throw new Exception("此学员已不存在!");
		}

		//判断此课程是否启用
		if (course.getStatus().equals(CourseStatusEnum.Closed.toValue())) {
			throw new Exception("此课程已下架!");
		}
		
		//判断此学员是否可用
		if (user.getStatus().equals(UserStatusEnum.Closed.toValue())) {
			throw new Exception("您的学员帐号已停用!");
		}
		
		//获取课程学员记录
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(courseId, userId);
		
		//判断是否存在学习记录
		if (courseUser == null) {
			throw new Exception("您还未开始学习呢!");
		}
		
		//判断是否开始过学习，防止作弊
		if (courseUser.getBeginTime() == null) {
			return;
		}
		
		//记录学习情况
		courseUser.setEndTime(DateUtility.getCurDate());
		
		//获取本次学习时间， 不满1分钟则为0分钟
		Integer minutes = DateUtility.getSeconds(courseUser.getBeginTime(), courseUser.getEndTime()).intValue()/60; 
		courseUser.setTotalTime(courseUser.getTotalTime() + minutes);
		
		 //对学习情况进行判断然后给出相应的学分，包括学习次数，学习时长
		int readyStudyNum = courseUser.getStudyNum();//该课程已学次数
		int readyStudyTime = courseUser.getTotalTime();//该课程已学时间(分钟)
		
		//按照课程规定应该的学习次数和学习时间
		int mustStudyNum = course.getStudyNum();//该课程规定的必修次数
		int mustStudyTime = course.getClassHour();//该课程规定的学时
		
		//按60%获取最低学习次数和学习时间
		int lowStudyNum = mustStudyNum * 60 / 100;
		int lowStudyTime = mustStudyTime * 60 / 100;
		
		//当次数满足0.6，而时间不满足0,6，则将次数设置为0.6
		if (readyStudyNum >= lowStudyNum && readyStudyTime < lowStudyTime) { 
			readyStudyNum = lowStudyNum;			
		}
		
		//当时间满足0.6，而次数不满足0,6，则将时间设置为0.6
		if (readyStudyTime >= lowStudyTime && readyStudyNum < lowStudyNum) { 
			readyStudyTime = lowStudyTime;			
		}
	
		//double类型格式化
		DecimalFormat df = new DecimalFormat("0.00");
				
		//学习次数完成率
		double studyNumIn = 0;
		if(readyStudyNum > 0){
			if(readyStudyNum >= mustStudyNum)
				studyNumIn = 1;
			else
				studyNumIn = Double.valueOf(df.format((double)readyStudyNum/mustStudyNum));
		}
			
		//学习时间完成率
		double studyTimeIn = 0;
		if(readyStudyTime > 0){
			if(readyStudyTime >= mustStudyTime)
				studyTimeIn = 1;
			else
				studyTimeIn = Double.valueOf(df.format((double)readyStudyTime/mustStudyTime));
		}
		
		//这两种条件完成率的平均值
		double avgStudyIn = (studyNumIn+studyTimeIn)/2;
		
		//插入学分的时候按照学习条件完成率进行给分
		int creditH = course.getCredit();
		double finalCreditH = creditH*avgStudyIn;
		courseUser.setCredit((int)finalCreditH);
		
		//清空本次开始时间
		courseUser.setBeginTime(null);
		courseUser.setStudyNum(courseUser.getStudyNum() + 1);
		
		this.daoFacade.getCourseUserDAO().update(courseUser);
		
		//同时更新学员总学分
		this.userService.updateUserCredit(courseUser.getUserId());
	}
	
	/**
	 * 完成课程
	 * @param courseId 课程编号
	 * @param userId 学员编号
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void finishStudy(Long courseId, Long userId) throws Exception {
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseId);
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//判断此学员是否已经开始学习了
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findByCourseId_UserId(courseId, userId);
		
		//判断是否存在学习记录
		if (courseUser == null) {
			throw new Exception("您还未学习呢!");
		}
		
		//判断是否已学完了
		if (courseUser.getStatus().equals(CourseUserStatusEnum.Done.toValue())) {
			throw new Exception("您早已学完此课程了!");
		}
		
		//判断学分是否已达标
		if(courseUser.getCredit().compareTo(course.getPassCreditLimit()) < 0){
			throw new Exception("学分未达标，请继续学习以便完成课程!");
		}
		
		//记录学习情况
		courseUser.setFinishedTime(DateUtility.getCurDate());
		courseUser.setStatus(CourseUserStatusEnum.Done.toValue());
		
		this.daoFacade.getCourseUserDAO().update(courseUser);
	}
	
	/**
	 * 在教师平台删除课程学员
	 * @param courseUserId 课程学员编号
	 * @param curManagerId 当前管理员编号
	 * @throws Exception 
	 */
	public void removeCourseUserByManager(Long courseUserId, Long curManagerId) throws Exception {
		
		//获取课程学员记录
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findById(courseUserId);
		
		//判断是否存在
		if (courseUser == null) {
			return;
		}
		
		//获取课程
		Course course = this.modelFactoryFacade.getCourseFactory().findById(courseUser.getCourseId());
		
		//判断此课程是否存在
		if (course == null) {
			throw new Exception("此课程已不存在!");
		}
		
		//校验记录编辑权限
		this.managerService.validateManagerDataAccess(course.getCreator(), curManagerId);

		
		//删除记录
		this.daoFacade.getCourseUserDAO().delete(courseUserId);
	}

	/**
	 * 单位管理员删除课程学员
	 * @param courseUserId 课程学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void removeCourseUserByOrgan(Long courseUserId, Long curOrganId) throws Exception {
		
		//获取课程学员记录
		CourseUser courseUser = this.modelFactoryFacade.getCourseUserFactory().findById(courseUserId);
		
		//判断是否存在
		if (courseUser == null) {
			return;
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(courseUser.getUserId());
		
		//判断是否自己单位的
		if (user != null) {
			if (!user.getOrganId().equals(curOrganId)) {
				throw new Exception("您无权删除此课程学员！");
			}
		}
		
		//删除记录
		this.daoFacade.getCourseUserDAO().delete(courseUserId);
	}
	
}
