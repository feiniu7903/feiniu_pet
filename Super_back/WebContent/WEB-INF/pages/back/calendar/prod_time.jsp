<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <input type="hidden" id="currPageDate" value="<s:date name="currPageDate" format="yyyy-MM-dd"/>"/>
  <p class="textTime textTimeTop">
  <em class="button button2 timePage nextMonth" tt="DOWN" current="<s:date name="currPageDate" format="yyyy-MM-dd"/>">下个月 </em>
  <em class="button button2 timePage prevMonth" tt="UP" current="<s:date name="currPageDate" format="yyyy-MM-dd"/>">上个月</em>
      	
<s:date name="currPageDate" format="yyyy年MM月"/>
<table width="950" border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;" class="newTable">
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
	        	 <td date="<s:date name="#timePrice.specDate" format="yyyy-MM-dd"/>" aheadTime="<s:property value="#timePrice.aheadHour" />" cancelTime="<s:property value="#timePrice.cancelHour" />" priceF="<s:property value="#timePrice.priceF"/>" class="dateTd">
		        	<s:property value="#timePrice.dateStr"/> 
		        	<s:if test="#timePrice.metaBranchId != 0">
			        	<span class="tableText01"><s:property value="#timePrice.priceStr"/> </span>
			        	<span class="tableText02"><s:property value="#timePrice.settlementPriceStr"/></span>
			        	<span class="tableText03"><s:property value="#timePrice.marketPriceStr"/></span><br/>
			        	 <s:if test="#timePrice.cuCouponFlag > 0">
								<font style="color:red">促</font>
						</s:if>
			        	<span class="tableText04"><s:property value="#timePrice.dayStockStr"/></span>
			        	<span class="tableText05"><s:property value="#timePrice.aheadHourStr"/></span>
			        	<s:if test='#timePrice.cancelStrategy == "ABLE"'>
			        		<span class="tableText06"><s:property value="#timePrice.cancelHourStr"/></span>
			        	</s:if>
			        	<s:else>
			        		<span class="tableText06"><s:property value="#timePrice.zhCancelStrategy"/></span>
			        	</s:else>
			        	<span class="tableText02" style="display:block"><s:property value="#timePrice.priceDesc" escape="false"/></span>
						<s:if test='#timePrice.multiJourneyName!=null && #timePrice.multiJourneyName!=""'>
							<span class="tableText01"><s:property value="#timePrice.multiJourneyName"/></span>
						</s:if>
		        	</s:if>
		        	<s:elseif test="product.hasSelfPack()">
		        		<span class="tableText01"><s:property value="#timePrice.priceStr"/> </span>
		        	</s:elseif>
		        </td>
	       </s:iterator>
	      </tr>
     </s:iterator>  
</table>
 


