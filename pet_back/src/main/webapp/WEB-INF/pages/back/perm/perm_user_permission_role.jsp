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
<title>分配角色</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript">
	function addUserRoleHandler(roleId){
		$.post("add_role.do?userId="+$("#userIdHd").val()+"&roleId="+roleId,
				function(data){
					if(data == 1){
						Utils.alert("用户已拥有此角色");
						return;
					}
					if(data != "success"){
						Utils.alert("操作失败");
					}else{
						Utils.alert("操作成功");
						//$("#searchForm").submit();
					}
		});
	}
</script>
</head>
<body>
<div class="popmain">
	<form id="searchForm" action="to_add_role.do" method="post">
		<input id="userIdHd" type="hidden" name="userId" value="${request.userId }" />
	    <div class="role">角色名称 &amp; ID：
	    	<input type="text" class="roleInput" name="roleName" value="${roleName }"/>
	    	<input type="submit" value="搜索">
	    </div>
    </form>
    <table cellspacing="0" cellpadding="0" class="sy_table">
			<tbody>
				<tr>
					<th>角色ID</th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<s:iterator value="permRolePage.items" var="item">
					<tr>
						<td><s:property value="roleId" /></td>
						<td><s:property value="roleName" /></td>
						<td><s:property value="description" /></td>
						<td><s:property value="createTime" /></td>
						<td class="gl_cz">
							<a href="javascript:void(0)" onclick="addUserRoleHandler(${item.roleId})">新增</a>
						</td>
					</tr>
			  </s:iterator>
			  <tr>
				<td cospan="1">总条数：<s:property value="permRolePage.totalResultSize" />
				</td>
				<td colspan="3" align="right"><s:property escape="false"
						value="@com.lvmama.comm.utils.Pagination@pagination(permRolePage)" />
				</td>
			  </tr>
			</tbody>
		</table>
    </div>
</body>
</html>