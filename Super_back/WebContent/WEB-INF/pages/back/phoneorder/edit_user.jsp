<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript">
function edit(userId) {
	var MOBILE_REGX = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/;
	var mob = $('#mobileNo').val();
	var mobcode = $('#codeNo').val();

	if ($.trim(mob) == "") {
		alert("手机号不能为空！");
		return;
	}
	if (!MOBILE_REGX.test(mob)) {
		alert("手机号格式不正确！");
		$('#mobileNo').val("");
		return;
	}
	$.ajax( {
		type : "POST",
		dataType : "json",
		url : "/super_back/phoneOrder/doEditUser.do",
		async : false,
		data : $('#editUserForm').serialize(),
		timeout : 3000,
		error : function(a, b, c) {
			if (b == "timeout") {
				alert("请求超时");
			} else if (b == "error") {
				alert("请求出错");
			}
		},
		success : function(data) {
			if (data.success) {
				if (data.jsonMsg == "mobile_used") {
					alert("此号码已被其他用户使用")
				} else if (data.jsonMsg == "mobile_changed") {
					alert("号码已更改或未验证，请填入验证码(已发送)");
				} else if (data.jsonMsg == "code_error") {
					alert("验证码错误")
				} else {
					alert("修改成功");
					$('#edituser').dialog("close");
					$("#userItem").load($("#userItem").attr('href'));
				}
			} else {
				alert(data.msg);
			}
		}
	});
}
</script>
	</head>

	<body>
		<s:form id="editUserForm" theme="simple">
			<table width="100%" class="datatable"
				style="margin-top: 10px; margin-left: 5px; margin-bottom: 10px;">
				<tr>
					<td>
						真实姓名：
					</td>
					<td>
						<input type="text" readonly="readonly" name="user.realName"
							id="name" value="${user.realName }" />
					</td>
					<td>
						用户名：
					</td>
					<td>
						<input type="text" readonly="readonly" name="user.userName"
							id="username" value="${user.userName }" />
					</td>
				</tr>
				<tr>
					<td>
						手机号码：
					</td>
					<td>
						<input type="text" name="user.mobileNumber" id="mobileNo"
							value="${user.mobileNumber }" />
					</td>
					<td>
						电子邮件：
					</td>
					<td>
						<input type="text" readonly="readonly" name="user.email" id="mail"
							value="${user.email }" />
					</td>
				</tr>
				<%--
				<tr>
					<td>
						验证码：
						<input type="text" name="codeNo" id="codeNo" maxlength="6"
							size="6" />
					</td>
					<td colspan="3">
						<input type="hidden" name="user.userId" value="${user.userId}" />
						<input type="button" value="修改" class="button" onclick="javascript:edit('${user.userId }');" />
					</td>
				</tr>
				 --%>
			</table>
		</s:form>
	</body>
</html>
