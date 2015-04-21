<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>上传合同</title>
	<script type="text/javascript" src="${basePath}/js/base/ajaxupload.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#uploadFile").fileUpload({onComplete:function(file,dt){
				var data=eval("("+dt+")");
				if(data.success){
					var fsId = data.file;
					var fsName = data.fileName;
					if(fsId == undefined || fsId == null || fsId == "") {
						alert("保存失败,请重新上传！");
						return false;
					}
					if(fsName == undefined || fsName == null || fsName == "") {
						alert("文件名称为空,请重新上传！");
						return false;
					}
					var $contractFileForm = $("#contractFileForm");
					$contractFileForm.find("input[name='contractFs.fsId']").val(data.file);
					$contractFileForm.find("input[name='contractFs.fsName']").val(data.fileName);
					$contractFileForm.submit();
				}else{
					alert(data.msg);
				}
			}});
			
			$("#contractFileForm").validateAndSubmit(function($form,dt){
				var data=eval("("+dt+")");
				if(data.success){
					alert("操作成功");
					$("#upload_file").dialog("close");
				}else{
					alert(data.msg);
				}
			});
		});
	</script>
</head>
<body>
<form action="${basePath}/contract/saveContractFile.do" method="post" id="contractFileForm">
<input type="hidden" name="contractFs.contractId" value="<s:property value="contract.contractId"/>"/>
<s:hidden name="contractFs.fsId"/>
<s:hidden name="contractFs.fsName" />
<table>
	<tr>
		<td style="line-height:30px;">选择文件</td><td><input type="button" serverType="SUP_SUPPLIER_CONTRACT" id="uploadFile" class="button" value="选择文件..."/></td>
	</tr>
</table>
</form>
</body>
</html>