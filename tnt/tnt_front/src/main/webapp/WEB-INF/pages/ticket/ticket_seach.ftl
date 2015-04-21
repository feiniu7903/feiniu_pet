<!-- 搜素筛选\\ -->
<div class="search-filter">
	<div class="filter-info clearfix">
		<span class="result-info"> 共找到 <i> ${pageConfig.totalResultSize} </i> 条结果。
		</span>
		<dl class="your-choices" id="your-choices">
			<#if searchvo.subject?? || searchvo.city?? || searchvo.placeActivity??>
				<dt>您已选择：</dt>
				<#if searchvo.city??>
                	<dd><a href="#" searchCondition="search" searchvo="${citiesSelectedUrl}"><h6>包含地区：</h6>${searchvo.city}<span class="icon-close"></span></a></dd>
                </#if>
                <#if searchvo.subject??>
                	<dd><a rel="nofollow" href="#" searchCondition="search" searchvo="${subjectsSelectedUrl}"><h6>主题：</h6>${searchvo.subject}<span class="icon-close"></span></a></dd>
                </#if>
                <#if searchvo.tag??>
                	<dd><a href="#" searchCondition="search" searchvo="${tagSelectedUrl}"><h6>标签：</h6>${searchvo.tag}<span class="icon-close"></span></a></dd>
                </#if>
                <#if searchvo.placeActivity??>
                	<dd><a rel="nofollow" href="#" searchCondition="search" searchvo="${placeActivitiesSelectedUrl}"><h6>景点活动：</h6>${searchvo.placeActivity}<span class="icon-close"></span></a></dd>
                </#if>
                <dd class="tags-empty"><a rel="nofollow" href="#" searchCondition="search" searchvo="">清空全部</a></dd>
			</#if>
		</dl>
	</div>
	<#assign cities_show = cities?? && cities?size gt 0 && !searchvo.city?? />
    <#assign subjects_show = subjects?? && subjects?size gt 0 && !searchvo.subject?? />
    <#assign placeActivities_show = placeActivities?? && placeActivities?size gt 0 && !searchvo.placeActivity?? />
	<#if cities_show || subjects_show || placeActivities_show>
		<ul id="tags-list" class="filter-tags">
			<#if cities_show>
				<li><a href="javascript:void(0);" class="view-more"> 更多 <i
					class="arrow"> </i>
				</a> <span> 包含地区： </span>
				<p>
					<a class="s-tag selected" href="#" searchCondition="search">全部</a>
	                	<#list cities?keys as key>
	                		<a class="s-tag" href="#" searchvo="${cities[key][1]}" searchCondition="search">${key}(${cities[key][0]})</a>
	                	</#list>
				</p></li>
			</#if>		
			<#if subjects_show>
	            <li>
	            	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                <span>主　　题：</span>
	                <p>
	                    <a rel="nofollow" class="s-tag selected" href="#" searchvo="" searchCondition="search">全部</a>
	                    <#list subjects?keys as key>
	                		<a rel="nofollow" class="s-tag" href="#" searchvo="${subjects[key][1]}" searchCondition="search">${key}(${subjects[key][0]})</a>
	                	</#list>
	            	</p>
				</li>
			</#if>
			<#if placeActivities_show>
	            <li>
	            	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                <span>景点活动：</span>
	                <p>
	                    <a rel="nofollow" class="s-tag selected" href="#" searchvo="" searchCondition="search">全部</a>
	                    <#list placeActivities?keys as key>
	                		<a rel="nofollow" class="s-tag" href="#" searchvo="${placeActivities[key][1]}" searchCondition="search">${key}(${placeActivities[key][0]})</a>
	                	</#list>
	            	</p>
				</li>
			</#if>
	    </ul>
    </#if>
