<div class="h-hotel-word">
<dl class="h-products">
<dd class="h-products-content">
    <#if singleRoomList?? && singleRoomList.size() gt 0>
    <div class="h-hotel-details">
        <dl class="h-title">
            <dt class="h-product-name">房型</dt>
            <dd class="h-online-order"></dd>
            <#if markCouponMap?? && markCouponMap.size() gt 0>
                <dd class="h-coupon">优惠券再减</dd>
            </#if>
            <dd class="h-lv-price">驴妈妈价</dd>
            <dd class="h-market-price">市场价</dd>
        </dl>
        <ul class="h-contain-out">
                <#list singleRoomList as productSearchInfo>
                    <#list productSearchInfo.prodBranchSearchInfoList as prodBranch>
                    <li>
                    <dl class="h-contain">
                        <dt class="h-product-name">
                            <span class="h-product-title"><a href="#" hidefocus="false">${prodBranch.branchName?if_exists}</a><i class="h-div-show"></i></span>
                            <#include "holidayHotel_tags.ftl">
                            <#if prodBranch.cashRefund?? && prodBranch.cashRefund != '0'>
                            <span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品,入住后发表体验点评，内容通过审核，即可获得<span>${prodBranch.cashRefund?if_exists}</span>元点评奖金返现"><em>返</em><i>
                                <span class="money-tag">¥</span>${prodBranch.cashRefund?if_exists}</i>
                            </span>
                            </#if>
                        </dt>
                        <dd class="h-online-order">
                            <#if prodBranch.validBeginTime??>
                                <a target="_blank" href="http://www.lvmama.com/buy/fill.do?buyInfo.prodBranchId=${prodBranch.prodBranchId?if_exists?c}" class="hOrder show-calendar" ></a>
                            <#else>
                                <button class="hOrder show-calendar time-price ibmclass" data-pid="${prodBranch.productId?if_exists?c}" data-bid="${prodBranch.prodBranchId?if_exists?c}" ibmc="cmCreateShopAction5s('${prodBranch.productId?if_exists?c}','<@s.property value="@com.lvmama.comm.utils.StringUtil@outputCoremetricsParam(prodBranch.branchName)"/>','${prodBranch.sellPriceInteger?if_exists}','${prodBranch.subProductType?if_exists}')">预订</button>
                            </#if>
                        </dd>
                        <#include "holidayMarkCoupon.ftl">
                        <dd class="h-lv-price">
                            <dfn> ¥ <i>${prodBranch.sellPriceInteger?if_exists?c}</i> </dfn>
                        </dd>
                        <dd class="h-market-price">
                            <dfn> ¥ <i>${prodBranch.marketPriceInteger?if_exists?c}</i> </dfn>
                        </dd>
                    </dl>
                    <div class="h-detail h-hotel-detail" style="display: none;">
                        <i class="h-triangle"></i>
                           <#if prodBranch.validBeginTime?? >
                                产品有效期：${prodBranch.validBeginTime?string("yyyy-MM-dd")} 至 ${prodBranch.validEndTime?string("yyyy-MM-dd")}
                                <#if prodBranch.invalidDateMemo?? >(${prodBranch.invalidDateMemo})</#if><br/>
                           </#if>
                           <#if prodBranch.description??>${prodBranch.description?replace('\n','<br/>')}</#if>
                        <a class="h-pack-up">收起<i></i></a>
                    </div>
                    <div class="h-detail h-calendar-detail" style="display: none;">
                        <span class="arrow-top"><i></i></span>
                        <div>
                            <#if markCouponMap?? && markCouponMap.size() gt 0>
                                <a target="_blank" href="http://www.lvmama.com/zt/promo/youhuiquan/" hidefocus="false">
                                    <img class="h-coupon-img" width="660" height="60" src="http://pic.lvmama.com/img/v3/holiday/coupon.jpg">
                                </a>                            
                            </#if>
                            <div id="timePrice${prodBranch.productId?if_exists?c}${prodBranch.prodBranchId?if_exists?c}" data-bid="${prodBranch.prodBranchId?if_exists?c}" data-pass-type="hotel" style="display:none;"></div>
                            <a class="h-pack-up" hidefocus="false">收起<i></i></a>
                        </div>
                    </div>
                  </li>
                  </#list>
                </#list>
            </li>
        </ul>
    </div>
    </#if>
    <#if holidayHotelTuanGouList?? && holidayHotelTuanGouList.size() gt 0>
    <div class="h-group">
        <dl class="h-title">
            <dt class="h-product-group">团购自由行套餐</dt>
            <dd class="h-online-order"></dd>
            <#if markCouponMap?? && markCouponMap.size() gt 0>
                <dd class="h-coupon"></dd>
            </#if>
            <dd class="h-lv-price"></dd>
            <dd class="h-market-price"></dd>
        </dl>
        <ul class="h-contain-out">
            <#list holidayHotelTuanGouList as productSearchInfo>
            <li>
                <dl class="h-contain">
                    <dt class="h-product-group">
                        <#if productSearchInfo.topic??>
                            <#list productSearchInfo.topic?split(",") as topic><a href="http://www.lvmama.com/search/hotel/${topic}.html" target="_blank" rel="nofollow"><span class="color-orange">[${topic}]</span></a></#list>
                        </#if>
                        <a href="http://www.lvmama.com${productSearchInfo.productUrl?if_exists}" target="_blank">${productSearchInfo.productName?if_exists}</a>
                        <#include "holidayHotel_tags.ftl">
                        <#if productSearchInfo.cashRefund?? && productSearchInfo.cashRefund != '0'>
                            <span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品,入住后发表体验点评，内容通过审核，即可获得<span>${productSearchInfo.cashRefund?if_exists}</span>元点评奖金返现"><em>返</em><i>
                            <span class="money-tag">¥</span>${productSearchInfo.cashRefund?if_exists}</i></span>
                        </#if>        
                    </dt>
                    <dd class="h-online-order">
                        <a class="hSee show-calendar" href="http://www.lvmama.com${productSearchInfo.productUrl?if_exists}" rel="nofollow" target="_blank">查看</a>
                    </dd>
                    <#include "holidayMarkCoupon.ftl">
                    <dd class="h-lv-price">
                        <dfn> ¥ <i>${productSearchInfo.sellPriceInteger?if_exists?c}</i> </dfn>
                    </dd>
                    <dd class="h-market-price">
                        <dfn> ¥ <i>${productSearchInfo.marketPriceInteger?if_exists?c}</i> </dfn>
                    </dd>
                </dl>
            </li>
        </#list>
        </ul>
    </div>
    </#if>
    <#if holiDayHotelFrontList?? && holiDayHotelFrontList.size() gt 0>
    <div class="h-single">
        <dl class="h-title">
            <dt class="h-product-group">自由行套餐</dt>
            <dd class="h-online-order"></dd>
            <#if markCouponMap?? && markCouponMap.size() gt 0>
                <dd class="h-coupon"></dd>
            </#if>
            <dd class="h-lv-price"></dd>
            <dd class="h-market-price"></dd>
        </dl>
        <ul class="h-contain-out">
           <#list holiDayHotelFrontList as productSearchInfo>
            <li>
                <dl class="h-contain">
                    <dt class="h-product-group">
                        <#if productSearchInfo.topic??>
                            <#list productSearchInfo.topic?split(",") as topic><a href="http://www.lvmama.com/search/hotel/${topic}.html" target="_blank" rel="nofollow"><span class="color-orange">[${topic}]</span></a></#list>
                        </#if>
                        <a href="http://www.lvmama.com${productSearchInfo.productUrl?if_exists}" target="_blank">${productSearchInfo.productName?if_exists}</a>
                        <#include "holidayHotel_tags.ftl">
                        <#if productSearchInfo.cashRefund?? && productSearchInfo.cashRefund != '0'>
                             <span class="tagsback" tip-title="写体验点评返奖金" tip-content="预订此产品,入住后发表体验点评，内容通过审核，即可获得<span>${productSearchInfo.cashRefund?if_exists}</span>元点评奖金返现"><em>返</em><i>
                             <span class="money-tag">¥</span>${productSearchInfo.cashRefund?if_exists}</i></span>
                        </#if>        
                    </dt>
                    <dd class="h-online-order">
                        <a class="hSee show-calendar" href="http://www.lvmama.com${productSearchInfo.productUrl?if_exists}" rel="nofollow" target="_blank">查看</a>
                    </dd>
                    <#include "holidayMarkCoupon.ftl">
                    <dd class="h-lv-price">
                        <dfn> ¥ <i>${productSearchInfo.sellPriceInteger?if_exists?c}</i> </dfn>
                    </dd>
                    <dd class="h-market-price">
                        <dfn> ¥ <i>${productSearchInfo.marketPriceInteger?if_exists?c}</i> </dfn>
                    </dd>
                </dl>
            </li>
         </#list>
        </ul>
    </div>
    </#if>
</dd>