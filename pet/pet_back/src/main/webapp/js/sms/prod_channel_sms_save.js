$(document).ready(function() {
	$("#form").validate({
		rules : {
			"model.templateId" : {
				required : contentValidate
			},
			"model.channelCode" : {
				required : true
			},
			"model.content" : {
				required : contentValidate
			}
		},
		messages : {
			"model.templateId" : {
				required : "请选择短信模板"
			},
			"model.channelCode" : {
				required : "请选择销售渠道"
			},
			"model.content" : {
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
			if (responseText == "1") {
				parent.window.location.href = parent.window.location.href;
			} else {
				alert(responseText);
			}
		}
	});

});

function contentValidate() {
	var templateId = $("#templateId").val();
	var content = $("#content").val();
	var result = ($.trim(templateId) == "" && $.trim(content) == "");
	return result;
}
