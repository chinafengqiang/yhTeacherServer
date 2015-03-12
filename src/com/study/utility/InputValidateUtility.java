package com.study.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidateUtility {

	/**
	 * 校验电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean validateEMail(String email) {
		
		String checkEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";   
	    Pattern regexEmail = Pattern.compile(checkEmail);   
	    Matcher matcherEmail = regexEmail.matcher(email);
	    return matcherEmail.matches();
	}

	/**
	 * 校验手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean validateMobile(String mobile) {
		
		String checkMobile = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern regexMobile = Pattern.compile(checkMobile);   
        Matcher matcherMobile = regexMobile.matcher(mobile);   
        return matcherMobile.matches();
	}
	
	/**
	 * 校验子域名：www.sina.com
	 * @param domainName
	 * @return
	 */
	public static boolean validateDomainName(String domainName) {
		
		String checkDomainName = "^(\\w+\\.){1}(\\w+\\-?\\w+\\.){1,2}[a-zA-Z]{2,3}$";   
        Pattern regexDomainName = Pattern.compile(checkDomainName);   
        Matcher matcherDomainName = regexDomainName.matcher(domainName);   
        return matcherDomainName.matches();
	}
	
    public static void main(String[] args) {
        String domainName = "www.www.sina.com";
        System.out.println(validateDomainName(domainName));
    } 
}
