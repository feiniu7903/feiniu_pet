
<div class="search-aside">
    <!-- 本期团购\\ -->
    <@s.if test="tuanGouList.size>0"> 
    <div class="aside-box lv-bd side-stuan clearfix">
        <h3 class="side-title"><strong>本期团购</strong><small>进行中</small></h3>
        <ul class="stuan-list">
           <#list tuanGouList as product>
           <#if (product_index lt 3)>
            <li class="stuan-item">
                <p class="img"><a target="_blank"  href="http://www.lvmama.com${product.productUrl}" ><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="http://pic.lvmama.com/pics/${product.smallImage}" width="178" height="89"></a>
                <@s.if test="${product.validTime}<172800000"><span class="tran-bg"></span><span class="stuan-time">${product.validTime}</span></@s.if><@s.else></@s.else>
                </p>
                <p class="tuan-title"><a rel="nofollow" target="_blank"  href="http://www.lvmama.com${product.productUrl}" >${product.productName}</a></p>
                <p><a rel="nofollow" target="_blank"  href="http://www.lvmama.com${product.productUrl}" class="abtn abtn-block">立即抢购</a>
                <del>&yen;<i>${product.marketPrice}</i></del>
                <dfn>&yen;<i>${product.sellPrice}</i></dfn>
                </p>
            </li>
            </#if>
          </#list>
        </ul>
        <p><a rel="nofollow" class="link-more" target="_blank"  href="http://www.lvmama.com/tuangou">更多团购产品&gt;&gt;</a></p>
    </div>
    </@s.if>
    <!-- //本期团购 -->
    <#if place??>
    <!-- 旅游推荐\\ -->
    <div class="aside-box lv-bd travel-index">
    	
        <h3 class="side-title">
        	<a target="_blank"  href="<#if place.stage=='2'>http://ticket.lvmama.com/scenic-${place.id}<#else>http://www.lvmama.com/dest/${place.pinYinUrl}</#if>">${place.name}旅游首页</a>
        </h3>
        
        <#if place.smallImage?? && place.smallImage!="">
        <a target="_blank"  href="<#if place.stage=='2'>http://ticket.lvmama.com/scenic-${place.id}<#else>http://www.lvmama.com/dest/${place.pinYinUrl}</#if>">
        	<img src="http://pic.lvmama.com${place.smallImage}" width="178" height="89">
        </a>
        </#if>
        <p>
        <#if place.subSummary!="" >
         	${place.subSummary}... 
        <#else>
        	 ${place.summary!""}
        </#if>
        <a rel="nofollow" class="link-more" target="_blank" href="<#if place.stage=='2'>http://ticket.lvmama.com/scenic-${place.id}<#else>http://www.lvmama.com/dest/${place.pinYinUrl}</#if>">更多&gt;&gt;</a>
        </p>
    </div><!-- //旅游推荐 -->
    
    <!-- 点评\\ -->
    <div class="aside-box lv-bd side-reviews">
    	
    	<h3 class="side-title">
    		<a target="_blank"  href="<#if place.stage=='2'>http://ticket.lvmama.com/scenic-${place.id}#comments<#else>http://www.lvmama.com/comment/${place.id}-1</#if>">${place.name}点评</a>
    	</h3>
        
        <div id="cmt_data_div" class="score-reviews" placeId="${place.id}">
            <p class="loading"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/loadingGIF46px.gif" width="46" height="46"></p>
        </div>
    </div><!-- //点评 -->
   	<#if  place.stage == 1>
    <!-- 攻略\\ -->
    <div id="guide_data_div" class="aside-box lv-bd side-guide" placeId="${place.id}">
    	<h3 class="side-title">
    		<a id="guide_title_a" target="_blank"  href="#">${place.name}旅游攻略</a>
    	</h3>
        <p id="guide_data_div_loading" class="loading"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/loadingGIF46px.gif" width="46" height="46"></p>
    </div><!-- //攻略 -->
	</#if>
	</#if>
	<#if relatedKeywords?? && relatedKeywords?size gt 0 >
	 <div class="aside-box lv-bd side-keyword">
    	<h3 class="side-title">您是不是要找</h3>
    	<ul class="hor">
        <#list relatedKeywords?keys as key>
        	<li><a target="_blank" title="${key}" href = "${relatedKeywords.get(key).replace("{fromDest}","${fromDest}")}">${key}</a></li>
        </#list>
        </ul>
    </div><!-- //推荐词 -->
    </#if>
    
    <!-- //推荐-->
    <#if recommendList?? && recommendList?size gt 0 >
    <div class="aside-box lv-bd side-stuan clearfix">
    <h3 class="side-title"><strong>为您推荐的产品</strong><small></small></h3>
        <ul class="stuan-list">
           <#list recommendList as product>
           <#if (product_index lt 3)>
            <li class="stuan-item">
                <p class="img"><a target="_blank"  href="http://www.lvmama.com${product.productUrl}" ><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="http://pic.lvmama.com/pics/${product.smallImage}" width="178" height="89"></a>
                <@s.if test="${product.validTime}<172800000"><span class="tran-bg"></span><span class="stuan-time">${product.validTime}</span></@s.if><@s.else></@s.else>
                </p>
                <p class="tuan-title"><a rel="nofollow" target="_blank"  href="http://www.lvmama.com${product.productUrl}" >${product.productName}</a></p>
                </p>
            </li>
            </#if>
          </#list>
        </ul>
    </div>
     </#if>
    
</div><!-- // div .search-aside-->