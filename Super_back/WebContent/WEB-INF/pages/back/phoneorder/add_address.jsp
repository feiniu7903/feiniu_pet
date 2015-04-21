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
		<s:form theme="simple" id="addAddressForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17%">
						<font color="red">*</font>地址：
					</td>
					<td width="34%">
						<s:select name="addressReceiver.province" id="addProvince"
							list="provinceList" listKey="provinceId" listValue="provinceName"></s:select>
						<s:select name="addressReceiver.city" id="addCity" list="cityList"
							listKey="cityId" listValue="cityName"></s:select>
						<input name="addressReceiver.address" type="text" id="addAddress"
							size="15" class="text2" value="${addressReceiver.address}" />
					</td>
					<td>
						<font color="red">*</font>联系人：
					</td>
					<td>
						<input name="addressReceiver.receiverName" type="text" class="text2"
							id="addReceiverName" value="${addressReceiver.receiverName}" />
					</td>
				</tr>
				<tr>
					<td>
						<font color="red">*</font>联系电话：
					</td>
					<td>
						<input name="addressReceiver.mobileNumber" type="text" class="text2"
							id="addMobileNumber" value="${addressReceiver.mobileNumber}" />
					</td>
					<td>
						邮编：
					</td>
					<td>
						<input name="addressReceiver.postCode" type="text" class="text2"
							value="${addressReceiver.postCode}" id="addPostCode" />
					</td>
				</tr>
				<tr>
					<td colspan="4" class="anniu">
						<s:if test="id != null">
							<s:hidden name="id" />
							<input type="button" value="修改" class="button"
								onclick="addOrderAddress();" />
						</s:if>
						<s:else>
							<input type="button" value="保存" class="button"
								onclick="addOrderAddress();" />
						</s:else>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
