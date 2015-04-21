<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/tuan/<@s.property value="prodProduct.productId" />/"> 
<title>${comSeoIndexPage.seoTitle}</title>
<meta name="keywords" content="${comSeoIndexPage.seoKeyword}"/>
<meta name="description" content="${comSeoIndexPage.seoDescription}"/>

<!--生产线引用-->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/base.css,/styles/v5/common.css,/styles/new_v/header-air.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/dialog.css,styles/new_v/ob_login/l_fast_login.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/form.css,/styles/v5/modules/button.css,/styles/v5/modules/table.css,/styles/v5/modules/tags.css,/styles/v5/modules/tip.css,/styles/v5/modules/paging.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/tuangou_dest.css,/styles/v4/modules/calendar.css" />

<#include "/common/coremetricsHead.ftl">
</head>
<body class="lvtg tuangou" data-spy="scroll" data-target=".J_scrollnav"> 
<!-- 公共头部开始  -->
<!-- 
 * 此示例展示，暂时使用了js来展现，
 * 但开发上线，务必请引用头部的那个公共模块，参考其他项目
-->
<@s.set var="pageMark" value="'tuangou'" />
<#include "/common/header.ftl">
<input type="hidden" value="freetour" id="pageName">
<!-- 公共头部结束  -->

<!-- wrap\\ 1 -->
<div class="wrap">
    
    <!--面包屑导航-->
    <div class="crumbs clearfix">
        <p class="crumbs-link">
            <a href="http://www.lvmama.com">驴妈妈首页</a> &gt; 
            <a href="http://www.lvmama.com/tuangou">旅游特卖</a> &gt; 
            <@s.if test='prodProduct.productType=="TICKET"' ><a href="http://www.lvmama.com/tuangou/all/ticket-all-all-1">门票特卖</a></@s.if>
            <@s.if test='prodProduct.productType=="ROUTE"' ><a href="
            <@s.if test='prodProduct.subProductType=="GROUP" || prodProduct.subProductType=="FREENESS" || prodProduct.subProductType=="SELFHELP_BUS"' >http://www.lvmama.com/tuangou/all/surround-all-all-1</@s.if>
            <@s.if test='prodProduct.subProductType=="GROUP_LONG" || prodProduct.subProductType=="FREENESS_LONG"' >http://www.lvmama.com/tuangou/all/china-all-all-1</@s.if>
            <@s.if test='prodProduct.subProductType=="GROUP_FOREIGN" || prodProduct.subProductType=="FREENESS_FOREIGN"' >http://www.lvmama.com/tuangou/all/abroad-all-all-1</@s.if>
            " target="_blank">线路特卖</a></@s.if>
            <@s.if test='prodProduct.productType=="HOTEL"' ><a href="http://www.lvmama.com/tuangou/all/hotel-all-all-1">酒店特卖</a></@s.if>&gt; 
            <a class="current"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodProduct.firstTitle,13)" /></a>
        </p>
        <div class="other-info">
        	<a target="_blank" href="http://www.lvmama.com/tuangouyuyue" >团购预约</a>
