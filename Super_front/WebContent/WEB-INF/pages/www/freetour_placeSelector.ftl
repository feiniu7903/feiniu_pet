<div class="row1">
	<h3>周边自由行推荐</h3>
	<#include "/WEB-INF/pages/www/common_placeSelector_to.ftl">
	<div class="moreCity">
		<h5>更多目的地<span></span></h5>
		<dl>
			<dd>
				<p>
				<#list tabPlaceListMore as prodContainer>
					<a href="javascript:void(0)" data-params='{toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
				</#list>
				</p>
			</dd>
		</dl>
	</div><!--moreCity end-->
	
	<div class="inPane">
		<#include "/WEB-INF/pages/www/freetour_productList.ftl">
	</div><!--inPane end-->
</div><!--row1 end-->
