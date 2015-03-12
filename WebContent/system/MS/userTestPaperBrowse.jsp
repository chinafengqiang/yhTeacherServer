<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>预览试卷-教师平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/public/js/PageUtility.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/userTestPaperBrowse.js"></script>
		<style type="text/css">
			body {font-family:宋体; width:1000px; margin:0px auto; text-align:center}
			
			#TestPaperArea {margin-top:30px;}
			#TestPaperArea .examName {font-size:30; font-weight:bold; color:#990000;}		
			#TestPaperArea .examSummary {font-size:14; color:#666666; margin-top:15px}
			#TestPaperArea .actionArea {margin-top:30px;}
			#TestPaperArea .actionArea .actualName{font-family:黑体; font-size:28; color:#666666}
			#TestPaperArea .actionArea .totalScore{margin-left:33px; font-size:14; color:#666666}
			#TestPaperArea .actionArea .questionCount{font-size:14; color:#666666}
			#TestPaperArea .actionArea .time{margin-left:109px; margin-right:109px; font-family:黑体; font-size:38; color:#666666}
			#TestPaperArea .actionArea .linkButton{margin-left:10px; font-size:14;}
			#TestPaperArea .actionArea .linkButton a:link {color: black;}
			#TestPaperArea .actionArea .linkButton a:hover {color: red;}
			#TestPaperArea .actionArea .button {margin-left:20px; width:161px; height:39px; font-family:黑体; font-size:24; background-color: #FF0000; color: white; border:0px; cursor: pointer}
			
			#questionArea {width:950px; text-align:left; margin-top:20px; margin-bottom:100px;}
			#questionArea .questionTypeName {margin-top: 30px; font-size:14; font-weight:bold;}
			#questionArea .question {margin-top: 20px; margin-left:30px; font-size:14; border:1px solid #F3F3F3; padding:10px;}
			#questionArea .questionDoing {margin-top: 20px; margin-left:30px; font-size:14; border:1px solid red; padding:10px;}
			#questionArea .questionContent {white-space:normal;word-break:break-all;overflow:hidden; }
			#questionArea .questionOptions {margin-top:15px; padding-left:20px;}
			#questionArea .questionOptions span{font-weight:bold;}
			#questionArea .questionAnswer {margin-top:15px; padding-left:20px; color:red}			
		</style>		
	</head>
	
	<body>
		<!-- 考试卷面模块 -->
		<div id="TestPaperArea">
			<div class="examName">2014年湖南省公务员法律学习工作正式考试</div>
			<div class="actionArea">
				<span class="actualName">张小龙</span>
				<span class="totalScore">得分：100分</span>
			</div>
			<div id="questionArea">
				<div class="questionTypeName">一、判断题：(每题5分，10道题）</div>
				<div id="question-1" class="question">
					<div class="questionContent">1、我国的第一部宪法是1949年诞生的吗？</div>
					<div class="questionOptions">
						<span>答：</span>
						<label><input name="questionAnswer-1" type="radio" value="" />对</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input name="questionAnswer-1" type="radio" value="" />错 </label>
					</div>
					<div class="questionAnswer">标准答案：对</div>
				</div>
				<div class="questionTypeName">二、单选题：(每题5分，10道题）</div>
				<div id="question-2" class="questionDoing">
					<div class="questionContent">1、我国的第一部宪法是1949年诞生的吗？</div>
					<div class="questionOptions">
						<span>答：</span>
						<label><input id="question1" name="question1" type="radio" value="" />A:国家地址</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input id="question1" name="question1" type="radio" value="" />B:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input id="question1" name="question1" type="radio" value="" />C:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input id="question1" name="question1" type="radio" value="" />D:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div class="questionAnswer">标准答案：A</div>
				</div>
				<div class="questionTypeName">三、多选题：(每题5分，10道题）</div>
				<div id="" class="question">
					<div class="questionContent">1、我国的第一部宪法是1949年诞生的吗？</div>
					<div class="questionOptions">
						<span>答：</span>
						<label><input name="Fruit" type="checkbox" value="" />A:国家地址</label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input name="Fruit" type="checkbox" value="" />B:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input name="Fruit" type="checkbox" value="" />C:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input name="Fruit" type="checkbox" value="" />D:国家地址 </label>&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
					<div class="questionAnswer">标准答案：CD</div>
				</div>
			</div>			
		</div>
	</body>
</html>
