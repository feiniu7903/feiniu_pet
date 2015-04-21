<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/ticket/piao-<@s.property value="place.placeId"/>/">
<title><@s.property value="scenicVo.seoTitle"/></title>
<link rel="shortcut icon" href="http://ticket.lvmama.com/favicon.ico" type="image/x-icon" > 
<meta name="keywords" content="<@s.property value="scenicVo.seoKeyword"/>">
<meta name="description" content="<@s.property value="scenicVo.seoDescription"/>">

<!--生产线引用-->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/base.css,/styles/v5/common.css,/styles/new_v/header-air.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/form.css,/styles/v5/modules/button.css,/styles/v5/modules/table.css,/styles/v5/modules/tags.css,/styles/v5/modules/tip.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/dialog.css,/styles/new_v/ob_login/l_fast_login.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v5/dest.css" />
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css" />
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>
<body class="dest" data-spy="scroll" data-target=".J_scrollnav">

<!-- 公共头部开始  -->
<!-- 
 * 此示例展示，暂时使用了js来展现，
 * 但开发上线，务必请引用头部的那个公共模块，参考其他项目
-->
    <!--头部 S-->
    <@s.set var="pageMark" value="'destScenic'" />
    <#include "/WEB-INF/pages/common/header.ftl">
<!-- 公共头部结束  -->


<!-- wrap\\ 1 -->
<div class="wrap">
    <!--面包屑导航-->
    <div class="crumbs clearfix">
        <p class="crumbs-link">
            <a href="http://www.lvmama.com/ticket">景点门票</a> &gt; 
            <@s.if test="null!=scenicVo.grandfatherPlace "><a href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.grandfatherPlace.codeId">${scenicVo.grandfatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.grandfatherPlace.name?if_exists}</@s.else>.html">${scenicVo.grandfatherPlace.name?if_exists}景点门票</a> &gt;</@s.if> 
            <a href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.fatherPlace.codeId">${scenicVo.fatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.fatherPlace.name?if_exists}</@s.else>.html">${scenicVo.fatherPlace.name?if_exists}景点门票</a> &gt; 
            <a class="current">${place.name?if_exists}</a>
        </p>
     </div>
    
    <div class="overview">
        <div class="dtitle clearfix">
            <span class="xorder">
                <span class="price"><dfn>&yen;<i>${scenicVo.lowerPrice?if_exists}</i></dfn>起</span>
                <a href="#destorder" class="btn btn-large cbtn-orange" onClick="cmCreateElementTag('门票预订_<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>_立即预订','新版门票预订点击');"><span class="btn-text">立即预订</span><i class="icon icon-r dicon-rarr"></i></a>
            </span>
            <div class="titbox">
                <h1 class="tit">${place.name?if_exists}</h1>
            </div>
        </div>
        <div class="dcontent clearfix">
            <ul class="dimg ul-hor J_photo">
                <@s.iterator value="scenicVo.placePhoto" var="v" status="st">
                <@s.if test="#st.count<=3">
	                <li <@s.if test="#st.first">class="big-img"</@s.if> >
	                   <img src="${absoluteImageUrl}" data-big-img="${absoluteImageUrl}"
	                    <@s.if test="#st.first">width="405" height="270"</@s.if><@s.else>width="198" height="132" </@s.else> alt="<@s.property value="place.name"/>">
	                </li>
	             </@s.if>
                </@s.iterator>
            </ul>
            <div class="dinfo">
                <div class="sec-info">
                    <div class="sec-inner">
                        <@s.if test="null!=trafficInfo"><a href="#traffic" class="xlink"><i class="icon dicon-local"></i>地图</a></@s.if>
                        <@s.if test="null!=place.address">
                        <dl class="dl-hor">
                            <dt>景点地址</dt>
                            <dd><p class="linetext">${place.address?if_exists}</p></dd>
                        </dl>
                        </@s.if>
                        
                         <@s.if test="null!=place.scenicOpenTime">
                          <dl class="dl-hor">
                            <dt>入园时间</dt>
                            <dd class="xlesstime">
                                ${place.scenicOpenTime?if_exists}
                            </dd>
                            <dd class="xalltime">
                                ${place.scenicOpenTime?if_exists}
                            </dd>
                        </dl>
                        </@s.if>
                        
                        <@s.if test="null!=scenicVo.placeActivity&&(scenicVo.placeActivity.size()>0)" >
                        <dl class="dl-hor link-active">
                            <dt><span class="tags-active">景点活动</span></dt>
                            <dd>
                                 <@s.iterator value="scenicVo.placeActivity" var="bean" status="st">
                                    <@s.if test="#st.count<=2">
                                    <a href="#activity">${bean.title?if_exists}</a>
                                    </@s.if>
                                 </@s.iterator>
                             </dd>
                        </dl>
                        </@s.if>
                        
                        
                     <@s.if test="null!=place.kuaiSuRuYuan ||null!=place.guiJiuPei ||null!=place.suiShiTui ||null!=place.ruYuanBaoZhang">
                        <dl class="dl-hor service_list"> 
							<dt>服务保障</dt> 
							<dd> 
							<@s.if test="null!=place.ruYuanBaoZhang"> <a class="service_poptip service_list_ensure" tip-content="顺利入园，快速服务" href="http://www.lvmama.com/public/user_security#mp" target="_blank">入园保证</a> </@s.if>
							<@s.if test="null!=place.kuaiSuRuYuan "><a class="service_poptip service_list_fast" tip-content="便捷入园，无需排队" href="http://www.lvmama.com/public/user_security#mp" target="_blank">快速入园</a> </@s.if>
							<@s.if test="null!=place.suiShiTui"><a class="service_poptip service_list_return" tip-content="无条件退，放心订票" href="http://www.lvmama.com/public/user_security#mp" target="_blank">随时退</a> </@s.if>
							<@s.if test="null!=place.guiJiuPei"><a class="service_poptip service_list_indemnity" tip-content="买贵就赔，保证便宜" href="http://www.lvmama.com/public/user_security#mp" target="_blank">贵就赔</a> </@s.if>
							</dd> 
						</dl> 
                        </@s.if>
                    </div>
                </div>
                <div class="comment-info">
                    <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
						<@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                         <dl class="dl-hor">
                            <dt>好 评 率</dt>
                            <dd><span class="dnum"><i class="orange" id="totalCmt_1"></i>人真实评价</span><dfn><i>${avgScore * 20}</i>%</dfn></dd>
                        </dl>
                        </@s.if>
                     </@s.iterator>
                    <div class="dot-line"></div>
                    <a class="quote" href="#comments" title="查看详细">
                        <i class="icon dicon-comment"></i>
                        <i class="qstart">“</i>
                        <i class="qend">”</i>
                        <p><@s.if test="scenicVo.lastcommonCmtCommentVO!=null">${scenicVo.lastcommonCmtCommentVO.content?if_exists}</@s.if><@s.else>暂无点评</@s.else></p>
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <div class="dest-main">
        <div id="destorder" class="tab-outer">
            <div class="tab-dest tab-fixed J_scrollnav">
                <ul class="ul-hor">
                     <@s.if test="(null!=scenicVo.ticketProductList.get('SINGLE')&&scenicVo.ticketProductList.get('SINGLE').size()>0)
                         ||(null!=scenicVo.ticketProductList.get('UNION')&&scenicVo.ticketProductList.get('UNION').size()>0)
                        ||(null!=scenicVo.ticketProductList.get('SUIT')&&scenicVo.ticketProductList.get('SUIT').size()>0) " >
                        <li class="active"><a href="#destorder">订票</a></li>
                        </@s.if>
                    <@s.if test="null!=scenicVo.freeNessAndHotelSuitProductList&&scenicVo.freeNessAndHotelSuitProductList.size()>0">
                    <li><a href="#dfreetour">自由行</a></li></@s.if>
                    <@s.if test="null!=scenicVo.groupAndBusTabNameList&&scenicVo.groupAndBusTabNameList.size()>0" >
                     <li><a href="#dtuangou">跟团游</a></li></@s.if>
                     <@s.if test="null != place.orderNotice"><li><a href="#policy">预订须知</a></li></@s.if>
                     <@s.if test="null!=scenicVo.placeActivity&&scenicVo.placeActivity.size()>0"><li><a href="#activity">景点活动</a></li></@s.if>
                    <@s.if test="null!=descripTion"><li><a href="#introduction">景点介绍</a></li></@s.if>
                    <@s.if test="null!=trafficInfo"><li><a href="#traffic">交通指南</a></li></@s.if>
                     <li><a href="#comments" >用户点评<span id="totalCmt">(0)</span></a></li>
                </ul>
            </div>
        </div>
        <div class="dcontent dorder-list">
        	    <div class="tiptext tip-warning">
        	    <span class="ui-close">×</span>
        	    <span class="tip-icon tip-icon-warning"></span>
        	    <p>门票限网上及手机客户端预订，不接受电话预订。</p></div>	
         <@s.if test="null!=scenicVo.noticeList&&scenicVo.noticeList.size()>0" >
            <@s.iterator value="scenicVo.noticeList" var="notice">
            	<div class="tiptext tip-warning"><span class="ui-close">×</span><span class="tip-icon tip-icon-warning"></span><p>${noticeContent?if_exists}</p></div>
            </@s.iterator>
         </@s.if>   
            
