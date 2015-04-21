<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>重置密码成功-驴妈妈旅游网</title>
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
			<div class="zhfs_re">
				<i class="zhfs_sb zhfs_re_success"></i><strong class="zhfs_re_strong">恭喜！密码重置成功。</strong>
				<#if mobile!=null>
                                <p class="zhfs_line1">提示：绑定邮箱过后，你也可以通过邮箱找回密码！&nbsp;<a href="http://www.lvmama.com/myspace/userinfo/email_bind.do" class="link_blue">立即绑定邮箱</a></p>
				<#elseif email!=null>
                                <p class="zhfs_line1">提示：绑定手机过后，你也可以通过手机找回密码！&nbsp;<a href="http://www.lvmama.com/myspace/userinfo/phone.do" class="link_blue">立即绑定手机</a></p>
                                </#if>
                                <p class="zhfs_line2"><a href="/nsso/login" class="zhfs_sb zhfs_re_login"></a></p>
			</div>
		</div>
	</div>

<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->

<script>
      cmCreatePageviewTag("密码重置成功", "F0001", null, null);
</script>
</body>
</html>
