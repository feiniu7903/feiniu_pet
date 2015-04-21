<!-- 优惠信息 -->
<#if couponEnabled=='Y'>
<div class="hr_a"></div>
<div class="order-title">
    <h3>优惠信息</h3>
</div>
<div class="order-content xdl-hor form-small">
    <input type="hidden" id="youhui_couponId" name="buyInfo.couponList[0].couponId"/>
    <input type="hidden" id="couponChecked" name="buyInfo.couponList[0].checked"/>
    <input type="hidden" id="couponCode" name="buyInfo.couponList[0].code"/>
    <input type="hidden" id="cashValue" name="buyInfo.cashValue"/>
    <input type="hidden" id="couponPayment" name="buyInfo.couponList[0].paymentChannel"/>
    <dl class="xdl">
        <dt class="B">可享优惠：</dt>
        <dd class="mb10">
            <div id="couponActivity" class="selectbox selectbox-big">
                <p class="select-info like-input">
                    <span class="select-arrow"><i class="ui-arrow-bottom dark-ui-arrow-bottom"></i></span>
                    <span class="select-value">不使用任何优惠</span>
                </p>

                <div class="selectbox-drop">
                    <ul class="select-results">
                        <li data-value="">不使用任何优惠</li>
<#if mainProdBranch.prodProduct.couponActivity=='true'||mainProdBranch.prodProduct.couponActivity==null||mainProdBranch.prodProduct.couponActivity=='' || mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble==''>
                        <#if mainProdBranch.prodProduct.couponActivity=='true'||mainProdBranch.prodProduct.couponActivity==null||mainProdBranch.prodProduct.couponActivity==''>
                            <@s.iterator value="orderCouponList" status="st" var="orderCoupon">
                                <#if orderCoupon.couponId!=2818>
                                    <li data-value='<@s.property value="couponId"/>' payment='<@s.property value="paymentChannel"/>'><@s.property value="couponName"/></li>
                                </#if>
                            </@s.iterator>
                        </#if>
                        <#if mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble==''>
                            <li data-value="userCoupon">使用优惠券</li>
                        </#if>
</#if>
                        <#if mainProdBranch.prodProduct.payToLvmama == 'true'>
                            <li data-value="userCash">奖金全额抵用（可抵用<span id="availableCash">0</span>元）</li>
                        </#if>
                    </ul>
                </div>
            </div>
        </dd>
        <!-- <dd><div class="text-gray">
        	<#if mainProdBranch.prodProduct.couponActivity=='true'||mainProdBranch.prodProduct.couponActivity==null||mainProdBranch.prodProduct.couponActivity==''>
				<@s.iterator value="orderCouponList" status="st" var="orderCoupon"> 
					 <#if orderCoupon.couponId!=2818 && orderCoupon.description != null && orderCoupon.description?trim != "">
			             ●${orderCoupon.description}<br/>
			         </#if>
			    </@s.iterator>
	         </#if>
	         </div>
        </dd> -->
        <dd>
            <input type="hidden" id="couponCodeUseStatus" value="unuse"/>

            <div class="tiptext tip-default" id="myCoupon" style="display:none">
                <#if userCouponList?? && userCouponList.size() gt 0>
                    <h5>我的可用优惠券： </h5>
                    <#if userCouponList?? && userCouponList.size() gt 0 >
                        <#list userCouponList as userCoupon>
                            <div class="check-text">
                                <label class="radio inline">
                                    <input class="input-radio" type="radio" name="couponCode" value='${userCoupon.markCoupon.couponId}' couponCode='${userCoupon.markCouponCode.couponCode}' onclick="userCouponCode(this);"/>
                                ${userCoupon.markCouponCode.couponCode}&nbsp;&nbsp;${userCoupon.markCoupon.couponName}
                                </label>
                            </div>
                        </#list>
                    </#if>
                    <div class="dot_line"></div>
                <#else>
                    <div class="hr_a"></div>
                </#if>
                <#if mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble==''>
                    <div class="check-text">
                        <label class="radio inline">
                            <input class="input-radio" type="radio" id="couponCodeId" name="couponCode" onclick="document.getElementById('couponCode_input').focus();userCouponCode('couponCode_radio');"/>输入优惠券代码：
                        </label>
                        <input type="text" id="couponCode_input" class="input-text"/>
                        <button class="pbtn pbtn-mini pbtn-blue" id="confirmSumbitCoupon" onclick="javascript:document.getElementById('couponCodeId').checked='true';userCouponCode('couponCode_input');return false;">确定</button>
                        <#if user != null && (mainProdBranch.prodProduct.couponAble=='true'||mainProdBranch.prodProduct.couponAble==null||mainProdBranch.prodProduct.couponAble=='') && markCouponPointChange != null && currentUserPoint &gt;= markCouponPointChange.point >
                            <input type="checkbox" onclick="showCouponByPoint('${markCouponPointChange.subProductType}',${markCouponPointChange.couponId},${markCouponPointChange.point},this);"/>积分兑换优惠券&nbsp;
                            <div id="pointChangeCouponDiv" class="hide">
                            ${markCouponPointChange.point}分兑换优惠券: ${markCouponPointChange.couponName}，您现在有积分<span id="currentUserPoint">${currentUserPoint}</span>分，是否兑换？
                            </div>
                        </#if>
                    </div>
                </#if>
                <div class="hr_a"></div>
            </div>
            <div style="display: none;" id="myCash" class="tiptext tip-default">
                <label class="radio inline">
                    我要抵现
                    <input type="text" class="input-text" id="cashValue_input">
                    元<i style="color:#ccc;margin-left:10px">可输入1~<span id="availableCashShow">0</span>的整数</i>
                </label><br/>
                <button class="pbtn pbtn-mini pbtn-blue" id="confirmSumbitCash" onclick="userCash();return false;" style="margin-top:10px;">确定</button>
            </div>
        </dd>
        <dd>
            <p class="text-gray" id="couponInfo_youhui"></p>
        </dd>
    </dl>
</div>
</#if>
<!-- //优惠信息 -->