<div class="dpro-list">
<form id="orderFillForm"  method="post">
	<input id="productIdNew" type="hidden"  name="buyInfo.productId">
	<input id="productBranchIdHidden" type="hidden" name="buyInfo.prodBranchId">
	<input id="productType" type="hidden" name="buyInfo.productType">
	<input id="subProductType" type="hidden" name="buyInfo.subProductType">
	<input id="buyNum" type="hidden">
	
</form>
    <table class="ptable table-full">
        <thead class="pttit">
            <tr>
                <td></td>
                <td>
                    <dl class="ptditem">
                        <dd class="pdpaytype">支付方式</dd>
                        <dd class="pdlvprice">驴妈妈价</dd>
                        <dd class="pdprice">市场价</dd>
                        <dt class="pdname">产品名称</dt>
                    </dl>
                </td>
            </tr>
        </thead>
        <!-- 单门票-->
         <@s.if test="null!=scenicVo.ticketProductList.get('SINGLE')&&scenicVo.ticketProductList.get('SINGLE').size()>0" >
        <tbody class="ptbox">
            <tr>
                <td class="ptdname">
                    <div class="ptname"><h5>单门票</h5></div>
                </td>
                <td>
                    <div class="ptdlist">
                        <div class="pdlist-inner">
                         <@s.iterator value="scenicVo.ticketProductList.get('SINGLE')" var="v1" status="st">    
                         <@s.iterator value="branchSearchInfo" var="v2" status="st">
