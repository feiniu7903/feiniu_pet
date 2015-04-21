function putErrorSpan(id, msg) {
	$("#" + id).siblings(".tureTipsBox").hide();
	var html = "<span class='tip-icon tip-icon-error'></span>" + msg;
	$("#" + id).siblings(".errorTipsBox").html(html);
}
function putOkSpan(id) {
	$("#" + id).siblings(".errorTipsBox").html("");
	$("#" + id).siblings(".tureTipsBox").show();
}

var cashAccount = {
	errorPlacement : function(error, element) {
		if (error.html() == "") {
			putOkSpan(element.attr("id"));
		} else {
			putErrorSpan(element.attr("id"), error.html());
		}
	},
	success : function(element) {
		putOkSpan(element.attr("id"));
	},
	rules : {
		mobile : {
			required : true,
			isphone : true,
			clientRemote : {
				url : "/ajax/checkPayMobileUnique.do",
				type : "get",
				dataType : 'json',
				data : {
					oldmobile : function() {
						var oldMobile = $("#oldMobile").val();
						return oldMobile ? oldMobile : null;
					}
				}
			}
		},
		pay_mobile2 : {
			required : true,
			isphone : true,
			equalTo : "#pay_mobile1"
		},
		authenticationCode : {
			required : true,
			digits : true,
			clientRemote : {
				url : "/ajax/validateAuthenticationCode.do",
				type : "get",
				dataType : 'json',
				data : {
					mobile : function() {
						var mobile = $("#oldMobile").val();
						if (!mobile)
							mobile = $("#pay_mobile2").val();
						return mobile;
					}
				}
			}
		},
		oldPayPass : {
			required : true,
			rangelength : [ 6, 16 ],
			clientRemote : {
				url : "/userspace/cashAccount/validOldPwd.do",
				type : "get",
				dataType : 'json'
			}
		},
		payPassword : {
			required : true,
			rangelength : [ 6, 16 ]
		},
		payPasswordTwo : {
			required : true,
			equalTo : "#payPassword",
			rangelength : [ 6, 16 ]
		},

		oldLoginPass : {
			required : true,
			rangelength : [ 6, 16 ],
			clientRemote : {
				url : "/user/validOldPwd.do",
				type : "get",
				dataType : 'json'
			}
		},
		loginPassword : {
			required : true,
			rangelength : [ 6, 16 ]
		},
		loginPasswordTwo : {
			required : true,
			rangelength : [ 6, 16 ],
			equalTo : "#loginPassword"
		}
	},
	messages : {
		mobile : {
			required : "请输入手机号码",
			isphone : "请输入11位数字的手机号码",
			clientRemote : "该手机号已被绑定,不能重复绑定相同的手机号"
		},
		pay_mobile2 : {
			required : "请输入手机号码",
			isphone : "请输入11位数字的手机号码",
			equalTo : "两次手机号输入不一致"
		},
		authenticationCode : {
			required : "请输入验证码",
			digits : "验证码格式必须为数字",
			clientRemote : "无效的验证码"
		},
		oldPayPass : {
			required : "请输入当前支付密码",
			clientRemote : "支付密码不正确",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		},
		payPassword : {
			required : "请输入支付密码",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		},
		payPasswordTwo : {
			required : "请输入重复支付密码",
			equalTo : "两次输入的支付密码不一致",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		},
		oldLoginPass : {
			required : "请输入当前登录密码",
			clientRemote : "登录密码不正确",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		},
		loginPassword : {
			required : "请输入登录密码",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		},
		loginPasswordTwo : {
			required : "请输入重复登录密码",
			equalTo : "两次输入的登录密码不一致",
			rangelength : jQuery.format("{0}-{1}个字符，推荐使用英文字母加数字的组合密码")
		}
	}
};

function JS_countdown(_cdbox, count, This) {
	var _InterValObj; // timer变量，控制时间
	var _count = 60; // 间隔函数，1秒执行
	var _curCount; // 当前剩余秒数

	sendMessage(_count);

	function sendMessage(_count) {
		_curCount = _count;
		$(_cdbox).html(_curCount);
		_InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
	}

	// timer处理函数
	function SetRemainTime() {
		if (_curCount == 0) {
			window.clearInterval(_InterValObj); // 停止计时器
			// var expr = _cdbox.indexOf("-old")>0?"-old":"";
			count
					.children(".tip-success")
					.html(
							'<span class="tip-icon tip-icon-success"></span>校验码已发送成功，以最近发送的验证码为准')
					.end().hide();
			This.html("重新发送验证码").show();
			$(".sendcodeinfo").hide();
			$(".sendcode").html("重新发送验证码").show();
		} else {
			_curCount--;
			$(_cdbox).html(_curCount);
			// alert("aaa");
		}
	}
}
