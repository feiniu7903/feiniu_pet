<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理用户权限</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript"
	src="<%=basePath%>/js/perm/perm_user_permission_manage.js"></script>
</head>
<body>
	<input id="userIdHd" type="hidden" value="${request.userId }" />
	<div class="sy_box_t">权限和角色【用户名：${request.userName }】</div>
	<div class="sy_box_b">
		<div class="sy_bot" style="text-align:left;padding:0px 0 10px;">
			<input type="button" value="分配角色" onclick="setUsrRoleHandler(${request.userId })">
			<input type="button" value="分配权限" onclick="setUsrPermissionHandler(${request.userId })">
			<input type="button" value="禁止权限" onclick="disableUsrPermissionHandler(${request.userId })">
			<input type="button" value="返回" onclick="javascript:window.location.href='index.do';" />
		</div>
		<table cellspacing="0" cellpadding="0" class="sy_table">
			<tbody>
				<tr>
					<th>角色ID</th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>操作</th>
				</tr>
				<s:iterator value="permRolePage.items" var="item">
					<tr>
						<td><s:property value="roleId" /></td>
						<td><s:property value="roleName" /></td>
						<td><s:property value="description" /></td>
						<td class="gl_cz">
							<a href="javascript:void(0)" onclick="deleteUserRoleHandler(${item.urId},${item.roleId })">删除</a>
						</td>
					</tr>
			  </s:iterator>
			</tbody>
		</table>
		
		<h3 class="sy_title">授权权限</h3>
		<table cellspacing="0" cellpadding="0" class="sy_table">
			<tbody>
				<tr>
					<th>权限ID</th>
					<th>权限名称</th>
					<th>父级权限名称</th>
					<th>类型</th>
					<th>有效时长</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<s:iterator value="#request.permissionList" var="item">
				<tr>
					<td>${item.permissionId }</td>
					<td>${item.permissionName }</td>
					<td>${item.parentPermissionName }</td>
					<td>${item.permissionTypeName }</td>
					<td>${item.enableDays }天</td>
					<td><s:property value="createTime" /></td>
					<td class="gl_cz">
						<a href="javascript:void(0)" onclick="deletePermissionHandler(${item.upId},${item.permissionId })">删除</a>
					</td>
				</tr>
				</s:iterator>
			</tbody>
		</table>
		<h3 class="sy_title">禁止权限</h3>
		<table cellspacing="0" cellpadding="0" class="sy_table">
			<tbody>
				<tr>
					<th>权限ID</th>
					<th>权限名称</th>
					<th>父级权限名称</th>
					<th>类型</th>
					<th>操作</th>
				</tr>
				<s:iterator value="#request.disabledPermissionList" var="item">
				<tr>
					<td>${item.permissionId }</td>
					<td>${item.permissionName }</td>
					<td>${item.parentPermissionName }</td>
					<td>${item.permissionTypeName }</td>
					<td class="gl_cz"><a href="javascript:void(0)" onclick="enablePermissionHandler(${item.upId},${item.permissionId })">删除</a></td>
				</tr>
				</s:iterator>
			</tbody>
		</table>
		
	</div>
</body>
</html>