<#include "/WEB-INF/pages/buy/201107/navigation.ftl">
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="" name="description">
<meta content="" name="keywords">
<title>下单完成_驴妈妈旅游网</title>
<link rel="stylesheet" type="text/css" href="http://pic.lvmama.com/styles/new_v/header-air.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/s2_globalV1_0.css">
<link type="text/css" rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/orderV2.css">
<script src="http://pic.lvmama.com/js/jquery142.js" type="text/javascript"></script>
<style type="text/css">
	.order-title{border-top:3px solid #CC0066;}
</style>
<#include "/common/coremetricsHead.ftl">
</head>

<body>
<!-- 弹出层 -->
<div class="t-bg-tm"></div>
<div class="t-div-zf">
	<div class="t-div-cont">
		<p class="t-titleandclose"><span class="t-fuanfatome">邮箱信息</span><a class="t-btn-close"><img src="http://pic.lvmama.com/img/icons/close.gif" alt="关闭" /></a></p>
		<p><span class="inputNameSend"><em>*</em>签约邮箱：</span><label><input type="text" class="txtSendMail" /></label><em class="wrongInfo">请输入有效的邮箱地址</em></p>
		<p><span class="inputNameSend">&nbsp;</span><input type="submit" class="sendEmailInfo" value="确认发送" /></p>
	</div>
	<span class="t-img-zf-down-span"></span>
</div>
<!-- /弹出层 -->

	<!--===== 头部文件区域 S ======-->
		<div style="text-align:center;">
			<#include "/common/orderHeaderNew.ftl">
		</div>
	<!--===== 头部文件区域 E ======-->

	<!--===== 导航条 S ======-->
	    
	<!--===== 导航条 E ======-->

	<!--===== 页面信用贴士S ======-->
		    <#include "/WEB-INF/pages/buy/201107/creditTip.ftl">
		    <#assign local_productNames><@s.iterator value="mainOrderList"><@s.property value="productName"/> </@s.iterator></#assign>
		    <@creditTip productName="${local_productNames}" productType="${order.mainProduct.productType?if_exists}"/>
	<!--===== 页面信用贴士E ======-->
<div class="tips-info01">
<@s.if test="order.isPartPay()">
			<h3 class="tit01">恭喜，您已付款成功！</h3>	
  			<dl class="red14_txt01">
            	<dt>付款信息:</dt>
                <dd>你的订单<span> ${order.orderId}</span>已经支付了<span>${order.actualPayFloat}</span>元，仍需支付<strong>${order.oughtPayYuanFloat}</strong>元</dd>
                <dd>请您尽快支付剩余款项，以确保您的订单能得到最快安排</dd>
            </dl>
            <p class="botton01"><a href="/view/view.do?orderId=${order.orderId?if_exists}">继续付款</a></p>
            </@s.if>           
</div> 


<!--===== 底部文件区域 S ======-->
<#include "/common/orderFooter.ftl">
	<#include "/WEB-INF/pages/buy/googleAnaly.ftl"/>
<!--===== 底部文件区域 E ======-->
<script language="javascript" src="http://pic.lvmama.com/js/super_v2/order_func.js"></script>
<script language="javascript">
function reSendEContract(){	
	$.ajax({
		type: "POST",
		url: "/ajax/sendOrderEContract.do",
		async: false,
		data: {orderId: <@s.property value="order.orderId"/> },
		dataType: "json",
		success: function(response) {
		   	if (response.success == true) {				    				
				alert("合同已重新发送！");						
			}else{			
			    alert("合同重新发送失败！");				
			}				
		}
	});
}
</script>
<#include "/common/ga.ftl"/>
<script>
        	cmCreatePageviewTag("支付成功页-"+"<@s.property value="order.mainProduct.productType"  escape="false"/>-"+"<@s.property value="order.mainProduct.subProductType"  escape="false"/>", "Q0001", null, null);
    </script>
</body>
</html>
