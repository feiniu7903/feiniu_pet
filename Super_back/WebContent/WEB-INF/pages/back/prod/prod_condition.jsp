<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
<!--
	$(function(){
		$("input.date").datetimepicker({
			showTimepicker:false,
			showButtonPanel:false
		});
	})
//-->
</script><input type="text" name="sss" style="border:none"/>
<form action="<%=request.getContextPath()%>/prod/saveCondition.do">
<input type="hidden" name="condition.conditionId"/>
<input type="hidden" name="condition.objectId" value="${objectId}"/><input type="hidden" name="condition.objectType" value="${objectType}"/>
<table width="700">
	<tr>
		<td>有效时间：<input type="text" class="date" name="condition.beginTime" readonly="readonly"/>至<input type="text" name="condition.endTime" class="date" readonly="readonly"/>提示类型:<s:select name="condition.conditionType" list="conditionTypeList" listKey="code" listValue="name"/></td><td>前台可见：<s:checkbox fieldValue="true" name="condition.frontend"/></td>
	</tr>
	<tr>
		<td><textarea name="condition.content" style="width:90%"></textarea></td><td><input type="button" class="saveCondition" style="float:left" value="保存"/></td>
	</tr>
</table>
</form>
<div id="conditionListDiv" style="min-height: 100px">
<s:include value="/WEB-INF/pages/back/prod/prod_condition_list.jsp"/>
</div>
<div>
来自采购产品的信息提示
<table width="600">
	<tr>
		<td width="5%">序号</td>
		<td width="20%">时间</td>
		<td>预订限制内容</td>
		<td width="10%">类型</td>
	</tr>
	<s:iterator value="metaConditionList" var="mc">
	<tr>
		<td>${mc.conditionId}</td>
		<td>${mc.timeDescription}</td>
		<td>${mc.content}</td>
		<td>${mc.zhConditionType}</td>
	</tr>
	</s:iterator>
</table>
</div>