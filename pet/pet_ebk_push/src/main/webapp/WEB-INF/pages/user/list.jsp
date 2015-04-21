<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Admin Console</title>
<meta name="menu" content="user" />
<link rel="stylesheet" type="text/css" href="<c:url value='/styles/tablesorter/style.css'/>" />
<script type="text/javascript" src="<c:url value='/scripts/jquery.tablesorter.js'/>"></script>
</head>

<body>

<h1>Users</h1>

<table id="tableList" class="tablesorter" cellspacing="1">
	<thead>
		<tr>
			<%--
			<th width="5%">Online</th>
			<th width="30%">Username</th>
			<th width="20%">Name</th>
			<th width="20%">Email</th>
			<th width="25%">Created</th>
			--%>
			<th>Online</th>
			<th>Username</th>
			<th>Name</th>
			<th>Email</th>
			<th>Created</th>
		</tr>
	</thead>
	<tbody>
	
	<s:iterator value="userList" var="user">

			<tr>
				<td align="center">
				<s:if test="${user.online}==true"><img src="images/user-online.png" />
				
				</s:if>
				<s:else>
				<img src="images/user-offline.png" />
				</s:else>
				
				</td>
				<td>${user.username}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td align="center"></td>
			</tr>
	</s:iterator>
	</tbody>
</table>

<script type="text/javascript">
//<![CDATA[
$(function() {
	$('#tableList').tablesorter();
	//$('#tableList').tablesorter( {sortList: [[0,0], [1,0]]} );
	//$('table tr:nth-child(odd)').addClass('odd');
	$('table tr:nth-child(even)').addClass('even');	 
});
//]]>
</script>

</body>
</html>