<dl class="ptditem">
    <dd class="pdpaytype">
        <span class="ipay ipay-online"><i class="itype"><#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></i><a class="btn btn-w btn-small btn-orange" href="javascript:;" date_productId="${v2.productId?if_exists?c}"  date_type="TICKET" date_prodBranchId="${v2.prodBranchId?if_exists?c}" date_subProductType="${v2.subProductType?if_exists}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${v2.productId?if_exists?c}','${v2.productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${v2.sellPriceYuan?if_exists}','${v2.subProductType?if_exists}')">预订</a></span>
    </dd>
    <dd class="pdlvprice">
        <dfn>&yen;<i>${v2.sellPriceYuan?if_exists}</i></dfn>
        <#include "/WEB-INF/pages/ticket/commentFanXian_tags.ftl" />
    </dd>
    <dd class="pdprice"><del>&yen;${v2.marketPriceInteger?if_exists}</del></dd>
    <dt class="pdname">
        <a href="javascript:;" class="ptlink" title="<@s.property value="productSearchInfo.productName" />"><@s.property value="productSearchInfo.productName" /> - ${v2.branchName?if_exists} </a>
         <#include "/WEB-INF/pages/ticket/ticket_tags.ftl" />
    </dt>
    <dd class="pdetails">
        <div class="tiptext tip-light">
            <div class="tip-arrow tip-arrow-11">
                <em>◆</em>
                <i>◆</i>
            </div>
            <div class="tip-other">
            <@s.if test="null!=#v2.descriptionWithTag" >
                 ${v2.descriptionWithTag?if_exists?replace('</br>','')} 
            </@s.if>
            </div>
            <a href="javascript:;" class="view-details">收起</a>
        </div><!--//.tiptext-->
    </dd><!--//.pdetails-->
</dl>                        </@s.iterator>
                       </@s.iterator>
                         </div>
                    </div>
                </td>
            </tr>
        </tbody>
          </@s.if>   
         <@s.if test="null!=scenicVo.ticketProductList.get('UNION')&&scenicVo.ticketProductList.get('UNION').size()>0" >
            <tbody class="ptbox">
                <tr>
                    <td class="ptdname">
                        <div class="ptname"><h5>组合套餐</h5></div>
                    </td>
                    <td>
                        <div class="ptdlist">
                            <div class="ptdlist-inner">
                         <@s.iterator value="scenicVo.ticketProductList.get('UNION')" var="v1" status="st">    
                             <@s.iterator value="branchSearchInfo" var="v2" status="st">    
<dl class="ptditem">
    <dd class="pdpaytype">
		<span class="ipay ipay-online"><i class="itype"><#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></i><a href="javascript:;" class="btn btn-w btn-small btn-orange" date_productId="${v2.productId?if_exists?c}"  date_type="${v2.branchType?if_exists}" date_prodBranchId="${v2.prodBranchId?if_exists?c}" date_subProductType="${v2.subProductType?if_exists}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${v2.productId?if_exists?c}','${v2.productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${v2.sellPriceYuan?if_exists}','${v2.subProductType?if_exists}')">预订</a></span>
    </dd>
    <dd class="pdlvprice">
        <dfn>&yen;<i>${v2.sellPriceYuan?if_exists}</i></dfn>
		<#include "/WEB-INF/pages/ticket/commentFanXian_tags.ftl" />
    </dd>
    <dd class="pdprice"><del>&yen;${v2.marketPriceInteger?if_exists}</del></dd>
    <dt class="pdname">
        <a href="javascript:;" class="ptlink" title="<@s.property value="productSearchInfo.productName" />"><@s.property value="productSearchInfo.productName" /> - ${v2.branchName?if_exists} </a>
         <#include "/WEB-INF/pages/ticket/ticket_tags.ftl" />
    </dt>
    <dd class="pdetails">
        <div class="tiptext tip-light">
            <div class="tip-arrow tip-arrow-11">
                <em>◆</em>
                <i>◆</i>
            </div>
            <div class="tip-other">
                  <@s.if test="null!=#v2.descriptionWithTag" >
                     ${v2.descriptionWithTag?if_exists?replace('</br>','')} 
                 </@s.if>
            </div>
            <a href="javascript:;" class="view-details">收起</a>
        </div><!--//.tiptext-->
    </dd><!--//.pdetails-->
</dl>                              </@s.iterator>
                               </@s.iterator>
                           </div>
                        </div>
                    </td>
                </tr>
            </tbody>
             </@s.if>  
             
             
              <@s.if test="null!=scenicVo.ticketProductList.get('SUIT')&&scenicVo.ticketProductList.get('SUIT').size()>0" >
            <tbody class="ptbox">
                <tr>
                    <td class="ptdname">
                        <div class="ptname"><h5>多人套餐</h5></div>
                    </td>
                    <td>
                        <div class="ptdlist">
                            <div class="ptdlist-inner">
                         <@s.iterator value="scenicVo.ticketProductList.get('SUIT')" var="v1" status="st">    
                             <@s.iterator value="branchSearchInfo" var="v2" status="st">    
<dl class="ptditem">
    <dd class="pdpaytype">
    	
        <span class="ipay ipay-online"><i class="itype"><#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></i><a href="javascript:;" class="btn btn-w btn-small btn-orange" date_productId="${v2.productId?if_exists?c}"  date_type="${v2.branchType?if_exists}" date_prodBranchId="${v2.prodBranchId?if_exists?c}" date_subProductType="${v2.subProductType?if_exists}" onClick="cmCreateElements('<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>${v2.branchName?if_exists}_${v2.productId?if_exists?c}','${v2.productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productSearchInfo.productName)"/>','${v2.sellPriceYuan?if_exists}','${v2.subProductType?if_exists}')">预订</a></span>
    </dd>
    <dd class="pdlvprice">
        <dfn>&yen;<i>${v2.sellPriceYuan?if_exists}</i></dfn>
		<#include "/WEB-INF/pages/ticket/commentFanXian_tags.ftl" />
    </dd>
    <dd class="pdprice"><del>&yen;${v2.marketPriceInteger?if_exists}</del></dd>
    <dt class="pdname">
        <a href="javascript:;" class="ptlink" title="<@s.property value="productSearchInfo.productName" />"><@s.property value="productSearchInfo.productName" /> - ${v2.branchName?if_exists} </a>
         <#include "/WEB-INF/pages/ticket/ticket_tags.ftl" />
    </dt>
    <dd class="pdetails">
        <div class="tiptext tip-light">
            <div class="tip-arrow tip-arrow-11">
                <em>◆</em>
                <i>◆</i>
            </div>
            <div class="tip-other">
                   <@s.if test="null!=#v2.descriptionWithTag" >
                	 ${v2.descriptionWithTag?if_exists?replace('</br>','')} 
          		  </@s.if>
            </div>
            <a href="javascript:;" class="view-details">收起</a>
        </div><!--//.tiptext-->
    </dd><!--//.pdetails-->
