/**  
 * 系统设置
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/System/getSysParamList.action";
var actionGetData = contextPath + "/System/getSysParam.action";
var actionModifyData = contextPath + "/System/modifySysParam.action";
var actionGenarateHtmls = contextPath + "/UserSystem/genarateHtmls.action";
var actionAutoStatReportExam = contextPath + "/Report/autoStatReportExam.action";
var actionAutoValidateOrganServiceLimit = contextPath + "/Organ/autoValidateOrganServiceLimit.action"; 

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
	
	//点击批量生成页面按钮事件
	$("#btnGenarateHtmls").click(function() {
		
		if (!confirm("是否确认要执行【自动刷新学员平台】操作?")) {
			reutrn;
		}
		
		showLoading(true);
		$("#btnGenarateHtmls").addClass('buttonDisabled');
		$("#btnGenarateHtmls").attr("disabled", true);

		try {
			var result = PageUtility.ajaxAction(actionGenarateHtmls, null);
			alert(result);
		} catch (ex) {
			alert(ex);
		}
		
		$("#btnGenarateHtmls").attr("disabled", false);
		$("#btnGenarateHtmls").removeClass('buttonDisabled');
		showLoading(false);
	});
	
	//点击自动统计考试报表按钮事件
	$("#btnAutoStatReportExam").click(function() {
		
		if (!confirm("是否确认要执行【自动统计考试报表】操作?")) {
			reutrn;
		}

		showLoading(true);
		$("#btnAutoStatReportExam").addClass('buttonDisabled');
		$("#btnAutoStatReportExam").attr("disabled", true);

		try {
			var result = PageUtility.ajaxAction(actionAutoStatReportExam, null);
			alert(result);
		} catch (ex) {
			alert(ex);
		}
		
		$("#btnAutoStatReportExam").attr("disabled", false);
		$("#btnAutoStatReportExam").removeClass('buttonDisabled');
		showLoading(false);
	});

	//点击自动校验单位期限按钮事件
	$("#btnAutoValidateOrganServiceLimit").click(function() {
		
		if (!confirm("是否确认要执行【自动校验单位期限】操作?")) {
			reutrn;
		}
				
		showLoading(true);
		$("#btnAutoValidateOrganServiceLimit").addClass('buttonDisabled');
		$("#btnAutoValidateOrganServiceLimit").attr("disabled", true);

		try {
			var result = PageUtility.ajaxAction(actionAutoValidateOrganServiceLimit, null);
			alert(result);
		} catch (ex) {
			alert(ex);
		}
		
		$("#btnAutoValidateOrganServiceLimit").attr("disabled", false);
		$("#btnAutoValidateOrganServiceLimit").removeClass('buttonDisabled');
		showLoading(false);
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

//导出按钮点击事件
function btnBrowse(id) {
	
	browseData(id);
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
	
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	if (id != null) {
		var paramters = {"sysParam.id": id};
		var data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").text(data.name);
		$("#edValue").val(data.value);
		$("#edNote").text(data.note);
		$("#sysParamType").val(data.sysParamType);
	}
}

//修改数据(*****11*)
function modifyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var edValue = PageUtility.getFormDataByString("edValue", "内容", false, 30000);
	var sysParamType = PageUtility.getFormDataByString("sysParamType", "类型", false);
	
	var paramters = { 
		"sysParam.id": id,
		"sysParam.value": edValue,
		"sysParam.sysParamType": sysParamType
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
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
			+ "</td><td>"
			+ data.name
			+ "</td><td class='txtl text-overflow'>"
			+ data.value
			+ "</td><td>" + getModifyActionString(data)
			+ "</td></tr>"
		);
	});
}

//判断是否显示修改按钮
function getModifyActionString(data) {
	
	if (data.canModify) {
		return "<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>";
	} else {
		return "";
	}
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
		$("#functionName").html("<span>设置</span>您正在浏览系统术语记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>设置</span>您正在编辑系统术语……");
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



