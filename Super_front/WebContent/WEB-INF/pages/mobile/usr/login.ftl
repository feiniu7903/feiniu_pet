<?xml version="1.0" encoding="utf-8"?>
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
    	<h3>登录</h3>
        <!--====== 当帐号或密码不正确时显示 ========-->
        <p class="red">帐号或密码不正确！</p>
        <!--====== End ========-->
        <form action="index.html">
        <p>
        	用户名/手机号/Email：<br />
            <input type="text" /><br />
        	密码：<br />
            <input type="password" /><br />
            <input type="submit" value="  登录  " />
        </p>
        </form>
    </div>
    <p>尚无账户，请<a href="${contentPath}/m/reg/mobilereg.do">立即注册</a></p>
 <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
