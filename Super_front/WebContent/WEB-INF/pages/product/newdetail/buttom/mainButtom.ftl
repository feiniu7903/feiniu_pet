    <!--若是自主打包产品，显示“可选行程”标签-->    
    <@s.if test="prodCProduct.prodProduct.hasSelfPack()">
    <link href="http://pic.lvmama.com/styles/new_v/ob_detail/l_superfree.css?r=8610" type="text/css" rel="stylesheet" />      
    <#include "/WEB-INF/pages/product/newdetail/buttom/selfPack/OJView.ftl">
    </@s.if>
    <!--标签头部-->
    <#include "/WEB-INF/pages/product/newdetail/buttom/tabHeader.ftl">
    
    <!--产品特色-->
    <@s.if test="viewPage.hasContent('FEATURES')">
    <#include "/WEB-INF/pages/product/newdetail/buttom/productQuality.ftl">
    </@s.if>
    
    
    
    <!--若是自由行，显示“行程说明”标签-->
    <@s.if test="prodCProduct.prodProduct.isRoute()">
    	<@s.if test="(!hasMultiJourney() && viewJourneyList!=null &&viewJourneyList.size()>0) || hasMultiJourney()">
    		<#include "/WEB-INF/pages/product/newdetail/buttom/journeyInfo.ftl">
    	</@s.if>
    </@s.if>

    <!--费用说明-->
    <@s.if test="viewPage.hasContent('COSTCONTAIN') || viewPage.hasContent('NOCOSTCONTAIN') || viewPage.hasContent('RECOMMENDPROJECT') || viewPage.hasContent('SHOPPINGEXPLAIN') || hasMultiJourney()">
    <#include "/WEB-INF/pages/product/newdetail/buttom/feeRemark.ftl">
    </@s.if>
    
    
    <!--产品点评-->
    <@s.if test="(productComments!=null && productComments.size>0)"> 
    <#include "/WEB-INF/pages/product/newdetail/buttom/productComment.ftl">
    </@s.if>
    
    <@s.if test='!prodCProduct.prodProduct.IsAperiodic()'>
    <!--快速预订-->
    	<#include "/WEB-INF/pages/product/newdetail/buttom/quickBooker.ftl">
    </@s.if>
    
	<!--AdForward Begin:-->
	<iframe marginheight="0" marginwidth="0" frameborder="0" width="1000" height="80" scrolling="no" src="http://lvmamim.allyes.com/main/s?user=lvmama_2014|product_2014|product_2014_banner&db=lvmamim&border=0&local=yes"></iframe>
	<!--AdForward End-->
	
    <!--重要提示-->
    <@s.if test="viewPage.hasContent('ORDERTOKNOWN') || viewPage.hasContent('ACITONTOKNOW') || viewPage.hasContent('REFUNDSEXPLANATION') || viewPage.hasContent('SERVICEGUARANTEE') || viewPage.hasContent('PLAYPOINTOUT')">
    <#include "/WEB-INF/pages/product/newdetail/buttom/importantNotice.ftl">
    </@s.if>
    
    <!--交通信息-->
    <@s.if test="viewPage.hasContent('TRAFFICINFO')">
    <#include "/WEB-INF/pages/product/newdetail/buttom/trafficInfo.ftl">
    </@s.if>
        
    <!--订购流程-->
    <#include "/WEB-INF/pages/product/newdetail/buttom/orderNotes.ftl">
    
    <!--签证/签注-->
    <@s.if test="viewPage.hasContent('VISA')||(visaVOList!=null&&visaVOList.size()>0)">
    <#include "/WEB-INF/pages/product/newdetail/buttom/foreignNotes.ftl">
    </@s.if>
    
    <!--目的地情报-->
    <@s.if test='comPlace!=null'>
    <#include "/WEB-INF/pages/product/newdetail/buttom/destInfo.ftl">
    </@s.if>
    
    <!--相关产品推荐-->
    <@s.if test="(guestProductList!=null && (guestProductList.productTicketList.size>0 || guestProductList.productSinceList.size>0 || guestProductList.productRouteList.size>0)) || (placeCoordinateHotel!=null && placeCoordinateHotel.size>0)"> 
    <#include "/WEB-INF/pages/product/newdetail/buttom/recommentProd.ftl">
    </@s.if>
