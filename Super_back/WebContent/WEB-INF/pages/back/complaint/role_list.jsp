<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色配置</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp" />
<script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
<script type="text/javascript" src="<%=basePath%>js/prod/condition.js"></script>
<script type="text/javascript">
	function editRole(orgId) {
		$("<iframe frameborder='0' ></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "修改人员",
	        position: 'center',
	        width: 750, 
	        height: 240
		}).width(720).height(200).attr("src","<%=basePath%>roleProd/updateUsers.do?orgId="+orgId);
	}
</script>
</head>

<body>
		<div class="row2" style="text-align: center;">
			<table border="0" cellspacing="0" cellpadding="0" class="newTable">
				<tr class="newTableTit">
					<td>角色部门</td>
					<td>人员</td>
					<td>操作</td>
				</tr>
				<s:iterator value="rolePage.items" var="role">
					<tr>
						<td>${role.departmentName}</td>
						<td>${role.persons}</td>
						<td>
							<a href="javascript:void(0)" onclick="editRole('${role.orgId}');">修改人员</a>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td align="left">总条数：<s:property
							value="rolePage.totalResultSize" />
					</td>
					<td colspan="2" align="right" style="text-align:right">
					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(rolePage)"/>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>