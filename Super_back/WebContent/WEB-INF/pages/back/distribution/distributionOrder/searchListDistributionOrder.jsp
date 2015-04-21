<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>分销管理_分销商管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		$("input[name='createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#form1 [name='distributorInfoId']").val("${distributorInfoId}");
	});
	//显示弹出层
	function addDetailDiv(partnerOrderId,distributorCode,distributorKey){
		var url = "${basePath}/distribution/searchDistributionOrder.do?partnerOrderId="+partnerOrderId+
				"&distributorCode="+distributorCode+"&distributorKey="+distributorKey;
		
		$("<iframe frameborder='0' id='addDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "退款操作页",
	        position: 'top',
	        width: 420
		}).width(400).height(300).attr("src",url);
	}
	
	
	function checkAndSubmitDistributionOrderCondition(){
		var value = $.trim($("#form1 #distributionOrderID").val());
		$("#form1 #distributionOrderID").val(value);
		if(value != ""){
			if(!(/^[A-Za-z0-9]+$/.test(value))){
				$("#form1 #distributionOrderID").focus();
				alert("分销商订单ID只能为数字和字母");
				return false;
			}
		}
		var valueOrder = $.trim($("#form1 #orderId").val());
		$("#form1 #orderId").val(valueOrder);
		if(valueOrder != ""){
			if(!(/^\+?[1-9][0-9]*$/.test(valueOrder))){
				$("#form1 #distributionOrderID").focus();
				alert("订单号ID只能为正整数");
				return false;
			}
		}
		document.form1.submit();
	}
	
</script>
</head>
<body style="height: auto;">
<form id="form1" name='form1' method='post' action='${basePath}/distribution/searchListDistributionOrder.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">订单号ID：</td>
			<td><input type="text" id="orderId" name="orderId"  value="${orderId}" class="newtext1"/></td>
			<td class="p_label">分销商订单ID：</td>
			<td><input type="text" id="distributionOrderID" name="distributionOrderID"  value="${distributionOrderID}"/></td>
			<td ><input type="button" value="查 询"
				class="right-button08 btn btn-small" id="btnDistributorQuery" onclick="checkAndSubmitDistributionOrderCondition()"/></td>
		</tr>
		<tr>
			<td class="p_label">分销商：</td>
			<td>
				<select name="distributorInfoId">
	             <option value=""></option>
	             <s:iterator value="distributorList" var="dist">
		             <option value="${dist.distributorInfoId}">${dist.distributorName }</option>
	             </s:iterator>
	            </select>
			</td>
			<td class="p_label">创建时间</td>
			<td>
			<input name="createTimeStart" value="${createTimeStart }" />~
			<input name="createTimeEnd" value="${createTimeEnd }" />
			</td>
			<td><td>
		</tr>
	</table>
</form>
<table class="p_table table_center Padding5">
	<tbody>
		<tr>
			<th>分销商流水号</th>
			<th>驴妈妈订单号</th>
			<th>分销商订单号</th>
			<th>分销商</th>
			<th>订单状态</th>
			<th>支付状态</th>
			<th>订单应付金额</th>
			<th>下单时间</th>
			<th>游玩日期</th>
			<th>操作</th>
		</tr>
		<s:iterator id="distributionOrder" var="distributionOrder" value="distributionOrderPage.items">
		     <tr>
				<td >${distributionOrder.serialNo}</td>
				<td>${distributionOrder.orderId}</td>
				<td>${distributionOrder.partnerOrderId}</td>
				<td>${distributionOrder.distributorName }</td>
				<td>${distributionOrder.ordOrder.zhOrderStatus}</td>
				<td>${distributionOrder.ordOrder.zhPaymentStatus}</td>
				<td>${distributionOrder.ordOrder.oughtPayFloat }</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#distributionOrder.createTime" /></td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#distributionOrder.ordOrder.visitTime" /></td>
				<td>
					<s:if test="#distributionOrder.isRefundSuccess">
					<a href="javascript:addDetailDiv('${distributionOrder.partnerOrderId}','${distributionOrder.distributorCode }','${distributionOrder.distributorKey }');">退款</a>
					</s:if>
				</td>
			</tr>
		</s:iterator> 
		<tr height="10">
			<td colspan="3" align="left">共有<s:property
					value="distributionOrderPage.totalResultSize" />个
			</td>
			<td colspan="7" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(distributionOrderPage)"/>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>

