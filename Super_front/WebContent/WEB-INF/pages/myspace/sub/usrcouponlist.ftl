<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的优惠券-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-coupon">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap"><p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; <a class="current">我的优惠券</a></p></div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
					<!-- 我的优惠券>> -->
					<div class="ui-box mod-top mod-info">
						<div class="ui-box-container clearfix">
					    	<div class="info-detail fl info-coupon">
					        <div class="hv_blank fr"></div>
					        	<h3>优惠券张数</h3>
					            <p class="text_bottom"><dfn class="info-num"><i><@s.property value='countMap.USE_COUNT'/></i>张</dfn>　<a href="http://www.lvmama.com/public/help_283" target="_blank">[优惠券使用说明]</a></p>
					            <p class="lv-cb">已使用优惠券：<dfn><i><@s.property value='countMap.USED_AMOUNT'/></i></dfn>元</p>
					        </div>
					        <div class="info-tips lv-cc fl">
					        <h4>提示：</h4>
					        <p>优惠券的号码复制到订单确定页面就能使用哦。</p> 
					        </div>
					    </div>
					</div>
					
					<div id="lv-tabs" class="ui-box mod-edit coupon-edit">
						<div class="ui-tab-title"><h3>优惠券明细</h3>
						<input type="hidden" name="used" value="<@s.property value="used"/>">
						<input type="hidden" name="currentPage" value="<@s.property value="currentPage"/>">
						<input type="hidden" name="page" value="<@s.property value="page"/>">
					    <ul class="lv-tabs-nav hor">
					    <#if used="false">
						    <li   class="lv-tabs-nav-selected"><a href="javascript:void(0);">可用(<@s.property value='countMap.USE_COUNT'/>)</a></li>
						    <li><a href="${base}/myspace/account/coupon.do?used=true&page=${currentPage}&currentPage=${page}" target="_parent">已使用(<@s.property value='countMap.USED_COUNT'/>)</a></li>
						<#else>
						    <li><a href="${base}/myspace/account/coupon.do?used=false&page=${currentPage}&currentPage=${page}" target="_parent">可用(<@s.property value='countMap.USE_COUNT'/>)</a></li>
						    <li  class="lv-tabs-nav-selected"><a href="javascript:void(0);">已使用(<@s.property value='countMap.USED_COUNT'/>)</a></li>
					    </#if>
					    </ul>
						</div>
						<div id="tabs-1" class="ui-tab-box">
					        <#include "/WEB-INF/pages/myspace/sub/coupon/coupon_list_used_${used}.ftl"/>
							<div class="page_order">
							<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
							</div>
						</div>
					</div><!-- <<我的优惠券 -->
				</div>
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<#include "/WEB-INF/pages/myspace/sub/footer/usrcouponlist_footer.ftl"/>
	<script>
		cmCreatePageviewTag("我的优惠劵", "D0001", null, null);
	</script>
</body>
</html>


