<h4><!-- <a id="moreDestroute" href="#" >更多&gt;&gt;</a> --><span>热门线路推荐</span></h4>
<div class="paneRecent">
<@s.iterator value="productListRecommend">
	<#include "/WEB-INF/pages/www/common_productList_recommend.ftl">
</@s.iterator>
</div><!--paneRecent end-->

<@s.if test="productListGroupLong.size>0">
<h4><span>跟团线路推荐</span></h4>
<ul>
<@s.iterator value="productListGroupLong">
	<#include "/WEB-INF/pages/www/common_productList.ftl">
</@s.iterator>
</ul>
<p><a href="#" id="teamDestroute" class="moreLink">更多跟团线路&gt;&gt;</a></p>
</@s.if>

<@s.if test="productListFreenessLong.size>0">
<h4><span>自由行线路推荐</span></h4>
<ul>
<@s.iterator value="productListFreenessLong">
	<#include "/WEB-INF/pages/www/common_productList.ftl">
</@s.iterator>
</ul>
<p><a href="#" id="freeDestroute" class="moreLink">更多自由行线路&gt;&gt;</a></p>
</@s.if>
