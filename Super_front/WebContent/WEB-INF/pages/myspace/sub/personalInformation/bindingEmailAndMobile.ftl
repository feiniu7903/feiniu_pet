<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>绑定邮箱-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">绑定邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>绑定邮箱</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>绑定邮箱后，可以用邮箱地址来登录。<@s.if test='!"F".equals(user.isEmailChecked)'>绑定邮箱并验证成功可获得<span class="lv-c1">300</span>积分。</@s.if></p>
							<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证手机，输入邮箱地址</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
							    <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱绑定成功</li></ul>
							</div>
							<div class="edit-inbox">
								<form id="myForm" action="/myspace/userinfo/email_send.do" method="post">
									<p><label>您的手机号：</label><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /> &nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do">手机号已丢失或停用？</a></p>
									<input id="nowmobile" name="nowmobile" value="${user.mobileNumber}" type="hidden" />
									<p><label></label><a id="send-verifycode-old" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取验证码</i></a>
									<span id="JS_countdown-old" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
									<span class="tips-warn" style="display:none" id="span_tips"><span class="tips-ico03"></span>已超过每日发送上限，请于次日再试</span></p>
									<p><label>请输入短信校验码：</label><input type="text" length="6" id="sso_verifycode3" name="authenticationCode" value="" class="input-text i-checkcode" /></p>
									<p><label>新邮箱地址：</label><input type="text" disabled="true" name="email" id="sso_email" class="input-text input-email input-disabled" /></p>
									<p><a id="submitBtn" class="ui-btn ui-btn2 btn-disabled"><i>&nbsp;下一步&nbsp;</i></a></p>
								</form>						   
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script src='/js/myspace/mobileUtil.js' type='text/javascript'></script>
<script type="text/javascript">
	function sso_email_callback(call){
		if($.trim($("#sso_email").val()) == ""){
			error_tip('#sso_email','Email地址格式不正确，请重新输入');
		}else{
			$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?email=" + $.trim($("#sso_email").val()) + "&jsoncallback=?",function(json){
				if (json.success == true) {
					call();
				}else{
					error_tip("#sso_email","该Email地址已被使用，请更换其他邮箱");
				}
			});
		}
	}

	$('#send-verifycode-old').click(function(){
	 	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=<@s.property value="user.mobileNumber"/>&jsoncallback=?",function(json){
			if (json.success) {
				$("#span_tips").hide();
				$('#send-verifycode-old').hide();
				$("#JS_countdown-old").show();
				JS_countdown("#JS_countdown-old span.num-second");
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
			
		});
	 });
	 
	function sso_verifycode3_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$.trim($('#nowmobile').val())+"&authenticationCode=" + $.trim($("#sso_verifycode3").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#sso_email").removeAttr("disabled").removeClass("input-disabled");
				$("#submitBtn").addClass("ui-btn4").removeClass("ui-btn2 btn-disabled");
			}else{
				error_tip("#sso_verifycode3","手机校检码错误");
			}
		});
	}


	 function validate_pass(){
		$('#myForm').submit();
	}

</script>
	<script>
		cmCreatePageviewTag("绑定邮箱", "D1003", null, null);
	</script>
</body>
</html>