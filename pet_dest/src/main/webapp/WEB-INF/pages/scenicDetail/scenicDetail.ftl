<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<meta name="mobile-agent" content="format=html5; url=http://m.lvmama.com/ticket/piao-<@s.property value="place.placeId"/>/">
<title><@s.property value="scenicVo.seoTitle"/></title>
<meta name="keywords" content="<@s.property value="scenicVo.seoKeyword"/>">
<meta name="description" content="<@s.property value="scenicVo.seoDescription"/>">

<!-- css景点详情页线上使用样式 -->
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/new_v/ob_common/ui-components.css,/styles/v3/module.css,/styles/v3/form.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/v3/xincomment.css,/styles/v3/typo.css,/styles/v3/viewpoint.css,/styles/new_v/ui_plugin/calendar.css,/styles/v5/modules/tip.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/super_v2/orderV2.css,/styles/v4/modules/button.css,/styles/v4/modules/dialog.css"/>
<!-- css end -->
<link rel="canonical" href="http://www.lvmama.com/dest/${place.pinYinUrl?if_exists}" />
<#include "/WEB-INF/pages/common/GoogleAnalytics.ftl">
<#include "/WEB-INF/pages/common/coremetricsHead.ftl">
</head>
<body>
    <!--头部 S-->
	<@s.set var="pageMark" value="'destScenic'" />
 	<#include "/WEB-INF/pages/common/header.ftl">
	
	<!-- 面包屑导航-->
 	<@s.action name="navigationNew" namespace="/place" executeResult="true">
			 <@s.param name="fromDestId" value="fromDestId"></@s.param>
			 <@s.param name="id" value="place.placeId" > </@s.param>
	</@s.action>
 	<!--头部 E-->
