package com.study.utility;

import java.util.ArrayList;
import java.util.List;

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

public class HttpClientPostUtility {

	private String url;
	
	private String characterSet;
	
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	private void setUrl(String url) {
		this.url = url;
	}

	public static HttpClientPostUtility post(String url){
		
		HttpClientPostUtility htmlClientUtil = new HttpClientPostUtility();
		htmlClientUtil.setUrl(url);
		htmlClientUtil.setCharacterSet("UTF-8");
		return htmlClientUtil;
	}
	
	public HttpClientPostUtility setCharacterSet(String characterSet) {
		
		if (characterSet != null) {
			this.characterSet = characterSet;
		}
		
		return this;
	}
	
	public HttpClientPostUtility setParameter(String name, String value) throws Exception {
	
		if (StringUtils.isBlank(name)) {
			throw new Exception("参数名不可为空！");
		}
		
		NameValuePair nameValuePair  = new BasicNameValuePair(name, value);
		params.add(nameValuePair);

		return this;
	}
	
	public String html() throws Exception  {
		
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
           	HttpPost httpPost = new HttpPost(url);  
           	HttpEntity httpEntity = new UrlEncodedFormEntity(params, characterSet);  
           	httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
           	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
           	httpPost.setEntity(httpEntity);  

            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                return EntityUtils.toString(httpResponse.getEntity());  
            } else {
            	throw new Exception("提交数据出错！");
            }
        } catch (Exception ex) {
        	throw new Exception("网络错误！");
        }      	
	}
}
