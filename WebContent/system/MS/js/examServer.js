/**  
 * 考试服务器管理
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/ExamServer/getExamServerListBySearch.action";
var actionGetData = contextPath + "/ExamServer/getExamServer.action";
var actionCreateData = contextPath + "/ExamServer/createExamServer.action";
var actionModifyData = contextPath + "/ExamServer/modifyExamServer.action";
var actionRemoveData = contextPath + "/ExamServer/removeExamServer.action";

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

//导出按钮点击事件
function btnExportToFile(id) {
	
	exportToFile(id);
}

//监控按钮点击事件
function btnControl(id) {
	
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/examServerControl.jsp?examServerId=" + id + "&timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
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
	
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("ExamServerStatusEnum");
	$("#edStatus").html(htmlStatus);
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edName").val("");
		$("#edNote").val("");
		$("#edStatus").val("0");
		$("#edUrl").val("");
		$("#edDirectUrl").val("");
	} else {
		
		var paramters = {"examServer.id": id};
		data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edNote").val(data.note);
		$("#edStatus").val(data.status);
		$("#edUrl").val(data.url);
		$("#edDirectUrl").val(data.directUrl);
	}
}

//创建数据(******1)
function createData() {
	
	var name = PageUtility.getFormDataByString("edName", "名称", false, 100);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var url = PageUtility.getFormDataByString("edUrl", "服务器地址", false, 100);
	var directUrl = PageUtility.getFormDataByString("edDirectUrl", "内网地址", false, 100);
	var note = PageUtility.getFormDataByString("edNote", "说明", true, 100);
	
	var paramters = { 
		"examServer.name": name,
		"examServer.status": status,
		"examServer.url": url,
		"examServer.directUrl": directUrl,
		"examServer.note": note
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//修改数据(*****11*)
function modifyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "名称", false, 100);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var url = PageUtility.getFormDataByString("edUrl", "服务器地址", false, 100);
	var directUrl = PageUtility.getFormDataByString("edDirectUrl", "内网地址", false, 100);
	var note = PageUtility.getFormDataByString("edNote", "说明", true, 100);
	
	var paramters = { 
		"examServer.id": id,
		"examServer.name": name,
		"examServer.status": status,
		"examServer.url": url,
		"examServer.directUrl": directUrl,
		"examServer.note": note
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"examServer.id": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
}

//显示数据列表(******1)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td class='txtl text-overflow'>"
			+ data.name
			+ "</td><td>"
			+ data.url
			+ "</td><td>"
			+ data.directUrl
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnControl(" + data.id + ")'>监控</a>" 
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber
				   };
	return result;
}


//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试服务器</span>您正在浏览考试服务器记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试服务器</span>您正在编辑考试服务器……");
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
}



