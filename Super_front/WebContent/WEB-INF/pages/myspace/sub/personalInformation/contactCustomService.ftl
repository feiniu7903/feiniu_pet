<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>联系客服-驴妈妈旅游网</title>
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
				<a class="current">联系客服</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit email-edit">
					<div class="ui-box-title"><h3>联系客服</h3></div>
					<div class="ui-box-container">
    					<div class="edit-box clearfix email-edit-box">
						<p class="tips-info"><span class="tips-ico03"></span>亲爱的用户，如果您无法进行账户的相关操作操作，请致电客服1010-6060。为了您的账户安全，我们必须验证您的身份，请先准备好以下资料，再致电客服。</p>
						<div class="msg-success">
<h3>建议提供如下资料：</h3><br/>
<h3>1.提供最近一笔订单的出游人信息（姓名，手机号）</h3><br/>
<h3>2.提供订单交易的银行卡交易流水号或者支付宝交易流水号，或者充值账号的充值银行卡交易流水号或支付宝交易流水号（提供截图）</h3><br/>
<h3>3.提供原绑定手机号</h3>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
	<script>
		cmCreatePageviewTag("联系客服", "D1003", null, null);
	</script>
</body>
</html>