package com.study.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.study.dao.DAOFacade;
import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.model.Organ;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.ActivateCode;
import com.study.model.part.UserJoinedItem;
import com.study.service.OrganService;
import com.study.service.SystemService;
import com.study.utility.DESUtility;
import com.study.utility.DateUtility;
import com.study.utility.JSONConvertor;

/**
 * 单位业务接口实现类
 */
@Service
public class OrganServiceImpl implements OrganService {

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(OrganServiceImpl.class);
	
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
	 * 系统服务接口 
	 */
	@Resource
	private SystemService systemService;

	/**
	 * 创建单位记录
	 * @param name 帐号
 	 * @param actualName 名称
	 * @param shortName 简称
	 * @param organTypeEnum 单位类型
	 * @param linkman 联系人
	 * @param tel 电话
	 * @param mobile 手机
	 * @param area 地区
	 * @param address 地址
	 * @param curOrganId 当前单位
	 * @return 单位记录
	 * @throws Exception
	 */
	public Organ createOrgan(String name, String actualName, String shortName, 
			OrganTypeEnum organTypeEnum, String linkman, String tel, String mobile, String area, String address,
			Long curOrganId) throws Exception {
	
		//获取当前单位帐号
		Organ curOrgan = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断当前单位是否存在
		if (curOrgan == null) {
			throw new Exception("当前单位帐号已不存在！");
		}
		
		//判断当前单位是否是法宣单位
		if (curOrgan.getOrganType().equals(OrganTypeEnum.Client.toValue())) {
			throw new Exception("只有法宣单位可创建新单位记录！");
		}
		
		//判断单位帐号是否重复
		if (this.modelFactoryFacade.getOrganFactory().findByName(name) != null) {
			throw new Exception("单位帐号已被占用！");
		}

		//判断单位名称是否重复
		if (this.modelFactoryFacade.getOrganFactory().findByActualName(actualName) != null) {
			throw new Exception("单位名称已被占用！");
		}

		//获取默认单位密码
		String password = this.systemService.getSysParamValueByString(SysParamTypeEnum.DefaultOrganPassword);
		
		//创建记录
		Organ organ = new Organ();

		organ.setName(name);
		organ.setActualName(actualName);
		organ.setShortName(shortName);
		organ.setOrganType(organTypeEnum.toValue());
		organ.setLinkman(linkman);
		organ.setTel(tel);
		organ.setMobile(mobile);
		organ.setArea(area);
		organ.setAddress(address);
		organ.setStatus(OrganStatusEnum.Closed.toValue());
		organ.setParentId(curOrganId);
		organ.setPassword(this.systemService.encryptPassword(password));
		
		organ.setUserNumberLimit(0);
		organ.setServiceTimeLimit(DateUtility.getCurDate());
		organ.setLastActivatedTime(DateUtility.getCurDate());
		
		this.daoFacade.getOrganDAO().insert(organ);

		return organ;
	}
	
	/**
	 * 修改单位信息
	 * @param id 单位编号
	 * @param shortName 简称
	 * @param linkman 联系人
	 * @param tel 电话
	 * @param mobile 手机
	 * @param area 地区
	 * @param address 地址
	 * @param curOrganId 当前单位
	 * @return 单位记录
	 * @throws Exception
	 */
	public Organ modifyOrgan(Long id, String shortName, 
			String linkman, String tel, String mobile, String area, String address,
			Long curOrganId) throws Exception {
		
		//获取当前单位帐号
		Organ curOrgan = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断当前单位是否存在
		if (curOrgan == null) {
			throw new Exception("当前单位帐号已不存在！");
		}
		
		//判断是否更改单位自己的账户信息
		if (id.equals(curOrganId)) {
			throw new Exception("不可修改自己的帐号信息！");
		}
		
		//获取单位记录
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(id);
		
		//判断是否存在
		if (organ == null) {
			throw new Exception("此单位的记录已不存在！");
		}
		
		//修改记录
		organ.setShortName(shortName);
		organ.setLinkman(linkman);
		organ.setTel(tel);
		organ.setMobile(mobile);
		organ.setArea(area);
		organ.setAddress(address);
		
		this.daoFacade.getOrganDAO().update(organ);
		
		return organ;
	}
	
	/**
	 * 删除单位
	 * @param id 要删除的单位编号
	 * @param curOrganId 当前单位编号
	 * @throws Exception
	 */
	public void removeOrgan(Long id, Long curOrganId) throws Exception {
		
		//判断是否更改单位自己的账户信息
		if (id.equals(curOrganId)) {
			throw new Exception("不可删除自己的帐号信息！");
		} 
		
		//获取当前单位帐号
		Organ curOrgan = this.modelFactoryFacade.getOrganFactory().findById(curOrganId);
		
		//判断当前单位是否存在
		if (curOrgan == null) {
			throw new Exception("当前单位帐号已不存在！");
		}
		
		//获取单位帐号
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(id);
		
		//判断单位是否存在
		if (organ == null) {
			return;
		}
		
		//判断是否删除已激活的单位
		if (organ.getStatus().equals(OrganStatusEnum.Opened.toValue())) {
			throw new Exception("不可删除已激活的单位帐号信息！");
		}
		
		//判断是否有子单位
		if (this.modelFactoryFacade.getOrganFactory().findCountByParant(id) > 0) {
			throw new Exception("此单位存在下级单位或对口单位，不可删除！");
		}
		
		//判断是否有学员记录存在
		if (this.modelFactoryFacade.getUserFactory().findCountByOrgan(id) > 0) {
			throw new Exception("此单位存在学员记录，不可删除！");
		}
		
		//删除记录
		this.daoFacade.getOrganDAO().delete(id);
	}
	
