<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>重置密码邮件发送成功-驴妈妈分销平台</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link href="http://pic.lvmama.com/styles/login/delv_css.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/new_v/header.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet" type="text/css">
<style type="text/css">
.login_main{width:1000px;}
</style>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top zhfs_logo_top top_wrap">
			<img  class="fx_phone" src="http://pic.lvmama.com/img/fx/fx_phone.png" />
			<span class="login_logo fx_logo"> <a href="http://f.lvmama.com"><img
					src="http://pic.lvmama.com/img/fx/fx_logo.png"></a> <label
				class="text">|</label> <a class="text">重置密码</a>
			</span>
		</div>
		<div class="zhfs_center">
			<div class="zhfs_strong_title">邮箱找回密码</div>
			<div class="zhfs_re zhfs_re_email">
				<i class="zhfs_sb zhfs_re_success"></i><strong class="zhfs_re_strong">您好，我们已发送验证邮件至您的邮箱：${email}</strong>
				<p class="zhfs_line1 color333">点击邮箱里的链接，设置新密码。</p>
				<p class="zhfs_line2 color333"><a href="${userMailHost}" target="_blank" class="zhfs_sb zhfs_link_email"></a>&nbsp;没有收到邮件？<a href="/findpass/findType?email=${email}" class="link_blue">再次发送</a></p>
				<div class="zhfs_bottom_email">
					<p>没有收到邮件</p>
					<p>1、到广告邮件、垃圾邮件目录里找找看</p>
					<p>2、不小心填错了Email？<a href="/findpass/findType?email=${email}" class="link_blue">重新填写</a></p>
				</div>
			</div>
		</div>
	</div>
<!-- 尾部 -->
<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
<!-- 尾部 -->
</body>
</html>
