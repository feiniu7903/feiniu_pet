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
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_role_edit.js"></script>
</head>
<body onload="description.innerHTML='${permRole.description }'">
	<form id="roleForm" action="save.do" method="post">
		<ul class="gl3_top">
			<li>
				<input type="hidden" name="permRole.roleId" value="${permRole.roleId }" />
				职位角色：<input type="text" name="permRole.roleName" value="${permRole.roleName }" />
			</li>
		    <li>所属组织：
		    	<select id="levelSlct" onchange="levelChangeHandler()">
					<option value="1" <s:if test="#request.org.permLevel == 1">selected="selected"</s:if>>一级</option>
					<option value="2" <s:if test="#request.org.permLevel == 2">selected="selected"</s:if>>二级</option>
					<option value="3" <s:if test="#request.org.permLevel == 3">selected="selected"</s:if>>三级</option>
					<option value="4" <s:if test="#request.org.permLevel == 4">selected="selected"</s:if>>四级</option>
				</select>
				<select id="orgSlct" name="permRole.roleLabel">
					<s:iterator value="#request.orgList" var="item">
						<option value="${item.orgId}"
							<s:if test="#item.orgId == #request.org.orgId">selected="selected"</s:if>>
							${item.zhDepartmentName }
						</option>
					</s:iterator>
				</select>
			</li>
		    <li style="width:600px; align:left;">
		    	角色描述：<textarea cols="70" rows="3" name="permRole.description" id="description"></textarea>
		    </li>
		</ul>
		<div class="gl3_zs_b">
			<table width="100%">
				<tr>
					<td align="center">
						<input value="修改" type="submit">
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>