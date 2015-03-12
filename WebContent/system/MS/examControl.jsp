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
		<title>考试控制管理-单位平台</title>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/examControl.js"></script>
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
					<div class="condition mart50 clearfix" style="clear: both;">
						<input id="btnQuery" class="option_btn fr" type="button" value="刷&nbsp;&nbsp;&nbsp;新" />
						<p class="fr marr10 marl10"><span class="font12grey marr10">单位：</span><input class="selectShort143x24" type="text" name="qpActualOrgan" id="qpActualOrgan"></p>
						<p class="fr marr10 marl10"><span class="font12grey marr10">姓名：</span><input class="input_text input120x22" type="text" name="qpActualName" id="qpActualName" /></p>
					</div>

					<!-- 数据表格  -->
					<table class="right_table mart12">
						<thead>
							<tr><th width="50px">编号</th><th>单位名称</th><th width="80px">姓名</th><th width="200px">实际开考时间</th><th width="60px">成绩</th><th width="60px">通过</th><th width="60px">状态</th><th width="200px" class="fillet">操作</th></tr>
						</thead>
						<tbody id="dataListBody"></tbody>
					</table>
					
					<br/>
					<br/>
					
					<!-- 按钮区域  -->
					<div class="fl page">
						<input id="btnMake" class="option_btn_g marr22" type="button" value="安排考生">
						<!-- <input id="btnImportFromFile" class="option_btn_g" type="button" value="导入答卷"> -->
					</div>					
					
					<!-- 分页区域  -->
					<jsp:include page="paginateResult.jsp" flush="true"></jsp:include>
				</div>
				
				<!-- 返回按钮  -->
				<div class="txtc" style="clear:both;"><input class="option_btn mart45" type="button" value="返&nbsp;&nbsp;&nbsp;回" onclick="window.parent.backFromSubPage()" /></div>
			</div>
		</div>
	</body>
</html>
