
var selectedReceiverId = null, selectedPersonId = null;
//新增地址弹出框
function showAddAddressDialg() {
	$("#addAddressDialg").attr("href", basePath + "usrReceivers/doAddAddress.do");	
	$("#addAddressDialg").reload({userId:userId});
	$("#addAddressDialg").openDialog();
}
//新增地址
function addAddress() {
	addOrUpdateAddress(basePath + "/usrReceivers/saveAddress.do", "addAddressDialg");
}
//修改地址弹出框
function showEditAddressDialg(receiverId, flag) {
	$("#editAddressDialg").attr("href", basePath + "usrReceivers/doUpdateAddress.do");	
	//需要修改ordPerson
	if (flag) {
		$("#editAddressDialg").reload({receiverId:receiverId, personId:Number(selectedPersonId), orderId:orderId});
	} else {
		$("#editAddressDialg").reload({receiverId:receiverId});
	}
	$("#editAddressDialg").openDialog();
}
//修改地址
function editAddress() {
	addOrUpdateAddress(basePath + "/usrReceivers/updateAddress.do", "editAddressDialg");
}
//新增或修改地址
function addOrUpdateAddress(url, dialgName) {
	if ($.trim($("#" + dialgName).find("#address").val()) == "") {
		alert("\u8bf7\u8f93\u5165\u5730\u5740");
		return false;
	}
	if ($.trim($("#" + dialgName).find("#receiverName").val()) == "") {
		alert("\u8bf7\u8f93\u5165\u8054\u7cfb\u4eba");
		return false;
	}
	if ($.trim($("#" + dialgName).find("#mobileNumber").val()) == "") {
		alert("\u8bf7\u8f93\u5165\u624b\u673a\u53f7\u7801");
		return false;
	}
	var m = $("#" + dialgName).find("#insertUpdateAddressForm").getForm({prefix:""});
	$.ajax({type:"POST", url:url, data:m, success:function (result) {
		var res = eval(result);
		if (res) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("select").each(function () {
				$(this).show();
			});
			$("#" + dialgName).hide();
			$("#[id^=addressDiv]").each(function(){
				var index=$(this).attr("idx");				
				$(this).reload({"userId":userId,"selectedReceiverId":selectedReceiverId,"index":index});				
			});
		} else {
			alert("\u64cd\u4f5c\u5931\u8d25!");
		}
	}});
}
//新增发票弹出框
//function showAddInvoiceDialg() {
//	$("#addInvoiceDialg").attr("href", basePath + "ord/doAddInvoice.do");
//	$("#addInvoiceDialg").reload({"orderId":orderId});
//	$("#addInvoiceDialg").openDialog();
//}
//新增发票
//function addInvoice() {
//	addOrUpdateInvoice(basePath + "ord/saveInvoice.do", "addInvoiceDialg");
//}
//修改发票弹出框
/*function showEditInvoiceDialg(inoviceId) {
	$("#editInvoiceDialg").attr("href", basePath + "ord/doUpdateInvoice.do");
	$("#editInvoiceDialg").reload({"ordInvoice.invoiceId":inoviceId});
	$("#editInvoiceDialg").openDialog();
}
//修改发票
function editInvoice() {
	addOrUpdateInvoice(basePath + "ord/updateInvoice.do", "editInvoiceDialg");
}
//新增或修改发票
function addOrUpdateInvoice(url, dialgName) {
	if ($.trim($("#" + dialgName).find("#title").val()) == "") {
		alert("\u8bf7\u8f93\u5165\u62ac\u5934!");
		return false;
	}
	*金额不显示,直接以订单的金额为准
	var amountVal = $.trim($("#" + dialgName).find("#amount").val());
	if (amountVal == "") {
		alert("\u8bf7\u8f93\u5165\u91d1\u989d!");
		return false;
	}
	
	if(isNaN(amountVal)) {
		alert("请输入金额!");
		return false;
	}
	
	var m = $("#" + dialgName).find("#addOrUpdateInvoiceForm").getForm({prefix:""});
	$.ajax({type:"POST", url:url, data:m, timeout:3000, success:function (result) {
		var res = eval("("+result+")");
		if (res.success) {
			alert("\u64cd\u4f5c\u6210\u529f!");
			$("select").each(function () {
				$(this).show();
			});
			$("#" + dialgName).hide();
			$("#invoiceDiv").reload({"orderId":orderId});
		} else {
			alert(res.msg);
		}
	}});
}
*/
//发票和地址删除
function deleteOperator(url, data, divName, paramData) {
	$.ajax({type:"POST", url:url, data:data, success:function (result) {
		var res = eval("("+result+")");
		if (res) {
		    alert("\u64cd\u4f5c\u6210\u529f!");			
			$("#" + divName).reload(paramData);
		} else {
			alert(res.msg);
		}
	}});
}
//删除发票
function deleteInvoice(invoiceId) {
	deleteOperator(basePath + "ord/doDeleteInvoice.do", {"ordInvoice.invoiceId":invoiceId}, "invoiceDiv", {"orderId":orderId});
}

function cancelInvoice(invoiceId){
	deleteOperator(basePath + "ord/doCancelInvoice.do", {"ordInvoice.invoiceId":invoiceId}, "invoiceDiv", {"orderId":orderId});
}
//发票申请红冲
function reqRedInvoice(invoiceId){
	deleteOperator(basePath + "ord/doReqRedInvoice.do", {"ordInvoice.invoiceId":invoiceId}, "invoiceDiv", {"orderId":orderId});
}


//删除地址
function deleteAddress(receiverId) {
	deleteOperator(basePath + "usrReceivers/deleteAddress.do", {"receiverId":receiverId}, "addressDiv", {"userId":userId, "selectedReceiverId":selectedReceiverId});
}
//是否需要发票
function needFaPiao(obj) {
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
		if (!isPhysical) {
			selectedReceiverId = null;
			selectedPersonId = null;
		}
	}
}

