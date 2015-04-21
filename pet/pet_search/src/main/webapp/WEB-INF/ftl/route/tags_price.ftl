<dd class="info">
	<#if product.tagGroupMap.get("优惠")?? >
    	<#list product.tagGroupMap.get("优惠") as t>
    		<span tip-content="${t.description}" class="${t.cssId} tags201">${t.tagName}</span>
    		<#break>
    	</#list>
    </#if>
    <dfn class="s-price">&yen;<i>${product.sellPrice}</i>起</dfn>
    <#if product.cashRefund &gt;= 1>
    	<span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${product.cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${product.cashRefund}元</i></span><br/>
    </#if>
    <#if product.tagGroupMap.get("抵扣")?? >
    	<#assign dikou_description = '' />
    	<#assign dikou_css= '' />
    	<#list product.tagGroupMap.get("抵扣") as t>
    			<#assign dikou_description = dikou_description + '<span>' +t.tagName +'</span><br/>' +t.description  />
    			<#if (t_has_next)><#assign dikou_description = dikou_description  + '<br/><br/>' /></#if> 
    			<#assign dikou_css= t.cssId />
    	</#list>
    	<span tip-content="${dikou_description}" class="${dikou_css}">抵扣</span>
    </#if>
    <#if  product.cmtNum gt 0 >
    	<a rel="nofollow" href="http://www.lvmama.com/product/${product.productId}/comment" target="_blank" class="comment-num" hidefocus="false">${product.cmtNum}条点评</a>
    </#if>
</dd>