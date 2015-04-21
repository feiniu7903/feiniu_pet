<#--自由行打包的相关方法-->
<#function get_type_pos type>
	<#local val=0/>
	<#switch type>
	<#case 'ticket'>
		<#local val=3/>
	<#break>
	<#case 'route'>
		<#local val=4/>
	<#break>
	</#switch>
	<#return val/>
</#function>
<#function getPolicy journey type>
<#local val=""/>
<#switch type>
<#case 'ticket'>
	<#local val=journey.ticketPolicy/>
<#break>
<#case 'hotel'>
	<#local val=journey.hotelPolicy/>
<#break>
<#case 'route'>
	<#local val=journey.routePolicy/>
<#break>
<#case 'traffic'>
	<#local val=journey.trafficPolicy/>
<#break>
</#switch>
<#return val/>
</#function>
<#--显示一个行程当中的酒店列表-->
<#macro showJourneyHotel journey hotelProductList total_person>
<input type="hidden" tt="hotel" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${journey.hotelPolicy}"/>
<ul class="free_yd_linedata prod_journey" tt="hotel" prodJourneyId="${journey.prodJourenyId}">
	<li class="free_yl_1"><span class="free_yd_cur free_yc_2"></span>酒店：</li>
	<li class="free_yl_2 content">&nbsp;</li>
	<li class="free_yl_3"><em title="${hotelProductList?size}个可选">${hotelProductList?size}个可选</em></li>
	<li class="free_yl_4"><a href="javascript:void(0)" class="free_change_a" tt="hotel">更改<em class="free_ca_jiao"></em></a></li>
</ul>
<div class="free_hb_detail">
	<div class="free_hotel_top">
		<label><label>出行人数：<em class="rs">${total_person}</em> </label></label>
		<label>入住时长：<em class="rzsc">${journey.maxTime.nights}</em>晚</label>
	</div>
	<div class="free_hotel_list" category="hotel_${journey.prodJourenyId}">
		<#list hotelProductList as product>
		<div class="free_hl_row">
			<div class="free_hl_left">
				<#if product.smallImage!=null><img width="120" height="60" src="http://pic.lvmama.com/pics/${product.smallImage}"></#if>
			</div>
			<div class="free_hl_right">
				<h1 class="free_hotel_title"><a href="javascript:void(0)" class="free_hotel_a" productId="${product.productId}">${product.productName}</a>
					<i class="icon_hotel0${product.toPlace.hotelStar}"></i>
				</h1>
				<p class="free_hotel_text">酒店地址：${product.toPlace.address}</p>
				<p class="free_hotel_text">酒店类型：${product.toPlace.hotelType}</p>
				<table class="free_ht_list">
					<tr class="free_hdl_head">
						<th width="200" class="td_first">房型</th>
						<th width="70" class="center">早餐</th>
						<th width="75" class="center">床型</th>
						<th width="70" class="center">宽带</th>
						<th width="100" class="center">可住人数</th>
						<th width="70" class="center">房间数</th>
						<th width="80" class="center">差价</th>
						<th width="50" class="center">选择</th>
					</tr>
					<#list product.prodJourneyProductList as pjp>
					<tr class="free_hdl_content hotel_select" id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}" nights="${journey.maxTime.nights}">
						<td class="td_first">
						<div style="display:none" id="description_${pjp.journeyProductId}">
							<#if pjp.prodBranch.icon!=null><div class="free_hc_img"><img title="" alt="" src="http://pic.lvmama.com/pics/${pjp.prodBranch.icon}" width="240" height="160"/></div></#if>
							<div class="free_hc_desc">
								${pjp.prodBranch.description}
							</div>
						</div><img src="/img/free/free_hotel_cur.jpg"/> <a href="javascript:void(0)" class="free_hotel_house" jpId="${pjp.journeyProductId}">${pjp.prodBranch.branchName}</a></td>
						<td class="breakfast center">${pjp.prodBranch.zhBreakfast}</td>
						<td class="bedType center">${pjp.prodBranch.bedType}</td>
						<td class="broadband center">${pjp.prodBranch.zhBroadband}</td>
						<td class="quantity center">${pjp.prodBranch.adultQuantity}</td>
						<td class="center"><select name="jp_${pjp.journeyProductId}" tt="HOTEL" stock="${pjp.prodBranch.stock}" branchid="${pjp.prodBranchId}" beginTime="${journey.beginTime?date}" endTime="${journey.hotelEndTime?date}" quantity="${pjp.prodBranch.adultQuantity}">                                        
                            </select></td>
						<td class="center"><b class="red discount" journey="buyJourney_hotel_${journey.prodJourenyId}" journeyProductId="${pjp.journeyProductId}" price="${pjp.prodBranch.sellPriceYuan-pjp.discountYuan*journey.maxTime.nights}">0元</b></td>
						<td class="center"><input type="radio" defaultProduct="${pjp.defaultProduct}"  class="useProduct calculate" value="${pjp.journeyProductId}" name="buyJourney_hotel_${journey.prodJourenyId}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}"></td>
					</tr>
					</#list>
				</table>
			</div>
			<div class="clear"></div>
		</div>
		</#list>		
	</div>
	<div class="free_yd_bottom"><a href="javascript:void(0)" class="free_cannel_a">取 消</a> <input type="image" tt="hotel" prodJourneyId="${journey.prodJourenyId}" src="/img/free/free_save_btn.png" class="free_save_btn"/></div>
