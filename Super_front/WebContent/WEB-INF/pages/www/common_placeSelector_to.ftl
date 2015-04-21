<ul class="tabs">
	<#list tabPlaceList as placeBean>
		<li id="city_${placeBean.prodContainer.toPlaceId}" <#if provincePlaceId="${placeBean.prodContainer.toPlaceId}">class="tabs_select"</#if>>
			<a href="javascript:void(0)" data-params='{toPlaceId:"${placeBean.prodContainer.toPlaceId}"}'>${placeBean.prodContainer.displayedToPlaceName}</a>
		</li>
	</#list>
	<#list tabPlaceListMore as prodContainer>
		<li class="moreCityText <#if provincePlaceId="${prodContainer.toPlaceId}">tabs_select</#if>" id="city_${prodContainer.toPlaceId}">
			<a href="javascript:void(0)" data-params='{toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
		</li>
	</#list>
</ul>
<script type="text/javascript">
	document.onreadystatechange = function(){
		if(this.readyState=="complete"){
			$("li.tabs_select").click();
		}
	}
</script>
<div class="panes" id="panes">
	<#list tabPlaceList as placeBean>
		<ul class="inTabs<#if provincePlaceId="${placeBean.prodContainer.toPlaceId}"> tabCurrent</#if>">
		<#if (placeBean.prodContainerList?size>0)>
			<li<#if provincePlaceId="${placeBean.prodContainer.toPlaceId}" && cityPlaceId=null> class="inCurrent"</#if>>
				<a href="javascript:void(0)" data-params='{toPlaceId:"${placeBean.prodContainer.toPlaceId}"}'>全部</a>
			</li>
			<#list placeBean.prodContainerList as prodContainer>
			<li<#if cityPlaceId="${prodContainer.toPlaceId}"> class="inCurrent"</#if>>
				<a href="javascript:void(0)" data-params='{toPlaceId:"${prodContainer.toPlaceId}"}'>${prodContainer.displayedToPlaceName}</a>
			</li>
			</#list>
		</#if>
		</ul>
	</#list>
</div>
