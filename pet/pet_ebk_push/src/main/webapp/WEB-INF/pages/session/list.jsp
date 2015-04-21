<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Admin Console</title>
<meta name="menu" content="session" />
<script type="text/javascript" src="http://s2.lvjs.com.cn/min/index.php?f=/js/new_v/jquery-1.7.2.min.js"  charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/styles/tablesorter/style.css"/>
<script type="text/javascript" src="<%=basePath %>/scripts/jquery.tablesorter.js"></script>
<script type="text/javascript">
function send(id){
	$.post("<%=path%>/push/target.do",{udid:id,msg:$("#"+id+"_text").val()},function(data){
		alert(data);
	});
	  
}
</script>
</head>

<body>

<h1>Sessions</h1>

<table id="tableList" class="tablesorter" cellspacing="1">
	<thead>
		<tr>
			<%--
			<th width="30%">Username</th>
			<th width="10%">Resource</th>
			<th width="10%">Status</th>
			<th width="10%">Presence</th>
			<th width="15%">Client IP</th>
			<th width="25%">Created</th>
			--%>
			<th>userId</th>
			<th>udid</th>
			<th>Status</th>
			<th>Presence</th>
			<th>Client IP</th>
			<th>Created</th>			
		</tr>
	</thead>
	<tbody>
	<s:iterator value="list" var="sess">

			<tr>
				<td>${userId}</td>
				<td>${udid}</td>
				<td align="center">${state}</td>
				<td>
					<s:if test="state=='onLine'">
					<img src="images/user-online.png" />
					
					</s:if>
					<s:elseif test="sess.presence=='Offline'">
					<img src="images/user-offline.png" />
					</s:elseif>
					<s:else>
						
					<img src="images/user-away.png" />
					</s:else>
					
				</td>
					
				<td>${remoteIp}</td>
				
				<td align="center"><textarea rows="5" cols="3" id="${udid}_text"></textarea> <button id="sendBtn" onclick="send('${udid}')">发送测试消息 </button></td>
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
