$(function(){
	$("#close_btn").click(function(){
		$("#popup_div").dialog("destroy");
	});

	
	$("#permission_form").validate({
		rules: {                     
			"permPermission.name":{
				required: true
			},
			"permPermission.category":{
				required: true
			},
			"permPermission.seq":{
				required: true,
				number:true
			}
		}, 
		messages: {                     
			"permPermission.name": {                         
				required: "请输入菜单名称"                    
			},
			"permPermission.category":{
				required: "请输入菜单类别"
			},
			"permPermission.seq":{
				required: "请输入用来排序的数值",
				number:"请输入数字"
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面                
	    } 
	});
	$("#permission_form").ajaxForm({
		type: 'post',
		url:  'save_top_menu.do',
		success: function(data) {
			if(data=="success"){
				$("#popup_div").dialog("close");
				Utils.alert("添加成功");
			}else{
				Utils.alert("添加失败");
			}
		}
	});
	$("#permission_child_form").validate({
		rules: {                     
			"permPermission.name":{
				required: true
			},
			"permPermission.category":{
				required: true
			},
			"permPermission.permLevel":{
				required: true
			},
			"permPermission.seq":{
				required: true,
				number:true
			}
		}, 
		messages: {                     
			"permPermission.name": {                         
				required: "请输入菜单名称"                    
			},
			"permPermission.category":{
				required: "请选择菜单类别"
			},
			"permPermission.permLevel":{
				required: "请选择菜单级别"
			},
			"permPermission.seq":{
				required: "请输入用来排序的数值",
				number:"请输入数字"
			}
		}, 
		errorPlacement: function (error, element) { 
	        error.appendTo(element.parent());    //将错误信息添加当前元素的父结点后面                
	    } 
	});
	$("#permission_child_form").ajaxForm({
		type: 'post',
		url:  'save_child_menu.do',
		success: function(data) {
			if(data=="success"){
				$("#popup_div").dialog("close");
				Utils.alert("添加成功");
			}else{
				Utils.alert("添加失败");
			}
		}
	});
});

