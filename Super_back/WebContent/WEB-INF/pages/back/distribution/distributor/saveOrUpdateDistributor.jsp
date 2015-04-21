<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
function checkAndSubmitDist(){
	if($.trim($("#form1 #distributorName").val()) == ""){
		$("#form1 #distributorName").focus();
		alert("分销商名称不能为空");
		return false;
	}
	if($.trim($("#form1 #distributorCode").val()) == ""){
		$("#form1 #distributorCode").focus();
		alert("分销商编号不能为空");
		return false;
	}
	if($.trim($("#form1 #channelCode").val()) == ""){
		$("#form1 #channelCode").focus();
		alert("分销商渠道不能为空");
		return false;
	}
	
	if($("#form1 #remark").val().length >200 ){
		$("#form1 #remark").focus();
		alert("备注最多200个字符");
		return false;
	}
	
	$.post($("#form1").attr("action"),
			$("#form1").serialize(),
			function(dt){
				var data=dt;
				if(data.success){
					alert("操作成功");
					parent.location.reload(true);
				}else{
					alert(data.msg);
				}
			}
	);
}
</script>
</head>
<body>
<form id='form1' method='post' action='${basePath}/distribution/saveOrUpdateDistributor.do'>
	<table class="p_table form-inline"> 
		<tbody>
			<tr class="p_label">
				<td>分销商ID：</td>
				<td>${distributor.distributorInfoId}
	            <input type="hidden" name="distributor.distributorInfoId" value="${distributor.distributorInfoId}"/>
	            </td>
	            <td>分销商密钥：</td>
	            <td><input type="text" disabled="disabled" id="distributorKey" name="distributor.distributorKey" value="${distributor.distributorKey}"/></td>
			</tr>
			<tr class="p_label">
				<td>分销商名称：</td>
				<td><input type="text" id="distributorName" name="distributor.distributorName" value="${distributor.distributorName }"/></td>
				<td>分销商编号：</td>
				<td><input type="text" id="distributorCode" name="distributor.distributorCode" value="${distributor.distributorCode }"/></td>
			</tr>
			<tr class="p_label">
				<td>分销商渠道：</td>
				<td><input type="text" id="channelCode" name="distributor.channelCode" value="${distributor.channelCode}"/></td>
				<td>是否推送更新：</td>
				<td><input type="checkbox" id="isPushUpdate" name="distributor.isPushUpdate" <s:if test="distributor.isPushUpdate=='true'">checked='checked'</s:if> />
				</td>
			</tr>
			<tr class="p_label">
				<td>是否允许注册用户：</td>
				<td><input type="checkbox" id="isRegisterUser" name="distributor.isRegisterUser" <s:if test="distributor.isRegisterUser=='true'">checked='checked'</s:if> /></td>
				<td>是否系统新增产品：</td>
				<td><input type="checkbox" id="isAddNewprod" name="distributor.isAddNewprod" <s:if test="distributor.isAddNewprod=='true'">checked='checked'</s:if> /></td>
			</tr>
			<tr class="p_label">
				<td >备注：</td>
				<td colspan="3">
				<textarea id="remark"  style="width: 350px;height: 100px;" name="distributor.remark">${distributor.remark}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<p class="tc mt20">
		<input type="button" value="保存" id="checkAndSubmitDist_btn" onclick="checkAndSubmitDist();" class="btn btn-small w6" />
	</p>
</form>
</body>
</html>