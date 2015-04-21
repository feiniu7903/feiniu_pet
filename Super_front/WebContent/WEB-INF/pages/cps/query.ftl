<#list orderChannelInfoList as orderChannelInfo>
订单来源渠道：${orderChannelInfo.channel}
订单参数一：${orderChannelInfo.arg1}
订单参数二：${orderChannelInfo.arg2}
订单ID：${orderChannelInfo.orderId}
订单创建时间：${orderChannelInfo.createDate?string("yyyy-MM-dd HH:mm:ss")}
<#if null!=orderChannelInfo.ordOrder>
订单类型：${orderChannelInfo.ordOrder.orderType}
订单主产品名称：${orderChannelInfo.ordOrder.mainProduct.productName}
订单价格：${orderChannelInfo.ordOrder.oughtPayYuan}
订单产品数据：${orderChannelInfo.ordOrder.mainProduct.quantity}
订单支付状态：${orderChannelInfo.ordOrder.paymentStatus}
</#if>
</#list>   
