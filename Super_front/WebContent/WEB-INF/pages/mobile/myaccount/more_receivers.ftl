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
a.bt {font-weight:bold;}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/page_common.ftl">
<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
        <h5>常用取票人&nbsp;<a href="${contentPath}/m/account/toReceiver.do">新增</a></h5>
        <p>
        <@s.iterator value="receiversList">
          姓　名：${receiverName}<br />
            手机号：${mobileNumber}<br />
            <a href="${contentPath}/m/account/toReceiver.do?recieverId=${receiverId}">编辑</a><br />
        </@s.iterator>
         </p>
         <@pagination pageConfig.currentPage pageConfig.totalPageNum contentPath+(pageConfig.url)></@pagination>
         <br/>
          <a href="${contentPath}/m/account/info.do">返回我的账户</a>
    </div>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
