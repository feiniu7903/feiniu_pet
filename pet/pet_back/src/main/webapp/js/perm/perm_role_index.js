// 新增角色
function newRoleHandler(){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "新增角色",
        position: 'center',
        width: 780, 
        height: 220,  
        close: function(event,ui){ 
        	window.location.href=window.location.href; 
        }
	}).width(750).height(200).attr("src","to_add.do");
}

// 修改角色
function editRoleHandler(roleId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "修改角色",
        position: 'center',
        width: 800, 
        height: 300
	}).width(820).height(300).attr("src","to_edit.do?roleId="+roleId);
}

// 绑定权限
function bindingPermHandler(roleId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
     modal: true,
     title : "绑定权限",
     position: 'center',
     width: 970, 
     height: 680
	}).width(950).height(660).attr("src","to_binding_perm.do?roleId="+roleId);
}

// 绑定用户
function bindingUserHandler(roleId){
	$("<iframe frameborder='0' ></iframe>").dialog({
//		autoOpen: true, 
     modal: true,
     title : "绑定用户",
     position: 'center',
     width: 970, 
     height: 720
	}).width(950).height(700).attr("src","to_binding_user.do?roleId="+roleId);
}