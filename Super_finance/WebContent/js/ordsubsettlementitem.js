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
	$("#add_form").validate({
		rules: {
			orderId: {required:true}
		},
		messages: {
			orderId: {required:"请输入订单号"}
		}
	});
	
	$('#add_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/detail/order/'+$("#main_settlementId").val()+'.json',
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
				var sqi = n.settlementQueueItemId == null ? "" : n.settlementQueueItemId; 
				$("<tr orderItemMetaId=\""+n.orderItemMetaId+"\" class=\"list_contant list_contant01 order_serch_result_tr\"><td class=\"list_contant_border\">"+
				"<input name=\"ids\" type=\"checkbox\" value=\""+n.orderItemMetaId+"_"+sqi+"\"  class=\"check_box_style check_box_style01\" />"+
				"</td>"+
				"<td class=\"list_contant_border\">"+n.orderId+"</td>"+
				"<td class=\"list_contant_border\" title=\""+n.productName+"\">"+pn+"</td>"+
				"<td class=\"list_contant_border\" title=\""+n.metaProductName+"\">"+mpn+"</td>"+
				"<td class=\"list_contant_border\">"+n.productQuantity+"</td>"+
				"<td class=\"list_contant_border\">"+n.quantity+"</td>"+
				"<td class=\"list_contant_border\">"+n.pickTicketPerson+"</td>"+
				"<td class=\"list_contant_border\">"+n.visitDateStr+"</td>"+
				"<td class=\"list_contant_border\">"+n.orderStatusStr+"</td>"+
				"<td class=\"list_contant_border\">"+n.settlementPrice+"</td>"+
				"<td class=\"list_contant_border\">"+n.settlementPriceSum+"</td>"+
				"<td class=\"list_contant_border\">"+n.settlementStatusStr+"</td></tr>").appendTo("#order_table");
			});
			if(_empty){
				$("<tr class=\"list_contant list_contant01 order_serch_result_tr\"><td class=\"list_contant_border\" colspan=\"12\">没有找到订单或订单不属于当前结算对象</td></tr>").appendTo("#order_table");
			}
			$("#order_table").parent("div").show();
		}
	});
	
	$("#checkbox_all").click(function(){
		if($(this).attr("checked")){
			$("input[name=ids]").attr("checked",true);
		}else{
			$("input[name=ids]").attr("checked",false);
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
		$.ajax({
				type: "POST",
				url: "/finance/settlement/ordsettlement/detail/order/add/"+sid+".json",
				data:params,
				complete:_complete,
				success:function(data){
					if(data == 0){
						$.msg("结算单已结算");
					}else if(data==-1){
						$.msg("选择的订单包含已付款且不是当前结算单");
					}else if (data == -2){
						$.msg("结算单打款金额与结算金额差额小于抵扣款，不能使用抵扣款");
					}else if (data == -3){
						$.msg("结算单结算金额小于抵扣款，不能使用抵扣款");
					}else if(data == 1){
						art.dialog.get("add-dialog").close();
						$("#result_table").GridUnload();
						$("#search_footer").hide();
						$.msg("新增成功，请重新查询数据",2500);
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
		if( ids.length <=0 ){
			$.msg("请选择删除的结算子单项",1500);
		}else{
			$("#del_form").resetForm();
			$("#del_form input[name='delId']").remove();
			$("#del_form input[name='subSettlementId']").remove();
			for(var i = 0;i < ids.length; i++ ){
				var rowData = $("#result_table").jqGrid("getRowData", ids[i]);
				var realItemPriceSum = rowData.realItemPriceSum;
				if(realItemPriceSum<0){
					$.msg("不能删除抵扣款");
					return;
				}
				var subsettlementId = rowData.subSettlementId;
				$("<input type = 'hidden' value='"+ids[i]+"' name = 'delId'/>").appendTo("#del_form");
				if($("#del_sei"+subsettlementId).length <=0){
					$("<input id = 'del_sei"+subsettlementId+"' type = 'hidden' value='"+subsettlementId+"' name = 'subSettlementId'/>").appendTo("#del_form");
				}
			}
			art.dialog({
				id:'delete-dialog',
				title: "删除结算子单",
			    content: document.getElementById("del_div"),
			    lock:true
		    });
		}
	});
	
	$('#del_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/detail/delete/'+$("#main_settlementId").val()+'.json',
		success: function(data) {
			art.dialog.get("delete-dialog").close();
			if(data == 0){
				$.msg("结算单已结算");
			}else if(data == 1){
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("删除成功，请重新查询数据！",2500);
			}else if(data == -1){
				$.msg("删除失败，打款金额小于结算金额");
			}else if(data == -2){
				$.msg("删除失败，结算单已结算");
			}else if(data == -3){
				$.msg("删除失败，结算单已确认");
			}else{
				$.msg("返回结果异常");
			}
		}
	});
	
	$("#record_button").click(function(){
		var type = $("#type").val();
		var id=0;
		if(type ==1 ){
			id = $("#main_settlementId").val();
		}else if(type ==2 ){
			id = $("#main_subSettlementId").val();
		}
    	$.get("/finance/settlement/ordsettlement/detail/record/"+type+"/"+id+".htm",
    			function(data){
    				art.dialog({
    					title: "流水记录",
    				    content: data,
    				    lock:true
    			});
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
			amountAfterChange: {required:true,amountfloatZero:true},
			remark: "required"
		},
		messages: {
			amountAfterChange: {required:"请输入修改后的实际结算价"},
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
			metaProductId:"请输入采购产品ID",
			amount: {required:"请输入修改后的实际结算价"},
			remark: "请输入修改原因"
		}
	});
	
	/**
	 * 添加修改结算价AJAX提交表单
	 */
	$('#modify_price_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/detail/modify/price/'+$("#main_settlementId").val()+'.json',
		success: function(result) {
			art.dialog.get("modify-price-dialog").close();
			var data = result.ordSettlementChange;
			if(result.res == 0){
				$.msg("结算单已结算");
			}else{
				//刷新结算价格
				var rowData = $("#result_table").jqGrid("getRowData", data.subSettlementItemId);
				var _sumprice  = parseFloat(data.amountAfterChange)*parseInt(rowData.quantity)*parseInt(rowData.productQuantity);
				$("#result_table").jqGrid('setRowData', data.subSettlementItemId, { realItemPrice: data.amountAfterChange,realItemPriceSum: _sumprice});
				$("#result_table").setSelection(data.supplierId);
				refreshSumPrice();
				$.msg("修改成功！",1500);
			}
		}
	});
	$('#batch_modify_price_form').ajaxForm({
		type: 'post',
		url:  '/finance/settlement/ordsettlement/detail/batchmodify/price/'+$("#main_settlementId").val()+'.json',
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
		var _url = "/finance/settlement/ordsettlement/detail/sumprice.json?"+queryString;
		$.get(_url,function(data){$("#sumprice").html(data);});
	};
	
//	$.validator.addMethod("checkTotalPrice", function(value, element) {
//		var rid = $("#total_subSettlementItemId").val();
//		var rowData = $("#result_table").jqGrid("getRowData", rid);
//		alert(rowData.totalQuantity * rowData.productPrice);
//		return value <= rowData.totalQuantity * rowData.productPrice;
//	}, "结算总价不能大于销售价乘以总数");   
	
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
			amountAfterChange: {required:true, amountfloatZero:true},
			remark: "required"
		},
		messages: {
			amountAfterChange: {required:"请输入修改后的结算总价"},
			remark: "请输入修改原因"
		}
	});
	
	var params = $("#modify_total_price_form").formSerialize();
	$('#modify_total_price_form').ajaxForm({
		type: 'post',
		data: params,
		url:  '/finance/settlement/ordsettlement/detail/modify/totalPrice/'+$("#main_settlementId").val()+'.json',
		success: function(data) {
			art.dialog.get("modify-total-price-dialog").close();
			if(data == 0){
				$.msg("结算单已结算");
			}else{
				$("#result_table").GridUnload();
				$("#search_footer").hide();
				$.msg("修改成功，请重新查询数据！",2000);
			}
		}
	});
	
	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];
	        var rowData = $("#result_table").jqGrid("getRowData", id);
	        if( parseFloat(rowData.realItemPriceSum) >= 0 ){
		        var operate = "<a href='#' class = 'subsettlementitem_total' rowid = '" + id + "'>总价</a>" +
		        				"<a href='#' class = 'subsettlementitem_modify' rowid = '" + id + "'>单个</a>" +
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
	    	$("#batch_modify_subSettlementId").val(rowData.subSettlementId);
	    	$("#batch_modify_price").val(rowData.realItemPrice);
			$("#item_product_prices").val(rowData.productPrice);
			$("#product_prices").html(accounting.formatMoney(rowData.productPrice));
	    	art.dialog({
	    		fixed: true,
	    		id:"batch-modify-price-dialog",
	    		title: "批量修改实际结算单价",
	    	    content: document.getElementById("batch_modify_price_div"),
	    	    lock:true
	    	});
	    });
	    
	    $('.subsettlementitem_modify').click(function(){
	    	$("#modify_price_form").resetForm();
	    	$("#modify_price_tips").remove();
		    $("#modify_price").removeClass("warning");
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#modify_subSettlementItemId").val(rid);
	    	$("#modify_orderItemMetaId").val(rowData.orderItemMetaId);
	    	$("#modify_amountBeforeChange").val(rowData.realItemPrice);
	    	$("#modify_subSettlementId").val(rowData.subSettlementId);
	    	$("#modify_price").val(rowData.realItemPrice);
			$("#item_product_price").val(rowData.productPrice);
			$("#product_price").html(accounting.formatMoney(rowData.productPrice));
	    	art.dialog({
	    		fixed: true,
	    		id:"modify-price-dialog",
	    		title: "修改实际结算单价",
	    	    content: document.getElementById("modify_price_div"),
	    	    lock:true
	    	});
		});
	    
	    // 修改总价
	    $(".subsettlementitem_total").click(function(){
	    	$("#modify_total_price_form").resetForm();
	    	var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	$("#total_price").val(rowData.realItemPriceSum);
	    	$("#total_amountBeforeChange").val(rowData.realItemPriceSum);
	    	$("#total_subSettlementItemId").val(rowData.subSettlementItemId);
	    	$("#total_subSettlementId").val(rowData.subSettlementId);
	    	$("#total_orderItemMetaId").val(rowData.orderItemMetaId);
	    	$("#total_settlementId").val($("#main_settlementId").val());
			$("#modify_total_price_tips").remove();
		    $("#total_price").removeClass("warning");
		    $("#totalQuantity").val(rowData.totalQuantity);
	    	
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
	
	$("#export_button2").click(function(){
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action")+"?exporttype=1");
		_form.submit();
	});
	
	$("#search_button").click(function(){
		var ord_id  =$("input[name=orderId]").val();
		if(ord_id!=""){
			if(!/^[0-9]*$/.test(ord_id)){
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
				colNames : ['itemid', 'orderItemMetaId','订单号','子单ID', '申请流水号', '通关码', '景区回调信息', '下单日期', '支付日期', '供应商ID', '供应商名称','销售产品ID', '销售产品','采购产品ID', '采购产品','采购产品类别','打包数量','购买数量','总数','取票人','游玩时间','实际履行时间','订单实收金额','结算价','实际结算价','结算总价','退款备注','销售价格','修改结算价'],
				colModel : [ {
					name : 'subSettlementItemId',
					index : 'subSettlementItemId',
					align:"center",
					sorttype: "int",
					hidden:true
				},{
					name : 'orderItemMetaId',
					index : 'orderItemMetaId',
					align:"center",
					sorttype: "int",
					hidden:true
				},{
					name : 'orderId',
					index : 'ORDERID',
					align:"center",
					sorttype: "int",
					width:58
				},{
					name : 'subSettlementId',
					index : 'SUBSETTLEMENTID',
					align:"center",
					sorttype: "int",
					width:58
				},{
					name:'serialNo',
					index:'serialNo',
					width:120, 
					sortable:false, 
					hidden:false
				},{
					name:'code',
					index:'code',
					width:120, 
					sortable:false, 
					hidden:false
				},{
					name:'extId',
					index:'extId',
					width:120, 
					sortable:false, 
					hidden:false
				},{
					name:'createDate',
					index:'createDate',
					width:120, 
					sortable:false, 
					hidden:false
				},{
					name:'payedTime',
					index:'createDate',
					width:120, 
					sortable:false, 
					hidden:false
				}, {
					name : 'supplierId',
					index : 'SUPPLIERID',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:80
				}, {
					name : 'supplierName',
					index : 'SUPPLIERNAME',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:150
				}, {
					name : 'productId',
					index : 'PRODUCTID',
					align:"center",
					sorttype: "int",
					width:78
				}, {
					name : 'productName',
					index : 'PRODUCTID',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:350
				}, {
					name : 'metaProductId',
					index : 'METAPRODUCTID',
					align:"center",
					sorttype: "int",
					width:78
				}, {
					name : 'metaProductName',
					index : 'METAPRODUCTID',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:350
				}, {
					name : 'branchName',
					index : 'BRANCHNAME',
					align:"center",
					sorttype: "string",
					sortable:false
				}, {
					name : 'productQuantity',
					index : 'productQuantity',
					align:"center",
					sorttype: "int",
					width:68
				}, {
					name : 'quantity',
					index : 'quantity',
					align:"center",
					sorttype: "int",
					width:68
				}, {
					name : 'totalQuantity',
					index : 'totalQuantity',
					align:"center",
					sorttype: "int",
					width:68
				}, {
					name : 'pickTicketPerson',
					index : 'pickTicketPerson',
					align:"center",
					sorttype: "string",
					sortable:false,
					width:55
				}, {
					name : 'visitDateStr',
					index : 'visitDate',
					align:"center",
					sorttype: "date",
					width:75
				}, {
					name : 'performTimeStr',
					index : 'performTime',
					align:"center",
					sorttype: "date",
					width:88
				}, {
					name : 'payedAmount',
					index : 'payedAmount',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:58
				}, {
					name : 'itemPrice',
					index : 'itemPrice',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:58
				}, {
					name : 'realItemPrice',
					index : 'realItemPrice',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:83
				}, {
					name : 'realItemPriceSum',
					index : 'realItemPriceSum',
					align:"center",
					sorttype: "float",
					formatter:"number",
					formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
					width:62
				},
				{
					name:'refundMemo', 
					sortable:false, 
					width:150
				},
				{
					name:'productPrice',
					index:'productPrice',
					align:"center",
					sortable:false,
					formatter:"number",
					hidden: true,
					width:80
				},
				{
					name:'operate',
					index:'Id',
					align:"center",
					sortable:false,
					width:100
				}]
			};
		if($("#settlement_status").val() != "SETTLEMENTED"){
			$.extend(config,{gridComplete:gridcomplete});
		}
		$("#result_table").grid(config);
	});
});