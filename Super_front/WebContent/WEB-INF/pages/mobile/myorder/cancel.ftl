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
        <p>
        	你确定要取消订单${ordOrder.orderId}?<br />
            <@s.iterator value="ordOrder.ordOrderItemProds" var="prod">
           		<#if additional=='false'>
           			<a href="${contentPath}/m/buy/prodDetail.do?id=${productId}">${productName} </a>
           		</#if>
           </@s.iterator><br />
           <@s.iterator value="ordOrder.ordOrderItemProds" var="prod">
           		${shortName}(${quantity})
           </@s.iterator><br />
        </p>
        <p>
            <a href="${contentPath}/m/myorder/cancelOrder.do?orderId=${ordOrder.orderId}&toPage=false">确定</a><br />
            <a href="${contentPath}/m/myorder/orderDetail.do?orderId=${ordOrder.orderId}&title=unpaid">撤销操作</a>
        </p>
    </div>
    <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
