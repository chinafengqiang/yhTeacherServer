/**  
 * 单位选择管理
 */

var argParentId=null;
var argStatus=null;

/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Organ/getOrganListBySelect.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//获取父页面传递过来的参数
	argParentId = PageUtility.getRequestParam('parentId');
	argStatus = PageUtility.getRequestParam('status');
	
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
	
	$("#functionName").html("<span>单位</span>您正在选择单位……");
	
	showLoading(true);
	
	try {
		setDataSelectOption();
		queryDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//选择
function btnSelect(id, name){
	
	window.parent.backFromSubPage();
	window.parent.selectOrgan(id, name);
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

//获取查询参数集(******1)
function getQueryParams() {
	
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	var organType = PageUtility.getQueryParamterByInputString("qpOrganType");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "parentId":argParentId,
				   "status":argStatus,
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

