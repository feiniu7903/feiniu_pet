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

.num { -wap-input-format: "11N"}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    	<h3>常用取票人</h3>
    	<p class="red">${Request.errorMessages}</p>
        <!--====== 当手机号格式不正确时显示 ========-->
        <form action="${contentPath}/m/account/addOrUpdateReceiver.do" method="post">
        	<input type="hidden" name="usrReceiver.receiverId" value="${usrReceiver.receiverId}">
	        <!--====== End ========-->
	        <p>
	            姓　名：<input type="text" size="15"  name="usrReceiver.receiverName" value="${usrReceiver.receiverName}" /><br />
	            手机号：<input type="text" size="15" class="num" name="usrReceiver.mobileNumber" value="${usrReceiver.mobileNumber}" />
	        </p>
	        <p>
	            <input type="submit" value=" 确认 " /><br />
	            <a href="${contentPath}/m/account/moreReceivers.do">取消</a>
	         </p>
         </form>
    </div>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
