package com.study.action;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.CourseActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.CourseStatusEnum;
import com.study.enums.CourseTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Course;
import com.study.model.CourseCategory;
import com.study.model.CourseChapter;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 课程业务操作类
 */
public class CourseAction extends BaseActionSupport implements ModelDriven<CourseActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(CourseAction.class);
	
	/**
	 * 数据工厂层门面 
	 */
	@Resource
	private ModelFactoryFacade modelFactoryFacade;
	
	/**
	 * 业务层门面 
	 */
	@Resource
	private ServiceFacade serviceFacade;
	
	/**
	 * Action表单
	 */
	private CourseActionForm actionForm = new CourseActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取课程分类数据
	 */
	public String getCourseCategory() {
		
		try {
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategory().getId()).noNull();
			
			CourseCategory courseCategory = this.modelFactoryFacade.getCourseCategoryFactory().findById( this.actionForm.getCourseCategory().getId());
			
			this.setJsonResult_ActionResult(courseCategory);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取课程分类列表
	 */
	public String getCourseCategoryListByTree() {
		
		try {		
			List<CourseCategory> list = this.serviceFacade.getCourseService().getCourseCategoryListByTree();
			
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取课程数据
	 */
	public String getCourse() {
		
		try {
			ActionValidator.create("课程编号", this.actionForm.getCourse().getId()).noNull();
			
			Course course = this.modelFactoryFacade.getCourseFactory().findById( this.actionForm.getCourse().getId());
			
			this.setJsonResult_ActionResult(course);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取教师平台的课程分页列表
	 */
	public String getCourseListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseFactory().findListBySearch(
					this.actionForm.getCourseCategoryId(),
					CourseTypeEnum.valueOf(this.actionForm.getCourseType()),
					CourseStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}

	/**
	 * 按搜索条件获取教师平台的课程分页列表
	 */
	public String getCourseListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategoryId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseFactory().findListByOrgan(
					this.actionForm.getCourseCategoryId(),
					CourseTypeEnum.valueOf(this.actionForm.getCourseType()),
					this.actionForm.getName(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的课程分页列表
	 */
	public String getCourseListByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseFactory().findListByUser(
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取教师平台中的课程章节的分页列表
	 */
	public String getCourseChapterList() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getCourseChapterFactory().findListByCourse_Manager(
					this.actionForm.getCourseId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的课程的所有章节列表
	 */
	public String getCourseChapterListByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			List<CourseChapter> list = this.modelFactoryFacade.getCourseChapterFactory().findListByCourse(
					this.actionForm.getCourseId());
			
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取学员平台的课程的章节
	 */
	public String getCourseChapterByUser() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.User);
			
			CourseChapter courseChapter = this.serviceFacade.getCourseService().getCourseChapter(this.actionForm.getCourseChapterId());
			
			this.setJsonResult_ActionResult(courseChapter);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取教师平台的课程的章节
	 */
	public String getCourseChapterByManager() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			CourseChapter courseChapter = this.modelFactoryFacade.getCourseChapterFactory().findById(this.actionForm.getCourseChapterId());
			
			this.setJsonResult_ActionResult(courseChapter);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建课程分类记录
	 */
	public String createCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategoryId()).noNull();
			ActionValidator.create("课程分类数据", this.actionForm.getCourseCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourseCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getCourseCategory().getSortFlag()).noNull();
			
			this.serviceFacade.getCourseService().createCategory(
					this.actionForm.getCourseCategoryId(),
					this.actionForm.getCourseCategory().getName(),
					this.actionForm.getCourseCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功新建课程分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建课程子分类记录
	 */
	public String createChildCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategoryId()).noNull();
			ActionValidator.create("课程分类数据", this.actionForm.getCourseCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourseCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getCourseCategory().getSortFlag()).noNull();
			
			this.serviceFacade.getCourseService().createChildCategory(
					this.actionForm.getCourseCategoryId(),
					this.actionForm.getCourseCategory().getName(),
					this.actionForm.getCourseCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功新建课程子分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改课程分类记录
	 */
	public String modifyCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程分类数据", this.actionForm.getCourseCategory()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourseCategory().getName()).noNull();
			ActionValidator.create("排序", this.actionForm.getCourseCategory().getSortFlag()).noNull();
			 
			this.serviceFacade.getCourseService().modifyCategory(
					this.actionForm.getCourseCategory().getId(),
					this.actionForm.getCourseCategory().getName(),
					this.actionForm.getCourseCategory().getSortFlag());
			
			this.setJsonResult_ActionResult(true, "您已成功修改课程分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除课程分类记录
	 */
	public String removeCategory() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程分类数据", this.actionForm.getCourseCategory()).noNull();
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategory().getId()).noNull();
			 
			this.serviceFacade.getCourseService().removeCategory(
					this.actionForm.getCourseCategory().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除课程分类记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建课程记录
	 */
	public String createCourse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程分类编号", this.actionForm.getCourseCategoryId()).noNull();
			ActionValidator.create("课程数据", this.actionForm.getCourse()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourse().getName()).noNull();
			ActionValidator.create("封面图片", this.actionForm.getCourse().getCoverImageLink()).noNull();
			ActionValidator.create("学时", this.actionForm.getCourse().getClassHour()).noNull();
			ActionValidator.create("学分", this.actionForm.getCourse().getCredit()).noNull();
			ActionValidator.create("必修学分", this.actionForm.getCourse().getPassCreditLimit()).noNull();
			ActionValidator.create("学习次数", this.actionForm.getCourse().getCanMatchDutyRank()).noNull();

			this.serviceFacade.getCourseService().createCourse(
					this.actionForm.getCourseCategoryId(),
					CourseTypeEnum.valueOf(this.actionForm.getCourse().getCourseType()),
					this.actionForm.getCourse().getName(),
					this.actionForm.getCourse().getDescription(),
					this.actionForm.getCourse().getCoverImageLink(),
					this.actionForm.getCourse().getClassHour(),
					this.actionForm.getCourse().getCredit(),
					this.actionForm.getCourse().getPassCreditLimit(),
					this.actionForm.getCourse().getStudyNum(),
					this.actionForm.getCourse().getCanMatchDutyRank(),
					this.actionForm.getCourse().getDutyRank(),
					this.actionForm.getCourse().getCanMatchTrade(),
					this.actionForm.getCourse().getTrade(),
					this.actionForm.getCourse().getCanAllowAllUser(),
					CourseStatusEnum.valueOf(this.actionForm.getCourse().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建课程记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改课程记录
	 */
	public String modifyCourse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程数据", this.actionForm.getCourse()).noNull();
			ActionValidator.create("课程编号", this.actionForm.getCourse().getId()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourse().getName()).noNull();
			ActionValidator.create("封面图片", this.actionForm.getCourse().getCoverImageLink()).noNull();
			ActionValidator.create("学时", this.actionForm.getCourse().getClassHour()).noNull();
			ActionValidator.create("学分", this.actionForm.getCourse().getCredit()).noNull();
			ActionValidator.create("必修学分", this.actionForm.getCourse().getPassCreditLimit()).noNull();
			ActionValidator.create("学习次数", this.actionForm.getCourse().getCanMatchDutyRank()).noNull();
			 
			this.serviceFacade.getCourseService().modifyCourse(
					this.actionForm.getCourse().getId(),
					CourseTypeEnum.valueOf(this.actionForm.getCourse().getCourseType()),
					this.actionForm.getCourse().getName(),
					this.actionForm.getCourse().getDescription(),
					this.actionForm.getCourse().getCoverImageLink(),
					this.actionForm.getCourse().getClassHour(),
					this.actionForm.getCourse().getCredit(),
					this.actionForm.getCourse().getPassCreditLimit(),
					this.actionForm.getCourse().getStudyNum(),
					this.actionForm.getCourse().getCanMatchDutyRank(),
					this.actionForm.getCourse().getDutyRank(),
					this.actionForm.getCourse().getCanMatchTrade(),
					this.actionForm.getCourse().getTrade(),
					this.actionForm.getCourse().getCanAllowAllUser(),
					CourseStatusEnum.valueOf(this.actionForm.getCourse().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改课程记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 设置课程学员范围
	 */
	public String modifyUserJoinedData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程数据", this.actionForm.getCourse()).noNull();
			ActionValidator.create("课程编号", this.actionForm.getCourse().getId()).noNull();
			 
			this.serviceFacade.getCourseService().modifyCourseUserJoinedData(
					this.actionForm.getCourse().getId(),
					this.actionForm.getUserJoinedData(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功设置课程学员范围！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 删除课程记录
	 */
	public String removeCourse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程数据", this.actionForm.getCourse()).noNull();
			ActionValidator.create("课程编号", this.actionForm.getCourse().getId()).noNull();
			 
			this.serviceFacade.getCourseService().removeCourse(
					this.actionForm.getCourse().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除课程记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}	
	
	/**
	 * 导入课程
	 */
	public String importCourse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			this.serviceFacade.getCourseService().importCourse(
					this.actionForm.getFile(),
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入课程！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出文章
	 */
	public String exportCourse() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("课程编号", this.actionForm.getCourseId()).noNull();
			
			Course course = this.modelFactoryFacade.getCourseFactory().findById(this.actionForm.getCourseId());
			String fileName = "课程【" + course.getName() + "】.txt";
			
			String data = this.serviceFacade.getCourseService().exportCourse(this.actionForm.getCourseId());

			this.actionForm.setFileFileContentType("application/vnd.ms-xml");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(new ByteArrayInputStream(data.getBytes("UTF-8")));

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 上传课程图片
	 */
	public String uploadCourseImage() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			String url = this.serviceFacade.getCourseService().uploadCourseImage(this.actionForm.getFile());

			this.setJsonResult_ActionResult(url);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 上传课程章节图片
	 */
	public String uploadCourseChapterImage() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			String url = this.serviceFacade.getCourseService().uploadCourseChapterImage(this.actionForm.getFile());

			this.setJsonResult_ActionResult(url);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 创建课程章节
	 */
	public String createCourseChapter() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("章节数据", this.actionForm.getCourseChapter()).noNull();
			ActionValidator.create("课程编号", this.actionForm.getCourseChapter().getCourseId()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourseChapter().getName()).noNull();
			ActionValidator.create("内容", this.actionForm.getCourseChapter().getContent()).noNull();
			ActionValidator.create("排序", this.actionForm.getCourseChapter().getSortFlag()).noNull();
			
			this.serviceFacade.getCourseService().createCourseChapter(
					this.actionForm.getCourseChapter().getCourseId(),
					this.actionForm.getCourseChapter().getName(),
					this.actionForm.getCourseChapter().getContent(),
					this.actionForm.getCourseChapter().getSortFlag(),
					this.getSessionLoginResult().getId()
					);
			
			this.setJsonResult_ActionResult(true, "您已成功新建课程章节记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改课程章节
	 */
	public String modifyCourseChapter() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("章节数据", this.actionForm.getCourseChapter()).noNull();
			ActionValidator.create("课程章节编号", this.actionForm.getCourseChapter().getId()).noNull();
			ActionValidator.create("名称", this.actionForm.getCourseChapter().getName()).noNull();
			ActionValidator.create("内容", this.actionForm.getCourseChapter().getContent()).noNull();
			ActionValidator.create("排序", this.actionForm.getCourseChapter().getSortFlag()).noNull();
			
			this.serviceFacade.getCourseService().modifyCourseChapter(
					this.actionForm.getCourseChapter().getId(),
					this.actionForm.getCourseChapter().getName(),
					this.actionForm.getCourseChapter().getContent(),
					this.actionForm.getCourseChapter().getSortFlag(),
					this.getSessionLoginResult().getId()
					);
			
			this.setJsonResult_ActionResult(true, "您已成功修改课程章节记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改课程章节
	 */
	public String removeCourseChapter() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Course);
			
			ActionValidator.create("章节数据", this.actionForm.getCourseChapter()).noNull();
			ActionValidator.create("课程编号", this.actionForm.getCourseChapter().getCourseId()).noNull();
			
			this.serviceFacade.getCourseService().removeCourseChapter(
					this.actionForm.getCourseChapter().getCourseId(),
					this.getSessionLoginResult().getId()
					);
			
			this.setJsonResult_ActionResult(true, "您已成功删除课程章节记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public CourseActionForm getModel() {
		return actionForm;
	}

}
