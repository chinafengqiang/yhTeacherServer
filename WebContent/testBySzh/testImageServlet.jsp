<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试验证码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript">
    function refresh(obj) {
        obj.src = "imageServlet?"+Math.random();
    }
    </script>
  </head>
  
  <body>
     测试验证码<br>
     <label>输入验证码</label><br/>
     <input type="text" name="randomCode"/><img title="点击更换" onclick="javascript:refresh(this);" src="imageServlet"><br/>
  </body>
</html>
