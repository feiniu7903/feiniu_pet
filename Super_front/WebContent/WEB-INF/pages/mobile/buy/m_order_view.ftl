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
    	<strong><@s.if test="type!='space'">您的订单已提交成功！</@s.if></strong> 
    	<#if order.paymentTarget=='TOLVMAMA'>
        <p> 
        请选择以下在线支付方式进行支付，如需帮助请致电 <a href="wtai://wp/mc;10106060" class="tel-400">10106060</a>。<br /> 
	        <span class="c-gray">支付完成后，取票人将收到确认短信，凭此短信即可游玩。</span> 
	  	</p> 
	  	<#else> 
		<p> 
		订单审核后，系统会向取票人发送一条手机确认短信，凭此短信前往景点以优惠价购票游玩。 
		</p>
  		</#if>
    	<h3>订单信息</h3> 
        <p class="info"> 
            订单号:${order.orderId}<br /> 
            您订购的是:<@s.iterator value="order.ordOrderItemProds">
            ${productName}
            </@s.iterator><br /> 
            游玩日期:<@s.date name="order.visitTime" format="yyyy年MM月dd日"/><br /> 
            共返奖金:<em>${order.cashRefund/100}</em><br /> 
            应付总金额:<strong>¥${order.oughtPayFloat}</strong><br /> 
            支付方式:
        <#if order.paymentTarget=='TOLVMAMA'>
            	线上支付
           <h3>电话信用卡支付</h3> 
	        <p class="bank">
	        <a href="wtai://wp/mc;4006666699" class="tel-400">1010-6060</a><br /></p> 
	        <h3>手机银行支付</h3> 
	        <p class="bank">
	        <a href="${payHost}/mobile/epos.do?orderId=${order.orderId}&&bank=CMBCHINA-WAP">招商银行</a>
	        <br /></p> 
		<#else>
			取票时付款
			<br/>
			<a href="${contentPath}/m/myorder/unpaidOrders.do?online=false">查看订单状态</a>
        </#if>
        </p> 
    </div> 
  <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div> 
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body> 
</html> 
