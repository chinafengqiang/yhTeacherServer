package com.study.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.OrganActionForm;
import com.study.action.form.SessionLoginResult;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.OrganStatusEnum;
import com.study.enums.OrganTypeEnum;
import com.study.model.Organ;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 单位业务操作类
 */
public class OrganAction extends BaseActionSupport implements ModelDriven<OrganActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(OrganAction.class);
	
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
	private OrganActionForm actionForm = new OrganActionForm();
	
	/**
	 * 获取单位数据
	 */
	public String getOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("单位编号", this.actionForm.getOrgan().getId()).noNull();
			
			Organ organ = this.modelFactoryFacade.getOrganFactory().findById( this.actionForm.getOrgan().getId());
			
			this.setJsonResult_ActionResult(organ);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取当前单位数据
	 */
	public String getCurOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			Organ organ = this.modelFactoryFacade.getOrganFactory().findById(this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(organ);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取顶级单位数据
	 */
	public String getTopOrgan() {
		
		try {
			Organ organ = this.modelFactoryFacade.getOrganFactory().findByTop();
			
			this.setJsonResult_ActionResult(organ);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按搜索条件获取单位分页列表
	 */
	public String getOrganListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getOrganFactory().findListBySearch(
					OrganTypeEnum.valueOf(this.actionForm.getOrganType()),
					OrganStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getActualName(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取本单位及下级单位列表
	 */
	public String getOrganListBySelfChild() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			List<Organ> list = this.modelFactoryFacade.getOrganFactory().findListBySelf_Child_Opened(this.getSessionLoginResult().getId());
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取所有单位列表
	 */
	public String getOrganList() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			List<Organ> list = this.modelFactoryFacade.getOrganFactory().findListByAll();
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取所有停用的单位列表
	 */
	public String getClosedOrganList() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			List<Organ> list = this.modelFactoryFacade.getOrganFactory().findListByClosed();
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取供选择的所有启动的单位分页列表
	 */
	public String getOrganListBySelect() {
		
		try {		
			PaginateResult dataList = this.modelFactoryFacade.getOrganFactory().findListBySelect(
					OrganTypeEnum.valueOf(this.actionForm.getOrganType()),
					this.actionForm.getActualName(),
					this.actionForm.getParentId(),
					OrganStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters()
					);
			this.setJsonResult_ActionResult(dataList);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建单位记录
	 */
	public String createOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("单位数据", this.actionForm.getOrgan()).noNull();
			ActionValidator.create("单位帐号", this.actionForm.getOrgan().getName()).noNull();
			ActionValidator.create("单位名称", this.actionForm.getOrgan().getActualName()).noNull();
			ActionValidator.create("地区", this.actionForm.getOrgan().getArea()).noNull();
			ActionValidator.create("单位类型", this.actionForm.getOrgan().getOrganType()).noNull();

			this.serviceFacade.getOrganService().createOrgan(
					this.actionForm.getOrgan().getName(),
					this.actionForm.getOrgan().getActualName(),
					this.actionForm.getOrgan().getShortName(),
					OrganTypeEnum.valueOf(this.actionForm.getOrgan().getOrganType()),
					this.actionForm.getOrgan().getLinkman(),
					this.actionForm.getOrgan().getTel(),
					this.actionForm.getOrgan().getMobile(),
					this.actionForm.getOrgan().getArea(),
					this.actionForm.getOrgan().getAddress(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建单位记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改单位记录
	 */
	public String modifyOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("单位数据", this.actionForm.getOrgan()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrgan().getId()).noNull();
			ActionValidator.create("地区", this.actionForm.getOrgan().getArea()).noNull();
			 
			this.serviceFacade.getOrganService().modifyOrgan(
					this.actionForm.getOrgan().getId(),
					this.actionForm.getOrgan().getShortName(),
					this.actionForm.getOrgan().getLinkman(),
					this.actionForm.getOrgan().getTel(),
					this.actionForm.getOrgan().getMobile(),
					this.actionForm.getOrgan().getArea(),
					this.actionForm.getOrgan().getAddress(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改单位记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除单位记录
	 */
	public String removeOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("单位数据", this.actionForm.getOrgan()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrgan().getId()).noNull();
			 
			this.serviceFacade.getOrganService().removeOrgan(
					this.actionForm.getOrgan().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除单位记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改单位密码
	 */
	public String modifyPassword() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("旧密码", this.actionForm.getOldPassword()).noNull();
			ActionValidator.create("新密码", this.actionForm.getNewPassword()).noNull();
			 
			this.serviceFacade.getOrganService().modifyPassword(
					this.getSessionLoginResult().getId(),
					this.actionForm.getOldPassword(),
					this.actionForm.getNewPassword());
			
			this.setJsonResult_ActionResult(true, "您已成功修改登录密码！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位登录
	 */
	public String login() {
		
		try {
			this.validateRandomCodeKey(this.actionForm.getRandomCodeKey());
			ActionValidator.create("帐号", this.actionForm.getName()).noNull();
			ActionValidator.create("密码", this.actionForm.getPassword()).noNull();
			 
			Organ organ = this.serviceFacade.getOrganService().login(
					this.actionForm.getName(),
					this.actionForm.getPassword());
			
			SessionLoginResult sessionLoginResult = SessionLoginResult.createOrganLoginResult(organ.getId(), organ.getName(), organ.getActualName());
			this.setSessionLoginResult(sessionLoginResult);
			
			this.setJsonResult_ActionResult(true, "您已成功登录单位平台！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 激活单位记录
	 */
	public String activateOrgan() {
		
		try {
			ActionValidator.create("单位帐号", this.actionForm.getName()).noNull();
			ActionValidator.create("登录密码", this.actionForm.getPassword()).noNull();
			ActionValidator.create("激活码数据", this.actionForm.getActivateCodeData()).noNull();
			 
			this.serviceFacade.getOrganService().activateOrgan(
					this.actionForm.getName(),
					this.actionForm.getPassword(),
					this.actionForm.getActivateCodeData());
			
			this.setJsonResult_ActionResult(true, "您已成功激活单位帐号！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导出下级单位列表
	 */
	public String exportChildOrganList() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);

			Organ organ = this.modelFactoryFacade.getOrganFactory().findById(this.getSessionLoginResult().getId());
			String fileName = "下级单位列表【" + organ.getName() + "】.xls";
			
			InputStream is = this.serviceFacade.getOrganService().exportChildOrganList(
					this.getSessionLoginResult().getId());			
			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 导出所有单位列表
	 */
	public String exportAllOrganList() {
		
		try {
			ActionValidator.create("操作验证码", this.actionForm.getActionPassword()).noNull();
			
			String fileName = "单位详情列表.xls";
			InputStream is = this.serviceFacade.getOrganService().exportAllOrganList(this.actionForm.getActionPassword());

			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 自动校验单位期限
	 */
	public String autoValidateOrganServiceLimit() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);

			this.serviceFacade.getOrganService().autoValidateOrganServiceLimit();
			
			this.setJsonResult_ActionResult(true, "您已成功校验单位期限！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public OrganActionForm getModel() {
		return actionForm;
	}

}
