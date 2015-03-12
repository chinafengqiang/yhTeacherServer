<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<input type="hidden" name="sysParamType" id="sysParamType"/>
	<table>
    	<tbody>
    		<tr><th></th><td></td><th></th><td></td></tr>
    		<tr><th>名称：</th><td colspan="3"><span id="edName" class="font12"></span></td></tr>
        	<tr><th valign="top">内容：</th><td colspan="3"><input id="edValue" class="input_text input745x22 mart5"></input></td></tr>
        	<tr><th valign="top">说明：</th><td colspan="3"><span id="edNote" class="font12"></span></td></tr>
    	</tbody>
    </table>
    
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

 