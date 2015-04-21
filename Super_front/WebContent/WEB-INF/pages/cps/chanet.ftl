<#list orderChannelInfoList as orderChannelInfo>
	<#if orderChannelInfo.ordOrder??>
		<#list orderChannelInfo.ordOrder.ordOrderItemProds as ordOrderItemProd>
${orderChannelInfo.createDate?string("yyyy-MM-dd HH:mm:SS")}	${orderChannelInfo.arg1}	${orderChannelInfo.orderId}	GOODS1	${ordOrderItemProd.quantity}	${ordOrderItemProd.priceYuan}	${ordOrderItemProd.productName}	9	<#if orderChannelInfo.ordOrder.orderStatus == "NORMAL" && orderChannelInfo.paymentStatus != "PAYED">0</#if><#if orderChannelInfo.ordOrder.orderStatus == "NORMAL" && orderChannelInfo.paymentStatus == "PAYED">1</#if><#if orderChannelInfo.ordOrder.orderStatus == "CANCEL">5</#if><#if orderChannelInfo.ordOrder.orderStatus == "FINISHED">6</#if>		
		</#list>	
	</#if>
</#list>   
