<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>手机验证</title>
<link href="/nsso/style/newegg_css.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div class="lvmama_login">
    <div class="step_list"><img src="/nsso/images/newegg/step1.gif" /></div>
    <div class="sj_renzhheng">
	    <form action="/nsso/shoppingSeason/verifyCode.do" id="yzmPostForm" method="post">
	    <@s.token></@s.token>
		<input name="mobile" type="hidden" value="<@s.property value='mobile' />"/>
		<input name="channel" type="hidden" value="10922"/>
		<input name="mobileNumber" type="hidden" id="mobileNumber"  value="<@s.property value='mobile' />"/>
		<input name="membershipCard" type="hidden" value="<@s.property value='membershipCard' />"/>
	      <p>您填写的手机号是<span><@s.property value='mobile' /></span>，已经发出验证短信。</p>
	      <p>请填写手机上的6位数字激活码，完成激活后即注册成功。</p>
	      <div class="jh_mima">
	        <div class="jh_title">激活码</div>
	        <input name="authenticationCode" id="yzm" type="text" class="jh_mima_txt" /><@s.actionerror/>
	        <br />
	        <input type="button" value="完成激活" class="bt_lv2" onClick="$('#yzmPostForm').submit()" />
	        <br />
	        <p class="dianji_jh">如果长时间没有收到短信，<a href="#" class="resend" onClick="sendAuthenticationCode()">重新发送</a>确认短信。</p>
	      </div>
	     </form>
    </div>
  </div>
</body>


<script src="/nsso/js/common/jquery.js" type="text/javascript"></script>		
	<script src="/nsso/js/common/closeF5MouseRight.js" type="text/javascript"></script>
	<script>
		function sendAuthenticationCode() {		    	
			$.ajax({
				type: "POST",
				url: "/nsso/ajax/reSendAuthenticationCode.do",
				async: false,
				data: {
					mobile: <@s.property value='mobile' />
				},
				dataType: "json",
				success: function(response) {
					if (response.success == true) {
						alert('验证码已经重新发送成功');
					} else {
						alert('验证码发送失败，请重新尝试');
					}
				}
			});	
		}
	</script>
	</html>