<!-- wrap\\ 1 -->
<div class="wrap">

    <div class="jd_desc1">
        <div class="jd_desc2 clearfix">
			<div class="jd_desc_word">
				<h1 class="jd_h2">${place.name?if_exists}</h1>

				<p>[<a class="color_blue"  rel="nofollow" target="_blank" href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.grandfatherPlace.codeId">${scenicVo.grandfatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.grandfatherPlace.name?if_exists}</@s.else>.html"><@s.property value="scenicVo.grandfatherPlace.name" /></a><span class="jd_desc_dot">·</span><a
						class="color_blue"  rel="nofollow" target="_blank" href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.fatherPlace.codeId">${scenicVo.fatherPlace.codeId!?c}</@s.if><@s.else>${scenicVo.fatherPlace.name?if_exists}</@s.else>.html"><@s.property value="scenicVo.fatherPlace.name"/></a>]
				</p>
				<div class="pm_score">
				   <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
						<@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
						 <p>总体评价：<span class="xc_star"><i style="width:${avgScore * 20}%"></i></span>&nbsp;&nbsp;
						 <dfn class="pc_score"><i>#{avgScore;m1M1}</i>分</dfn>&nbsp;&nbsp;&nbsp;&nbsp;
						 <a rel="nofollow" class="comment-num" href="#js-remark" id="totalCmt_1"></a>
						 </p>
							  </@s.if>
						</@s.iterator>
					<div class="score_itembox">
						<@s.iterator value="scenicVo.cmtLatitudeStatisticsList"><@s.if test='latitudeId != "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
						<div class="score_item">
							<span class="pm_name"><em>${latitudeName?if_exists}：</em><i>#{avgScore;m1M1}分</i></span>
						</div>
						</@s.if></@s.iterator>
					</div>
				</div>
				
				<@s.if test="null!=place.kuaiSuRuYuan ||null!=place.guiJiuPei ||null!=place.suiShiTui ||null!=place.ruYuanBaoZhang">
					<p class="service_list">
					<span class="color_grey">服务保障：</span>
					<@s.if test="place.ruYuanBaoZhang=='ruyuanbaozhang'"><a hidefocus="false" href="http://www.lvmama.com/public/user_security#mp" target="_blank" class="service_poptip service_list_ensure" tip-content="顺利入园，快速服务">入园保证</a></@s.if>
					<@s.if test="place.kuaiSuRuYuan=='kuaisuruyuan'"><a hidefocus="false" href="http://www.lvmama.com/public/user_security#mp" target="_blank" class="service_poptip service_list_fast" tip-content="便捷入园，无需排队">快速入园</a></@s.if>
					<@s.if test="place.suiShiTui=='suishitui'"><a hidefocus="false" href="http://www.lvmama.com/public/user_security#mp" target="_blank" class="service_poptip service_list_return" tip-content="无条件退，放心订票">随时退</a></@s.if>
					<@s.if test="place.guiJiuPei=='guijiupei'"><a hidefocus="false" href="http://www.lvmama.com/public/user_security#mp" class="service_poptip service_list_indemnity" tip-content="买贵就赔，保证便宜">贵就赔</a></@s.if>
					</p>
				</@s.if>
				
				
				
				<@s.if test="null!=place.address"><p><span class="color_grey">景区地址：</span>${place.address?if_exists}<i class="jd_map"></i><a href="#js-map" class="color_blue">查看地图</a></@s.if>
				</p>
				<@s.if test="null!=place.scenicOpenTime"><p><span class="color_grey">开放时间：</span>${place.scenicOpenTime?if_exists}</p></@s.if>
					<@s.if test="null!=place.firstTopic||null!=place.scenicSecondTopic"><p><span class="color_grey">景点主题：</span>
					<@s.if test="null!=place.firstTopic "><a class="color_blue grey_boder"  rel="nofollow" target="_blank" href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.firstTopicCodeId">${scenicVo.firstTopicCodeId?if_exists}</@s.if><@s.else>${place.firstTopic?if_exists}</@s.else>.html">${place.firstTopic?if_exists}</a></@s.if>
					<@s.if test="null!=place.scenicSecondTopic"><a class="color_blue grey_boder"  rel="nofollow" target="_blank" href="http://www.lvmama.com/search/ticket/<@s.if test="null!=scenicVo.scenicSecondTopicCodeId">${scenicVo.scenicSecondTopicCodeId?if_exists}</@s.if><@s.else>${place.scenicSecondTopic?if_exists}</@s.else>.html">${place.scenicSecondTopic?if_exists}</a></p></@s.if></@s.if>
				
				<@s.if test="null!=scenicVo.placeActivity&&(scenicVo.placeActivity.size()>0)" >
				<p><span class="color_grey">景点活动：</span>
					  <@s.iterator value="(scenicVo.placeActivity)" var="var" status="st">
					   <a href="#js-activity" class="color_orange">${title?if_exists}</a>
					  </@s.iterator>
				</p>
		       </@s.if>
		     
    			<div class="ewm_box ewm_box_jd"> 
                    <img src="http://www.lvmama.com/dest/placeQr/${place.placeId!?c}.png" width="75" height="75" alt="手机订购二维码"> 
                    <p> 用驴妈妈app扫描此二维码 <span>手机订购更优惠</span> 
                    </p> 
                    <span class="zhiyin"></span> 
                </div>
			</div>
			<div id="slide">
				<ul id="js-slide-content">
					  <@s.iterator value="(scenicVo.placePhoto)" var="var" status="st">
						 <li><img src="${absoluteImageUrl?if_exists}" width="480" height="270"  alt="<@s.property value="place.name"/>" /></li>
					  </@s.iterator>
				</ul>
				<div class="layer"></div>
				<div class="js-nav">
					<span id="js-slide-prev" class="slide-prev-bear"><i></i></span>
					<div id="js-nav-con">
						<ul id="js-slide-nav">
							 <@s.iterator value="(scenicVo.placePhoto)" var="var" status="st">
							  <li <@s.if test="#st.first" > class="on"</@s.if> ><img src="${absoluteImageUrl?if_exists}" width="47" height="47"  alt="<@s.property value="place.name"/>" /></li>
							</@s.iterator>
						</ul>
					</div>
					<span id="js-slide-next" class="slide-next"><i></i></span>
				</div>
			</div>
        </div>
    </div>
    
    <!--  
    -----------------公告以上部分----------------
    
    --->
     
 <div class="jd_main">
    <div class="jd_left">
        
            <!-- 景点公告 --->
            <@s.if test="null != scenicVo.noticeList&&scenicVo.noticeList.size()>0">
                <div class="jd_notice">
                    <h3><i></i>景点公告</h3>
                    <@s.iterator value="scenicVo.noticeList" var="var" status="st">
                     <p class="color_red">${noticeStartTime?string("yyyy年MM月dd日")?if_exists}~${noticeEndTime?string("yyyy年MM月dd日")?if_exists},${noticeContent?if_exists}</p>
                    </@s.iterator>
                </div>
            </@s.if>
            <!--- tab导航---->
            <div class="viewnav"  id="js-filters">
                <div class="viewnav_inner" id="filter-inner" >
                    <ul>
                       <@s.if test="(null!=scenicVo.ticketProductList.get('SINGLE')&&scenicVo.ticketProductList.get('SINGLE').size()>0)
                         ||(null!=scenicVo.ticketProductList.get('UNION')&&scenicVo.ticketProductList.get('UNION').size()>0)
                        ||(null!=scenicVo.ticketProductList.get('SUIT')&&scenicVo.ticketProductList.get('SUIT').size()>0) " >
                        <li data-link="#js-ticket" class="active_li">门票预订</li>
                        </@s.if>
                        <@s.if test="null != place.orderNotice"><li data-link="#js-subscribe">预订须知</li></@s.if>

                        <@s.if test="null!=scenicVo.freeNessAndHotelSuitProductList&&scenicVo.freeNessAndHotelSuitProductList.size()>0">  <li data-link="#js-freetour">自由行</li></@s.if>
                        <@s.if test="null!=scenicVo.groupAndBusTabNameList&&scenicVo.groupAndBusTabNameList.size()>0" ><li data-link="#js-group">跟团游</li></@s.if>
                        <@s.if test="null!=scenicVo.placeActivity&&scenicVo.placeActivity.size()>0"><li data-link="#js-activity">景点活动</li></@s.if>
                        <@s.if test="descriptionFlag=='true'"><li data-link="#js-intro">景点简介</li></@s.if>
                        <li data-link="#js-remark" >游客点评(<span id="totalCmt_2"></span>)</li>
                        <@s.if test="scenicVo.qaCount > 0"><li data-link="#js-qa">问答</li></@s.if>
                        <li data-link="#js-map">交通地图</li>
                    </ul>
                </div>
            </div>
			<div class="jd_check">

                   
                <div id="js-ticket" class="jd_check_content">
                 <!-- ---打折门票-----   -->
                 <#include "/WEB-INF/pages/scenicDetail/scenicTicketTab.ftl" />
                
                <!--- 预订须知--->
                <@s.if test="null != place.orderNotice">
                    <div class="jd_check_info" id="js-subscribe">
                            <@s.if test="null != place.orderNotice">
                                <div class="viewbox">
                                    <div class="view-title"><h3 class=""><span>预订须知</span></h3></div>
                                    <div class="tiptext tip-info"><span class="tip-icon tip-icon-info"></span>本产品限网上及手机客户端预订，不接受电话预订。</div>
                                    <div>${place.orderNotice}</div>
                                </div>
                            </@s.if>
                     </div>
                 </@s.if>
                    
                </div>
			
		    </div>
         
         <!-- 自由行 -->
       <@s.if test="null!=scenicVo.freeNessAndHotelSuitProductList&&scenicVo.freeNessAndHotelSuitProductList.size()>0"> 
		<div class="jd_free_trip jd_border_blue" id="js-freetour">
			<h2 class="td_head_blue">
				<span class="font_size_18">自由行——</span>
				<span class="font_size_14">景点+酒店</span>
			</h2>
			<div class="jd_tabouter">
				<table class="jd_tab">
					<tr class="jd_ticket_type">
						<th style="width:540px;">线路名称</th>
						<th style="width:50px;">市场价</th>
						<th style="width:65px;">驴妈妈价</th>
						<th style="width:65px;"></th>
					</tr>
					<@s.iterator value="(scenicVo.freeNessAndHotelSuitProductList)" var="var" status="st">
					<@s.if test="#st.count<=3">
						<tr>
							<td><a class="color_blue" href="http://www.lvmama.com${productUrl?if_exists}">${productName?if_exists}</a><span class="color_grey">${recommendInfoSecond?if_exists}</span>
							   <@s.if test="null!=cashRefund&&cashRefund!=0" ><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${cashRefund?if_exists}元</i></span></@s.if>
							</td>
							<td>
								<del class="price">&yen;${marketPriceInteger?if_exists}</del>
							</td>
							<td><dfn>&yen;<i>${sellPriceInteger?if_exists}</i></dfn></td>
							<td>
								<a class="btn btn-small btn-orange" target="_blank" href="http://www.lvmama.com${productUrl?if_exists}"> 查看</a>
							</td>
						</tr> 
					</@s.if>
					</@s.iterator>
				</table>
			</div>
			<div class="free_trip_more color_blue">
				 <a  rel="nofollow"  target="_blank" href="http://www.lvmama.com/search/freetour/${place.name?if_exists}.html"> 查看
				 <@s.if test="null!=scenicVo.freeNessAndHotelSuitProductList&&scenicVo.freeNessAndHotelSuitProductList.size()>0"><@s.property value="scenicVo.freeNessAndHotelSuitProductList.size()" /></@s.if>条自由行》</a>
			</div>
		</div>
		</@s.if>
		<!--跟团游 -->

        <div class="jd_free_trip jd_border_blue" id="js-group">
            <@s.if test="null!=scenicVo.groupAndBusTabNameList&&scenicVo.groupAndBusTabNameList.size()>0" >
		
			<h2 class="td_head_blue">
			    <span class="font_size_18">跟团游</span>
				<ul class="jd_group_trip hor JS_tabnav"> 
				 <@s.iterator value="(scenicVo.groupAndBusTabNameList)" var="var" status="st">
				    <@s.if test="#st.count<=6"><li class="<@s.if test="#st.first">selected</@s.if>"><a href="javascript:;">${var?if_exists}出发</a></li> </@s.if>
				</@s.iterator>
				</ul> 
			</h2>
			<div class="JS_tabsbox"> 
			   <@s.iterator value="(scenicVo.groupAndBusTabNameList)" var="v2" status="st">
					<@s.if test="#st.count<=6">
						 <div class="tabcon jd_tabouter <@s.if test="#st.first">selected</@s.if>"> 
						 <table class="jd_tab" >
							<tr class="jd_ticket_type">
								<th style="width:540px;">线路名称</th>
								<th style="width:50px;">市场价</th>
								<th style="width:65px;">驴妈妈价</th>
								<th style="width:65px;"></th>
							</tr>
							<@s.iterator value="(scenicVo.groupAndBusDataMap).get('${v2}')" var="v3" status="st">
							<@s.if test="#st.count<=3">
							<tr>
								<td><a class="color_blue" href="http://www.lvmama.com${productUrl?if_exists}">${productName?if_exists}</a><span class="color_grey">${recommendInfoSecond?if_exists}</span>
								<@s.if test="null!=cashRefund&&cashRefund!=0" ><span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品，游玩后发表体验点评，内容通过审核，即可获得&lt;span&gt;${cashRefund}&lt;/span&gt;元点评奖金返现。"><em>返</em><i>${cashRefund?if_exists}元</i></span></@s.if>
								</td>
								<td>
									<del class="price">&yen;${marketPriceInteger?if_exists}</del>
								</td>
								<td><dfn>&yen;<i>${sellPriceInteger?if_exists}</i></dfn></td>
	 							<td>
									<a class="btn btn-small btn-orange" target="_blank" href="http://www.lvmama.com${productUrl?if_exists}"> 查看</a>
								</td>
							</tr>
							</@s.if>
							</@s.iterator>
						</table>
						<p class="free_trip_more color_blue">
						      <a rel="nofollow" target="_blank" href="http://www.lvmama.com/search/route/${v2}-${place.name?if_exists}.html"> 查看
	                         <@s.if test="null!=(scenicVo.groupAndBusDataMap).get('${v2}')"><@s.property value="(scenicVo.groupAndBusDataMap).get('${v2}').size()" /></@s.if>条跟团游》</a>
						</p>
						</div>
					 </@s.if>
			   </@s.iterator>
			</div>
		
		</@s.if>
		</div>

        
        <!-- 景点活动-->		
        
		<@s.if test="null!=scenicVo.placeActivity&&scenicVo.placeActivity.size()>0" >
		<div class="jd_active jd_border_blue" id="js-activity">
			<div class="view-title">
				<h3>
					<span>景点活动进行中</span>——${place.name?if_exists}
				</h3>
			</div>
		   <@s.iterator value="(scenicVo.placeActivity)" var="var" status="st">
			<div class="jd_active_banner font_size_14">
				<div class="jd_oneBanner">
					<strong class="color_orange">★活动名称：</strong>
					<strong>${title?if_exists}</strong>
				</div>
				<div class="jd_oneBanner">
					<strong class="color_orange">★活动时间：</strong>

					<strong><@s.if test="startTime!=null" >${startTime?date?string("yyyy年MM月dd日")}</@s.if>-
					<@s.if test="endTime!=null">${endTime?date?string("MM月dd日")}</@s.if></strong>
				</div>
				<i class="tri_grey"></i>
				<div class="jd_textarea typo">
                    ${content?if_exists}
                </div>
			</div>
			 </@s.iterator>
		</div>
		</@s.if>
		
        <!-- 景点简介-->
 		<@s.if test="descriptionFlag=='true'">
		<div class="jd_active jd_border_blue" id="js-intro">
			<div class="view-title">
				<h3>
					<span>景点简介</span>——${place.name?if_exists}
				</h3>
			</div>

			<div class="jd_textarea typo">
			   <#include  "${descriptionPath?if_exists}" />
			</div>
		</div>
        </@s.if> 
	<a href="http://m.lvmama.com/static/zt/3.0.0/519/default/hotfocusPc.html" target="_blank" class="dest-banner"><img src="http://pic.lvmama.com/img/v3/productBanner.jpg" width="790" height="80"></a>
        <!--驴友点评-->
        <div class="viewbox " id="js-remark">
                <div class="view-title">
                    <h3><span>驴友点评</span>——${place.name?if_exists}</h3>

                </div>
                <div class="view-content p_comment" id="commentDisplay" style="display: none;">
                  
                        <div class="p_cominfo">
                            <div class="pm_score">
                             <@s.if test="null!=scenicVo.cmtLatitudeStatisticsList&&scenicVo.cmtLatitudeStatisticsList.size>0" >
                                <@s.iterator value="scenicVo.cmtLatitudeStatisticsList">
                                    <@s.if test='latitudeId == "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                        <p class="score_total">
                                            <dfn class="pc_score"><i>#{avgScore;m1M1}</i>分</dfn>
                                            <span class="xc_star"><i style="width:${avgScore * 20}%"></i></span>
                                            <a rel="nofollow" class="comment-num" target="_blank" id="totalCmt"></a>
                                        </p>
                                    </@s.if>
                                </@s.iterator>
                                <div class="score_itembox">
                                    <@s.iterator value="scenicVo.cmtLatitudeStatisticsList"><@s.if test='latitudeId != "FFFFFFFFFFFFFFFFFFFFFFFFFFFF"'>
                                        <div class="score_item">
                                            <span class="pm_name"><em>${latitudeName?if_exists}：</em><i>#{avgScore;m1M1}</i></span>
                                            <span class="xcm_star"><i style="width:${avgScore * 20}%"></i></span>
                                        </div>
                                    </@s.if></@s.iterator>
                                </div>
                              </@s.if>
                            </div>
                            <div class="v_line"></div>
                            <div class="comment_tips">
                                <p><i class="bgorange">返</i> 有订单？发表体验点评，返点评奖金。<a rel="nofollow" target="_blank" href="http://www.lvmama.com/myspace/share/comment.do">写体验点评&gt;&gt;</a></p>
                                <p><i class="bgorange">送</i> 没订单？发表普通点评，赠50积分，精华追加150积分！</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a rel="nofollow" class="btn btn-small btn-pink write_commont" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}" target="_blank">写普通点评</a></p>
                            </div>
                        </div> <!-- //.p_cominfo 点评信息概况 -->
                        
                        <div class="p_cnav">
                            <ul class="JS_tabnav clearfix">
                                <li class="selected"><a href="javascript:;"><b>体验点评</b><span id="experienceCommentCount"></span></a></li>
                                <li><a href="javascript:;"><b>精华点评</b><span id="isBestCommentCount"></span></a></li>
                                <li><a href="javascript:;"><b>普通点评</b><span id="commonCommentCount"></span></a></li>
                            </ul>
                        </div> <!-- //.p_cnav 点评切换 -->
                        
                        <div class="p_content JS_tabsbox">
                            <div class="tabcon selected" id="experienceComment">
                            </div>
                            
                            <div class="tabcon" id="bestComment">
                                
                            </div>
                            <div class="tabcon" id="commonComment">
                               
                            </div>
                        </div> <!-- // div.p_content  点评内容-->
                </div>
                <div class="p_cominfo" id="commentHide" style="display: none;">
                                <div class="no-comment">
                                    <h4>暂无点评</h4>
                                    
                                </div>
                                <div class="v_line"></div>
                                <div class="comment_tips">
                                    <p><i class="bgorange">返</i> 有订单？发表体验点评，返点评奖金。<a target="_blank" href="http://www.lvmama.com/myspace/share/comment.do">写体验点评&gt;&gt;</a></p>
                                    <p><i class="bgorange">送</i> 没订单？发表普通点评，赠50积分，精华追加150积分！</p>
                                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-small btn-pink write_commont" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId!?c}" target="_blank">写普通点评</a></p>
                                </div>
                </div> <!-- //.p_cominfo 点评信息概况 -->
                    
        </div>
            <!-- 问答-->
            <@s.if test="scenicVo.qaCount != 0"><div class="jd_askAnswer jd_border_blue" id="js-qa"></div></@s.if>
            <!-- 交通地图-->
            <div class="jd_askAnswer jd_border_blue" id="js-map">
                <div class="view-title">
                    <h3>
                        <span>交通地图</span>——${place.name}
                    </h3>
                </div>

                <div class="jd_trafic_map">
                    <iframe src="http://www.lvmama.com/dest/baiduMap/getBaiduMapCoordinate.do?id=<@s.property value="place.placeId"/>&windage=0.005&width=730px&height=287px&flag=2" width="750" height="300" scrolling="no"></iframe>
                </div>
                <div class="jd_public_traffic">
                   <@s.if test="trafficInfoFlag=='true'"> 
                    <#include  "${trafficinfoPath}" />
                   </@s.if>
                </div>
            </div>
    </div>
    
    <!-- 
    ----
    ----
    ----右侧文件 --------------
    ---
    ------------>
    <div class="aside">
            <div class="jd_sidebar jd_search">
                <b class="sidebarTit">景点门票搜索</b>
                <input class="input-text" placeholder="中文/拼音" id="searchKey" type="text" name="keyword"  autocomplete="off" role="textbox" aria-autocomplete="list" aria-haspopup="true" style="" />
               	<input name="searchButton" id="searchButton" type="button" value="搜&nbsp;索" class="btn btn-pink" />
            </div><!--jd_search end-->
            <div class="jd_sidebar jd_guide" style="display: none;" >
                <!-- 攻略  -->
            </div><!--jd_guide end-->
		
		     <!--团购 -->
            <@s.if test="null!=scenicVo.tuangouProduct"> 
            <div class="jd_sidebar jd_tuan" > 
                <b class="sidebarTit">团购进行中</b> 
                <a target="_blank" class="text-cover" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}"> 
                    <img src="http://pic.lvmama.com/pics/${scenicVo.tuangouProduct.smallImage?if_exists}" width="180" height="120" alt="${scenicVo.tuangouProduct.productName?if_exists}"> 
                    <span></span> 
                    <i class="countdown">${scenicVo.tuangouProduct.validTime?if_exists}</i> 
                </a> 
                <a target="_blank" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}">${scenicVo.tuangouProduct.productName?if_exists}</a> 
                <p class="jd_tuanPrice"> 
                    <a target="_blank" class="btnbuy" href="http://www.lvmama.com/product/${scenicVo.tuangouProduct.productId!?c}" hidefocus="false">抢购</a> 
                    <dfn>&yen;<i>${scenicVo.tuangouProduct.sellPrice}</i></dfn> 
                    <span class="price">节省：&yen;${scenicVo.tuangouProduct.marketPrice - scenicVo.tuangouProduct.sellPrice}</span> 
                </p> 
            </div> 
            </@s.if>
	     <!-- //.JS_tabsbox -->

            <!-- 周边景点 周边酒店--->
            <@s.if test="(null != scenicVo.VictinityHotel && scenicVo.VictinityHotel.size() > 0)||(null != scenicVo.VictinityScenic && scenicVo.VictinityScenic.size() > 0)">
                <div class="jd_sidebar_tabs">
                    <ul class="tabsNav JS_tabnav">
                        <li class="selected"><a href="javascript:;">周边酒店</a></li>
                        <li><a href="javascript:;" class="tabsNav_on">周边景点</a></li>
                    </ul> 
                  
                    <div class="tabsContent JS_tabsbox">
                        <div class="tabcon selected">
                            <ul class="nearbyList">
                            
                            <@s.if test="null != scenicVo.VictinityHotel && scenicVo.VictinityHotel.size() > 0">
                                <@s.iterator value="scenicVo.VictinityHotel">
                                <li>
                                    <a href="javascript:;"><img src="http://pic.lvmama.com/${smallImage?if_exists}" width="60" height="40" /></a>
                                    <p><a href="http://www.lvmama.com/hotel/v${placeId!?c}">${name?if_exists}</a><span>约${distance?if_exists}km</span></p>
                                </li>
                                </@s.iterator>
                            </@s.if>
                            </ul>
                        </div>
                        <div class="tabcon">
                            <ul class="nearbyList">
                            <@s.if test="null != scenicVo.VictinityScenic && scenicVo.VictinityScenic.size() > 0">
                                <@s.iterator value="scenicVo.VictinityScenic">
                                <li>
                                    <a href="javascript:;"><img src="http://pic.lvmama.com/${smallImage?if_exists}" width="60" height="40" /></a>
                                    <p><a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}">${name?if_exists}</a><span>约${distance?if_exists}km</span></p>
                                </li>
                                </@s.iterator>
                            </@s.if>
                            </ul>
                        </div>
                    </div> <!-- //.JS_tabsbox -->
                </div><!--jd_sidebar_tabs end-->
            </@s.if>
			
			<@s.if test="null != scenicVo.SameSubjectScenic && scenicVo.SameSubjectScenic.size() > 0">
                <div class="jd_sidebar moreView">
                    <b class="sidebarTit">更多“${place.firstTopic?if_exists}”景点</b>
                    <ul class="nearbyList">
                    <@s.if test="null != scenicVo.SameSubjectScenic && scenicVo.SameSubjectScenic.size() > 0">
                        <@s.iterator value="scenicVo.SameSubjectScenic">
                            <li>
                               <a href="javascript:;"><img src="http://pic.lvmama.com/${smallImage?if_exists}" width="60" height="40" /></a>
                               <p><a href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}">${name?if_exists}</a><span>约${distance?if_exists}km</span></p>
                            </li>
                        </@s.iterator>
                    </@s.if>
                    </ul>
                </div><!--moreView end-->
			</@s.if>

            <!-- 相关专题-->
            <div class="jd_sidebar ztBox"  style="display: none;">    
            
            </div><!-- moreView end-->
     
            <!-- 手机客户端-->
            <div class="sidebar_banner">
                <a target="_blank" href="http://m.lvmama.com/static/zt/3.0.0/1401/rightOffPolite.html"><img src="http://pic.lvmama.com/img/v3/client.jpg" width="200" height="60" /></a>
            </div>
    </div> <!-- //.aside -->

    
    
    
