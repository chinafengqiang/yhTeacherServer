/**  
 * 考试服务器管理
 */
/* =========== 定义父页面传递过来的参数 (******1)============= */
var argExamServerId=0;

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/ExamServer/getExamServerSummary.action";
var actionGetData = contextPath + "/ExamServer/getExamServer.action";

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//获取父页面传递过来的参数
	argExamServerId = PageUtility.getRequestParam('examServerId');

	//点击刷新按钮事件
	$("#btnQuery").click(function() {
		
		showLoading(true);
		
		try {
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});
	
	//初始化页面
	initPage();
})

/* =========== 定义数据操作方法 ============= */

//页面初始化
function initPage() {
	
	showLoading(true);
	
	try {
		showExamServerInfo();
		queryDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//显示当前考试服务器信息
function showExamServerInfo() {
	
	var params = {"examServer.id":argExamServerId};
	var result = PageUtility.ajaxData(actionGetData, params);
	$("#functionName").html("<span>考试服务器</span>您正在浏览【" + result.name + "】的监控数据……");
}

//查询数据列表
function queryDataList() {
	
	var params = {"examServer.id":argExamServerId};
	var result = PageUtility.ajaxData(actionQueryDataList, params);
	showDataList(result);
}

//显示数据列表(******1)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.examName
			+ "</td><td>"
			+ data.statusName
			+ "</td><td>"
			+ data.totalNumber + '人'
			+ "</td><td>"
			+ (data.totalNumber - data.beginExamNumber - data.endExamNumber) + '人'  
			+ "</td><td>"
			+ data.beginExamNumber + '人'
			+ "</td><td>"
			+ data.endExamNumber + '人'
			+ "</td></tr>"
		);
	});
}

//显示/隐藏Loading图片
function showLoading(visible) {
	
	if (visible) {
		$("#loading").show();
	} else {
		$("#loading").hide();
	} 
}



