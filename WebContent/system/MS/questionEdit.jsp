<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-- 数据编辑区域  -->          
<div class="unit_edit" style="clear:both;">
	<input type="hidden" name="dataId" id="dataId"/>
	<input type="hidden" name="questionType" id="questionType"/>
	<input type="hidden" name="edOptions" id="edOptions"/>
	<input type="hidden" name="edAnswer" id="edAnswer"/>
	<div id="editPage" style="display: none;">
		<table>
	    	<tbody>
	    		<tr><th></th><td></td><th></th><td></td></tr>
	    		<tr><th></th><td></td><th></th><td><div class="padl200"><input id="btnUploadImage" class="option_btn" type="button" value=" 上传图片 " /></div></td></tr>
	    		<tr><th>题号：</th><td colspan="3"><input id="edName" name="edName" class="input_text input745x22" type="text"></td></tr>
	        	<tr><th>分数：</th><td><input class="input_text input294x22"  name="edScore"  type="text" id="edScore" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
	        	<th>难度：</th><td><select class="select input294x22"  name="edDifficulty"  id="edDifficulty" ></select></td></tr>
	        	<tr><th>知识点：</th><td colspan="3"><input id="edKen" class="input_text input745x22" type="text"></td></tr>
	        	<tr><th>题目路径：</th><td colspan="3"><input id="edNote" class="input_text input745x22" type="text"></td></tr>
	    	</tbody>
	    </table>
	    <div id="rightAnswer" style="width:900px; margin-top:20px; text-align:center">请输入选项并选择正确答案</div>
    </div>
    <div class="txtc">
      	<input id="btnSingleSelect" class="option_btn mart35 marr25" type="button" value="单选题">
        <input id="btnMultipleSelect" class="option_btn mart35 marr25" type="button" value="多选题">
        <input id="btnJudgeSelect" class="option_btn mart35 marr25" type="button" value="判断题">
        <input id="btnNoSelect" class="option_btn mart35 marr25" type="button" value="非选题">
    </div>
    
     <!--单选题 -->
     <div id="singleSelect" class="check_choose" style="display: none;">
      	  <p><span class="display_ib w20 font14">A:</span><input type="text" id="edSingle1" name="edSingle1" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="1" checked="checked" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">B:</span><input type="text" id="edSingle2" name="edSingle2" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="2" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">C:</span><input type="text" id="edSingle3" name="edSingle3" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="3" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">D:</span><input type="text" id="edSingle4" name="edSingle4" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="4" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">E:</span><input type="text" id="edSingle5" name="edSingle5" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="5" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">F:</span><input type="text" id="edSingle6" name="edSingle6" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="6" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">G:</span><input type="text" id="edSingle7" name="edSingle7" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="7" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">H:</span><input type="text" id="edSingle8" name="edSingle8" class="input294x22 input_text marr12" /><input type="radio" name="edSingleAanswer" value="8" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
      </div>
    
    <!--多选题 -->
      <div id="multipleSelect" class="check_choose" style="display: none;">
      	<p><span class="display_ib w20 font14">A:</span><input type="text"   id="edMultiple1" name="edMultiple1" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="1" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">B:</span><input type="text" id="edMultiple2" name="edMultiple2" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="2" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">C:</span><input type="text" id="edMultiple3" name="edMultiple3" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="3" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">D:</span><input type="text" id="edMultiple4" name="edMultiple4" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="4" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">E:</span><input type="text" id="edMultiple5" name="edMultiple5" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="5" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">F:</span><input type="text" id="edMultiple6" name="edMultiple6" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="6" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">G:</span><input type="text" id="edMultiple7" name="edMultiple7" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="7" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
          <p><span class="display_ib w20 font14">H:</span><input type="text" id="edMultiple8" name="edMultiple8" class="input294x22 input_text marr12" /><input type="checkbox" name="edMultipleAanswer" value="8" class="checkbox_s marr10" /><span class="grey_dark">正确答案</span></p>
      </div>
      
      <!--判断题 -->
      <div id="judgeSelect" class="check_choose_judge" style="display: none; padding-left:50px;">
	      <p><span class="display_ib w20 font14"></span>
	      <input type="radio" name="edJudge" id="edJudge" value="1" /> 对&nbsp;&nbsp;&nbsp;&nbsp;
	      <input type="radio" name="edJudge" id="edJudge" value="0" /> 错</p>
      </div>
      
      <!--内容 -->
      <div id="noSelect" class="check_choose_judge" style="display: none; padding-left:50px;">
	      <p><span class="display_ib w20 font14"></span>
      </div>
    
    <!-- 按钮区域  -->
    <div class="txtc">
    	<input id="btnSave" class="option_btn mart24 marr45" type="button" value="保&nbsp;&nbsp;&nbsp;存">
    	<input id="btnClose" class="option_btn mart24 marr45" type="button" value="关&nbsp;&nbsp;&nbsp;闭">
    </div>
</div>

<!-- 上传图片结果展示区域  -->
<div class="uploadImagePopup">
	<div class="picture"><img id="imageShow" src=""/></div>
	<div class="url" class=""></div>
	<div class="note">请首先复制上面的链接，然后点击键盘左上角的【esc】键关闭</div>
</div>
