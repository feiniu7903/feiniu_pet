<input type="hidden" id="subProductType" value="${prodCProduct.prodProduct.subProductType}"/>
<!--预订浮层-->
<div class="free_yd" id="free_yd">
<div id="quickBooker1_tab2">
	<label class="free_yd_label">出发日期：</label>
	<select name="buyInfo.visitTime" onChange="" id="quickBooker_select_3" class="quickBooker_select"></select>
	<b class="free_people">人数</b>
	<@s.if test="prodBranch!=null">
	<span>
（<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(prodBranch.branchName,8)" /><@s.if test="prodBranch.description!=null && prodBranch.description!=''"><img width="13" height="13" title="${prodBranch.description}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>）
	<input type="button" value="-" class="free_mins" onclick="updateOperator('${prodBranch.prodBranchId}','miuns');this.blur()">				
	<input seq="1" name="buyInfo.buyNum.product_${prodBranch.prodBranchId}" 
                id="param${prodBranch.prodBranchId}" type="text" size="2" class="number prod-num free_input" 
                value="${prodBranch.minimum}" 
                ordNum="ordNum"
                onchange="updateOperator('${prodBranch.prodBranchId}','input')" 
                minAmt="${prodBranch.minimum}" 
                maxAmt="${prodBranch.maximum}" 
                textNum="textNum${prodBranch.prodBranchId}" 
                people="${prodBranch.adultQuantity+prodBranch.childQuantity}"
                branchId="${prodBranch.prodBranchId}"/>
					<input type="button" value="+" class="free_plus" onclick="updateOperator('${prodBranch.prodBranchId}','add');this.blur()">
</span>
</@s.if>
<@s.iterator value="prodProductBranchList" var="ppb"> 
 <#if ppb.online = "true" && ppb.defaultBranch != "true">
<span>
（<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(#ppb.branchName,8)" /><@s.if test="#ppb.description!=null && #ppb.description!=''"><img width="13" height="13" title="${ppb.description}" src="http://pic.lvmama.com/img/new_v/ob_detail/wen.gif" class="wen"></@s.if>）
	<input type="button" value="-" class="free_mins" onclick="updateOperator('${ppb.prodBranchId}','miuns');this.blur()">	
	<input seq="1" name="buyInfo.buyNum.product_${ppb.prodBranchId}" 
	                id="param${ppb.prodBranchId}" type="text" size="2" class="number prod-num free_input" 
	                value="${ppb.minimum}" 
	                ordNum="ordNum"
	                onchange="updateOperator('${ppb.prodBranchId}','input')" 
	                minAmt="${ppb.minimum}" 
	                maxAmt="${ppb.maximum}" 
	                textNum="textNum${ppb.prodBranchId}" 
	                people="${ppb.adultQuantity+ppb.childQuantity}"
	                branchId="${ppb.prodBranchId}"/>
	<input type="button" value="+" class="free_plus" onclick="updateOperator('${ppb.prodBranchId}','add');this.blur()">
</span>
	</#if>    		   
    </@s.iterator>
    <span class="coupon_price_pos" style="display:none;"><b>优惠价:</b><b class="total_discount_price">加载中</b></span>
	<span class="free_price_pos"><b>总价:</b><b class="free_price">加载中</b></span>
	<input type="button" class="free_yd_btn" onclick="checkJourneySelecteAndSubmit()" value="立即预订"/>
</div>
</div>
<div id="select-travel">
<#include "/WEB-INF/pages/product/newdetail/buttom/selfPack/optionalJoureny.ftl">
</div>


