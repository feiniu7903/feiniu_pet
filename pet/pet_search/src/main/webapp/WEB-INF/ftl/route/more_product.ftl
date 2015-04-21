<#if pageConfig.items?size gt 0 && relationSearch?? && relationSearch.get("pageConfig").totalResultSize gt 0 >
<!-- dep-to-dest\\ -->
<!-- 更多产品\\ -->
<div class="ui-box plist-pic-box lv-bd">
    <div class="ui-box-title sl-linear"><span class="link-more fr">
    <#if relationSearch.get("pageConfig").totalResultSize gt 4>
	    <a target="_blank"  href="http://www.lvmama.com/search/${type}/${relationSearch.get("fromDest")}-${relationSearch.get("keyword")}.html">
	    	全部产品（${relationSearch.pageConfig.totalResultSize}条）&gt;&gt;
		</a>
	</#if>
    </span><h4>&ldquo;${relationSearch.get("fromDest")}出发&rdquo;，到&ldquo;${relationSearch.get("keyword")}&rdquo;，有更多产品 </h4></div>
    <div class="ui-box-container clearfix">
        <ul class="plist-pic-list">
        	<#list relationSearch.get("pageConfig").items as product>
	        	<li>
	                <a target="_blank"  href="http://www.lvmama.com${product.productUrl}">
	                	<img src="http://pic.lvmama.com/pics/${product.smallImage}" width="120" height="60">
                	</a>
	                <p><a target="_blank" href="http://www.lvmama.com${product.productUrl}">${product.productName}</a></p>
	                <p><dfn>&yen;<i>${product.sellPrice}</i>起</dfn></p>
	            </li>
        	</#list>
        </ul>
    </div>
</div><!-- //更多产品 -->
<!-- //dep-to-dest -->
</#if>
