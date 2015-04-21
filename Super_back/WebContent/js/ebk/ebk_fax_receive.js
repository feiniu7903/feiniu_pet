$(function() {
	$("input.date").datepicker({
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true,
		showOtherMonths : true,
		selectOtherMonths : true,
		buttonImageOnly : true
	}).attr("readonly", true);

	var $upload_receive_file_div;
	$("#upload_receive_file_btn").live("click", function() {
		$upload_receive_file_div = $("#upload_receive_file_div").showWindow({
			width : 1000,
			title : '上传回传件'
		});
	});

	$(".showFaxReceiveFile").live("click", function() {
		var data = $(this).attr("data");
		if (data == undefined || data == null || data == "") {
			alert("无法查看该回传件！");
			return false;
		}
		showReceiveAndRelate(data);
	});

	//上传回传件
	$("#upload_receive_file_submit").live(
			"click",
			function() {
				var $form = $(this).parents("form");
				var fileUrl = $.trim($form.find(
						"input[name=ordFaxRecv.fileUrl]").val());
				if (fileUrl == "") {
					alert("请先上传回传件！");
					return false;
				}
				var callerId = $.trim($form.find(
						"input[name=ordFaxRecv.callerId]").val());
				if (callerId == "") {
					alert("发送号码不能为空！");
					return false;
				}
				var recvTime = $.trim($form.find(
						"input[name=ordFaxRecv.recvTime]").val());
				if (recvTime == "") {
					alert("接收时间不能为空！");
					return false;
				}
				$.post($form.attr("action"), $form.serialize(), function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("保存成功！");
						$upload_receive_file_div.dialog("close");
						showReceiveAndRelate(data.ordFaxRecvId);
					} else {
						alert(data.msg);
					}
				});
			});

	//关联凭证并保存
	
});

function showReceiveAndRelate(data) {
	$("#show_receive_file_div").showWindow({
		width : document.body.offsetWidth,
		height : document.body.offsetHeight,
		title : '查看回传件',
		data : {
			'ordFaxRecvId' : data,
			'modifyCertIdflag' : 'true'
		}
	});
}

//删除凭证关联
function deleteLink(obj) {
	var $this = $(obj);
	if (confirm("确定要删除该条关联?")) {
		var ordFaxRecvLinkId = $this.attr("data");
		var ordFaxRecvId = $("#hiddenOrdFaxRecvId").val();
		$.post("/super_back/fax/faxReceive/deleteReceiveLink.do", {
			'ordFaxRecvLinkId' : ordFaxRecvLinkId
		}, function(dt) {
			var data = eval("(" + dt + ")");
			if (data.success) {
				alert("删除成功！");
				refreshRelateList(ordFaxRecvId);
			} else {
				alert(data.msg);
			}
		});
	}
}

//刷新关联凭证区域内容
function refreshRelateList(data) {
	$("#relate_certificate_div").load("/super_back/fax/faxReceive/showRelateList.do", {'ordFaxRecvId': data});
}