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
		<title>试卷题目-教师平台</title>
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/system/MS/js/testPaperQuestion.js"></script>
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
						<p class="fr marr10 marl10"><span class="font12grey marr10">类型：</span><select class="selectShort143x24" name="qpQuestionType" id="qpQuestionType"></select></p>
						<p class="fr marr10 marl10"><span class="font12grey marr10">难度：</span><select class="selectShort143x24" name="qpDifficulty" id="qpDifficulty"></select></p>
						<p class="fr marr10 marl10"><span class="font12grey marr10">知识点：</span><input class="input_text input120x22" type="text" name="qpKen" id="qpKen" /></p>
						<p class="fr marr10 marl10"><span class="font12grey marr10">名称：</span><input class="input_text input120x22" type="text" name="qpName" id="qpName" /></p>
					</div>
					
					<div class="mar-ie7" style="clear:both;">
                	<div class="course_dif mart12 pr fl">
                	<h3 class="course_sort">试卷概况</h3>
	                    <div class="turn_title">
	                    	<a class="turn_left fl" onclick="btnDecrease()" href="javascript:void(0);">向左</a>
	                        <a class="turn_right fr" onclick="btnIncrease()" href="javascript:void(0);">向右</a>
	                    </div>
	                    <div class="view_part"><div class="all_part">
	                    <ul class="turn_list turn_content1 font12">
	                    	<li><div class="turn_control">
	                        <span class="turn_t_f">第<span id="qpSeries"></span>/<span id="totalSeries"></span> 套</span>
	                        </div></li>
	                    	<li class="marl25">单选题：<span id="singleSelectedNumber"></span>/<span id="singleTotalNumber"></span>道</li>
	                        <li class="marl25">多选题：<span id="multiSelectedNumber"></span>/<span id="multiTotalNumber"></span>道</li>
	                        <li class="marl25">判断题：<span id="judgeSelectedNumber"></span>/<span id="judgeTotalNumber"></span>道</li>
	                        <li class="marl10 marb20"><b><span>试卷总分：</span><span id="score"></span>/<span id="totalScore"></span>分</b></li>
	                        <li class="clearfix" style="clear:both"><input id="btnBrowseTestPaper" type="button" class="option_btn_g fl" value="预览试卷" />
	                        <input id="btnExportTestPaperToWord" type="button" class="option_btn_g fr marr5" value="导出Word" /></li>
	                    </ul>
	                    </div></div>
               		 </div>
					
					<div class="choice_table ct1 pr mar_ie7_2">
             	    <div class="open_layer" id="questionData" style="display: none;">
                    	<a href="javascript:void(0);" onclick="btnHidden()" class="close_s">关闭</a>
                        <table class="open_table" style="clear:both;">
                        	<tr><th>题库：</th><td><select class="select input168x18" name="edQuestionCategory" id="edQuestionCategory"></select></td></tr>
                            <tr><th>题型：</th><td><select class="select input168x18" name="edQType" id="edQType"></select></td></tr>
                            <tr><th>难度：</th><td><select class="select input168x18" id="edDifficulty" ></select></td></tr>
                            <tr><th>分数：</th><td><input type="text" class="input_text input168x18" id="edScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td></tr>
                            <tr><th>数量：</th><td><input type="text" class="input_text input168x18" id="edNumber" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /></td></tr>
                        </table>
                        <p class="txtc mart24"><input type="button" onclick="btnSigleQuestion()" class="option_btn_g2 marr30 marl15" value="提取单套" />
                        <input type="button" class="option_btn_g2 marr10" onclick="btnAllQuestion()" value="提取全套" /></p>
                    </div>

					<!-- 数据表格  -->
					<table class="right_table mart12">
						<thead>
							<tr><th width="60px">编号</th><th width="60px">类型</th><th width="60px">题号</th><th width="50px">分数</th><th width="80px" class="fillet">操作</th></tr>
						</thead>
						<tbody id="dataListBody"></tbody>
					</table>
					
					<br/>
					<br/>
					
					<!-- 按钮区域  -->
					<div class="fl page">
                    	<input id="btnSelectQuestion" class="option_btn_g marr10 fl" type="button" value="手动提取">
						<input id="btnAutoQuestion" class="option_btn_g marr10 fl" type="button" value="自动提取">
						<input id="btnExportToFile" class="option_btn_g marr10 fl" type="button" value="导出题目">
						<input id="btnQImportFromFile" class="option_btn_g" type="button" value="导入题目">
                    </div>
					
					<!-- 分页区域  -->
					<jsp:include page="../MS/paginateResult.jsp" flush="true"></jsp:include>
					<br/>
					<br/>
					<div class="txtc" style="clear:both;">
	                   	<input id="btnValidateTestPaper" class="option_btn mart75 marr45" type="button" value="校验试卷">
	                   	<input class="option_btn mart75 marr45" type="button" value="返&nbsp;&nbsp;&nbsp;回" onclick="window.parent.backFromSubPage()" />
	                </div>
				</div>
				
				<!-- 返回按钮  -->
			</div>
			</div>
			<div id="pageEditQuestionData"  style="display: none;"><jsp:include page="testPaperQuestionEdit.jsp" flush="true"></jsp:include></div>
			</div>
		</div>
	</body>
</html>
