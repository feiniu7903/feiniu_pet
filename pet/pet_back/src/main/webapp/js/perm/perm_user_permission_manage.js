$(document).ready(function(){
	
});

function deleteUserRoleHandler(urId,roleId){
	$("<div>确定删除？</div>").dialog({
		autoOpen: true, 
        modal: true,
        title : "提示",
        position: 'center',
        width: 180, 
        height: 100,
        buttons:{
        	"确定":function(){
        		$.post("delete_role.do?urId="+urId+"&roleId="+roleId+"&userId="+$("#userIdHd").val(),function(rs){
    				if(rs != "success"){
    					Utils.alert("操作失败");
    				}else{
    					window.location.reload();
    				}
    			});
        	}
        }
	});
}
function setUsrRoleHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "分配角色",
        position: 'center',
        width: 780, 
        height: 400, 
        close:function(){
        	window.location.reload();
        }
	}).width(750).height(400).attr("src","to_add_role.do?userId="+userId);
}
function setUsrPermissionHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "分配权限",
        position: 'center',
        width: 780, 
        height: 400, 
        close:function(){
        	window.location.reload();
        }
	}).width(750).height(400).attr("src","to_add_permission.do?userId="+userId);
}
function disableUsrPermissionHandler(userId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "禁止权限",
        position: 'center',
        width: 780, 
        height: 400, 
        close:function(){
        	window.location.reload();
        }
	}).width(750).height(400).attr("src","to_disable_permission.do?userId="+userId);
}

function deletePermissionHandler(upId,permissionId){
	$("<div>确定删除？</div>").dialog({
		autoOpen: true, 
        modal: true,
        title : "提示",
        position: 'center',
        width: 180, 
        height: 100, 
        buttons:{
        	"确定":function(){
        		$(this).dialog("close");
        		$.post("delete_permission.do?upId="+upId+"&permissionId="+permissionId+"&userId="+$("#userIdHd").val(),function(rs){
    				if(rs != "success"){
    					Utils.alert("操作失败");
    				}else{
    					window.location.reload();
    				}
    			});
        	}
        }
	});
}

function enablePermissionHandler(upId,permissionId){
	$("<div>确定删除？</div>").dialog({
		autoOpen: true, 
        modal: true,
        title : "提示",
        position: 'center',
        width: 180, 
        height: 100,  
        buttons:{
        	"确定":function(){
        		$(this).dialog("close");
        		$.post("delete_permission.do?upId="+upId+"&permissionId="+permissionId+"&userId="+$("#userIdHd").val(),function(rs){
    				if(rs != "success"){
    					Utils.alert("操作失败");
    				}else{
    					window.location.reload();
    				}
    			});
        	}
        }
	});
}