<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>绑定用户</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_child_element.js"></script>
</head>
<body>
	<fieldset>
		<legend>系统权限</legend>
		<ul class="gl_top">
			<li>
				<mis:checkPerm permCode="1110">
				<input value="新增权限绑定" type="button" class="u10" id="u10" onClick="addPermissionHandler('<s:property value="roleId" />')">
				</mis:checkPerm>
			</li>
		</ul>
		<table class="gl_table" cellspacing="0" cellpadding="0">
		  <tr>
		  	<th><input type="checkbox" onClick="checkAll(this, 'ckb_bindingSystemPerm')" /></th>
		    <th>权限ID</th>
		    <th>类型</th>
		    <th>权限名称</th>
		    <th>父级名称</th>
		    <th>父级ID</th>
		  	<th>是否有效</th>
		    <th>类别</th>
		  </tr>
		  <s:iterator value="permPermissionPage.items" var="item">
				<tr>
					<td><input type="checkbox" name="ckb_bindingSystemPerm" value="<s:property value="permissionId" />" /></td>
					<td><s:property value="permissionId" /></td>
					<td><s:property value="type" /></td>
					<td><s:property value="name" /></td>
						<td><s:property value="parentName" /></td>
						<td><s:property value="parentId" /></td>
						<td><s:property value="valid" /></td>
						<td><s:property value="zhCategory" /></td>
					</tr>
			  </s:iterator>
			  <tr>
				<td>总条数：<s:property value="permPermissionPage.totalResultSize" /></td>
				<td colspan="9" align="right"><s:property escape="false"
						value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)" />
				</td>
			  </tr>
			</table>
		</fieldset>
</body>
</html>