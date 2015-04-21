<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>验证邮箱-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
</head>
<body id="page-userinfo">
	<div id="wrap" class="ui-container lvmama-bg">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">验证邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>验证邮箱</h3></div>
					<div class="ui-box-container">

    						<div class="edit-box clearfix email-edit-box">
        						<p class="tips-info"><span class="tips-ico03"></span>
        							验证邮箱后，您可以及时收到驴妈妈的优惠产品信息。<@s.if test='!"F".equals(user.isEmailChecked)'>验证邮箱成功可获得<span class="lv-c1">300</span>积分。</@s.if></p>
							<div class="msg-success">
								<h3>您好，我们已发送验证邮件至您的邮箱：<a href="<@s.property value="mailHost" />"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenEmail(email)" /></a></h3>
								<input type="hidden" id="email" name="email" value='<@s.property value="email" />'/>
								<p class="mt10" style="padding-left:0px"><a target="_blank" href="<@s.property value="mailHost" />" class="ui-btn ui-btn4"><i>&nbsp;登录邮箱&nbsp;</i></a>　没收到邮件? 
								<a href="javascript:;" onclick="resend()">再次发送</a></p>
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

	 function resend() {
	   	$.getJSON("http://login.lvmama.com/nsso/ajax/sendAuthenticationCode.do?mobileOrEMail="+$("#email").val()+"&validateType=EMAIL_AUTHENTICATE&userId="+${user.id}+"&jsoncallback=?",function(json){
			if (json.success == true) {
				alert('邮件发送成功');
			} else {
				alert('邮件发送失败，请重新尝试');
			}	
		});	
	}
</script>
</body>
</html>