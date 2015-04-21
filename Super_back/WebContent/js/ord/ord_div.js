//显示弹出层
function showDetaiPaylDiv(divName, orderId,payTotal,paymentStatus,accountpay) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"orderId":orderId,"payTotal":payTotal,"paymentStatus":paymentStatus,"accountpay":accountpay});
}

//显示弹出层
function showDetaiSmsDiv(divName, orderId,mobileNumber,smsType,returnUrl) {
	var checkBoxName =document.getElementsByName("checkBoxName");
	var checkValue="";
	for(var i=0;i<checkBoxName.length;i++){
		if(checkBoxName[i].checked){
			checkValue=checkValue+checkBoxName[i].value+",";
		}
	}
	if(mobileNumber==""&&checkValue==""){
		alert("请至少选择一个订单项进行重发短信！");
		return;
	}else{
		document.getElementById(divName).style.display = "block";
		document.getElementById("bg").style.display = "block";
		//请求数据,重新载入层
		$("#" + divName).reload({"ordOrderId":orderId,"mobileNumber":mobileNumber,"checkValue":checkValue,"smsType":smsType,"returnUrl":returnUrl});
	}
}
//显示弹出层  支付信息-合并支付
function showDetaiMergePaylDiv(divName, orderId,paymentTradeNo,gatewayTradeNo,paymentGateway) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"orderId":orderId,"paymentTradeNo":paymentTradeNo,"gatewayTradeNo":gatewayTradeNo,"paymentGateway":paymentGateway});
}
//需重播
function doRestoreOrder(path,orderId) {
	httpRequestResult(path, {"orderId":orderId},"Y","historyInventoryDiv");
}
function httpRequestResult(url, param, closeFlag, divName){
	//ajax发送请求操作
		$.ajax({type:"POST",url:url + "?random=" + Math.random(), data:param, success:function (data) {
			var result = eval('('+data +')')
			if("false"==result.status){
				alert("修改失败");
			}else{
				alert(result.status);
				var flag = closeFlag != null ? closeFlag : false;
				var div = divName != null ? divName : "approveDiv";
				//关闭层
				if (flag) {
					closeDetailDiv(div);
					document.location.reload();
				} else {
					$("#"+div).reload({"orderId":orderId});
				}	
			}
		}});
}