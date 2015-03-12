package com.study.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.action.form.SystemActionForm;
import com.study.constant.ServerIP;
import com.study.enums.SysAccessTypeEnum;
import com.study.enums.SysParamTypeEnum;
import com.study.model.QuestionCategory;
import com.study.model.SysClass;
import com.study.model.SysParam;
import com.study.model.factory.ModelFactoryFacade;
import com.study.service.ServiceFacade;
import com.study.utility.ActionValidator;
import com.study.utility.FastJsonTools;
import com.study.utility.HttpUtil;
import com.study.utility.PaginateResult;

/**
 * 系统业务操作类
 */
public class SystemAction extends BaseActionSupport implements ModelDriven<SystemActionForm>{

	/**
	 * 日志记录器
	 */
	private static Log logger = LogFactory.getLog(SystemAction.class);
	
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
	 * Memcached客户端
	 */
	@Resource
	private MemcachedClient memcachedClient;
	
	private static String classListByDataMemKey = "ClassListByDataMemKey";
	
	private static Map<Long, String> mapCache = new HashMap<Long, String>();
	
	/**
	 * Action表单
	 */
	private SystemActionForm actionForm = new SystemActionForm();

	/**
	 * 获取子站点列表
	 */
	public String getSubSiteMap() {
		
		try {
			Map map = this.serviceFacade.getSystemService().getSubSiteMap();
			
			this.setJsonResult_ActionResult(map);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取系统参数数据
	 */
	public String getSysParam() {
		
		try {
			ActionValidator.create("系统参数编号", this.actionForm.getSysParam().getId()).noNull();
			
			SysParam sysParam = this.modelFactoryFacade.getSysParamFactory().findById( this.actionForm.getSysParam().getId());
			
			this.setJsonResult_ActionResult(sysParam);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取系统参数分页列表
	 */
	public String getSysParamList() {
		
		try {		
			ActionValidator.create("分页参数", this.actionForm.getPaginateParamters()).noNull();
			
			PaginateResult paginateResult = this.modelFactoryFacade.getSysParamFactory().findList(this.actionForm.getPaginateParamters());
			
			this.setJsonResult_ActionResult(paginateResult);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 修改系统参数
	 */
	public String modifySysParam() {
		
		try {
			this.validateLoginType(SessionLoginTypeEnum.Manager);
			this.serviceFacade.getManagerService().validateSysAccess(this.getSessionLoginResult().getId(), SysAccessTypeEnum.SysParam);
			
			ActionValidator.create("系统参数数据", this.actionForm.getSysParam()).noNull();
			 
			this.serviceFacade.getSystemService().modifySysParam(
					SysParamTypeEnum.valueOf(this.actionForm.getSysParam().getSysParamType()),
					this.actionForm.getSysParam().getValue(),
					this.getSessionLoginResult().getId());
			
			this.setJsonResult_ActionResult(true, "您已成功修改系统参数！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取枚举键对值
	 */
	public String getEnumMap() {
		
		try {
			ActionValidator.create("枚举名称", this.actionForm.getEnumName()).noNull();
			
			Map map = this.serviceFacade.getSystemService().getEnumMap(this.actionForm.getEnumName());
			
			this.setJsonResult_ActionResult(map);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取枚举键对值
	 */
	public String geClassMap() {
		
		try {
			
			Map<Long, String> map = new HashMap<Long, String>();
			
			if(mapCache != null && mapCache.size() > 0){
				map.putAll(mapCache);
			} else {
			
				String path = String.format(ServerIP.SERVLET_CLASS);
				System.out.println("path==="+path);
				String data = HttpUtil.getDataFromUrl(path);
				String jsonString = new String(data);
				JSONObject item = new JSONObject(jsonString);
				Object obj = item.get("rows");
				System.out.println("obj==="+obj);
				List<SysClass> sysClasss = FastJsonTools.getObects(obj.toString(), SysClass.class);
				
				for(SysClass sc : sysClasss){
					map.put(sc.getId(), sc.getName());
				}
				
				mapCache.putAll(map);
			}
			
			this.setJsonResult_ActionResult(map);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	/**
	 * 获取系统参数
	 */
	public String getSysParamValue() {
		
		try {
			ActionValidator.create("参数名称", this.actionForm.getSysParamName()).noNull();
			
			String sysParamValue = this.serviceFacade.getSystemService().getSysParamValue(this.actionForm.getSysParamName());
			
			this.setJsonResult_ActionResult(sysParamValue);
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	public String genarateUSHtml() {
		
		try {
			
			
			this.setJsonResult_ActionResult(true, "已成功生成学员平台Html文件！");
		} catch (Exception ex) {
			this.setJsonResult_ActionResult(false, ex.getMessage());
		}
		
		return JSONResult;
	}
	
	@Override
	public SystemActionForm getModel() {
		return actionForm;
	}
	
	/**
	 * 从缓存中获取分类树数据
	 * @return
	 * @throws Exception
	 */
	private Map<Long, String> loadClassListByDataCache() throws Exception {
		
		return this.memcachedClient.get(classListByDataMemKey, 5000);
	}
	
	/**
	 * 保存分类树数据到缓存中
	 * @return
	 * @throws Exception
	 */
	private void saveClassByDataCache(Map<Long, String> map) throws Exception {
		
		this.memcachedClient.set(classListByDataMemKey, 3600*24, map);
	}
	
	/**
	 * 清除缓存中的分类树数据
	 * @return
	 * @throws Exception
	 */
	private void clearQuestionCategoryListByTreeCache() throws Exception {
		
		this.memcachedClient.delete(classListByDataMemKey);
	}
	
	
	

}
