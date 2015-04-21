//关闭层
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
	$("#"+divName).closeDialog();
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
function showDetailDiv(divName, orderId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"orderId":orderId});
}

//显示弹出层
function showDetailDivByTrackId(divName, trackId, orderId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"trackId":trackId,"orderId":orderId});
}

//显示弹出层
function showDetailDivByAmount(divName, amount,orderId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"amount":amount,"orderId":orderId});
}
//ajax发送请求操作
function httpRequest(url, param, closeFlag, divName) {
	$.ajax({type:"POST", url:url + "?random=" + Math.random(), data:param, success:function (result) {
		var res = eval("("+result+")");
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			var flag = closeFlag != null ? closeFlag : false;
			var div = divName != null ? divName : "approveDiv";
			if(workTaskId==null || workTaskId=="null" || workTaskId=="" || workTaskId==undefined){
				//关闭层
				if (flag) {
					closeDetailDiv(div);
					document.location.reload();
				} else {
					$("#"+div).reload({"orderId":orderId});
				}
			}else{
				if(res.creatorComplete=="system"){
					parent.location.reload();
				}
			}
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//修改支付等待时间
function doModifyWaitPayment(url) {
	var waitPayment = $("#waitPayment :selected").val();
	if(isNaN(parseInt(waitPayment))){
		alert("支付等待时间必须为空，不可以操作");
		return;
	}
	httpRequest(url, {"orderId":orderId, "orderDetail.waitPayment":waitPayment});
}
//删除联系人或游客记录
function doDeletePerson(personId) {
	httpRequest("doDeletePerson.do", {"orderId":orderId, "personId":personId});
}
//弹出新增联系人框
function showAddReceiverDialg() {
	var receiverId = $("#usrReceiver").val();
	$("#addReceiverDialg").attr("href", basePath + "usrReceivers/toAddReciever.do");
	$("#addReceiverDialg").reload({userId:userId});
	$("#addReceiverDialg").openDialog();
}
//新增联系人
function addReceivers() {
	var receiverId = $("#usrReceiver").val();
	var m = $("#addReceiverDialg").find("#insertUpdateReceiverForm").getForm({prefix:""});
	$.ajax({type:"POST", url:basePath + "usrReceivers/saveReceiver.do", data:m, success:function (result) {
		if (result.jsonMsg == "ok") {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#addReceiverDialg").hide();
			$("#usrReceiversList").reload({userId:userId});
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//弹出修改联系人框
function showEditReceiverDialg() {
	var receiverId = $("#usrReceiver").val();
	if (receiverId == "" || receiverId == null) {
		alert("\u8bf7\u9009\u62e9\u4e00\u4e2a\u8054\u7cfb\u4eba\u518d\u4fee\u6539");
		return false;
	}
	$("#editReceiverDialg").attr("href", basePath + "usrReceivers/toUpdateReciever.do");
	var receiverId = $("#usrReceiver").val();
	$("#editReceiverDialg").reload({receiverId:receiverId});
	$("#editReceiverDialg").openDialog();
}
//修改联系人
function editReceiver() {
	var receiverId = $("#usrReceiver").val();
	if (receiverId == "" || receiverId == null) {
		alert("\u8bf7\u9009\u62e9\u4e00\u4e2a\u8054\u7cfb\u4eba\u518d\u4fee\u6539");
		return false;
	}
	var m = $("#editReceiverDialg").find("#insertUpdateReceiverForm").getForm({prefix:""});
	$.ajax({type:"POST", dataType:"json", url:basePath + "usrReceivers/updateReceiver.do", data:m, success:function (result) {
		if (result.jsonMsg == "ok") {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#editReceiverDialg").hide();
			if($("#usrReceiversList").attr("href")!=undefined){
			$("#usrReceiversList").reload({receiverId:receiverId, userId:userId});
			}
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//保存为游客
function addVisitor() {
	var receiverId = $("#usrReceiver").val();
	if (receiverId == "" || receiverId == null) {
		alert("\u8bf7\u9009\u62e9\u4e00\u4e2a\u8054\u7cfb\u4eba\u518d\u6dfb\u52a0");
		return false;
	}
	//判断是否已在游客列表里
	for (x in existedTraveller) {
		if(receiverId == existedTraveller[x]) {
			alert("该游客已经存在!");
			return false;
		}
	}
	$.ajax({type:"POST", url:"doAddVisitor.do", data:{orderId:orderId, receiverId:receiverId, personId:contactId}, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#approveDiv").reload({"orderId":orderId});
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//保存为联系人
function addContactor() {
	var receiverId = $("#usrReceiver").val();
	if (receiverId == "" || receiverId == null) {
		alert("\u8bf7\u9009\u62e9\u4e00\u4e2a\u8054\u7cfb\u4eba\u518d\u6dfb\u52a0");
		return false;
	}
//	if(receiverId == contactReceiverId) {
//		alert("该取票人已经存在!");
//		return false;
//	}
	$.ajax({type:"POST", url:"doAddContactor.do", data:{orderId:orderId, receiverId:receiverId, personId:contactId, operatFrom:operatFrom}, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			if($("#approveDiv").attr("href")!=undefined){
				$("#approveDiv").reload({"orderId":orderId});
			}
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//取消订单
function doCancelOrder(divName) {
	var reason = $("#cancelReason").find("option:selected").text();
	if(reason==''||reason=='请选择'){
		alert("请选择废单原因");
		return false;
	}
	httpRequest("http://super.lvmama.com/super_back/ord/doOrderCancel.do", {"orderId":orderId, "orderDetail.cancelReason":reason}, true, divName);
}
//需重播
function doNeedReplay() {
	var checked = $("#redail").attr("checked");
	httpRequest("doNeedReplay.do", {"orderId":orderId, "orderDetail.redail":checked});
}
//取消需重播
function doCancelNeedReplay() {
	var checked = 'false';
	$.ajax({type:"POST", url:"doNeedReplay.do", data:{"orderId":orderId, "orderDetail.redail":checked}, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
	
	
}
//审核通过
function doApprovePass(url) {
	var isNeedInvoice = $("#isNeedInvoice").attr("checked");
	var isPhysical = $("#physical").val()=='true';
	//如果需要发票则需有发票记录
	if (isNeedInvoice) {
		if ($("input[name='invoiceRadio']").length == 0) {
			alert("\u8bf7\u65b0\u589e\u53d1\u7968\u8bb0\u5f55!");
			return false;
		}
	}
	var addressId = "";
	//需要发票或者有实体票则需选择地址
	if (isNeedInvoice || isPhysical) {
		if ($("input[name='addressId']").length == 0) {alert("eeeee");
			alert("\u8bf7\u65b0\u589e\u914d\u9001\u5730\u5740\u5e76\u4fdd\u5b58!");
			return false;
		}
	}
	httpRequest(url, {"orderId":orderId}, true);
}
//保存配送地址
function doSaveExpressAdd(flag, divName) {
	var addressId = $("input[name=addressId]:checked").val();
	httpRequest("doSaveExpressAdd.do", {"orderId":orderId, "receiverId":addressId, "personId":Number(selectedPersonId)},flag,divName);
}


function doSaveInvoiceExpressAdd(flag,divName){
	var addressId = $("input[name=invoiceAddressId]:checked").val();
	if(addressId==undefined){
		alert("未选中操作的地址");
		return;
	}
	
	var invoiceId=$("input[name=invoiceId]:checked").val();
	if(invoiceId==undefined){
		alert("未选中修改的发票");
		return;
	}
	
	httpRequest("doSaveExpressAdd.do", {"invoiceId":invoiceId,'type':'INVOICE', "receiverId":addressId, "personId":Number(selectedPersonId)},flag,divName);
}

//是否需要发票后续操作
function doNeedInvoice() {
	var isNeedInvoice = $("#isNeedInvoice").attr("checked");
	$.ajax({type:"POST", url:"doNeedInvoice.do", data:{"orderId":orderId, "orderDetail.needInvoice":isNeedInvoice}, success:function (result) {
		var res = eval(result);
		if (res) {
			if (!isNeedInvoice) {
				$("#invoiceDiv").reload({"orderId":orderId});
				$("#addressDiv").reload({"userId":userId, 'selectedReceiverId': selectedReceiverId});
			}
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}