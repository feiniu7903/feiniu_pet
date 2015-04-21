<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>找回密码-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/styles/login/delv_css.css"
	rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css"
	rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet"
	href="http://pic.lvmama.com/min/index.php?g=commonCss" />
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css" />
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
	
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript"
	src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<style type="text/css">
.login_main{width:1000px;}
</style>
</head>
<body>
	<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top top_wrap">
			<img  class="fx_phone" src="http://pic.lvmama.com/img/fx/fx_phone.png" />
			<span class="login_logo fx_logo"> <a href="http://f.lvmama.com"><img
					src="http://pic.lvmama.com/img/fx/fx_logo.png"></a> <label
				class="text">|</label> <a class="text">重置密码</a>
			</span>
		</div>
		<div class="zhfs_center">
			<ul class="zhfs_step">
				<li><i>3</i><label class="zhfs_step_text">设置新密码</label></li>
				<li class="curr"><i>2</i><label class="zhfs_step_text">输入注册账号</label><em
					class="zhfs_jiao"></em></li>
				<li><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em
					class="zhfs_jiao"></em></li>
			</ul>
			<c:if test="${email!=null }">
				<form action="/findpass/sendResetEmailSucc" method="post"
					id="submitForm">
					<div class="zhfs_t_left">邮箱找回密码</div>
					<ul class="zhfs_form yxzh_form">
						<li><label class="csmm_form_col">Email</label><input
							type="text" class="zhfs_form_input" id="sso_email_c"
							name="txtUsername"
							<c:if test="${email!=null }"> value="${email}"</c:if> /></li>
						<li><label class="csmm_form_col">验证码</label> <input
							type="text" class="zhfs_form_input zhfs_w99" id="sso_verifycode1" />
							<img id="image" src="/tnt/code.png"> <a class="link_blue"
							onclick="refreshCheckCode('image');return false;" href="#">换一张</a>
						<li><label class="csmm_form_col"></label><a
							href="javascript:void(0)" class="csmm_sj_submit" id="submitBtn"></a></li>
					</ul>

				</form>
			</c:if>
			<c:if test="${mobile!=null }">
				<form action="/findpass/saveNewPass" method="post"
					id="submitForm">
					<div class="zhfs_t_left">手机找回密码</div>
					<ul class="zhfs_form">
						<li><label class="zhfs_form_col w_sjzh">手机号</label><input
							type="text" class="zhfs_form_input" id="sso_mobile2"
							name="mobile" /></li>
						<li><span class="zhfs_info_s w_sjzh_xym" id="sendCodeBtn">发送校验码</span>
							<span class="tips-warn" style="display: none" id="span_tips"><span
								class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span>
							<div id="yzm_send_ok" style="display: none">
								<span class="zhfs_success_s w_sjzh_xym"><i></i>校验码已经发送成功，请查看手机</span>
								<span class="zhfs_info_send">60秒内没有收到短信？<a
									href="javascript:void(0)" class="zhfs_send_again"
									id="zhfs_send_again">(<label></label>)秒后再次发送
								</a></span>
							</div></li>
						<li><label class="zhfs_form_col w_sjzh">校验码</label><input
							type="text" class="zhfs_form_input w_xym"
							name="authenticationCode" id="sso_verifycode2" /></li>
						<li><label class="zhfs_form_col w_sjzh">设置新密码</label><input
							type="password" class="zhfs_form_input" name="password"
							id="sso_password" /></li>
						<li><label class="zhfs_form_col w_sjzh">确认新密码</label><input
							type="password" class="zhfs_form_input" name="password2"
							id="sso_againPassword" /></li>
						<li><label class="zhfs_form_col"></label><a
							href="javascript:void(0)" class="zhfs_sj_submit" id="submitBtn"
							style="margin-left: 25px"></a></li>
					</ul>

				</form>
			</c:if>

		</div>
	</div>
	<!-- 尾部 -->
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>

	<!-- 尾部 -->