</div>
</#macro>

<#macro showJourneyTraffic journey trafficProductList adult child=0>
<input type="hidden" tt="traffic" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${journey.trafficPolicy}"/>
<ul class="free_yd_linedata prod_journey" tt="traffic" prodJourneyId="${journey.prodJourenyId}">
	<li class="free_yl_1"><span class="free_yd_cur free_yc_1"></span>交通：</li>
	<li class="free_yl_2 content">
		<b>未选择大交通</b>
		<div>
		</div>
	<#--上海→三亚　吉祥航空 HO1129　12:00 虹桥国际机场起飞　15:20 抵达凤凰国际机场　机型320--></li>
	<li class="free_yl_3"><em title="${trafficProductList?size}个可选">${trafficProductList?size}个可选</em></li>
	<li class="free_yl_4"><a href="javascript:void(0)" class="free_change_a">更改<em class="free_ca_jiao"></em></a></li>
</ul>
<div class="free_hb_detail">
	<div class="free_hotel_list" category="traffic_${journey.prodJourenyId}">
	<table class="free_hb_detail_list">
		<tr class="free_hdl_head">
			<td class="td_first">日期</td>
			<td>时间/机场</td>
			<td>航空公司</td>
			<td>舱位</td>
			<td>飞行总时长</td>
			<td>总差价</td>
			<td>选择</td>
		</tr>
		<#list trafficProductList as product>
		<#list product.prodJourneyProductList as pjp>
		<tr class="free_hdl_content hotel_select" id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}" direction="${pjp.prodBranch.prodProduct.direction}">
			<td class="td_first">${journey.beginTime?string('yyyy年MM月dd日')}</td>
			<td class="black trafficInfo" flightNo="${product.goFlight.flightNo}"><b class="start_time">${product.goFlight.startTime}</b><em class="start_city">${product.goFlight.startPlaceName}</em>（<em class="start_jc">${product.goFlight.startAirport.airportName}</em>）<br/>
							   <em class="end_time">${product.goFlight.arriveTime}</em><em class="end_city">${product.goFlight.arrivePlaceName}</em>（<em class="end_jc">${product.goFlight.arriveAirport.airportName}</em>）</td>
			<td class="black"><em class="hk"><#if product.goFlight.airline??>${product.goFlight.airline.airlineName}</#if></em><br/><em class="hkh">${product.goFlight.flightNo}</em> <#--<em class="jx">320</em>--></td>
			<td>${pjp.prodBranch.zhBerth}</td>
			<td>${product.goFlight.zhFlightTime}</td>
			<td <#if product.direction=='ROUND'>rowspan="2"</#if>><b class="red"><b class="red discount" journey="buyJourney_traffic_${journey.prodJourenyId}" journeyProductId="${pjp.journeyProductId}" price="${pjp.calcTotalSellPrice(adult,child)}">0元</b></b></td>
			<td <#if product.direction=='ROUND'>rowspan="2"</#if>><input type="radio" defaultProduct="${pjp.defaultProduct}" branchid="${pjp.prodBranchId}" quantity="${pjp.prodBranch.adultQuantity}" jpIds="${pjp.journeyProductIds}" goTime="${journey.beginTime?string('yyyy-MM-dd')}" backTime="${product.getBackDate(journey.beginTime)?string('yyyy-MM-dd')}" class="useProduct calculate" value="${pjp.journeyProductId}" name="buyJourney_traffic_${journey.prodJourenyId}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}"></td>
		</tr>
		<#if pjp.prodBranch.prodProduct.direction=='ROUND' && product.backFlight??>
		<tr class="free_hdl_content hotel_select" id="tr_jp_${pjp.journeyProductId}_2">
			<td class="td_first">${product.getBackDate(journey.beginTime)?string('yyyy年MM月dd日')}</td>
			<td class="black trafficInfo" flightNo="${product.backFlight.flightNo}"><b class="start_time">${product.backFlight.startTime}</b><em class="start_city">${product.backFlight.startPlaceName}</em>（<em class="start_jc">${product.backFlight.startAirport.airportName}</em>）<br/>
							   <em class="end_time">${product.backFlight.arriveTime}</em><em class="end_city">${product.backFlight.arrivePlaceName}</em>（<em class="end_jc">${product.backFlight.arriveAirport.airportName}</em>）</td>
			<td class="black"><em class="hk">${product.backFlight.airline.airlineName}</em><br/><em class="hkh">${product.backFlight.flightNo}</em> <#--<em class="jx">320</em>--></td>
			<td>${pjp.prodBranch.zhBerth}</td>
			<td>${product.backFlight.zhFlightTime}</td>
		</tr>
		</#if>
		</#list>
		</#list>		
	</table>
	</div>
	<div class="free_yd_bottom"><a class="free_cannel_a" href="javascript:void(0)">取 消</a> <input type="image" class="free_save_btn" tt="traffic" prodJourneyId="${journey.prodJourenyId}" src="/img/free/free_save_btn.png"></div>
