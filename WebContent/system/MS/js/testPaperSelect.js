/**  
 * 课程管理
 */


//author:xushisong
/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/TestPaper/getTestPaperListByExamSelect.action";

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

	var htmlCategory = PageUtility.ajaxGetSysParamOptions("TestPaperCategory");
	$("#qpCategory").html(htmlCategory);

	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("CourseUserStatusEnum");
	$("#qpStatus").html(htmlStatus);
	
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
			+ data.category
			+ "</td><td class='txtl text-overflow'>"
			+ data.name
			+ "</td><td>"
			+ data.totalSeries + ' 套'
			+ "</td><td>"
			+ data.totalScore + '分'
			+ "</td><td>"
			+ data.passScore + '分'
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSelectTestPaper(" + data.id + ", \"" + data.name + "\")'>选取</a>"
			+ "</td></tr>"
		);
	});
}

//选取试卷
function btnSelectTestPaper(id, name){
	$("#edTestPaperId",parent.document).val(id);
	$("#edTestPaperName",parent.document).val(name);
	window.parent.backFromSubPage();
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var category = PageUtility.getQueryParamterByInputString("qpCategory");
	var name = PageUtility.getQueryParamterByInputString("qpcName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "category":category,
				   "name":name
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试管理</span>您正在选取试卷……");
		$("#pageCategoryData").show();
		$("#pageChildEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>课程控制台</span>您正在编辑课程学员记录……");
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

