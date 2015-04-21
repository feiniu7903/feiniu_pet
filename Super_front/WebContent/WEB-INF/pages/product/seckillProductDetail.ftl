<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
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
        	<a tager="_blank" href="http://www.lvmama.com/tuangouyuyue" >团购预约</a>
<!--             <a href="#">我的团购券</a> -->
<!--             <a href="#">我的抽奖号</a> -->
        </div>
    </div>
    
    <div class="overview clearfix">
    	<!--预订左侧内容---开始-->
    	<div class="overview_l">
            <div class="titbox">
                <h1 class="tit"><@s.property value="prodProduct.firstTitle" /></h1><span class="tags tags-red">折扣</span><span class="tags tags-red">促销</span>
            </div>
            <p class="overview_l_text"><span class="p_number">编号<i><@s.property value="prodProduct.productId" /></i>|</span><span class="p_text"><@s.property value="prodProduct.nextTitle" /></span></p>
            <div class="yd_box clearfix">
            	<div class="img_box">
                	<i class="icon2 icon-tgx"></i>
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
                  		<dfn><small>￥</small><b>${prodProduct.sellPriceYuan}</b></dfn>
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
                        	<@s.if test="seckillStatus=='starting'">
                        		<div class="tiptext tip-warning"><span class="tip-icon tip-icon-time"></span>距离结束<span class="countdown">${seckillMilliSeconds}</span></div>
                        	</@s.if>
                        	<@s.elseif test="seckillStatus=='saleOver'">
                        		<div class="tiptext tip-warning"><span class="tip-icon tip-icon-time"></span>距离结束<span class="countdown">${seckillMilliSeconds}</span><@s.if test="${NoPayNum}>0">还有<b>${NoPayNum}</b>人未支付，您还有机会哦！</@s.if></div>
                        	</@s.elseif>
                        	<@s.elseif test="seckillStatus=='willStart'">
                        		<div class="tiptext tip-warning"><span class="tip-icon tip-icon-time"></span>距开抢<span class="countdown">${seckillMilliSeconds}</span><@s.date name="prodSeckillRule.startTime" format="yyyy-MM-dd HH:mm:ss"/>开抢</div>
                        	</@s.elseif>
                        	<@s.elseif test="seckillStatus=='over'">
                        		<div class="tiptext tip-warning"><span class="tip-icon tip-icon-warning"></span>很抱歉，抢购已结束！</div>
                        	</@s.elseif>
                        <dl>
                            <dt>购买数量</dt>
                            <dd>
                            	<p class="num_box"><a href="javascript:void(0);" onclick="dcrBuyNum();" class="btn_down js_down">-</a><input class="js_isNumber" name="buyInfo.buyNum.product_${branchId}" type="text" value="1"/><a href="javascript:void(0);" onClick="addBuyNum(<#if (prodProductBranch.maximum>0) >${prodProductBranch.maximum}<#else>${prodProductBranch.stock}</#if>,${remainNum});" class="btn_up js_up">+</a></p>限购<span class="orange"><#if (prodProductBranch.maximum>0) >${prodProductBranch.maximum}<#else>${prodProductBranch.stock}</#if></span>件/剩余<span class="orange">${remainNum}</span>件
                            </dd>
                        </dl>
                        <@s.if test="seckillVerifyCodeFlag == true">
	                        <@s.if test="seckillStatus=='starting'">
								<dl class="yzm">
	               					<dt>验证码</dt>
		                            <dd>
		                            	<input type="text" name="verifycode" class="seckill_verifycode"/><img class="buy-check-img" id="image" src="/account/chinesecode.htm" width="100" height="30"><a class="buy-check-change" href="#" onClick="refreshCheckCode('image');return false;">换一张</a>
		                            </dd>
	        					</dl>
	                        </@s.if>
                        </@s.if>
                    </div>
                    <dl class="yd_fangshi clearfix">
                    	<dt></dt>
                        <dd>
                            <div class="btnclick clearfix">
                            	<div class="btnl">
                                    <!--<a class="yd_tangou yd_qiang" href="#"></a><!--按钮有4种状态：yd_qiang,yd_jijiang,yd_jieshu,yd_wan，-->
                             	<!--4种状态的判断条件-->
	                           	<@s.if test="diff>15">
									<input name="visitTimeQuick" id="visitTimeQuick" class="input-date" type="hidden" />
									<!--不定期-->
									<@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
										<a class="yd_tangou no-time-price" data-bid="${prodProductBranch.prodBranchId}" ></a>
									</@s.if>
									<@s.else>
										<@s.if test="seckillStatus=='starting'">
											<input id="seckillBtn" type="hidden" class="seckillBtn"/>
											<a class="yd_tangou seckill-time-price yd_qiang  calendar" style="cursor: hand;cursor: pointer;"></a>
                        				</@s.if>
                        				<@s.elseif test="seckillStatus=='willStart'">
                        					<a class="yd_tangou yd_jijiang" ></a>
                        				</@s.elseif>
                        				<@s.elseif test="seckillStatus=='saleOver'">
                        					<a class="yd_tangou yd_wan" ></a>
                        				</@s.elseif>
                        				<@s.else>
                        					<a class="yd_tangou yd_jieshu" ></a>
                        				</@s.else>
										
									</@s.else>
									</p>
			        			</@s.if>
							
                                    <p class="extra">
	                                    <a class="collect" href="javascript:favorites();"><span></span><i>收藏商品</i></a>
	                                    <div class="bdsharebuttonbox">分享<a href="#" class="bds_more" data-cmd="more"></a></div>
                                	</p>
                                </div>
                                <div class="yd_ewm">
                                    <img src="http://www.lvmama.com/productQr/<@s.property value="prodProduct.productId" />.png" width="70" height="70" alt="">
                                    <p>用驴妈妈app扫描此二维码<br><span>手机订购更优惠</span></p>
                                    <span class="zhiyin"></span>
                                </div>
                            </div>
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
                    <span class="taocan_jg"><small>￥</small><big><@s.property value='#st.intSellPrice'/></big></span>
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
                             <h5>费用包含</h5>
                                 <p>${productInfo.get('viewPage').contents.get('COSTCONTAIN').contentRn?if_exists}</p>
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
                           ${productInfo.get('viewPage').contents.get('FEATURES').contentRn?if_exists}
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
                        <h3 class="dtit"><i class="icon dicon-introduction"></i>预订须知</h3>
                    </div>
                    <div class="dcontent">
                    <h4>预订须知</h4>
                    <p>${productInfo.get('viewPage').contents.get('ORDERTOKNOWN').contentRn?if_exists}</p>
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
            </div>
        </div>
        
        <div class="dside">
            
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
                                    <dfn>&yen;<i><@s.property value='#st.productsPrice'/></i><span></span></dfn>
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
	                
