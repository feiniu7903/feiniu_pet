<#assign title = ""/>
<#if fromType =="ticket">
	<#assign title = "没有找到&ldquo;${searchvo.keyword}&rdquo;相关的景点门票。"/>
<#elseif fromType =="hotel">
	<#assign title = "没有找到&ldquo;${searchvo.keyword}&rdquo;相关的特色酒店。"/>
<#else>
	<#assign title = "没有找到&ldquo;${searchvo.fromDest}&rdquo;出发到&ldquo;${searchvo.keyword}&rdquo;的产品。"/>
</#if>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>${pageTitle}</title>
<meta name="keywords" content="${pageKeyword}" />
<meta name="description" content="${pageDescription}" />
<#include "/WEB-INF/ftl/common/meta.ftl" >
<#include "/WEB-INF/ftl/common/coremetricsHead.ftl" >
</head>
<body class="lvcomment">
<#include "/WEB-INF/ftl/common/header.ftl" >

<div class="lv-crumbs wrap">
    <p><b>您当前所处的位置：</b><a href="http://www.lvmama.com">首页</a> &gt; <a href="http://www.lvmama.com/search/route/${fromDest}-${keyword}.html">${searchvo.fromDest}出发</a> &gt; ${searchvo.keyword}</p>
</div><!-- // div .lv-crumbs -->

<#include "/WEB-INF/ftl/common/search_box.ftl"/>

<div class="ui-content wrap">
	<#assign info_item_selected_route="true" />
	<#include "/WEB-INF/ftl/common/search_aside.ftl">
    <div class="search-main">
        
        <div class="msg-warn"><span class="msg-ico02"></span>
        	<h3>${title}</h3>
		    <p>以下是驴妈妈的推荐，可能会给您带来惊喜哦！</p>
		</div><!-- // div .msg-warn -->
        
        <#if pageConfig.items?size gt 0 >
			<!-- dep-to-dest\\ -->
			<!-- 更多产品\\ -->
			<div class="ui-box plist-pic-box lv-bd">
			    <div class="ui-box-title sl-linear"><span class="link-more fr">
			    <#if pageConfig.totalResultSize gt 4>
				    <a target="_blank"  href="http://www.lvmama.com/search/${type}/${pageConfig.items.get(0).fromDest}-${searchvo.keyword}.html">
				    	全部产品（${pageConfig.totalResultSize}条）&gt;&gt;
					</a>
				</#if>
			    </span>
			    	<h4>&ldquo;${pageConfig.items.get(0).fromDest}&rdquo;出发到&ldquo;${searchvo.keyword}&rdquo;有更多产品 </h4>
		    	</div>
			    <div class="ui-box-container clearfix">
			        <ul class="plist-pic-list">
			        	<#list pageConfig.items as product>
				        	<li>
				                <a target="_blank"  href="http://www.lvmama.com${product.productUrl}">
				                	<img src="http://pic.lvmama.com/pics/${product.smallImage}" width="120" height="60">
			                	</a>
				                <p>
				                <a target="_blank" href="http://www.lvmama.com${product.productUrl}">
				                	<#if product.productName?length gt 46 >${product.productName?substring(0,43)}<b>...</b><#else>${product.productName}</#if>
				                </a>
				                </p>
				                <p><dfn>&yen;<i>${product.sellPrice}</i>起</dfn></p>
				            </li>
			        	</#list>
			        </ul>
			    </div>
			</div><!-- //更多产品 -->
			<!-- //dep-to-dest -->
		</#if>
		<#if !SEARCH_FOREIGN??>
			 <!-- 热门景点\\ -->
				<div class="ui-box hot-pic-box lv-bd">
				    <div class="ui-box-title sl-linear"><h4>热门景点</h4></div>
				    <div class="ui-box-container clearfix">
				        <ul class="hot-pic-list">
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-上海欢乐谷.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_1.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-上海欢乐谷.html">上海欢乐谷</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-东方明珠.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_2.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-东方明珠.html">东方明珠</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-中华恐龙园.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_3.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-中华恐龙园.html">中华恐龙园</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-横店影视城.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_4.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-横店影视城.html">横店影视城</a></p>
				            </li>
							<li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-灵山大佛.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_5.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-灵山大佛.html">灵山大佛</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-杜莎夫人蜡像馆.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_6.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-杜莎夫人蜡像馆.html">杜莎夫人蜡像馆</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-西溪湿地.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_7.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-西溪湿地.html">西溪湿地</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/ticket/${fromDest}-上海长风海洋世界.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_8.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/ticket/${fromDest}-上海长风海洋世界.html">上海长风海洋世界</a></p>
				            </li>
				        </ul>
				    </div>
				</div><!-- //热门景点 -->
				<!-- 国内目的地\\ -->
				<div class="ui-box hot-pic-box lv-bd">
				    <div class="ui-box-title sl-linear"><h4>国内目的地</h4></div>
				    <div class="ui-box-container clearfix">
				        <ul class="hot-pic-list">
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-天目湖.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_9.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-天目湖.html">天目湖</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-普陀山.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_10.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-普陀山.html">普陀山</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-千岛湖.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_11.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-千岛湖.html">千岛湖</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-西塘.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_12.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-西塘.html">西塘</a></p>
				            </li>
							<li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-武夷山.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_13.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-武夷山.html">武夷山</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-海南.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_14.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-海南.html">海南</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-云南.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_15.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-云南.html">云南</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-厦门.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_16.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-厦门.html">厦门</a></p>
				            </li>
							<li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-张家界.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_17.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-张家界.html">张家界</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-桂林.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_18.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-桂林.html">桂林</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-四川.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_19.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-四川.html">四川</a></p>
				            </li>
				            <li>
				                <a href="http://www.lvmama.com/search/route/${fromDest}-黄山.html"><img src="http://pic.lvmama.com/img/new_v/ob_search/hotpic_20.jpg" width="170" height="85"></a>
				                <p><a href="http://www.lvmama.com/search/route/${fromDest}-黄山.html">黄山</a></p>
				            </li>
				        </ul>
				    </div>
				</div><!-- //国内目的地 -->
		</#if>
		<#include "/WEB-INF/ftl/common/foreign_dest.ftl"/>
    </div><!-- // div .search-main -->
</div><!-- // div .ui-content -->

<#include "/WEB-INF/ftl/common/footer.ftl"/>
<script>
cmCreatePageviewTag("产品搜索结果-搜索失败", "G0001","<@s.property value="searchvo.fromDest"  escape="false"/>-"+"<@s.property value="searchvo.keyword"  escape="false"/>", "0");
</script>
</body>
</html>