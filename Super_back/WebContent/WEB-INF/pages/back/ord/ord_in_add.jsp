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
	<head>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js"
			type="text/javascript"></script>
		<script type="text/javascript">
			var basePath = '<%=basePath%>';
			</script>
		<script type="text/javascript">
			var orderId='${orderDetail.orderId }',userId='${orderDetail.userId }';
			var selectedReceiverId2='';
		</script>
	</head>

	<body>
		<s:iterator id="person" value="orderDetail.personList">
			<s:if test="#person.personType == 'ADDRESS'">
				<script>
					selectedReceiverId = '${person.receiverId }';
					selectedPersonId = '${person.personId }';
				</script>
			</s:if>
		</s:iterator>
		<s:iterator id="invoice" value="orderDetail.invoiceList">
			<s:if test="invoice.status!='CANCEL'">
				<script>
					selectedReceiverId2='${invoice.deliveryAddress.receiverId}';
					alert(selectedReceiverId2);
				</script>
			</s:if>
		</s:iterator>
		<div class="orderpoptit">
			<strong>发票与物流：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('in_addDiv');">
			</p>
		</div>
		<!-- 发票信息 -->
		<h3 style="color:red;">2012年1月9日起，单张开票、合并开票请到订单管理-发票申请中进行操作</h3>
		<div href="<%=basePath%>ord/loadInvoices.do?editable=true" id="invoiceDiv"
			param="{'orderId':'${orderDetail.orderId }'}"></div>
		
		<!-- 地址信息 --><s:set var="selectedReceiverId2" value="-1"/>		
		<div href="<%=basePath%>usrReceivers/loadAddresses.do" id="addressDiv"
			param="{userId: '${orderDetail.userId }', 'selectedReceiverId': selectedReceiverId,'selectedReceiverId2':selectedReceiverId2}"></div>
		<!-- 地址信息 -->
		<div class="btn09" id="addressBtns">
			<input type="button" value="新增地址" class="right-button08"
				name="editPassed" onclick="showAddAddressDialg();">
			<input type="button" value="保存配送地址" class="right-button08"
				name="editPassed" onclick="doSaveExpressAdd(false, 'in_addDiv');">
			<input type="button" value="保存发票地址" class="right-button08"
				name="editPassed" onclick="doSaveInvoiceExpressAdd(false, 'in_addDiv');">
		</div>
		<div id="addAddressDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="editAddressDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="addInvoiceDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<div id="editInvoiceDialg"
			style="position: absolute; z-index: 10001; display: none;">
		</div>
		<script>
			$(document).ready(function(){
				$("#invoiceDiv").loadUrlHtml();
				$("#addressDiv").loadUrlHtml();
				$("#addAddressDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
				$("#editAddressDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
				$("#addInvoiceDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
				$("#editInvoiceDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
			});
		</script>
	</body>
</html>
