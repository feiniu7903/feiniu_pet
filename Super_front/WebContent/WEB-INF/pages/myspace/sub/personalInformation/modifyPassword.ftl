<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>登录密码修改-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-password">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">登录密码修改</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				<div class="ui-box mod-edit password-edit">
					<div class="ui-box-title"><h3>登录密码修改</h3></div>
					<div class="ui-box-container">
						<div class="edit-box clearfix password-edit-box">
    						<p class="tips-info"><span class="tips-ico03"></span>修改登录密码后，原密码将不能用来登录。</p>
    						<div class="edit-inbox">
							<form action="/myspace/submitModifyPassword.do" method="post">
								<p><label><span>*</span>请输入旧密码：</label><input type="password" id="sso_oldPassword" name="orgPassword" class="input-text input-pwd" /></p>
								<p><label><span>*</span>请输入新密码：</label><input type="password" id="sso_password" name="newPassword" class="input-text input-pwd" /><span class="tips-warn" style="display:none"><span class="tips-ico03"></span>密码在6-16个字符内</span></p>
								<p><label><span>*</span>请确认新密码：</label><input type="password" id="sso_againPassword" name="confirmPassword" class="input-text input-pwd" /><span class="tips-error" style="display:none"><span class="tips-ico02"></span>两次输入的密码不一致</span></p>

								<p><a class="ui-btn ui-button" id="submitBtn"><i>&nbsp;确 定&nbsp;</i></a></p>
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script type="text/javascript">

function validate_pass(){
	$("form").submit();
}
</script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script src="http://pic.lvmama.com/js/new_v/form/form.validate.js"></script>
	<script>
		cmCreatePageviewTag("修改登录密码", "D1003", null, null);
	</script>
</body>
</html>