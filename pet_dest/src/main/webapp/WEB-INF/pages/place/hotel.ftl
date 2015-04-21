<!--上海酒店预订 S-->
<@s.if test=" recHotelPrdSearchList.size>0 ">
<@s.if test='currentTab!="products"'>
<div class="dstnt_recomend">
    <@s.if test="recHotelPrdSearchList.size>=10 "><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/hotel/-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>预订">更多&gt;&gt;</a></@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="currentPlace.name"/>酒店预订</h3>
</div>
</@s.if>
<@s.iterator value="recHotelPrdSearchList"  status="hotel" >
<div class="dstnt_list">
            <div class="dstnt_info dstnt_hotel_info">
			<span class="dstnt_info_img"><a rel="nofollow" target="_blank" href="http://www.lvmama.com/dest/<@s.property value="place.pinYinUrl"/>"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="http://pic.lvmama.com/<@s.property value="place.imgUrl"/>" alt="${place.name}" width="120px;" height="60px;"><i style="display: block;" class="dstnt_info_i">${place.hotelType?if_exists}</i></a></span>
			<span class="dstnt_info_text">
				<a class="dstnt_title" target="_blank" href="http://www.lvmama.com/dest/<@s.property value="place.pinYinUrl"/>">${place.name?if_exists}</a><span class="mdd_s${place.hotelStar?if_exists}"></span>
				<@s.if test="place.address!=null"><p class="dstnt_address">酒店地址：${place.address?if_exists}</p></@s.if>
				<@s.if test="place.hotelFacilities!=null"><p class="dstnt_address">特色设施：${place.hotelFacilities?if_exists?replace(',',' ')}</p></@s.if>
				<@s.if test="coorPlace!=null&&coorPlace.size()>0"><p class="dstnt_address">周边景区：<@s.iterator value="coorPlace"><@s.property value="name"/> </@s.iterator></p></@s.if>
				<p ><@s.property value='place.remarkes' escape="false" /></p>
			</span>
			<span class="dstnt_info_other">
				<p class="dstnt_price"><@s.if test="(singleRoomList!=null && singleRoomList.size>0)||(hotelSuitList!=null && hotelSuitList.size>0)" ><@s.if test="place.productsPriceInteger!=null && place.productsPriceInteger>0"><i class="dstnt_my">¥</i><label>${place.productsPriceInteger?if_exists}</label>起</@s.if></@s.if></p>
				<@s.if test="place.commentCount>0"><p class="dstnt_cmt"><a rel="nofollow" class="link_blue" href="http://www.lvmama.com/comment/<@s.property value="place.placeId"/>-1" target="_blank">${place.commentCount?if_exists}封</a>点评</p></@s.if>
				<@s.if test="place.avgScore>0">
				<p class="star_bg">
					<i class="ct_Star${place.roundHalfUpOfAvgScore?if_exists}"></i>
				</p>
				</@s.if>
			</span>
		</div><!--dstnt_hotel_info-->
		<@s.if test="singleRoomList!=null && singleRoomList.size>0" >
			<ul class="dstnt_list_title">

				<li class="dstnt_hotel_1">房型服务</li>
				<li class="dstnt_hotel_6">床型</li>
				<li class="dstnt_hotel_5">宽带</li>
				<li class="dstnt_s_l_t_2">市场价</li>
				<li class="dstnt_s_l_t_3">驴妈妈价</li>
				<li class="dstnt_s_l_t_4">点评奖金<img title="您发表体验点评后将获得现金奖励" src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_help.jpg"></li>

			</ul>
			<div id="dstnt_list_c1<@s.property value="#hotel.index"/>">
			  <@s.set name="status" value="0"/> 
                <@s.iterator value="singleRoomList" status="prd">
					<@s.iterator value="prodBranchSearchInfoList" status="prdb">
					 <@s.set name="status" value="#status+1"/> 
					
				<ul class="dstnt_list_content <@s.if test="#status>3">dn</@s.if>">
					<li class="dstnt_hotel_1 dstnt_list_1">
						<a class="h_img  link_blue" data-tp-id="${prodBranchId!''}" href="javascript:void(0)">
						<@s.if test="icon !=null"><img src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_hotel_cur.jpg"></@s.if>&nbsp;${branchName?if_exists}</a>
						<#include "/WEB-INF/pages/place/ticket_tags.ftl" />
					</li>
					<li class="dstnt_hotel_6">${bedType!''}&nbsp;</li>
					<li class="dstnt_hotel_5">${broadbandStr!''}&nbsp;</li>
					<li class="dstnt_s_l_t_2 dstnt_list_2">￥${marketPriceInteger!''}&nbsp;</li>
					<li class="dstnt_s_l_t_3 dstnt_list_3">￥${sellPriceInteger!''}&nbsp;</li>
					<li class="dstnt_s_l_t_4 dstnt_list_4">￥${cashRefund!''}&nbsp;</li>

					<#if validBeginTime?? >
						<li class="dstnt_s_l_t_5 dstnt_list_5"><a class="dstnt_btn" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranchId?c}">预 订</a></li>	
					<#else>
						<li class="dstnt_s_l_t_5 dstnt_list_5 "><a class="dstnt_btn dstnt_hotel_btn time-price ibmclass" data-bid="<@s.property value="prodBranchId" escape="false"/>" data-pid="<@s.property value="productId" escape="false"/>" ibmc="cmCreateShopAction5s('${prodBranchId?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(branchName)"/>','${sellPriceInteger!''}','${subProductType?if_exists}')" href="javascript:void(0)">预 订</a></li>
					</#if>
					<li class="dstnt_llclear"></li>
				</ul>
				<div class="dstnt_detail">
					<div class="dstnt_detail_icon"></div>
					<div class="dstnt_detail_bg">
						<div class="dstnt_info">
							<span class="dstnt_info_img"><@s.if test="icon !=null"><a rel="nofollow" href="javascript:void(0);"><img width="120" height="60" src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="http://pic.lvmama.com/pics/${icon!''}"></a></@s.if></span>
							<span class="dstnt_info_text" style="width:90%">
							<@s.if test="validBeginTime != null">产品有效期：${validBeginTime?string("yyyy-MM-dd")} 至 ${validEndTime?string("yyyy-MM-dd")}<@s.if test='invalidDateMemo != null && invalidDateMemo != ""'>(${invalidDateMemo})</@s.if><br/></@s.if><@s.if test="description != null"><p class="dstnt_d_value" style="width:90%">${description?replace('\n','<br/>')}</p></@s.if></span>
							<div class="dstnt_clear"></div>
						</div>
					</div>
				</div>
				<div class="dstnt_detail">
					<div class="dstnt_detail_icon"></div>
					<div class="dstnt_detail_bg">
                          <div id="timePrice<@s.property value="productId" escape="false"/>${prodBranchId?if_exists?c}"  data-pid="<@s.property value="productId" escape="false"/>" data-bid="${prodBranchId?if_exists?c}"></div>
					</div>
				</div>
                     </@s.iterator>
	       </@s.iterator>
	       <@s.if test="#status>4">
				     <div class="dstnt_textright dstnt_more_i"><i class="dstnt_more_tuan dstnt_m_down"></i><a class="link_blue" href="javascript:void(0)" attr_show="#dstnt_list_c1<@s.property value="#hotel.index"/> ul.dn">更多</a></div>
			</@s.if>
			</div>
         </@s.if>
         
         <@s.if test="hotelSuitList!=null && hotelSuitList.size>0" >
			<ul class="dstnt_list_title">
				<li class="dstnt_hotel_1">套餐服务</li>
				<li class="dstnt_hotel_6">床型</li>
				<li class="dstnt_hotel_5">宽带</li>
				<li class="dstnt_s_l_t_2">市场价</li>
				<li class="dstnt_s_l_t_3">驴妈妈价</li>
				<li class="dstnt_s_l_t_4">点评奖金<img title="您发表体验点评后将获得现金奖励" src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_help.jpg"></li>
				<li class="dstnt_hotel_6">&nbsp;</li>
			</ul>
			<div id="dstnt_list_c2<@s.property value="#hotel.index"/>">
			  <@s.set name="suitStatus" value="0"/> 
                <@s.iterator value="hotelSuitList" status="hotelSuit">
					 <@s.set name="suitStatus" value="#suitStatus+1"/> 
				<ul class="dstnt_list_content <@s.if test="#suitStatus>3">dn</@s.if>">
					<li class="dstnt_hotel_1 dstnt_list_1">
						<a class="link_blue" href="http://www.lvmama.com<@s.property value="productUrl"/>" target="_blank">${productName?if_exists}</a>
						<@s.if test= 'isAperiodic != null && isAperiodic == "true"' ><span tip-content="无需选择入住时间，仅需在产品有效期内致电商家进行预约，让您的出行更加自由" class="tags101" >期票</span></@s.if>
					</li>
					<li class="dstnt_hotel_6"><@s.property value="bedType"/>&nbsp;</li>
					<li class="dstnt_hotel_5"><@s.property value="broadbandStr"/>&nbsp;</li>
					<li class="dstnt_s_l_t_2 dstnt_list_2">￥${marketPriceInteger!''}&nbsp;</li>
					<li class="dstnt_s_l_t_3 dstnt_list_3">￥${sellPriceInteger!''}&nbsp;</li>
					<li class="dstnt_s_l_t_4 dstnt_list_4">￥${cashRefund!''}&nbsp;</li>
					<li class="dstnt_s_l_t_5 dstnt_list_5"><a class="dstnt_freelink" href="http://www.lvmama.com<@s.property value="productUrl"/>" target="_blank"></a></li>
					<li class="dstnt_llclear"></li>
				</ul>
	       </@s.iterator>
	       <@s.if test="#suitStatus>4">
				     <div class="dstnt_textright dstnt_more_i"><i class="dstnt_more_tuan dstnt_m_down"></i><a class="link_blue" href="javascript:void(0)" attr_show="#dstnt_list_c2<@s.property value="#hotel.index"/> ul.dn">更多</a></div>
			</@s.if>
			</div>
         </@s.if>
         
		</div>
		</@s.iterator>
 </@s.if>
<!--上海酒店预订 E-->
