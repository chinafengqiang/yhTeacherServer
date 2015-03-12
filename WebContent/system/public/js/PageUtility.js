/* 
 * 页面工具类 
 */
function PageUtility() {
	
}

function showMSHeader() {
	
}

/** 
 * 获取Url参数 
 */
PageUtility.getRequestParam = function(paras){
 
    var url = location.href; 
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {} 
    for (i=0; j=paraString[i]; i++){ 
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined"){ 
    return ""; 
    }else{ 
    return returnValue; 
    } 
}

/** 
 * 获取ContextPath 
 */
PageUtility.getContextPath = function(){

	var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    
    if (result != "/Study") {
    	return "";
    } else {
	    return result;
    }
        
    return result;
};

/** 
 * 获取默认的每页记录数
 */
PageUtility.getPerPageNumber = function() {
	
	return 20;
}

PageUtility.getQueryParamterByInputString = function(id) {
	
	var value =  $.trim($("#" + id).val());
	
	if (value != null && value != NaN && value != "") { 
		return value;
	} else {
		return null;
	}
}

PageUtility.getQueryParamterBySelect = function(id) {
	
	var value =  $.trim($("#" + id).val());
	
	if (value != null && value != NaN && value != "") { 
		return value;
	} else {
		return null;
	}
}

PageUtility.getFormDataByString = function(id, name, isNull, maxLength) {
	
	var value =  $.trim($("#" + id).val());
	
	if (!(value != null && value != NaN && value != "")) { 
		value = null;
	}
	
	if (value == null && !isNull) {
		throw "请填写" + name;
	}
	
	if (value != null && value.length > maxLength) {
		throw  name + "的字符串长度已是" + value.length + ",不可超过" + maxLength;
	}
	return value;
}

PageUtility.getFormDataBySelect = function(id, name, isNull) {
	
	var value =  $.trim($("#" + id).val());
	
	if (!(value != null && value != NaN && value != "")) { 
		value = null;
	}
	
	if (value == null && !isNull) {
		throw "请选择" + name;
	}
	
	return value;
}
PageUtility.ajaxGetClassNameOptions = function() {
	
	var url = PageUtility.getContextPath() + "/System/geClassMap.action";
	var data = PageUtility.ajaxData(url, "");
	
	var html = "<option value=''>　</option>";
	$.each(data,function(key,values){     
		html += "<option value=" + key + ">" + values + "</option>"
 	}); 
	
	return html;
}

PageUtility.ajaxGetEnumNameOptions = function(enumName) {
	
	var paramters = {"enumName": enumName};
	var url = PageUtility.getContextPath() + "/System/getEnumMap.action";
	var data = PageUtility.ajaxData(url, paramters);
	
	var html = "<option value=''>　</option>";
	$.each(data,function(key,values){     
		html += "<option value=" + key + ">" + values + "</option>"
 	}); 
	
	return html;
}

PageUtility.ajaxGetUrlOptions = function(strUrl) {
	
	var data = PageUtility.ajaxData(strUrl, "");
	
	var html = "";
	$.each(data,function(key,values){     
		
		html += "<option value=" + values.id + ">" + values.name + "</option>"
 	}); 
	
	return html;
}

PageUtility.ajaxGetUrlOptionsByActualName= function(strUrl, bIncludeNulll) {
	
	var data = PageUtility.ajaxData(strUrl, "");
	
	var html = "";
	if (bIncludeNulll) {
		html = "<option value=''>　</option>";
	}
	
	$.each(data,function(key,values){     
		
		html += "<option value=" + values.id + ">" + values.actualName + "</option>"
 	}); 
	
	return html;
}

PageUtility.getQuestionDifficultyOptions= function() {
	
	var html = "<option value=''>　</option>";
	for (var i=1; i<=5; i++) {	
		html += "<option value=" + i + ">" + i + "</option>"
 	}
	
	return html;
}


PageUtility.ajaxGetEnumNameChecks = function(enumName) {
	
	var paramters = {"enumName": enumName};
	var url = PageUtility.getContextPath() + "/System/getEnumMap.action";
	var data = PageUtility.ajaxData(url, paramters);
	var html = "";
	$.each(data,function(key,values){     
		html += " <input type='checkbox' name='chkData' value='"+ key +"' />"+ values;
 	}); 
	
	return html;
}

PageUtility.ajaxGetEnumNameCheckValue = function(enumName) {
	
	var paramters = {"sysParamName": enumName};
	var url = PageUtility.getContextPath() + "/System/getSysParamValue.action";
	data = PageUtility.ajaxData(url, paramters);
	
	var datas = data.split(";");
	var html = "";
	for(var i = 0; i < datas.length - 1; i++){
			html += "<input class='checkbox_s' type='checkbox' name='chkData' value='"+ datas[i] +"' />"+ datas[i];
	}
	
	return html;
}

PageUtility.ajaxGetEnumNameCheckName = function(enumName,checkName) {
	
	var paramters = {"sysParamName": enumName};
	var url = PageUtility.getContextPath() + "/System/getSysParamValue.action";
	var data = PageUtility.ajaxData(url, paramters);
	
	var datas = data.split(";");
	var html = "";
	for(var i = 0; i < datas.length - 1; i++){
			html += "<input class='checkbox_s' type='checkbox' name='"+checkName+"' value='"+ datas[i] +"' /> "+ datas[i] + " ";
	}
	
	return html;
}

