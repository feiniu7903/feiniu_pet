<div id="coupon-tooltip" class="coupon-box coupon-unused">
<table class="lv-table coupon-table">
        <colgroup>
        <col class="lvcol-1">
        <col class="lvcol-2">
        <col class="lvcol-3">
        <col class="lvcol-4">
        <col class="lvcol-5">
        <col class="lvcol-6">
        <col class="lvcol-8">
        </colgroup>
        	<thead>
			    <tr class="thead">
			        <th class="coupon_num">优惠券号码</th>
			        <th class="price">优惠额度</th>
			        <th class="en-date">生效日期</th>
                    <th class="ex-date">失效日期</th>
			        <th >优惠券名称</th>
			        <th class="use-range">使用范围</th>
			        <th class="remark">操作</th>
			    </tr>
              </thead>
              
              <tbody>
			   <@s.iterator id="item" value="pageConfig.items" status="index">
				  <tr>
				    <td class="couponCode coupon_num"><@s.property value="markCouponCode.couponCode"/></td>
				    <td style="width: 120px;">${markCoupon.favorTypeDescription}</td>
				    
				    <@s.if test="markCouponCode.beginTime!=null">
				    <td class="en-date">${markCouponCode.beginTime?string('yyyy-MM-dd')}</td>
				    </@s.if>
				    <@s.else>
				    <td class="en-date"></td>
				    </@s.else>
				    
				    <@s.if test="markCouponCode.endTime!=null">
				     <td class="ex-date">${markCouponCode.endTime?string('yyyy-MM-dd')}</td>
				    </@s.if>
				    <@s.else>
				     <td class="ex-date"></td>
				    </@s.else>
				   
				    <td class="use-range">${markCoupon.couponName}</td>
				    <td class="use-range">
				    <@s.if test="productTypes!=null">
				      <@s.iterator id="type" value="productTypes" status="typeIndex">
				      <@s.if test="%{#typeIndex.index != 0}" >
				      ,
				       </@s.if>
				       ${productName}
				      </@s.iterator>  
				    </@s.if></td>
				    <td class="remark" style="width: 67px;">
				    <a class="ui-btn ui-btn2 copyCouponCode" href="javascript:void(0);"><i>复制使用</i></a>
				    <a class="view_detail" href="javascript:void(0);">查看详情</a>
				    <div class="tooltip coupon-tooltip ie6png" style="position: absolute; top: 695.4px; left: 948.1px; opacity: 0; display: none;">
					  <p class="coupon_description">${item.markCoupon.description}</p>
				    </div>
				    </td>
				  </tr>
				</@s.iterator>  
</tbody></table>