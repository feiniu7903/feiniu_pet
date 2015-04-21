$(document).ready(function() {
	$('#startDate').datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$('#endDate').datepicker({
		dateFormat : 'yy-mm-dd'
	});
	resendInitFun();
});

function resendInitFun() {
	$('#resendForm').ajaxForm({
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		},
		success : function(responseText, statusText) {
			if (responseText == "1") {
				alert("发送成功!");
				window.location.href = window.location.href;
			} else {
				alert(responseText);
			}
		}
	});
}

// 重发短信
function resend(id, channel, tableName) {
	$("#resendForm>#id").val(id);
	$("#resendForm>#channel").val(channel);
	$("#resendForm>#tableName").val(tableName);
	$("#resendDialog").dialog({
		autoOpen : true,
		modal : true,
		title : "重发短信",
		position : 'center',
		width : 400,
		height : 80
	/*
	 * , close : function(event, ui) { window.location.href =
	 * window.location.href; }
	 */
	});
}