<!-- 	                 <a target="_blank" href="http://www.lvmama.com/tuangou/all/all-all-zyk-1"><div class="bargin car"> -->
<!-- 	                     <h5>周游客</h5> -->
<!-- 	                </div></a> -->
	                
	                <a target="_blank" href="http://www.lvmama.com/tuangou/all/all-all-hyr-1"><div class="bargin member">
	                    <h5>会员日</h5>
	                    <p>会员超低价特权！</p>
	                </div></a>
	                
	            </div>
	            <ul class="recommend" id="divAnchor">
	            	<@s.iterator value="recommendBanner" status="st" var="benner">
	            	<@s.if test="#st.index<5">
	            	<li><a href="<@s.property value="#benner.url"/>" target="_blank"><img src="<@s.property value="#benner.imgUrl"/>" width="259" height="73"  title="${benner.title}"></a></li>
	            	</@s.if>
	            </@s.iterator>
	            </ul>
            </div>
    	</div>
</div> <!-- //.wrap 1 -->

  <!-- 频道公用js-->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/placeholder.js,/js/v5/modules/pandora-poptip.js,/js/v5/modules/pandora-calendar.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js,/js/v4/login/rapidLogin.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/bt-scrollspy.js,/js/v5/tuangou_dest.js"></script>
<script type="text/javascript" src="http://www.lvmama.com/js/tuan.js"></script>
 <script language="javascript">
 <!--不定期-->
	<@s.if test="prodProduct.IsAperiodic() && prodProductBranch!=null">
		<form method="post" id="buyForm2" action="/buy/fill.do"}">
			<input type="hidden" name="buyInfo.productId" id="productIdHidden" value="${prodProduct.productId}"></input>
			<input type="hidden" name="buyInfo.prodBranchId" id="productBranchIdHidden" value="${prodProductBranch.prodBranchId}"></input>
			<input type="hidden" name="buyInfo.productType" value="${prodProduct.productType?if_exists}"></input>
			<input type="hidden" name="buyInfo.subProductType" value="${prodProduct.subProductType?if_exists}"></input>
			<input type="hidden" ordNum="ordNum" id="param${prodProduct.productId}" name="buyInfo.buyNum.product_${prodProductBranch.prodBranchId}" value=""></input>
			<input type="hidden" name="buyInfo.visitTime"  id="hiddenVisitTime"/>
			<#if prodProduct.days??> 
			<input type="hidden" name="buyInfo.days" value="${prodProduct.days}" />
			</#if>
			<input type="hidden" name="buyInfo.channel" id="hiddenchannel" value="TUANGOU"/>
			<input type="hidden" name="tn"  id="tn" value="${tn}"/>
			<input type="hidden" name="baiduid" id="baiduid" value="${baiduid}"/>
			<input type="hidden" name="buyInfo.seckillId" id="seckillId" value="${prodSeckillRule.id}"/>
			<input type="hidden" name="buyInfo.seckillBranchId" id="seckillBranchId" value="${branchId}"/>
			<input type="hidden" name="buyInfo.waitPayment" id="waitPayment" value="${prodSeckillRule.payValidTime}"/>
		</form>
	</@s.if>

