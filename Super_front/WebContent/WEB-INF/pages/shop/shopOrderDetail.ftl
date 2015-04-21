<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>兑换详情—驴妈妈旅游网</title>
<link href="http://pic.lvmama.com/styles/points/points_mall.css" rel="stylesheet" />
<link href="http://pic.lvmama.com/min/index.php?g=commonCss" rel="stylesheet"/>

</head>

<body>
	<!-----------头部文件区域 S-------------->
	<#include "/common/header.ftl">
	<div class="main clearfix">
		<div class="mainTop">
			<p>
				<strong>您当前所处的位置：</strong>
				<span><a href="http://www.lvmama.com/">首页</a></span>
				<span><a href="/points">积分商城</a></span>
				<span><#if product??>${product.productName}</#if></span>
			</p>
		</div>
 
		<!-- content begin -->
<div class="content">
	<div class="contentJFTop"><h3 class="tit" style="margin-bottom:20px">订单详情</h3></div>	
	<div class="detail">			
		<div class="areaMyPointDetails">
			<dl>					
				<dd>			
					<table style="line-height:30px;" border="0" width="100%" >
					  <tr>
						<td width="30">&nbsp;</td>
						<td width="75">订单号：</td>
						<td width="400"><@s.property value="shopOrder.orderId"/></td>
						<td width="75">兑换时间：</td>
						<td><@s.date name="shopOrder.createTime" format="yyyy-MM-dd HH:mm"/></td>
					  </tr>
					  <tr>
						<td>&nbsp;</td>
						<td>收件人改名：</td>
						<td><@s.property value="shopOrder.name"/></td>
						<td>收件人手机：</td>
						<td><@s.property value="shopOrder.mobile"/></td>
					  </tr>
					  <tr>
						<td>&nbsp;</td>
						<td>收件人地址：</td>
						<td><@s.property value="shopOrder.address"/></td>
						<td>兑换产品：</td>
						<td><@s.property value="shopOrder.productName"/></td>
					  </tr>
					  <tr>
						<td>&nbsp;</td>
						<td>兑换数量：</td>
						<td><@s.property value="shopOrder.quantity"/></td>
						<td>所用积分：</td>
						<td><@s.property value="shopOrder.actualPay"/></td>
					  </tr>
					  <tr>
						<td>&nbsp;</td>
						<td>订单状态：</td>
						<td><@s.property value="shopOrder.formatOrderStatus"/></td>
						<td>备注：</td>
						<td><@s.property value="shopOrder.remark"/></td>
					  </tr>
					   <tr>
						<td>&nbsp;</td>
						<td>商品信息：</td>
						<td colspan="3">
							<@s.property escape="false" value="shopOrder.productInfo"/>
						</td>
					  </tr>
					</table>
					<br/>
					<a href="http://www.lvmama.com/myspace/account/points_order.do">返回兑换记录</a>
				</dd>
			</dl>			
		</div>			
	</div>
</div>
	  
	<!-----------底部文件区域 S -------------->
	<#include "/common/orderFooter.ftl">

</body>
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
<script src="http://pic.lvmama.com/js/points/shop.js"></script>
<script src="http://pic.lvmama.com/js/points/points_mall.js"></script> 
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
<script type="text/javascript">
$(document).ready(function () {

	$('.clickShow').click(function(){
		<#if shopUser??>
			$('#myForm').submit();
		<#else>
			//$('.login01').show();
			$(UI).ui("login");
		</#if>	
	})
});
</script>
</html>
