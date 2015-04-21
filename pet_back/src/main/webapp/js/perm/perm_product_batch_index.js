$(document).ready(function(){
//	Utils.setComboxDataSource("/pet_back/perm_organization/get_all_org.do", "orgSlct", true, undefined);
	$( "#managerInput" ).autocomplete({
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
			$("#managerIdHd").val(ui.item.userId).attr("label",ui.item.value);
		}
	}).focusout(function(){
		if($.trim($(this).val()) == ""){
			$("#managerIdHd").val("").attr("label","");
		}else{
			$(this).val($("#managerIdHd").attr("label"));
		} 
	});
	$( "#placeInput" ).autocomplete({
		source: function(request,response){
			$.ajax({
				url: "get_prod_target_list.do",
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
			$("#placeIdHd").val(ui.item.userId).attr("label",ui.item.value);
		}
	}).focusout(function(){
		if($.trim($(this).val()) == ""){
			$("#placeIdHd").val("").attr("label","");
		}else{
			$(this).val($("#placeIdHd").attr("label"));
		} 
	});
	
	$( "#newManagerInput" ).autocomplete({
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
			$("#newManagerInput").val(ui.item.value).attr("userId",ui.item.userId).attr("realName",ui.item.value);
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
	        position: 'center',
	        close:function(){
	        	$("#newManagerInput").attr("userId","").attr("realName","");
	        }
		});
		
	}
	
}
function saveManagerHandler(){
	if($("#newManagerInput").attr("userId") == "" || $("#newManagerInput").attr("userId") == undefined){
		Utils.alert("请选择产品经理");
		return false;
	}else{
		$.post("save_product_manager_batch.do",{
			managerId:$("#newManagerInput").attr("userId"),
			productIds:ids
		},
		function(data){
			if("success" != data){
				Utils.alert("操作失败");
			}else{
				$("#popWin").dialog("close");
				Utils.alert("批量修改成功",function(){window.location.href = window.location.href;});
			}
		});
	}
}