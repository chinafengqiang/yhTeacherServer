<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<table>
    	<tbody>
    		<tr><th>名称：</th><td><input class="input_text input294x22"  name="edName"  id="edName" ></td>
        	<th>状态：</th><td><select class="select input294x22"  name="edStatus"  id="edStatus"></select></td></tr>
    		<tr><th>服务器地址：</th><td colspan="3"><input id="edUrl" class="input_text input745x22" type="text"></td></tr>
    		<tr><th>内网地址：</th><td colspan="3"><input id="edDirectUrl" class="input_text input745x22" type="text"></td></tr>
        	<tr><th>说明：</th><td colspan="3"><input id="edNote" class="input_text input745x22" type="text"></td></tr>
    	</tbody>
    </table>
    
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>
