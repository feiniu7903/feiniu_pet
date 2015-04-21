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
			<div class="ui-box-title"><h3>登录密码修改</h3></div>
				<p class="tips-info"><span class="tips-ico03"></span>修改登录密码后，原密码将不能用来登录。</p>
				<div class="msg-success"><span class="msg-ico01"></span>
				    <h3>恭喜！登录密码修改成功！</h3>
				    <!--<p class="mt10">您现在需要重新登录。<a href="http://login.lvmama.com/nsso">返回登录页面</a></p>-->
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	
<script type="text/javascript">
    
	$(document).ready(function(){
		var PASSWORD_REGX = /^[A-Za-z0-9]{6,16}$/;


		$('#orgPassword').data('valid','false');
		$('#newPassword').data('valid','false');
		$('#confirmPassword').data('valid','false');

                $('#orgPassword').focus(function(){
			$('#orgPassword').data('valid','false');	
		});
		$('#orgPassword').blur(function(){
			if (PASSWORD_REGX.test(this.value)) {
				$('#orgPassword').data('valid','true');	
			} else {
				$('#newPassword').data('valid','false');
			}			
		});
	        $('#newPassword').focus(function(){
		        $('#newPassword').data('valid','false');
			$('.tips-warn').hide();
		});
		$('#newPassword').blur(function(){
			$('.tips-warn').hide();
			if (PASSWORD_REGX.test(this.value)) {
				$('.tips-warn').hide();
				$('#newPassword').data('valid','true');
			} else {
				$('.tips-warn').show();
			}
		});

		$('#confirmPassword').focus(function(){
		        $('#confirmPassword').data('valid','false');
			$('.tips-error').hide();
		});
		$('#confirmPassword').blur(function(){
			$('.tips-error').hide();
			if (this.value != $('#newPassword').val()) {
				$('.tips-error').show();
				$('#confirmPassword').data('valid','false');
			} else {
				$('#confirmPassword').data('valid','true');
			}
		});
		
		$('#submitBtn').click(function(){
			if ($("#orgPassword").data('valid') == 'false') {
				alert('请输入合法的旧密码');
				$("#orgPassword").focus();
				return;
			}
			if ($("#newPassword").data('valid') == 'false') {
				alert('请输入合法的新密码');
				$("#newPassword").focus();
				return;
			}
			if ($("#confirmPassword").data('valid') == 'false') {
				alert('请确认您的新密码');
				$("#confirmPassword").focus();
				return;
			}

		});
	});
</script>
	<script>
		cmCreatePageviewTag("修改登录密码成功", "D1003", null, null);
	</script>
</body>
</html>