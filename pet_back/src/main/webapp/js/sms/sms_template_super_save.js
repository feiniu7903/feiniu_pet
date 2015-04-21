$(document).ready(function() {
	$("#form").validate({
		rules : {
			"template.templateId" : {
				required : true
			},
			"template.templateName" : {
				required : true
			},
			"template.immendiately" : {
				required : true
			},
			"template.content" : {
				required : true
			}
		},
		messages : {
			"template.templateId" : {
				required : "请输入短信模板ID"
			},
			"template.templateName" : {
				required : "请输入短信模板名称"
			},
			"template.immendiately" : {
				required : "请选择是否立即发送"
			},
			"template.content" : {
				required : "请输入短信模板内容"
			}
		},
		errorPlacement : function(error, element) {
			error.appendTo(element.parent()); // 将错误信息添加当前元素的父结点后面
		}
	});

	$('#form').ajaxForm({
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		},
		success : function(responseText, statusText) {
			if(responseText=="1"){
				parent.window.location.href = parent.window.location.href;
			}else{
				alert(responseText);
			}
		}
	});

});

