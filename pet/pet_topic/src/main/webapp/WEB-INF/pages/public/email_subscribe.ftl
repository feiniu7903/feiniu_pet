<@s.if test="!isFrontLogin()">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>邮件订阅</title>
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/global.css">
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/layout.css">
	<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/subscribe.css">
        <link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
	<link rel="shortcut icon" href="http://pic.lvmama.com/img/oldlvmama/img/favicon.ico" />
	<script src="http://pic.lvmama.com/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/UniformResourceLocator.js"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/top/topMsg_comment.js"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/top/jquery.remote.jsonSuggest.js"></script>

</head>
<body>
<#include "/common/setKeywor2.ftl">
<div class="wrap icon-location" style="z-index:0">
<iframe src='/edm/showSubscribeEmail.do' id="contractTemplateIframe" frameborder="no" style="align:center;border:0px solid #7F9DB9;padding-right:1px;height:500px;width:100%;z-index:0;"></iframe>
</div>
<#include "/common/footer.ftl">
</body>
</html>
</@s.if>
<@s.else>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
<link charset="utf-8" media="screen" type="text/css" rel="stylesheet" href="http://pic.lvmama.com/min/index.php?g=commonCss">
<link href="http://pic.lvmama.com/styles/oldlvmama/style/myspace.css" type="text/css" rel="stylesheet"  />
<link rel="shortcut icon" href="http://pic.lvmama.com/img/oldlvmama/img/favicon.ico" />
<script src='http://pic.lvmama.com/js/oldlvmama/js/common/jquery.js' type='text/javascript'></script> 
<script src='/scripts/front/My97DatePicker/WdatePicker.js' type='text/javascript'></script>
<script src='http://pic.lvmama.com/js/oldlvmama/js/member/orderSeach.js' type='text/javascript'></script>
</head>
<body>
<#include "/WEB-INF/ftl/common/myLmamaHeader.ftl">
<#include "/WEB-INF/ftl/common/myspansLeft.ftl">
<div class="content" style="height:700px; z-index: -1;">
<iframe src='/edm/showSubscribeEmail.do?email=<@s.property value="#session.SESSION_USER_OBJECT.email"/>' id="contractTemplateIframe" frameborder="no" style="border:0px solid #7F9DB9;padding-right:1px;height:600px;width:750px;position:absolute;overflow: hidden;z-index:0;"></iframe>
</div>
</div>
<#include "/WEB-INF/ftl/common/footer.ftl">
<#include "/page/static_file/seo/comment_stat.ftl">
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
</body>
</html>
</@s.else>