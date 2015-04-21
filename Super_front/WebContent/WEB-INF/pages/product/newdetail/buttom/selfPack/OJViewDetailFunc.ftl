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
<#macro showJourneyHotel journey hotelProductList total_person now_day>
<input type="hidden" tt="hotel" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${journey.hotelPolicy}"/>

        <h6>当晚入住酒店</h6>
    	<div class="superFreeProdJourney" now_day="${now_day}" tt="hotel" prodJourneyId="${journey.prodJourenyId}">
    			<div class="superFreeProdJourney_content" tt="hotel" prodJourneyId="${journey.prodJourenyId}">
    				
    			</div>
            		<span class="btn_tj superFreeProdJourneyAddBtn" tt="hotel" prodJourneyId="${journey.prodJourenyId}" >添加酒店</span>
                
        </div>
	<div class="superFreeSubTitle" tt="hotel" prodJourneyId="${journey.prodJourenyId}"     style="display:none;"><h4 class="gaiTitle">更改酒店<span>
								第
								${now_day}
								天${journey.toPlace.name}</span></h4></div>
    <div class="superFreeSubMain superFreeList" tt="hotel" prodJourneyId="${journey.prodJourenyId}"    category="hotel_${journey.prodJourenyId}"  style="display:none;">
        <div class="zuheDay zuheDayT">
            <div class="GaiBoxS">
				<#list hotelProductList as product>	
                <div class="dayBoxAll">
                    <div class="dayBox1">
                        <div class="dayBox1_L"><#if product.smallImage!=null><img width="87" height="58" src="http://pic.lvmama.com/pics/${product.smallImage}"></#if></div>
                        <div class="dayBox1_R" >
                            <h5><a href="javascript:void(0)" >${product.productName}</a></h5>
                            <p>酒店地址：${product.toPlace.address}<br />酒店类型：${product.toPlace.hotelType}</p>
                        </div>
                        
                    </div>
                    <table class="dayTable">
                                <tr>
                                    <th>房型</th>
                                    <th width="80">早餐</th>
                                    <th width="80">床型</th>
                                    <th width="80">宽带</th>
                                    <th width="80">可住人数</th>
                                    <th width="80">房间数</th>
                                    <th width="80">差价</th>
                                    <th width="80">操作</th>
                                </tr>
								<#list product.prodJourneyProductList as pjp>
                                <tr id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}" nights="${journey.maxTime.nights}">
                                    <td><a href="javascript:void(0)">${pjp.prodBranch.branchName}</a></td>
                                    <td>${pjp.prodBranch.zhBreakfast}</td>
                                    <td>${pjp.prodBranch.bedType}</td>
                                    <td>${pjp.prodBranch.zhBroadband}</td>
                                    <td>${pjp.prodBranch.adultQuantity}</td>                                    
