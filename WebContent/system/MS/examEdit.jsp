<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->  
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<input type="hidden" name="edTestPaperId" id="edTestPaperId"/>
	<table>
        <tbody>
        	<tr><th></th><td></td><th></th><td></td></tr>
        	<tr><th>考试名称：</th><td colspan="3"><input class="input_text input745x22" type="text" id="edName"></td></tr>
            <tr><th valign="top">考试说明：</th><td colspan="3"><textarea class="input_text input745x124 mart5 marb40" id="edDescription"></textarea></td></tr>
            
            <tr><th></th><td colspan="4" class="pr "><div style='width:720px;' class="txtr"><input class="option_btn mart5" id="selectTestPaper" type="button" value="选用试卷"></div></td></tr>
            <tr><th>考试试卷：</th><td colspan="3" class="pr"><input class="input_text input745x22 mart5" id="edTestPaperName" readonly="readonly"></input></td></tr>
            <tr><th>试卷随机：</th><td><select class="select input294x22 mart10 marb5" name="edQuestionFetchType"  id="edQuestionFetchType"></select></td>
            <th>考试分类：</th><td><select class="select input294x22 mart10 marb5" id="edCategory" ></select></td></tr>
            <tr><th>考试代号：</th><td><input class="input_text input294x22 mart5 marb5" id="edExamCode" type="text" /></td>
            <th>考试方式：</th><td><select class="select input294x22 mart5 marb5" id="edExamMode" ></select></td></tr>
             
             <tr><td colspan="4"><div style="height:40px">　</div></td></tr>
             
             <tr><th valign="top">保密设置：</th><td colspan="3"><div class="checkbox_area2 marb10 font12">
             <input class="checkbox_s" type="checkbox" name="edCanKeepSecretScore" id="edCanKeepSecretScore" /><span class="marl10 grey_dark">考试分数保密</span>
             <input class="checkbox_s marl55" type="checkbox" name="edCanAllowMultiJoin" id="edCanAllowMultiJoin" /><span class="marl10 grey_dark">允许考完后再考</span>
             <input class="checkbox_s marl55" type="checkbox" name="edCanQueryAnswer" id="edCanQueryAnswer" /><span class="marl10 grey_dark">允许查看答卷</span>
             </div></td></tr>
             
             <tr><th valign="top">学员设置：</th><td colspan="3">
             <div class="checkbox_area">
	             <input class="checkbox_s" type="checkbox" name="edCanAllowAllUser" id="edCanAllowAllUser" /><span class="marl10 grey_dark">允许所有单位（包括下级单位）学员参加考试</span>
	             <input class="checkbox_s marl55" type="checkbox" name="edCanCourseStudyLimit" id="edCanCourseStudyLimit" /><span class="marl10 grey_dark">学员必须完成必修课程后才能参加考试</span>
             </div>
             </td></tr>
             <tr><th></th><td colspan="3"><div class="checkbox_area mart10">
             	<p><input id="edCanMatchDutyRank" name="edCanMatchDutyRank" class="checkbox_s" type="checkbox" /><span class="marl10">自动匹配职务级别</span></p>
                 <div style="display: none;" id="edSysParamType" class="edSysParamType font12" ></div>
             </div></td></tr>
             <tr><th></th><td colspan="3"><div class="checkbox_area mart10">
             	<p><input id="edCanMatchTrade" name="edCanMatchTrade" class="checkbox_s" type="checkbox" /><span class="marl10">自动匹配行业</span></p>
                  <div style="display: none;" id="edTrade" class="edTrade font12" ></div>
             </div></td></tr>
             
            <tr><th valign="top">时间设置：</th><td colspan="3">
             <div class="checkbox_area3 mart10 font12">
	             <input type="radio"  id="edNoTimerModeTime"  name="edTimerMode" value="0"/><span class="grey_dark"> 考试不计时</span>
	             <input class="marl20" type="radio" id="edTimerModeTime" name="edTimerMode"  value="1"/><span class="grey_dark"> 考试计时</span>
	             <span id="timeCount" style="display: none;" ><input class="input30x22 input_text marl10" type="text" id="edTimerLimit" ><span class=" grey_dark"> 分钟</span></span>

	             <input class="checkbox_s marl75" type="checkbox" name="edCanLimitValidTime" id="edCanLimitValidTime"><span class="marl10 grey_dark">规定开考有效时间段</span>
	             <input class="checkbox_s marl20" type="checkbox" name="edCanLimitCommitTime" id="edCanLimitCommitTime"><span class="marl10 grey_dark">允许统一交卷</span>
             </div>
             </td></tr>
             
             <tr id="beginShow" style="display: none;"><td class="grey_dark line_height50 font12" colspan="4" align="center"><div style="width:800px; " class='txtc mart10'>进场开考时间：<input type="text" id="edBeginName" class="input_text" onClick="WdatePicker()"/>  <select name="beginHour" id="beginHour" >
	    			 </select> 时 <select name="beginMinute" id="beginMinute"></select> 分</div></td></tr>
             <tr id="endShow" style="display: none;"><td class="grey_dark line_height30 font12" colspan="4" align="center"><div style="width:800px" class='txtc'>进场关闭时间：<input id="edEndName" type="text" class="input_text" onClick="WdatePicker()"/> <select name="endHour" id="endHour" >
		   				 </select> 时 <select name="endMinute" id="endMinute"></select> 分</div></td></tr>
        </tbody>
   </table>
    
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>