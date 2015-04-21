 var MOBILE_REGX = /^(13[0-9]|147|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|6|7|8|9])\d{8}$/;
var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var USERNAME_REGX = /^([^u4E00-u9FA5]|[a-zA-Z0-9_\-]){4,16}$/;
var PASSWORD_REGX = /^[A-Za-z0-9]{6,18}$/;

$(
	function(){ 
		//手机或者Email的校验
		if ($('#sso_mobileAndEmail').length > 0) {
			$('#sso_mobileAndEmail').blur(function(){
				clearMobileAndEmailTextErrorInfo();
				MobileAndEmailFieldOnBlur(this.value);
			});
			$('#sso_mobileAndEmail').focus(function(){
				clearMobileAndEmailTextErrorInfo();
				showContent("sso_mobileAndEmail_errorText","有效的手机和email将用来接收账户信息、订单通知");
			});

		}
		
		//用户名的校验
		if ($('#sso_username').length > 0) {
			$('#sso_username').blur(function(){
				clearUsernameTextErrorInfo();
				UsernameFieldOnBlur(this.value);
			});
			$('#sso_username').focus(function(){
				clearUsernameTextErrorInfo();
				showContent("sso_username_errorText","请输入4-16个字符(包括数字、字母、下划线、中文)");
			});
		}
		
		//密码的校验
		if ($('#sso_password').length > 0) {
			$('#sso_password').blur(function(){
				clearPasswordTextErrorInfo();
				PasswordFieldOnBlur(this.value);
			});
			$('#sso_password').focus(function(){
				clearPasswordTextErrorInfo();
				showContent("sso_password_errorText","请输入6-16位大小写英文字母或数字");
			});
		}
		
		//确认密码的校验
		if ($('#sso_againPassword').length > 0 && $('#sso_password').length > 0) {
			$('#sso_againPassword').blur(function(){
				clearAgainPasswordTextErrorInfo();
				AgainPasswordFieldOnBlur(this.value);
			});		
		}		
		
		//email的校验
		if ($('#sso_email').length > 0) {
			$('#sso_email').blur(function(){
				clearEmailTextErrorInfo();
				if (this.value == '') {
					this.value='例：zhangsan@163.com';
					return;
				}
				if (EMAIL_REGX.test(this.value)) {
					$.ajax({
						type: "POST",
						url: "/nsso/ajax/checkUniqueField.do",
						async: false,
						data: {
							email: this.value
						},
						dataType: "json",
						success: function(response) {
							if (response.success == false) {
								noValidEmailTextErrorInfo();
							} else {
								validEmailTextErrorInfo();
							}
						}
					});				
				} else {
					noValidEmailTextErrorInfo();
					$('#sso_email_errorText').html("<font color='red'>*请输入合法的邮箱地址</font>");
				}
			});
			$('#sso_email').focus(function(){
				$('#sso_email_pic').html();
				$('#sso_email_errorText').hide();
				if (this.value == '例：zhangsan@163.com') {
					this.value='';
				}
			});
		}	
    }
)

/**
 * 手机或者Email表单域的辅助信息域清空
 */
function clearMobileAndEmailTextErrorInfo() {
	clearContent("sso_mobileAndEmail_pic");
	clearContent("sso_mobileAndEmail_errorText");	
}

/**
 * Username表单域的辅助信息域清空
 **/
function clearUsernameTextErrorInfo() {
	clearContent("sso_username_pic");
	clearContent("sso_username_errorText");
}

/**
 * Password表单域的辅助信息域清空
 **/
function clearPasswordTextErrorInfo() {
	clearContent("sso_password_pic");
	clearContent("sso_password_errorText");
}

/**
 * 确认Password表单域的辅助信息域清空
 **/
function clearAgainPasswordTextErrorInfo() {
	clearContent("sso_againPassword_pic");
	clearContent("sso_againPassword_errorText");
}

/**
 * 手机或者Email表单域合法状态下的辅助信息域
 **/
function validMobileAndEmailTextErrorInfo() {
	showContent("sso_mobileAndEmail_pic","<img src='/nsso/images/input_ok.gif' />");
	clearContent("sso_mobileAndEmail_errorText");	
}

/**
 * Username表单域合法状态下的辅助信息域
 **/
function validUsernameTextErrorInfo() {
	showContent("sso_username_pic","<img src='/nsso/images/input_ok.gif' />");
	clearContent("sso_username_errorText");
}

/**
 * Password表单域合法状态下的辅助信息域
 **/
function validPasswordTextErrorInfo() {
	showContent("sso_password_pic","<img src='/nsso/images/input_ok.gif' />");
	clearContent("sso_password_errorText");
}

/**
 * 确认Password表单域合法状态下的辅助信息域
 **/
function validAgainPasswordTextErrorInfo() {
	showContent("sso_againPassword_pic","<img src='/nsso/images/input_ok.gif' />");
	clearContent("sso_againPassword_errorText");
}

