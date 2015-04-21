<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上传回传件</title>
<script type="text/javascript" src="/pet_back/js/base/ajaxupload.js"></script>
<script type="text/javascript">
	$(function() {
		$("input.longDateFormat").datetimepicker({
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 5,
			stepSecond: 5,
			showButtonPanel:false
		});
		
		$("#uploadReceiveFileBtn").faxReceiveUpload({
			onSubmit : function() {
				$("#uploadReceiveFileBtn").attr("disabled", true);
			},
			onComplete : function(file, dt) {
				var data = eval("(" + dt + ")");
				if (data.success) {
					$("input[name=ordFaxRecv.fileUrl]").val(data.fullName);
					alert("上传成功！");
				} else {
					alert(data.msg);
				}
				$("#uploadReceiveFileBtn").removeAttr("disabled");
			}
		});
	})
</script>
</head>
<body>
	<div class="iframe-content">
		<form action="${basePath }/fax/faxReceive/uploadReceiveFile.do"
			method="post">
			<div class="p_box">
				<table border="0" cellspacing="0" cellpadding="0"
					class="p_table form-inline">
					<tr>
						<td class="p_label">文件：</td>
						<td><input type="hidden" name="ordFaxRecv.fileUrl" /> <input
							type="button" value="上传" id="uploadReceiveFileBtn" class="btn btn-small" /> (jpg、pdf)</td>
					</tr>
					<tr>
						<td class="p_label">发送号码：</td>
						<td><s:textfield name="ordFaxRecv.callerId" /></td>
					</tr>
					<tr>
						<td class="p_label">接收时间：</td>
						<td><input type="text" name="ordFaxRecv.recvTime" class="longDateFormat" readonly="readonly" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="button" value="保存"
					id="upload_receive_file_submit" class="btn btn-small" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>