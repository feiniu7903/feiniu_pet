var editable = true;
var isNeedCount = false;
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
	};
// 更新产品列表数据
function updateProductGrid() {
	jQuery("#productTbl").GridUnload();
	$.post("getProductByGroupCode.do", function(data) {
				jQuery("#productTbl").jqGrid({
					datatype : "local",
					autowidth : true,
					autoheight : true,
					scrollOffset : 0,
					colNames : ['分支ID', '预算成本项', '类别', '预算成本', '数量', '成人数',
							'儿童数', '币种', '汇率', '币种单位', '单项总成本(元)', '单项总成本(币种)',
							'供应商/ID', '结算对象/结算ID', '付款方式', '打款金额', '付款状态', 
							'是否计入总成本','是否计入总成本标识',
							'操作','销售产品子类型'],
					colModel : [{
								name : 'prdBranchId',
								hidden : true,
								key : true
							}, {
								name : 'productName',
								width : 100
							}, {
								name : 'prdBranchName',
								width : 100
							}, {
								name : 'bgCosts',
								width : 80,
								formatter:"number",
								formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
							}, {
								name : 'quantity',
								width : 60
							}, {
								name : 'audltQuantity',
								hidden : true
							}, {
								name : 'childQuantity',
								hidden : true
							}, {
								name : 'currencyName',
								width : 60
							}, {
								name : 'exchangeRate',
								width : 50
							}, {
								name : 'currencySymbol',
								hidden : true
							}, {
								name : 'subtotalCosts',
								width : 130,
								formatter:"number",
								formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
							}, {
								name : 'subtotalCostsFc',
								width : 130,
								formatter:"number",
								formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
							}, {
								name : 'supplierStr',
								width : 120
							}, {
								name : 'targetStr',
								width : 120
							}, {
								name : 'paymentTypeName',
								width : 80
							}, {
								name : 'payAmount',
								width : 80,
								formatter:"number",
								formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
							}, {
								name : 'payStatusName',
								width : 60
							}, { 
								name : 'isInCostStr', 
								width : 60
							}, {
								name : 'isInCost',
								hidden: true
							}, {
								name : 'opt',
								width : 100
							}, {
								name : 'subProductType',
								width : 100,
								hidden: true
							}]
				});
				var productData = eval(data);
				if (productData != undefined && productData.length > 0) {
					for (var i = 0; i < productData.length; i++) {
						var s = "";
						if (editable && productData[i].subtotalCostsFc > 0 && productData[i].isInCost == "Y") {
							var s1 = "<a href='#' onclick='requireProductPayHandler("
									+ productData[i].itemId
									+ ","
									+ productData[i].supplierId
									+ ","
									+ productData[i].subtotalCostsFc
									+ ")'>付款申请</a>";
							var s2 = "<a href='#' onclick='delayProductPayHandler("
									+ productData[i].itemId + ")'>延迟</a>";
							if ($("#groupSettlementStatusHd").val() == 'BUDGETED') { // 已做预算表
								if (productData[i].payStatus == 'NOPAY' 
									|| productData[i].payStatus == 'PARTREQPAY' 
									|| productData[i].payStatus == 'PARTPAY') {
									s = s + s1;
								} else if (productData[i].payStatus == 'REQPAY' || productData[i].payStatus == 'PARTREQPAY') {
									s = s + s2;
								}
							}
						}
						if(editable && productData[i].payStatus == 'NOPAY'){
							var s3 = ""; 
							if(productData[i].payStatus == 'NOPAY'){ 
								if(productData[i].isInCost == "Y"){ 
									s3 = "<a id='isInCost_" +productData[i].prdBranchId + "' href='javascript:void(0);' onclick='isInCostHandler(" + productData[i].prdBranchId + ")'>不计入总成本</a>"; 
								}else{ 
									s3 = "<a id='isInCost_" +productData[i].prdBranchId + "' href='javascript:void(0);' onclick='isInCostHandler(" + productData[i].prdBranchId + ")'>计入总成本</a>";
								} 
							}							 
							s = s + s3; 
						}
						productData[i].opt = s;
						jQuery("#productTbl").jqGrid('addRowData',
								productData[i].prdBranchId, productData[i]);
					}
				}
				$(".ui-jqgrid-bdiv").height("");
			});
}
// 更新固定成本Grid
function updateFixedGrid() {
	jQuery("#fixedTbl").GridUnload();
	$.post("getFixedItemByGroupCode.do", function(data) {
		jQuery("#fixedTbl").jqGrid({
			datatype : "local",
			autowidth : true,
			autohight : true,
			scrollOffset : 0,
			colNames : ['预算成本项', '预算成本项ID', '预算成本', '数量', '币种', '币种编码', '汇率',
					'单项总成本(元)', '单项总成本(币种)', '供应商/ID', '供应商ID', '供应商名称',
					'结算对象/ID', '结算对象ID', '结算对象名称', '付款方式', '付款方式编码', '打款金额',
					'付款状态', '备注', '操作', '成本项ID'],
			colModel : [{
						name : 'costsItemName',
						sortable : false,
						width : 150
					}, {
						name : 'costsItem',
						hidden : true
					}, {
						name : 'bgCosts',
						width : 90,
						formatter:"number",
						formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
					}, {
						name : 'quantity',
						width : 80
					}, {
						name : 'currencyName',
						width : 80
					}, {
						name : 'currency',
						hidden : true
					}, {
						name : 'exchangeRate',
						width : 80
					}, {
						name : 'subtotalCosts',
						width : 150,
						formatter:"number",
						formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
					}, {
						name : 'subtotalCostsFc',
						width : 160,
						formatter:"number",
						formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
					}, {
						name : 'supplierStr',
						width : 120
					}, {
						name : 'supplierId',
						hidden : true
					}, {
						name : 'supplierName',
						hidden : true
					}, {
						name : 'targetStr',
						width : 150
					}, {
						name : 'targetId',
						hidden : true
					}, {
						name : 'targetName',
						hidden : true
					}, {
						name : 'paymentTypeName',
						width : 150
					}, {
						name : 'paymentType',
						hidden : true
					}, {
						name : 'payAmount',
						width : 150,
						formatter:"number",
						formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
					}, {
						name : 'payStatusName',
						width : 150
					}, {
						name : 'remark',
						width : 150
					}, {
						name : 'opt',
						width : 150
					}, {
						name : 'itemId',
						width : 150,
						hidden : true
					}]
		});
		var fixedData = eval(data);
		if (fixedData != undefined && fixedData.length > 0) {
			for (var i = 0; i < fixedData.length; i++) {
				if (editable) {
					var s1 = "<a href='#' onclick='requireFixedPayHandler("
							+ fixedData[i].itemId + ","
							+ fixedData[i].supplierId + ","
							+ fixedData[i].subtotalCostsFc + ")'>付款申请</a>";
					var s2 = "<a href='#' onclick='delayFixedPayHandler("
							+ fixedData[i].itemId + ")'>延迟</a>";
					var s3 = "<a href='#' onclick='editFixedHandler("
							+ fixedData[i].itemId + ")'>修改</a>";
					var s4 = "<a href='#' onclick='deleteFixedHandler("
							+ fixedData[i].itemId + ")'>删除</a>";
					var s = "";
					if ($("#groupSettlementStatusHd").val() == 'BUDGETED') { // 已做预算
						var payStatus = fixedData[i].payStatus;
						if (payStatus == 'NOPAY'|| payStatus == 'PARTREQPAY' || payStatus == 'PARTPAY') {
							s = s + s1;
						}
						if (payStatus == 'REQPAY' || payStatus == 'PARTREQPAY') {
							s = s + s2;
						}
					}
					if (($("#groupSettlementStatusHd").val() == 'UNBUDGET' || $("#groupSettlementStatusHd")
							.val() == 'BUDGETED')
							&& fixedData[i].payStatus == 'NOPAY') {
						s = s + s3 + s4;
					}
					fixedData[i].opt = s;
				}
				jQuery("#fixedTbl").jqGrid('addRowData', fixedData[i].itemId,
						fixedData[i]);
			}
		}
		$(".ui-jqgrid-bdiv").height("");
		if (isNeedCount) {
			doCount();
		}

	});
}
$(function() {
	if ($("#groupSettlementStatusHd").val() != 'UNBUDGET' && $("#groupSettlementStatusHd").val() != 'BUDGETED') { // 已做实际成本，无法修改
		editable = false;
		$(".hidableClass").hide();
	}
	Utils.setComboxDataSource("getFixedOptions.do", "fixedSlct", false, undefined);
	Utils.setComboxDataSource("getCurrencyOptions.do", "currencySlct", false, undefined);

	$("#supplierInput").combox("getSupplierForAutocomplete.do");
	$("#supplierInput").change(function() {
				$("#targetSlct").val("");
			});
	$("#supplierInput").autocomplete({
		select : function(event, ui) {
			Utils.setComboxDataSource("getTargetForAutocomplete.do?supplierId="
					+ ui.item.value, "targetSlct", true, undefined);
		}
	});

	// 产品成本列表
	updateProductGrid();

	// 固定成本列表
	updateFixedGrid();

	// 计算
	$("#countBtn").click(function() {
		if (!/^\d*$/.test($("#bgPersonsInput").val())
				|| $("#bgPersonsInput").val() == ""
				|| parseFloat($("#bgPersonsInput").val()) < 1) {
			art.dialog({
						lock : true,
						fixed : true,
						title : "提示",
						content : "预算人数输入错误"
					});
			return false;
		}
		$("#saveFlag").val("false");
		doCount();
	});

	// 弹出添加成本项窗口
	$("#addFixedBtn").click(function() {
				isNewfixed = true;
				cleanFixedPopForm();
				$("#saveFixedAndContinueBtn").show();
				popWin = art.dialog({
							fixed : true,
							lock : true,
							title : "添加成本项",
							content : document.getElementById('addFixedWin')
						});
			});

	// 保存成本项
	$("#saveFixedBtn").click(saveFixed);
	$("#saveFixedAndContinueBtn").click(saveFixed);
	var isContinueAdd = false;
	function saveFixed() {
		var tip = "";
		if (!/^\d+[\.]?\d{0,2}$/g.test($("#fixedPriceInput").val())) {
			tip = tip + "成本输入错误！<br>";
		}
		if (!/^\d*$/.test($("#fixedNumInput").val())
				|| $("#fixedNumInput").val() == "") {
			tip = tip + "数量输入错误！<br>";
		}
		if ($("#supplierInput_val").length == 0
				|| $("#supplierInput_val").val() == "") {
			tip = tip + "未选择供应商！<br>";
		}
		if ($("#targetSlct").length == 0
				|| $("#targetSlct").val() == "") {
			tip = tip + "未选择结算对象！<br>";
		}
		if (tip.length > 0) {
			art.dialog({
						fixed : true,
						lock : true,
						title : "提示",
						content : tip
					});
			return false;
		}
		if ($(this).attr("id") == "saveFixedBtn") {
			isContinueAdd = false;
		} else {
			isContinueAdd = true;
		}
		art.dialog({
			fixed : true,
			lock : true,
			title : "确认信息",
			content : '确定保存？',
			cancelValue : '取消',
			cancel : true,
			okValue : '确定',
			ok : function() {
				isNeedCount = true;
				var data = {};
				if (!isNewfixed) { // 编辑
					data.itemId = editFixedItemId;
				} else {
					data.itemId = "new" + new Date().getTime();
				}
				data.costsItem = $("#fixedSlct").val();
				var ops = $("#fixedSlct").children();
				for (var i = 0; i < ops.length; i++) {
					if (ops[i].value == data.costsItem) {
						data.costsItemName = ops[i].text;
						break;
					}
				}
				data.bgCosts = $("#fixedPriceInput").val();
				data.quantity = $("#fixedNumInput").val();
				if ($("#supplierInput_val").length > 0) {
					data.supplierId = $("#supplierInput_val").val();
					data.supplierName = $("#supplierInput_val").attr("label");
					data.supplierStr = data.supplierName + "/"
							+ data.supplierId;
				}
				if ($("#targetSlct").length > 0) {
					data.targetId = $("#targetSlct").val();
					data.targetName = Utils.getSelectedLabel("targetSlct",data.targetId);
					data.targetStr = data.targetName + "/" + data.targetId;
				}
				data.paymentType = $("#paymentTypeSlct").val();
				var ops = $("#paymentTypeSlct").children();
				for (var i = 0; i < ops.length; i++) {
					if (ops[i].value == data.paymentType) {
						data.paymentTypeName = ops[i].text;
						break;
					}
				}
				data.remark = $("#fixedMemoInput").val();
				var cur = $("#currencySlct").val();
				data.currency = cur.substr(0, cur.indexOf("-"));
				data.exchangeRate = parseFloat(cur.substr(cur.indexOf("-") + 1));
				var ops = $("#currencySlct").children();
				for (var i = 0; i < ops.length; i++) {
					if (ops[i].value == cur) {
						data.currencyName = ops[i].text;
						break;
					}
				}
				data.subtotalCostsFc = parseFloat($("#fixedNumInput").val())
						* parseFloat($("#fixedPriceInput").val());
				data.subtotalCosts = (data.subtotalCostsFc * data.exchangeRate)
						.toFixed(2);

				if ($("#groupSettlementStatusHd").val() == 'UNBUDGET') { // 未做预算
					data.payAmount = 0;
					data.payStatusName = '未支付';
					var s3 = "<a href='#' onclick='editFixedHandler(\""
							+ data.itemId + "\")'>修改</a>";
					var s4 = "<a href='#' onclick='deleteFixedHandler(\""
							+ data.itemId + "\")'>删除</a>";
					data.opt = s3 + s4;
					if (isNewfixed) { // 新增
						jQuery("#fixedTbl").jqGrid('addRowData', data.itemId,
								data);
						doCount();
					} else { // 编辑
						jQuery("#fixedTbl").jqGrid('setRowData',
								editFixedItemId, data);
						doCount();
					}
					cleanFixedPopForm();
					$("#saveFlag").val("false");
					if (!isContinueAdd) {
						popWin.close();
					}
				} else { // 已做预算
					if (isNewfixed) {
						data.itemId = "";
					} else {
						data.itemId = editFixedItemId;
					}
					var arr = new Array();
					arr.push(data);
					$.post('saveFixedItem.do', {
								data : Utils.arrayToJson(arr)
							}, function(rsp) {
								rsp = eval("(" + rsp + ")");
								if (rsp.result != 'SUCCESS') {
									art.dialog({
												fixed : true,
												lock : true,
												title : "提示",
												content : '保存失败'
											});
								} else {
									updateFixedGrid();
									doCount();
									isNeedUpdateGroupBudget = true;
									if (!isContinueAdd) {
										popWin.close();
									}
								}
							});
				}
				
				cleanFixedPopForm();
			}
		});
	}
	// 保存预算单
	$("#saveBudgetBtn").click(function() {
		art.dialog({
			fixed : true,
			lock : true,
			cancel : true,
			title : '提示',
			content : "确定保存？",
			okValue : "确定",
			cancelValue : "取消",
			ok : function() {
				var tip = "";
				if (!/^\d*$/.test($("#bgPersonsInput").val())
						|| $("#bgPersonsInput").val() == "") {
					tip = tip + "预算人数输入错误！<br>"
				}
				if (isNaN(parseFloat($("#bgPersonsNumSp").text()))
						|| $("#bgPersonsNumSp").text() == "") {
					tip = tip + "未做计算！<br>"
				}
				if (tip.length > 0) {
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : tip
							});
					return false;
				}
				// 未做预算,保存产品是否计入总成本，且保存固定成本项
				var prdArr = new Array();
				var fixedArr = new Array();
				if ($("#groupSettlementStatusHd").val() == 'UNBUDGET') { 
					var ids1 = $("#productTbl").getDataIDs();
					for (var i in ids1) {
						var rowData = $("#productTbl").jqGrid("getRowData",ids1[i])
						prdArr.push({prdBranchId:rowData.prdBranchId,isInCost:rowData.isInCost});
					}
					var ids = $("#fixedTbl").getDataIDs();
					for (var i in ids) {
						var rowData = $("#fixedTbl").jqGrid("getRowData",ids[i]);
						rowData.opt = "";
						fixedArr.push(rowData);
					}
				}
				var s = window.Utils.arrayToJson(fixedArr);
				$.post("saveGroupBudget.do", {
							bgPersons : $("#bgPersonsNumSp").text(),
							salePrice : $("#salePriceSp").text(),
							bgTotalCosts : $("#bgTotalCostsSp").text(),
							bgPerCosts : $("#bgPerCostsSp").text(),
							bgIncoming : $("#bgIncomingSp").text(),
							bgProfit : $("#bgProfitSp").text(),
							bgProfitRate : $("#bgProfitRateSp").text(),
							prdList : Utils.arrayToJson(prdArr),
							fixedList : Utils.arrayToJson(fixedArr)
						}, function(data) {
							data = eval("(" + data + ")");
							if (data.result != 'SUCCESS') {
								art.dialog({
											fixed : true,
											lock : true,
											title : "提示",
											content : '保存失败'
										});
							} else {
								location.reload();
								$("#saveFlag").val("true");
								art.dialog({
									fixed : true,
									lock : true,
									title : "提示",
									content : '保存成功'
								});
							}
						});
			}
		});
	});

	// 保存延迟打款时间
	$("#saveDelayBtn").click(function() {
				if ($("#delayTimeInput").val() == "") {
					art.dialog({
								fixed : true,
								lock : true,
								title : '提示',
								content : "未选择延迟时间"
							});
					return false;
				}
				var url = "";
				if ($("#delayType").val() == 1) {
					url = "delayProductPay.do";
				} else {
					url = "delayFixedPay.do";
				}
				$.post(url, {
							id : $("#delayId").val(),
							time : $("#delayTimeInput").val(),
							memo : $("#delayMemoInput").val()
						}, function(data) {
							data = eval("(" + data + ")");
							if (data.result != 'SUCCESS') {
								art.dialog({
											fixed : true,
											lock : true,
											title : "提示",
											content : '操作失败'
										});
							} else {
								art.dialog({
											fixed : true,
											lock : true,
											title : "提示",
											content : '操作成功'
										});
							}
							if ($("#delayType").val() == 1) {
								updateProductGrid();
							} else {
								updateFixedGrid();
							}
							popWin.close();
						});
			});
});