$(function(){
		//不定期产品无需选择游玩日期,不展示时间价格表,直接判断是否可售,转向产品详情页
		$(".no-time-price").live("click", function() {
		var buyNum = $(".js_isNumber").val();
		var verifycode = $(".seckill_verifycode").val();
		var url="http://www.lvmama.com/fill/seckill.do?buyInfo.seckillId=${prodSeckillRule.id}&buyInfo.seckillBranchId=${branchId}&buyInfo.waitPayment=${prodSeckillRule.payValidTime}&buyInfo.buyNum.product_${branchId}="+buyNum+"&verifycode="+verifycode;
			$.ajax({
                 url: url,
                 type: "POST",
                 dataType: "jsonp",
                 jsonp: 'callback',
                 success: function (result) {
                 	if (result.flag) {
                 			var bid = $(this).attr('data-bid');
							//var fill_url = "http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=" + bid+"&buyInfo.channel=TUANGOU";
							var buyNum = $(".js_isNumber").val();
        					$("input[ordNum='ordNum']").val(buyNum);
        				<#if login>
    						$('#buyForm2').submit();
    					<#else>
        					showLogin(function(){
        					$('#buyForm2').submit();
        				});
    					</#if>
	        	 		} else {
	        	 			alert(result.msg);
	        	 		}
                  }
             });
			
		});
		
});
var prdId = '${productId}';


//切换验证码 
function refreshCheckCode(s) { 
var elt = document.getElementById(s); 
elt.src = elt.src + "?_=" + (new Date).getTime(); 
}
//购买数量加减
function addBuyNum(Max,remainNum){
	var add = $(".js_isNumber").val();
	add++;
	if(remainNum<Max){
		Max = remainNum;
	}
	if(add<=Max){
		$(".js_isNumber").attr("value",add); 
	}
}
function dcrBuyNum(){
	var dcr = $(".js_isNumber").val();
	dcr--;
	if(dcr>0){
		$(".js_isNumber").attr("value",dcr); 
	}
}
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
<@s.if test="diff>15 && prodProductBranch != null&&!prodProduct.IsAperiodic()">
	<#include "/WEB-INF/pages/group/include/seckill.ftl">
</@s.if>

<!--公共底部-->
<script src="http://pic.lvmama.com/js/v4/copyright.js"></script>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
<script>
        	cmCreatePageviewTag("秒杀产品详情页-"+"${prodProduct.productId?if_exists}", "N0001", null, null);
    		cmCreateProductviewTag("${prodProduct.productId?if_exists}", "秒杀商品", "${prodProduct.subProductType?if_exists}", "${prodProduct.subProductType?if_exists}");
    </script>
</body>
</html>
