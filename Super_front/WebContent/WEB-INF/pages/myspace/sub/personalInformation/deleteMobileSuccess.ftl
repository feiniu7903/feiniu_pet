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
        					
        					<div class="set-step set-step2 clearfix">
								<ul class="hor">
							    <li class="s-step1"><span class="s-num">1</span>验证手机</li>
							    <li class="s-step2"><i class="s-arrow"></i><span class="s-num">2</span>手机解绑成功</li>
							    </ul>
							</div>
        					
							<div class="msg-success">
							<span class="msg-ico01"></span>
        						<h3>恭喜！您的手机号<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobile)" /> 解绑成功！</h3>
        						<p class="mt10">您现在可以：<a href="http://www.lvmama.com/myspace/userinfo.do">返回个人资料</a>　　<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
		<script>
		cmCreatePageviewTag("解绑手机成功", "D1003", null, null);
	</script>
</body>
</html>