<!--             <a href="#">我的团购券</a> -->
<!--             <a href="#">我的抽奖号</a> -->
        </div>
    </div>
    
    <div class="overview clearfix">
        <!--预订左侧内容---开始-->
        <div class="overview_l">
            <div class="titbox">
                <h1 class="tit"><@s.property value="prodProduct.firstTitle" /></h1>
                <#if tagList??>
                    <#list tagList as t>
                        <span class="tags tags-red" <#if t.description!="">tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
                    </#list>
                </#if>
            </div>
            <@s.if test="prodProduct.nextTitle!=''"><p class="overview_l_text"><span class="p_number">编号<i><@s.property value="prodProduct.productId" /></i>|</span><span class="p_text"><@s.property value="prodProduct.nextTitle" />&nbsp;&nbsp;</span></p></@s.if>
            	<div class="yd_box clearfix">
                <div class="img_box">
                    <ul class="img_list">
                    <@s.iterator value="productInfo.get('comPictureList')" status="comPic" var="cpl">
                    <@s.if test="#comPic.index<5">
                        <li <@s.if test="#comPic.count==1">style="display:block"</@s.if>><img src="http://pic.lvmama.com/pics/<@s.property value='#cpl.pictureUrl'/>" width="414" height="276" alt="<@s.property value='#cpl.pictureName'/>"></li>
                    </@s.if>
                    </@s.iterator>
                    </ul>
                    <div class="img_tab_box">
                        <ul class="img_tab">
                        <@s.iterator value="productInfo.get('comPictureList')" status="comPic" var="cpl">
                       	<@s.if test="#comPic.index<5">
                        <li <@s.if test="#comPic.count==1"> class="active"</@s.if>><img src="http://pic.lvmama.com/pics/<@s.property value='#cpl.pictureUrl'/>" width="72" height="48" alt="<@s.property value='#cpl.pictureName'/>"><i class="icon_jt1"></i></li>
                        </@s.if>
                        </@s.iterator>
                        </ul>
                    </div>
                </div>
                
                <div class="yd_text_box">
                    <div class="priceinfo">
                        <dfn><small>￥</small><b>${ prodProduct.sellPriceYuan}</b>起</dfn>
                        <del>￥<i>${prodProduct.marketPriceYuan}</i></del>
                        <@s.if test ="${discount }<10 && ${discount }>=0">
                        <span class="tags101"><@s.property value="discount"/>折</span>
                        </@s.if>
                        <@s.if test="prodProduct.cashRefundY != 0"><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得<span><@s.property value="prodProduct.cashRefundY" /></span>元点评奖金返现。"><em>返</em><i>￥<@s.property value="prodProduct.cashRefundY" /></i></span></@s.if>
                    </div>
                    <div class="saleamount">
                        <span class="tstar"><i style="width:<@s.property value="productInfo.get('avgScore')"/>%;"></i></span>
                        <span class="score"><@s.if test="null != productInfo.get('clsAvgScore')"><b><@s.property value="productInfo.get('clsAvgScore')"/></b>分</span>|</@s.if><span class="score">已售出<b><@s.property value="orderCount"/></b>件</span>
                    </div>
                    <div class="infointro">
                        <dl class="scenery">
                        <@s.if test='prodProduct.productType=="TICKET" && productInfo.prodCProduct.to != null' >
                            <dt>游玩景点</dt>
                            <dd>
                            	<span><@s.property value="productInfo.get('prodCProduct').to.name"/></span>
                            </dd>
                        </@s.if>
                        <@s.if test='prodProduct.productType=="HOTEL" && productInfo.prodCProduct.to != null'>
                            <dt>入住酒店</dt>
                            <dd>
                                <span><@s.property value="productInfo.get('prodCProduct').to.name"/></span>
                            </dd>
                        </@s.if>
	                    
	                    <@s.if test='prodProduct.productType=="ROUTE"'>
	                       <@s.if test ="productInfo.scenicPlace !=''">
	                            <dt>游玩景点</dt>
	                            <dd>
	                                <span><@s.property value="productInfo.get('scenicPlace')"/></span>
	                            </dd>
	                       </@s.if>
	                       <@s.if test ="productInfo.hotelPlace != ''">
	                            <dt>入住酒店</dt>
	                              <dd><@s.property value="productInfo.get('hotelPlace')"/></dd>
	                        </@s.if>
                        </@s.if>
                        
                        </dl>
                        <dl>
                            <dt>服务保障</dt>
                            <dd>
                            	<ul class="serviceitem">
                                    <li><a href="http://www.lvmama.com/public/user_security#tmh" target="_blank"><span class="you"></span><i>精挑细选</i></a></li>
                                    <li><a href="http://www.lvmama.com/public/user_security#tmh" target="_blank"><span class="bao"></span><i>铁定出行</i></a></li>
                                    <li><a href="http://www.lvmama.com/public/user_security#tmh" target="_blank"><span class="true"></span><i>真实点评</i></a></li>
                                </ul>
                            </dd>
                        </dl>
                        
                    </div>
                    <dl class="yd_fangshi clearfix">
                    	<dt></dt>
                        <dd>
                        	<div class="tuan-tit">剩余<span class="countdown"><@s.if test="diff<=0"><span class="countdown">已结束</span></@s.if><@s.else><@s.property  value="diff"/></@s.else></span></div>
                            <div class="btnclick clearfix">
                            	<div class="btnl">
						<@s.if test="diff>15 && productInfo.productStatic!='xxcp'">
							<input name="visitTimeQuick" id="visitTimeQuick" class="input-date" type="hidden" />
							<!--不定期-->
							<@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
								<a class="yd_tangou calendar no-time-price" data-bid="${prodProductBranch.prodBranchId}" ></a>
							</@s.if>
							<@s.else>
								<@s.if test='productInfo.productStatic=="ptcp"' ><a class="yd_tangou calendar" ></a></@s.if>
								<@s.else><a class="yd_tangou yd_wx" ></a></@s.else>
							</@s.else>
							</p>
		        		</@s.if><@s.else><a class="yd_tangou yd_jieshu" ></a></@s.else>
                                <p class="extra">
                                    <a class="collect" href="javascript:favorites();"><span></span><i>收藏商品</i></a>
                                    <div class="bdsharebuttonbox">分享<a class="bds_more" data-cmd="more"></a></div>
                                </p>
                                </div>
                                <@s.if test='prodProduct.productType=="TICKET" && productInfo.prodCProduct.to != null'>
                                <div class="yd_ewm">
                                <img src="http://ticket.lvmama.com/placeQr/<@s.property value="productInfo.prodCProduct.to.placeId" />.png" width="70" height="70" alt="">
                                    <p>用驴妈妈app扫描此二维码<br><span>手机订购更优惠</span></p>
                                    <span class="zhiyin"></span>
                                </div>
                                </@s.if>
                                <@s.if test='prodProduct.productType=="ROUTE"' >
                                <div class="yd_ewm">
                                <img src="http://www.lvmama.com/qrRoute/<@s.property value="prodProduct.productId" />.png" width="63" height="63" alt="">
                                    <p>用驴妈妈app扫描此二维码<br><span>手机订购更优惠</span></p>
                                    <span class="zhiyin"></span>
                                </div>
                                </@s.if>
                        </dd>
                        
                    </dl>
                </div>
            </div>
        </div>
        <!--预订左侧内容---结束-->
        
    </div>
    <#if productInfo.get('viewPage')?? && productInfo.get('viewPage').contents.get('ANNOUNCEMENT')?if_exists && productInfo.get('viewPage').contents.get('ANNOUNCEMENT').contentRn?if_exists >
    <div class="tiptext tip-warning"><span class="tip-icon tip-icon-warning"></span><p>${productInfo.get('viewPage').contents.get('ANNOUNCEMENT').contentRn?if_exists} </p></div>
    </#if>
