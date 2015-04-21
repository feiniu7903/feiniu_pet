<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>变更及补充单</title>
<script type="text/javascript" src="${basePath}/js/base/ajaxupload.js"></script>
<script type="text/javascript">
$("#uploadChangeFile").fileUpload({
	onSubmit:function() {
		$("#uploadChangeFile").attr("disabled", true);
	},
	onComplete:function(file,dt){
	var data=eval("("+dt+")");
	if(data.success){
		var $input=$("#fileId");
		$input.val(data.file);
		$("#fileDowload").text("上传成功");
	}else{
		alert(data.msg);
	}
	$("#uploadChangeFile").removeAttr("disabled");
}});

function savaContrackChange(){
	var type = $("#contractTypeSelect").val();
	if(type == 'DELAY_CONTRACT') {
		if($("#contractEndDate").val() == '') {
			alert("到期时间不能为空！");
			return false;
		}
	}
	var $form=$("#contractChangeUploadForm");
	$.post(
		$form.attr("action"),
		$form.serialize(),
		function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				$("#contract_change_div").dialog("close");
				alert("操作成功");
			}else{
				alert(data.msg);
			}
		});
}
$(function() {
	$(document).ready(function() {
		$("input.date").datepicker({dateFormat:'yy-mm-dd',
			changeMonth: true,
			changeYear: true,
			showOtherMonths: true,
			selectOtherMonths: true,
			buttonImageOnly: true
		});	
		
		$("#contractTypeSelect").change(function() {
			var val = $(this).val();
			if(val == 'DELAY_CONTRACT') {
				$("#delayContractSpan").show();
			} else {
				$("#delayContractSpan").hide();
			}
			if(val == 'SETTLEMENT_ALTERING') {
				$("textarea[name='supContractChange.changeMemo']").val("1、供应商结算对象编号:\r\n2、变更项目:\r\n3、变更内容:");
			} else {
				$("textarea[name='supContractChange.changeMemo']").val("");
			}
		});
	});
});
</script>
</head>
<body>
	<div>
		<form action="${basePath }/contract/save.do" id="contractChangeUploadForm" method="post">
			 <table class="cg_xx" border="0" cellspacing="0" cellpadding="0" width="100%">
			 	<tr>
			 		<td colspan="4">
			 			关联合同(<s:property value="supContract.contractId"/>):<s:property value="supContract.contractNo"/>
			 			<input type="hidden" name="supContractChange.contractId" value="<s:property value="supContract.contractId"/>"/> 
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>变更类型:</td>
			 		<td>
			 			<s:select list="contractType" listKey="code" listValue="cnName" name="supContractChange.changeType" id="contractTypeSelect"/>
			 			&nbsp;&nbsp;
			 			<span id="delayContractSpan" style="display:none;">到期时间:<input name="contractEndDate" type="text" class="date" readonly="readonly" id="contractEndDate" /></span>
			 		</td>
			 		<td>上传副本</td>
			 		<td>
			 			<div>
			 				<input  type="hidden" id="fileId" name="supContractChange.fsId" value=""/>
			 				<input type="button" class="button" value="上传" id="uploadChangeFile" serverType="SUP_SUPPLIER_CONTRACT"/>
			 				<div id="fileDowload"></div>
			 			</div>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td colspan="4">变更内容说明:
			 			<textarea rows="8" cols="80" name="supContractChange.changeMemo"/>
			 		</td>
			 	</tr>
			 </table>
			  <input type="button" class="button" onclick="savaContrackChange();" id="saveButton" value="保存"/>
		</form>
	</div>
	 
</body>
</html>