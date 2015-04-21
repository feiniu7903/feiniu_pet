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
 
<div><a name="top"></a> 
<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div> 
    	<h3>意见反馈</h3> 
        <p>您的意见和建议是我们努力的方向。</p> 
         <p class="red">
        	<#if Request.errorMessages!=null>
        	<@s.property value="#request.errorMessages"/>
        	</#if>
        	
   
        </p>
        <form action="${contentPath}/m/feedback/feed.do" method="post"> 
        <p><textarea name="feedBack.content" cols="26" rows="6" ></textarea><br /> 
            手机型号及浏览器：<br /> 
            <input type="text" size="20" name="feedBack.instantMessaging"/><br /> 
            手机号：<br /> 
            <input type="text" size="20" name="feedBack.mobile"/> 
        </p> 
        <p><input type="submit" value=" 提交 " /></p> 
        </form> 
    </div> 
    <#include "/WEB-INF/pages/mobile/common/footer2.ftl">
    
</div> 
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body> 
</html> 
