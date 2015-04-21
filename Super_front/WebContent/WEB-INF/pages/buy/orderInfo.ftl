<script type="text/javascript">
$(function(){
$("span.selfPack").hover(function(){
	$("#relat_product_list").show();
},
function(){
	$("#relat_product_list").hide();
}
);
});
</script>

<table cellspacing="0" cellpadding="0" class="paymentOrder">
  <tr class="paymentOrderTit"  align="center">
    <td width="120"><span>订单号</span></td>
    <td width="380"><span>产品名称</span></td>
    <td width="120"><span>订单金额</span></td>
    <td>
    	<#if order.orderType == 'HOTEL'>
    		<span>入住日期</span>
    	<#else>
    		<span>游玩日期</span>
    	</#if>
    </td>
    <@s.if test='order.orderType == "HOTEL"'>    	
    </@s.if>
    <@s.elseif test='order.orderType =="TICKET"'>
    </@s.elseif>
    <@s.else>
	    <td><span>游玩人数</span></td>
    </@s.else>
  </tr>
  
  
  <tr  align="center">
    <td><@s.property value="order.orderId"/></td>
    <td width="380">
		<@s.iterator value="mainOrderList">
			<input type="hidden" sellName="sellName" cashRefund=""  marketPrice="${marketPriceYuan}" sellPrice="${sellPriceYuan}" value="1"    />
			<span <@s.if test="order.hasSelfPack()">class="selfPack"</@s.if>><@s.property value="productName"/></span>
			    <em>&nbsp;&nbsp;&nbsp;&nbsp;×
			        <#if subProductType=='SINGLE_ROOM'>
						<@s.property value="quantity/days"/>
					<#else>
						<@s.property value="quantity"/>
					</#if>
				</em><br/>    
			</@s.iterator>
			<@s.if test="order.hasSelfPack()">
			<div id="relat_product_list" style="display:none;position:absolute;background-color:#fff;border:1px solid #ccc;width:359px;padding:10px;text-align:left">
			<span style="font-weight:bold">包含产品:</span><br/>
			</@s.if>
			<@s.iterator value="relativeOrderList"> 
				<input type="hidden" id="param${productId}" name="paramName" minAmt="${minimum}" maxAmt="<#if (maxinum>0) >${maxinum}<#else>${stock}</#if>" textNum="textNum${productId}" people="${adultQuantity+childQuantity}" buyPeopleNum=""/>  	  
        	    <@s.property value="productName"/><em>&nbsp;&nbsp;&nbsp;&nbsp;×<@s.property value="quantity"/></em><br/>   
        	</@s.iterator>
        	<@s.iterator value="additionalOrderList"> 
				<input type="hidden" id="param${productId}" name="paramName" minAmt="${minimum}" maxAmt="<#if (maxinum>0) >${maxinum}<#else>${stock}</#if>" textNum="textNum${productId}" people="${adultQuantity+childQuantity}" buyPeopleNum=""/>  	  
        	    <@s.property value="productName"/><em>&nbsp;&nbsp;&nbsp;&nbsp;×<@s.property value="quantity"/></em><br/>
        	</@s.iterator>
        	<@s.if test="order.hasSelfPack()">
			</div>
			</@s.if>
    </td>
    <td>&yen;${order.oughtPayYuan?if_exists}</td>
    <td>
    	<@s.if test="!order.IsAperiodic()">
	    	<#if order.orderType == 'HOTEL'>
	    		<@s.iterator value="order.ordOrderItemProds">   
	    			<@s.if test="productType=='HOTEL'">           						
	              	<@s.property value="dateRange"/>
	              	</@s.if>              					
	            </@s.iterator>
	    	<#else>
	    		<@s.date   name= "order.visitTime" format= "yyyy年MM月dd日 " />
	    	</#if>
    	</@s.if>
    	<@s.else>
    		未确定
    	</@s.else>
    </td> 
    <#assign personCount = 0 >
    <@s.if test='order.orderType == "HOTEL"'>
    	
    </@s.if>
    <@s.elseif test='order.orderType =="TICKET"'>
    </@s.elseif>
    <@s.else>
    	<@s.iterator value="order.travellerList">
        	<@s.if test='personType=="TRAVELLER"'>
        	   	<#assign personCount = personCount + 1>
        	</@s.if>
     	</@s.iterator>
     	<td>${personCount}人</td>
    </@s.else>
  </tr>
</table>
