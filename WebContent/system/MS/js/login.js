$(function() {

	//登录 
	$("#btnLogin").click(function(){ 
	if(checkUserName() && checkUserPwd() && checkCheckCode()) { 
			var data = { 
					name: $('#txtUserName').val(), 
					password: $('#txtUserPwd').val(), 
					randomCodeKey: $('#txtCheckCode').val() 
					}; 
					
			//提交数据
			var url = PageUtility.getContextPath() + "/Manager/login.action";
			$.post(url,data,function(result){
				if(result.actionStatus == 'success'){
					window.location.href = getFirstMenuUrl(result.data.sysAccess);
				} else {
					alert(result.actionMessage);
				}
			}); 
	} 
	});
	
	//关闭
	$("#btnClose").click(function(){ 
		window.location.href = window.location.href = PageUtility.getContextPath() + "/US-index";
	});
});

//获取登录后的第一个模块链接
function getFirstMenuUrl(sysAccess) {
	var sysAccessList = sysAccess.split(";");
	
	if (sysAccessList[0] == 0) {
		return PageUtility.getContextPath() + "/MS-question";
	}
	if (sysAccessList[0] == 1) {
		return PageUtility.getContextPath() + "/MS-article";
	}
	if (sysAccessList[0] == 2) {
		return PageUtility.getContextPath() + "/MS-course";
	}
	if (sysAccessList[0] == 3) {
		return PageUtility.getContextPath() + "/MS-question";
	}
	if (sysAccessList[0] == 4) {
		return PageUtility.getContextPath() + "/MS-testPaper";
	}
	if (sysAccessList[0] == 5) {
		return PageUtility.getContextPath() + "/MS-exam";
	}
	if (sysAccessList[0] == 6) {
		return PageUtility.getContextPath() + "/MS-reportExam";
	}
	if (sysAccessList[0] == 7) {
		return PageUtility.getContextPath() + "/MS-examServer";
	}
	if (sysAccessList[0] == 8) {
		return PageUtility.getContextPath() + "/MS-manager";
	}
	if (sysAccessList[0] == 9) {
		return PageUtility.getContextPath() + "/MS-sysParam";
	}

	return "";
}

//check the userName 
function checkUserName() {
	if($("#txtUserName").val().length == 0) { 
		alert("教师名不为空");
		return false; 
	} 
	return true;
}

//check the pwd 
function checkUserPwd() {
	if($('#txtUserPwd').val().length == 0) { 
		alert("密码不为空");
		return false; 
	} 
	return true;
} 

//check the check code 
function checkCheckCode() {
	if($('#txtCheckCode').val().length == 0) { 
		alert("验证码不为空");
		return false; 
	} 
	return true;
} 

function changeImg(){
	var imgSrc = $("#verifyCodeImg");
	var src = imgSrc.attr("src");
	imgSrc.attr("src",chgUrl(src));
}
function chgUrl(url){
	var timestamp = (new Date()).valueOf();
	if(url.indexOf("&") >= 0){
		url = url + "tamp=" + timestamp;
	}else{
		url = url + "?timestamp=" + timestamp;
	}
	return url;
}