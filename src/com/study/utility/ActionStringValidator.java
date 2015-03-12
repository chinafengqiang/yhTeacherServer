package com.study.utility;

import org.apache.commons.lang.StringUtils;

public class ActionStringValidator {

	private String fieldName;
	
	private String fieldValue;
	
	public static ActionStringValidator create(String fieldName, String fieldValue) {
		
		ActionStringValidator validator = new ActionStringValidator();
		validator.fieldName = fieldName;
		validator.fieldValue = fieldValue;
		return validator;
	}
	
	public ActionStringValidator noNull() throws Exception {
		
		//检查是否已输入
		if (fieldValue == null) {
			throw new Exception("请输入" + this.fieldName);
		}
		
		//检查是否为空字符串
        if (StringUtils.isBlank(fieldValue.toString())){
       		throw new Exception("请输入" + this.fieldName);
        }
        
        return this;
	}
	
	public ActionStringValidator maxLength(Integer maxLength) throws Exception {
		
        //检查长度是否超界
        if (this.fieldValue != null && maxLength > 0) {
        	if (this.fieldValue.getBytes("GB2312").length > maxLength) {
        		throw new Exception(this.fieldName + "的长度不可超过" + maxLength + "个字符");
        	}
        }
        
        return this;
	}
	
	public ActionStringValidator positiveInteger(Boolean bVerify) throws Exception {
		
        //检查是否是正整数（包含零）
		if (bVerify) {
	        try {
	        	Integer temp = Integer.parseInt(fieldValue);
	        	if (temp < 0) {
	        		throw new Exception(fieldName + "应为大于等于零的整数");
	        	}
	        } catch (Exception ex) {
	        	throw new Exception(fieldName + "应为大于等于零的整数");
	        }
		}

        return this;
	}
	
	public ActionStringValidator positiveInteger() throws Exception {
		
		return positiveInteger(true);

	}
}
