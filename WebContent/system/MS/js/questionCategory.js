/**  
 * 编辑题库分类
 */

/* =========== 定义Action地址 (******)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Question/getQuestionCategoryListByTree.action";
var actionChildCreateData = contextPath + "/Question/createChildCategory.action";
var actionChildModifyData = contextPath + "/Question/modifyCategory.action";
var actionChildGetData = contextPath + "/Question/getQuestionCategory.action";
var actionChildRemoveData = contextPath + "/Question/removeCategory.action";

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
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
	$("#btnCategorySave").click(function() {
		
		showLoading(true);
		
		try {
			if (isCreateData() || isCreateCategoryData() ) {
				modifyData();
			} else {
				createData();
			}
			
			showEditForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
		
	});
	
	//关闭	
	$("#btnCategoryClose").click(function() {
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
		queryDataList();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置编辑页面的数据内容(******)
function setEditFormData(id, categoryId) {
	
	$("#dataId").val(id);
	$("#categoryId").val(categoryId);
	
	if (id != null && categoryId != null) {
		$("#edName").val("");
		$("#edSortFlag").val("");
	} else if(id != null) {
		
		var paramters = {"questionCategory.id": id};
		data = PageUtility.ajaxData(actionChildGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edSortFlag").val(data.sortFlag);
	}
}

//删除数据(******1)
function removeData(id) {
	
	var paramters = {
		"questionCategory.id": id
	};
	var result = PageUtility.ajaxAction(actionChildRemoveData, paramters);
}

//查询数据列表
function queryDataList() {
	
	result = PageUtility.ajaxData(actionQueryDataList, "");
	showDataList(result);
}

//显示数据列表(******)
function showDataList(dataList) {
	
	$("#dataListBody").empty();
	
	$.each(dataList, function(i, data) {
		$("#dataListBody").append(
			"<tr><td class='txtl padl45'>"
			+  data.levelName
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnChildCategory(" + data.id + ")'>添加子分类</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModifyCategory(" + data.id + ")'>重命名</a>" + 
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemoveCategory(" + data.id + ")'>删除</a>"
			+ "</td></tr>"
		);
	});
}

//创建数据(******1)
function createData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "题库分类名称", false, 50);
	var sortFlag = PageUtility.getFormDataBySelect("edSortFlag", "排序", false);
	
	var paramters = { 
		"questionCategoryId": id,
		"questionCategory.name": name,
		"questionCategory.sortFlag": sortFlag
	};
	
	var result = PageUtility.ajaxAction(actionChildCreateData, paramters);
}

//修改数据(*****11*)
function modifyData() {
	
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "题库分类名称", false, 50);
	var sortFlag = PageUtility.getFormDataBySelect("edSortFlag", "排序", false);
	
	var paramters = { 
		"questionCategory.id": id,
		"questionCategory.name": name,
		"questionCategory.sortFlag": sortFlag
	};
	
	var result = PageUtility.ajaxAction(actionChildModifyData, paramters);
}


//添加子分类
function btnChildCategory(id){
	
	showLoading(true);
	
	try {
		setEditFormData(id,id);
		showEditForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

//重命名
function btnModifyCategory(id){
	
	showLoading(true);
	
	try {
		setEditFormData(id,null);
		showEditForm(true);
	} catch (ex) {
		alert(ex);
	}
	
	showLoading(false);
}

function btnRemoveCategory(id){
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



//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>题库分类</span>您正在浏览题库分类记录列表……");
		$("#pageDataList").show();
		$("#pageCategoryEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>题库分类</span>您正在编辑题库分类……");
		$("#pageDataList").hide();
		$("#pageCategoryEditData").show();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	}
}

//判断编辑模式
function isCreateData() {
	
	var value = $("#dataId").val();
	return !(value != null && value != NaN && value != ""); 
}

function isCreateCategoryData() {
	
	var value = $("#categoryId").val();
	return !(value != null && value != NaN && value != ""); 
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