<#assign description=pjp.prodBranch.description />
                                    <td><select name="jp_${pjp.journeyProductId}"   tt="HOTEL" stock="${pjp.prodBranch.stock}" branchid="${pjp.prodBranchId}" beginTime="${journey.beginTime?date}" endTime="${journey.hotelEndTime?date}" quantity="${pjp.prodBranch.adultQuantity}" smallImage="${product.smallImage}" branchName="${pjp.prodBranch.branchName}" zhBreakfast="${pjp.prodBranch.zhBreakfast}" bedType="${pjp.prodBranch.bedType}" description="" productId="${product.productId}" hotelStar="${product.toPlace.hotelStar}">                                        
                            			</select>
                            			<textarea  style="display: none;" id="jp_description_${pjp.journeyProductId}">${description?replace('\n','</br>')}</textarea>
                                    </td>
                                    <td><samp class="differenceOfPrices" journey="buyJourney_hotel_${journey.prodJourenyId}" journeyProductId="${pjp.journeyProductId}" price="${pjp.prodBranch.sellPriceYuan-pjp.discountYuan*journey.maxTime.nights}">--</samp></td>
                                    <td><span class="xuanBnt"  tt="hotel" prodJourneyId="${journey.prodJourenyId}"   defaultProduct="${pjp.defaultProduct}"  value="${pjp.journeyProductId}" name="buyJourney_hotel_${journey.prodJourenyId}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}">选择</span></td>
                                </tr>
								</#list>                                
                            </table>
                </div>   
			    <div class="superFreeSubTitleByProduct"  tt="hotel" prodJourneyId="${journey.prodJourenyId}"  productId="${product.productId}"   style="display:none;"><h4 class="gaiTitle">酒店详情</h4></div>
				<div class="superFreeSubMainByProduct"  tt="hotel" prodJourneyId="${journey.prodJourenyId}" productId="${product.productId}"   category="hotel_${journey.prodJourenyId}"  style="display:none;">
			    	<div class="jdxqBox">
			    		<#assign img_index=0 />
						<#list product.toPlace.placePhoto as placePhoto>
							<#if img_index==0 && placePhoto.type=='LARGE' && placePhoto.imagesUrl!=null><#assign img_index = img_index+1 /><img class="jiudianImg"  width="400" height="200" src="http://pic.lvmama.com/pics/${placePhoto.imagesUrl}"></#if>
						</#list>			        	
			            <div class="jdxqName">
			            	<h4>${product.toPlace.name}</h4>
			                <p>酒店地址：${product.toPlace.address}<br />
			               <br /> <!--设施服务：${product.toPlace.hotelFacilities}--></p>
			            </div>
			            <p class="jdxqText">
			            	开业时间：${product.toPlace.placeHotel.hotelOpenTimeStr}<br />
			                酒店电话：${product.toPlace.placeHotel.hotelPhone}<br />
			                酒店类型：${product.toPlace.placeHotel.hotelTopic}<br />
			                是否涉外：<#if product.toPlace.placeHotel.hotelForeigner=='true'>是<#else>否</#if>
			               <br />
			                房间数量(间)：${product.toPlace.placeHotel.hotelRoomNum}
			            </p>
			            <b class="jdxqB">酒店介绍：</b>
			            <p class="jdxqText">
			            ${product.toPlace.remarkes}
			            </p>
			            <b class="jdxqB">交通信息：</b>
			            <p class="jdxqText">
			           ${product.toPlace.placeHotel.hotelTrafficInfoHtml}
			            </p>
			        </div>
			    </div>	   
				</#list>	
                        <span class="btn_save superFreeBtnSave"  tt="hotel" prodJourneyId="${journey.prodJourenyId}"  >确定</span>
            </div>
        </div>        
    </div>	
</#macro>

