<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<div class="p_box">
		<table class="p_table table-center" id="linkTable">
			<tr>
				<th>操作时间</th>
				<th>关联凭证</th>
				<th>操作人</th>
				<th>回传状态</th>
				<th>回传备注</th>
				<th>操作</th>
			</tr>
			<s:iterator value="ordFaxRecvLinkList">
				<tr>
					<td>${zhCreateTime}</td>
					<td>${ebkCertificateId}</td>
					<td>${operator}</td>
					<td>${zhResultStatus }</td>
					<td>${memo }</td>
					<td class="oper">
						<a href="javascript:void(0);"	
						onclick="deleteLink(this);" data="${ordFaxRecvLinkId }">删除</a>
							<a href="javascript:void(0);"
							onclick="fillLinkContent(this,${ebkCertificateId});" data="${ordFaxRecvLinkId }">修改</a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	<div class="p_box">
		<form action="${basePath }/fax/faxReceive/relateCertUpdateStatusAndCertMemo.do"
			method="post" id="ordFaxRecvLinkform">
			<input type="hidden" name="ordFaxRecvLink.ordFaxRecvId"
				value="${ordFaxRecv.ordFaxRecvId }" id="hiddenOrdFaxRecvId" />
			<input type="hidden" name="ordFaxRecvLink.ordFaxRecvLinkId"
				value="" id="hiddenOrdFaxRecvLinkId" />
			<table border="0" cellspacing="0" cellpadding="0"
				class="p_table form-inline">
				<tr>
					<td class="p_label">凭证编号：</td>
					<td>
						<s:if test="ordFaxRecvLinkList!=null">
							<s:textfield name="ordFaxRecvLink.ebkCertificateId"/>
						</s:if>
						<s:elseif test="ordFaxRecvLinkList==null">
							 <input name="ordFaxRecvLink.ebkCertificateId"  readonly="readonly"
							 value="${requestScope.showEbkCertificateId}" />   
							<input type="hidden" id="checkIfUpdateOnly" 
							name="checkIfUpdateOnly" value="true"/>
							<input type="checkbox" id="updateFaxRecvStatusAndCertMemoOnly" 
							name="updateFaxRecvStatusAndCertMemoOnly"/>无回传件确认
						</s:elseif>
					</td>
				</tr>
				<tr>
					<td class="p_label">备注内容：</td>
					<td><s:textarea name="ordFaxRecvLink.memo" cols="30" rows="10" /></td>
				</tr>
				<tr>
					<td class="p_label">回传状态：</td>
					<td><s:radio list="#{'FAX_SEND_STATUS_RECVOK':'确认回传同意','FAX_SEND_STATUS_RECVNO':'确认回传不同意'}"
							name="ordFaxRecvLink.resultStatus"></s:radio></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="button" value="保存"
						id="relate_receive_certificate_btn" class="btn btn-small w6" />
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
$(function() {
	$("#relate_receive_certificate_btn")
	.click(
			function() {
				var reg = new RegExp("^[0-9]*$");
				var $form = $(this).parents("form");
				var ebkCertificateId = $.trim($form.find("input[name=ordFaxRecvLink.ebkCertificateId]").val());
				if (ebkCertificateId == "") {
					alert("凭证编号不能为空！");
					return false;
				}
				if(!reg.test(ebkCertificateId)){
					alert("凭证编号请填写数字！");
			    	return false;
				}
				var resultStatus = $form
						.find("input[name=ordFaxRecvLink.resultStatus]:checked");
				if (resultStatus.length < 1) {
					alert("请选择回传状态！");
					return false;
				}
				var checkIfUpdateOnly=$("#checkIfUpdateOnly").val();
				if(checkIfUpdateOnly=="true"){
					if($("#updateFaxRecvStatusAndCertMemoOnly").attr("checked")!=true){
						alert("请勾选无回传件确认");
						return false;
					}
				}
				$form.find("input[name=ordFaxRecvLink.ebkCertificateId]").val(ebkCertificateId);
				$.post(
						$form.attr("action"),
						$form.serialize(),
						function(dt) {
							var data = eval("(" + dt + ")");
							if (data.success) {
								alert("操作成功！");
								window.location.reload();
							} else {
								alert(data.msg);
							}
						});
			});	
});
function fillLinkContent(obj,certificateId) {
	var $this = $(obj);
	$("input[name=ordFaxRecvLink.ebkCertificateId]").attr("readonly","true");
	$("input[name=ordFaxRecvLink.ebkCertificateId]").val(certificateId);
	$("input[name=ordFaxRecvLink.ordFaxRecvLinkId]").val($this.attr("data"));
}
</script>
</body>
</html>