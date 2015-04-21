<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>点评管理_订单点评返现管理</title>
	<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
	<script type="text/javascript"> var path='<%=basePath%>'; </script>
<script type="text/javascript">
function showCashRefund(){
	document.getElementById("showCashRefundDiv").style.display = "block";
}

function closeOrderDiv(){
	document.getElementById("showCashRefundDiv").style.display = "none";
}

function checkCashRefund(){
 	var cashRefundValue = document.getElementById("cashRefund").value;
	var cashOrderIdValue= document.getElementById("cashOrderId").value;
	var orderIdValue = document.getElementById("orderId").value;

	if(orderIdValue===cashOrderIdValue){
	}else{
		alert('与之前查询的订单号，不一致，请查正');
		return false;
	}

	if(cashOrderIdValue.indexOf(" ")!=-1){
		alert('输入订单号中有空格，请输入正确的订单号');
		return false;
	}
	if(cashRefundValue.match('\\.')!=null){
		alert('输入有误，请输入整数。');
		return false;
 	}
	if(cashRefundValue>100){
		alert('输入的值大于一百。');
		return false;
 	}
	if(cashRefundValue<=0){
		alert('输入的值大于零。');
		return false;
 	}
	if(orderIdValue===cashOrderIdValue){
	}else{
		alert('与之前查询的订单号，不一致，请查正');
		return false;
	}

	
 	return true;
}
</script>

</head>
<body>

	<div id="table_box">
		<!--=================='ORDER_MANUAL_ADJUST'======================-->
		<div class="table_box" id=tags_content_1>
			<div class="mrtit3">
				<form id='applyForm' method='post'
					action='<%=basePath%>orderComment/toSeatchOrder.do'>
					<table width="60%" border="0" class="newfont06"
						style="font-size: 12; text-align: left;">
						<tr>
							<td>请输入订单号：<s:textarea id = "orderId" name="orderId" style="width: 198px; height: 17px;" ></s:textarea>
							</td>
							<td><input type='submit' value="查 询"
								class="right-button08"  /></td>
						</tr>
					</table>
				</form>
			</div>

			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#666666" width="100%" class="newfont06">
				<tbody>
					<tr bgcolor="#eeeeee">
						<td height="35" width="10%" align="center">订单号</td>
						<td height="35" width="10%" align="center">下单时间</td>
						<td height="35" width="10%" align="center">用户名</td>
						<td height="35" width="10%" align="center">应返现金额</td>
						<td height="35" width="10%" align="center">是否已返现</td>
						<td width="8%" align="center">操作</td>
					</tr>

					<s:if test="order">
						<s:iterator var="order" id="orderAmount">
							<tr bgcolor="#ffffff">
								<td align="center"><s:property value="order.orderId" /></td>
								<td align="center"><s:date name="order.createTime" format="yyyy-MM-dd HH:mm:ss" />
								<td align="center"><s:property value="user.userName" /></td>
								<td align="center"><s:property value="order.cashRefundYuan" /></td>
								<td align="center"><s:if test="order.isCashRefund=='false'">否</s:if>
								<s:if test="order.isCashRefund=='true'">是</s:if></td>
								<td align="center"><a href="javascript:showCashRefund()">手动返现</a></td>
							</tr>
						</s:iterator>
					</s:if>
				</tbody>
			</table>
		</div>
	</div>
	<br><br><br>
	<div id="showCashRefundDiv" style="display:none; margin-left: auto;margin-right: auto;width: 450px; background-color:gray; ">
<form id='applyForm' method='post' action="<%=basePath%>orderComment/toOrderManualAdjust.do" onsubmit="return checkCashRefund();">
	<table>
		<tr><td colspan="3" align="center"><b>手动返现</b></td></tr>
		<tr><td>返现订单号</td><td><input type="text" id="cashOrderId" name="orderAndComment.orderId"></td><td>&nbsp;</td></tr>
		<tr><td>用户名</td><td><input type="text" name="orderAndComment.userName"></td><td>&nbsp;</td></tr>
		<tr><td>返现金额</td><td><input type="text" id="cashRefund" name="orderAndComment.cashRefund"></td><td><font style="font-size: 12px;font-family: sans-serif;color: red;">请输入范围1-100的整数(超过无效)</font></td></tr>
		<tr><td><input type='submit' name="btnOrdManual" value="确定" class="right-button08" /></td>
			<td><input type='button' name='btnOrdCancel' value="取消" class="right-button08" onclick="javascript:closeOrderDiv()"/></td></tr>
	</table>
</form>
</div>
</body>
</html>
