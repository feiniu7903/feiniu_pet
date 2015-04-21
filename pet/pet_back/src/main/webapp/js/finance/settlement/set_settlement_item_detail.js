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
		}
	$.validator.addMethod("orderIdV", function(value, element) {
	    return /^([\d\s]+,)*(\s*\d+\s*)$/.test(value);   
	  }, "订单号错误"); 
	$("#add_form").validate({
		rules: {
			orderId: {required:true,orderIdV:true}
		},
		messages: {
			orderId: {required:"请输入订单号"}
		}
	});
	
	$('#add_form').ajaxForm({
		type: 'post',
		url:  'searchOrder.do?settlementId='+$("#main_settlementId").val()+"&settlementType="+$("#settlement_type").val(),
		success: function(data) {
			$(".order_serch_result_tr").remove();
			var _empty = true;
			$.each(data,function(i,n){
				_empty = false;
				var mpn;
				if(n.metaProductName.length>=13){
					mpn = n.metaProductName.substring(0,10)+"...";
				}else{
					mpn =n.metaProductName;
				}
				var pn;
				if(n.productName.length>=13){
					pn = n.productName.substring(0,10)+"...";
				}else{
					pn =n.productName;
				}
				var readOnly = "";
				console.log(n.status);
				if( n.status  != 'NORMAL'){
					readOnly = "disabled = 'disabled'";
				}
				$("<tr orderItemMetaId=\""+n.orderItemMetaId+"\" class=\"list_contant list_contant01 order_serch_result_tr\"><td class=\"list_contant_border\">"+
				"<input name=\"settlementItemIds\" type=\"checkbox\" value=\""+n.settlementItemId+"\"    class=\"check_box_style check_box_style01\" "+readOnly+" />"+
				"</td>"+
				"<td class=\"list_contant_border\">"+n.orderId+"</td>"+
				"<td class=\"list_contant_border\" title=\""+n.productName+"\">"+pn+"</td>"+
				"<td class=\"list_contant_border\" title=\""+n.metaProductName+"\">"+mpn+"</td>"+
				"<td class=\"list_contant_border\">"+n.productQuantity+"</td>"+
				"<td class=\"list_contant_border\">"+n.quantity+"</td>"+
				"<td class=\"list_contant_border\">"+n.orderContactPerson+"</td>"+
				"<td class=\"list_contant_border\">"+n.visitTimeStr+"</td>"+
				"<td class=\"list_contant_border\">"+n.orderStatusName+"</td>"+
				"<td class=\"list_contant_border\">"+n.actualSettlementPriceYuan+"</td>"+
				"<td class=\"list_contant_border\">"+n.totalSettlementPriceYuan+"</td>"+
				"<td class=\"list_contant_border\">"+n.statusName+"</td></tr>").appendTo("#order_table");
			});
			if(_empty){
				$("<tr class=\"list_contant list_contant01 order_serch_result_tr\"><td class=\"list_contant_border\" colspan=\"12\">没有找到订单或订单不属于当前结算对象</td></tr>").appendTo("#order_table");
			}
			$("#order_table").parent("div").show();
		}
	});
	
	$("#checkbox_all").click(function(){
		if($(this).attr("checked")){
			$("input[name=settlementItemIds]").attr("checked",true);
		}else{
			$("input[name=settlementItemIds]").attr("checked",false);
		}
	});
	
	$("#add_order_button").click(function(){
		
		var params = $("#add_order_form").formSerialize();
		if(params == ""){
			$.msg("请选择增加的订单!");
			return;
		}
		var _button = $(this);
    	_button.attr("disabled", true);
    	_button.attr("val",_button.val());
    	_button.val("提交中...");
    	var _complete = function(){
    		_button.removeAttr("disabled");
    		_button.val(_button.attr("val"));
    	};
		var sid = $("#main_settlementId").val();
		var stype = $("#settlement_type").val();
		$.ajax({
				type: "POST",
				url: "addOrder.do?settlementId="+sid,
				data:params,
				complete:_complete,
				success:function(data){
					if(data == -1){
						$.msg("结算单已结算");
					}else if(data == 1){
						art.dialog.get("add-dialog").close();
						$("#result_table").GridUnload();
						$("#search_footer").hide();
						$.msg("新增成功，请重新查询数据",2500);
					}else{
						$.msg("新增失败",1500);
					}
				}
		});
	});
	
	$("#add_button").click(function(){
		$('#add_form').resetForm();
		$("#order_table").parent("div").hide();
		art.dialog({
			id:'add-dialog',
			title: "新增订单",
		    content: document.getElementById("add_div"),
		    lock:true
	    });
		
	});
	$("#del_button").click(function(){
		var ids = $("#result_table").jqGrid('getGridParam','selarrrow');
		var settlementId = $("#main_settlementId").val();
		if( ids.length <=0 ){
			$.msg("请选择删除的订单结算项",1500);
		}else{
			art.dialog({
				fixed: true,
				lock:true,
				title:"确认信息",
			    content: "确认要删除选中的订单结算项？",
			    cancelValue: '取消',
			    cancel: true,
			    okValue: '确定',
			    ok: function () {
					var params ={settlementItemIds:[],orderItemMetaIds:[],settlementId:settlementId};
					var j = 0;
					for(var i in ids){
						var rowData = $("#result_table").getRowData(ids[i]);
						params.orderItemMetaIds[j] = rowData.orderItemMetaId;
						params.settlementItemIds[j] = rowData.settlementItemId;
						j++;
					}
					if(params.orderItemMetaIds.length > 0){
						$.post(
								"removeSettlementItem.do",
								decodeURIComponent($.param(params,true)),
								deleteSettlementItemResponseHandler
						);
					}else{
						$.msg("请选择删除的订单结算项",1500);
					}
			    }
			});
		}
	});
	deleteSettlementItemResponseHandler = function(data){
		if(data == 1){
			$("#result_table").GridUnload();
			$("#search_footer").hide();
			$.msg("删除成功，请重新查询数据！",2500);
		}else if(data == -1){
			$.msg("删除失败，打款金额小于结算金额");
		}else if(data == -2){
			$.msg("删除失败，结算单已结算");
		}else{
			$.msg("返回结果异常");
		}
	}
	
	$("#record_button").click(function(){
		var id = $("#main_settlementId").val();
    	$.get("searchSettlementChange.do?settlementId="+id,
    			function(data){
    				$.record_dialog_callback(data,"paginationDiv");
    		});
    });
	$.validator.addMethod("amountfloatZero", function(value, element) {
		var reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
	    return reg.test(value) && parseFloat(value) >= 0;   
	  }, "请输入非负值的数字，最多只能有两位小数");   
	/**
	 * 添加修改结算单价表单验证
	 */
	$("#modify_price_form").validate({
		rules: {
			settlementPrice: {required:true,amountfloatZero:true},
			remark: "required"
		},
		messages: {
			settlementPrice: {required:"请输入修改后的实际结算价"},
			remark: "请输入修改原因"
		}
	});
	
	$("#modify_price").blur(function() {
		   var modifyPrice = parseFloat($(this).val());
		   var productPrices =  parseFloat($("#item_product_price").val());
		   $("#modify_price_tips").remove();
		   $(this).removeClass("warning");
		   if(modifyPrice > productPrices){
			   $(this).after('<span id="modify_price_tips" style ="height:20px;line-height:25px;margin-left:8px;color:red;">大于销售价格了</span>');
			   $(this).addClass("warning");
		   }
	});
	
	/**
	 * 添加修改结算单价表单验证
	 */
	$("#batch_modify_price_form").validate({
		rules: {
			metaProductId:{required:true},
			amount: {required:true,amountfloatZero:true},
			remark: "required"
		},
		messages: {
			metaProductId:"请输入产品ID",
			amount: {required:"请输入修改后的实际结算价"},
			remark: "请输入修改原因"
		}
	});
	
	/**
	 * 添加修改结算价AJAX提交表单
	 */
	$('#modify_price_form').ajaxForm({
		type: 'post',
		url:  'modifySettlementPrice.do?type=single&settlementId='+$("#main_settlementId").val(),
		success: function(data) {
			art.dialog.get("modify-price-dialog").close();
			if(data == -1){
				$.msg("结算单已结算",2500);
			}else if(data ==1 ){
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("修改成功，请重新查询数据！",2000);
			}else{
				$.msg("修改失败",2500);
			}
		}
	});
	$('#batch_modify_price_form').ajaxForm({
		type: 'post',
		url:  'batchModifySettlementPrice.do?settlementId='+$("#main_settlementId").val(),
		success: function(data) {
			art.dialog.get("batch-modify-price-dialog").close();
			if(data == 0){
				$.msg("结算单已结算");
			}else{
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("修改成功，请重新查询数据！",2500);
			}
		}
	});

	$("#batch_modify_price").blur(function() {
		   var modifyPrice = parseFloat($(this).val());
		   var productPrices =  parseFloat($("#item_product_prices").val());
		   $("#batch_modify_price_tips").remove();
		   $(this).removeClass("warning");
		   if(modifyPrice > productPrices){
			   $(this).after('<span id="batch_modify_price_tips" style ="height:20px;line-height:25px;margin-left:8px;color:red;">大于销售价格了</span>');
			   $(this).addClass("warning");
		   }
	});
	
	var refreshSumPrice = function(){
		var _form = $("#search_form");
		var queryString = _form.formSerialize();
		var _url = "sumprice.do?"+queryString;
		$.get(_url,function(data){$("#sumprice").html(data);});
	};
	
	$("#total_price").blur(function() {
		   var modifyPrice = parseFloat($(this).val());
		   var rid = $("#total_subSettlementItemId").val();
		   var rowData = $("#result_table").jqGrid("getRowData", rid);
		   $("#modify_total_price_tips").remove();
		   $(this).removeClass("warning");
		   if(modifyPrice > rowData.totalQuantity * rowData.productPrice){
			   $(this).after('<span id="modify_total_price_tips" style ="height:20px;line-height:25px;margin-left:8px;color:red;">大于销售价乘以总数了</span>');
			   $(this).addClass("warning");
		   }
	});
	
	/**
	 * 添加修改结算总价表单验证
	 */
	$("#modify_total_price_form").validate({
		rules: {
			settlementPrice: {required:true, amountfloatZero:true},
			remark: "required"
		},
		messages: {
			settlementPrice: {required:"请输入修改后的结算总价"},
			remark: "请输入修改原因"
		}
	});
	
	$('#modify_total_price_form').ajaxForm({
		type: 'post',
		url:  'modifySettlementPrice.do?type=total&settlementId='+$("#main_settlementId").val(),
		success: function(data) {
			art.dialog.get("modify-total-price-dialog").close();
			if(data == -1){
				$.msg("结算单已结算",2500);
			}else if(data ==1 ){
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("修改成功，请重新查询数据！",2000);
			}else{
				$.msg("修改失败",2500);
			}
		}
	});
	
	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];
	        var rowData = $("#result_table").jqGrid("getRowData", id);
	        if( parseFloat(rowData.totalSettlementPriceYuan) >= 0 ){
		        var operate = "<a href='#' class = 'modify_totalSettlementPrice' rowid = '" + id + "'>总价</a>" +
		        				"<a href='#' class = 'modify_settlementPrice' rowid = '" + id + "'>单个</a>" +
		        		"<a href='#' class = 'subsettlementitem_modify_batch' rowid = '" + id + "' prodid = '"+rowData.metaProductId+"'>批量</a>" ;
		        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	        }
	    }
	    $('.subsettlementitem_modify_batch').click(function(){
	    	var rowData = $("#result_table").jqGrid("getRowData", $(this).attr("rowid"));
			$("#batch_modify_price_form").resetForm();
			$("#batch_modify_price_tips").remove();
		    $("#batch_modify_price").removeClass("warning");
			$("#batch_modify_metaProductId").val($(this).attr("prodid"));
			$("#batch_modify_metaProduct").html($(this).attr("prodid"));
	    	$("#batch_modify_price").val(rowData.actualSettlementPriceYuan);
			$("#product_prices").html(accounting.formatMoney(rowData.productPriceYuan));
	    	art.dialog({
	    		fixed: true,
	    		id:"batch-modify-price-dialog",
	    		title: "批量修改实际结算单价",
	    	    content: document.getElementById("batch_modify_price_div"),
	    	    lock:true
	    	});
	    });
	    
	    $('.modify_settlementPrice').click(function(){
	    	$("#modify_price_form").resetForm();
	    	$("#modify_price_tips").remove();
		    $("#modify_price").removeClass("warning");
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#modify_price_settlementItemId").val(rid);
	    	$("#modify_price").val(rowData.actualSettlementPriceYuan);
			$("#product_price").html(accounting.formatMoney(rowData.productPriceYuan));
	    	art.dialog({
	    		fixed: true,
	    		id:"modify-price-dialog",
	    		title: "修改实际结算单价",
	    	    content: document.getElementById("modify_price_div"),
	    	    lock:true
	    	});
		});
	    
	    // 修改总价
	    $(".modify_totalSettlementPrice").click(function(){
	    	$("#modify_total_price_form").resetForm();
	    	$("#modify_total_price_tips").remove();
	    	$("#total_price").removeClass("warning");
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#total_price").val(rowData.totalSettlementPriceYuan);
	    	$("#total_settlementItemId").val(rid);
			
	    	art.dialog({
	    		fixed: true,
	    		id:"modify-total-price-dialog",
	    		title: "修改结算总价",
	    	    content: document.getElementById("modify_total_price_div"),
	    	    lock:true
	    	});
	    });
	    
	    $("#search_footer").show();
	};
	$("#export_button").click(function(){
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
	
	$("#search_button").click(function(){
		var ord_id  =$("input[name=orderId]").val();
		if(ord_id!=""){
			if(!/^[0-9\s]*$/.test(ord_id)){
				$.msg("订单号输入错误",1500);
				return false;
			}
		}

		if($("#serialNoCbx").attr("checked")){
			$("#serialNoInput").val(0);
		}else{
			$("#serialNoInput").val(1);
		}
		
		refreshSumPrice();
		var _form = $("#search_form");
		_form.attr("action",_form.attr("grid_action"));
		var config = {
				former: '#search_form',
				pager: '#pagebar_div',
				multiselect: true,
				colNames : ['结算项ID', 
				            '订单子子项ID',
				            '订单号',
				            '申请流水号', 
				            '通关码', 
				            '景区回调信息', 
				            '下单日期', 
				            '支付日期', 
				            '供应商ID', 
				            '供应商名称',
				            '产品ID', 
				            '产品名称',
				            '产品类别',
				            '打包数量',
				            '购买数量',
				            '总数',
				            '联系人',
				            '游玩时间',
				            '订单实收金额',
				            '实际结算价',
				            '结算总价',
				            '退款备注',
				            '销售价格'],
				colModel : [ {
					name : 'settlementItemId',
					index : 'set_settlement_Item_Id',
					align:"center",
					sorttype: "int",
					hidden:true
				},{
					name : 'orderItemMetaId',
					index : 'ORDER_ITEM_META_ID',
					align:"center",
					sorttype: "int",
					hidden:true
				},{
					name : 'orderId',
					index : 'ORDER_ID',
					align:"center",
					sorttype: "int",
					width:58
				},{
					name:'passSerialno',
					index:'PASS_SERIALNO',
					width:120, 
					sortable:false
				},{
					name:'passCode',
					index:'PASS_CODE',
					width:120, 
					sortable:false
				},{
					name:'passExtid',
					index:'PASS_EXTID',
					width:120, 
					sortable:false
				},{
					name:'orderCreateTimeStr',
					index:'ORDER_CREATE_TIME',
					width:120, 
					sorttype: "Date"
				},{
					name:'orderPaymentTimeStr',
					index:'ORDER_PAYMENT_TIME',
					width:120, 
					sorttype: "Date"
				}, {
					name : 'supplierId',
					align:"center",
					sortable:false,
					width:80
				}, {
					name : 'supplierName',
					align:"center",
					sortable:false,
					width:150
				}, {
					name : 'metaProductId',
					index : 'META_PRODUCT_ID',
					align:"center",
					sorttype: "int",
					width:78
				}, {
					name : 'metaProductName',
					index : 'META_PRODUCT_ID',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:350
				}, {
					name : 'metaBranchName',
					index : 'META_BRANCH_NAME',
					align:"center",
					sorttype: "string"
				}, {
					name : 'productQuantity',
					index : 'PRODUCT_QUANTITY',
					align:"center",
					sorttype: "int",
					width:68
				}, {
					name : 'quantity',
					index : 'QUANTITY',
					align:"center",
					sorttype: "int",
					width:68
				}, {
					name : 'totalQuantity',
					index : 'totalQuantity',
					align:"center",
					sorttype: "int",
					sortable:false,
					width:68
				}, {
					name : 'orderContactPerson',
					index : 'ORDER_CONTACT_PERSON',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:55
				}, {
					name : 'visitTimeStr',
					index : 'VISIT_TIME',
					align:"center",
					sorttype: "date",
					width:75
				}, {
					name : 'orderItemMetaPayedAmountYuan',
					index : 'ORDER_ITEM_META_PAYED_AMOUNT',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:58
				},{
					name : 'actualSettlementPriceYuan',
					index : 'ACTUAL_SETTLEMENT_PRICE',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:83
				}, {
					name : 'totalSettlementPriceYuan',
					index : 'TOTAL_SETTLEMENT_PRICE',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:62
				},
				{
					name:'refundMemo', 
					index : 'REFUND_MEMO',
					sortable:false, 
					width:150
				},
				{
					name:'productPriceYuan',
					index:'PRODUCT_PRICE',
					align:"center",
					formatter:"float",
					hidden: true,
					width:80
				}]
			};
		if($("#settlement_status").val() != "SETTLEMENTED"){
			$.extend(config,{gridComplete:gridcomplete});
		}
		$("#result_table").grid(config);
	});
});