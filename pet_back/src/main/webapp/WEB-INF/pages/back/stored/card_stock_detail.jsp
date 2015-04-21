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
//关闭层
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
}
</script>
	<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>

	<body>
		<div class="orderpoptit">
			<strong>储值卡出库单信息</strong>
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
								${cardStock.customer}
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								接收人：
							</td>
							<td width="8%">
								${cardStock.accepter}
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								出库时间：
							</td>
							<td width="8%">
								<s:date name="cardStock.createTime" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								备注信息：
							</td>
							<td width="40%">
								${cardStock.memo}
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								实收金额：
							</td>
							<td width="8%">
								${cardStock.actualReceived}
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								付款单位：
							</td>
							<td width="8%">
								${cardStock.paymentUnit}
							</td>
						</tr>
						<tr height="25" bgcolor="#f4f4f4">
							<td width="8%">
								收款方式：
							</td>
							<td width="40%">
								${cardStock.paymentType}
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
				<input type="button" name="btnCardStockDetailDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('cardStockDetailDiv');">
			</p>
		</div>
	</body>
</html>