//是否加入总成本
function isInCostHandler(branchId){
	var rowData = $("#productTbl").jqGrid("getRowData", branchId);
	if(rowData.isInCost == "Y"){
		rowData.isInCost = "N";
		rowData.opt = rowData.opt.replace("不计入总成本","计入总成本");
		rowData.isInCostStr = "否";
	}else if(rowData.isInCost == "N"){
		rowData.isInCost = "Y";
		rowData.opt = rowData.opt.replace("计入总成本","不计入总成本");
		rowData.isInCostStr = "是";
	}
	jQuery("#productTbl").jqGrid('setRowData',branchId, rowData);
	
	//已做预算，修改数据库中标识
	if($("#groupSettlementStatusHd").val() == 'BUDGETED'){
		$.post("isInCostBudget.do",
			{
				prdBranchId:branchId,
				isInCost:rowData.isInCost == "Y"?"N":"Y"
			},
			function(data){
				data = eval("(" + data + ")");
				if (data.result != 'SUCCESS') {
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : '操作失败'
							});
				} else {
					isNeedUpdateGroupBudget = true;
					doCount();
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : '操作成功'
							});
				}
			}
		);
	}else{	//做页面计算
		doCount();
	}
}

// 产品付款申请
function requireProductPayHandler(itemId, supplierId, subtotalCostsFc) {
	$("#reqType").val(1);
	$("#reqId").val(itemId);
	$("#subtotalCostsFc").val(subtotalCostsFc);
	$("#reqPayAmountInput").val(subtotalCostsFc);
	$("#isUseAdvanceCkb").removeAttr("checked");
	$("#subtotalCostsSpan").text(subtotalCostsFc);
	popWin = art.dialog({
				fixed : true,
				lock : true,
				title : "付款申请",
				content : document.getElementById("reqPayWin")
			});
	getReqPayFormInfo(supplierId,itemId,"PRODUCT");
};
// 产品延迟
function delayProductPayHandler(itemId) {
	$("#delayType").val(1);
	$("#delayId").val(itemId);
	cleanDelayTimeForm();
	popWin = art.dialog({
				fixed : true,
				lock : true,
				title : "延迟支付",
				content : document.getElementById('delayPayWin')
			});
};

