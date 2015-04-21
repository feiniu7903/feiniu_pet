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
		$("#form1 [name='refundStatus']").val("${refundStatus}");
	});
	
	function reApplyRefund(id){
		if(!confirm("操作有延时,是否确认操作")){
			return;
		}
		$.post(
				"${basePath}/distribution/reApplyRefund.do",
				{
					refundId: id
				},
				function(data){
					var resultData = eval("(" + data + ")");
					if(resultData.result == '1'){
						alert("重新申请退款请求成功");
					}else if (resultData.result == '0'){
						alert("重新申请退款失败");
					}else if(resultData.result == '-1'){
						alert("已经退款成功,不能重复退款");
					}else{
						alert("系统操作失败，请重新操作！");
					}
					location.reload(true);
				}
			);
	}
	
	
	function checkAndSubmitDistributionOrderRefundCondition(){
		var value = $.trim($("#form1 #orderId").val());
		$("#form1 #orderId").val(value);
		if(value != ""){
			if(!(/^\+?[1-9][0-9]*$/.test(value))){
				$("#form1 #orderId").focus();
				alert("订单ID只能为正整数");
				return false;
			}
		}
		document.form1.submit();
	}
	
</script>
</head>
<body style="height: auto;">
<form id="form1" name='form1' method='post' action='${basePath}/distribution/searchDistributionOrderRefund.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">订单号ID：</td>
			<td><input type="text" id="orderId" name="orderId"  value="${orderId}" class="newtext1"/></td>
			<td class="p_label">退款状态：</td>
			<td>
			<select id="refundStatus" name="refundStatus">
			  <option value="0">请选择</option>
			  <option value="SUCCESS">成功</option>
			  <option value="FAILED">失败</option>
			</select>
			</td>
			<td ><input type="button" value="查 询" onclick="checkAndSubmitDistributionOrderRefundCondition()"
				class="right-button08 btn btn-small" id="btnDistributorQuery" /></td>
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
			<th>订单号</th>
			<th>分销商订单号</th>
			<th>分销商</th>
			<th>退款状态</th>
			<th>订单金额</th>
			<th>退款金额</th>
			<th>手续费</th>
			<th>退款时间</th>
			<th>退款备注</th>
			<th>操作</th>
		</tr>
		<s:iterator id="distributionOrderRefund" var="distributionOrderRefund" value="distributionOrderRefundPage.items">
		     <tr>
				<td>${distributionOrderRefund.orderId}</td>
				<td>${distributionOrderRefund.partnerOrderId}</td>
				<td>${distributionOrderRefund.distributorName }</td>
				<td>${distributionOrderRefund.zhRefundStatus}</td>
				<td>${distributionOrderRefund.amountFloat}</td>
				<td>${distributionOrderRefund.refundAmountFloat}</td>
				<td>${distributionOrderRefund.factorageFloat}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="#distributionOrderRefund.refundTime" /></td>
				<td>${distributionOrderRefund.remark }</td>
				<td>
					<s:if test="#distributionOrderRefund.canRefund">
					<a href="javascript:reApplyRefund('${distributionOrderRefund.distributionOrderRefundId}');">重新申请退款</a>
					</s:if>
				</td>
			</tr>
		</s:iterator> 
		<tr height="10">
			<td colspan="3" align="left">共有<s:property
					value="distributionOrderRefundPage.totalResultSize" />个
			</td>
			<td colspan="7" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(distributionOrderRefundPage)"/>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>





