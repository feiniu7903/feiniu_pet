<#list orderChannelInfoList as orderChannelInfo><#if orderChannelInfo.ordOrder??> ${orderChannelInfo.arg1}||${orderChannelInfo.createDate?string("yyyy-MM-dd HH:mm:SS")}||${orderChannelInfo.orderId}||${orderChannelInfo.ordOrder.oughtPayYuan}</#if>	
</#list>   
