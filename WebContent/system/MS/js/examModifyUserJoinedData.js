/**  
 * 学员选择管理
 */

var argExamId=0;
var selectedDataList = new Array();

/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Organ/getOrganListBySelect.action";
var actionGetData = contextPath + "/Exam/getExam.action";
var actionSaveSelectedDataList = contextPath + "/Exam/modifyUserJoinedData.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//获取父页面传递过来的参数
	argExamId = PageUtility.getRequestParam('examId');
	
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
		
	//初始化页面
	initPage();
	 
})

/* =========== 定义数据操作方法 ============= */

//页面初始化
function initPage() {
	
	$("#functionName").html("<span>考试</span>您正在安排参与考试的单位……");
	
	showLoading(true);
	
	try {
		setDataSelectOption();
		queryDataList();
		intiSelectedDataList();
		showSelectedDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//初始化已选择的数据列表
function intiSelectedDataList(){
	
	var params = { 
			"exam.id": argExamId
		};

	var result = PageUtility.ajaxData(actionGetData, params);
	var userJoinedData = result.userJoinedData;
	
	if(userJoinedData != null && userJoinedData != ''){
		var dataList = eval("("+userJoinedData+")");
		
		$.each(dataList, function(i, data) {
			
			selectedDataList.push(data);
		});
    }
}

Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

//删除选择
function btnRemove(id){

	$.each(selectedDataList, function(i, data) {
		if (data.id == id) {
			selectedDataList.remove(data);
		}
	})
	
	showSelectedDataList();	
}

//清除所有选择
function btnClear(){
	
	selectedDataList.splice(0, selectedDataList.length);  
	showSelectedDataList();
}

//选择
function btnSelect(id, name){

	var bExist = false;
	$.each(selectedDataList, function(i, data) {
		if (data.id == id) {
			bExist = true;
		}
	})
	
	if (bExist) {
		return;
	}

	var data = {id:id, name:name};
	selectedDataList.push(data);

	showSelectedDataList();
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {

	var htmlOrganType = PageUtility.ajaxGetEnumNameOptions("OrganTypeEnum");
	$("#qpOrganType").html(htmlOrganType);
}

//查询数据列表
function queryDataList() {
	
	var result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
}

//保存数据
function btnSave() {
	
	try {
		
		var userJoinedData = $.toJSON(selectedDataList);
		
		if (userJoinedData == "[]") {
			userJoinedData = "";
		}
		
		var paramters = { 
			"exam.id": argExamId,
			"userJoinedData": userJoinedData
		};
		
		var result = PageUtility.ajaxAction(actionSaveSelectedDataList, paramters);
		
		btnClose();
	} catch (ex) {
		alert(ex);
	}
}

//显示数据列表(******1)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td>"
			+ data.organTypeName
			+ "</td><td class='txtl text-overflow'>"
			+ data.actualName
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ data.linkman
			+ "</td><td>"
			+ data.mobile
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10'  href='javascript:void(0)' onclick='btnSelect(" + data.id + ",  \"" + data.actualName + "\")'>选取</a>" 
			+ "</td></tr>"
		);
	});
}

//显示已选择的数据列表(******1)
function showSelectedDataList() {
	
	$("#selectedDataListBody").empty();
	
	$.each(selectedDataList, function(i, data) {
		$("#selectedDataListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ "<a class='grey_underline marr10'  href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>取消选择</a>"
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	var organType = PageUtility.getQueryParamterByInputString("qpOrganType");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "status":0,
				   "actualName":actualName,
				   "organType":organType };
	return result;
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

//返回
function btnClose(){
	window.parent.backFromSubPage();
}

