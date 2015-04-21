<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>验证手机-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
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
				<a class="current">验证手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit phone-edit">
					<div class="ui-box-title"><h3>验证手机</h3></div>
						<div class="ui-box-container">
							<div class="edit-box clearfix phone-edit-box">
							<p class="tips-info"><span class="tips-ico03"></span>
							验证手机后，可以及时收到驴妈妈的优惠产品信息。<@s.if test='!"F".equals(user.isMobileChecked)'>验证手机成功可获得<span class="lv-c1">300</span>积分。</@s.if>
							</p>
							<div class="msg-success"><span class="msg-ico01"></span>
					            <h3>恭喜！手机验证成功！<@s.if test='!"F".equals(user.isMobileChecked)'>您获得了<span class="lv-c1">300</span>积分。</@s.if></h3>
					            <p style="padding-left:0px">今后，你可以使用手机号<span class="lv-c1"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobile)" /></span>登录驴妈妈旅游网。</p>
					            <p class="mt10" style="padding-left:0px">您现在可以：
					            	<a href="/myspace/userinfo.do">返回个人资料</a>&#12288;&#12288;
					            	<a href="/myspace/account/store.do">返回存款帐户</a></p>
					        </div>
						</div>
					</div>
				</div>
			</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
		cmCreatePageviewTag("手机验证成功", "D1003", null, null);
	</script>
</body>
</html>