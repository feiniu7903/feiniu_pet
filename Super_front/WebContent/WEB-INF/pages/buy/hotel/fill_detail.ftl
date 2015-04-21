<div class="s2-info-area">
    	<h3>预订产品</h3>
        <ul>
            <li class="data-head">
	          <span class="t-datetime">入住日期</span>
	          <span class="t-datetime">离店日期</span>
	          <span class="t-price">入住晚数</span>
	          <span class="t-datetime room-type">入住房型</span>
	          <span class="t-price">市场价</span>
	          <span class="t-memberprice">现售价</span>
	          <span class="t-bonus">奖金</span>
	          <span class="t-total">数量<span>（间/晚）</span></span>
	        </li>
        	<li>
        	<input name="orderCouponPrice" type="hidden" id="orderCouponPrice" value="0"/>
        	<#if mainProduct.physical=='true'>
	            	<script>
	            		isPhysical = true;
	            	</script>
            	</#if>
              <span class="t-datetime" value="${mainProduct.productId?if_exists}" id="id">${buyInfo.visitTime?if_exists}
              <input name="buyTime" type="hidden" value="${buyInfo.visitTime?if_exists}" id="buyTime"/>
              </span>
              <span class="t-datetime t-datetime-height">${buyInfo.leaveTime?if_exists}</span>
              <span class="t-price t-datetime-height">${buyInfo.days}晚</span>
              <span class="t-datetime room-type">
              	<a href="http://www.lvmama.com/product/${mainProduct.productId?if_exists}"><@s.property value='@com.lvmama.comm.utils.StringUtil@subStringStr(mainProduct.shortName,11)'/></a>
              </span>
              <del id="marketPrice" value="${mainProduct.marketPriceYuan?if_exists}">&yen;${mainProduct.marketPriceYuan?if_exists}</del>
              <strong id="sellPrice" value="${mainProduct.sellPriceYuan?if_exists}">&yen;${mainProduct.sellPriceYuan?if_exists}</strong>
              <em>&yen;${mainProduct.cashRefund/100}</em>
				<input type="hidden" name="buyInfo.productId" value="${mainProduct.productId?if_exists}"/>
				<@s.if test='mainProduct.payToLvmama=="true"'>
					<input type="hidden" name="buyInfo.paymentTarget" value="TOLVMAMA"/>
				</@s.if>
				<@s.elseif test='mainProduct.payToSupplier=="true"'>
					<input type="hidden" name="buyInfo.paymentTarget" value="TOSUPPLIER"/>
				</@s.elseif>
              <span class="add-minus">
              <input type="hidden" productId="${mainProduct.productId}" id="param${mainProduct.productId}" name="paramName" minAmt="${mainProduct.minimum}" maxAmt="<#if (mainProduct.maxinum>0) >${mainProduct.maxinum}<#else>${mainProduct.stock}</#if>" textNum="textNum${mainProduct.productId}" people="${mainProduct.adultQuantity+mainProduct.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}*${mainProduct.adultQuantity+mainProduct.childQuantity}'/>"/>
              <button type="button"  onClick="updateOperator('param${mainProduct.productId}','miuns')" class="button-add">-</button>
              &nbsp;<input id="textNum${mainProduct.productId}" name="buyInfo.buyNum.product_${mainProduct.productId}" couponPrice="0"  type="text" cashRefund="${mainProduct.cashRefundFloat?if_exists}" sellName="sellName" marketPrice="${mainProduct.marketPriceYuan?if_exists}"  sellPrice="${mainProduct.sellPriceYuan}" value="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}'/>"   onblur="updateOperator('param${mainProduct.productId}','input')" size="3" class="input-add-minus" />&nbsp;
              <button type="button" onClick="updateOperator('param${mainProduct.productId}','add')" class="button-minus">+</button></span></li>
            <@s.iterator value="relatedProductList" var="product">
                 <#if !product.additional>
                 	<@s.if test="buyInfo.buyNum.product_${product.productId}!=null" >
		        	<li><span class="t-datetime">${buyInfo.visitTime?if_exists}</span>
		        	<span class="t-datetime">${buyInfo.leaveTime?if_exists}</span>
              		<span class="t-price">${buyInfo.days}晚</span>
		              <span class="t-title"><@s.property escape="false" value="@com.lvmama.comm.utils.StringUtil@subStringStr(productName,15)" />(<@s.property value="shortName" />)</span>
		              <del>&yen;${product.marketPriceYuan?if_exists}</del>
		              <strong>&yen;${product.sellPriceYuan?if_exists}</strong>
		              <em>&yen;${product.cashRefundFloat?if_exists}</em>
		             <span class="add-minus">
		             <input type="hidden" productId="${product.productId}" id="param${product.productId}" name="paramName" minAmt="${product.minimum}" maxAmt="<#if (product.maxinum>0) >${product.maxinum}<#else>${product.stock}</#if>" textNum="textNum${product.productId}" people="${product.adultQuantity+product.childQuantity}" buyPeopleNum="<@s.property value='buyInfo.buyNum.product_${product.productId}*${product.adultQuantity+product.childQuantity}'/>"/>
		             <button type="button" onClick="updateOperator('param${product.productId}','miuns')" class="button-add">-</button>
		             &nbsp;<input  type="text" size="3" name="buyInfo.buyNum.product_${product.productId}"  couponPrice="0" onblur="updateOperator('param${product.productId}','input')" sellName="sellName" cashRefund="${product.cashRefundFloat?if_exists}"  marketPrice="${product.marketPriceYuan}" sellPrice="${product.sellPriceYuan}" <@s.if test="buyInfo.buyNum.product_${product.productId}==null" >value="0"</@s.if><@s.else>value="<@s.property value='buyInfo.buyNum.product_${product.productId}'/>"</@s.else> id="textNum${product.productId}"   class="input-add-minus" />&nbsp;
		             <button type="button" onClick="updateOperator('param${product.productId}','add')"  class="button-minus">+</button></span></li>
		            </@s.if>
            	</#if>
            	<#if product.physical=='true'>
	            	<script>
	            		isPhysical = true;
	            	</script>
            	</#if>
          </@s.iterator>
        </ul>
    </div>
