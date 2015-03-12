<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<table>
      	<tbody>
      	  <tr><th></th><td></td><th></th><td></td></tr>
      	  <tr><th>发送带宽：</th><td colspan="3"><input class="input_text input745x22" type="text" id="bandwidth" name="bandwidth" value="1000">(kpbs)</td></tr>
      	  <tr><th>重发次数：</th><td colspan="3"><input class="input_text input745x22" type="text" id="repeatcount" name="repeatcount" value="1">(次)</td></tr>
          <tr><th>开始发送时间：</th><td colspan="3"><input class="input_text input745x22" type="text" id="beginTime" name="beginTime" placeholder="点击开始发送时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td></tr>
          <tr><th>结束发送时间: </th><td colspan="3"><input class="input_text input745x22" type="text" id="endTime" name="endTime" placeholder="点击结束发送时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td></tr>
          </tbody>
      </table>
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSendSave" class="option_btn mart24 marr45" type="button" value="发&nbsp;&nbsp;&nbsp;送">
    	<input id="btnSendClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

 