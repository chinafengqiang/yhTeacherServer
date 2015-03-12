<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<input type="hidden" name="categoryId" id="categoryId"/>
	<table>
    	<tbody>
        	<tr><th>分类名称：</th><td><input class="input_text input294x22"  name="edName"  id="edName"></td>
        	<th>排序：</th><td><input class="input_text input294x22"  name="edSortFlag"  id="edSortFlag" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td></tr>
    	</tbody>
    </table>
    
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnCategorySave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnCategoryClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

