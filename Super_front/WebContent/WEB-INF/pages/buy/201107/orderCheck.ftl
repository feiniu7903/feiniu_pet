<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>核对订单_驴妈妈旅游网</title>
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<link type="text/css" rel="stylesheet" href="/style/cards01.css">
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<#include "/common/coremetricsHead.ftl">
</head>
<body>
<!--===== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
<!--===== 头部文件区域 E ======-->

<!--===== 导航条 S ======-->
	<@navigation productType="${order.mainProduct.productType?if_exists}" 
			subProductType="${order.mainProduct.subProductType?if_exists}" 
			resourceConfirm="${order.resourceConfirm}"
			eContract = "${order.isNeedEContract()?string('true','false')}" 
			stepView="checkView"  
			payToSupplier="${(order.paymentTarget=='TOSUPPLIER')?string('true','false')}" 
			fillTravellerInfo="${(travellerInfoOptions!=null)?string('true','false')}"/>
<!--===== 导航条 E ======-->
		<!--===== 页面信用贴士S ======-->
		<#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		<#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		<@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
		<!--===== 页面信用贴士E ======-->
<div class="ord_tipbox  tips-info">
            <div class="atr_pay_hint">
            <em>订单提交成功！</em>
                <#if order.isJinjiangOrder || order.isShHolidayOrder>
                	<script type="text/javascript">
					var isReload=true;
					setInterval(getOrderBookResult,10000);
					function getOrderBookResult(){
						if(isReload){
							$.ajax({
								type: "POST",
								url: "/ajax/supplierBookingResult.do",
								async: false,
								data: {orderId: <@s.property value="order.orderId"/> },
								dataType: "json",
								success: function(response) {
								   	if (response.success == true) {
								   		isReload=false;				    				
										window.location.href="/view/view.do?orderId=<@s.property value="order.orderId"/>";
									}else if (response.success == false) {
										$(".atr_pay_hint").find("p").html("<span style='color:red'>"+response.message+"</span>");	
									}				
								}
							});
						}
						
					}
					</script>
                	<p>您的订单正在提交确认中，需要大约1分钟时间,订单确认后页面会自动跳转至支付页面</p>
                <#else>
	                <p>我们将尽快确认资源，以手机短信的形式通知到您，请耐心等待。</p>
		      </#if>
		<ul class="atr_pay_listtxt">
		      <@s.if test="@com.lvmama.common.utils.OnLinePreSalePowerUtil@getOnLinePreSalePower(order)">
		       <li>在资源确认前，您也可使用<a  class="boldfont inline" href="${base}/view/view.do?orderId=${order.orderId}">银行卡预授权</a>，我们会优先为您确认资源，一旦资源确认，您无需再次登录网站进行了支付操作。</li>
		      </@s.if>
		      <li>您可以在 <a href="/myspace/order.do">我的订单</a> 中查看您的订单状态。</li>
		      <li>&nbsp;</li>
		      <li  class="align-center f-14"><img src="http://pic.lvmama.com/img/icons/mobile.gif" style="margin-right: 9px"><i>下载驴妈妈手机客户端，随时查看订单。</i></li>
		      <li class="align-center"><a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/iPhone.jpg" style="vertical-align: middle; margin-right: 3px"></a> <a href="http://shouji.lvmama.com"><img src="http://pic.lvmama.com/img/icons/Android.jpg"></a></li>
		      <div class="btn-content2"></div>
		</ul>
            </div>

</div>
	    
<!--订单信息 S-->
<#include "/WEB-INF/pages/buy/orderInfo.ftl"/>
<!--订单信息 E-->

<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
	<script>
		<#if order.mainProduct.productType == "HOTEL">
			cmCreatePageviewTag("核对订单-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-DJJD-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
		<#else>
			cmCreatePageviewTag("核对订单-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>（订单提交成功）", "Q0001", null, null);
		</#if>
    	
    </script>
</body>
</html>
