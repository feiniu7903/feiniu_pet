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
	<form name="submit_form_tenpay" method="post" id="submit_pay"
		action="https://gw.tenpay.com/gateway/pay.htm">
		<input type="hidden" name="sign"	value="${payInfo.sign?if_exists}"/>
		<input type="hidden" name="sign_type"	value="${payInfo.sign_type?if_exists}"/>
		<input type="hidden" name="service_version"	value="${payInfo.service_version?if_exists}"/>
		<input type="hidden" name="input_charset"	value="${payInfo.input_charset?if_exists}"/>
		<input type="hidden" name="sign_key_index"	value="${payInfo.sign_key_index?if_exists}"/>
		<input type="hidden" name="bank_type"	value="${payInfo.bank_type?if_exists}"/>
		<input type="hidden" name="body"	value="${payInfo.body?if_exists}"/>
		<input type="hidden" name="attach"	value="${payInfo.attach?if_exists}"/>
		<input type="hidden" name="return_url"	value="${payInfo.return_url?if_exists}"/>
		<input type="hidden" name="notify_url" value="${payInfo.notify_url?if_exists}">
		<input type="hidden" name="buyer_id" value="${payInfo.buyer_id?if_exists}">
		<input type="hidden" name="partner"	value="${payInfo.partner?if_exists}"/>
		<input type="hidden" name="out_trade_no" value="${payInfo.out_trade_no?if_exists}"/>
		<input type="hidden" name="total_fee"	value="${payInfo.total_fee?if_exists}"/>
		<input type="hidden" name="fee_type"	value="${payInfo.fee_type?if_exists}"/>
		<input type="hidden" name="spbill_create_ip"	value="${payInfo.spbill_create_ip?if_exists}"/>
		<input type="hidden" name="time_start"	value="${payInfo.time_start?if_exists}"/>
		<input type="hidden" name="time_expire"	value="${payInfo.time_expire?if_exists}"/>
		<input type="hidden" name="transport_fee"	value="${payInfo.transport_fee?if_exists}"/>
		<input type="hidden" name="product_fee"	value="${payInfo.product_fee?if_exists}"/>
		<input type="hidden" name="goods_tag"	value="${payInfo.goods_tag?if_exists}"/>
	</form>
</#if>
</body>
</html>
