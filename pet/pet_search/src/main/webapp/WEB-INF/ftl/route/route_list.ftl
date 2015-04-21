<div class="slist-item">
	<#list pageConfig.items as product>
    <dl class="search-result-item line-info">
        <dt class="img">
            <a rel="nofollow" target="_blank" href="http://www.lvmama.com${product.productUrl}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${product_index+1}_${product.productId}');">
            <img src="http://pic.lvmama.com/pics/${product.smallImage}" width="168" height="86">
            </a>
            <span class="s-type">
            	<#if product.subProductType  == "GROUP_FOREIGN" 
            			|| product.subProductType  == "GROUP_FOREIGN" 
            			|| product.subProductType  == "GROUP_LONG">
            		跟团游
            	<#elseif product.subProductType  == "GROUP"
            			|| product.subProductType  == "SELFHELP_BUS" >
            		<#if tc.keywordIsFromDest>
            			<#if place??>${searchvo.keyword}</#if>周边/当地跟团游
            		<#else>
            			跟团游
            		</#if>
            	<#elseif product.subProductType =="FREENESS_FOREIGN" || product.subProductType  == "FREENESS_LONG">
            		<#if product.selfPack=='true'>超级自由行 (机票+酒店)<#else>自由行套餐 (机票+酒店)</#if>
            	<#elseif product.subProductType =="FREENESS">
            		<#if product.selfPack=='true'>超级自由行 (景点+酒店)<#else>自由行套餐 (景点+酒店)</#if>
            	</#if>
            </span>
        </dt>
        <#include "/WEB-INF/ftl/route/tags_price.ftl" >
        <dd class="intro">
            <h5>
            <a rel="nofollow" target="_blank"  href="http://www.lvmama.com${product.productUrl}" onclick="coremetricsClick('${searchvo.fromDest}_${searchvo.keyword}_${type}_${pageConfig.currentPage}-${product_index+1}_${product.productId}');">${product.productName}</a>
            <#list product.tagList as t>
            	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'>
            		<span <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
            	</#if>
            </#list>
            </h5>
            <#if product.recommendInfoSecond!='' ><p class="promotions">${product.recommendInfoSecond}</p></#if>
            <p class="route">
            	[<#if product.fromDest=="ANYWHERE">全国<#else>${product.fromDest}</#if>出发 · ${product.visitDay}天
            	<#if product.playNum==1>·单人<#elseif product.playNum==2>·双人<#elseif product.playNum gt 0 >·${product.playNum}</#if>] <br>
				<#if product.playFeatures!="" >游玩特色：<#list product.playFeatures?split(",") as playFeature>${playFeature} </#list><br></#if>
				<#if product.traffic!="" >往返交通：<#list product.traffic?split(",") as traffic>${traffic} </#list><br></#if>
				出发时间：${product.travelTime}
				<#if product.travelTime?exists && product.travelTime?matches("(\\d{2}/\\d{2},)+(\\d{2}/\\d{2})")>
					&nbsp;&nbsp;<a rel="nofollow" class="link-more" target="_blank" href="http://www.lvmama.com${product.getProductUrl()}" >更多</a>
				</#if> 
			</p>
        </dd>
    </dl>
    </#list>
</div>
<#if pageConfig.currentPage == pageConfig.totalPageNum && ((freetourPgcfg?? && freetourPgcfg.items?size gt 0) || (aroundPgcfg?? && aroundPgcfg.items?size gt 0))>
<#include "/WEB-INF/ftl/route/search_plus.ftl"/>
</#if>
<div class="mb10">
<#if pageConfig.totalPageNum gt 1 > 
	<div class="pages rosestyle"> 
		<@s.property escape="false" value="@com.lvmama.search.util.Pagination@pagination(pageConfig)"/>
	</div>
</#if>
</div>
<script>
	function coremetricsClick(val){
		cmCreateElementTag(val,"搜索结果点击");
	}
</script>