$(function() {
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplier").combox("/pet_back/sup/searchSupplierJSON.do");

	var add_source = null ;

	$.validator.addMethod("advanceDeposit_amount", function(value, element) {
	   var amount = parseFloat(value);
	   var all =  parseFloat($("#advanceDeposit_amount").attr("amount"));
	   if($("#advanceDeposit_type").val() == 'RETURN' || $("#advanceDeposit_type").val() == 'REVISION'){
		   if(amount > all){
			   return false;
		   }else{
			   return true;
		   }
	   }else{
		   return true;
	   }
	}, "退回/冲正金额不能大于预存款总额");   

	/**
	 * 添加预存款表单验证
	 */
	$("#add_form").validate({
		rules: {
			amount: {required:true,amountfloat:true,advanceDeposit_amount:true},
			bank: "required",
			operatetimes: "required",
			advCurrency:"required"
			//serial: "required"
			
		},
		messages: {
			amount: {required:"请输入预存款金额"},
			bank: "请输入支付平台",
			operatetimes: "请输入打款时间",
			advCurrency:"请选择币种"
			//serial: "请输入流水号"
		}
	});

	/**
	 * 添加预存款AJAX提交表单
	 */
	$('#add_form').ajaxForm({
		type: 'post',
		url:  'addAdvancedDeposit.do',
		success: function(data) {
			art.dialog.get("add-dialog").close();
			$("#search_button").click();
			$.msg("添加成功！",1500);
		}
	});

	/**
	 * 打开添加窗口
	 * kind 类型
	 * sid 供应商ID
	 * sname 供应商名称
	 */
	var opp_add_dialog = function (sid, supplierName, advCurrency, advanceDepositBal){
		$("#add_form").resetForm();
		$('#add_supplierId').val(sid);
		$("#add_supplier").html(supplierName);
		$("#currencySelect").val(advCurrency);
		// 判断预存款总额是否为0，用来设置币种下拉框是否可用
		if(advanceDepositBal == 0){
			$("#currencySelect").attr("disabled", false);
			$("#cur_hidden").attr("name","currencyDisabled");
			$("#cur_hidden").val(advCurrency);
		} else {
			$("#currencySelect").attr("disabled","disabled");
			$("#cur_hidden").attr("name","advCurrency");
			$("#cur_hidden").val(advCurrency);
		}
		art.dialog({
			id:"add-dialog",
			fixed: true,
			title: "添加",
		    content: document.getElementById("add_div"),
		    lock:true
		});
	};

	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var operate = "<a href='#' class = 'advanceDeposit_add' rowid = '" + id + "'>添加</a>" +
	        "<a href='#' class = 'settlement_payment_record' rowid = '" + id + "'>查看结算记录</a> " +
	        		"<a href='#' class = 'advanceDeposit_record' rowid = '" + id + "'>流水记录</a> " +
	        				"<a href='#' class = 'advanceDeposit_shiftout' rowid = '" + id + "'>转为押金</a>";
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	    
	    /**
	     * 查看流水记录
	     */
	    $('.advanceDeposit_record').click(function(){
	    	$.get("record.do?supplierId=" + $(this).attr("rowid"),
				function(data){
	    			$.record_dialog_callback(data,"paginationDiv");
			});
	    });
	    
	    /**
	     * 查看结算记录
	     */
	    $('.settlement_payment_record').click(function(){
	    	$.get("settlementRecord.do?supplierId=" + $(this).attr("rowid"),
				function(data){
					art.dialog({
						title: "结算记录",
					    content: data,
					    lock:true
				});
			});
	    });
	    
	    /**
	     * 点击【新增】超链接
	     */
	    $('.advanceDeposit_add').click(function(){
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#advanceDeposit_amount").attr("amount", parseFloat(rowData.advanceDepositAmountYuan));
	    	opp_add_dialog(rid, rowData.supplierName, rowData.advanceDepositCurrency, rowData.advanceDepositAmountYuan);
	    });
	    
	    /**
	     * 转为押金
	     */
	    $('.advanceDeposit_shiftout').click(function(){
	    	$("#shiftout_form").resetForm();
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#shiftout_amount").attr("advanceDepositAmountYuan",rowData.advanceDepositAmountYuan);
	    	$("#shiftout_supplierId").val(rid);
	    	$("#advCurrency").val(rowData.advanceDepositCurrency);
	    	if(rowData.advanceDepositCurrency == rowData.foregiftsCurrency || (rowData.foregiftsAmountYuan == null || rowData.foregiftsAmountYuan == "" || rowData.foregiftsAmountYuan == 0)){
	    		art.dialog({
	        		id:"shiftout-dialog",
	        		fixed: true,
	        		title: "转为押金",
	        	    content: document.getElementById("shiftout_div"),
	        	    lock:true
	        	});
	    	}else{
	    		$.msg("不能转为押金！", 1500);
	    		return;
	    	}
	    });
	};

	$("#search_button").click(function(){
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '供应商ID', '供应商名称', '预存款总额', '预存款结算总额', '预存款余额', '预存款币种', '押金余额', '押金币种', '操作', '币种'],
			colModel : [ {
				name : 'supplierId',
				index : 'supplierId',
				align:"center",
				width: 50,
				sorttype: "string"
			}, {
				name : 'supplierName',
				index : 'supplierName',
				align:"center",
				width: 300,
				sorttype: "string",
				sortable:false
			}, {
				name : 'advanceDepositAllYuan',
				index : 'advanceDepositAll',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:60,
				sortable:false
			},{
				name : 'advanceDepositPayYuan',
				index : 'advanceDepositPay',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:70,
				sortable:false
			},{
				name : 'advanceDepositAmountYuan',
				index : 'advanceDepositAmount',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:60
			},{
				name : 'zhAdvanceDepositCurrency',
				index : 'advanceDepositCurrency',
				sorttype: "string",
				align:"center",
				width: 60
			},{
				name : 'foregiftsAmountYuan',
				index : 'foregiftsAmount',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:80,
				hidden: true
			},{
				name : 'foregiftsCurrency',
				index : 'foregiftsCurrency',
				width: 60,
				hidden: true
			},
			{
				name:'operate',
				index:'Id',
				align:"center",
				width: 180,
				sortable:false
			},{
				name : 'advanceDepositCurrency',
				index : 'advanceDepositCurrency',
				hidden: true
			}],
	        gridComplete:gridcomplete
		});
	});

	$('#add_button').click(
			function(){
				var  _sid= $('input[name=supplier]').val();
				var _sname = $("#supplier").val();
				if(typeof _sid =="undefined"){
					$.msg("请先选择供应商!", 1000);
					return;
				}
				add_source = "header";
				$.get("getamount.do?supplierId=" + _sid,function(data){
					var amount,currency;
					if(null != data){
						amount = data.advanceDepositAmountYuan;
						currency = data.advanceDepositCurrency;
					}else{
						amount = 0;
						currency = "CNY";
					}
					$("#advanceDeposit_amount").attr("amount", amount );
					opp_add_dialog( _sid, _sname, currency, amount);
				});
			}
	);

	/**
	 * 转押金金额校验
	 */
	$.validator.addMethod("shiftout_amount", function(value, element) {
		   var aint = parseFloat(value);	
		   var sa =  parseFloat($(element).attr("advanceDepositAmountYuan"));
		   return  aint>0 && aint<=sa ;
	}, "转出金额不能大于预存款金额");   

	/**
	 * 转为押金表单验证
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
				required:"请输入转为押金的金额",
				shiftout_amount:"转出金额不能大于预存款金额"
			}
		}
	});

	/**
	 * 转为押金AJAX提交表单
	 */
	$('#shiftout_form').ajaxForm({
		type: 'post',
		url:  'shiftout.do',
		success: function(data) {
			art.dialog.get("shiftout-dialog").close();
			$("#search_button").click();
			$.msg("预存款转押金成功！",1500);
		}
	});
});