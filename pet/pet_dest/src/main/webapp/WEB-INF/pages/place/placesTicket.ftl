<!--上海景点推荐 S-->
<@s.if test="recPlacePrdSearchList.size>0">
<@s.if test='currentTab!="products"'>
<div class="dstnt_recomend">
    <@s.if test="recPlacePrdSearchList.size>=10"><a target="_blank" rel="nofollow" href="http://www.lvmama.com/search/ticket/-<@s.property value="currentPlace.name"/>.html" class="fr gray"  title="更多<@s.property value="currentPlace.name"/>景点推荐">更多&gt;&gt;</a></@s.if>
	<h3 class="dstnt_jdtj"><@s.property value="currentPlace.name"/>景点推荐</h3>
</div>
</@s.if>
<@s.iterator value="recPlacePrdSearchList" status="recPlace">
<div class="dstnt_list" <@s.if test="#recPlace.isLast()">style="border-bottom:none"</@s.if>>
        <!--listtopinfo-->
          <div class="dstnt_info dstnt_scenic_info">
			<span class="dstnt_info_img"><a rel="nofollow" href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="http://pic.lvmama.com/<@s.property value="place.imgUrl"/>"  width="120px;" height="60px;" alt="${place.name}"/></a></span>
			<span class="dstnt_info_text">
				<a class="dstnt_title" href="http://ticket.lvmama.com/scenic-<@s.property value="place.placeId"/>" target="_blank" >${place.name}</a>
				<#if place.tagList??>
				<#list place.tagList as t>
            		<span  <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
            	</#list>
            	</#if>
				<@s.if test="place.address!=null"><p class="dstnt_address">景点地址：${place.address?if_exists}</p></@s.if>
				<p >${place.remarkes?if_exists}</p>
			</span>
			<span class="dstnt_info_other">
				<p class="dstnt_price"><@s.if test="prdList!=null && prdList.size>0" ><@s.if test="place.productsPriceInteger!=null&&place.productsPriceInteger>0"><i class="dstnt_my">¥</i><label>${place.productsPriceInteger?if_exists}</label>起</@s.if></@s.if></p>
				<@s.if test="place.commentCount>0"><p class="dstnt_cmt"><a rel="nofollow" class="link_blue" href="http://www.lvmama.com/comment/<@s.property value="place.placeId"/>-1" target="_blank">${place.commentCount?if_exists}封</a>点评</p></@s.if>
				<@s.if test="place.avgScore>0">
				<p class="star_bg">
					<i class="ct_Star${place.roundHalfUpOfAvgScore?if_exists}"></i>
				</p>
				</@s.if>
			</span>
		  </div><!--listtopinfo-->
		   <@s.if test="prdList!=null && prdList.size>0" >
			<ul class="dstnt_list_title">
				<li class="dstnt_s_l_t_1">产品名称</li>
				<li class="dstnt_s_l_t_2">市场价</li>
				<li class="dstnt_s_l_t_3">驴妈妈价</li>
				<li class="dstnt_s_l_t_4">点评奖金<img title="您发表体验点评后将获得现金奖励" src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_help.jpg"></li>
			</ul>
			</@s.if>
			<#if prdList?? && prdList?size &gt; 0>
			<div id="dstnt_list_c">
			<@s.iterator value="prdList" status="prd">
				<ul class="dstnt_list_content">
					<li class="dstnt_s_l_t_1 dstnt_list_1">
					<#if isAperiodic?? && isAperiodic =="true">
						<a class="link_blue dstnt_recomend_link" data-pid="${productId?c}" href="http://www.lvmama.com/product/${productId?c}">${productName}</a>
					<#else>
						<a class="link_blue dstnt_recomend_link" data-pid="${productId?c}" href="javascript:void(0)">${productName}</a>
					</#if>
					<#include "/WEB-INF/pages/place/ticket_tags.ftl" />
					</li>	
					<li class="dstnt_s_l_t_2 dstnt_list_2">&yen;${marketPriceInteger?if_exists}</li>
					<li class="dstnt_s_l_t_3 dstnt_list_3">&yen;${sellPriceInteger?if_exists}</li>
					<li class="dstnt_s_l_t_4 dstnt_list_4">&yen;${cashRefund?if_exists}</li>
					<li class="dstnt_s_l_t_5 dstnt_list_5">
					<#if isAperiodic?? && isAperiodic =="true">
						<a class="dstnt_btn"  data-pid="${productId?c}" href="http://www.lvmama.com/product/${productId?c}">预 订</a>
					<#else>
						<a class="dstnt_btn dstnt_order_btn time-price ibmclass"  data-pid="${productId?c}" ibmc="cmCreateShopAction5s('${productId?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(productName)"/>','${sellPriceInteger?if_exists}','${subProductType?if_exists}')" href="javascript:void(0)">预 订</a>
					</#if>
					</li>
					<li class="dstnt_llclear"></li>
				</ul>
				
				<div class="dstnt_action">
					<table class="dstnt_yd_table">
					<tbody>
					<tr>
						<td>
						</td>
					</tr>
					</tbody></table>
					<div data-pid="${productId?c}" data-bid="" id="timePrice${productId?c}">
					</div>

					<div class="dstnt_textright"><a href="javascript:void(0)" class="dstnt_jtop_gray">收起  <img src="http://pic.lvmama.com/img/new_v/ob_destnt/dstnt_jtop_gray.jpg"></a></div>
				</div>
				</@s.iterator>
			</div>
			</#if>
		</div>
		</@s.iterator>
</@s.if>
 <!--上海景点推荐 E-->
