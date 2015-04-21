$(function(){
	$("#supplierId").combox("/finance/autocomplete/supplier.json");
	
	var gridcomplete = function(){
		var ids = $("#result_table").jqGrid("getDataIDs");
		for(var i=0; i<ids.length; i++){
			var id=ids[i];  
	        var operate = "<a href='javascript:void(0)' rowid = '" + id + "' class='add_invoice'>添加</a>" +
	        "<a href='javascript:void(0)' class='ordsettlement_log' rowid='" + id + "'>操作流水</a>";
			
	        $("#result_table").jqGrid('setRowData', id, { operate: operate });
		}
		
		// 点击[添加]超链接时触发事件
		$(".add_invoice").click(function(){
			$("#insert_form").resetForm();
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	var supplierId = parseFloat(rowData.supplierId);
	    	var supplierName = rowData.supplierName;
	    	var balance = rowData.invoiceBalance;
	    	if(balance <= 0){
				$.msg("供应商发票余额为0，不可添加发票", 1000);
				return;
			}
	    	$("#supplier").val(supplierId);
	    	$("#supplierName").val(supplierName);
	    	$("#balance").val(balance);
    		$("#invoiceCode").val("");
    		$("#invoiceAmount").val("");
    		$("#settlementCode").val("");
    		$("#groupCode").val("");
    		$("#invoiceTitle").val("");
			art.dialog({
	    		id:"add-invoice-dialog",
	    		fixed: true,
	    		title: "添加发票",
	    	    content: document.getElementById("insert_div"),
	    	    lock:true
	    	});
		});

		// 查看日志
		$('.ordsettlement_log').click(function(){
	    	var id = $(this).attr("rowid");
	    	Utils.showLog("FIN_INVOICE", id);
	    });
		
	};
	
	var queryDatas = function(){
		var id = $("#id").val();
		var invoiceId = $("#invoiceId").val();
		if((null == id || "" == id) && (null == invoiceId || "" == invoiceId)){
			$("#result_table").grid({
				former: '#search_form',
				pager: '#pagebar_div',
				colNames: ['ID','供应商ID','供应商','付款金额','发票金额','发票余额','操作'],
				colModel: [{
					name: 'invoiceId',
					index: 'invoiceId',
					hidden: true
				},{
					name: 'supplierId',
					index: 'supplierId',
					hidden: true
				},{
					name: 'supplierName',
					index: 'supplierName',
					align: 'center',
					sorttype: 'string',
					width: 200
				},{
					name: 'payAmount',
					index: 'payAmount',
					align: 'center',
					sorttype: 'float',
					formatter:'number',
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width: 80
				},{
					name: 'invoiceAmount',
					index: 'invoiceAmount',
					align: 'center',
					sorttype: 'float',
					formatter:'number',
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width: 80
				},{
					name: 'invoiceBalance',
					index: 'invoiceBalance',
					align: 'center',
					sorttype: 'float',
					formatter:'number',
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width: 80
				},{
					name:'operate',
					index:'Id',
					align:"center",
					sortable:false,
					width:80
				}],
		        gridComplete:gridcomplete
			});
		} else {
			$("#result_table").grid({
				former: '#search_form',
				pager: '#pagebar_div',
				colNames: ['ID','供应商ID','供应商','发票号','发票抬头','团号/结算单号','发票金额'],
				colModel: [{
					name: 'invoiceId',
					index: 'invoiceId',
					hidden: true
				},{
					name: 'supplierId',
					index: 'supplierId',
					hidden: true
				},{
					name: 'supplierName',
					index: 'supplierName',
					align: 'center',
					sorttype: 'string',
					width: 200
				},{
					name: 'invoiceCode',
					index: 'invoiceCode',
					align: 'center',
					width: 80
				},{
					name: 'invoiceTitle',
					index: 'invoiceTitle',
					align: 'center',
					width: 80
				},{
					name: 'code',
					index: 'code',
					align: 'center',
					width: 80
				},{
					name: 'invoiceAmount',
					index: 'invoiceAmount',
					align: 'center',
					sorttype: 'float',
					formatter:'number',
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width: 80
				}]
			});
		}
	};
	
	// 【查询】
	$("#search_button").click(queryDatas);
	
	$.validator.addMethod("invoice_amount", function(value, element) {
		var amount = parseFloat(value);	
		var balance = parseFloat($("#balance").val());
	    return  amount > 0 && amount <= balance ;
	}, "发票金额不能大于发票余额");   
	
//	$.validator.addMethod("settlementIdCheck", function(value, element) {
//		if("" != value){
//			var reg =/^[0-9,]*$/;
//			return reg.test(value);
//		}
//	    return true;   
//	}, "结算单号格式错误"); 
//	$.validator.addMethod("codeCheck", function(value, element) {
//		if("" != value){
//			var reg = /^([\u4E00-\u9FA5a-zA-Z0-9\-]{1,10}-(19|20)\d{2}(0\d{1}|1[012])(0\d{1}|[12]\d{1}|3[01])-\d{1,10},?)*$/;
//			return reg.test(value);
//		}
//	    return true;   
//	}, "团号格式错误");   
	
	$.validator.addMethod("checkCode", function(value, element) {
		var settlementCode = $("#settlementCode").val();
		var groupCode = $("#groupCode").val();
		var flag = true;
		if(groupCode == "" && settlementCode == ""){
			flag = false;
		}
	    return flag;   
	}, "请填写团号或结算单号");   
	
	
	$("#insert_form").validate({
		rules: {
			invoiceCode: {
				required: true
			},
			invoiceAmount: {
				required:true,
				amountfloat:true,
				invoice_amount: true
			},
			supplierName:{
				required:true
			},
			settlementCode:{
				required:false//,
//				settlementIdCheck: true
			},
			groupCode:{
				required:false,
				checkCode: true
			}
		},
		messages: {
			invoiceCode: {
				required: "请输入发票号"
			},
			invoiceAmount: {
				required:"请输入发票金额"
			},
			supplierName:{
				required:"请选择供应商名称"
			}
		}
	});
	
	// 增加付款发票信息
	$('#insert_form').ajaxForm({
		type: 'post',
		url:  '/finance/group/paymentInvoice/insert.json',
		success: function(data) {
			art.dialog.get("add-invoice-dialog").close();
			$.msg(data, 1000);
			// 重新执行一次查询，显示最新的数据
			setTimeout(function(){
				$("#search_button").click();
			}, 1000);
		}
	});
	
	// 关闭
	$("#close_button").click(function(){
		art.dialog.get("add-invoice-dialog").close();
	});
	
	// 【添加】
	$("#add_button").click(function(){
    	var supName = $("#supplierId").val();
    	var supplierId = $("#supplierId_val").val();
    	if(null == supName || "" == supName){
    		art.dialog({
        		id:"confirm-dialog",
        		fixed: true,
        		title: "提示",
        	    content: "请选择供应商",
        	    lock:true
        	});
    	} else {
    		// 根据供应商ID查询供应商的发票余额
    		$.post("/finance/group/paymentInvoice/queryBalance.json", {supplierId: supplierId}, function(data){
    			// 如果返回的发票余额为0，则给出提示，程序终止
    			if(data <= 0){
    				$.msg("供应商发票余额为0，不可添加发票", 1000);
    				return;
    			}

    			$("#insert_form").resetForm();
    			$("#balance").val(data);
    			$("#supplier").val(supplierId);
        		$("#supplierName").val(supName);
        		$("#invoiceCode").val("");
        		$("#invoiceAmount").val("");
        		$("#settlementCode").val("");
        		$("#groupCode").val("");
        		$("#invoiceTitle").val("");
            	
        		art.dialog({
            		id:"add-invoice-dialog",
            		fixed: true,
            		title: "添加发票",
            	    content: document.getElementById("insert_div"),
            	    lock:true
            	});
			});
    		
    		
    	}
	});
});