<!--套餐模块-->
    <@s.if test ="productInfo.get('prodRecommendToPlaceList').size()>0">
    <div class="taocan">
        <h3>相关团购套餐</h3>
        <ul class="taocan_list">
         <@s.iterator value="productInfo.get('prodRecommendToPlaceList')" var="st" >
            <li>
                <div class="taocan_text">
                    <h4><a href="http://www.lvmama.com/tuangou/detail-<@s.property value='#st.productId'/>" target="_blank"><@s.property value='#st.firstTitle'/></a></h4>
                    <p><@s.property value='#st.nextTitle '/></p>
                </div>
                <p class="taocan_list_r">
                    <span class="taocan_jg"><small>￥</small><big><@s.property value='#st.intSellPrice'/></big>起</span>
                    <del>￥<@s.property value='#st.intMarketPrice'/></del>
                    <@s.if test ="#st.discount<10 && #st.discount>=0">
                    <span class="tags tags-lightblue"><@s.property value='#st.discount'/>折</span>
                    </@s.if>
                    
                    
                    
                    <a class="btn btn-mini btn-orange" href="http://www.lvmama.com/tuangou/detail-<@s.property value='#st.productId'/>" target="_blank">去看看</a>
                </p>
            </li>
        </@s.iterator>
        </ul>
    </div>
  </@s.if>
        
    <div id="liyou" class="tab-outer">
                <div class="tab-dest tab-fixed J_scrollnav">
                    <ul class="ul-hor">
                        <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('MANAGERRECOMMEND'))?? && productInfo.get('viewPage').contents.get('MANAGERRECOMMEND').contentRn?if_exists><li class="active"><a href="#liyou">推荐理由</a></li></#if>
                        <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('COSTCONTAIN')?? && productInfo.get('viewPage').contents.get('COSTCONTAIN').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('NOCOSTCONTAIN')?? && productInfo.get('viewPage').contents.get('NOCOSTCONTAIN').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('RECOMMENDPROJECT')?? && productInfo.get('viewPage').contents.get('RECOMMENDPROJECT').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN')?? && productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION')?? && productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION').contentRn?if_exists) >
                        <li><a href="#feiyong">费用说明</a></li></#if>
                        <#if productInfo.get('viewPage')?? &&  (productInfo.get('viewPage').contents.get('FEATURES'))?? && productInfo.get('viewPage').contents.get('FEATURES').contentRn?if_exists><li><a href="#tese">产品特色</a></li></#if>
                        <@s.if test='prodProduct.productType=="ROUTE"'><#if productInfo.get('viewJourneyList')?? &&  (productInfo.get('viewJourneyList'))?? && (productInfo.get('viewJourneyList')).size()&gt;0><li><a href="#xingcheng">行程介绍</a></li></#if></@s.if>
                        <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('ORDERTOKNOWN')?? && productInfo.get('viewPage').contents.get('ORDERTOKNOWN').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('ACITONTOKNOW')?? && productInfo.get('viewPage').contents.get('ACITONTOKNOW').contentRn?if_exists) ||  
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('SERVICEGUARANTEE')?? && productInfo.get('viewPage').contents.get('SERVICEGUARANTEE').contentRn?if_exists) || 
                             productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('PLAYPOINTOUT')?? && productInfo.get('viewPage').contents.get('PLAYPOINTOUT').contentRn?if_exists) >
                        <li><a href="#xuzhi">重要提示</a></li></#if>
                        <#if productInfo.get('viewPage')?? &&  (productInfo.get('viewPage').contents.get('TRAFFICINFO'))?? && productInfo.get('viewPage').contents.get('TRAFFICINFO').contentRn?if_exists><li><a href="#jiaotong">交通信息</a></li></#if>
                        <#if productInfo.get('viewPage')?? &&  (productInfo.get('viewPage').contents.get('VISA'))?? && productInfo.get('viewPage').contents.get('VISA').contentRn?if_exists><li><a href="#qianzheng">签证说明</a></li></#if>
