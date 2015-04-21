
$(function() {
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplier").combox("/pet_back/sup/searchSupplierJSON.do");

	/**
	 * 查询抵扣款
	 */ 
	$("#search_button").click(function(){
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '供应商ID', '供应商', '抵扣款金额', '操作' ],
			colModel : [ 
			             {name:'supplierId',align:"center", index:'A.SUPPLIER_ID',witdth:70},
			             {name:'supplierName',align:"center", sortable:false},
			             {
			            	 name:'deductionAmountYuan',
			            	 index:'DEDUCTION_AMOUNT',
			 				 sorttype: "float",
			 				 formatter:"number",
			            	 formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
			            	 align:"center",
			            	 witdth:70
			             },
			             {name:'operate', index:'Id', align:"center",witdth:100, sortable:false}
			            ],
			gridComplete:gridcomplete
		});
	});
	
	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id = ids[i];   
	        var operate = "<a href='#' class = 'deduction_record' rowid = '" + id + "'>查看流水</a>";
	        operate += "<a  href='#' class='update_deduction' rowid = '" + id + "' >修改</a>";
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    } 
	    
	    
	    /**
	     * 查看流水
	     */
	    $('.deduction_record').click(function(){
	    	$.get("searchRecord.do?supplierId=" + $(this).attr("rowid"),
				function(data){
	    		$.record_dialog_callback(data,"paginationDiv");
			});
	    });
	    
	    /**
	     * 修改
	     */
	    $('.update_deduction').click(function(){
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
			$("#update_form").resetForm();
			$('#supplierId').val(rowData.supplierId);
			$("#update_supplier").html(rowData.supplierName);
			$("#deduction_amount_yuan").html(rowData.deductionAmountYuan + "元");
			
	    	art.dialog({
				id:"update-dialog",
				fixed: true,
				title: "修改",
			    content: document.getElementById("update_div"),
			    lock:true
			});
	    });
	};

	$.validator.addMethod("validateAmount", function(value, element) {
	   var amount = parseFloat(value);
	   var all =  parseFloat($("#deduction_amount_yuan").html());
	   var type = $("#type").val();
	   // 退回时：校验退回金额不能大于抵扣款总金额
	   if(type == "RETURN"){
		   if(amount > all){
			   return false;
		   }else{
			   return true;
		   } 
	   }else{
		   return true;
	   }
	}, "退回金额不能大于抵扣款总额");   
	
	/**
	 * 修改抵扣款表单验证
	 */
	$("#update_form").validate({
		rules: {
			amount: {required:true,amountfloat:true,validateAmount:true}
		},
		messages: {
			amount: {required:"请输入抵扣款金额"}
		}
	});

	/**
	 * 修改抵扣款AJAX提交表单
	 */
	$('#update_form').ajaxForm({
		type: 'post',
		url:  'updateDeduction.do',
		success: function(data) {
			art.dialog.get("update-dialog").close();
			$("#search_button").click();
			$.msg("添加成功！",1500);
		}
	});
	
});

