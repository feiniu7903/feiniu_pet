$(function(){
	$("a.showDetail").click(function(){
		var supplierId=$(this).attr("data");
		$("#supplierDetail").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	
	$("input.addNewSupplier").click(function(){
		$("#editSupplier").showWindow();
	});
	
	$("a.editSupplier").click(function(){
		var supplierId=$(this).attr("data");
		$("#editSupplier").showWindow({
			url:basePath+"/sup/toEditSupplier.do",
			data:{"supplierId":supplierId}});
	});
});