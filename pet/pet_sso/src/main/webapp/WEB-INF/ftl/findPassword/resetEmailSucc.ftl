<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>重置密码邮件发送成功-驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/login/delv_css.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>

<#include "/common/coremetricsHead.ftl">
</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">重置密码</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="zhfs_center">
			<div class="zhfs_strong_title">邮箱找回密码</div>
			<div class="zhfs_re zhfs_re_email">
				<i class="zhfs_sb zhfs_re_success"></i><strong class="zhfs_re_strong">您好，我们已发送验证邮件至您的邮箱：${email}</strong>
				<p class="zhfs_line1 color333">点击邮箱里的链接，设置新密码。</p>
				<p class="zhfs_line2 color333"><a href="${userMailHost}" target="_blank" class="zhfs_sb zhfs_link_email"></a>&nbsp;没有收到邮件？<a href="/nsso/findpass/findType.do?email=${email}" class="link_blue">再次发送</a></p>
				<div class="zhfs_bottom_email">
					<p>没有收到邮件</p>
					<p>1、到广告邮件、垃圾邮件目录里找找看</p>
					<p>2、不小心填错了Email？<a href="/nsso/findpass/findType.do?email=${email}" class="link_blue">重新填写</a></p>
				</div>
			</div>
		</div>
	</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->

<script>
      cmCreatePageviewTag("邮箱找回密码-已发送验证码", "F0001", null, null);
</script>
</body>
</html>
