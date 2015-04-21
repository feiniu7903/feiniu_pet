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

// 删除用户绑定
function deleteUserHandler(roleId){
	var ids = "";
	var ckbArr = document.getElementsByName("ckb_bindingUser");
	for(var i=0; i<ckbArr.length; i++){
		var ckb = ckbArr[i];
		if(ckb.checked){
			ids += ckb.value + ",";
		}
	}
	if(ids == ""){
		alert("请选中要删除的用户");
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
        		$.post("to_delete_user_binding.do?ids="+ids+"&roleId="+roleId,function(rs){
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

// 新增用户绑定
function addUserHandler(roleId){
	var ids = "";
	var ckbArr = document.getElementsByName("ckb_departmentUser");
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
      		$.post("binding_role_user.do?ids="+ids+"&roleId="+roleId,function(rs){
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


