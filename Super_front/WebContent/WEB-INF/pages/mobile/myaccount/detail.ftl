<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="chg" /> 
<meta http-equiv="Cache-Control" content="no-cache" />
<title>驴妈妈-手机版</title>
<link href="http://pic.lvmama.com/styles/wap/lvmamaWap.css?r=2916" rel="stylesheet" type="text/css" />
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/header.ftl">
     <div>
    	<h3>我的账户</h3>
        <h5>个人信息</h5>
        <p>
            用户名：<@s.property value="user.userName"/><br />
            姓　名：<@s.property value="user.realName"/><br />
            手机号：<@s.property value="user.mobileNumber"/><br />
            邮　箱：<@s.property value="user.email"/>
         </p>
        
       <!-- <h5>奖金账户</h5>
        <p>
            奖金账户余额：元<br />
         </p>
        <h5>现金账户</h5>
        <p>
            可用余额：88元
         </p>-->
        <h5>
        <@s.if test='pageConfig.totalResultSize>2'>
			<a href="${contentPath}/m/account/moreReceivers.do" class="bt">常用取票人</a>
		</@s.if>
		<@s.else>
			常用取票人
		</@s.else>
        &nbsp;<a href="${contentPath}/m/account/toReceiver.do">新增</a></h5>
        <p>
        <@s.iterator value="receiversList">
          姓　名：${receiverName}<br />
            手机号：${mobileNumber}<br />
            <a href="${contentPath}/m/account/toReceiver.do?recieverId=${receiverId}">编辑</a><br />
        </@s.iterator>
         </p>
         <a href="${contentPath}/m/account/moreReceivers.do" class="bt">更多>></a>
         <br />
    </div>
       <#include "/WEB-INF/pages/mobile/common/footer.ftl">
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>

