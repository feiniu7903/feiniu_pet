/* 点击通过手机找回密码的事件 */
function getPasswordByMobileClick() {
	showGetPasswordByMobileDiv();
	refreshCheckCode("image");
}

/* 点击通过邮件找回密码的事件 */
function getPasswordByEmailClick() {
	showGetPasswordByMailDiv();
	refreshCheckCode("image1");
}

/* 通过手机找回密码 */
function getPasswordByMobileFunc() {
	$("#getPasswordByMobileTextError").html('');
    $("#getPasswordByMobileSecurityCodeTextError").html('');
	if (!checkFormat("^[0-9]{11}$",$("#getPasswordByMobileText").val())) {
		$("#getPasswordByMobileTextError").html('<font color=red>请输入正确的手机号</font>');
		refreshCheckCode('image');
		return;
	}
	if ($("#getPasswordByMobileSecurityCodeText").val().length == 0) {
		$("#getPasswordByMobileSecurityCodeTextError").html('<font color=red>请输入验证码</font>');
		refreshCheckCode('image');
		return;
	}
	var parameter = {
			mobile: $("#getPasswordByMobileText").val(),
			authenticationCode:$("#getPasswordByMobileSecurityCodeText").val() 
		};
    $.ajax({
		type: "POST",
        url: "/nsso/ajax/resetPassword.do",
        data: parameter,
        dataType: "json",
		success: function(data) {
			if (!data.valid) {
				$("#getPasswordByMobileSecurityCodeTextError").html("<font color='red'>\u9519\u8bef\uff01\u8bf7\u91cd\u65b0\u8f93\u5165</font>");
				refreshCheckCode('image');
				return;
			}
			$("#getPasswordByMobileSecurityCodeTextError").html("");
			if (!data.success) {
				$("#getPasswordByMobileTextError").html("<font color=red>对不起!系统未找到" +  parameter.mobile + "相应的注册信息,请确认!</font>");
				refreshCheckCode('image');
				return;
			}
			$("#getPasswordByMobileTextError").html("");
			$("#successSendMessageDiv").html("我们已向" + parameter.mobile + "手机发送了新密码，请您及时查收,您也可以到'我的驴妈妈'修改您的密码.");
			showSuccessSendMessageDiv();
		},
		error: function(data) {
				alert('服务器暂时无法响应，请稍后操作!');
				refreshCheckCode('image');
		}
	});	
}

/* 通过邮件找回密码 */
function getPasswordByEmailFunc() {
	$("#getPasswordByMailTextError").html('');
    $("#getPasswordByMailSecurityCodeTextError").html('');
	if (!checkFormat("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",$("#getPasswordByMailText").val())) {
		$("#getPasswordByMailTextError").html('<font color=red>请输入正确的邮箱地址</font>');
		refreshCheckCode('image1');
		return;
	}
	if ($("#getPasswordByMailSecurityCodeText").val().length == 0) {
		$("#getPasswordByMailSecurityCodeTextError").html('<font color=red>请输入验证码</font>');
		refreshCheckCode('image1');
		return;
	}
    $.ajax({
		type: "POST",
        url:  "/nsso/ajax/resetPassword.do",
        data: {
			email: $("#getPasswordByMailText").val(),
			authenticationCode:$("#getPasswordByMailSecurityCodeText").val() 
		},
        dataType: "json",
        success: function(data) {
			if (!data.valid) {
				$("#getPasswordByMailSecurityCodeTextError").html("<font color='red'>\u9519\u8bef\uff01\u8bf7\u91cd\u65b0\u8f93\u5165</font>");
				refreshCheckCode('image1');
				return;
            } 
			$("#getPasswordByMailSecurityCodeTextError").html("");
			if (!data.success){
				$("#getPasswordByMailSecurityCodeTextError").html("<font color=red>对不起!系统未找到" +  $("#getPasswordByMailText").val() + "相应的注册信息,请确认!</font>");
				refreshCheckCode('image1');
				return;
			}
			$("#successSendMessageDiv").html("我们已向" + $("#getPasswordByMailText").val() + "邮箱发送了密码重置邮件，请您及时查收.");
			showSuccessSendMessageDiv();
			},
		error: function(data) {
				alert('服务器暂时无法响应，请稍后操作!');
				refreshCheckCode('image');
		}
	});	
}
		
/* 更新验证码 */
function refreshCheckCode(id){
	 document.getElementById(id).src = "/nsso/account/checkcode.htm?now=" + new Date();
}

/* 显示成功发送消息的层 */
function showSuccessSendMessageDiv(){
	$("#successSendMessageDiv").css({
        "display": ""
    });
	$("#getPasswordByMailDiv").css({
        "display": "none"
    });
	$("#getPasswordByMobileDiv").css({
        "display": "none"
    });
	$("#getPasswordByMobileText").val("");
	$("#getPasswordByMailText").val("");
	$("#getPasswordByMobileSecurityCodeText").val("");
	$("#getPasswordByMailSecurityCodeText").val("");
	$("#getPasswordByMobileTextError").html("");
	$("#getPasswordByMailTextError").html("");
}

/* 显示通过手机找回密码的层 */
function showGetPasswordByMobileDiv(){
	$("#successSendMessageDiv").css({
        "display": "none"
    });
	$("#getPasswordByMailDiv").css({
        "display": "none"
    });
	$("#getPasswordByMobileDiv").css({
        "display": ""
    });
	$("#getPasswordByMobileText").val("");
	$("#getPasswordByMailText").val("");
	$("#getPasswordByMobileSecurityCodeText").val("");
	$("#getPasswordByMailSecurityCodeText").val("");
	$("#getPasswordByMobileTextError").html("");
	$("#getPasswordByMailTextError").html("");
}

/* 显示通过邮箱找回密码的层 */
function showGetPasswordByMailDiv(){
	$("#successSendMessageDiv").css({
        "display": "none"
    });
	$("#getPasswordByMailDiv").css({
        "display": ""
    });
	$("#getPasswordByMobileDiv").css({
        "display": "none"
    });
	$("#getPasswordByMobileText").val("");
	$("#getPasswordByMailText").val("");
	$("#getPasswordByMobileSecurityCodeText").val("");
	$("#getPasswordByMailSecurityCodeText").val("");
	$("#getPasswordByMobileTextError").html("");
	$("#getPasswordByMailTextError").html("")
}