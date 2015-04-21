<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table width="100%">
	<tr>
		<td style="width:20%">限制销售开始时间</td>
		<td style="width:20%">限售开始时间点</td>
		<td style="width:20%">限售结束时间点</td>
		<td style="width:20%">限制销售游玩时间</td>
		<td>操作</td>
	</tr>
	<s:iterator value="limitSaleTimeList" var="ls">
	<tr id="tr_limitSale_${ls.limitSaleTimeId}">
		<td><s:date name="#ls.limitSaleTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${ls.limitHourStart}</td>
		<td>${ls.limitHourEnd}</td>
		<td><s:date name="#ls.limitVisitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td><a href="#delete" class="deleteLimitSale" result="${ls.limitSaleTimeId}">删除</a></td>
	</tr>
	</s:iterator>
</table>