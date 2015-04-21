$(function() {
	accounting.settings = {
			currency: {
				symbol : "$",   // default currency symbol is '$'
				format: "%v", // controls output: %s = symbol, %v = value/number (can be object: see below)
				decimal : ".",  // decimal point separator
				thousand: ",",  // thousands separator
				precision : 2   // decimal places
			},
			number: {
				precision : 0,  // default precision on numbers is 0
				thousand: ",",
				decimal : "."
			}
		};
	$("#targetId").combox("/finance/autocomplete/settlement_target.json");
	/**
	 * 销售产品名称自动查询下拉框
	 */
	$("#product").combox("/finance/autocomplete/product.json");
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplierId").combox("/finance/autocomplete/supplier.json");
	
	var cellFormater_period = function(cellVal){
		if(cellVal == 'PERMONTH'){
			return '月结';
		}else if(cellVal == 'PER_HALF_MONTH'){
			return '半月结';
		}else if(cellVal == 'PERQUARTER'){
			return '季结';
		}else if(cellVal == 'PER_WEEK'){
			return '周结';
		}else if(cellVal == 'PERORDER'){
			return '单结';
		}else{
			return '未知';
		}
	};
	var cellFormater_paymentType = function(cellVal){
		if(cellVal == 'CASH'){
			return '现金';
		}else if(cellVal == 'TRANSFER'){
			return '银行转账';
		}else{
			return '';
		}
	};
	
//	var to_pay = function(rid , type ){
//		var ids;
//		if(typeof rid == "string"){
//			ids = [rid];
//		}else{
//			ids = rid;
//		}
//		
//		var b = true;
//		$.each(ids,function(i,id){
//			if(parseFloat($("#result_table").jqGrid("getRowData", id).subtotalCosts) >= 0){
//				b = false;
//				return false;
//			}
//		});
//		if(b){
//			$.msg("无法全部使用抵扣款合并打款");
//			return false;
//		}
//		
//		var rowData = $("#result_table").jqGrid("getRowData", ids[0]);
////		if(ids.length==1 && parseFloat(rowData.subtotalCosts)<0){
////			$.msg("无法使用一个抵扣款合并打款",1500);
////			return false;
////		}
//		$("input[name=groupSettlementIds]").remove();
//    	$.get("/finance/settlement/advancedeposits/getamount/"+rowData.supplierId+"/"+rowData.currency+".json",function(data){
//    		var toPayAmount =0;
//    		var dk = 0;
//    		var break_msg  = "";
//    		var isUseAdv = false;
//    		$.each(ids,function(inx,item){
//    			var rd = $("#result_table").jqGrid("getRowData", item);
//    			if(rd.paymentStatusStr == '已打款' || rd.paymentStatusStr == '已使用'){
//    				break_msg = "选择了已打款项，无法合并打款";
//    				return false;
//    			}
//    			if(rd.isUseAdvancedeposits == "Y" ){
//    				isUseAdv = true;
//    			}
//    			if(parseFloat(rd.subtotalCosts)<0){
//    				dk += parseFloat(rd.subtotalCosts);
//    			}
//    			toPayAmount += parseFloat(rd.subtotalCosts) - parseFloat(rd.payAmount);
//    		});
//    		if(break_msg !=""){
//    			$.msg(break_msg,1500);
//    			return false;
//    		}
//    		
//    		if(data<= 0 || (typeof type =="string" && type =="BANKPAY") || toPayAmount <= 0){//线下打款
//	    		$("#bank_pay_form").resetForm();
//	    		$("#bank_pay_supplier").html(rowData.supplierName);
//	    		$("#bank_pay_type").html(rowData.paymentType);
//	    		$("#bank_pay_supplierId").val(rowData.supplierId);
//	    		$("#bank_pay_targetId").val(rowData.targetId);
//	    		$("#bank_pay_currency").html(rowData.currencyName);
//	    		$("#bank_pay_amount").val(toPayAmount.toFixed(2));
//	    		$("#bank_pay_amount").attr("to_pay",toPayAmount.toFixed(2));
//	    		$.each(ids,function(inx,item){
//	    			$("#bank_pay_supplierId").after('<input type="hidden" name="groupSettlementIds" value = "'+item+'"/>');
//	    		});
//	    		dk = 0 - dk;
//	    		if(dk>0){
//	    			$("#bank_pay_dk").html(dk.toFixed(2));
//	    			$("#bank_pay_dk_li").show();
//	    		}
//	        	art.dialog({
//	        		id:"bank-pay-dialog",
//	        		fixed: true,
//	        		title: "打款",
//	        	    content: document.getElementById("bank_pay_div"),
//	        	    lock:true
//	        	});
//	        	if(ids.length>1 || toPayAmount <= 0){
//	        		$("#bank_pay_amount").attr("readonly","readonly");
//	        	}else{
//	        		$("#bank_pay_amount").removeAttr("readonly");
//	        	}
//    		}else{ //预存款打款
//	    		$("#advancedepositsBal_pay_form").resetForm();
//	    		if(isUseAdv){
//	    			$("#advancedepositsBal_pay_isUseAdv").html("选择了使用预存款打款");
//	    		}else{
//	    			$("#advancedepositsBal_pay_isUseAdv").html("没有选择使用预存款打款");
//	    		}
//	    		$("#advancedepositsBal_pay_supplier").html(rowData.supplierName);
//	    		$("#advancedepositsBal").html(accounting.formatMoney(data));
//	    		$("#advancedepositsBal_pay_currency").html(rowData.currencyName);
//	    		$("#advancedepositsBal_pay_supplierId").val(rowData.supplierId);
//	    		$("#advancedepositsBal_pay_targetId").val(rowData.targetId);
//	    		$.each(ids,function(inx,item){
//	    			$("#advancedepositsBal_pay_supplierId").after('<input type="hidden" name="groupSettlementIds" value = "'+item+'"/>');
//	    		});
//	    		if(data < toPayAmount && ids.length>1 ){
//	    			art.dialog({
//			    		id:'deldk-confirm-dialog',
//			    		fixed: true,
//			    		lock:true,
//			    		title:"确认信息",
//			    	    content: '预存款不足，无法使用合并打款，确认使用线下打款？',
//			    	    cancelValue: '取消',
//			    	    cancel: true,
//			    	    okValue: '确认',
//			    	    ok: function () {
//			    	    	art.dialog.get("deldk-confirm-dialog").close();
//		    	    		to_pay(ids,"BANKPAY");
//			    	    }
//			    	});
//	    			return false;
//	    		}
//	    		var _payAmount = data > toPayAmount ? toPayAmount : data;
//	    		_payAmount = _payAmount.toFixed(2);
//	    		$("#advancedepositsBal_pay_amount").val(_payAmount);
//	    		$("#advancedepositsBal_pay_amount").attr("to_pay",_payAmount);
//	    		dk = 0 - dk;
//	    		if(dk>0){
//	    			$("#advancedepositsBal_pay_dk").html(dk.toFixed(2));
//	    			$("#advancedepositsBal_pay_dk_li").show();
//	    		}
//		    	art.dialog({
//		    		id:"advancedepositsBal-pay-dialog",
//		    		fixed: true,
//		    		title: "打款（预存款）",
//		    	    content: document.getElementById("advancedepositsBal_pay_div"),
//		    	    lock:true
//		    	});
//		    	if(ids.length>1){
//		    		$("#advancedepositsBal_pay_amount").attr("readonly","readonly");
//		    	}else{
//		    		$("#advancedepositsBal_pay_amount").removeAttr("readonly");
//		    	}
//	    	}
//    	});
//	};
	
	/**
	 * 合并打款
	 */
	$("#merge_pay").click(function(){
		var ids = $("#result_table").jqGrid('getGridParam','selarrrow');
		if( ids.length <=0 ){
			$.msg("请选择合并打款的成本项",1500);
		}else{
			var supplierId,targetId,currency;
			var subFlag = true;
			var params = "";
			$.each(ids,function(inx,item){
				params += item + ",";
				var rowData = $("#result_table").jqGrid("getRowData", item);
				if(inx != 0){
					if(rowData.supplierId != supplierId || 
							rowData.targetId != targetId ||
							rowData.currency != currency ){
						$.msg("供应商、结算对象、币种必须一致才可以使用合并打款",2000);
						subFlag = false;
						return false;
					}
				}
				if(rowData.paymentStatusStr == '已打款' || rowData.paymentStatusStr == '已使用'){
					$.msg("选择了已打款项，无法合并打款", 1000);
					subFlag = false;
    				return false;
    			}
				supplierId = rowData.supplierId;
				targetId = rowData.targetId;
				currency = rowData.currency;
			});
			if(subFlag){
//				to_pay(ids);

	    		$("#bank_pay_form").resetForm();
				$.get("/finance/group/settlement/pay.htm", {groupSettlementIds: params},
						function(data){
							art.dialog({
								id:"bank-pay-dialog",
								title: "打款",
							    content: data,
							    lock:true
							});
							$.getScript("/finance/js/group/set_settlement_pay.js");
						}
			    	);
				}
			}
	});
	
	var gridcomplete = function(){  
		$("#search_footer").show();
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];
	        var rowData = $("#result_table").jqGrid('getRowData',ids[i]);
	        var operate = "";
	        
	       if((rowData.paymentStatusStr=='未打款' || 
	    		   rowData.paymentStatusStr=='部分打款') && parseFloat(rowData.subtotalCosts)>0){
	    	   operate+="<a href='javascript:void(0);' class = 'to_pay' rowid = '" + id + "' >打款</a>";
	       }
	       
	       operate += "<a href='javascript:void(0);' class = 'detail_info' rowid = '" + id + "'>详情</a>";
	       var groupSettlementId = rowData.groupSettlementId;
    	   operate+="<a href='/finance/group/settlement/orderInfoDetail/" + groupSettlementId + ".htm'  rowid = '" + id + "' target='_blank'>查看订单</a>";
	       $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
		
	    /**
	     * 打款
	     */
	    $('.to_pay').click(function(){
	    	var rid = $(this).attr("rowid");
//	    	to_pay(rid);

    		$("#bank_pay_form").resetForm();
	    	var rd = $("#result_table").jqGrid("getRowData", rid);
			if(rd.paymentStatusStr == '已打款' || rd.paymentStatusStr == '已使用'){
				$.msg("选择了已打款项，无法合并打款", 1000);
				return false;
			}
	    	$.get("/finance/group/settlement/pay.htm", {groupSettlementIds: rid},
				function(data){
					art.dialog({
						id:"bank-pay-dialog",
						title: "打款",
					    content: data,
					    lock:true
					});
					$.getScript("/finance/js/group/set_settlement_pay.js");
				}
	    	);
	    });
	    
	    // 详情
	    $('.detail_info').click(function(){
	    	var rid = $(this).attr("rowid");
	    	$.get("/finance/group/settlement/info/" + rid + ".htm",
				function(data){
					art.dialog({
						id:"confirm-dialog",
						title: "团详细信息",
					    content: data,
					    lock:true
				});
	    	});
	    });
	};

	// 刷新总金额
//	var refreshSumPrice = function(){
//		var _form = $("#search_form");
//		var queryString = _form.formSerialize();
//		var _url = "/finance/group/settlement/settlementSumprice.json?"+queryString;
//		$.get(_url,function(data){
//			$("#subTotalCostsTotal").html(data.subTotalCostsTotal);
//			$("#payAmountTotal").html(data.payAmountTotal);
//		});
//	};
	
	$("#search_button").click(function(){
		var order_id  =$("input[name=orderId]").val();
		if(order_id != ""){
			if(!/^(\s*)(\d+)(\s*)$/.test(order_id)){
				$.msg("订单号输入错误",1500);
				return false;
			}else{
				order_id = RegExp.$2;
				$("input[name=orderId]").val(order_id);
			}
		}
		
		var _form = $("#search_form");
		_form.attr("action",_form.attr("search_action"));
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			multiselect: true,
			colNames : [ 'groupSettlementId','budgetItemName','供应商ID','currency','isUseAdvancedeposits','团号', '团收入','团利润','成本项/ID', '类别', '供应商', '结算对象','结算周期','付款方式','币种','金额','延迟时间','打款状态','打款时间','打款金额','产品经理','备注','销售产品子类型','操作'],
			colModel : [  {
				name : 'groupSettlementId',
				index : 'GROUPSETTLEMENTID',
				align:"center",
				sorttype: "string",
				hidden:true
			}, {
				name : 'budgetItemName',
				index : 'budgetItemName',
				hidden:true
			}, {
				name : 'supplierId',
				index : 'SUPPLIERID',
				align:"center",
				sorttype: "string"
			}, {
				name : 'currency',
				index : 'CURRENCY',
				align:"center",
				sorttype: "string",
				hidden:true
			},{
				name : 'isUseAdvancedeposits',
				index : 'IS_USE_ADVANCEDEPOSITS',
				align:"center",
				sorttype: "string",
				hidden:true
			}, {
				name : 'travelGroupCode',
				index : 'TRAVELGROUPCODE',
				align:"center",
				sorttype: "string"
			},{
				name : 'actIncoming',
				index : 'ACTINCOMING',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				sortable:false
			},{
				name : 'actProfit',
				index : 'ACTPROFIT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				sortable:false
			}, {
				name : 'budgetItemNameId',
				index : 'BUDGETITEMNAME',
				align:"center",
				sorttype: "string",
				sortable:false
			}, {
				name : 'typeStr',
				index : 'PRDBRANCHNAME',
				align:"center",
				sorttype: "string"
			}, {
				name : 'supplierName',
				index : 'SUPPLIERID',
				align:"center",
				sorttype: "string",
				sortable:false
			},{
				name : 'targetName',
				index : 'TARGETID',
				align:"center",
				sorttype: "string",
				sortable:false
			},{
				name : 'settlementPeriod',
				index : 'SETTLEMENTPERIOD',
				align:"center",
				sorttype: "string",
				formatter:cellFormater_period
			},{
				name : 'paymentType',
				index : 'PAYMENTTYPE',
				align:"center",
				sorttype: "string",
				formatter:cellFormater_paymentType
			},{
				name : 'currencyName',
				index : 'CURRENCY',
				align:"center",
				sorttype: "string"
			},{
				name : 'subtotalCosts',
				index : 'SUBTOTALCOSTS',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
			},{
				name : 'settlementDateStr',
				index : 'SETTLEMENTDATE',
				align:"center",
				sorttype: "string"
			},{
				name : 'paymentStatusStr',
				index : 'PAYMENTSTATUS',
				align:"center",
				sorttype: "string"
			},{
				name : 'paymentTimeStr',
				index : 'paymentTime',
				width: 310,
				align:"center",
				sortable:false
			},{
				name : 'payAmount',
				index : 'PAYAMOUNT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
			},{
				name : 'userName',
				index : 'USERNAME',
				align:"center",
				sorttype: "string"
			},{
				name : 'remark',
				index : 'REMARK',
				align:"center",
				sorttype: "string",
				sortable:false
			},{
				name : 'routeType',
				index : 'routeType',
				hidden:true
			},{
				name:'operate',
				index:'Id',
				align:"center",
				width: 310,
				sortable:false
			}],
	        gridComplete:gridcomplete
		});
	});
	
	$.validator.addMethod("advancedepositsBal_pay_amount", function(value, element) {
		   var aint = parseFloat(value);	
		   var sa =  parseFloat($("#advancedepositsBal").html());
		    return  aint > 0 && aint <= sa ;
		  }, "使用金额不能大于预存款余额");   
	$.validator.addMethod("max_pay_amount", function(value, element) {
		   var aint = parseFloat(value);	
		   var sa =  parseFloat($(element).attr("to_pay"));
		    return  aint <= sa;
		  }, "打款金额过大");  
	$.validator.addMethod("amountfloat2", function(value, element) {
		if($(element).attr("readonly") == "readonly"){
			return true;
		}
		var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
	    return reg.test(value) && parseFloat(value) > 0;   
	  }, "请输入一个正数，最多只能有两位小数");   
	
	$.validator.addMethod("checkRate", function(value, element){
		var reg = new RegExp("^[0-9]+(.[0-9]{1,6})?$", "g");
	    return reg.test(value) && parseFloat(value) > 0;
	}, "请输入一个正数，最多只能有六位小数");
	
	/**
	 * 线下打款表单验证
	 */
	$("#bank_pay_form").validate({
		rules: {
			amount: {
				required:true,
				amountfloat2:true,
				max_pay_amount:true
			},
			operatetimes:{
				required:true
			},
			rate:{
				required:true,
				checkRate: true
			}
		},
		messages: {
			amount: {
				required:"请输入打款金额"
			},
			operatetimes:{
				required:"请选择打款时间"
			},
			rate:{
				required:"请输入汇率"
			}
		}
	});
	
	/**
	 * 线下打款提交表单
	 */
	$('#bank_pay_form').ajaxForm({
		type: 'post',
		url:  '/finance/group/settlement/bankpay.json',
		success: function(data) {
			art.dialog.get("bank-pay-dialog").close();
			if(typeof data.res != "undefined"  && data.res == -1){
				$.msg("无法打款，存在其他币种的发票未回收！",2500);
			}else{
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("打款成功！",1500);
			}
		}
	});
	
	/**
	 * 预存打款表单验证
	 */
	$("#advancedepositsBal_pay_form").validate({
		rules: {
			amount: {
				required:true,
				amountfloat:true,
				max_pay_amount:true
			},
			rate:{
				required:true,
				checkRate: true
			}
		},
		messages: {
			amount: {
				required:"请输入打款金额"
			},
			rate:{
				required:"请输入汇率"
			}
		}
	});
	
	/**
	 * 预存款打款提交表单
	 */
	$('#advancedepositsBal_pay_form').ajaxForm({
		type: 'post',
		url:  '/finance/group/settlement/advpay.json',
		success: function(data) {
			art.dialog.get("advancedepositsBal-pay-dialog").close();
			if(typeof data.res != "undefined"  && data.res == -1){
				$.msg("无法打款，存在其他币种的发票未回收！",2500);
			}else{
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("打款成功！",1500);
			}
		}
	});
	
	/**
	 * 使用线下打款
	 */
	$("#bankpay_button").click(function(){
		art.dialog.get("advancedepositsBal-pay-dialog").close();
		var ids = new Array();
		$.each($("input[name=groupSettlementIds]"), function(i,n){
			ids.push($(n).val());
		});
		to_pay(ids,"BANKPAY");
	});
	
	//删除抵扣款
	$("#del_dk").click(function(){
		var ids = $("#result_table").jqGrid('getGridParam','selarrrow');
		if( ids.length <=0 ){
			$.msg("请选择抵扣款",1500);
		}else{
			var subFlag = true;
			$.each(ids,function(inx,item){
				var rowData = $("#result_table").jqGrid("getRowData", item);
				if(rowData.subtotalCosts >=0 ){
					$.msg("选择了非抵扣款项",2000);
					subFlag = false;
					return false;
				}
				if(rowData.paymentStatus == '已使用'){
					$.msg("抵扣款已使用，无法删除",2000);
					subFlag = false;
					return false;
				}
			});
			
			if(subFlag){
				var params = "";
				$.each(ids,function(inx,item){
					params+=item+",";
				});
				params = params.substring(0, params.length-1);
				art.dialog({
		    		id:'deldk-confirm-dialog',
		    		fixed: true,
		    		lock:true,
		    		title:"确认信息",
		    	    content: '确认要删除么？',
		    	    cancelValue: '取消',
		    	    cancel: true,
		    	    okValue: '确认',
		    	    ok: function () {
		    	    	$.post("/finance/group/settlement/deldk.json", {ids: params},
								function(data){
				    	    		art.dialog.get("deldk-confirm-dialog").close();
				    				$("#result_table").GridUnload();
				    				$("#search_footer").hide();
				    				$.msg("抵扣款删除成功！",1500);
						});
		    	    	return false;
		    	    }
		    	});
			}
		}
	});
	
	$("#export_button").click(function(){
		var order_id  =$("input[name=orderId]").val();
		if(order_id != ""){
			if(!/^(\s*)(\d+)(\s*)$/.test(order_id)){
				$.msg("订单号输入错误",1500);
				return false;
			}else{
				order_id = RegExp.$2;
				$("input[name=orderId]").val(order_id);
			}
		}
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
});