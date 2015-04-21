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
	<title>新增角色</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_role_add.js"></script>
</head>
<body>
	<form id="roleForm" action="save.do" method="post">
		<ul class="gl3_top">
			<li>职位角色：<input type="text" name="permRole.roleName" ></li>
		    <li>所属组织：
		    	<select id="levelSlct" onchange="levelChangeHandler()">
					<option value="1">一级</option>
					<option value="2">二级</option>
					<option value="3">三级</option>
					<option value="4">四级</option>
				</select>
				<select id="orgSlct" name="permRole.roleLabel" selected="${permRole.roleLabel }">
				</select>
			</li>
		    <li style="width:600px; align:left;">角色描述：<textarea cols="70" rows="3" name="permRole.description"></textarea></li>
		</ul>
		<div class="gl3_zs_b">
			<table width="100%">
				<tr>
					<td align="center">
						<input value="新增" type="submit">
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>