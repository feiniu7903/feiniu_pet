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
.info em, .info strong, .info span {color:#f60;font-style:normal;}
.bank {padding-left:10px;}
</style>
</head>

<body>

<div><a name="top"></a>
	<#include "/WEB-INF/pages/mobile/common/header.ftl">
    <div>
    	<strong>您的订单已经提交成功！</strong>
    	<h3>订单信息</h3>
        <p class="info">
            订单号:${ordOrder.orderNo}<br />
            您订购的是: 
            <@s.iterator value="ordOrder.ordOrderItemProds" var="prod">
           		<#if additional=='false'>
           			<a href="${contentPath}/m/buy/prodDetail.do?id=${productId}">${productName} </a>
           		</#if>
           </@s.iterator><br />
            游玩日期:${ordOrder.zhVisitTime}<br />
            共返奖金:<em>${cashRefund/100}</em><br />
            应付总金额:<strong>¥${ordOrder.oughtPayFloat}</strong><br />
            驴妈妈现金账户余额:<span>¥158.00</span><br />
        	<a href="#">使用现金账户余额支付</a>
        </p>
        <h3>其它支付方式</h3>
        <p class="bank">
          <input type="radio" name="bank" /><img src="http://pic.lvmama.com/img/wap/icbc.gif" width="154" height="33" alt="中国工商银行" /><br />
          <input type="radio" name="bank" /><img src="http://pic.lvmama.com/img/wap/cmbchina.gif" width="154" height="33" alt="招商银行" /><br />
          <input type="radio" name="bank" /><img src="http://pic.lvmama.com/img/wap/ccb.gif" width="154" height="33" alt="中国建设银行" /><br />
          <input type="submit" value=" 进行支付 " />
        </p>
    </div>
     <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