<!--                         <li><a href="#dianping">体验点评</a></li> -->
                    </ul>
                    <@s.if test='productInfo.productStatic=="ptcp"' ><a class="tab-dest-yd calendar" hidefocus="false"></a></@s.if>
                </div>
            </div>
    <div class="dest-main clearfix">
        
        <div class="dest-main-l">
            
            <div class="dmain">

            <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('MANAGERRECOMMEND'))?? && productInfo.get('viewPage').contents.get('MANAGERRECOMMEND').contentRn?if_exists>
                 <div  class="dbox policy">
                    <ul class="liyou">
                         <li>
                            ${productInfo.get('viewPage').contents.get('MANAGERRECOMMEND').contentRn?if_exists} 
                         </li>
                    </ul>
                </div>
            </#if>
     
               
               <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('COSTCONTAIN')?? && productInfo.get('viewPage').contents.get('COSTCONTAIN').contentRn?if_exists) || 
                    productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('NOCOSTCONTAIN')?? && productInfo.get('viewPage').contents.get('NOCOSTCONTAIN').contentRn?if_exists) || 
                    productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('RECOMMENDPROJECT')?? && productInfo.get('viewPage').contents.get('RECOMMENDPROJECT').contentRn?if_exists) || 
                    productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN')?? && productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN').contentRn?if_exists) || 
                    productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION')?? && productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION').contentRn?if_exists) >
                    <div id="feiyong" class="dbox policy">
                        <div class="dtitle">
                            <h3 class="dtit">费用说明</h3>
                        </div>
                        <div class="dcontent">
                            <div class="dactive">
                         <#if (productInfo.get('viewPage').contents.get('COSTCONTAIN'))?? && productInfo.get('viewPage').contents.get('COSTCONTAIN').contentRn?if_exists>
                             <h5>费用包含</h5>
                                 <p>${productInfo.get('viewPage').contents.get('COSTCONTAIN').contentRn?if_exists}</p>
                         </#if>
                         <#if (productInfo.get('viewPage').contents.get('NOCOSTCONTAIN'))?? && productInfo.get('viewPage').contents.get('NOCOSTCONTAIN').contentRn?if_exists>
	                         <h5>费用不包含</h5>
	                             <p>${productInfo.get('viewPage').contents.get('NOCOSTCONTAIN').contentRn?if_exists}</p>
                         </#if>
                         <#if (productInfo.get('viewPage').contents.get('RECOMMENDPROJECT'))?? && productInfo.get('viewPage').contents.get('RECOMMENDPROJECT').contentRn?if_exists>
                             <h5>推荐项目</h5>
                                <p>${productInfo.get('viewPage').contents.get('RECOMMENDPROJECT').contentRn?if_exists}</p>
                         </#if>
                         <#if (productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN'))?? && productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN').contentRn?if_exists>
                             <h5>购物说明</h5>
                                <p>${productInfo.get('viewPage').contents.get('SHOPPINGEXPLAIN').contentRn?if_exists}</p>
                         </#if>
                         <#if (productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION'))?? && productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION').contentRn?if_exists>
                            <h5>退款说明</h5>
                                <p>${productInfo.get('viewPage').contents.get('REFUNDSEXPLANATION').contentRn?if_exists}</p>
                         </#if>
                            </div>
                            
                        </div>
                    </div>
                   </#if>
                
                 <#if  productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('FEATURES'))?? && productInfo.get('viewPage').contents.get('FEATURES').contentRn?if_exists>
                   <div id="tese" class="dbox introduction">
                        <div class="dtitle">
                            <h3 class="dtit"><i class="icon dicon-introduction"></i>产品特色</h3>
                        </div>
                        <div class="dcontent">
                           ${productInfo.viewPage.contents["FEATURES"].content}
                        </div>
                   </div>
                 </#if>
             
              <!--  start -->  
          <@s.if test='prodProduct.productType=="ROUTE"'>
              <#if (productInfo.get('viewJourneyList'))?? && (productInfo.get('viewJourneyList')).size()&gt;0>
              <div id="xingcheng" class="dbox activity">
                    <div class="dtitle">
                        <h3 class="dtit"><i class="icon dicon-activity"></i>行程介绍</h3>
                    </div>
                    <div class="xingcheng_box">
                        <div class="dcontent">
			              <#list productInfo.get('viewJourneyList') as vjl>
			                <h4><span id="route_day${vjl.seq}"></span>第${vjl.seq}天 ${vjl.title?if_exists}</h4>
			                <p>${vjl.contentBr?default("")}</p>
			                <#if (vjl.dinner)??>
			                <p class="p_bg"><b>用餐</b>${vjl.dinner?default("")}</p>
			                </#if>
			                <#if (vjl.hotel)??>
                            <p class="p_bg"><b>住宿</b>${vjl.hotel?default("")}</p>
			                </#if>
				       </#list>
				       </div>
                        <div class="day_box">
                            <ul class="day_list" id="ulDaybox">
                            <#list productInfo.get('viewJourneyList') as vjl>
                                <li <@s.if test='${vjl.seq}'>class="active" </@s.if>><a href="#route_day${vjl.seq}">第${vjl.seq}天</a></li>
                            </#list>
                            </ul>
                        </div>
                     </div>
                </div><!--//.dbox--> 
	              </#if>
	           </@s.if>
              <!-- end -->  
                
            <#if productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('ORDERTOKNOWN')?? && productInfo.get('viewPage').contents.get('ORDERTOKNOWN').contentRn?if_exists) || 
                 productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('ACITONTOKNOW')?? && productInfo.get('viewPage').contents.get('ACITONTOKNOW').contentRn?if_exists) ||  
                 productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('SERVICEGUARANTEE')?? && productInfo.get('viewPage').contents.get('SERVICEGUARANTEE').contentRn?if_exists) || 
                 productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('PLAYPOINTOUT')?? && productInfo.get('viewPage').contents.get('PLAYPOINTOUT').contentRn?if_exists) >
                 <div id="xuzhi" class="dbox introduction">
                    <div class="dtitle">
                        <h3 class="dtit"><i class="icon dicon-introduction"></i>重要提示</h3>
                    </div>
                    <div class="dcontent">
                    <#if (productInfo.get('viewPage').contents.get('ORDERTOKNOWN'))?? && productInfo.get('viewPage').contents.get('ORDERTOKNOWN').contentRn?if_exists>
                    <h4>预订须知</h4>
                    <p>${productInfo.get('viewPage').contents.get('ORDERTOKNOWN').contentRn?if_exists}</p>
                    </#if>
                    <#if (productInfo.get('viewPage').contents.get('ACITONTOKNOW'))?? && productInfo.get('viewPage').contents.get('ACITONTOKNOW').contentRn?if_exists>
                    <h4>行前须知</h4>
                    <p>${productInfo.get('viewPage').contents.get('ACITONTOKNOW').contentRn?if_exists}</p>
                    </#if>
                    <#if (productInfo.get('viewPage').contents.get('SERVICEGUARANTEE'))?? && productInfo.get('viewPage').contents.get('SERVICEGUARANTEE').contentRn?if_exists>
                    <h4>服务保障</h4>
					<p>${productInfo.get('viewPage').contents.get('SERVICEGUARANTEE').contentRn?if_exists}</p>
					</#if>
                    <#if (productInfo.get('viewPage').contents.get('PLAYPOINTOUT'))?? && productInfo.get('viewPage').contents.get('PLAYPOINTOUT').contentRn?if_exists>
                    <h4>游玩提示</h4>
                    <p>${productInfo.get('viewPage').contents.get('PLAYPOINTOUT').contentRn?if_exists}</p>
                    </#if>
                    </div>
                </div>
             </#if>
                
             <#if  productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('TRAFFICINFO'))?? && productInfo.get('viewPage').contents.get('TRAFFICINFO').contentRn?if_exists>
                 <div id="jiaotong" class="dbox traffic">
                    <div class="dtitle">
                        <h3 class="dtit"><i class="icon dicon-traffic"></i>交通指南</h3>
                    </div>
                    <div class="dcontent">
                         ${productInfo.get('viewPage').contents.get('TRAFFICINFO').contentRn?if_exists}
                    </div>
                </div>
              </#if>
                
              <#if  productInfo.get('viewPage')?? && (productInfo.get('viewPage').contents.get('VISA'))?? && productInfo.get('viewPage').contents.get('VISA').contentRn?if_exists>
                 <div id="qianzheng" class="dbox introduction">
                    <div class="dtitle">
                        <h3 class="dtit"><i class="icon dicon-introduction"></i>签证说明</h3>
                    </div>
                    <div class="dcontent">
                         ${productInfo.get('viewPage').contents.get('VISA').contentRn?if_exists}
                    </div>
                </div>
              </#if>
                
