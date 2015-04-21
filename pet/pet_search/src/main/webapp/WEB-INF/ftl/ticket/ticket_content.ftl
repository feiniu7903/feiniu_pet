<dt class="product-name">
 	<a target="_blank" href="http://www.lvmama.com${prod.productUrl}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_index+1}_${prod.productId}','${prod.productId?if_exists}', '<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prod.productName)"/>','${prod.sellPrice?if_exists}', '${prod.subProductType?if_exists}');">${prod.productName}</a>
    <#if prod.cashRefund &gt;= 1>
    	<span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${prod.cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${prod.cashRefund}元</i></span>
    </#if>
    <#if prod.tagGroupMap.get("抵扣")?? >
    	<#assign dikou_description = '' />
    	<#assign dikou_css= '' />
    	<#list prod.tagGroupMap.get("抵扣") as t>
    			<#assign dikou_description = dikou_description + '<span>' +t.tagName +'</span><br/>' +t.description  />
    			<#if (t_has_next)><#assign dikou_description = dikou_description  + '<br/><br/>' /></#if> 
    			<#assign dikou_css= t.cssId />
    	</#list>
    	<span tip-content="${dikou_description}" class="${dikou_css}">抵扣</span>
    </#if>
    <#assign youhui_description = '<b>可以享受以下优惠</b><br/>' />
	<#assign youhui_css= '' />
    <#if prod.tagGroupMap.get("优惠")?? >
    	<#list prod.tagGroupMap.get("优惠") as t>
    		<#assign youhui_description = youhui_description  +'<span>'+t.tagName  +'</span>'/>
    		<#if (t_has_next)><#assign youhui_description = youhui_description  +' | '  /></#if> 
    		<#assign youhui_css= t.cssId />
    	</#list>
    	<span tip-content="${youhui_description}" class="${youhui_css}">优惠</span>
    </#if>
    <#list prod.tagList as t>
    	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'><span <#if t.description!="" >tip-content="${t.description}"</#if>class="${t.cssId}">${t.tagName}</span></#if>
    </#list>
</dt>
<dd class="market-price"><del>&yen;${prod.marketPrice}</del></dd>
<dd class="lv-price"><dfn>&yen;<i>${prod.sellPrice}</i></dfn></dd>
<dd class="pay-type"><#if prod.payMethod =="ONLINE">在线支付<#else>景区支付</#if></dd>
<dd class="remark"><a class="btn booking" target="_blank" href="http://www.lvmama.com${prod.productUrl}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${prod_index+1}_${prod.productId}','${prod.productId?if_exists}', '<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prod.productName)"/>','${prod.sellPrice?if_exists}', '${prod.subProductType?if_exists}');">预 订</a></dd>
<!--<dd class="remark"><a class="btn booking" target="_blank" href="javascript:void(0);" date_productId="prod.productId">预 订</a></dd>-->