<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>邮箱注册+再次发送</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/super_v2/newReg.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/top/top_common_noLazy.js"></script>
<script type="text/javascript">
	function loginEmail(){
		window.open("<@s.property value='userMailHost'/>");
	}
</script>
</head>

<body>
<!-- 头部 -->
<#include "/WEB-INF/ftl/comm/header.ftl">
<!-- 头部 -->

<div class="phoneReg">
	<s class="sLT"></s><s class="sRT"></s><s class="sLB"></s><s class="sRB"></s>
	<div class="regTop"><span class="newUserEmail2">手机注册驴妈妈新会员</span><!-- <span class="pleaseLogin">已经有驴妈妈账号？请<a href="#" class="pleaseLoginA">直接登录</a></span> --></div>
	<div class="regSuccess nobg">
		我们已再次发送激活信至：<em>${sessionRegisterUser.email}</em> 。 <br />
		请<input type="button" class="nowActive2" value="登录邮箱" onclick="loginEmail()" />完成激活。<br /><br /><br /><br /><br /><br /><br /><br /><br />
	</div>
</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
</body>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>