package com.study.utility;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HtmlClientUtility {

	private String url;
	
	private String characterSet;
	
	private NameValuePair[] nameValuePairs; 
	
	public List<NameValuePair> list = new ArrayList<NameValuePair>();
	
	private void setUrl(String url) {
		this.url = url;
	}

	public static HtmlClientUtility post(String url){
		
		HtmlClientUtility htmlClientUtil = new HtmlClientUtility();
		htmlClientUtil.setUrl(url);
		htmlClientUtil.setCharacterSet("UTF-8");
		return htmlClientUtil;
	}
	
	public HtmlClientUtility setCharacterSet(String characterSet) {
		
		if (characterSet != null) {
			this.characterSet = characterSet;
		}
		
		return this;
	}
	
	public HtmlClientUtility setParameter(String name, String value) throws Exception {
	
		if (name == null && "".equals(name)) {
			throw new Exception("参数名不可为空！");
		}
		
		NameValuePair nameValuePair  = new NameValuePair();
		nameValuePair.setName(name);
		nameValuePair.setValue(value);
		list.add(nameValuePair);
		
		nameValuePairs = new NameValuePair[list.size()];
		for(int i = 0; i < list.size(); i ++){
			nameValuePairs[i] = list.get(i);
		}
		
		return this;
	}
	
	public String html() throws Exception  {
		
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, characterSet); 

		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		
		postMethod.setRequestBody(nameValuePairs);
		try {  
			int statusCode = httpClient.executeMethod(postMethod); 
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception("页面无法访问");
			}        
			byte[] responseBody = postMethod.getResponseBody();
			
			String htmlValue = new String(responseBody);
			
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header locationHeader = postMethod.getResponseHeader("location");
					String location = null;
					if (locationHeader != null) {
					location = locationHeader.getValue();
					} else {
					}
			}
			
		return htmlValue;
		
		} catch (Exception e) { 
			throw new Exception("页面无法访问");
		}     	
	}
}
