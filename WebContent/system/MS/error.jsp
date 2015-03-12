<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>错误-教师平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=request.getContextPath() %>/system/public/css/globel.css" type="text/css" />
	<link rel="stylesheet" href="<%=request.getContextPath() %>/system/public/css/pagestyle.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/system/public/js/PageUtility.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/login.js"></script>
</head>
<body>
<div class="box clearfix">
    	<div class="left_banner fl pr">
        	<div class="logo"><img src="<%=request.getContextPath() %>/system/public/image/logo-146x66.jpg" /></div>
        </div>
        <div class="right_content fl">
            <div style="margin-top:200px; text-align:left; margin-left:300px;">
            	<p style="font-size:20px;">当您看到此页面时，有以下几种可能......</p>
            	<br/>
            	<p>1.您登录后长时间未操作，需要重新登录</p>
            	<br/>
            	<p>2.您未曾登录，就试图进入系统模块页面，这是不允许的，需要登录后操作</p>
            	<br/>
            	<p>3.您可能试图进入未授权的系统模块页面，这是不允许的</p>
            	<br/>
            	<p>4.系统可能已经重新启动，您需要重新登录</p>
            	<br/>
            	<p>5.系统可能出现故障，请联系系统管理员解决</p>
            </div>
        </div>
    </div>
</body>
</html>