// 固定成本付款申请
function requireFixedPayHandler(itemId, supplierId, subtotalCostsFc) {
	$("#reqType").val(2);
	$("#reqId").val(itemId);
	$("#subtotalCostsFc").val(subtotalCostsFc);
	$("#reqPayAmountInput").val(subtotalCostsFc);
	$("#isUseAdvanceCkb").removeAttr("checked");
	$("#subtotalCostsSpan").text(accounting.formatMoney(subtotalCostsFc));
	popWin = art.dialog({
				fixed : true,
				lock : true,
				title : "付款申请",
				content : document.getElementById("reqPayWin")
			});
	getReqPayFormInfo(supplierId,itemId,"FIXED");
};
// 保存催款申请
function saveReqPayHandler() {
	var tip = "";
	if (!/^\d+[\.]?\d{0,2}$/g.test($("#reqPayAmountInput").val())
			|| $("#reqPayAmountInput").val() == "") {
		tip = tip + "金额输入错误！<br>"
	}else if(parseFloat((parseFloat($("#subtotalCostsFc").val()) - parseFloat($("#reqPayAmountHd").val())).toFixed(2)) < parseFloat($("#reqPayAmountInput").val()).toFixed(2)) {
		tip = tip + "打款金额过大！<br>"
	}
	if (tip.length > 0) {
		art.dialog({
					fixed : true,
					lock : true,
					title : "提示",
					content : tip
				});
		return false;
	}
	var url = "";
	if ($("#reqType").val() == 1) {
		url = "requireProductPay.do";
	} else {
		url = "requireFixedPay.do";
	}
	$.post(url, {
				itemId : $("#reqId").val(),
				reqPayAmount : $("#reqPayAmountInput").val(),
				isUseAdvance : $("#isUseAdvanceCkb").attr("checked") == "checked"
						? 'Y'
						: 'N'
			}, function(data) {
				data = eval("(" + data + ")");
				if (data.result != 'SUCCESS') {
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : '操作失败'
							});
				} else {
					popWin.close();
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : '操作成功'
							});
				}
				if ($("#reqType").val() == 1) {
					updateProductGrid();
				} else {
					updateFixedGrid();
				}
			});
}
// 固定成本延迟
function delayFixedPayHandler(itemId) {
	$("#delayType").val(2);
	$("#delayId").val(itemId);
	cleanDelayTimeForm();
	popWin = art.dialog({
				fixed : true,
				lock : true,
				title : "延迟支付",
				content : document.getElementById('delayPayWin')
			});
}
// 查询催款所需信息（预付款余额、已催款金额等）
function getReqPayFormInfo(supplierId,itemId,type) {
	$("#saveReqPayBtn").attr("disabled","disabled");
	$.post("getReqPayFormInfo.do", {
				supplierId : supplierId,
				itemId:itemId,
				type:type
			}, function(data) {
				data = eval("(" + data + ")");
				if (data.result != 'SUCCESS') {
					art.dialog({
								fixed : true,
								lock : true,
								title : "提示",
								content : '查询供应商预付款失败'
							});
				} else {
					if(parseFloat($("#subtotalCostsFc").val()) - parseFloat(data.reqPayAmount) == 0){
						popWin.close();
						art.dialog({
							fixed: true,
							lock: true,
							title:"提示",
							content: "已经全部催款！"
						});
					}
					if(data.supAdvanceAmount == 0 || data.supAdvanceAmount == undefined){
						$("#isUseAdvanceTr").hide();
						$("#supAdvanceAmountTr").hide();
					}else{
						$("#supAdvanceAmountSpan").text(data.supAdvanceAmount);
						$("#isUseAdvanceTr").show();
						$("#supAdvanceAmountTr").show();
					}
					$("#reqPayAmountHd").val(data.reqPayAmount);
					$("#reqPayAmountInput").val((parseFloat($("#subtotalCostsFc").val()) - parseFloat(data.reqPayAmount)).toFixed(2));
					$("#saveReqPayBtn").removeAttr("disabled");
					
				}
			});
}
var isNeedUpdateGroupBudget = false;
var isNewfixed = true;
var editFixedItemId; // 行id
var popWin;
// 修改成本项
function editFixedHandler(itemId) {
	isNewfixed = false;
	editFixedItemId = itemId;
	var editFixedRowData = $("#fixedTbl").jqGrid("getRowData", itemId);
	$("#fixedSlct").val(editFixedRowData.costsItem);
	var $children = $("#currencySlct").children();
	for(var i=0;i<$children.length;i++){
		if(editFixedRowData.currency == $children[i].value.substr(0, $children[i].value.indexOf("-"))){
			$("#currencySlct").val($children[i].value);
		}
	}
	$("#fixedPriceInput").val(editFixedRowData.bgCosts);
	$("#fixedNumInput").val(editFixedRowData.quantity);

	$("#supplierInput_val").attr('label', editFixedRowData.supplierName);
	$("#supplierInput_val").val(editFixedRowData.supplierId);
	$("#supplierInput").val(editFixedRowData.supplierName);
	
	Utils.setComboxDataSource("getTargetForAutocomplete.do?supplierId="
					+ editFixedRowData.supplierId, "targetSlct", true, editFixedRowData.targetId);

	$("#paymentTypeSlct").val(editFixedRowData.paymentType);
	$("#fixedMemoInput").val(editFixedRowData.remark);

	$("#saveFixedAndContinueBtn").hide();

	popWin = art.dialog({
				fixed : true,
				lock : true,
				title : "修改成本项",
				content : document.getElementById('addFixedWin')
			});
}
// 删除成本项
function deleteFixedHandler(itemId) {
	art.dialog({
				fixed : true,
				lock : true,
				title : "确认信息",
				content : '确定删除？',
				cancelValue : '取消',
				cancel : true,
				okValue : '确定',
				ok : function() {
					isNeedCount = true;
					if ($("#groupSettlementStatusHd").val() == 'UNBUDGET') { // 未做预算
						$("#fixedTbl").jqGrid('delRowData', itemId, {
									reloadAfterSubmit : false
								});
						doCount();
					} else {
						$.post("deleteFixedItem.do", {
									id : itemId
								}, function(data) {
									data = eval("(" + data + ")");
									if (data.result != 'SUCCESS') {
										art.dialog({
													fixed : true,
													lock : true,
													title : "提示",
													content : '操作失败'
												});
									} else {
										updateFixedGrid();
										doCount();
										isNeedUpdateGroupBudget = true;
									}
								});
					}
				}
			});
}

