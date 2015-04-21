<!DOCTYPE html>
<#setting number_format="#.##">
<#assign base_url = "http://www.lvmama.com/search/freetour/${fromDest}-${keyword}"/>
<head>
<meta charset="utf-8">
<title>${pageTitle!"自由行_驴妈妈旅游网"}</title>
<meta name="keywords" content="${pageKeyword!"自由行_驴妈妈旅游网"}" />
<meta name="description" content="${pageDescription!"自由行_驴妈妈旅游网"}" />
<#include "/WEB-INF/ftl/common/meta.ftl" >
<#include "/WEB-INF/ftl/common/coremetricsHead.ftl" >
</head>
<body>

<#include "/WEB-INF/ftl/common/header.ftl" >

<div class="lv-crumbs wrap">
    <p><b>您当前所处的位置：</b><a href="http://www.lvmama.com">首页</a> &gt; <a href="http://www.lvmama.com/search/route/${fromDest}-${keyword}.html">${searchvo.fromDest}出发</a> &gt; <a href="${base_url}.html">自由行（景点+酒店）</a> &gt; <#if searchvo.city??><a href="${base_url}-A${searchvo.city}.html">${searchvo.city}</a> &gt; </#if>${searchvo.keyword}</p>
</div><!-- // div .lv-crumbs -->

<#include "/WEB-INF/ftl/common/search_box.ftl"/>

<div class="ui-content wrap">
	<#assign info_item_selected_freetour="true" />
	<#include "/WEB-INF/ftl/common/search_aside.ftl" >
	
    <div class="search-main search-line">
    	<#include "/WEB-INF/ftl/common/nav.ftl">
        <#include "/WEB-INF/ftl/route/freetour_filter.ftl">
        <!-- 线路搜索列表\\ -->
		<div id="list" class="search-result-box search-line-list">
		    <!-- 筛选排序\\  .filter-order filter-commont filter-commont-desc -->
		    <div class="filter-order">
		    	<#if pageConfig.totalPageNum gt 1 > 
	        		<div class="pageoper">
					<@s.property escape="false" value="@com.lvmama.search.util.Pagination@pagination(pageConfig,1)"/>
					</div>
				</#if>
		        <#if !sort??>
					<span  class="lv-order current-order">驴妈妈推荐</span>
					<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="price-order">价格<i class="ico-order"></i></a>
					<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="price-order order-asc">价格<i class="ico-order"></i></a>
				<#elseif sort =="2">
					<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
					<span class="price-order current-order">价格<i class="ico-order"></i></span>
					<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="3" s=true/>.html#list" class="feedback-order  order-asc">价格<i class="ico-order"></i></a>
				<#elseif sort == "3">
					<a href="${base_url}<@fp filter="${filterStr}" type="S" val="1" s=false/>.html#list" class="lv-order">驴妈妈推荐</a>
					<a rel="nofollow" href="${base_url}<@fp filter="${filterStr}" type="S" val="2" s=true/>.html#list" class="feedback-order">价格<i class="ico-order"></i></a>
					<span class="price-order current-order  order-asc">价格<i class="ico-order"></i></span>
				</#if>
		        <#include "/WEB-INF/ftl/route/filter_common.ftl">
		    </div><!-- //筛选排序 -->
		    <#if pageConfig.items?size gt 0>
            	<#include "/WEB-INF/ftl/route/freetour_list.ftl">
            <#else>
             	<div class="msg-warn"><span class="msg-ico02"></span>
                <h4>没有找到符合您条件的产品！查看<a href="${base_url}.html">全部产品</a></h4>
            	</div><!-- // div .msg-warn -->
            </#if>
		</div><!-- //线路搜索列表 -->
		
		<#include "/WEB-INF/ftl/route/more_product.ftl">
		
    </div><!-- // div .search-main -->
</div><!-- // div .ui-content -->
<#include "/WEB-INF/ftl/common/footer.ftl">
<script src="/search/js/route.js"></script>
<script>
if(<@s.property value="pageConfig.items.size()"/> > 0){
     cmCreatePageviewTag("产品搜索结果-自由行(景点+酒店)（可预订商品2）", "G0001","<@s.property value="searchvo.fromDest"  escape="false"/>-"+"<@s.property value="searchvo.keyword"  escape="false"/>", "<@s.property value="pageConfig.totalResultSize"  escape="false"/>");
     }else{
     	cmCreatePageviewTag("产品搜索结果-自由行(景点+酒店)-搜索失败", "G0001","<@s.property value="searchvo.fromDest"  escape="false"/>-"+"<@s.property value="searchvo.keyword"  escape="false"/>", "0");
     }
</script>
</body>
</html>