</body>
	<c:if test="${mobile!=null }">
	<script type="text/javascript"> 
		function yzm_send_ok() {
			var _this = $("#sendCodeBtn");
			_this.hide();
			var loop = function (label, time, elt) {
				label.html(time);
				if (time <= 0) {
					$("#yzm_send_ok").hide();
					$("#sendCodeBtn").html("\u91CD\u65B0\u53D1\u9001\u6821\u9A8C\u7801").show();
					return;
				}
				time--;
				setTimeout(function () {
					loop(label, time, elt);
				}, 1000);
			};
			var label = $("#yzm_send_ok").show().find("label");
			loop(label, 60, _this);
		}
		function sendMessage() {
			$.ajax({
				url:"/ajax/findpass/sendMessage",
				type:"post",
				dataType:"json",
				data:{
					mobile:$("#sso_mobile2").val(),
					email:$("sso_email_c").val()
				},
				success:function (json) {
					if (json.success == true) {
						$("#span_tips").hide();
						yzm_send_ok();
					} else {
						if(json.errorText == 'phoneWarning'){
							$("#span_tips").html("已超过每日发送上限，请于次日再试");
							$("#span_tips").show();
							$("#send-verifycode").unbind();  
						}else if(json.errorText == 'ipLimit'){
							$("#span_tips").html("当前IP发送频率过快，请稍后重试");
							$("#span_tips").show();
						}else if(json.errorText == 'waiting'){
							$("#span_tips").html("发送频率过快，请稍后重试");
							$("#span_tips").show();
						}else{
							$("#span_tips").html(json.errorText);
							$("#span_tips").show();
						}
					}
				}
			});
		}
		/*发送校验码 按钮事件 */
		$("#sendCodeBtn").click(function () {  
			var mobile = document.getElementById("sso_mobile2");
			$(mobile).change();
			if(!$(mobile).hasClass("input_border_red")){
				$.ajax({
					url:"/ajax/checkUniqueField",
					type:"post",
					dataType:"json",
					data:{
						mobile:$("#sso_mobile2").val()
					},
					success:function (response){
						if (response.success == true) {
							error_tip("#sso_mobile2","该手机号尚未注册，请重新输入或<a href='/reg/index'>立即注册</a>");
						} else {
							sendMessage();
						}
					}
				});
			}                 
		});
		 function sso_verifycode2_callback(call){
			$.ajax({
				url:"/ajax/validateAuthenticationCode",
				type:"post",
				dataType:"json",
				data:{
					mobile:$("#sso_mobile2").val(),
					authenticationCode : $("#sso_verifycode2").val()
				},
				success:function (response) {
					if (response.success == true) {
						call();
					} else {
						error_tip("#sso_verifycode2",response.errorText);
					}
				}
			}); 
		} 
		/* 提交按钮事件 */   
		function validate_pass(){
			$("#submitForm").submit();
		}
		</script>
		</c:if>
    	<c:if test="${email!=null }">
    		<script type="text/javascript"> 
		function sso_email_c_callback(call){
			$.ajax({
				type: "POST",
				async: false,
				url: "/ajax/checkUniqueField",
				data: {
					email: $("#sso_email_c").val()
				},
				dataType: "json",
				success: function(response) {
					if (response.success == true) {
						error_tip("#sso_email_c","该邮箱尚未注册，请重新输入或<a href='/reg/index'>立即注册</a>");
					} else {
						call();
					}
				}
			});
		}
		
		//验证校验码
		function sso_verifycode1_callback(call){
			$.ajax({
				url:"/ajax/findpass/validateVerifycode",
				type:"post",
				dataType:"json",
				data:{
					validateCode:$("#sso_verifycode1").val()
				},
				success:function (response) {
					if (response.success == true) {
						call();
					} else {
						error_tip("#sso_verifycode1",response.errorText,":last");
					}
				}
			}); 
		} 
		function validate_pass(){
			$.ajax({
				url:"/ajax/findpass/sendMessage",
				type:"post",
				dataType:"json",
				data:{
					email:$('#sso_email_c').val(),
					validateCode:$("#sso_verifycode1").val()
				},
				success:function (response) {
					if (response.success == true) {
						window.location.href="/findpass/sendResetEmailSucc?email=" + $('#sso_email_c').val();
					} else {
						error_tip("#sso_verifycode1",response.errorText,":last");
					}
				}
			}); 
		}
		</script>
		</c:if>


</html>