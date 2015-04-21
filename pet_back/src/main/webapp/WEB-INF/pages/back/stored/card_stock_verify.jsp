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
$(function() {
		$("input[name='paymentTime']" ).datepicker({dateFormat:'yy-mm-dd'});
});
//关闭层
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
}
function showActiveByStockDiv(divName,stockId) {
	var actualReceived = $("input[name='actualReceived']").val();
	var paymentUnit = $("input[name='paymentUnit']").val();
	var paymentTime = $("input[name='paymentTime']").val();
	var paymentType = $("select[name='paymentType']").val();
		 $.ajax( {
			type : "POST",
			dataType : "json",
			data : {stockId:stockId,paymentUnit:paymentUnit,paymentTime:paymentTime,paymentType:paymentType,actualReceived:actualReceived},
			url : "<%=basePath%>stored/activeCardByOutStockId.do",
			success : function(data){
				if(data.flag){
					alert("该出库单内所有储值卡已经激活，请及时核实!");
				}
			}
		});
	closeDetailDiv(divName);
   }
</script>
	<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>

	<body>
		<div class="orderpoptit">
			<strong>储值卡出库单激活</strong>
		</div>

		<div class="orderpopmain">
			<s:if test="conform">
				<div class="popbox">
					<strong>此次激活的出库单中明细如下，请最后确认</strong>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
						<tbody>
							<tr bgcolor="#f4f4f4" align="center">
								<td width="10%">
									面值
								</td>
								<td width="10%">
									数量
								</td>
								<td width="8%">
									总金额
								</td>
							</tr>
							<s:if test="activeCardStatisticsList != null">
								<s:set var="size" value="activeCardStatisticsList.size"></s:set>
								<s:iterator value="activeCardStatisticsList" var="statistics" status="index" >
									<s:if test="%{#index.count != #size}">
										<tr bgcolor="#ffffff">
											<td>
												${statistics.amount/100}
											</td>
											<td>
												${statistics.totalCount}
											</td>
											<td>
												${statistics.totalAmount/100}
											</td>
										</tr>
									</s:if>
									<s:if test="%{#index.count == #size}">
										<tr bgcolor="#ffffff">
											<td>总计</td>
											<td><s:property value="activeCardStatisticsList[#size-1].totalTotalCount"/></td>
											<td><s:property value="activeCardStatisticsList[#size-1].totalTotalAmount / 100"/></td>
										</tr>
									</s:if>
								</s:iterator>
							</s:if>
						</tbody>
					</table>
				</div>
				
				<div class="mrtit3">
						<table width="80%" border="0" style="font-size: 12;">
							<tr>
								<td width="10%">实收金额：<input name="actualReceived" type="text" value="${actualReceived}"/> <s:property value="confirm" /></td>
							</tr>
							<tr>
								<td width="10%">付款单位：<input name="paymentUnit" type="text" value="${paymentUnit}"/></td>
							</tr>
							<tr>
								<td width="10%">付款时间：<input name="paymentTime" type="text" value="<s:date name="paymentTime" format="yyyy-MM-dd"/>" /></td>
							</tr>
							<tr>
								<td width="60%">收款方式：<s:select  list="#{'现金支付':'现金支付','银行转账':'银行转账'}" name="paymentType"></s:select></td>
							</tr>
							<tr>
								<td colspan="4"></td>
								<td align="right">
									<input type="button" onclick="showActiveByStockDiv('cardStockActiveDiv',${stockId});" value="激活" class="right-button08" />
								</td>
							</tr>
						</table>
				</div>
			</s:if>
			<s:else>
				<div class="mrtit3" style="font-size:14px">
					抱歉，该出库单不能执行激活操作，
					<br/>请核实所要激活的出库单必须同时符合以下三个条件：
					<br/>1、该出库单内所有卡库存状态为出库
					<br/>2、该出库单内所有卡常规状态为正常
					<br/>3、该出库单内所有卡激活状态为未激活!
				</div>
			</s:else>
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;">
			</div>
			<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" name="btnCardStockActiveDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('cardStockActiveDiv');">
			</p>
		</div>
	</body>
</html>