/**
 * 手机或者Email表单域的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function MobileAndEmailFieldOnBlur(value) {
	if (EMAIL_REGX.test(value) || MOBILE_REGX.test(value)) {
		if (MOBILE_REGX.test(value)) {
			$.ajax({
				type: "POST",
				url: "/nsso/ajax/checkUniqueField.do",
				async: false,
				data: {
					mobile: value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						showContent("sso_mobileAndEmail_errorText","<font color='red'>*手机号码已经存在，请重新填写</font>");	
						return false;
					} else {
						validMobileAndEmailTextErrorInfo();
						return true;
					}
				}
			});			
		}

		if (EMAIL_REGX.test(value)) {
			$.ajax({
				type: "POST",
				url: "/nsso/ajax/checkUniqueField.do",
				async: false,
				data: {
					email: value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						showContent("sso_mobileAndEmail_errorText","<font color='red'>*邮箱地址已经存在，请重新填写</font>");
						return false;
					} else {
						validMobileAndEmailTextErrorInfo();
						return true;
					}
				}
			});
		}		
	} else {
		showContent("sso_mobileAndEmail_errorText","<font color='red'>*请输入有效的手机号/email地址</font>");
		return false;
	}
	
}

/**
 * 用户名表单域的onBlur事件
 * @param value
 */
function UsernameFieldOnBlur(value) {
	if (USERNAME_REGX.test(value)) {
		$.ajax({
			type: "POST",
			url: "/nsso/ajax/checkUniqueField.do",
			async: false,
			data: {
				userName: value
			},
			dataType: "json",
			success: function(response) {
				if (response.success == false) {
					showContent("sso_username_errorText","<font color='red'>*用户名已经存在，请重新填写</font>");
					return false;
				} else {
					validUsernameTextErrorInfo();
					return true;
				}
			}
		});				
	} else {
		showContent("sso_username_errorText","<font color='red'>*用户名长度为4-16个字符组成</font>");
		return false;
	}	
}

/**
 * 密码框的onbBlur事件
 * @param value
 * @returns {Boolean}
 */
function PasswordFieldOnBlur(value) {
	if (value.length < 6 || value.length > 16) {
		showContent("sso_password_errorText","<font color='red'>*密码长度错误</font>");
		return false;
	}
	if (PASSWORD_REGX.test(this.value)) {
		validPasswordTextErrorInfo();
		return true;
	} else {
		showContent("sso_password_errorText","<font color='red'>*密码格式错误</font>");
		return false;
	}
}

/**
 * 确认密码框的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function AgainPasswordFieldOnBlur(value) {
	if (value.length < 6 || value.length > 16) {
		showContent("sso_againPassword_errorText","<font color='red'>*密码长度错误</font>");
		return false;
	}
	if (!PASSWORD_REGX.test(this.value)) {
		showContent("sso_againPassword_errorText","<font color='red'>*密码格式错误</font>");
		return false;
	}
	if (value != $('#sso_password').val()) {
		showContent("sso_againPassword_errorText","<font color='red'>*两次密码不一致</font>");
		return false;
	}
	validAgainPasswordTextErrorInfo();
	return true;
}

	

/**
 * Email表单域的辅助信息域清空
 **/
function clearEmailTextErrorInfo() {
	if ($('#sso_email_pic').length > 0) {
		$('#sso_email_pic').empty();
		$('#sso_email_pic').hide();
	}
	if ($('#sso_email_errorText') > 0) {
		$('##sso_email_errorText').empty();
		$('#sso_email_errorText').hide();
	}
}

/**
 * Email表单域合法状态下的辅助信息域
 **/
function validEmailTextErrorInfo() {
	if ($('#sso_email_pic').length > 0) {
		$('#sso_email_pic').html("<img src='/nsso/images/input_ok.gif' />");
		$('#sso_email_pic').show();
	}
	if ($('#sso_email_errorText') > 0) {
		$('##sso_email_errorText').empty();
		$('#sso_email_errorText').hide();
	}
	
}

/**
 * Email表单域非法状态下的辅助信息域
 **/
function noValidEmailTextErrorInfo() {
	
	if ($('#sso_email_pic').length > 0) {
		$('#sso_email_pic').empty();
		$('#sso_email_pic').hide();
	}
	if ($('#sso_email_errorText').length > 0) {
		$('#sso_email_errorText').html("<font color='red'>*邮箱地址已经存在，请重新填写</font>");
		$('#sso_email_errorText').show();
	}
	
}



/**
 * 指定元素展示指定内容
 * @param name
 * @param content
 */
function showContent(name, content) {
	if ($('#' + name).length > 0) {
		$('#' + name).html(content);
		$('#' + name).show();
	} 
}

function clearContent(name) {
	if ($('#' + name).length > 0) {
		$('#' + name).empty();
		$('#' + name).hide();
	} 
}


