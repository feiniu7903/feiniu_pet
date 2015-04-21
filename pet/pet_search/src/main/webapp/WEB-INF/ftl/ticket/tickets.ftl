<#list pageConfig.items as item>
<#if item.get("place")?? >
<#assign item_place = item.get("place") />
<div class="slist-item">
     <dl class="search-result-item tickets-info">
        <dt class="img">
			<a target="_blank" href="http://ticket.lvmama.com/scenic-${item_place.id}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_index+1}_${item_place.id}','','','','');">
            <img src="http://pic.lvmama.com${item_place.smallImage}" width="168" height="86"></a>
        </dt>
        <dd class="info">
	            <#if (item_place.productsPrice ==0) >
	            	<a target="_blank" href="http://ticket.lvmama.com/scenic-${item_place.id}">详情</a>
	            <#else>
	               <dfn class="s-price">&yen; 
	            	<i>  ${item_place.productsPrice}</i>起
	               </dfn>
	            </#if>
            <#if item_place.cmtNum gt 0>
            <a rel="nofollow" class="comment-num" href="http://ticket.lvmama.com/scenic-${item_place.id}#comments" target="_blank">${item_place.cmtNum}条点评</a>
            <span class="sm_star"><i style="width:${(item_place.avgScore*100/5)?string("#.##")}%"></i></span>
            </#if>
            <#if item_place.weekSales?exists && item_place.weekSales gt 0><span class="booking">近一周${item_place.weekSales}人预订</span></#if>
        </dd>
        <dd class="intro">
            <h5>
            	<small>[${item_place.province}•${item_place.city}]</small>
					<a target="_blank" href="http://ticket.lvmama.com/scenic-${item_place.id}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_index+1}_${item_place.id}','','','','');
				<#list item_place.tagList as t>
            		<span  <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
            	</#list>
            </h5>
            <#if item_place.placeActivity?exists && item_place.placeActivity?length gt 0>
            	<p class="promotions"><i class="pro-type"></i>
            		${item_place.placeActivity?trim}
            	</p>
            </#if>
            <div class="route">
	            <p>
	            	<span>景点地址：</span>${item_place.address}<br>
	            	<span>景点主题：</span><#list item_place.destSubjects?split(",") as ds><b>${ds}</b></#list>
			   	</p>
	            <p class="intro-text">
	            	<a class="view-more">更多介绍<i class="arrow"></i></a>
	            	<span>${item_place.summary}</span>
	            </p>  
            </div>
        </dd>
    </dl>
    <div class="item-service">
        <#if item.get("product")?? >
        <dl class="title">
            <dt class="product-name">门票类型</dt>
            <dd class="market-price">市场价</dd>
            <dd class="lv-price">驴妈妈价</dd>
            <dd class="pay-type">支付方式</dd>
            <dd class="remark"></dd>
        </dl>
        <#assign item_product = item.get("product") />
        <#assign tilte_show = true />
        <#list item_product.get("SINGLE") as prod>
	        <dl class="plist-item">
	        	<#if prod_index ==0 >
	        		<#assign tilte_show = false />
	        		<dt class="plist-title">门票</dt>
	        	</#if>
	            <#include "/WEB-INF/ftl/ticket/ticket_content.ftl" >
	        </dl><!-- //div.plist-item -->
        </#list>
        <#list item_product.get("WHOLE") as prod>
	        <dl class="plist-item">
	        	<#if prod_index ==0 && tilte_show>
	        		<dt class="plist-title">门票</dt>
	        	</#if>
	            <#include "/WEB-INF/ftl/ticket/ticket_content.ftl" >
	        </dl><!-- //div.plist-item -->
        </#list>
        <#list item_product.get("SUIT") as prod>
	        <dl class="plist-item">
	        	<#if prod_index ==0 >
	        		<dt class="plist-title">套票</dt>
	        	</#if>
	            <#include "/WEB-INF/ftl/ticket/ticket_content.ftl" >
	        </dl><!-- //div.plist-item -->
        </#list>
        <#list item_product.get("UNION") as prod>
	        <dl class="plist-item">
	        	<#if prod_index ==0 >
	        		<dt class="plist-title">联票</dt>
	        	</#if>
	            <#include "/WEB-INF/ftl/ticket/ticket_content.ftl" >
	        </dl><!-- //div.plist-item -->
        </#list>
        <a class="more-list view-more">更多门票产品<i class="arrow"></i></a>
        </#if>
        <#if  item.get("route")??  && item.get("route")?size gt 0 >
        <div class="package-service">
            <div class="package-title"><span class="link-more fr"><a href="http://www.lvmama.com/search/freetour/${fromDest}-${item_place.name}.html" target="_blank">更多套餐&gt;&gt;</a></span>
                <h6>"${item_place.name}"+"酒店" 套餐推荐</h6>
            </div>
            <ul class="package-list">
            	<#list item.get("route") as route>
                <li class="package-item">
                <dfn>
                	&yen;<i>${route.sellPrice}</i>起
                	<#if route.cashRefund &gt;= 1>
	            		<span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${route.cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${route.cashRefund}元</i></span>
		            </#if>
                </dfn>
                    <p>
	                    <a target="_blank" href="http://www.lvmama.com${route.productUrl}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${item_index+1}_${route.productId}','','','','');">
	                    	<#if route.productName?length gt 50 >${route.productName?substring(0,50)}...<#else>${route.productName}</#if>
	                    </a>
                    </p>
                </li>
            	</#list>
            </ul>
        </div>
        </#if>
    </div>
</div><!-- //div.slist-item -->
</#if>
</#list>
    

<#if pageConfig.totalPageNum gt 1 > 
	<div class="pages rosestyle"> 
		<@s.property escape="false" value="@com.lvmama.search.util.Pagination@pagination(pageConfig)"/>
	</div>
</#if>
<form>
</form>
<script>
	function coremetricsClick(val,productId,productName,price,subType){
		cmCreateElementTag(val,"搜索结果点击");
		if(productId!=''){
			cmCreateShopAction5Tag(productId, productName, "1", price, subType);
			cmDisplayShops();
		}
	}
</script>
    
