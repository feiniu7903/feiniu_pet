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
<title>后台用户管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_user_index.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
</head>
<body>
	<ul class="gl_top">
		<form action="search.do" method="post">
			<li>用户名：<input type="text" class="u3" name="userName" value="<s:property value="userName" />" id="u3"></li>
			<li>真实姓名：<input type="text" class="u5" name="realName" value="<s:property value="realName" />" id="u5"></li>
			<li>所属部门：
				<select id="departmentsSlt" name="departmentId">
					<s:iterator value="departmentsList" var="item">
						<option value="${item.orgId}"
							<s:if test="#item.orgId == #request.departmentId">selected="selected"</s:if>>
							${item.zhDepartmentName }</option>
					</s:iterator>
				</select>
			</li>
		    <li>状态：
				<select id="statusSlt" name="valid">
					<option value=""
						<s:if test="#request.valid == \"\"">selected="selected"</s:if>>全部</option>
					<option value="Y"
						<s:if test="#request.valid == \"Y\"">selected="selected"</s:if>>正常</option>
					<option value="N"
						<s:if test="#request.valid == \"N\"">selected="selected"</s:if>>锁定</option>
				</select>
			</li>
			<li>
				<input type="submit" value="查询" class="u10" id="u10"> 
				<mis:checkPerm permCode="1119">
				<input type="button" value="新增" class="u10" id="u10" onclick="newUserHandler()"> 
				</mis:checkPerm>
			</li> 
		</form>
	</ul>
	<div class="tab_top"></div>
	<table class="gl_table" cellspacing="0" cellpadding="0">
	  <tr>
	    <th>用户ID</th>
	    <th>用户名</th>
	    <th>真实姓名</th>
	    <th>部门编号</th>
	    <th>部门</th>
	    <th>是否有效</th>
	    <th>职务</th>
	    <th>操作</th>
	  </tr>
	  <s:iterator value="permUserPage.items" var="item">
			<tr>
				<td><s:property value="userId" /></td>
				<td><s:property value="userName" /></td>
				<td><s:property value="realName" /></td>
				<td><s:property value="departmentId" /></td>
				<td><s:property value="departmentName" /></td>
				<td><s:property value="zhValid" /></td>
				<td><s:property value="position" /></td>
				<td class="gl_cz">
					<mis:checkPerm permCode="1121">
					<a href="javascript:void(0);" onclick="showUserHandler(${item.userId})">查看</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="1120">
					<a href="javascript:void(0);" onclick="editUserHandler(${item.userId})">修改</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="1122">
					<a href="to_permission_manage.do?userId=${item.userId}">角色及权限</a>
					</mis:checkPerm>
					<mis:checkPerm permCode="1128">
					<a href="javascript:void(0);" onclick="initPasswordHandler(${item.userId})">初始化密码</a>
					</mis:checkPerm>
					<a href="#log" class="showLogDialog" param={"objectId":${item.userId},"objectType":"PERM_USER"}>日志</a>
				</td>
			</tr>
	  </s:iterator>
	  <tr>
		<td colspan="1">总条数：<s:property value="permUserPage.totalResultSize" />
		</td>
		<td colspan="7" align="right">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permUserPage)"/>
		</td>
	  </tr>
	</table>
</body>
</html>