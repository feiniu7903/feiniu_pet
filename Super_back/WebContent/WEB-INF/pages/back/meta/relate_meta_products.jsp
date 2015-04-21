<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>采购产品信息</title>
</head>
<body>
	<table style="width:100%" class="zhanshi_table" cellspacing="0" cellpadding="0">
		<tr>
			<th>编号</th>
			<th>采购产品名称</th>
			<th>类别名称</th>
			<th>采购经理</th>
		</tr>
		<s:iterator value="metaProductList"> 
			<tr>
				<td>${metaProductId }</td>
				<td>${productName }</td>
				<td>${branchName }</td>
				<td>${managerName }</td>
			</tr>
		</s:iterator>
	</table>
	<form action="${pagination.actionUrl}" onsubmit="return false">
	<s:hidden name="targetId" id="targetId" />
<table width="600" class="pagination">
	<tr>
		<td width="120">共${pagination.totalRecords}条,第${pagination.page}页/共${pagination.totalPages}页</td>
		<td width="80">
			<s:if test="pagination.page>1">
				<a href="#" page="${pagination.page-1}" class="_page">上一页</a>
			</s:if>
			<s:else>
				上一页
			</s:else>
		</td>
		<td width="80">
			<s:if test="pagination.totalPages>pagination.page">
			<a href="#" page="${pagination.page+1}" class="_page">下一页</a>
			</s:if>
			<s:else>
				下一页
			</s:else>
		</td>
		<td>
			<input type="text" name="page" style="width:20px;" totalPage="${pagination.totalPages}"/>&nbsp;<a href="#" tt="custom" totalPage="${pagination.totalPages}" class="_page">GO</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>