<!--                 <div id="dianping" class="dbox comments"> -->
<!--                     <div class="dtitle"> -->
<!--                         <h3 class="dtit"><i class="icon dicon-comments"></i>点评信息</h3> -->
<!--                     </div> -->
<!--                <div class="dcontent"> -->
                        
<!-- <!-- 点评信息概况 --> 
<!-- <div class="cominfo"> -->
<!--     <div class="dscore"> -->
<!--         <span class="comlevel"><dfn><em>好评率</em><i data-mark="dynamicNum" data-level="91.5">00.0</i>%</dfn></span> -->
<!--         <span class="scorebox"> -->
<!--             <p class="score-item"> -->
<!--                 <em>娱乐</em> -->
<!--                 <span class="score-level"><i data-mark="dynamicNum" data-level="5.0"></i></span> -->
<!--                 <em>5.0</em> -->
<!--             </p> -->
<!--             <p class="score-item"> -->
<!--                 <em>安全</em> -->
<!--                 <span class="score-level"><i data-mark="dynamicNum" data-level="4.5"></i></span> -->
<!--                 <em>4.5</em> -->
<!--             </p> -->
<!--             <p class="score-item"> -->
<!--                 <em>规模</em> -->
<!--                 <span class="score-level"><i data-mark="dynamicNum" data-level="3.6"></i></span> -->
<!--                 <em>3.6</em> -->
<!--             </p> -->
<!--             <p class="score-item"> -->
<!--                 <em>人气</em> -->
<!--                 <span class="score-level"><i data-mark="dynamicNum" data-level="4.3"></i></span> -->
<!--                 <em>4.3</em> -->
<!--             </p> -->
<!--         </span> -->
<!--     </div> -->
<!--     <div class="dtext"> -->
<!--         <a href="#" class="btn btn-mini btn-w btn-orange">&nbsp;&nbsp;有订单，写点评，返现金&nbsp;&nbsp;</a> -->
<!--         <p>没订单？<a href="#" class="dlink">发表普通点评</a> 赠50积分，精华追加150积分！</p> -->
<!--     </div> -->
<!-- </div>//.cominfo -->

<!-- <div class="dp_tab_box"> -->
<!-- 	<ul class="dp_tab"> -->
<!--     	<li class="active">特卖点评</li> -->
<!--         <li>临安瑞口众安温泉</li> -->
<!--         <li>临安瑞口众安温泉系统温泉</li> -->
<!--         <li>上海欢乐谷</li> -->
<!--         <li>靖江众安温泉大酒店</li> -->
<!--         <li>特卖点评</li> -->
<!--         <li>临安瑞口众安温泉</li> -->
<!--         <li>临安瑞口众安温泉系统温泉</li> -->
<!--         <li>上海欢乐谷</li> -->
<!--         <li>靖江众安温泉大酒店</li> -->
<!--     </ul> -->
<!--     <a class="btn_gd" href="javascript:;">更多<i></i></a> -->
<!-- </div> -->

<!-- <!-- 点评详情 --> 
<!-- <div class="dcomment"> -->
<!--     <div class="tab-dcom"> -->
<!--         <ul class="ul-hor JS_tabnav"> -->
<!--             <li class="selected"><a href="javascript:;">体验点评<span>（15893）</span></a></li> -->
<!--             <li><a href="javascript:;">精华点评<span>（88）</span></a></li> -->
<!--             <li><a href="javascript:;">普通点评<span>（688）</span></a></li> -->
<!--         </ul> -->
<!--     </div> -->
<!--     <div class="dcombox JS_tabsbox"> -->
<!--         <div class="tabcon selected"> -->
<!--             <div class="dcom-item"> -->
<!--                 <div class="feedbox"> -->
<!--                     <div class="feed-info"> -->
<!--                         <span class="feed-date">2014-01-21</span> -->
<!--                         <span class="feed-user">user20140220</span> -->
<!--                         <p class="feed-score"> -->
<!--                             <span class="icon dicon-good"></span> -->
<!--                             <span class="feed-item">娱乐 5(很好)</span> -->
<!--                             <span class="feed-item">安全 5(好)</span> -->
<!--                             <span class="feed-item">规模 5(很好)</span> -->
<!--                             <span class="feed-item">人气 5(好)</span> -->
<!--                         </p> -->
<!--                     </div> -->
<!--                     <div class="dbackinfo"> -->
<!--                         <span class="tagsback tagsback-blue"><em>退</em><i>&yen;20</i></span> -->
<!--                         <span class="tagsback tagsback-green"><em>送</em><i>150分</i></span> -->
<!--                     </div> -->
<!--                     <div class="dcom-info"> -->
<!--                         <p class="dcom-text"><span class="tags-active">精华点评</span>不错，行程安排很好。给老妈和她的朋友订的。。。挺满意。只是订的时候没有看见行程中的优惠信息。客服和我联系的时候也没提。。。。错过了省钱的机会。。。。。唯一的遗憾。建议下回客服在联系的时候应该主动和客人说一下。这么好的优惠活动，万一说了之后又能吸引更多的游客呢。</p> -->
<!--                     </div> -->
<!--                     <p class="dfeeduser"> -->
<!--                         <a href="javascript:;" class="dcomplus"><i class="icon dicon-plus"></i><em>5</em></a> -->
<!--                         <span class="s-feed">|</span> -->
<!--                         <a href="javascript:;" class="dcomuser"><i class="icon dicon-dcom"></i><em>24</em></a> -->
<!--                     </p> -->
                    
