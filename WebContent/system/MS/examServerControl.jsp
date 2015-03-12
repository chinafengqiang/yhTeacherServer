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
		<title>考试服务器-教师平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" href="<%=request.getContextPath()%>/system/public/css/globel.css" type="text/css" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/system/public/css/pagestyle.css"	type="text/css" />

		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.ocupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.bpopup.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/public/js/PageUtility.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/examServerControl.js"></script>
	</head>
	
	<body style="background:#fafaf2; height:1000px;">
	
		<div id="functionArea">
			<div id="subPage" style="height:1200px; display:none;"><iframe id="iframeSubPage" width="100%" height="100%" frameborder="0" scrolling="auto" ></iframe></div>
			<div id="mainPage" class="content_box">

				<!-- Loading图片  -->
				<div id="loading" class="layoutLoading"><img src="<%=request.getContextPath()%>/system/public/image/loading.gif" alt="" /></div>
			
				<!-- Header区域  -->
				<jsp:include page="header.jsp" flush="true"></jsp:include>			

				<div id="pageDataList">
					
					<!-- 查询区域  -->
					<div class="condition mart50 clearfix">
                		<input id="btnQuery" class="option_btn fr" type="button" value="刷&nbsp;&nbsp;&nbsp;新" />
                	</div>

					<!-- 数据表格  -->
					<table class="right_table mart12">
						<thead>
						<tr><th>考试</th><th width="120px">状态</th><th width="100px">总人数</th><th width="100px">待考</th><th width="100px">在考</th><th width="100px">已考</th></tr>
						</thead>
						<tbody id="dataListBody"></tbody>
					</table>
					
					<br/>
					<br/>
					
				</div>
				
				<!-- 返回按钮  -->
				<div class="txtc" style="clear:both;"><input class="option_btn mart45" type="button" value="返&nbsp;&nbsp;&nbsp;回" onclick="window.parent.backFromSubPage()" /></div>
			</div>
		</div>
	</body>
</html>
