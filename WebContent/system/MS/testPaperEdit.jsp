<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<table>
      	<tbody>
      	  <tr><th></th><td></td><th></th><td></td></tr>
      	  <tr><th>试卷名称：</th><td colspan="3"><input class="input_text input745x22" type="text" id="edName" name="edName"></td></tr>
          
          <tr><th valign="top">试卷分类：</th><td><select class="select input294x22 mart5" id="edCategory" name="edCategory"></select></td>
          <th valign="top">试卷总分：</th><td><input class="input_text input294x22 mart5" type="text" value="100" id="edTotalScore" name="edTotalScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /></td></tr>
          <tr><th valign="top">出题套数：</th><td><input class="input_text input294x22 mart5" type="text" id="edTotalSeries" name="edTotalSeries" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /></td>
          <th valign="top">通过分数：</th><td><input class="input_text input294x22 mart5" type="text" value="60" id="edPassScore" name="edPassScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /></td></tr>
          
          <tr><th valign="top">试卷状态：</th><td><select class="select input294x22 mart10 marb50" name="edStatus"  id="edStatus"></select></td>
           <th valign="top">班级：</th><td><select class="select input294x22 mart10 marb50" name="edClassType"  id="edClassType"></select></td></tr>
          
           <tr><th valign="top">保密设置：</th><td colspan="3"><div class="checkbox_area2 marb10 font12">
             <input class="checkbox_s marl55" type="checkbox" name="edCanQueryAnswer" id="edCanQueryAnswer" /><span class="marl10 grey_dark">允许查看答卷</span>
           </div></td></tr>
           <tr><th>题目排序：</th><td><select class="select input294x22" name="edQuestionSortType"  id="edQuestionSortType"></select></td>
           <th>选项排序：</th><td><select class="select input294x22" name="edQuestionOptionsSortType"  id="edQuestionOptionsSortType"></select></td></tr>
           <tr><th>分数设置：</th><td colspan="3"><div class="checkbox_area2 marb20 mart10">
           <input class="checkbox_s marl25" type="checkbox" id="edCanIgnoreScore" name="edCanIgnoreScore" /><span class="marl10 grey_dark font12">忽略试题原分数，按试卷题型设置分数</span>
           </div></td></tr>
           <tr><th valign="top">题型设置：</th><td colspan="3"><div class="checkbox_area line_se">
           <p class="mart5">
           <span class="font14 grey_dark" id="btnJudgeSelect">判断题：</span><span class="marl10 grey">每题分数：</span><input class="input_text input30x22" type="text" id="edJudgeScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">题目数量：</span><input class="input_text input30x22" type="text" id="edJudgeNum" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">出题顺序：</span><input class="input_text input30x22" type="text" id="edJudgeSortFlag" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="marl20 grey">说明：</span><input class="input_text input183x22" id="edJudgeNote" type="text" /></p>
           <p><span class="font14 grey_dark" id="btnSingleSelect">单选题：</span><span class="marl10 grey">每题分数：</span><input class="input_text input30x22" type="text" id="edSingleScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="marl20 grey">题目数量：</span><input class="input_text input30x22" type="text" id="edSingleNum"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">出题顺序：</span><input class="input_text input30x22" type="text" id="edSingleSortFlag" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">说明：</span><input class="input_text input183x22" id="edSingleNote" type="text" /></p>
           <p class="mart5"><span class="font14 grey_dark" id="btnMultipleSelect">多选题：</span><span class="marl10 grey">每题分数：</span><input class="input_text input30x22" id="edMultipleScore" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">题目数量：</span><input class="input_text input30x22" id="edMultipleNum" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="marl20 grey">出题顺序：</span><input class="input_text input30x22"  id="edMultipleSortFlag" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="marl20 grey">说明：</span><input class="input_text input183x22" id="edMultipleNote" type="text" /></p>
           <p class="marb10" ><span class="font14 grey_dark" id="btnNoSelect">非选题：</span><span class="marl10 grey">每题分数：</span><input class="input_text input30x22" type="text" id="edNoSelectScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span class="marl20 grey">题目数量：</span><input class="input_text input30x22" type="text" id="edNoSelectNum"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">出题顺序：</span><input class="input_text input30x22" type="text" id="edNoSelectFlag" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/><span class="marl20 grey">说明：</span><input class="input_text input183x22" id="edNoSelectNote" type="text" /></p>
           </div></td></tr>
          </tbody>
      </table>
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

 