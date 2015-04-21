	<!--右侧 S-->
	<div class="right-container">
    	<!-- tab -->
    	<#include "/WEB-INF/pages/place/template_abroad/tab.ftl" />
    		<!-- todo 需要增减判断 当点击推荐时显示推荐产品 tab-->
		<!-- 推荐产品 -->
		<#--<@s.if test='currentTab=="products"'>
				    <#include "/WEB-INF/pages/place/recommendProducts.ftl" />
		</@s.if>-->
		<@s.if test='currentTab=="dest2dest"'>
		<div id="ajaxDestContent">
				    <#include "/WEB-INF/pages/place/template_abroad/dest2dest.ftl" />
		</div>
		</@s.if>
		<!-- 出发地到目的地跟团游 -->
		<@s.if test='currentTab=="dest2destGroup"'>
		<div id="ajaxDestContent">
				    <#include "/WEB-INF/pages/place/template_abroad/dest2destGroup.ftl" />
		</div>
		</@s.if>
		
		<!--出发地到目的地自由行-->
		<@s.if test='currentTab=="dest2destFreeness"'>
		<div id="ajaxDestContent">
				    <#include "/WEB-INF/pages/place/template_abroad/dest2destFreeness.ftl" />
		</div>
		</@s.if>
		
		<!--特色酒店-->
		<@s.if test='currentTab=="hotel"'>
				    <#include "/WEB-INF/pages/place/hotel.ftl" />
		</@s.if>
		<!-- 景区 -->
		<@s.if test='currentTab=="ticket"'>
				    <#include "/WEB-INF/pages/place/placesTicket.ftl" />
		</@s.if>
     </div>
	<!--右侧 E-->
