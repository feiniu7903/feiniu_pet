<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>邮箱注册</title>
		<link href="http://pic.lvmama.com/styles/login/Login_20100420.css" rel="stylesheet" type="text/css" />
	</head>

	<body>

		<div class="c_MainContainer">
			<div class="c_eHeader">
				<a href="http://www.lvmama.com/" class="aNone_sty"><img
						src="http://pic.lvmama.com/img/lvmamalogo.gif" alt="驴妈妈网旅游网" />
				</a>
				<img src="/nsso/images/login_tel.gif" alt="订购热线 8:00-20:00　1010-6060"
					class="c_tel" />
				<span>已经有驴妈妈网帐号？ 请<a href="/nsso/login">登录</a></span>
			</div>

			<!--Step start-->
			<dl class="c_Steps">
				<dt id="Steps_Title_2">
					<strong>2.邮箱/手机验证</strong>
				</dt>
				<dd id="Steps_Content_2">
					<p>
						您填写的<@s.if test='membershipCard!=null && membershipCard!=""'>会员卡是<span class="c_Red"><@s.property	value='membershipCard' /></span>,</@s.if>
						邮箱是
						<strong class="c_Red"><@s.property value='email' />
						</strong> ，已经发出验证邮件。
						<a href=<@s.property value="userMailHost"/> target="_blank">
						去邮箱查收验证信</a>
					</p>
					
					<form action="/nsso/register/verifyCode.do" id="yzmPostForm" method="post">
						<@s.token></@s.token>
						<input name="email" type="hidden" value="<@s.property value='email' />"/>
						<input name="membershipCard" type="hidden" value="<@s.property value='membershipCard' />"/>
						<div>
							请填写邮件中的<strong>６位数字激活码，完成注册</strong>。<br />
							激活码：
							   <input name="authenticationCode" id="yzm" type="text" />
							  <@s.actionerror/>
							 
						</div>
						<div class="c_paddingL255">
							<img id="subImg" style="cursor:pointer"
									src="/nsso/images/c_login_btn.gif" onClick="$('#yzmPostForm').submit()"/>
							
						</div>
					</form>
						<ul>
					<li>
						如果长时间没有收到邮件，请尝试：
					</li>
					<li>
						１.若未收到邮件，请到垃圾邮件箱查看，邮件可能被误放入。
					</li>
					<li>
						２.点此
						<a id="reSend" href="#" onClick="sendAuthenticationCode()">重新发送</a>确认邮件。
					</li>
					<li>
						３.点此
						<a id="backPage" href="/nsso/register/registering.do">更换注册邮箱</a>，再次接收验证邮件。
					</li>
					</ul>

				</dd>
			</dl>
			<!--Step end ///////-->

			<div class="c_eFoot">
				Copyright &copy; 2011 www.lvmama.com. 景域旅游运营集团版权所有
				<br />
				沪ICP备07509677
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
					email: "<@s.property value='email' />"
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
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>