</div>
</#macro>

<#--显示单产品，包含当地游、门票等-->
<#macro showSingleProduct title type journey ticketProductList total_person>
<input type="hidden" tt="${type}" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${getPolicy(journey,type)}"/>
<ul class="free_yd_linedata prod_journey" tt="${type}" prodJourneyId="${journey.prodJourenyId}" >
	<li class="free_yl_1"><span class="free_yd_cur free_yc_${get_type_pos(type)}"></span>${title}：</li>
	<li class="free_yl_2 content">
		<b>未选择景点</b>
		<div>
		</div>
	</li>
	<li class="free_yl_3"><em title="${ticketProductList?size}个可选">${ticketProductList?size}个可选</em></li>
	<li class="free_yl_4"><a href="javascript:void(0)" class="free_change_a" tt="${type}">更改<em class="free_ca_jiao"></em></a></li>
</ul>
<div class="free_hb_detail">
	<div class="free_hotel_top">
		<label><label>出行人数：<em class="rs">${total_person}</em> </label></label>
		<label>请选择游玩日期和选择张数（可多选）</label>
	</div>
	<div class="free_hotel_list" category="${type}_${journey.prodJourenyId}">
		<#list ticketProductList as product>
		<div class="free_hl_row">
			<div class="free_hl_left">
				<#if product.smallImage!=null><img width="120" height="60" src="http://pic.lvmama.com/pics/${product.smallImage}"></#if>
			</div>
			<div class="free_hl_right">
				<h1 class="free_hotel_title"><a href="javascript:void(0)" productId="${product.productId}" class="free_product_a">${product.productName}</a>
				</h1>
				<p class="free_hotel_text">景点地址：${product.toPlace.address}</p>
				<table class="free_ht_list">
					<tr class="free_hdl_head">
						<th width="500" class="td_first">
						<#if type=='route'>
						 当地游
						<#else>
						门票						
						</#if>
						</th>						
						<th width="100">日期</th>
						<th width="50">数量</th>
					</tr>
					<#list product.prodJourneyProductList as pjp>
					<tr class="free_hdl_content no_cursor" id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}">
						<td class="td_first">
						<div style="display:none" id="description_${pjp.journeyProductId}">							
							<div class="free_hc_desc">
								${pjp.prodBranch.description}
							</div>
						</div>
						<a href="javascript:void(0)" class="free_scenic_a" jpId="${pjp.journeyProductId}">${pjp.prodBranch.branchName}</a></td>
						
						<td><select name="product_date_${pjp.journeyProductId}" journeyProductId="${pjp.journeyProductId}" prodBranchId="${pjp.prodBranch.prodBranchId}" timeList="[<#list pjp.timeInfos as ti><#if ti_index gt 0>,</#if>{'time':'${ti.date?date}','stock':${ti.stock}}</#list>]">                                    
                            </select></td>
						<td><select name="jp_${pjp.journeyProductId}" journeyProductId="${pjp.journeyProductId}" id="buyJourney_${type}_${journey.prodJourenyId}_${pjp.journeyProductId}" require="${pjp.require}" defaultProduct="${pjp.defaultProduct}" tt="${type?upper_case}" stock="${pjp.prodBranch.stock}" quantity="${pjp.prodBranch.adultQuantity}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}">                                        
                            </select></td>
					</tr>
					</#list>
				</table>
			</div>
			<div class="clear"></div>
		</div>
		</#list>		
	</div>
	<div class="free_yd_bottom"><a href="javascript:void(0)" class="free_cannel_a">取 消</a> <input type="image" tt="${type}" prodJourneyId="${journey.prodJourenyId}" src="/img/free/free_save_btn.png" class="free_save_btn"/></div>
