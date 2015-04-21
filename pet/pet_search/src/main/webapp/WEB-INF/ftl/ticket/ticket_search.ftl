<!DOCTYPE html>
<#setting number_format="#.##">
<#assign base_url = "http://www.lvmama.com/search/ticket/${fromDest}-${keyword}"/>
<head>
<meta charset="utf-8">
<title>${pageTitle!"景点门票_驴妈妈旅游网"}</title>
<meta name="keywords" content="${pageKeyword!"景点门票_驴妈妈旅游网"}" />
<meta name="description" content="${pageDescription!"景点门票_驴妈妈旅游网"}" />
<#include "/WEB-INF/ftl/common/meta.ftl" >
<#include "/WEB-INF/ftl/common/coremetricsHead.ftl" >
<script src="http://pic.lvmama.com/min/index.php?f=js/new_v/jquery-1.7.2.min.js"></script> 
</head>
<body>
<#include "/WEB-INF/ftl/common/header.ftl" >
<div class="lv-crumbs wrap">
    <p><b>您当前所处的位置：</b><a href="http://www.lvmama.com">首页</a> &gt; <a href="${base_url}.html">景点门票</a> &gt; <#if searchvo.city??><a href="${base_url}-A${searchvo.city}.html">${searchvo.city}</a> &gt; </#if>${searchvo.keyword}</p>
</div><!-- // div .lv-crumbs -->

<#include "/WEB-INF/ftl/common/search_box.ftl"/>

