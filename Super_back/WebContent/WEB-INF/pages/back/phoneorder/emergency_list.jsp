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
		<input type="hidden" id="hasEmergencyContact" value="${emergencyContact!=null }" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<td>
					姓名
				</td>
				<td>
					联系电话
				</td>
				<td>
					Email
				</td>
				<td>
					证件类型
				</td>
				<td>
					证件号码
				</td>
				<td>
					出生日期
				</td>
				<td>
					邮编
				</td>
				<td>
					地址
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:if test="emergencyContact != null">
				<tr>
					<td receiverId="emergencyContact${emergencyContact.receiverId }">
						${emergencyContact.receiverName }
					</td>
					<td>
						${emergencyContact.mobileNumber }
					</td>
					<td>
						${emergencyContact.email }&nbsp;
					</td>
					<td>
						${emergencyContact.zhCardType }&nbsp;
					</td>
					<td>
						${emergencyContact.cardNum }&nbsp;
					</td>
					<td>
						<s:date name="emergencyContact.brithday" format="yyyy-MM-dd" />
						&nbsp;
					</td>
					<td>
						${emergencyContact.postCode }&nbsp;
					</td>
					<td>
						${emergencyContact.address }&nbsp;
					</td>
					<td>
						<a href="javascript:void(0)"
							onclick="doOperator('emergencyContactList', '${emergencyContact.receiverId}&delete');">删除</a>
					</td>
				</tr>
			</s:if>
		</table>
	</body>
</html>
