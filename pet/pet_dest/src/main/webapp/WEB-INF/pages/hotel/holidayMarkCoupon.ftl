<#if markCouponMap?? && markCouponMap.size() gt 0>
    <#assign maxPrice=0/>
    <#assign allPrice=''/>
    <#if markCouponMap['${productSearchInfo.productId?c}']?? && markCouponMap['${productSearchInfo.productId?c}'].size() gt 0>
        <#list markCouponMap['${productSearchInfo.productId?c}']?sort_by("argumentY") as coupon>
            <#if coupon.argumentY??>
                <#if maxPrice?number lt (coupon.argumentY/100)?number><#assign maxPrice='${coupon.argumentY/100}'/></#if>
                <#if allPrice?index_of('${coupon.argumentY/100}') == -1>
                    <#if allPrice == ''>
                        <#assign allPrice='<dfn>&yen; <i>${coupon.argumentY/100}</i></dfn>'/>
                    <#else>
                        <#assign allPrice='${allPrice},<dfn>&yen; <i>${coupon.argumentY/100}</i></dfn>'/>
                    </#if>
                </#if>
            </#if>
        </#list>
    </#if>
    <dd class="h-coupon">
    <#if allPrice != ''>
        <dfn>-&yen;<i>${maxPrice}</i></dfn>
        <span class="tags tip-icon tip-icon-info" tip-content="驴妈妈会员在该酒店享有特惠房型价格，需先<a target='_blank' href='http://www.lvmama.com/zt/promo/youhuiquan/' class='h-coupon-btn'>领取</a>优惠券(${allPrice})"></span>
    <#else>
        <dfn><i>--</i></dfn>
    </#if>
    </dd>
</#if>