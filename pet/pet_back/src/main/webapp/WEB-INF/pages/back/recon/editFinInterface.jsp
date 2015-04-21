<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>对账接口信息</title>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
</head>
<script type="text/javascript">
$(function(){
	$("#frmEdit").validateAndSubmit(
			function($form,dt){
				var data=eval("("+dt+")");//alert(data.msg);
				if(data.success){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
	});
});
</script>
<body>
	<form id="frmEdit" method="post" action="${basePath}/recon/editFinInterface.do">
		<input type="hidden" name="finGLInterface.glInterfaceId" value="${finGLInterface.glInterfaceId}" />
		<table class="cg_xx" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td>借方科目编码</td>
				<td>
					<input type="text" name="finGLInterface.borrowerSubjectCode" 
					value="${finGLInterface.borrowerSubjectCode}" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td>贷方科目编码</td>
				<td>
					<input type="text" name="finGLInterface.lenderSubjectCode" 
					value="${finGLInterface.lenderSubjectCode}" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td>凭证类型</td>
				<td>
					<input type="text" name="finGLInterface.proofType" 
					value="${finGLInterface.proofType}" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td>帐套号</td>
				<td>
					<input type="text" name="finGLInterface.accountBookId" 
					value="${finGLInterface.accountBookId}" maxlength="50"/>
				</td>
			</tr>
		</table>
		<center>
			<input type="submit" id="button" value="  保存    " class="button" />
		</center>
	</form>
</body>
<div id="fff"></div>
</html>