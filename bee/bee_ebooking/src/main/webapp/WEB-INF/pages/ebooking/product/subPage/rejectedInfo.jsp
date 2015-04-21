<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="760" cellspacing="0" cellpadding="0" border="0"
	class="eject_rz_table">
	<thead><tr class="eject_rz_table_tr"><td>模块</td><td>审核信息</td></tr></thead>
	<tbody>
		<s:if test="rejectList!=null && !rejectList.isEmpty()">
		<s:iterator value="rejectList" var="rejected">
			<tr><td><s:property value="typeCh"/></td><td><s:property value="message"/></td></tr>
		</s:iterator>
		</s:if>
		<s:else><tr><td colspan="2">没有找到审核信息</td></tr></s:else>
	</tbody>
</table>