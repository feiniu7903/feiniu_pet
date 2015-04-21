//<!--校验“手机文本域”。校验正确，执行传入的成功函数；否则打印错误信息。-->
function validMobileField(success_function) {
	$
			.ajax({
				url : "/ajax/checkUniqueField.do",
				type : "post",
				dataType : "json",
				data : {
					mobile : $("#mobileOrEmail").val()
				},
				success : function(response) {
					if (response.success == true) {
						error_tip("#mobileOrEmail",
								"该手机号尚未注册，请重新输入或<a href='/register/registering.do'>立即注册</a>");
					} else {
						success_function();
					}
				}
			});
}

// <!--校验码文件域。若校验正确，则发送“填写新密码”请求；否则打印错误信息。-->
function validAutheCodeField() {
	$.ajax({
		url : "/ajax/validateAuthenticationCode.do",
		type : "post",
		dataType : "json",
		data : {
			mobile : $("#mobileOrEmail").val(),
			authenticationCode : $("#validateNumber").val()
		},
		success : function(response) {
			if (response.success == true) {
				$("#submitForm").submit();
			} else {
				error_tip("#validateNumber", response.errorText);
			}
		}
	});
}

// <!--发送手机短信。-->
function sendMessage() {
	$.ajax({
		url : "/ajax/findpass/sendMessage",
		type : "post",
		dataType : "json",
		data : {
			mobileOrEMail : $("#mobileOrEmail").val()
		},
		success : function(response) {
			if (response.success == true) {
				yzm_send_ok();
			} else {
				error_tip("#sendCodeBtn", response.errorText);
			}
		}
	});
}

// <!--邮箱地址域校验。校验成功，执行success_function函数-->
function validEmailField(success_function) {
	$
			.ajax({
				type : "POST",
				async : false,
				url : "/ajax/checkUniqueField.do",
				data : {
					email : $("#mobileOrEmail").val()
				},
				dataType : "json",
				success : function(response) {
					if (response.success == true) {
						error_tip("#mobileOrEmail",
								"该邮箱尚未注册，请重新输入或<a href='/register/registering.do'>立即注册</a>");
						return false;
					} else {
						success_function();
					}
				}
			});
}

// <!--校验码文件域校验-->
function validCodeField() {
	var validateCode = $('#validateCode').val();

	$.ajax({
		url : "/ajax/findpass/sendMessage",
		type : "post",
		dataType : "json",
		data : {
			mobileOrEMail : $('#mobileOrEmail').val(),
			validateCode : validateCode
		},
		success : function(response) {
			if (response.success == true) {
				window.location.href = "/findpass/sendResetEmailSucc?email="
						+ $('#mobileOrEmail').val();
			} else {
				error_tip("#validateCode", response.errorText);
				return false;
			}
		}
	});
}

function noteTime() {
	document.getElementById('sendCodeBtn').disabled = true; // 将按钮设为失效
	for ( var i = 1; i <= 60; i++) {
		window.setTimeout("update(" + i + ")", i * 1000);
	}
}

function update(num) {
	if (num == 60) {
		var resendBtn = document.getElementById('sendCodeBtn');
		resendBtn.value = "发送校验码";
		resendBtn.disabled = false;
	} else {
		printnr = 60 - num;
		document.getElementById('sendCodeBtn').value = printnr + "秒后点此重发";
	}
}