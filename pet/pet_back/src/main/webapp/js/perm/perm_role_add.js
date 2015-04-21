$(document).ready(function(){
	
	//加载一级部门
	Utils.setComboxDataSource("/pet_back/perm_role/get_org_list_by_level.do?level=1", "orgSlct", true, undefined);
	
	$("#roleForm").validate({
		rules: {                     
			"permRole.roleName":{                         
				required: true                  
			}
		}, 
		messages: {                     
			"permRole.roleName": {                         
				required: "请选择职位角色"                    
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面                
	    } 
	});
	
});

function levelChangeHandler(){
	Utils.setComboxDataSource("/pet_back/perm_role/get_org_list_by_level.do?level="+$("#levelSlct").val(), "orgSlct", true, undefined);
}

