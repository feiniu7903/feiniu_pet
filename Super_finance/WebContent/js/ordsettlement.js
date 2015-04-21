$(function() {

	$("#targetId").combox("/finance/autocomplete/settlement_target.json");
	$("#metaProductId").combox({
		source : "/finance/autocomplete/meta_product.json",
		minLength : 3
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
	/**
	 * 预存款打款表单验证
	 */
	$("#advancedepositsBal_pay_form").validate({
		rules: {
			payedAmount: {
				required:true,
				amountfloat:true,
				advancedepositsBal_pay_amount:true,
				max_pay_amount:true
			}
		},
		messages: {
			payedAmount: {
				required:"请输入转为预存款的金额",
				advancedepositsBal_pay_amount:"使用金额不能大于预存款余额"
			}
		}
	});
	/**
	 * 预存款打款提交表单
	 */
	$('#advancedepositsBal_pay_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/pay/1.json',
		success: function(data) {
			if(typeof data.version_error != "undefined" ){
				$.msg("打款失败，结算单已被修改，请刷新后重试！",2500);
			}else if(typeof data.res != "undefined"  && data.res == -1){
				$.msg("无法打款，存在其他币种的发票未回收！",2500);
			}else if(typeof data.res != "undefined"  && data.res == -2){
				$.msg("无法打款，打款币种与发票余额的币种不一致！",2500);
			}else{
				art.dialog.get("advancedepositsBal-pay-dialog").close();
				//刷新金额
				var rowData = $("#result_table").jqGrid("getRowData", data.ors.settlementId);
				var _payedAmount = parseFloat(rowData.payedAmount)+parseFloat(data.ors.payedAmount);
				$("#result_table").jqGrid('setRowData', data.ors.settlementId, { payedAmount: _payedAmount ,advancedepositsBal:parseFloat(rowData.advancedepositsBalCNY)-parseFloat(data.payedAmount)});
				$("#result_table").setSelection(data.ors.settlementId);
				if(_payedAmount >= parseFloat(rowData.payAmount)){
					$("#pay_"+data.ors.settlementId).hide();
					if( rowData.status == '已确认' ){
		    	        $("#settt_"+data.ors.settlementId).show();
		            }
				}
				$.msg("预存款打款成功！",1500);
			}
		}
	});
	
	
	/**
	 * 线下打款表单验证
	 */
	$("#bank_pay_form").validate({
		rules: {
			payedAmount: {
				required:true,
				amountfloat:true,
				max_pay_amount:true
			},
			operatetimes:{
				required:true
			}//,
			//serial:{
				//required:true
			//}
		},
		messages: {
			payedAmount: {
				required:"请输入打款金额"
			},
			operatetimes:{
				required:"请选择打款时间"
			}//,
			//serial:{
			//	required:"请输入流水号"
			//}
		}
	});
	
	/**
	 * 线下打款提交表单
	 */
	$('#bank_pay_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/pay/2.json',
		success: function(data) {
			art.dialog.get("bank-pay-dialog").close();
			if(typeof data.version_error != "undefined" ){
				$.msg("打款失败，结算单已被修改，请刷新后重试！",2500);
			}else if(typeof data.res != "undefined"  && data.res == -1){
				$.msg("无法打款，存在其他币种的发票未回收！",2500);
			}else{
			//刷新金额
			var rowData = $("#result_table").jqGrid("getRowData", data.settlementId);
			var _payedAmount = parseFloat(rowData.payedAmount)+parseFloat(data.payedAmount);
			$("#result_table").jqGrid('setRowData', data.settlementId, { payedAmount: _payedAmount,payStatus:1});
			$("#result_table").setSelection(data.settlementId);
			if(_payedAmount >= parseFloat(rowData.payAmount)){
				$("#pay_"+data.settlementId).hide();
				if( rowData.status == '已确认' ){
	    	        $("#settt_"+data.settlementId).show();
	            }
			}
			$.msg("打款成功！",1500);
			}
		}
	});
	
	/**
	 * 使用线下打款
	 */
	$("#bankpay_button").click(function(){
		var settlementId = $("#advancedepositsBal_pay_settlementId").val();
		art.dialog.get("advancedepositsBal-pay-dialog").close();
		var rowData = $("#result_table").jqGrid("getRowData", settlementId);
		bank_pay(rowData);
	});
	var bank_pay = function(rowData){
		$("#bank_pay_form").resetForm();
		$("#bank_pay_suppliderId").val(rowData.supplierId);
		$("#bank_pay_targetId").val(rowData.targetId);
		$("#bank_pay_settlementId").val(rowData.settlementId);
		$("#bank_pay_syncVersion").val(rowData.syncVersion);
		var toPayAmount = parseFloat(rowData.payAmount) - parseFloat(rowData.payedAmount);
		$("#bank_pay_amount").val(toPayAmount.toFixed(2));
		$("#bank_pay_amount").attr("to_pay",toPayAmount.toFixed(2));
    	art.dialog({
    		id:"bank-pay-dialog",
    		fixed: true,
    		title: "结算单打款",
    	    content: document.getElementById("bank_pay_div"),
    	    lock:true
    	});
	};
	
	var infoTabEvents = function(){
		// 固化对象信息 超链接触发事件
		$("#fixInfoTab").click(function(){
			$("#fixInfoTab").attr("class","current");
			$("#initalInfoTab").attr("class","other_26");
			$("#fixInfo").show();
			$("#initalInfo").hide();
		});
		
		// 原始对象信息 超链接触发事件
		$("#initalInfoTab").click(function(){
			$("#initalInfoTab").attr("class","current");
			$("#fixInfoTab").attr("class","other_26");
			$("#initalInfo").show();
			$("#fixInfo").hide();
		});
		
		// 【刷新】按钮触发事件
		$("#refresh").click(function(){
			art.dialog({
	    		id:'refresh-info-confirm-dialog',
	    		fixed: true,
	    		lock:true,
	    		title:"确认信息",
	    	    content: '确认要刷新么？',
	    	    cancelValue: '取消',
	    	    cancel: true,
	    	    okValue: '确认',
	    	    ok: function () {
	    	    	$.post("/finance/settlement/ordsettlement/queryInitalInfo.json", {id: $("#target_name").val()},
							function(data){
	    	    				art.dialog.get("refresh-info-confirm-dialog").close();
								$("#contactName").html(data.contact.name);//name对应PO里面的属性名
								$("#contactTitle").html(data.contact.title);
								$("#bankAccountName").html(data.bankAccountName);
								$("#contactTelephone").html(data.contact.telephone);
								$("#bankName").html(data.bankName);
								$("#contactFax").html(data.contact.fax);
								$("#bankAccount").html(data.bankAccount);
								$("#alipayName").html(data.alipayName);
								$("#alipayAccount").html(data.alipayAccount);
								//$("#memo").html(data.memo);
					});
	    	    	return false;
	    	    }
	    	});
		});
	};
	
	var gridcomplete = function(){
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var operate = "<a href='/finance/settlement/ordsettlement/sub/search/" + id + ".htm'  rowid = '" + id + "' target='_blank'  >查看子单</a>" +
	        "<a href='/finance/settlement/ordsettlement/detail/search/" + id + ".htm' rowid = '" + id + "' target='_blank'  >查看订单</a>";
	        var rowData = $("#result_table").jqGrid('getRowData',ids[i]);
	        if( parseFloat(rowData.payedAmount) < parseFloat(rowData.payAmount) && rowData.status !='已结算'){
	        	operate += "<a  id = 'pay_" + id + "' href='javascript:void(0);' class = 'ordsettlement_pay' rowid = '" + id + "' >打款</a>" ;
	        }
	        var confirmcls = "style = 'display:none'";
	        if( rowData.status !='已结算' && rowData.status !='已确认'){
	        	confirmcls="";
	        }
	        operate += "<a  id = 'confirm_" + id + "' href='javascript:void(0);' class = 'ordsettlement_confirm' "+confirmcls+" rowid = '" + id + "' >确认</a>";
	        var setttcls = "style = 'display:none'";
	        if( parseFloat(rowData.payedAmount) >= parseFloat(rowData.payAmount) && rowData.status =='已确认'){
	        	setttcls = "";
	        }
	        operate += "<a id = 'settt_" + id + "' href='javascript:void(0);' class = 'ordsettlement' "+setttcls+" rowid = '" + id + "'  >结算</a>";
	        operate += "<a  href='javascript:void(0);' class = 'ordsettlement_log'  rowid = '" + id + "'  >查看日志</a><a  href='javascript:void(0);' class = 'ordsettlement_info'  rowid = '" + id + "'  >详情</a>";
	        
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	    
	    $('.ordsettlement_pay').click(function(){
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	var adb = parseFloat(rowData.advancedepositsBalCNY);
	    	if( adb > 0 ){ //预存款打款
	    		$("#advancedepositsBal_pay_form").resetForm();
	    		$("#advancedepositsBal_pay_supplier").html(rowData.supplierName);
	    		$("#advancedepositsBal").html(rowData.advancedepositsBalCNY);
	    		$("#advancedepositsBal_pay_suppliderId").val(rowData.supplierId);
	    		$("#advancedepositsBal_pay_targetId").val(rowData.targetId);
	    		$("#advancedepositsBal_pay_settlementId").val(rowData.settlementId);
	    		$("#advancedepositsBal_pay_syncVersion").val(rowData.syncVersion);
	    		var toPayAmount = parseFloat(rowData.payAmount) - parseFloat(rowData.payedAmount);
	    		var _payAmount = adb > toPayAmount ? toPayAmount : adb;
	    		_payAmount = _payAmount.toFixed(2);
	    		$("#advancedepositsBal_pay_amount").val(_payAmount);
	    		$("#advancedepositsBal_pay_amount").attr("to_pay",_payAmount);
		    	art.dialog({
		    		id:"advancedepositsBal-pay-dialog",
		    		fixed: true,
		    		title: "结算单打款（预存款）",
		    	    content: document.getElementById("advancedepositsBal_pay_div"),
		    	    lock:true
		    	});
	    	}else {//线下打款
	    		bank_pay(rowData);
	    	}
	    });
	    $('.ordsettlement_info').click(function(){
	    	$.get("/finance/settlement/ordsettlement/info/"+$(this).attr("rowid")+"/0.htm",
				function(data){
					art.dialog({
						id:"confirm-dialog",
						title: "结算单详细信息",
					    content: data,
					    lock:true
				});
					
				infoTabEvents();
				
	    	});
    	});
	    $('.ordsettlement_confirm').click(function(){
	    	$.get("/finance/settlement/ordsettlement/info/"+$(this).attr("rowid")+"/1.htm",
				function(data){
					art.dialog({
						id:"confirm-dialog",
						title: "结算单确认",
					    content: data,
					    lock:true
				});
				infoTabEvents();
				$("#info_form").ajaxForm({
		    		type: 'post',
		    		url:  '/finance/settlement/ordsettlement/confirm.json',
		    		success: function(data) {
			    		art.dialog.get("confirm-dialog").close();
			    		if(typeof data.version_error != "undefined" ){
			    			$.msg("确认失败，结算单已被修改，请刷新后重试！",2500);
			    		}else{
				    		//刷新状态
				    		var rowData = $("#result_table").jqGrid("getRowData", data.settlementId);
				    		var version =  Number(rowData.syncVersion)+1;
				    		$("#result_table").jqGrid('setRowData', data.settlementId, { status: 'CONFIRMED',syncVersion: version});
				    		$("#result_table").setSelection(data.settlementId);
				    		$("#confirm_"+data.settlementId).hide();
				    		if( parseFloat(rowData.payedAmount) >= parseFloat(rowData.payAmount)){
				    	        $("#settt_"+data.settlementId).show();
				            }
				    		$.msg("结算单确认成功！",1500);
			    		}
		    		}
		    	});
			});
	    	
	    });
	    $('.ordsettlement').click(function(){
	    	$.get("/finance/settlement/ordsettlement/info/"+$(this).attr("rowid")+"/2.htm",
					function(data){
						art.dialog({
							id:"settt-dialog",
							title: "结算单结算",
						    content: data,
						    lock:true
					});
					infoTabEvents();
					$("#info_form").ajaxForm({
			    		type: 'post',
			    		url:  '/finance/settlement/ordsettlement/settt.json',
			    		success: function(result) {
			    			var data = result.ors;
				    		art.dialog.get("settt-dialog").close();
				    		if(typeof result.version_error != "undefined" ){
				    			$.msg("结算失败，结算单已被修改，请刷新后重试！",2500);
				    		}else{
					    		//刷新状态
					    		$("#result_table").jqGrid('setRowData', data.settlementId, { status: 'SETTLEMENTED'});
					    		$("#result_table").setSelection(data.settlementId);
					    		$("#settt_"+data.settlementId).hide();
					    		$.msg("结算单结算成功！",1500);
				    		}
			    		}
			    	});
				});
		    });
	    //转为押金
	    $('.ordsettlement_log').click(function(){
	    	var id = $(this).attr("rowid");
	    	Utils.showLog("ORD_SETTLEMENT",id);
	    });
	    $("#export_button").attr("class","seach_bt yajin_seacher");
	    $("#export_button").click(function(){
			var _form = $("#search_form");
			_form.attr("action",_form.attr("excel_action")+"?exporttype=2");
			_form.submit();
		});
		
	};

	var cellFormater_status = function(cellVal){
		if(cellVal == 'UNSETTLEMENTED'){
			return '未确认';
		}else if(cellVal == 'CONFIRMED'){
			return '已确认';
		}else if(cellVal == 'SETTLEMENTED'){
			return '已结算';
		}else{
			return '未知';
		}
	};
	var cellFormater_paystatus = function(cellVal){
		if(cellVal == 1){
			return '已打款';
		}else if(cellVal == 0){
			return '未打款';
		}else{
			return '未知';
		}
	};
	$("#search_button").click(function(){
		var sett_id  =$("input[name=settlementId]").val();
		if(sett_id!=""){
			if(!/^[0-9\,]*$/.test(sett_id)){
				$.msg("结算单号输入错误",1500);
				return false;
			}
		}
		var _form = $("#search_form");
		_form.attr("action",_form.attr("grid_action"));
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '结算单号', '版本号','结算对象', '供应商ID', '供应商名称', '应结金额', '已打款金额', '打款状态', '结算状态', '用户名', '生成时间', '操作', '预存款余额','结算对象ID','供应商ID','供应商名称'],
			colModel : [ {
				name : 'settlementId',
				index : 'SETTLEMENTID',
				align:"center",
				sorttype: "int",
				width:70
			}, {
				name : 'syncVersion',
				index : 'SYNC_VERSION',
				align:"center",
				sorttype: "int",
				hidden:true
			},{
				name : 'targetName',
				index : 'TARGETID',
				align:"center",
				sorttype: "string",
				sortable:false,
				width:120
			},{
				name : 'supplierId',
				index : 'SUPPLIERID',
				align:"center",
				sorttype: "string",
				sorttype: "int",
				width:60
			},{
				name : 'supplierName',
				index : 'SUPPLIERNAME',
				align:"center",
				sorttype: "string",
				sortable:false,
				width:150
			}, {
				name : 'payAmount',
				index : 'PAYAMOUNT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:75
			},{
				name : 'payedAmount',
				index : 'PAYEDAMOUNT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:75
			},{
				name : 'payStatus',
				index : 'PAYEDAMOUNT',
				align:"center",
				sorttype: "string",
				formatter:cellFormater_paystatus,
				width:65
			},{
				name : 'status',
				index : 'STATUS',
				align:"center",
				sorttype: "string",
				formatter:cellFormater_status,
				width:65
			},{
				name : 'userName',
				index : 'USER_NAME',
				align:"center",
				sorttype: "string",
				width:60
			},{
				name : 'createTimeStr',
				index : 'A.CREATE_TIME',
				align: "center",
				sorttype: "string",
				width: 95
			},
			{
				name:'operate',
				index:'Id',
				align:"center",
				sortable:false,
				width:225
			}, {
				name:'advancedepositsBalCNY',
				index:'SETTLEMENTID',
				align:"center",
				hidden:true
			}, {
				name:'targetId',
				index:'TARGETID',
				align:"center",
				hidden:true
			}, {
				name:'supplierId',
				index:'SUPPLIERID',
				align:"center",
				hidden:true
			}, {
				name:'supplierName',
				index:'SUPPLIERNAME',
				align:"center",
				hidden:true
			}],
	        gridComplete:gridcomplete
		});
	});
	
	
});