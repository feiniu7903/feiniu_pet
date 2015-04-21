<!DOCtype html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
请用POS机输入以下代码，以继续支付：
<p/>
<#if (payInfo)??>
	${payInfo.paymentTradeNo?if_exists}
</#if>
</body>
</html>