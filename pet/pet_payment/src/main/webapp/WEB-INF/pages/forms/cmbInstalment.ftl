<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		document.cmbInstalmentForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
	<form id="cmbInstalmentForm" name="cmbInstalmentForm" action="https://netpay.cmbchina.com/cdpay/cdpay.dll?cdpay" method="post">
		<table>
			<input type=hidden name= "Request" value="${payInfo.requestXML?if_exists}">
		</table>
		<!-- <input type="button" value="支付" onclick="paySubmit();"/> -->
	</form>
</#if>	
</body>
</html>