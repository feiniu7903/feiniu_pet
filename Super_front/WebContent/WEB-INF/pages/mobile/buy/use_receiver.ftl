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
    	<h3>常用取票人</h3>
        <p>
        <@s.iterator value="receiversList">
            姓名：${receiverName}<br />
            手机号：${mobileNumber}<br />
            <a href="${contentPath}/m/buy/toFillNum.do?receiverId=${receiverId}">使用</a><br />
              </@s.iterator>
        </p>
         <@pagination pageConfig.currentPage pageConfig.totalPageNum contentPath+(pageConfig.url)></@pagination>
    </div>
    <p>
      <a href="${contentPath}/m/buy/toFillNum.do">不使用，返回上一页</a>
    </p>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
