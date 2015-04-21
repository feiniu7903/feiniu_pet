<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
</head>
<body id="page-store">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p><a href="http://www.lvmama.com">我的驴妈妈</a> 
			&gt; <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
			&gt; <a href="http://www.lvmama.com/myspace/account/bonusreturn.do">现金账户</a>
			&gt; <a class="current" href="javascript:return false;">修改手机</a>
			</p>
		</div>
		<#include "/WEB-INF/pages/myspace/base/my_space_nav.ftl"/>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="msg-error">
					<span class="msg-ico03"></span>
					<h3>抱歉！手机修改失败！请重新尝试。</h3>
					<p class="mt10">您现在可以：<a href="/myspace/userinfo.do">返回个人资料</a>　　<a href="/myspace/index.do">我的驴妈妈</a></p>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
</body>
</html>