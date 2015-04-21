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
.pc-list {color:#333;padding:5px 0;}
.pc-list li {padding:0 5px;}
.pc-list .odd-line {background-color:#EFEFEF;}
.pc-list del {color:#666;}
.pc-list strong {color:#f00;}
.pc-list em {color:#f60;font-style:normal;}

.num { -wap-input-format: "3N"}
</style>
</head>

<body>

<div><a name="top"></a>
<#include "/WEB-INF/pages/mobile/common/header.ftl">

    <div>
    <form method="post" action="${contentPath}/m/order/saveOrder.do">
    	<strong>您预订的是:<@s.property value="mainProduct.productName"/></strong>
        <p class="red">
        	<!--====== 门票数量格式不对 门票数量格式不对，请输入1及1以上的整数。 ======-->
        	
        	<!--====== 取票人名字格式不正确  取票人名字格式不正确======-->
        	
        	<!--====== 取票人手机号格式不正确  取票人手机号格式不正确======-->
        	
        	<!--====== 订票人名字格式不正确  订票人名字格式不正确======-->
        	
        	<!--====== 订票人手机号格式不正确         订票人手机号格式不正确======-->
        	<#if Request.errorMessages!=null>
        	<@s.property value="#request.errorMessages"/>
        	</#if>
        	
   
        </p>
        <h5>取票人信息:</h5>
        <p><#if receivers!=null>
        姓名:　<input type="text" size="11" name="receivers.receiverName" value="<@s.property value="receivers.receiverName"/>"/><br />
            手机号:<input type="text" size="11" name="receivers.mobileNumber" format="*N" value="<@s.property value="receivers.mobileNumber"/>"/><br />
            <#else>
              姓名:　<input type="text" size="11" name="receivers.receiverName" value="<@s.property value="user.realName"/>"/><br />
            手机号:<input type="text" size="11" name="receivers.mobileNumber" format="*N" value="<@s.property value="user.mobileNumber"/>"/><br />
        </#if>
        	
            <a href="${contentPath}/m/buy/toUserReceivers.do">使用已有取票人</a>
        </p>
        <h5>游玩日期:</h5>
        <p>${visitTime}</p>
        <h5>票种：</h5>
    
        <ul class="pc-list">
           
        <@s.iterator value="branchList" status="ri">
        
            <li>${shortName}<br />
            原价:<del>￥<@s.property value="marketPriceYuan"/></del>&nbsp;现售:<strong>￥<@s.property value="sellPriceYuan"/></strong>&nbsp;奖金:<em>￥<@s.property value="cashRefundFloat"/></em><br />
            数量<input type="text" class="num" name="buyInfo[${ri.index}].num" size="5" value="${minimum}" />
            <input type="hidden" size="5" name="buyInfo[${ri.index}].productId" value="${productId}"/>
            <input type="hidden" size="5" name="buyInfo[${ri.index}].prodBranchId" value="${prodBranchId}"/>
            </li>
      </@s.iterator>
      
        </ul>
        
        <p><input name="submitOrder" type="submit" value="确认并下单" /></p>
    </div>
    <p>
      <a href="${contentPath}/m/buy/toFillDate.do?id=${mainBranch.productId}&branchId=${mainBranch.prodBranchId}">返回上一页</a>
    </p>
    </form>
     <#include "/WEB-INF/pages/mobile/common/footer.ftl">
    
</div>
<img src="${contentPath}${googleAnalyticsGetImageUrl}" />
</body>
</html>
