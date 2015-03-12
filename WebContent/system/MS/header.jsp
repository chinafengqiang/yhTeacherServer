<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="content_top clearfix">
	<p class="fr"><span id="managerActualName"></span>，您已进入<span class="city" id="siteName"></span>站点的教师平台</p>
</div>
<div class="content_title clearfix">
	<p class="explain fl" id="functionName"><span></span></p>
</div>
<script type="text/javascript">

showLoginResult();

function showLoginResult() {
	
	var actionGetCurOrgan = PageUtility.getContextPath() + "/Manager/getCurManager.action";
	var manager = PageUtility.ajaxData(actionGetCurOrgan, null);
	
	$("#managerActualName").text(manager.actualName);
	
	var params = {sysParamName:"SiteName"};
	var actionGetSysParam = PageUtility.getContextPath() + "/System/getSysParamValue.action";
	var siteName = PageUtility.ajaxData(actionGetSysParam, params);
	
	$("#siteName").text(siteName);
}

function modifyPassword() {
	
	var w = window;
	while (true) {
		if(w.frameElement && w.frameElement.tagName=="IFRAME"){ 
			w = window.parent;
		} else {
			break;
		}
	}
	
	w.location.href = PageUtility.getContextPath() + "/MS-modifyPassword";
	
	return false;
}

function quit() {

	var w = window;
	while (true) {
		if(w.frameElement && w.frameElement.tagName=="IFRAME"){ 
			w = window.parent;
		} else {
			break;
		}
	}
	
	w.location.href = PageUtility.getContextPath() + "/US-index";
	
	return false;
}

</script>