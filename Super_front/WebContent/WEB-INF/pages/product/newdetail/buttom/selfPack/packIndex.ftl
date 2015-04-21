<#import "/WEB-INF/pages/product/newdetail/buttom/selfPack/packFunc.ftl" as func/>
<#macro showProductName prodJourneyGroupMap prodJourneyId>
	<#if prodJourneyGroupMap!=null >
	<#assign x = 0 />
    	<#list prodJourneyGroupMap?keys as itemKey>
    		<#list prodJourneyGroupMap[itemKey] as branchItem>
    		<#assign x = x+1 />
    		<#if (x>1)>、</#if>
    		<#if branchItem.prodBranch.prodProduct.productType=='HOTEL'>
    			<b productid="${branchItem.prodBranch.prodProduct.productId}" prodjourneyid="${prodJourneyId}" tt="hotel" style="cursor: pointer;color: #0077CC;" class="jiudianText_AA">${branchItem.prodBranch.prodProduct.productName}</b>
    			
    		</#if>
    		<#if branchItem.prodBranch.prodProduct.productType=='TICKET'>
    			<b productid="${branchItem.prodBranch.prodProduct.productId}" prodjourneyid="${prodJourneyId}" tt="TICKET" style="cursor: pointer;color: #0077CC;" class="ticketText_AA">${branchItem.prodBranch.prodProduct.productName}</b>
    			
    		</#if>
    		<#if branchItem.prodBranch.prodProduct.productType!='TICKET' && branchItem.prodBranch.prodProduct.productType!='HOTEL'>
    			${branchItem.prodBranch.prodProduct.productName}
    		</#if>
    		</#list>
    	</#list>
    </#if>
</#macro>
<table class="taocanTab" id="taocanTab">
	<@s.if test="prodProdutJourneyPackList!=null">
		<tr>
        <th width="120">套餐推荐</th>
        <th>包含项目</th>
        <th width="160">预订</th>
    </tr>
	</@s.if>
    <@s.iterator value="prodProdutJourneyPackList" var="ppk"> 
	<#if ppk.onLine = "true">
	<tr>
        <td><b>${ppk.packName}</b></td>
        <td>
        	<#list ppk.prodProductJourneys as ppj> 
            	<p><span>第${ppj_index+1}天 </span>
            	<@showProductName ppj.prodJourneyGroupMap ppj.prodJourenyId/>
        	</#list>
        </td>
        <#if ppk.valid=='false' && ppk.onLine=='true' >
        	<td><a class="tc_btnNO tc_btn_js" packId="${ppk.prodJourneyPackId}" needRef="true" href="javascript:void(0)">已售完</a></td>
        </#if>
        <#if ppk.valid=='true' && ppk.onLine=='true' >
        	<td><a class="tc_btn tc_btn_js" packId="${ppk.prodJourneyPackId}" onclick="submitOrder(this)"
        	content="${packContent.get('${ppk.prodJourneyPackId}')}" needRef="true" href="javascript:void(0)">购买套餐</a><samp><i>¥${packPrice.get('${ppk.prodJourneyPackId}')}</i></samp></td>
        </#if>
    </tr>
    <tr class="taocanTime" style="display:none;">
        <td colspan="3">
             <div class="taocanTimeBox">
             <div class="time-price-two-${ppk.prodJourneyPackId}" style="float: left;width: 554px;height: 627px;overflow: hidden;" data-pid="<@s.property value="prodBranch.productId" escape="false"/>" data-bid="<@s.property value="prodBranch.prodBranchId" escape="false"/>"></div>
             <a class="tc_btn2 tc_btn2_js" href="javascript:void(0)">↑收起</a>
            </div>
        </td>
    </tr>
	<span>
	</#if>    		   
	</@s.iterator>
</table>
<div>
<@func.displayDiv prodProdutJourneyPackList/>
</div>