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
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
</head>
<body>
	<ul class="gl3_top">
		<li>
			用户名：${permUser.userName }
		</li>
		<li>真实姓名：${permUser.realName }</li>
	    <li>职务：${permUser.position }</li>
	    <li>联系方式：${permUser.mobile}</li>
		<li>邮箱：${permUser.email}</li> 
		<li>部门：${permUser.departmentName}</li> 
		<li>状态：${permUser.zhValid }</li> 
		<li>分机：${permUser.extensionNumber}</li> 
		<li>华为CC系统账号：
			<s:if test="permUser.isHuaweiCc == \"true\"">是</s:if>
			<s:if test="permUser.isHuaweiCc == \"false\"">否</s:if>
		</li>
	</ul>
	<br />
	权限：
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>编码</th>
	    <th>名称</th>
	    <th>URL</th>
	    <th>父权限名称</th>
	    <th>类别</th>
	  </tr>
	  <s:iterator value="permPermissionPage.items" var="item">
			<tr>
				<td><s:property value="permissionId" /></td>
				<td><s:property value="name" /></td>
				<td><s:property value="url" /></td>
				<td><s:property value="parentName" /></td>
				<td><s:property value="zhCategory" /></td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="permPermissionPage.totalResultSize" />
		</td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>