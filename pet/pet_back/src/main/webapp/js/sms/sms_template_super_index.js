$(document).ready(function() {

});

// 新增短信模板
function newHandler() {
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen : true,
		modal : true,
		title : "新增短信模板",
		position : 'center',
		width : 800,
		height : 350/*,
		close : function(event, ui) {
			window.location.href = window.location.href;
		}*/
	}).height(350).width(820).attr("src", "superAdd.do");
}

/**
 * 编辑模板
 * @param id
 */
function editHandler(id) {
	$("<iframe frameborder='0' ></iframe>").dialog({
		autoOpen : true,
		modal : true,
		title : "修改短信模板",
		position : 'center',
		width : 800,
		height :350
	}).height(350).width(820).attr("src", "superEdit.do?templateId=" + id);
}

/**
 * 修改模板
 * @param id
 */
function deleteHandler(id) {
	if (confirm("您确定删除该短信模板?")) {
		window.location.href = "superDelete.do?templateId=" + id;
	}
}