$(function(){
	/**
	 * 批量修改结算价
	 */ 
	$("#updateBatchPrice").click(function(){
		var basePath = this.alt;
		// 选中项的ids
		var checkall=document.getElementsByName("checkname");
		var ordItemIds = "";
		var metaProductId = "";
		var metaBranchId = "";
		var priceCur;
		for(var i=0; i<checkall.length; i++){
			if(checkall[i].checked){
				priceCur = checkall[i].alt;
				ordItemIds += checkall[i].value + ",";
				metaProductId += checkall[i].id + ",";
				metaBranchId += checkall[i].title + ",";
			}
		}
		// 没有选中项时给出提示
		if(ordItemIds == ""){
			alert("请选择订单项进行操作！");
			return;
		}
		
		var idArray = metaProductId.split(",");
		var branchIdArray = metaBranchId.split(",");
		// 当选中多个的时候判断选中项的采购产品是否一样
		for(var i=0; i<idArray.length-1; i++){
			if(i > 0){
				if(idArray[i] != idArray[i-1] || branchIdArray[i] != branchIdArray[i-1]){
					alert("请选择相同采购产品相同类别的订单子子项！");
					return;
				}
			}
		}
		
		// 弹出的层
		var $dlg=$("#update_div");
		$dlg.find("span.msg").empty();
		$dlg.find("span.msg_remark").empty();
		$dlg.find("input[name=settlementPrice2add]").val(priceCur);
		$dlg.find("input[name=priceBeforeUpdate]").val(priceCur);
		$dlg.find("span.type").text("修改结算单价：");

		var reg;
		var price;
		var remark;
		var reason2add;
		
		var submitUpdate = function(dialog1,dialog2){
			var priceBeforeUpdate = $dlg.find("input[name=priceBeforeUpdate]").val();
			var postUrl = basePath + "ord/batchUpdateSettlementPrice.do";
			$.post(postUrl, {"ordItemId4add": ordItemIds, 
							"settlementPrice4add": price,  
							"reason4add": reason2add, 
							"priceBeforeUpdate4add": priceBeforeUpdate, 
							"remark4add": remark}, function(dt){
				if(typeof dialog1 !="undefined" && dialog1.length !=0 ){
					dialog1.dialog("close");
				}
				if(typeof dialog2 !="undefined" && dialog2.length !=0 ){
					dialog2.dialog("close");
				}
				var resultData = eval("(" + dt + ")");
				if(resultData.result == 0){
					alert("结算价修改成功，存在已经结算打款的订单！");
				}else{
					alert("结算价修改成功！");
				}
				$("#searchBtn").click();
			});
		}
		$dlg.dialog({
			modal:true,
			title:"批量修改结算价",
			width:400,
			height:310,
			buttons:{				
				"确认":function(){
					reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
					price = $dlg.find("input[name=settlementPrice2add]").val();
					remark = $dlg.find("textarea[name=remark2add]").val(); 
					reason2add = $dlg.find("#reason2add").val();
					if(price == "" || price == null){
						$dlg.find("span.msg").text("请输入修改后的实际结算价");
						return;
					} else if(!reg.test(price) || parseFloat(price) < 0){
						$dlg.find("span.msg").text("请输入一个数字，最多只能有两位小数");
						return;
					} else {
						$dlg.find("span.msg").empty();
					}
					if(null == remark || remark == ""){
						$dlg.find("span.msg_remark").text("请填写备注");
						return;
					}else {
						$dlg.find("span.msg_remark").empty();
					}
					
					$.post(basePath + "ord/confirmSettltmentPrice.do?type=batchUpdate&changeType=UNIT_PRICE&updatePrice="+price, {"ordItemId4add": ordItemIds}, function(data){
						var resultData = eval("(" + data + ")");
						if(resultData.result == 1){
							var $confirm_dlg = $("#confirm_div");
							$confirm_dlg.dialog({
								modal:true,
								title:"大于销售价，确定修改吗?",
								width:200,
								height:110,
								buttons:{
									"确认":function(){
										submitUpdate($confirm_dlg,$dlg);
									},
									"取消":function(){
										$confirm_dlg.dialog("close");
										$dlg.dialog("close");
									}
								}
							});
						}else if(resultData.result == 2){
							submitUpdate($dlg);
						}else if(resultData.result == 3){
							$dlg.dialog("close");
							alert("存在已退款的订单，不允许批量修改！");
						}else if(resultData.result == 0){
							submitUpdate($dlg);
						}else if(resultData.result == 4){
							$dlg.dialog("close");
							alert("已存在此订单审核中的结算价修改历史，不能再次修改！");
						}
					});
				},
				
				"关闭":function(){
					$dlg.dialog("close");
				}
			}
		});
	});

	/**
	 * 全选
	 */
	$("#checkall").click(function(){
		if(this.checked){
			$("input[name='checkname']").each(function(){this.checked=true;}); 
		}else{ 
			$("input[name='checkname']").each(function(){this.checked=false;}); 
		} 
	});
	
});