</dl>                              </@s.iterator>
                               </@s.iterator>
                           </div>
                        </div>
                    </td>
                </tr>
            </tbody>
             </@s.if>  
        
        <!-- 自由行 -->
  <@s.if test="null!=scenicVo.freeNessAndHotelSuitProductList&&scenicVo.freeNessAndHotelSuitProductList.size()>0"> 
        <tbody class="ptbox">
            <tr>
                <td class="ptdname">
                    <div class="ptname"><h5>自由行</h5><p>景点+酒店</p></div>
                </td>
                <td>
                    <div class="ptdlist">
                        <div id="dfreetour" class="ptdlist-inner">
            <@s.iterator value="(scenicVo.freeNessAndHotelSuitProductList)" var="v2" status="st">
                 <@s.if test="#st.count<=3">
            <dl class="ptditem">
                <dd class="pdpaytype">
                    <span class="ipay ipay-online"><i class="itype"><#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></i><a  href="http://www.lvmama.com${productUrl?if_exists}" target="_blank" class="btn btn-w btn-small btn-link">查看</a></span>
                </dd>
                <dd class="pdlvprice">
                    <dfn>&yen;<i>${sellPriceInteger?if_exists}</i></dfn>
                    <@s.if test="null!=cashRefund&&cashRefund!=0&&sellPriceInteger>=50" ><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得最高&lt;span&gt;${cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${cashRefund?if_exists}元</i></span></@s.if>
                </dd>
                <dd class="pdprice"><del>&yen;${marketPriceInteger?if_exists}</del></dd>
                <dt class="pdname">
                    <a target="_blank" href="http://www.lvmama.com${productUrl?if_exists}" class="ptlink" title="${productName?if_exists}" tip-content="${productName?if_exists}">${productName?if_exists} </a>
                    <p>${recommendInfoSecond?if_exists}</p>
                </dt>
            </dl>
             </@s.if>
            </@s.iterator>
                             <a  target="_blank" href="http://www.lvmama.com/search/freetour/${place.name?if_exists}.html" class="link-more">查看 <@s.property value="scenicVo.freeNessAndHotelSuitProductList.size()" />条自由行&gt;&gt;</a>
                        </div>
                    </div>
                </td>
            </tr>
        </tbody><!--//#freetour-->
      </@s.if>
   
   
 <!-- 跟团游-->   
   <@s.if test="null!=scenicVo.groupAndBusTabNameList&&scenicVo.groupAndBusTabNameList.size()>0" >
    <@s.iterator value="(scenicVo.groupAndBusTabNameList)" var="var1" status="st1">
        <tbody class="ptbox">
            <tr>
                 <td class="ptdname">
                    <div class="ptname"><@s.if test="#st1.first"><h5>跟团游</h5></@s.if>
                     <p> ${var1?if_exists} 出发</p>
                    </div>
                </td>
                <td>
                    <div class="ptdlist">
                        <div id="dtuangou" class="ptdlist-inner">
                            <@s.iterator value="(scenicVo.groupAndBusDataMap).get('${var1?if_exists}')" var="v3" status="st">
                            <@s.if test="#st.count<=3">
                            <dl class="ptditem">
                                <dd class="pdpaytype">
                                    <span class="ipay ipay-online"><i class="itype"><#if  payToLvmama=="true">在线支付<#elseif  payToSupplier=="true">景区支付<#else>在线支付</#if></i><a href="http://www.lvmama.com${productUrl?if_exists}"  target="_blank" class="btn btn-w btn-small btn-link">查看</a></span>
                                </dd>
                                <dd class="pdlvprice">
                                    <dfn>&yen;<i>${sellPriceInteger?if_exists}</i></dfn>
                                     <@s.if test="null!=cashRefund&&cashRefund!=0&&sellPriceInteger>=50" ><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得最高&lt;span&gt;${cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${cashRefund?if_exists}元</i></span></@s.if>
                            
                                </dd>
                                <dd class="pdprice"><del>&yen;${marketPriceInteger?if_exists}</del></dd>
                                <dt class="pdname">
 
                                    <a  target="_blank" href="http://www.lvmama.com${productUrl?if_exists}" class="ptlink"  title="${productName?if_exists}" tip-content="${productName?if_exists}">${productName?if_exists}</a>
                                    <p>${recommendInfoSecond?if_exists}</p>
                                </dt>
                            </dl>
                            </@s.if>
                            </@s.iterator>
               <a target="_blank" href="http://www.lvmama.com/search/route/${var1?if_exists}-${place.name?if_exists}.html" class="link-more">查看
                                <@s.property value="(scenicVo.groupAndBusDataMap).get('${var1?if_exists}').size()" />条跟团游&gt;&gt;</a>
                            
                  </div>
                    </div>
                </td>
            </tr>
        </tbody><!--//#tuangou-->
        </@s.iterator>
         </@s.if>
    </table>
 
</div><!--//.dpro-list-->        
        </div>
        <!--<div class="hr_a"></div>-->
        <div class="dside">
        
        
<!--团购<i></i>人购买-->
<@s.if test="null!=scenicVo.tuangouProduct">            
<div class="sidebox dside-tuan">
    <p class="tuan-tit"><i class="icon dicon-time"></i>团购剩余时间 <span class="countdown">${scenicVo.tuangouProduct.validTime?if_exists}</span></p>
    <div class="scontent">
        <a target="_blank" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}" class="block">
            <img src="http://pic.lvmama.com/pics/${scenicVo.tuangouProduct.smallImage?if_exists}" width="228" height="152" alt="${scenicVo.tuangouProduct.productName?if_exists}" />
            <a target="_blank" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}">${scenicVo.tuangouProduct.productName?if_exists}</a> 
        </a>
        <p>
        <a  target="_blank" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}" class="btn btn-small btn-w btn-orange">查看详情</a>
        <dfn>&yen;<i>${scenicVo.tuangouProduct.sellPrice?if_exists}</i></dfn></p>
    </div>
    <div class="bottom">
        <p><span class="buy"><i></i></span><del>市场价:&yen;${scenicVo.tuangouProduct.marketPrice?if_exists}</del>   </p>
    </div> 
