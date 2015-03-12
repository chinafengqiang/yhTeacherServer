/**  
 * 选取题目
 */


//author:xushisong
/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Question/getQuestionListBySearch.action";
var actionCategoryTree = contextPath + "/Question/getQuestionCategoryListByTree.action";

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
		queryDataTree();
		queryDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {

	var htmlQuestionType = PageUtility.ajaxGetEnumNameOptions("QuestionTypeEnum");
	$("#qpQuestionType").html(htmlQuestionType);
	
	var htmlDifficulty = PageUtility.getQuestionDifficultyOptions();
	$("#qpDifficulty").html(htmlDifficulty);
}

//查询分类数据树
function queryDataTree() {
	
	result = PageUtility.ajaxData(actionCategoryTree, "");
	showCategoryDataList(result);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
	
}

//显示数据列表(******1)
function showCategoryDataList(dataList) {
	
	$("#categoryData").empty();
	
	var html = "<dl class='course_choice'>";
	
	$.each(dataList, function(i, data) {
		if (i == 0) {
			html = html + "<dd class='ddchoised' onclick='btnSelectCategory(" + data.id + ")'>" + data.levelName +"</dd>";
		} else {
			html = html + "<dd onclick='btnSelectCategory(" + data.id + ")'>" + data.levelName +"</dd>";
		}
		
	});
	
	html = html + "</dl>"

	$("#categoryData").append(html);
	
	$(".course_choice dd").click(function(){             
		$(".course_dif dl dd").removeClass("ddchoised");
		$(this).addClass("ddchoised");		
	});
	
	if (dataList.length > 0) {
		btnSelectCategory(dataList[0].id);
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
			+ data.questionTypeName
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ data.score
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSelectQuestionByUse(" + data.id + ")'>选取</a>"
			+ "</td></tr>"
		);
	});
}

//选取试卷
function btnSelectQuestionByUse(id){
	window.parent.selectQuestionData(id);
}

//选择分类(******1)
function btnSelectCategory(id){
	$("#questionCategoryId").val(id);
	queryDataList();
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var questionType = PageUtility.getQueryParamterByInputString("qpQuestionType");
	var difficulty = PageUtility.getQueryParamterByInputString("qpDifficulty");
	var ken = PageUtility.getQueryParamterByInputString("qpKen");
	var name = PageUtility.getQueryParamterByInputString("qpName");
	var questionCategoryId = $("#questionCategoryId").val();
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "questionType":questionType,
				   "difficulty":difficulty,
				   "ken":ken, 
				   "name":name,
				   "questionCategoryId":questionCategoryId
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷管理</span>您正在手动提取题目……");
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

function closeFunction(){
	window.parent.backFromSubPage();
	//window.parent.location.reload();
}

