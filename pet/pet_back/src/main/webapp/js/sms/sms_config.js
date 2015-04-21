$(document).ready(function() {
	$('#form').ajaxForm({
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		},
		dataType : "json",
		success : function(data, statusText) {
			alert(data.msg);
		}
	});
});
