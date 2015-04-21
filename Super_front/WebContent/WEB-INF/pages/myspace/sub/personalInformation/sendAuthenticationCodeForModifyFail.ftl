<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改邮箱-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
</head>
<body id="page-userinfo">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">修改邮箱</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 绑定及验证>> -->
				<p class="tips-info"><span class="tips-ico03"></span>修改邮箱后，可以用新邮箱地址登录。原来的邮箱地址将不能用来登录。</p>
				<div class="set-step set-step3 clearfix">
					<ul class="hor">
					    <li class="s-step1"><span class="s-num">1</span>验证原邮箱，输入新邮箱地址</li>
					    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>验证新邮箱</li>
					    <li class="s-step3"><i class="s-arrow"></i><span class="s-num">3</span>新邮箱修改成功</li>
					</ul>
				</div>
				<div class="msg-error">
					<span class="msg-ico03"></span>
					<h3>抱歉，验证邮件未发送。请<a href="/myspace/userinfo/email_bind.do">重新尝试</a></h3>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
</body>
</html>