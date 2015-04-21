var MOBILE_REGX = /^(13[0-9]|147|15[0|1|3|5|6|7|8|9]|18[8|9|6|7|0|2])\d{8}$/;
var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var USERNAME_REGX = /^([^u4E00-u9FA5]|[a-zA-Z0-9_\-]){4,16}$/;
var PASSWORD_REGX = /^[A-Za-z0-9]{6,18}$/;
var Domain = "http://login.lvmama.com";

function mobileAndEmail_addcheck($form){
	$('#sso_mobileAndEmail').one("blur",function(){
		MobileAndEmailFieldOnBlur(this.value);
	}).one("focus",function(){
		//error_tip("#sso_mobileAndEmail_errorText","有效的手机和email将用来接收账户信息、订单通知");  
	});
}

function mobile_addcheck($form){
	$('#sso_mobile').one("blur",function(){
		MobileFieldOnBlur(this.value);
	}).one("focus",function(){;
		//error_tip("#sso_mobile","请填写您的真实手机号码，做为驴妈妈的登录账号");  
	});
}
	
function  username_addcheck($form){
	$('#sso_username').one("blur",function(){		
		UsernameFieldOnBlur(this.value);
	}).one("focus",function(){			
		//error_tip("#sso_username","4-16个字符，中英文均可，推荐使用中文名");  
	});
}
	
function password_addcheck($form){
	$('#sso_password').one("blur", function(){
		//PasswordFieldOnBlur(this.value);
	}).one("focus", function(){
		//error_tip("#sso_password","6-16个字符，推荐使用英文字母加数字的组合密码"); 
	});
}
	
function againPassword_addcheck($form){
	$('#sso_againPassword').one("blur",function(){
		//AgainPasswordFieldOnBlur(this.value);
	}).one("focus", function(){
		//error_tip("#sso_againPassword","请再次输入密码"); 
	});
}

function email_addcheck($form){
	$('#sso_email').one("blur",function(){
		EmailFieldOnBlur(this.value);
	}).one("focus",function(){
		//error_tip("#sso_email","请填写您的常用Email地址，做为驴妈妈的登录账号"); 
	});
}

function membership_addcheck($form){
	$('#sso_membership').one("blur", function(){
		MembershipFieldOnBlur(this.value);
	}).one("focus", function(){
		//error_tip("#sso_membership","请输入有效的会员卡号"); 
	});
}

/**
 * 校验码的校验
 */
function verifycode_addcheck($form){
	$('#sso_verifycode').one("blur",function(){
		VerifycodeFieldOnBlur(this.value);
	}).one("focus",function(){
		//error_tip("#sso_verifycode","请输入校验码"); 
	});
}


function MembershipFieldOnBlur(value){
	if (value.length == 0) {
		return true;
	}
	$.ajax({
		type: "POST",
		url: Domain + "/nsso/ajax/checkUniqueField.do",
		async: false,
		data: {
			membershipCard: value
		},
		dataType: "json",
		success: function(response) {
			if (response.success == false) {
				error_tip("#sso_membership","会员卡不存在或已被激活"); 
				return false;
			} else {
				return true;
			}
		}
	});			
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
				url: Domain + "/nsso/ajax/checkUniqueField.do",
				async: false,
				data: {
					mobile: value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						error_tip("#sso_mobileAndEmail_errorText","手机号码已经存在，请重新填写");  
						return false;
					} else {
						return true;
					}
				}
			});			
		}

		if (EMAIL_REGX.test(value)) {
			$.ajax({
				type: "POST",
				url: Domain + "/nsso/ajax/checkUniqueField.do",
				async: false,
				data: {
					email: value
				},
				dataType: "json",
				success: function(response) {
					if (response.success == false) {
						error_tip("#sso_mobileAndEmail_errorText","邮箱地址已经存在，请重新填写");  
						return false;
					} else {
						return true;
					}
				}
			});
		}		
	} else {
		error_tip("#sso_mobileAndEmail_errorText","请输入有效的手机号/email地址");  
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
			url: Domain + "/nsso/ajax/checkUniqueField.do",
			async: false,
			data: {
				userName: value
			},
			dataType: "json",
			success: function(response) {
				if (response.success == false) {
					error_tip("#sso_username","用户名已经存在，请重新填写");  
					return false;
				} else {
					return true;
				}
			}
		});				
	} else {
		error_tip("#sso_username","用户名长度为4-16个字符组成"); 
		return false;
	}	
}


