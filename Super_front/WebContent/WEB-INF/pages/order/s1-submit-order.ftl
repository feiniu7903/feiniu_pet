<!-- 同意协议/提交订单 -->
<div class="order-content xdl-hor">
    <div class="hr_d"></div>
    <dl class="xdl">
        <dt class="tl"><a href="javascript:history.back();" class="vmiddle">&lt; 返回上一步</a></dt>
        <dd><input type="button" class="pbtn pbtn-big pbtn-orange" onclick="btnFormSubmit();" value="同意以下预订协议并提交订单"></input>
        </dd>
    </dl>   
    <#if mainProdBranch.prodProduct.IsAperiodic()>
		<#include "/WEB-INF/pages/order/agreement/qipiao_xieyi.ftl"/>
	<#else>
	    <#if mainProdBranch.prodProduct.productType == "TICKET">        	   
			<#include "/WEB-INF/pages/order/agreement/men_piao_xieyi.ftl"/>
		</#if>
	    <#if mainProdBranch.prodProduct.isTrain()>
			<#include "/WEB-INF/pages/order/agreement/train_xieyi.ftl"/>
		</#if>
	    <#if mainProdBranch.prodProduct.productType == "ROUTE">
			<#if mainProdBranch.prodProduct.subProductType == "GROUP" || mainProdBranch.prodProduct.subProductType == "GROUP_LONG" || mainProdBranch.prodProduct.subProductType == "SELFHELP_BUS">
				<#include "/WEB-INF/pages/order/agreement/gen_tuan_you_xieyi.ftl"/>
		    </#if>
		    <#if mainProdBranch.prodProduct.subProductType == "GROUP_FOREIGN" || mainProdBranch.prodProduct.subProductType == "FREENESS_FOREIGN">
				<#include "/WEB-INF/pages/order/agreement/chu_jing_you_xieyi.ftl"/>
		    </#if>
		    <#if mainProdBranch.prodProduct.subProductType == "FREENESS" || mainProdBranch.prodProduct.subProductType == "FREENESS_LONG">  
				<#include "/WEB-INF/pages/order/agreement/zi_you_xing_xieyi.ftl"/>
		    </#if>
	    </#if>
	    <#if mainProdBranch.prodProduct.productType == "HOTEL">        
			<#include "/WEB-INF/pages/order/agreement/hotel_xieyi.ftl"/>   
		</#if>  
    </#if>
</div> <!-- 同意协议/提交订单 -->