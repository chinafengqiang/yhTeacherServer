var userTestPaperBrowseData=null;

/** 
 * 装载页面 
 */
$(document).ready( function() {
	initPage();	
});

/** 
 * 初始化页面 
 */
function initPage() {
	
	var actionUrl = PageUtility.getContextPath() + "/ExamUser/getUserTestPaperBrowseDataByManager.action";
	
	try {
		userTestPaperBrowseData = PageUtility.ajaxData(actionUrl, null);
	} catch (ex) {
		$("#TestPaperArea").hide();
		alert(ex);
		return;
	}

	showTestPaperArea();
}

/** 
 * 显示试卷头 
 */
function showTestPaperHeader() {
	
	$(".examName").text(userTestPaperBrowseData.examName);
	$(".actualName").text(userTestPaperBrowseData.examUser.actualName);
	$(".totalScore").text("得分：" + userTestPaperBrowseData.examUser.score + "分");
}

/** 
 * 显示试卷题型 
 */
function showTestPaperOption(testPaperOption, index) {
	
	var numberHz = getHZNumber(index);
	var questionTypeName = getQuestionTypeName(testPaperOption.questionType);

	var note = "";
	if (testPaperOption.note != "" && testPaperOption.note != null) {
		note = "(" + testPaperOption.note + ")";
	}
	
	$("#questionArea").append("<div class=\"questionTypeName\">" + numberHz + "、" + questionTypeName + "：" + note + "</div>");
}

/** 
 * 显示试卷题目 
 */
function showTestPaperQuestion(testPaperQuestion, index) {

	var html = "";
	if (testPaperQuestion.questionType == QuestionTypeEnum.SingleSelect) {
		html = getQuestionHtmlBySingleSelect(testPaperQuestion, index);
	}
	if (testPaperQuestion.questionType == QuestionTypeEnum.MultiSelect) {
		html = getQuestionHtmlByMultiSelect(testPaperQuestion, index);
	}
	if (testPaperQuestion.questionType == QuestionTypeEnum.Judge) {
		html = getQuestionHtmlByJudge(testPaperQuestion, index);
	}
	
	$("#questionArea").append(html);
}

/** 
 * 显示单选题 
 */
function getQuestionHtmlBySingleSelect(testPaperQuestion, index) {
	
	var html = "";
	var questionId = getQuestionId(testPaperQuestion.id);
	var questionOptionId = getQuestionOptionId(testPaperQuestion.id);
	var questionOptionList = getQuestionOptionList(testPaperQuestion.options);
	
	html = html + "<div id=\"" + questionId + "\" class=\"question\">";
	html = html + "<div class=\"questionContent\">" + index + "、" + testPaperQuestion.name + "</div>";
	html = html + "<div class=\"questionOptions\">";
	html = html + "<span>答：</span>";
	
	if (userTestPaperBrowseData.testPaper.questionOptionsSortType == QuestionOptionsSortTypeEnum.Fixed) {
		
		for (var i=0; i<questionOptionList.length; i++) {
			html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"radio\" value=\"" + getCharNumber(i + 1) + "\" />" + getCharNumber(i + 1) + " " + questionOptionList[i] + "</label>&nbsp;&nbsp;&nbsp;&nbsp;";
		}
	} 
	
	if (userTestPaperBrowseData.testPaper.questionOptionsSortType == QuestionOptionsSortTypeEnum.Random) {
	
		var optionArray = new Array();
		for (var i=0; i<questionOptionList.length; i++) {
			optionArray.push({option:questionOptionList[i], value:getCharNumber(i + 1)});
		}
		
		for (var i=0; i<optionArray.length; i++) {
			html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"radio\" value=\"" + optionArray[i].value + "\" />" + getCharNumber(i + 1) + " " + optionArray[i].option + "</label>&nbsp;&nbsp;&nbsp;&nbsp;";
		}
	}
	html = html + "</div>";
	html = html + "<div class=\"questionAnswer\">标准答案：" + testPaperQuestion.answer + "</div>";
	html = html + "</div>";
	
	return html;
}

/** 
 * 显示多选题 
 */
