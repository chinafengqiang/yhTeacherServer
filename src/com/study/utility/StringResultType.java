package com.study.utility;

import  java.io.PrintWriter; 

import  javax.servlet.http.HttpServletResponse; 

import  org.apache.struts2.dispatcher.StrutsResultSupport; 

import  com.opensymphony.xwork2.ActionInvocation; 

/** 
 * result type for output string in action 
 *  
 * @author songwei,yaolei <b>Example:</b> 
 *  
 * <pre> 
 * <!-- START SNIPPET: example --> 
 * <result name="success" type="string"> 
 *   <param name="stringName">stringName</param> 
 * </result> 
 * <!-- END SNIPPET: example --> 
 * </pre> 
 *  
 */ 
public   class  StringResultType  extends  StrutsResultSupport { 
     private   static   final   long  serialVersionUID = 1L; 
     private  String contentTypeName; 
     private  String stringName =  "" ; 

     public  StringResultType() { 
         super (); 
    } 

     public  StringResultType(String location) { 
         super (location); 
    } 

     protected   void  doExecute(String finalLocation, ActionInvocation invocation) 
             throws  Exception { 
        HttpServletResponse response = (HttpServletResponse) invocation 
                .getInvocationContext().get(HTTP_RESPONSE); 
         // String contentType = (String) 
         // invocation.getStack().findValue(conditionalParse(contentTypeName, 
         // invocation)); 
        String contentType = conditionalParse(contentTypeName, invocation); 
         if  (contentType ==  null ) { 
            contentType =  "text/plain; charset=UTF-8" ; 
        } 
        response.setContentType(contentType); 
        PrintWriter out = response.getWriter(); 
         // String result = conditionalParse(stringName, invocation); 
        String result = (String) invocation.getStack().findValue(stringName); 
        out.println(result); 
        out.flush(); 
        out.close(); 

    } 

     public  String getContentTypeName() { 
         return  contentTypeName; 
    } 

     public   void  setContentTypeName(String contentTypeName) { 
         this .contentTypeName = contentTypeName; 
    } 

     public  String getStringName() { 
         return  stringName; 
    } 

     public   void  setStringName(String stringName) { 
         this .stringName = stringName; 
    } 

} 

