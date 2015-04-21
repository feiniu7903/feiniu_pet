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
.c-f60 {color:#f60;}
</style>
</head>

<body>

<div><a name="top"></a>
	<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    	<h3><#include "/WEB-INF/pages/mobile/myorder/title.ftl"></h3>
        <p>
            订单编号:${ordOrder.orderId}<br />
            产品名称:<@s.set name="p" value="0"/>
            <@s.set name="market" value="0"/>
           <@s.iterator value="ordOrder.ordOrderItemProds" var="prod">
           		<#if additional=='false'>
           			<#if productType=='TICKET'>
           			<a href="${contentPath}/m/buy/prodDetail.do?id=${productId}">${productName} </a>
           			<@s.set name="p" value="${p+priceYuan}"/>
           			<@s.set name="market" value="${m+marketPriceYuan}"/>
           			<#else>
           			${productName} 
           			</#if>
           		</#if>
           </@s.iterator><br />
            游玩时间:${ordOrder.zhVisitTime} <br />
            票种:
           <@s.iterator value="ordOrder.ordOrderItemProds" var="prod">
           		${shortName}(${quantity})
           </@s.iterator><br />
            支付方式:<#if ordOrder.paymentTarget=='TOLVMAMA'>
            	线上支付
		<#else>
			取票时付款
            </#if>
            <br />
            取票人姓名:${ordOrder.contact.name} <br />
            取票人手机:${ordOrder.contact.mobile} <br />
            共节省:￥${market-p}<br />
            订单结算总额:￥<strong class="c-f60">${ordOrder.oughtPayFloat}</strong> 
        </p>
        <p>
            <@s.if test="title=='unpaid'">
            	<#if ordOrder.paymentTarget=="TOLVMAMA">
            		<a href="${contentPath}/m/buy/viewOrderInfo.do?orderId=${ordOrder.orderId}&type=space">支付该订单</a><br />
            	</#if>
	            <a href="${contentPath}/m/myorder/cancelOrder.do?orderId=${ordOrder.orderId}">取消该订单</a><br />
				<a href="${contentPath}/m/myorder/unpaidOrders.do">返回待支付订单列表</a>
			</@s.if>
			<@s.elseif test="title=='approving'">
	            <a href="${contentPath}/m/myorder/cancelOrder.do?orderId=${ordOrder.orderId}">取消该订单</a><br />
				<a href="${contentPath}/m/myorder/approvingOrders.do">返回审核中订单列表</a>
			</@s.elseif>
			<@s.elseif test="title=='paid'">
				<a href="${contentPath}/m/myorder/paidOrders.do">返回已付款订单列表</a>
			</@s.elseif>
			<@s.elseif test="title=='finished'">
				<a href="${contentPath}/m/myorder/finishedOrders.do">返回已完成订单列表</a>
			</@s.elseif>
			<@s.else>
				<a href="${contentPath}/m/myorder/canceledOrders.do">返回已取消的订单列表</a>
			</@s.else>
        </p>
    </div>
    <#include "/WEB-INF/pages/mobile/common/footer.ftl">
	</div>
	<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
