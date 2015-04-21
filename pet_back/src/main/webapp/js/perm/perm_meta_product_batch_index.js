$(document).ready(function(){
//	Utils.setComboxDataSource("/pet_back/perm_organization/get_all_org.do", "orgSlct", true, undefined);
	$( "#metaManagerInput" ).autocomplete({
		source: function(request,response){
			$.ajax({
				url: "get_perm_user_list.do",
				dataType: "json",
				data:{
					name: request.term
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							value:item.label,
							userId:item.value
						};
					}));
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			$("#metaManagerIdHd").val(ui.item.userId).attr("label",ui.item.value);
		}
	}).focusout(function(){
		if($.trim($(this).val()) == ""){
			$("#metaManagerIdHd").val("").attr("label","");
		}else{
			$(this).val($("#metaManagerIdHd").attr("label"));
		} 
	});
	$( "#supplierInput" ).autocomplete({
		source: function(request,response){
			$.ajax({
				url: "get_supplier_list.do",
				dataType: "json",
				data:{
					name: request.term
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							value:item.label,
							userId:item.value
						};
					}));
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			$("#supplierHd").val(ui.item.userId).attr("label",ui.item.value);
		}
	}).focusout(function(){
		if($.trim($(this).val()) == ""){
			$("#supplierHd").val("").attr("label","");
		}else{
			$(this).val($("#supplierHd").attr("label"));
		} 
	});
	
	$( "#newMetaManagerInput" ).autocomplete({
		source: function(request,response){
			$.ajax({
				url: "get_perm_user_list.do",
				dataType: "json",
				data:{
					name: request.term
				},
				success: function( data ) {
					response( $.map( data, function( item ) {
						return {
							value:item.label,
							userId:item.value
						};
					}));
				}
			});
		},
		minLength: 1,
		select: function( event, ui ) {
			$("#newMetaManagerInput").val(ui.item.value).attr("userId",ui.item.userId).attr("realName",ui.item.value);
		}
	}).focusout(function(){
		if($.trim($(this).val()) == ""){
			$(this).attr("userId","").attr("realName","");
		}else{
			$(this).val($(this).attr("realName"));
		} 
	});
	
});

var ids;
function batchEditHandler(){
	ids = "";
	$(".ckbClass").each(function(){
		if($(this).attr("checked")){
			ids = ids + $(this).val() +",";
		}
	});
	if(ids.length == 0){
		Utils.alert("请选择产品");
	}else{
		$("#popWin").dialog({
			autoOpen:true,
			modal:true,
			width:300,
			height:100,
	        title : "批量修改产品经理",
	        position: 'center'
		});
	}
	
}
function saveManagerHandler(){
	if($("#newMetaManagerInput").attr("userId") == "" || $("#newMetaManagerInput").attr("userId") == undefined){
		Utils.alert("请选择产品经理");
		return false;
	}else{
		$.post("save_product_manager_batch.do",{
			managerId:$("#newMetaManagerInput").attr("userId"),
			metaProductIds:ids
		},
		function(data){
			if("success" != data){
				Utils.alert("操作失败");
			}else{
				$("#searchForm").submit();
			}
		});
	}
}