function getQuestionHtmlByMultiSelect(testPaperQuestion, index) {
	
	var html = "";
	var questionId = getQuestionId(testPaperQuestion.id);
	var questionOptionId = getQuestionOptionId(testPaperQuestion.id);
	var questionOptionList = getQuestionOptionList(testPaperQuestion.options);
	
	html = html + "<div id=\"" + questionId + "\" class=\"question\">";
	html = html + "<div class=\"questionContent\">" + index + "、" + testPaperQuestion.name + "</div>";
	html = html + "<div class=\"questionOptions\">";
	html = html + "<span>答：</span>";
	
	if (userTestPaperBrowseData.testPaper.questionOptionsSortType == QuestionOptionsSortTypeEnum.Fixed) {
		for (var i=0; i<questionOptionList.length; i++) {
			html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"checkbox\" value=\"" + getCharNumber(i + 1) + "\" />" + getCharNumber(i + 1) + " " + questionOptionList[i] + "</label>&nbsp;&nbsp;&nbsp;&nbsp;";
		}
	}
	
	if (userTestPaperBrowseData.testPaper.questionOptionsSortType == QuestionOptionsSortTypeEnum.Random) {
	
		var optionArray = new Array();
		for (var i=0; i<questionOptionList.length; i++) {
			optionArray.push({option:questionOptionList[i], value:getCharNumber(i + 1)});
		}
		
		for (var i=0; i<optionArray.length; i++) {
			html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"checkbox\" value=\"" + optionArray[i].value + "\" />" + getCharNumber(i + 1) + " " + optionArray[i].option + "</label>&nbsp;&nbsp;&nbsp;&nbsp;";
		}
	}
	html = html + "</div>";
	html = html + "<div class=\"questionAnswer\">标准答案：" + testPaperQuestion.answer + "</div>";
	html = html + "</div>";
	
	return html;
}

/** 
 * 显示判断题 
 */
function getQuestionHtmlByJudge(testPaperQuestion, index) {
	
	var html = "";
	var questionId = getQuestionId(testPaperQuestion.id);
	var questionOptionId = getQuestionOptionId(testPaperQuestion.id);
	
	html = html + "<div id=\"" + questionId + "\" class=\"question\">";
	html = html + "<div class=\"questionContent\">" + index + "、" + testPaperQuestion.name + "</div>";
	html = html + "<div class=\"questionOptions\">";
	html = html + "<span>答：</span>";
	html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"radio\" value=\"1\" />对</label>&nbsp;&nbsp;&nbsp;&nbsp;";
	html = html + "<label><input id=\"" + questionOptionId + "\" name=\"" + questionOptionId + "\" type=\"radio\" value=\"0\" />错</label>&nbsp;&nbsp;&nbsp;&nbsp;";
	html = html + "</div>";
	html = html + "<div class=\"questionAnswer\">标准答案：" + getJudgeAnswer(testPaperQuestion.answer) + "</div>";
	html = html + "</div>";
	
	return html;
}

/** 
 * 获取判断题答案 
 */
function getJudgeAnswer(answer) {
	
	if (answer == 1) {
		return "对";
	} else {
		return "错";
	}
}

/** 
 * 显示某题型的题目列表 
 */
function showTestPaperQuestionListByOption(testPaperOption) {
	
	var questionList = new Array();
	for (var i=0; i<userTestPaperBrowseData.questionList.length; i++) {
		if (userTestPaperBrowseData.questionList[i].questionType == testPaperOption.questionType) {
			questionList.push(userTestPaperBrowseData.questionList[i]);
		}
	}
	
	for (var i=0; i<questionList.length; i++) {
		showTestPaperQuestion(questionList[i], i + 1);
	}
}

/** 
 * 显示试卷主体
 */
function showTestPaperBody() {
	
	$("#questionArea").empty();
	
	var testPaperOptions = userTestPaperBrowseData.testPaper.testPaperOptions;
	var testPaperOptionList = $.parseJSON(testPaperOptions);
	
	for (var i=0; i<testPaperOptionList.length; i++) {
		
		if (testPaperOptionList[i].number == 0) {
			continue;
		}
		showTestPaperOption(testPaperOptionList[i], i + 1);
		showTestPaperQuestionListByOption(testPaperOptionList[i]);
	}
}

/** 
 * 显示答题结果 
 */
