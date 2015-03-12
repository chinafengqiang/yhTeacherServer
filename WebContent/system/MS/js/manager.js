/**  
 * 教师管理
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Manager/getManagerListBySearch.action";
var actionGetData = contextPath + "/Manager/getManager.action";
var actionCreateData = contextPath + "/Manager/createManager.action";
var actionModifyData = contextPath + "/Manager/modifyManager.action";
var actionRemoveData = contextPath + "/Manager/removeManager.action";
var actionBrowseData = "";
var actionImportFromFile = contextPath + "/Manager/importArticle.action";
var actionExportToFile = contextPath + "/Manager/exportArticle.action";
var actionUploadImage = contextPath + "/Manager/uploadArticleImage.action";

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
	
	var htmlManagerGrade = PageUtility.ajaxGetEnumNameOptions("ManagerGradeEnum");
	var htmlStatus = PageUtility.ajaxGetEnumNameOptions("ArticleStatusEnum");
	var htmlSysAccessType = PageUtility.ajaxGetEnumNameChecks("SysAccessTypeEnum");

	$("#qpGradeName").html(htmlManagerGrade);
	
	$("#edSysAccessType").html(htmlSysAccessType);
	$("#edGradeName").html(htmlManagerGrade);
	$("#edStatus").html(htmlStatus);
}

//设置编辑页面的数据内容(******)
function setEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		
		$("#edName").val("");
		$("#edActualName").val("");
		$("#edMobile").val("");
		$("#edEmail").val("");
		$("#edGradeName").val("0");
		$("#edStatus").val("0");
		
		$('#edName').attr("readonly",false);

		clearCheckValue();
	} else {
		
		var paramters = {"manager.id": id};
		var data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edActualName").val(data.actualName);
		$("#edMobile").val(data.mobile);
		$("#edEmail").val(data.email);
		$("#edGradeName").val(data.grade);
		$("#edStatus").val(data.status);
		
		//修改选中值
		showSysAccess(data.sysAccess);
		
		$('#edName').attr("readonly",true);
	}
}

//创建数据(******1)
function createData() {
	
	var name = PageUtility.getFormDataByString("edName", "帐号", false);
	var actualName = PageUtility.getFormDataByString("edActualName", "姓名", false, 50);
	var mobile = PageUtility.getFormDataBySelect("edMobile", "手机", true, 100);
	var email = PageUtility.getFormDataBySelect("edEmail", "Email", true, 100);
	var gradeName = PageUtility.getFormDataBySelect("edGradeName", "级别", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var checkedAccess = getCheckValue();
	

	
	var paramters = { 
		"manager.name": name,
		"manager.actualName": actualName,
		"manager.mobile": mobile,
		"manager.email": email,
		"manager.grade": gradeName,
		"manager.status": status,
		"manager.sysAccess": checkedAccess
		
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//选中方法
function getCheckValue(){
	
	var checkedAccess = '';
	
	if($("input[name='chkData']:checkbox:checked").length > 0) {
			$("input[name='chkData']:checkbox:checked").each(function() {
				checkedAccess += $(this).val() + ';';
			})
		} else {
			var checkedAccess = PageUtility.getFormDataBySelect("edSysAccessType", "权限", false);
	}
	
	return checkedAccess;
	
}

//取消选中
function clearCheckValue(){
	if($("input[name='chkData']:checkbox:checked").length > 0) {
			$("input[name='chkData']:checkbox:checked").each(function() {
				 $(this).attr("checked",false);
			})
		} 
}

//选中
function showSysAccess(sysAccess){

	var htmlSysAccessType = PageUtility.ajaxGetEnumNameChecks("SysAccessTypeEnum");
	$("#edSysAccessType").html(htmlSysAccessType);

	if ($("input[name='chkData']").length > 0) {
		$("input[name='chkData']").each(function() {
			
			var value = $(this).val();			
			var checkList = sysAccess.split(";");
			var flag = false;
			for(var i = 0; i < checkList.length - 1; i++){
				
				if(checkList[i] == value){
					flag = true;
					break;
				}				
			}
			
			if(flag){
				$(this).attr("checked",true);
			} else {
				$(this).attr("checked",false);
			}
		})
	} 
}


//修改数据(*****11*)
function modifyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "帐号", false, 50);
	var actualName = PageUtility.getFormDataByString("edActualName", "姓名", false, 50);
	var mobile = PageUtility.getFormDataByString("edMobile", "手机", true, 50);
	var email = PageUtility.getFormDataByString("edEmail", "Email", true, 50);
	var gradeName = PageUtility.getFormDataBySelect("edGradeName", "级别", false);
	var status = PageUtility.getFormDataBySelect("edStatus", "状态", false);
	var checkedAccess = getCheckValue();
	
	var paramters = { 
		"manager.id": id,
		"manager.name": name,
		"manager.actualName": actualName,
		"manager.mobile": mobile,
		"manager.email": email,
		"manager.grade": gradeName,
		"manager.status": status,
		"manager.sysAccess": checkedAccess
		
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"manager.id": id
	};
	
	var result = PageUtility.ajaxAction(actionRemoveData, paramters);
}

//浏览数据(******)
function browseData(id) {
	
	//新标签里显示
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
			+ data.gradeName
			+ "</td><td>"
			+ data.actualName
			+ "</td><td>"
			+ data.name
			+ "</td><td style='font-size:10px; text-align:left;'>"
			+ data.sysAccessName
			+ "</td><td>"
			+ data.statusName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>" 
			+ "</td></tr>"
		);
	});
}

//获取查询参数集(******1)
function getQueryParams() {
	
	var grade = PageUtility.getQueryParamterByInputString("qpGradeName");
	var actualName = PageUtility.getQueryParamterByInputString("qpActualName");
	var name = PageUtility.getQueryParamterByInputString("qpName");
	
	var result = { "paginateParamters.pageNo":pageNo,
				   "paginateParamters.perPageNumber":perPageNumber,
				   "name":name,
				   "actualName":actualName,
				   "grade":grade
				   };
	return result;
}

//导出到文件(******1)
function exportToFile(id) {
	
	var url = actionExportToFile + "?manager.id=" + id;
	PageUtility.exportToFile(url);
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>教师</span>您正在浏览教师记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>教师</span>您正在编辑教师……");
		$("#pageDataList").hide();
		$("#pageEditData").show();
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



