var stchProd="false";
$(function(){
	$("input[name=channel]").change(function(){
		if($(this).val()=='TUANGOU'){
			$("input[name=product.groupMin]").attr("disabled",!$(this).attr("checked"));
			$("input[name=channel][value=FRONTEND]").removeAttr("checked");
		}else if($(this).val()=='FRONTEND'){
			$("input[name=channel][value=TUANGOU]").removeAttr("checked");
			$("input[name=product.groupMin]").attr("disabled",true);
		}
	});	
	
	
	$("input[name=product.isForegin]").click(function(){
		if("Y"==$(this).val()){
			$('select[name=product.regionName]').removeAttr("disabled");
		}else{
			$('select[name=product.regionName]').attr("disabled",true);
		}
	});
		
	
	$("input[name=product.subProductType]").change(function(){
		if(current_product_type=='ROUTE'){
			var val=$(this).val();
			var res=(val=='GROUP_FOREIGN'||val=='GROUP_LONG'||val=='FREENESS_LONG'||val=='FREENESS_FOREIGN');
			$("input[name=product.payDeposit]").attr("disabled",!res);
			
			
			res=res||(val=='SELFHELP_BUS'||val=='GROUP');
			
			$("input[name=product.travelGroupCode]").attr("disabled",!res);
			$("input[name=product.aheadConfirmHours]").attr("disabled",!res);
			
			if(has_self_pack=='false'){//只有非超级自由行需要该值
				res=(val=='FREENESS_LONG'||val=='FREENESS_FOREIGN');
				if(res){
					$("#qiFlagId").show();	
				}else{
					$("#qiFlagId").hide();
				}
			}
			//FREENESS_FOREIGN  GROUP_FOREIGN
			if("FREENESS_FOREIGN"==val || "GROUP_FOREIGN"==val ){
				$('select[name=product.regionName]').removeAttr("disabled");
			}else{
				$('select[name=product.regionName]').attr("disabled",true);
			}
			
			if("GROUP"==val || "GROUP_LONG"==val || "GROUP_FOREIGN"==val || "FREENESS_LONG"==val || "FREENESS_FOREIGN" ==val || "SELFHELP_BUS"==val || "FREENESS"==val){
				$('input[name=productEContract]').attr('checked','checked');
				$('#productEContract_content').show();
			}else{
				$('input[name=productEContract]').attr('checked','');
				$('#productEContract_content').hide();
			}
			$("input[name=product.groupType]").attr("disabled",val==='FREENESS');
			
			
			res = "FREENESS_FOREIGN"==val||"GROUP_FOREIGN"==val;
			if(res){
				$(".visa_document").show();
			}else{
				$(".visa_document").hide();
			}

		}else if(current_product_type=='OTHER'){
            if ($(this).val() == 'INSURANCE') {
                $("#applicableTravel").show();
            } else {
                $("#applicableTravel").hide();
            }
        }
	});
	
	$("input[name=product.isRefundable]").change(function() {
		var val = $(this).val();
		if(val == "Y") {
			$(".refundBlock").show();
			var checked = $("input[name=product.isManualBonus][value=Y]").attr('checked');
			if (checked) {
				$(".return_auto").hide();
				$(".return_person").show();
			} else {
				$(".return_auto").show();
				$(".return_person").hide();
			}
		} else {
			$(".refundBlock").hide();
		}
	});
	
	$("input[name=product.isManualBonus]").change(function() {
		var checked = $("input[name=product.isRefundable][value=Y]").attr('checked');
		if (!checked) {
			return;
		}
		var val = $(this).val();
		if(val == "Y") {
			$(".return_auto").hide();
			$(".return_person").show();
		} else {
			$(".return_auto").show();
			$(".return_person").hide();
		}
	});
	
	$(document).ready(function(){
		
		$("input[name=product.groupMin]").attr("disabled",!$("input[name=channel][value=TUANGOU]").attr("checked"));
		$("input[name=product.subProductType]:checked").trigger("change");
		$("input[name=product.isRefundable]:checked").trigger("change");
		$("input[name=product.isManualBonus]:checked").trigger("change");
		var is_econtract = $('input[name=productEContract]').attr('product_econtract');
		if(is_econtract=='false'){
			$('input[name=productEContract]').attr('checked','');
			$('#productEContract_content').hide();
		}
		
		if($("#product_isForeginY").attr('checked')){
			$('select[name=product.regionName]').removeAttr("disabled");
		}else if(null==$("#product_isForeginY").attr('checked')){
			if( $("#product_subProductTypeFREENESS_FOREIGN").attr('checked') || $("#product_subProductTypeGROUP_FOREIGN").attr('checked')){
				$('select[name=product.regionName]').removeAttr("disabled");
			}else{
				$('select[name=product.regionName]').attr("disabled",true);
			}
		}else{
			$('select[name=product.regionName]').attr("disabled",true);
		}
	});
	
	
	
	$("#inputUserId").jsonSuggest({
		url:"/pet_back/perm_user/search_user.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("input[name=product.managerId]").val(item.id);
		}
	});
		
	$(".saveForm").click(function(){
		if(stchProd=="true"){
			return;
		}
		var str=$(this).attr("ff");
		var $form=$("form[name="+str+"]");
		
		var productName = $form.find('#productName').val();
		if (productName.indexOf('\'') != -1 ||
			productName.indexOf('‘') != -1 ||
			productName.indexOf('’') != -1 ||
			productName.indexOf('"') != -1 ||
			productName.indexOf('”') != -1 ||
			productName.indexOf('“') != -1) {
			alert("产品名称不能含有('\"‘’“”)");
			return;
		}
		
		var validator=new ProductValidator($form,current_product_type,has_self_pack);
		if(!validator.validate()){
			return;
		}
		var newSubProductType=$("input[name='product.subProductType']:checked").val();
		var v_producttype=$("input[name='product.productType']").val();
		var oldSubProductType=$("#subProductType").val();
		var v_productId=$("#productId").val();
		
		if(v_producttype=="ROUTE"&&v_productId!=null&&v_productId!=""){
			if(newSubProductType!=oldSubProductType){
				if(!confirm("你更改了“产品类型”，请到“其他信息”中完善内容，因为系统已经自动清空之前的所有数据")){
					window.location.href="backend/prod/toEditProduct.do?productId="+$("#productId").val();
					return;
				}
			}
		}
		if((v_producttype=="HOTEL")&&v_productId!=null&&v_productId!=""){
			if(newSubProductType!=oldSubProductType && newSubProductType == "SINGLE_ROOM"){
				if(!confirm("你更改了“产品类型”，套餐原有的“其他信息”数据已经被自动清空")){
					return;
				}
			}
		}
		
		var sensitiveValidator=new SensitiveWordValidator($form, true);
		if(!sensitiveValidator.validate()){
			return;
		}
		
		if (!bounsInputValid($form)) {
			return;
		}
		
		var visaprod=$("input[name=has_visa_prod]").val();
		stchProd="true";
		$.ajax({
			url:$form.attr("action"),data:$form.serialize(),
			type:"POST",
			//dataType:'json',
			success:function(dt){
				stchProd="false";
				var data=eval("("+dt+")");
				if(data.success){
					/*if(data.isClearRoute=="Y"){
						alert("保存成功！如若你更改了“产品类型”，请到“其他信息”中完善内容，因为系统已经自动清空之前的所有数据。");
					}else{
						alert("保存成功");
					}*/
                    if(data.bindingInsurance=='Y'){
                        alert(data.bindingInsuranceInfo);
                    }

					alert("保存成功");
					if(data.hasNew){
						$("input[name=productId]").val(data.productId);
						current_product_id=data.productId;
						if(visaprod!='undefined' && $.trim(visaprod)!=""){
							window.location.href="/super_back/prod/editAsiaProduct.do?productId="+data.productId;	
							}else{
						     window.location.href="/super_back/prod/toEditProduct.do?productId="+data.productId;
						   }
					}
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	$("input[type=radio][name=product.isAperiodic]").click(function() {
		var value = $(this).val();
		if(value == "true") {
			//更换短信模板提示信息
			$("#aperiodicSpan").show();
			$("#normalSpan").hide();
			//不定期产品不能设置合同
			$("#contract_info_div_id").hide();
		} else {
			$("#aperiodicSpan").hide();
			$("#normalSpan").show();
			$("#contract_info_div_id").show();
		}
	});
	
	function bounsInputValid($form) {
		var isRefundable = $form.find('input[name="product.isRefundable"][value=Y]').attr('checked');
		if (isRefundable) {
			var isManualBonus = $form.find('input[name="product.isManualBonus"][value=Y]').attr('checked');
			if (isManualBonus) {
				var maxCashRefund = $form.find('input[name="product.maxCashRefund"]').val();
				if ($.trim(maxCashRefund) == '') {
					alert("返现金额不能为空");
					return false;
				} else {
					if (!/^[1-9]\d{0,}$/.test(maxCashRefund)) {
						alert("返现金额为大于0的正整数");
						return false;
					}
				}
				var reason = $form.find('textarea[name="product.bounsReason"]').val();
				if ($.trim(reason) == '') {
					alert("投放原因不能为空");
					return false;
				}
				var limit = $form.find('input[name="product.bounsLimitYuan"]').val();
				if ($.trim(limit) != '') {
					if (!/^\d+(.\d{1,2})?$/.test(limit)) {
						alert("投放金额为1-2位小数或正整数");
						return false;
					}
				} else {
					alert("投放金额不能为空");
					return false;
				}
				var bStart = $form.find('input[name="product.bounsStart"]').val();
				var bEnd = $form.find('input[name="product.bounsEnd"]').val();
				if ($.trim(bStart) == '' || $.trim(bEnd) == '') {
					alert("投放时长不能为空");
					return false;
				}
			} else {
				var scale = $form.find('input[name="product.bounsScale"]').val();
				if ($.trim(scale) == '') {
					alert("返现比例不能为空");
					return false;
				} else {
					if (!/^\d+(.\d)?$/.test(scale)) {
						alert("返现比例为一位小数或正整数");
						return false;
					}
				}
			}
		}
		return true;
	};
});