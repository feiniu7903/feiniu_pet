$(function(){
	
	$("a.editAssess").live("click",function(){
		var supplierId=$(this).attr("data");
		$("#editAssess").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	
	$("input.assessSubmit").live("click",function(){
		var $assessFrom=$(this).parents("form");
		$assessFrom.validateAndSubmit(function($form,dt){
			var supplierId=$form.find("input[name=supplierAssess.supplierId]").val();
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				window.location.reload();
			}else{
				alert(data.msg);
			}
		},{onSubmit:function($form){
			var content=$form.find("textarea[name=supplierAssess.assessMemo]").val();
			if(content.length>200){
				alert("描述内容太长,请控制在200个字符内");
				return false;
			}
			return true;
		}});
		
	});
});