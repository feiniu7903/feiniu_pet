<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		  document.commForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
   <form name="commForm" id="commForm"  action="https://pay.95559.com.cn/netpay/MerPayB2C"  method="post">
	<p><table>
	        <input type = "hidden" name = "interfaceVersion" value = "${payInfo.interfaceVersion?if_exists}">
            <input type = "hidden" name = "merID" value = "${payInfo.merID?if_exists}">
            <input type = "hidden" name = "orderid" value = "${payInfo.orderId?if_exists}">
            <input type = "hidden" name = "orderDate" value = "${payInfo.orderDate?if_exists}">
            <input type = "hidden" name = "orderTime" value = "${payInfo.orderTime?if_exists}">
            <input type = "hidden" name = "tranType" value = "${payInfo.tranType?if_exists}">
            <input type = "hidden" name = "amount" value = "${payInfo.amount?if_exists}">
            <input type = "hidden" name = "curType" value = "${payInfo.curType?if_exists}">
            <input type = "hidden" name = "orderContent" value = "${payInfo.orderContent?if_exists}">
            <input type = "hidden" name = "orderMono" value = "${payInfo.orderMono?if_exists}">
            <input type = "hidden" name = "phdFlag" value = "${payInfo.phdFlag?if_exists}">
            <input type = "hidden" name = "notifyType" value = "${payInfo.notifyType?if_exists}">
            <input type = "hidden" name = "merURL" value = "${payInfo.merURL?if_exists}">
            <input type = "hidden" name = "goodsURL" value = "${payInfo.goodsURL?if_exists}">
            <input type = "hidden" name = "jumpSeconds" value = "${payInfo.jumpSeconds?if_exists}">
            <input type = "hidden" name = "payBatchNo" value = "${payInfo.payBatchNo?if_exists}">
            <input type = "hidden" name = "proxyMerName" value = "${payInfo.proxyMerName?if_exists}">
            <input type = "hidden" name = "proxyMerType" value = "${payInfo.proxyMerType?if_exists}">
            <input type = "hidden" name = "proxyMerCredentials" value = "${payInfo.proxyMerCredentials?if_exists}">
            <input type = "hidden" name = "netType" value = "${payInfo.netType?if_exists}">
            <input type = "hidden" name = "merSignMsg" value = "${payInfo.merSignMsg?if_exists}">
	</table>
  </form>
</#if>	
</body>
</html>