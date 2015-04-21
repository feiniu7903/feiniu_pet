$(function(){
	//操作显示隐藏层
	$("h3.titBotton").live("click",function(){		
		var pos=$(this).attr("pos");
		if(pos=='undefined'){
			return false;
		}
		var $ul=$("#"+pos);
		if($ul.css("display")=='none'){
			$ul.show();
		}else{
			$ul.hide();
		}
	});
})