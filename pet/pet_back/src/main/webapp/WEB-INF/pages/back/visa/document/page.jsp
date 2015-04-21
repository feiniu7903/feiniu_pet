<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>
	<body>
		<div class="row2">
		    <table border="0" cellspacing="0" cellpadding="0" class="newTable">
				<tr class="newTableTit">
				        <th>材料类型</th>
						<th>国家</th>
						<th>签证类型</th>
						
					</tr>
					<s:iterator value="documentlist" var="document">
						<tr>
						    <td>${document.cnOccupation}</td>
							<td>${document.country}</td>
							<td>${document.cnVisaType}</td>
						</tr>
					</s:iterator>
				</table>
			</div>
	</body>
</html>
