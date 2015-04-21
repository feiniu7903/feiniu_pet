<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>驴妈妈-手机版</title> 
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css?r=2916" rel="stylesheet" type="text/css" /> 
<style> 
.red {color:#f00;}
</style> 
</head> 
 
<body> 
 
<div> 
	<#include "/WEB-INF/pages/mobile/common/header.ftl"> 
    <div class="search-index"> 
    	<h3>填写注册信息</h3> 
        <!--====== 当手机号格式不正确时显示 ========--> 
        <p class="red">手机号格式不正确！</p> 
        <!--====== End ========--> 
        <!--====== 当用户名格式不正确时显示 ========--> 
        <p class="red">用户名格式不正确！</p> 
        <!--====== End ========--> 
        <!--====== 当Email格式不正确时显示 ========--> 
        <p class="red">Email格式不正确！</p> 
        <!--====== End ========--> 
        <!--====== 当密码格式不正确时显示 ========--> 
        <p class="red">密码格式不正确！</p> 
        <!--====== End ========--> 
        <!--====== 当该手机号已经被注册时显示 ========--> 
        <p class="red">该手机号已经被注册！</p> 
        <!--====== End ========--> 
        <!--====== 当用户名XXX已被占用时显示 ========--> 
        <p class="red">用户名XXX已被占用！</p> 
        <!--====== End ========--> 
        <!--====== 当该邮箱地址已经被使用时显示 ========--> 
        <p class="red">该邮箱地址已经被使用！</p> 
        <!--====== End ========--> 
        <form action="reg_ok.html"> 
        <p> 
        	手机号：<span class="c-gray">（用于确认订单信息）</span><br /> 
            <input type="text" /><br /> 
        	用户名：<span class="c-gray">（4-16个字符）</span><br /> 
            <input type="text" /><br /> 
            E-mail：<span class="c-gray">（用于取回密码）</span><br /> 
            <input type="text" /><br /> 
            密码：<span class="c-gray">（6-16位英文字母或数字）</span><br /> 
            <input type="password" /><br /> 
            <input type="submit" value="  注册  " /> 
        </p> 
        </form> 
    </div> 
    <p>账户已激活，请<a href="login.html">立即登录</a></p> 
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div> 
 <img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body> 
</html> 
