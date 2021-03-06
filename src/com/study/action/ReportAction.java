package com.study.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.ReportActionForm;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.enums.ReportExamDataStatusEnum;
import com.study.enums.ReportExamStatusEnum;
import com.study.enums.SysAccessTypeEnum;
import com.study.model.Organ;
import com.study.model.ReportExam;
import com.study.model.ReportExamData;
import com.study.model.ReportExamResult;
import com.study.model.factory.ModelFactoryFacade;
import com.study.model.part.ReportExamResultOrgan;
import com.study.model.part.ReportExamResultScore;
import com.study.service.ManagerService;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.PaginateResult;

/**
 * 报表业务操作类
 */
public class ReportAction extends BaseActionSupport implements ModelDriven<ReportActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(ReportAction.class);
	
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
	private ReportActionForm actionForm = new ReportActionForm();
	
	/**
	 * 教师业务接口
	 */
	@Resource
	private ManagerService managerService;
	
	/**
	 * 获取考试报表数据
	 */
	public String getReportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("报表编号", this.actionForm.getReportExam().getId()).noNull();
			
			ReportExam reportExam = this.modelFactoryFacade.getReportExamFactory().findById( this.actionForm.getReportExam().getId());
			
			this.setJsonResult_ActionResult(reportExam);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取考生成绩数据
	 */
	public String getReportExamData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("考生成绩编号", this.actionForm.getReportExamDataId()).noNull();
			
			ReportExamData reportExamData = this.modelFactoryFacade.getReportExamDataFactory().findById( this.actionForm.getReportExamDataId());
			
			this.setJsonResult_ActionResult(reportExamData);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取教师平台的考试报表分页列表
	 */
	public String getReportExamListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getReportExamFactory().findListBySearch(
					this.actionForm.getName(),
					this.actionForm.getExamCode(),
					ReportExamStatusEnum.valueOf(this.actionForm.getStatus()),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取单位平台的考试报表分页列表
	 */
	public String getReportExamListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getReportExamFactory().findListByOrgan(
					this.actionForm.getName(),
					this.actionForm.getExamCode(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取教师平台的考生成绩分页列表
	 */
	public String getReportExamDataListBySearch() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			
			ActionValidator.create("考试报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getReportExamDataFactory().findListBySearch(
					this.actionForm.getReportExamId(),
					this.actionForm.getActualOrgan(),
					this.actionForm.getActualName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取单位平台的考生成绩分页列表
	 */
	public String getReportExamDataListByOrgan() {
		
		try {		
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考试报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getReportExamDataFactory().findListByOrgan(
					this.actionForm.getReportExamId(),
					this.getSessionLoginResult().getId(),
					this.actionForm.getActualOrgan(),
					this.actionForm.getActualName(),
					this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按单位获取考试报表结果列表
	 */
	public String getReportExamResultOrganList() {
		
		try {
			ActionValidator.create("报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrganId()).noNull();
			
			List<ReportExamResultOrgan> list = this.serviceFacade.getReportService().getReportExamResultOrganList(
					this.actionForm.getReportExamId(),
					this.actionForm.getOrganId());
		
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 按单位获取分数段统计结果列表
	 */
	public String getReportExamResultScoreList() {
		
		try {
			ActionValidator.create("报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrganId()).noNull();
			
			List<ReportExamResultScore> list = this.serviceFacade.getReportService().getReportExamResultScoreList(
					this.actionForm.getReportExamId(),
					this.actionForm.getOrganId());
		
			this.setJsonResult_ActionResult(list);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取本站点顶级单位的考试统计报表数据
	 */
	public String getReportExamResultByTopOrgan() {
		
		try {
			ActionValidator.create("考试代号", this.actionForm.getExamCode()).noNull();
			
			ReportExamResult result = this.serviceFacade.getReportService().getReportExamResultByTopOrgan(
					this.actionForm.getExamCode());
		
			this.setJsonResult_ActionResult(result);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 新建考试报表记录
	 */
	public String createReportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考试报表数据", this.actionForm.getReportExam()).noNull();
			ActionValidator.create("报表名称", this.actionForm.getReportExam().getName()).noNull();
			ActionValidator.create("考试代号", this.actionForm.getReportExam().getExamCode()).noNull();
			ActionValidator.create("是否自动汇总成绩", this.actionForm.getReportExam().getCanAutoGather()).noNull();
			ActionValidator.create("统计状态", this.actionForm.getReportExam().getStatus()).noNull();

			this.serviceFacade.getReportService().createReportExam(
					this.actionForm.getReportExam().getName(),
					this.actionForm.getReportExam().getExamCode(),
					this.actionForm.getReportExam().getCanAutoGather(),
					ReportExamStatusEnum.valueOf(this.actionForm.getReportExam().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功新建考试报表记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改考试报表记录
	 */
	public String modifyReportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考试报表数据", this.actionForm.getReportExam()).noNull();
			ActionValidator.create("报表编号", this.actionForm.getReportExam().getId()).noNull();
			ActionValidator.create("是否自动汇总成绩", this.actionForm.getReportExam().getCanAutoGather()).noNull();
			ActionValidator.create("报表名称", this.actionForm.getReportExam().getName()).noNull();
			ActionValidator.create("统计状态", this.actionForm.getReportExam().getStatus()).noNull();

			this.serviceFacade.getReportService().modifyReportExam(
					this.actionForm.getReportExam().getId(),
					this.actionForm.getReportExam().getName(),
					this.actionForm.getReportExam().getCanAutoGather(),
					ReportExamStatusEnum.valueOf(this.actionForm.getReportExam().getStatus()),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改考试报表记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 删除考试报表记录
	 */
	public String removeReportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考试报表数据", this.actionForm.getReportExam()).noNull();
			ActionValidator.create("报表编号", this.actionForm.getReportExam().getId()).noNull();

			this.serviceFacade.getReportService().removeReportExam(
					this.actionForm.getReportExam().getId(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考试报表记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 将单位考试统计列表导出到Excel
	 */
	public String exportReportExamResultOrganToExcel() {
		
		try {
			ActionValidator.create("报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrganId()).noNull();

			Organ organ = this.modelFactoryFacade.getOrganFactory().findById(this.actionForm.getOrganId());
			String fileName = "单位【" + organ.getActualName() + "】考试统计报表.xls";
			
			InputStream is = this.serviceFacade.getReportService().exportReportExamResultOrganToExcel(
					this.actionForm.getReportExamId(),
					this.actionForm.getOrganId());	
			
			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 将单位考试分数段统计列表导出到Excel
	 */
	public String exportReportExamResultScoreToExcel() {
		
		try {
			ActionValidator.create("报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrganId()).noNull();

			Organ organ = this.modelFactoryFacade.getOrganFactory().findById(this.actionForm.getOrganId());
			String fileName = "单位【" + organ.getActualName() + "】考试分数段统计报表.xls";
			
			InputStream is = this.serviceFacade.getReportService().exportReportExamResultScoreToExcel(
					this.actionForm.getReportExamId(),
					this.actionForm.getOrganId());	
			
			this.actionForm.setFileFileContentType("application/vnd.ms-excel");
			this.actionForm.setFileFileName(URLEncoder.encode(fileName, "UTF-8"));
			this.actionForm.setInputStream(is);

			return ExportFileResultSuccess;
		} catch (Exception ex) {
			return ExportFileResultError;
		}
	}
	
	/**
	 * 教师平台删除考试报表的考生成绩
	 */
	public String removeReportExamDataByManager() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考生成绩编号", this.actionForm.getReportExamDataId()).noNull();

			this.serviceFacade.getReportService().removeReportExamData(
					this.actionForm.getReportExamDataId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考生成绩记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 单位平台删除考试报表的考生成绩
	 */
	public String removeReportExamDataByOrgan() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Organ);
			
			ActionValidator.create("考生成绩编号", this.actionForm.getReportExamDataId()).noNull();

			this.serviceFacade.getReportService().removeReportExamData(
					this.actionForm.getReportExamDataId());
			
			this.setJsonResult_ActionResult(true, "您已成功删除考生成绩记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 教师平台修改考试报表的考生成绩
	 */
	public String modifyReportExamData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考生成绩编号", this.actionForm.getReportExamDataId()).noNull();
			ActionValidator.create("分数", this.actionForm.getScore()).noNull();
			ActionValidator.create("状态", this.actionForm.getStatus()).noNull();

			this.serviceFacade.getReportService().modifyReportExamData(
					this.actionForm.getReportExamDataId(),
					this.actionForm.getScore(),
					ReportExamDataStatusEnum.valueOf(this.actionForm.getStatus()));
			
			this.setJsonResult_ActionResult(true, "您已成功修改考生成绩记录！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 导入软件生成的考生成绩包
	 */
	public String importSoftwareReportExamData() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.managerService.validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.ReportExam);
			
			ActionValidator.create("考试报表编号", this.actionForm.getReportExamId()).noNull();
			ActionValidator.create("单位编号", this.actionForm.getOrganId()).noNull();
			ActionValidator.create("通过分数", this.actionForm.getPassScore()).noNull();
			
			this.serviceFacade.getReportService().importSoftwareReportExamData(
					this.actionForm.getReportExamId(),
					this.actionForm.getOrganId(),
					this.actionForm.getPassScore(),
					this.actionForm.getFile());

			this.setJsonResult_ActionResult(true, "您已成功导入考生成绩包！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		this.responseWriteJsonResult();
		return NONE;
	}
	
	/**
	 * 教师平台自动统计考试报表
	 */
	public String autoStatReportExam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);

			this.serviceFacade.getReportService().autoStatReportExam();
			
			this.setJsonResult_ActionResult(true, "您已成功统计考试报表！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public ReportActionForm getModel() {
		return actionForm;
	}

}
