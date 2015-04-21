<ul class="scroll_nav_ul" id="scroll_nav" selfPack="<@s.property value="prodCProduct.prodProduct.hasSelfPack()"/>">
    <@s.if test="viewPage.hasContent('FEATURES')">
    <li class="row_1" ><a href="javascript:void(0)">产品特色</a></li>
    </@s.if>

    
    <@s.if test="prodCProduct.prodProduct.isRoute()">
    	<@s.if test="(!hasMultiJourney() && viewJourneyList!=null &&viewJourneyList.size()>0) || hasMultiJourney()">
   			 <li class="row_3"><a href="javascript:void(0)"><@s.if test="prodCProduct.prodProduct.hasSelfPack()">推荐行程</@s.if><@s.else>行程说明</@s.else></a></li>
   		 </@s.if>
    </@s.if>                
    
    <@s.if test="viewPage.hasContent('COSTCONTAIN')||viewPage.hasContent('NOCOSTCONTAIN') || viewPage.hasContent('RECOMMENDPROJECT') || viewPage.hasContent('SHOPPINGEXPLAIN')">
    <li class="row_4"><a href="javascript:void(0)">费用说明</a></li>
    </@s.if>

     <@s.if test="(productComments!=null && productComments.size>0)"> 
    <li class="row_5"><a href="javascript:void(0)">体验点评</a></li>
    </@s.if>

    <@s.if test="viewPage.hasContent('ORDERTOKNOWN') || viewPage.hasContent('ACITONTOKNOW') || viewPage.hasContent('REFUNDSEXPLANATION') || viewPage.hasContent('SERVICEGUARANTEE') || viewPage.hasContent('PLAYPOINTOUT')">

    
   
    

    <li class="row_6"><a href="javascript:void(0)">重要提示</a></li>
    </@s.if>
    
    <@s.if test="viewPage.hasContent('TRAFFICINFO')">
    <li class="row_7"><a href="javascript:void(0)">交通信息</a></li>
    </@s.if>
    
    <@s.if test="viewPage.hasContent('VISA')||(visaVOList!=null&&visaVOList.size()>0)">                    
    <li class="row_8"><a href="javascript:void(0)">签证/签注</a></li>
    </@s.if>
    
    <@s.if test='comPlace!=null'>
    <li class="row_9"><a href="javascript:void(0)">目的地情报</a></li>
    </@s.if>
    
	<@s.if test="(guestProductList!=null && (guestProductList.productTicketList.size>0 || guestProductList.productSinceList.size>0 || guestProductList.productRouteList.size>0)) || (placeCoordinateHotel!=null && placeCoordinateHotel.size>0)"> 
    <li class="row_10"><a href="javascript:void(0)">相关推荐产品</a></li>
    </@s.if>
    
</ul>
<!--scroll_nav end-->
