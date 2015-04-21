<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>绑定手机-驴妈妈旅游网</title>
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
				<a class="current">绑定手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
			
				<div class="set-step set-step2 clearfix">
					<ul class="hor">
				    <li class="s-step1"><span class="s-num">1</span>绑定手机</li>
				    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机绑定成功</li>
				    </ul>
				</div>
			
			
				<div class="msg-success">
					<span class="msg-ico01"></span>

					<h3>恭喜！手机绑定成功！<@s.if test='!"F".equals(oldIsMobileChecked)'>您获得了<span class="lv-c1">300</span>积分。</@s.if></h3>
					<p>今后，你可以使用手机号<span class="lv-c1"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>登录驴妈妈旅游网。</p>
					<p class="mt10">您现在可以：<a href="/myspace/userinfo.do">返回个人资料</a>　　<a href="/myspace/account/store.do">返回存款帐户</a></p>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
		cmCreatePageviewTag("绑定手机", "D1003", null, null);
	</script>
</body>
</html>