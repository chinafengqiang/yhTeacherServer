<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="../jquery/jquery.min.js"></script>
		<script type="text/javascript" src="../jquery/jquery.ocupload.js"></script>
		<script type="text/javascript" src="../jquery/jquery.bpopup.min.js"></script>
		</head>
		
		<script type="text/javascript">
		
		function MyMsgDiv()
{
    this.msgDiv = null;
    this.msgStyle = "FONT-SIZE:12px;LEFT:expression(this.offsetParent.scrollLeft+20);COLOR:#ffffff;LINE-HEIGHT:150%;POSITION:absolute;TOP:expression(this.offsetParent.scrollTop+10);BACKGROUND-COLOR:#7f1ef6;";
    this.ffMsgStyle = "FONT-SIZE:12px;LEFT:20px;COLOR:#ffffff;LINE-HEIGHT:150%;POSITION:absolute;TOP:10px;BACKGROUND-COLOR:#7f1ef6;";
    this.createDiv = function()
    {
        this.msgDiv = document.createElement("div");
        if(window.XMLHttpRequest)
        {
            this.msgDiv.style.cssText = this.ffMsgStyle;
        }
        else
        {
            this.msgDiv.style.cssText = this.msgStyle;
        }
        document.body.insertBefore(this.msgDiv, document.body.childNodes[0]);
    }
    
    this.Show = function(msgstr)
    {
        this.msgDiv.innerHTML = "&nbsp;" + msgstr + "&nbsp;";
    }
    
    this.createDiv(); //放置此处是为了防止以下的md值为null 
    
    var closetime;
    var md = this.msgDiv;
    this.Show = function(msg,showtime)
    {
        md.innerHTML ="&nbsp;"+msg+"&nbsp;";
        md.style.visibility = "visible";
        if(closetime)window.clearTimeout(closetime);
        closetime=setTimeout(this.Hidden,showtime*1000);
    }
    this.Hidden = function()
    {
        md.innerHTML = "";
        md.style.visibility = "hidden";
    }
}
function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();k
        if (now.getTime() > exitTime)
            return;
    }
}
function test()
{
   var md = new MyMsgDiv();
   md.Show("Hello, world!", 3);
}
		$(document).ready(function(){
		

            /*
           	$("#importUserList").upload({
                action: 'http://localhost:8080/Study/User/importUserList.action',
                name: 'file',
                params: {questionCategoryId:1},
                onSelect: function (self, element) {
                    this.autoSubmit = false;
					this.submit();
                },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });
            
			 $("#importQuestionList").upload({
                action: 'http://localhost:8080/Study/Question/importQuestionList.action',
                name: 'file',
                params: {questionCategoryId:1},
                onSelect: function (self, element) {
                    this.autoSubmit = false;
					this.submit();
                },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });
            
            $("#uploadArticleImage").upload({
                action: 'http://localhost:8080/Study/Article/uploadArticleImage.action',
                name: 'file',
                params: {},
                onSelect: function (self, element) {
                    this.autoSubmit = false;
					this.submit();
                },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });
                       */
                       
                    			
        	$("#importCourse").upload({
                action: 'http://localhost:8080/Study/Course/importCourse.action',
                name: 'file',
                iframeName: 'bbb',
                params: {courseId:1},
                onSelect: function (self, element) {
                    this.autoSubmit = false;
					this.submit();
                },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });      
                
            $("#importArticle").upload({
                action: 'http://localhost:8080/Study/Article/importArticle.action',
                name: 'file',
                iframeName: 'aaa',
                params: {},
                onSelect: function (self, element) {
                    this.autoSubmit = false;
					this.submit();
                },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });
            
            $("#importSoftwareReportExamData").upload({
                action: 'http://localhost:8080/Study/Report/importSoftwareReportExamData.action',
                name: 'file',
                iframeName: 'aaab1',
                onSelect: function (self, element) {
                    this.autoSubmit = false;
                    
                    var reportExamId = $("#reportExamId").val();
                    if (reportExamId > 10) {
                        this.clear();
                    	alert("reportExamId太大了！");
                    	return;
                    }
                     
	                this.params({reportExamId:reportExamId, organId:9, passScore:60});
    	            this.submit();
	            },
                onSubmit: function (self, element) {
                	$("#loading").show();	
                },
                onComplete: function (data, self, element) {
                	$("#loading").hide();	
                	$("#result").html(data);
                    //alert(data);
                }
            });
            
            
            $("#testPopup").click(function(){
 				$('#popup').bPopup({
 				opacity:0.1,
 				autoClose:false
 				
 				});          
            });

		});
	</script>		
	<body>
		<div>
		<img src="loading.gif" id="loading" style="display: none;">
		<div id="uploadArticleImage">上传文章图片</div>
		<div id="importQuestionList">导入题目</div>
		<div id="importCourse">导入课程</div>
		<div id="importArticle">导入文章</div>
		<div id="importUserList">导入学员</div>
		<div id="importSoftwareReportExamData">导入软件生成的考生成绩包</div>
		<div id="menuId">90</div>
		<input type="text" id="reportExamId" value=11 />
		<br/>
		<br/>
		<br/>
		<div id="testPopup">测试弹出框</div>
		<div><img id="showPic"/></div>
		
		<div id="result">none</div>
		<div id="popup" style="display:none; color:red; width:90px; height:30px;border-style: solid; border-width: 1px;background-color: green; padding: 20px;">awefawfwafeaef</div>
		</div>
	</body>
</html>
