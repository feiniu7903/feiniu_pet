function showControlRoleDialog(id) {
	$('#role_div').showWindow({
		title : id ? '修改' : '新增',
		width : 350,
		height : 370,
		data : {
			"role.productControlRoleId" : id
		}
	});
	
}

function closeControlRoleDialog() {
	$("#role_div").dialog("close");
}

function isFormValid($form) {
	var userId = $form.find('#userId').val();
	if (!userId) {
		alert('姓名填写有误');
		return;
	}
	var roleArea = $form.find('#roleAreaSelect').val();
	if (!roleArea) {
		alert('查看范围必填');
		return;
	}
	if (roleArea == 'ROLE_MANAGER') {
		var managerIdList = $form.find('#managerIdList').val();
		if (!managerIdList || managerIdList == '[]') {
			alert('产品经理列表不能为空');
			return;
		}
	} else {
		$form.find('#managerIdList').val('');
	}
	return true;
}

function addControlRole() {
	var $form = $('#controlRoleForm');
	if (!isFormValid($form)) {
		return;
	}
	$.ajax({
		url:"/super_back/meta/saveControlRole.do",
		type:"POST",
		data:$form.serialize(),
		success:function(dt){
			var data = dt;
			if(data.success){
				/*
				if (data.opType == 'NEW') {
					alert("添加成功");
				} else {
					alert("修改成功");
				}*/
				closeControlRoleDialog();
				if ($('#query_form').length > 0) {
					$('#query_form').submit();
				}
			}else{
				alert(data.msg);
			}
		}
	});
}

function deleteRole(id) {
	if (confirm('确定删除?')) {
		$.ajax({
			url:"/super_back/meta/deleteControlRole.do",
			type:"POST",
			data:{"role.productControlRoleId" : id},
			success:function(dt){
				var data = dt;
				if(data.success){
					if ($('#query_form').length > 0) {
						$('#query_form').submit();
					}
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

$(document).ready(function() {
	$('#newControlRoleBtn').click(function() {
		showControlRoleDialog('');
	});
});