<!--                     <div class="feed-discuss hide"> -->
<!--                         <div class="tiptext tip-default"> -->
<!--                             <div class="tip-arrow tip-arrow-1"> -->
<!--                                 <em>◆</em> -->
<!--                                 <i>◆</i> -->
<!--                             </div> -->
<!--                             <div class="tip-other"> -->
<!--                                 <div class="feeds-reply-box"> -->
<!--                                     <p>回复 lv138*******</p> -->
<!--                                     <p class="dform form-inline"> -->
<!--                                         <textarea maxlength="100" class="textarea"></textarea> -->
<!--                                         <button class="btn btn-small btn-w btn-orange">回复</button> -->
<!--                                     </p> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div>//.feed-discuss -->
<!--                 </div>-//.feedbox -->
                
<!--             </div>//.dcom-item -->
            
<!--             <div class="dcom-item"> -->
<!--                 <div class="feedbox"> -->
<!--                     <div class="feed-info"> -->
<!--                         <span class="feed-date">2014-01-21</span> -->
<!--                         <span class="feed-user">user20140220</span> -->
<!--                         <p class="feed-score"> -->
<!--                             <span class="icon dicon-good"></span> -->
<!--                             <span class="feed-item">娱乐 5(很好)</span> -->
<!--                             <span class="feed-item">安全 5(好)</span> -->
<!--                             <span class="feed-item">规模 5(很好)</span> -->
<!--                             <span class="feed-item">人气 5(好)</span> -->
<!--                         </p> -->
<!--                     </div> -->
<!--                     <div class="dbackinfo"></div> -->
<!--                     <div class="dcom-info"> -->
<!--                         <p class="dcom-text">不错，行程安排很好。给老妈和她的朋友订的。。。挺满意。只是订的时候没有看见行程中的优惠信息。客服和我联系的时候也没提。。。。错过了省钱的机会。。。。。唯一的遗憾。建议下回客服在联系的时候应该主动和客人说一下。这么好的优惠活动，万一说了之后又能吸引更多的游客呢。</p> -->
<!--                     </div> -->
<!--                     <p class="dfeeduser"> -->
<!--                         <a href="javascript:;" class="dcomplus"><i class="icon dicon-plus"></i><em>5</em></a> -->
<!--                         <span class="s-feed">|</span> -->
<!--                         <a href="javascript:;" class="dcomuser"><i class="icon dicon-dcom"></i><em>24</em></a> -->
<!--                     </p> -->
                    
<!--                     <div class="feed-discuss"> -->
<!--                         <div class="tiptext tip-default"> -->
<!--                             <div class="tip-arrow tip-arrow-1"> -->
<!--                                 <em>◆</em> -->
<!--                                 <i>◆</i> -->
<!--                             </div> -->
<!--                             <div class="tip-other"> -->
<!--                                 <div class="feeds-reply-box hide"> -->
<!--                                     <p>回复 lv138*******</p> -->
<!--                                     <p class="dform form-inline"> -->
<!--                                         <textarea maxlength="100" class="textarea"></textarea> -->
<!--                                         <button class="btn btn-small btn-w btn-orange">回复</button> -->
<!--                                     </p> -->
<!--                                 </div> -->
<!--                                 <ul class="feed-comments j-feed-comments"> -->
<!--                                     <li class="comment-item"> -->
<!--                                         <p class="lv-recomment"> -->
<!--                                             <span>驴妈妈回复：</span>你好，请咨询客服处理，感谢您的关注！ -->
<!--                                         </p> -->
<!--                                     </li> -->
<!--                                     <li class="comment-item"> -->
<!--                                         <p> -->
<!--                                             <span>lv133********回复：</span>是啊，我也超级喜欢古木游龙项目，体验超刺激，玩了还想玩。 -->
<!--                                         </p> -->
<!--                                     </li> -->
<!--                                 </ul> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div>//.feed-discuss -->
<!--                 </div>-//.feedbox -->
                
<!--             </div>//.dcom-item -->
            
<!--             <div class="dcom-item"> -->
<!--                 <div class="feedbox"> -->
<!--                     <div class="feed-info"> -->
<!--                         <span class="feed-date">2014-01-21</span> -->
<!--                         <span class="feed-user">user20140220</span> -->
<!--                         <p class="feed-score"> -->
<!--                             <span class="feed-item">娱乐 5(很好)</span> -->
<!--                             <span class="feed-item">安全 5(好)</span> -->
<!--                             <span class="feed-item">规模 5(很好)</span> -->
<!--                             <span class="feed-item">人气 5(好)</span> -->
<!--                         </p> -->
<!--                     </div> -->
<!--                     <div class="dbackinfo"> -->
<!--                         <span class="tagsback tagsback-blue"><em>退</em><i>&yen;20</i></span> -->
<!--                     </div> -->
<!--                     <div class="dcom-info"> -->
<!--                         <p class="dcom-text">不错，行程安排很好。给老妈和她的朋友订的。。。挺满意。只是订的时候没有看见行程中的优惠信息。客服和我联系的时候也没提。。。。错过了省钱的机会。。。。。唯一的遗憾。建议下回客服在联系的时候应该主动和客人说一下。这么好的优惠活动，万一说了之后又能吸引更多的游客呢。</p> -->
<!--                     </div> -->
<!--                     <p class="dfeeduser"> -->
<!--                         <a href="javascript:;" class="dcomplus"><i class="icon dicon-plus"></i><em>5</em></a> -->
<!--                         <span class="s-feed">|</span> -->
<!--                         <a href="javascript:;" class="dcomuser"><i class="icon dicon-dcom"></i><em>24</em></a> -->
<!--                     </p> -->
                    
