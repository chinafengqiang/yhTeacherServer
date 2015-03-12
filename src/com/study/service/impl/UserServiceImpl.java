package com.study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.dao.DAOFacade;
import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.enums.CourseUserStatusEnum;
import com.study.enums.OrganStatusEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.enums.UserStatusEnum;
import com.study.model.Organ;
import com.study.model.User;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.UserJoinedItem;
import com.study.model.us.USUserSummary;
import com.study.service.SystemService;
import com.study.service.UserService;
import com.study.utility.JSONConvertor;
import com.study.utility.SystemUtility;

/**
 * 学员业务接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(UserServiceImpl.class);
	
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
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;

	/**
	 * 创建学员
	 * @param name 帐号
	 * @param actualName 项目
	 * @param actualOrgan 单位
	 * @param dutyRank 职务级别
	 * @param trade 行业
	 * @param mobile 手机号
	 * @param password 密码
	 * @param statusEnum 状态
	 * @param curOrganId 当前单位编号
	 * @return 学员
	 * @throws Exception
	 */
	public User createUser(String name, String actualName, String actualOrgan, String dutyRank, 
			String trade, String mobile, String password, UserStatusEnum statusEnum, Long curOrganId) throws Exception {
		
		//获取单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断单位是否已停用
		if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("抱歉，您单位帐号已停用了！");
		}
	
		//判断人数是否超标
		if (this.modelFactoryFacade.getUserFactory().findCountByOrgan(curOrganId).compareTo(organ.getUserNumberLimit()) >= 0) {
			throw new Exception("您单位学员人数已达到上限！");
		}
		
		//判断是否帐号重复
		if (this.modelFactoryFacade.getUserFactory().findByName(name.trim()) != null) {
			throw new Exception(actualName + "的学员帐号已被占用了！");
		}
		
		//判断姓名是否重复
		if (this.modelFactoryFacade.getUserFactory().findByOrganId_ActualName(curOrganId, actualName.trim()) != null) {
			throw new Exception(actualName + "的姓名已重复！");
		}
		
		//判断单位名称是否正确
		if (!actualOrgan.trim().equals(organ.getActualName().trim())) {
			throw new Exception(actualName + "的所属单位不正确！");
		}
		
		//判断手机是否填写
		if (StringUtils.isBlank(mobile)) {
			throw new Exception(actualName + "的手机信息需要填写！");
		} else {
			if (mobile.length() != 11) {
				throw new Exception(actualName + "的手机号码长度不对！");
			}
		}
		
		//校验系统参数约定
		this.systemService.verifySysParamByStringList(dutyRank.trim(), SysParamTypeEnum.DutyRank);
		this.systemService.verifySysParamByStringList(trade.trim(), SysParamTypeEnum.Trade);

		//创建记录
		User user = new User();
		
		user.setOrganId(curOrganId);
		user.setUserKey(SystemUtility.createUUID());
		user.setName(name.trim());
		user.setActualName(actualName.trim());
		user.setActualOrgan(actualOrgan);
		user.setDutyRank(dutyRank);
		user.setTrade(trade);
		user.setTotalCredit(0);
		user.setExamCredit(0);
		user.setMobile(mobile);
		user.setCourseCredit(0);
		user.setPassword(this.systemService.encryptPassword(password));
		user.setStatus(statusEnum.toValue());
		
		this.daoFacade.getUserDAO().insert(user);
		
		return user;
	}
	
	/**
	 * 修改学员
	 * @param id 编号
	 * @param actualName 项目
	 * @param dutyRank 职务级别
	 * @param trade 行业
	 * @param mobile 手机号
	 * @param password 密码
	 * @param statusEnum 状态
	 * @param curOrganId 当前单位编号
	 * @return 学员
	 * @throws Exception
	 */
	public User modifyUser(Long id, String actualName, String dutyRank, 
			String trade, String mobile, String password, UserStatusEnum statusEnum, Long curOrganId) throws Exception {
	
		//获取单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断单位是否已停用
		if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("抱歉，您单位帐号已停用了！");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(id);
		
		//判断学员记录是否存在
		if (user == null) {
			throw new Exception("此学员帐号已不存在了！");
		}
		
		//判断姓名是否重复
		User temp = this.modelFactoryFacade.getUserFactory().findByOrganId_ActualName(curOrganId, actualName.trim());
		if (temp != null && !temp.getId().equals(id)) {
			throw new Exception("姓名已重复！");
		}
		
		//校验系统参数约定
		this.systemService.verifySysParamByStringList(dutyRank.trim(), SysParamTypeEnum.DutyRank);
		this.systemService.verifySysParamByStringList(trade.trim(), SysParamTypeEnum.Trade);
		
		//修改记录
		user.setActualName(actualName);
		user.setDutyRank(dutyRank);
		user.setTrade(trade);
		user.setMobile(mobile);
		user.setPassword(this.systemService.encryptPassword(password));
		user.setStatus(statusEnum.toValue());
		
		this.daoFacade.getUserDAO().update(user);
		
		return user;
	}
	
	/**
	 * 删除学员
	 * @param id 学员编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void removeUser(Long id, Long curOrganId) throws Exception {
	
		//获取单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断单位是否已停用
		if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("抱歉，您单位帐号已停用了！");
		}
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(id);
		
		//判断学员记录是否存在
		if (user == null) {
			return;
		}
		
		//判断是否存在考试记录
		if (this.modelFactoryFacade.getExamUserFactory().findListByUserId(id).size() > 0) {
			throw new Exception("此学员存在考试记录，删除记录后才能删除学员！");
		}
		
		//判断是否存在课程学员记录
		if (this.modelFactoryFacade.getCourseUserFactory().findListByUserId(id).size() > 0) {
			throw new Exception("此学员存在课程学习记录，删除记录后才能删除学员！");
		}		
		
		//删除考试记录
		this.daoFacade.getExamUserDAO().deleteListByUser(id);
		
		//删除课程记录
		this.daoFacade.getCourseUserDAO().deleteListByUser(id);
		
		//删除记录
		this.daoFacade.getUserDAO().delete(id);
	}
	
	/**
	 * 修改学员密码
	 * @param id 学员编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	public void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception {
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(id);
		
		//判断是否存在
		if (user == null) {
			throw new Exception("您的学员记录已不存在！");
		}
		
		//判断旧密码是否正确
		if (!this.systemService.decryptPassword(user.getPassword()).toLowerCase().equals(oldPassword.toLowerCase())) {
			throw new Exception("旧密码输入有误！");
		}
		
		//修改记录
		user.setPassword(this.systemService.encryptPassword(newPassword));

		this.daoFacade.getUserDAO().update(user);
	}
	
	/**
	 * 学员登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 学员记录
	 * @throws Exception
	 */
	public User login(String name, String password) throws Exception {
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findByName(name);
		
		//判断是否存在
		if (user == null) {
			throw new Exception("您的学员记录已不存在！");
		}
		
		//获取当前单位帐号
		Organ curOrgan = this.modelFactoryFacade.getOrganFactory().findById(user.getOrganId());
		
		//判断当前单位是否存在
		if (curOrgan == null) {
			throw new Exception("您单位帐号已不存在！");
		}
		
		//判断当前单位是否已停用
		if (curOrgan.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("您单位帐号已暂停停用！");
		}
		
		//判断密码是否正确
		if (!this.systemService.decryptPassword(user.getPassword()).toLowerCase().equals(password.toLowerCase())) {
			throw new Exception("密码输入有误！");
		}
		
		return user;
	}
	
	/**
	 * 校验此学员是否在学员参与范围中
	 * @param userId 学员编号
	 * @param userJoinedData 参与范围
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return
	 */
	public Boolean verifyUserJoinedData(Long userId, String userJoinedData, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade) {
		
		//判断是否存在学员参与范围，若存在，则组装单位编号列表
		String organIdList = "";
		if (!StringUtils.isBlank(userJoinedData)) {

			List<UserJoinedItem> userJoinedItemList = JSONConvertor.json2List(userJoinedData, UserJoinedItem.class);

			for (UserJoinedItem userJoinedItem :userJoinedItemList) {
				if (!organIdList.equals("")) {
					organIdList = organIdList + ",";
				}
				organIdList = organIdList + userJoinedItem.getId();
			}
			
			//递归获取本单位及下级单位的编号列表
			organIdList = this.modelFactoryFacade.getOrganFactory().findIdListBySelf_Child_Opened(organIdList);
		}
		
		//按条件抽取学员列表
		return this.modelFactoryFacade.getUserFactory().findByVerify(userId, organIdList, canMatchDutyRank, dutyRank, canMatchTrade, trade) != null;
	}
	
	/**
	 * 按职务级别、行业、参与范围获取已启用的学员列表
	 * @param canAllowAllUser 是否允许所有学员参加
	 * @param userJoinedData 参与范围
	 * @param canMatchDutyRank 是否匹配职务级别
	 * @param dutyRank 职务级别
	 * @param canMatchTrade 是否匹配行业
	 * @param trade 行业
	 * @return 学员列表
	 */
	public List<User> extractUserList(Boolean canAllowAllUser, String userJoinedData, Boolean canMatchDutyRank, String dutyRank, Boolean canMatchTrade, String trade, Boolean canCourseStudyLimit) {

		String organIdList = "";

		//判断是否存在学员参与范围，若存在，则组装单位编号列表
		if (canAllowAllUser) {
			
			organIdList = null;
		} else {
			if (!StringUtils.isBlank(userJoinedData)) {

				List<UserJoinedItem> userJoinedItemList = JSONConvertor.json2List(userJoinedData, UserJoinedItem.class);

				for (UserJoinedItem userJoinedItem :userJoinedItemList) {
					if (!organIdList.equals("")) {
						organIdList = organIdList + ",";
					}
					organIdList = organIdList + userJoinedItem.getId();
				}
				
				//递归获取本单位及下级单位的编号列表
				organIdList = this.modelFactoryFacade.getOrganFactory().findIdListBySelf_Child_Opened(organIdList);
			} else {
				organIdList = "99999999999";
			}
		}
		
		//判断是否需要修完必修课，若是，则获取要学的已启动的必修课的数量
		Integer requiredCourseNumber = null;
		if (canCourseStudyLimit) {
			requiredCourseNumber = this.modelFactoryFacade.getCourseFactory().findCountByCourseType_Status(CourseTypeEnum.Required, CourseStatusEnum.Opened);
		}
		
		//按条件抽取学员列表
		return this.modelFactoryFacade.getUserFactory().findListByExtract(organIdList, canMatchDutyRank, dutyRank, canMatchTrade, trade, requiredCourseNumber);
	}
	
	/**
	 * 导入学员集
	 * @param curOrganId 当前单位编号
	 * @param file Excel文件
	 * @throws Exception 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void importUserList(Long curOrganId, File file) throws Exception {
			
		//获取单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断单位是否已停用
		if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("抱歉，您单位帐号已停用了！");
		}
		
		//从Excel中解析出学员集
		List<User> userList = UserExcelTools.getUserList(file);
		
		//导入学员
		for (int i=0; i<userList.size(); i++) {
			
			//获取行号
			Integer row = UserExcelTools.dataRowFirst + i + 1;
			
			//当前学员
			User user = userList.get(i);
			
			//创建学员
			try {
				this.createUser(
						user.getName(), 
						user.getActualName(), 
						user.getActualOrgan(), 
						user.getDutyRank(), 
						user.getTrade(), 
						user.getMobile(),
						this.systemService.getSysParamValueByString(SysParamTypeEnum.DefaultUserPassword),
						UserStatusEnum.Opened,
						curOrganId);
			} catch (Exception ex) {
				throw new Exception("学员校验错误，第" + row + "行出错：" + ex.getMessage());
			}
		}
	}
	
	/**
	 * 导出当前单位的所有学员到Excel
	 * @param curOganId 当前单位编号
	 * @return
	 * @throws IOException
	 */
	public InputStream exportUserList(Long curOganId) throws IOException {
		
		//获取学员分类的所有学员
		List<User> userList = this.modelFactoryFacade.getUserFactory().findListByOrgan(curOganId);
		
		//获取职务级别
		String dutyRank = this.systemService.getSysParamValueByString(SysParamTypeEnum.DutyRank);
		
		//获取行业
		String trade = this.systemService.getSysParamValueByString(SysParamTypeEnum.Trade);
		
		//将学员集转换成工作簿
		HSSFWorkbook workbook = UserExcelTools.getWorkbook(userList, dutyRank, trade);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 导出学员模板
	 * @return
	 * @throws IOException
	 */
	public InputStream exportUserExcelTemplet() throws IOException {
		
		//获取职务级别
		String dutyRank = this.systemService.getSysParamValueByString(SysParamTypeEnum.DutyRank);
		
		//获取行业
		String trade = this.systemService.getSysParamValueByString(SysParamTypeEnum.Trade);
		
		//获取模板中的所有学员
		List<User> userList = new ArrayList<User>();
		
		//装载模拟学员
		User user = new User();
		
		user.setActualName("张小凡");
		user.setDutyRank("处级干部");
		user.setTrade("检察院");
		user.setActualOrgan("安化县检察院");
		user.setName("ahxjcy-0001");
		user.setMobile("13900001111");
		
		userList.add(user);
		
		//将学员集转换成工作簿
		HSSFWorkbook workbook = UserExcelTools.getWorkbook(userList, dutyRank, trade);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 获取学员平台的学员摘要
	 * @param userId 学员编号
	 * @return 学员摘要
	 * @throws Exception
	 */
	public USUserSummary getUSUserSummary(Long userId) throws Exception {
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断是否存在
		if (user == null) {
			throw new Exception("您的学员记录已不存在！");
		}
		
		USUserSummary userSummary = new USUserSummary();
		
		userSummary.setName(user.getName());
		userSummary.setActualName(user.getActualName());
		userSummary.setActualOrgan(user.getActualOrgan());
		userSummary.setDutyRank(user.getDutyRank());
		userSummary.setTrade(user.getTrade());
		userSummary.setCourseCredit(user.getCourseCredit());
		
		Integer courseDoingNumber = this.modelFactoryFacade.getCourseUserFactory().findCountByUser_Status(userId, CourseUserStatusEnum.Doing);
		Integer courseDoneNumber = this.modelFactoryFacade.getCourseUserFactory().findCountByUser_Status(userId, CourseUserStatusEnum.Done);
		
		userSummary.setCourseDoingNumber(courseDoingNumber);
		userSummary.setCourseDoneNumber(courseDoneNumber);
		
		return userSummary;
	}
	
	/**
	 * 更新学员学分
	 * @param userId 学员编号
	 */
	public void updateUserCredit(Long userId) {
		
		//获取学员记录
		User user = this.modelFactoryFacade.getUserFactory().findById(userId);
		
		//判断是否存在
		if (user == null) {
			return;
		}
		
		//更新学员学分
		Integer courseCredit = this.modelFactoryFacade.getCourseUserFactory().findSumCreditByUserId(userId);
		user.setCourseCredit(courseCredit);
		user.setTotalCredit(courseCredit);
		
		this.daoFacade.getUserDAO().update(user);
	}
}
