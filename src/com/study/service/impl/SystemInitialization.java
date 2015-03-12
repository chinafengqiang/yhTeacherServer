package com.study.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.transaction.annotation.Transactional;

import com.study.dao.DAOFacade;
import com.study.enums.ManagerGradeEnum;
import com.study.enums.ManagerStatusEnum;
import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.CourseCategory;
import com.study.model.Manager;
import com.study.model.Organ;
import com.study.model.QuestionCategory;
import com.study.model.SysParam;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.SystemService;
import com.study.service.UserSystemService;
import com.study.utility.DateUtility;

/**
 * 系统初始化
 */
public class SystemInitialization {

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
	 * 学员平台业务层接口
	 */
	@Resource
	private UserSystemService userSystemService;
	
	/**
	 * 系统业务接口 
	 */
	@Resource
	private SystemService systemService;
	
	/**
	 * 初始化系统
	 */
	public void init() {
		
		//判断是否存在顶级单位
		if (this.modelFactoryFacade.getOrganFactory().findByTop() != null) {
			return;
		}

		String fileName = this.getClass().getClassLoader().getResource("").getPath() + "system.xml"; 
		
		Document document = null; 
		try 
		{ 
			SAXReader saxReader = new SAXReader(); 
			document = saxReader.read(new File(fileName));
			
			Element root = document.getRootElement();
			
			this.createOrgan(root);
			this.createManager(root);
			this.createCourseCategory(root);
			this.createQuestionCategory(root);
			this.createSysParams(root);
		} 
		catch (Exception ex){ 
			System.out.println("==========！！！严重错误：无法初始化系统！！！=============="); 
		}   
		
		System.out.println("=============成功初始化系统！==============");
	}
	
	/**
	 * 创建单位记录
	 * @param root
	 */
	private void createOrgan(Element root) {

		String name = root.element("Organ").attributeValue("name");
		String actualName = root.element("Organ").attributeValue("actualName");
		String password = root.element("Organ").attributeValue("password");
		String shortName = root.element("Organ").attributeValue("shortName");
		String area = root.element("Organ").attributeValue("area");
		
		Organ organ = new Organ();
		
		organ.setName(name);
		organ.setActualName(actualName);
		organ.setAddress("");
		organ.setParentId(0l);
		organ.setLinkman("");
		organ.setTel("");
		organ.setMobile("");
		organ.setArea(area);
		organ.setAddress("");
		organ.setPassword(this.systemService.encryptPassword(password));
		organ.setServiceTimeLimit(DateUtility.getCurDate());
		organ.setShortName(shortName);
		organ.setOrganType(OrganTypeEnum.Master.toValue());
		organ.setStatus(OrganStatusEnum.Opened.toValue());
		organ.setLastActivatedTime(DateUtility.getCurDate());
		organ.setUserNumberLimit(500);
		
		this.daoFacade.getOrganDAO().insert(organ);
	}
	
	/**
	 * 创建教师记录
	 * @param root
	 */
	private void createManager(Element root) {
		
		String name = root.element("Manager").attributeValue("name");
		String actualName = root.element("Manager").attributeValue("actualName");
		String password = root.element("Manager").attributeValue("password");
		
		Manager manager = new Manager();
		manager.setActualName(actualName);
		manager.setEmail("");
		manager.setGrade(ManagerGradeEnum.High.toValue());
		manager.setMobile("");
		manager.setName(name);
		manager.setPassword(this.systemService.encryptPassword(password));
		manager.setStatus(ManagerStatusEnum.Opened.toValue());
		manager.setSysAccess(SysAccessTypeEnum.getValueList());
		
		this.daoFacade.getManagerDAO().insert(manager);
	}
	
	/**
	 * 创建课程分类
	 * @param root
	 */
	private void createCourseCategory(Element root) {
		
		String name = root.element("CourseCategory").attributeValue("name");
		
		CourseCategory courseCategory = new CourseCategory();
		
		courseCategory.setLevel(1);
		courseCategory.setName(name);
		courseCategory.setParentId(0l);
		courseCategory.setSortFlag(1);
		
		this.daoFacade.getCourseCategoryDAO().insert(courseCategory);
	}
	
	/**
	 * 创建题目分类
	 * @param root
	 */
	private void createQuestionCategory(Element root) {
		
		String name = root.element("QuestionCategory").attributeValue("name");
		
		QuestionCategory questionCategory = new QuestionCategory();
		
		questionCategory.setLevel(1);
		questionCategory.setName(name);
		questionCategory.setParentId(0l);
		questionCategory.setSortFlag(1);
		
		this.daoFacade.getQuestionCategoryDAO().insert(questionCategory);
	}
	
	/**
	 * 创建系统参数
	 * @param root
	 */
	private void createSysParams(Element root) {
	
		List list = root.element("SysParams").elements();
		
		for (int i=0; i<list.size(); i++) {
			
			Element item = (Element)list.get(i);
			Integer sysParamType = Integer.parseInt(item.attributeValue("sysParamType"));
			Integer sysParamValueType = Integer.parseInt(item.attributeValue("sysParamValueType"));
			String name = item.attributeValue("name");
			String value = item.attributeValue("value");
			String note = item.attributeValue("note");
			Boolean canModify = item.attributeValue("canModify").endsWith("1");
			
			SysParam sysParam = new SysParam();
			sysParam.setCanModify(canModify);
			sysParam.setName(name);
			sysParam.setNote(note);
			sysParam.setSysParamType(sysParamType);
			sysParam.setSysParamValueType(sysParamValueType);
			sysParam.setValue(value);
			
			this.daoFacade.getSysParamDAO().insert(sysParam);
		}
	}
}
