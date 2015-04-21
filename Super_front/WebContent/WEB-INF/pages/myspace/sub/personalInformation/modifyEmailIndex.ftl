<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改邮箱-驴妈妈旅游网</title>
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
				<a class="current">修改邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>修改邮箱</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>修改邮箱后，可以用新邮箱地址登录。原来的邮箱地址将不能用来登录。</p>
							<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证原邮箱，输入新邮箱地址</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证新邮箱</li>
							    <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>新邮箱修改成功</li></ul>
							</div>
							<div class="edit-inbox">
								<form id="myForm" action="/myspace/userinfo/email_send.do" method="post">
									<p><label>原邮箱地址：</label><span class="u-info-big"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(#request.oldEmail)" /></span>&nbsp;&nbsp;&nbsp;<a href="/myspace/userinfo/contactCustomService.do" target="_blank">原邮箱地址已不再使用？</a></p>
<input type="hidden"  id="old_email" name="old_email" value="${oldEmail}"/>
									<p><label></label><a class="ui-btn ui-btn1" id="send-verifycode" onclick="sendVerifycode()"  style="display: inline-block;"><i>发送验证邮件</i></a>
						                        <span id="JS_sendEmail" style="display:none"><span class="tips-success"><span class="tips-ico01"></span>已成功发送验证邮件。<!--a href="" target="_blank">登录邮箱</a--></span><span class="lh28">&#12288;&#12288;没收到邮件？<a style="cursor: pointer;" onclick="sendVerifycode()">再次发送</a></span></span>
						                        </p>
									<p><label>请输入验证码：</label><input type="text"  id="sso_verifycode3" name="authenticationCode" value="" class="input-text i-checkcode" />
									<p><label>新邮箱地址：</label><input type="text"  name="email" id="sso_email" class="input-text input-email" /></p>
									<p><a id="submitBtn" class="ui-btn ui-button"><i>&nbsp;下一步&nbsp;</i></a></p>
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
<script type="text/javascript">
	var oldEmail = "${Request.oldEmail}";
	function sso_email_callback(call){
		if($.trim($("#sso_email").val()) == ""){
			error_tip("#sso_email","Email地址不能为空");
		}else if($.trim($("#sso_email").val()) == oldEmail){
			error_tip("#sso_email","该Email地址已被使用，请更换其他邮箱");
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

	function sso_verifycode3_callback(call){
		$.getJSON("http://login.lvmama.com/nsso/ajax/validateAuthenticationCode.do?email="+$.trim($("#old_email").val())+"&authenticationCode=" + $.trim($("#sso_verifycode3").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				call();
				$("#sso_email").removeAttr("disabled").removeClass("input-disabled");
				$("#submitBtn").addClass("ui-btn4").removeClass("ui-btn2 btn-disabled");
			}else{
				error_tip("#sso_verifycode3","校检码错误");
			}
		});
	}
	function validate_pass(){
		$.getJSON("http://login.lvmama.com/nsso/ajax/checkUniqueField.do?email=" + $.trim($("#sso_email").val()) + "&jsoncallback=?",function(json){
			if (json.success == true) {
				$('#myForm').submit();
			}else{
				error_tip("#sso_email","该Email地址已被使用，请更换其他邮箱");
			}
		});
	}
	
	

/**
 * 邮件bug zhux.chen 2013.7.29
 */
	function sendVerifycode(){
	      $.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?userId="+${user.id}+"&mobileOrEMail=" + oldEmail + "&validateType=EMAIL_NUMBER_AUTHENTICATE_CODE&jsoncallback=?",
            function(json){
                if (json.success) {
                    $("#send-verifycode").hide();
                    $("#JS_sendEmail").show();

                } else {
                    alert('验证码发送失败，请重新尝试');
                }
            }); 
	}
 

</script>
	<script>
		cmCreatePageviewTag("绑定邮箱", "D1003", null, null);
	</script>
</body>
</html>