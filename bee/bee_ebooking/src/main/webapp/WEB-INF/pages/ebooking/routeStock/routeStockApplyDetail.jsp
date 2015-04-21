<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<h4>查看已提交申请</h4>
	<p class="tab_bot_p_1 tab_bot_p_2">
	<b>变更产品：</b><s:property value="ebkHousePrice.productName" />
	(<s:property value="ebkHousePrice.metaProductBranchName" />)<br />
	<b>增减库存数：</b><s:if test="ebkHousePrice.stockAddOrMinus!=0"><s:if test="ebkHousePrice.isAddDayStock=='false'">-</s:if></s:if>
	<s:property value="ebkHousePrice.stockAddOrMinus" /><br />
	<b>是否超卖：</b><s:if test="ebkHousePrice.isOverSale=='true'">是</s:if>
		 			<s:elseif test="ebkHousePrice.isOverSale=='false'">否</s:elseif><br />
	<b>是否关班：</b>
		 		<s:if test="ebkHousePrice.isStockZero=='true'">是</s:if>
   		   		<s:else>否</s:else><br />
	<b>开始日期：</b><s:date name="ebkHousePrice.startDate" format="yyyy-MM-dd"/><br />
	<b>结束日期：</b><s:date name="ebkHousePrice.endDate" format="yyyy-MM-dd"/><br />
	</p>
	<p class="tab_bot_p_1">    
	<b>申请单号：</b>
		<span class="orange bold"><s:property value="ebkHousePrice.housePriceId" /></span>（EBOOKING修改库存申请单）<br>
		<b>提交信息：</b>申请单于<s:date name="ebkHousePrice.createTime" format="yyyy-MM-dd HH:mm:ss"/> 由
		<s:property value="ebkHousePrice.submitUser" /> 提交<br>
		<b>审核信息：</b>
			<s:if test="ebkHousePrice.auditStatus.cnName == '待审核'">
			<s:property value="ebkHousePrice.auditStatus.cnName"/></s:if>
			<s:else>申请单于<s:date name="ebkHousePrice.confirmTime" format="yyyy-MM-dd HH:mm:ss"/> 
			由 <s:property value="ebkHousePrice.confirmUser" />审核</s:else><br>
	</p>
	<span class="close"></span>
   		 
