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
						parent.location.reload(true);
					}else{
						alert("批量废单操作出现异常");
					}
				}
		);
	}
	
	
</script>
</head>
<body style="height: auto;">
<div>
	<form id="form1" action="${basePath }/distribution/batch/batchCancelOrder.do" method="post">
	<input type="hidden" id="orderIds" name="orderIds" value="${orderIds}"/>
	<table class="p_table form-inline">
		<tr>
			<td colspan="4">确定需要废除选择的${orderCount}笔订单吗？
			</td>
		</tr>
		<tr>
			<td colspan="4">废单原因：
			</td>
		</tr>
		<tr>
			<td colspan="4">
			<textarea name="cancelReason" id="cancelReason" style="width: 140px; height: 106px;"></textarea>
			</td>
		</tr>
	</table>
	<p>
		<input type="button" value="确定" 
				class="right-button08 btn btn-small" id="btnBatchCancelOrder" onclick="checkAndSubmitBatchCancelOrder()"/>
	</p>
	
	</form>
	
</div>
</body>
</html>