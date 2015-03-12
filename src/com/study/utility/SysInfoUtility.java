package com.study.utility;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;

public class SysInfoUtility {

	public static String getCPUCode() {
		
		String rt = "";
		
		for (int i=1;i<=17;i++) {
			String value = getCPUCodeIndex(i);
			if (value != null) {
				rt = rt + value;
			}
		}
		
		return rt;
	}
	
	private static String getCPUCodeIndex(Integer index) {
		
		try {
		   JNative jnative = new JNative("SysInfoUtility.dll","GetCPUCode" + index);
		   jnative.setRetVal(Type.INT); 
		   jnative.invoke();
		   char s = (char)jnative.getRetValAsInt(); 
		   return String.valueOf(s);  
		}catch(Exception e){
		   return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(getCPUCode());
	}

}
