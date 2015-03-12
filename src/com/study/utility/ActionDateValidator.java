package com.study.utility;

import java.util.Date;

public class ActionDateValidator {

	private String fieldName;
	
	private Date fieldValue;
	
	public static ActionDateValidator create(String fieldName, Date fieldValue) {
		
		ActionDateValidator validator = new ActionDateValidator();
		validator.fieldName = fieldName;
		validator.fieldValue = fieldValue;
		return validator;
	}
	
	public ActionDateValidator noNull() throws Exception {
		
		//检查是否已输入
		if (fieldValue == null) {
			throw new Exception("请输入" + this.fieldName);
		}
		
        return this;
	}
	
	public ActionDateValidator minDate(Date date) throws Exception {
		
        //检查是否迟了
        if (this.fieldValue != null && this.fieldValue.before(date)) {
       		throw new Exception(this.fieldName + "应在日期[" + date.toLocaleString() + "]之后");
        }
        
        return this;
	}
	
	public ActionDateValidator maxDate(Date date) throws Exception {
		
        //检查是否早了
        if (this.fieldValue != null && this.fieldValue.after(date)) {
        	throw new Exception(this.fieldName + "应在日期[" + date.toLocaleString() + "]之前");
        }
        
        return this;
	}
}
