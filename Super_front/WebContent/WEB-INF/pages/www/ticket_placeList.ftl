<dl class="paneTab">
	<dt>
		<i class="paneTabPlus"></i>
		<span>点评数：<em><a href="http://www.lvmama.com/comment/${place.placeSearchInfo.placeId}-1" target="_blank">${place.placeSearchInfo.cmtNum}</a></em></span>
		<kbd>总体评价：
		<small class="commentsStar${place.placeSearchInfo.cmtAvgScoreStr!''?string}"></small></kbd>
		<a href="http://www.lvmama.com/dest/${place.placeSearchInfo.pinYinUrl}" target="_blank">
		<#if place.placeSearchInfo.name.indexOf("~") gt 0> 
		${place.placeSearchInfo.name?substring(0,place.placeSearchInfo.name?index_of("~"))}
		<#else>${place.placeSearchInfo.name}</#if>
		</a>
		<#if place.placeSearchInfo.summary?length gt 25 >
			<label title="${place.placeSearchInfo.summary}">${place.placeSearchInfo.summary?substring(0,25)}...</label>
		<#else>
			${place.placeSearchInfo.summary}
		</#if>
	</dt>
	<#list place.productSearchInfoList as productSearchInfo>
	<dd>
		<strong>&yen;<b>${(productSearchInfo.sellPrice!0)/100}</b>起</strong>
		<#if productSearchInfo.cashRefund gt 0>
		<kbd>点评奖金：<small>&yen;${productSearchInfo.cashRefund}</small></kbd>
		</#if>
		<a href="http://www.lvmama.com${productSearchInfo.productUrl}" title="${productSearchInfo.productName}" target="_blank">${productSearchInfo.productName}</a>
		${productSearchInfo.recommendInfoSecond}
	</dd>
	</#list>
</dl><!--paneTab end-->
