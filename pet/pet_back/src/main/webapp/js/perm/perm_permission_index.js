$(function(){
	$("#searchForm").validate({
		rules: {                     
			"parentId":{
				number: true
			}
		}, 
		messages: {                     
			"parentId": {                         
				number: "请输入数字"                    
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面                
	    } 
	});
});
//新增权限
function newPermissionHandler(){
	$("#popup_div").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增菜单",
        position: 'center',
        width: 680, 
        height: 300,  
        open:function(){$(this).load("to_add_top_menu.do");}
	});
}
//新增子菜单
function addChildPermission(permissionId){
	$("#popup_div").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增子菜单",
        position: 'center',
        width: 700, 
        height: 340,  
        open:function(){$(this).load("to_add_child_menu.do?permissionId="+permissionId);}
	});
}
//删除
function deletePermission(permissionId){
	$("<div/>").dialog({
		autoOpen: true, 
        modal: true,
        position: 'center',
        title : "提示",
        width: 180, 
        height: 100,
        buttons: { 
        	"确定": function() { 
        		$(this).dialog("close"); 
        		$.post("delete.do", { "permissionId": permissionId},
        				   function(data){
        					    if(data=="success"){
        					    	Utils.alert("删除成功",function(){window.location.href = window.location.href;});
        					    }else{
        					    	Utils.alert("删除失败");
        					    }
        				   }, "json");
        		}
        }
	}).html("确定删除？");
}
//修改
function editPermission(permissionId){
	$("#popup_div").dialog({
		autoOpen: true, 
        modal: true,
        title : "修改菜单",
        position: 'center',
        width: 680, 
        height: 300,  
        open:function(){$(this).load("to_edit.do?permissionId="+permissionId);}
	});
}
//查看元素
function viewChildList(permissionId,type){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "查看元素",
        position: 'center',
        width: 800, 
        height: 500,  
	}).width(780).height(500).attr("src","view_child_list.do?permissionId="+permissionId+"&type="+type);
}

