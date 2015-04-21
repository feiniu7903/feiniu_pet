<span class="dstnt_info_other">
	<#if tagGroupMap?? && tagGroupMap.get("优惠")?? >
    	<#list tagGroupMap.get("优惠") as t>
    		<span tip-content="${t.description}" class="${t.cssId}  tags201">${t.tagName}</span>
    		<#break>
    	</#list>
    </#if>
	<@s.if test="sellPrice>0"><p class="dstnt_price"><i class="dstnt_my">&yen;</i><label>${sellPriceInteger?if_exists}</label>起</p></@s.if>
	<#if cashRefund?number &gt;= 1>
    	<span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${cashRefund}元</i></span><br/>
    </#if>
	<#if tagGroupMap?? && tagGroupMap.get("抵扣")?? >
    	<#assign dikou_description = '' />
    	<#assign dikou_css= '' />
    	<#list tagGroupMap.get("抵扣") as t>
    			<#assign dikou_description = dikou_description + '<span>' +t.tagName +'</span><br/>' +t.description  />
    			<#if (t_has_next)><#assign dikou_description = dikou_description  + '<br/><br/>' /></#if> 
    			<#assign dikou_css= t.cssId />
    	</#list>
    	<span tip-content="${dikou_description}" class="${dikou_css}">抵扣</span>
    </#if>
	<@s.if test="cmtNum>0"><p class="dstnt_cmt"><a rel="nofollow" class="link_blue"href="http://www.lvmama.com/product/<@s.property value="productId"/>/comment" target="_blank">${cmtNum?if_exists}封</a>点评</p></@s.if>
</span>