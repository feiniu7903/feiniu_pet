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
<title>分配权限</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript">
	function addUserPermissionHandler(permissionId){
		$.post("add_permission.do?userId="+$("#userIdHd").val()+"&permissionId="+permissionId,
				{
					userId:$("#userIdHd").val(),
					permissionId:permissionId,
					enableDays:$("#enableDaysSlct" + permissionId).val()
				},
				function(data){
					if(data == 1){
						Utils.alert("用户已拥有此权限,无法添加");
						return;
					}
					if(data != "success"){
						Utils.alert("操作失败");
					}else{
						Utils.alert("操作成功");
// 						$("#searchForm").submit();
					}
		});
	}
</script>
</head>
<body>
<div class="popmain">
	<form id="searchForm" action="to_add_permission.do" method="post">
		<input id="userIdHd" type="hidden" name="userId" value="${request.userId }" />
	    <div class="role">权限名称 &amp; ID：
	    	<input type="text" class="roleInput" name="name" value="${name }"/>
	    	<input type="submit" value="搜索">
	    </div>
    </form>
    <table cellspacing="0" cellpadding="0" class="sy_table">
			<tbody>
				<tr>
					<th>权限ID</th>
					<th>权限名称</th>
					<th>父级名称</th>
					<th>有效时长</th>
					<th>操作</th>
				</tr>
				<s:iterator value="permPermissionPage.items" var="item">
					<tr>
						<td><s:property value="permissionId" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="parentName" /></td>
						<td>
							<select id="enableDaysSlct${item.permissionId }">
								<option value="1">1天</option>
								<option value="2">2天</option>
								<option value="10">10天</option>
								<option value="30">30天</option>
							</select>
						</td>
						<td class="gl_cz">
							<a href="javascript:void(0)" onclick="addUserPermissionHandler(${item.permissionId})">新增</a>
						</td>
					</tr>
			  </s:iterator>
			  <tr>
				<td colspan="1">总条数：<s:property value="permPermissionPage.totalResultSize" />
				</td>
				<td colspan="4" align="right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
				</td>
			  </tr>
			</tbody>
		</table>
    </div>
</body>
</html>