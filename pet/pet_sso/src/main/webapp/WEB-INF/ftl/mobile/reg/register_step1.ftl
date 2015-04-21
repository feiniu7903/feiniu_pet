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
    	<h3>填写注册信息</h3> 
        <!--====== 当手机号格式不正确时显示 ========--> 
        <p class="red">${Request.errorMessages}</p> 
      
        <form action="${sso}/wap/regist.do" method="post"> 
        <p> 
        	手机号:<span class="c-gray">(用于确认订单信息)</span><br /> 
            <input type="text" name="mobileOrEMail" value="${mobileOrEMail}"/><br /> 
        	用户名:<span class="c-gray">(选填,4-16个字符)</span><br /> 
            <input type="text" name="userName" value="${userName}" /><br /> 
            E-mail:<span class="c-gray">(选填,用于取回密码)</span><br /> 
            <input type="text" name="email" value="${email}"/><br /> 
            密码:<span class="c-gray">(6-16位英文字母或数字)</span><br /> 
            <input type="text" name="password" value="${password}"/><br /> 
            <input type="submit" value="  注册  " /> 
        </p> 
        </form> 
    </div> 
      	<#include "/WEB-INF/ftl/mobile/common/footer.ftl">
</div> 
 
</body> 
</html> 