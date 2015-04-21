<#list orderChannelInfoList as orderChannelInfo>
	<#if orderChannelInfo.ordOrder??>
		<#list orderChannelInfo.ordOrder.ordOrderItemProds as ordOrderItemProd>
2	${orderChannelInfo.createDate?string("HHmmSS")}	${orderChannelInfo.arg1}	${orderChannelInfo.orderId}	${ordOrderItemProd.productId}	${orderChannelInfo.ordOrder.userId}	${ordOrderItemProd.quantity}	${ordOrderItemProd.priceYuan}	GOODS	<#if orderChannelInfo.ordOrder.orderStatus == "NORMAL" && orderChannelInfo.paymentStatus != "PAYED">100</#if><#if orderChannelInfo.ordOrder.orderStatus == "NORMAL" && orderChannelInfo.paymentStatus == "PAYED">200</#if><#if orderChannelInfo.ordOrder.orderStatus == "CANCEL">300</#if><#if orderChannelInfo.ordOrder.orderStatus == "FINISHED">200</#if>		
		</#list>	
	</#if>
</#list>   
