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
    	<h3>我的订单</h3>
        <p>
            【团购订单,请访问<a href="http://www.lvmama.com">www.lvmama.com</a>查看】
            <a href="${contentPath}/m/myorder/unpaidOrders.do">待支付订单(${countMap.unpaidOrders})</a><br />
            <a href="${contentPath}/m/myorder/approvingOrders.do">审核中订单(${countMap.approvingOrders})</a><br />
            <a href="${contentPath}/m/myorder/paidOrders.do">已付款订单(${countMap.paidOrders})</a><br />
            <a href="${contentPath}/m/myorder/finishedOrders.do">完成订单</a><br />
            <a href="${contentPath}/m/myorder/canceledOrders.do">取消订单</a>
             </p>
    </div>
   <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
