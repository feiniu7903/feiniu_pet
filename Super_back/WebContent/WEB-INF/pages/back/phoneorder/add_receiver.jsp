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
		<s:form theme="simple" id="addUsrReceiverForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<font color="red">*</font>取票（联系）人：
					</td>
					<td>
						<input name="newReceiver.receiverName" type="text"
							id="addReceiverName" class="text3"
							value="${newReceiver.receiverName}" />
					</td>
					<td>
						Email：
					</td>
					<td>
						<input name="newReceiver.email" type="text" class="text4"
							value="${newReceiver.email}" />
					</td>
				</tr>
				<tr>
					<td>
						姓名拼音：
					</td>
					<td>
						<input name="newReceiver.pinyin" type="text"
							id="addReceiverPinyin" class="text3"
							value="${newReceiver.pinyin}" />
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>				
				<tr>
					<td>
						<font color="red">*</font>手机号码：
					</td>
					<td>
						<input name="newReceiver.mobileNumber" id="addReceiverMobile"
							type="text" class="text2" value="${newReceiver.mobileNumber}" />
					</td>
					<td>
						座机号：
					</td>
					<td>
						<input name="newReceiver.phone" type="text" class="text2"
							value="${newReceiver.phone}" />
					</td>
				</tr>
				<tr>
					<td>
						<s:if test="cardTypeRequired==true"><font color="red">*</font></s:if>证件类型：
					</td>
					<td>
						<s:select name="newReceiver.cardType" id="addReceivercardType"
							onchange="onCardTypeSelect('addReceiver')" listKey="code"
							listValue="name" headerKey="" headerValue="请选择" list="cardTypes"></s:select>
					</td>
					<td>
						<font id="addReceivercardNumSpan" color="red">*</font>证件号码：
					</td>
					<td>
						<input type="text" class="text4" name="newReceiver.cardNum"
							id="addReceivercardNum" value="${newReceiver.cardNum}" />
					</td>
				</tr>
				<tr>
					<td>
						<font id="addReceiverbirthdaySpan" color="red">*</font>出生日期：
					</td>
					<td>
						<input name="brithday" type="text" class="date text2"
							id="addReceiverbirthday"
							value="<s:date name='newReceiver.brithday' format='yyyy-MM-dd' />" />
					</td>
					<td>
						<font id="addReceivergenderSpan" color="red">*</font>性别：
					</td>
					<td>
						<s:radio name="newReceiver.gender" list="#{'M':'男','F':'女'}"></s:radio>
					</td>
				</tr>
				<tr>
					<td>
						传真：
					</td>
					<td>
						<input name="newReceiver.fax" type="text" class="text2"
							value="${newReceiver.fax}" />
					</td>
					<td>
						传真接收人：
					</td>
					<td>
						<input name="newReceiver.faxContactor" type="text" class="text3"
							value="${newReceiver.faxContactor}" />
					</td>
				</tr>
				<tr>
					<td>
						邮编：
					</td>
					<td>
						<input name="newReceiver.postCode" type="text" class="text3"
							value="${newReceiver.postCode}" />
					</td>
					<td>
						地址：
					</td>
					<td>
						<input name="newReceiver.address" type="text" class="text1"
							value="${newReceiver.address}" />
					</td>
				</tr>
				<tr>
					<td colspan="4" class="anniu">
						<s:if test="id != null">
							<s:hidden name="id" />
							<span class="button02 button" onclick="addUsrReceiver();">修改</span>
						</s:if>
						<s:else>
							<span class="button02 button" onclick="addUsrReceiver();">保存</span>
						</s:else>
					</td>
				</tr>
			</table>
			<input type="hidden" id="cardTypeRequired" value="${cardTypeRequired}" />
		</s:form>
		<script type="text/javascript">
$(function() {
	var dfDate=new Date();
	$('#addReceiverbirthday').datepicker( {
		changeMonth : true,
		changeYear : true,
		defaultDate:dfDate,
		yearRange:''+(dfDate.getFullYear()-100)+':'+dfDate.getFullYear()
	});
	
	$(document).ready(function() {
		onCardTypeSelect('addReceiver');
	});
});
</script>
	</body>
</html>
