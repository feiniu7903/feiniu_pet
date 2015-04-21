<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>修改支付密码-驴妈妈旅游网</title>
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
				<a class="current">修改支付密码</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<!-- 修改支付密码>> -->
				<div class="ui-box mod-edit store_pwd_change-edit">
					<div class="ui-box-title"><h3>修改支付密码</h3></div>
					<div id="store_password_change1" class="ui-box-container">
				    	<!-- 修改密码>> -->
				    	<div class="edit-box clearfix store_pwd_change-edit-box">
				        	<p class="tips-info"><span class="tips-ico03"></span>支付密码请不要与登录密码相同，以确保您现金账户的安全性。</p>
				        	<div class="edit-inbox">
				            <p><label>请输入原支付密码：</label><input type="password" id="oldPaymentPassword" name="oldPaymentPassword"  class="input-text input-pwd" /><span id="oldPaymentPasswordTip"></span><span id="oldPaymentPasswordRight"></span>
				            <div class="ui-font-right"><a href="/myspace/userinfo/contactCustomService.do">原手机号已丢失或停用? </a></div></p>
				            <p><label>请输入新支付密码：</label><input type="password" id="newPaymentPassword" name="newPaymentPassword"  class="input-text input-pwd" /><span id="newPaymentPasswordTip"></span><span id="newPaymentPasswordRight"></span></p>
				            <p><label>请确认支付密码：</label><input type="password" id="newConfirmPassword" name="confirmPassword"  class="input-text input-pwd" /><span id="newConfirmPasswordTip"></span><span id="newConfirmPasswordRight"></span></p>
				            <p><a id="modifyPasswordButton" class="ui-btn ui-button"><i>&nbsp;确 定&nbsp;</i></a></p>
				            </div>
				        </div>
				        <!-- <<修改密码 -->
					</div>
					<div id="store_password_change2" class="ui-box-container" style="display:none">
				        <!-- 修改密码>> -->
				        <p class="tips-info"><span class="tips-ico03"></span>支付密码请不要与登录密码相同，以确保您现金账户的安全性。</p>
				        <div class="msg-success"><span class="msg-ico01"></span>
				            <h3>恭喜！支付密码修改成功！</h3>
				            <p class="mt10">您现在可以：<a href="/myspace/account/bonusreturn.do">返回现金账户</a></p>
				        </div>
				        <!-- <<修改密码 -->
					</div>
				</div>
				<!-- <<修改支付密码 -->
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src='/js/myspace/account.js' type='text/javascript'></script>
</body>
</html>