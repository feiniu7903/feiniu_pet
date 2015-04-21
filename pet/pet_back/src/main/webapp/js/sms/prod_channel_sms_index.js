$(document).ready(function() {

});

// 新增短信模板
function newHandler() {
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen : true,
		modal : true,
		title : "新增产品销售渠道相关短信模板",
		position : 'center',
		width : 800,
		height : 350/*,
		close : function(event, ui) {
			window.location.href = window.location.href;
		}*/
	}).height(350).width(820).attr("src", "add.do");
}

/**
 * 编辑模板
 * @param id
 */
function editHandler(id) {
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen : true,
		modal : true,
		title : "修改产品销售渠道相关短信模板",
		position : 'center',
		width : 800,
		height :350
	}).height(350).width(820).attr("src", "edit.do?id=" + id);
}

/**
 * 修改模板
 * @param id
 */
function deleteHandler(id) {
	if (confirm("您确定删除该数据?")) {
		window.location.href = "delete.do?id=" + id;
	}
}

/**
 * 批量编辑渠道
 */
function editBatchChannelFun() {
	$("#editBatchChannelDialog").dialog({
		autoOpen : true,
		modal : true,
		title : "批量修改渠道",
		position : 'center',
		width : 800,
		height : 200,
		close : function(event, ui) {
			window.location.href = window.location.href;
		}
	});
}

/**
 * 批量编辑渠道，提交
 */
function editBatchChannelFormSubmit() {
	var $form = $("#editBatchChannelForm");
	var options = {
		type : "POST",
		url:$form.attr("action"),
		dataType : "json",
		data : $form.serializeArray(),
		success : function(data) {
			if (data.result == "error") {
				alert(data.msg);
			} else {
				alert(data.msg);
				$("#searchForm").get(0).submit();
			}
		}
	};
	$.ajax(options);
}