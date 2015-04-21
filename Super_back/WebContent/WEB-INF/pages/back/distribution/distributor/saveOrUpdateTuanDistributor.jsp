<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	if($.trim($("#form1 #distributorName").val()).length>40){
		$("#form1 #distributorName").focus();
		alert("分销商名称不能超过40");
		return false;
	}
	var distCode = $.trim($("#form1 #distributorCode").val());
	if(distCode == ""){
		$("#form1 #distributorCode").focus();
		alert("分销商编号不能为空");
		return false;
	}
	var regex=/^[0-9A-Z_]{1,18}$/
	if(!regex.exec(distCode)){
		alert("分销商编号规则不对 (1-18位数字/大写字母/_组成)");
		return false;
	}
	if($.trim($("#form1 #channelType").val()) == ""){
		$("#form1 #channelType").focus();
		alert("分销商渠道不能为空");
		return false;
	}
	
	if($("#form1 #memo").val().length >200 ){
		$("#form1 #memo").focus();
		alert("备注最多200个字符");
		return false;
	}
	$.post($("#form1").attr("action"),
			$("#form1").serialize(),
			function(dt){
				alert(dt.msg);
				parent.location.href="${basePath}/distributiontuan/searchListDistributor.do";
			}
	);
}
</script>
</head>
<body>
<form id='form1' method='post' action='${basePath}/distributiontuan/saveOrUpdateDistributor.do'>
	<table class="p_table form-inline"> 
		<tbody>
			<tr class="p_label">
				<td>分销商ID：</td>
				<td>${distributor.distributorTuanInfoId}
	            <input type="hidden" name="distributorTuanInfoId" value="${distributor.distributorTuanInfoId}"/>
	            </td>
	            <td>分销商名称：</td>
				<td><input type="text" id="distributorName" name="distributorName" value="${distributor.distributorName }"/></td>
			</tr>
			<tr class="p_label">
				<td>分销商渠道类型：</td>
				<td>
				<select id="channelType" name="channelType" <s:if test="distributor.distributorTuanInfoId!=null">disabled="true"</s:if>>
					<option  value="EXPORT_DIEM" <c:if test="${'EXPORT_DIEM' eq distributor.channelType}">selected="selected"</c:if> >批量导码</option>
					<option  value="DIST_YUYUE" <c:if test="${'DIST_YUYUE' eq distributor.channelType}">selected="selected"</c:if> >分销预约平台</option>
				</select>
				</td>
				<td>分销商编号：</td>
				<td><input type="text" id="distributorCode" name="distributorCode" value="${distributor.distributorCode }" <s:if test="distributor.distributorTuanInfoId!=null">readonly="true"</s:if>/></td>
				
			</tr>
			<tr class="p_label">
				<td >备注：</td>
				<td colspan="3">
				<textarea id="memo"  style="width: 350px;height: 100px;" name="memo">${distributor.memo}</textarea>
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