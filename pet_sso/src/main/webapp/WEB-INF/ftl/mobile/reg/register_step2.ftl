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
    	<h3>激活帐户</h3> 
        <!--====== 当激活码不正确时显示 ========--> 
        <p class="red">${Request.errorMessages}</p> 
        <!--====== End ========--> 
        <p>您填写的手机号是${mobileOrEMail}，激活码稍后将以免费短信的形式发送给您，请输入激活码，完成注册。</p> 
        <form action="${sso}/wap/activeAccount.do" method="post"> 
        <p> 
            <input type="text" name="authenticationCode"/><br /> 
            <input type="submit" value="  激活  " /> 
        </p> 
        </form> 
    </div> 
    <p>如果长时间没有收到短信，请点<br /><a href="${sso}/wap/reSendCode.do?mobile=${mobileOrEMail}">重新发送</a></p> 
      	<#include "/WEB-INF/ftl/mobile/common/footer.ftl">
    
</div> 
 
</body> 
</html> 