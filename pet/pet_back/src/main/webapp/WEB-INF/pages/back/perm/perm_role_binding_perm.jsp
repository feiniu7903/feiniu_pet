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
	<title>绑定的角色权限</title>
	<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
	<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
	<script type="text/javascript" src="<%=basePath%>/js/perm/perm_role_binding_perm.js"></script>
</head>
<body>
	<fieldset>
	<form id="bindingPermForm" action="to_binding_perm.do?roleId=<s:property value="roleId" />" method="post">
			<legend>绑定的角色权限</legend>
			<h3 class="order_check"></h3>
			<ul class="gl_top">
				<li>权限名称：
					<input type="text" name="perm_permName" value="<s:property value="perm_permName" />">
				</li>
			    <li>父级名称：<input type="text" name="perm_parentName" value="<s:property value="perm_parentName" />"></li>
			    <li><input value="查询" type="submit" class="u10" id="u10" ></li>
			</ul>
	</form>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	  	<th><input type="checkbox" onClick="checkAll(this, 'ckb_bindingRolePerm')" /></th>
	    <th>权限ID</th>
	    <th>权限名称</th>
	    <th>父级名称</th>
	    <th>类型</th>
	  </tr>
	  <s:iterator value="permRolePermissionPage.items" var="item">
			<tr>
				<td><input type="checkbox" name="ckb_bindingRolePerm" value="<s:property value="rpId" />" /></td>
				<td><s:property value="permissionId" /></td>
				<td><s:property value="permName" /></td>
				<td><s:property value="parentPermName" /></td>
				<td><s:property value="zkType" /></td>
			</tr>
	  </s:iterator>
	  <tr>
		<td>总条数：<s:property value="permRolePermissionPage.totalResultSize" /></td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permRolePermissionPage)"/>
		</td>
	  </tr>
	  <tr>
	  	<td colspan="5">
			<mis:checkPerm permCode="1109">
	  			<input value="删除绑定" type="button" class="u10" id="u10" onClick="deleteRoleHandler('<s:property value="roleId" />')">
	  		</mis:checkPerm>
	  	</td>
	  </tr>
	</table>
	</fieldset>
		<br>
		<fieldset>
		<form id="bindingPermForm" action="to_binding_perm.do?roleId=<s:property value="roleId" />" method="post">
			<legend>系统权限</legend>
			<h3 class="order_check"></h3>
			<ul class="gl_top">
				<li>权限名称：<input type="text" name="name" value="<s:property value="name" />"></li>
			    <li>类型：
				    <select id="type" name="type">
						<option value="URL"
							<s:if test="#request.type == \"URL\"">selected="selected"</s:if>
						>URL</option>
						<option value="ELEMENT" 
							<s:if test="#request.type == \"ELEMENT\"">selected="selected"</s:if>
						>元素</option>
					</select>
				</li>
			    <li>类别：
				    <select id="category" name="category" >
					    <option value="">--请选择--</option>
					   	<s:iterator value="gategoryList" var="data">	
					   	<option value="${data.category}" <s:if test="%{#data.selected==true}">selected="selected"</s:if>>${data.name}</option>
					   	</s:iterator>
				    </select>
			    </li>
			    <li>父级ID：<input type="text" name="parentID" value="<s:property value="parentID" />"></li>
			    <li>父级名称：
			    	<input type="text" name="parentName" value="<s:property value="parentName" />">
			    </li>
			    <li>
			    	<input value="查询" type="submit" class="u10" id="u10" >
			    	<mis:checkPerm permCode="1110">
			    		<input value="新增角色权限绑定" type="button" class="u10" id="u10" onClick="addPermissionHandler('<s:property value="roleId" />')">
			   		</mis:checkPerm>
			    </li>
			</ul>
		</form>
			<table class="gl_table" cellspacing="0" cellpadding="0">
			  <tr>
			  	<th><input type="checkbox" onClick="checkAll(this, 'ckb_bindingSystemPerm')" /></th>
			    <th>权限ID</th>
			    <th>类型</th>
			    <th>权限名称</th>
			    <th>父级名称</th>
			    <th>父级ID</th>
			  	<th>是否有效</th>
			    <th>财务管理</th>
			    <th>级别</th>
			    <th>操作</th>
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
						<td><s:property value="permLevel" /></td>
						<td>
							<a href="javascript:void(0);" onClick='showElementHandler(${item.permissionId}, <s:property value="roleId" />)'>查看子元素</a>
						</td>
					</tr>
			  </s:iterator>
			  <tr>
				<td>总条数：<s:property value="permPermissionPage.totalResultSize" /></td>
				<td colspan="9" align="right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
				</td>
			  </tr>
			</table>
	</fieldset>
</body>
</html>