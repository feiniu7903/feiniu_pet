<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人注册页面-驴妈妈分销平台</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script
	src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"></script>
</head>

<body>
	<div style="display: none" id="filterPage"></div>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>

	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form id="userForm" name="userForm" modelAttribute="userForm"
		action="/reg/userAuditSave.do" method="post">
		<div class="login_form">
			<div class="flowstep step02"></div>
			<div class="tiptext tip-warning" style="height: auto;">
				*请谨慎修改资料信息，资料提供越多，审核通过率越大。<br /> <span
					class="tip-icon tip-icon-warning" style="margin: 0 5px 0 18px"></span>${userDetail.failReason
				}<br />
			</div>
			<div class="nRBox">
				<h3>个人资料和联系方式</h3>
				<ul class="formBox">
					<li><label class="titleBox"><em>*</em>您的真实姓名：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="realName" name="realName"
								value="${userForm.realName }" type="text"> <span
								class="tiptext tip-line tipsxx">请输入不超过40个字符的真实姓名</span> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>性别：</label> <label
						class="sexBox"> <input type="radio" checked="checked"
							name="gender"
							<c:if test="${userForm.gender=='Y' }">checked</c:if> value="Y">先生
					</label> <label class="sexBox"> <input type="radio" name="gender"
							value="M" <c:if test="${userForm.gender=='M' }">checked</c:if>>女士
					</label></li>
					<li><label class="titleBox">您的职务：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="duties" name="duties"
								value="${userForm.duties }" type="text">
						</div></li>
					<li><label class="titleBox">所在部门：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="department" name="department"
								value="${userForm.department }" type="text">
						</div></li>
					<li><label class="titleBox">常用固定电话：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="phoneNumber" name="phoneNumber"
								value="${userForm.phoneNumber }" type="text">
						</div></li>
					<li><label class="titleBox">传真：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="faxNumber" name="faxNumber"
								value="${userForm.faxNumber }" type="text">
						</div></li>
					<li><label class="titleBox"><em>*</em>手机：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="mobileNumber" name="mobileNumber"
								value="${userForm.mobileNumber }" type="text"> <span
								class="tiptext tip-line tipsxx">手机是您找回用户名和密码的主要途径，请务必正确填写，否则将无法通过审核。</span>
							<span class="tiptext tip-line errorTipsBox"> </span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>常用通迅地址：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="address" name="address"
								value="${userForm.address }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>邮编：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="zipCode" name="zipCode"
								value="${userForm.zipCode }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox"><em>*</em>电子邮件：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="email" name="email"
								value="${userForm.email }" type="text"> <span
								class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
					<li><label class="titleBox">网址：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="netUrl" name="netUrl"
								value="${userForm.netUrl }" type="text">
						</div></li>
					<li><label class="titleBox">QQ号：</label>
						<div class="form-inline form-small">
							<input class="textClass" id="qqAccount" name="qqAccount"
								value="${userForm.qqAccount }" type="text">
						</div></li>
					<li><label class="titleBox"><em>*</em>验证码：</label>
						<div class="form-inline form-small">
							<input class="textCodeClass textClass" id="imageCode"
								name="imageCode" type="text"> <img id="image"
								src="/tnt/code.png"> <a class="link_blue"
								onclick="refreshCheckCode('image');return false;" href="#">换一张</a>
							<span class="tiptext tip-line errorTipsBox"></span> <span
								class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
							</span>
						</div></li>
				</ul>
			</div>

			<div class="tjbkBox">
				<a class="btn cbtn-blue btn-big" target="_self"
					href="javascript:void(0)" id="regSubmit">提交用户信息</a>
			</div>
		</div>
		<input name="isCompany" value="false" type="hidden" />
	</form:form>
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
	<script src="${basePath}/js/jquery.form.js"></script>
	<script src="${basePath}/js/jquery.validate.min.js"></script>
	<script src="${basePath}/js/user/user_validate.js"></script>
	<script type="text/javascript">
		$(function() {
			var userForm = $("#userForm");
			userForm.validate(personUpdate);
			$("#regSubmit").bind("click", function() {
				if (!userForm.validate().form()) {
					return;
				}
				$("#userForm").submit();
			});
		});

		function refreshCheckCode(s) {
			var elt = document.getElementById(s);
			elt.src = elt.src + "?_=" + new Date().getTime();
		}
	</script>
</body>
</html>