$(document).ready(function(){
	$("#userForm").validate({
		rules: {                     
			"permUser.userName":{                         
				required: function (element) {                             
					return $("input:radio[name='permUser.userName']:checked").val() != "";                         
				}                     
			},
			"permUser.realName":{
				required: true
			},
			"permUser.mobile":{
				required: true,
				number:true
			},
			"permUser.position":{
				required: true
			},
			"permUser.email":{
				required: true,
				email:true
			},
			"permUser.extensionNumber":{
				required: true,
				number:true
			},
			"permUser.valid":{
				required: true
			},
			"permUser.departmentId":{
				required:true
			}
		}, 
		messages: {                     
			"permUser.userName": {                         
				required: "请选择用户名"                    
			},
			"permUser.realName": {                         
				required: "请输入姓名"                    
			},
			"permUser.mobile":{
				required: "请输入联系方式",
				number:"请输入数字"
			},
			"permUser.position":{
				required: "请输入职位"
			},
			"permUser.email":{
				required: "请输入邮箱",
				email:"请输入正确的邮箱格式"
			},
			"permUser.extensionNumber":{
				required: "请输入分机号",
				number:"请输入数字"
			},
			"permUser.valid":{
				required: "请选择状态"
			},
			"permUser.departmentId":{
				required:"请选择部门"
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面                
	    } 
	});

	$('#userForm').ajaxForm({
		beforeSubmit:  function(formData, jqForm, options){
			return true;
		},  
        success: function(responseText, statusText){
        	parent.window.location.href = parent.window.location.href;
        } 
    });
	
	//加载一级部门
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level=1", "orgSlct", true, undefined);
	
});

function levelChangeHandler(){
	Utils.setComboxDataSource("/pet_back/perm_organization/get_org_list_by_level.do?level="+$("#levelSlct").val(), "orgSlct", true, undefined);
}

