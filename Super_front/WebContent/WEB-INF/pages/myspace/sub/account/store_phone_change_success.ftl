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
				<a class="current">修改手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box-title"><h3>修改手机</h3></div>
					<p class="tips-info"><span class="tips-ico03"></span>修改手机号后，原来的手机将不再接收现金账户余额变动提醒短息。</p>
					<div class="msg-success">
						<h3>恭喜！手机修改成功！</h3>
						<p class="mt10">您现在可以：<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
</body>
</html>