<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>找回密码-驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/login/delv_css.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link href="http://pic.lvmama.com/styles/mylvmama/ui-lvmama.css" rel="stylesheet" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>

<#include "/common/coremetricsHead.ftl">
</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">重置密码</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="zhfs_center">
            <#if email!=null>
            	<ul class="zhfs_step">
					<li><i>3</i><label class="zhfs_step_text">新密码设置成功</label></li>
					<li class="curr"><i>2</i><label class="zhfs_step_text">设置新密码</label><em class="zhfs_jiao"></em></li>
					<li><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em class="zhfs_jiao"></em></li>
				</ul>
			        <form action="/nsso/findpass/sendResetEmailSucc.do" method="post" id="submitForm">
				<div class="zhfs_t_left">邮箱找回密码</div>
				<ul class="zhfs_form yxzh_form">
					<li><label class="csmm_form_col">Email</label><input type="text" class="zhfs_form_input" id="sso_email_c" name="txtUsername" value="<#if email!='Y'>${email}</#if>"/></li>
					<li>
						<label class="csmm_form_col">验证码</label><input type="text" class="zhfs_form_input zhfs_w99" id="sso_verifycode1"/>
						<img id="image_code" src="/nsso/account/checkcode.htm" /><a href="#" class="link_blue" onClick="refreshCheckCode('image_code');return false;">换一张</a>
					</li>
					<li><label class="csmm_form_col"></label><a href="javascript:void(0)" class="csmm_sj_submit" id="submitBtn"></a></li>
				</ul>
				 <@s.token />
				</form>
               </#if>
               <#if mobile!=null>
               <ul class="zhfs_step">
					<li><i>4</i><label class="zhfs_step_text">新密码设置成功</label></li>
					<li <#if validated??>class="curr"</#if>><i>3</i><label class="zhfs_step_text">设置新密码</label><em class="zhfs_jiao"></em></li>
					<li <#if !validated??>class="curr"</#if>><i>2</i><label class="zhfs_step_text">输入手机号码</label><em class="zhfs_jiao"></em></li>
					<li><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em class="zhfs_jiao"></em></li>
				</ul>
               	<#if !validated??>
               		<form action="/nsso/findpass/validateMobile.do" method="post" id="submitForm">
						<div class="zhfs_t_left">手机找回密码</div>
						<ul class="zhfs_form">
							<li><label class="zhfs_form_col w_sjzh">手机号</label><input type="text" class="zhfs_form_input" id="sso_mobile2"  name="mobile"/></li>
							<li>
								<label class="zhfs_form_col w_sjzh">验证码</label><input type="text" class="zhfs_form_input zhfs_w99" id="sso_verifycode1" name="validateCode"/>
								<img id="image_code"  src="/nsso/account/checkcode.htm" /><a href="#" class="link_blue" onClick="refreshCheckCode('image_code');return false;">换一张</a>
							</li>
							<li><label class="zhfs_form_col"></label><a href="javascript:void(0)" class="zhfs_sj_submit" id="submitBtn" style="margin-left:25px"></a></li>
						</ul>
					</form>
               	<#else>
	               	<form action="/nsso/findpass/saveNewPass.do" method="post" id="submitForm">
						<div class="zhfs_t_left">手机找回密码</div>
						<ul class="zhfs_form">
							<li><label class="zhfs_form_col w_sjzh">手机号:</label><input type="text" class="zhfs_form_input" id="find_mobile"  value="${mobile}" disabled="disabled" />
							<input type="hidden" value="${mobile}" name="mobile"/>
							</li>
							<li>
								<span class="zhfs_info_s w_sjzh_xym" style="margin-bottom:0px;" id="sendCodeBtn">发送校验码</span>
								<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span>
								<div id="yzm_send_ok" style="display:none">
									<span class="zhfs_success_s w_sjzh_xym"><i></i>校验码已经发送成功，请查看手机</span>
									<span class="zhfs_info_send">60秒内没有收到短信？<a href="javascript:void(0)" class="zhfs_send_again" id="zhfs_send_again">(<label></label>)秒后再次发送</a></span>
								</div>
							</li>
							<li><label class="zhfs_form_col w_sjzh">校验码</label><input type="text" class="zhfs_form_input w_xym" name="authenticationCode" id="sso_verifycode2" /></li>
							<li><label class="zhfs_form_col w_sjzh">设置新密码</label><input type="password" class="zhfs_form_input" name="password" id="sso_password"/></li>
							<li><label class="zhfs_form_col w_sjzh">确认新密码</label><input type="password" class="zhfs_form_input" name="password2" id="sso_againPassword"/></li>
							<li><label class="zhfs_form_col"></label><a href="javascript:void(0)" class="zhfs_sj_submit" id="submitBtn" style="margin-left:25px"></a></li>
						</ul>
						 <@s.token />
						</form>
               	</#if>
               </#if>
		      
		</div>
	</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->

