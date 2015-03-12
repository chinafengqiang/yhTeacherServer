package com.study.utility;


public class ActionLongValidator {

	private String fieldName;
	
	private Long fieldValue;
	
	public static ActionLongValidator create(String fieldName, Long fieldValue) {
		
		ActionLongValidator validator = new ActionLongValidator();
		validator.fieldName = fieldName;
		validator.fieldValue = fieldValue;
		return validator;
	}
	
	public ActionLongValidator noNull() throws Exception {
		
		//检查是否已输入
		if (fieldValue == null) {
			throw new Exception("请输入" + this.fieldName);
		}
        
        return this;
	}
	
	public ActionLongValidator maxValue(Long maxValue) throws Exception {
		
        //检查长度是否超界
        if (this.fieldValue != null && fieldValue > maxValue) {
        	throw new Exception(this.fieldName + "的值不可超过" + maxValue + "！");
        }
        
        return this;
	}
	
	public ActionLongValidator minValue(Long minValue) throws Exception {
		
        //检查长度是否超界
        if (this.fieldValue != null && fieldValue < minValue) {
        	throw new Exception(this.fieldName + "的值不可低于" + minValue + "！");
        }
        
        return this;
	}
	
	public ActionLongValidator positiveInteger(Boolean bVerify) throws Exception {
		
        //检查是否是正整数（包含零）
		if (bVerify) {
        	if (fieldValue < 0) {
        		throw new Exception(fieldName + "应为大于等于零的整数");
        	}
		}

        return this;
	}
	
	public ActionLongValidator positiveInteger() throws Exception {
		
		return positiveInteger(true);

	}
}