// 计算
function doCount() {
	var personNum = parseFloat($("#bgPersonsNumSp").text());
	if ($("#bgPersonsInput").val() != "") {
		personNum = parseFloat($("#bgPersonsInput").val());
		$("#bgPersonsNumSp").text(personNum);
	}
	if(isNaN(personNum)){
		return;
	}

	var bgTotalCosts = 0; // 预计总成本
	// 产品成本
	var grid = jQuery("#productTbl");
	var dataIds = grid.getDataIDs();
	for (var i in dataIds) {
		var itemData = grid.getRowData(dataIds[i]);
		var num = parseFloat(itemData.audltQuantity)
				+ parseFloat(itemData.childQuantity);
		var num1 = parseFloat(personNum) / num;
		itemData.quantity = num1; // 数量
		itemData.subtotalCostsFcStr = (num1 * parseFloat(itemData.bgCosts))
				.toFixed(2)
				+ itemData.currencySymbol; // 单项总成本（币种）
		itemData.subtotalCosts = (num1 * parseFloat(itemData.bgCosts) * parseFloat(itemData.exchangeRate))
				.toFixed(2);// 单项总成本（元）
		if(itemData.isInCost == "Y"){
			bgTotalCosts = bgTotalCosts + parseFloat(itemData.subtotalCosts);
		}
		grid.setRowData(dataIds[i], itemData);
	}
	// 固定成本
	var ids = $("#fixedTbl").jqGrid("getDataIDs");
	for (var i in ids) {
		var item = $("#fixedTbl").jqGrid("getRowData", ids[i]);
		bgTotalCosts = bgTotalCosts + parseFloat(item.subtotalCosts);
	}
	$("#bgTotalCostsSp").text(bgTotalCosts.toFixed(2));
	$("#bgPerCostsSp").text((bgTotalCosts / parseFloat(personNum)).toFixed(2));

	var totalSaleAmount = parseFloat($("#bgPersonsInput").val())
			* parseFloat($("#groupSalePriceHd").val());
	$("#bgIncomingSp").text(totalSaleAmount.toFixed(2));

	$("#bgProfitSp").text((totalSaleAmount - bgTotalCosts).toFixed(2));
	$("#bgProfitRateSp").text(((totalSaleAmount - bgTotalCosts)
			/ totalSaleAmount * 100).toFixed(2));
	if(isNeedUpdateGroupBudget){
		updateGroupBudget();
		isNeedUpdateGroupBudget = false;
	}
};

