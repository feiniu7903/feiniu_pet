<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		  document.chinapayPreForm.submit();
	}
</script>
</head>
<body  onload="javascript:paySubmit();">
<#if (payInfo)??>
   <form name="chinapayPreForm" id="chinapayPreForm"  action="https://unionpaysecure.com/api/Pay.action"  method="post">
	<p><table>
				<input type="hidden" name="acqCode" id="acqCode" value="${payInfo.acqCode?if_exists}">
				<input type="hidden" name="backEndUrl" id="backEndUrl" value="${payInfo.backEndUrl?if_exists}">
				<input type="hidden" name="charset" id="charset" value="${payInfo.charset?if_exists}">
				<input type="hidden" name="commodityDiscount" id="commodityDiscount" value="${payInfo.commodityDiscount?if_exists}">
				<input type="hidden" name="commodityName" id="commodityName" value="${payInfo.commodityName?if_exists}">
				<input type="hidden" name="commodityQuantity" id="commodityQuantity" value="${payInfo.commodityQuantity?if_exists}">
				<input type="hidden" name="commodityUnitPrice" id="commodityUnitPrice" value="${payInfo.commodityUnitPrice?if_exists}">
				<input type="hidden" name="commodityUrl" id="commodityUrl" value="${payInfo.commodityUrl?if_exists}">
				<input type="hidden" name="customerIp" id="customerIp" value="${payInfo.customerIp?if_exists}">
				<input type="hidden" name="customerName" id="customerName" value="${payInfo.customerName?if_exists}">
				<input type="hidden" name="defaultBankNumber" id="defaultBankNumber" value="${payInfo.defaultBankNumber?if_exists}">
				<input type="hidden" name="defaultPayType" id="defaultPayType" value="${payInfo.defaultPayType?if_exists}">
				<input type="hidden" name="frontEndUrl" id="frontEndUrl" value="${payInfo.frontEndUrl?if_exists}">
				<input type="hidden" name="merAbbr" id="merAbbr" value="${payInfo.merAbbr?if_exists}">
				<input type="hidden" name="merCode" id="merCode" value="${payInfo.merCode?if_exists}">
				<input type="hidden" name="merId" id="merId" value="${payInfo.merId?if_exists}">
				<input type="hidden" name="merReserved" id="merReserved" value="${payInfo.merReserved?if_exists}">
				<input type="hidden" name="orderAmount" id="orderAmount" value="${payInfo.orderAmount?if_exists}">
				<input type="hidden" name="orderCurrency" id="orderCurrency" value="${payInfo.orderCurrency?if_exists}">
				<input type="hidden" name="orderNumber" id="orderNumber" value="${payInfo.orderNumber?if_exists}">
				<input type="hidden" name="orderTime" id="orderTime" value="${payInfo.orderTime?if_exists}">
				<input type="hidden" name="origQid" id="origQid" value="${payInfo.origQid?if_exists}">
				<input type="hidden" name="signMethod" id="signMethod" value="${payInfo.signMethod?if_exists}">
				<input type="hidden" name="signature" id="signature" value="${payInfo.merSignMsg?if_exists}">
				<input type="hidden" name="transTimeout" id="transTimeout" value="${payInfo.transTimeout?if_exists}">
				<input type="hidden" name="transType" id="transType" value="${payInfo.transType?if_exists}">
				<input type="hidden" name="transferFee" id="transferFee" value="${payInfo.transferFee?if_exists}">
				<input type="hidden" name="version" id="version" value="${payInfo.version?if_exists}">

  </form>
</#if>	
</body>
</html>