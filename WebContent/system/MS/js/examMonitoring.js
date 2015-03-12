/**  
 * 查看报表
 */
var argExamId=0;

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/ExamServer/getExamSummary.action";
var actionDeployExam = contextPath + "/Exam/deployExam.action";
var actionOpenExam = contextPath + "/Exam/openExam.action";
var actionCloseExam = contextPath + "/Exam/closeExam.action";
var actionGatherExam = contextPath + "/Exam/gatherExam.action";
var actionDestroyExam = contextPath + "/Exam/destroyExam.action";

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
	
	//部署按钮事件
	$("#btnDeployExam").click(function() {
		
		if (!confirm("是否确认要部署考试?")) {
			reutrn;
		}
		
		deployExam();
	});
	
	//启动按钮事件
	$("#btnOpenExam").click(function() {
		
		if (!confirm("是否确认要启动考试?")) {
			reutrn;
		}
		
		openExam();
	});
	
	//停止按钮事件
	$("#btnCloseExam").click(function() {
		
		if (!confirm("是否确认要停止考试?")) {
			reutrn;
		}
		
		closeExam();
	});
	
	//汇总成绩按钮事件
	$("#btnGatherExam").click(function() {
		
		if (!confirm("是否确认要汇总考试成绩?")) {
			reutrn;
		}
				
		gatherExam();
	});
	
	//卸载按钮事件
	$("#btnDestroyExam").click(function() {
		
		if (!confirm("是否确认要卸载考试?")) {
			reutrn;
		}
		
		destroyExam();
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
		queryDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//部署考试
function deployExam() {
	
	showLoading(true);
	$("#btnDeployExam").addClass('buttonDisabled');
	$("#btnDeployExam").attr("disabled", true);

	try {
		var paramters = { "exam.id": argExamId };
		var result = PageUtility.ajaxAction(actionDeployExam, paramters);
		queryDataList();
		alert(result);
	} catch (ex) {
		alert(ex);
	}
	
	$("#btnDeployExam").attr("disabled", false);
	$("#btnDeployExam").removeClass('buttonDisabled');
	showLoading(false);
}

//启动考试
function openExam() {
	
	showLoading(true);
	$("#btnOpenExam").addClass('buttonDisabled');
	$("#btnOpenExam").attr("disabled", true);

	try {
		var paramters = { "exam.id": argExamId };
		var result = PageUtility.ajaxAction(actionOpenExam, paramters);
		queryDataList();
		alert(result);
	} catch (ex) {
		alert(ex);
	}
	
	$("#btnOpenExam").attr("disabled", false);
	$("#btnOpenExam").removeClass('buttonDisabled');
	showLoading(false);
}

//停止考试
function closeExam() {
	
	showLoading(true);
	$("#btnCloseExam").addClass('buttonDisabled');
	$("#btnCloseExam").attr("disabled", true);

	try {
		var paramters = { "exam.id": argExamId };
		var result = PageUtility.ajaxAction(actionCloseExam, paramters);
		queryDataList();
		alert(result);
	} catch (ex) {
		alert(ex);
	}
	
	$("#btnCloseExam").attr("disabled", false);
	$("#btnCloseExam").removeClass('buttonDisabled');
	showLoading(false);
}

//汇总成绩
function gatherExam() {
	
	showLoading(true);
	$("#btnGatherExam").addClass('buttonDisabled');
	$("#btnGatherExam").attr("disabled", true);

	try {
		var paramters = { "exam.id": argExamId };
		var result = PageUtility.ajaxAction(actionGatherExam, paramters);
		queryDataList();
		alert(result);
	} catch (ex) {
		alert(ex);
	}
	
	$("#btnGatherExam").attr("disabled", false);
	$("#btnGatherExam").removeClass('buttonDisabled');
	showLoading(false);
}

//卸载考试
function destroyExam() {
	
	showLoading(true);
	$("#btnDestroyExam").addClass('buttonDisabled');
	$("#btnDestroyExam").attr("disabled", true);

	try {
		var paramters = { "exam.id": argExamId };
		var result = PageUtility.ajaxAction(actionDestroyExam, paramters);
		queryDataList();
		alert(result);
	} catch (ex) {
		alert(ex);
	}
	
	$("#btnDestroyExam").attr("disabled", false);
	$("#btnDestroyExam").removeClass('buttonDisabled');
	showLoading(false);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result);
}


//显示数据列表(******)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.examServerName
			+ "</td><td>"
			+ data.statusName
			+ "</td><td>"
			+ data.totalNumber + '人'
			+ "</td><td>"
			+ (data.totalNumber - data.beginExamNumber - data.endExamNumber) + '人'  
			+ "</td><td>"
			+ data.beginExamNumber + '人'
			+ "</td><td>"
			+ data.endExamNumber + '人'
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******)
function getQueryParams() {
	
	var result = { "examId":argExamId };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试监控</span>您正在浏览考试监控信息……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试监控</span>您正在浏览考试成绩……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//显示/隐藏Loading图片
function showLoading(visible) {
	
	if (visible) {
		$("#loading").show();
	} else {
		$("#loading").hide();
	} 
}

