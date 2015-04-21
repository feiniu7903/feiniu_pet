<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body>
		<form id="channelRegistForm" method="post">
			<table cellspacing="1" cellpadding="4" border="0" bgcolor="#B8C9D6"
				width="100%" style="line-height: 30px;">
				<tbody>
					<tr align="center">
						<td bgcolor="#f4f4f4" width="2%">
							渠道来源
						</td>
						<td bgcolor="#ffffff" align="left">
							<input type="radio" name="isTaobao" id="isTaobao" value="010936"
								checked />
							淘宝
						</td>
					</tr>
					<tr align="center">
						<td bgcolor="#f4f4f4" width="15%">
							手机号
						</td>
						<td bgcolor="#ffffff" align="left">
							<input type="text" name="mobileNumber" id="channelMobile" />
						</td>
					</tr>
				</tbody>
			</table>

			<input type="button" name="btnOrdSmsSend"
				onClick="doChannelRegist('<%=basePath%>phoneOrder/doChannelRegist.do');" class="button" value="会员注册" />
		</form>
	</body>
</html>
