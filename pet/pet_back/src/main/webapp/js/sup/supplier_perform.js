$(function(){
	$("a.editPerform").live("click",function(){
		var supplierId=$(this).attr("data");
		$("#editPerform").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	
	var addPerformDiv;
	$("a.addPerformBtn").live("click",function(){
		var supplierId=$(this).attr("data");
		addPerformDiv=$("#addPerformDiv").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	$("a.editPerformBtn").live("click",function(){
		var targetId=$(this).attr("data");
		addPerformDiv=$("#addPerformDiv").showWindow({
			url:basePath+"/sup/target/toEditPerform.do",
			data:{"targetId":targetId}
		});
	});
	$("input.performSubmit").live("click",function(){
		var $form1=$(this).parents("form");
		if($form1.find("input[name='performTarget.certificateType']:checked").length < 1) {
			alert("履行方式不能为空！");
			return false;
		}
		$form1.validateAndSubmit(function($form,dt) {
			var supplierId=$form.find("input[name=performTarget.supplierId]").val();
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				addPerformDiv.dialog("close");
				$("#editPerform").resetWindow({
					data:{"supplierId":supplierId}
				});
			}else{
				alert(data.msg);
			}
		});
	});
});