
<%@page import="com.lvmama.tnt.user.po.TntUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet"
	href="http://pic.lvmama.com/min/index.php?f=/styles/v4/modules/dialog.css,/styles/v4/modules/button.css,/styles/v5/modules/tip.css">
<link rel="stylesheet"
	href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css" />
<base target="_self">
<style>
.logoFormBox {
	margin: 20px;
	height: 30px;
	clear: both;
	overflow: hidden;
}

.logoFormBox .textBox {
	padding: 0 10px;
	text-align: center;
	float: left;
	line-height: 30px;
	height: 30px;
}

.logoFormBox .login_text {
	height: 28px;
	line-height: 28px;
	width: 200px;
	float: left;
}

.logoFormBox .teshuText {
	width: 80px;
}

.yzmpic {
	display: inline-block;
	width: 60px;
	height: 30px;
	background: #000;
	float: left;
	margin-left: 10px;
}

.hyhBtn {
	display: inline-block;
	padding: 0 10px;
	float: left;
	line-height: 30px;
	color: #0077CC;
}

.logoFormBox .login_submit {
	margin-left: 67px;
}

.logoFormBox .login_input_bottom {
	margin-left: 45px;
}
</style>
<div id="loginFrame" style="display: none">
	<sf:form action="/ajaxLogin" id="loginForm" modelAttribute="tntUser">
		<div class='logoFormBox'>
			<label class='textBox'>账 &#160;&#160;户：</label>
			<sf:input class="login_text" path="userName" />
		</div>
		<div class='logoFormBox'>
			<label class='textBox'>密 &#160;&#160;码：</label>
			<sf:password class="login_text" path="loginPassword" />
		</div>
		<div class='logoFormBox'>
			<label class='textBox'>验证码：</label> <input
				class="login_text teshuText" type="text" id="imageCode"
				name="imageCode" /> <span class='yzmpic'> <img id="image"
				src="/tnt/code.png"></span> <a
				onclick="javascript:refreshCheckCode('image');return false;"
				class='hyhBtn' target="_self" href="#">换一张</a>
		</div>
		<div class='logoFormBox'>
			<span class="login_sp login_submit"></span><a class='link_blue'
				style='position: relative; left: -12px; top: -16px;'
				href="/findpass/index">忘记密码？</a>
		</div>
		<div class='logoFormBox'>
			<p class='login_input_bottom login_input_bottom_1'>
				<span style="color: red" id="msgSpan"></span>
			</p>
		</div>
		<div class='logoFormBox'>
			<p class='login_input_bottom login_input_bottom_1'>
				还不是驴妈妈会员？<a class='link_blue login_mfzc' href="/reg/index">免费注册</a>
			</p>
		</div>
	</sf:form>
</div>
<script type="text/javascript">
	function beforeLogin() {
		if ($.trim($("#userName").val()) == "") {
			$("#msgSpan").text("请输入会员登录名");
			return false;
		}
		if ($.trim($("#loginPassword").val()) == "") {
			$("#msgSpan").text("请输入密码");
			return false;
		}
		if ($.trim($("#imageCode").val()) == "") {
			$("#msgSpan").text("请输入验证码");
			return false;
		}
		return true;
	}

	function refreshCheckCode(s) {
		var elt = document.getElementById(s);
		elt.src = elt.src + "?_=" + new Date().getTime();
	}

	function enterHandler(event) {
		if (event.keyCode == 13) {
			$("#loginBtn").click();
		}
	}
	$(function() {
		$("#userName").focus();
	});

	var checkLogin = function(callbacks, params) {
		$.getJSON("/checkLogin", function(data) {
			var json = eval(data);
			if (json) {
				$.getJSON("/userspace/cashAccount/check", function(response) {
					if (response.success == true) {
						callbacks.fire(params);
					} else {
						$.dialog({
							ok : true,
							okValue : "确定",
							title : "提示框",
							content : response.errorText
						});
					}
				});
			} else {
				showLoginDialog(callbacks, params);
			}
		});
	};

	var showLoginDialog = function(callbacks, params) {
		var loginDialog = pandora.dialog({
			wrapClass : "dialog-middle",
			width : '400px',
			title : "登录",
			content : $("#loginFrame").html()
		});
		$(".login_submit").click(
				function() {
					var flag = beforeLogin();
					if (flag) {
						var loginForm = $("#loginForm");
						loginForm.ajaxSubmit({
							success : function(data) {
								if (data.success) {
									loginDialog.close();
									$.getJSON(
											"/userspace/cashAccount/check",
											function(response) {
												if (response.success == true) {
													callbacks.fire(params);
												} else {
													$.dialog({
														ok : true,
														okValue : "确定",
														title : "提示框",
														content : response.errorText
													});
												}
											});
								} else {
									$("#msgSpan").text(data.errorText);
									refreshCheckCode('image');
								}
								;
							},
							error : function(XmlHttpRequest, textStatus,
									errorThrown) {
								alert("系统暂时忙，请稍后登录！");
							}
						});
					}
					;
				});
	};
</script>