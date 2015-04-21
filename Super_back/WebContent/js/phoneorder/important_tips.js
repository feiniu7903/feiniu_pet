$(function() {
	$(".showImportantTips").click(function() {
		var productId = $(this).attr('productId');
		var prodBranchId = $(this).attr('prodBranchId');
		if(productId == null || productId == undefined) {
			alert("该产品id为空！");
			return false;
		} else {
			if (typeof($importantTipsDiv) == 'undefined') {
				$importantTipsDiv = $('<div></div>');
				$importantTipsDiv.appendTo($('body'));
			}
			$importantTipsDiv.empty().load('/super_back/phoneOrder/showImportantTipTabs.do', {
				pageId : productId,
				prodBranchId : prodBranchId
			}, function() {
				$importantTipsDiv.dialog( {
					title : "产品信息",
					width : 1000,
					height: 800,
					modal : true
				})
			});
		}
	});
});