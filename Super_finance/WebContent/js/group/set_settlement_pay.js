$(function() {
	function calculateBankPay(type){
		//应打款金额
		var settlementAmount = parseFloat($("#settlementAmount").val());
		//抵扣款余额
		var deductionAmount = parseFloat($("#deductionAmount").val());
		//预存款余额
		var advanceDepositAmount = parseFloat($("#advanceDepositAmount").val());
		//抵扣款使用金额
		var deduction_pay_amount = parseFloat($("#deduction_pay_amount").val());
		//预存款使用金额
		var advanceDeposits_pay_amount = parseFloat($("#advanceDeposits_pay_amount").val());
		if(type == 1 && deduction_pay_amount > deductionAmount){//如果抵扣款使用金额大于抵扣款余额 提示用户过大
			$.msg("抵扣款的使用金额不能超过抵扣款余额",2500);
			$("#deduction_pay_amount").val(deductionAmount);
			$("#deduction_pay_amount").focus();
			return;
		}
		if(type == 2 && advanceDeposits_pay_amount > advanceDepositAmount){//如果预存款余额使用金额大于预存款余额余额 提示用户过大
			$.msg("预存款的使用金额不能超过预存款余额",2500);
			$("#advanceDeposits_pay_amount").val(advanceDepositAmount);
			$("#advanceDeposits_pay_amount").focus();
			return;
		}
		//线下打款的实际金额等于 应打款金额 - 抵扣款使用金额 - 预存款使用金额
		var bank_pay_amount = settlementAmount - deduction_pay_amount - advanceDeposits_pay_amount;
		bank_pay_amount = bank_pay_amount < 0 ? 0 : bank_pay_amount;
		$("#bank_pay_amount").val(bank_pay_amount.toFixed(2));
		$("label.error").remove();
		$("input.error").removeClass("error");
	}
	
	function calculateDeductionPay(){
		var settlementAmount = parseFloat($("#settlementAmount").val());
		var advanceDeposits_pay_amount = parseFloat($("#advanceDeposits_pay_amount").val());
		settlementAmount = settlementAmount - advanceDeposits_pay_amount;
		settlementAmount = settlementAmount < 0 ? 0 : settlementAmount;
		var deductionAmount = parseFloat($("#deductionAmount").val());
		if(deductionAmount >= settlementAmount){
			$("#deduction_pay_amount").val(settlementAmount.toFixed(2));
		}else{
			$("#deduction_pay_amount").val(deductionAmount.toFixed(2));
		}
	}
	
	function calculateAdvanceDepositsPay(){
		var settlementAmount = parseFloat($("#settlementAmount").val());
		var deduction_pay_amount = parseFloat($("#deduction_pay_amount").val());
		settlementAmount = settlementAmount - deduction_pay_amount;
		settlementAmount = settlementAmount < 0 ? 0 : settlementAmount;
		var advanceDepositAmount = parseFloat($("#advanceDepositAmount").val());
		if(advanceDepositAmount >= settlementAmount){
			$("#advanceDeposits_pay_amount").val(settlementAmount.toFixed(2));
		}else{
			$("#advanceDeposits_pay_amount").val(advanceDepositAmount.toFixed(2));
		}
	}
	
	
	$("#deductionPay").click(function(){
		var checked = $(this).attr("checked");
		if(checked){
			calculateDeductionPay();
			$("#deductionPay_li").slideDown("slow");
		}else{
			$("#deductionPay_li").slideUp("slow");
			$("#deduction_pay_amount").val("0");
		}
		calculateBankPay(0);
	});
	$("#advanceDepositsPay").click(function(){
		var checked = $(this).attr("checked");
		if(checked){
			calculateAdvanceDepositsPay();
			$("#advanceDepositsPay_li").slideDown("slow");
		}else{
			$("#advanceDepositsPay_li").slideUp("slow");
			$("#advanceDeposits_pay_amount").val("0");
		}
		calculateBankPay(0);
	});
	
	$("#deduction_pay_amount").blur(function(){
		calculateBankPay(1);
	});
	
	$("#advanceDeposits_pay_amount").blur(function(){
		calculateBankPay(2);
	});
	
	$.validator.addMethod("amountfloat2", function(value, element) {
		var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
	    return reg.test(value);   
	  }, "请输入一个正数，最多只能有两位小数"); 
	$.validator.addMethod("bank_pay_amount", function(value, element) {
		// 抵扣款使用金额
		var deduction_pay_amount = parseFloat($("#deduction_pay_amount").val());
		// 预存款使用金额
		var advanceDeposits_pay_amount = parseFloat($("#advanceDeposits_pay_amount").val());
		// 银行打款金额
		var bank_pay_amount = parseFloat($("#bank_pay_amount").val());
		var payAmount = deduction_pay_amount+advanceDeposits_pay_amount+bank_pay_amount;
	    return payAmount > 0;  
	  }, "打款总金额不能为0"); 
	$.validator.addMethod("bank_pay_amount2", function(value, element) {
		//应打款金额
		var settlementAmount = parseFloat($("#settlementAmount").val());
		// 抵扣款使用金额
		var deduction_pay_amount = parseFloat($("#deduction_pay_amount").val());
		// 预存款使用金额
		var advanceDeposits_pay_amount = parseFloat($("#advanceDeposits_pay_amount").val());
		// 银行打款金额
		var bank_pay_amount = parseFloat($("#bank_pay_amount").val());
		var payAmount = deduction_pay_amount+advanceDeposits_pay_amount+bank_pay_amount;
	    return settlementAmount >= payAmount;  
	  }, "打款总金额不能大于应打款金额"); 
	$.validator.addMethod("ratev", function(value, element) {
		var reg = new RegExp("^[0-9]+(.[0-9]{1,6})?$", "g");
		return reg.test(value);
	  }, "格式错误(最多只能有六位小数)"); 
	/**
	 * 线下打款表单验证
	 */
	$("#bank_pay_form").validate({
		rules: {
			amountYuan: {
				required:true,
				amountfloat2:true,
				bank_pay_amount:true,
				bank_pay_amount2:true
			},
			operatetimes:{
				required:true
			},
			rate:{
				required:true,
				ratev:true
			}
		},
		messages: {
			amountYuan: {
				required:"请输入打款金额"
			},
			operatetimes:{
				required:"请选择打款时间"
			},
			rate:{
				required:"请填写汇率"
			}
		}
	});
	var submit_callback = function(data) {
//		alert(data.res);
		art.dialog.get("bank-pay-dialog").close();
//		if(data.res == -1){
//			$.msg("抵扣款使用金额超过了抵扣款余额，请重新打款！",2500);
//		}else if(data.res == -2){
//			$.msg("预存款使用金额超过了预存款余额，请重新打款！",2500);
//		}else if(data.res == 1 ){
			//刷新金额
//			var rowData = $("#result_table").jqGrid("getRowData", data.settlement.settlementId);
//			var _payedAmount = parseFloat(data.settlement.payedAmountYuan);
//			$("#result_table").jqGrid('setRowData', +data.settlement.settlementId, { settlementAmountYuan: data.settlement.settlementAmountYuan,payedAmountYuan: _payedAmount, status : data.settlement.status, statusName : data.settlement.statusName});
//			$("#result_table").setSelection(data.settlementId);
//			if(_payedAmount >= parseFloat(data.settlement.settlementAmountYuan)){
//				$("#pay_"+data.settlement.settlementId).hide();
//    	        $("#settt_"+data.settlement.settlementId).show();
//			}
		if(typeof data.res != "undefined"  && data.res == -1){
			$.msg("无法打款，存在其他币种的发票未回收！",2500);
		}else if(typeof data.res != "undefined"  && data.res == -2){
			$.msg("无法打款，预存款币种不一致！",2500);
		}else if(typeof data.res != "undefined"  && data.res == -3){
			$.msg("无法打款，抵扣款币种不一致！",2500);
		}else{
			$("#search_button").click();
			$.msg("打款成功！",1500);
		}
//			else{
//			$.msg("打款失败！",1500);
//		}
	}
	/**
	 * 线下打款提交表单
	 */
	$('#bank_pay_form').ajaxForm({
		type: 'post',
		beforeSubmit:function(){
			//应打款金额
			var settlementAmount = parseFloat($("#settlementAmount").val());
			//抵扣款使用金额
			var deduction_pay_amount = parseFloat($("#deduction_pay_amount").val());
			//预存款使用金额
			var advanceDeposits_pay_amount = parseFloat($("#advanceDeposits_pay_amount").val());
			//银行打款金额
			var bank_pay_amount = parseFloat($("#bank_pay_amount").val());
			var payAmount = deduction_pay_amount+advanceDeposits_pay_amount+bank_pay_amount;
			if(payAmount <= 0){
				$.msg("打款金额不能为0",2500);
				return false;
			}
			if(payAmount - settlementAmount > 0){
				art.dialog({
		    		id:'submit-confirm-dialog',
		    		fixed: true,
		    		lock:true,
		    		title:"确认打款么？",
		    	    content: '打款总金额大于应打款金额，确定要打款么？',
		    	    cancelValue: '取消',
		    	    cancel: true,
		    	    okValue: '确认',
		    	    ok: function () {
		    	    	$('#bank_pay_form').ajaxSubmit(function(data){submit_callback(data)});
		    	    }
		    	});
				return false;
			}else{
				return true;
			}
		},
		success: function(data){submit_callback(data)}
	});
	
});