$(function() {

	$("#targetId").combox("/pet_back/finance/settlementInterface/searchSupplierSettleRule.do");
	$("#metaProductId").combox({
		source : "/pet_back/finance/settlementInterface/searchGoodsList.do",
		minLength : 2
	});

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
	    	    	$.post("refreshInitalInfo.do", {settlementId: $("#settlementId").val()},
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
	        var operate = "<a href='searchOrderDetailIndex.do?settlementId=" + id + "' rowid = '" + id + "' target='_blank'  >查看订单</a>";
	        var rowData = $("#result_table").jqGrid('getRowData',ids[i]);
	        if( parseFloat(rowData.payedAmountYuan)+parseFloat(rowData.deductionAmountYuan) < parseFloat(rowData.settlementAmountYuan) && rowData.status !='SETTLEMENTED'){
	        	operate += "<a  id = 'pay_" + id + "' href='javascript:void(0);' class = 'settlement_pay' rowid = '" + id + "' >打款</a>" ;
	        }
	        var setttcls = "style = 'display:none'";
	        if((parseFloat(rowData.payedAmountYuan)+parseFloat(rowData.deductionAmountYuan) >= parseFloat(rowData.settlementAmountYuan) && rowData.status =='PAYED') || parseFloat(rowData.settlementAmountYuan) == 0 && rowData.status !='SETTLEMENTED'){
	        	setttcls = "";
	        }
	        operate += "<a id = 'settt_" + id + "' href='javascript:void(0);' class = 'ordsettlement' "+setttcls+" rowid = '" + id + "'  >结算</a>";
	        operate += "<a  href='javascript:void(0);' class = 'settlement_log'  rowid = '" + id + "'  >查看日志</a><a  href='javascript:void(0);' class = 'settlement_info'  rowid = '" + id + "'  >详情</a>";
	        
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	    
	    $('.settlement_pay').click(function(){
	    	$.get("pay.do?settlementId="+$(this).attr("rowid"),
				function(data){
					art.dialog({
						id:"bank-pay-dialog",
						title: "结算单打款",
					    content: data,
					    lock:true
					});
					$.getScript("/pet_back/js/finance/settlement/set_settlement_pay.js");
					}
	    	);
	    });
	    $('.settlement_info').click(function(){
	    	$.get("info.do?settlementId="+$(this).attr("rowid")+"&type=0",
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
	    $('.ordsettlement').click(function(){
	    	$.get("info.do?settlementId="+$(this).attr("rowid")+"&type=1",
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
			    		url:  'settle.do',
			    		success: function(data_str) {
			    			var data = eval("("+data_str+")");
			    			if(data.res == 1 ){
			    				var msg = "结算单结算成功！";
			    				if(typeof data.deductionAmount != "undefined"){
			    					msg ="供应商：["+data.settlement.supplierName+"] 本次结算产生抵扣款：<font style='color:red;font-weight:bold;'>"+data.deductionAmount+" </font> 元"
			    				}
					    		art.dialog.get("settt-dialog").close();
						    		//刷新状态
						    		$("#result_table").jqGrid('setRowData', data.settlement.settlementId, {  status : data.settlement.status, statusName : data.settlement.statusName});
						    		$("#result_table").setSelection(data.settlement.settlementId);
						    		$("#settt_"+data.settlement.settlementId).hide();
						    		$.msg(msg);
			    			}else{
			    				
			    				$.msg("结算单结算失败！",2500);
			    			}
			    		}
			    	});
				});
		    });
	    $('.settlement_log').click(function(){
	    	var id = $(this).attr("rowid");
	    	Utils.showLog("SET_SETTLEMENT",id);
	    });
	    $("#export_button").attr("class","seach_bt yajin_seacher");
	    $("#export_button").click(function(){
	    	var targetId = $("#targetId_val").val();
	    	var settlementId  =$("input[name=settlementId]").val();
	    	var order_id  =$("input[name=orderId]").val();
	    	
	    	if(($.trim(targetId) == "") && ($.trim(settlementId) == "")){
	    		$.msg("请输入结算名称或结算单号",2500);
	    		return false;
	    	}
	    	
	    	if(order_id != ""){
				if(!/^[0-9\s]*$/.test(order_id)){
					$.msg("订单号输入错误",1500);
					return false;
				}
			}
			var _form = $("#search_form");
			_form.attr("action",_form.attr("excel_action")+"?exporttype=2");
			_form.submit();
		});
		
	};

	$("#search_button").click(function(){
		var sett_id  =$("input[name=settlementId]").val();
		var order_id  =$("input[name=orderId]").val();
		if(sett_id!="" ){
			if(!/^([\d\s]+,)*(\s*\d+\s*)$/.test(sett_id)){
				$.msg("结算单号输入错误",1500);
				return false;
			}
		}
		if(order_id != ""){
			if(!/^[0-9\s]*$/.test(order_id)){
				$.msg("订单号输入错误",1500);
				return false;
			}
		}
		var _form = $("#search_form");
		_form.attr("action",_form.attr("grid_action"));
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '结算单号', 
			             '结算名称', 
			             '供应商ID', 
			             '供应商名称', 
			             '应结金额', 
			             '应结金额(隐藏)', 
			             '已打款金额', 
			             '已打款金额(隐藏)', 
			             '抵扣款使用金额', 
			             '抵扣款使用金额(隐藏)', 
			             '状态(隐藏)', 
			             '状态', 
			             '用户名', 
			             '生成时间', 
			             '操作', 
			             '结算ID(隐藏)',
			             '供应商ID(隐藏)',
			             '供应商名称(隐藏)'],
			colModel : [ {
				name : 'settlementId',
				index : 'SETTLEMENT_ID',
				align:"center",
				sorttype: "int",
				width:65
			}, {
				name : 'targetName',
				index : 'TARGET_ID',
				align:"center",
				sorttype: "string",
				sortable:false,
				width:120
			},{
				name : 'supplierId',
				align:"center",
				sortable:false,
				width:60
			},{
				name : 'supplierName',
				align:"center",
				sorttype: "string",
				sortable:false,
				width:150
			}, {
				name : 'settlementAmountYuan',
				align:"center",
				sortable:false,
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:75
			},{
				name : 'settlementAmount',
				index : 'SETTLEMENT_AMOUNT',
				sorttype: "float",
				hidden:true
			},{
				name : 'payedAmountYuan',
				index : 'PAYED_AMOUNT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:75
			},{
				name : 'payedAmount',
				index : 'PAYED_AMOUNT',
				sorttype: "float",
				hidden:true
			},{
				name : 'deductionAmountYuan',
				align:"center",
				sortable:false,
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:90
			},{
				name : 'deductionAmount',
				sortable:false,
				hidden:true
			},{
				name : 'status',
				index : 'STATUS',
				align:"center",
				sorttype: "string",
				hidden:true
			},{
				name : 'statusName',
				index : 'STATUS',
				align:"center",
				sorttype: "string",
				width:50
			},{
				name : 'operatorName',
				index : 'OPERATOR_NAME',
				align:"center",
				sorttype: "string",
				width:60
			},{
				name : 'createTimeStr',
				index : 'A.CREATE_TIME',
				align: "center",
				sorttype: "string",
				width: 105
			},
			{
				name:'operate',
				align:"center",
				sortable:false,
				width:160
			}, {
				name:'targetId',
				index:'TARGET_ID',
				align:"center",
				hidden:true
			}, {
				name:'supplierId',
				sortable:false,
				align:"center",
				hidden:true
			}, {
				name:'supplierName',
				sortable:false,
				align:"center",
				hidden:true
			}],
	        gridComplete:gridcomplete
		});
	});
	
	
});