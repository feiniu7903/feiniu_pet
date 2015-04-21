
$(document).keydown(function (e ){
	var key = e.keyCode;
    if (key == 13) {
    	if(e.stopPropagation) { //W3C阻止冒泡方法
            e.stopPropagation();
        } else {
            e.cancelBubble = true; //IE阻止冒泡方法
        }
        return false;
    }
});

$(function(){
	Utils.setComboxDataSource("getFixedOptions.do","fixedSlct",false,undefined);
	Utils.setComboxDataSource("getCurrencyOptions.do","currencySlct",false,undefined);
	Utils.setComboxDataSource("getFixedOptions.do","incomingFixedSlct",false,undefined);
	
	$("#supplierInput").combox("getSupplierForAutocomplete.do");
	$("#supplierInput").change(function(){
		$("#targetSlct").val("");
	});
	$( "#supplierInput" ).autocomplete({
	   select: function(event, ui) { 
			Utils.setComboxDataSource("getTargetForAutocomplete.do?supplierId="
					+ ui.item.value, "targetSlct", true, undefined);
	   }
	});
	
	if($("#groupSettlementStatusHd").val() == "CHECKED"){
		$(".hidableClass").hide();
	}
	
	$("#addIncomingBtn").click(function(){
		cleanIncomingForm();
		popWin = art.dialog({
			fixed:true,
			lock:true,
			title:"附加收入",
			content:document.getElementById("addIncomingWin")
		});
	});
	//保存附加收入
	$("#saveIncomingBtn").click(function(){
		var tip = "";
		if(!/^\d+[\.]?\d{0,2}$/g.test($("#incomingAmountInpupt").val())){
			tip = tip + "金额输入错误！<br>";
		}
		if(tip.length > 0){
			art.dialog({
				fixed:true,
				lock:true,
				title:"提示",
				content:tip
			});
			return false;
		}
		$.post(
			"saveIncoming.do",
			{
				id:$("#incomingItemIdHd").val(),
				costsItemId:$("#incomingFixedSlct").val(),
				amount:$("#incomingAmountInpupt").val(),
				remark:$("#incomingRemarkInpupt").val()
			},
			function(data){
				data = eval("(" + data + ")");
				if(data.result != 'SUCCESS'){
					art.dialog({
						fixed:true,
						lock:true,
						title:'提示',
						content:'操作失败'
					});
					return false;
				}else{
					refreshPage();
					popWin.close();
					cleanIncomingForm();
				}
			}
		);
	});
	//弹出添加成本项窗口
	$("#addFixedBtn").click(function(){
		isNewfixed = true;
		cleanFixedPopForm();
		popWin = art.dialog({
			fixed: true,
			lock:true,
			title:"添加成本项",
		    content: document.getElementById('addFixedWin')
		});
	});
	
	//保存成本项
	$("#saveFixedBtn").click(saveFixed);
	function saveFixed(){
		var tip = "";
		if(!/^\d+[\.]?\d{0,2}$/g.test($("#fixedPriceInput").val())){
			tip = tip + "成本输入错误！<br>";
		}
		if(!/^\d*$/.test($("#fixedNumInput").val()) || $("#fixedNumInput").val() == ""){
			tip = tip + "数量输入错误！<br>";
		}
		if($("#supplierInput_val").length == 0 || $("#supplierInput_val").val() == ""){
			tip = tip + "未选择供应商！<br>";
		}
		if($("#targetSlct").length == 0 || $("#targetSlct").val() == ""){
			tip = tip + "未选择结算对象！<br>";
		}
		if(tip.length > 0){
			art.dialog({
				fixed:true,
				lock:true,
				title:"提示",
				content:tip
			});
			return false;
		}
		art.dialog({
			fixed: true,
			lock:true,
			title:"确认信息",
		    content: '确定保存？',
		    cancelValue: '取消',
		    cancel: true,
		    okValue: '确定',
		    ok: function () {
				var data = {};
				if(!isNewfixed){					//编辑
					data.itemId = editFixedItemId;
				}
				data.costsItem = $("#fixedSlct").val();
				data.bgCosts = $("#fixedPriceInput").val();
				data.quantity = $("#fixedNumInput").val();
				if($("#supplierInput_val").length > 0){
					data.supplierId = $("#supplierInput_val").val();
				}
				if($("#targetSlct").length > 0){
					data.targetId = $("#targetSlct").val();
				}
				data.paymentType = $("#paymentTypeSlct").val();
				data.remark = $("#fixedMemoInput").val();
				var cur = $("#currencySlct").val();
				data.currency = cur.substr(0,cur.indexOf("-"));
				data.exchangeRate = parseFloat(cur.substr(cur.indexOf("-") + 1));
				data.subtotalCostsFc = parseFloat($("#fixedNumInput").val()) * parseFloat($("#fixedPriceInput").val());
				data.subtotalCosts = (data.subtotalCostsFc * data.exchangeRate).toFixed(2);
				var arr = new Array();
				arr.push(data);
				$.post(
						'saveFinalFixedItem.do',
						{
							data:Utils.arrayToJson(arr)
						},
						function(rsp){
							rsp = eval("(" + rsp + ")");
							if(rsp.result != 'SUCCESS'){
								art.dialog(
									{
										fixed: true,
										lock:true,
										title:"提示",
									    content: '保存失败'
									}
								);
							}else{
								refreshPage();
							}
						}
				);
				cleanFixedPopForm();
		    }
		});
	}
});
//修改附加收入
function editIncomingHandler(id,costsItemId,amount,remark){
	$("#incomingItemIdHd").val(id);
	$("#incomingFixedSlct").val(costsItemId);
	$("#incomingAmountInpupt").val(amount);
	$("#incomingRemarkInpupt").val(remark);
	popWin = art.dialog({
		fixed:true,
		lock:true,
		title:"附加收入",
		content:document.getElementById("addIncomingWin")
	});
}
//删除附加收入
function deleteIncomingHandler(id){
	$.post(
		"deleteIncoming.do",
		{
			id:id
		},
		function(data){
			data = eval("(" + data + ")");
			if(data.result != 'SUCCESS'){
				art.dialog({
					fixed:true,
					lock:true,
					title:'提示',
					content:'操作失败'
				});
				return false;
			}else{
				refreshPage();
			}
		}
	);
}
//付款申请
function requirePayHandler (type,itemId,supplierId,subtotalCostsFc,subtotalCostsFc2){
	$("#reqType").val(type);
	$("#reqId").val(itemId);
	$("#subtotalCostsFc").val(subtotalCostsFc);
	$("#reqPayAmountInput").val("");
	$("#isUseAdvanceCkb").removeAttr("checked");
	if(typeof subtotalCostsFc2 != 'undefined'){
		$("#subtotalCostsSpan").text(subtotalCostsFc2);
	}else{
		$("#subtotalCostsSpan").text(subtotalCostsFc);
	}
	popWin = art.dialog({
		fixed: true,
		lock:true,
		title:"付款申请",
	    content: document.getElementById("reqPayWin")
	});
	getReqPayFormInfo(supplierId,itemId,type==1?"PRODUCT":"FIXED");
}

