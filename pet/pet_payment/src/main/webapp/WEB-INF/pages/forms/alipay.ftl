<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	function paySubmit(){
		document.getElementById("submit_pay").submit();
	}
</script>
</head>
<body onload="paySubmit();">
<#if (payInfo)??>
	<form name="submit_form_alipay" method="post" id="submit_pay"
		action="https://mapi.alipay.com/gateway.do?_input_charset=utf-8">
		<input type="hidden" name="notify_url" value="${payInfo.notify_url?if_exists}">
		<input type="hidden" name="out_trade_no" value="${payInfo.out_trade_no?if_exists}"/>
		<input type="hidden" name="partner"	value="${payInfo.partner?if_exists}"/>
		<input type="hidden" name="payment_type" value="${payInfo.payment_type?if_exists}"/>
		<input type="hidden" name="seller_email" value="${payInfo.seller_email?if_exists}"/>
		<input type="hidden" name="service"	value="${payInfo.service?if_exists}"/>
		<input type="hidden" name="sign"	value="${payInfo.sign?if_exists}"/>
		<input type="hidden" name="sign_type"	value="${payInfo.sign_type?if_exists}"/>
		<input type="hidden" name="subject"		value="${payInfo.subject?if_exists}"/>
		<input type="hidden" name="total_fee"	value="${payInfo.total_fee?if_exists}"/>
		<input type="hidden" name="return_url"	value="${payInfo.return_url?if_exists}"/>
		<input type="hidden" name="body"	value="${payInfo.body?if_exists}"/>
		<input type="hidden" name="paymethod"	value="${payInfo.paymethod?if_exists}"/>
		<input type="hidden" name="defaultbank"	value="${payInfo.defaultbank?if_exists}"/>
		<input type="hidden" name="it_b_pay" value="${payInfo.it_b_pay?if_exists}"/>
		<input type="hidden" name="token" value="${payInfo.token!""}"/>
		<input type="hidden" name="anti_phishing_key" value="${payInfo.anti_phishing_key!""}"/>
		<input type="hidden" name="exter_invoke_ip" value="${payInfo.exter_invoke_ip!""}"/>
		<input type="hidden" name="extra_common_param" value="${payInfo.extra_common_param?if_exists}"/>
		<input type="hidden" name="credit_card_pay" value="${payInfo.credit_card_pay?if_exists}"/>
		<input type="hidden" name="credit_card_default_display" value="${payInfo.credit_card_default_display?if_exists}"/>
		<#if payInfo.royalty_parameters ??>
		<input type="hidden" name="royalty_type"	value="${payInfo.royalty_type?if_exists}"/>
		<input type="hidden" name="royalty_parameters"	value="${payInfo.royalty_parameters?if_exists}"/>
		</#if>
		<#if payInfo.qr_pay_mode ??>
		<input type="hidden" name="qr_pay_mode"	value="${payInfo.qr_pay_mode?if_exists}"/>
		</#if>
	</form>
</#if>
</body>
</html>
