$(function(){
	var editSettlementDiv;
	$("a.editSettlement").live("click",function(){
		var supplierId=$(this).attr("data");
		editSettlementDiv=$("#editSettlement").showWindow({
			data:{
				"supplierId":supplierId
			}
		});
	});
	var addSettlementDiv;
	$("a.addSettmentBtn").live("click",function(){
		var supplierId=$(this).attr("data");
		addSettlementDiv=$("#addSettlementDiv").showWindow({
			width:1000,
			data:{"supplierId":supplierId}
		});
	});
	
	$("a.editSettlementBtn").live("click",function(){
		var targetId=$(this).attr("data");
		addSettlementDiv=$("#addSettlementDiv").showWindow({
			width:800,
			url:basePath+"/sup/target/toEditSettlement.do",
			data:{"targetId":targetId}
		});
	});
	
	$("input[name=settlementTarget.settlementPeriod]").live("change",function(){
		var val=$(this).val();
		$("input[name=settlementTarget.advancedDays]").attr("disabled",val!='PERORDER');
	});
	
	$("input.settlementSubmit").live("click",function(){
		var $form=$(this).parents("form");
		$form.validateAndSubmit(function($form,dt){
			var supplierId=$form.find("input[name=settlementTarget.supplierId]").val();
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				addSettlementDiv.dialog("close");
				$("#editSettlement").resetWindow({data:{"supplierId":supplierId}});
			}else{
				alert(data.msg);
			}
		},{
			onSubmit:function($form){
				if ($form.find("input[name='settlementTarget.settlementPeriod']:checked").val() == "PERORDER") {
					if ($.trim($form.find("input[name='settlementTarget.advancedDays']").val()) == "") {
						alert("提前结算天数不能为空！");
						return false;
					}
				}
				
				var bankAccountName=$form.find("input[name=settlementTarget.bankAccountName]").val();
				var bankName=$form.find("input[name=settlementTarget.bankName]").val();
				var bankAccount=$form.find("input[name=settlementTarget.bankAccount]").val();
				
				var alipayAccount=$form.find("input[name=settlementTarget.alipayAccount]").val();
				var alipayName=$form.find("input[name=settlementTarget.alipayName]").val();
				if (bankAccountName != "" || bankName != "" || bankAccount != "") {
					if (bankAccountName != "" && bankName != "" && bankAccount != "") {
					} else {
						alert("请填写完整开户信息！");
						return false;
					}
				} else {
					if (alipayAccount != "" || alipayName != "") {
						if (alipayAccount != "" && alipayName != "") {
						} else {
							alert("请填写完整支付宝信息！");
							return false;
						}
					} else {
						alert("请填写开户信息或者支付宝信息！");
						return false;
					}
				}
				
				
				
				if($.trim(bankAccount) != "") {
					if (isNaN($.trim(bankAccount))) {
						alert("开户账号格式错误！");
						return false;
					}
				}
				
				var contactListId=$form.find("input[name=contactListId]").val();
				if($.trim(contactListId)==''){
					alert("财务联系人不可以为空");
					return false;
				}
				return true;
			}
		});		
	});
	
	var $metaProductListDiv;
	$("a.showMetaProductListBtn").live("click", function(){
		var targetId = $(this).attr("data");
		$metaProductListDiv = $("#metaProductListDiv").showWindow({
			width: 800,
			url: "/super_back/meta/showRelateMetaProductList.do",
			data: {"targetId": targetId}
		});
	});
	
	$(".pagination a._page").live("click",function(){
		var tt=$(this).attr("tt");
		var page=$(this).attr("page");
		
		if(tt=='custom'){
			var tmp=$(".pagination input[name=page]").val();
			if($.trim(tmp)==''){
				alert("页码为空");
				return false;
			}
			page=parseInt(tmp);
			var total_page=parseInt($(".pagination input[name=page]").attr("totalPage"));
			if(isNaN(page)){
				alert("页码错误");
				return false;
			}
			if(page>total_page){
				alert("页数过大.");
				return false;
			}
		}
		var targetId = $("#targetId").val();
		if(targetId != null) {
			$.post("/super_back/meta/showRelateMetaProductList.do",{"targetId": targetId, "page": page},function(dt){
				$metaProductListDiv.html(dt);
			});
		} else {
			alert("结算对象id丢失！");
		}
	});
});