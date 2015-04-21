<dt class="product-name">
    <a target="_blank" href="http://www.lvmama.com${prod.productUrl}">${prod.productName}</a>
    <!--<#if prod.cashRefund &gt;= 1>
    	<span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${prod.cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${prod.cashRefund}元</i></span>
    </#if> -->
    <!-- <#if prod.tagGroupMap["抵扣"]?? >
    	<#assign dikou_description = '' />
    	<#assign dikou_css= '' />
    	<#list prod.tagGroupMap["抵扣"] as t>
    			<#assign dikou_description = dikou_description + '<span>' +t.tagName +'</span><br/>' +t.description  />
    			<#if (t_has_next)><#assign dikou_description = dikou_description  + '<br/><br/>' /></#if> 
    			<#assign dikou_css= t.cssId />
    	</#list>
    	<span tip-content="${dikou_description}" class="${dikou_css}">抵扣</span>
    </#if>
    <#assign youhui_description = '<b>可以享受以下优惠</b><br/>' />
	<#assign youhui_css= '' />
    <#if prod.tagGroupMap["优惠"]?? >
    	<#list prod.tagGroupMap["优惠"] as t>
    		<#assign youhui_description = youhui_description  +'<span>'+t.tagName  +'</span>'/>
    		<#if (t_has_next)><#assign youhui_description = youhui_description  +' | '  /></#if> 
    		<#assign youhui_css= t.cssId />
    	</#list>
    	<span tip-content="${youhui_description}" class="${youhui_css}">优惠</span>
    </#if>-->
    <#list prod.tagList as t>
    	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'><span <#if t.description!="" >tip-content="${t.description}"</#if>class="${t.cssId}">${t.tagName}</span></#if>
    </#list>
</dt>
<#if prod.tntPrice == "登录有优惠">
<dd class="lv-price fx-price"><dfn><i>${prod.tntPrice}</i></dfn></dd>
<#else>
<dd class="lv-price fx-price"><del><i>&yen;${prod.tntPrice}</i></del></dd>
</#if>
<dd class="market-price"><del>&yen;${prod.tntMarketPrice}</del></dd>
<dd class="lv-price"><dfn>&yen;<i>${prod.tntSellPrice}</i></dfn></dd>
<dd class="pay-type"><#if prod.payMethod =="ONLINE">在线支付<#else>景区支付</#if></dd>
<#if prod.valid == "Y">
<dd class="remark">
<#if prod.isAperiodic == "false">
<a class="btn booking" id="${prod.productId}" productId="${prod.productId}" branchId="${prod.prodBranchId}" href="javascript:" >预 订</a>
<#else>
<a class="btn" id="${prod.productId}" productId="${prod.productId}" branchId="${prod.prodBranchId}" href="javascript:aperiodBooking('${prod.productId}','${prod.prodBranchId}')" >预 订</a>
</#if>
<#else>
<dd class="remark"><a class="btn btn_gray" href="javascript:void(0);">预 订</a></dd>
</#if>
<!--<dd class="remark"><a class="btn booking" target="_blank" href="javascript:void(0);" date_productId="prod.productId">预 订</a></dd>-->