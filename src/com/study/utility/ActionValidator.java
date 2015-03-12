package com.study.utility;

import java.util.Date;
import java.util.List;


public class ActionValidator {

	public static ActionStringValidator create(String fieldName, String fieldValue) {
		
		return ActionStringValidator.create(fieldName, fieldValue);
	}
	
	public static ActionDateValidator create(String fieldName, Date fieldValue) {
		
		return ActionDateValidator.create(fieldName, fieldValue);
	}
	
	public static ActionLongValidator create(String fieldName, Long fieldValue) {
		
		return ActionLongValidator.create(fieldName, fieldValue);
	}
	
	public static ActionIntegerValidator create(String fieldName, Integer fieldValue) {
		
		return ActionIntegerValidator.create(fieldName, fieldValue);
	}
	
	public static ActionObjectValidator create(String name, Object object) {
		
		return ActionObjectValidator.create(name, object);
	}
	
	public static ActionListValidator create(String name, List list) {
		
		return ActionListValidator.create(name, list);
	}	
}
