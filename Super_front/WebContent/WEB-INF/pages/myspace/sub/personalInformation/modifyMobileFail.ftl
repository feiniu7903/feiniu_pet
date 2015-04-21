<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改手机-驴妈妈旅游网</title>
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
				<a class="current">修改手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
			
				<div class="set-step set-step1 clearfix">
					<ul class="hor">
				    <li class="s-step1"><span class="s-num">1</span>验证原手机，输入新手机</li>
				    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机修改成功</li>
				    </ul>
				</div>
			
				<div class="msg-success">
					<span class="msg-ico01"></span>
					<h3>抱歉！手机修改失败或验证码失效！请重新尝试。</h3>
					<p class="mt10">您现在可以：<a href="/myspace/userinfo.do">返回个人资料</a>　　<a href="/myspace/index.do">我的驴妈妈</a></p>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
		<script>
		cmCreatePageviewTag("修改手机", "D1003", null, null);
	</script>
</body>
</html>