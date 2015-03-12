<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="left_banner fl pr">
	<div class="logo"><img src="<%=request.getContextPath()%>/system/public/image/logo-146x66.jpg" /></div>
	<ul class="menu_list">
	    <li id="menuQuestion"><a class="menu menu4" href="<%=request.getContextPath() %>/MS-question">题库管理</a></li>
	    <li id="menuTestPaper"><a class="menu menu5" href="<%=request.getContextPath() %>/MS-testPaper">试卷管理</a></li>
	    <li id="menuSysParam"><a class="menu menu10" href="<%=request.getContextPath() %>/MS-sysParam">系统设置</a></li>
	</ul>
</div>
<script type="text/javascript">

showMenu();

function showMenu() {
	
	var actionGetCurManager = PageUtility.getContextPath() + "/Manager/getCurManager.action";
	var manager = PageUtility.ajaxData(actionGetCurManager, null);
	
	var sysAccessList = manager.sysAccess.split(";");
	
	if (checkAccess('0', sysAccessList)) {
		$("#menuNotice").show();
	} else {
		$("#menuNotice").hide();
	}
	
	if (checkAccess('1', sysAccessList)) {
		$("#menuArticle").show();
	} else {
		$("#menuArticle").hide();
	}
	
	if (checkAccess('2', sysAccessList)) {
		$("#menuCourse").show();
	} else {
		$("#menuCourse").hide();
	}
	
	if (checkAccess('3', sysAccessList)) {
		$("#menuQuestion").show();
	} else {
		$("#menuQuestion").hide();
	}
	
	if (checkAccess('4', sysAccessList)) {
		$("#menuTestPaper").show();
	} else {
		$("#menuTestPaper").hide();
	}		
	
	if (checkAccess('5', sysAccessList)) {
		$("#menuExam").show();
	} else {
		$("#menuExam").hide();
	}	
	
	if (checkAccess('6', sysAccessList)) {
		$("#menuReportExam").show();
	} else {
		$("#menuReportExam").hide();
	}		
	
	if (checkAccess('8', sysAccessList)) {
		$("#menuManager").show();
	} else {
		$("#menuManager").hide();
	}
	
	if (checkAccess('7', sysAccessList)) {
		$("#menuExamServer").show();
	} else {
		$("#menuExamServer").hide();
	}
	
	if (checkAccess('9', sysAccessList)) {
		$("#menuSysParam").show();
	} else {
		$("#menuSysParam").hide();
	}			
}

function checkAccess(sysAccess, sysAccessList) {

	for (var i=0; i<sysAccessList.length; i++) {
		if (sysAccessList[i] == sysAccess) {
			return true;
		}
	}
	
	return false;
}
</script>

