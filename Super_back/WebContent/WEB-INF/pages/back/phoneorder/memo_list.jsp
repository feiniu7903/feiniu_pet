<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<td>
					备注类型
				</td>
				<td>
					内容
				</td>
				<td>
					维护人
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:if test="memoList != null">
				<s:iterator value="memoList">
					<tr>
						<td>
							${zhType}
							
							<s:if test="userMemo=='true'"><b style="color: #E40000;">(此备注有特殊用户需求)</b></s:if>
						</td>
						<td>
							${content}
						</td>
						<td>
							${operatorName}
						</td>
						<td>
							<a href="javascript:void(0)"
								onclick="doOperator('memoList', '${uuid}');">删除</a>
						</td>
						
					</tr>
				</s:iterator>
			</s:if>
		</table>
	</body>
</html>
