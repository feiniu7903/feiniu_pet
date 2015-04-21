<@s.iterator value="prdList" status="prd"> 
 	<div class="dstnt <@s.if test="!#prd.isFirst()">dstnt_line</@s.if>">
		<div class="dstnt_info dstnt_other_info">
			<span class="dstnt_info_img">
				<a rel="nofollow" href="javascript:void(0)"><img src="http://pic.lvmama.com/img/new_v/ui_scrollLoading/pixImagePath.gif" to="${smallImageUrl?if_exists}" width="120px" height="60px" alt="<@s.property value='productName'/>"></a>
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
				<a class="dstnt_title" target="_blank" href="http://www.lvmama.com/product/<@s.property value="productId"/>" class="pro-tit" target="_blank" ><@s.property value='productName' escape="false"/><@s.if test="tagsImage!=null"><@s.iterator value="tagsImage.split(',')" status="tag"><span class="icon<@s.property/>"></span></@s.iterator></@s.if></a>
				<#if tagList?? >
				<#list tagList as t>
	            	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'>
	            		<span <#if t.description!="" >tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span>
	            	</#if>
	            </#list>
	            </#if>
				<@s.if test="recommendReason!=null"><p><@s.property value='recommendReason' escape="false"/></p></@s.if>
				<p class="dstnt_address"><@s.if test="visitDay!=null"><label class="dstnt_label_w190">游玩天数：${visitDay?if_exists}天</label></@s.if>
				<@s.if test='playNumHandle!=null && playNumHandle!=""'><label class="dstnt_label_w190">游玩人数：${playNumHandle?if_exists}</label></@s.if></p>
				<@s.if test="scenicPlace!=null"><p class="dstnt_address">包含景点：${scenicPlace?if_exists?replace(',',' ')}</p></@s.if>
				<@s.if test='hotelTypeHandle!=null && hotelTypeHandle!=""'><p class="dstnt_address">酒店类型：${hotelTypeHandle?if_exists}</p></@s.if>
				
				<@s.if test='isAperiodic == "true"'></@s.if><@s.else><@s.if test="travelTime!=null"><p class="dstnt_address">出发时间：${travelTime?if_exists} <#if travelTime?matches("(\\d{2}/\\d{2},)+(\\d{2}/\\d{2})")><a rel="nofollow" target="_blank"  href="http://www.lvmama.com/product/<@s.property value="productId"/>" class="sc_pro_tit">更多</a></#if></p></@s.if></@s.else>
			</span>
			<#include "/WEB-INF/pages/place/tags_price.ftl" />
		</div>
	</div>
	 </@s.iterator>