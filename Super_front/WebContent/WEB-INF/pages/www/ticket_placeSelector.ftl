<div class="row1" id="row1">
	<h3>打折门票精选</h3>
	<#include "/WEB-INF/pages/www/common_placeSelector_to.ftl">
	<div class="moreCity">
		<h5>更多目的地<span></span></h5>
		<dl>
			<#list tabPlaceListMoreZone as placeBean>
			<dd>
				<strong>${placeBean.zoneName}：</strong>
				<p>
				<#list placeBean.prodContainerList as prodContainer>
					<a href="javascript:void(0)" data-params='{toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
				</#list>
				</p>
			</dd>
			</#list>
		</dl>
	</div><!--moreCity end-->
	
	<div class="inPane">
		<#include "/WEB-INF/pages/www/ticket_productList.ftl">
	</div><!--inPane end-->
	<p class="align_right"><a href="javascript:void(0)" id="a-select-more">查看更多门票>></a></p>	
</div><!--row1 end-->
