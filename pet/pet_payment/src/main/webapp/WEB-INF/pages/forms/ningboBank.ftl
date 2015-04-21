<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<script type="text/javascript">
	function paySubmit(){
		document.getElementById("submit_pay").submit();
	}
</script>
</head>
<body onload="paySubmit();">
<#if (payInfo)??>																  
	<form name="submit_form_ningboBank" method="post" id="submit_pay" action="https://mybank.nbcb.com.cn/payment/NCTR01Comm.do">
		<input type="hidden" name="return_url"	value="${payInfo.return_url?if_exists}"/>
		<input type="hidden" name="notify_url" value="${payInfo.notify_url?if_exists}">
		<input type="hidden" name="seller_email" value="${payInfo.seller_email?if_exists}"/>
		<input type="hidden" name="subject"		value="${payInfo.subject?if_exists}"/>
		<input type="hidden" name="payment_type" value="${payInfo.payment_type?if_exists}"/>
		<input type="hidden" name="paymethod"	value="${payInfo.paymethod?if_exists}"/>
		<input type="hidden" name="it_b_pay" value="${payInfo.it_b_pay?if_exists}"/>
		<input type="hidden" name="out_trade_no" value="${payInfo.out_trade_no?if_exists}"/>
		<input type="hidden" name="total_fee"	value="${payInfo.total_fee?if_exists}"/>
		<input type="hidden" name="defaultbank"	value="${payInfo.defaultbank?if_exists}"/>
		<input type="hidden" name="reqCustomerId"	value="${payInfo.reqCustomerId?if_exists}"/>
		<input type="hidden" name="reqTime"	value="${payInfo.reqTime?if_exists}"/>
		<input type="hidden" name="reqFlowNo"	value="${payInfo.reqFlowNo?if_exists}"/>
		<input type="hidden" name="buyer_email"	value="${payInfo.buyer_email?if_exists}"/>
		<input type="hidden" name="seller_id"	value="${payInfo.seller_id?if_exists}"/>
		<input type="hidden" name="buyer_id"	value="${payInfo.buyer_id?if_exists}"/>
		<input type="hidden" name="seller_account_name"	value="${payInfo.seller_account_name?if_exists}"/>
		<input type="hidden" name="buyer_account_name"	value="${payInfo.buyer_account_name?if_exists}"/>
		<input type="hidden" name="price"	value="${payInfo.price?if_exists}"/>
		<input type="hidden" name="quantity"	value="${payInfo.quantity?if_exists}"/>
		<input type="hidden" name="body"	value="${payInfo.body?if_exists}"/>
		<input type="hidden" name="show_url"	value="${payInfo.show_url?if_exists}"/>
		<input type="hidden" name="royalty_type"	value="${payInfo.royalty_type?if_exists}"/>
		<input type="hidden" name="anti_phishing_key"	value="${payInfo.anti_phishing_key?if_exists}"/>
		<input type="hidden" name="exter_invoke_ip"	value="${payInfo.exter_invoke_ip?if_exists}"/>
		<input type="hidden" name="extra_common_param"	value="${payInfo.extra_common_param?if_exists}"/>
		<input type="hidden" name="sign_msg"	value="${payInfo.sign_msg?if_exists}"/>
	</form>
</#if>
</body>
</html>