//更新预算表计算结果
function updateGroupBudget(){
	$.post("saveGroupBudget.do", {
			bgPersons : $("#bgPersonsNumSp").text(),
			salePrice : $("#salePriceSp").text(),
			bgTotalCosts : $("#bgTotalCostsSp").text(),
			bgPerCosts : $("#bgPerCostsSp").text(),
			bgIncoming : $("#bgIncomingSp").text(),
			bgProfit : $("#bgProfitSp").text(),
			bgProfitRate : $("#bgProfitRateSp").text()
		});
}
// 清空固定成本项表单
function cleanFixedPopForm() {
	$("#fixedItemIdInput").val("");
	$("#fixedSlct").val("");
	$("#currencySlct").val("");
	$("#fixedPriceInput").val("");
	$("#fixedNumInput").val("");
	$("#supplierInput").val("");
	$("#supplierInput_val").val("");
	$("#supplierInput_val").attr("label", "");
	$("#targetSlct").val("");
	$("#paymentTypeSlct").val("");
	$("#fixedMemoInput").val("");
}
// 清空延迟时间form
function cleanDelayTimeForm() {
	$("#delayTimeInput").val("");
	$("#delayMemoInput").val("");
}

function goBack() {
	 
	if (($("#saveFlag").val() == 'false') || (($("#bgPersonsNumSp").text() != "" || $("#bgPersonsInput").val() != "") && parseInt($("#bgPersonsNumSp").text()) != parseInt($("#bgPersonsInput").val()))) {
		 var result = window.confirm("内容未保存，确定离开?");
		 if (result) {
			 window.history.back();
		 }
	} else {
		 window.history.back();
	}
}

function showLog() {
	jQuery("#logTbl").GridUnload();
	$.post("getLogs.do", {
				type : 'BUDGET'
			}, function(data) {
				jQuery("#logTbl").jqGrid({
							datatype : "local",
							autowidth : true,
							height : 380,
							scrollOffset : 20,
							colNames : ['用户名', '操作', '时间', '内容'],
							colModel : [{
										name : 'operatorName',
										width : 100
									}, {
										name : 'logName',
										width : 100
									}, {
										name : 'createTimeStr',
										width : 120
									}, {
										name : 'content',
										width : 150
									}]
						});
				data = eval(data);
				if (data != undefined && data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						jQuery("#logTbl").jqGrid('addRowData', i, data[i]);
					}
				}
				art.dialog({
							fixed : true,
							lock : true,
							title : "操作日志",
							content : document.getElementById('logWin')
						});
			});
}