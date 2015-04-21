<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入杉德POS机csv文件</title>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript">
function doSubmit() {
	var $form = $("#csvFrom");
	var filePath = $.trim($form.find("input[name=file]").val());
	if (filePath == "") {
		alert("请上传文件！");
		return false;
	}
	if (filePath.lastIndexOf(".") == -1) {
		alert("文件类型错误！");
		return false;
	} else {
		var suffix=filePath.substring(filePath.lastIndexOf("."));
         suffix = suffix.toLowerCase();
         if(!(suffix=='.xls'||suffix=='.xlsx')){
             alert("文件名后缀不对请重新上传!");
             return;
         }
	}
	$('#submitButton').attr('disabled',true); 
	var options = {
		url : "${basePath}/recon/fin_recon_result!importCsv.do",
		async : false,
		type : "POST",
		success : function(data) {
			if(data== "success") {
                alert("操作成功!");
                $("#upload_file").val("");
                $('#submitButton').attr('disabled',false); 
            } else { 
                alert(data); 
            } 
		},
		error : function() {
			alert("操作超时！");
		}
	};
	$form.ajaxSubmit(options);
	return true;
}
</script>
</head>
<body>
	<form method="post" id="csvFrom" enctype="multipart/form-data">
		<div>
			选择文件:
			<s:file id="upload_file" name="file" label="文件" cssStyle="width:300px;" />
			<br /><br /><input id="submitButton" type="button" class="right-button08"
				value="导入" onclick="doSubmit();" />
		</div>
	</form>
</body>
</html>