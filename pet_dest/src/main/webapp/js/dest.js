//搜索按钮的点击功能
function onclicksearch() {
	var $searchKey = $('#Key');
	var searchKeyVal = $.trim($('#searchKey').val());
	if (searchKeyVal == '' || searchKeyVal == null || searchKeyVal == '中文/拼音') {
		$searchKey.attr('style', 'border:1px solid red;');
		$searchKey.click();
		return false;
	}
	var _searchKeyVal = $.trim($('#searchKey').val()).toLowerCase();
	$searchKey.val(_searchKeyVal);
	if (_searchKeyVal == '' || _searchKeyVal == null || _searchKeyVal == '中文拼音') {
		$searchKey.attr('style', 'border:1px solid red;');
		$searchKey.click();
		return false;
	}
	location.href = 'http://www.lvmama.com/search/ticket/上海-'+_searchKeyVal
			+ '.html';
	return false;
	$('#searchForm').submit();
};

$("a[class='btn btn-w btn-small btn-orange']").live("click", function() {
		var preductId=$(this).attr("date_productId");
		var productType=$(this).attr("date_type");
		var prodBranchId=$(this).attr("date_prodBranchId"); 
		var subProductType=$(this).attr("date_subProductType");
		$("#buyNum").attr("name","buyInfo.buyNum.product_"+prodBranchId); 
		$("#buyNum").val(1);
		$("#productIdNew").val(preductId);
		$("#productType").val(productType);
		$("#productBranchIdHidden").val(prodBranchId);
		$("#subProductType").val(subProductType);
		var orderUrl;
			orderUrl ="http://www.lvmama.com/orderFill/ticket-"+preductId;
		$("#orderUrl").val(orderUrl);
		_flag = 0;
		$.ajax({
			async:false,
	        type: "get",
	        url: "http://ticket.lvmama.com/check/login.do",
	        dataType:"html",
	        success: function(data){
	                 if(data == "true"){
	                    var form = $("#orderFillForm");
	         			form.attr("action",orderUrl);
	         			form.get(0).submit();
	                 }
	                 else{
	         	    	showLogin(function(){$("#orderFillForm").attr("action",orderUrl).submit();});
	         	    };
	        }});
}); 

// 登陆后的回传操作
function longinCallback() {
	if(_flag==1){
		location.reload();
	}else{
		var orderUrl = $("#orderUrl").val();
		window.location.href = orderUrl;
	}	
};


