package com.study.utility;


public class ActionIntegerValidator {

	private String fieldName;
	
	private Integer fieldValue;
	
	public static ActionIntegerValidator create(String fieldName, Integer fieldValue) {
		
		ActionIntegerValidator validator = new ActionIntegerValidator();
		validator.fieldName = fieldName;
		validator.fieldValue = fieldValue;
		return validator;
	}
	
	public ActionIntegerValidator noNull() throws Exception {
		
		//检查是否已输入
		if (fieldValue == null) {
			throw new Exception("请输入" + this.fieldName);
		}
        
        return this;
	}
	
	public ActionIntegerValidator maxValue(Integer maxValue) throws Exception {
		
        //检查长度是否超界
        if (this.fieldValue != null && fieldValue > maxValue) {
        	throw new Exception(this.fieldName + "的值不可超过" + maxValue + "！");
        }
        
        return this;
	}
	
	public ActionIntegerValidator minValue(Integer minValue) throws Exception {
		
        //检查长度是否超界
        if (this.fieldValue != null && fieldValue < minValue) {
        	throw new Exception(this.fieldName + "的值不可低于" + minValue + "！");
        }
        
        return this;
	}
	
	public ActionIntegerValidator positiveInteger(Boolean bVerify) throws Exception {
		
        //检查是否是正整数（包含零）
		if (bVerify) {
        	if (fieldValue < 0) {
        		throw new Exception(fieldName + "应为大于等于零的整数");
        	}
		}

        return this;
	}
	
	public ActionIntegerValidator positiveInteger() throws Exception {
		
		return positiveInteger(true);

	}
}
