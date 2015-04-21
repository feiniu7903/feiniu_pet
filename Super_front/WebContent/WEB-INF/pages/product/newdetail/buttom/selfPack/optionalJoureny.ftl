<@compress single_line=true>
<#import "/WEB-INF/pages/product/newdetail/buttom/selfPack/journey_func.ftl" as func/>
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
<!--详情-->
<div class="free_detail" id="free_detail">
	<div class="free_fc_msg">
		<em class="free_msg_info">友情提示：</em>以下是行程包含的产品细节，您可以根据需要进行更改。价格会根据你的更改上下浮动。<input type="image" src="/img/free/free_cha.png" onmouseover="this.src='/img/free/free_cha_hover.png'" onmouseout="this.src='/img/free/free_cha.png'" class="free_cha" id="free_cha"/>
	</div>
	<div id="free_yd_main_list">
		<#assign current_days=1/>	
		<#list prodProductJourneyDetail.productJourneyList as journey>		
		<@func.showProdJourneyItem journey current_days adult child/>
		<#assign current_days=current_days+journey.maxTime.days/>
		</#list>
	</div>
	<div class="free_yd_bottom">
		<a href="javascript:void(0)" id="recommend_xc">恢复到推荐行程</a>
	</div>
</div>
<#--
<div class="free_map">
	<span class="free_map_title"><b>产品分布地图</b>(已选择的产品)</span>
	<div class="free_baidumap" id="container"></div>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
	<script type="text/javascript">
		var map = new BMap.Map("container");
		map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
		
		/*	map.addEventListener("click", function(){
			  alert("您点击了地图。");
			});
		*/
	</script>
</div>
-->
<div id="free_map" class="free_map" style="display:none">
	<span class="free_map_title"><b>产品分布地图</b>(已选择的产品)</span>
	<iframe id="map_iframe" style="height:408px; width:910px; overflow:hidden" frameborder="none"></iframe>
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/new_v/ob_detail/free/index.js?r=8266"></script>
<script type="text/javascript" src="/js/buy/buy_journey.js"></script>
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
</@compress>