<!--
--
---
--底部内容-----
---
-->
    </div>
</div>

    <div class="wrap" style="margin-top:20px;"><a href="http://www.lvmama.com/public/user_security#mp" target="_blank"><img src="http://pic.lvmama.com/img/v5/lybz_banner.gif" width="1000" height="50"></a></div> 

    <script src="http://pic.lvmama.com/js/common/copyright.js"></script>

    <!--友情链接 -->
    <div class="hh_cooperate">
        <@s.if test="null!=scenicVo.fatherBrotherList">
        <p><b>相关推荐：</b><span>
            <@s.iterator value="scenicVo.fatherBrotherList" var="v" status="st">
                <a target="_blank" href="http://www.lvmama.com/dest/${pinYinUrl?if_exists}"><@s.if test="null!=seoName">${seoName?if_exists}</@s.if><@s.else>${name?if_exists}</@s.else>旅游</a>
            </@s.iterator>
                <a target="_blank" href="http://www.lvmama.com/guide/place/${place.pinYinUrl?if_exists}/">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>攻略 </a>
                <a target="_blank" href="http://www.lvmama.com/comment/${place.placeId!?c}-1">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>点评</a>
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/dish">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>美食 </a>
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/hotel">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>附近住宿</a>
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/traffic">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>地址</a>
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/entertainment">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>贴士</a>
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/shop">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>购物 </a>      
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/weekendtravel">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>一日游  </a> 
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/photo">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>图片</a>       
                <a target="_blank" href="http://www.lvmama.com/travel/${place.pinYinUrl?if_exists}/place">
                    <@s.if test="null!=place.seoName">${place.seoName?if_exists}</@s.if><@s.else>${place.name?if_exists }</@s.else>介绍 </a>                        
            </span>
        </p>
        </@s.if>

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
    
    
<script type="text/javascript">
	document.getElementById("adPro").setAttribute("data-adsrc","http://lvmamim.allyes.com/main/s?user=lvmama_2013|ticket_2013|ticket_2013_flag01&db=lvmamim&border=0&local=yes#300px#60px");
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js"></script> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v3/plugins.js,/js/v3/app.js,/js/v3/plugins/jQuery.slide.js,/js/v3/viewpoint.js,/js/new_v/ui_plugin/jquery-time-price-table.js"></script> 
<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/modules/pandora-dialog.js,/js/v5/modules/pandora-poptip.js" ></script>
<#if !login>
	<#-- 未登录状态下需要显示快速登录层 S -->
	<script src="http://pic.lvmama.com/min/index.php?f=/js/v4/login/rapidLogin.js" type="text/javascript"></script>
