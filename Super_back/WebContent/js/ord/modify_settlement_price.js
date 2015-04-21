$(function(){
	var initProductBranch = function(metaProductId){
		$.post(
				"/super_back/meta/getMetaBranchJSON.do",
				{
					metaProductId: metaProductId
				},
				function(data){
					$("#metaBranchTypeSelect").val('');
					$("#metaBranchTypeSelect").html("");
					$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
					var d = eval("("+data+")");
					var branchId = $('#metaBranchTypeSelect').attr("branchId");
					if(typeof d.list != "undefined"){
						$.each(d.list,function(i,n){
							var selected_str = "";
							if(branchId!="" && n.branchId == branchId){
								selected_str= 'selected="selected"';
							}
							$('#metaBranchTypeSelect').append('<option '+selected_str+'  value="' + n.branchId + '">' + n.branchName + '</option>');
						})
					}
				}
			);
	}
	if($("#metaProductId").val()!=""){
		initProductBranch($("#metaProductId").val());
	}
	/**
	 * 供应商
	 */
	$("#supplierInput").jsonSuggest({
		url:"/super_back/supplier/searchSupplier.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#comSupplierId").val(item.id);
		}
	});
	
	/**
	 * 采购产品
	 */
	$("#metaProductInput").jsonSuggest({
		url:"/super_back/meta/searchMetaList.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#metaProductId").val(item.id);
			initProductBranch(item.id);
		}
	});
	
	/**
	 * 结算对象
	 */
	$("#settlementTargetInput").jsonSuggest({
		url:"/pet_back/sup/target/settlementSearch.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#comTargetId").val(item.id);
		}
	});
	
});

function validateOrderId(){
	var orderId = $("input[name=orderId]").val();
	var visitDateStart = $("input[name=visitDateStart]").val();
	var visitDateEnd = $("input[name=visitDateEnd]").val();
	var createOrderTimeBegin = $("input[name=createOrderTimeBegin]").val();
	var createOrderTimeEnd = $("input[name=createOrderTimeEnd]").val();
	var totalSettlePriceBegin = $(":text[name=totalSettlePriceBegin]").val();
	var totalSettlePriceEnd = $(":text[name=totalSettlePriceEnd]").val();
	var refundment = $("select[name=refundment]").val();
	if(null!=totalSettlePriceBegin && totalSettlePriceBegin!=''){
		if(!/^\d+$/.test(totalSettlePriceBegin)){
			alert("结算总价起始值请输入数字");
			return false;
		}else if(totalSettlePriceBegin>1000000){
			alert("结算总价起始值过大");
			return false;
		}
	}
	if(null!=totalSettlePriceEnd && totalSettlePriceEnd!=''){
		if(!/^\d+$/.test(totalSettlePriceEnd)){
			alert("结算总价结束值请输入数字");
			return false;
		}else if(totalSettlePriceEnd>1000000){
			alert("结算总价起始值过大");
			return false;
		}
	}
	var hasParam = false;
	
	if(orderId != null && orderId != ""){
		hasParam = true;
	}
	if(visitDateStart != null && visitDateStart != "" && visitDateEnd != null && visitDateEnd != ""){
		hasParam = true;
	}
	if(createOrderTimeBegin != null && createOrderTimeBegin != "" && createOrderTimeEnd != null && createOrderTimeEnd != ""){
		hasParam = true;
	}
	if((null!=totalSettlePriceEnd && totalSettlePriceEnd!='') || (null!=totalSettlePriceBegin && totalSettlePriceBegin!='')){
		hasParam = true;
	}
	if(!hasParam){
		alert("请输入订单号或游玩日期或下单时间！");
		return false;
	}
	
	if(orderId!=""){
		if(!/^[0-9\s]*$/.test(orderId)){
			alert("订单号输入错误");
			return false;
		}
	}
	
	var _form = $("#search_form");
	_form.submit();
}

