<%@page import="java.util.Date"%>
<%@page import="com.lvmama.comm.utils.DateUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>分销平台登录</title>
<link rel="shortcut icon" type="image/x-icon"
	href="http://www.lvmama.com/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script>
	function loginHandler() {
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
		$("#user").submit();
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
</script>
</head>
<body>
	<div style="display: none" id="filterPage"></div>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="main login">
		<div class="login_box">
			<h2>分销商平台登录</h2>
			<form:form id="user" name="user" action="/login" method="post">
				<input type="hidden" name="service" value="${service }" />
				<p>会员登录名</p>
				<label><input id="userName" type="text" name="userName"
					value="${user.userName}" class="login_input"
					onkeyup="enterHandler(event)"></label>
				<p>
					密码 <a href="/findpass/index">忘记密码?</a>
				</p>
				<label> <input id="loginPassword" type="password"
					name="loginPassword" value="${user.loginPassword}"
					class="login_input" onkeyup="enterHandler(event)"></label>
				<p>验证码</p>
				<label class="yzm"> <input class="textCodeClass textClass"
					id="imageCode" name="imageCode" type="text"> <img
					id="image" src="/tnt/code.png"> <a class="link_blue"
					onclick="refreshCheckCode('image');return false;" href="#">点击换张图</a>
				</label>
				<label class="login_btn"> <a id="loginBtn"
					class="btn cbtn-blue btn-big" href="#" target="_self"
					onclick="loginHandler()">立即登录</a> 还没有账号？点此 <a href="/reg/index"
					class="regLink">立即注册</a></label>
				<span style="color: red" id="msgSpan"> <c:if
						test="${loginError!=null }">
						${loginError}
					</c:if>
				</span>
			</form:form>
		</div>
	</div>
	<c:if test="${statusError!=null }">
		<script type="text/javascript">
			alert("${statusError}");
		</script>
	</c:if>
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>