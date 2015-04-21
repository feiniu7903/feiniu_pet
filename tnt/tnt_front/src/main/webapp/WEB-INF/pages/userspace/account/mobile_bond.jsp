<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付手机绑定</title>
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
	<form id="mobileForm">
		<c:if test="${empty mobile }">
			<input type="hidden" class="input-text" name="isFirst" id="isFirst"
				value="true" />
			<h3>支付手机绑定</h3>
			<div class="rightBox">
				<div class="tiptext tip-warning">
					<span class="tip-icon tip-icon-warning"></span>设置支付手机绑定后，通过预存款账户进行支付成功后，会向支付绑定手机发送通过预存款账户支付的信息。
				</div>
				<table class="mobile">
					<tr>
						<td class="phonenum">您的手机号：</td>
						<td><input type="text" class="input-text" id="pay_mobile1"
							name="mobile" /> <span class="tiptext tip-line errorTipsBox">
						</span> <span class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum">请再次输入手机号：</td>
						<td><input type="text" class="input-text" id="pay_mobile2"
							name="pay_mobile2" /> <span
							class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum"></td>
						<td>
							<p>
								<a class="send-verifycode btn btn-small" id="sendCodeBtn">免费获取校验码</a>
								<span class="JS_countdown hide" id="sendCodetips"> <span
									class="tiptext tip-success tip-line"> <span
										class="tip-icon tip-icon-success"></span> 校验码已发送成功，请查看手机
								</span> <span class="tiptext tip-default tip-line"> 60秒内没有收到短信?
										<a href="javascript:;" class="btn btn-small disabled">(<span
											class="J_num">60</span>)秒后再次发送
									</a>
								</span>
								</span>
							</p>
						</td>
					</tr>
					<tr>
						<td class="phonenum">短信校验码：</td>
						<td><input type="text" class="input-text" id="verifycode"
							name="authenticationCode" /><span
							class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum"></td>
						<td><button type="button" class="btn cbtn-blue"
								id="submitBtn">确认</button></td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${not empty mobile }">
			<input type="hidden" class="input-text" id="isFirst" value="false" />
			<h3>支付手机绑定</h3>
			<div class="rightBox">
				<div class="tiptext tip-warning">
					<span class="tip-icon tip-icon-warning"></span>设置支付手机绑定后，通过预存款账户进行支付成功后，会向支付绑定手机发送通过预存款账户支付的信息。
				</div>
				<table class="mobile">
					<tr>
						<td class="phonenum">您的手机号：</td>
						<td class="mobile_num"><b>${mobile }</b><a href="#"
							id="pop010">原手机号已丢失或停用？</a> <input type="hidden" id="oldMobile"
							value="${mobile }"></td>
					</tr>
					<tr>
						<td class="phonenum"></td>
						<td>
							<p>
								<a class="send-verifycode btn btn-small" id="sendCodeBtn2">免费获取校验码</a>
								<span class="JS_countdown hide" id="sendCodetips"> <span
									class="tiptext tip-success tip-line"> <span
										class="tip-icon tip-icon-success"></span> 校验码已发送成功，请查看手机
								</span> <span class="tiptext tip-default tip-line"> 60秒内没有收到短信?
										<a href="javascript:;" class="btn btn-small disabled">(<span
											class="J_num">60</span>)秒后再次发送
									</a>
								</span>
								</span>
							</p>
						</td>
					</tr>
					<tr>
						<td class="phonenum">短信校验码：</td>
						<td><input type="text" class="input-text" id="verifycode"
							name="authenticationCode" /><span
							class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum">您的手机号：</td>
						<td><input type="text" class="input-text" id="pay_mobile1"
							name="mobile" /> <span class="tiptext tip-line errorTipsBox">
						</span> <span class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum">请再次输入手机号：</td>
						<td><input type="text" class="input-text" id="pay_mobile2"
							name="pay_mobile2" /><span class="tiptext tip-line errorTipsBox">
						</span> <span class="tiptext tip-line tureTipsBox"> <span
								class="tip-icon tip-icon-success"></span>
						</span></td>
					</tr>
					<tr>
						<td class="phonenum"></td>
						<td><button type="button" class="btn cbtn-blue"
								id="submitBtn">确认</button></td>
					</tr>
				</table>
			</div>
		</c:if>
	</form>
	<div id="demo03" style="display: none">
		<h3>联系客服</h3>
		<div class="rightBox">
			<div class="tiptext tip-warning">
				<span class="tip-icon tip-icon-warning"></span>亲爱的用户，如果您无法进行账户的相关操作操作，请致电客服400-6040-616。为了您的账户安全，我们必须验证您的身份，请先准备
				好以下资料，再致电客服。
			</div>
			<span class="mobile_tit">建议提供如下资料：</span>
			<div class="mobile_rule">
				<p>1.提供最近一笔订单的出游人信息（姓名，手机号）</p>
				<p>2.提供最近一次银行转账的银行账号、银行转账流水号 （提供截图）</p>
				<p>3.提供原绑定手机号</p>
			</div>
		</div>
	</div>
	<script src="/js/jquery.validate.min.js/"></script>
	<script src="/js/jquery.validate.expand.js/"></script>
	<script src="/js/user/account_validate.js/"></script>
	<script type="text/javascript">
		$(function() {
			var mobileForm = $("#mobileForm");
			mobileForm.validate(cashAccount);
			$("#submitBtn").bind("click", function() {
				if (!mobileForm.validate().form()) {
					return;
				}
				formSubmit();
			});
		});
		/*获取验证码*/
		function sendAuthenticationCode(id) {
			var btn = $(".send-verifycode");
			var count = $("#sendCodetips");
			btn.hide();
			count.show();
			JS_countdown(count.find("span.J_num"), count, btn);
			$.ajax({
				url : "/ajax/sendAuthenticationCode.do",
				type : "post",
				dataType : "json",
				data : {
					mobile : $(id).val()
				},
				success : function(json) {
					if (json.success == true) {

					} else {
						if (json.errorText == 'phoneWarning') {
							$("#sendCodetips").html("已超过每日发送上限，请于次日再试");
							$("#sendCodetips").show();
						} else if (json.errorText == 'ipLimit') {
							$("#sendCodetips").html("当前IP发送频率过快，请稍后重试");
							$("#sendCodetips").show();
						} else if (json.errorText == 'waiting') {
							$("#sendCodetips").html("发送频率过快，请稍后重试");
							$("#sendCodetips").show();
						} else {
							$("#sendCodetips").html(json.errorText);
							$("#sendCodetips").show();
						}
					}
				}
			});
		}

		function jump(count, formId) {
			window.setTimeout(function() {
				count--;
				if (count > 0) {
					$('#jumpNum').html(count);
					jump(count, str);
				} else {
					$(str).submit();
				}
			}, 1000);
		}

		/*绑定手机号 */
		function formSubmit() {
			$("#submitBtn").attr("disabled", true);
			$.ajax({
				url : "/userspace/cashAccount/savePayMobile.do",
				type : "post",
				dataType : "json",
				data : {
					mobile : $("#pay_mobile2").val(),
					isFirst : $("#isFirst").val()
				},
				success : function(response) {
					query("/userspace/cashAccount/index.do",".main_r");
				},
				error : function() {
					$("#submitBtn").attr("disabled", false);
				}
			});
		}

		$("#pop010").click(function() {
			$.dialog({
				title : "联系客服",
				content : $("#demo03").html(),
				height : "330",
				width : "550"
			});
		});

		/*发送校验码 按钮事件 */
		$("#sendCodeBtn").click(function() {
			if (!$("#mobileForm").validate().element($("#pay_mobile1"))) {
				return;
			}
			sendAuthenticationCode("#pay_mobile2");
		});

		$("#sendCodeBtn2").click(function() {
			sendAuthenticationCode("#oldMobile");
		});
	</script>
</body>
</html>