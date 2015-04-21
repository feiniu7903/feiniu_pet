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
    	<h3>登录</h3> 
        <!--====== 帐号或密码不正确！ 当帐号或密码不正确时显示 ========--> 
        <p class="red"> ${Request.errorMessages}</p> 
        <!--====== End ========--> 
        <form action="${sso}/wap/login.do" method="post"> 
        <p> 
        	用户名/手机号/Email：<br /> 
            <input type="text" name="mobileOrEMail"/><br /> 
        	密码：<br /> 
            <input type="password" name="password"/><br />
            
            <input type="hidden" name="service" value="${service}"/> 
            <input type="submit" value="  登录  " /> 
        </p> 
        </form> 
    </div> 
    <p>尚无账户，请<a href="${sso}/wap/toRegist.do">立即注册</a></p> 
  	<#include "/WEB-INF/ftl/mobile/common/footer.ftl">
</div> 
 
</body> 
</html> 