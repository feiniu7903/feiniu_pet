<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改密码</title>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css,/styles/v4/modules/dialog.css,/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link href="http://s2.lvjs.com.cn/styles/mylvmama/ui-lvmama.css"
	rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	$(".tureTipsBox").hide();
</script>
</head>
<body>
	<div id="tab">
		<div class="nav">
			<strong>修改密码</strong>
			<ul>
				<li class=${ispay?"":"active"}><a
					href="javascript:query('/user/updatePasswordPage.do?ispay=false','.main_r');">登录密码修改</a></li>
				<li class=${ispay?"active":""}><a
					href="javascript:query('/user/updatePasswordPage.do?ispay=true','.main_r');">支付密码修改</a></li>
			</ul>
		</div>
		<div class="content">
			<form action="/user/savePassword.do" id="loginPwdForm" method="POST">
				<div class="box" style=${ispay?"display:none":"display:block"}>
					<div class="msg-success" style="display: none">
						<span class="msg-ico01"></span>
						<h3 style="border: none; background: none; padding-left: 10px;">恭喜！登录密码修改成功！</h3>
					</div>
					<ul class="change_password" id="change_login_password">
						<li><label class="titleBox"><em>*</em>当前登录密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="oldLoginPass" name="oldLoginPass"
									type="password"> <span class="tiptext tip-line tipsxx"></span>
								<span class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>
							</div></li>
						<li><label class="titleBox"><em>*</em>新登录密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="loginPassword" name="loginPassword"
									type="password"> <span class="tiptext tip-line tipsxx"></span>
								<span class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>

							</div></li>
						<li><label class="titleBox"><em>*</em>确认新登录密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="loginPasswordTwo"
									name="loginPasswordTwo" type="password"> <span
									class="tiptext tip-line tipsxx"></span> <span
									class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>
							</div></li>
						<li><button type="button" class="btn cbtn-blue btn-middle"
								id="loginPwdBtn">提 交</button></li>
					</ul>
				</div>
			</form>
			<form id="payPwdForm">
				<div class="box" style=${ispay?"display:block":"display:none"}>
					<ul class="change_password">
						<li><label class="titleBox"><em>*</em>当前支付密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="oldPayPass" name="oldPayPass"
									type="password"> <span class="tiptext tip-line tipsxx"></span>
								<span class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>
							</div></li>
						<li><label class="titleBox"><em>*</em>新支付密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="payPassword" name="payPassword"
									type="password"> <span class="tiptext tip-line tipsxx"></span>
								<span class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>
							</div></li>
						<li><label class="titleBox"><em>*</em>确认新支付密码：</label>
							<div class="form-inline form-small">
								<input class="textClass" id="payPasswordTwo"
									name="payPasswordTwo" type="password"> <span
									class="tiptext tip-line tipsxx"></span> <span
									class="tiptext tip-line errorTipsBox"> </span> <span
									class="tiptext tip-line tureTipsBox"> <span
									class="tip-icon tip-icon-success"></span>
								</span>
							</div></li>
						<li><c:if test="${canShowpay!=false}">
								<button type="button" class="btn cbtn-blue btn-middle"
									id="payPwdBtn">提 交</button>
							</c:if> <c:if test="${canShowpay==false}"> 请先绑定手机号再做修改: <a
									href="#" onclick='query("/userspace/cashAccount/index.do");'>绑定手机</a>
							</c:if></li>
					</ul>
				</div>

			</form>
		</div>
	</div>
</body>
<script src="/js/jquery.validate.min.js/"></script>
<script src="/js/jquery.validate.expand.js/"></script>
<script src="/js/user/account_validate.js/"></script>
<script type="text/javascript">
	$(function() {
		var payPwdForm = $("#payPwdForm");
		payPwdForm.validate(cashAccount);
		$("#payPwdBtn").bind(
				"click",
				function() {
					if (!payPwdForm.validate().form()) {
						return;
					}
					formSubmit($("#payPwdBtn"),
							"/userspace/cashAccount/savePassword.do", $(
									"#payPasswordTwo").val());
				});

		var loginPwdForm = $("#loginPwdForm");
		loginPwdForm.validate(cashAccount);
		$("#loginPwdBtn").bind("click", function() {
			if (!loginPwdForm.validate().form()) {
				return;
			}
			$("#loginPwdBtn").attr("disabled", true);
			loginPwdForm.ajaxSubmit({
				success : function(data) {
					$(".msg-success").show();
					$("#change_login_password").hide();
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					alert("系统暂时忙，请稍后再修改密码！");
				}
			});
		});

		var formSubmit = function(button, url, password) {
			button.attr("disabled", true);
			$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				data : {
					password : password
				},
				success : function(response) {
					query("/userspace/cashAccount/index.do",".main_r");
				},
				error : function() {
					button.attr("disabled", false);
				}
			});
		};
	});

	//选项卡
	function a() {
		var oTab = document.getElementById('tab');
		var aLi = getByClass(oTab, 'nav')[0].getElementsByTagName('li');
		var aA = oTab.getElementsByTagName('ul')[0].getElementsByTagName('a');
		var aDiv = getByClass(oTab, 'box');
		var i = 0;
		for (i = 0; i < aLi.length; i++) {
			aLi[i].index = i;
			aLi[i].onclick = function() {
				for (i = 0; i < aLi.length; i++) {
					aLi[i].className = '';
					aDiv[i].style.display = 'none';
				}
				this.className = 'active';
				aDiv[this.index].style.display = 'block';
			};
			aA[i].onfocus = function() {
				this.blur();
			};
		}
	};
	function getByClass(oParent, sClassName) {
		var aElm = oParent.getElementsByTagName('*');
		var aArr = [];
		for ( var i = 0; i < aElm.length; i++) {
			if (aElm[i].className == sClassName) {
				aArr.push(aElm[i]);
			}
		}
		return aArr;
	}
	//a();
</script>
</html>