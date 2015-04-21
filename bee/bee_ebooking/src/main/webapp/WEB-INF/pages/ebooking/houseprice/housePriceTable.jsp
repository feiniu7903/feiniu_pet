<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<!--价格表-->
    <div class="pricelist">
    	<h4><b>${ebkHousePrice.metaProductBranchName}</b> （类别ID:<span>${ebkHousePrice.metaBranchId}</span>）<span class="f12">【RMB结算】</span></h4>
        <div class="plug_calendar_box">
    	 <div class="plug_calendar_main"> 	
         <h2 class="plug_calendar_tit">${calendarModel.year}年${calendarModel.month + 1}月</h2>
         <div class="plug_calendar_table">
         
      <div width="96%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;" class="newTable">
      <ul class="plug_calendar_t">
             <li>日</li>
             <li>一</li>
             <li>二</li>
             <li>三</li>
             <li>四</li>
             <li>五</li>
             <li>六</li>
         </ul>
      <s:iterator value="calendarModel.calendar" var="cal" status="status">
	       <ul class="plug_calendar_d">
	        <s:iterator value="#cal" var="timePrice">
		        	<li date="<s:date name="#timePrice.specDate" format="yyyy-MM-dd"/>" class="plug_calendar_current">
		        	<div class="plug_calendar_d_box_no_hover month_1"> 
		        	<span class="plug_calendar_day"><s:property value="#timePrice.dateStr"/></span>
		        	<s:if test="#timePrice.metaBranchId != 0">
			        	<p class="plug_calendar_price">
			        	<i class="plug_calendar_price1"><s:if test="#timePrice.marketPrice != null"><s:property value="#timePrice.marketPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price2"><s:if test="#timePrice.suggestPrice != null"><s:property value="#timePrice.suggestPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price3"><s:if test="#timePrice.settlementPrice != null"><s:property value="#timePrice.settlementPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price4"><s:if test="#timePrice.breakfastCount != null"><s:property value="#timePrice.breakfastCount"/></s:if><s:else>无</s:else></i>
			        	</p>
		        	</s:if>
		        	</div>
		        	</li>
	         </s:iterator>
	     </ul>
      </s:iterator>
    </div>
    </div>
    </div>
    <div class="plug_calendar_main"> 	
         <h2 class="plug_calendar_tit">${nextCalendarModel.year}年${nextCalendarModel.month + 1}月</h2>
         <div class="plug_calendar_table">
         
      <div width="96%" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;" class="newTable">
      <ul class="plug_calendar_t">
             <li>日</li>
             <li>一</li>
             <li>二</li>
             <li>三</li>
             <li>四</li>
             <li>五</li>
             <li>六</li>
         </ul>
      <s:iterator value="nextCalendarModel.calendar" var="cal" status="status">
	       <ul class="plug_calendar_d">
	        <s:iterator value="#cal" var="timePrice">
		        	<li date="<s:date name="#timePrice.specDate" format="yyyy-MM-dd"/>" class="plug_calendar_current">
		        	<div class="plug_calendar_d_box_no_hover month_1"> 
		        	<span class="plug_calendar_day"><s:property value="#timePrice.dateStr"/></span>
		        	<s:if test="#timePrice.metaBranchId != 0">
			        	<p class="plug_calendar_price">
			        	<i class="plug_calendar_price1"><s:if test="#timePrice.marketPrice != null"><s:property value="#timePrice.getMarketPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price2"><s:if test="#timePrice.suggestPrice != null"><s:property value="#timePrice.suggestPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price3"><s:if test="#timePrice.settlementPrice != null"><s:property value="#timePrice.settlementPriceF"/></s:if><s:else>无</s:else></i>
			        	<i class="plug_calendar_price4"><s:if test="#timePrice.breakfastCount != null"><s:property value="#timePrice.breakfastCount"/></s:if><s:else>无</s:else></i>
			        	</p>
		        	</s:if>
		        	</div>
		        	</li>
	         </s:iterator>
	     </ul>
      </s:iterator>
    </div>
    </div>
    </div>
</div>
</div>
  
</html>
