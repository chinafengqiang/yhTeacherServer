/**  
 * 考试成绩
 */
var argReportExamId=0;

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Report/getReportExamDataListBySearch.action";
var actionRemoveDataChild = contextPath + "/Report/removeReportExamData.action";
var actionImportFromFile = contextPath + "/Report/importSoftwareReportExamData.action";
var actionOrganData = contextPath + "/Organ/getClosedOrganList.action";
var actionGetReportData = contextPath + "/Report/getReportExamData.action";
var actionModifyReportData = contextPath + "/Report/modifyReportExamData.action";

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
	
	//点击导入按钮事件(******)
	$("#btnImportFromFile").upload({
        action: actionImportFromFile,
        name: 'file',
        iframeName: 'ImportFromFile',
        onSelect: function (self, element) {
            this.autoSubmit = false;
            var passScore = $("#edPassScore").val();
	        var organId = PageUtility.getQueryParamterByInputString("edOrgan");
            this.params({reportExamId:argReportExamId, organId:organId, passScore:passScore});
            this.submit();
	    },
        onSubmit: function (self, element) {
        	showLoading(true);
        },
        onComplete: function (data, self, element) {
        	queryDataList();
        	showLoading(false);
        	
        	var dataObject =  eval('(' + data + ')');;
			alert(dataObject.actionMessage);
        }
    });
	
	//点击保存按钮事件
	$("#btnImportData").click(function() {
		
		showLoading(true);
		
		try {
			setImportFormData(null);
			showImportForm(true);
		} catch (ex) {
			alert(ex);
		}
		showLoading(false);
		
	});
	
	//点击保存按钮事件
	$("#btnScoreSave").click(function() {
		
		showLoading(true);
		
		try {
		
			modifyScoreData();
			showEditForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
		
	});
	
	//点击关闭按钮事件
	$("#btnScoreClose").click(function() {
		
		showEditForm(false);
	});
	
	//点击关闭按钮事件
	$("#btnImportClose").click(function() {
		
		showImportForm(false);
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
	
	var htmlOrgan = PageUtility.ajaxGetUrlOptionsByActualName(actionOrganData);
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("ReportExamDataStatusEnum");
	
	$("#edOrgan").html(htmlOrgan)
	$("#edStatus").html(htmlStatus);
}

//设置编辑页面的数据内容(******)
function setEditFormScoreData(id) {

	$("#dataId").val(id);
	
	if (id != null) {
		
		var paramters = {"reportExamDataId": id};
		data = PageUtility.ajaxData(actionGetReportData, paramters);
				
		$("#edStatus").val(data.status);
		$("#edScore").val(data.score);
	}
}

//设置编辑页面的数据内容(******)
function setImportFormData() {

		$("#edPassScore").val("");
}


//删除
function removeDataChild(id) {
	
	var paramters = {
		"reportExamDataId": id
	};
	var result = PageUtility.ajaxAction(actionRemoveDataChild, paramters);
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
			+ data.actualOrgan
			+ "</td><td>"
			+ data.actualName
			+ "</td><td>"
			+ data.dutyRank
			+ "</td><td>"
			+ data.trade
			+ "</td><td>"
			+ data.score
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnReportModify(" + data.id + ")'>调整</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnReportRemove(" + data.id + ")'>删除</a>"
			+ "</td></tr>"
		);
	});
}

//修改数据(******)
function modifyScoreData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var status = PageUtility.getFormDataByString("edStatus", "状态", false);
	var score = PageUtility.getFormDataBySelect("edScore", "分数", false);

	var paramters = {
		"reportExamDataId": id,
		"score": score,
		"status":status
	};
	
	var result = PageUtility.ajaxAction(actionModifyReportData, paramters);
}

//删除按钮点击事件
function btnReportRemove(id) {
	
	if (!confirm("你确定要删除此记录吗？")) {
		return false;
	};

	showLoading(true);
	
	try {
		removeDataChild(id);
		queryDataList();
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

function btnReportModify(id){
	
	showLoading(true);
	
	try {
		setEditFormScoreData(id);
		showEditForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//获取查询参数集(******)
function getQueryParams() {
	
	var actualOrgan = PageUtility.getQueryParamterByInputString("qpActualOrgan");
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "actualName":actualName,
				   "actualOrgan":actualOrgan,
				   "reportExamId":argReportExamId
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试报表</span>您正在浏览考试成绩……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试报表</span>您正在编辑考试成绩……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showImportForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试报表</span>您正在浏览考试成绩……");
		$("#pageDataList").show();
		$("#pageImportData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试报表</span>您正在导入考试成绩……");
		$("#pageDataList").hide();
		$("#pageImportData").show();
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

