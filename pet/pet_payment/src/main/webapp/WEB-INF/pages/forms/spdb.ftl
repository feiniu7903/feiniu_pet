<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	function paySubmit(){
		  document.spdbForm.submit();
	}
</script>
</head>
<body onload="javascript:paySubmit();">
<#if (payInfo)??>
   <form name="spdbForm" id="spdbForm"  action="https://ebank.spdb.com.cn/payment/main"  method="post">
	<p><table>
            <input type = "hidden" name = "transName" value = "${payInfo.transName?if_exists}">
            <input type = "hidden" name = "Plain" value = "${payInfo.plain?if_exists}">
            <input type = "hidden" name = "Signature" value = "${payInfo.bankSignature?if_exists}">
	</table>
  </form>
</#if>	
</body>
</html>