package com.study.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.NoticeActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.NoticeGradeEnum;
import com.study.enums.NoticeStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Notice;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.JSONConvertor;
import com.study.utility.PaginateResult;

/**
 * 公告业务操作类
 */
public class NoticeAction extends BaseActionSupport implements ModelDriven<NoticeActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(NoticeAction.class);
	
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
	private NoticeActionForm actionForm = new NoticeActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取公告数据
	 */
	public String getNotice() {
		
		try {
			ActionValidator.create("公告编号", this.actionForm.getNotice().getId()).noNull();
			
			Notice notice = this.modelFactoryFacade.getNoticeFactory().findById( this.actionForm.getNotice().getId());
			
			this.setJsonResult_ActionResult(notice);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取在教师平台公告分页列表
	 */
	public String getNoticeListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getNoticeFactory().findListBySearch(
					NoticeGradeEnum.valueOf(this.actionForm.getNoticeGrade()),
					NoticeStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getTitle(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取在单位平台显示的公告列表
	 */
	public String getNoticeListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getNoticeFactory().findListByOrgan(
					NoticeGradeEnum.valueOf(this.actionForm.getNoticeGrade()),
					this.actionForm.getTitle(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取在学员平台显示的公告列表
	 */
	public String getNoticeListByUser() {
		
		try {		
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getNoticeFactory().findListByUser(
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建公告记录
	 */
	public String createNotice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			ActionValidator.create("公告数据", this.actionForm.getNotice()).noNull();
			ActionValidator.create("标题", this.actionForm.getNotice().getTitle()).noNull();
			ActionValidator.create("内容", this.actionForm.getNotice().getContent()).noNull();
			ActionValidator.create("级别", this.actionForm.getNotice().getGrade()).noNull();
			ActionValidator.create("排序", this.actionForm.getNotice().getSortFlag()).noNull();
			ActionValidator.create("状态", this.actionForm.getNotice().getStatus()).noNull();
			 
			this.serviceFacade.getNoticeService().createNotice(
					this.actionForm.getNotice().getTitle(),
					this.actionForm.getNotice().getContent(),
					NoticeGradeEnum.valueOf(this.actionForm.getNotice().getGrade()),
					this.actionForm.getNotice().getSortFlag(),
					NoticeStatusEnum.valueOf(this.actionForm.getNotice().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建公告记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改公告记录
	 */
	public String modifyNotice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			ActionValidator.create("公告数据", this.actionForm.getNotice()).noNull();
			ActionValidator.create("公告编号", this.actionForm.getNotice().getId()).noNull();
			ActionValidator.create("标题", this.actionForm.getNotice().getTitle()).noNull();
			ActionValidator.create("内容", this.actionForm.getNotice().getContent()).noNull();
			ActionValidator.create("排序", this.actionForm.getNotice().getSortFlag()).noNull();
			 
			this.serviceFacade.getNoticeService().modifyNotice(
					this.actionForm.getNotice().getId(),
					this.actionForm.getNotice().getTitle(),
					this.actionForm.getNotice().getContent(),
					NoticeGradeEnum.valueOf(this.actionForm.getNotice().getGrade()),
					this.actionForm.getNotice().getSortFlag(),
					NoticeStatusEnum.valueOf(this.actionForm.getNotice().getStatus()),
					this.getSessionLoginResult().getId());			
			this.setJsonResult_ActionResult(true, "您已成功修改公告记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除公告记录
	 */
	public String removeNotice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			ActionValidator.create("公告数据", this.actionForm.getNotice()).noNull();
			ActionValidator.create("公告编号", this.actionForm.getNotice().getId()).noNull();
			 
			this.serviceFacade.getNoticeService().removeNotice(
					this.actionForm.getNotice().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除公告记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入公告
	 */
	public String importNotice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			this.serviceFacade.getNoticeService().importNotice(
					this.actionForm.getFile(), 
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入公告记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;   
	}
	
	/**
	 * 导出公告
	 */
	public String exportNotice() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			ActionValidator.create("公告编号", this.actionForm.getNotice().getId()).noNull();
			
			String data = this.serviceFacade.getNoticeService().exportNotice(
					this.actionForm.getNotice().getId(),
					this.getSessionLoginResult().getId());

			Notice notice = this.modelFactoryFacade.getNoticeFactory().findById(this.actionForm.getNotice().getId());
			String fileName = "公告【" + notice.getTitle() + "】.txt";

			this.actionForm.setFileFileContentType("application/vnd.ms-xml");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(new ByteArrayInputStream(data.getBytes("UTF-8")));

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 上传图片
	 */
	public String uploadNoticeImage() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Notice);
			
			String url = this.serviceFacade.getNoticeService().uploadNoticeImage(this.actionForm.getFile());

			this.setJsonResult_ActionResult(url);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	@Override
	public NoticeActionForm getModel() {
		return actionForm;
	}

}
