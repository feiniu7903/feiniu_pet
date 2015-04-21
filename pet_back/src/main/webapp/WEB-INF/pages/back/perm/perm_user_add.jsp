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
<title>新增用户</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript" src="<%=basePath%>/js/perm/perm_user_add.js"></script>
</head>
<body>
	<form id="userForm" action="save.do" method="post">
	<ul class="gl3_top">
		<li class="clear">
			用户名：
			<input type="radio" name="permUser.userName" value="cs${request.userNum }" />cs${request.userNum }
			<input type="radio" name="permUser.userName" value="lv${request.userNum }" />lv${request.userNum }
		</li>
		<li>真实姓名：<input type="text" name="permUser.realName" ></li>
	    <li>职务：<input type="text" name="permUser.position"></li>
	    <li>联系方式：<input type="text" name="permUser.mobile"></li>
		<li>邮箱：<input type="text" name="permUser.email"></li> 
		<li>部门：
			<select id="levelSlct" onchange="levelChangeHandler()">
				<option value="1">一级</option>
				<option value="2">二级</option>
				<option value="3">三级</option>
				<option value="4">四级</option>
			</select>
			<select id="orgSlct" name="permUser.departmentId">
			</select>
		</li> 
		<li>状态：
			<input type="radio" value="Y" name="permUser.valid"/>正常
			<input type="radio" value="N" name="permUser.valid"/>锁定
		</li> 
		<li>分机：<input type="text" name="permUser.extensionNumber"></li> 
		<li>华为CC系统账号：
			<label><input type="radio" name="permUser.isHuaweiCc" value="false" checked="checked">否</label>
			<label><input type="radio" name="permUser.isHuaweiCc" value="true">是</label>
		</li>
	</ul>
	<div class="gl3_zs_b" align="center">
	    <input name="" value="保存" type="submit">
	</div>
	</form>
</body>
</html>
	