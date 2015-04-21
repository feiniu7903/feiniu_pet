$(function() {
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplier").combox("/pet_back/finance/settlementInterface/searchSupplierList.do");

	var add_source=null;

	/**
	 * 查询
	 */
	$("#search_button").click(function(){
		var search_kind = $("#search_kind").val();
		var s1,s2,s3,s4;
		if(search_kind == "CASH" ){
			s1 = '押金金额',s2 = "depositAmountYuan",s3 = "DEPOSIT_AMOUNT",s4 = false;
		}else{
			s1 = '担保函额度',s2 = "guaranteeLimitYuan",s3 = "A.GUARANTEE_LIMIT",s4 = true;
		}
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '供应商ID', '供应商名称', s1, '预存款余额', '预存款币种', '币种简称','币种','操作'],
			colModel : [ {
				name : 'supplierId',
				index : 'A.SUPPLIER_ID',
				align:"center",
				width:50,
				sorttype: "string"
			}, {
				name : 'supplierName',
				align:"center",
				width:350,
				sorttype: "string",
				sortable:false
			}, {
				name : s2,
				index : s3,
				align:"center",
				sorttype: "float",
				formatter:"number",
				width:60,
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
			}, {
				name : "advanceDepositAmountYuan",
				index : "advanceDepositAmount",
				align:"center",
				hidden: true
			}, {
				name : "advanceDepositCurrency",
				index : "advanceDepositCurrency",
				align:"center",
				hidden: true
			}, {
				name : "depositCurrency",
				index : "depositCurrency",
				align:"center",
				hidden: true
			}, {
				name : "zhDepositCurrency",
				index : "DEPOSIT_CURRENCY",
				width:60,
				align:"center",
				hidden: s4
			},
			{
				name:'operate',
				index:'Id',
				align:"center",
				width:150,
				sortable:false
			}],
	        gridComplete:gridcomplete
		});
	});
	
	var gridcomplete = function(){  
		var _search_kind = $("#search_kind").val();
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var operate = "<a href='#' class = 'deposit_add' rowid = '" + id + "' >添加</a>" +
	        		"<a href='#' class = 'deposit_record' rowid = '" + id + "'  >流水记录</a>";
	        if(_search_kind == "CASH"){
	        	operate += "<a  href='#' class='deposit_shiftout' rowid = '" + id + "' >转为预存款</a>";
	        }
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	    
	    /**
	     * 流水记录
	     */
	    $('.deposit_record').click(function(){
	    	$.get("record.do?supplierId=" + $(this).attr("rowid") + "&type=" +_search_kind,
				function(data){
	    			$.record_dialog_callback(data,"paginationDiv");
			});
	    });
	    
	    /**
	     * 添加
	     */
	    $('.deposit_add').click(function(){
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	add_source = "grid";
	    	var _amounts;
	    	if(_search_kind == "CASH"){
				_amounts = rowData.depositAmountYuan;
			}else{
				_amounts = rowData.guaranteeLimitYuan;
			}
	    	$("#deposit_amount").attr("amount", parseFloat(_amounts));

	    	var amount = 0;
	    	if((rowData.depositAmountYuan != null && rowData.depositAmountYuan != 0.00) || (rowData.guaranteeLimitYuan != null && rowData.guaranteeLimitYuan != 0.00)){
	    		amount = 1;
	    	}
	    	opp_add_dialog(_search_kind, rid, rowData.supplierName, rowData.depositCurrency, amount);
	    });
	    
	    /**
	     * 转为预存款
	     */
	    $('.deposit_shiftout').click(function(){
	    	$("#shiftout_form").resetForm();
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#shiftout_amount").attr("depositAmountYuan",rowData.depositAmountYuan);
	    	$("#shiftout_supplierId").val(rid);
	    	$("#depositCurrency").val(rowData.depositCurrency);
	    	
//	    	alert(rowData.currency);
//	    	alert(rowData.foreCurrency);
//	    	if(rowData.currency != rowData.foreCurrency){
//	    		$.msg("押金和预存款的币种不一致！", 1000);
//	    		return;
//	    	}
//	    	alert(rowData.advancedepositsBal);
//	    	if(rowData.advancedepositsBal != null && rowData.advancedepositsBal != "" && rowData.advancedepositsBal != 0){
//	    		$.msg(rowData.advancedepositsBal, 1000);
//	    		return;
//	    	}
	    	if(rowData.advanceDepositsCurrency == rowData.depositCurrency || (rowData.advanceDepositsAmountYuan == null || rowData.advanceDepositsAmountYuan == "" || rowData.advanceDepositsAmountYuan == 0)){
	    		art.dialog({
	        		id:"shiftout-dialog",
	        		fixed: true,
	        		title: "转为预存款",
	        	    content: document.getElementById("shiftout_div"),
	        	    lock:true
	        	});
	    	}else{
	    		$.msg("不能转为预存款！", 1000);
	    		return;
	    	}
	    });
	};

	/**
	 * 转为预存款金额表单验证
	 */
	$.validator.addMethod("shiftout_amount", function(value, element) {
	   var aint = parseFloat(value);	
	   var sa =  parseFloat($(element).attr("depositAmountYuan"));
	    return  aint>0 && aint<=sa ;
	}, "转出金额不能大于押金金额");   

	/**
	 * 转为预存款表单验证
	 */
	$("#shiftout_form").validate({
		rules: {
			amount: {
				required:true,
				amountfloat:true,
				shiftout_amount:true
			}
		},
		messages: {
			amount: {
				required:"请输入转为预存款的金额",
				shiftout_amount:"转出金额不能大于押金金额"
			}
		}
	});
	
	/**
	 * 转为预存款AJAX提交表单
	 */
	$('#shiftout_form').ajaxForm({
		type: 'post',
		url:  'shiftout.do',
		success: function(data) {
			art.dialog.get("shiftout-dialog").close();
			$("#search_button").click();
			$.msg("押金转预存款成功！",1500);
		}
	});	

	/**
	 * 打开添加窗口
	 * kind 类型
	 * sid 供应商ID
	 * sname 供应商名称
	 */
	var opp_add_dialog = function (kind, sid, sname, currency, amount){
		$("#add_form").resetForm();
		$('#add_supplierId').val(sid);
		$("#add_supplier").html(sname);
		$("#add_kind").val(kind);
		if("CASH"==kind){
			$("#add_kind_label").html("押金");
		}else if("GUARANTEE"== kind){
			$("#add_kind_label").html("担保函押金");
		}
	
		$("#currencySelect").val(currency);
		// 判断预存款总额是否为0，用来设置币种下拉框是否可用
		if(amount == 0 || null == amount){
			$("#currencySelect").attr("disabled", false);
			$("#cur_hidden").attr("name","currencyDisabled");
			$("#cur_hidden").val(currency);
		} else {
			$("#currencySelect").attr("disabled","disabled");
			$("#cur_hidden").attr("name","depositCurrency");
			$("#cur_hidden").val(currency);
		}
		
		art.dialog({
			id:"add-dialog",
			fixed: true,
			title: "添加",
		    content: document.getElementById("add_div"),
		    lock:true
		});
	};


	/**
	 * 
	 */
	$.validator.addMethod("deposit_amount", function(value, element) {
		   var amount = parseFloat(value);
		   var all =  parseFloat($("#deposit_amount").attr("amount"));
		   if($("#deposit_type").val() == 'RETURN' || $("#deposit_type").val() == 'REVISION'){
			   if(amount > all){
				   return false;
			   }else{
				   return true;
			   }
		   }else{
			   return true;
		   }
		  }, "退回/冲正金额不能大于押金总额");   


	/**
	 * 添加押金表单验证
	 */
	$("#add_form").validate({
		rules: {
			amount: {required:true,amountfloat:true,deposit_amount:true},
			bank: "required",
			operatetimes: "required",
			depositCurrency: "required"
			//serial: "required"
			
		},
		messages: {
			amount: {required:"请输入押金金额"},
			bank: "请输入支付平台",
			operatetimes: "请输入打款时间",
			depositCurrency:"请选择币种"
			//serial: "请输入流水号"
		}
	});

	/**
	 * 添加押金AJAX提交表单
	 */
	$('#add_form').ajaxForm({
		type: 'post',
		url:  'addForegift.do',
		success: function(data) {
			art.dialog.get("add-dialog").close();
			$("#search_button").click();
			$.msg("添加成功！",1500);
		}
	});
	
	/**
	 * 添加
	 */
	$('#add_button').click(
		function(){
			var _search_kind = $("#search_kind").val();
			var  _sid= $('input[name=supplier]').val();
			var _sname = $("#supplier").val();
			if(typeof _sid =="undefined"){
				$.msg("请先选择供应商!", 1000);
				return;
			}
			add_source = "header";
			$.get("getamount.do?_search_kind="+_search_kind+"&supplierId="+_sid, function(data){
		    	var amount = 0;
				if(null != data){
					if((data.depositAmount != null && data.depositAmount != 0.00) || (data.guaranteeLimit != null && data.guaranteeLimit != 0.00)){
			    		amount = 1;
			    	}
					opp_add_dialog(_search_kind, _sid, _sname, data.depositCurrency, amount);
					$("#deposit_amount").attr("amount", data.depositAmountYuan );
				}else{
					opp_add_dialog(_search_kind, _sid, _sname, 'CNY', amount);
					$("#deposit_amount").attr("amount", 0 );
				}
			});
		}
	);

});