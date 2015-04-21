<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
function checkAndSubmitRefund(){
	if(!(/^[0-9]+(.[0-9]{0,2})?$/.test($.trim($("#form1 #refundAmount").val())))){
		$("#form1 #refundAmount").focus();
		alert("退款金额只能为有两位小数的正数");
		return false;
	}
	if(!(/^[0-9]+(.[0-9]{0,2})?$/.test($.trim($("#form1 #factorage").val())))){
		$("#form1 #factorage").focus();
		alert("手续费只能为有两位小数的正数");
		return false;
	}
	if($.trim($("#form1 #remark").val()) == ""){
		$("#form1 #remark").focus();
		alert("退款备注");
		return false;
	}
	$("#form1 #checkAndSubmitRefund_btn").attr('disabled','disabled');
	
	$.post($("#form1").attr("action"),
			$("#form1").serialize(),
			function(dt){
				var data=dt;
				if(data.success){
					alert("操作成功");
				}else{
					alert(data.msg);
				}
				parent.location.reload(true);
			}
	);
}
</script>
</head>
<body>
<form id='form1' method='post' action='${basePath}/distribution/saveOrUpdateDistributionOrderRefund.do'>
	<table class="p_table"> 
		<tbody>
			<tr>
				<td>订单ID：</td>
				<td><input type="text" name="partnerOrderId" value="${partnerOrderId}" disabled="disabled" style="width: 180px;"/>
				<input type="hidden" id="partnerOrderId" name="partnerOrderId" value="${partnerOrderId}"/>
	            <input type="hidden" id="distributorCode" name="refund.distributorCode" value="${distributorCode}"/>
	            <input type="hidden" id="distributorKey" name="refund.distributorKey" value="${distributorKey}"/>
	            </td>
			</tr>
			<tr>
				<td>退款金额：</td>
				<td><input type="text" id="refundAmount" name="refundAmountYuan" value="0"/></td>
			</tr>
			<tr>
				<td>手续费：</td>
				<td><input type="text" id="factorage" name="factorageYuan" value="0"/></td>
			</tr>
			<tr>
				<td >退款备注：</td>
				<td colspan="3">
				<textarea id="remark"  style="width: 180px;height: 100px;" name="refund.remark"></textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<p class="tc mt20">
		<input type="button" value="保存" id="checkAndSubmitRefund_btn" onclick="checkAndSubmitRefund();" class="btn btn-small w6" />
	</p>
</form>
</body>
</html>
