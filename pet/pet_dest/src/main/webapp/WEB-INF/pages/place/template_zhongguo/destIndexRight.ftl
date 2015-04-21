	<!--右侧 S-->
	<div class="right-container">
    	<!-- tab -->
    	<#include "/WEB-INF/pages/place/template_zhongguo/tab.ftl" />
    		<!-- todo 需要增减判断 当点击推荐时显示推荐产品 tab-->
		<!-- 推荐产品 -->
		<@s.if test='currentTab=="products"'>
				    <#include "/WEB-INF/pages/place/recommendProducts.ftl" />
		</@s.if>
		<!-- 景区 -->
		<@s.if test='currentTab=="ticket"'>
				    <#include "/WEB-INF/pages/place/placesTicket.ftl" />
		</@s.if>
		
		<!--特色酒店-->
		<@s.if test='currentTab=="hotel"'>
				    <#include "/WEB-INF/pages/place/hotel.ftl" />
		</@s.if>
    	<!--自由行-->
		<@s.if test='currentTab=="freeness"'>
				    <#include "/WEB-INF/pages/place/freeness.ftl" />
		</@s.if>
    	 <!--周边跟团游-->
		<@s.if test='currentTab=="surrounding"'>
				    <#include "/WEB-INF/pages/place/surroundingProduct.ftl" />
		</@s.if>
		<!--各地到上海-->
		<@s.if test='currentTab=="dest2dest"'>
		<div id="ajaxDestContent">
				    <#include "/WEB-INF/pages/place/template_zhongguo/dest2dest.ftl" />
		</div>
		</@s.if>
		

     </div>
	<!--右侧 E-->	
