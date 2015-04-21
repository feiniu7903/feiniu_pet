<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>在线支付_驴妈妈旅游网</title>
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css">
<link href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css" rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/super_v2/s2_orderV1_0.css" rel="stylesheet" type="text/css" />
<#include "/common/commonJsIncluedTopNew.ftl">
</head>

<body>
<!--===== 头部文件区域 S ======-->
<div style="text-align:center;">
	<#include "/common/orderHeaderNew.ftl">
</div>
<!--===== 头部文件区域 E ======-->

<div class="s2-order-container">
  <h1><em>预订:</em>
  <@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator>
  </h1>
  <span class="progress-bar-ticket3"></span>
  <div class="tips-area">
  	订单状态：<@s.property value="order.zhOrderViewStatus"/>
  		<@s.if test='order.isCanToPay()'>
		   			  请选择以下支付方式支付订单！
	   </@s.if>
  </div>
	<#include "/WEB-INF/pages/buy/canTopay.ftl"/>
   <#include "/WEB-INF/pages/buy/orderInfo.ftl"/>

<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">

<script type="text/javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<!--===== 底部文件区域 E ======-->
<script>
$(document).ready(function(){
  $(".s2-info-area").shuoming({move_area:".qijiashuoming",layer_content:"shuoming"});
});
</script>
<script charset="utf-8" type="text/javascript" src="http://pic.lvmama.com/min/index.php?g=common"></script>
</body>
</html>
