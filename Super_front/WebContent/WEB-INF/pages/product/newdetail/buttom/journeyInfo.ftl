            <i id="row_3" class="pkg-maodian">&nbsp;</i>
            <h3 class="h3_tit"><span><@s.if test="prodCProduct.prodProduct.hasSelfPack()">推荐行程</@s.if><@s.else>行程说明</@s.else></span></h3>
            <div class="row recommend_travel">
            	<b>行程提示:</b>
        		<@s.iterator value="viewTravelTipsList" var="vtt">
        		<a target="_blank" href="http://www.lvmama.com/product/getTravelTips/${vtt.travelTipsId}">${vtt.tipsName}</a>&nbsp;
        		</@s.iterator>
            	<!-- 多行程 -->
            	<@s.if test="prodCProduct.prodProduct.hasMultiJourney()">
            		<div id="multiJourneyDiv"></div>            		
            	</@s.if>
            	<@s.else>
            		<#include "/WEB-INF/pages/product/newdetail/buttom/journeyDetail.ftl">
            	</@s.else>
            </div><!--recommend_travel end-->