	/**
	 * 修改单位密码
	 * @param id 单位编号
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws Exception
	 */
	public void modifyPassword(Long id, String oldPassword, String newPassword) throws Exception {
		
		//获取单位记录
		Organ organ = this.modelFactoryFacade.getOrganFactory().findById(id);
		
		//判断是否存在
		if (organ == null) {
			throw new Exception("您的单位记录已不存在！");
		}
		
		//判断旧密码是否正确
		if (!this.systemService.decryptPassword(organ.getPassword()).toLowerCase().equals(oldPassword.toLowerCase())) {
			throw new Exception("旧密码输入有误！");
		}
		
		//修改记录
		organ.setPassword(this.systemService.encryptPassword(newPassword));

		this.daoFacade.getOrganDAO().update(organ);
	}
	
	/**
	 * 单位登录
	 * @param name 帐号
	 * @param password 密码
	 * @return 单位记录
	 * @throws Exception
	 */
	public Organ login(String name, String password) throws Exception {
		
		//获取单位记录
		Organ organ = this.modelFactoryFacade.getOrganFactory().findByName(name);
		
		//判断是否存在
		if (organ == null) {
			throw new Exception("您的单位记录已不存在！");
		}
		
		//判断密码是否正确
		if (!this.systemService.decryptPassword(organ.getPassword()).toLowerCase().equals(password.toLowerCase())) {
			throw new Exception("密码输入有误！");
		}
		
		//判断当前单位是否已停用
		if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
			throw new Exception("您单位帐号已暂停停用！");
		}
		
		return organ;
	}
	
	/**
	 * 析出参加的单位编号列表
	 * @param userJoinedData 学员参与范围
	 * @return 单位编号列表
	 */
	public String extractJoinedOrgans(String userJoinedData) {
		
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
		
		return organIdList;
	}
	
	/**
	 * 自动校验单位服务期限
	 */
	public void autoValidateOrganServiceLimit() {
	
		//获取所有单位期限
		List<Organ> organList = this.modelFactoryFacade.getOrganFactory().findListByAll();
		
		for (Organ organ : organList) {

			//顶级单位不处理
			if (organ.getParentId().intValue() == 0) {
				continue;
			}
			
			//关闭状态不处理
			if (organ.getStatus().equals(OrganStatusEnum.Closed.toValue())) {
				continue;
			}
			
			//判断是否已经到期
			if (organ.getServiceTimeLimit().compareTo(DateUtility.getCurDate()) < 0) {
				
				//设置为关闭状态
				organ.setStatus(OrganStatusEnum.Closed.toValue());
				this.daoFacade.getOrganDAO().update(organ);
			}
		}
	}
	
	/**
	 * 激活单位
	 * @param name 单位帐号
	 * @param password 密码
	 * @param activateCodeData 激活码数据
	 */
	public void activateOrgan(String name, String password, String activateCodeData) throws Exception {
		
		//获取单位
		Organ organ = this.modelFactoryFacade.getOrganFactory().findByName(name);
		
		//判断单位是否存在
		if (organ == null) {
			throw new Exception("请输入正确的单位帐号！");
		}
		
		//判断登录密码是否争取
		if (!this.systemService.decryptPassword(organ.getPassword()).toLowerCase().equals(password.toLowerCase())) {
			throw new Exception("请输入正确的登录密码！");
		}
		
		//解析出激活码数据
		String json = DESUtility.decrypt(activateCodeData, "20010809");
		ActivateCode activateCode = (ActivateCode)JSONConvertor.json2Bean(json, ActivateCode.class);
		
		//更新数据
		organ.setUserNumberLimit(activateCode.getUserNumberLimit());
		organ.setLastActivatedTime(DateUtility.getCurDate());
		Date serviceTimeLimit = DateUtility.chineseStringToDate(activateCode.getServiceTimeLimit());
		organ.setServiceTimeLimit(serviceTimeLimit);
		
		//判断是否已经到期
		if (organ.getServiceTimeLimit().compareTo(DateUtility.getCurDate()) < 0) {
			organ.setStatus(OrganStatusEnum.Closed.toValue());
		} else {
			organ.setStatus(OrganStatusEnum.Opened.toValue());
		}
		
		this.daoFacade.getOrganDAO().update(organ);
	}
	
	/**
	 * 导出当前单位的所有下级单位到Excel
	 * @param curOganId 当前单位编号
	 * @return
	 * @throws IOException
	 */
	public InputStream exportChildOrganList(Long curOganId) throws Exception {
		
		//获取当前单位的所有下级单位
		List<Organ> organList = this.modelFactoryFacade.getOrganFactory().findListByChild(curOganId);
		
		//将单位列表转换成工作簿
		HSSFWorkbook workbook = OrganExcelTools.getWorkbook(organList, false);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 导出所有单位到Excel
	 * @param actionPassword 操作验证码
	 * @return
	 * @throws IOException
	 */
	public InputStream exportAllOrganList(String actionPassword) throws Exception {
		
		//判断操作验证码是否正确
		if (!this.getActionPassword().equals(actionPassword)) {
			throw new Exception("请输入正确的操作验证码！");
		}
		
		//获取当前单位的所有下级单位
		List<Organ> organList = this.modelFactoryFacade.getOrganFactory().findListByAll();
		
		//将单位列表转换成工作簿
		HSSFWorkbook workbook = OrganExcelTools.getWorkbook(organList, true);
		
		//创建文件数据流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();   
		workbook.write(baos);   
		
		byte[] ba = baos.toByteArray();   
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);   
		
		return bais;
	}
	
	/**
	 * 获取操作验证码
	 * @return
	 */
	private String getActionPassword() {
		
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    
		Integer value = (month * 8 + day * 9 + 2001 + year) * 2;
		return value.toString();
	}

}
