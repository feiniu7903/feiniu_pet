<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>解绑手机-驴妈妈旅游网</title>
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
				<a class="current">解绑手机</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>解绑手机</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
        					<p class="tips-info"><span class="tips-ico03"></span>解绑手机后，手机号将不能用来登录。</p>
        					
        					<div class="set-step set-step1 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机解绑成功</li>
							    </ul>
							</div>
        					
							<div class="msg-success">
								<h3>抱歉，手机解绑失败或验证码失效。请<a href="/myspace/userinfo/phone_delete.do">重新尝试</a></h3>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
	<script>
		cmCreatePageviewTag("解绑手机失败", "D1003", null, null);
	</script>
</body>
</html>