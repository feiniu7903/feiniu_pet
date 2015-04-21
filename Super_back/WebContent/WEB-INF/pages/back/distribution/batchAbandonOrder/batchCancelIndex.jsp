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
<script>
	
	function checkAndSubmitBatchCancelOrder(){
		if($.trim($("#form1 #cancelReason").val()) == ""){
			$("#form1 #cancelReason").focus();
			alert("废单原因不能为空");
			return false;
		}
		
		$.post($("#form1").attr("action"),
				$("#form1").serialize(),
				function(dt){
					var data=eval("(" + dt + ")");
					$("#btnBatchCancelOrder").attr('disabled','disabled');
					if(data.result){
						alert(data.msg);
						parent.location.href="${basePath }/distribution/batch/batchList.do";
					}else{
						alert("批量废单操作出现异常");
					}
				}
		);
	}
	
	
</script>
</head>
<body>
<div>
	<form id="form1" action="${basePath }/distribution/batch/batchCancelOrders.do" method="post">
	<input type="hidden" id="batchId" name="batchId" value="${batchId}"/>
	<table class="p_table" style="width:590px">
		<tr>
			<td  width="20%">订单有效期：</td><td >${result["beginDate"] }~${result["endDate"] }</td>
		</tr>
		<tr>
			<td >订单总数量：</td><td >${result["ordCount"] }</td>
		</tr>
		<tr>
			<td >已履行数量：</td><td >${result["usedCount"] }</td>
		</tr>
		<tr>
			<td >已废单数量：</td><td >${result["canceledCount"] }</td>
		</tr>
		<tr>
			<td >可废单数量：</td><td >${result["canCancelCount"] }</td>
		</tr>
		<tr>
			<td >失败单数量：</td><td >${result["otherCount"] }</td>
		</tr>
		<tr>
			<td >废单原因：</td><td><textarea id="cancelReason" name="cancelReason" style="width: 457px; height: 101px;"></textarea></td>
		</tr>
	</table>
	<br/>
	<p>
		<input type="button" value="确定" style="width:80px; margin-left:210px;"
				class="right-button08 btn btn-small" id="btnBatchCancelOrder" onclick="checkAndSubmitBatchCancelOrder()"/>
	</p>
	
	</form>
	
</div>
</body>
</html>