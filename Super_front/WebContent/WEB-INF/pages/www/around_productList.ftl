<h4><!-- <a id="moreAround" href="#" >更多&gt;&gt;</a> --><span>热门线路推荐</span></h4>
<div class="paneRecent">
<@s.iterator value="productListRecommend">
	<#include "/WEB-INF/pages/www/common_productList_recommend.ftl">
</@s.iterator>
</div><!--paneRecent end-->

<div class="row1">
	<@s.if test="fromPlaceId!=79 and fromPlaceId!=1">
	<h3>跟团线路推荐</h3>
	</@s.if>
	<@s.if test="productListBus!=null && productListBus.size>0">
	<h4><span <@s.if test="fromPlaceId==79">style="color: #222;"</@s.if> >开心驴行</span><i title="开心驴行是驴妈妈旅游网推出的新型跟团游产品，提出“轻松、纯玩、品质、让旅游更开心”的质量标准和运营理念，每一条线路都由资深的旅游产品经理实地考察、精选酒店和景点、潜心设计而成，让游客轻松跟团出游，尽享品质、开心之旅。 "></i>（独家产品 独立发团）</h4>  
	<ul>
	<@s.iterator value="productListBus">
		<#include "/WEB-INF/pages/www/common_productList.ftl">
	</@s.iterator>
	</ul>
	<p><a href="#" id="travelAround" class="moreLink">更多开心驴行&gt;&gt;</a></p>
	</@s.if>
	
	<@s.if test="productListGroup.size>0">
	<h4><span <@s.if test="fromPlaceId==79">style="color: #222;"</@s.if> >精选线路</span></h4>
	<ul>
	<@s.iterator value="productListGroup">
		<#include "/WEB-INF/pages/www/common_productList.ftl">
	</@s.iterator>
	</ul>
	<p><a href="#" id="jointAround" class="moreLink">更多精选线路&gt;&gt;</a></p>
	</@s.if>
</div><!--row1 end-->
