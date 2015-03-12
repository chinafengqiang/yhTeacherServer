package com.study.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.study.action.form.SessionLoginResult;
import com.study.action.form.SessionLoginTypeEnum;
import com.study.utility.JSONBuilder;
import com.study.utility.JSONConvertor;
import com.study.utility.PaginateResult;
import com.study.utility.RandomValidateCode;
import com.study.utility.SystemUtility;

/**
 * ActionSupport扩展，系统中的所有Action由此类派生
 */
public class BaseActionSupport extends ActionSupport{
	
	/**
	 * 定义Action返回的JSON导航值
	 */
	protected static String JSONResult = "JSONResult"; 

	/**
	 * 定义Action返回的JSON单一对象导航值
	 */
	protected static String JSONResultObject = "JSONResultObject"; 

	/**
	 * 定义Action返回的字符串单一对象导航值
	 */
	protected static String StringResult = "StringResult";
	
	/**
	 * 定义Action返回的导出文件成功的单一对象导航值
	 */
	protected static String ExportFileResultSuccess = "ExportFileResultSuccess";
	
	/**
	 * 定义Action返回的下载文件失败的单一对象导航值
	 */
	protected static String ExportFileResultError = "ExportFileResultError";
	
	/**
	 * 定义存储json字符串的变量，
	 */
	protected Map<String,Object> jsonResult = new HashMap<String,Object>();
	
	/**
	 * 定义存储json单一字符串的变量，
	 */
	protected Object jsonResultObject = new Object();
	
	/**
	 * 定义存储json单一字符串的变量，
	 */
	private String stringResult = new String();
	
	/**
	 * 校验登录
	 * @throws Exception
	 */
	public void validateLoginResult() throws Exception {
		
		//获取登录结果
		SessionLoginResult loginResult = this.getSessionLoginResult();
		
		//若为空，则判断
		if (loginResult == null) {
			throw new Exception("请重新登录！");
		}
	}
	
	/**
	 * 校验登录类型
	 * @param loginType 登录类型
	 * @throws Exception
	 */
	public void validateLoginType(SessionLoginTypeEnum loginType) throws Exception {
		
		//获取登录结果
		SessionLoginResult loginResult = this.getSessionLoginResult();
		
		//若为空，则判断
		if (loginResult == null) {
			throw new Exception("请首先登录系统！");
		}
		
		if (loginType.toValue() != loginResult.getLoginType().toValue()) {
			throw new Exception("请首先登录系统！");
		}
	}
	
	/**
	 * 校验输入的验证码
	 * @param randomCodeKey 验证码
	 * @throws Exception
	 */
	public void validateRandomCodeKey(String randomCodeKey) throws Exception {
		
		if (StringUtils.isBlank(randomCodeKey)) {
			throw new Exception("请输入验证码！");
		}
		
		Map session = ActionContext.getContext().getSession();
		String theCode = (String)session.get(RandomValidateCode.RANDOMCODEKEY);
		
		if (StringUtils.isBlank(theCode)) {
			throw new Exception("请输入验证码！");
		}
		
		if (!theCode.toLowerCase().equals(randomCodeKey.toLowerCase())) {
			throw new Exception("请输入正确的验证码！");
		}
	}

	/**
	 * 获取Session中的登录结果
	 */
	public SessionLoginResult getSessionLoginResult() {
		
		Map session = ActionContext.getContext().getSession();
		return (SessionLoginResult)session.get("sessionLoginResult");
	}
	
	/**
	 * 设置Session中的登录结果
	 * @param sessionLoginResult 登录结果
	 */
	public void setSessionLoginResult(SessionLoginResult sessionLoginResult) {
		
		Map session = ActionContext.getContext().getSession();
		session.put("sessionLoginResult", sessionLoginResult);
	}
 	
	/**
	 * 获取Session中的随机码
	 */
	public String getSessionRandomCode() {
		
		Map session = ActionContext.getContext().getSession();
		return (String)session.get("randomCode");
	}
	
	/**
	 * 设置Session中的随机码
	 * @param randomCode 随机码
	 */
	public void setSessionRandomCode(String randomCode) {
		
		Map session = ActionContext.getContext().getSession();
		session.put("randomCode", randomCode);
	}
	
	/**
	 * 设置Session中的token
	 */
	public void setSessionRequestToken() {
		
		Map session = ActionContext.getContext().getSession();
		session.put("requestToken", SystemUtility.createUUID());
	}

	/**
	 * 获取Session中的token
	 */
	public String getSessionRequestToken() {
		
		Map session = ActionContext.getContext().getSession();
		return (String)session.get("requestToken");
	}
	
	/**
	 * 将操作状态转换成字符串
	 * @param actionStatus 操作状态(成功/失败)
	 * @return
	 */
	private String getActionStatusString(Boolean actionStatus) {
		
		if (actionStatus) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	/**
	 * 设置字符串内容--操作结果
	 * @param result 操作结果
	 */
	public void setStringResult_ActionResult(String result) {
		
		this.setStringResult(result);
	}
	
	/**
	 * 设置Josn字符串内容--操作结果
	 * @param actionStatus 操作状态
	 * @param actionMessage 操作消息
	 */
	public void setJsonResult_ActionResult(Boolean actionStatus, String actionMessage) {
		
		this.jsonResult.put("actionStatus", getActionStatusString(actionStatus));
		this.jsonResult.put("actionMessage", actionMessage);
	}
	
	/**
	 * 设置Josn字符串内容--操作结果
	 * @param actionStatus 操作状态
	 * @param actionMessage 操作消息
	 * @param data 数据
	 */
	public void setJsonResult_ActionResult(Boolean actionStatus, String actionMessage, Object data) {
		
		this.jsonResult.put("actionStatus", getActionStatusString(actionStatus));
		this.jsonResult.put("actionMessage", actionMessage);
		this.jsonResult.put("data", data);
	}
	
	/**
	 * 设置Josn字符串内容--操作结果
	 * @param actionStatus 操作状态
	 * @param data 数据
	 */
	public void setJsonResult_ActionResult(Object data) {
		
		this.jsonResult.put("actionStatus", getActionStatusString(true));
		this.jsonResult.put("data", data);
	}
	
	/**
	 * 获取Josn单一数据对象
	 */
	public Object getJsonResultObject() {
		return jsonResultObject;
	}
	
	/**
	 * 设置Josn字符串内容--单个数据记录
	 * @param data 数据
	 */
	public void setJsonResultObject(Object data) {
		
		this.jsonResultObject = data;
	}
	
	/**
	 * 添加Josn字符串内容--追加数据记录
	 * @param key 名称
	 * @param object 对象
	 */
	public void setJsonResult_Add(String key, Object object) {
		
		this.jsonResult.put(key, object);
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}
	
	public String getJsonResultString() {
		
		return JSONBuilder.create(this.jsonResult).toJsonString();
	}

	/**
	 * 获取服务器Http路径
	 */
	public String getServerPath() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String path = request.getScheme() + "://" + 
			request.getServerName() + ":" +
			request.getServerPort() +
			request.getContextPath() + "/";
		
		return path;
	}
	
	public void responseWriteJsonResult() {
		
		HttpServletResponse response = ServletActionContext.getResponse();   
		response.setCharacterEncoding("GB2312");
		String json = JSONConvertor.map2Json(this.jsonResult);   
		
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
		}   
	}
	
	public String getStringResult() {
		return stringResult;
	}
	
	public void setStringResult(String stringResult) {
		this.stringResult = stringResult;
	}

}
