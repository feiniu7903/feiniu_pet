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
		<input type="hidden" id="hasReceiver" value="${usrReceivers!=null }" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<td>
					姓名
				</td>
				<td>
					姓名拼音
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
					邮编
				</td>
				<td>
					地址
				</td>
				<td>
					座机号
				</td>
				<td>
					传真
				</td>
				<td>
					传真接收人
				</td>
			</tr>
			<s:if test="usrReceivers != null">
			 <input type="hidden" id="hasCardNum" value="${usrReceivers.cardNum!=null}"/>
				<tr>
					<td receiverId="receiver${usrReceivers.receiverId }">
						${usrReceivers.receiverName }
					</td>
					<td>
						${usrReceivers.pinyin }
					</td>
					<td>
						${usrReceivers.mobileNumber }
					</td>
					<td>
						${usrReceivers.email }&nbsp;
					</td>
					<td>
						${usrReceivers.zhCardType }&nbsp;
					</td>
					<td>
						${usrReceivers.cardNum }&nbsp;
					</td>
					<td>
						${usrReceivers.postCode }&nbsp;
					</td>
					<td>
						${usrReceivers.address }&nbsp;
					</td>
					<td>
						${usrReceivers.phone }&nbsp;
					</td>
					<td>
						${usrReceivers.fax }&nbsp;
					</td>
					<td>
						${usrReceivers.faxContactor }&nbsp;
					</td>
				</tr>
			</s:if>
		</table>
	</body>
</html>
