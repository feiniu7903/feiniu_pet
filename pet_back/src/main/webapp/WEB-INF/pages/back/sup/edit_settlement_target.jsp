<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>供应商 结算对象</title>
<script type="text/javascript">
function changePaymentType(val) {
	if (val == "PERSON") {
		$("select[name='settlementTarget.paymentType']").removeAttr(
		"disabled");
	} else {
		$("select[name='settlementTarget.paymentType']").attr(
				"disabled", true).find("option[value='TRANSFER']")
				.attr("selected", true);
	}
}

$(function(){
	$("input[name=settlementTarget.advancedDays]").attr("disabled","${settlementTarget.settlementPeriod}"!='PERORDER');
	$("input[name='settlementTarget.type']").change(function() {
		var val = $(this).val();
		changePaymentType(val);
	});
	if($("#targetId").val() == "") {
		$("input[name='settlementTarget.type'][value='COMPANY']").attr("checked", true);
	}
	changePaymentType("<s:property value='settlementTarget.type' />");
});
</script>
</head>
<body>
<form id="settlementForm" method="post" action="${basePath}/sup/target/saveSettlement.do" onsubmit="return false">
<s:hidden name="settlementTarget.supplierId"/><s:hidden name="settlementTarget.targetId" id="targetId" />
	<table style="width:100%" class="cg_xx" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>对象名称：</td><td><s:if test="settlementTarget.targetId==null">自动生成</s:if><s:else>${settlementTarget.name}</s:else></td>
			<td colspan="2"><span class="required">*</span>结算帐号</td>
			<td colspan="2">其他</td>
		</tr>
		<tr>
			<td rowspan="5"><span class="required">*</span>结算周期</td></td>
			<td rowspan="5"><s:iterator value="settlementPeriodList" var="sp">
				<div>
					<input type="radio" name="settlementTarget.settlementPeriod" value="${sp.code}" class="required" <s:if test="#sp.code==settlementTarget.settlementPeriod">checked</s:if>/>${sp.cnName}
					<s:if test="#sp.code=='PERORDER'"><span style="margin-left:10px;">提前<s:textfield name="settlementTarget.advancedDays" cssStyle="width:40px;"/>天结算</span></s:if>
				</div>
				</s:iterator></td>
			<td>开户名称：</td>
			<td><s:textfield name="settlementTarget.bankAccountName" /></td>
			<td>类型：</td>
			<td><s:radio list="settlementTargetTypeList" name="settlementTarget.type"  listKey="code" listValue="cnName" cssClass="required"/></td>
		</tr>
		<tr>
			<td>开户银行：</td>
			<td><s:textfield name="settlementTarget.bankName"/></td>
			<td>付款方式：</td>
			<td><s:select list="paymentTypeList" name="settlementTarget.paymentType" listValue="cnName" listKey="code" cssClass="required"/></td>
		</tr>
		<tr>
			<td>开户账号：</td>
			<td><s:textfield name="settlementTarget.bankAccount"/></td>
			<td>联行号：</td>
			<td><s:textfield name="settlementTarget.bankLines"/></td>
		</tr>
		<tr>
			<td>支付宝账号：</td>
			<td><s:textfield name="settlementTarget.alipayAccount"/></td>
			<td rowspan="2">备注：</td>
			<td rowspan="2"><s:textarea name="settlementTarget.memo" cols="30" rows="3"/></td>
		</tr>
		<tr>
			<td>支付宝用户名：</td>
			<td><s:textfield name="settlementTarget.alipayName"/></td>
		</tr>
		<tr>
			<td>财务联系人：<s:hidden name="contactListId"/></td>
			<td colspan="5">
			<div id="contact_show_pos">
			<s:iterator value="contactList">
				<div contactId="<s:property value="contactId"/>"><span><s:property value="toHtml()" escape="false"/></span><a href='javascript:void(0)' class='deleteRelation'>删除</a></div>
			</s:iterator></div><input type="button" value="绑定联系人" class="bindContactBtn button"/></td>
		</tr>
	</table>
	<br/>
	<div><input type="submit" value="保存" class="settlementSubmit button"/></div>
</form>
</body>
</html>