/**
 * 手机表单域的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function MobileFieldOnBlur(value) {
	if (MOBILE_REGX.test(value)) {
		$.ajax({
			type: "POST",
			url: Domain +"/nsso/ajax/checkUniqueField.do",
			async: false,
			data: {
				mobile: value
			},
			dataType: "json",
			success: function(response) {
				if (response.success == false) {
					error_tip("#sso_mobile","手机号码已经存在，请重新填写");  
					return false;
				} else {
					return true;
				}
			}
		});			
	} else {
		error_tip("#sso_mobile","请输入有效的手机号");  
		return false;
	}	
}

/**
 * 邮箱表单域的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function EmailFieldOnBlur(value) {
	if (EMAIL_REGX.test(value)) {
		$.ajax({
			type: "POST",
			url: Domain +"/nsso/ajax/checkUniqueField.do",
			async: false,
			data: {
				email: value
			},
			dataType: "json",
			success: function(response) {
				if (response.success == false) {
					error_tip("#sso_email","邮箱地址已经被注册，请重新填写"); 
					return false;
				} else {
					return true;
				}
			}
		});			
	} else {
		error_tip("#sso_email","请输入有效的邮箱地址"); 
		return false;
	}	
}

	
/**
 * Email表单域的辅助信息域清空
 **/
function clearEmailTextErrorInfo() {
		clearContent("sso_email_pic");
		clearContent("sso_email_errorText");	
}


	
/**
 * 校验码表单域的onBlur事件
 * @param value
 * @returns {Boolean}
 */
function VerifycodeFieldOnBlur(value) {	
	if(value.length == 4){
		$.ajax({
			type: "POST",
			url: Domain +"/nsso/ajax/checkAuthenticationCode.do",
			async: false,
			data: {
				authenticationCode: value
			},
			dataType: "json",
			success: function(response) {						
				if (response.success == false) {
					error_tip("#sso_verifycode","校验码输入错误"); 
					return false;
				} else {
					return true;
				}
			}
		});
	}else{
		error_tip("#sso_verifycode","请输入4位校验码"); 
		return false;
	}
}


(function($){
$.extend($.fn,{
	addMethod:function($form,field)
	{		
		eval(field+"_addcheck()");
	},
	/**
	 * 验证表单时调用
	 * @param {Object} opts
	 */
	checkForm:function(opts)
	{		
		var $this=$(this);		
		function handle()
		{
			var valid=true;
			if(opts.checkBefore)
			{
				valid=opts.checkBefore.call();				
				if(valid!=undefined&&!valid)
					return false;
			}
			
			if(opts.fields)//需要操作的fields等
			{
				for(var i=0;i<opts.fields.length;i++)
				{
					$("#sso_"+opts.fields[i]).blur();
				}
				if($("input.input_border_red").size() > 0)
				{
					return false;
				}
				
			}		
			
			if(opts.checkAfter)
			{
				opts.checkAfter.call();	
			}else
			{
				$this.submit();	
			}
		}
		//if(!opts.submitButton)
		//{
		//	alert("表单当中不存在操作的按钮");
		//	return false;
		//}
		//$("#"+opts.submitButton).click(handle);
		if(opts.fields)//需要操作的fields等
		{
			for(var i=0;i<opts.fields.length;i++)
			{
				this.addMethod($this,opts.fields[i]);
			}
		}
		handle();
	}
	})
})(jQuery);