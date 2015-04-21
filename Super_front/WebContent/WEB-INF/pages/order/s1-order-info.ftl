<!-- 预订信息 -->
<div class="order-title">
    <h3>预订信息</h3>
</div>
<div class="order-content">
		<!--========= 预订产品 S ========-->
		<#if mainProdBranch.prodProduct.selfPack=='true'>
			<#include "/WEB-INF/pages/buy/201107/route/fill_detail_selfpack.ftl">
	  	<#elseif  mainProdBranch.prodProduct.productType = "TICKET" >
	  		<#include "/WEB-INF/pages/order/ticket/fill_detail2.ftl">
	  	 <#elseif   mainProdBranch.prodProduct.productType = "ROUTE" ||mainProdBranch.prodProduct.isTrain()>
            <#include "/WEB-INF/pages/order/ticket/fill_detail.ftl">
		<#elseif mainProdBranch.prodProduct.productType = "HOTEL">
			<#if mainProdBranch.prodProduct.subProductType = "SINGLE_ROOM" && mainProdBranch.prodProduct.isAperiodic != "true">
				<#include "/WEB-INF/pages/buy/201107/hotel/fill_detail_single.ftl">
			<#else>
				<#include "/WEB-INF/pages/buy/201107/hotel/fill_detail.ftl">
			</#if>
	  	</#if>
</div> <!-- //预订信息 -->