<!--                     <div class="feed-discuss"> -->
<!--                         <div class="tiptext tip-default"> -->
<!--                             <div class="tip-arrow tip-arrow-1"> -->
<!--                                 <em>◆</em> -->
<!--                                 <i>◆</i> -->
<!--                             </div> -->
<!--                             <div class="tip-other"> -->
<!--                                 <div class="feeds-reply-box"> -->
<!--                                     <p>回复 lv138*******</p> -->
<!--                                     <p class="dform form-inline"> -->
<!--                                         <textarea maxlength="100" class="textarea"></textarea> -->
<!--                                         <button class="btn btn-small btn-w btn-orange">回复</button> -->
<!--                                     </p> -->
<!--                                 </div> -->
<!--                                 <ul class="feed-comments j-feed-comments"> -->
<!--                                     <li class="comment-item"> -->
<!--                                         <p class="lv-recomment"> -->
<!--                                             <span>驴妈妈回复：</span>你好，请咨询客服处理，感谢您的关注！ -->
<!--                                         </p> -->
<!--                                     </li> -->
<!--                                     <li class="comment-item"> -->
<!--                                         <p> -->
<!--                                             <span>lv133********回复：</span>是啊，我也超级喜欢古木游龙项目，体验超刺激，玩了还想玩。 -->
<!--                                         </p> -->
<!--                                     </li> -->
<!--                                 </ul> -->
<!--                             </div> -->
<!--                         </div> -->
<!--                     </div>//.feed-discuss -->
<!--                 </div>-//.feedbox -->
<!--             </div>//.dcom-item -->
            
<!--             <hr> -->
<!--             <div class="paging orangestyle"> -->
<!--                 <div class="pagebox"> -->
<!--                     <span class="prevpage"><i class="larr"></i></span><span class="pagesel">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><span class="pagemore">...</span><a href="#">20</a><a class="nextpage" href="#"><i class="rarr"></i></a> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div>//.tabcon -->
        
<!--         <div class="tabcon"> -->
<!--             tab2 -->
<!--             <div class="paging orangestyle"> -->
<!--                 <div class="pagebox"> -->
<!--                     <span class="prevpage"><i class="larr"></i></span><span class="pagesel">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><span class="pagemore">...</span><a href="#">20</a><a class="nextpage" href="#"><i class="rarr"></i></a> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div>//.tabcon -->
        
<!--         <div class="tabcon"> -->
<!--             tab3 -->
<!--             <div class="paging orangestyle"> -->
<!--                 <div class="pagebox"> -->
<!--                     <span class="prevpage"><i class="larr"></i></span><span class="pagesel">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><span class="pagemore">...</span><a href="#">20</a><a class="nextpage" href="#"><i class="rarr"></i></a> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div>//.tabcon -->
        
<!--     </div>//.dcombox -->
<!-- </div>//.dcomment -->

<!--                     </div> -->
<!--                 </div>//.dbox -->
                


            </div>
        </div>
        
        <div class="dside">
        
<!--             <div class="sidebox dside-tuan"> -->
<!--                 <div class="scontent"> -->
<!--                     <a href="#" class="block"> -->
<!--                         <img src="//placehold.it/228x152" width="228" height="152" alt="某某团购图片" /> -->
<!--                         <p>仅540元即享原价1148住莎海国际酒店，逛上海野生动物园、游新场...</p> -->
<!--                         <div class="zero">0元抽奖</div> -->
<!--                     </a> -->
<!--                     <p> -->
<!--                     <a href="#" class="btn btn-small btn-w btn-orange">抽奖</a> -->
<!--                     <dfn>&yen;<i>0</i></dfn>市场价<del>￥1500</del></p> -->
<!--                 </div> -->
<!--                 <div class="bottom"> -->
<!--                     <p> -->
<!--                         <div class="tuan-tit"><i class="icon dicon-time"></i>剩 <span class="countdown">234223423</span></div> -->
<!--                         <span class="buy"><i>1922</i>人购买</span> -->
<!--                     </p> -->
<!--                 </div> -->
<!--             </div><!--//.sidebox -->
            
            
<!--             <div class="sidebox"> -->
<!--                 <div class="stitle"> -->
<!--                     <h4 class="stit">本周人气爆款</h4> -->
<!--                 </div> -->
<!--                 <div class="scontent"> -->
<!--                     <div class="num1"> -->
<!--                         <a href="#" class="block"> -->
<!--                             <img src="//placehold.it/228x152" width="228" height="152" alt="人气产品" /> -->
<!--                             <p>苏州天颐温泉成人票2张苏州天颐温泉苏州天颐温泉成人票2张苏州天颐...</p> -->
<!--                         </a> -->
<!--                         <p class="pricebox"> -->
<!--                             <dfn>&yen;<i>324</i><span>起/人</span></dfn> -->
<!--                             <del>1586</del> -->
<!--                             <em>3.0折</em> -->
<!--                          </p> -->
<!--                          <span class="num1_icon"></span> -->
<!--                     </div> -->
<!--                     <ul class="ranklist"> -->
                        
<!--                         <li> -->
<!--                             <span class="numrank">2</span> -->
<!--                             <div class="rightpro"> -->
<!--                                 <a href="#">【中国西部大峡谷温泉】灵山秀水中的养生福地，泡温泉情...</a> -->
<!--                                 <p class="pricebox"> -->
<!--                                     <dfn>&yen;<i>324</i><span>起/人</span></dfn> -->
<!--                                     <del>1586</del> -->
<!--                                     <em>3.0折</em> -->
<!--                                  </p> -->
<!--                             </div> -->
<!--                         </li> -->
<!--                         <li> -->
<!--                             <span class="numrank">5</span> -->
<!--                             <div class="rightpro"> -->
<!--                                 <a href="#">【中国西部大峡谷温泉】灵山秀水中的养生福地，泡温泉情...</a> -->
<!--                                 <p class="pricebox"> -->
<!--                                     <dfn>&yen;<i>324</i><span>起/人</span></dfn> -->
<!--                                     <del>1586</del> -->
<!--                                     <em>3.0折</em> -->
<!--                                  </p> -->
<!--                             </div> -->
<!--                         </li> -->
<!--                     </ul> -->
<!--                 </div> -->
                
