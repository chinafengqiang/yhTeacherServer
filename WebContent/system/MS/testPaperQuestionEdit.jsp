<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<table>
      	  <tbody>
      	    <tr><th>分数：</th><td><input class="input_text input294x22" type="text" id="edcScore" name="edcScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
      	    <th>题号：</th><td><input class="input_text input294x22" type="text" id="edSortFlag" name="edSortFlag" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td></tr>
          </tbody>
      </table>
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnPaperSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnPaperClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

 