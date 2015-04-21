
$(function() {
	
	$("#targetId").combox("/pet_back/finance/settlementInterface/searchSupplierSettleRule.do");
	$("#metaProductId").combox({
									source:"/pet_back/finance/settlementInterface/searchGoodsList.do",
									select:function(event, ui){
										//获取采购产品类型
										var metaProductId = ui.item.value;
										$.post(
											"/pet_back/finance/settlementInterface/searchGoodsTypeList.do",
											{
												metaProductId: metaProductId
											},
											function(data){
												$("#metaBranchTypeSelect").val('');
												$("#metaBranchTypeSelect").html("");
												$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
												var d = eval(data);
												$.each(d,function(i,n){
													if (n.branchId && n.branchName) {
														$('#metaBranchTypeSelect').append('<option value="' + n.branchId + '">' + n.branchName + '</option>');
													}
												})
											}
										);
									}
	});
	$("#supplierId").combox("/pet_back/finance/settlementInterface/searchSupplierList.do");
	
	$("#search_button").click(function(){
		var orderId  =$("input[name=orderId]").val();
		if(orderId!=""){
			if(!/^[0-9\s]*$/.test(orderId)){
				$.msg("订单号输入错误",1500);
				return false;
			}
		}
		$("#gridDiv").show();
		if($("#isLvmamaTargetContainedCbx").attr("checked")){
			$("#isLvmamaTargetContainedInput").val(1);
		}
		if($("#metaProductInput_val").length == 0){
			$("#metaBranchTypeSelect").val("");
			$("#metaBranchTypeSelect").html("");
			$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
		}
		$("#result_table").grid({
			former:'#search_settlement_item_form',
			pager:'#pagebar_div',
		    mtype : "GET",
			colNames : [ 
			             '结算项ID',
			             '订单号', 
			             '产品ID', 
			             '产品名称',  
			             '产品类别',
			             '供应商ID',
			             '供应商名称',
			             '打包数量',
			             '结算名称',
			             '张数',
			             '联系人',
			             '游玩时间',
			             '下单时间',
			             '支付时间',
			             '我方结算主体',
			             '实际结算价',
			             '结算总价',
			             '建议打款时间',
			             '退款备注',
			             '状态',
			             '状态(隐藏)',
			             '子子项ID'
			             ],
			colModel : [ 
			             {name:'settlementItemId', sortable:false,hidden:true},
			             {name:'orderId', index:'ORDER_ID',sorttype: "int", width:90},
			             {name:'metaProductId',index:'META_PRODUCT_ID', sorttype: "int", width:120},
			             {name:'metaProductName', index: "META_PRODUCT_NAME", sorttype: "string", width:200},
			             {name:'metaBranchName', index: "META_BRANCH_NAME", sorttype: "string", width:120},
			             {name:'supplierId',sortable:false,width:100},
			             {name:'supplierName',sortable:false,width:130},
			             {name:'productQuantity',index:'PRODUCT_QUANTITY', sorttype: "int", width:100},
			             {name:'targetName',sortable:false,  width:200},
			             {name:'quantity',index:'QUANTITY',sorttype: "int", width:50},
			             {name:'orderContactPerson', index:'ORDER_CONTACT_PERSON', sorttype: "string", width:80},
			             {name:'visitTimeStr',index:'VISIT_TIME',  sortable:true,width:100},
			             {name:'orderCreateTimeStr',index:'ORDER_CREATE_TIME', sortable:true,width:100},
			             {name:'orderPaymentTimeStr',index:'ORDER_PAYMENT_TIME', sortable:true,width:100},
			             {name:'companyName', sortable:false,width:160},
			             {name:'actualSettlementPriceYuan',index:'ACTUAL_SETTLEMENT_PRICE', sorttype: "float",formatter:"number",formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}, width:120},
			             {name:'totalSettlementPriceYuan',index:'TOTAL_SETTLEMENT_PRICE', sorttype: "float",formatter:"number",formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}, width:120},
			             {name:'suggestionPayTimeStr', sortable:false, width:140},
			             {name:'refundMemo', sortable:false, width:150},
			             {name:'statusName',index:'STATUS', sortable:true, width:50},
			             {name:'status', sortable:true,hidden:true},
			             {name:'orderItemMetaId', sortable:false,hidden:true}
			            ],
			multiselect: true
		});
		
	});
	
	//验证是否有查询结果集
	function validateHasSearched(){
		if(jQuery("#result_table").jqGrid('getGridParam','records') == 0
				|| jQuery("#result_table").jqGrid('getGridParam','records') == undefined){
			$.msg("请先查询结算项",1500);
			return false;
		}
		return true;
	}

	//按单结算时建议打款时间才可用
	function periodChangeHandler(){
		 if($("#settlementPeriodSelect").val() == 'PERORDER' || $("#settlementPeriodSelect").val() == ''){
	         $("#suggestionPayDateStart").removeAttr('disabled');
	         $("#suggestionPayDateEnd").removeAttr('disabled');
	    }else{
	        $("#suggestionPayDateStart").attr('disabled','disabled');
	        $("#suggestionPayDateEnd").attr('disabled','disabled');
	        $("#suggestionPayDateStart").val("");
	        $("#suggestionPayDateEnd").val("");
	    }
	}
	//不结
	$("#noSettleBtn").click(function(){
		if(!validateHasSearched()){
			return;
		}
		var grid = jQuery("#result_table");
		var ids = grid.jqGrid('getGridParam','selarrrow');
		if(ids.length == 0){
			$.msg('请选择结算项');
			return false;
		}
		art.dialog({
			fixed: true,
			lock:true,
			title:"确认信息",
		    content: '确定不结？',
		    cancelValue: '取消',
		    cancel: true,
		    okValue: '确定',
		    ok: function () {
				var tipStr = "";
				var b = false;
				//获取订单子子项ID数组、结算队列项ID数组
				var params ={settlementItemIds:[],orderItemMetaIds:[]};
				var j = 0;
				for(var i in ids){
					var rowData = grid.getRowData(ids[i]);
					if(rowData.status == 'NORMAL'){	
						params.orderItemMetaIds[j] = rowData.orderItemMetaId;
						params.settlementItemIds[j] = rowData.settlementItemId;
						j++;
					}else{
						b = true;
						tipStr = tipStr 
										+ '[订单号：' + rowData.orderId 
										+ ',产品ID:' + rowData.metaProductId 
										+ ',状态：' + rowData.statusName
										+ ']<br>';
					}
				}
				if(b){
					$.msg("以下结算项：<br/>" + tipStr + "无法不结!");
					return false;
				}
				$.post(
						"noSettle.do",
						decodeURIComponent($.param(params,true)),
						function(data){
							if(data != 'SUCCESS'){
								$.msg('操作失败',1500);
							}else{
								$.msg('操作成功',1500);
							}
							jQuery("#result_table").trigger("reloadGrid");	//刷新grid
						}
				);
		    }
		});
	});
	//生成结算单
	$("#settleBtn").click(function(){
		if(!validateHasSearched()){
			return;
		}
		var tipMsg = '确定结算？';
		var grid = jQuery("#result_table");
		var ids = grid.jqGrid('getGridParam','selarrrow');
		if(ids.length == 0){
			$.msg('请选择结算项',1500);
			return false;
		}
		art.dialog({
			fixed: true,
			lock:true,
			title:"确认信息",
		    content: tipMsg,
		    cancelValue: '取消',
		    cancel: true,
		    okValue: '确定',
		    ok: function () {
		    	//获取订单子子项ID数组、结算队列项ID数组
				var params ={settlementItemIds:[],orderItemMetaIds:[]};
				var j = 0;
				for(var i in ids){
					var rowData = grid.getRowData(ids[i]);
					if(rowData.status == 'NORMAL'){
						params.orderItemMetaIds[j] = rowData.orderItemMetaId;
						params.settlementItemIds[j] = rowData.settlementItemId;
						j++;
					}
				}
				//结算
				if(params.orderItemMetaIds.length > 0){
					$.post(
							"settle.do?settlementType="+$("#settlementType").val(),
							decodeURIComponent($.param(params,true)),
							createSettlementResponseHandler
					);
				}else{
					$.msg('选中的结算项都不可结算',1500);
				}
		    }
		});
	});
	//生成结算单返回结果处理
	function createSettlementResponseHandler(data){
		if(data.result == 'EMPTY'){
			$.msg('没有生成任何结算单!');
		}else if(data.result == 'SUCCESS'){
			var tmp = '生成结算单成功!<br />';
			if(data.newSettlement && data.newSettlement.length > 0){
				tmp = tmp + '<br />共生成' + data.newSettlement.length + '张结算单:<br />';
				var s = "";
				for(var i in data.newSettlement){
					s = s + "[结算单号：" + data.newSettlement[i]
							+ "]<br />";
				}
				tmp = tmp + s;
			}
			if(data.mergeSettlement && data.mergeSettlement.length > 0){
				tmp = tmp + '<br />共合并' + data.mergeSettlement.length + '张结算单:<br />';
				var s = "";
				for(var i in data.mergeSettlement){
					s = s + "[结算单号：" + data.mergeSettlement[i]
							+ "]<br />";
				}
				tmp = tmp + s;
			}

			//查看结算单按钮
			if(data.newSettlement && data.newSettlement.length > 0 
					|| data.mergeSettlement && data.mergeSettlement.length > 0){
				var href = '../settlement/index.do?settlementType='+$("#settlementType").val()+'&settlementId=';
				
				var i = 0;
				for(i in data.newSettlement){
					href = href + data.newSettlement[i] + ",";
				}
				i = 0;
				for(i in data.mergeSettlement){
					href = href + data.mergeSettlement[i] + ",";
				}
				href = href.substring(0, href.length - 1);
				
				tmp = tmp + '<a style="text-decoration: underline;" href="' + href + '"> 查看结算单</a>';
			}
			$.msg(tmp);
		}else{
			$.msg('生成结算单失败!',2500);
		}
		jQuery("#result_table").trigger("reloadGrid");	//刷新grid
	}
	//全部生成结算单
	$("#settleAllBtn").click(function(){
		if(!validateHasSearched()){
			return;
		}
		art.dialog({
			fixed: true,
			lock:true,
			title:"确认信息",
		    content: '确定全部结算？',
		    cancelValue: '取消',
		    cancel: true,
		    okValue: '确定',
		    ok: function () {
		    	var form_action = $("#search_settlement_item_form").attr("action");
		    	$("#search_settlement_item_form").attr("action","settleAll.do");
		    	$("#search_settlement_item_form").ajaxSubmit(function(data){
		    		createSettlementResponseHandler.call(this,data);
		    		$("#search_settlement_item_form").attr("action",form_action);
	    		}); 
		    }
		});
	});
});



