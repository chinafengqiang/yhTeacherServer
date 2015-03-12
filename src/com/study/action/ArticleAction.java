package com.study.action;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ArticleActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ArticleStatusEnum;
import com.study.enums.ArticleTypeEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Article;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 资讯业务操作类
 */
public class ArticleAction extends BaseActionSupport implements ModelDriven<ArticleActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ArticleAction.class);
	
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
	private ArticleActionForm actionForm = new ArticleActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取文章数据
	 */
	public String getArticle() {
		
		try {
			ActionValidator.create("文章编号", this.actionForm.getArticle().getId()).noNull();
			
			Article article = this.modelFactoryFacade.getArticleFactory().findById( this.actionForm.getArticle().getId());
			
			this.setJsonResult_ActionResult(article);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取文章分页列表
	 */
	public String getArticleListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListBySearch(
					ArticleTypeEnum.valueOf(this.actionForm.getArticleType()),
					this.actionForm.getTitle(),
					this.actionForm.getChannel(),
					ArticleStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取学员平台的文章分页列表
	 */
	public String getArticleListByChannelUser() {
		
		try {		
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			ActionValidator.create("频道名称", this.actionForm.getChannel()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListBySearch(
					ArticleTypeEnum.valueOf(this.actionForm.getArticleType()),
					this.actionForm.getTitle(),
					this.actionForm.getChannel(),
					ArticleStatusEnum.Opened,
					this.actionForm.getPaginateParamters());
			
			this.serviceFacade.getArticleService().fixTitle(paginateResult.getList(), 80);
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取已推荐且已发布的文章列表
	 */
	public String getArticleListByRecommended() {
		
		try {		
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			ActionValidator.create("文章类型", this.actionForm.getArticleType()).noNull();
			ActionValidator.create("发布区域", this.actionForm.getChannel()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByRecommended(
					ArticleTypeEnum.valueOf(this.actionForm.getArticleType()),
					this.actionForm.getChannel(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按发布区域获取已发布的文章分页列表
	 */
	public String getArticleListByChannel() {
		
		try {		
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			ActionValidator.create("文章类型", this.actionForm.getArticleType()).noNull();
			ActionValidator.create("发布区域", this.actionForm.getChannel()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getArticleFactory().findListByChannel(
					ArticleTypeEnum.valueOf(this.actionForm.getArticleType()),
					this.actionForm.getChannel(),
					this.actionForm.getContent(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建文章记录
	 */
	public String createArticle() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			ActionValidator.create("文章数据", this.actionForm.getArticle()).noNull();
			ActionValidator.create("标题", this.actionForm.getArticle().getTitle()).noNull();
			ActionValidator.create("内容", this.actionForm.getArticle().getContent()).noNull();
			 
			this.serviceFacade.getArticleService().createArticle(
					this.actionForm.getArticle().getTitle(),
					this.actionForm.getArticle().getContent(),
					ArticleTypeEnum.valueOf(this.actionForm.getArticle().getArticleType()),
					this.actionForm.getArticle().getChannel(),
					this.actionForm.getArticle().getSource(),
					this.actionForm.getArticle().getKeyword(),
					this.actionForm.getArticle().getCanRecommended(),
					ArticleStatusEnum.valueOf(this.actionForm.getArticle().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建文章记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改文章记录
	 */
	public String modifyArticle() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			ActionValidator.create("文章数据", this.actionForm.getArticle()).noNull();
			ActionValidator.create("文章编号", this.actionForm.getArticle().getId()).noNull();
			ActionValidator.create("标题", this.actionForm.getArticle().getTitle()).noNull();
			ActionValidator.create("内容", this.actionForm.getArticle().getContent()).noNull();
			 
			this.serviceFacade.getArticleService().modifyArticle(
					this.actionForm.getArticle().getId(),
					this.actionForm.getArticle().getTitle(),
					this.actionForm.getArticle().getContent(),
					ArticleTypeEnum.valueOf(this.actionForm.getArticle().getArticleType()),
					this.actionForm.getArticle().getChannel(),
					this.actionForm.getArticle().getSource(),
					this.actionForm.getArticle().getKeyword(),
					this.actionForm.getArticle().getCanRecommended(),
					ArticleStatusEnum.valueOf(this.actionForm.getArticle().getStatus()),
					this.getSessionLoginResult().getId());			
			this.setJsonResult_ActionResult(true, "您已成功修改文章记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除文章记录
	 */
	public String removeArticle() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			ActionValidator.create("文章数据", this.actionForm.getArticle()).noNull();
			ActionValidator.create("文章编号", this.actionForm.getArticle().getId()).noNull();
			 
			this.serviceFacade.getArticleService().removeArticle(
					this.actionForm.getArticle().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除文章记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入文章
	 */
	public String importArticle() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			this.serviceFacade.getArticleService().importArticle(
					this.actionForm.getFile(), 
					this.getSessionLoginResult().getId());

			this.setJsonResult_ActionResult(true, "您已成功导入文章记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 导出文章
	 */
	public String exportArticle() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			ActionValidator.create("文章编号", this.actionForm.getArticle().getId()).noNull();
			
			String fileName = "文章【" + this.actionForm.getArticle().getId() + "】.txt";
			String data = this.serviceFacade.getArticleService().exportArticle(
					this.actionForm.getArticle().getId(),
					this.getSessionLoginResult().getId());

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
	public String uploadArticleImage() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.Article);
			
			String url = this.serviceFacade.getArticleService().uploadArticleImage(this.actionForm.getFile());

			this.setJsonResult_ActionResult(url);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	@Override
	public ArticleActionForm getModel() {
		return actionForm;
	}

}
