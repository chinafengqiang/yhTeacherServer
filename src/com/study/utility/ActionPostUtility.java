package com.study.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;

public class ActionPostUtility {

	private String url;
	
	private String characterSet;
	
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	private void setUrl(String url) {
		this.url = url;
	}

	public static ActionPostUtility post(String url){
		
		ActionPostUtility htmlClientUtil = new ActionPostUtility();
		htmlClientUtil.setUrl(url);
		htmlClientUtil.setCharacterSet("UTF-8");
		return htmlClientUtil;
	}
	
	public ActionPostUtility setCharacterSet(String characterSet) {
		
		if (characterSet != null) {
			this.characterSet = characterSet;
		}
		
		return this;
	}
	
	public ActionPostUtility setParameter(String name, String value) throws Exception {
	
		if (StringUtils.isBlank(name)) {
			throw new Exception("参数名不可为空！");
		}
		
		NameValuePair nameValuePair  = new BasicNameValuePair(name, value);
		params.add(nameValuePair);

		return this;
	}
	
	public void exec() throws Exception  {
		
		String html;
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
           	HttpPost httpPost = new HttpPost(url);  
           	HttpEntity httpEntity = new UrlEncodedFormEntity(params, characterSet);  
           	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
           	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
           	httpPost.setEntity(httpEntity);  

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
            	html = EntityUtils.toString(httpResponse.getEntity());  
            } else {
            	throw new Exception("提交数据出错！");
            }
        } catch (Exception ex) {
        	throw new Exception("网络出错，请检查！");
        }   
        
		//分析部署结果
		Map result = JSONConvertor.json2Map(html);
		String actionStatus = (String)result.get("actionStatus");
		
		if (!actionStatus.equals("success")) {
			throw new Exception((String)result.get("actionMessage"));
		}
		
	}
	
	public Object data(Class objectClass) throws Exception  {
		
		String html;
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
           	HttpPost httpPost = new HttpPost(url);  
           	HttpEntity httpEntity = new UrlEncodedFormEntity(params, characterSet);  
           	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
           	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
           	httpPost.setEntity(httpEntity);  

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
            	html = EntityUtils.toString(httpResponse.getEntity());  
            } else {
            	throw new Exception("提交数据出错！");
            }
        } catch (Exception ex) {
        	throw new Exception("网络出错，请检查！");
        }   
        
		//分析部署结果
		Map result = JSONConvertor.json2Map(html);
		String actionStatus = (String)result.get("actionStatus");
		
		if (!actionStatus.equals("success")) {
			throw new Exception((String)result.get("actionMessage"));
		}
		
		JSONObject jsonObject = (JSONObject)result.get("data");
		return JSONObject.toBean(jsonObject, objectClass); 
	}
	
	public <T> List<T> dataList(Class<T> objectClass) throws Exception  {
		
		String html;
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
           	HttpPost httpPost = new HttpPost(url);  
           	HttpEntity httpEntity = new UrlEncodedFormEntity(params, characterSet);  
           	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
           	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
           	httpPost.setEntity(httpEntity);  

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
            	html = EntityUtils.toString(httpResponse.getEntity());  
            } else {
            	throw new Exception("提交数据出错！");
            }
        } catch (Exception ex) {
        	throw new Exception("网络出错，请检查！");
        }   
        
		//分析部署结果
		Map result = JSONConvertor.json2Map(html);
		String actionStatus = (String)result.get("actionStatus");
		
		if (!actionStatus.equals("success")) {
			throw new Exception((String)result.get("actionMessage"));
		}
		
		JSONArray jsonArray = (JSONArray)result.get("data");
		return JSONArray.toList(jsonArray, objectClass);
	}
}
