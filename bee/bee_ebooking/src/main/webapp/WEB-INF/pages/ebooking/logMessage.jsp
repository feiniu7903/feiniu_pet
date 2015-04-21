<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="760" cellspacing="0" cellpadding="0" border="0"
	class="eject_rz_table">
	<tbody>
		<tr class="eject_rz_table_tr">
			<th width="15%" style="text-align: center;">创建时间</th>
			<th width="10%" style="text-align: center;">操作人</th>
			<th width="15%" style="text-align: center;">操作主题</th>
			<th width="60%" style="text-align: center;">操作内容</th>
		</tr>
		<s:iterator value="logList" var="log">
			<tr>
				<td><s:date name="createTime" format="yyyy/MM/dd HH:mm" /></td>
				<td><s:property value="operatorName" /></td>
				<td><s:property value="logName" /></td>
				<td><p>
						<s:property value="content" />
					</p></td>
			</tr>
		</s:iterator>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="4">
				 <jsp:include page="./product/subPage/queryPageFooter.jsp"></jsp:include>
			</td>
		</tr>
	</tfoot>
</table>