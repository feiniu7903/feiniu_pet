
function nextMonth(pre,next){

//$("table[name='lvmama_calendar']").each(function(){$(this).hide()});
//$("table[id='calendar_table_"+name+"']").each(function(){
//$(this).show();
//});

	alert($("table[id='"+pre+"']").attr("id"));
	if($("table[id='"+pre+"']").attr("id")!=undefined){
		$("table[id='"+pre+"']").hide();
		$("table[id='"+next+"']").fadeIn();
	}


}

function showOrHide(event,hide,show){
	if (event.stopPropagation) event.stopPropagation();
  		else event.cancelBubble = true;
		if($("#"+show).attr("id")!=undefined){
		$("#"+hide).hide();
		$("#"+show).fadeIn();
	}

	
}