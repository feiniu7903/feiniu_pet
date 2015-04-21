<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付密码绑定</title>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css,/styles/v4/modules/dialog.css,/styles/v5/modules/tip.css,/styles/mylvmama/ui-lvmama.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	$(".tureTipsBox").hide();
</script>
</head>

<body>
	<c:if test="${empty mobile }">
		<h3>支付密码绑定</h3>
		<form id="payPwdForm">
			<div class="rightBox">
				<div class="tiptext tip-warning">
					<span class="tip-icon tip-icon-warning"></span>亲爱的用户，通过预存款账户进行支付时，需要输入支付密码，支付密码验证通过后才可以进行支付，设置好支付密码后，请牢记！
				</div>
				<table class="mobile">
					<tr>
						<td class="phonenum">请输入支付密码：</td>
						<td><input class="textClass" id="payPassword"
							name="payPassword" type="password"> <span
							class="tiptext tip-line tipsxx"></span> <span
							class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum">请再次输入支付密码：</td>
						<td><input class="textClass" id="payPasswordTwo"
							name="payPasswordTwo" type="password"> <span
							class="tiptext tip-line tipsxx"></span> <span
							class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum"></td>
						<td><button type="button" class="btn cbtn-blue btn-middle"
								id="payPwdBtn">提 交</button></td>
					</tr>
				</table>
			</div>
		</form>
	</c:if>
	<script src="/js/jquery.validate.min.js/"></script>
	<script src="/js/jquery.validate.expand.js/"></script>
	<script src="/js/user/account_validate.js/"></script>
	<script type="text/javascript">
		$(function() {
			var payPwdForm = $("#payPwdForm");
			payPwdForm.validate(cashAccount);
			$("#payPwdBtn").bind("click", function() {
				if (!payPwdForm.validate().form()) {
					return;
				}
				formSubmit();
			});
		});

		var formSubmit = function() {
			$("#payPwdBtn").attr("disabled", true);
			$.ajax({
				url : "/userspace/cashAccount/savePassword.do",
				type : "post",
				dataType : "json",
				data : {
					password : $("#payPasswordTwo").val()
				},
				success : function(response) {
					query("/userspace/cashAccount/index.do",".main_r");
				},
				error : function() {
					$("#payPwdBtn").attr("disabled", false);
				}
			}); 
		};
	</script>
</body>
</html>