</#if>
<script src="http://pic.lvmama.com/js/common/losc.js"></script>
<script src="http://pic.lvmama.com/js/new_v/ob_dest/dest_js.js"></script>
<script type="text/javascript">
	$(function(){
		$(".jd_guide .ztBox").hide();

		loadExperienceComment(1,'Defalut');
		
		loadBestComment(1,'Defalut');
		
		loadCommonComment(1,'Defalut');

		$.ajax({
			url:"/dest/newplace/getCommentCount.do",
			data:{
				placeId :<@s.property value="place.placeId"/>
			},
			dataType:'json',
			success:function(data) {
				$("#experienceCommentCount").html("(" + data.experienceCommentCount + ")");
				$("#isBestCommentCount").html("(" + data.isBestCommentCount + ")"); 
				$("#commonCommentCount").html("(" + data.commonCommentCount + ")");
				$("#totalCmt").html("共" + (parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount)) + "条点评"); 
			    $("#totalCmt_1").html("共" + (parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount)) + "条点评"); 
			     $("#totalCmt_2").html( (parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount))); 
			     if((parseInt(data.experienceCommentCount) + parseInt(data.commonCommentCount))>0){
			        $("#commentDisplay").show();
			     }else{
			        $("#commentHide").show();
			     }
			}

		});

        //问答
		loadPaginationOfQuestionAndAnswer(1);

        //攻略
		$.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getOrgInfo&id=<@s.property value="place.placeId"/>&callback=?", function(result){
			if (result.data.title) {
				var content = "<b class=\"sidebarTit\">" + result.data.title + "</b>";
				content += "<a href=\"" + result.data.pdfurl + "\" class=\"hot_pic\"><img src=\"" + result.data.thumb + "\" width=\"90\" height=\"125\" /></a>";
				content += "<div class=\"hot_pic_info\">";
				content += "<p class=\"banben\">版本：</p><strong>" + result.data.version +"</strong><p class=\"banben\">更新时间：</p><strong>" + result.data.updatetime  + "</strong>";
				content += "<button class=\"btn btn-small\"  onclick='window.location=\""+result.data.pdfurl+"\"'>&nbsp;下载&nbsp;</button></div></div>";
				$(".jd_guide").html(content);
				$(".jd_guide").show();
			}
  		});
        //相关专题
		$.getJSON("http://www.lvmama.com/zt/s/api.php?action=getTagName&tagName={place.firstTopic}&order=desc&callback=?", function(result){
			if (result.data.length > 0) {
				var content = "<b class=\"sidebarTit\">相关专题</b>";
				for (var i = 0 ; i < result.data.length ; i++) {
					content += "<dl class=\"dlborder\"><dt><a href=\"" + result.data[i].url+ "\"><img src=\"" + result.data[i].thumb + "\" width=\"180\" height=\"120\" /></a></dt><dd>" + result.data[i].title + "</dd></dl>";
				}
				$(".ztBox").html(content);
				$(".ztBox").show();
			}
  		});
	});
	
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
			url:"/dest/newplace/paginationOfComments.do",
			data:{
				placeId :<@s.property value="place.placeId"/>,
				startRow:(currentPage - 1) * 5,
				cmtType:cmtType,
				isBest:isBest
			},
			dataType:'html',
			success:function(data) {
				obj.html(data);
				$('span[class^="tags"]').ui('lvtip',{
                    hovershow: 200
                });
                if(defalut!='Defalut'){
                 //定位
                  $("#lv_page a").click(function(){ 
                    $(window).scrollTop($("#js-remark").offset().top) 
                    })
                  $(window).scrollTop($("#js-remark").offset().top) 
                }
			}

		});		
	}

	function loadPaginationOfQuestionAndAnswer(currentPage) {
		$.ajax({
			url:"/dest/newplace/getPaginationOfQuestionAndAnswer.do",
			data:{
				placeId :<@s.property value="place.placeId"/>,
				startRow:(currentPage - 1) * 5
			},
			dataType:'html',
			success:function(data) {
				$("#js-qa").html(data);
			}

		});
	}

	function addUsefulCount(varUsefulCount,varCommentId,obj) {
		$.ajax({
			type: "post",
			url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
			data:{
				commentId: varCommentId
			},
			dataType:"json",
			success: function(jsonList, textStatus){
				 if(!jsonList.result){
				   alert("已经点过一次");
				 }else{
					var newUsefulCount = varUsefulCount + 1;
					$("#userfulCount_" + varCommentId).html("有用 <span>(" + newUsefulCount + ")</span>") ;
				 }
			}
		});
	}

	 //冒泡提醒
	 $(".service_poptip").poptip({
	 place: 6
	 });

	function showReply(commentId) {
		$.ajax({
			type: "post",
			url: "/dest/newplace/getReply.do",
			data:{
				commentId: commentId
			},
			dataType:'html',
			success: function(data){
				$("#reply_" + commentId).html(data);
				$("#reply_" + commentId).show();
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
			url: "http://www.lvmama.com/check/login.do",
			dataType:"html",
			success: function(data){
				 if(data == "true"){
					$.ajax({
						type: "post",
						url: "http://www.lvmama.com/comment/ajax/addReply.do",
						data:{
							commentId: commentId,
							content:$("#newReplyContent_" + commentId).val()
						},
						dataType:"json",
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
					 $(UI).ui("login");
				 }
			}
		});
	}

	function showCompleteData(commentId) {
		var content = $("#cmtContent_" + commentId).attr("complete-data");
		$("#cmtContent_" + commentId).html(content);
	}
</script>
<!-- js end-->
<script>
      cmCreatePageviewTag("景点产品详情页_"+"<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>（可预订商品1）", "M1004", null, null);
      cmCreateProductviewTag("<@s.property value="place.placeId"/>","<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(place.name)"/>","TICKET");
</script>
</body>
</html>
