<!--========= 酒店套餐 E ===========-->
<!--listbox1-->
<#global singleRoomItem_index=0 />
<#list singleRoomList as singleRoom>
	<#list singleRoom.prodBranchSearchInfoList as prodBranch>
    	<#global singleRoomItem_index=singleRoomItem_index+1/>
    </#list>
</#list>
<#if singleRoomList?? && singleRoomList.size() gt 0 && singleRoomItem_index gt 0> 
	<h3 class="h3_tit"><span>房型预订</span></h3>
    <div class="l_row l_select_travel" id="row_1">
    	<div class="l_hotel_Information">
        	<div class="l_hotel_room">
            	<dl class="l_hotel_room_header">
                	<dt>房型</dt>
                    <dd>市场价</dd>
                    <dd>驴妈妈价</dd>
                    <dd>点评奖金<img width="13" height="13" class="wen" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" title="您发表体验点评后将获得现金奖励"></dd>
                    <dd>宽带</dd>
                    <dd>床型</dd>
                    <dd>在线预订</dd>
                </dl>
                <div class="l_hotel_panes">
                	<#list singleRoomList as singleRoom>
                    	<#list singleRoom.prodBranchSearchInfoList as prodBranch>
	                        <div class="l_hotel_pane">
	                            <dl>
	                                <dt <#if !prodBranch.icon??>class="background_none"</#if>><a href="javascript:void(0)" data-tp-id="${prodBranch.prodBranchId?if_exists?c}" class="l_clickShow">${prodBranch.branchName}</a>
	                                <#if prodBranch.validBeginTime??>
						            	<span tip-content="无需选择入住时间，仅需在产品有效期内致电商家进行预约，让您的出行更加自由" class="tags101" >期票</span>
					        		</#if>
	                                </dt>
	                                <dd><del>¥${prodBranch.marketPriceInteger?if_exists}&nbsp;</del></dd>
	                                <dd><strong>¥${prodBranch.sellPriceInteger?if_exists}&nbsp;</strong></dd>
	                                <dd><strong><span>¥${prodBranch.cashRefund?if_exists}&nbsp;</span></strong></dd>
	                                <dd>${prodBranch.broadbandStr?if_exists}&nbsp;</dd>
	                                <dd>${prodBranch.bedType?if_exists}&nbsp;</dd>
	                                <dd>
	                                <#if prodBranch.validBeginTime??>
	                                <a target="_blank" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranch.prodBranchId?c}" class="l_recommend_yuding" ></a>
	                                <#else>
	                                <span class="l_recommend_yuding time-price" data-pid="${prodBranch.productId?if_exists?c}" data-bid="${prodBranch.prodBranchId?if_exists?c}" ></span>
	                                </#if>
	                                </dd>
	                            </dl>
	                            <div id="timePrice${prodBranch.productId?if_exists?c}${prodBranch.prodBranchId?c}" data-bid="${prodBranch.prodBranchId?c}" data-pass-type="hotel" style="display:none;"></div>
		                        <ul class="l_hotel_detail">
		                        	<li <#if !prodBranch.icon??> class="l_hotel_li_noimg"</#if>><#if prodBranch.icon??><a href="javascript:void(0);" onclick='<@s.property value="singleRoom.productId"/>'><img width="120" height="60" src="http://pic.lvmama.com/pics/${prodBranch.icon?if_exists}" class="img_block" /></a><#else>&nbsp;</#if><span class="l_clickHiden">隐藏</span></li>
		                            <li class="l_hotel_detail_li">
		                            	<#if prodBranch.validBeginTime?? >
		                            	产品有效期：${prodBranch.validBeginTime?string("yyyy-MM-dd")} 至 ${prodBranch.validEndTime?string("yyyy-MM-dd")}<#if prodBranch.invalidDateMemo?? >(${prodBranch.invalidDateMemo})</#if><br/></#if>
		                            	<#if prodBranch.description??>${prodBranch.description?replace('\n','<br/>')}</#if>
		                            </li>
		                        </ul>
	                        </div><!--l_hotel_pane end-->
                        </#list>
                    </#list>
                </div><!--l_hotel_panes end-->
            </div><!--l_hotel_room end-->
        </div><!--l_hotel_Information end-->
    </div> <!--listbox2 end-->
</#if>
        
<#if hotelSuitList?? && hotelSuitList.size() gt 0>
	<h3 class="h3_tit"><span>套餐预订</span></h3>
    <div class="l_row l_select_travel" id="row_1">
        <div class="l_hotel_Information">
           	<div class="l_hotel_room">
               	<dl class="l_hotel_room_header">
                	<dt>房型</dt>
                    <dd>市场价</dd>
                    <dd>驴妈妈价</dd>
                    <dd>点评奖金<img width="13" height="13" class="wen" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" title="您发表体验点评后将获得现金奖励"></dd>
                    <dd>宽带</dd>
                    <dd>床型</dd>
                    <dd>在线预订</dd>
                </dl>
                <div class="l_hotel_panes">
                	<#list hotelSuitList as hotelSuit>
                    	<div class="l_hotel_pane">
                           	<dl>
	            	            <dt><a href="http://www.lvmama.com${hotelSuit.productUrl?if_exists}">${hotelSuit.productName?if_exists}</a>
	            	            <#if hotelSuit.isAperiodic?? && hotelSuit.isAperiodic == "true">
					            	<span tip-content="无需选择入住时间，仅需在产品有效期内致电商家进行预约，让您的出行更加自由" class="tags101">期票</span>
				        		</#if>
	            	            </dt>
	                            <dd><del>¥${hotelSuit.marketPriceInteger?if_exists}&nbsp;</del></dd>
	                            <dd><strong>¥${hotelSuit.sellPriceInteger?if_exists}&nbsp;</strong></dd>
	                            <dd><strong><span>¥${hotelSuit.cashRefund?if_exists}&nbsp;</span></strong></dd>
	                            <dd>${hotelSuit.broadbandStr?if_exists}&nbsp;</dd>
	                            <dd>${hotelSuit.bedType?if_exists}&nbsp;</dd>
	                            <dd><a href="http://www.lvmama.com${hotelSuit.productUrl?if_exists}" class="l_recommend_xiangqing"></a></dd>
                            </dl>
                        </div>
                    </#list>
                </div><!--l_hotel_panes end-->
            </div><!--l_hotel_room end-->
        </div><!--l_hotel_Information end-->
    </div><!--listbox2 end--> 
</#if>
