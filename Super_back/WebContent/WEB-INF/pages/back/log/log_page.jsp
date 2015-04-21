<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<table width="800">
	<tr>
		<td width="15%">创建时间</td>
		<td width="15%">操作人</td>
		<td>操作内容</td>
	</tr>
	<s:iterator value="comLogList" var="log">
	<tr>
		<td><s:date name="#log.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		<td><span style="font-weight:bold">${log.operatorName}</span>-<span>${log.logName}</span></td>
		<td>${log.content}</td>
	</tr>
	</s:iterator>
</table><form action="${pagination.actionUrl}" onsubmit="return false">
<table width="600" class="pagination">
	<tr>
		<td width="120">共${pagination.totalRecords}条,第${pagination.page}页/共${pagination.totalPages}页</td>
		<td width="80">
			<s:if test="pagination.page>1">
				<a href="#log" page="${pagination.page-1}" class="page">上一页</a>
			</s:if>
			<s:else>
				上一页
			</s:else>
		</td>
		<td width="80">
			<s:if test="pagination.totalPages>pagination.page">
			<a href="#log" page="${pagination.page+1}" class="page">下一页</a>
			</s:if>
			<s:else>
				下一页
			</s:else>
		</td>
		<td>
			<input type="text" name="page" style="width:20px;" totalPage="${pagination.totalPages}"/>&nbsp;<a href="#log" tt="custom" totalPage="${pagination.totalPages}" class="page">GO</a>
		</td>
	</tr>
</table></form>