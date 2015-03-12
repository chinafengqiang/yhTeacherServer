/**  
 * 试卷题目
 */
var argTestPaperId=0;
var argTotalSeries=0;

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/TestPaper/getTestPaperQuestionListBySearch.action";
var actionSummaryData = contextPath + "/TestPaper/getTestPaperSummary.action";
var actionCategoryTree = contextPath + "/Question/getQuestionCategoryListByTree.action";
var actionAutoSelectData = contextPath + "/TestPaper/autoSelectQuestionList.action";
var actionQuestionRemoveData = contextPath + "/TestPaper/removeQuestion.action";
var actionQuestionExportToFile = contextPath + "/TestPaper/exportQuestionList.action";
var actionQuestionImportFromFile = contextPath + "/TestPaper/importQuestionList.action";
var actionvalidateData = contextPath + "/TestPaper/validateTestPaper.action";
var actionSelectDataList = contextPath + "/Question/getQuestionListBySearch.action";
var actionSelectQuestionData = contextPath + "/TestPaper/selectQuestion.action";
var actionExportTestPaperToWord = contextPath + "/TestPaper/exportTestPaperToWord.action";
var actionQuestionModifyData = contextPath + "/TestPaper/adjustQuestion.action";
var actionQuestionGetData = contextPath + "/TestPaper/getTestPaperQuestion.action";
var actionBrowseTestPaper = contextPath + "/TestPaper/browseTestPaper.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//获取父页面传递过来的参数
	argTestPaperId = PageUtility.getRequestParam('testPaperId');
	argTotalSeries = PageUtility.getRequestParam('totalSeries');
	
	$("#qpSeries").text(1);
	$("#totalSeries").text(argTotalSeries);
	
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
	
	//点击刷新按钮事件
	$("#btnSelectQuery").click(function() {
		
		showLoadingSelect(true);
		
		try {
			pageNo = 1;
			querySelectDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoadingSelect(false);
	});
	
	//点击保存按钮事件
	$("#btnPaperSave").click(function() {
		
		showLoading(true);
		try {
			modifyPaperData();
			showQuestionEditForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
		
	});

	//点击关闭按钮事件
	$("#btnPaperClose").click(function() {
		
		showQuestionEditForm(false);
	});
	
	
	//自动
	$("#btnAutoQuestion").click(function() {
		
		$("#btnQImportFromFile").hide();
		$("#questionData").show();
		setSelectQuestionData(null);
		
	});
	
	//点击关闭按钮事件
	$("#btnSelectClose").click(function() {
		
		showSelectQuestionForm(false);
	});
	
	//手动
	$("#btnSelectQuestion").click(function() {
		
		var timeStamp = (new Date()).valueOf();
		var url = contextPath + "/system/MS/questionSelect.jsp?timestamp=" + timeStamp;
		
		$('#iframeSubPage').attr('src',url);
	 	$("#mainPage").hide();
	 	$("#subPage").show();
		
	});
	
	//点击添加按钮事件
	$("#btnExportToFile").click(function() {
		
		showLoading(true);
		exportQuestionToFile();
		showLoading(false);
	});
	
	//点击预览试卷按钮事件
	$("#btnBrowseTestPaper").click(function() {
		
		browseTestPaper();
	});
	
	//点击添加按钮事件
	$("#btnExportTestPaperToWord").click(function() {
		
		showLoading(true);
		exportTestPaperToWord();
		showLoading(false);
	});
	
	//点击导入按钮事件(******)
	$("#btnQImportFromFile").upload({
        action: actionQuestionImportFromFile,
        name: 'file',
        iframeName: 'ImportFromFile2',
        onSelect: function (self, element) {
            this.autoSubmit = false;
            
            var testPaperId = argTestPaperId;
	        var series = $("#qpSeries").text();
            this.params({testPaperId:testPaperId, series:series});
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
	
	//校检
	$("#btnValidateTestPaper").click(function() {
		
		showLoading(true);
		
		try {
			validateData();
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
	
	var htmlQuestionType = PageUtility.ajaxGetEnumNameOptions("QuestionTypeEnum");
	var htmlDifficulty = PageUtility.getQuestionDifficultyOptions();
	
	$("#qpQuestionType").html(htmlQuestionType);
	$("#edQType").html(htmlQuestionType);
	$("#edQuestionCategory").html(queryDataTreeByUse());
	$("#qpCQuestionType").html(htmlQuestionType);
	$("#qpDifficulty").html(htmlDifficulty);
	$("#edDifficulty").html(htmlDifficulty);
}

//关闭
function btnHidden(){
	$("#questionData").hide();
	$("#btnQImportFromFile").show();
}

//设置编辑页面的数据内容(******)
function setEditFormQuestionData(id) {
	
	$("#dataId").val(id);
	
	if (id != null) {
		
		var paramters = {"testPaperQuestion.id": id};
		data = PageUtility.ajaxData(actionQuestionGetData, paramters);
		$("#edcScore").val(data.score);
		$("#edSortFlag").val(data.name);
	}
}

//设置编辑页面的数据内容(******)
function setSelectQuestionData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edDifficulty").val("");
		$("#edScore").val("");
		$("#edNumber").val("");
		
	} 
}

//校验
function validateData() {
	var paramters = { 
		"testPaperId": argTestPaperId
	};
	
	var result = PageUtility.ajaxAction(actionvalidateData, paramters);
	
	alert(result);
}

//提取单套
function btnSigleQuestion(){
	
		showLoading(true);
		
		try {
			createSelectQuestionData(true);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
}

//提取全套
function btnAllQuestion(){
	
		showLoading(true);
		try {
			createSelectQuestionData(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
}

//选取
function selectQuestionData(questionId) {
	
	try {
		var series = $("#qpSeries").text();
		var paramters = { 
			"testPaperId": argTestPaperId,
			"series": series,
			"questionId": questionId
		};
		
		var result = PageUtility.ajaxAction(actionSelectQuestionData, paramters);
		alert(result);
	} catch (ex) {
		alert(ex);
	}

}

//修改数据(*****11*)
function modifyPaperData() {
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var score = PageUtility.getFormDataByString("edcScore", "分数", false);
	var sortFlag = PageUtility.getFormDataBySelect("edSortFlag", "题号", false);
	
	var paramters = { 
		"testPaperQuestionId": id,
		"score": score,
		"sortFlag": sortFlag
	};
	
	var result = PageUtility.ajaxAction(actionQuestionModifyData, paramters);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result)
	testPaperSummary();
}

//选择查询
function querySelectDataList() {
	
	result = PageUtility.ajaxData(actionSelectDataList, getQuerySelectParams());
	showSelectDataList(result.list);
	setPaginateResultByUse(result);
}

//删除数据(******)
function removeQuestionData(id) {
	
	var paramters = {
		"testPaperQuestionId": id
	};
	
	var result = PageUtility.ajaxAction(actionQuestionRemoveData, paramters);
}


//向左
function btnDecrease(){
	var series = eval($("#qpSeries").text());
	
	if(series - 1 > 0){
		$("#qpSeries").text(series - 1);
		queryDataList();
	}
}

//向右
function btnIncrease(){
	var totalSeries = $("#totalSeries").text();
	var series = eval($("#qpSeries").text());
	
	if(series + 1 <= totalSeries){
		$("#qpSeries").text(series + 1);
		queryDataList();
	}
}

//试卷概况
function testPaperSummary() {
	
	map = PageUtility.ajaxData(actionSummaryData, getSummaryQueryParams());
	
	$("#singleTotalNumber").text(map.SingleSelect_TotalNumber);
	$("#singleSelectedNumber").text(map.SingleSelect_SelectedNumber);
	$("#multiTotalNumber").text(map.MultiSelect_TotalNumber);
	$("#multiSelectedNumber").text(map.MultiSelect_SelectedNumber);
	$("#judgeTotalNumber").text(map.Judge_TotalNumber);
	$("#judgeSelectedNumber").text(map.Judge_SelectedNumber);
	
	$("#totalScore").text(map.TotalScore);
	$("#score").text(map.SelectedScore);
	
}

//显示数据列表(******)
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
			+ data.score + '分'
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnQuestionModify(" + data.id + ")'>调整</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnQuestionRemove(" + data.id + ")'>删除</a>"
			+ "</td></tr>"
		);
	});
}


//显示数据列表(******1)
function showSelectDataList(dataList) {
	
	$("#dataSelectListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataSelectListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td>"
			+ data.questionTypeName
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ data.score
			+ "</td><td>"
			+ data.difficulty
			+ "</td><td>"
			+ data.ken
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSelectQuestionByUse(" + data.id + ")'>选取</a>"
			+ "</td></tr>"
		);
	});
}

//显示数据列表(******1)
function showCategoryDataList(dataList) {
	
	$("#categoryData").empty();
	
	$.each(dataList, function(i, data) {
		$("#categoryData").append(
			"<dl class='course_choice1'>"
			+ "<dt><a class='' href='javascript:void(0)' onclick='btnMenu(" + data.id + ")'> "+ data.levelName +" </a></dt>"
			+ "</dl>"
		);
	});
	
}

//调整
function btnQuestionModify(id){

	showLoading(true);
	
	try {
		setEditFormQuestionData(id);
		showQuestionEditForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}


//菜单点击
function btnMenu(id){
	$("#questionCategoryId").val(id);
}

function btnSelectQuestionByUse(id){
	selectQuestionData(id);
	showSelectQuestionForm(false);
	queryDataList();
}

//添加
function createSelectQuestionData(flag) {
	
	var series = "";
	if(flag == true){
		var series = $("#qpSeries").text();
	}
	var questionCategoryId = PageUtility.getFormDataByString("edQuestionCategory", "题库", false);
	var questionType = PageUtility.getFormDataBySelect("edQType", "题型", false);
	var	difficulty = PageUtility.getFormDataBySelect("edDifficulty", "难度", true);
	var score = PageUtility.getFormDataBySelect("edScore", "分数", true);
	var number = PageUtility.getFormDataBySelect("edNumber", "数量", false);
	
	var paramters = { 
		"testPaperId": argTestPaperId,
		"series": series,
		"questionCategoryId": questionCategoryId,
		"questionType": questionType,
		"difficulty": difficulty,
		"score": score,
		"number": number
	};
	
	var result = PageUtility.ajaxAction(actionAutoSelectData, paramters);
	alert(result);
}

//获取查询参数集(******)
function getQueryParams() {
	
	var questionType = PageUtility.getQueryParamterByInputString("qpQuestionType");
	var difficulty = PageUtility.getQueryParamterByInputString("qpDifficulty");
	var ken = PageUtility.getQueryParamterByInputString("qpKen");
	var name = PageUtility.getQueryParamterByInputString("qpName");
	var series = $("#qpSeries").text();
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "questionType":questionType,
				   "difficulty":difficulty,
				   "ken":ken,
				   "name":name,
				   "testPaperId":argTestPaperId,
				   "series":series
				   };
	
	return result;
}

//获取查询参数集(******)
function getQuerySelectParams() {
	
	var questionType = PageUtility.getQueryParamterByInputString("qpCQuestionType");
	var difficulty = PageUtility.getQueryParamterByInputString("qpCDifficulty");
	var ken = PageUtility.getQueryParamterByInputString("qpCKen");
	var name = PageUtility.getQueryParamterByInputString("qpCName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "questionType":questionType,
				   "difficulty":difficulty,
				   "ken":ken, 
				   "name":name
				   };
	return result;
}

//试卷概况
function getSummaryQueryParams(){
	var series = $("#qpSeries").text();
	var result = { 
				   "testPaperId":argTestPaperId,
				   "series":series
				   };
	return result;
	
}

//删除
function btnQuestionRemove(id){
	
	if (!confirm("你确定要删除此记录吗？")) {
		return false;
	};

	showLoading(true);
	
	try {
		removeQuestionData(id);
		queryDataList();
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//查询分类数据树
function queryDataTreeByUse() {
	
	result = PageUtility.ajaxData(actionCategoryTree, "");
	
	var html = "";
	$.each(result,function(i,data){     
		
		html += "<option value=" + data.id + ">" + data.levelName + "</option>"
 	}); 
	
	return html;
}

//查询分类数据树
function queryDataTree() {
	
	result = PageUtility.ajaxData(actionCategoryTree, "");
	showCategoryDataList(result);
}

function browseTestPaper() {
	
	showLoading(true);
	
	try {
		var series = $("#qpSeries").text();
		var params = {"testPaperId":argTestPaperId,
				      "series":series 
				     };
		PageUtility.ajaxAction(actionBrowseTestPaper, params);
		
		var url = PageUtility.getContextPath() + "/MS-testPaperBrowse";
		window.open(url);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//导出到Word(******1)
function exportTestPaperToWord() {

	var url = actionExportTestPaperToWord + "?testPaperId=" + argTestPaperId + "&series="+$("#qpSeries").text();
	PageUtility.exportToFile(url);
}

//导出到文件(******1)
function exportQuestionToFile() {
	
	var url = actionQuestionExportToFile + "?testPaperId=" + argTestPaperId + "&series="+$("#qpSeries").text();
	PageUtility.exportToFile(url);
}


//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷题目</span>您正在浏览试卷题目记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷题目</span>您正在试卷题目成绩……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showSelectQuestionForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷题目</span>您正在浏览试卷题目记录列表……");
		$("#pageDataList").show();
		$("#pageEditSelectData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷</span>您正在选取试卷题目列表……");
		$("#pageDataList").hide();
		$("#pageEditSelectData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showQuestionEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷</span>您正在浏览试卷记录列表……");
		$("#pageDataList").show();
		$("#pageEditQuestionData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷</span>您正在编辑试卷题目列表……");
		$("#pageDataList").hide();
		$("#pageEditQuestionData").show();
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

//设置分页结果
function setPaginateResultByUse(result) {
	
	if (result.pageCount < pageNo) {
		pageNo = result.pageCount;
	}
			
	$("#lbPageCountUse").text(result.pageCount);
	$("#lbDataListCountUse").text(result.count);

	$("#btnPriorPageUse").attr("disabled", pageNo <= 1);
	$("#btnNextPageUse").attr("disabled", pageNo >= result.pageCount);
	
	var html = "";
	for(var i = 1; i <= result.pageCount; i++){
		 if(i == pageNo){
			html += "<option value="+i+" selected='selected'>第" + i + "页</option>"
		 } else{
		 	html += "<option value="+i+">第" + i + "页</option>";
		 }
	}
   	$("#selectPageNoListUse").html(html);
}

//显示/隐藏Loading图片
function showLoadingSelect(visible) {
	
	if (visible) {
		$("#loadingSelect").show();
	} else {
		$("#loadingSelect").hide();
	} 
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
	queryDataList();
}