</div><!--//.sidebox-->
</@s.if>


<!--景点门票搜索-->
<div class="sidebox dside-search">
    <div class="stitle">
        <h4 class="stit">景点门票搜索</h4>
    </div>
    <div class="scontent">
        <div class="dsearch form-inline">
            <input type="text" class="input-text" placeholder="请输入目的地/景点/主题/城市" id="searchKey"/><a href="javascript:;" onclick="onclicksearch()" class="dsearch-btn"><i class="icon dicon-search"></i></a>
        </div>
        <hr>

        <@s.if test="null!=scenicVo.listPlace && scenicVo.listPlace.size()>0">
        <dl class="dl-ver">
            <dt>相关景点推荐</dt>
            <dd class="list2row">
            	<@s.iterator value="scenicVo.listPlace" var="listpalce">
	            	<a  target="_blank" title="${listpalce.name}" href="http://ticket.lvmama.com/scenic-${listpalce.placeId?c}">${listpalce.name}</a>
            	</@s.iterator>
            </dd>
        </dl>
       </@s.if>
       
    </div>
</div><!--//.sidebox-->

<!-- 酒店 -->
 <@s.if test="null != scenicVo.victinityHotel && scenicVo.victinityHotel.size() > 0">
<div class="sidebox dside-near">
    <div class="stitle">
        <h4 class="stit">附近酒店</h4>
    </div>
    <div class="scontent">
         <@s.iterator value="scenicVo.victinityHotel" status="st">
          <@s.if test="#st.first" >
            <a target="_blank" title="${name?if_exists}" href="http://www.lvmama.com/hotel/v${placeId!?c}" class="block">
                <img  src="http://pic.lvmama.com/${smallImage?if_exists}" width="228" height="152" alt="${name?if_exists}图片" />
                <p>${name?if_exists}</p>
            </a>
            
            <p class="gray">距离约<span class="num">${distance?if_exists}km</span></p>
            <p><dfn>&yen;<i>${productsPriceInteger?if_exists}</i><em>起</em></dfn>
             <hr>
            </@s.if>
         </@s.iterator>
        <ul class="textlist">
           <@s.iterator value="scenicVo.victinityHotel" status="st">
            <@s.if test="!(#st.first)" >
            <li>
                <dfn>&yen;<i>${productsPriceInteger!?c}</i><em>起</em></dfn>
                <a  target="_blank" title="${name?if_exists}" href="http://www.lvmama.com/hotel/v${placeId!?c}"><@s.if test="name!=null && name.length()>13">
                                        <@s.property value="name.substring(0,13)" escape="false"/>...
                                        </@s.if><@s.else>${name?if_exists}
                                        </@s.else></a>
                <p class="distance">距离约<span class="num">${distance?if_exists}km</span></p>
            </li>
            </@s.if>
           </@s.iterator> 
        </ul>
    </div>
</div><!--//.sidebox-->
 </@s.if>
 
<@s.if test="null != scenicVo.victinityScenic && scenicVo.victinityScenic.size() > 0">
<div class="sidebox dside-near">
    <div class="stitle">
        <h4 class="stit">附近景点</h4>
    </div>
    <div class="scontent">
     <@s.iterator value="scenicVo.victinityScenic" status="st">
	     <@s.if test="#st.first" >
	        <dl class="dl-hor">
	            <dt><a target="_blank" href="http://ticket.lvmama.com/scenic-${placeId!?c}">
	            <img  src="http://pic.lvmama.com/${smallImage?if_exists}" width="90" height="60" alt="${name?if_exists}"></a></dt>
	            <dd>
	                <p><a target="_blank" title="${name?if_exists}" href="http://ticket.lvmama.com/scenic-${placeId!?c}">
	                                <@s.if test="name!=null && name.length()>13">
                                        <@s.property value="name.substring(0,13)" escape="false"/>...
                                        </@s.if><@s.else>${name?if_exists}
                                        </@s.else></a></p>
	                <p><dfn>¥<i>${productsPriceInteger!?c}</i><em>起</em></dfn>
 	            </dd>
	        </dl>
	        <hr>
	       </@s.if>
       </@s.iterator>
        <ul class="textlist">
          <@s.iterator value="scenicVo.victinityScenic" status="st">
              <@s.if test="!(#st.first)" >
            <li>
                <dfn>&yen;<i>${productsPriceInteger?if_exists}</i><em>起</em></dfn>
                <a target="_blank" title="${name?if_exists}" href="http://ticket.lvmama.com/scenic-${placeId!?c}"><@s.if test="name!=null && name.length()>13">
                                        <@s.property value="name.substring(0,13)" escape="false"/>...
                                        </@s.if><@s.else>${name?if_exists}
                                        </@s.else></a>
                <p class="distance">距离约<span class="num">${distance?if_exists}km</span></p>
            </li>
              </@s.if>
           </@s.iterator>
        </ul>
    </div>
