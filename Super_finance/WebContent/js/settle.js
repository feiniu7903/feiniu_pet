
$(function() {
	
	$("#settlementTargetInput").combox("/finance/autocomplete/settlement_target.json");
	$("#metaProductInput").combox({
									source:"/finance/autocomplete/meta_product.json",
									select:function(event, ui){
										//获取采购产品类型
										var metaProductId = ui.item.value;
										$.post(
											"/finance/settlement/settle/getMetaBranchTypeByMetaProductId.json",
											{
												productId: metaProductId
											},
											function(data){
												$("#metaBranchTypeSelect").val('');
												$("#metaBranchTypeSelect").html("");
												$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
												for(var i in data){
													$('#metaBranchTypeSelect').append('<option value="' + data[i].value + '">' + data[i].label + '</option>');
												}
											}
										);
									}
	});
	$("#supplierInput").combox("/finance/autocomplete/supplier.json");
	
	Utils.setComboxDataSource("/finance/combox_data_source/getSettlementCompany.json","settlementCompanySelect");
	
	$("#payToFlag").val("TOLVMAMA");
	
	$("#search_settlement_item_form").validate({
        rules: {
        	ordId: {
        		number: true
		   }
        },
        messages: {
        	ordId: {
        		number:"请输入数字"
			}
		}
    });
});
//切换支付对象
function changePaymentTargetHandler(i){
	if(i == 0){
		if($("#payToFlag").val() != "TOLVMAMA"){
			$("#payToFlag").val("TOLVMAMA");
			$("#payToLvmamaTab").attr("class","current");
			$("#payToSupplierTab").attr("class","other_26");
			jQuery("#settlement_item_table").GridUnload();
			$("#gridDiv").hide();
		}
	}else if(i == 1){
		if($("#payToFlag").val() != "TOSUPPLIER"){
			$("#payToFlag").val("TOSUPPLIER");
			$("#payToSupplierTab").attr("class","current");
			$("#payToLvmamaTab").attr("class","other_26");
			jQuery("#settlement_item_table").GridUnload();
			$("#gridDiv").hide();
		}
	}
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

//查询结算队列项
function searchSettlementItemHandler(){
	if(!$("#search_settlement_item_form").validate().checkForm()){
		return false;
	}
	$("#gridDiv").show();
	$("#bankAccount").val(encodeURIComponent(encodeURIComponent($("#bankAccountInput").val())));	//加码
	if($("#isLvmamaTargetContainedCbx").attr("checked")){
		$("#isLvmamaTargetContainedInput").val(0);
	}else{
		$("#isLvmamaTargetContainedInput").val(1);
	}
	if($("#hasRefundedCbx").attr("checked")){
		$("#hasRefundedInput").val('1');
	}else{
		$("#hasRefundedInput").val('0');
	}
	if($("#metaProductInput_val").length == 0){
		$("#metaBranchTypeSelect").val("");
		$("#metaBranchTypeSelect").html("");
		$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
	}
	$("#statusNormal").val($("#statusNormalInput").attr("checked")?1 : 0);
	$("#statusDelaySettle").val($("#statusDelaySettleInput").attr("checked")?1 : 0);
	$("#statusNoSettle").val($("#statusNoSettleInput").attr("checked")?1 : 0);
	
	var url = "/finance/settlement/settle/searchSettlementQueueItem.json?" + $("#search_settlement_item_form").formSerialize();
	$("#search_settlement_item_form").attr("action",url);
	$("#settlement_item_table").grid({
		former:'#search_settlement_item_form',
		pager:'#pagebar_div',
	    mtype : "GET",
		colNames : [ 
		             '订单号', 
		             '销售产品ID', 
		             '销售产品', 
		             '采购产品ID', 
		             '采购产品', 
		             '产品类别',
		             '供应商ID',
		             '供应商名称',
		             '打包数量',
		             '结算对象',
		             '张数',
		             '取票人',
		             '游玩时间',
		             '下单时间',
		             '支付时间',
		             '实际履行时间',
		             '我方结算主体',
		             '结算价',
		             '实际结算价',
		             '结算总价',
		             '建议打款时间',
		             '退款备注',
		             '状态',
		             '状态编码',
		             '子子项ID',
		             '结算队列项ID',
		             '已打款金额',
		             '行号'
		             ],
		colModel : [ 
		             {name:'orderId', index:'orderId',sortable:true, width:80},
		             {name:'productId',index:'productId', sortable:false, width:120},
		             {name:'productName',index:'productName', sortable:false, width:200},
		             {name:'metaProductId',index:'metaProductId', sortable:true, width:120},
		             {name:'metaProductName', sortable:false, width:200},
		             {name:'branchTypeName', sortable:false, width:120},
		             {name:'supplierId',index:'supplierId', sortable:true, width:100},
		             {name:'supplierName',sortable:false, width:130},
		             {name:'productQuantity', sortable:false, width:100},
		             {name:'settlementTargetName',index:'settlementTargetName', sortable:false, width:200},
		             {name:'quantity', sortable:false, width:50},
		             {name:'pickTicketPerson', sortable:false, width:80},
		             {name:'visitDateStr',index:'visitDate', sortable:true, width:100},
		             {name:'createTimeStr',index:'createTime', sortable:true, width:100},
		             {name:'paymentTimeStr',index:'paymentTime', sortable:true, width:100},
		             {name:'performTimeStr',index:'performTime', sortable:false, width:140},
		             {name:'settlementCompanyName',sortable:false, width:160},
		             {name:'settlementPriceYuanStr', sortable:false, width:80},
		             {name:'realSettlementPriceYuanStr', sortable:false, width:120},
		             {name:'realSettlementAmountMoneyYuanStr', sortable:false, width:100},
		             {name:'suggestionPayTimeStr',index:'suggestionPayTime', sortable:false, width:140},
		             {name:'refundMemo', sortable:false, width:150},
		             {name:'statusName',index:'status', sortable:true, width:50},
		             {name:'status', sortable:true,hidden:true},
		             {name:'orderItemMetaId', sortable:false,hidden:true},
		             {name:'settlementQueueItemId', sortable:false,hidden:true},
		             {name:'payedAmountYuanStr',index:'payedAmount',width:120, sortable:true, hidden:false},
		             {name:'rowId',key:true, sortable:false,width:120,hidden:true}
		            ],
		autowidth: true,
		autoheight: true,
		multiselect: true
	});
	
	if($("#settlementTargetInput_val")){
		$("#settlementTargetInput").val($("#settlementTargetInput_val").attr("label"));
	}
	if($("#metaProductInput_val")){
		$("#metaProductInput").val($("#metaProductInput_val").attr("label"));
	}
	if($("#supplierInput_val")){
		$("#supplierInput").val($("#supplierInput_val").attr("label"));
	}
	
}
//不结
function noSettleHandler(){
	if(!validateHasSearched()){
		return;
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
	    	var grid = jQuery("#settlement_item_table");
			var ids = grid.jqGrid('getGridParam','selarrrow');
			if(ids.length == 0){
				$.msg('请选择结算项');
				return false;
			}
			//获取结算队列项ID数组
			var tipStr = "";
			var b = false;
			var arr = new Array();
			var j = 0;
			for(var i in ids){
				if((grid.getRowData(ids[i]).status == 'NORMAL' 
					|| grid.getRowData(ids[i]).status == 'PAUSE')
					&& parseFloat(grid.getRowData(ids[i]).payedAmountYuanStr.replace(',',"")) >= 0){
					arr[j] = grid.getRowData(ids[i]).settlementQueueItemId;
					j++;
				}else{
					b = true;
					tipStr = tipStr 
									+ '[订单号：' + grid.getRowData(ids[i]).orderId 
									+ ',采购产品ID:' + grid.getRowData(ids[i]).metaProductId 
									+ ',状态：' + grid.getRowData(ids[i]).statusName
									+ ',支付金额：' + grid.getRowData(ids[i]).payedAmountYuanStr
									+ ']<br>';
				}
			}
			if(b){
				$.msg("以下结算项：<br/>" + tipStr + "无法不结!");
				return false;
			}
			Utils.lockPage();
			$.post(
					"settle/noSettle.json",
					{
						params:arr.toString()
					},
					function(data){
						Utils.unlockPage();
						if(data != 'SUCCESS'){
							$.msg('操作失败');
						}else{
							$.msg('操作成功');
						}
						jQuery("#settlement_item_table").trigger("reloadGrid");	//刷新grid
					}
			);
	    }
	});
}
//缓结
function delaySettleHandler(){
	if(!validateHasSearched()){
		return;
	}
	art.dialog({
		id:'abc',
		fixed: true,
		lock:true,
		title:"确认信息",
	    content: '确定缓结？',
	    cancelValue: '取消',
	    cancel: true,
	    okValue: '确定',
	    ok: function () {
			var grid = jQuery("#settlement_item_table");
			var ids = grid.jqGrid('getGridParam','selarrrow');
			if(ids.length == 0){
				$.msg('请选择结算项');
				return false;
			}
			var neverSettleItems = "";
			var hasNeverSettleItem = false;
			for(var i in ids){
				var rowData = grid.getRowData(ids[i]);
				if(rowData.status == 'NEVER' || rowData.status == ''){		//不结和 不在结算队列项中的订单子子项 无法缓解
					hasNeverSettleItem = true;
					neverSettleItems = neverSettleItems 
									+ '[订单号：' + rowData.orderId 
									+ ',采购产品ID:' + rowData.metaProductId 
									+ ',状态：' + rowData.statusName
									+ ']<br>';
				}
			}
			if(hasNeverSettleItem){			//校验
				$.msg("以下结算项：<br/>" + neverSettleItems + "无法缓结!");
				return false;
			}
			
			//获取结算队列项ID数组
			var arr = new Array();
			for(var i in ids){
				arr[i] = grid.getRowData(ids[i]).settlementQueueItemId;
			}
			
			Utils.lockPage();
			$.post(
					"settle/delaySettle.json",
					{
						params:arr.toString()
					},
					function(data){
						Utils.unlockPage();
						if(data != 'SUCCESS'){
							$.msg('操作失败');
						}else{
							$.msg('操作成功');
						}
						jQuery("#settlement_item_table").trigger("reloadGrid");	//刷新grid
					}
			);
	    }
	});
}
//删除抵扣款
function deleteHandler(){
	if(!validateHasSearched()){
		return;
	}
	art.dialog({
		fixed: true,
		lock:true,
		title:"确认信息",
	    content: '确定删除？',
	    cancelValue: '取消',
	    cancel: true,
	    okValue: '确定',
	    ok: function () {
	    	var grid = jQuery("#settlement_item_table");
			var ids = grid.jqGrid('getGridParam','selarrrow');
			if(ids.length == 0){
				$.msg('请选择结算项');
				return false;
			}
			//提取抵扣款的结算队列项和不可删除的结算队列项
			var arr1 = new Array();
			var j = 0;
			var arr2 = new Array();	//记录不可删除的结算队列项
			var m = 0;
			for( var i in ids){
				if(parseFloat(grid.getRowData(ids[i]).payedAmountYuanStr.replace(',',"")) < 0){		
					arr1[j] = grid.getRowData(ids[i]).settlementQueueItemId;
					j++;
				}else{
					arr2[m] = grid.getRowData(ids[i]);
					m++;
				}
			}
			//存在不可删除的项
			if(arr2.length != 0){
				var s = "以下结算项不是抵扣款，无法删除!<br />";
				var n = 0;
				for(n in arr2){
					s = s 	+ '[订单号：' + arr2[n].orderId 
							+ ',采购产品ID:' + arr2[n].metaProductId 
							+ ']<br>';
				}
				$.msg(s);
				return false;
			}
			
			Utils.lockPage();
			$.post(
					"settle/deleteSettlementQueueItemForCharge.json",
					{
						params:arr1.toString()
					},
					function(data){
						Utils.unlockPage();
						if(data.version_error != undefined ){
							$.msg("结算单已被修改，无法修改金额！操作失败！");
							return false;
						}
						if(data != 'SUCCESS'){
							$.msg('操作失败');
						}else{
							$.msg('操作成功');
						}
						jQuery("#settlement_item_table").trigger("reloadGrid");	//刷新grid
					}
			);
	    }
	});
}
//生成结算单
function createSettlementHandler(){
	if(!validateHasSearched()){
		return;
	}
	var tipMsg = '确定结算？';
	var grid = jQuery("#settlement_item_table");
	var ids = grid.jqGrid('getGridParam','selarrrow');
	for(var i in ids){
		var rowData = grid.getRowData(ids[i]);
		if(parseFloat(rowData.payedAmountYuanStr.replace(',',"")) < 0){		//过滤不结状态的数据
			tipMsg = '选中的结算项中存在抵扣款！<br />生成结算单后，将无法删除抵扣款。<br />确定结算？';
			break;
		}
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
			var grid = jQuery("#settlement_item_table");
			var ids = grid.jqGrid('getGridParam','selarrrow');
			//校验
			if(ids.length == 0){
				$.msg('请选择结算项');
				return false;
			}
			//获取订单子子项ID数组、结算队列项ID数组
			var arr = new Array();
			var arr2 = new Array();
			var j = 0;
			var j2 = 0;
			for(var i in ids){
				var rowData = grid.getRowData(ids[i]);
				if(rowData.status != 'NEVER'){		//过滤不结状态的数据
					arr[j] = rowData.orderItemMetaId;
					if(rowData.settlementQueueItemId != ''){
						arr2[j2] = rowData.settlementQueueItemId;
						j2++;
					}
					j++;
				}
			}
			
			//结算
			if(arr.length > 0){
				Utils.lockPage();
				$.post(
						"settle/createSettlement.json",
						{
							metaItemIds:arr.toString(),
							queueItemIds:arr2.toString()
						},
						createSettlementResponseHandler
				);
			}else{
				$.msg('选中的结算项都不可结算');
			}
	    }
	});
}
//全部生成结算单
function createSettlementAllHandler(){
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
			var url = jQuery("#settlement_item_table").jqGrid('getGridParam','url');
			url = "settle/createSettlementAll.json" + url.substr(url.indexOf("?"));
			Utils.lockPage();
			$.post(
					url,
					createSettlementResponseHandler
			);
	    }
	});
}
//生成结算单返回结果处理
function createSettlementResponseHandler(data){
	Utils.unlockPage();
	if(data.version_error != undefined ){
		$.msg("结算单已被修改，无法合并！操作失败");
		return false;
	}
	if(data.result != 'SUCCESS'){
		var tmp = '操作失败<br />';
		if(data.errorCode == 2){
			tmp = tmp + '没有可结算的结算项！<br />';
		}
		if(data.errorCode == 3){
			var s = "<br />由于生成后的结算金额小于0，或者生成后结算金额小于已打款金额。<br />选择的结算项都无法结算：<br>";
			for(var i in data.filtedMap){
				s = s + "结算对象ID：" + i + "<br />";
				for(var j in data.filtedMap[i]){
					s = s + "[订单号：" + data.filtedMap[i][j].orderId 
					+ ";采购产品ID：" + data.filtedMap[i][j].metaProductId 
					+ ";已打款金额:" + (isNaN(data.filtedMap[i][j].payedAmount)?"":data.filtedMap[i][j].payedAmount/100)
					+ "]<br />";
				}
				
			}
			tmp = tmp + s;
		}
		$.msg(tmp);
	}else{
		var tmp = '操作成功!<br />';
		if(data.newSettlements && data.newSettlements.length > 0){
			tmp = tmp + '<br />共生成' + data.counter + '张结算单:<br />';
			var s = "";
			for(var i in data.newSettlements){
				s = s + "[结算单号：" + data.newSettlements[i].settlementId
						+ "]<br />";
			}
			tmp = tmp + s;
		}
		if(data.oldSettlements && data.oldSettlements.length > 0){
			tmp = tmp + '<br />共合并' + data.mergeCounter + '张结算单:<br />';
			var s = "";
			for(var i in data.oldSettlements){
				s = s + "[结算单号：" + data.oldSettlements[i].settlementId
						+ "]<br />";
			}
			tmp = tmp + s;
		}
		if(data.payedItems && data.payedItems.length > 0){
			var s = "<br />以下结算项已打款，放回原结算单：<br>";
			for(var i in data.payedItems){
				s = s + "[订单号：" + data.payedItems[i].orderId 
						+ ";采购产品ID：" + data.payedItems[i].metaProductId 
						+ ";结算对象ID:" + data.payedItems[i].settlementTargetId
						+ "]<br />";
			}
			tmp = tmp + s;
		}
		if(data.filtedMap){
			var s = "<br />由于生成后的结算金额小于0，或者生成后结算金额小于已打款金额。<br />以下对象的结算项无法结算：<br>";
			for(var i in data.filtedMap){
				s = s + "结算对象ID：" + i + "<br />";
				for(var j in data.filtedMap[i]){
					s = s + "[订单号：" + data.filtedMap[i][j].orderId 
					+ ";采购产品ID：" + data.filtedMap[i][j].metaProductId 
					+ ";已打款金额:" + (isNaN(data.filtedMap[i][j].payedAmount)?"":data.filtedMap[i][j].payedAmount/100)
					+ "]<br />";
				}
				
			}
			tmp = tmp + s;
		}
		//查看结算单按钮
		if(data.newSettlements && data.newSettlements.length > 0 
				|| data.oldSettlements && data.oldSettlements.length > 0){
			var href = '/finance/settlement/ordsettlement/index/';
			var i = 0;
			for(i in data.newSettlements){
				href = href + data.newSettlements[i].settlementId + ",";
			}
			i = 0;
			for(i in data.oldSettlements){
				href = href + data.oldSettlements[i].settlementId + ",";
			}
			href = href.substring(0, href.length - 1) + ".htm";
			if($("#isProxySaleHd").val() == 1){		//代售产品结算单
				href = href + "?from=op";
			}
			tmp = tmp + '<a style="text-decoration: underline;" href="' + href + '"> 查看结算单</a>';
		}
		$.msg(tmp);
	}
	jQuery("#settlement_item_table").trigger("reloadGrid");	//刷新grid
}
//验证是否有查询结果集
function validateHasSearched(){
	if(jQuery("#settlement_item_table").jqGrid('getGridParam','records') == 0
			|| jQuery("#settlement_item_table").jqGrid('getGridParam','records') == undefined){
		$.msg("请先查询结算项");
		return false;
	}
	return true;
}



