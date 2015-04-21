<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>重置密码成功-驴妈妈旅游网</title>
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
			<div class="zhfs_re">
				<i class="zhfs_sb zhfs_re_success"></i><strong class="zhfs_re_strong">恭喜！密码重置成功。</strong>
				<c:if test="mobile!=null">
                                <p class="zhfs_line1">提示：绑定邮箱过后，你也可以通过邮箱找回密码！&nbsp;<a href="/myspace/userinfo/email_bind.do" class="link_blue">立即绑定邮箱</a></p>
				</c:if>
				<c:if test="email!=null">
                                <p class="zhfs_line1">提示：绑定手机过后，你也可以通过手机找回密码！&nbsp;<a href="/myspace/userinfo/phone.do" class="link_blue">立即绑定手机</a></p>
                                </c:if>
                                <p class="zhfs_line2"><a href="/login/index.do" class="zhfs_sb zhfs_re_login"></a></p>
			</div>
		</div>
	</div>

<!-- 尾部 -->
<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
<!-- 尾部 -->
</body>
</html>