</div><!--//.sidebox-->
</@s.if>



<!--主题-->
<@s.if test="null != scenicVo.sameSubjectScenic && scenicVo.sameSubjectScenic.size() > 0">
<div class="sidebox dside-theme">
    <div class="stitle">
        <h4 class="stit">“${place.firstTopic?if_exists}”景点</h4>
    </div>
    <div class="scontent">
    	<@s.if test="null != scenicVo.sameSubjectScenic && scenicVo.sameSubjectScenic.size() > 0">
        <@s.iterator value="scenicVo.sameSubjectScenic">
        <dl class="dl-hor">
            <dt><a target="_blank" href="http://ticket.lvmama.com/scenic-${placeId!?c}"><img src="http://pic.lvmama.com/${smallImage?if_exists}" width="60" height="40" /></a></dt>
            <dd>

                <p><a  target="_blank" title="${name?if_exists}" href="http://ticket.lvmama.com/scenic-${placeId!?c}"><@s.if test="name!=null && name.length()>13">
                                        <@s.property value="name.substring(0,13)" escape="false"/>...
                                        </@s.if><@s.else>${name?if_exists}
                                        </@s.else></a></p>
                <p><dfn>&yen;<i>${productsPriceInteger!?c}</i><em>起</em></dfn></p>

            </dd>
        </dl>
       </@s.iterator>
       </@s.if>
    </div>
</div><!--//.sidebox-->
</@s.if>




<!--攻略-->
<div class="sidebox dside-guide" style="display: none;" >

</div><!--//.sidebox-->
        </div><!--//.dside-->
        <div class="dmain">
         <!--- 预订须知--->
          <@s.if test="null != place.orderNotice">
            <div id="policy" class="dbox policy">
                <div class="dtitle">
                    <h3 class="dtit"><i class="icon dicon-policy"></i>预订须知</h3>
                </div>
                <div class="dcontent">
                    <div class="dactive">
                        ${place.orderNotice?if_exists?replace("<p>","")?replace("</p>","</br>")?replace("color:#000000;","")}
                    </div>
                </div>
            </div><!--//.dbox-->
             </@s.if>
             
           <@s.if test="null!=scenicVo.placeActivity&&scenicVo.placeActivity.size()>0" >
            <div id="activity" class="dbox activity">
                <div class="dtitle">
                    <h3 class="dtit"><i class="icon dicon-activity"></i>景点活动</h3>
                </div>
                <div class="dcontent">
                   <@s.iterator value="(scenicVo.placeActivity)" var="var" status="st">
                    <h5>${title?if_exists}</h5>
                    <ul class="ul">
                        <li>活动时间：${startTime?date?string("yyyy年MM月dd日")} - ${endTime?date?string("MM月dd日")}</li>
                        <li>活动详情： ${content?if_exists}</li>
                    </ul>
                     <@s.if test="!(#st.last)">
                          <hr>
                     </@s.if>
                   </@s.iterator>
                </div>
            </div><!--//.dbox-->
             </@s.if>
             
             
             <!--景点介绍-->
            <@s.if test="descripTion!=null">
            <div id="introduction" class="dbox introduction">
                <div class="dtitle">   
                    <h3 class="dtit"><i class="icon dicon-introduction"></i>景点介绍</h3>
                </div>
                <div class="dcontent">
             	 	  <@s.if test="descripTion!=''"> 
                   			${descripTion?if_exists}
                  	 </@s.if>
                </div>
                	 <@s.if test="destinationExplore!=null"> 
                   			${destinationExplore?if_exists}
                  	 </@s.if>
            </div><!--//.dbox-->
            </@s.if>
            
            <@s.if test="null!=trafficInfo">
            <div id="traffic" class="dbox traffic">
                <div class="dtitle">
                    <h3 class="dtit"><i class="icon dicon-traffic"></i>交通指南</h3>
                </div>
                <div class="dcontent">
                    <div class="traffic-map">
                     	<iframe marginheight="0" marginwidth="0" border="0" src="http://ticket.lvmama.com/baiduMap/getBaiduMapCoordinate.do?id=<@s.property value="place.placeId"/>&windage=0.005&width=716px&height=287px&flag=2" width="750" height="300" scrolling="no"></iframe>
                    </div>
                    <h5>公共交通</h5>
		                  <@s.if test="trafficInfo!=''"> 
		                   			${trafficInfo?if_exists}
		                  </@s.if>
                </div>
            </div><!--//.dbox-->
            </@s.if>
            
                    
<!-- 点评信息概况 -->
 <@s.if test="null!=scenicVo.cmtLatitudeStatisticsList&&scenicVo.cmtLatitudeStatisticsList.size>0" >
	<div id="comments" class="dbox comments">
	    <div class="dtitle">
	        <h3 class="dtit"><i class="icon dicon-comments"></i>用户点评</h3>
	    </div>
	    <div class="dcontent">
            <div class="cominfo">
                <div class="dscore">
                    <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
                             <@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                      <span class="comlevel"><dfn><em>好评率</em><i data-mark="dynamicNum" data-level="${avgScore * 20}">${avgScore * 20}</i>%</dfn></span>
                     </@s.if>
                    </@s.iterator>
                    <span class="scorebox">
                     <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
                            <@s.if test='latitudeId != "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                 <p class="score-item">
                                    <em>${latitudeName?if_exists}</em>
                                    <span class="score-level"><i data-mark="dynamicNum" data-level="${avgScore}"></i></span>
                                    <em>#{avgScore;m1M1}分</em>
                                  </p>              
                               </@s.if>
                    </@s.iterator>
                    </span>
                </div>
                <div class="dtext">
                    <a class="btn btn-mini btn-w btn-orange" href="http://www.lvmama.com/myspace/share/comment.do">&nbsp;&nbsp;有订单，写点评，返现金&nbsp;&nbsp;</a>
                    <p>没订单？<a  class="dlink" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}">发表普通点评</a> 赠50积分，精华追加150积分！</p>
                </div>
            </div><!--//.cominfo-->

			<!-- 点评详情 -->
			<div class="dcomment">
				    <div class="tab-dcom">
				        <ul class="ul-hor JS_tabnav">
				            <li class="selected"><a href="javascript:;">体验点评<span id="experienceCommentCount"></span></a></li>
				            <li><a href="javascript:;">精华点评<span id="isBestCommentCount"></span></a></li>
				            <li><a href="javascript:;">普通点评<span id="commonCommentCount"></span></a></li>
				        </ul>
				    </div>
				    <div class="dcombox JS_tabsbox">
				        <div class="tabcon selected"  id="experienceComment">
				           
				        </div><!--//.tabcon-->
				        
				        <div class="tabcon" id="bestComment">
				        </div><!--//.tabcon-->
				        
				        <div class="tabcon" id="commonComment">
				        </div><!--//.tabcon-->
				        <!-- // div.p_content  点评内容-->
				    </div><!--//.dcombox-->
			</div><!--//.dcomment-->
        </div><!--dcontent -->
    </div><!--//.dbox-->
