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
		<strong>配送</strong>
		<p>
			已有地址：
		</p>
		<br />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<s:if test="product.physical == 'true'">
					<td height="30">
						实体票
					</td>
				</s:if>
				<td>
					发票
				</td>
				<td>
					地址
				</td>
				<td>
					接收人
				</td>
				<td>
					电话
				</td>
				<td>
					邮编
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:if test="addressReceiverList != null">
				<s:iterator value="addressReceiverList">
					<tr bgcolor="#ffffff" align="center">
						<s:if test="product.physical == 'true'">
							<td>
								<input type="radio" name="shitipiaoRadio" value="${receiverId}" />
							</td>
						</s:if>
						<td>
							<input type="radio" name="fapiaoRadio" value="${receiverId}" />
						</td>
						<td>
							${province} ${city} ${address }
						</td>
						<td>
							${receiverName }
						</td>
						<td>
							${mobileNumber }
						</td>
						<td>
							${postCode }
						</td>
						<td>
							<a href="javascript:void(0);"
								onclick="doShowDialog('addAddressDiv', {'to': 'add_address', 'id': '${receiverId}'}, '修改地址');">修改</a>
							<a href="javascript:void(0);"
								onclick="doOperator('addressList', '${receiverId}');">删除</a>
						</td>
					</tr>
				</s:iterator>
			</s:if>
		</table>
	</body>
</html>
