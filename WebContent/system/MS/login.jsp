<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="com.study.service.impl.SystemServiceImpl"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>登录-教师平台</title>
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
        	<p class="explain">您即将进入教师平台……</p>
            <div class="login_box">
            	<table>
                	<tr><th width="70px">教&nbsp;&nbsp;&nbsp;&nbsp;师：</th><td><input id="txtUserName" class="input_text input183x22 unitID" type="text" /></td></tr>
                    <tr><th>密&nbsp;&nbsp;&nbsp;&nbsp;码：</th><td><input id="txtUserPwd" type="password" class="input_text input183x22" /></td></tr>
                    <tr><th>验&nbsp;证&nbsp;码：</th><td><input id="txtCheckCode" type="text" class="input_text input183x22" />
                    <a href="javascript:changeImg();"><img align="middle" src="<%=request.getContextPath() %>/imageServlet" border="0" id="verifyCodeImg"></a>
                    </td></tr>
                    <tr><td colspan="2"><input class="option_btn login_btn login" id="btnLogin" type="button" value="登&nbsp;&nbsp;&nbsp;录" />
                    <input id="btnClose" class="option_btn login_btnClose" type="button" value="关&nbsp;&nbsp;&nbsp;闭" /></td></tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
