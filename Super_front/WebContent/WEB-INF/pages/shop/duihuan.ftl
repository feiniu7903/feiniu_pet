<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分商城—驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/points/points_mall.css?r=4550" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" > 
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">

</head>

<body>

<!-----------头部文件区域 S-------------->
	<#include "/common/header.ftl">
<div class="main clearfix">
	<div class="mainTop"><p><strong>您当前所处的位置：</strong><span><a href="http://www.lvmama.com/">首页</a></span><span>积分商城</span></p></div>
	
	 <!----------------------------mainLeft-------------------------->
	<#include "/WEB-INF/pages/shop/mainLeft.ftl">
	
    <div class="mainRight">
		<#if product.changeType == "POINT_CHANGE"> 
			<div class="ord-top03">兑换成功</div> 
		</#if> 
		<#if product.changeType == "RAFFLE"> 
			<div class="ord-top03_new">兑换成功</div> 
		</#if>
         <div class="successful">
         	<h3>恭喜，兑换成功</h3>
            <p><a href="http://www.lvmama.com/myspace/shop/orderdetail/${orderId}">查看订单状态</a><span>订单号：${orderId}</span></p>
            <p><a href="http://www.lvmama.com/myspace/account/points.do">查看积分明细</a><span>您目前的剩余积分：<strong><@s.property value="shopUser.point"/></strong>分</span></p>
            <p class="button02"><a href="/myspace/account/points_order.do">查询兑换记录</a><a href="/shop/index.do">返回商城首页</a></p>
          </div>
    </div>
</div>
<!-----------底部文件区域 S -------------->
<#include "/common/orderFooter.ftl">
</body>
	<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"  charset="utf-8"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/points/shop.js?r=4440"></script>
	<script type="text/javascript" src="http://pic.lvmama.com/js/points/points_mall.js?r=2943"></script> 
</html>
