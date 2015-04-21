<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		document.getElementById("submit_pay").submit();
	}
</script>
</head>
<body onload="paySubmit();">
<#if (payInfo)??>
	<form name="submit_form_icbcInstalment" method="post" id="submit_pay" action="https://B2C.icbc.com.cn/servlet/ICBCINBSEBusinessServlet">
		<input type="hidden" name="interfaceName" value="${payInfo.interfaceName?if_exists}"/>
		<input type="hidden" name="interfaceVersion" value="${payInfo.interfaceVersion?if_exists}"/>
		<input type="hidden" name="tranData" value='${payInfo.tranData?if_exists}'/>
		<input type="hidden" name="merSignMsg" value="${payInfo.merSignMsg?if_exists}"/>
		<input type="hidden" name="merCert" value="${payInfo.merCert?if_exists}"/>
	</form>
</#if>
</body>
</html>
