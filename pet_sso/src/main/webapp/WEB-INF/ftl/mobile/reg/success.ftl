<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>驴妈妈-手机版</title> 
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css" rel="stylesheet" type="text/css" /> 
<style> 
.red {color:#f00;}
</style> 
</head> 
 
<body> 
 
<div> 
<#include "/WEB-INF/ftl/mobile/common/header.ftl">
    <div class="search-index"> 
    	<h3>注册成功</h3> 
        <p>恭喜！您已成为驴妈妈会员！<br />以后，您可以凭${loginUser.mobileNumber}，${loginUser.email}，或者用户名${loginUser.userName}登录驴妈妈，购买上万种低价旅游产品！ <br /><br /> 
		账户信息可以登录www.lvmama.com进行修改！</p> 
    </div> 
    <p><a href="${index}">马上开始我的旅程</a></p> 
     	<#include "/WEB-INF/ftl/mobile/common/footer.ftl">
    
</div> 
 
</body> 
</html> 