PageUtility.ajaxGetSysParamOptions = function(enumName) {
	
	var paramters = {"sysParamName": enumName};
	var url = PageUtility.getContextPath() + "/System/getSysParamValue.action";
	var data = PageUtility.ajaxData(url, paramters);
	var datas = data.split(";");
	
	var html = "<option value=''>　</option>";
	for(var i = 0; i < datas.length - 1; i++){   
		html += "<option value=" + i + ">" + datas[i] + "</option>"
 	}
	
	return html;
}

PageUtility.ajaxGetSysParamList = function(enumName) {
	
	var paramters = {"sysParamName": enumName};
	var url = PageUtility.getContextPath() + "/System/getSysParamValue.action";
	var data = PageUtility.ajaxData(url, paramters);
	var datas = data.split(";");
	return datas;
}


/** 
 * 用Ajax获取数据 
 * @param actionUrl action链接
 * @param paramters 参数集
 * @return 数据
 */
PageUtility.ajaxData = function(actionUrl, paramters) {
	var data = null;
	var result = false;
	var actionMessage = "";
	
	$.ajax({
		type : "post",
		dataType : "json", 
		async: false,
		url : actionUrl, 
		timeout: 10000,
		data : paramters,
		error: function(){
			result = false;
			actionMessage = "加载数据出错！请检查网络！";
		},
		success : function(msg){ 
			if(msg.actionStatus == 'success'){
				result = true;
				data = msg.data;
				actionMessage = msg.actionMessage;
			} else {
				result = false;
				actionMessage = msg.actionMessage;
			}
		}
	});
	
	if (!result) {
		throw actionMessage;
	};
	
	if (data == null) {
		throw "未能成功加载数据,请刷新后操作！";
	} 
	
	return data;
}

/** 
 * 用Ajax执行操作 
 * @param actionUrl action链接
 * @param paramters 参数集
 * @return 操作消息
 */
PageUtility.ajaxAction = function(actionUrl, paramters) {

	var data = null;
	var result = false;
	var actionMessage = "";
	
	$.ajax({
		type : "post",
		dataType : "json", 
		async: false,
		url : actionUrl, 
		data : paramters,
		error: function(){
			result = false;
			actionMessage = "加载数据出错！请检查网络！";
		},
		success : function(msg){ 
			if(msg.actionStatus == 'success'){
				result = true;
				actionMessage = msg.actionMessage;
			} else {
				result = false;
				actionMessage = msg.actionMessage;
			}
		}
	});
	
	if (result) {
		return actionMessage;
	} else {
		throw actionMessage;
	}
}

/** 
 * 用于将下载文件的链接放到自己创建的iframe中，实现下载 
 * @param url链接
 */
PageUtility.exportToFile = function(url) {

	if($('#iframeExportToFile').length<=0){
		$('body').append("<iframe id=\"iframeExportToFile\" style=\"display:none\"></iframe>");
	} 
 	$('#iframeExportToFile').attr('src',url);
}

PageUtility.showUploadImagePopup = function(url) {
	
	
    $(".uploadImagePopup .url").text(url);
	$(".uploadImagePopup .picture").html("<img src='" + url + "' width=100% height=100%/>");
	$(".uploadImagePopup").bPopup({
		opacity:0.1,
		modalClose:false,
		escClose:true
	});

}

/*
 * 处理过长的字符串，截取并添加省略号
 * 注：半角长度为1，全角长度为2
 * 
 * pStr:字符串
 * pLen:截取长度
 * 
 * return: 截取后的字符串
 */
function autoAddEllipsis(pStr, pLen) {

	var _ret = cutString(pStr, pLen);
	var _cutFlag = _ret.cutflag;
	var _cutStringn = _ret.cutstring;

	if ("1" == _cutFlag) {
		return _cutStringn + "...";
	} else {
		return _cutStringn;
	}
}

/*
 * 取得指定长度的字符串
 * 注：半角长度为1，全角长度为2
 * 
 * pStr:字符串
 * pLen:截取长度
 * 
 * return: 截取后的字符串
 */
function cutString(pStr, pLen) {

	// 原字符串长度
	var _strLen = pStr.length;

	var _tmpCode;

	var _cutString;

	// 默认情况下，返回的字符串是原字符串的一部分
	var _cutFlag = "1";

	var _lenCount = 0;

	var _ret = false;

	if (_strLen <= pLen/2) {
		_cutString = pStr;
		_ret = true;
	}

	if (!_ret) {
		for (var i = 0; i < _strLen ; i++ ) {
			if (isFull(pStr.charAt(i))) {
				_lenCount += 2;
			} else {
				_lenCount += 1;
			}

			if (_lenCount > pLen) {
				_cutString = pStr.substring(0, i);
				_ret = true;
				break;
			} else if (_lenCount == pLen) {
				_cutString = pStr.substring(0, i + 1);
				_ret = true;
				break;
			}
		}
	}
	
	if (!_ret) {
		_cutString = pStr;
		_ret = true;
	}

	if (_cutString.length == _strLen) {
		_cutFlag = "0";
	}

	return {"cutstring":_cutString, "cutflag":_cutFlag};
}

/*
 * 判断是否为全角
 * 
 * pChar:长度为1的字符串
 * return: true:全角
 * 			false:半角
 */
function isFull (pChar) {
	if ((pChar.charCodeAt(0) > 128)) {
		return true;
	} else {
		return false;
	}
}