</@s.if>  
<@s.else>
<div id="comments" class="dbox comments">
	    <div class="dtitle">
	        <h3 class="dtit"><i class="icon dicon-comments"></i>用户点评</h3>
	    </div>
	    <div class="dcontent">
            <div class="cominfo">
                <div class="dtext">
                    <a class="btn btn-mini btn-w btn-orange" href="http://www.lvmama.com/myspace/share/comment.do">&nbsp;&nbsp;有订单，写点评，返现金&nbsp;&nbsp;</a>
                    <p>没订单？<a  class="dlink" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}">发表普通点评</a> 赠50积分，精华追加150积分！</p>
                </div>
            </div><!--//.cominfo-->
        </div><!--dcontent -->
</div><!--//.dbox-->
</@s.else>
     
      </div>
    </div>
</div> <!-- //.wrap 1 -->


<div class="xh_float">
   <img src="http://ticket.lvmama.com/placeQr/${place.placeId!?c}.png" width="70" height="70" alt="手机订购二维码">
   <p>扫描此二维码 <span>手机订更优惠</span></p>
   <span class="zhiyin"></span>
</div>


<div class="xfloatbar">
    <ul class="xfloatitem">
        <li class="xbuynow"><a rel="nofollow" class="icon" href="#destorder"></a></li>
        <li class="xcollect"><a rel="nofollow" class="icon" title="收藏" href="javascript:favorites();"></a></li>
        <li class="xsharebox"><a rel="nofollow" class="icon xshare" href="javascript:;"></a>
            <div class="xsharelink bdsharebuttonbox">
                <a href="#" class="icon xsharesina bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                <a href="#" class="icon xshareweibo bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
                <a href="#" class="icon xshareqzone bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
            </div>
        </li>
        <li class="xfeed"><a target="_blank" rel="nofollow" class="icon" href="http://www.lvmama.com/userCenter/user/transItfeedBack.do" title="意见反馈"></a></li>
        <li class="xgotop"><a rel="nofollow" class="icon" href="javascript:;" title="返回顶部"></a></li>
    </ul>
</div>

<div class="wrap" style="margin-top:20px;"><a href="http://www.lvmama.com/public/user_security#mp" target="_blank"><img src="http://pic.lvmama.com/img/v5/lybz_banner.gif" width="1000" height="50"></a></div> 

<!--公共底部-->
<script src="http://pic.lvmama.com/js/v6/public/footer.js"></script>
<!-- 频道公用js-->
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js,/js/v4/login/rapidLogin.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/placeholder.js,/js/v5/modules/pandora-poptip.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/bt-scrollspy.js"></script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/v5/dest.js"></script>
<script src="/js/dest.js"></script>