<#macro showJourneyTraffic journey trafficProductList now_day adult child=0>
<input type="hidden" tt="traffic" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${journey.trafficPolicy}"/>

        <h6>交通</h6>
    	<div class="superFreeProdJourney" now_day="${now_day}" tt="traffic" prodJourneyId="${journey.prodJourenyId}">
				<div class="dayBox1">
		            <div class="dayBox1_R superFreeProdJourney_content" tt="traffic" prodJourneyId="${journey.prodJourenyId}">
		                      
		            </div>
		        </div> 
            		<span class="btn_tj superFreeProdJourneyAddBtn" tt="traffic" prodJourneyId="${journey.prodJourenyId}" >添加交通</span>
               
        </div>
	<div class="superFreeSubTitle" tt="traffic" prodJourneyId="${journey.prodJourenyId}"    style="display:none;"><h4 class="gaiTitle">更改交通<span>
								第
								${now_day}
								天${journey.toPlace.name}</span></h4></div>
    <div class="superFreeSubMain superFreeList" tt="traffic" prodJourneyId="${journey.prodJourenyId}"    category="traffic_${journey.prodJourenyId}"  style="display:none;">
        <div class="zuheDay zuheDayT">
            <div class="GaiBoxS">
				<#list trafficProductList as product>	
                <div class="dayBoxAll">
                    <table class="dayTable">
                                <tr>
                                    <th width="80">日期</th>
                                    <th width="150">时间/机场</th>
                                    <th width="120">航空公司</th>
                                    <th width="80">舱位</th>
                                    <th width="80">飞行总时长</th>
                                    <th width="80">总差价</th>
                                    <th width="80">操作</th>
                                </tr>
								<#list product.prodJourneyProductList as pjp>
                                <tr id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}" nights="${journey.maxTime.nights}">
                                    <td class="trafficBeginTime">${journey.beginTime?string('yyyy年MM月dd日')}</td>
                                    <td class="trafficInfo" flightNo="${product.goFlight.flightNo}"><b class="start_time">${product.goFlight.startTime}</b><em class="start_city">${product.goFlight.startPlaceName}</em>（<em class="start_jc">${product.goFlight.startAirport.airportName}</em>）<br/>
							   				<em class="end_time">${product.goFlight.arriveTime}</em><em class="end_city">${product.goFlight.arrivePlaceName}</em>（<em class="end_jc">${product.goFlight.arriveAirport.airportName}</em>）</td>
                                    <td class="trafficAirlineName"><em class="hk"><#if product.goFlight.airline??>${product.goFlight.airline.airlineName}</#if></em><br/><em class="hkh">${product.goFlight.flightNo}</em> <#--<em class="jx">320</em>--></td>
                                    <td class="trafficZhBerth">${pjp.prodBranch.zhBerth}</td>
                                    <td class="trafficZhFlightTime">${product.goFlight.zhFlightTime}</td>     
                                    <td <#if product.direction=='ROUND'>rowspan="2"</#if>><samp class="differenceOfPrices" journey="buyJourney_traffic_${journey.prodJourenyId}" journeyProductId="${pjp.journeyProductId}"  price="${pjp.calcTotalSellPrice(adult,child)}">--</samp></td>
                                    <td <#if product.direction=='ROUND'>rowspan="2"</#if>><span class="xuanBnt"   tt="traffic"  prodJourneyId="${journey.prodJourenyId}"  defaultProduct="${pjp.defaultProduct}" branchid="${pjp.prodBranchId}" quantity="${pjp.prodBranch.adultQuantity}" jpIds="${pjp.journeyProductIds}" goTime="${journey.beginTime?string('yyyy-MM-dd')}" backTime="${product.getBackDate(journey.beginTime)?string('yyyy-MM-dd')}"  value="${pjp.journeyProductId}" name="buyJourney_traffic_${journey.prodJourenyId}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}">选择</span></td>
                                </tr>
                                <#if pjp.prodBranch.prodProduct.direction=='ROUND' && product.backFlight??>
								<tr  id="tr_jp_${pjp.journeyProductId}_2">
									<td class="trafficBeginTime">${product.getBackDate(journey.beginTime)?string('yyyy年MM月dd日')}</td>
									<td class="trafficInfo" flightNo="${product.backFlight.flightNo}"><b class="start_time">${product.backFlight.startTime}</b><em class="start_city">${product.backFlight.startPlaceName}</em>（<em class="start_jc">${product.backFlight.startAirport.airportName}</em>）<br/>
													   <em class="end_time">${product.backFlight.arriveTime}</em><em class="end_city">${product.backFlight.arrivePlaceName}</em>（<em class="end_jc">${product.backFlight.arriveAirport.airportName}</em>）</td>
									<td class="trafficAirlineName"><em class="hk">${product.backFlight.airline.airlineName}</em><br/><em class="hkh">${product.backFlight.flightNo}</em> <#--<em class="jx">320</em>--></td>
									<td class="trafficZhBerth">${pjp.prodBranch.zhBerth}</td>
									<td class="trafficZhFlightTime">${product.backFlight.zhFlightTime}</td>
								</tr>
								</#if>
								</#list>                                
                            </table>
                </div>      
				</#list>	
                        <span class="btn_save superFreeBtnSave"  tt="traffic" prodJourneyId="${journey.prodJourenyId}"  >确定</span>
            </div>
        </div>        
    </div>		
