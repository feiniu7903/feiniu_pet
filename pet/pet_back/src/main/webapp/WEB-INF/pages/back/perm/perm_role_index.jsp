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
	<title>角色设置</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_role_index.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
</head>
<body>
	<ul class="gl_top">
		<form action="search.do" method="post">
			<li>角色名称：<input type="text" class="u3" name="roleName" value="<s:property value="roleName" />" id="u3"></li>
			<li>角色标签：<input type="text" class="u5" name="roleLabel" value="<s:property value="roleLabel" />" id="u5"></li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10" /> 
				<mis:checkPerm permCode="1107">
					<input type="button" value="新增" class="u10" id="u10" onClick="newRoleHandler()" /> 
				</mis:checkPerm>
			</li> 
		</form>
	</ul>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>编号</th>
	    <th>角色名</th>
	    <th>是否生效</th>
	    <th>创建时间</th>
	    <th>角色标签</th>
	    <th>描述</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="permRolePage.items" var="item">
			<tr>
				<td><s:property value="roleId" /></td>
				<td><s:property value="roleName" /></td>
				<td><s:property value="valid" /></td>
				<td><s:property value="createTime" /></td>
				<td><s:property value="roleLabel" /></td>
				<td><s:property value="description" /></td>
				<td class="gl_cz">
					<mis:checkPerm permCode="1108">
					<s:if test='ifShowEdit == "Y"'>
						<a href="javascript:void(0);" onClick='bindingPermHandler(${item.roleId})'>绑定权限</a>|
					</s:if>
					</mis:checkPerm>
					<mis:checkPerm permCode="2002">
					<a href="javascript:void(0);" onClick='bindingUserHandler(${item.roleId})'>绑定用户</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="1970">
					<s:if test='ifShowEdit == "Y"'>
						|<a href="javascript:void(0);" onClick='editRoleHandler(${item.roleId})'>修改</a>
					</s:if>
					</mis:checkPerm>
					<a href="#log" class="showLogDialog" param={"objectId":${item.roleId},"objectType":"PERM_ROLE"}>日志</a>
				</td>
			</tr>
	  </s:iterator>
	  <tr>
		<td>总条数：<s:property value="permRolePage.totalResultSize" /></td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permRolePage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>