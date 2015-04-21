<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	<input type="hidden" id="currPageDate" value="<s:date name="currPageDate" format="yyyy-MM-dd"/>" />
	<em class="button button2 timePage nextMonth" tt="DOWN" current="<s:date name="currPageDate" format="yyyy-MM-dd"/>">下个月 </em>
    <em class="button button2 timePage prevMonth" tt="UP" current="<s:date name="currPageDate" format="yyyy-MM-dd"/>">上个月 </em> 
   
  	<s:date name="currPageDate" format="yyyy年MM月"/>  
    <table width="96%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;" class="newTable">
      <tr class="newTableTit">
        <td>星期日</td>
        <td>星期一</td>
        <td>星期二</td>
        <td>星期三</td>
        <td>星期四</td>
        <td>星期五</td>
        <td>星期六</td>
      </tr>
      <s:iterator value="calendarModel.calendar" var="cal" status="status">
	      <tr>
	        <s:iterator value="#cal" var="timePrice">
		        <td date="<s:date name="#timePrice.specDate" format="yyyy-MM-dd"/>" class="dateTd">
		        	<s:property value="#timePrice.dateStr"/> 
		        	<s:if test="#timePrice.metaBranchId != 0">
			        	<span class="tableText01"><s:property value="#timePrice.marketPriceStr"/></span>
			        	<s:if test="metaProduct.productType=='HOTEL'"><span class="tableText06"><s:property value="#timePrice.suggestPriceStr"/></span></s:if>
			        	<span class="tableText02"><s:property value="#timePrice.settlementPriceStr"/></span>
			        	<span class="tableText03"><s:property value="#timePrice.dayStockStr"/></span><br/>
			        	<span class="tableText05"><s:property value="#timePrice.aheadHourStr"/></span><br/>
			        	<span class="tableText06"><s:property value="#timePrice.zhCancelStrategy"/></span>
			        	<s:if test='#timePrice.cancelStrategy == "ABLE"'>
			        		<span class="tableText06"><s:property value="#timePrice.cancelHourStr"/></span>
			        	</s:if>
			        	<s:if test="metaProduct.productType=='HOTEL'"><span class="tableText06">早:<s:property value="#timePrice.breakfastCount"/></span></s:if>
			        	<span class="tableText04"><s:property value="#timePrice.resourceConfirmStr"/></span>
			        	<span class="tableText05"><s:property value="#timePrice.overSaleStr "/></span>
			        	<span class="tableText03"><s:property value="#timePrice.zeroStockStr"/></span>
			        	<s:if test="metaProduct.productType == 'TICKET'">
			        	<span class="tableText01"><s:property value="#timePrice.earliestUseTimeStr"/></span>
			        	<span class="tableText02"><s:property value="#timePrice.latestUseTimeStr"/></span>
			        	</s:if>
		        	</s:if>
		        </td>
	         </s:iterator>
	      </tr>
      </s:iterator>
    </table>
    
