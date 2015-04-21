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
<title>后台用户管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_user_edit.js"></script>
</head>
<body>
	<input type="hidden" id="validHd" value="${permUser.valid }" />
	<form id="userForm" action="save.do" method="post">
	<input type="hidden" name="permUser.userId" value="${permUser.userId }" />
	<input type="hidden" name="permUser.userName" value="${permUser.userName }" />
	<input type="hidden" name="permUser.password" value="${permUser.password }" />
	<input type="hidden" name="permUser.employeeNum" value="${permUser.employeeNum }" />
	<ul class="gl3_top">
		<li>
			用户名：${permUser.userName}
		</li>
		<li>真实姓名：<input type="text" name="permUser.realName" value="${permUser.realName }"></li>
	    <li>职务：<input type="text" name="permUser.position" value="${permUser.position }"></li>
	    <li>联系方式：<input type="text" name="permUser.mobile" value="${permUser.mobile }"></li>
		<li>邮箱：<input type="text" name="permUser.email" value="${permUser.email }"></li> 
		<li>分机：<input type="text" name="permUser.extensionNumber" value="${permUser.extensionNumber }"></li> 
		<li>部门：
			<select id="levelSlct" onchange="levelChangeHandler()">
				<option value="1" <s:if test="#request.org.permLevel == 1">selected="selected"</s:if>>一级</option>
				<option value="2" <s:if test="#request.org.permLevel == 2">selected="selected"</s:if>>二级</option>
				<option value="3" <s:if test="#request.org.permLevel == 3">selected="selected"</s:if>>三级</option>
				<option value="4" <s:if test="#request.org.permLevel == 4">selected="selected"</s:if>>四级</option>
			</select>
			<select id="orgSlct" name="permUser.departmentId">
				<s:iterator value="#request.orgList" var="item">
					<option value="${item.orgId}"
						<s:if test="#item.orgId == #request.org.orgId">selected="selected"</s:if>>
						${item.zhDepartmentName }
					</option>
				</s:iterator>
			</select>
		</li> 
		<li>状态：
			<input id="validY" type="radio" value="Y" name="permUser.valid" />正常
			<input id="validN" type="radio" value="N" name="permUser.valid"/>锁定
		</li> 
		<li>华为CC系统账号：
			<label><input type="radio" name="permUser.isHuaweiCc" value="false" 
				<s:if test="permUser.isHuaweiCc == \"false\"">
					checked="checked"
				</s:if>>否
			</label>
			<label><input type="radio" name="permUser.isHuaweiCc" value="true"
				<s:if test="permUser.isHuaweiCc == \"true\"">
					checked="checked"
				</s:if>>是
			</label>
		</li>
	</ul>
	<div class="gl3_zs_b">
	    <span><input name="" value="保存" type="submit"></span>
	</div>
	</form>
</body>
</html>
	