//保存付款申请
function saveReqPayHandler (){
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
	var type = $("#reqType").val();
	var url = "";
	if(type == 1){
		url = "requireFinalProductPay.do";
	}else{
		url = "requireFinalFixedPay.do";
	}
	$.post(
		url,
		{
			itemId:$("#reqId").val(),
			reqPayAmount:$("#reqPayAmountInput").val(),
			isUseAdvance:$("#isUseAdvanceCkb").attr("checked") == "checked"?'Y':'N'
		},
		function(data){
			data = eval("(" + data + ")");
			if(data.result != 'SUCCESS'){
				art.dialog({
					fixed: true,
					lock:true,
					title:"提示",
				    content: '操作失败'
				});
			}else{
				art.dialog({
					fixed: true,
					lock:true,
					title:"提示",
				    content: '操作成功'
				});
			}
			refreshPage();
		}
	);
}

//延迟
function delayPayHandler (type,itemId){
	$("#delayType").val(type);
	$("#delayId").val(itemId);
	$("#delayTimeInput").val("");
	$("#delayMemoInput").val("");
	popWin = art.dialog({
		fixed: true,
		lock:true,
		title:"延迟支付",
	    content: document.getElementById('delayPayWin')
	});
};

//保存延迟时间
function saveDelayHandler(){
	if($("#delayTimeInput").val() == ""){
		art.dialog({
			fixed:true,
			lock:true,
			title:'提示',
			content:"未选择延迟时间"
		});
		return false;
	}
	var url = "";
	if($("#delayType").val() == 1){
		url = "delayFinalProductPay.do";
	}else{
		url = "delayFinalFixedPay.do";
	}
	$.post(
		url,
		{
			id:$("#delayId").val(),
			time:$("#delayTimeInput").val(),
			memo:$("#delayMemoInput").val()
		},
		function(data){
			data = eval("(" + data + ")");
			if(data.result != 'SUCCESS'){
				art.dialog({
					fixed: true,
					lock:true,
					title:"提示",
				    content: '操作失败'
				});
			}else{
				art.dialog({
					fixed: true,
					lock:true,
					title:"提示",
				    content: '操作成功'
				});
			}
			popWin.close();
		}
	);
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

var isNewfixed = true;
var editFixedItemId;	//行id
var popWin;
//修改成本项
function editFixedHandler(current){
	var itemId=$(current).attr("item_id"),costsItemId=$(current).attr("costs_item"),bgCosts=$(current).attr("bg_costs"),quantity=$(current).attr("quantity");
	var currency=$(current).attr("currency"),exchangeRate=$(current).attr("exchangeRate"),supplierId=$(current).attr("supplier_id"),supplierName=$(current).attr("supplier_name");
	var targetId=$(current).attr("target_id"),targetName=$(current).attr("target_name"),paymentType=$(current).attr("payment_type");
	var remark=$(current).attr("remark"),payStatus=$(current).attr("pay_status");
	isNewfixed = false;
	editFixedItemId = itemId;
	$("#fixedSlct").val(costsItemId);
	var $children = $("#currencySlct").children();
	for(var i=0;i<$children.length;i++){
		if(currency == $children[i].value.substr(0, $children[i].value.indexOf("-"))){
			$("#currencySlct").val($children[i].value);
		}
	}
	$("#fixedPriceInput").val(bgCosts);
	$("#fixedNumInput").val(quantity);
	
	$("#supplierInput_val").attr('label',supplierName);
	$("#supplierInput_val").val(supplierId);
	$("#supplierInput").val(supplierName);
	
	Utils.setComboxDataSource("getTargetForAutocomplete.do?supplierId="
					+ supplierId, "targetSlct", true, targetId);
	
	$("#paymentTypeSlct").val(paymentType);
	$("#fixedMemoInput").val(remark);
	
	if("NOPAY" != payStatus){	//已发付款申请或付款，只能修改数量、单价和备注
		$("#fixedSlct").attr("disabled","disabled");
		$("#currencySlct").attr("disabled","disabled");
		$("#supplierInput").attr("disabled","disabled");
		$("#targetSlct").attr("disabled","disabled");
		$("#paymentTypeSlct").attr("disabled","disabled");
	}else{
		$("#fixedSlct").removeAttr("disabled");
		$("#currencySlct").removeAttr("disabled");
		$("#supplierInput").removeAttr("disabled");
		$("#targetSlct").removeAttr("disabled");
		$("#paymentTypeSlct").removeAttr("disabled");
	}
	
	popWin = art.dialog({
		fixed: true,
		lock:true,
		title:"修改成本项",
	    content: document.getElementById('addFixedWin')
	});
}
//删除成本项
function deleteFixedHandler(itemId){
	art.dialog({
		fixed: true,
		lock:true,
		title:"确认信息",
	    content: '确定删除？',
	    cancelValue: '取消',
	    cancel: true,
	    okValue: '确定',
	    ok: function () {
			$.post(
				"deleteFinalFixedItem.do",
				{
					id:itemId
				},
				function(data){
					data = eval("(" + data + ")");
					if(data.result != 'SUCCESS'){
						art.dialog({
							fixed: true,
							lock:true,
							title:"提示",
						    content: '操作失败'
						});
					}else{
						refreshPage();
					}
				}
			);
	    }
	});
}


var popWin;
function refreshPage(){
	window.location.href = "doFinalBudget.do?travelGroupCode=" + encodeURIComponent($("#groupCodeHd").val());
}
//清空附加收入表单
function cleanIncomingForm(){
	$("#incomingItemIdHd").val("");
	$("#incomingFixedSlct").val("");
	$("#incomingAmountInpupt").val("");
	$("#incomingRemarkInpupt").val("");
}
//清空固定成本项表单
function cleanFixedPopForm(){
	$("#fixedSlct").removeAttr("disabled");
	$("#currencySlct").removeAttr("disabled");
	$("#supplierInput").removeAttr("disabled");
	$("#targetSlct").removeAttr("disabled");
	$("#paymentTypeSlct").removeAttr("disabled");
	$("#fixedItemIdInput").val("");
	$("#fixedSlct").val("");
	$("#currencySlct").val("");
	$("#fixedPriceInput").val("");
	$("#fixedNumInput").val("");
	$("#supplierInput").val("");
	$("#supplierInput_val").val("");
	$("#supplierInput_val").attr("label","");
	$("#targetSlct").val("");
	$("#paymentTypeSlct").val("");
	$("#fixedMemoInput").val("");
}
function showLog(){
	jQuery("#logTbl").GridUnload();
	$.post(
		"getLogs.do",
		{
			type:'FINAL_BUDGET'
		},
		function(data){
			jQuery("#logTbl").jqGrid({
				datatype: "local",
				autowidth: true,
				height: 380,
				scrollOffset : 20,
			   	colNames:[
			   	          '用户名',
			   	          '操作',
			   	          '时间',
			   	          '内容'
			   	          ],
			   	colModel:[
			   	          {name:'operatorName',width:100},
			   	          {name:'logName',width:100},
			   	          {name:'createTimeStr',width:120},
			   	          {name:'content',width:150}
			   	]
			});
			data = eval(data);
			if(data != undefined && data.length > 0){
				for(var i=0;i < data.length;i++){
					jQuery("#logTbl").jqGrid('addRowData',i,data[i]);
				}
			}
			art.dialog({
				fixed: true,
				lock:true,
				title:"操作日志",
			    content: document.getElementById('logWin')
			});
		}	
	);
} 

function printGroupCostIncome(basePath,travelGroupCode){
	window.open(basePath+"/op/printGroupCostIncome.do?travelGroupCode="+encodeURIComponent(travelGroupCode));
} 
function printGroupSettle(basePath,travelGroupCode){
	window.open(basePath+"/op/printGroupSettle.do?travelGroupCode="+encodeURIComponent(travelGroupCode));
}

//查看产品明细  ,
function showProductDetails(prdBranchId,travelGroupCode){
	jQuery("#prdDetailTbl").GridUnload();
	
	jQuery("#prdDetailTbl").jqGrid(
		{ 
			url:'getProductOrderDetails.do?prdBranchId='+prdBranchId+'&travelGroupCode='+encodeURIComponent(travelGroupCode), 
			datatype: "json",
			colNames:['订单号','结算单价', '数量', '结算总价'], 
			colModel:[ {name:'orderId',index:'orderId',width:100}, 
			           {name:'settlePrice',index:'settlePrice',width:90,formatter:"number",formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}}, 
			           {name:'quantity',index:'quantity', width:100}, 
			           {name:'settleAmount',index:'settleAmount',formatter:"number",formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}}
			          ], 
           jsonReader: {
        	   repeatitems : false, 
        	   id: "0"
     	    },
			rowNum:10, 
			rowList:[10,20,30], 
			pager: '#prdPageDiv', 
			sortname: 'orderId', 
			viewrecords: true, 
			sortorder: "desc"
		}); 
	jQuery("#prdDetailTbl").jqGrid('navGrid','#prdPageDiv',{edit:false,add:false,del:false});
	
	art.dialog({
		fixed:true,
		lock:true,
		title:"产品成本",
		content:document.getElementById("prdDetailWin")
	});
}
//修改人数
function editActPersonsHandler(){
	$("#actAdultInput").val($("#actAdultSp").html());
	$("#actChildInput").val($("#actChildSp").html());
	$("#actPersonsSpan").text($("#actPersonsSp").html());
	art.dialog({
		fixed:true,
		lock:true,
		title:"人数修改",
		content:document.getElementById("editActPersonsWin")
	});
}
//计算人数
function personNumChangeHandler(){
	var adultNum = 0;
	var childNum = 0;
	if(/^\d*$/.test($("#actAdultInput").val()) && $("#actAdultInput").val() != ""){
		adultNum = parseInt($("#actAdultInput").val());
	}
	if(/^\d*$/.test($("#actChildInput").val()) && $("#actChildInput").val() != ""){
		childNum = parseInt($("#actChildInput").val());
	}
	$("#actPersonsSpan").html(adultNum + childNum);
}
//保存人数
function savePersonNumHandler(){
	var msg = "";
	if(!/^\d*$/.test($("#actAdultInput").val()) || $.trim($("#actAdultInput").val()) == ""){
		msg = msg + "成人数填写错误！<br />";
	}
	if(!/^\d*$/.test($("#actChildInput").val()) || $.trim($("#actChildInput").val()) == ""){
		msg = msg + "儿童数填写错误！<br />";
	}
	if(($("#actAdultInput").val() == "" || $("#actAdultInput").val() == "0") && ($("#actChildInput").val() == "" || $("#actChildInput").val() == "0")){
		msg = msg + "人数不能为空！<br />";
	}
	if(msg.length > 0){
		art.dialog({
			fixed:true,
			lock:true,
			title:"提示",
			content:msg
		});
		return false;
	}else{
		$("#editActPersonsForm").submit();
	}
}


