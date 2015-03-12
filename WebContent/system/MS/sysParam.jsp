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
		<title>系统设置-教师平台</title>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/sysParam.js"></script>
	</head>
	
	<body>
		<div class="box clearfix">
		
			<!-- Loading图片  -->
			<div id="loading" class="layoutLoading"><img src="<%=request.getContextPath()%>/system/public/image/loading.gif" alt="" /></div>
			
			<!-- 菜单区域  -->
			<jsp:include page="menu.jsp" flush="true"></jsp:include>
					
			<div id="functionArea" class="right_content fl">
				<div class="content_box">
				
					<!-- Header区域  -->
					<jsp:include page="header.jsp" flush="true"></jsp:include>				

					<div id="pageDataList">
					
						<!-- 查询区域  -->
						<div class="condition mart50 clearfix" style="clear: both;">
							<input id="btnQuery" class="option_btn fr" type="button" value="刷&nbsp;&nbsp;&nbsp;新" />
						</div>
	
						<!-- 数据表格  -->
						<table class="right_table mart12">
							<thead>
								<tr><th width="50px">编号</th><th width="120px">名称</th><th>内容</th><th width="100px" class="fillet">操作</th></tr>
							</thead>
							<tbody id="dataListBody"></tbody>
						</table>
						
						<br/>
						<br/>
						
						<!-- 分页区域  -->
						<jsp:include page="paginateResult.jsp" flush="true"></jsp:include>
					</div>

					<!-- 编辑区域  -->
					<div id="pageEditData" style="display: none;"><jsp:include page="sysParamEdit.jsp" flush="true"></jsp:include></div>
				</div>
			</div>
		</div>
	</body>
</html>
