$(function(){
	$("#save_btn").click(function(){
		var params = $("#rate_form").formSerialize();
		var modifyRateArr = document.getElementsByName("modifyRate");
		// 用来判断校验是否通过
		var flag = true;
		for(var i=0; i<modifyRateArr.length; i++){
			// 判断是否为正数
			if(null != modifyRateArr[i].value && modifyRateArr[i].value != ""){
				flag = parseFloat(modifyRateArr[i].value) > 0;
				if(!flag){
			    	$.msg("请输入一个正数，最多只能有六位小数");
			    	return false;
			    }
				var reg = new RegExp("^[0-9]+(.[0-9]{1,6})?$", "g");
				flag = reg.test(modifyRateArr[i].value);
				if(!flag){
			    	$.msg("请输入一个正数，最多只能有六位小数");
			    	return false;
			    }
			}
		}
		
		if(flag){
			$.ajax({
				type: "POST",
				url: "/finance/rateset/doSaveOrUpdateRate.json",
				data:params,
				success:function(data){
					if(data==true){
						$.msg("汇率设置成功");
						setTimeout(function(){
							location.reload();
						}, 1000)
					}
				}
			});
		}
	});

	// 查看日志
	$('.ordsettlement_log').click(function(){
    	var id = $(this).attr("rowid");
    	Utils.showLog("FIN_EXCHANGE_RATE", id);
    });
	
});