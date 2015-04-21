<@compress single_line=true>
<#import "/WEB-INF/pages/product/newdetail/buttom/selfPack/OJViewDetailFunc.ftl" as func/>
<input type="hidden" id="placeParamId" value="${placeInfoForMap}"/>
<#if prodProductJourneyDetail?? && prodProductJourneyDetail.success>
<form id="orderForm" name="orderForm" method="POST" action="/buy/fill.do" onsubmit="return checkJourneySelecte()">
	<input type="hidden" name="buyInfo.prodBranchId" value="${prodBranchId}"/>
	<input type="hidden" name="buyInfo.visitTime" value="${choseDate}"/>
	<input type="hidden" id="buyInfo_adult" name="buyInfo.adult" value="${adult}"/>
	<input type="hidden" id="buyInfo_child" name="buyInfo.child" value="${child}"/>
	<input type="hidden" name="buyInfo.selfPack" value="true"/>
	<input type="hidden" id="buyInfo_content" name="buyInfo.content" value=""/>
</form>
<#assign current_days=1/>	
<#list prodProductJourneyDetail.productJourneyList as journey>		
<@func.showProdJourneyItem journey current_days adult child/>
<#assign current_days=current_days+journey.maxTime.days/>
</#list>
<#else>
<div class="free_detail" id="free_detail">
<#if prodProductJourneyDetail??>
<div style="display:none">${prodProductJourneyDetail.success}</div>
</#if>
	<div class="free_fc_msg">
		<em class="free_msg_info">友情提示：</em>很抱歉，您选择的日期暂时不可销售，请重新选择出游日期，谢谢。<input type="image" src="/img/free/free_cha.png" onmouseover="this.src='/img/free/free_cha_hover.png'" onmouseout="this.src='/img/free/free_cha.png'" class="free_cha" id="free_cha"/>
	</div>
</div>
</#if>
<script type="text/javascript" src="/js/buy/buy_journey_new.js"></script>
</@compress>