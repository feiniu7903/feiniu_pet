<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/style/myspace.css" type="text/css" rel="stylesheet">
<script src='/js/common/jquery.js' type='text/javascript'></script>
<script type="text/javascript" src="/js/member/pop2.js"></script>
<script type="text/javascript" src="/js/member/selectOrder.js"></script>
<style type="text/css">
.online_list_table{color:#000;font-family:Arial,'宋体';border:1px solid #ccc;margin:20px 0px 0px 10px;}
.online_list_table tr{height:30px;}
.online_list_table td{border:1px solid #ccc;padding-left:8px;line-height:16px;}
.order_cancel{backgroundColor:#000;width:280px;height:40px;border:2px solid #000;text-align:center;color:#000;padding:20px 0px;font-family:Arial, Helvetica, sans-serif}
</style>
<title>我的订单</title>
</head>
<body>
<!-- modified:20100804_001 -->
<div class="order_list">
<#if pageConfig.items?has_content>
<div class="order_top">
<#assign orderCount=0/>
<#assign totalAmountFloat=0/>
<#if orderStat??>
<#assign orderCount=orderStat.orderCount?default(0)/>
<#assign totalAmountFloat=orderStat.totalAmountFloat?default(0)/>
</#if>
<p>截至${endDate??}，您共有<span>${orderCount}</span>张完成订单,累计消费 ￥<span> 
${totalAmountFloat}</span></p>
</div>
<table width="740" border="0" cellpadding="0" class="online_list_table">
  <tr class="order_tr1">
    <td width="50">订单号</td>
    <td width="270">预订产品</td>
    <td width="55">订单金额</td>
    <td width="50">下单时间</td>
    <td width="50">支付方式</td>
    <td width="50">订单状态</td>
    <td width="80">操作</td>
  </tr>
  <#list pageConfig.items as obj>
  <tr>
    <td><a href="${base}/myspace/order_detail.do?orderId=${obj.orderId?if_exists}" target="_parent">${obj.orderId?if_exists}</a>&nbsp;</td>
    <td><#list obj.ordOrderItemProds as itemObj>
	    <#if itemObj.additional?default("false")=="false">
		    <#if itemObj.wrapPage?default("false")=="true">
		    <a href="/product/${itemObj.productId?if_exists}" target="_parent">
		    </#if>
		    <#if itemObj.productName?length gt 28>
		    &nbsp;${itemObj.productName?substring(0,27)}...&nbsp;<br/>
		    <#else>
		    &nbsp;${itemObj.productName?if_exists}&nbsp;<br/>
		    </#if>
		    <#if itemObj.wrapPage?default("false")=="true"></a></#if>
	    </#if>
    </#list>
    </td>
    <td style="color: #f50;">&nbsp;¥${obj.actualPayYuan?if_exists}&nbsp;</td>
    <td>&nbsp;<#if obj.createTime?exists>${obj.createTime?string("yyyy-MM-dd")}</#if>&nbsp;</td>
    <td><#if obj.paymentTarget?default("")=="TOLVMAMA">在线支付</#if><#if obj.paymentTarget?default("")=="SUPPLIER">景区支付</#if>&nbsp;</td>
    <td>${obj.zhOrderViewStatus?if_exists}&nbsp;</td>
    <td>
		<#if obj.orderStatus=="NORMAL" && obj.paymentStatus?default("")!="PAYED" && obj.paymentTarget?default("")=="TOLVMAMA"><#-- 如果还没支付，并且订单没有被取消 -->
			<#if obj.approveStatus=="VERIFIED"><#--如果订单不需要确认或订单已经审核-->
	    		<a href="${base}/view/view.do?orderId=${obj.orderId?if_exists}" target="_parent">进行支付</a>&nbsp; 
	    	</#if>
	    </#if>
	    <#if obj.orderStatus?default("")=="NORMAL">
	    <#if obj.paymentStatus?default("")=="UNPAY">
	    <a href="#" onclick="pops('${obj.orderId}')">取消</a>&nbsp;
	    </#if>
	    </#if>
	</td>
  </tr>
  </#list>
</table>
<div class="Pages">
	<@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
</div>
<#else>
<br/>
<center>暂无订单记录</center>
</#if><#-- end of pageConfig.items -->
</div><!--order_list end-->


</body>
</html>
