<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改手机-驴妈妈旅游网</title>
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
				<div id="storePhoneChange" class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>绑定手机</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>修改手机号后，原来的手机将不再接收现金账户余额变动提醒短息。</p>

							<form id="myForm" action="/myspace/account/store_phone_change_success.do" method="post">
								<div class="edit-inbox">
									<p><label>您的手机号：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(moneyAccount.mobileNumber)" /></span>
									<div class="ui-font-right"><a target="_blank" href="/myspace/userinfo/contactCustomService.do">原手机号已丢失或停用? </a></div>
									</p>
						                        <input type="hidden" id="old_mobile" name="old_mobile"  value="${moneyAccount.mobileNumber}" />
						                        <p><label></label><a id="send-verifycode-old" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取验证码</i></a>
						                        <span id="JS_countdown-old" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span>
						                        </p>
						                        <p><label>请输入短信校验码：</label><input id="sso_verifycode3" name="authOldMobileCode" value="" class="input-text i-checkcode" type="text"></p>
									<p><label>请输入新的手机号：</label><input disabled="true" type="text" id="sso_mobile" name="mobile" maxlength="13" value="<@s.property value="user.mobileNumber"/>" class="input-text input-phone" /></p>
									<p><label></label><a id="send-verifycode" class="ui-btn ui-btn2 btn-disabled" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span></p>
									<p><label>请输入短信校验码：</label><input disabled="true"  type="text" id="sso_verifycode4" name="authenticationCode" value="" class="input-text i-checkcode" /></p>
									<p><a class="ui-btn ui-btn2 btn-disabled" id="submitBtn" ><i>&nbsp;确 定&nbsp;</i></a></p>
									<#--
									<p><label>请输入您的手机号：</label><input disabled="true"  type="text" id="sso_mobile2" name="mobile" value="<@s.property value="moneyAccount.mobileNumber" />" class="input-text input-phone" /></p>
									<p><label></label><a id="send-verifycode-old" class="ui-btn ui-btn1" href="javascript:;"><i>免费获取校验码</i></a><span id="JS_countdown" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>校验码已发送成功，请查看手机</span><span class="tips-winfo"><span class="ui-btn ui-disbtn" href=""><i>(<span class="num-second">60</span>)秒后再次发送</i></span>60秒内没有收到短信?　</span></span></p>
									<p><label>请输入短信校验码：</label><input disabled="true"  type="text" id="sso_verifycode2" name="authenticationCode" value="" class="input-text i-checkcode" /></p>
									<p><a class="ui-btn ui-btn2 btn-disabled" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
									-->
								</div>
							</form>

						</div>
					</div>
				</div>
				<!--步骤1-->
				<!--步骤2-->
				<div id="storePhoneChangeSuccess" style="display:none" class="ui-box-container">
					<div class="ui-box-title"><h3>修改手机</h3></div>
						<p class="tips-info"><span class="tips-ico03"></span>修改手机号后，原来的手机将不再接收现金账户余额变动提醒短息。</p>
						<div class="msg-success">
							<h3>恭喜！手机修改成功！</h3>
							<p class="mt10">您现在可以：<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
						</div>
					</div>
				</div>
				<!--步骤2-->
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script src='/js/myspace/mobileUtil.js' type='text/javascript'></script>
<script type="text/javascript">

	var phone = /^1[3|5|8][0-9]\d{8}$/;
	function sso_verifycode3_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$.trim($("#old_mobile").val())+"&authenticationCode=" + $.trim($("#sso_verifycode3").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#sso_mobile").removeAttr("disabled");
				$("#send-verifycode").removeClass("ui-btn2 btn-disabled").addClass("ui-btn1");
			}else{
				error_tip("#sso_verifycode3","老手机号校检码错误");
				$("#sso_mobile").attr("disabled","true");
				$("#send-verifycode").removeClass("ui-btn1").addClass("ui-ui-btn2 btn-disabled");
			}
		});
	}
	function sso_verifycode4_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?mobile="+$("#sso_mobile").val().replace(/\s/g,"")+"&authenticationCode=" + $.trim($("#sso_verifycode4").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#submitBtn").removeClass("ui-btn2 btn-disabled").addClass("ui-btn1");
			}else{
				error_tip("#sso_verifycode4","新手机号校检码错误");
				$("#submitBtn").removeClass("ui-btn1").addClass("ui-ui-btn2 btn-disabled");
			}
		});
	}


	 $('#send-verifycode-old').click(function(){
	 	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $("#old_mobile").val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
			if (json.success) {
				$('#send-verifycode-old').hide();
				$("#JS_countdown-old").show();
				JS_countdown("#JS_countdown-old span.num-second");
			} else {
				alert('验证码发送失败，请重新尝试');
			}
			
		});
	 });


	 $('#send-verifycode').click(function(){
	 	if(phone.test($('#sso_mobile').val().replace(/\s+/g,''))){
			$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#sso_mobile').val().replace(/\s+/g,'') + "&jsoncallback=?",function(json){
				if (json.success) {
					$('#send-verifycode').hide();
					$("#JS_countdown").show();
					JS_countdown("#JS_countdown span.num-second");
					$("#sso_verifycode4").removeAttr("disabled");
				} else {
					alert('验证码发送失败，请重新尝试');
				}	
			});	
		}else{
			error_tip('#sso_mobile','请输入有效的手机号码');
		}
	 });


	function validate_pass(){
		$('#myForm').submit();	
	}



        /**
	function checkUniqueMobile(v){
		var flag=false;
		if(phone.test(v)){
			flag=true;
		}else{
			error_tip('#sso_mobile2','请输入有效的手机号码');
		}
		return flag;
	}
	function sso_mobile2_callback(call){
	    if(checkMobileNumber('#sso_mobile2')){
			call();	
		}
	}
	function sso_verifycode2_callback(call){
		call();	
    }
	 $('#send-verifycode').click(function(){
	 	if(checkMobileNumber('#sso_mobile2')){
	 		sendAuthenticationCode('#sso_mobile2');
		}
	 });

	function validate_pass(){
		var postData = {validatePasswodCode:$("#sso_verifycode2").val(),mobileNumber:$('#sso_mobile2').val().replace(/\s+/g,'')};
		validateAuthenticationCode('#sso_verifycode2','#storePhoneChange','/myspace/account/store_phone_change_success.do',postData);
	}
	*/
</script>
</body>
</html>
