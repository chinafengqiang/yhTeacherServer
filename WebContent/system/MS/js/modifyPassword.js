/**  
 * 修改密码
 */

$(function() {
	
	showLoading(false);
	
	//点击修改密码按钮事件
	$("#btnModifyPassword").click(function() {
		
		showLoading(true);
		
		try {
			var oldPassword = PageUtility.getFormDataByString("oldPassword", "旧密码", false, 50);
			var newPassword = PageUtility.getFormDataByString("newPassword", "新密码", false, 50);
			var confirmedPassword = PageUtility.getFormDataByString("confirmedPassword", "验证用的新密码", false, 50);
			
			if (newPassword != confirmedPassword) {
				throw "两次输入的新密码不一致！"
			}
			
			var paramters = {
				"oldPassword": oldPassword,
				"newPassword": newPassword
			};
			
			var actionModifyPassword = PageUtility.getContextPath() + "/Manager/modifyPassword.action";
			var result = PageUtility.ajaxAction(actionModifyPassword, paramters);
			
			alert(result);
			
			history.back();
		} catch (ex) {
			alert(ex);
		}
		
		showLoading(false);
	});

	//点击下一页按钮事件
	$("#btnCancel").click(function() {
		
		history.back();
	});
 
})

//显示/隐藏Loading图片
function showLoading(visible) {
	
	if (visible) {
		$("#loading").show();
	} else {
		$("#loading").hide();
	} 
}



