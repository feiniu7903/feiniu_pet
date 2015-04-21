//关闭层
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
	if(divName==='approveDiv'){//关掉顺便要关掉里面的修改订单对话
		if(document.getElementById('changeOrderItemMateDiv')){
			try{
			$("#changeOrderItemMateDiv").dialog("destroy").remove();
			}catch(ex){}
		}
	}
	
}
//显示tab下信息
function Show_list_div($z, $kk) {
	for ($i = 1; $i <= $kk; $i++) {
		$div_name = "tags_content_" + $i;
		$title_name = "tags_title_" + $i;
		$object = document.getElementById($div_name);
		$objects = document.getElementById($title_name);
		if ($i == $z) {
			$object.style.display = "block";
			$objects.className = "tags_at";
		} else {
			$object.style.display = "none";
			$objects.className = "tags_none";
		}
	}
}
//显示弹出层
function showDetailDiv(divName, orderId, metaId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"orderId": orderId, "metaId": metaId});
}

//ajax发送请求操作
function httpRequest(url, param, closeFlag) {
	//ajax中GET方法调整为POST方法.
	$.ajax({type:"POST", url:url, data:param, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			var flag = closeFlag != null ? closeFlag : false;
			//关闭层
			if(flag) {
				closeDetailDiv('approveDiv');
				document.location.reload();
			}else{
				//$("#approveDiv").reload({"orderId": orderId, "metaId": metaId});
			}	
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}

//保存传备备注
function doSaveFaxMemo() {
	var faxMemo = $("#faxMemo").val();
	httpRequest("doSaveFaxMemo.do", {"orderItemMeta.orderItemMetaId": metaId, "orderItemMeta.faxMemo": faxMemo}, false);
}
//保存
function doSaveResourceStatus(res,orderId) {
	var confirmStr = "是否确认资源已经审核";
	
	if(res == 'BEFOLLOWUP'){
		confirmStr = "是否确认为待跟进";
	}
	if(!confirm(confirmStr)){
		return false;
	}
	var yearTime=document.getElementById("retentionTimeId").value;
	var retentionTime = null;
	var retentionHour = $("#retentionHour").val();
	var retentionMinute = $("#retentionMinute").val();
	retentionTime = yearTime+"-"+retentionHour+"-"+retentionMinute;
	if(retentionTime == null){
		alert("请选择资源保留时间");
		return;
	}
	if(res == "LACK"){
		$(".check_not_pass_reason").show();
		return;
	}
	/**else{
		var dateStr=yearTime.replace(/-/g,"/")+" "+retentionHour+":"+retentionMinute+":00";
		var date= new Date(Date.parse(dateStr));
		if(date.getTime()<=new Date().getTime()){
		 	alert("资源保留时间必须在当前时间之后");
			return;
		}
	}**/
	httpRequest("doSaveResourceStatus.do", {"orderItemMeta.orderId": orderId,"retentionTime": retentionTime,"orderItemMeta.orderItemMetaId": metaId, "orderItemMeta.resourceStatus": res}, true);
}

function doSaveResourceStatusAndReason(orderId){
	var yearTime=document.getElementById("retentionTimeId").value;
	var retentionTime = null;
	var retentionHour = $("#retentionHour").val();
	var retentionMinute = $("#retentionMinute").val();
	retentionTime = yearTime+"-"+retentionHour+"-"+retentionMinute;
	if(retentionTime == null){
		alert("请选择资源保留时间");
		return;
	}
	var checkedRadio=$('input:radio[name="notpassreasonradio"]:checked').val();
	var reason = '';
	//判断是否资源不通过原因为其他
	if(checkedRadio == 'OTHER'){
		var input = $("#other_reason_input");
		reason = input.val();
	}else{
		reason = checkedRadio;
	}
	httpRequest("doSaveResourceStatus.do", {"orderItemMeta.orderId": orderId, "orderItemMeta.resourceLackReason": reason,"retentionTime": retentionTime,"orderItemMeta.orderItemMetaId": metaId, "orderItemMeta.resourceStatus": "LACK"}, true);
}
