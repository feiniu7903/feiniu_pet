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
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_role_binding_user.js"></script>
</head>
<body>
	<fieldset>
		<form id="bindingUserForm" action="to_binding_user.do?roleId=<s:property value="roleId" />" method="post">
			<legend>绑定的用户</legend>
			<ul class="gl_top">
				<li>部门名称：<input type="text" name="departmentName" value="<s:property value="departmentName" />"></li>
			    <li>用户名称：<input type="text" name="userName" value="<s:property value="userName" />"></li>
			    <li>真实姓名：<input type="text" name="realName" value="<s:property value="realName" />"><input value="查询" type="submit"></li>
			</ul>
		</form>
			<table class="gl_table" cellspacing="0" cellpadding="0">
			  <tr>
			  	<th><input type="checkbox" onClick="checkAll(this, 'ckb_bindingUser')" /></th>
			    <th>编号</th>
			    <th>角色</th>
			    <th>用户名</th>
			    <th>真实姓名</th>
			    <th>部门</th>
			  </tr>
			  <s:iterator value="permUserRolePage.items" var="item">
					<tr>
						<td><input type="checkbox" name="ckb_bindingUser" value="<s:property value="urId" />" /></td>
						<td><s:property value="urId" /></td>
						<td><s:property value="roleName" /></td>
						<td><s:property value="userName" /></td>
						<td><s:property value="realName" /></td>
						<td><s:property value="departmentName" /></td>
					</tr>
			  </s:iterator>
			  <tr>
				<td>总条数：<s:property value="permUserRolePage.totalResultSize" /></td>
				<td colspan="7" align="right">
						<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permUserRolePage)"/>
				</td>
			  </tr>
			  <tr>
			  	<td colspan="6">
			  		<mis:checkPerm permCode="1111">
			  		<input value="删除绑定" type="button" class="u10" id="u10" onClick="deleteUserHandler('<s:property value="roleId" />')" />
			  		</mis:checkPerm>
			  	</td>
			  </tr>
			</table>
		</fieldset>
		<br>
		<fieldset>
		<form action="to_binding_user.do?roleId=<s:property value="roleId" />" method="post">
			<legend>部门用户</legend>
			<ul class="gl_top">
			    <li>用户名称：<input type="text" name="dep_userName" value="<s:property value="dep_userName" />"></li>
			    <li>真实姓名：<input type="text" name="dep_realName" value="<s:property value="dep_realName" />"></li>
				<li>部门名称：<input type="text" name="dep_departmentName" value="<s:property value="dep_departmentName" />"></li>
				<li>职位：
					<input type="text" name="dep_position" value="<s:property value="dep_position" />">
					<input value="查询" type="submit">
					<mis:checkPerm permCode="1112">
					<input value="新增用户绑定" type="button" class="u10" id="u10" onClick="addUserHandler('<s:property value="roleId" />')">
					</mis:checkPerm>
				</li>
			</ul>
		</form>
			<table class="gl_table" cellspacing="0" cellpadding="0">
			  <tr>
			  	<th><input type="checkbox" onClick="checkAll(this, 'ckb_departmentUser')" /></th>
			    <th>用户编号</th>
			    <th>用户名</th>
			    <th>真实姓名</th>
			    <th>职位</th>
			    <th>部门</th>
			  </tr>
			  <s:iterator value="permUserPage.items" var="item">
					<tr>
						<td><input type="checkbox" name="ckb_departmentUser" value="<s:property value='userId' />" /></td>
						<td><s:property value="userId" /></td>
						<td><s:property value="userName" /></td>
						<td><s:property value="realName" /></td>
						<td><s:property value="position" /></td>
						<td><s:property value="departmentName" /></td>
					</tr>
			  </s:iterator>
			  <tr>
				<td>总条数：<s:property value="permUserPage.totalResultSize" /></td>
				<td colspan="7" align="right">
					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permUserPage)"/>
				</td>
			  </tr>
			</table>
		</fieldset>
</body>
</html>