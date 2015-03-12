/**  
 * 单位管理
 */
var argReportExamId=0;

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Report/getReportExamResultOrganList.action";
var actionOrganData = contextPath + "/Organ/getOrganList.action";
var actionQueryResultDataList = contextPath + "/Report/getReportExamResultScoreList.action";
var actionExportReportExamResultOrganToExcel = contextPath + "/Report/exportReportExamResultOrganToExcel.action";
var actionExportReportExamResultScoreToExcel = contextPath + "/Report/exportReportExamResultScoreToExcel.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//获取父页面传递过来的参数
	argReportExamId = PageUtility.getRequestParam('reportExamId');
	
	//点击上一页按钮事件
	$("#btnPriorPage").click(function() {
		
		showLoading(true);
		
		try {
			priorPage();
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});

	//点击下一页按钮事件
	$("#btnNextPage").click(function() {
		
		showLoading(true);
		
		try {
			nextPage();
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});
	
	//选择页面试卷事件
	$("#selectPageNoList").change(function() {
		
		showLoading(true);
		
		try {
			selectPage();
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});
		
	//点击导出单位报表按钮事件
	$("#btnExportReportExamResultOrganToExcel").click(function() {
		
		exportReportExamResultOrganToExcel();
	});
	
	//点击导出分数段按钮事件
	$("#btnExportReportExamResultScoreToExcel").click(function() {
		
		exportReportExamResultScoreToExcel();
	});

	//点击刷新按钮事件
	$("#btnQuery").click(function() {
		
		showLoading(true);
		
		try {
			pageNo = 1;
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});
	
	//选择单位
	$("#btnSelectOrgan").click(function() {
		
		var timeStamp = (new Date()).valueOf();
		var url = contextPath + "/system/MS/organSelect.jsp?parentId=&status=&timestamp=" + timeStamp;
	
		$('#iframeSubPage').attr('src',url);
	 	$("#mainPage").hide();
	 	$("#subPage").show();
	});
	
	//初始化页面
	initPage();
})

/* =========== 定义数据行按钮事件 ============= */

//选择了单位
function selectOrgan(id, name) {
	
	$("#qpOrgan").val(id);
	$("#qpOrganName").text(name);
	
	queryDataList();
	queryResultDataList();	
}

//导出单位报表
function exportReportExamResultOrganToExcel() {
	
	var argOrganId = PageUtility.getQueryParamterByInputString("qpOrgan");
	var url = actionExportReportExamResultOrganToExcel + "?reportExamId=" + argReportExamId;
	url = url + "&organId=" + argOrganId;

	PageUtility.exportToFile(url);
}

//导出分数段报表
function exportReportExamResultScoreToExcel() {
	
	var argOrganId = PageUtility.getQueryParamterByInputString("qpOrgan");
	var url = actionExportReportExamResultScoreToExcel + "?reportExamId=" + argReportExamId;
	url = url + "&organId=" + argOrganId;

	PageUtility.exportToFile(url);
}


/* =========== 定义数据操作方法 ============= */

//页面初始化
function initPage() {
	
	showLoading(true);
	
	try {
		showMSHeader();
		showEditForm(false);
		setDataSelectOption();
		queryDataList();
		queryResultDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {
	
	var url = contextPath + "/Organ/getTopOrgan.action";
	var data = PageUtility.ajaxData(url, null);
	
	selectOrgan(data.id, data.actualName);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result);
}

function queryResultDataList() {
	
	result = PageUtility.ajaxData(actionQueryResultDataList, getQueryParams());
	showDataResultList(result);
}

//显示数据列表(******)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.name
			+ "</td><td>"
			+ data.totalNum
			+ "</td><td>"
			+ data.joinedNum
			+ "</td><td>"
			+ data.passedNum
			+ "</td><td>"
			+ data.joinedRate
			+ "</td><td>"
			+ data.passedRate
			+ "</td></tr>"
		);
	});
}

//显示数据列表(******)
function showDataResultList(dataList) {
	
	$("#dataResultListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataResultListBody").append(
			"<tr><td>"
			+ data.name
			+ "</td><td>"
			+ data.passedNum
			+ "</td><td>"
			+ data.passedRate
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******)
function getQueryParams() {
	
	var argOrganId = PageUtility.getQueryParamterByInputString("qpOrgan");
	
	var result = { 
				   "organId":argOrganId,
				   "reportExamId":argReportExamId
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试报表</span>您正在浏览考试报表记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试报表</span>您正在浏览考试成绩……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//判断编辑模式
function isCreateData() {
	
	var value = $("#dataId").val();
	return !(value != null && value != NaN && value != ""); 
}

//设置分页结果
function setPaginateResult(result) {
	
	if (result.pageCount < pageNo) {
		pageNo = result.pageCount;
	}
			
	$("#lbPageCount").text(result.pageCount);
	$("#lbDataListCount").text(result.count);

	$("#btnPriorPage").attr("disabled", pageNo <= 1);
	$("#btnNextPage").attr("disabled", pageNo >= result.pageCount);
	
	var html = "";
	for(var i = 1; i <= result.pageCount; i++){
		 if(i == pageNo){
			html += "<option value="+i+" selected='selected'>第" + i + "页</option>"
		 } else{
		 	html += "<option value="+i+">第" + i + "页</option>";
		 }
	}
   	$("#selectPageNoList").html(html);
}

//显示下一页
function nextPage() {
	pageNo = pageNo + 1;
}

//显示上一页
function priorPage() {
	pageNo = pageNo - 1;
}

//选择页码
function selectPage() {
			
	var selectedPageNo = $("#selectPageNoList").children('option:selected').val(); 
	pageNo = selectedPageNo;
}

//显示/隐藏Loading图片
function showLoading(visible) {
	
	if (visible) {
		$("#loading").show();
	} else {
		$("#loading").hide();
	} 
}

//返回到主页面
function backFromSubPage() {
	
	$("#subPage").hide();
	$("#mainPage").show();
	$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
}