<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
<script type="text/javascript">

    $(function(){
        $.ajax({
            url:"http://ticket.lvmama.com/newplace/getCommentCount.do",
            data:{
                placeId :<@s.property value="place.placeId"/>
            },
            dataType:'json',
            success:function(data) {
                $("#experienceCommentCount").html("(" + data.experienceCommentCount + ")");
                $("#isBestCommentCount").html("(" + data.isBestCommentCount + ")"); 
                $("#commonCommentCount").html("(" + data.commonCommentCount + ")");
                $("#totalCmt").html( "("+(parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount))+")"); 
                $("#totalCmt_1").html(parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount)); 
            }

        });
         loadExperienceComment(1,'Defalut');
        
         loadBestComment(1,'Defalut');
        
         loadCommonComment(1,'Defalut');  
         
         $(".dside-guide").hide();
         
        //攻略
		$.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getOrgInfo&id=<@s.property value="place.placeId"/>&callback=?", function(result){
			if (result.data.title) {
				var content ="<div class=\"stitle\">";
				    content+="<h4 class=\"stit\">攻略随身带</h4>";
					content+="</div>";
				    content+="<div class=\"scontent\">";
				    content+="<dl class=\"dl-hor\">";
			     	content+="<dt><a href=\"" + result.data.pdfurl + "\" class=\"hot_pic\"> <img src=\"" + result.data.thumb + "\" width=\"90\" height=\"125\" /></a> </dt>";
				    content+="<dd>";
				    content+="<h5><a href=\""+ result.data.pdfurl +"\"> "+result.data.title +" </a> </h5>";
			    	content+="<p>官方攻略2014版</p>";
					content+="<p class=\"gray\">"+result.data.downs+"人下载</p>";
					content+="<a href=\""+  result.data.pdfurl+ "\" class=\"btn btn-mini btn-orange\">下载攻略</a>";
					content+="</dd>";
					content+="</dl>";
   				    content+="</div>";
				$(".dside-guide").html(content);
				$(".dside-guide").show();
			}
  		});
    
    });
    	
       var _flag = 0;
		function favorites(){ 
			  $.ajax({
            type: "get",
            url: "http://ticket.lvmama.com/check/login.do",
            dataType:"html",
          success: function(data){
			if(data == "true"){
				$.ajax({
				type: "get",
            	async:false,
            	dataType : "jsonp",
            	jsonp: "jsoncallback",
            	jsonpCallback:"success_jsonpCallback", 
				url:"http://www.lvmama.com/myspace/share/ticketFavorite.do",
				data:{ 
				objectId :<@s.property value="place.placeId"/>, 
				<@s.if test="scenicVo.placePhoto!=null"> 
				objectImageUrl : '<@s.property value="scenicVo.placePhoto.get(0).imagesUrl"/>', 
				</@s.if> 
				objectName :'<@s.property value="place.name"/>' 
				}, 
				success : function(json){
					if(json.success == "true"){ 
						alert("收藏成功"); 
					}else if(json.success == "false"){ 
						alert("已经收藏过此景点"); 
					} 
				},error : function(){ 
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
    
    /***/
    function loadExperienceComment(currentPage,defalut) {
        loadPaginationOfComment($("#experienceComment"), currentPage, 'EXPERIENCE','',defalut);
    }
    
    function loadBestComment(currentPage,defalut) {
        loadPaginationOfComment($("#bestComment"), currentPage, '','Y',defalut);
    }       
        
    function loadCommonComment(currentPage,defalut) {
        loadPaginationOfComment($("#commonComment"), currentPage, 'COMMON','',defalut);
    }

    function loadPaginationOfComment(obj, currentPage, cmtType, isBest,defalut) {
        $.ajax({
            url:"http://ticket.lvmama.com/newplace/paginationOfCommentsNew.do",
            data:{
                placeId :<@s.property value="place.placeId"/>,
                startRow:(currentPage - 1) * 5,
                cmtType:cmtType,
                isBest:isBest
            },
            dataType:'html',
            success:function(data) {
                obj.html(data);
            }
        });     
    }
        function addUsefulCount(varUsefulCount,varCommentId,obj) {
        $.ajax({
            type: "get",
            async:false,
            dataType : "jsonp",
            jsonp: "jsoncallback",
            jsonpCallback:"success_jsonpCallback",
            url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
            data:{
                commentId: varCommentId
            },
             success: function(jsonList, textStatus){
                 if(!jsonList.result){
                   alert("已经点过一次");
                 }else{
                    var newUsefulCount = varUsefulCount + 1;
                    $("#userfulCount_" + varCommentId).html("<i class=\"icon dicon-plus\"></i> <em>(" + newUsefulCount + ")</em>") ;
                 }
            }
        });
    }
    function reply(commentId) {
        if ($("#newReplyContent_" + commentId).val() == "") {
            alert('请输入需要回复的内容!');
            $("#newReplyContent_" + commentId).focus();
            return;
        }
        $.ajax({
            type: "get",
            url: "http://ticket.lvmama.com/check/login.do",
            dataType:"html",
            success: function(data){
	                 if(data == "true"){
		                    $.ajax({
		                        type: "get",
					            async:false,
					            dataType : "jsonp",
					            jsonp: "jsoncallback",
					            jsonpCallback:"success_jsonpCallback",
		                        url: "http://www.lvmama.com/comment/ajax/addReply.do",
		                        data:{
		                            commentId: commentId,
		                            content:$("#newReplyContent_" + commentId).val()
		                        },
		                         success: function(data){
		                             if(data.success){
		                                alert("您的回复已经发布成功，请等待审核！");
		                                $("#newReplyContent_" + commentId).val("");
		                             }else{
		                                alert("您的回复发布失败，请重新尝试!");    
		                             }
		                        }
		                    });                
	                 }else{
	                    // $(UI).ui("login");
	                    showLogin(function(){window.location.href="http://ticket.lvmama.com/scenic-"+'${place.placeId!?c}'+"#comments";window.location.reload();});
	                 }
   				 }
        });
    }


    function showCompleteData(commentId) {
		var content = $("#cmtContent_" + commentId).attr("complete-data");
		$("#cmtContent_" + commentId).html(content);
	}
	$(function(){
		$('.js_zhankai').live('click',function(){
			$(this).parent().html($(this).parent().attr("complete-data"));
		})
	})
  </script>
  
  <!-- 流量统计-->
  <script src="http://pic.lvmama.com/js/common/losc.js"></script>
  <script>
         cmCreatePageviewTag("新版景点产品详情页-"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>（可预订商品1）","M1004", null, null);
         cmCreateProductviewTag("<@s.property value="place.placeId"/>","<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>","TICKET");
         function cmCreateElements(one,productId,productName,price,subType){ 
         	cmCreateElementTag("新版门票预订_"+one,"门票预订点击");
         	cmCreateShopAction5Tag(productId, productName, "1", price, subType);
         	cmDisplayShops();
         }
  </script>
  
   <div class="hh_cooperate">
  <#include "/WEB-INF/pages/common/footer.ftl"/>
   <p> 
      <@s.property value='scenicVo.seoPublicContent' escape="false"/>
   </p>
   
   <@s.if test="null!=scenicVo.seoList && scenicVo.seoList.size()>0">
   <p><b>友情链接：</b>
   <span>
       <@s.iterator value="scenicVo.seoList" var ="v" status="st">
          <a target="_blank" href="${linkUrl?if_exists }">${linkName?if_exists }</a>
       </@s.iterator>
   </span>
   </p>
 </@s.if>
  </div>
</body>
</html>
