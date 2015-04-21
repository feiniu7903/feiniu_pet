$(function() {

var add_source = null ;

/**
 * 供应商名称自动查询下拉框
 */
$("#supplier").combox("/finance/autocomplete/supplier.json");

$.validator.addMethod("advancedeposits_amount", function(value, element) {
	   var amount = parseFloat(value);
	   var all =  parseFloat($("#advancedeposits_amount").attr("amount"));
	   if($("#advancedeposits_type").val() == 'RETURN' || $("#advancedeposits_type").val() == 'REVISION'){
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
		amount: {required:true,amountfloat:true,advancedeposits_amount:true},
		bank: "required",
		operatetimes: "required"//,
		//serial: "required"
		
	},
	messages: {
		amount: {required:"请输入预存款金额"},
		bank: "请输入支付平台",
		operatetimes: "请输入打款时间"//,
		//serial: "请输入流水号"
	}
});

/**
 * 添加预存款AJAX提交表单
 */
$('#add_form').ajaxForm({
	type: 'post',
	url:  '/finance/settlement/advancedeposits/add.json',
	success: function(data) {
		art.dialog.get("add-dialog").close();
		if(add_source == "header"){
			$("#search_button").click();
		}else{
			//刷新金额
			var rowData = $("#result_table").jqGrid("getRowData", data.supplierId);
			var _amounts = rowData.advancedepositsBal,
			_amounts2 = rowData.advancedepositsAll;
			var amount = new Number(_amounts),
				amount2  = new Number(_amounts2);
			if(data.type == "DEPOSIT"){
				amount2 += data.amount;
			}
			amount+=data.amount;
			$("#result_table").jqGrid('setRowData', data.supplierId, { advancedepositsBal: amount,advancedepositsAll: amount2,currency:data.advCurrency});
			
			$("#result_table").setSelection(data.supplierId);
		}
		$.msg("添加成功！",1500);
	}
});

/**
 * 打开添加窗口
 * kind 类型
 * sid 供应商ID
 * sname 供应商名称
 */
var opp_add_dialog = function (sid,supplierName,advCurrency,advancedepositsBal){
	$("#add_form").resetForm();
	$('#add_supplierId').val(sid);
	$("#add_supplier").html(supplierName);
	$("#currencySelect").val(advCurrency);
	// 判断预存款总额是否为0，用来设置币种下拉框是否可用
	if(advancedepositsBal == 0){
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
        var operate = "<a href='#' class = 'advancedeposits_add' rowid = '" + id + "' >添加</a>" +
        "<a href='#' class = 'settlement_payment_record' rowid = '" + id + "'   >查看结算记录</a> " +
        		"<a href='#' class = 'advancedeposits_record' rowid = '" + id + "'   >流水记录</a> " +
        				"<a href='#' class = 'advancedeposits_shiftout' rowid = '" + id + "' >转为押金</a>";
        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
    }
    $('.advancedeposits_record').click(function(){
    	$.get("/finance/settlement/advancedeposits/record/"+$(this).attr("rowid")+".htm",
			function(data){
				art.dialog({
					title: "流水记录",
				    content: data,
				    lock:true
			});
		});
    });
    $('.settlement_payment_record').click(function(){
    	$.get("/finance/settlement/advancedeposits/settlementrecord/"+$(this).attr("rowid")+".htm",
			function(data){
				art.dialog({
					title: "结算记录",
				    content: data,
				    lock:true
			});
		});
    });
    $('.advancedeposits_add').click(function(){
    	var rid = $(this).attr("rowid");
    	var rowData = $("#result_table").jqGrid("getRowData", rid);
    	$("#advancedeposits_amount").attr("amount", parseFloat(rowData.advancedepositsBal));
    	opp_add_dialog(rid, rowData.supplierName,rowData.currency,rowData.advancedepositsBal);
    });
    //转为押金
    $('.advancedeposits_shiftout').click(function(){
    	$("#shiftout_form").resetForm();
    	var rid = $(this).attr("rowid");
    	var rowData = $("#result_table").jqGrid("getRowData", rid);
    	$("#shiftout_amount").attr("advancedepositsbal",rowData.advancedepositsBal);
    	$("#shiftout_supplierId").val(rid);
    	$("#advCurrency").val(rowData.currency);
    	if(rowData.currency == rowData.foreCurrency || (rowData.foregiftsBal == null || rowData.foregiftsBal == "" || rowData.foregiftsBal == 0)){
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
			index : 'SUPPLIER_ID',
			align:"center",
			sorttype: "string"
		}, {
			name : 'supplierName',
			index : 'SUPPLIER_NAME',
			align:"center",
			sorttype: "string"
		}, {
			name : 'advancedepositsAll',
			index : 'advancedepositsAll',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
			width:80,
			sortable:false
		},{
			name : 'advancedepositsPay',
			index : 'advancedepositsPay',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
			width:80,
			sortable:false
		},{
			name : 'advancedepositsBal',
			index : 'ADVANCEDEPOSITS_BAL',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
			width:80
		},{
			name : 'currencyName',
			index : 'CURRENCY_NAME',
			sorttype: "string",
			width: 80
		},{
			name : 'foregiftsBal',
			index : 'foregiftsBal',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
			width:80,
			hidden: true
		},{
			name : 'foreCurrency',
			index : 'foreCurrency',
			width: 80,
			hidden: true
		},
		{
			name:'operate',
			index:'Id',
			align:"center",
			sortable:false
		},{
			name : 'currency',
			index : 'currency',
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
				$.msg("请先选择供应商!");
				return;
			}
			add_source = "header";
			$.get("/finance/settlement/advancedeposits/getamount/"+_sid+".json",function(data){
				$("#advancedeposits_amount").attr("amount", data.advancedepositsBal );
				opp_add_dialog( _sid, _sname, data.currency, data.advancedepositsBal);
			});
			
			
		}
	);

$.validator.addMethod("shiftout_amount", function(value, element) {
	   var aint = parseFloat(value);	
	   var sa =  parseFloat($(element).attr("advancedepositsbal"));
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
	url:  '/finance/settlement/advancedeposits/shiftout.json',
	success: function(data) {
		art.dialog.get("shiftout-dialog").close();
		//刷新金额
		var _amounts = $("#result_table").jqGrid("getRowData", data.supplierId).advancedepositsBal;
		var amount = new Number(_amounts);
		$("#result_table").jqGrid('setRowData', data.supplierId, { advancedepositsBal: amount+data.amount });
		$("#result_table").setSelection(data.supplierId);
		$.msg("预存款转押金成功！",1500);
	}
});
});