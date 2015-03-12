package com.study.utility;

import java.util.List;


public class ActionListValidator {

	private String name;
	
	private List list;
	
	public static ActionListValidator create(String name, List list) {
		
		ActionListValidator validator = new ActionListValidator();
		validator.name = name;
		validator.list = list;
		return validator;
	}
	
	public ActionListValidator noNull() throws Exception {
		
		//检查是否为空
		if (list == null) {
			throw new Exception("参数集【" + name + "】为空！" );
		}
        
        return this;
	}
	
	public ActionListValidator maxSize(Integer maxSize) throws Exception {
		
        //检查长度是否超界
        if (this.list != null && list.size() > maxSize) {
        	throw new Exception(this.name + "的记录数不可超过" + maxSize + "！");
        }
        
        return this;
	}
	
	public ActionListValidator minSize(Integer minSize) throws Exception {
		
        //检查长度是否超界
        if (this.list != null && list.size() < minSize) {
        	throw new Exception(this.name + "的记录数不可少于" + minSize + "！");
        }
        
        return this;
	}
	
	
}