</#macro>

<#--显示单产品，包含当地游、门票等-->
<#macro showSingleProduct title type journey ticketProductList total_person>
<input type="hidden" tt="${type}" prodJourneyId="${journey.prodJourenyId}" name="policy_${journey.prodJourenyId}"  value="${getPolicy(journey,type)}"/>
				<h6>${title}</h6>
		    	<div class="superFreeProdJourney" now_day="${now_day}" tt="${type}" prodJourneyId="${journey.prodJourenyId}">
		    			<div class="superFreeProdJourney_content" tt="${type}" prodJourneyId="${journey.prodJourenyId}" >
		    				
		    			</div>
		            		<span class="btn_tj superFreeProdJourneyAddBtn" tt="${type}" prodJourneyId="${journey.prodJourenyId}"  >添加<#if type=='route'>附加
																														<#else>景点						
																															</#if></span>
		                
		        </div>
		        
				<div class="superFreeSubTitle" tt="${type}" prodJourneyId="${journey.prodJourenyId}"     style="display:none;"><h4 class="gaiTitle">更改<#if type=='route'>附加
																																											<#else>景点						
																																											</#if><span>
								第
								${now_day}
								天${journey.toPlace.name}</span></h4></div>
                <div class="superFreeSubMain superFreeList" tt="${type}" prodJourneyId="${journey.prodJourenyId}"    category="${type}_${journey.prodJourenyId}"  style="display:none;">
			        <div class="zuheDay zuheDayT">
			            <div class="GaiBoxS">
							<#list ticketProductList as product>	
			                <div class="dayBoxAll">
			                    <div class="dayBox1">
			                        <div class="dayBox1_L"><#if product.smallImage!=null><img width="87" height="58" src="http://pic.lvmama.com/pics/${product.smallImage}"></#if></div>
			                        <div class="dayBox1_R">
			                            <h5><a href="javascript:void(0)" >${product.productName}</a></h5>
			                            <p>景点地址：${product.toPlace.address}</p>
			                        </div>
			                        
			                    </div>
			                    <table class="dayTable">
			                                <tr>
			                                    <th>
			                                    	<#if type=='route'>
													 当地游
													<#else>
													门票						
													</#if>
												</th>
			                                    <th width="100">日期</th>
			                                    <th width="50">数量</th>
			                                    <th width="80">操作</th>
			                                </tr>
											<#list product.prodJourneyProductList as pjp>
			                                <tr id="tr_jp_${pjp.journeyProductId}" pname="${product.productName}" bname="${pjp.prodBranch.branchName}" nights="${journey.maxTime.nights}">
			                                    <td><a href="javascript:void(0)" >${pjp.prodBranch.branchName}</a></td>
			                                    <td><select name="product_date_${pjp.journeyProductId}" journeyProductId="${pjp.journeyProductId}" prodBranchId="${pjp.prodBranch.prodBranchId}" timeList="[<#list pjp.timeInfos as ti><#if ti_index gt 0>,</#if>{'time':'${ti.date?date}','stock':${ti.stock}}</#list>]" smallImage="${product.smallImage}" productId="${product.productId}">                                    
                           							</select>
			                                    </td>
			                                    <td><select name="jp_${pjp.journeyProductId}" journeyProductId="${pjp.journeyProductId}" id="buyJourney_${type}_${journey.prodJourenyId}_${pjp.journeyProductId}" require="${pjp.require}" defaultProduct="${pjp.defaultProduct}" tt="${type?upper_case}" stock="${pjp.prodBranch.stock}" quantity="${pjp.prodBranch.adultQuantity}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}" >                                        
                            						</select></td>
			                                    <td><span class="xuanBnt" require="${pjp.require}" tt="${type}"  prodJourneyId="${journey.prodJourenyId}"  defaultProduct="${pjp.defaultProduct}"  value="${pjp.journeyProductId}" name="buyJourney_hotel_${journey.prodJourenyId}" placeBranchId="${product.toPlace.placeId}_${pjp.prodBranchId}">选择</span></td>
			                                </tr>
											</#list>                                
			                            </table>
			                </div>   
			                
						    <div class="superFreeSubTitleByProduct"  tt="${type}"  prodJourneyId="${journey.prodJourenyId}" productId="${product.productId}"    style="display:none;"><h4 class="gaiTitle"><#if type=='route'>当地游<#else>门票</#if>详情</h4></div>
							<div class="superFreeSubMainByProduct" tt="${type}"  prodJourneyId="${journey.prodJourenyId}" productId="${product.productId}"    category="hotel_${journey.prodJourenyId}"  style="display:none;">
						    	<div class="jdxqBox">
									<#list product.toPlace.placePhoto as placePhoto>
										<#if placePhoto.type=='LARGE' && placePhoto.imagesUrl!=null><img class="jiudianImg"  width="400" height="200" src="http://pic.lvmama.com/pics/${placePhoto.imagesUrl}"></#if>
									</#list>			        	
						            <div class="jdxqName">
						            	<h4>${product.productName}</h4>
						                <p>所属地区：${product.toPlace.city}<br />
						                景点地址：${product.toPlace.address}<br />
						            	开放时间：${product.toPlace.hotelOpenTime}<br />
						                景区主题：${product.toPlace.firstTopic}<br/></p>
						            </div>
			            			<p class="jdxqText">
			            			</p>
						            <b class="jdxqB">景点介绍：</b>
						            <p class="jdxqText">
						            ${product.toPlace.description}
						            </p>
						            
						            <b class="jdxqB">预订须知：</b>
						            <p class="jdxqText">
						           ${product.toPlace.orderNotice}
						            </p>
						            
						            <b class="jdxqB">行前须知：</b>
						            <p class="jdxqText">
						           ${product.toPlace.importantTips}
						            </p>
						        </div>
						    </div>	     
							</#list>	
			                        <span class="btn_save superFreeBtnSave"  tt="${type}" prodJourneyId="${journey.prodJourenyId}"  >确定</span>
			            </div>
			        </div>        
			    </div>	
