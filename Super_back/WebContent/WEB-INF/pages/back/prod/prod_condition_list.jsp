<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table width="100%">
	<tr>
		<td width="5%">序号</td><td width="20%">时间</td><td>内容</td><td width="10%">前台显示</td><td width="10%">类型</td><td width="10%">操作</td>
	</tr>
	<s:iterator value="conditionList" var="condition">
	<tr id="tr_condition_${condition.conditionId}">
		<td>${condition.conditionId}</td>
		<td>${condition.timeDescription}</td>
		<td>${condition.content}</td>
		<td><s:if test='#condition.frontend=="true"'>显示</s:if></td>
		<td id="condition.conditionType">${condition.zhConditionType}</td>
		<td><a href="#delete" class="deleteCondition" result="${condition.conditionId}">删除</a><!-- <a href="#edit" class="editCondition">编辑</a> --></td>
	</tr>
	</s:iterator>
</table>