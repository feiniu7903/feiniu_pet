$(function(){
	$("a.editAptitude").click(function(){
		var supplierId=$(this).attr("data");
		$("#editAptitude").showWindow({
			width:500,
			data:{"supplierId":supplierId}
		});
	});
});