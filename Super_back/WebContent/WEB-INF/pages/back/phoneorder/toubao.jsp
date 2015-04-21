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
		<s:form theme="simple" id="toubaoForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<font color="red">*</font>证件类型：
					</td>
					<td>
						<s:select name="usrReceivers.cardType"
							value="usrReceivers.cardType" id="cardType" listKey="code"
							listValue="name" list="cardTypes" onchange="onCardTypeSelect('')"></s:select>
					</td>
					<td>
						<font color="red" id="cardNumSpan">*</font>证件号码：
					</td>
					<td>
						<input id="cardNum" type="text" class="text4"
							name="usrReceivers.cardNum" value="${usrReceivers.cardNum}" />
					</td>
				</tr>
				<tr>
					<td>
						<font color="red" id="birthdaySpan">*</font>出生日期：
					</td>
					<td>
						<input name="brithday" type="text" class="date text2"
							id="birthday"
							value="<s:date name='usrReceivers.brithday' format='yyyy-MM-dd' />" />
					</td>
					<td>
						<font color="red" id="genderSpan">*</font>性别：
					</td>
					<td>
						<s:radio name="usrReceivers.gender" list="#{'M':'男','F':'女'}"></s:radio>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="anniu">
						<s:hidden name="usrReceivers.receiverId" id="toubaoReceiverId" />
						<s:if test="id != null">
							<s:hidden name="id" />
							<span class="button02 button" onclick="updateTouBaoReceiver();">修改</span>
						</s:if>
						<s:else>
							<span class="button02 button" onclick="addTouBaoReceiver();">保存</span>
						</s:else>
					</td>
				</tr>
			</table>
		</s:form>
		<script type="text/javascript">
$(function() {
	var dfDate=new Date();
	$('#birthday').datepicker( {
		changeMonth : true,
		changeYear : true,
		defaultDate:dfDate,
		yearRange:''+(dfDate.getFullYear()-100)+':'+dfDate.getFullYear()
	});
	$(document).ready(function() {
		onCardTypeSelect("");
	});
});
</script>
	</body>
</html>
