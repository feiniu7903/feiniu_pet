//发票和地址删除
function deleteOperator(url, data, divName) {
	$.ajax({type:"POST", url:url, data:data, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#" + divName).reload();
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//新增或修改地址
function addOrUpdateAddress(url) {
	var receiverId = $.trim($("#receiverId").val());
	var address = $.trim($("#address").val());
	var receiverName = $.trim($("#receiverName").val());
	var mobileNum = $.trim($("#mobileNumber").val());
	var postCode = $.trim($("#postCode").val());
	if (address == "") {
		alert("\u8bf7\u8f93\u5165\u5730\u5740");
		return false;
	}
	if (receiverName == "") {
		alert("\u8bf7\u8f93\u5165\u8054\u7cfb\u4eba");
		return false;
	}
	if (mobileNum == "") {
		alert("\u8bf7\u8f93\u5165\u624b\u673a\u53f7\u7801");
		return false;
	}
	var m = {"usrReceivers.receiverId":receiverId, "usrReceivers.address":address, "usrReceivers.receiverName":receiverName, "usrReceivers.mobileNumber":mobileNum, "usrReceivers.postCode":postCode};
	$.ajax({type:"POST", url:url, data:m, timeout:3000, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#addressDiv").attr("href", "/usrReceivers/loadAddresses.do");
			$("#addressDiv").reload();
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//修改地址弹出框
function showEditAddress(receiverId) {
	$("#addressDiv").reload({"usrReceivers.receiverId":receiverId});
}
//删除
function deleteAddress(receiverId) {
	deleteOperator("/usrReceivers/deleteAddress.do", {"usrReceivers.receiverId":receiverId}, "addressDiv");
}
//是否需要发票
function needFaPiao(obj) {
	$.ajax({type:"GET", url:"doCheckSession.do", timeout:3000, success:function (result) {
		var res = eval(result);
		if (!res) {
			alert("用户超时,请重新登录!");
			return false;
		}
	}});

	if ($(obj).attr("checked") == true) {
		$("#invoiceDiv").show();
		if (!isPhysical) {
			$("#addressDiv").show();
			$("#addressBtns").show();
		}
	} else {
		$("#invoiceDiv").hide();
		if (!isPhysical) {
			$("#addressDiv").hide();
			$("#addressBtns").hide();
		}
	}
}
//修改发票弹出框
function showEditInvoice(inoviceId) {
	$("#invoiceDiv").reload({"ordInvoice.invoiceId":inoviceId});
}
//新增或修改发票
function addOrUpdateInvoice(url) {
	var invoiceId = $("#invoiceDL").find("#invoiceId").val();
	var title = $.trim($("#invoiceDL").find("#title").val());
	var detail = $.trim($("#invoiceDL").find("#detail").val());
	var amount = $.trim($("#invoiceDL").find("#amount").val());
	var memo = $.trim($("#invoiceDL").find("#memo").val());
	if (title == "") {
		alert("\u8bf7\u8f93\u5165\u62ac\u5934!");
		return false;
	}
	if (amount == "") {
		alert("\u8bf7\u8f93\u5165\u91d1\u989d!");
		return false;
	}
	if(isNaN(amount)) {
		alert("请输入金额!");
		return false;
	}
	$.ajax({type:"POST", url:url, data:{"ordInvoice.invoiceId":invoiceId, "ordInvoice.title":title, "ordInvoice.detail":detail, "ordInvoice.amountYuan":amount, "ordInvoice.memo":memo}, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("#invoiceDiv").reload();
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//删除发票
function deleteInvoice(invoiceId) {
	deleteOperator("doDeleteInvoice.do", {"ordInvoice.invoiceId":invoiceId}, "invoiceDiv");
}