</div>
</#macro>

<#macro showProdJourneyItem journey current_days adult child>
<#assign total_person=adult+child/>
		<div class="free_yd_main">
			<div class="free_yd_title">
				<span class="free_yt_1">${journey.beginTime?string('MM月dd日')}</span>
				<#assign end_days=current_days-1+journey.maxTime.days>				
				<span class="free_yt_2">第
				<#if current_days gt end_days>
				${current_days}
				<#else>
				<#list current_days..end_days as day>				
				${day}
				<#if day_has_next>
				,
				</#if>				
				</#list>
				</#if>
				天</span>
				<span class="free_yt_3">${journey.toPlace.name}</span>
			</div>
			<div class="free_yd_content">
			<#--大交通-->
				<#if journey.hasTraffic()>
				<@showJourneyTraffic journey journey.trafficProductList adult child/>
				<div class="free_yd_line"></div>
				</#if>
				
				<#if journey.hasHotel()>
				<@showJourneyHotel journey journey.hotelProductList total_person/>
				<div class="free_yd_line"></div>
				</#if>
				<!--*景点*-->
				<#if journey.hasTicket()>
				<@showSingleProduct "门票" "ticket" journey journey.ticketProductList total_person/>
				<div class="free_yd_line"></div>
				</#if>
				<!--*当地游*-->
				<#if journey.hasRoute()>
				<@showSingleProduct "当地游" "route" journey journey.routeProductList total_person/>
				</#if>
			</div>
		</div>
</#macro>
