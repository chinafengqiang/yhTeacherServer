<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testJs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="jquery/jquery.min.js"></script>	
	<script type="text/javascript" src="jquery/jquery.json-2.4.js"></script>
	<script type="text/javascript" src="system/public/js/PageUtility.js"></script>
	
	<script type="text/javascript">
	
		function saveCookie(name, value, days) {
			$.cookie(key, examUserDataJson, {expires: 2}); 
	
	if (bAuto) {
		return;
	}
	
	if ($.cookie(key) == null) {
		alert("请允许浏览器的Cookie缓存数据，否则将无法暂存答卷！");
	} else {
		alert("您已成功保存您的答卷到本地缓存！");
	}
		}
		function loadCookie(name) {
		
		}
		function decryptAnswer(id, answer) {
		
			var answerArray = answer.split("");
			
			var remainder = id%9;
			for (i = 0; i < answerArray.length; i++) 
			{
				var code = answerArray[i].charCodeAt() - remainder;
				answerArray[i] = String.fromCharCode(code);
				remainder = remainder + 1;
			}
			
			return answerArray.join("");
		}
		function managerLogin() {
			try {
				var paramters = {
					"name": "admin",
					"password": "999999"
				};
				var action = PageUtility.getContextPath() + "/Manager/login.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功登录");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function deployExam() {
			try {
				var paramters = {
					"exam.id": 2
				};
				var action = PageUtility.getContextPath() + "/Exam/deployExam.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功部署考试");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function openExam() {
			try {
				var paramters = {
					"exam.id": 2
				};
				var action = PageUtility.getContextPath() + "/Exam/openExam.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功启动考试");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function closeExam() {
			try {
				var paramters = {
					"exam.id": 2
				};
				var action = PageUtility.getContextPath() + "/Exam/closeExam.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功停止考试");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function gatherExam() {
			try {
				var paramters = {
					"exam.id": 2
				};
				var action = PageUtility.getContextPath() + "/Exam/gatherExam.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功汇总成绩");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function destroyExam() {
			try {
				var paramters = {
					"exam.id": 2
				};
				var action = PageUtility.getContextPath() + "/Exam/destroyExam.action";
				PageUtility.ajaxAction(action, paramters);
				alert("成功清除部署");
			} catch (ex) {
				alert(ex);
			}
		};
		
		function getExamSummary() {
			try {
				var paramters = {
					"examId": 8
				};
				var action = PageUtility.getContextPath() + "/ExamServer/getExamSummary.action";
				var data = PageUtility.ajaxData(action, paramters);
				alert("此考试的考试服务器摘要:" + $.toJSON(data));
			} catch (ex) {
				alert(ex);
			}
		};
		
		function getExamServerSummary() {
			try {
				var paramters = {
					"examServer.id": 2
				};
				var action = PageUtility.getContextPath() + "/ExamServer/getExamServerSummary.action";
				var data = PageUtility.ajaxData(action, paramters);
				alert("考试服务器中所有考试的摘要:" + $.toJSON(data));
			} catch (ex) {
				alert(ex);
			}
		};
	</script>
  </head>
  
  <body>
    测试JS<br>
  <button onclick="alert(decryptAnswer(22, 'FH'));">解密答案</button>
  <button onclick="managerLogin();">教师登录</button>  
  <button onclick="deployExam();">部署考试</button>
  <button onclick="openExam();">启动考试</button>
  <button onclick="closeExam();">停止考试</button>
  <button onclick="gatherExam();">汇总成绩</button>
  <button onclick="destroyExam();">清除部署</button>
  <button onclick="getExamSummary();">获取考试的考试服务器摘要</button>
  <button onclick="getExamServerSummary();">获取考试服务器摘要列表</button>
  
  <button onclick="saveCookie('examUserData','',2);">保存cookie</button>
  </body>
</html>
