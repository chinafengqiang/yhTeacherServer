/**  
 * 题目管理
 */

/* =========== 定义Action地址 (******1)============= */
var contextPath = PageUtility.getContextPath();
var actionQueryDataList = contextPath + "/Question/getQuestionListBySearch.action";
var actionGetData = contextPath + "/Question/getQuestion.action";
var actionCreateData = contextPath + "/Question/createQuestion.action";
var actionModifyData = contextPath + "/Question/modifyQuestion.action";
var actionRemoveData = contextPath + "/Question/removeQuestion.action";
var actionImportFromFile = contextPath + "/Question/importQuestionList.action";
var actionExportToFile = contextPath + "/Question/exportQuestionList.action";
var actionUploadImage = contextPath + "/Question/uploadArticleImage.action";
var actionCategoryTree = contextPath + "/Question/getQuestionCategoryListByTree.action";
var actionChildGetData = contextPath + "/Question/getQuestionCategory.action";
var actionCategoryCreateData = contextPath + "/Question/createCategory.action";
var actionChildCreateData = contextPath + "/Question/createChildCategory.action";
var actionExcelTemplet = contextPath + "/Question/exportQuestionExcelTemplet.action";
var actionUploadImage = contextPath + "/Question/uploadQuestionImage.action";

/* =========== 定义分页参数 ============= */
var perPageNumber = PageUtility.getPerPageNumber();
var pageNo = 1;

/* =========== 定义页面加载和页面按钮事件 ============= */
$(function() {
	
	//默认值
	$("#questionType").val("1");
	
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
			setQuestionEditFormData(null);
			showEditForm(true);
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});
	
	//点击导出
	$("#btnExportFromFile").click(function() {
		
		var id = $("#questionCategoryId").val();
		if(id == "" || id == null){
			alert("请选择题库分类");
			return false;
		}
		exportToFile(id);
	
	});
	
	//点击导出模板
	$("#btnTempFromFile").click(function() {
			exportToTemplet();
	
	});
	
	//点击导入按钮事件(******)
	$("#btnImportFromFile").upload({
        action: actionImportFromFile,
        name: 'file',
        iframeName: 'ImportFromFile',
        onSelect: function (self, element) {
            this.autoSubmit = false;
            var id = $("#questionCategoryId").val();
			if(id == "" || id == null){
				alert("请选择题库分类");
				return false;
			}
            this.params({questionCategoryId:id});
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
        name: 'image',
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
				createQuestionData();
			} else {
				modifyQuestionData();
			}
			
			showEditForm(false);
			queryDataList();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
		
	});
	
	$("#btnTreeClose").click(function() {
		
		showCategoryPage(false);
		queryDataTree();
	});
	
	//点击关闭按钮事件
	$("#btnClose").click(function() {
		
		showEditForm(false);
	});
	
	//单选
	$("#btnSingleSelect").click(function() {
		
		hideAll();
		btnHideAll();
		$("#singleSelect").show();
		$("#questionType").val("0");
		$("#editPage").show();
		$("#btnSave").show();
		$("#rightAnswer").show();
	    clearAll();
		
	});
	
	//多选
	$("#btnMultipleSelect").click(function() {
		
		hideAll();
		btnHideAll();
		$("#multipleSelect").show();
		$("#questionType").val("1");
		$("#editPage").show();
		$("#btnSave").show();
		$("#rightAnswer").show();
		clearAll();
	});
	
	//判断
	$("#btnJudgeSelect").click(function() {
		hideAll();
		btnHideAll();
		$("#judgeSelect").show();
		$("#questionType").val("2");
		$("#editPage").show();
		$("#btnSave").show();
		$("#rightAnswer").show();
		clearAll();
		
	});
	
	//非选题
	$("#btnNoSelect").click(function() {
		hideAll();
		btnHideAll();
		$("#questionType").val("3");
		$("#editPage").show();
		$("#btnSave").show();
		$("#rightAnswer").hide();
		clearAll();
		
	});
	
		
	//初始化页面
	initPage();
})


function clearAll(){
		for(var i = 1; i < 9; i++){
			$("#edSingle" + i).val("");
		}
		
		for(var i = 1; i < 9; i++){
			$("#edMultiple" + i).val("");
		}
		$("input[name='edSingleAanswer']:checked").val("0");
		$("input[name='edJudge']:checked").val("1");
		clearCheckValue("edMultipleAanswer");
}

function clearByInput(){
		for(var i = 1; i < 9; i++){
			$("#edSingle" + i).val("");
		}
		
		for(var i = 1; i < 9; i++){
			$("#edMultiple" + i).val("");
		}
}


