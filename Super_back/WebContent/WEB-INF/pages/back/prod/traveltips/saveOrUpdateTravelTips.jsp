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
<style type="text/css">
td textarea{height:120px;width:550px;}
</style>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath}kindeditor-3.5.1/kindeditor.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	KE.show({
		id:'content',    	
		cssPath : '${basePath}kindeditor-3.5.1/skins/default.css',
		filterMode : true
	});
	$(function(){
		$("#form1 #continent").val('${travelTips.continent}');	
	});

	function checkAndSubmitTravelTips(){
		if($.trim($("#form1 #continent").val()) == ""){
			$("#form1 #continent").focus();
			alert("洲域不能为空！");
			return false;
		}
		if($.trim($("#form1 #country").val()) == ""){
			$("#form1 #country").focus();
			alert("国家不能为空！");
			return false;
		}
		if($.trim($("#form1 #tipsName").val()) == ""){
			$("#form1 #tipsName").focus();
			alert("旅游须知名称不能为空!");
			return false;
		}
		
		if($("#form1 #content").val().length <1 ){
			$("#form1 #content").focus();
			alert("旅行须知内容不能为空!");
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
<form id='form1' method='post' action='${basePath}/prod/saveOrUpdateTravelTips.do'>
	<table class="p_table form-inline"> 
		<tbody>
			<input type="hidden" name="travelTips.travelTipsId" value="${travelTips.travelTipsId}"/>
			<tr class="p_label">
				<td>所在洲域：</td>
				<td>
				<select id="continent" name="travelTips.continent">
				<option value="">所在洲域</option>
				<s:iterator value="continentList" var="con">
				<option value="${con.code}">${con.cnName}</option> 
				</s:iterator>
				</select>
	            </td>
	            <td>所在国家：</td>
	            <td><input type="text" id="country" name="travelTips.country" value="${travelTips.country}"/></td>
			</tr>
			<tr class="p_label">
				<td>须知名称：</td>
				<td><input type="text" id="tipsName" name="travelTips.tipsName" value="${travelTips.tipsName}"/></td>
				<td colspan="2">
				</td>
			</tr>
			<tr class="p_label">
				<td>内容：</td>
				<td colspan="3"><textarea id="content" name="travelTips.content" >${travelTips.content}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<p class="tc mt20">
		<input type="button" value="保存" id="checkAndSubmitTravelTips_btn" onclick="checkAndSubmitTravelTips();" class="btn btn-small w6" />
	</p>
</form>
</body>
</html>