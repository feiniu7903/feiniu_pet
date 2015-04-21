<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>传真管理</title>
<script type="text/javascript" src="${basePath }/js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript">
	function showAndUndisabled(e) {
		$("#orderItemMetaIdTr").hide();
		$("#orderItemMetaId").attr("disabled","disabled");
		if($("#messageType").val() == "ORDER_MODIFY_SETTLEMENT_PRICE") {
			$("#orderItemMetaIdTr").show();
			$("#orderItemMetaId").removeAttr("disabled");
		}
	}
</script>
</head>
<body style="height: auto;">
<form action="<%=request.getContextPath()%>/ebk/fax/backCreateFaxSubmit.do" method="post">
<table>
	<tr>
		<td>订单号</td>
		<td><input type="text" name="orderId" value=""/></td>
	</tr>
	<tr>
		<td>消息类型</td>
		<td>
			<select id="messageType" name="messageType" onchange="showAndUndisabled(this)">
				<option value="ORDER_CREATE">ORDER_CREATE</option>
				<option value="ORDER_APPROVE">ORDER_APPROVE</option>
				<option value="ORDER_MODIFY_PERSON">ORDER_MODIFY_PERSON</option>
				<option value="ORDER_PAYMENT">ORDER_PAYMENT</option>
				<option value="ORDER_MODIFY_SETTLEMENT_PRICE">ORDER_MODIFY_SETTLEMENT_PRICE</option>
				<option value="ORDER_CANCEL">ORDER_CANCEL</option>
				<option value="RETRANSMISSION">RETRANSMISSION</option>
			</select>
		</td>
	</tr>
	<tr id="orderItemMetaIdTr" style="display: none;">
		<td>子子项ID</td>
		<td>
		<input type="text" id="orderItemMetaId" name="orderItemMetaId" value="" disabled="disabled" style="width: 300px;"/>
		逗号分隔
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="生成"/></td>
	</tr>
</table>
</form>
</body>
</html>