<!-- Google Code for &#35746;&#36141;&#25104;&#21151; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 1043765710;
var google_conversion_language = "zh_TW";
var google_conversion_format = "2";
var google_conversion_color = "ffffff";
var google_conversion_label = "s0uvCPzf5QEQzrPa8QM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/1043765710/?label=s0uvCPzf5QEQzrPa8QM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<!-- Google Code for &#35746;&#36141;&#25104;&#21151; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 968086145;
var google_conversion_language = "zh_CN";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "hk3vCO-zlAMQgaXPzQM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/968086145/?label=hk3vCO-zlAMQgaXPzQM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<!-- Google Code for &#35746;&#36141;&#25104;&#21151; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 963331259;
var google_conversion_language = "zh_CN";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "_qugCK2x_QIQu4mtywM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/963331259/?label=_qugCK2x_QIQu4mtywM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<!-- Google Code for &#35746;&#36141;&#25104;&#21151; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 968567822;
var google_conversion_language = "zh_CN";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "iwpeCOKd2wIQjtjszQM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/968567822/?label=iwpeCOKd2wIQjtjszQM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>

<!-- Google Code for &#35746;&#36141;&#25104;&#21151; Conversion Page -->
<script type="text/javascript">
/* <![CDATA[ */
var google_conversion_id = 962608731;
var google_conversion_language = "zh_CN";
var google_conversion_format = "3";
var google_conversion_color = "ffffff";
var google_conversion_label = "wBX1CN2B-QIQ2_yAywM";
var google_conversion_value = 0;
/* ]]> */
</script>
<script type="text/javascript" src="http://www.googleadservices.com/pagead/conversion.js">
</script>
<noscript>
<div style="display:inline;">
<img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/962608731/?label=wBX1CN2B-QIQ2_yAywM&amp;guid=ON&amp;script=0"/>
</div>
</noscript>


<script type="text/javascript">
	function _gsCallback() {
        if (window._gsTracker) {
            _gsTracker.addOrder("${order.orderId?if_exists}", ${order.oughtPayYuan?if_exists}, "");
           <@s.iterator value="order.ordOrderItemProds" >
            _gsTracker.addProduct("<@s.property value="orderId"/>", "<@s.property value="@com.lvmama.util.StringUtil@replaceQuoteInJS(productName)" />", "<@s.property value="productId"/>", <@s.property value="priceYuan"/>, <@s.property value="quantity"/>, "");
           </@s.iterator>
            _gsTracker.trackECom();
        }
    }
</script>


<!-- 睿广的订单数据提交 -->
<script type="text/javascript">
	var rg_user_id = "${order.userId?if_exists}";     // 当前用户的user_id，即其注册名的编号，string类型。
	var rg_item_id = [];  // 当前商品的sku_id，如果没有sku_id,用其productid,唯一编号即可，string类型
	var rg_item_price = [];  // 当前商品的价格（标价）
	var rg_item_cnt = []; // 商品的个数
	<@s.iterator value="order.ordOrderItemProds" id="ooip" status="st">
		rg_item_id[${st.index}] = "${ooip.productId}";
		rg_item_price[${st.index}] = ${ooip.priceYuan};
		rg_item_cnt[${st.index}] = ${ooip.quantity};
	</@s.iterator>
	var rg_ord_id ="${order.orderId?if_exists}"; // 订单的订单号
</script>
<script type="text/javascript" src="http://cdn1.uguide.cn/10010001/lvmama_order.js"></script>


