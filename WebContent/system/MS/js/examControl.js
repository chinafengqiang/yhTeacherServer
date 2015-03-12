/**  
 * 课程管理
 */

/* =========== 定义父页面传递过来的参数 (******1)============= */
var argExamId=0;

/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/ExamUser/getExamUserListByManager.action";
var actionResetData = contextPath + "/ExamUser/resetExamUserByManager.action";
var actionRemoveData = contextPath + "/ExamUser/removeExamUserByManager.action"; 
var actionImportFromFile = contextPath + "/ExamUser/importExamUserData.action";
var actionLinkData = contextPath + "/ExamUser/getExamLink.action";
var actionSelectData = contextPath + "/ExamUser/createExamUserByManager.action";
var actionBrowseUserTestPaperData = contextPath + "/ExamUser/browseUserTestPaperByMananger.action";

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
	
	//点击导入按钮事件(******)
	$("#btnImportFromFile").upload({
        action: actionImportFromFile,
        name: 'file',
        iframeName: 'ImportFromFile',
        onSelect: function (self, element) {
            this.autoSubmit = false;
            this.params({examId:argExamId});
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
	
	//点击刷新按钮事件
	$("#btnMake").click(function() {
		
		var timeStamp = (new Date()).valueOf();
	    var url = contextPath + "/system/MS/userSelect.jsp?timestamp=" + timeStamp;

		$('#iframeSubPage').attr('src',url);
	 	$("#mainPage").hide();
	 	$("#subPage").show();
		
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
	
	//初始化页面
	initPage();
	 
})

/* =========== 定义数据操作方法 ============= */

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {

	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("ExamUserStatusEnum");
	$("#qpStatus").html(htmlStatus);
	
}

//选取
function selectUserData(userId) {

	try {
		var paramters = { 
			"userId": userId,
			"examId": argExamId
		};
		var result = PageUtility.ajaxAction(actionSelectData, paramters);
		alert("选取成功!");
	} catch (ex) {
		alert(ex);
	}
}

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

//重考按钮点击事件
function btnReset(examId,userId) {
	
	if (!confirm("你确定要重考此记录吗？")) {
		return false;
	};

	showLoading(true);
	
	try {
		resetData(examId,userId);
		queryDataList();
		alert("重考设置成功！");
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

//删除按钮点击事件
function btnBrowseUserTestPaper(id) {
	
	showLoading(true);
	
	try {
		browseUserTestPaper(id);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
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
			+ "</td><td class='txtl text-overflow'>"
			+ data.actualOrgan
			+ "</td><td>"
			+ data.actualName
			+ "</td><td>"
			+ data.beginTimeName
			+ "</td><td>"
			+ data.score
			+ "</td><td>"
			+ getPassName(data)
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnReset(" + data.examId + ", " + data.userId + ")'>重考</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnBrowseUserTestPaper(" + data.id + ")'>查看答卷</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnLink(" + data.id + ")'>考试直通车</a>"
			+ "</td></tr>"
		);
	});
}

//获取是否通过的名称
function getPassName(data) {
	
	if (data.canPass) {
		return "通过";
	} else {
		return "";
	}
}

//查看连接
function btnLink(id){
	showLoading(true);
	try {
		linkData(id);
	} catch (ex) {
		alert(ex);
	}
	showLoading(false);
}

//重考数据(******1)
function resetData(examId,userId) {
	
	var paramters = {
		"examId": examId,
		"userId": userId
	};
	
	var result = PageUtility.ajaxAction(actionResetData, paramters);
}

//查看答卷
function browseUserTestPaper(id) {
	
	var paramters = {
		"examUserId": id
	};
	
	var result = PageUtility.ajaxAction(actionBrowseUserTestPaperData, paramters);
	
	var url = PageUtility.getContextPath() + "/MS-userTestPaperBrowse";
	window.open(url);
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"examUserId": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

//考试直通车(******1)
function linkData(id) {
	
	var paramters = {
		"examUserId": id
	};
	
	var result = PageUtility.ajaxData(actionLinkData, paramters);
	window.open(result);
}

//获取查询参数集(******1)
function getQueryParams() {
		
	var actualOrgan = PageUtility.getQueryParamterByInputString("qpActualOrgan");
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	var status = PageUtility.getQueryParamterBySelect("qpStatus");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "actualName":actualName,
				   "actualOrgan":actualOrgan,
				   "status":status,
				   "examId":argExamId
				   };
	return result;
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>考试控制台</span>您正在浏览考试的学员记录列表……");
		$("#pageCategoryData").show();
		$("#pageChildEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>考试控制台</span>您正在编辑考试学员记录……");
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

//返回到主页面
function backFromSubPage() {
	
	$("#subPage").hide();
	$("#mainPage").show();
	$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
}