</div>
<!-- //搜素筛选 -->
<!-- 门票搜索列表\\ -->
<div id="list" class="search-result-box search-tickets-list">
    <!-- 筛选排序\\  .filter-order filter-commont filter-commont-desc -->
    <div class="filter-order">
    	<#if pageConfig.totalPageNum gt 1 > 
    		<div class="pageoper">
    			${pageinations}
			</div>
		</#if>
		<#if !sort??>
        	<span  class="lv-order current-order">驴妈妈推荐</span>
        	<!-- <a rel="nofollow" href="#" class="comment-order" searchvo="${commNum}" searchCondition="search">点评数<i class="ico-order"></i></a>
       	 	<a rel="nofollow" href="#" class="feedback-order" searchvo="${goodCommNum}" searchCondition="search">好评率<i class="ico-order"></i></a> -->
        	<a rel="nofollow" href="#" class="price-order" searchvo="${priceDown}" searchCondition="search">价格<i class="ico-order"></i></a>
        	<a rel="nofollow" href="#" class="price-order order-asc" searchvo="${priceUp}" searchCondition="search">价格<i class="ico-order"></i></a>
        <#elseif sort =="6">
        	<a href="" class="lv-order">驴妈妈推荐</a>
        	<!-- <span class="comment-order current-order">点评数<i class="ico-order"></i></span>
       	 	<a rel="nofollow" href="#" class="feedback-order" searchvo="${goodCommNum}" searchCondition="search">好评率<i class="ico-order"></i></a> -->
        	<a rel="nofollow" href="#" class="price-order" searchvo="${priceDown}" searchCondition="search">价格<i class="ico-order"></i></a>
        	<a rel="nofollow" href="#" class="price-order order-asc" searchvo="${priceUp}" searchCondition="search">价格<i class="ico-order"></i></a>
      	<#elseif sort =="4">
        	<a href="" class="lv-order">驴妈妈推荐</a>
    		<!--  <a rel="nofollow" href="#" class="comment-order" searchvo="${commNum}" searchCondition="search">点评数<i class="ico-order"></i></a>
    		<span class="feedback-order current-order">好评率<i class="ico-order"></i></span> -->
        	<a rel="nofollow" href="#" class="price-order" searchvo="${priceDown}" searchCondition="search">价格<i class="ico-order"></i></a>
        	<a rel="nofollow" href="#" class="price-order order-asc" searchvo="${priceUp}" searchCondition="search">价格<i class="ico-order"></i></a>
         <#elseif sort =="2">
        	<a href="" class="lv-order">驴妈妈推荐</a>
    		<!--  <a rel="nofollow" href="#" class="comment-order" searchvo="${commNum}" searchCondition="search">点评数<i class="ico-order"></i></a>
       	 	<a rel="nofollow" href="#" class="feedback-order" searchvo="${goodCommNum}" searchCondition="search">好评率<i class="ico-order"></i></a> -->
    		<span class="price-order current-order">价格<i class="ico-order"></i></span>
    		<a rel="nofollow" href="#" class="price-order order-asc" searchvo="${priceUp}" searchCondition="search">价格<i class="ico-order"></i></a>
         <#elseif sort == "3">
        	<a href="" class="lv-order">驴妈妈推荐</a>
    		<!--  <a rel="nofollow" href="#" class="comment-order" searchvo="${commNum}" searchCondition="search">点评数<i class="ico-order"></i></a>
       	 	<a rel="nofollow" href="#" class="feedback-order" searchvo="${goodCommNum}" searchCondition="search">好评率<i class="ico-order"></i></a> -->
    		<a rel="nofollow" href="#" class="price-order" searchvo="${priceDown}" searchCondition="search">价格<i class="ico-order"></i></a>
    		<span class="price-order order-asc current-order">价格<i class="ico-order"></i></span>
         </#if>
         <!--<#if searchvo.placeActivityHave == "1" >
         	<span class="checkbox"><a rel="nofollow" class="selected" href="#" searchvo="${notByActivity}" searchCondition="search">景点活动</a></span>
         <#else>
         	<span class="checkbox"><a rel="nofollow" href="#" searchvo="${byActivity}" searchCondition="search">景点活动</a></span>
         </#if>
         <#if searchvo.promotion == "1" >
		 	<span class="checkbox"><a rel="nofollow" class="selected" href="#" searchvo="${notPromotionByActivity}" searchCondition="search">促销活动</a></span>
		 <#else>
		 	<span class="checkbox"><a rel="nofollow" href="#" searchvo="${byPromotionActivity}" searchCondition="search">促销活动</a></span>
		 </#if> -->
    </div><!-- //筛选排序 -->
    <#if pageConfig?? && pageConfig.items?size gt 0>
    	<#include "tickets.ftl" >
    <#else>
     	<div class="msg-warn"><span class="msg-ico02"></span>
        <h4>没有找到符合您条件的产品！查看<a href="#" searchvo="" searchCondition="search">全部产品</a></h4>
    	</div><!-- // div .msg-warn -->
    </#if>
</div><!-- //门票搜索列表 -->