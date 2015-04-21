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
							    <li class="s-step1"><span class="s-num">1</span>输入邮箱地址</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证邮箱</li>
							    <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>邮箱绑定成功</li>
								</ul>
							</div>
							<div class="msg-error">
								<span class="msg-ico03"></span>
								<h3>抱歉，验证邮件未发送。请<a href="/myspace/userinfo/email_bind.do">重新尝试</a></h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
<script type="text/javascript">
	 $('#sendAuthenticationCode').click(function(){
	 alert('');
	 $.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail=" + $('#mobile').val() + "&jsoncallback=?",function(json){
			if (json.success) {
				$('#reSendAuthenticationCodeDiv').show();
				$('#sendAuthenticationCodeDiv').hide();	
			} else {
				alert('验证码发送失败，请重新尝试');
			}
			
		});	
	 });
</script>
	<script>
		cmCreatePageviewTag("绑定邮箱-发送验证邮件", "D1003", null, null);
	</script>
</body>
</html>