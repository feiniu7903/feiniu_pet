<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		document.bocForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
	<form id="bocForm" name="bocForm" method="post" action="https://ebspay.boc.cn/PGWPortal/RecvOrder.do">
		<table>
			<!--01.商户号-->
			<input type="hidden" size="25" id="merchantNo" name="merchantNo" value="${payInfo.merchantNo?if_exists}"><br/>
			<!--02.支付类型-->
			<input type="hidden" size="10" id="payType" name="payType" value="${payInfo.payType?if_exists}"><br/>
			<!--03.商户订单号-->
			<input type="hidden" size="19" id="orderNo" name="orderNo" value="${payInfo.orderNo?if_exists}"><br/>
			<!--04.订单币种-->
			<input type="hidden" size="3" id="curCode" name="curCode" value="${payInfo.curCode?if_exists}"><br/>
			<!--05.订单金额-->
			<input type="hidden" size="13" id="orderAmount" name="orderAmount" value="${payInfo.orderAmount?if_exists}"><br/>
			<!--06.订单时间-->
			<input type="hidden" size="14" id="orderTime" name="orderTime" value="${payInfo.orderTime?if_exists}"><br/>
			<!--07.订单说明-->
			<input type="hidden" size="30" id="orderNote" name="orderNote" value="${payInfo.orderNote?if_exists}"><br/>
			<!--08.商户接收通知URL-->
			<input type="hidden" size="100" id="orderUrl" name="orderUrl" value="${payInfo.orderUrl?if_exists}"><br/>
			<!--09.商户签名数据-->
			<input type="hidden" id="signData"  name="signData" value="${payInfo.signData?if_exists}">
		</table>
	</form>
</#if>	
</body>
</html>