<div class="ui-content wrap">
	<#assign info_item_selected_ticket="true" />
	<#include "/WEB-INF/ftl/common/search_aside.ftl">
    <div class="search-main search-tickets">
        <#include "/WEB-INF/ftl/common/msg_tips.ftl" >
        <#include "/WEB-INF/ftl/common/nav.ftl">
        <!-- 搜素筛选\\ -->
        <div class="search-filter">
            <div class="filter-info clearfix"><span class="result-info">共找到<i>${pageConfig.totalResultSize}</i>条结果。</span>
                <dl id="your-choices" class="your-choices">
                	<#if searchvo.subject?? || searchvo.city?? || searchvo.placeActivity??>
	                    <dt>您已选择：</dt>
	                    <#if searchvo.city??>
	                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="A" remove=true/>.html"><h6>包含地区：</h6>${searchvo.city}<span class="icon-close"></span></a></dd>
	                    </#if>
	                    <#if searchvo.subject??>
	                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="C" remove=true/>.html"><h6>主题：</h6>${searchvo.subject}<span class="icon-close"></span></a></dd>
	                    </#if>
	                    <#if searchvo.tag??>
	                    	<dd><a href="${base_url}<@fp filter="${filterStr}" type="T" remove=true/>.html"><h6>标签：</h6>${searchvo.tag}<span class="icon-close"></span></a></dd>
	                    </#if>
	                    <#if searchvo.placeActivity??>
	                    	<dd><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="R" remove=true/>.html"><h6>景点活动：</h6>${searchvo.placeActivity}<span class="icon-close"></span></a></dd>
	                    </#if>
	                    <dd class="tags-empty"><a rel="nofollow" href="${base_url}.html">清空全部</a></dd>
                    </#if>
                </dl>
            </div>
            <#assign cities_show = cities?? && cities?size gt 0 && !searchvo.city?? />
            <#assign subjects_show = subjects?? && subjects?size gt 0 && !searchvo.subject?? />
            <#assign placeActivities_show = placeActivities?? && placeActivities?size gt 0 && !searchvo.placeActivity?? />
            <#if cities_show || subjects_show || placeActivities_show>
            <ul id="tags-list" class="filter-tags">
            	<#if cities_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>包含地区：</span>
                    	<p>
	                    	<a class="s-tag selected" href="#">全部</a>
	                    	
	                    	<#list cities?keys as key>
	                    		<a class="s-tag" href="${base_url}<@fp filter="${filterStr}" type="A" val="${key}"/>.html">${key}(${cities.get(key)})</a>
	                    	</#list>
                    	</p>
	                 </li>
                </#if>
                <#if subjects_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>主　　题：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag selected" href="#">全部</a>
		                    <#list subjects?keys as key>
		                		<a rel="nofollow" class="s-tag" href="${base_url}<@fp filter="${filterStr}" type="C" val="${key}"/>.html">${key}(${subjects.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>
				<#if placeActivities_show>
	                <li>
	                	<a href="javascript:void(0);" class="view-more">更多<i class="arrow"></i></a>
	                    <span>景点活动：</span>
	                    <p>
		                    <a rel="nofollow" class="s-tag selected" href="#">全部</a>
		                    <#list placeActivities?keys as key>
		                		<a rel="nofollow" class="s-tag" href="${base_url}<@fp filter="${filterStr}" type="R" val="${key}"/>.html">${key}(${placeActivities.get(key)})</a>
		                	</#list>
	                	</p>
					</li>
				</#if>
            </ul>
            </#if>
        </div><!-- //搜素筛选 -->
        
        <!-- 门票搜索列表\\ -->
		<div id="list" class="search-result-box search-tickets-list">
        
            <!-- 筛选排序\\  .filter-order filter-commont filter-commont-desc -->
            <div class="filter-order">
            	<#if pageConfig.totalPageNum gt 1 > 
            		<div class="pageoper">
					<@s.property escape="false" value="@com.lvmama.search.util.Pagination@pagination(pageConfig,1)"/>
					</div>
				</#if>
				<#if !sort??>
                	<span  class="lv-order current-order">驴妈妈推荐</span>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="6" s=true/>.html#list" class="comment-order">点评数<i class="ico-order"></i></a>
               	 	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="4" s=true/>.html#list" class="feedback-order">好评率<i class="ico-order"></i></a>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="price-order">价格<i class="ico-order"></i></a>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="price-order order-asc">价格<i class="ico-order"></i></a>
                <#elseif sort =="6">
                	<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
                	<span class="comment-order current-order">点评数<i class="ico-order"></i></span>
               	 	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="4" s=true/>.html#list" class="feedback-order">好评率<i class="ico-order"></i></a>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="price-order">价格<i class="ico-order"></i></a>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="price-order order-asc">价格<i class="ico-order"></i></a>
              	<#elseif sort =="4">
                	<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
            		<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="6" s=true/>.html#list" class="comment-order">点评数<i class="ico-order"></i></a>
            		<span class="feedback-order current-order">好评率<i class="ico-order"></i></span>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="price-order">价格<i class="ico-order"></i></a>
                	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="price-order order-asc">价格<i class="ico-order"></i></a>
                 <#elseif sort =="2">
                	<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
            		<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="6" s=true/>.html#list" class="comment-order">点评数<i class="ico-order"></i></a>
               	 	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="4" s=true/>.html#list" class="feedback-order">好评率<i class="ico-order"></i></a>
            		<span class="price-order current-order">价格<i class="ico-order"></i></span>
            		<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="price-order order-asc">价格<i class="ico-order"></i></a>
                 <#elseif sort == "3">
                	<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
            		<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="6" s=true/>.html#list" class="comment-order">点评数<i class="ico-order"></i></a>
               	 	<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="4" s=true/>.html#list" class="feedback-order">好评率<i class="ico-order"></i></a>
            		<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="price-order">价格<i class="ico-order"></i></a>
            		<span class="price-order order-asc current-order">价格<i class="ico-order"></i></span>
                 </#if>
                 <#if searchvo.placeActivityHave == "1" >
                 	<span class="checkbox"><a rel="nofollow" class="selected" href="${base_url}<@fp filter="${filterStr}" type="U"  remove=true/>.html">景点活动</a></span>
                 <#else>
                 	<span class="checkbox"><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="U" val ="1" />.html">景点活动</a></span>
                 </#if>
                 <#if searchvo.promotion == "1" >
				 	<span class="checkbox"><a rel="nofollow" class="selected" href="${base_url}<@fp filter="${filterStr}" type="V"  remove=true/>.html">促销活动</a></span>
				 <#else>
				 	<span class="checkbox"><a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="V" val ="1" />.html">促销活动</a></span>
				 </#if>
            </div><!-- //筛选排序 -->
            <#if pageConfig?? && pageConfig.items?size gt 0>
            	<#include "/WEB-INF/ftl/ticket/tickets.ftl" >
            <#else>
             	<div class="msg-warn"><span class="msg-ico02"></span>
                <h4>没有找到符合您条件的产品！查看<a href="${base_url}.html">全部产品</a></h4>
            	</div><!-- // div .msg-warn -->
            </#if>
        </div><!-- //门票搜索列表 -->
        
    </div><!-- // div .search-main -->
</div><!-- // div .ui-content -->

<#include "/WEB-INF/ftl/common/footer.ftl">
<script>
	if(<@s.property value="pageConfig.items.size()"/> > 0){
     cmCreatePageviewTag("产品搜索结果-景点门票（可预订商品2）", "G0001","<@s.property value="searchvo.fromDest"  escape="false"/>-"+"<@s.property value="searchvo.keyword"  escape="false"/>", "<@s.property value="pageConfig.totalResultSize"  escape="false"/>");
     }else{
     cmCreatePageviewTag("产品搜索结果-景点门票-搜索失败", "G0001","<@s.property value="searchvo.fromDest"  escape="false"/>-"+"<@s.property value="searchvo.keyword"  escape="false"/>", "0");
     }
</script>
</body>
</html>