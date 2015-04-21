<div id="coupon-tooltip" class="coupon-box coupon-unused">
<table class="lv-table coupon-table">
  <colgroup>
	  <col class="lvcol-1">
	  <col class="lvcol-2">
	  <col class="lvcol-3">
  </colgroup>
  <thead>
    <tr class="thead">
        <th class="coupon_num">优惠券号码</th>
        <th >优惠券名称</th>
        <th class="price">金额(元)</th>
    </tr>
  </thead>
  <tbody>
  <#list pageConfig.items as item>
	  <tr>
	    <td class="coupon_num">${item.markCouponCode.couponCode}</td>
	    <td>${item.markCoupon.couponName}</td>
	    <td class="price">${item.amountYuan}</td>
	  </tr>
	</#list>
	</tbody>
</table>