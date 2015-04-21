<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>设置支付密码-驴妈妈旅游网</title>
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
				<a class="current">设置支付密码</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
						<!-- 设置支付密码>> -->
				<div class="ui-box mod-edit password-edit">
					<div class="ui-box-title"><h3>设置支付密码</h3></div>
					<div id="store_password_set1" class="ui-box-container">
				    	<!-- 修改密码>> -->
				    	<div class="edit-box clearfix password-edit-box">
				        	<p class="tips-info"><span class="tips-ico03"></span>设置支付密码后，方可进行支付和退款提现。同时您的账户会更加安全。</p>
				        	<div class="edit-inbox">
				            <p><label>请输入支付密码：</label><input type="password" id="paymentPassword" name="paymentPassword" value="" class="input-text input-pwd" /><span id="paymentPasswordRight"></span><span id="paymentPasswordTip"></span></p>
				            <p><label>请确认支付密码：</label><input type="password" id="confirmPassword" name="confirmPassword" value="" class="input-text input-pwd" /><span id="confirmPasswordRight"></span><span id="confirmPasswordTip"></span></p>
				            <p><a id="initPasswordButton" class="ui-btn ui-button"><i>&nbsp;确 定&nbsp;</i></a></p>
				            </div>
				        </div>
				        <!-- <<修改密码 -->
					</div>
					<div id="store_password_set2" class="ui-box-container" style="display:none">
				        <!-- 修改密码>> -->
				        <p class="tips-info"><span class="tips-ico03"></span>设置支付密码后，方可进行支付和退款提现。同时您的账户会更加安全。</p>
				        <div class="msg-success"><span class="msg-ico01"></span>
				            <h3>恭喜！支付密码设置成功！</h3>
				            <p class="mt10">您现在可以：<a href="/myspace/account/store_add.do">立即充值</a>　　　<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
				        </div>
				        <!-- <<修改密码 -->
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src='/js/myspace/account.js' type='text/javascript'></script>
</body>
</html>