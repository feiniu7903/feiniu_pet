            <input type="checkbox" name="inpAgreement" id="lvmamaxieyi"/>&nbsp;
            <label id="titAgreement" onClick="agreementShow()">
            <!--不定期走自己的协议 -->
            <@s.if test='mainProdBranch.prodProduct.IsAperiodic()'>
            	同意驴妈妈期票预订协议</label>
            	<#include "/WEB-INF/pages/buy/xieyi/qipiao_xieyi.ftl"/>
            </@s.if>
            <@s.else>
	        	<@s.if test='mainProdBranch.prodProduct.productType == "TICKET"'>        	   
	        		<!-- 门票 -->
	        		同意驴妈妈票务预订协议</label>
	        		<#include "/WEB-INF/pages/buy/xieyi/men_piao_xieyi.ftl"/>
	        	</@s.if>
	        	<@s.elseif test='mainProdBranch.prodProduct.isTrain()'>
	        		同意驴妈妈旅游网快铁驴行旅游产品预订服务协议</label>
	        		<#include "/WEB-INF/pages/buy/xieyi/train_xieyi.ftl"/>
	        	</@s.elseif>
	        	<@s.elseif test='mainProdBranch.prodProduct.productType == "ROUTE" && !mainProdBranch.prodProduct.isEContract()'>
	        			同意驴妈妈委托服务协议</label>
	        			
	        			<p style="height:10px;"></p>
						<#include "/WEB-INF/pages/buy/xieyi/weituo_xieyi.ftl"/>
	        	</@s.elseif>
	        	<@s.elseif test='mainProdBranch.prodProduct.productType == "HOTEL"'>        
	        		驴妈妈旅游网酒店预订须知</label>
	        		<#include "/WEB-INF/pages/buy/xieyi/hotel_xieyi.ftl"/>   
	        	</@s.elseif>
        	</@s.else>
<script type="text/javascript">
$(document).ready(function() {
 $("#titAgreement").attr("status", "open");
 $(".agreement").show();
});
</script>