function removeAll(id){
	var target = $("#" + id); 
	if(target.hasClass("option_btn mart35 marr25")){ 
		target.removeClass("option_btn mart35 marr25");
		target.addClass("option_btn2 mart35 marr25");
	}
	
}

function hideAll(){
	$("#singleSelect").hide();
	$("#multipleSelect").hide();
	$("#judgeSelect").hide();
}

function btnHideAll(){
	$("#btnSingleSelect").hide();
	$("#btnMultipleSelect").hide();
	$("#btnJudgeSelect").hide();
	$("#btnNoSelect").hide();
}

function btnShowAll(){
	$("#btnSingleSelect").show();
	$("#btnMultipleSelect").show();
	$("#btnJudgeSelect").show();
	$("#btnNoSelect").show();
}

/* =========== 定义数据行按钮事件 ============= */

//修改按钮点击事件
function btnModify(id) {

	showLoading(true);
	try {
		setQuestionEditFormData(id);
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
		removeQuestion(id);
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

//导出到文件(******1)
function exportToFile() {
	
	var url = actionExportToFile;
	PageUtility.exportToFile(url);
}

//导出模板(******1)
function exportToTemplet() {
	var url = actionExcelTemplet;
	PageUtility.exportToFile(url);
}

//分类添加
function btnCreateCategory() {

	var timeStamp = (new Date()).valueOf();
	var url = contextPath + "/system/MS/questionCategory.jsp?timestamp=" + timeStamp;

	$('#iframeSubPage').attr('src',url);
 	$("#mainPage").hide();
 	$("#subPage").show();
	
}

/* =========== 定义数据操作方法 ============= */

//页面初始化
function initPage() {
	
	showLoading(true);
	
	try {
		showMSHeader();
		showEditForm(false);
		
		setDataSelectOption();
		queryDataTree();
	} catch (ex) {
		alert(ex);
	}

	showLoading(false);
}

//设置查询条件和编辑界面的数据项的选项(******)
function setDataSelectOption() {
	
	var htmlQuestionType = PageUtility.ajaxGetEnumNameOptions("QuestionTypeEnum");
	var htmlDifficulty = PageUtility.getQuestionDifficultyOptions();
	
	$("#qpDifficulty").html(htmlDifficulty);
	$("#edDifficulty").html(htmlDifficulty);
	
	$("#qpQuestionType").html(htmlQuestionType);
	$("#edQuestionType").html(htmlQuestionType);
}


//设置编辑页面的数据内容(******)
function setQuestionEditFormData(id) {
	
	$("#dataId").val(id);
	
	if (id == null) {
		$("#edName").val("");
		$("#edScore").val("");
		$("#edDifficulty").val("");
		$("#edKen").val("");
		$("#edNote").val("");
		
		for(var i = 1; i < 9; i++){
			$("#edSingle" + i).val("");
		}
		
		for(var i = 1; i < 9; i++){
			$("#edMultiple" + i).val("");
		}
		
		for(var i = 1; i < 9; i++){ 
			$("input[name='edSingleAanswer'][value='" + i + "']").prop("checked",false);
		}
		
		//$("input[name='edSingleAanswer']:checked").val("1");
		for(var i = 0; i < 2; i++){ 
			$("input[name='edJudge'][value='" + i + "']").prop("checked",false);
		}
		//$("input[name='edJudge']:checked").val("1");
		clearCheckValue("edMultipleAanswer");
		
		$("#editPage").hide();
		$("#singleSelect").hide();
		$("#multipleSelect").hide();
		$("#judgeSelect").hide();
		$("#btnSave").hide();
		btnShowAll();
		
	} else {
		btnHideAll();
		var paramters = {"question.id": id};
		var data = PageUtility.ajaxData(actionGetData, paramters);
		
		$("#edName").val(data.name);
		$("#edScore").val(data.score);
		$("#edDifficulty").val(data.difficulty);
		$("#edKen").val(data.ken);
		$("#edNote").val(data.note);
		
		var options = data.options;
		var answerValue = data.answer;
		var optionsValue = options.split(";");
		$("#questionType").val(data.questionType);
		
		if(data.questionType == 0){
			hideAll();
			clearByInput();
			btnSingleStyle();
			$("#singleSelect").show();
			for(var i = 0; i < optionsValue.length; i++){
				$("#edSingle" + (i + 1)).val(optionsValue[i]);
			}
			
			getAllValue(answerValue,"edSingleAanswer");
		}
		if(data.questionType == 1){
			hideAll();
			clearByInput();
			btnMultipleStyle();
			$("#multipleSelect").show();
			for(var i = 0; i < optionsValue.length; i++){
				$("#edMultiple" + (i + 1)).val(optionsValue[i]);
			}
			getAllValue(answerValue,"edMultipleAanswer");
		}
		
		if(data.questionType == 2){
			hideAll();
			btnJudgeStyle();
			$("#judgeSelect").show();
			getAllValueRadio(answerValue,"edJudge");
		}
	
		$("#editPage").show();
	}
}

//选中
function getAllValueRadio(answerValue,checkName){
	
	if ($("input[name='"+checkName+"']").length > 0) {
			var box = '';
			$("input[name='"+checkName+"']").each(function() {
				box = $(this).val();
				var  flag = false;
				
					var curChar = answerValue;
					if(curChar == box){
						flag = true;
					}
				
				if(flag){
					$(this).prop("checked",true);
				}
			})
		} 
}

//选中
function getAllValue(answerValue,checkName){
	
	if ($("input[name='"+checkName+"']").length > 0) {
			var box = '';
			$("input[name='"+checkName+"']").each(function() {
				box = $(this).val();
				var  flag = false;
				
				for(var i = 0; i < answerValue.length; i++){
					var curChar = answerValue.charAt(i);
					if(stringToNumber(curChar)== box){
						flag = true;
					}
					
				}
				
				if(flag){
					$(this).prop("checked",true);
				}
			})
		} 
}

//单选样式
function btnSingleStyle(){
	var target = $("#btnSingleSelect"); 
	if(target.hasClass("option_btn2 mart35 marr25")){ 
		target.removeClass("option_btn2 mart35 marr25"); 
		target.addClass("option_btn mart35 marr25"); 
		removeAll("btnMultipleSelect");
		removeAll("btnJudgeSelect");
	}
}
//多选样式
function btnMultipleStyle(){
	var target = $("#btnMultipleSelect"); 
		if(target.hasClass("option_btn2 mart35 marr25")){ 
			target.removeClass("option_btn2 mart35 marr25"); 
			target.addClass("option_btn mart35 marr25"); 
			removeAll("btnSingleSelect");
			removeAll("btnJudgeSelect");
		}
}
//判断样式
function btnJudgeStyle(){
		var target = $("#btnJudgeSelect"); 
		if(target.hasClass("option_btn2 mart35 marr25")){ 
			target.removeClass("option_btn2 mart35 marr25"); 
			target.addClass("option_btn mart35 marr25"); 
			removeAll("btnSingleSelect");
			removeAll("btnMultipleSelect");
		}
}


//创建数据(******1)
function createQuestionData() {
	
	var questionCategoryId = PageUtility.getFormDataByString("questionCategoryId", "题库分类名称", false);
	var questionType = PageUtility.getFormDataByString("questionType", "题型", false);
	var name = PageUtility.getFormDataByString("edName", "题干", false, 150);
	var score = PageUtility.getFormDataBySelect("edScore", "分数", false);
	var difficulty = PageUtility.getFormDataBySelect("edDifficulty", "难度", false);
	var ken = PageUtility.getFormDataByString("edKen", "知识点", true, 50);
	var note = PageUtility.getFormDataByString("edNote", "题目路径", true, 200);
	
	var answer = "";
	var options = "";
	var questionType = $("#questionType").val();
	if(questionType == 0){
		btnSingle();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
		options = PageUtility.getFormDataBySelect("edOptions", "选项", false);
	} else if (questionType == 1){
		btnMultiple();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
		options = PageUtility.getFormDataBySelect("edOptions", "选项", false);
	}else if (questionType == 2){
		btnJudge();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
	} if (questionType == 3) {
		answer = "1";
	}
	
	var paramters = { 
		"questionCategoryId": questionCategoryId,
		"question.questionType": questionType,
		"question.name": name,
		"question.answer": answer,
		"question.options": options,
		"question.score": score,
		"question.difficulty": difficulty,
		"question.ken": ken,
		"question.note": note
	};
	
	var result = PageUtility.ajaxAction(actionCreateData, paramters);
}

//修改数据(*****11*)
function modifyQuestionData() {
	var id = PageUtility.getFormDataByString("dataId", "编号", false);
	var name = PageUtility.getFormDataByString("edName", "题干", false, 150);
	var score = PageUtility.getFormDataBySelect("edScore", "分数", false);
	var difficulty = PageUtility.getFormDataBySelect("edDifficulty", "难度", false);
	var ken = PageUtility.getFormDataByString("edKen", "知识点", true, 50);
	var note = PageUtility.getFormDataByString("edNote", "题目路径", true, 200);
	
	var answer = "";
	var options = "";
	var questionType = $("#questionType").val();
	if(questionType == 0){
		btnSingle();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
		options = PageUtility.getFormDataBySelect("edOptions", "选项", false);
	} else if (questionType == 1){
		btnMultiple();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
		options = PageUtility.getFormDataBySelect("edOptions", "选项", false);
	}else if (questionType == 2){
		btnJudge();
		answer = PageUtility.getFormDataBySelect("edAnswer", "答案", false);
	} if (questionType == 3) {
		answer = "1";
	}
	
	var paramters = { 
		"question.id": id,
		"question.name": name,
		"question.answer": answer,
		"question.options": options,
		"question.score": score,
		"question.difficulty": difficulty,
		"question.ken": ken,
		"question.note": note
	};
	
	var result = PageUtility.ajaxAction(actionModifyData, paramters);
}

//单选方法
function btnSingle(){
	var singleSelected  = "";
	var separator = ";";
	
	for(var i = 1; i < 9; i++){
		var value = $("#edSingle" + i).val();
		
		if(value == "" || value == null){
			break;
		}
		
		singleSelected += value + separator;
		
	}
	$("#edOptions").val(singleSelected);
	
	var listAanswer= $('input:radio[name="edSingleAanswer"]:checked').val();
	$("#edAnswer").val(numberToString(listAanswer));

}

//多选方法
function btnMultiple(){
	var multipleSelected  = "";
	var separator = ";";
	var checkValue = '';
	
	for(var i = 1; i < 9; i++){
		var value = $("#edMultiple" + i).val();
		
		if(value == "" || value == null){
			break;
		}
		
		multipleSelected += value + separator;
	}
	$("#edOptions").val(multipleSelected);

	if($("input[name='edMultipleAanswer']:checkbox:checked").length > 0) {
		$("input[name='edMultipleAanswer']:checkbox:checked").each(function() {
			checkValue += numberToString($(this).val());
			$("#edAnswer").val(checkValue);
		})
    }

}

//判断方法
function btnJudge(){
	var listAanswer= $('input:radio[name="edJudge"]:checked').val();
	$("#edAnswer").val(listAanswer);
}



//删除数据(******1)
function removeQuestion(id) {
	
	var paramters = {
		"question.id": id
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

//查询分类数据树
function queryDataTree() {
	
	result = PageUtility.ajaxData(actionCategoryTree, "");
	showCategoryDataList(result);
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

//选择分类(******1)
function btnSelectCategory(id){
	
	$("#questionCategoryId").val(id);
	queryDataList();
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
			+ "</td><td><a class='grey_underline marr10' href='javascript:void(0)' onclick='btnModify(" + data.id + ")'>修改</a>" +
			"<a class='grey_underline marr10' href='javascript:void(0)' onclick='btnRemove(" + data.id + ")'>删除</a>"  
			+ "</td></tr>"
		);
	});
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

//导出到文件(******1)
function exportToFile(id) {
	
	var url = actionExportToFile + "?questionCategoryId=" + id;
	PageUtility.exportToFile(url);
}

//切换列表和编辑页面(******)
function showEditForm(visible) {
	
	if (!visible) {
		$("#functionName").html("<span>题库</span>您正在浏览题目记录列表……");
		$("#pageDataList").show();
		$("#pageEditData").hide();
		$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
	} else {
		$("#functionName").html("<span>题库</span>您正在编辑题目……");
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

function isCreateCategoryData() {
	
	var value = $("#categoryId").val();
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

/*
 * 数字项对应的字母项 如1:A 2:B
 */
function numberToString(num){

	switch(parseInt(num)){
	
	 case 1:
	 
		return "A";
		break;
		
	 case 2:
	 
	    return "B";
		break;
		
	  case 3:
	  
		return "C";
		break;
		
	  case 4:
	  
		return "D";
		break;
		
	   case 5:
	   
	   return "E";
	   break;
	   
	   case 6:
	   
	   return "F";
	   break;
	   
	   case 7:
	   
	   return "G";
	   break;
	   
	   case 8:
	   return "H"
	}
}
//取消选中
function clearCheckValue(checkName){
	if($("input[name='"+checkName+"']:checkbox:checked").length > 0) {
			$("input[name='"+checkName+"']:checkbox:checked").each(function() {
				 $(this).attr("checked",false);
			})
		} 
}

/*
 * 字母对应的数字
 */
function stringToNumber(str){

	if(str=='A'){
	
		return 1;
	}
	
	if(str=='B'){
	
		return 2;
	}
	
	if(str=='C'){
	
		return 3;
	}
	
	if(str=='D'){
	
		return 4;
	}
	
	if(str=='E'){
	
		return 5;
	}
	
	if(str=='F'){
	
		return 6;
	}
	
	if(str=='G'){
	
		return 7;
	}
	
	if(str=='H'){
	
		return 8;
	}
}

//返回到主页面
function backFromSubPage() {
	
	$("#subPage").hide();
	$("#mainPage").show();
	$("html,body").animate({scrollTop:$("#functionArea").offset().top},500);
}

