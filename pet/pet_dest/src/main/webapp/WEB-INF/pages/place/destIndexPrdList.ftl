<@s.iterator value="prdList" status="prd"> 
 <div class="dstnt <@s.if test="!#prd.isFirst()">dstnt_line</@s.if>">
		<div class="dstnt_info dstnt_other_info">
			<span class="dstnt_info_img">
				<a href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="${smallImageUrl?if_exists}" width="120px" height="60px" alt="<@s.property value='productName'/>"></a>
				<@s.if test="prodRouteTypeName!=null">
				<div class="dstnt_none">
					<span class="dstnt_red <@s.if test='prodRouteTypeName.contains("经济")'>dstnt_red_cur</@s.if>">经济</span>
					<span class="dstnt_red <@s.if test='prodRouteTypeName.contains("舒适")'>dstnt_red_cur</@s.if>">舒适</span>
					<span class="dstnt_red <@s.if test='prodRouteTypeName.contains("豪华")'>dstnt_red_cur</@s.if>">豪华</span>
				</div>
				<div class="dstnt_bottom">
					<span class="dstnt_red_all <@s.if test='prodRouteTypeName.contains("经济")'>dstnt_red_over</@s.if><@s.else>dstnt_red_out</@s.else>"></span>
					<span class="dstnt_red_all <@s.if test='prodRouteTypeName.contains("舒适")'>dstnt_red_over</@s.if><@s.else>dstnt_red_out</@s.else>"></span>
					<span class="dstnt_red_all <@s.if test='prodRouteTypeName.contains("豪华")'>dstnt_red_over</@s.if><@s.else>dstnt_red_out</@s.else>"></span>
				</div>
				</@s.if>
			</span>
			<span class="dstnt_info_text">
				<a class="dstnt_title" target="_blank" href="http://www.lvmama.com/product/<@s.property value="productId"/>" ><@s.property value='productName' escape="false"/><@s.if test="tagsImage!=null"><@s.iterator value="tagsImage.split(',')" status="tag"><span class="icon<@s.property/>"></span></@s.iterator></@s.if>
				</a>
				<#if tagList??>
					<#list tagList as t>
		            	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'>
		            		<span <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
		            	</#if>
		            </#list>
	            </#if>
				<p class="dstnt_desc">
				<@s.property value="recommendReason" escape="false" /></p>
				<p class="dstnt_address labelW190"><@s.if test="fromDest!=null"><label class="dstnt_label_w150">出发地：${fromDest?if_exists}</label></@s.if><@s.if test="visitDay!=null"><label class="dstnt_label_w150">游玩天数：${visitDay?if_exists}天</label></@s.if><@s.if test="trafficHandle!=null"><label class="dstnt_label_w150 dstnt_label_wfjt" title="<@s.property value="trafficHandle"/>">往返交通：<@s.property value='trafficHandle' escape="false" /></label></@s.if></p>
				<@s.if test='isAperiodic != "true"'>
					<p class="dstnt_address labelW190">
					<@s.if test="travelTime!=null"><label class="dstnt_label_w190">最近团期：${travelTime?if_exists}</label></@s.if>
					<span class="dstnt_label_w190"><a href="javascript:void(0)" class="dstnt_more_a time-price link_blue" data-pid="<@s.property value="productId"/>" linktype="product"><@s.if test="travelTime!=null">更多</@s.if><@s.else>查看</@s.else>团期</a><i class="dstnt_more_tuan dstnt_m_down"></i></span></p>
				</@s.if>
			</span>
			<#include "/WEB-INF/pages/place/tags_price.ftl" />
			<div class="dstnt_clear animate" id="timePrice<@s.property value="productId"/>" data-bid="" data-pid="<@s.property value="productId"/>"></div>
		</div>
	</div>
	 </@s.iterator>