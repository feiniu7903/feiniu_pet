$(function(){
	$("#supplierForm").validateAndSubmit(
		function($form,dt){
			var data=eval("("+dt+")");//alert(data.msg);
			if(data.success){
				alert("操作成功");
				window.location.reload();
			}else{
				alert(data.msg);
			}
	});	
	
	$("#supplier_suggest_id").jsonSuggest({
		url:basePath+"/sup/searchSupplierJSON.do",
		maxResults: 10,
		width:300,
		minCharacters:1,
		onSelect:function(item){					
			$("#supplier_parentId").val(item.id);
		}
	});
});