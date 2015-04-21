$(function(){
	
	/**
	 * 加载数据.
	 * @param {Object} productId
	 * @param {Object} all 为true时加载全部的数据，为false时只加载列表.
	 */
	function loadSaleTime(productId,all){
		var $div;
		if(all){
			$div=$("#limitSaleDiv");
		}else{
			$div=$("#limitSaleDataDiv");
		}
		$div.empty().load("/super_back/prod/editSaleTime.do",{"productId":productId,"all":all},function(){
			if(all){
				showLimitSaleDiv();				
			}
		});
	}
	$("a.limtSale").click(function(){
		var result=$(this).attr("result");
		loadSaleTime(result,true);
	})
	var $limit_sale_dlg;
	function showLimitSaleDiv(){
		$limit_sale_dlg=$("#limitSaleDiv");
		$limit_sale_dlg.dialog({
			modal:true,
			title:"修改产品时间限制",
			width:750,
			height:400
		});
	}
	$("input.saveLimitSale").live("click",function(){
		var productId=$limit_sale_dlg.find("input[name=saleTime.productId]").val();
		var saleTime=$limit_sale_dlg.find("input[name=saleTime.limitSaleTime]").val();
		var limitVisitTime=$limit_sale_dlg.find("input[name=saleTime.limitVisitTime]").val();
		if($.trim(saleTime)==''){
			alert("限制销售开始时间不可以为空");
			return false;
		}
		if($.trim(limitVisitTime)==''){
			alert("限制销售游玩时间不可以为空.");
			return false;
		}
		
		var $form=$limit_sale_dlg.find("form[name=form1]");
		$.ajax({
			url:"/super_back/prod/saveSaleTime.do",
			data:$form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					loadSaleTime(productId,false)
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	$("input.saveLimitHourSale").live("click",function(){
		var productId=$limit_sale_dlg.find("input[name=saleTime.productId]").val();
		var HourStart=$limit_sale_dlg.find("input[name=saleTime.limitHourStart]").val();
		var HourEnd=$limit_sale_dlg.find("input[name=saleTime.limitHourEnd]").val();
		var reg = /^(([0-1][0-9])|(2[0-5])):[0-9]0$/;
		if($.trim(HourStart)==''){
			alert("限售开始时间不可以为空.");
			return false;
		}else{
			if (!reg.test(HourStart)) {
				alert("限售开始时间格式错误");
				return false;
			}
		}
		if($.trim(HourEnd)==''){
			alert("限售结束时间不可以为空.");
			return false;
		}else{
			if (!reg.test(HourEnd)) {
				alert("限售结束时间格式错误");
				return false;
			}	
		}
		
		var $form=$limit_sale_dlg.find("form[name=form2]");
		$.ajax({
			url:"/super_back/prod/saveSaleTime.do",
			data : $form.serialize(),
			type:"POST",
			success:function(dt){
				var data=eval("("+dt+")");
				if(data.success){
					loadSaleTime(productId,false);
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	
	$("a.deleteLimitSale").live("click",function(){
		var result=$(this).attr("result");
		$.post("/super_back/prod/deleteLimitSale.do",{"saleTime.limitSaleTimeId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#tr_limitSale_"+result).remove();
			}else{
				alert(data.msg);
			}
		});
	})
})