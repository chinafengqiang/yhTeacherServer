/**  
 * 考试报表
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Report/getReportExamListBySearch.action";
var actionGetData = contextPath + "/Report/getReportExam.action";
var actionCreateData = contextPath + "/Report/createReportExam.action";
var actionModifyData = contextPath + "/Report/modifyReportExam.action";
var actionRemoveData = contextPath + "/Report/removeReportExam.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
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
		
	//点击添加按钮事件
	$("#btnCreate").click(function() {
		
		showLoading(true);
		
		try {
			setEditFormData(null);
			showEditForm(true);
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
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
			
	//点击保存按钮事件
	$("#btnSave").click(function() {
		
		showLoading(true);
		
		try {
			if (isCreateData()) {
				createData();
			} else {
				modifyData();
			}
			
			showEditForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
		
	});
		
	//点击关闭按钮事件
	$("#btnClose").click(function() {
		
		showEditForm(false);
	});
	
	//初始化页面
	initPage();
})



/* =========== 定义数据行按钮事件 ============= */

//修改按钮点击事件
function btnModify(id) {

	showLoading(true);
	
	try {
		setEditFormData(id);
		showEditForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//删除按钮点击事件
function btnRemove(id) {
	
	if (!confirm("你确定要删除此记录吗？")) {
		return false;
	};

	showLoading(true);
	
	try {
		removeData(id);
		queryDataList();
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
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
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {
	
	var htmlReportStatus = PageUtility.ajaxGetEnumNameOptions("ReportExamStatusEnum");
	$("#qpStatus").html(htmlReportStatus);
	
	$("#edReportStatus").html(htmlReportStatus);
	
}

function getChecked(edName) {
	
	var checkValue = false;
	
	if($("input[name='"+edName+"']:checkbox:checked").length > 0) {
		checkValue = true;
	} 
	return checkValue;
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edName").val("");
		$("#edExamCode").val("");
		$("#edReportStatus").val("0");
		$("#edCanAutoGather").attr("checked", false);
	} else {
		
		var paramters = {"reportExam.id": id};
		data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edExamCode").val(data.examCode);
		$("#edReportStatus").val(data.status);
		$("#edCanAutoGather").attr('checked', data.canAutoGather);
	}
}

//创建数据(******)
function createData() {
	
	var name = PageUtility.getFormDataByString("edName", "考试报表", false, 100);
	var examCode = PageUtility.getFormDataByString("edExamCode", "考试代号", false, 50);
	var status = PageUtility.getFormDataBySelect("edReportStatus", "状态", false);
	var canAutoGather = $("#edCanAutoGather").is(':checked');
	
	var paramters = { 
		"reportExam.name": name,
		"reportExam.examCode": examCode,
		"reportExam.canAutoGather": canAutoGather,
		"reportExam.status": status
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//修改数据(******)
function modifyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "考试报表", false, 100);
	var examCode = PageUtility.getFormDataByString("edExamCode", "考试代号", false, 50);
	var status = PageUtility.getFormDataBySelect("edReportStatus", "状态", false);
	var canAutoGather = $("#edCanAutoGather").is(':checked');
	
	var paramters = {
		"reportExam.id": id,
		"reportExam.name": name,
		"reportExam.examCode": examCode,
		"reportExam.canAutoGather": canAutoGather,
		"reportExam.status": status
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//删除数据(******)
function removeData(id) {
	
	var paramters = {
		"reportExam.id": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
}

//显示数据列表(******)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td class='txtl text-overflow'>"
			+ data.name
			+ "</td><td>"
			+ data.examCode
			+ "</td><td>"
			+ data.canAutoGatherName
			+ "</td><td>"
			+ data.statedTimeName
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>"
			+ "<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnGetScore(" + data.id + ")'>考试成绩</a>"
			+ "<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnViewReport(" + data.id + ")'>查看报表</a>"
			+ "</td></tr>"
		);
	});
}


//获取查询参数集(******)
function getQueryParams() {
	
	var name = PageUtility.getQueryParamterByInputString("qpName");
	var examCode = PageUtility.getQueryParamterByInputString("qpExamCode");
	var status = PageUtility.getQueryParamterBySelect("qpStatus");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "name":name,
				   "examCode":examCode,
				   "status":status
				   };
	return result;
}

//考试成绩
function btnGetScore(id){
	
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/reportExamScore.jsp?reportExamId=" + id + "&timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
	
}

//查看报表
function btnViewReport(id){
	
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/reportExamResult.jsp?reportExamId=" + id + "&timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
}



//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试报表</span>您正在浏览考试报表记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试报表</span>您正在编辑考试报表……");
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

function clearCheckValue(checkName){
	if($("input[name='"+checkName+"']:checkbox:checked").length > 0) {
			$("input[name='"+checkName+"']:checkbox:checked").each(function() {
				 $(this).attr("checked",false);
			})
		} 
}

//返回到主页面
function backFromSubPage() {
	
	$("#subPage").hide();
	$("#mainPage").show();
	$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
}

