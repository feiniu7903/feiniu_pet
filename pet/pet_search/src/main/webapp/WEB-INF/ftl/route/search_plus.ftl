<div class="slist-item slist-itemplus">
	<#if freetourPgcfg?? && freetourPgcfg.items?size gt 0>
	<#assign productFirst = freetourPgcfg.items.get(0) />
    <dl class="search-result-item line-info">
        <dt class="img">
            <a target="_blank" href="http://www.lvmama.com${productFirst.productUrl}">
            <img src="http://pic.lvmama.com/pics/${productFirst.smallImage}" width="168" height="86">
            </a>
            <span class="s-type"><#if productFirst.selfPack=='true'>超级自由行 (景点+酒店)<#else>自由行套餐 (景点+酒店)</#if></span>
        </dt>
        <dd class="info">
        </dd>
        <dd class="short-intro">
            <h5><a  href="http://www.lvmama.com/search/freetour/${searchvo.fromDest}-${searchvo.keyword}.html"  target="_blank" >自由行 (景点+酒店)<small> 查看全部产品${freetourPgcfg.totalResultSize}条&gt;&gt;</small></a></h5>
            <ul class="short-splist">
            	<#list freetourPgcfg.items as item>
	                <li>
	                <dfn>&yen;<i>${item.sellPrice}</i>起</dfn>
	                <a target="_blank" href="http://www.lvmama.com${item.productUrl}">
	                <#if item.productName?length gt 33 >${item.productName?substring(0,30)}<b>...</b><#else>${item.productName}</#if>
	                </a>
	                </li>
                </#list>
            </ul>
        </dd>
    </dl>
    </#if>
    <#if aroundPgcfg?? && aroundPgcfg.items?size gt 0>
	<#assign productFirst = aroundPgcfg.items.get(0) />
    <dl class="search-result-item line-info">
        <dt class="img">
            <a target="_blank" href="http://www.lvmama.com${productFirst.productUrl}">
            <img src="http://pic.lvmama.com/pics/${productFirst.smallImage}" width="168" height="86">
            </a>
            <span class="s-type">周边/当地跟团游</span>
        </dt>
        <dd class="info">
        </dd>
        <dd class="short-intro">
            <h5><a href="http://www.lvmama.com/search/around/${searchvo.fromDest}-${searchvo.keyword}.html" target="_blank" >周边/当地跟团游<small> 查看全部产品${aroundPgcfg.totalResultSize}条&gt;&gt;</small></a></h5>
            <ul class="short-splist">
            	<#list aroundPgcfg.items as item>
	                <li>
	                <dfn>&yen;<i>${item.sellPrice}</i>起</dfn>
	                <a target="_blank" href="http://www.lvmama.com${item.productUrl}">
	                <#if item.productName?length gt 33 >${item.productName?substring(0,30)}<b>...</b><#else>${item.productName}</#if>
	                </a>
	                </li>
                </#list>
            </ul>
        </dd>
    </dl>
    </#if>
</div>