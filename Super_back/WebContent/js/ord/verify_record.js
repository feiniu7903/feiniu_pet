$(function(){
	
	/**
	 * 查询修改结算价历史记录
	 */
	$("#searchBtn").click(function(){
		var orderId = $("input[name=orderId]").val();
		if(orderId!=""){
			if(!/^[0-9\s]*$/.test(orderId)){
				alert("订单号输入错误");
				return false;
			}
		}
		
		var _form = $("#search_form");
		_form.submit();
	});

})

/**
 * 审核
 */
function doVerified(obj, status){
	var recordId = obj.id;
	
	var postUrl = "ord/doVerify.do";
	$.post(postUrl, {"recordId": recordId, "status": status}, function(dt){
		$("#searchBtn").click();
	});
}