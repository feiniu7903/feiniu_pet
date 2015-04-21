$(function(){
	
	$("input[name=product.subProductType]").change(function(){
		var val=$(this).val();
		var $input=$("input[name=product.days]");
		$input.attr("disabled",val!='HOTEL_SUIT');
		if(val=='SINGLE_ROOM'){
			$input.val('');
		}
	});
	
	$(document).ready(function(){
		var val=$("input[name=product.subProductType]:checked").val();
		if(val!=''){
			var $input=$("input[name=product.days]");
			$input.attr("disabled",val!='HOTEL_SUIT');
		}
	});
})