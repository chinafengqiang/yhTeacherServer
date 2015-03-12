/**  
 * 试卷管理
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/TestPaper/getTestPaperListBySearch.action";
var actionGetData = contextPath + "/TestPaper/getTestPaper.action";
var actionCreateData = contextPath + "/TestPaper/createTestPaper.action";
var actionModifyData = contextPath + "/TestPaper/modifyTestPaper.action";
var actionRemoveData = contextPath + "/TestPaper/removeTestPaper.action";
var actionImportFromFile = contextPath + "/TestPaper/importTestPaper.action";
var actionExportToFile = contextPath + "/TestPaper/exportTestPaper.action";
var actionSendData = contextPath + "/TestPaper/sendTestPaper.action";


/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 
	
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
	
	
	//点击导入按钮事件(******)
	$("#btnImportFromFile").upload({
        action: actionImportFromFile,
        name: 'file',
        iframeName: 'ImportFromFile',
        params: {},
        onSelect: function (self, element) {
            this.autoSubmit = false;
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
	
	//点击关闭按钮事件
	$("#btnSendClose").click(function() {
		
		showSendForm(false);
	});
	
		//点击保存按钮事件
	$("#btnSendSave").click(function() {
		
		showLoading(true);
		
		try {
			sendyData();
			showSendForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
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
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("TestPaperStatusEnum");
	var htmlQuestionSortType = PageUtility.ajaxGetEnumNameOptions("QuestionSortTypeEnum");
	var htmlQuestionOptionsSortType = PageUtility.ajaxGetEnumNameOptions("QuestionOptionsSortTypeEnum");
	var htmlClass = PageUtility.ajaxGetClassNameOptions();
	
	$("#qpCategory").html(htmlCategory);
	$("#edCategory").html(htmlCategory);
	$("#qpStatus").html(htmlStatus);
	
	$("#edStatus").html(htmlStatus);
	$("#edQuestionSortType").html(htmlQuestionSortType);
	$("#edQuestionOptionsSortType").html(htmlQuestionOptionsSortType);
	
	$("#edClassType").html(htmlClass);
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edName").val("");
		$("#edClassType").val("");
		$("#edCategory").val("");
		
		$("#edJudgeScore").val("");
		$("#edJudgeNum").val("");
		$("#edJudgeSortFlag").val("");
		$("#edJudgeNote").val("");
		$("#edMultipleScore").val("");
		$("#edMultipleNum").val("");
		$("#edMultipleSortFlag").val("");
		$("#edMultipleNote").val("");
		$("#edSingleScore").val("");
		$("#edSingleNum").val("");
		$("#edSingleSortFlag").val("");
		$("#edSingleNote").val("");
		$("#edCanQueryAnswer").attr("checked", false);
		
		$("#edNoSelectScore").val("");
		$("#edNoSelectNum").val("");
		$("#edNoSelectFlag").val("");
		$("#edNoSelectNote").val("");
		
		$("#edTotalScore").val("100");
		$("#edTotalSeries").val("1");
		$("#edPassScore").val("60");
		$("#edStatus").val("1");
		$("#edQuestionSortType").val("0");
		$("#edQuestionOptionsSortType").val("0");
		$("#edCanIgnoreScore").prop("checked", true);
		
	} else {
		
		var paramters = {"testPaper.id": id};
		data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edClassType").val(data.description);
		$("#edCategory").val(data.category);
		$("#edTotalScore").val(data.totalScore);
		$("#edTotalSeries").val(data.totalSeries);
		$("#edPassScore").val(data.passScore);
		$("#edStatus").val(data.status);
		$("#edQuestionSortType").val(data.questionSortType);
		$("#edQuestionOptionsSortType").val(data.questionOptionsSortType);
		$("#edCanIgnoreScore").prop("checked", data.canIgnoreQuestionScore);
		$("#edCanQueryAnswer").prop("checked", data.canQueryAnswer);
		
		fromToJson(data.testPaperOptions);
	}
}



function setSendFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
	} else {
		
		var paramters = {"testPaper.id": id};
		data = PageUtility.ajaxData(actionGetData, paramters);
		var times = data.createdTime;
		var timef = (new Date()).Format("yyyy-MM-dd hh:mm:ss"); 
		$("#beginTime").val(timef);
		$("#endTime").val(timef);
		
	}
}

function fromToJson(fromJson){
	var json_obj = $.evalJSON(fromJson);
 
					$("#edNoSelectScore").val("");
					$("#edNoSelectNum").val("");
					$("#edNoSelectFlag").val("");
					$("#edNoSelectNote").val("");
					$("#edJudgeScore").val("");
					$("#edJudgeNum").val("");
					$("#edJudgeSortFlag").val("");
					$("#edJudgeNote").val("");
					$("#edMultipleScore").val("");
					$("#edMultipleNum").val("");
					$("#edMultipleSortFlag").val("");
					$("#edMultipleNote").val("");
					$("#edSingleScore").val("");
					$("#edSingleNum").val("");
					$("#edSingleSortFlag").val("");
					$("#edSingleNote").val("");
		for(var i in json_obj){
			
					
			if(json_obj[i].questionType == 3){
		
				$("#edNoSelectScore").val(json_obj[i].score);
				$("#edNoSelectNum").val(json_obj[i].number);
				$("#edNoSelectFlag").val(json_obj[i].sortFlag);
				$("#edNoSelectNote").val(json_obj[i].note);
			}
				
			if(json_obj[i].questionType == 2){
				$("#edJudgeScore").val(json_obj[i].score);
				$("#edJudgeNum").val(json_obj[i].number);
				$("#edJudgeSortFlag").val(json_obj[i].sortFlag);
				$("#edJudgeNote").val(json_obj[i].note);
			}
			if(json_obj[i].questionType == 0){
				$("#edSingleScore").val(json_obj[i].score);
				$("#edSingleNum").val(json_obj[i].number);
				$("#edSingleSortFlag").val(json_obj[i].sortFlag);
				$("#edSingleNote").val(json_obj[i].note);
			}
			if(json_obj[i].questionType == 1){
				$("#edMultipleScore").val(json_obj[i].score);
				$("#edMultipleNum").val(json_obj[i].number);
				$("#edMultipleSortFlag").val(json_obj[i].sortFlag);
				$("#edMultipleNote").val(json_obj[i].note);
			}
			
		}
}

//非选题
function noSelectJson(){
	var noSelectScore = PageUtility.getFormDataByString("edNoSelectScore", "每题分数", true);
	var noSelectNum = PageUtility.getFormDataByString("edNoSelectNum", "题目数量", true);
	var noSelectFlag = PageUtility.getFormDataByString("edNoSelectFlag", "出题顺序", true);
	var noSelectNote = PageUtility.getFormDataByString("edNoSelectNote", "说明", true, 50);
	
	if(noSelectScore == null){
		return "";
	}
		
	var obj = {
		"questionType" : 3,
		"score":noSelectScore,
		"number":noSelectNum,
		"sortFlag":noSelectFlag,
		"note":noSelectNote
	};
	
	var obj_json = $.toJSON(obj);
	
	return obj_json;
}


//判断题
function judgeJson(){
	var judgeScore = PageUtility.getFormDataByString("edJudgeScore", "每题分数", true);
	var judgeNum = PageUtility.getFormDataByString("edJudgeNum", "题目数量", true);
	var judgeSortFlag = PageUtility.getFormDataByString("edJudgeSortFlag", "出题顺序", true);
	var judgeNote = PageUtility.getFormDataByString("edJudgeNote", "说明", true, 50);
	
	if(judgeScore == null){
		return "";
	}
		
	var obj = {
		"questionType" : 2,
		"score":judgeScore,
		"number":judgeNum,
		"sortFlag":judgeSortFlag,
		"note":judgeNote
	};
	
	var obj_json = $.toJSON(obj);
	
	return obj_json;
}
//单选题
function singleJson(){
	var singleScore = PageUtility.getFormDataByString("edSingleScore", "每题分数", true);
	var singleNum = PageUtility.getFormDataByString("edSingleNum", "题目数量", true);
	var singleSortFlag = PageUtility.getFormDataByString("edSingleSortFlag", "出题顺序", true);
	var singleNote = PageUtility.getFormDataByString("edSingleNote", "说明", true, 50);
	
	if(singleScore == null){
		return "";
	}
	
	var obj = {
		"questionType" : 0,
		"score":singleScore,
		"number":singleNum,
		"sortFlag":singleSortFlag,
		"note":singleNote
	};
	
	var obj_json = $.toJSON(obj);
	
	return obj_json;
}

//多选题
function multipleJson(){
	var multipleScore = PageUtility.getFormDataByString("edMultipleScore", "每题分数", true);
	var multipleNum = PageUtility.getFormDataByString("edMultipleNum", "题目数量", true);
	var multipleSortFlag = PageUtility.getFormDataByString("edMultipleSortFlag", "出题顺序", true);
	var multipleNote = PageUtility.getFormDataByString("edMultipleNote", "说明", true, 50);
	
	if(multipleScore == null){
		return "";
	}
	
	var obj = {
		"questionType" : 1,
		"score":multipleScore,
		"number":multipleNum,
		"sortFlag":multipleSortFlag,
		"note":multipleNote
	};
	
	var obj_json = $.toJSON(obj);
	
	return obj_json;
}

//创建数据(******1)
function createData() {
	
	var classId = PageUtility.getFormDataBySelect("edClassType", "班级", false);
	
	var name = PageUtility.getFormDataByString("edName", "试卷名称", false, 100);
	var category = PageUtility.getFormDataBySelect("edCategory", "试卷分类", false);
	var totalScore = PageUtility.getFormDataBySelect("edTotalScore", "试卷总分", false);
	var totalSeries = PageUtility.getFormDataBySelect("edTotalSeries", "出题套数", false);
	var passScore = PageUtility.getFormDataBySelect("edPassScore", "通过分数", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var questionSortType = PageUtility.getFormDataByString("edQuestionSortType", "题目排序", false);
	var questionOptionsSortType = PageUtility.getFormDataByString("edQuestionOptionsSortType", "选项排序", false);
	var canIgnoreQuestionScore = $("#edCanIgnoreScore").is(':checked');
	var canQueryAnswer = $("#edCanQueryAnswer").is(':checked');
	
	var testPaperOptions = "[";
	if(judgeJson() != ''){
		testPaperOptions += judgeJson() + ","
	}
	if(singleJson() != ''){
		testPaperOptions += singleJson() + ","
	}
	if(multipleJson() != ''){
		testPaperOptions += multipleJson() + ","
	}
	if(noSelectJson() != ''){
		testPaperOptions += noSelectJson() + ","
	}
	var endOptons = "]";
	
	var newOptions = testPaperOptions + endOptons;
	var realOptions = newOptions.substring(0, newOptions.length -2) + endOptons;
	
//	alert(testPaperOptions + endOptons);
//	var testPaperOptions = "[" + judgeJson() + "," + singleJson() + "," +  multipleJson() + ","+ noSelectJson() + "]";
	

  
  var paramters = { 
		"testPaper.name": name,
		"testPaper.description": classId,
		"testPaper.category": category,
		"testPaper.totalScore": totalScore,
		"testPaper.totalSeries": totalSeries,
		"testPaper.passScore": passScore,
		"testPaper.status": status,
		"testPaper.credit": 0,
		"testPaper.questionSortType": questionSortType,
		"testPaper.questionOptionsSortType": questionOptionsSortType,
		"testPaper.testPaperOptions": realOptions,
		"testPaper.canIgnoreQuestionScore": canIgnoreQuestionScore,
		"testPaper.canQueryAnswer": canQueryAnswer
	};
	
   var result = PageUtility.ajaxAction(actionCreateData, paramters);
	
	
}

//修改数据(*****11*)
function modifyData() {
	
	var classId = PageUtility.getFormDataBySelect("edClassType", "班级", false);
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "试卷名称", false, 100);
	var category = PageUtility.getFormDataBySelect("edCategory", "试卷分类", false);
	var totalScore = PageUtility.getFormDataBySelect("edTotalScore", "试卷总分", false);
	var totalSeries = PageUtility.getFormDataBySelect("edTotalSeries", "出题套数", false);
	var passScore = PageUtility.getFormDataBySelect("edPassScore", "通过分数", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var questionSortType = PageUtility.getFormDataByString("edQuestionSortType", "题目排序", false);
	var questionOptionsSortType = PageUtility.getFormDataByString("edQuestionOptionsSortType", "选项排序", false);
	var canIgnoreQuestionScore = $("#edCanIgnoreScore").is(':checked');
//	var testPaperOptions = "[" + judgeJson() + "," + singleJson() + "," +  multipleJson() + ","+ noSelectJson() + "]";
	var canQueryAnswer = getChecked("edCanQueryAnswer");
	
	var testPaperOptions = "[";
	if(judgeJson() != ''){
		testPaperOptions += judgeJson() + ","
	}
	if(singleJson() != ''){
		testPaperOptions += singleJson() + ","
	}
	if(multipleJson() != ''){
		testPaperOptions += multipleJson() + ","
	}
	if(noSelectJson() != ''){
		testPaperOptions += noSelectJson() + ","
	}
	var endOptons = "]";
	
	var newOptions = testPaperOptions + endOptons;
	var realOptions = newOptions.substring(0, newOptions.length -2) + endOptons;
	
	
	
	var paramters = { 
		"testPaper.id": id,
		"testPaper.name": name,
		"testPaper.description": classId,
		"testPaper.category": category,
		"testPaper.totalScore": totalScore,
		"testPaper.totalSeries": totalSeries,
		"testPaper.passScore": passScore,
		"testPaper.status": status,
		"testPaper.credit": 0,
		"testPaper.questionSortType": questionSortType,
		"testPaper.questionOptionsSortType": questionOptionsSortType,
		"testPaper.testPaperOptions": realOptions,
		"testPaper.canIgnoreQuestionScore": canIgnoreQuestionScore,
		"testPaper.canQueryAnswer": canQueryAnswer
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}


function sendyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var beginTime = PageUtility.getFormDataByString("beginTime", "编号", false);
	var endTime =  PageUtility.getFormDataByString("endTime", "编号", false);
	
	var paramters = { 
		"testPaper.id": id,
		"beginTime": beginTime,
		"endTime": endTime,
	};
	
	var result = PageUtility.ajaxAction(actionSendData, paramters);
	
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"testPaper.id": id
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
			+ "</td><td>"
			+ getCategoryName(data.category)
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
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnQuestion(" + data.id + ", "+data.totalSeries +")'>题目</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnExportToFile(" + data.id + ")'>导出</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSendToFile(" + data.id + ")'>推送</a>"
			+ "</td></tr>"
		);
	});
}

//
function btnSendToFile(id) {

	showLoading(true);
	
	try {
		setSendFormData(id);
		showSendForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//获得分类名称
function getCategoryName(id){
	var datas = PageUtility.ajaxGetSysParamList("TestPaperCategory");
	
	for(var i = 0; i < datas.length; i++){   
		if(id == i){
			return datas[i];
		}	
 	}
}

function btnQuestion(id, totalSeries){
	
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/testPaperQuestion.jsp?testPaperId=" + id + "&totalSeries="+ totalSeries +"&timestamp=" + timeStamp;
	
	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var category = PageUtility.getQueryParamterByInputString("qpCategory");
	var status = PageUtility.getQueryParamterByInputString("qpStatus");
	var name = PageUtility.getQueryParamterByInputString("qpName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "name":name,
				   "status":status,
				   "category":category
				   };
	return result;
}


//导出到文件(******1)
function exportToFile(id) {
	
	var url = actionExportToFile + "?testPaper.id=" + id;
	PageUtility.exportToFile(url);
}


//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷</span>您正在浏览试卷记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷</span>您正在编辑试卷……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showSendForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>试卷</span>您正在浏览试卷记录列表……");
		$("#pageDataList").show();
		$("#pageSendData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷</span>您正在推送试卷……");
		$("#pageDataList").hide();
		$("#pageSendData").show();
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

function getChecked(edName) {
	
	var checkValue = false;
	
	if($("input[name='"+edName+"']:checkbox:checked").length > 0) {
		checkValue = true;
	} 
	return checkValue;
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

 
