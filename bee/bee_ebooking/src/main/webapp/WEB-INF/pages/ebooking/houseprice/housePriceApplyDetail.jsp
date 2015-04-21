<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<h4>查看已提交申请</h4>
	<p class="tab_bot_p_1 tab_bot_p_2">
	<b>变价产品：</b><s:property value="ebkHousePrice.productName" />
	(<s:property value="ebkHousePrice.metaProductBranchName" />)<br />
	<b>开始日期：</b><s:date name="ebkHousePrice.startDate" format="yyyy-MM-dd"/><br />
	<b>结束日期：</b><s:date name="ebkHousePrice.endDate" format="yyyy-MM-dd"/><br />
	<b>适用星期：</b><s:property value="ebkHousePrice.applyWeekString"/><br />
	<b>定价明细：</b>
		<span class="c_orange bold"><s:property value="ebkHousePrice.settlementPriceYuan" /></span>/
		<span class="c_orange bold"><s:property value="ebkHousePrice.suggestPriceYuan" /></span>/
		<span class="c_orange bold"><s:property value="ebkHousePrice.marketPriceYuan" /></span>
		（结算价/建议售价/门市价）<br />
	<b>早餐情况：</b>
		<s:if test="ebkHousePrice.breakfastCount>0">
			<s:property value="ebkHousePrice.breakfastCount" />早/天
		</s:if>
		<s:else>
			无
		</s:else>
 		<br />
	</p>
	<p class="tab_bot_p_1">    
	<b>申请单号：</b>
		<span class="orange bold"><s:property value="ebkHousePrice.housePriceId" /></span>（EBOOKING申请单）<br>
		<b>提交信息：</b>申请单于<s:date name="ebkHousePrice.createTime" format="yyyy-MM-dd HH:mm:ss"/> 由
		<s:property value="ebkHousePrice.submitUser" /> 提交<br>
		<b>审核信息：</b>
			<s:if test="ebkHousePrice.auditStatus.cnName == '待审核'">
			<s:property value="ebkHousePrice.auditStatus.cnName"/></s:if>
			<s:else>申请单于<s:date name="ebkHousePrice.confirmTime" format="yyyy-MM-dd HH:mm:ss"/> 
			由 <s:property value="ebkHousePrice.confirmUser" />审核</s:else><br>
		<b>备注：</b> <s:property value="ebkHousePrice.memo"/>
	</p>
	<span class="close"></span>
   		 
