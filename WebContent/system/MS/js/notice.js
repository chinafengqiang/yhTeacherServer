/**  
 * 公告管理
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Notice/getNoticeListBySearch.action";
var actionGetData = contextPath + "/Notice/getNotice.action";
var actionCreateData = contextPath + "/Notice/createNotice.action";
var actionModifyData = contextPath + "/Notice/modifyNotice.action";
var actionRemoveData = contextPath + "/Notice/removeNotice.action";
var actionBrowseData = contextPath + "/Notice/getNotice.action";
var actionImportFromFile = contextPath + "/Notice/importNotice.action";
var actionExportToFile = contextPath + "/Notice/exportNotice.action";
var actionUploadImage = contextPath + "/Notice/uploadNoticeImage.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//创建html编辑器
	KindEditor.ready(function(K) {
		window.editor = K.create('#edContent');
	});
	
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
	
	//点击关闭按钮事件
	$("#btnClose").click(function() {
		
		showEditForm(false);
	});
	
	//点击关闭按钮事件
	$("#btnBrowseClose").click(function() {
		
		showBrowsePage(false);
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
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {
	
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("NoticeStatusEnum");
	var htmlNoticeGrade = PageUtility.ajaxGetEnumNameOptions("NoticeGradeEnum");
	
	$("#qpStatus").html(htmlStatus);
	$("#qpNoticeGrade").html(htmlNoticeGrade);
	
	$("#edStatus").html(htmlStatus);
	$("#edNoticeGrade").html(htmlNoticeGrade);
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edTitle").val("");
		$("#edNoticeGrade").val("0");
		$("#edStatus").val("0");
		editor.html("");
		$("#edSortFlag").val("");
	} else {
		
		var paramters = {"notice.id": id};
		var data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edTitle").val(data.title);
		$("#edNoticeGrade").val(data.grade);
		$("#edStatus").val(data.status);
		editor.html(data.content);
		$("#edSortFlag").val(data.sortFlag);
	}
}

//创建数据(******)
function createData() {
	
	editor.sync();
	
	var title = PageUtility.getFormDataByString("edTitle", "标题", false, 100);
	var noticeGrade = PageUtility.getFormDataBySelect("edNoticeGrade", "级别", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var content = PageUtility.getFormDataByString("edContent", "内容", false, 30000);
	var sortFlag = PageUtility.getFormDataByString("edSortFlag", "排序", false);
	
	var paramters = { 
		"notice.title": title,
		"notice.grade": noticeGrade,
		"notice.status": status,
		"notice.content": content,
		"notice.sortFlag": sortFlag
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//修改数据(******)
function modifyData() {
	
	editor.sync();
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var title = PageUtility.getFormDataByString("edTitle", "标题", false, 100);
	var noticeGrade = PageUtility.getFormDataBySelect("edNoticeGrade", "级别", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var content = PageUtility.getFormDataByString("edContent", "内容", false, 30000);
	var sortFlag = PageUtility.getFormDataByString("edSortFlag", "排序", false);
	
	var paramters = {
		"notice.id": id,
		"notice.title": title,
		"notice.grade": noticeGrade,
		"notice.status": status,
		"notice.content": content,
		"notice.sortFlag": sortFlag
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//删除数据(******)
function removeData(id) {
	
	var paramters = {
		"notice.id": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

//浏览数据(******)
function browseData(id) {
	
	//新标签里显示
	if (id != null) {
		
		var paramters = {"notice.id": id};
		data = PageUtility.ajaxData(actionGetData, paramters);	
		$("#browseContent").html(data.content);
		
		showBrowsePage(true);
	}
	
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, getQueryParams());
	showDataList(result.list);
	setPaginateResult(result);
}

//显示数据列表(******)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td>"
			+ data.id
			+ "</td><td>"
			+ data.gradeName
			+ "</td><td class='txtl text-overflow'>"
			+  data.title
			+ "</td><td>"
			+ data.createdTimeName
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnBrowse(" + data.id + ")'>预览</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnExportToFile(" + data.id + ")'>导出</a>"
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******)
function getQueryParams() {
	
	var title = PageUtility.getQueryParamterByInputString("qpTitle");
	var status = PageUtility.getQueryParamterByInputString("qpStatus");
	var noticeGrade = PageUtility.getQueryParamterByInputString("qpNoticeGrade");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "title":title,
				   "status":status,
				   "noticeGrade":noticeGrade };
	return result;
}

//导出到文件(******)
function exportToFile(id) {
	
	var url = actionExportToFile + "?notice.id=" + id;
	PageUtility.exportToFile(url);
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>公告</span>您正在浏览公告记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>公告</span>您正在编辑公告……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//切换列表和编辑页面(******)
function showBrowsePage(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>公告</span>您正在浏览公告记录列表……");
		$("#pageDataList").show();
		$("#pageBrowseData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>公告</span>您正在预览公告……");
		$("#pageDataList").hide();
		$("#pageBrowseData").show();
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



