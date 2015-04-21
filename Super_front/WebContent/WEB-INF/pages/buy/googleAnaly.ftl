<script type="text/javascript">
	window["_BFD"] = window["_BFD"] || {};
	_BFD.BFD_INFO = {
		"city":"",
		"order_id" : "${order.orderId?if_exists}",
		"order_items" : [<@s.iterator value="order.ordOrderItemProds" id="ooip" status="st"><@s.if test="#st.last">["${ooip.productId}",${ooip.priceYuan},${ooip.quantity}]</@s.if><@s.else>["${ooip.productId}",${ooip.priceYuan},${ooip.quantity}],</@s.else></@s.iterator>],   //同预订页
		"total" : "${order.oughtPayYuan?if_exists}",   //用户实际支付的价格
		"user_id" : "${order.userId?if_exists}", //网站当前用户id，如果未登录就为0或空字符串
		"page_type" : "order" //当前页面全称，请勿修改
	};
	var _zzOrderId = "${order.orderId?if_exists}";
	var _zzOrderTotal = "${order.oughtPayYuan?if_exists}";
	var _zzOrderDetails =eval('[<@s.iterator value="order.ordOrderItemProds" id="ooip" status="st"><@s.if test="#st.last">"${ooip.productId},${ooip.quantity},${ooip.priceYuan}"</@s.if><@s.else>"${ooip.productId},${ooip.quantity},${ooip.priceYuan}",</@s.else></@s.iterator>]');
	var length = document.getElementsByTagName('script').length;
	var _zzsiteid="702693135909740545";
	var _zzid = "702693135909740544";
	var zz = document.createElement('script');
	zz.type = 'text/javascript';
	zz.async = true;
	zz.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'trace.zhiziyun.com/api/order.js';
	var s = document.getElementsByTagName('script')[length-1];
	s.parentNode.insertBefore(zz, s);
</script>


