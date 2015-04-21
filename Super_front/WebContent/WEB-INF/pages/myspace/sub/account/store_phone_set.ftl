<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>绑定手机-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/account/bonusreturn.do">现金账户</a>
				&gt;
				<a class="current">绑定手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!--步骤1-->
				<div id="storePhoneSet" class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>绑定支付手机并设置支付密码</h3></div>
					<div class="ui-box-container">
						<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>请填写您真实的手机号码，用于接收现金账户余额变动提醒短信。</p>
							<div class="edit-inbox" id="default_bind_mobile">
							     <p><label id="mobile_hint">支付手机号：</label><b>
								     <input type="hidden" id="payment_mobile" value="<@s.property value="user.mobileNumber"/>"/>
								     <@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" />
								     <a href="javascript:change_mobile();" id="change_mobile_href">修改手机号</a>
								     <div class="ui-font-right"><a href="/myspace/userinfo/contactCustomService.do">原手机号已丢失或停用? </a></div>
							     </b></p>
							     <p><label></label>
							     	 <a id="send-verifycode-old" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取验证码</i></a>
						             <span id="JS_countdown-old" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>
						                                   校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href="">
						                                   <i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
						         </p>
								<p><label>请输入校验码：</label><input type="text" id="sso_verifycode3" value="" class="input-text i-checkcode" /></p>
								<p style="display:none;" class="change_moble_p"><label>请输入支付手机号：</label><input disabled="true" type="text" id="sso_mobile2" maxlength="13" value="<@s.property value="user.mobileNumber"/>" class="input-text input-phone" /></p>
								<p style="display:none;" class="change_moble_p"><label></label><a id="send-verifycode" class="ui-btn ui-btn2" href="javascript:;" ><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span></p>
								<p style="display:none;" class="change_moble_p"><label>请输入短信校验码：</label><input  disabled="true" type="text" id="sso_verifycode4" value="" class="input-text i-checkcode" /></p>
								<p><label>请输入支付密码：</label><input type="password" id="paymentPassword" name="paymentPassword" value="" class="input-text input-pwd" disabled="true"/><span id="paymentPasswordRight"></span><span id="paymentPasswordTip"></span></p>
				            	<p><label>请确认支付密码：</label><input type="password" id="confirmPassword" name="confirmPassword" value="" class="input-text input-pwd" disabled="true"/><span id="confirmPasswordRight"></span><span id="confirmPasswordTip"></span></p>
								<p><a class="ui-btn ui-btn2 btn-disabled" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
							</div>
						</div>
					</div>
				</div>
				<!--步骤1-->
				<!--步骤2-->
				<div id="storePhoneSetSuccess" class="ui-box-container" style="display:none">
					<div class="ui-box-title"><h3>绑定手机</h3></div>
						<p class="tips-info"><span class="tips-ico03"></span>您的现金账户余额有任何变动，通知信息将以短信的方式发送到您的手机。</p>
						<div class="msg-success">
							<h3>恭喜！手机绑定成功！</h3>
							<p class="mt10">您现在可以：<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
						</div>
					</div>
				</div>
				<!--步骤2-->
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	

<script src='/js/myspace/account.js' type='text/javascript'></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script src='/js/myspace/mobileUtil.js' type='text/javascript'></script>
<script type="text/javascript">
	var i = true;
	 $('#send-verifycode-old').click(function(){
	 	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + "<@s.property value="user.mobileNumber"/>".replace(/\s+/g,'') + "&jsoncallback=?",function(json){
			if (json.success) {
				$('#send-verifycode-old').hide();
				$("#JS_countdown-old").show();
				JS_countdown("#JS_countdown-old span.num-second");
			} else {
				alert('验证码发送失败，请重新尝试');
			}
			
		});
	 });
	function sso_mobile2_callback(call){
	    if(checkMobileNumber('#sso_mobile2')){
			call();	
			$("#send-verifycode").removeClass("ui-btn2 btn-disabled").addClass("ui-btn1");
			$("#sso_verifycode4").removeAttr("disabled");
		}
	}
	function sso_verifycode3_callback(call){
		call();
		if(i){
			$("#paymentPassword,#confirmPassword").removeAttr("disabled");
			
		}else{
			$("#sso_mobile2").removeAttr("disabled");
		}
	}
	function sso_verifycode4_callback(call){
		call();	
		$("input[disabled]").removeAttr("disabled");
		//$("#send-verifycode").removeClass("ui-btn2 btn-disabled").addClass("ui-btn1");
		$("#submitBtn").removeClass("ui-btn2 btn-disabled").addClass("ui-btn4");
	}
	$('#send-verifycode').click(function(){
		if($(this).hasClass("ui-btn2")){
			return;
		}
		if(!($("#sso_verifycode3").hasClass("i-checkcode input_border_red")) && checkMobileNumber('#sso_mobile2')){
	 		sendAuthenticationCode('#sso_mobile2');
		}
	 });
	
	function change_mobile(){
		 i = !i;
		 if(!i){
		 	$(".change_moble_p").show();
		 	$("#change_mobile_href").text("返回，不修改");
		 	$("#mobile_hint").text("你的手机号：");
		 }else{
		 	$(".change_moble_p").hide();
		 	$("#change_mobile_href").text("修改手机号");
		 	$("#mobile_hint").text("支付手机号：");
		 }
	}
	function validate_pass(){
		var postData = {
			validatePasswodCode2:$("#sso_verifycode3").val(),
			mobileNumber:$('#payment_mobile').val().replace(/\s+/g,''),
			paymentPassword:$("#paymentPassword").val(),
			confirmPassword:$("#confirmPassword").val(),
			isDefault:"true"
		};
		if(!i){
			postData = {
				validatePasswodCode2:$("#sso_verifycode3").val(),
				validatePasswodCode:$("#sso_verifycode4").val(),
				mobileNumber:$('#sso_mobile2').val().replace(/\s+/g,''),
				paymentPassword:$("#paymentPassword").val(),
				confirmPassword:$("#confirmPassword").val(),
				isDefault:"false"
			};
		}
		validateAuthenticationCode('#sso_verifycode3','#storePhoneSet','/myspace/account/store_phone_success.do',postData);
	}
</script>
</body>
</html>