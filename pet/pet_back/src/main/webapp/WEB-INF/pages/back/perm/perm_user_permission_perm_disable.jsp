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
<title>禁用权限</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/back/perm/perm_page_head.jsp" />
<script type="text/javascript">
	function disableUserPermissionHandler(permissionId){
		$("<div/>").dialog({
			autoOpen: true, 
	        modal: true,
	        position: 'center',
	        show: "explode",
	        hide: "highlight",
	        title : "提示",
	        width: 200, 
	        height: 100,
	        buttons: { 
	        	"确定": function() { 
	        		$(this).dialog("close"); 
	        		$.post("disable_permission.do?userId="+$("#userIdHd").val()+"&permissionId="+permissionId,
	        				{
	        					userId:$("#userIdHd").val(),
	        					permissionId:permissionId,
	        				},
	        				function(data){
	        					if(data != "success"){
	        						Utils.alert("操作失败");
	        					}else{
	         						$("#searchForm").submit();
	        					}
	        		});
	        		}
	        }
		}).html("确定禁用？");
	}
</script>
</head>
<body>
<div class="popmain">
	<form id="searchForm" action="to_disable_permission.do" method="post">
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
					<th>类型</th>
					<th>类别</th>
					<th>操作</th>
				</tr>
				<s:iterator value="permPermissionPage.items" var="item">
					<tr>
						<td><s:property value="permissionId" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="parentName" /></td>
						<td><s:property value="zkType" /></td>
						<td><s:property value="zhCategory" /></td>
						<td class="gl_cz">
							<a href="javascript:void(0)" onclick="disableUserPermissionHandler(${item.permissionId})">禁止</a>
						</td>
					</tr>
			  </s:iterator>
			  <tr>
				<td cospan="1">总条数：<s:property value="permPermissionPage.totalResultSize" />
				</td>
				<td colspan="3" align="right">
				<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(permPermissionPage)"/>
				</td>
			  </tr>
			</tbody>
		</table>
    </div>
</body>
</html>