<script type='text/javascript'> 
	function sso_verifycode1_callback(call){
			$.ajax({
				url:"/nsso/ajax/findpass/validateVerifycode.do",
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
    <#if mobile!=null>
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
				url:"/nsso/ajax/findpass/sendMessage.do",
				type:"post",
				dataType:"json",
				data:{
					mobileOrEMail:$.trim($("#find_mobile").val())
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
		<!--“发送校验码”按钮事件-->
		$("#sendCodeBtn").click(function () {  
			var mobile = document.getElementById("find_mobile");
			if(!$(mobile).hasClass("input_border_red")){
				$.ajax({
					url:"/nsso/ajax/checkUniqueField.do",
					type:"post",
					dataType:"json",
					data:{
						mobile: $.trim($(mobile).val())
					},
					success:function (response){
						if (response.success == true) {
							error_tip("#find_mobile","该手机号尚未注册，请重新输入或<a href='/nsso/register/registering.do'>立即注册</a>");
						} else {
							sendMessage();
						}
					}
				});
			}                 
		});
		function sso_mobile2_callback(call){
			$.ajax({
					url:"/nsso/ajax/checkUniqueField.do",
					type:"post",
					dataType:"json",
					data:{
						mobile:$.trim($("#sso_mobile2").val())
					},
					success:function (response){
						if (response.success == true) {
							error_tip("#sso_mobile2","该手机号尚未注册，请重新输入或<a href='/nsso/register/registering.do'>立即注册</a>");
						} else {
							call();
						}
					}
				});
		}
		function sso_verifycode2_callback(call){
			$.ajax({
				url:"/nsso/ajax/validateAuthenticationCode.do",
				type:"post",
				dataType:"json",
				data:{
					mobile:$.trim($("#find_mobile").val()),
					authenticationCode : $("#sso_verifycode2").val()
				},
				success:function (response) {
					if (response.success == true) {
						call()
					} else {
						error_tip("#sso_verifycode2",response.errorText);
					}
				}
			}); 
		}
		<!--“提交”按钮事件-->   
		function validate_pass(){
			$("#submitForm").submit();
		}
    <#elseif email!=null>
		function sso_email_c_callback(call){
			$.ajax({
				type: "POST",
				async: false,
				url: "/nsso/ajax/checkUniqueField.do",
				data: {
					email: $.trim($("#sso_email_c").val())
				},
				dataType: "json",
				success: function(response) {
					if (response.success == true) {
						error_tip("#sso_email_c","该邮箱尚未注册，请重新输入或<a href='/nsso/register/registering.do'>立即注册</a>");
					} else {
						call();
					}
				}
			});
		}
		function validate_pass(){
			$.ajax({
				url:"/nsso/ajax/findpass/sendMessage.do",
				type:"post",
				dataType:"json",
				data:{
					mobileOrEMail:$.trim($('#sso_email_c').val()),
					validateCode:$("#sso_verifycode1").val()
				},
				success:function (response) {
					if (response.success == true) {
						window.location.href="/nsso/findpass/sendResetEmailSucc.do?email=" + $('#sso_email_c').val();
					} else {
						error_tip("#sso_verifycode1",response.errorText,":last");
					}
				}
			}); 
		}
    </#if>
</script> 

<script>
	if("<@s.property value="email" />"!=null){
		cmCreatePageviewTag("邮箱找回密码", "F0001", null, null);
	}else if("<@s.property value="mobile" />"!=null){
		cmCreatePageviewTag("手机找回密码", "F0001", null, null);
	}else if("<@s.property value="mobile" />"!=null&&"<@s.property value="validated" />"!=null){
		cmCreatePageviewTag("手机找回密码-输入校验码", "F0001", null, null);
	}
      
</script>
</body> 
</html>
