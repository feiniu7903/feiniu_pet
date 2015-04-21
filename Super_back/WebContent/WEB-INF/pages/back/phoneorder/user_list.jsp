<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_dialog.js">
</script>
		<script type="text/javascript">
$(document).ready(function() {
	$("input[name='userRadio']").each(function() {
		$(this).bind("click", function() {
			var userId = $(this).val();
			$("#userId").val(userId);
			confirmUser(userId);
		});
	});

});
</script>

	</head>

	<body>
		<table width="100%" class="datatable" border="0">
			<tr>
				<th>
					&nbsp;
				</th>
				<th>
					用户名
				</th>
				<th>
					真实姓名
				</th>
				<th>
					手机号码
				</th>
				<th>
					电子邮件
				</th>
				<th>
					会员卡号
				</th>
				<th>
					注册时间
				</th>
			</tr>
			<s:if test="userList != null">
				<s:iterator value="userList">
					<tr>
						<td>
							<input name="userRadio" type="radio" value="${userId}" />
							<input type="hidden" value="${userName}" id="user_name" />
						</td>
						<td>
							<s:property value="userName" />
						</td>
						<td>
							<s:property value="realName" />
						</td>
						<td>
							<s:property value="mobileNumber" />
							<s:if test='isMobileChecked=="Y"'>(已验证)</s:if>
						</td>
						<td>
							<s:property value="email" />
						</td>
						<td>
							<s:property value="memberShipCard" />
						</td>
						<td>
							<s:date name="createdDate" format="yyyy-MM-dd" />
						</td>
					</tr>
				</s:iterator>
			</s:if>
		</table>
	</body>
</html>
