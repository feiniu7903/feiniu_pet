
function onlinepay(){
	$.md({modal:".online_out_box"});
	//alert(document.submit_form);
	document.submit_form.submit(); 
}

function closePay(){
$(".online_out_box").css({"display":"none"}); 
}

function bankpay(payname){
	var productID = $("#productID").val();
	var opsId = $("#opsId").val();
	var advOrderId = $("#advOrderId").val();
	var dataMember = {productID:productID,payname:payname,opsId:opsId,advOrderId:advOrderId,operateType:"checkpayMethod"};
	$.ajax({type:"POST", url:"/buy/bankAction!getPayType.do", data:dataMember, dataType:"json", success:function (data) {
			if (data.isReturn == "N") {
				//var payInfoDvi=document.getElementById("payInfo");
				//payInfoDvi.style.display = "block";
				alert(data.msgInfo);
			} else {				
				if(data.methodName == "BILL99"){
					var formbill = document.submit_form_bill99;
					formbill.inputCharset.value=data.inputCharset;
					formbill.bgUrl.value=data.bgUrl;
					formbill.pageUrl.value=data.pageUrl;
					formbill.version.value=data.version;
					formbill.language.value=data.language;
					formbill.signType.value=data.signType;
					formbill.signMsg.value=data.signMsg;
					formbill.merchantAcctId.value=data.merchantAcctId;
					formbill.payerName.value=data.payerName;
					formbill.payerContactType.value=data.payerContactType;
					formbill.payerContact.value=data.payerContact;
					formbill.orderId.value=data.orderId;
					formbill.orderAmount.value=data.orderAmount;
					formbill.orderTime.value=data.orderTime;
					formbill.productName.value=data.productName;
					formbill.productNum.value=data.productNum;
					formbill.productId.value=data.productId;
					formbill.productDesc.value=data.productDesc;
					formbill.ext1.value=data.ext1;
					formbill.ext2.value=data.ext2;
					formbill.payType.value=data.payType;
					formbill.bankId.value=data.bankId;
					formbill.redoFlag.value=data.redoFlag;
					formbill.pid.value=data.pid;
					$.md({modal:".online_out_box"});
					document.submit_form_bill99.submit();
				}else if(data.methodName == "CHINAPAY"){
					var formchinapay = document.submit_form_chinapay;
					formchinapay.MerId.value=data.MerId;
					formchinapay.OrdId.value=data.OrdId;
					formchinapay.TransAmt.value=data.TransAmt;
					formchinapay.CuryId.value=data.CuryId;
					formchinapay.TransDate.value=data.TransDate;
					formchinapay.TransType.value=data.TransType;
					formchinapay.Version.value=data.Version;
					formchinapay.BgRetUrl.value=data.BgRetUrl;
					formchinapay.PageRetUrl.value=data.PageRetUrl;
					formchinapay.Priv1.value=data.Priv1;
					formchinapay.ChkValue.value=data.ChkValue;
					$.md({modal:".online_out_box"});
					document.getElementById("submit_chinapay").submit();
				}else if(data.methodName == "ALIPAY"){
					var formalipay = document.submit_form_alipay;
					formalipay.notify_url.value=data.notify_url;
					formalipay.out_trade_no.value=data.out_trade_no;
					formalipay.partner.value=data.partner;
					formalipay.payment_type.value=data.payment_type;
					formalipay.seller_email.value=data.seller_email;
					formalipay.service.value=data.service;
					formalipay.sign.value=data.sign;
					formalipay.sign_type.value=data.sign_type;
					//formalipay.subject.value=data.subject;
					formalipay.total_fee.value=data.total_fee;
					formalipay.return_url.value=data.return_url;
					//document.getElementById("payInfo").innerHTML = data.return_url;
					formalipay.defaultbank.value=data.defaultbank;
					$.md({modal:".online_out_box"});
					document.getElementById("submit_alipay").submit();
				}
			}
		}});
}
function onlinepayNew(formName){
	$.md({modal:".online_out_box"});
	document.getElementsByName(formName).submit(); 
}