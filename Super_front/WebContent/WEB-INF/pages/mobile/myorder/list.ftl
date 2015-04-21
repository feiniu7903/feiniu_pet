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
.pc-list {color:#333;padding:5px 0;}
.pc-list li {padding:0 5px;}
.pc-list .odd-line {background-color:#EFEFEF;}
.pc-list del {color:#666;}
.pc-list strong {color:#f00;}
.pc-list em {color:#f60;font-style:normal;}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/page_common.ftl">
	<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    	<h3><#include "/WEB-INF/pages/mobile/myorder/title.ftl"></h3>
        <ul class="pc-list">
        	<@s.if test="title=='unpaid'">
        		<p>
        		<@s.if test="online">线上支付&nbsp;&nbsp;<a href="${contentPath}/m/myorder/unpaidOrders.do?online=false" class="current-a">取票时付款</a></@s.if>
        		<@s.else>
        			<a href="${contentPath}/m/myorder/unpaidOrders.do">线上支付</a>&nbsp;&nbsp;取票时付款
				</@s.else>
        		</p>
			</@s.if>
    		<@s.iterator value="ordersList" var="order">
        			<li><a href="${contentPath}/m/myorder/orderDetail.do?orderId=${order.orderId}&title=${title}">
        			<@s.property value="#order.mainProduct.productName"/>
        			</a></li>
        	</@s.iterator>
        </ul>
        <p>
        <@pagination pageConfig.currentPage pageConfig.totalPageNum contentPath+(pageConfig.url)></@pagination>
        </p>
    </div>
     <#include "/WEB-INF/pages/mobile/common/footer.ftl">
	</div>
	<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
