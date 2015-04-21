<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>导入excel文件</title>
    <script src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript">
	function doSubmit() {
		var $form = $("#excelFrom");
		var filePath = $.trim($form.find("input[name=file]").val());
		if (file == "") {
			alert("请上传文件！");
			return false;
		}
		if (filePath.lastIndexOf(".") == -1) {
			alert("文件类型错误！");
			return false;
		} else {
			var suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
			if (suffix != "xls" && suffix != "xlsx") {
				alert("文件类型错误！");
				return false;
			}
		}
		var options = {
			url : "/pet_back/sensitiveWord/importExcel.do",
			async : false,
			type : "POST",
			success : function(data) {
				var dt = eval("("+data+")");
				if (dt.success) {
					alert("操作成功");
				} else {
					alert(dt.msg);
				}
				location.reload(window.location.href);
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
	<form method="post" id="excelFrom" enctype="multipart/form-data">
		<div>
			选择文件:
			<s:file name="file" label="文件" cssStyle="width:300px;" />
			<br /> <br /> <input type="button" class="btn btn-small w5"
				value="导入" onclick="doSubmit();" />
		</div>
	</form>
</body>
</html>