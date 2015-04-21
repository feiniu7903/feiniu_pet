// 全选
function checkAll(obj, ckbId){
	var ckbArr = document.getElementsByName(ckbId);
	for(var i=0; i<ckbArr.length; i++){
		var ckb = ckbArr[i];
		if(obj.checked){
			ckb.checked = true;
		}else{
			ckb.checked = false;
		}
	}
}

// 删除角色权限绑定
function deleteRoleHandler(roleId){
	var ids = "";
	var ckbArr = document.getElementsByName("ckb_bindingRolePerm");
	for(var i=0; i<ckbArr.length; i++){
		var ckb = ckbArr[i];
		if(ckb.checked){
			ids += ckb.value + ",";
		}
	}
	if(ids == ""){
		alert("请选中要删除的角色权限");
		return false;
	}
	
	$("<div>确定删除？</div>").dialog({
		autoOpen: true, 
        modal: true,
        title : "提示",
        position: 'center',
        width: 200, 
        height: 120,  
        buttons:{
        	"确定":function(){
        		$.post("delete_role_permission.do?ids="+ids+"&roleId="+roleId,function(rs){
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

// 新增角色权限绑定
function addPermissionHandler(roleId){
	var ids = "";
	var ckbArr = document.getElementsByName("ckb_bindingSystemPerm");
	for(var i=0; i<ckbArr.length; i++){
		var ckb = ckbArr[i];
		if(ckb.checked){
			ids += ckb.value + ",";
		}
	}
	if(ids == ""){
		alert("请选中要绑定的用户");
		return false;
	}

	$("<div>确定绑定？</div>").dialog({
		autoOpen: true, 
      modal: true,
      title : "提示",
      position: 'center',
      width: 200, 
      height: 120,  
      buttons:{
      	"确定":function(){
      		$.post("save_role_permission.do?ids="+ids+"&roleId="+roleId,function(rs){
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

// 查看子元素
function showElementHandler(permissionId, roleId){
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "查看子元素",
        position: 'center',
        width: 800, 
        height: 500
	}).width(780).height(500).attr("src","to_show_element.do?permissionId=" + permissionId + "&roleId=" + roleId);
}

