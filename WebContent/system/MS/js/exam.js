/**  
 * 考试管理
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Exam/getExamListBySearch.action";
var actionGetData = contextPath + "/Exam/getExam.action";
var actionCreateData = contextPath + "/Exam/createExam.action";
var actionModifyData = contextPath + "/Exam/modifyExam.action";
var actionRemoveData = contextPath + "/Exam/removeExam.action";
var actionImportFromFile = contextPath + "/Exam/importExam.action";
var actionExportToFile = contextPath + "/Exam/exportExam.action";
var actionUploadImage = contextPath + "/Exam/uploadArticleImage.action";
var actionSelectDataList = contextPath + "/TestPaper/getTestPaperListByExamSelect.action";
var actionGetTestPaperData = contextPath + "/TestPaper/getTestPaper.action";

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
	
	//点击导入图片按钮事件(******)
	$("#btnUploadImage").upload({
        action: actionUploadImage,
        name: 'file',
        iframeName: 'UploadImage',
        params: {},
        onSelect: function (self, element) {
            this.autoSubmit = false;
			this.submit();
        },
        onSubmit: function (self, element) {
        	showLoading(true);
        },
        onComplete: function (data, self, element) {
        	showLoading(false);
        	var dataObject =  eval('(' + data + ')');
        	
        	if (dataObject.actionStatus == 'success') {
        		PageUtility.showUploadImagePopup(dataObject.data);
        	} else {
        		alert(dataObject.actionMessage);
        	}
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
	
	//规定开考有效时间段
	$("#edCanLimitValidTime").click(function() {
		
		if($("input[name='edCanLimitValidTime']:checkbox:checked").length > 0) {
			$("#beginShow").show();
			$("#endShow").show();
			$("#edCanLimitCommitTime").prop('checked', false);
		} else{
			$("#beginShow").hide();
			$("#endShow").hide();
			$("#edCanLimitCommitTime").prop('checked', false);
		}
	});
	
	//允许统一交卷
	$("#edCanLimitCommitTime").click(function() {
		
		if($("input[name='edCanLimitCommitTime']:checkbox:checked").length > 0) {
			$("#edCanLimitValidTime").prop('checked', true);	
			$("#beginShow").show();
			$("#endShow").hide();
		} else{
			$("#beginShow").show();
			$("#endShow").show();
		}
	});
	
	//考试计时
	$("#edTimerModeTime").click(function() {
		
		$("#timeCount").show();
	});
	
	//考试计时
	$("#edNoTimerModeTime").click(function() {
		
		$("#timeCount").hide();
	});
	
	$("#edBeginName").val(new Date());
	$("#edEndName").val(new Date());
		
	//点击关闭按钮事件
	$("#btnClose").click(function() {
		
		showEditForm(false);
	});
	
	//选取试卷
	$("#selectTestPaper").click(function() {
		
		var timeStamp = (new Date()).valueOf();
	    var url = contextPath + "/system/MS/testPaperSelect.jsp?timestamp=" + timeStamp;

		$('#iframeSubPage').attr('src',url);
	 	$("#mainPage").hide();
	 	$("#subPage").show();
	 	
	});
	
	//点击关闭按钮事件
	$("#btnSelectClose").click(function() {
		
		showSelectForm(false);
	});
	
	//初始化页面
	initPage();
	
  	$("#edCanMatchDutyRank").click(function() {
		
		if($("input[name='edCanMatchDutyRank']:checkbox:checked").length > 0) {
				 $("#edSysParamType").show();
			}else{
				 $("#edSysParamType").hide();
			}
	});
  	
  	$("#edCanMatchTrade").click(function() {
		
		if($("input[name='edCanMatchTrade']:checkbox:checked").length > 0) {
				 $("#edTrade").show();
			}else{
				 $("#edTrade").hide();
			}
	});
	
})


$("body").ready(function(){

$('#edBeginName').val((new Date()).Format("yyyy-MM-dd"));
$('#edEndName').val((new Date()).Format("yyyy-MM-dd"));

	//应用方法
	$("input.date").jSelectDate({
		css:"date",
		yearBeign: 1995,
		disabled : false,
		showLabel : false
	});

})

$(this).jSelectDate

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
		querySelectDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {
	
	var htmlCategory = PageUtility.ajaxGetSysParamOptions("ExamCategory");
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("ExamStatusEnum");
	var htmlExamMode = PageUtility.ajaxGetEnumNameOptions("ExamModeEnum");
	var htmlQuestionFetchType = PageUtility.ajaxGetEnumNameOptions("QuestionFetchTypeEnum");
	var htmlDutyRank = PageUtility.ajaxGetEnumNameCheckName("DutyRank","checkDuty");
	var htmlTrade = PageUtility.ajaxGetEnumNameCheckName("Trade","checkTrade");

	$("#qpStatus").html(htmlStatus);
	$("#qpCategory").html(htmlCategory);
	$("#edCategory").html(htmlCategory);
	
	$("#edStatus").html(htmlStatus);
	$("#edExamMode").html(htmlExamMode);
	$("#edQuestionFetchType").html(htmlQuestionFetchType);
	
	$("#edSysParamType").html(htmlDutyRank);
	$("#edTrade").html(htmlTrade);
}

function setTimeHour(){
	
	var htmlHour = "";
	var htmlMinute = "";
	for (var i = 0; i < 24; i++) {
		
		if(i < 10){
			htmlHour += "<option value=0"  +i + ">" + i + "</option>"
		} else {
			htmlHour += "<option value=" + i + ">" + i + "</option>"
		}
	}
	
	$("#beginHour").html(htmlHour);
	$("#endHour").html(htmlHour);
		
	for (var i = 0; i < 60; i++) {
		if(i < 10){
			htmlMinute += "<option value=0" + i + ">" + i + "</option>"
		} else {
			htmlMinute += "<option value=" + i + ">" + i + "</option>"
		}
	}
	
	$("#beginMinute").html(htmlMinute);
	$("#endMinute").html(htmlMinute);
	
}	


//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	setTimeHour();
	if (id == null) {
		$("#edName").val("");
		$("#edDescription").val("");
		$("#edTestPaperId").val("");
		$("#edQuestionFetchType").val("0");
		$("#edCategory").val("");
		$("#edExamCode").val("");
		$("#edExamMode").val("0");
        $("#edTimerLimit").val("");
        $("#edTestPaperName").val("");
        
        $("#edCanKeepSecretScore").attr("checked", false);
        $("#edCanQueryAnswer").attr("checked", false);
        $("#edCanAllowAllUser").attr("checked", false);
        $("#edCanCourseStudyLimit").attr("checked", false);
        $("#edCanMatchDutyRank").attr("checked", false);
		$("#edCanMatchTrade").attr("checked", false);
		$("#edCanLimitValidTime").attr("checked", false);
		$("#edCanLimitCommitTime").attr("checked", false);
		$("#edCanAllowMultiJoin").attr("checked", false);

		$("#edExamCode").attr("readonly", false);
		$("#edExamCode").removeClass("inputReadOnly");
		$("#edExamMode").attr("readonly", false);
		$("#edExamMode").removeClass("inputReadOnly");

		clearCheckValue("checkDuty");
		clearCheckValue("checkTrade")
		$('#edExamCode').attr("disabled",false);
		
		$("#edNoTimerModeTime").prop('checked' ,true);
		$("#edTimerModeTime").prop('checked' ,false);
		
		$("#beginShow").hide();
		$("#endShow").hide();
	} else {
		var paramters = {"exam.id": id};
		var data = PageUtility.ajaxData(actionGetData, paramters);
		var paramtersTest = {"testPaper.id": data.testPaperId};
		datas = PageUtility.ajaxData(actionGetTestPaperData, paramtersTest);
		
		$("#edName").val(data.name);
		$("#edDescription").val(data.description);
		$("#edTestPaperId").val(data.testPaperId);
		$("#edTestPaperName").val(datas.name);
		$("#edQuestionFetchType").val(data.questionFetchType);
		$("#edCategory").val(data.category);
		$("#edExamCode").val(data.examCode);
		$("#edExamMode").val(data.examMode);
        $("#edTimerLimit").val(data.timerLimit);
        
        $("#edExamCode").attr("readonly", true);
		$("#edExamCode").addClass("inputReadOnly");
        $("#edExamMode").attr("readonly", true);
		$("#edExamMode").addClass("inputReadOnly");
		
		checkBoxChecked(data);
        $('#edExamMode').attr("disabled","disabled");
	}
}

function checkBoxChecked(data){
	  
	$("#edCanKeepSecretScore").prop("checked", data.canKeepSecretScore);
	$("#edCanQueryAnswer").prop("checked", data.canQueryAnswer);
	$("#edCanAllowAllUser").prop("checked", data.canAllowAllUser);
	$("#edCanCourseStudyLimit").prop("checked", data.canCourseStudyLimit);
	$("#edCanAllowMultiJoin").prop("checked", data.canAllowMultiJoin);
        
	var htmlDutyRank = PageUtility.ajaxGetEnumNameCheckName("DutyRank","checkDuty");
	var htmlTrade = PageUtility.ajaxGetEnumNameCheckName("Trade","checkTrade");
	$("#edSysParamType").html(htmlDutyRank);
	$("#edTrade").html(htmlTrade);
		
	$("#edCanMatchDutyRank").prop("checked", data.canMatchDutyRank);
    if(data.canMatchDutyRank == true){
		getAllValue(data.dutyRank,"checkDuty");
		$("#edSysParamType").show();
	} else {
		$("#edSysParamType").hide();
	}
		
    $("#edCanMatchTrade").prop("checked", data.canMatchTrade);
	if(data.canMatchTrade == true){
		getAllValue(data.trade,"checkTrade");
		 $("#edTrade").show();
	} else {
		$("#edTrade").hide();
	}
		
	if(data.timerMode == 0){
		$("#edNoTimerModeTime").prop('checked' ,true);
		$("#edTimerModeTime").prop('checked' ,false);
		$("#edTimerLimit").val(0);
		$("#timeCount").hide();
	} else{
		$("#edTimerModeTime").prop('checked' ,true);
		$("#edNoTimerModeTime").prop('checked' ,false);
		$("#edTimerLimit").val(data.timerLimit);
		$("#timeCount").show();
	}
		
		if(data.canLimitValidTime == true){
			$("input[name='edCanLimitValidTime']").prop("checked",true);
			$("#beginShow").show();
			$("#endShow").show();
			
			if (data.canLimitCommitTime == true) {
				$("#endShow").hide();
			}
			
			var firstTime = data.validFirstTime;
			if(firstTime != null && firstTime.indexOf("T")){
				var beginName = firstTime.substring(0,firstTime.indexOf("T"));
				$('#edBeginName').val(beginName);
				var overName = firstTime.substring(firstTime.indexOf("T") + 1, firstTime.length);
				var beginHourAndMitue = overName.split(":");
				var beginHours = beginHourAndMitue[0];
				$('#beginHour').val(beginHours);
				$('#beginMinute').val(beginHourAndMitue[1]);
			}
			
			var endTime = data.validLastTime;
			if(endTime != null && endTime.indexOf("T")){
				var endName = endTime.substring(0,firstTime.indexOf("T"));
				$('#edEndName').val(endName);
				var lastName = endTime.substring(endTime.indexOf("T") + 1, endTime.length);
				var endHourAndMitue = lastName.split(":");
				var endHours = endHourAndMitue[0];
				$('#endHour').val(endHours);
				$('#endMinute').val(endHourAndMitue[1]);
			}
		
		} else {
			$("input[name='edCanLimitValidTime']").removeAttr("checked");
			$("#beginShow").hide();
			$("#endShow").hide();
		}
		
		if(data.canLimitCommitTime == true){
			$("input[name='edCanLimitCommitTime']").prop("checked",true);
			$("#beginShow").show();
			
			var firstTime = data.validFirstTime;
			if(firstTime.indexOf("T")){
				var beginName = firstTime.substring(0,firstTime.indexOf("T"));
				$('#edBeginName').val(beginName);
				var overName = firstTime.substring(firstTime.indexOf("T") + 1, firstTime.length);
				var beginHourAndMitue = overName.split(":");
				var beginHours = beginHourAndMitue[0];
				$('#beginHour').val(beginHours);
				$('#beginMinute').val(beginHourAndMitue[1]);
			}
		} else{
			$("input[name='edCanLimitCommitTime']").removeAttr("checked");
		}
		
}

function getChecked(edName) {
	
	var checkValue = false;
	
	if($("input[name='"+edName+"']:checkbox:checked").length > 0) {
		checkValue = true;
	} 
	return checkValue;
}

function getCheckedValue(edName) {
	
	if ($("input[name='"+edName+"']").length > 0) {
		  $("input[name='"+edName+"']").attr("checked",true);
		  
	} 
}

function getCheckeRadio(edName) {
	
	var checkValue = false;
	
	if($("input[name='"+edName+"']:radio:checked").length > 0) {
		checkValue = true;
	} 
	return checkValue;
}

//创建数据(******1)
function createData() {
	var timerLimit = "";
	var name = PageUtility.getFormDataByString("edName", "考试名称", false, 100);
	var description = PageUtility.getFormDataByString("edDescription", "考试说明", false, 200);
	var testPaperId = PageUtility.getFormDataByString("edTestPaperId", "试卷编号", false);
	var questionFetchType = PageUtility.getFormDataBySelect("edQuestionFetchType", "题目随机", false);
	var category = PageUtility.getFormDataBySelect("edCategory", "考试分类", true);
	var examCode = PageUtility.getFormDataByString("edExamCode", "考试代号", false);
	var examMode = PageUtility.getFormDataBySelect("edExamMode", "考试方式", false);
	
	var canKeepSecretScore = $("#edCanKeepSecretScore").is(':checked');
	var canQueryAnswer = $("#edCanQueryAnswer").is(':checked');
	var canAllowAllUser = $("#edCanAllowAllUser").is(':checked');
	var canCourseStudyLimit = $("#edCanCourseStudyLimit").is(':checked');
	var canMatchDutyRank = $("#edCanMatchDutyRank").is(':checked');
	var canMatchTrade = $("#edCanMatchTrade").is(':checked');
	var canAllowMultiJoin = $("#edCanMatchTrade").is(':checked');
	
	var dutyRank = getCheckValue("checkDuty");
	var trade = getCheckValue("checkTrade");
	var timerMode = getRadioValue("edTimerMode");
	if(timerMode == 0){
		timerLimit = 0;
	} else {
		timerLimit = PageUtility.getFormDataByString("edTimerLimit", "", true);
	}
	var canLimitValidTime = $("#edCanLimitValidTime").is(':checked');
	var canLimitCommitTime = $("#edCanLimitCommitTime").is(':checked');
	
	var beginTime = PageUtility.getFormDataByString("edBeginName", "", true);
	var beginHour = PageUtility.getFormDataByString("beginHour", "", true);
	var beginMinute = PageUtility.getFormDataByString("beginMinute", "", true);
	
	var endTime = PageUtility.getFormDataByString("edEndName", "", true);
	var endHour = PageUtility.getFormDataByString("endHour", "", true);
	var endMinute = PageUtility.getFormDataByString("endMinute", "", true);
	
	var validFirstTime = beginTime + " " + beginHour + ":" + beginMinute + ":0";
	var validLastTime = endTime + " " + endHour + ":" + endMinute + ":0";

	var paramters = { 
		"exam.name": name,
		"exam.description": description,
		"exam.testPaperId": testPaperId,
		"exam.questionFetchType": questionFetchType,
		"exam.category": category,
		"exam.examCode": examCode,
		"exam.examMode": examMode,
		"exam.canKeepSecretScore": canKeepSecretScore,
		"exam.canQueryAnswer": canQueryAnswer,
		"exam.canAllowAllUser": canAllowAllUser,
		"exam.canCourseStudyLimit": canCourseStudyLimit,
		"exam.canMatchDutyRank": canMatchDutyRank,
		"exam.canMatchTrade": canMatchTrade,
		"exam.canAllowMultiJoin" : canAllowMultiJoin,
		"exam.dutyRank": dutyRank,
		"exam.trade": trade,
		"exam.timerMode": timerMode,
		"exam.timerLimit": timerLimit,
		"exam.canLimitValidTime": canLimitValidTime,
		"exam.canLimitCommitTime": canLimitCommitTime,
		"exam.validFirstTime": validFirstTime,
		"exam.validLastTime": validLastTime
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//修改数据(*****11*)
function modifyData() {
	var timerLimit = "";
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "考试名称", false, 100);
	var description = PageUtility.getFormDataByString("edDescription", "考试说明", false, 200);
	var testPaperId = PageUtility.getFormDataByString("edTestPaperId", "试卷编号", false);
	var questionFetchType = PageUtility.getFormDataBySelect("edQuestionFetchType", "题目随机", false);
	var category = PageUtility.getFormDataBySelect("edCategory", "考试分类", true);
	var examCode = PageUtility.getFormDataByString("edExamCode", "考试代号", false);
	var examMode = PageUtility.getFormDataBySelect("edExamMode", "考试方式", false);
	
	var canKeepSecretScore = getChecked("edCanKeepSecretScore");
	var canQueryAnswer = getChecked("edCanQueryAnswer");
	var canAllowAllUser = getChecked("edCanAllowAllUser");
	var canCourseStudyLimit = getChecked("edCanCourseStudyLimit");
	var canMatchDutyRank = getChecked("edCanMatchDutyRank");
	var canMatchTrade = getChecked("edCanMatchTrade");
	var canAllowMultiJoin = getChecked("edCanAllowMultiJoin");
	
	var dutyRank = getCheckValue("checkDuty");
	var trade = getCheckValue("checkTrade");
	var timerMode = getRadioValue("edTimerMode");
	if(timerMode == 0){
		timerLimit = 0;
	} else {
		timerLimit = PageUtility.getFormDataByString("edTimerLimit", "", true);
	}
	var canLimitValidTime = getChecked("edCanLimitValidTime");
	var canLimitCommitTime = getChecked("edCanLimitCommitTime");
	
	var beginTime = PageUtility.getFormDataByString("edBeginName", "", true);
	var beginHour = PageUtility.getFormDataByString("beginHour", "", true);
	var beginMinute = PageUtility.getFormDataByString("beginMinute", "", true);
	
	var endTime = PageUtility.getFormDataByString("edEndName", "", true);
	var endHour = PageUtility.getFormDataByString("endHour", "", true);
	var endMinute = PageUtility.getFormDataByString("endMinute", "", true);
	
	var validFirstTime = beginTime + " " + beginHour + ":" + beginMinute + ":00";
	var validLastTime = endTime + " " + endHour + ":" + endMinute + ":00";

	var paramters = { 
		"exam.id": id,
		"exam.name": name,
		"exam.description": description,
		"exam.testPaperId": testPaperId,
		"exam.questionFetchType": questionFetchType,
		"exam.category": category,
		"exam.examCode": examCode,
		"exam.examMode": examMode,
		"exam.canKeepSecretScore": canKeepSecretScore,
		"exam.canQueryAnswer": canQueryAnswer,
		"exam.canAllowAllUser": canAllowAllUser,
		"exam.canCourseStudyLimit": canCourseStudyLimit,
		"exam.canMatchDutyRank": canMatchDutyRank,
		"exam.canMatchTrade": canMatchTrade,
		"exam.canAllowMultiJoin" : canAllowMultiJoin,
		"exam.dutyRank": dutyRank,
		"exam.trade": trade,
		"exam.timerMode": timerMode,
		"exam.timerLimit": timerLimit,
		"exam.canLimitValidTime": canLimitValidTime,
		"exam.canLimitCommitTime": canLimitCommitTime,
		"exam.validFirstTime": validFirstTime,
		"exam.validLastTime": validLastTime
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"exam.id": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

function getCheckValue(name){
	
	var checkValue = '';
	
	if($("input[name='"+name+"']:checkbox:checked").length > 0) {
			$("input[name='"+name+"']:checkbox:checked").each(function() {
				checkValue += $(this).val() + ';';
			})
	}
	
	return checkValue;
	
}

function getRadioValue(name){
	
	var checkValue = '';
	
	if($("input[name='"+name+"']:radio:checked").length > 0) {
			$("input[name='"+name+"']:radio:checked").each(function() {
				checkValue += $(this).val();
			})
	}
	
	return checkValue;
	
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
}

function querySelectDataList() {
	
	result = PageUtility.ajaxData(actionSelectDataList, getQuerySelectParams());
	showSelectDataList(result.list);
	setPaginateResultByUse(result);
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
			+ data.examSummary
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnMaker(" + data.id + ")'>安排</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnMonitoring(" + data.id + ")'>监控</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnControl(" + data.id + ")'>控制台</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnExportToFile(" + data.id + ")'>导出</a>"
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
			+ data.category
			+ "</td><td>"
			+ data.name
			+ "</td><td>"
			+ data.totalScore
			+ "</td><td>"
			+ data.passScore
			+ "</td><td>"
			+ data.credit
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnSelectTestPaper(" + data.id + ", \"" + data.name + "\")'>选取</a>"
			+ "</td></tr>"
		);
	});
}


//选择
function btnSelectTestPaper(id, name){
	$("#edTestPaperId").val(id);
	$("#edTestPaperName").val(name);
	showSelectForm(false);
}

//安排
function btnMaker(id){
	
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/examModifyUserJoinedData.jsp?examId=" + id + "&timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
}

//监控
function btnMonitoring(id){
		
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/examMonitoring.jsp?examId=" + id + "&timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();

}

//控制台
function btnControl(id){
	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/examControl.jsp?examId=" + id + "&timestamp=" + timeStamp;

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
				   "category":category,
				   "status":status,
				   "name":name
				   };
	return result;
}

function getQuerySelectParams() {
	
	var category = PageUtility.getQueryParamterByInputString("qpCategory");
	var name = PageUtility.getQueryParamterByInputString("qpcName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "category":category,
				   "name":name
				   };
	return result;
}


//导出到文件(******1)
function exportToFile(id) {
	
	var url = actionExportToFile + "?examId=" + id;
	PageUtility.exportToFile(url);
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试</span>您正在浏览考试记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试</span>您正在编辑考试……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showSelectForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试</span>您正在浏览考试记录列表……");
		$("#pageEditData").show();
		$("#pageEditSelectData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>试卷</span>您正在选取试卷列表……");
		$("#pageEditData").hide();
		$("#pageEditSelectData").show();
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
//取消选中
function clearCheckValue(checkName){
	if($("input[name='"+checkName+"']:checkbox:checked").length > 0) {
			$("input[name='"+checkName+"']:checkbox:checked").each(function() {
				 $(this).prop("checked",false);
			})
		} 
}

//显示/隐藏Loading图片
function showLoadingSelect(visible) {
	
	if (visible) {
		$("#loadingSelect").show();
	} else {
		$("#loadingSelect").hide();
	} 
}

function getAllValue(sysParamType,checkName){
	
	if ($("input[name='"+checkName+"']").length > 0) {
			var box = '';
			$("input[name='"+checkName+"']").each(function() {
				box = $(this).val();
				var checkList = sysParamType.split(";");
				var  flag = false;
				
				for(var i = 0; i < checkList.length; i++){
					
					if(checkList[i] == box){
						flag = true;
					}
					
				}
				
				if(flag){
					$(this).attr("checked",true);
				}else {
					$(this).attr("checked",false);
				}
			})
		} 
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


// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//返回到主页面
function backFromSubPage() {
	
	$("#subPage").hide();
	$("#mainPage").show();
	$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
}


