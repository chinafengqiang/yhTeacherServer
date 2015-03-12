/**  
 * 学员选择管理
 */


//author:xushisong
/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/User/getUserListByManagerSelect.action";
var actionOrganData = contextPath + "/Organ/getOrganList.action";

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
				createCourseData();
			} else {
				modifyCourseData();
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

	var htmlDutyRank = PageUtility.ajaxGetSysParamOptions("DutyRank");
	var htmlTrade = PageUtility.ajaxGetSysParamOptions("Trade");
	var htmlOrgan = PageUtility.ajaxGetUrlOptionsByActualName(actionOrganData, true);
	
	$("#qpOrgan").html(htmlOrgan)
	$("#qpDutyRank").html(htmlDutyRank);
	$("#qpTrade").html(htmlTrade);
	
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
			+ "</td><td class='txtl text-overflow'>"
			+ data.actualOrgan
			+ "</td><td>"
			+ data.actualName
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ data.trade
			+ "</td><td>"
			+ data.dutyRank
			+ "</td><td>"
			+ data.totalCredit
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSelectUser(" + data.id + ")'>选取</a>" 
			+ "</td></tr>"
		);
	});
}

//选取人员
function btnSelectUser(id){
	showLoading(true);
	window.parent.selectUserData(id);
	showLoading(false);
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var actualOrgan = PageUtility.getQueryParamterByInputString("qpActualOrgan");
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	var dutyRank = PageUtility.getQueryParamterByInputString("qpDutyRank");
	var trade = PageUtility.getQueryParamterByInputString("qpTrade");
	var organ = PageUtility.getQueryParamterByInputString("qpOrgan");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "actualOgan":actualOrgan,
				   "actualName":actualName,
				   "dutyRank":dutyRank,
				   "trade":trade,
				   "organId":organ
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>学员</span>您正在浏览学员记录……");
		$("#pageCategoryData").show();
		$("#pageChildEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>学员</span>您正在编辑课程学员记录……");
		$("#pageCategoryData").hide();
		$("#pageChildEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
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

function closeFunction(){
	window.parent.backFromSubPage();
	window.parent.location.reload();
}

