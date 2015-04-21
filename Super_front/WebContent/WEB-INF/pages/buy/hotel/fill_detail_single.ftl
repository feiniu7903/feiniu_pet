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
              <span class="t-datetime">${buyInfo.leaveTime?if_exists}</span>
              <span class="t-price">${buyInfo.days}晚</span>
              <span class="t-datetime room-type">
              	<a href="http://www.lvmama.com/product/${mainProduct.productId?if_exists}"><@s.property value='@com.lvmama.comm.utils.StringUtil@subStringStr(mainProduct.shortName,11)'/></a>
              </span>
              <del id="marketPrice" value="${marketPrice/100}">&yen;${marketPrice/100}</del>
              <strong id="sellPrice" value="${sellPrice}">&yen;${sellPrice}</strong>
              <em>&yen;${mainProduct.cashRefund/100*buyInfo.days}</em>
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
              &nbsp;<input name="textNum${mainProduct.productId}" id="textNum${mainProduct.productId}" couponPrice="0"  type="text" cashRefund="${mainProduct.cashRefundFloat?if_exists}" sellName="sellName" marketPrice="${mainProduct.marketPriceYuan?if_exists}"  sellPrice="${mainProduct.sellPriceYuan}" value="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}'/>"   onblur="updateOperator('param${mainProduct.productId}','input')" size="3" class="input-add-minus" />&nbsp;
              <button type="button" onClick="updateOperator('param${mainProduct.productId}','add')" class="button-minus">+</button></span></li>
            <@s.iterator value="productsList" status="st">
              <input  type="hidden" name="buyInfo.timeInfo[${st.index}].quantity" value="<@s.property value='buyInfo.buyNum.product_${mainProduct.productId}'/>" />
              <input type="hidden" name="buyInfo.timeInfo[${st.index}].visitTime" value="<@s.property value="key"/>"/>
              <input type="hidden" name="buyInfo.timeInfo[${st.index}].productId" value="${mainProduct.productId}"/>
            </@s.iterator>
        </ul>
    </div>
