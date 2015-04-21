<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link href="<%=basePath%>/themes/cc.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    .yellow_bg {background-color:#FFF5BD;cursor:pointer;}
    .s2_calendar {
border-bottom:1px solid #CCCCCC;
border-bottom:1px solid #CCCCCC;
float:left;
height:380px;
padding:15px 0 0;
}
.s2_calendar table tbody th {
font-weight:100;
height:25px;
padding:3px;
text-align:center;
width:14%;
}
.s2_calendar table tbody tr td.first, .s2_calendar table tbody tr th.first {
border-left:1px solid #CCCCCC;
}
.s2_calendar table tbody tr td {
font-size:12px;
height:50px;
line-height:15px;
padding:3px;
text-align:right;
vertical-align:top;
width:14%;
}
.s2_calendar table tbody tr td, .s2_calendar table tbody tr th {
border-bottom:1px solid #CCCCCC;
border-right:1px solid #CCCCCC;
}
.s2_calendar table {
border-bottom:1px solid #CCCCCC;
border:medium none;
padding:5px;
width:570px;
}

.s2_calendar table {
border-collapse:collapse;
border-spacing:0;
}
.s2_calendar table tbody tr td em {
color:#339900;
}

.s2_calendar table thead tr th span {
display:inline-block;
font-weight:100;
}




    </style>
  </head>
  
  <body>
             
             <div id="backCalendar" class="s2_calendar">
             <s:iterator value="calendarList" status="callist">
             <table  id="calendar_table_${year}_${month}" <s:if test="#callist.index!=0">style="display:none;" </s:if>>
             <thead><tr><th style="cursor: pointer;font-weight: bold;">
             <span class="turn_left_c" value="20110" id="turn_left" hide="calendar_table_${year}_${month}" show="calendar_table_<s:if test="(month-1)==0">${year-1}</s:if><s:else>${year}</s:else>_<s:if test="(month-1)==0">12</s:if><s:else>${month-1}</s:else>"  onClick="showOrHide(event,'calendar_table_${year}_${month}','calendar_table_<s:if test="(month-1)==0">${year-1}</s:if><s:else>${year}</s:else>_<s:if test="(month-1)==0">12</s:if><s:else>${month-1}</s:else>');"><<${preMonth}月
        	</span>
             </th>
        	<th colspan="5" align="center">
        	${year}年${month}月价格日历<font>(点击日期预订)</font>
        	</th>
        	<th style="cursor: pointer;font-weight: bold;">
        	<span hide="calendar_table_${year}_${month}"  show="calendar_table_<s:if test="month==12">${year+1}</s:if><s:else>${year}</s:else>_<s:if test="month==12">1</s:if><s:else>${month+1}</s:else>" onclick="showOrHide(event,'calendar_table_${year}_${month}','calendar_table_<s:if test="month==12">${year+1}</s:if><s:else>${year}</s:else>_<s:if test="month==12">1</s:if><s:else>${month+1}</s:else>');" id="turn_right_c" class="turn_right_c">${nextMonth}月>></span>
        	</th></tr>
        	</thead>
             <tbody>
               <tr>
                <td style="border-left:1px solid #CCCCCC;border-top:1px solid #CCCCCC; height: 25px;font-weight: bold;">日</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">一</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">二</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">三</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">四</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">五</td>
                <td style="border-top:1px solid #CCCCCC;height: 25px;font-weight: bold;">六</td>
               </tr>
               </tbody>
               <tbody>
                  <s:iterator value="calendar" status="cal" var="ca1">
               <tr  >
             
               <s:iterator value="#ca1" status="cal2" var="ca2">
     	<td style="<s:if test="#cal2.index==0">border-left:1px solid #CCCCCC;</s:if>" price="<s:property value="#ca2.priceF"/>"  
     		<s:if test="#ca2.isSellable(0) || #ca2.onlyForLeave==true"> id="hasTimePrice" </s:if> date="<s:date name="#ca2.specDate" format="yyyy-MM-dd"/>" stock="<s:property value="#ca2.dayStock"/>">
          <s:date name="#ca2.specDate" format="MM-dd"/> 
 	    <br/>
	 	   <s:if test="#ca2.dayStock==-1 || #ca2.dayStock==0 || #ca2.dayStock>0 || #ca2.onlyForLeave==true || #ca2.overSale=='true' ">
					    <em> <s:property value="#ca2.priceF"/></em>
				   		<s:if test="#ca2.dayStock==-1">
				   		<br /><font color="red">不限</font>
				   		</s:if>
				   		<s:elseif test="#ca2.dayStock>-1&&#ca2.dayStock!=0">
				   		<br /><font color="red"><s:property value="#ca2.dayStock"/></font>
				   		</s:elseif >
				   		<s:elseif test="#ca2.overSale=='true'" >
				   			<br /><font color="red">可超售</font>
				   		</s:elseif>
				   		<s:elseif test="#ca2.onlyForLeave==true" >
				   			<br /><font color="red">仅能离店</font>
				   		</s:elseif>
				   		<s:elseif test="!#ca2.isSellable(0)" >
				   			<br /><font color="red">售完</font>
				   		</s:elseif>
	   		</s:if>
	      </td>
    </s:iterator>
               </tr>
          
                  </s:iterator>
               			  </tbody>			  			  
             </table>
             </s:iterator>
             </div>
             
             
             
             
             
             
  </body>
</html>
