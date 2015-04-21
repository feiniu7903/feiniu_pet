<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改用户名-驴妈妈旅游网</title>
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
				<a class="current">修改用户名</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
			<div class="ui-box-title"><h3>修改用户名</h3></div>
        <div class="msg-success"><span class="msg-ico01"></span>
            <h3>恭喜！用户名修改成功！</h3>
            <p>今后，你可以使用新用户名<span class="lv-c1">${newUserName}</span>登录驴妈妈旅游网。</p>
            <p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a></p>
        </div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
	<script>
		cmCreatePageviewTag("修改用户名成功", "D1003", null, null);
	</script>
</body>
</html>