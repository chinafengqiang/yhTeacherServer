package com.study.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON生成器
 */
public class JSONBuilder {

	/**
	 * 定义并初始化Map，用于存储要转换的对象
	 */
	private Map<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * 获取转换时用到的默认配置（解决了日期问题）
	 * @return
	 */
	public static JsonConfig getDefaultCfg() {
		
		JsonConfig cfg = new JsonConfig();
		
		//注册Json值处理器
	    cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
		    
	    	//日期格式
	    	private final String format="yyyy-MM-dd hh:mm:ss";
		    
	    	public Object processObjectValue(String key, Object value, JsonConfig arg2)
		    {
		    	if(value==null)
		    		return "";
		    	
		    	if (value instanceof Date) {
		    		String str = new SimpleDateFormat(format).format((Date) value);
		    		return str;
		    	}
		    	return value.toString();
		    }

		    public Object processArrayValue(Object value, JsonConfig arg1)
		    {
		    	return null;
		    }
		    
	    });
		
	    return cfg;
	}
	
	/**
	 * 创建工具实例
	 * @return
	 */
	public static JSONBuilder create() {
		
		return new JSONBuilder();
	}
	
	/**
	 * 创建工具实例
	 * @paramer map 对象集合 
	 * @return
	 */
	public static JSONBuilder create(Map map) {
		
		JSONBuilder rt = new JSONBuilder();
		rt.map = map;
		return rt;
	}
	
	/**
	 * 添加对象
	 * @param name 对象名称
	 * @param value 对象值
	 * @return
	 */
	public JSONBuilder add(String name, Object value) {
		map.put(name, value);
		return this;
	}
	
	/**
	 * 转换成JSON字符串
	 * @return
	 */
	public String toJsonString() {
		   
		JSONObject jsonObject = JSONObject.fromObject(map, getDefaultCfg());
		
		return jsonObject.toString();
	}
	
	/**
	 * 按新的值转换器转换成Josn字符串
	 * @param cfg 值转换器
	 * @return
	 */
	public String toJsonString(JsonConfig cfg) {
		   
		JSONObject jsonObject = JSONObject.fromObject(map, cfg);
		
		return jsonObject.toString();
	}
}
