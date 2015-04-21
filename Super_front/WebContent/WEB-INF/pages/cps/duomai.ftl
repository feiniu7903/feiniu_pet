<#list orderChannelInfoList as orderChannelInfo>
${orderChannelInfo.arg1}|${orderChannelInfo.orderId}|${orderChannelInfo.createDate?string("yyyy-MM-dd HH:mm:SS")}|${orderChannelInfo.ordOrder.orderType}|${orderChannelInfo.ordOrder.mainProduct.productName}|${orderChannelInfo.ordOrder.oughtPayYuan}|${orderChannelInfo.ordOrder.mainProduct.quantity}|${orderChannelInfo.ordOrder.paymentStatus}
</#list>   
