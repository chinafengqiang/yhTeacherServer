package com.study.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * URL网络解析
 * @author Administrator
 */
public class HttpUtil {
	
  /**
   * get方法传递参数
 * @param path
 * @return
 */
public static String getDataFromUrl(String path){
	
		URL url = null;

		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.setReadTimeout(5000);
		try {
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "text/html");
			conn.setRequestProperty("contentType", "UTF-8");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = "";
//		byte[] data = null;
//		try {
//			data = StreamTool.readInputStream(inputStream);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
        StringBuffer buffer = new StringBuffer();  
        String line = "";  
        while ((line = in.readLine()) != null){  
          buffer.append(line);  
        } 
        str = buffer.toString();  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return str;
  }
  
  
  /**
   * psot方法传弟参数
 * @param path
 * @param params
 * @return
 */
public static byte[] getDataFromUrl(String path, String params){
		
	  	byte[] data = params.getBytes();
	  	byte[] returndata = null;
		URL url = null;
		try {
			url = new URL(path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.setDoOutput(true); // 允许对外发送请求参数
		conn.setUseCaches(false); // 不进行缓存
		conn.setConnectTimeout(5 * 1000);
		
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg,image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/x-ms-xbap, application/vnd.ms-excel,  */*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("Connection", "Keep-Alive");
		
		// 发送参数
		DataOutputStream outStream;
		try {
			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(data); // 把参数发送出去
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (conn.getResponseCode() == 200) {
				try {
					returndata = StreamTool.readInputStream(conn.getInputStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return returndata;
}
}