// 修改结算价
function updatePrice(obj, type, basePath){
	var price = obj.name;
	var ordItemId = obj.id;
	var $dlg = $("#update_div");
	$dlg.find("span.msg").empty();
	$dlg.find("span.msg_remark").empty();
	var titleContent = "";
	if(type==1){
		$dlg.find("span.type").text("修改结算单价：");
		titleContent = "修改结算单价";
		$dlg.find("input[name=changeType2add]").val("UNIT_PRICE");
		$dlg.find("input[name=priceBeforeUpdate]").val(price);
		$dlg.find("input[name=totalPriceBeforeUpdate]").val(obj.alt);
	} else if(type==2){
		$dlg.find("span.type").text("修改结算总价：");
		titleContent = "修改结算总价";
//		if(null == price || price == 0){
//			alert(obj.alt);
//			price = obj.alt * obj.title;
//			alert(price);
//		}
		$dlg.find("input[name=changeType2add]").val("TOTAL_PRICE");
		$dlg.find("input[name=priceBeforeUpdate]").val(obj.alt);
		$dlg.find("input[name=totalPriceBeforeUpdate]").val(price);
	}
	$dlg.find("input[name=settlementPrice2add]").val(price);
	$dlg.find("input[name=ordItemId2add]").val(ordItemId);
	var changeType2add = $dlg.find("input[name=changeType2add]").val();
	var priceBeforeUpdate = $dlg.find("input[name=priceBeforeUpdate]").val();
	var totalPriceBeforeUpdate = $dlg.find("input[name=totalPriceBeforeUpdate]").val(); 
	
	var reg;
	var price;
	var oid;
	var remark;
	var reason2add;
	
	var submitUpdate = function(dialog1,dialog2){
		var postUrl = basePath + "ord/updateSettlementPrice.do";
		$.post(postUrl, {"ordItemId4add": ordItemId, 
						"settlementPrice4add": price, 
						"changeType4add": changeType2add, 
						"reason4add": reason2add, 
						"remark4add": remark, 
						"priceBeforeUpdate4add": priceBeforeUpdate}, function(dt){
			if(typeof dialog1 !="undefined" && dialog1.length !=0 ){
				dialog1.dialog("close");
			}
			if(typeof dialog2 !="undefined" && dialog2.length !=0 ){
				dialog2.dialog("close");
			}
			alert("结算价修改成功！");
			if($("#searchBtn").length!=0){
				$("#searchBtn").click();
			}else{
				$("#tags_title_2").click();
			}
		});
	}
	$dlg.dialog({
		modal:true,
		title:titleContent,
		width:400,
		height:310,
		buttons:{				
			"确认":function(){
						reg = new RegExp("^[0-9]+(.[0-9]{1,2})?$", "g");
						price = $dlg.find("input[name=settlementPrice2add]").val();
						oid = $dlg.find("input[name=ordItemId2add]").val();
						remark = $dlg.find("textarea[name=remark2add]").val();
						reason2add = $dlg.find("#reason2add").val();
						if(price == "" || price == null){
							$dlg.find("span.msg").text("请输入修改后的实际结算价");
							return;
						} else if(!reg.test(price) || parseFloat(price) < 0){
							$dlg.find("span.msg").text("请输入一个数字，最多只能有两位小数");
							return;
						} else {
							$dlg.find("span.msg").empty();
						}
						
						// 判断是否修改了价格
						if(type==1){
							if(priceBeforeUpdate == price){
								alert("未修改结算单价");
								return;
							}
						}else{
							if(totalPriceBeforeUpdate == price){
								alert("未修改结算总价");
								return;
							}
						}
						if(null == remark || remark == ""){
							$dlg.find("span.msg_remark").text("请填写备注");
							return;
						}else {
							$dlg.find("span.msg_remark").empty();
						}
						
						var postUrl =  basePath + "ord/confirmSettltmentPrice.do?changeType=" + changeType2add + "&updatePrice=" + price;
						$.post(postUrl, {"ordItemId4add": ordItemId}, function(data){
							var resultData = eval("(" + data + ")");
							if(resultData.result == 1){
								var $confirm_dlg = $("#confirm_div");
								$confirm_dlg.dialog({
									modal:true,
									title:"大于销售价，确定修改吗?",
									width:200,
									height:110,
									buttons:{
										"确认":function(){
											submitUpdate($confirm_dlg,$dlg);
										},
										"取消":function(){
											$confirm_dlg.dialog("close");
											$dlg.dialog("close");
										}
									}
								});
							}else if(resultData.result == 2){
								var $confirm_dlg = $("#confirm_div");
								$confirm_dlg.dialog({
									modal:true,
									title:"已经结算打款，确定修改吗?",
									width:200,
									height:110,
									buttons:{
										"确认":function(){
											submitUpdate($confirm_dlg,$dlg);
										},
										"取消":function(){
											$confirm_dlg.dialog("close");
											$dlg.dialog("close");
										}
									}
								});
							}else if(resultData.result == 0){
								submitUpdate($dlg);
							}else if(resultData.result == 4){
								$dlg.dialog("close");
								alert("已存在此订单审核中的结算价修改历史，不能再次修改！");
							}
							
						});
						
						
			},
			
			"关闭":function(){
				$dlg.dialog("close");
			}
		}
	});
}
//修改订单子子项上的结算属性，以便能生成结算子项
function update_travel_group_code_virtual(basePath, orderItemMetaId, orderId){
	var postUrl =  basePath + "/ord/settle/showGroupCodeStock.do";
	$.ajax({
		type:"POST",
		url:postUrl, 
		data:{recordId:orderItemMetaId,orderId:orderId}, 
		success:function (result) {
			$("#edit_settlement_div :input[name=travelGroupCode]").val(result.travelGroupCode);
			$("#edit_settlement_div :radio[name=virtual]").each(function(){
				if($(this).val()==(""+result.virtual)){
					$(this).attr({"checked":"true"});
				}
			});
			$("#edit_settlement_div").dialog({
				modal : true,
				title : "修改结算属性",
				width : 400,
				height : 310,
				buttons : {
					"确认" : function() {
						var postUrl = basePath
								+ "ord/settle/updateGroupCodeStock.do";
						$.post(
										postUrl,
										{
											"travelGroupCode" : $("#edit_settlement_div :input[name=travelGroupCode]").val(),
											"virtual" : $("#edit_settlement_div :radio[name=virtual]:checked").val(),
											"orderId" : orderId,
											"recordId" : orderItemMetaId
										}, function(data) {
											if (data.success == true) {
												alert("编辑成功");
											} else {
												alert("编辑失败");
											}
										});
					},
					"关闭" : function() {
						$("#edit_settlement_div").dialog("close");
					}
				}
			});
		}
	});
}

function create_settle_item_fun(basePath,orderItemMetaId){
	var postUrl =  basePath + "ord/hanlderCreateSettleItem.do?recordId=" + orderItemMetaId;
	$.post(postUrl, function(data){
		alert("发送消息成功");
	});
}