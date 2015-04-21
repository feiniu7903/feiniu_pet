<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="tan_xzkc">
	<form action="${basePath }product/branch/updateOneDayProdTimePriceStock.do" method="POST" id="ebkProdTimePriceForm">
	<s:hidden name="ebkProdTimePrice.timePriceId" />
	<input type="hidden" name="ebkProdTimePrice.specDate" value="<s:date name="ebkProdTimePrice.specDate" format="yyyy-MM-dd" />"/> 
	<s:hidden name="ebkProdTimePrice.productId" />
	<s:hidden name="ebkProdTimePrice.prodBranchId" />
	<s:hidden name="ebkProdTimePrice.operateStatus"/>
	<s:hidden name="ebkProdTimePrice.branchType"/>
	<table class="xzkc_1">
    	<tbody>
    	<tr>
			<td align="right"><font color="red">*</font>是否<s:if test='"HOTEL"==ebkProductViewType'>满房：</s:if><s:else>关班：</s:else></td>
			<td><s:radio name="ebkProdTimePrice.forbiddenSell" list="#{'true':'是','false':'否'}" theme="simple"/></td>
		</tr>
    	<s:if test="'HOTEL'==ebkProductViewType">
    		<tr>
    			<td align="right"><font color="red">*</font>早餐份数：</td>
    			<td>　<s:select list="{0,1,2,3,4,5,6,7,8,9,10}" name="ebkProdTimePrice.breackfastCount" theme="simple" cssStyle="width:120px;"/></td>
    		</tr>
    	</s:if>
    	<tr>
        	<td align="right"><font color="red">*</font>提前预订小时数：</td>
            <td><s:textfield name="aheadHourDay" cssClass="width_58" />天<s:textfield name="aheadHour" cssClass="width_58" />小时<s:textfield name="aheadHourSecend" cssClass="width_58" />分</td>
        </tr>
        <tr>
        	<td align="right"><font color="red">*</font>退改策略：</td>
            <td><s:radio name="ebkProdTimePrice.cancelStrategy" list="#{'FORBID':'不退不改','MANUAL':'人工确认'}" theme="simple"/>
                <span class="red_ff4444">&#12288;&#12288;(必须与退变更说明一致)</span>
            </td>
        </tr>
    	<s:if test="'VIRTUAL'!=ebkProdTimePrice.branchType">
	        <tr>
	        	<td align="right"><font color="red">*</font>结算价(￥)：</td>
	        	<td><s:textfield name="ebkProdTimePrice.settlementPrice" cssClass="width_58" /></td>
	        </tr>
	        <tr>
	        	<td align="right"><font color="red">*</font>门市价(￥)：</td>
	        	<td><s:textfield name="ebkProdTimePrice.marketPrice" cssClass="width_58"/></td>
	        </tr>
	        <tr>
	        	<td align="right"><font color="red">*</font>销售价(￥)：</td>
	        	<td><s:textfield name="ebkProdTimePrice.price" cssClass="width_58"/></td>
	        </tr> 
	    </s:if>          
        <tr>
        	<td align="right"><font color="red">*</font>日库存量：</td>
        	<td><s:select list="stockTypes" name="ebkProdTimePrice.stockType" value="ebkProdTimePrice.stockType" listKey="code" listValue="cnName" listTitle="shortName" cssClass="oneDayProdTimePriceStockType"/><s:textfield name="ebkProdTimePrice.dayStock" cssClass="width_58" disabled="'UNLIMITED_STOCK'==ebkProdTimePrice.stockType"/></td>
        </tr>   
        <tr>
        	<td align="right"><font color="red">*</font>资源审核：</td>
        	<td><s:radio name="ebkProdTimePrice.resourceConfirm" list="#{'true':'是','false':'否'}" theme="simple"/></td>
        </tr>
        <tr>
        	<td align="right"><font color="red">*</font>超卖：</td>
        	<td><s:radio name="ebkProdTimePrice.overSale" list="#{'true':'是','false':'否'}" theme="simple"/></td>
        </tr>
    </tbody></table>
    </form>
    <span class="tan_fp_btn fp_btn updateOneDayProdTimePriceStock">保存</span>
    <span><font color="red" id="validateErrorMesssage"></font></span>
</div>