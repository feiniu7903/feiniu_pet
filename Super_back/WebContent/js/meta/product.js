var stch="false";
$(function(){
	$("#inputUserId").jsonSuggest({
		url:"/pet_back/perm_user/search_user.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=metaProduct.managerId]").val(item.id);
		}
	});
	
	  $("#goFlightInput").jsonSuggest({
			url : "/pet_back/place/placeFligthBysearch.do",
			maxResults : 10,
			width : 300,
			emptyKeyup : false,
			minCharacters : 1,
			onSelect : function(item) {
			$("#goFlight").val(item.id);
		}
	}).change(function() {
		if ($.trim($(this).val()) == "") {
			$("#goFlight").val("");
		}
	});
	  $("#backFlightInput").jsonSuggest({
			url : "/pet_back/place/placeFligthBysearch.do",
			maxResults : 10,
			width : 300,
			emptyKeyup : false,
			minCharacters : 1,
			onSelect : function(item) {
			$("#backFlight").val(item.id);
		}
	}).change(function() {
		if ($.trim($(this).val()) == "") {
			$("#backFlight").val("");
		}
	});
	    
	  
	  
	  
	
	/*$("#goFlightInput").jsonSuggest({
		url:"",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=metaProduct.goFlight]").val(item.id);
		}
	});
	
	$("#backFlightInput").jsonSuggest({
		url:"",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=metaProduct.backFlight]").val(item.id);
		}
	});
	*/
	
	
	$("input[name=metaProduct.subProductType]").change(function(){
		if(current_meta_product_type == 'OTHER'){
			var val=$(this).val();
			if(val == 'INSURANCE'){
				$('#insuranceDayDiv').show();
			}else{
				$('#insuranceDayDiv').hide();
				$("input[name=metaProduct.insuranceDay]").val('');
			}
		}else if(current_meta_product_type=='HOTEL'){
			var val=$(this).val()=='HOTEL_SUIT';
			if(val){
				$("input[name=metaProduct.nights]").show();
			}else{
				$("input[name=metaProduct.nights]").hide();
			}
		}
	});
	
	$("input[name=metaProduct.direction]").change(function() {
		var val = $(this).val();
		if(val == "SINGLE") {
			$("#backFlightSpan").hide();
			$("#flightDayTr").hide();
		} else {
			$("#backFlightSpan").show();
			$("#flightDayTr").show();
		}
	});
	
	$("input[name=metaProduct.days]").keyup(function() {
		var v = $.trim($(this).val());
		if (v.length > 0) {
			if (isNaN(v)) {
				alert("输入有误！");
				$(this).val("1");
				return;
			}
			if (v.indexOf(".") > 0) {
				alert("输入有误！");
				$(this).val("1");
				return;
			}
		}
		if(parseInt(v) < 1) {
			alert("行程天数不能小于一天");
			$(this).val("1");
			return;
		}
		$(this).val(v);
	});
	
	$(document).ready(function(){
		if($("input[name=metaProduct.subProductType][value=INSURANCE]").attr("checked")){
			$('#insuranceDayDiv').show();
		}
		if(current_meta_product_type=='HOTEL'){
			if($("input[name=metaProduct.subProductType][value=HOTEL_SUIT]").attr("checked")){
				$('input[name=metaProduct.nights]').show();
			}
		}
		
		if(current_meta_product_type=='TRAFFIC'){
			if($("input[name=metaProduct.direction][value=SINGLE]").attr("checked")) {
				$("#backFlightSpan").hide();
				$("#flightDayTr").hide();
			} else {
				$("#backFlightSpan").show();
				$("#flightDayTr").show();
			}
		}
	});
	
	$("input[name=metaProduct.supplierId]").change(function(){
		var supplierId=$(this).val();
		var $sel=$("select[name=metaProduct.contractId]");
		$sel.empty();
		if(supplierId!=undefined&&supplierId!=''){
			$.post("/pet_back/contract/searchJSON.do",{"supplierId":supplierId},function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					var list=data.list;
					for(var i=0;i<list.length;i++){
						var $opt=$("<option value='"+list[i].contractId+"'>"+list[i].contractNo+"</option>");
						$sel.append($opt);
					}
				}else{
					$sel.empty();
				}
			});
		}
	});
	
	$("#supplierText").jsonSuggest({
		url:"/super_back/supplier/searchSupplier.do",
		maxResults:10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=metaProduct.supplierId]").val(item.id);
			$("input[name=metaProduct.supplierId]").trigger("change");
		}
	});
	
	$(".saveForm").click(function(){
		if(stch=="true"){
			return;
		}
		var $form=$("form[name="+$(this).attr("ff")+"]");
		$("span.msg").empty();
		var supplierId=$form.find("input[name=metaProduct.supplierId]").val();
		if($.trim(supplierId)==''){
			alert("供应商不可以为空");
			return false;
		}
				
		var manager=$form.find("input[name=metaProduct.managerId]").val();
		if($.trim(manager)==''){
			alert("采购经理不可以为空");
			return false;
		}
		
		if($.trim($form.find("input[name=metaProduct.productName]").val())==''){
			alert("产品名称不可以为空");
			return false;
		}

		if($.trim($form.find("input[name=metaProduct.bizCode]").val())==''){
			alert("产品编号不可以为空");
			return false;
		}
		
		
		if(current_meta_product_type!='TRAFFIC'){//大交通不需要采购主体
			if($.trim($form.find("select[name=metaProduct.filialeName]").val())==''){
				alert("请选择采购主体");
				return false;
			}
		}

		if(current_meta_product_type=='ROUTE'||current_meta_product_type=='OTHER'||current_meta_product_type=='HOTEL' || current_meta_product_type=='TRAFFIC'){
		
			if($("#currencyType").val() != undefined){
				if($.trim($("#currencyType").val())==''){
					alert("币种不可以为空");
					return false;
				}
			}
		

			var tmp=$form.find("input[name=metaProduct.subProductType]:checked").val();
			if(tmp=='undefined'||$.trim(tmp)==''){
				alert("产品子类型不可以为空");
				return false;
			}
			
			if(current_meta_product_type == 'OTHER' && tmp == 'INSURANCE'){
				var insuranceDayValue = $form.find("input[name=metaProduct.insuranceDay]").val();
				if(insuranceDayValue == 'undefined'|| $.trim(insuranceDayValue)=='' || $.trim(insuranceDayValue)=='null'){
					alert("产品分类为保险时，保险期限不能为空");
					return false;
				}else{
					var re = /^[1-9]\d*$/;
					if (!re.test(insuranceDayValue)){
						$form.find("input[name=metaProduct.insuranceDay]").focus();
						//$form.find("input[name=metaProduct.insuranceDay]").val(0);
						alert("保险期限只能为大于0的整数");
						return false;
					}
				}
			}
			
		}
		var target=$("input[name=payToTarget]:checked").val();
		if(target=='undefined'||$.trim(target)==''){
			alert("支付对象不可以为空");
			return false;
		}
		
		var isResourceSendFax=$("input[name=metaProduct.isResourceSendFax]:checked").val()=='true';
		if(target=='TOSUPPLIER'&&!isResourceSendFax){
			alert("支付给供应商的，必须选择资源审核通过后发传真");
			return false;
		}
		
		if(current_meta_product_type == 'TICKET') {
			var lastTicketTime = $.trim($("input[name=metaProduct.lastTicketTimeHour]").val());
			if(lastTicketTime == "") {
				alert("最短换票间隔小时数不能为空");
				return false;
			} else {
				if(isNaN(lastTicketTime)) {
					alert("最短换票间隔小时数必须为大于等于0小于等于3的正数");
					return false;
				} else {
					var lastTicketTimeF = parseFloat(lastTicketTime);
					if(lastTicketTimeF < 0 || lastTicketTimeF > 3) {
						alert("最短换票间隔小时数必须为大于等于0小于等于3的正数");
						return false;
					} else {
						var index = lastTicketTime.lastIndexOf(".");
						if(index > 0 && index != lastTicketTime.length - 2) {
							alert("最短换票间隔小时数只能有一位小数位");
							return false;
						}
					}
				}
			}
			
			var lastPassTime = $.trim($("input[name=metaProduct.lastPassTimeHour]").val());
			if(lastPassTime == "") {
				alert("最晚入园前可售小时数不能为空");
				return false;
			} else {
				if(isNaN(lastPassTime)) {
					alert("最晚入园前可售小时数必须为大于等于0小于等于24的正数");
					return false;
				} else {
					var lastPassTimeF = parseFloat(lastPassTime);
					if(lastPassTimeF < 0 || lastPassTimeF > 24) {
						alert("最晚入园前可售小时数必须为大于等于0小于等于24的正数");
						return false;
					} else {
						var index = lastPassTime.lastIndexOf(".");
						if(index > 0 && index != lastPassTime.length - 2) {
							alert("最晚入园前可售小时数只能有一位小数位");
							return false;
						}
					}
				}
			}
		}
		
		if(current_meta_product_type == 'TRAFFIC') {
			var goFlight = $("input[name=metaProduct.goFlight]").val();
			var direction = $("input[name=metaProduct.direction]:checked").val();
			if($.trim(goFlight) == "") {
				alert("单程航班信息不能为空");
				return false;
			}
			
			if(direction == "ROUND") {
				var backFlight = $("input[name=metaProduct.backFlight]").val();
				if($.trim(backFlight) == "") {
					alert("往返航班信息不能为空");
					return false;
				}
				var days = $("input[name=metaProduct.days]").val();
				if($.trim(days) == "") {
					alert("行程天数不能为空");
					return false;
				}
				
			}
			
			var flightDays = $("input[name=metaProduct.days]").val();
			if($.trim(flightDays) == "") {
				alert("行程天数不能为空");
				return false;
			}
		}
		stch="true";	
		$.ajax({
			url:$form.attr("action"),
			type:"POST",
			data:$form.serialize(),
			success:function(dt){
			    stch = "false";
				var data=eval("("+dt+")");
				if(data.success){
					alert("保存成功");
					if(data.hasNew){
						current_meta_product_id=data.metaProductId;
					}		
					window.location.href="/super_back/meta/toEditProduct.do?metaProductId="+data.metaProductId;					
				}else{
					alert(data.msg);
				}
			}
		});
	});
})