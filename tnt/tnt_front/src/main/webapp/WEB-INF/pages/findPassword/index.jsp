<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>选择找回密码方式-驴妈妈分销平台</title>
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
	<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
	<script src="http://pic.lvmama.com/js/new_v/ob_login/l_mmzh.js"></script>
	<script>
		$(function(){
			zhfs_init();
		});
	</script>
<script src="http://pic.lvmama.com/js/common/losc.js" type="text/javascript" ></script>
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
			<ul class="zhfs_step">
				<li><i>3</i><label class="zhfs_step_text">设置新密码</label></li>
				<li><i>2</i><label class="zhfs_step_text">输入注册账号</label><em class="zhfs_jiao"></em></li>
				<li class="curr"><i>1</i><label class="zhfs_step_text">选择找回密码方式</label><em class="zhfs_jiao"></em></li>
			</ul>
			<div class="zhfs_zhmm">
				<div class="zhfs_mm">
					<span class="zhfs_mm_btn" onclick="location.href='/findpass/findType?email=Y'">
						<i class="zhfs_email_cur"></i>
						<label class="zhfs_mm_title">邮箱找回密码</label> 
						<i class="zhfs_mm_jiao"></i>
					</span>
					<span class="zhfs_mm_text">邮箱发送邮件找回密码</span>
				</div>
				<div class="zhfs_mm">
					<span class="zhfs_mm_btn" onclick="location.href='/findpass/findType?mobile=Y'">
						<i class="zhfs_phone_cur"></i>
						<label class="zhfs_mm_title">手机找回密码</label> 
						<i class="zhfs_mm_jiao"></i>
					</span>
					<span class="zhfs_mm_text">手机发送短信找回密码</span>
				</div>
			</div>
		</div>
	</div>
<!-- 尾部 -->
<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
<!-- 尾部 -->
</body>
</html>
