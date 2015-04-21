<%--
  Created by IntelliJ IDEA.
  User: zhushuying
  Date: 13-11-13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>查看内容</title>
</head>
<body>
<div>
	<table class="nc_table" cellspacing="1" cellpadding="1">
		<s:iterator value="emailList" var="email">
			<tr>
				<td class="nc_tr_head" width="100px;">邮箱内容：</td>
				<td class="nc_tr_body">
					<s:property value="contentEmail" escape="false"/>
				</td>
			</tr>
		</s:iterator>
	</table>
</div>


</body>
</html>