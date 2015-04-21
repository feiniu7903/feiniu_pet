<h4><span>近期推荐</span></h4>
<div class="paneRecent">
<@s.iterator value="productListRecommend">
	<#include "/WEB-INF/pages/www/common_productList_recommend.ftl">
</@s.iterator>
</div><!--paneRecent end-->

<@s.if test="productListGroupForeign.size>0">
<h4><span>跟团游推荐</span></h4>
<ul>
<@s.iterator value="productListGroupForeign">
	<#include "/WEB-INF/pages/www/common_productList.ftl">
</@s.iterator>
</ul>
<p><a href="#" id="teamAbroad" class="moreLink">更多跟团游&gt;&gt;</a></p>
</@s.if>

<@s.if test='productListFreenessForeign.size>0 && toPlaceId!="SHIP"'>
<h4><span>自由行（机票+酒店）推荐</span></h4>  
<ul>
<@s.iterator value="productListFreenessForeign">
	<#include "/WEB-INF/pages/www/common_productList.ftl">
</@s.iterator>
</ul>
<p><a href="#" id="freeAbroad" class="moreLink">更多自由行&gt;&gt;</a></p>
</@s.if>