</#macro>

<#macro showProdJourneyItem journey current_days adult child>
<#assign total_person=adult+child/>
<#assign now_day='' />
<#if current_days gt end_days>
	<#assign now_day=current_days />
<#else>
	<#list current_days..end_days as day>			
		<#assign now_day=now_day+day />
		<#if day_has_next>
			<#assign now_day=now_day+',' />
		</#if>				
	</#list>
</#if>
							<h4>
								第
								${now_day}
								天
								<span>${journey.toPlace.name}</span>
							</h4>
                            <p class="zuheDayP">
           							<#list viewJourneyList as viewJourney>
           								<#if now_day==viewJourney.seq>
           									<#assign content=viewJourney.content/>           									
           									${content?replace('\n','</br>')}</br>
           								</#if>
           							</#list>                 </p>
                            <div class="dayBoxAll">
								<#--大交通-->
								<#if journey.hasTraffic()>
								<@showJourneyTraffic journey journey.trafficProductList now_day adult child/>
								</#if>
								<#--酒店-->
	                            <#if journey.hasHotel()>
								<@showJourneyHotel journey journey.hotelProductList total_person now_day/>
								</#if>
								<#--景点门票-->
								<#if journey.hasTicket()>
								<@showSingleProduct "景点门票" "ticket" journey journey.ticketProductList total_person/>
								</#if>                            
								<!--*其他附加产品*-->
								<#if journey.hasRoute()>
								<@showSingleProduct "其他附加产品" "route" journey journey.routeProductList total_person/>
								</#if>  
                            </div>
		
</#macro>
