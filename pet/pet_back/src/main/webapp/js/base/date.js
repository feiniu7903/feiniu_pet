$(function(){
	$("input.date,input.dateISO").datepicker({dateFormat:'yy-mm-dd',
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			buttonImageOnly: true
	});	
	
	$("input.date,input.dateISO").live("load",function(){
		$this.datepicker({dateFormat:'yy-mm-dd',
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			buttonImageOnly: true
	});	
	});
})