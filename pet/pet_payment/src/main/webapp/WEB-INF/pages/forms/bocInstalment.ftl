<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		document.bocInstalmentForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
	<form id="bocInstalmentForm" name="bocInstalmentForm" action="https://jf365.boc.cn/BOCCASH/cardPay!addOrderInfo" method="post">
		<table>
			<input type=hidden name="orig" value="${payInfo.orig?if_exists}">
			<input type=hidden name= "sign" value="${payInfo.sign?if_exists}">
			<input type=hidden name= "returnurl" value="${payInfo.returnurl?if_exists}">
			<input type=hidden name= "NOTIFYURL" value="${payInfo.notifyurl?if_exists}">
			<input type=hidden name="transName" value="${payInfo.transName?if_exists}" >
		</table>
	</form>
</#if>	
</body>
</html>