<!--             </div> -->
            
            <@s.if test="null != productInfo.get('TUANGOU_DETAIL_PRODUCTID')">
            <div class="sidebox">
                <div class="stitle">
                    <h4 class="stit">最近浏览记录</h4>
                </div>
                <div class="scontent">
                    <ul class="review">
                        <@s.iterator value="productInfo.get('TUANGOU_DETAIL_PRODUCTID')" var="st" >  
                        <li>
                            <a class="reviewimage" target="_blank" href="http://www.lvmama.com/tuangou/detail-<@s.property value='#st.placeId'/>"><img src="http://pic.lvmama.com/pics/<@s.property value='#st.imageUrl'/>" width="60" height="40" alt="" /></a>
                            <div class="rightpro">
                                <a target="_blank" href="http://www.lvmama.com/tuangou/detail-<@s.property value='#st.placeId'/>"><@s.property value='#st.name'/></a>
                                <p class="pricebox">
                                    <dfn>&yen;<i><@s.property value='#st.productsPrice'/><small>起</small></i><span></span></dfn>
                                 </p>
                            </div>
                        </li>
                       </@s.iterator>
                    </ul>
                </div>
            </div>
            </@s.if>
            
        <div class="fixed_box"> 
            <div class="channel">
                <h4>更多频道</h4>
                 <a target="_blank" href="http://www.lvmama.com/tuangou/all"><div class="bargin wireless">
                    <h5>精品团购</h5>
                    <p>精品团购，让旅行更精彩！</p>
                </div></a>
                
                <a target="_blank" href="http://www.lvmama.com/tuangou/all/all-all-xsms-1"><div class="bargin">
                    <h5>限时秒杀</h5>
                    <p>热门产品，限时秒杀！</p>
                </div></a>
                
<!--                 <a target="_blank" href="http://www.lvmama.com/tuangou/all/all-all-zyk-1"><div class="bargin car"> -->
<!--                     <h5>周游客</h5> -->
<!--                     <p>自驾我做主，我是周游客！</p> -->
<!--                 </div></a> -->
                
                <a target="_blank" href="http://www.lvmama.com/tuangou/all/all-all-hyr-1"><div class="bargin member">
                    <h5>会员日</h5>
                    <p>会员超低价特权！</p>
                </div></a>
                
            </div>
            <ul class="recommend" id="divAnchor">
            <@s.iterator value="recommendBanner" status="st" var="benner">
            	<@s.if test="#st.index<5">
            	<li><a href="<@s.property value="#benner.url"/>" target="_blank"><img src="<@s.property value="#benner.imgUrl"/>" width="260" height="74"  title="${benner.title}"></a></li>
            	</@s.if>
            </@s.iterator>
            </ul>
        </div>
    </div>
</div> <!-- //.wrap 1 -->


</div>
   <!-- 频道公用js-->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/placeholder.js,/js/v5/modules/pandora-poptip.js,/js/v5/modules/pandora-calendar.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js,/js/v4/login/rapidLogin.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/bt-scrollspy.js,/js/v5/tuangou_dest.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/tuan.js"></script>
 <script language="javascript">
$(function(){
		//不定期产品无需选择游玩日期,不展示时间价格表,直接判断是否可售,转向产品详情页
		$(".no-time-price").live("click", function() {
			var bid = $(this).attr('data-bid');
			var fill_url = "http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=" + bid+"&buyInfo.channel=TUANGOU";
			<#if login>
					window.location.href=fill_url;
        	<#else>
            	showLogin(function(){window.location.href=fill_url;});
        	</#if>
		});
});
var prdId = '${productId}';
</script>
<script type="text/javascript"> 
    var _flag = 0; 
    function favorites(){ 
             $.ajax({ 
                type: "get", 
                url: "http://www.lvmama.com/check/login.do", 
                dataType:"html", 
                success: function(successStr){ 
                    if(successStr == 'true'){ 
                        $.ajax({ 
                            type:"get", 
                            async:false, 
                            dataType:"jsonp", 
                            jsonp:"jsoncallback", 
                            jsoncallback:"success_jsonpCallback", 
                            url:"http://www.lvmama.com/myspace/share/tuangouFavorite.do", 
                            data:{ 
                                objectId :<@s.property value="prodProduct.productId"/>, 
                                objectName :'<@s.property value="prodProduct.firstTitle"/>' 
                                    }, 
                                    success:function(json){ 
                                        if(json.success == "true"){ 
                                        	  alert("收藏成功"); 
                                        	  }else if(json.success == "false"){ 
                                        		  alert("已经收藏过此景点"); 
                                        		  } 
                                        }, 
                                        error : function(){ 
                                        	  alert("收藏失败"); 
                                        	  } 
                                        }); 
                        }else{ 
                        	  _flag = 1; 
                        	  $(UI).ui("login"); 
                        } 
                   } 
             }); 		
        } 
</script>


<@s.if test="diff>15 && prodProductBranch != null">
	<#include "/WEB-INF/pages/group/include/newtuangou.ftl">
</@s.if>

<!--公共底部-->
<script src="http://pic.lvmama.com/js/v4/copyright.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>



<script>
        	cmCreatePageviewTag("团购产品详情页-"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productInfo.get('prodCProduct').prodProduct.productName)"  escape="false"/>", "N0001", null, null);
    		cmCreateProductviewTag("${productInfo.get('prodCProduct').prodProduct.productId?if_exists}", "<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productInfo.get('prodCProduct').prodProduct.productName)"  escape="false"/>", "<@s.property value="productInfo.get('prodCProduct').prodProduct.subProductType"  escape="false"/>", "${productInfo.get('prodCProduct').prodProduct.subProductType?if_exists}");
    </script>
</body>
</html>
