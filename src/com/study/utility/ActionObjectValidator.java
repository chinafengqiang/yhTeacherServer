package com.study.utility;


public class ActionObjectValidator {

	private String name;
	
	private Object object;
	
	public static ActionObjectValidator create(String name, Object object) {
		
		ActionObjectValidator validator = new ActionObjectValidator();
		validator.name = name;
		validator.object = object;
		return validator;
	}
	
	public ActionObjectValidator noNull() throws Exception {
		
		//检查是否为空
		if (object == null) {
			throw new Exception("参数集【" + name + "】为空！" );
		}
        
        return this;
	}
}