function showExamUserData() {

	var examUserQuestionList = $.parseJSON(userTestPaperBrowseData.data);
	
	for (var i=0; i<examUserQuestionList.length; i++) {
		showExamUserQuestion(examUserQuestionList[i]);
	}
}

/** 
 * 显示题目的答题情况 
 */
function showExamUserQuestion(examUserQuestion) {
	
	var questionOptionId = getQuestionOptionId(examUserQuestion.testPaperQuestionId);
	$("input[name='" + questionOptionId + "']").each(function(){
		if (examUserQuestion.userAnswer.indexOf(this.value) >= 0) {
			this.checked = true;
		} else {
			this.checked = false;
		}
	});
}

/** 
 * 显示考卷区域 
 */
function showTestPaperArea() {
	
	showTestPaperHeader();
	showTestPaperBody();
	showExamUserData();
}

/***工具区域******************************************************/
/** 
 * 获取时间字符串
 */
function getTimeStr(time) {
	
	var rt = "";
	var s = time % 60;
	var m = (time - s) / 60;
	var h = 0;
	
	if (m >= 60) {
		m = m%60;
		h = (time - s - 60*m)/3600;
	}
	
	if (s >= 10) {
		rt = rt + s;
	} else {
		rt = rt + "0" + s;
	}

	if (m >= 10) {
		rt = m + ":" + rt;
	} else {
		rt = "0" + m + ":" + rt;
	}
	
	if (h >= 10) {
		rt = h + ":" + rt;
	} else {
		if (h > 0) {
			rt = "0" + h + ":" + rt;
		}		
	}

	return rt;
}

/** 
 * 获取汉字数字
 */
function getHZNumber(index) {
	
	if (index==1) {
		return "一";
	}
	if (index==2) {
		return "二";
	}
	if (index==3) {
		return "三";
	}
	if (index==4) {
		return "四";
	}
	if (index==5) {
		return "五";
	}
	if (index==6) {
		return "六";
	}
	if (index==7) {
		return "七";
	}
	if (index==8) {
		return "八";
	}
	return "";
}

/** 
 * 获取汉字数字
 */
function getCharNumber(index) {
	
	if (index==1) {
		return "A";
	}
	if (index==2) {
		return "B";
	}
	if (index==3) {
		return "C";
	}
	if (index==4) {
		return "D";
	}
	if (index==5) {
		return "E";
	}
	if (index==6) {
		return "F";
	}
	if (index==7) {
		return "G";
	}
	if (index==8) {
		return "H";
	}
	return "";
}

/***枚举变量区域******************************************************/

/** 
 * 题目类型枚举
 */
var QuestionTypeEnum = {
   SingleSelect:0,
   MultiSelect:1,
   Judge:2
};

/** 
 * 考生题目答题状态枚举
 */
var ExamUserQuestionStatusEnum = {
   Right:0,
   Error:1
};

/** 
 * 题目排序方式枚举
 */
var QuestionSortTypeEnum = {
   Fixed:0,
   Random:1
};

/** 
 * 题目选项排序方式枚举
 */
var QuestionOptionsSortTypeEnum = {
   Fixed:0,
   Random:1
};

/** 
 * 考试计时方式枚举
 */
var TimerModeEnum = {
   None:0,
   Limit:1
};

/** 
 * 获取题目类型名称
 */
function getQuestionTypeName(questionType) {
	
	if (questionType == QuestionTypeEnum.SingleSelect) {
		return "单选题";
	}
	if (questionType == QuestionTypeEnum.MultiSelect) {
		return "多选题";
	}
	if (questionType == QuestionTypeEnum.Judge) {
		return "判断题";
	}

	return null;
}

/** 
 * 获取题目选项列表
 */
function getQuestionOptionList(questionOptions) {
	
	var questionOptionList = questionOptions.split(";");
	if (questionOptionList.length > 0 && questionOptionList[questionOptionList.length - 1] == "") {
		questionOptionList.pop();
	}
	
	return questionOptionList;
}

/** 
 * 获取题目ID
 */
function getQuestionId(id) {
	return "question-" + id;
}

/** 
 * 获取题目选项ID
 */
function getQuestionOptionId(id) {
	return "quesitonOption-" + id;
}

