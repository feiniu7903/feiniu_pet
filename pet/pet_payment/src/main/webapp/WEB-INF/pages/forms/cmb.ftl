<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		document.cmbForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
   <form name="cmbForm" id="cmbForm" action="https://netpay.cmbchina.com/netpayment/BaseHttp.dll?PrePayC2"  method="post">
	<p><table>
	<input type=hidden name="BranchID" value="${payInfo.branchID?if_exists}" >
    <input type=hidden name="CoNo" value="${payInfo.coNo?if_exists}" >
	<input type=hidden name="BillNo" value="${payInfo.billNO?if_exists}" >
	<input type=hidden name="Amount" value="${payInfo.amount?if_exists}" >
	<input type=hidden name="Date" value="${payInfo.payDate?if_exists}" >
	<input type=hidden name="MerchantUrl" value="${payInfo.callBackUrl?if_exists}" >
	<input type=hidden name="MerchantPara" value="${payInfo.merchantPara?if_exists}" >
	</table>
  </form>
</#if>	
</body>
</html>