<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head></head>
<script language="javascript">
function showResultDiv(divName,stockId) {
	var stockId = $("input[name='cardStock.stockId']").val();
	var customer = $("input[name='cardStock.customer']").val();
	var accepter = $("input[name='cardStock.accepter']").val();
	var actualReceived = $("input[name='cardStock.actualReceived']").val();
	var paymentUnit = $("input[name='cardStock.paymentUnit']").val();
	var memo = $("textarea[name='cardStock.memo']").val();
	var paymentType = $("select[name='cardStock.paymentType']").val();
		 $.ajax( {
			type : "POST",
			dataType : "json",
			data : {'cardStock.stockId':stockId,'cardStock.customer':customer,'cardStock.accepter':accepter,'cardStock.paymentUnit':paymentUnit,
				'cardStock.paymentType':paymentType,'cardStock.actualReceived':actualReceived,'cardStock.memo':memo},
			url : "<%=basePath%>stored/outStockUpdate.do",
			success : function(data){
				if(data.jsonMsg){
					alert("修改成功");
					$("#accepter_"+stockId).html(data.cardStock.accepter);
				} else {
					alert("修改失败");
				}
			}
		});

	closeDetailDiv(divName);
}
//关闭层
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
}

</script>
	<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>

	<body>
		<div class="orderpoptit">
			<strong>储值卡出库单信息修改</strong>
		</div>

		<div class="orderpopmain">
			<div class="popbox">
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="10%">
								客户：
							</td>
							<td width="10%">
								<input type="text" name="cardStock.customer" value="${cardStock.customer}"/>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								接收人：
							</td>
							<td width="8%">
								<input type="text" name="cardStock.accepter" value="${cardStock.accepter}"/>
							</td>
						</tr>						
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								实收金额：
							</td>
							<td width="8%">
								<input type="text" name="cardStock.actualReceived" value="${cardStock.actualReceived}"/>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								付款单位：
							</td>
							<td width="8%">
								<input type="text" name="cardStock.paymentUnit" value="${cardStock.paymentUnit}"/>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								收款方式：
							</td>
							<td width="40%">
								<s:select  list="#{'现金支付':'现金支付','银行转账':'银行转账'}" name="cardStock.paymentType" ></s:select>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								备注信息：
							</td>
							<td width="40%">
								<textarea name="cardStock.memo" cols="40" rows="5">${cardStock.memo}</textarea>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td colspan="2" align="center">
								<input type="hidden" name="cardStock.stockId" value="${cardStock.stockId}"/>
								<input type="button" onclick="showResultDiv('cardStockDetailToUpdateDiv',${cardStock.stockId});" value="确定" class="right-button08" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;">
			</div>

			<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" name="btnCardStockDetailToUpdateDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('cardStockDetailToUpdateDiv');">
			</p>
		</div>
	</body>
</html>
