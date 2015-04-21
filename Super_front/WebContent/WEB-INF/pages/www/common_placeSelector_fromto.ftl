<h3 class="moreCity"><span>上海出发</span><strong class="special4"></strong>
	<ul>
		<#list tabPlaceList as placeBean>
		<li>
			<a href="javascript:void(0)" code="${placeBean.prodContainerFromPlace.fromPlaceCode}" data-tabId="${placeBean.prodContainerFromPlace.fromPlaceSeq}" bid="${placeBean.prodContainerFromPlace.blockId}">${placeBean.prodContainerFromPlace.fromPlaceName}出发</a>
		</li>
		</#list>
	</ul>
</h3>
<div class="tabCon">
	<#list tabPlaceList as placeBean>
	<ul class="tabs" data-tabId="${placeBean.prodContainerFromPlace.fromPlaceSeq}" from-to="${placeBean.prodContainerFromPlace.fromPlaceName}" <#if fromPlaceId?exists && placeBean.prodContainerFromPlace.fromPlaceId==fromPlaceId> style="display: block;"</#if> >
		<#list placeBean.prodContainerList as prodContainer>
			<#if prodContainer_index==0>
				<li class="current">
					<a href="javascript:void(0)" data-params='{fromPlaceId:${prodContainer.fromPlaceId},toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
				</li>
			<#else>
				<li>
					<a href="javascript:void(0)" data-params='{fromPlaceId:${prodContainer.fromPlaceId},toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
				</li>
			</#if>
		</#list>
	</ul>
	</#list>
</div>
